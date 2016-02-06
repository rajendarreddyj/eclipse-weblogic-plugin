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
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class WeblogicPlugin extends AbstractUIPlugin {

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
        plugin = this;
        try {
            this.resourceBundle = ResourceBundle.getBundle(PLUGIN_ID + ".resources");
        } catch (MissingResourceException x) {
            this.resourceBundle = null;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
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
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Returns a String Value for Key value from Resources properties
     * 
     * @param key
     * @return
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = getDefault().getResourceBundle();
        try {
            return bundle != null ? bundle.getString(key) : key;
        } catch (MissingResourceException e) {
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

    public static void log(String msg) {
        ILog log = getDefault().getLog();
        Status status = new Status(4, PLUGIN_ID, 4, msg, null);
        log.log(status);
    }

    public WeblogicLauncher startWeblogic() {
        return new StartWeblogic();
    }

    public WeblogicLauncher stopWeblogic() {
        return new StopWeblogic();
    }

    public static void log(Exception ex) {
        ILog log = getDefault().getLog();
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        String msg = stringWriter.getBuffer().toString();

        Status status = new Status(4, PLUGIN_ID, 4, msg, null);
        log.log(status);
    }

    public String getJRE() {
        IPreferenceStore pref = getDefault().getPreferenceStore();
        return pref.getString("jreid");
    }
}
