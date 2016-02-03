package com.rajendarreddyj.eclipse.plugins.weblogic.preferences;

import java.io.File;
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

public class JVMOptionsPreferencePage extends FieldEditorPreferencePage implements WeblogicPluginResources, IWorkbenchPreferencePage {
    private String[][] jvmNamesAndValues;

    public JVMOptionsPreferencePage() {
        super(GRID);
        setPreferenceStore(WeblogicPlugin.getDefault().getPreferenceStore());
        setDescription(WeblogicPluginResources.JVM_DESCRIPTION_LABEL);
        initializeDefaults();
    }

    private void initializeDefaults() {
        IPreferenceStore store = getPreferenceStore();
        store.setDefault("jreid", JavaRuntime.getDefaultVMInstall().getId());
        String defaultOptions = "-Xms256m;-Xmx512m;-Dweblogic.ProductionModeEnabled=false;";
        if (File.separator.equals("\\")) {
            defaultOptions = defaultOptions
                    + "-Dweblogic.security.SSL.trustedCAKeyStore=C:\\bea\\weblogic700\\server\\lib\\cacerts;-Djava.security.policy=C:\\bea\\weblogic700\\server\\lib\\weblogic.policy;";
        } else {
            defaultOptions = defaultOptions
                    + "-Dweblogic.security.SSL.trustedCAKeyStore=/opt/bea/weblogic700/server/lib/cacerts;-Djava.security.policy=/opt/bea/weblogic700/server/lib/weblogic.policy;";
        }
        store.setDefault("jvmoptions", defaultOptions);
    }

    @Override
    public void init(IWorkbench arg0) {
    }

    private void setAllVMs() {
        List<IVMInstall> allVMs = new ArrayList<>();
        IVMInstallType[] vmTypes = JavaRuntime.getVMInstallTypes();
        for (int i = 0; i < vmTypes.length; i++) {
            IVMInstall[] vms = vmTypes[i].getVMInstalls();
            for (int j = 0; j < vms.length; j++) {
                allVMs.add(vms[j]);
            }
        }
        this.jvmNamesAndValues = new String[allVMs.size()][2];
        for (int i = 0; i < allVMs.size(); i++) {
            this.jvmNamesAndValues[i][0] = ((IVMInstall) allVMs.get(i)).getName();
            this.jvmNamesAndValues[i][1] = ((IVMInstall) allVMs.get(i)).getId();
        }
    }

    public void createFieldEditors() {
        setAllVMs();
        ComboFieldEditor jvmChoice = new ComboFieldEditor("jreid", WeblogicPluginResources.JVM_JAVAVM_LABEL, this.jvmNamesAndValues, getFieldEditorParent());
        addField(jvmChoice);
        addField(new JVMOptionEditor("jvmoptions", WeblogicPluginResources.JVM_OPTIONS_LABEL, getFieldEditorParent()));
        addField(new PathFieldEditor("libpath", WeblogicPluginResources.JVM_LIBRARYPATH_LABEL, getFieldEditorParent()));
    }

}
