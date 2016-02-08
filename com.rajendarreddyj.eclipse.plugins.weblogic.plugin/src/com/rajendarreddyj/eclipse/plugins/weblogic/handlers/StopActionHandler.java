package com.rajendarreddyj.eclipse.plugins.weblogic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

/**
 * Stop Weblogic handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class StopActionHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        WeblogicPlugin.log(WeblogicPluginResources.WEBLOGIC_STOP_MSG);
        try {
            WeblogicPlugin.getDefault().stopWeblogic().run();
        } catch (Exception ex) {
            WeblogicPlugin.log(WeblogicPluginResources.STOP_FAILED_MSG);
            WeblogicPlugin.log(ex);
        }
        return null;
    }

}
