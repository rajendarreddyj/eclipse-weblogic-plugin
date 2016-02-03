package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import java.io.File;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Widget;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

public class PathFieldEditor extends ListFieldEditor {
    protected Button addDirButton;
    private String lastPath;

    public PathFieldEditor(String name, String labelText, Composite parent) {
        super(name, labelText, parent);
    }

    protected String getNewInputObject() {
        return null;
    }

    protected String getNewDir() {
        DirectoryDialog dialog = new DirectoryDialog(this.addDirButton.getShell());
        if ((this.lastPath != null) && (new File(this.lastPath).exists())) {
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

    protected void createButtons(Composite buttonBox) {
        this.addDirButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_ADDDIR_LABEL);
        this.removeButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_REMOVE_LABEL);
        this.upButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_UP_LABEL);
        this.downButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_DOWN_LABEL);
    }

    public void createSelectionListener() {
        this.selectionListener = new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Widget widget = event.widget;
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

    protected void addDirPressed() {
        setPresentsDefaultValue(false);
        String input = getNewDir();
        if (input != null) {
            int index = this.list.getSelectionIndex();
            if (index >= 0) {
                this.list.add(input, index + 1);
            } else {
                this.list.add(input, 0);
            }
            selectionChanged();
        }
    }
}