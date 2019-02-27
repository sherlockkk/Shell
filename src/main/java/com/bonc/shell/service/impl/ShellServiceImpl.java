package com.bonc.shell.service.impl;

import com.bonc.shell.common.RuntimeEnvironment;
import com.bonc.shell.common.StreamHandler;
import com.bonc.shell.domain.AnalysisData;
import com.bonc.shell.service.ShellService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Service
public class ShellServiceImpl implements ShellService {
  @Override
  public long invokeShell(String location, AnalysisData analysisData) {
    String startTime = analysisData.getStartTime() == null ? "" : analysisData.getStartTime().toString();
    String endTime = analysisData.getEndTime() == null ? "" : analysisData.getEndTime().toString();
    String pointCodes = analysisData.getPointCodes() == null ? "" : analysisData.getPointCodes();
    String sessionId = analysisData.getSessionId() == null ? "" : analysisData.getSessionId();
    return executeShell(location, startTime, endTime, pointCodes, sessionId);
  }

  public synchronized static long executeShell(String... shellArgs) {
    final CountDownLatch countDownLatch = new CountDownLatch(2);
    long pid = -1;
    //格式化日期时间，记录日志时使用
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ");
    try {
      Process process;
      String[] command = new String[0];
      int currentSystem = RuntimeEnvironment.getCurrentSystem();
      switch (currentSystem) {
        //windows
        case 0:
          String[] cmd = new String[]{"cmd", "/c"};
          command = ArrayUtils.addAll(cmd, shellArgs);
          break;
        //linux
        case 1:
          String[] shell = new String[]{"/bin/sh"};
          command = ArrayUtils.addAll(shell, shellArgs);
          break;
        default:
          break;
      }
      System.out.println(dateFormat.format(new Date()) + "开始执行shell脚本：" + Arrays.toString(shellArgs));
      process = Runtime.getRuntime().exec(command);
      if (process != null) {
        switch (currentSystem) {
          case 0:
            Field handleWindows = process.getClass().getDeclaredField("handle");
            handleWindows.setAccessible(true);
            pid = handleWindows.getLong(process);
            break;
          case 1:
            Field handleLinux = process.getClass().getDeclaredField("pid");
            handleLinux.setAccessible(true);
            pid = handleLinux.getLong(process);
            break;
          default:
            break;
        }

        System.out.println("进程号：" + pid);
        //为了防止阻塞，分别开单独线程去处理标准输出流和标准错误输出流
        new Thread(new StreamHandler(countDownLatch, process.getInputStream(), "STDOUT")).start();
        new Thread(new StreamHandler(countDownLatch, process.getErrorStream(), "STDERR")).start();
        process.waitFor();
        countDownLatch.await();
      } else {
        System.out.println("没有pid");
      }
      System.out.println(dateFormat.format(new Date()) + "Shell命令执行完毕");
    } catch (Exception ioe) {
      System.out.println("执行Shell命令时发生异常：>>>" + ioe.getMessage());
      ioe.printStackTrace();
    }
    return pid;
  }
}
