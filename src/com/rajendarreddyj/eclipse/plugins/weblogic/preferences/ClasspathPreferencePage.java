package com.rajendarreddyj.eclipse.plugins.weblogic.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;
import com.rajendarreddyj.eclipse.plugins.weblogic.editors.ClasspathFieldEditor;

public class ClasspathPreferencePage extends FieldEditorPreferencePage implements WeblogicPluginResources, IWorkbenchPreferencePage {

    public ClasspathPreferencePage() {
        super(GRID);
        setPreferenceStore(WeblogicPlugin.getDefault().getPreferenceStore());
        setDescription(WeblogicPluginResources.CLASSPATH_DESCRIPTION_LABEL);
        initializeDefaults();
    }

    private void initializeDefaults() {
    }

    @Override
    public void init(IWorkbench arg0) {
    }

    @Override
    protected void createFieldEditors() {
        addField(new ClasspathFieldEditor("preclasspath", WeblogicPluginResources.CLASSPATH_BEFORE_LABEL, getFieldEditorParent()));
        addField(new ClasspathFieldEditor("postclasspath", WeblogicPluginResources.CLASSPATH_AFTER_LABEL, getFieldEditorParent()));
    }

}
