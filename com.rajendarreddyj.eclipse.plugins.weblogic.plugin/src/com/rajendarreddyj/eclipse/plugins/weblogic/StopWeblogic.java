package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.util.ArrayList;

public class StopWeblogic extends WeblogicLauncher {

    protected String getLabel()
    {
      return "WebLogic Server";
    }
    
    protected String getMainClass()
    {
      return "weblogic.Admin";
    }
    
    protected String[] getClasspath()
    {
      return WeblogicPlugin.getDefault().startWeblogic().getClasspath();
    }
    
    protected String[] getPrgArgs()
    {
      String hostname = WeblogicPreferenceStore.getHostname();
      String port = WeblogicPreferenceStore.getPort();
      String username = WeblogicPreferenceStore.getUserName();
      String password = WeblogicPreferenceStore.getPassword();
      String servername = WeblogicPreferenceStore.getServerName();
      
      ArrayList<String> vmargs = new ArrayList<>();
      vmargs.add("-url");
      vmargs.add("t3://" + hostname + ":" + port);
      vmargs.add("-username");
      vmargs.add(username);
      vmargs.add("-password");
      vmargs.add(password);
      vmargs.add("FORCESHUTDOWN");
      vmargs.add(servername);
      
      return (String[])vmargs.toArray(new String[0]);
    }
    
    protected String getWorkdir()
    {
      String domaindir = WeblogicPreferenceStore.getDomainDir();
      return domaindir;
    }

}
