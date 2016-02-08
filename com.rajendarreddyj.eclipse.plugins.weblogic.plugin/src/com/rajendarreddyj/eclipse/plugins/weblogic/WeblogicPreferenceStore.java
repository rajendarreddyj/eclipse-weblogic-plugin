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

public final class WeblogicPreferenceStore implements WeblogicPluginResources {
    public static String getVersion() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("version");
    }

    public static String getOracleHome() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("oraclehome");
    }

    public static String getWlHome() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("wlhome");
    }

    public static String getDomainName() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("domainname");
    }

    public static String getDomainDir() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("domaindir");
    }

    public static String getServerName() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("servername");
    }

    public static String getUserName() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("username");
    }

    public static String getPassword() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("password");
    }

    public static String getHostname() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("hostname");
    }

    public static String getPort() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("port");
    }

    private static List<String> toArrayList(String str, String delim) {
        List<String> strings = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        while (tokenizer.hasMoreTokens()) {
            strings.add(tokenizer.nextToken());
        }
        return strings;
    }

    public static String getProjects() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();

        return prefs.getString("projects");
    }

    public static List<String> getProjectClassPathList() {
        String projects = getProjects();

        List<String> projectList = toArrayList(projects, ";");
        List<String> projectClassPathList = new ArrayList<>();

        Iterator<String> iterator = projectList.iterator();
        while (iterator.hasNext()) {
            String projectName =  iterator.next();
            IProject iproject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
            IJavaProject jproject = JavaCore.create(iproject);
            try {
                if ((jproject != null) && (jproject.exists()) && (jproject.isOpen())) {
                    String[] projectcp = JavaRuntime.computeDefaultRuntimeClassPath(jproject);
                    for (int i = 0; i < projectcp.length; i++) {
                        projectClassPathList.add(projectcp[i]);
                    }
                }
            } catch (CoreException localCoreException) {
            }
        }
        return projectClassPathList;
    }

    public static String getPreClassPath() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("preclasspath");
    }

    public static List<String> getPreClassPathList() {
        String preClassPath = getPreClassPath();
        return toArrayList(preClassPath, ";");
    }

    public static String getPostClassPath() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("postclasspath");
    }

    public static List<String> getPostClassPathList() {
        String postClassPath = getPostClassPath();
        return toArrayList(postClassPath, ";");
    }

    public static String getJVMOptions() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("jvmoptions");
    }

    public static List<String> getJVMOptionList() {
        String jvmoptions = getJVMOptions();
        return toArrayList(jvmoptions, ";");
    }

    public static String getLibPath() {
        IPreferenceStore prefs = WeblogicPlugin.getDefault().getPreferenceStore();
        return prefs.getString("libpath");
    }

    public static List<String> getLibPathList() {
        String libPath = getLibPath();
        return toArrayList(libPath, ";");
    }
}
