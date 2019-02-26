package com.bonc.shell.common;

public class RuntimeEnvironment {
  public static int getCurrentSystem() {
    String osName = System.getProperties().getProperty("os.name");
    if (osName != null && osName.toLowerCase().contains("windows")) {
      return 0;
    } else if (osName != null && osName.toLowerCase().contains("linux")) {
      return 1;
    } else {
      return -1;
    }
  }
}
