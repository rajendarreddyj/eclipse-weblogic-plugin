package com.rajendarreddyj.eclipse.plugins.weblogic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

/**
 * Start Weblogic handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class StartActionHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        WeblogicPlugin.log(WeblogicPluginResources.WEBLOGIC_START_MSG);
        try {
            WeblogicPlugin.getDefault().startWeblogic().run();
        } catch (Exception ex) {
            WeblogicPlugin.log(WeblogicPluginResources.START_FAILED_MSG);
            WeblogicPlugin.log(ex);
        }
        return null;
    }
}
