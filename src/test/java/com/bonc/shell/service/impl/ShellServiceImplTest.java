package com.bonc.shell.service.impl;

import com.bonc.shell.domain.AnalysisData;
import com.bonc.shell.service.ShellService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShellServiceImplTest {

  @Autowired
  private ShellService shellServicea;

  @Test
  public void executeShell() {
    String location = "location";
    AnalysisData analysisData = new AnalysisData();
    analysisData.setStartTime(11111111L);
    analysisData.setEndTime(222222222L);
    analysisData.setPointCodes("xxxx,cccc,dddd");
    shellServicea.invokeShell(location, analysisData);
  }
}