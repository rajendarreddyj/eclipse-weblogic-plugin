package com.rajendarreddyj.eclipse.plugins.weblogic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import com.rajendarreddyj.eclipse.plugins.weblogic.RemoteControl;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPreferenceStore;

/**
 * This Class will Start Weblogic Remote Control.
 * 
 * @author anandchakru
 *
 */
public class StartRemoteControlHandler extends AbstractHandler implements WeblogicPluginResources {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			String startUrl = WeblogicPreferenceStore.getRemoteStart();
			String stopUrl = WeblogicPreferenceStore.getRemoteStop();
			Integer port = Integer.parseInt(WeblogicPreferenceStore.getRemotePort());
			WeblogicPlugin.log(WEBLOGIC_REMOTE_START_MSG + ":" + startUrl + ":" + stopUrl + ":" + port);
			RemoteControl.init(startUrl, stopUrl, port);
		} catch (final Exception ex) {
			WeblogicPlugin.log(REMOTE_START_FAILED_MSG);
			WeblogicPlugin.log(ex);
		}
		return null;
	}
}