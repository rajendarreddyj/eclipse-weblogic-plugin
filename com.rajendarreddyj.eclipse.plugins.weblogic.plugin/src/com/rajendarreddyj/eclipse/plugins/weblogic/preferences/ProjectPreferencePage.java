package com.rajendarreddyj.eclipse.plugins.weblogic.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;
import com.rajendarreddyj.eclipse.plugins.weblogic.editors.ProjectFieldEditor;

public class ProjectPreferencePage extends FieldEditorPreferencePage implements WeblogicPluginResources, IWorkbenchPreferencePage {

    public ProjectPreferencePage()
    {
      super(GRID);
      setPreferenceStore(WeblogicPlugin.getDefault().getPreferenceStore());
      setDescription(WeblogicPluginResources.PROJECT_DESCRIPTION_LABEL);
      initializeDefaults();
    }
    
    private void initializeDefaults() {}
    
    @Override
    public void init(IWorkbench arg0) {}

    @Override
    protected void createFieldEditors() {
        addField(new ProjectFieldEditor("projects", WeblogicPluginResources.PROJECT_LIST_LABEL, getFieldEditorParent()));
    }

}
