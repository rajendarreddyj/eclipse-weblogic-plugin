package com.rajendarreddyj.eclipse.plugins.weblogic.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;
import com.rajendarreddyj.eclipse.plugins.weblogic.editors.ProjectFieldEditor;

/**
 * This class represents Project preference page
 * 
 * @author rajendarreddyj
 *
 */
public class ProjectPreferencePage extends FieldEditorPreferencePage implements WeblogicPluginResources, IWorkbenchPreferencePage {

    /**
     * 
     */
    public ProjectPreferencePage() {
        super(GRID);
        this.setPreferenceStore(WeblogicPlugin.getDefault().getPreferenceStore());
        this.setDescription(PROJECT_DESCRIPTION_LABEL);
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
     * Creates the Project Preferece page field editors.
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
     */
    @Override
    protected void createFieldEditors() {
        this.addField(new ProjectFieldEditor(PREF_PROJECTS, PROJECT_LIST_LABEL, this.getFieldEditorParent()));
    }

}
