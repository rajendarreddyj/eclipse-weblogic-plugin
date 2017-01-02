package com.rajendarreddyj.eclipse.plugins.weblogic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

/**
 * This Class will Stop Stop Weblogic handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * @author rajendarreddyj
 *
 */
public class StopActionHandler extends AbstractHandler implements WeblogicPluginResources {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
     * ExecutionEvent)
     */
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        WeblogicPlugin.log(WEBLOGIC_STOP_MSG);
        try {
            WeblogicPlugin.getDefault().stopWeblogic().run();
        } catch (final Exception ex) {
            WeblogicPlugin.log(STOP_FAILED_MSG);
            WeblogicPlugin.log(ex);
        }
        return null;
    }

}
