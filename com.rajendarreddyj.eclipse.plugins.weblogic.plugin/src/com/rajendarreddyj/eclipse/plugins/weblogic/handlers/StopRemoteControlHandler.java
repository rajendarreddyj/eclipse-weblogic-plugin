package com.rajendarreddyj.eclipse.plugins.weblogic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.rajendarreddyj.eclipse.plugins.weblogic.RemoteControl;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

/**
 * This Class will Stop Weblogic Remote Control.
 * 
 * @author anandchakru
 *
 */
public class StopRemoteControlHandler extends AbstractHandler implements WeblogicPluginResources {
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
     * ExecutionEvent)
     */
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        WeblogicPlugin.log(WEBLOGIC_REMOTE_STOP_MSG);
        try {
            RemoteControl.shutown();
        } catch (final Exception ex) {
            WeblogicPlugin.log(REMOTE_STOP_FAILED_MSG);
            WeblogicPlugin.log(ex);
        }
        return null;
    }
}
