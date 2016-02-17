package com.rajendarreddyj.eclipse.plugins.weblogic.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPlugin;
import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;
import com.rajendarreddyj.eclipse.plugins.weblogic.editors.JVMOptionEditor;
import com.rajendarreddyj.eclipse.plugins.weblogic.editors.PathFieldEditor;

/**
 * This class represents JVM Options preference page
 * 
 * @author rajendarreddyj
 *
 */
public class JVMOptionsPreferencePage extends FieldEditorPreferencePage
		implements WeblogicPluginResources, IWorkbenchPreferencePage {
	private String[][] jvmNamesAndValues;

	/**
	 * 
	 */
	public JVMOptionsPreferencePage() {
		super(GRID);
		setPreferenceStore(WeblogicPlugin.getDefault().getPreferenceStore());
		setDescription(JVM_DESCRIPTION_LABEL);
		initializeDefaults();
	}

	/**
	 * This method will Initialize Default values(if any)
	 */
	private void initializeDefaults() {
		final IPreferenceStore store = getPreferenceStore();
		store.setDefault(PREF_JRE, JavaRuntime.getDefaultVMInstall().getId());
		final String defaultOptions = DEFAULT_JVM_OPTIONS;
		store.setDefault(PREF_JVM_OPTIONS, defaultOptions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(final IWorkbench arg0) {
	}

	/**
	 * This method will fetch and set All Available JVMs in Dropdown
	 */
	private void setAllVMs() {
		final List<IVMInstall> allVMs = new ArrayList<>();
		final IVMInstallType[] vmTypes = JavaRuntime.getVMInstallTypes();
		for (final IVMInstallType vmType : vmTypes) {
			final IVMInstall[] vms = vmType.getVMInstalls();
			for (final IVMInstall vm : vms) {
				allVMs.add(vm);
			}
		}
		jvmNamesAndValues = new String[allVMs.size()][2];
		for (int i = 0; i < allVMs.size(); i++) {
			jvmNamesAndValues[i][0] = allVMs.get(i).getName();
			jvmNamesAndValues[i][1] = allVMs.get(i).getId();
		}
	}

	/**
	 * Creates the JVM Options Preferece page field editors.
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	public void createFieldEditors() {
		setAllVMs();
		addField(new ComboFieldEditor(PREF_JRE, JVM_JAVAVM_LABEL, jvmNamesAndValues, getFieldEditorParent()));
		addField(new JVMOptionEditor(PREF_JVM_OPTIONS, JVM_OPTIONS_LABEL, getFieldEditorParent()));
		addField(new PathFieldEditor(PREF_LIBPATH, JVM_LIBRARYPATH_LABEL, getFieldEditorParent()));
	}

}
