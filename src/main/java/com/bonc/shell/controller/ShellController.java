package com.bonc.shell.controller;

import com.alibaba.fastjson.JSON;
import com.bonc.shell.domain.AnalysisData;
import com.bonc.shell.service.ShellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/shell")
public class ShellController {

  @Autowired
  private ShellService shellService;

  @PostMapping("/execute")
  public Long executeShell(@RequestBody Map<String, Object> data) {
    String location = (String) data.get("location");
    AnalysisData analysisData = JSON.parseObject(JSON.toJSONString(data.get("analysisData")), AnalysisData.class);
    return shellService.invokeShell(location, analysisData);
  }
}
