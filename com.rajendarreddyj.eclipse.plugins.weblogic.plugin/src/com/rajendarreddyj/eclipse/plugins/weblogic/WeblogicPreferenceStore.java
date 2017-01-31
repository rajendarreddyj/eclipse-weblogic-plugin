package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This Class will be Used to retrieve data from stored preferences
 * 
 * @author rajendarreddyj
 *
 */
public final class WeblogicPreferenceStore implements WeblogicPluginResources {
    /**
     * @return
     */
    public static String getOracleHome() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_ORACLEHOME);
    }

    /**
     * @return
     */
    public static String getWeblogicHome() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_WLHOME);
    }

    /**
     * @return
     */
    public static String getDomainName() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_DOMAINNAME);
    }

    /**
     * @return
     */
    public static String getDomainDir() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_DOMAINDIR);
    }

    /**
     * @return
     */
    public static String getServerName() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_SERVERNAME);
    }

    /**
     * @return
     */
    public static String getUserName() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_USERNAME);
    }

    /**
     * @return
     */
    public static String getPassword() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_PASSWORD);
    }

    /**
     * @return
     */
    public static String getHostname() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_HOSTNAME);
    }

    /**
     * @return
     */
    public static String getPort() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_PORT);
    }

    /**
     * @param str
     * @param delim
     * @return
     */
    private static List<String> toArrayList(final String str, final String delim) {
        final List<String> strings = new ArrayList<>();
        final StringTokenizer tokenizer = new StringTokenizer(str, delim);
        while (tokenizer.hasMoreTokens()) {
            strings.add(tokenizer.nextToken());
        }
        return strings;
    }

    /**
     * @return
     */
    public static String getProjects() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_PROJECTS);
    }

    /**
     * @return
     */
    public static List<String> getProjectClassPathList() {
        final String projects = getProjects();
        final List<String> projectList = toArrayList(projects, PATH_SEPARATOR);
        final List<String> projectClassPathList = new ArrayList<>();
        final Iterator<String> iterator = projectList.iterator();
        while (iterator.hasNext()) {
            final String projectName = iterator.next();
            final IProject iproject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
            final IJavaProject jproject = JavaCore.create(iproject);
            try {
                if ((jproject != null) && jproject.exists() && jproject.isOpen()) {
                    final String[] projectcp = JavaRuntime.computeDefaultRuntimeClassPath(jproject);
                    for (final String element : projectcp) {
                        projectClassPathList.add(element);
                    }
                }
            } catch (final CoreException localCoreException) {
            }
        }
        return projectClassPathList;
    }

    public static String getPreClassPath() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_PRE_CLASSPATH);
    }

    public static List<String> getPreClassPathList() {
        final String preClassPath = getPreClassPath();
        return toArrayList(preClassPath, PATH_SEPARATOR);
    }

    public static String getPostClassPath() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_POST_CLASSPATH);
    }

    public static List<String> getPostClassPathList() {
        final String postClassPath = getPostClassPath();
        return toArrayList(postClassPath, PATH_SEPARATOR);
    }

    public static String getJVMOptions() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_JVM_OPTIONS);
    }

    public static List<String> getJVMOptionList() {
        final String jvmoptions = getJVMOptions();
        return toArrayList(jvmoptions, PATH_SEPARATOR);
    }

    public static String getLibPath() {
        final IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString(PREF_LIBPATH);
    }

    public static List<String> getLibPathList() {
        final String libPath = getLibPath();
        return toArrayList(libPath, PATH_SEPARATOR);
    }
}
