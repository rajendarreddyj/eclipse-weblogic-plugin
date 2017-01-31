package com.rajendarreddyj.eclipse.plugins.weblogic.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

/**
 * This class represents Weblogic preference page that is contributed to the Preferences dialog. By subclassing
 * <samp>FieldEditorPreferencePage</samp>, we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to the main
 * plug-in class. That way, preferences can be accessed directly via the preference store.
 * 
 * @author rajendarreddyj
 *
 */
public class WeblogicPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage, WeblogicPluginResources {
    /**
     * 
     */
    public WeblogicPreferencePage() {
        super(GRID);
        this.setPreferenceStore(WeblogicPlugin.getDefault().getPreferenceStore());
        this.setDescription(WEBLOGIC_DESCRIPTION_LABEL);
        this.initializeDefaults();
    }

    /**
     * This method will Initialize Default values(if any)
     */
    private void initializeDefaults() {
    }

    /**
     * Creates the Weblogic Preferece page field editors. Field editors are abstractions of the common GUI blocks needed
     * to manipulate various types of preferences. Each field editor knows how to save and restore itself.
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
     */
    @Override
    public void createFieldEditors() {
        this.addField(new DirectoryFieldEditor(PREF_ORACLEHOME, WEBLOGIC_ORACLEHOME_LABEL, this.getFieldEditorParent()));
        this.addField(new DirectoryFieldEditor(PREF_WLHOME, WEBLOGIC_WLHOME_LABEL, this.getFieldEditorParent()));
        this.addField(new StringFieldEditor(PREF_DOMAINNAME, WEBLOGIC_DOMAINNAME_LABEL, 20, this.getFieldEditorParent()));
        this.addField(new DirectoryFieldEditor(PREF_DOMAINDIR, WEBLOGIC_DOMAINDIR_LABEL, this.getFieldEditorParent()));
        this.addField(new StringFieldEditor(PREF_SERVERNAME, WEBLOGIC_SERVERNAME_LABEL, 20, this.getFieldEditorParent()));
        this.addField(new StringFieldEditor(PREF_USERNAME, WEBLOGIC_USER_LABEL, 20, this.getFieldEditorParent()));
        this.addField(new StringFieldEditor(PREF_PASSWORD, WEBLOGIC_PASSWORD_LABEL, 20, this.getFieldEditorParent()));
        this.addField(new StringFieldEditor(PREF_HOSTNAME, WEBLOGIC_HOSTNAME_LABEL, 20, this.getFieldEditorParent()));
        this.addField(new StringFieldEditor(PREF_PORT, WEBLOGIC_PORT_LABEL, 10, this.getFieldEditorParent()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(final IWorkbench workbench) {
    }
}