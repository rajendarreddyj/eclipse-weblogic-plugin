package com.rajendarreddyj.eclipse.plugins.weblogic.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;
import com.rajendarreddyj.eclipse.plugins.weblogic.editors.ClasspathFieldEditor;

/**
 * This class represents Classpath preference page
 * 
 * @author rajendarreddyj
 *
 */
public class ClasspathPreferencePage extends FieldEditorPreferencePage implements WeblogicPluginResources, IWorkbenchPreferencePage {

    /**
     * 
     */
    public ClasspathPreferencePage() {
        super(GRID);
        this.setPreferenceStore(WeblogicPlugin.getDefault().getPreferenceStore());
        this.setDescription(CLASSPATH_DESCRIPTION_LABEL);
        this.initializeDefaults();
    }

    /**
     * This method will Initialize Default values(if any)
     */
    private void initializeDefaults() {
        // Initialize values if needed
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(final IWorkbench arg0) {
        // Nothing to implement at this moment
    }

    /**
     * Creates the Classpath Preferece page field editors.
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
     */
    @Override
    protected void createFieldEditors() {
        this.addField(new ClasspathFieldEditor(PREF_PRE_CLASSPATH, CLASSPATH_BEFORE_LABEL, this.getFieldEditorParent()));
        this.addField(new ClasspathFieldEditor(PREF_POST_CLASSPATH, CLASSPATH_AFTER_LABEL, this.getFieldEditorParent()));
    }

}
