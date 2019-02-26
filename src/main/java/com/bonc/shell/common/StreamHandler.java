package com.bonc.shell.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class StreamHandler implements Runnable {

  private CountDownLatch countDownLatch;
  private InputStream inputStream;
  private String type;
  private OutputStream outputStream;

  public StreamHandler(CountDownLatch countDownLatch, InputStream inputStream, String type) {
    this(countDownLatch, inputStream, type, null);
  }

  public StreamHandler(CountDownLatch countDownLatch, InputStream inputStream, String type, OutputStream outputStream) {
    this.countDownLatch = countDownLatch;
    this.inputStream = inputStream;
    this.type = type;
    this.outputStream = outputStream;
  }

  @Override
  public void run() {
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    PrintWriter printWriter = null;
    if (outputStream != null) {
      printWriter = new PrintWriter(outputStream);
    }
    inputStreamReader = new InputStreamReader(inputStream);
    bufferedReader = new BufferedReader(inputStreamReader);
    String line;
    try {
      while ((line = bufferedReader.readLine()) != null) {
        byte[] bytes = line.getBytes();
        line = new String(bytes, StandardCharsets.UTF_8);
        if (printWriter != null) {
          printWriter.println(line);
        }
        System.out.println(type + ">" + line);
      }
      if (printWriter != null) {
        printWriter.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (printWriter != null) {
        printWriter.close();
      }
      try {
        bufferedReader.close();
        inputStreamReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    countDownLatch.countDown();
  }
}
