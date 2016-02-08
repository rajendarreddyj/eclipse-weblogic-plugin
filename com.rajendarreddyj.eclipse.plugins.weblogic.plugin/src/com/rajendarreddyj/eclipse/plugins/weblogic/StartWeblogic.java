package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StartWeblogic extends WeblogicLauncher {

    protected String getLabel() {
        return "WebLogic Server";
    }

    protected String getMainClass() {
        return "weblogic.Server";
    }

    protected String[] getClasspath() {
        String wlHome = WeblogicPreferenceStore.getWlHome();
        if ((wlHome != null) && (wlHome.length() != 0)) {
            List<String> projectClassPathList = WeblogicPreferenceStore.getProjectClassPathList();
            List<String> preClassPathList = WeblogicPreferenceStore.getPreClassPathList();
            List<String> postClassPathList = WeblogicPreferenceStore.getPostClassPathList();

            List<String> classpath = new ArrayList<>(preClassPathList);

            classpath.add(getVMInstall().getInstallLocation() + File.separator + "lib" + File.separator + "tools.jar");
            classpath.add(wlHome + File.separator + "server" + File.separator + "lib" + File.separator + "weblogic_sp.jar");
            classpath.add(wlHome + File.separator + "server" + File.separator + "lib" + File.separator + "weblogic.jar");
            classpath.addAll(postClassPathList);
            classpath.addAll(projectClassPathList);

            return (String[]) classpath.toArray(new String[0]);
        }
        return null;
    }

    protected String[] getVMArgs() {
        String servername = WeblogicPreferenceStore.getServerName();
        String oracleHome = WeblogicPreferenceStore.getOracleHome();
        String username = WeblogicPreferenceStore.getUserName();
        String password = WeblogicPreferenceStore.getPassword();

        List<String> jvmoptions = WeblogicPreferenceStore.getJVMOptionList();

        List<String> vmargs = new ArrayList<>(jvmoptions);
        vmargs.add("-Dweblogic.Name=" + servername);
        vmargs.add("-Dbea.home=\"" + oracleHome + "\"");
        vmargs.add("-Dweblogic.management.username=" + username);
        vmargs.add("-Dweblogic.management.password=" + password);

        List<String> libpathlist = WeblogicPreferenceStore.getLibPathList();
        if (libpathlist.size() > 0) {
            String[] libpatharray = (String[]) libpathlist.toArray(new String[0]);
            StringBuffer buff = new StringBuffer(libpatharray[0]);
            for (int i = 1; i < libpatharray.length; i++) {
                buff.append(File.pathSeparator);
                buff.append(libpatharray[i]);
            }
            vmargs.add("-Djava.library.path=" + buff.toString());
        }
        return (String[]) vmargs.toArray(new String[0]);
    }

    protected String getWorkdir() {
        String domaindir = WeblogicPreferenceStore.getDomainDir();
        return domaindir;
    }

}
