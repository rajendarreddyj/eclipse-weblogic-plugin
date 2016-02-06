package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

public class JVMOptionEditor extends ListFieldEditor {
    protected Button modifyButton;

    public JVMOptionEditor(String name, String labelText, Composite parent) {
        super(name, labelText, parent);
    }

    protected void createButtons(Composite buttonBox) {
        this.addButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_ADD_LABEL);
        this.removeButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_REMOVE_LABEL);
        this.modifyButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_MODIFY_LABEL);
        this.upButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_UP_LABEL);
        this.downButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_DOWN_LABEL);
    }

    protected void modifyPressed() {
        setPresentsDefaultValue(false);
        int index = this.list.getSelectionIndex();
        if (index < 0) {
            return;
        }
        String item = this.list.getItem(index);
        String input = getInputObject(item);
        if (input != null) {
            this.list.setItem(index, input);
            selectionChanged();
        }
    }

    public void createSelectionListener() {
        this.selectionListener = new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Widget widget = event.widget;
                if (widget == JVMOptionEditor.this.addButton) {
                    JVMOptionEditor.this.addPressed();
                } else if (widget == JVMOptionEditor.this.removeButton) {
                    JVMOptionEditor.this.removePressed();
                } else if (widget == JVMOptionEditor.this.upButton) {
                    JVMOptionEditor.this.upPressed();
                } else if (widget == JVMOptionEditor.this.downButton) {
                    JVMOptionEditor.this.downPressed();
                } else if (widget == JVMOptionEditor.this.modifyButton) {
                    JVMOptionEditor.this.modifyPressed();
                } else if (widget == JVMOptionEditor.this.list) {
                    JVMOptionEditor.this.selectionChanged();
                }
            }
        };
    }

    protected void selectionChanged() {
        int index = this.list.getSelectionIndex();

        this.modifyButton.setEnabled(index >= 0);

        super.selectionChanged();
    }

    protected String getNewInputObject() {
        return getInputObject("");
    }

    protected String getInputObject(String current) {
        InputDialog dialog = new InputDialog(getShell(), WeblogicPluginResources.JVM_NEW_LABEL, WeblogicPluginResources.JVM_ENTER_LABEL, current, null);
        String param = null;
        int dialogCode = dialog.open();
        if (dialogCode == 0) {
            param = dialog.getValue();
            if (param != null) {
                param = param.trim();
                if (param.length() == 0) {
                    return null;
                }
            }
        }
        return param;
    }
}