package com.bonc.shell.service;


import com.bonc.shell.domain.AnalysisData;

public interface ShellService {
  long invokeShell(String location, AnalysisData analysisData);
}
