package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.debug.ui.JavaUISourceLocator;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.jdt.launching.sourcelookup.JavaSourceLocator;

public abstract class WeblogicLauncher implements WeblogicPluginResources {
    String[] defaultBootClasspath = new String[0];
    String[] defaultVMArgs = new String[0];
    String[] defaultPrgArgs = new String[0];

    public IVMInstall getVMInstall() {
        IVMInstallType[] vmTypes = JavaRuntime.getVMInstallTypes();
        for (int i = 0; i < vmTypes.length; i++) {
            IVMInstall[] vms = vmTypes[i].getVMInstalls();
            for (int j = 0; j < vms.length; j++) {
                if (vms[j].getId().equals(WeblogicPlugin.getDefault().getJRE())) {
                    return vms[j];
                }
            }
        }
        return JavaRuntime.getDefaultVMInstall();
    }

    public void runVM(String label, String classToLaunch, String[] classpath, String[] bootClasspath, String[] vmArgs, String[] prgArgs, String workDir,
            ISourceLocator sourceLocator, boolean debug, boolean showInDebugger) throws CoreException {
        IVMInstall vmInstall = getVMInstall();
        String mode = "";
        if ((debug) && (classToLaunch.equals("weblogic.Server"))) {
            mode = "debug";
        } else {
            mode = "run";
        }
        IVMRunner vmRunner = vmInstall.getVMRunner(mode);

        ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager()
                .getLaunchConfigurationType("org.eclipse.jdt.launching.localJavaApplication");
        ILaunchConfigurationWorkingCopy config = launchType.newInstance(null, label);
        config.setAttribute(IDebugUIConstants.ATTR_PRIVATE, true);
        config.setAttribute(IDebugUIConstants.ATTR_TARGET_DEBUG_PERSPECTIVE, "perspective_default");
        config.setAttribute(IDebugUIConstants.ATTR_TARGET_RUN_PERSPECTIVE, "perspective_default");
        config.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID, JavaUISourceLocator.ID_PROMPTING_JAVA_SOURCE_LOCATOR);

        Launch launch = new Launch(config, mode, sourceLocator);

        config.doSave();
        if (vmRunner != null) {
            VMRunnerConfiguration vmConfig = new VMRunnerConfiguration(classToLaunch, classpath);
            vmConfig.setVMArguments(vmArgs);
            vmConfig.setProgramArguments(prgArgs);
            if (workDir != null) {
                vmConfig.setWorkingDirectory(workDir);
            }
            if (bootClasspath.length == 0) {
                vmConfig.setBootClassPath(null);
            } else {
                vmConfig.setBootClassPath(bootClasspath);
            }
            vmRunner.run(vmConfig, launch, null);
        }
        if (showInDebugger) {
            DebugPlugin.getDefault().getLaunchManager().addLaunch(launch);
        }
    }

    protected abstract String getLabel();

    protected abstract String getMainClass();

    protected abstract String[] getClasspath();

    protected String[] getBootClasspath() {
        return this.defaultBootClasspath;
    }

    protected String[] getVMArgs() {
        return this.defaultVMArgs;
    }

    protected String[] getPrgArgs() {
        return this.defaultPrgArgs;
    }

    abstract String getWorkdir();

    protected ISourceLocator getSourceLocator() throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] allProjects = root.getProjects();

        List<IProjectNature> tempList = new ArrayList<>(allProjects.length);
        for (int i = 0; i < allProjects.length; i++) {
            if ((allProjects[i].isOpen()) && (allProjects[i].hasNature("org.eclipse.jdt.core.javanature"))) {
                tempList.add(allProjects[i].getNature("org.eclipse.jdt.core.javanature"));
            }
        }
        ISourceLocator sourceLocator = null;
        if (!tempList.isEmpty()) {
            IJavaProject[] javaProjects = (IJavaProject[]) tempList.toArray(new IJavaProject[1]);
            sourceLocator = new JavaSourceLocator(javaProjects, true);
        }
        return sourceLocator;
    }

    protected boolean isDebugMode() {
        return true;
    }

    protected boolean isShowInDebugger() {
        return true;
    }

    public void run() throws CoreException {
        String[] tmp = getClasspath();
        if ((tmp != null) && (tmp.length > 0)) {
            runVM(getLabel(), getMainClass(), getClasspath(), getBootClasspath(), getVMArgs(), getPrgArgs(), getWorkdir(), getSourceLocator(), isDebugMode(),
                    isShowInDebugger());
        }
    }
}
