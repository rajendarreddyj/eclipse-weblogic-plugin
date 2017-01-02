package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class Will be Used To Stop Weblogic Server
 * 
 * @author rajendarreddyj
 *
 */
public class StopWeblogic extends WeblogicLauncher implements WeblogicPluginResources {

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
        return WEBLOGIC_WLST;
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
        return WeblogicPlugin.getDefault().startWeblogic().getClasspath();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicLauncher#getPrgArgs()
     */
    @Override
    protected String[] getPrgArgs() {
        final ArrayList<String> vmargs = new ArrayList<>();
        final String filePath = WeblogicPreferenceStore.getDomainDir() + File.separator + "bin" + File.separator + "shutdown.py";
        final File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), false));
            bufferedWriter.write(getShutDownWLSTScript());
            bufferedWriter.close();
            vmargs.add(filePath);
        } catch (final IOException e) {
        } finally {
        }
        return vmargs.toArray(new String[0]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicLauncher#getWorkdir()
     */
    @Override
    protected String getWorkdir() {
        return WeblogicPreferenceStore.getDomainDir();
    }

    /**
     * This will get Shutdown WLST script for weblogic Server
     * 
     * @return
     */
    private static String getShutDownWLSTScript() {
        final StringBuffer sb = new StringBuffer(STRING_EMPTY);
        sb.append("connect" + STRING_OPEN_PARANTHESIS);
        sb.append("url=" + STRING_SINGLE_QUOTE + "t3://" + WeblogicPreferenceStore.getHostname() + STRING_COLON + WeblogicPreferenceStore.getPort()
                + STRING_SINGLE_QUOTE + STRING_COMMA);
        sb.append("adminServerName=" + STRING_SINGLE_QUOTE + WeblogicPreferenceStore.getServerName() + STRING_SINGLE_QUOTE);
        sb.append(STRING_CLOSE_PARANTHESIS);
        sb.append(PATH_SEPARATOR);
        sb.append("shutdown" + STRING_OPEN_PARANTHESIS);
        sb.append(STRING_SINGLE_QUOTE + WeblogicPreferenceStore.getServerName() + STRING_SINGLE_QUOTE + STRING_COMMA);
        sb.append(STRING_SINGLE_QUOTE + "Server" + STRING_SINGLE_QUOTE + STRING_COMMA);
        sb.append("ignoreSessions= " + STRING_SINGLE_QUOTE + "true" + STRING_SINGLE_QUOTE);
        sb.append(STRING_CLOSE_PARANTHESIS);
        sb.append(PATH_SEPARATOR);
        sb.append("exit()");
        sb.append(PATH_SEPARATOR);
        return sb.toString();
    }

}
