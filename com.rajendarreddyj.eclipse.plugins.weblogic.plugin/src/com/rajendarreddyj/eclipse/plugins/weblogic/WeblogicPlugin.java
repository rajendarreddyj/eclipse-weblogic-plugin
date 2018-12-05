package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author rajendarreddyj
 *
 */
public class WeblogicPlugin extends AbstractUIPlugin implements WeblogicPluginResources {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.rajendarreddyj.eclipse.plugins.weblogic"; //$NON-NLS-1$

    // The shared instance
    private static WeblogicPlugin plugin;

    // Shared Properties
    private ResourceBundle resourceBundle;

    /**
     * The constructor
     */
    public WeblogicPlugin() {
        try {
            this.resourceBundle = ResourceBundle.getBundle(PLUGIN_ID + ".resources");
        } catch (final MissingResourceException x) {
            this.resourceBundle = null;
        }
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static WeblogicPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in relative path
     *
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(final String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Returns a String Value for Key value from Resources properties
     * 
     * @param key
     * @return
     */
    public static String getResourceString(final String key) {
        final ResourceBundle bundle = getDefault().getResourceBundle();
        try {
            return bundle != null ? bundle.getString(key) : key;
        } catch (final MissingResourceException e) {
            WeblogicPlugin.log(e);
        }
        return key;
    }

    /**
     * Returns ResourceBundle
     * 
     * @return
     */
    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    /**
     * @return
     */
    public WeblogicLauncher startWeblogic() {
        return new StartWeblogic();
    }

    /**
     * @return
     */
    public WeblogicLauncher stopWeblogic() {
        return new StopWeblogic();
    }

    /**
     * @return
     */
    public String getJRE() {
        final IPreferenceStore pref = getDefault().getPreferenceStore();
        return pref.getString(PREF_JRE);
    }

    /**
     * @param msg
     */
    public static void log(final String msg) {
        final ILog log = getDefault().getLog();
        final Status status = new Status(4, PLUGIN_ID, 4, msg, null);
        log.log(status);
    }

    /**
     * @param ex
     */
    public static void log(final Exception ex) {
        final ILog log = getDefault().getLog();
        final StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        final String msg = stringWriter.getBuffer().toString();

        final Status status = new Status(4, PLUGIN_ID, 4, msg, null);
        log.log(status);
    }

}
