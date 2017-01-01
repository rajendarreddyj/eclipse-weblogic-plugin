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
			String port = WeblogicPreferenceStore.getRemotePort();
			WeblogicPlugin.log(WEBLOGIC_REMOTE_START_MSG + ":" + startUrl + ":" + stopUrl + ":" + port);
			if (startUrl != null && startUrl.length() > 0 && port != null && port.length() > 0 && stopUrl != null && stopUrl.length() > 0) {
				RemoteControl.init(startUrl, stopUrl, Integer.parseInt(port));
			}
		} catch (final Exception ex) {
			WeblogicPlugin.log(REMOTE_START_FAILED_MSG);
			WeblogicPlugin.log(ex);
		}
		return null;
	}
}