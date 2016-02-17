package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputer;
import org.eclipse.debug.core.sourcelookup.containers.DefaultSourceContainer;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.launching.JavaSourceLookupDirector;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.jdt.launching.sourcelookup.containers.JavaProjectSourceContainer;
import org.eclipse.jdt.launching.sourcelookup.containers.PackageFragmentRootSourceContainer;

/**
 * This Abstract class will be Used in Starting and Stoping of Weblogic Server
 * 
 * @author rajendarreddyj
 *
 */
@SuppressWarnings({ "restriction" })
public abstract class WeblogicLauncher implements WeblogicPluginResources {
	String[] defaultBootClasspath = new String[0];
	String[] defaultVMArgs = new String[0];
	String[] defaultPrgArgs = new String[0];

	/**
	 * 
	 * @return
	 */
	public IVMInstall getVMInstall() {
		final IVMInstallType[] vmTypes = JavaRuntime.getVMInstallTypes();
		for (final IVMInstallType vmType : vmTypes) {
			final IVMInstall[] vms = vmType.getVMInstalls();
			for (final IVMInstall vm : vms) {
				if (vm.getId().equals(WeblogicPlugin.getDefault().getJRE())) {
					return vm;
				}
			}
		}
		return JavaRuntime.getDefaultVMInstall();
	}

	/**
	 * @param label
	 * @param classToLaunch
	 * @param classpath
	 * @param bootClasspath
	 * @param vmArgs
	 * @param prgArgs
	 * @param workDir
	 * @param sourceLocator
	 * @param debug
	 * @param showInDebugger
	 * @throws CoreException
	 */
	public void runVM(final String label, final String classToLaunch, final String[] classpath,
			final String[] bootClasspath, final String[] vmArgs, final String[] prgArgs, final String workDir,
			final ISourceLocator sourceLocator, final boolean debug, final boolean showInDebugger)
			throws CoreException {
		final IVMInstall vmInstall = getVMInstall();
		String mode = "";
		if (debug && classToLaunch.equals(WEBLOGIC_MAIN_CLASS)) {
			mode = "debug";
		} else {
			mode = "run";
		}
		final IVMRunner vmRunner = vmInstall.getVMRunner(mode);

		final ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfigurationType("org.eclipse.jdt.launching.localJavaApplication");
		final ILaunchConfigurationWorkingCopy config = launchType.newInstance(null, label);
		config.setAttribute(IDebugUIConstants.ATTR_PRIVATE, true);
		config.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID,
				"org.eclipse.jdt.launching.sourceLocator.JavaSourceLookupDirector");
		DebugUITools.setLaunchPerspective(launchType, mode, IDebugUIConstants.PERSPECTIVE_DEFAULT);
		final Launch launch = new Launch(config, mode, sourceLocator);

		config.doSave();
		if (vmRunner != null) {
			WeblogicPlugin.log("VM ARGS" + Arrays.toString(vmArgs));
			WeblogicPlugin.log("classpath" + Arrays.toString(classpath));
			final VMRunnerConfiguration vmConfig = new VMRunnerConfiguration(classToLaunch, classpath);
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

	/**
	 * 
	 * @return
	 */
	protected abstract String getLabel();

	/**
	 * @return
	 */
	protected abstract String getMainClass();

	/**
	 * @return
	 */
	protected abstract String[] getClasspath();

	/**
	 * @return
	 */
	protected String[] getBootClasspath() {
		return defaultBootClasspath;
	}

	/**
	 * @return
	 */
	protected String[] getVMArgs() {
		return defaultVMArgs;
	}

	/**
	 * @return
	 */
	protected String[] getPrgArgs() {
		return defaultPrgArgs;
	}

	/**
	 * @return
	 */
	protected abstract String getWorkdir();

	/**
	 * @return
	 * @throws CoreException
	 */
	protected ISourceLocator getSourceLocator() throws CoreException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject[] allProjects = root.getProjects();
		final List<IProjectNature> tempList = new ArrayList<>(allProjects.length);
		for (final IProject allProject : allProjects) {
			if (allProject.isOpen() && allProject.hasNature(JavaCore.NATURE_ID)) {
				tempList.add(allProject.getNature(JavaCore.NATURE_ID));
			}
		}
		final ISourceLookupDirector sourceLocator = new JavaSourceLookupDirector();

		final ISourcePathComputer computer = DebugPlugin.getDefault().getLaunchManager()
				.getSourcePathComputer("org.eclipse.jdt.launching.sourceLookup.javaSourcePathComputer");
		sourceLocator.setSourcePathComputer(computer);

		final List<ISourceContainer> sourceContainers = new ArrayList<>();

		if (!tempList.isEmpty()) {
			final IJavaProject[] javaProjects = tempList.toArray(new IJavaProject[1]);

			// Eclipse stops looking for source if it finds a jar containing the
			// source code
			// despite this jar as no attached source (the user will have to use
			// 'Attach source' button).
			// So we have to enforce that sources in project are searched before
			// jar files,
			// To do so we add source containers in this orders :
			// - First project source containers.
			// - second packageFragmentRoot container (jar files in projects
			// build path will be added to source path)
			// - third DefaultSourceContainer (jar files added to classpath will
			// be added to source path)

			// First add all projects source containers
			for (final IJavaProject project : javaProjects) {
				sourceContainers.add(new JavaProjectSourceContainer(project));
			}

			// Adding packageFragmentRoot source containers, so classes in jar
			// files associated to a project will be seen
			final Set<IPath> external = new HashSet<>();

			for (final IJavaProject project : javaProjects) {
				final IPackageFragmentRoot[] iPackageFragmentRoots = project.getPackageFragmentRoots();
				for (final IPackageFragmentRoot iPackageFragmentRoot : iPackageFragmentRoots) {
					if (iPackageFragmentRoot.isExternal()) {
						final IPath location = iPackageFragmentRoot.getPath();
						if (external.contains(location)) {
							continue;
						}
						external.add(location);
					}
					sourceContainers.add(new PackageFragmentRootSourceContainer(iPackageFragmentRoot));
				}
			}
		}

		// Last add DefaultSourceContainer, classes in jar files added to
		// classpath will be visible
		sourceContainers.add(new DefaultSourceContainer());

		sourceLocator.setSourceContainers(sourceContainers.toArray(new ISourceContainer[sourceContainers.size()]));
		sourceLocator.initializeParticipants();
		return sourceLocator;
	}

	/**
	 * @return
	 */
	protected boolean isDebugMode() {
		return true;
	}

	/**
	 * @return
	 */
	protected boolean isShowInDebugger() {
		return true;
	}

	/**
	 * @throws CoreException
	 */
	public void run() throws CoreException {
		final String[] tmp = getClasspath();
		if (tmp != null && tmp.length > 0) {
			runVM(getLabel(), getMainClass(), getClasspath(), getBootClasspath(), getVMArgs(), getPrgArgs(),
					getWorkdir(), getSourceLocator(), isDebugMode(), isShowInDebugger());
		}
	}
}
