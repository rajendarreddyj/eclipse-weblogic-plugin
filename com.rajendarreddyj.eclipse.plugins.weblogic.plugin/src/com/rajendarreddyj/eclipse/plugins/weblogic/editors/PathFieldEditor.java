package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import java.io.File;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Widget;

/**
 * This Class Represents Path Field Editor
 * 
 * @author rajendarreddyj
 *
 */
public class PathFieldEditor extends ListFieldEditor {
    protected Button addDirButton;
    private String lastPath;

    /**
     * @param name
     * @param labelText
     * @param parent
     */
    public PathFieldEditor(final String name, final String labelText, final Composite parent) {
        super(name, labelText, parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#getNewInputObject()
     */
    @Override
    protected String getNewInputObject() {
        return null;
    }

    /**
     * @return
     */
    protected String getNewDir() {
        final DirectoryDialog dialog = new DirectoryDialog(this.addDirButton.getShell());
        if ((this.lastPath != null) && new File(this.lastPath).exists()) {
            dialog.setFilterPath(this.lastPath);
        }
        String dir = dialog.open();
        if (dir != null) {
            dir = dir.trim();
            if (dir.length() == 0) {
                return null;
            }
            this.lastPath = dir;
        }
        return dir;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rajendarreddyj.eclipse.plugins.weblogic.editors.ListFieldEditor#
     * createButtons(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createButtons(final Composite buttonBox) {
        this.addDirButton = this.createPushButton(buttonBox, BUTTON_ADDDIR_LABEL);
        this.removeButton = this.createPushButton(buttonBox, BUTTON_REMOVE_LABEL);
        this.upButton = this.createPushButton(buttonBox, BUTTON_UP_LABEL);
        this.downButton = this.createPushButton(buttonBox, BUTTON_DOWN_LABEL);
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
                if (widget == PathFieldEditor.this.addDirButton) {
                    PathFieldEditor.this.addDirPressed();
                } else if (widget == PathFieldEditor.this.removeButton) {
                    PathFieldEditor.this.removePressed();
                } else if (widget == PathFieldEditor.this.upButton) {
                    PathFieldEditor.this.upPressed();
                } else if (widget == PathFieldEditor.this.downButton) {
                    PathFieldEditor.this.downPressed();
                } else if (widget == PathFieldEditor.this.list) {
                    PathFieldEditor.this.selectionChanged();
                }
            }
        };
    }

    /**
     * 
     */
    protected void addDirPressed() {
        this.setPresentsDefaultValue(false);
        final String input = this.getNewDir();
        if (input != null) {
            final int index = this.list.getSelectionIndex();
            if (index >= 0) {
                this.list.add(input, index + 1);
            } else {
                this.list.add(input, 0);
            }
            this.selectionChanged();
        }
    }
}