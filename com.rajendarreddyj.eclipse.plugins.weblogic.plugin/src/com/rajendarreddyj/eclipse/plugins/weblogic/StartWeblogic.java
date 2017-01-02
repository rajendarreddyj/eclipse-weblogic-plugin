package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class will be Used to Start Weblogic Server
 * 
 * @author rajendarreddyj
 *
 */
public class StartWeblogic extends WeblogicLauncher implements WeblogicPluginResources {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicLauncher#getLabel()
     */
    @Override
    protected String getLabel() {
        return WEBLOGIC_LABEL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicLauncher#getMainClass
     * ()
     */
    @Override
    protected String getMainClass() {
        return WEBLOGIC_MAIN_CLASS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicLauncher#getClasspath
     * ()
     */
    @Override
    protected String[] getClasspath() {
        final String weblogicHome = WeblogicPreferenceStore.getWeblogicHome();
        if ((weblogicHome != null) && (weblogicHome.length() != 0)) {
            final List<String> classpath = new ArrayList<>(WeblogicPreferenceStore.getPreClassPathList());
            classpath.add(this.getVMInstall().getInstallLocation() + File.separator + "lib" + File.separator + "tools.jar");
            classpath.add(weblogicHome + File.separator + "server" + File.separator + "lib" + File.separator + "weblogic.jar");
            classpath.add(weblogicHome + File.separator + "server" + File.separator + "lib" + File.separator + "weblogic_sp.jar");
            classpath.addAll(WeblogicPreferenceStore.getPostClassPathList());
            classpath.addAll(WeblogicPreferenceStore.getProjectClassPathList());
            return classpath.toArray(new String[0]);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicLauncher#getVMArgs()
     */
    @Override
    protected String[] getVMArgs() {
        final List<String> vmargs = new ArrayList<>(WeblogicPreferenceStore.getJVMOptionList());
        vmargs.add("-Dweblogic.Name=" + WeblogicPreferenceStore.getServerName());
        vmargs.add("-Dweblogic.Domain=" + WeblogicPreferenceStore.getDomainName());
        vmargs.add("-Dweblogic.home=" + WeblogicPreferenceStore.getWeblogicHome() + File.separator + "server" + File.separator);
        vmargs.add("-Dweblogic.management.username=" + WeblogicPreferenceStore.getUserName());
        vmargs.add("-Dweblogic.management.password=" + WeblogicPreferenceStore.getPassword());

        final List<String> libpathlist = WeblogicPreferenceStore.getLibPathList();
        if (libpathlist.size() > 0) {
            final String[] libpatharray = libpathlist.toArray(new String[0]);
            final StringBuffer buff = new StringBuffer(libpatharray[0]);
            for (int i = 1; i < libpatharray.length; i++) {
                buff.append(File.pathSeparator);
                buff.append(libpatharray[i]);
            }
            vmargs.add("-Djava.library.path=" + buff.toString());
        }
        return vmargs.toArray(new String[0]);
    }

    @Override
    protected String getWorkdir() {
        return WeblogicPreferenceStore.getDomainDir();
    }

}
