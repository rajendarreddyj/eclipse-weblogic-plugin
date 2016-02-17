package com.rajendarreddyj.eclipse.plugins.weblogic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

/**
 * This Class will Start Weblogic Server Start Weblogic handler extends
 * AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * @author rajendarreddyj
 *
 */
public class StartActionHandler extends AbstractHandler implements WeblogicPluginResources {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		WeblogicPlugin.log(WEBLOGIC_START_MSG);
		try {
			WeblogicPlugin.getDefault().startWeblogic().run();
		} catch (final Exception ex) {
			WeblogicPlugin.log(START_FAILED_MSG);
			WeblogicPlugin.log(ex);
		}
		return null;
	}
}
