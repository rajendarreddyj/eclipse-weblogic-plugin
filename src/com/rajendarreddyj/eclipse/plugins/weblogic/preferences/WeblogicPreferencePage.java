package com.rajendarreddyj.eclipse.plugins.weblogic.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

/**
 * This class represents a preference page that is contributed to the Preferences dialog. By subclassing
 * <samp>FieldEditorPreferencePage</samp>, we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to the main
 * plug-in class. That way, preferences can be accessed directly via the preference store.
 */

public class WeblogicPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    public WeblogicPreferencePage() {
        super(GRID);
        setPreferenceStore(WeblogicPlugin.getDefault().getPreferenceStore());
        setDescription(WeblogicPluginResources.WEBLOGIC_DESCRIPTION_LABEL);
        initializeDefaults();
    }

    private void initializeDefaults() {
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common GUI blocks needed to manipulate various
     * types of preferences. Each field editor knows how to save and restore itself.
     */
    public void createFieldEditors() {
        addField(new DirectoryFieldEditor("oraclehome", WeblogicPluginResources.WEBLOGIC_ORACLEHOME_LABEL, getFieldEditorParent()));
        addField(new DirectoryFieldEditor("wlhome", WeblogicPluginResources.WEBLOGIC_WLHOME_LABEL, getFieldEditorParent()));
        addField(new StringFieldEditor("domainname", WeblogicPluginResources.WEBLOGIC_DOMAINNAME_LABEL, 20, getFieldEditorParent()));
        addField(new DirectoryFieldEditor("domaindir", WeblogicPluginResources.WEBLOGIC_DOMAINDIR_LABEL, getFieldEditorParent()));
        addField(new StringFieldEditor("servername", WeblogicPluginResources.WEBLOGIC_SERVERNAME_LABEL, 20, getFieldEditorParent()));
        addField(new StringFieldEditor("username", WeblogicPluginResources.WEBLOGIC_USER_LABEL, 20, getFieldEditorParent()));
        addField(new StringFieldEditor("password", WeblogicPluginResources.WEBLOGIC_PASSWORD_LABEL, 20, getFieldEditorParent()));
        addField(new StringFieldEditor("hostname", WeblogicPluginResources.WEBLOGIC_HOSTNAME_LABEL, 20, getFieldEditorParent()));
        addField(new StringFieldEditor("port", WeblogicPluginResources.WEBLOGIC_PORT_LABEL, 10, getFieldEditorParent()));
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

}