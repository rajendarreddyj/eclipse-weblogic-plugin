package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

/**
 * This Class Represents JVM Option Editor
 * 
 * @author rajendarreddyj
 *
 */
public class JVMOptionEditor extends ListFieldEditor {
    protected Button modifyButton;

    /**
     * @param name
     * @param labelText
     * @param parent
     */
    public JVMOptionEditor(final String name, final String labelText, final Composite parent) {
        super(name, labelText, parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rajendarreddyj.eclipse.plugins.weblogic.editors.ListFieldEditor#
     * createButtons(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createButtons(final Composite buttonBox) {
        this.addButton = this.createPushButton(buttonBox, BUTTON_ADD_LABEL);
        this.removeButton = this.createPushButton(buttonBox, BUTTON_REMOVE_LABEL);
        this.modifyButton = this.createPushButton(buttonBox, BUTTON_MODIFY_LABEL);
        this.upButton = this.createPushButton(buttonBox, BUTTON_UP_LABEL);
        this.downButton = this.createPushButton(buttonBox, BUTTON_DOWN_LABEL);
    }

    /**
     * 
     */
    protected void modifyPressed() {
        this.setPresentsDefaultValue(false);
        final int index = this.list.getSelectionIndex();
        if (index < 0) {
            return;
        }
        final String item = this.list.getItem(index);
        final String input = this.getInputObject(item);
        if (input != null) {
            this.list.setItem(index, input);
            this.selectionChanged();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rajendarreddyj.eclipse.plugins.weblogic.editors.ListFieldEditor#
     * createSelectionListener()
     */
    @Override
    public void createSelectionListener() {
        this.selectionListener = new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                final Widget widget = event.widget;
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

    /*
     * (non-Javadoc)
     * 
     * @see com.rajendarreddyj.eclipse.plugins.weblogic.editors.ListFieldEditor#
     * selectionChanged()
     */
    @Override
    protected void selectionChanged() {
        final int index = this.list.getSelectionIndex();
        this.modifyButton.setEnabled(index >= 0);
        super.selectionChanged();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#getNewInputObject()
     */
    @Override
    protected String getNewInputObject() {
        return this.getInputObject(STRING_EMPTY);
    }

    /**
     * @param current
     * @return
     */
    protected String getInputObject(final String current) {
        final InputDialog dialog = new InputDialog(this.getShell(), JVM_NEW_LABEL, JVM_ENTER_LABEL, current, null);
        String param = null;
        final int dialogCode = dialog.open();
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