package com.rajendarreddyj.eclipse.plugins.weblogic;

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
		vmargs.add("-url");
		vmargs.add("t3://" + WeblogicPreferenceStore.getHostname() + ":" + WeblogicPreferenceStore.getPort());
		vmargs.add("-username");
		vmargs.add(WeblogicPreferenceStore.getUserName());
		vmargs.add("-password");
		vmargs.add(WeblogicPreferenceStore.getPassword());
		vmargs.add("FORCESHUTDOWN");
		vmargs.add(WeblogicPreferenceStore.getServerName());
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

}
