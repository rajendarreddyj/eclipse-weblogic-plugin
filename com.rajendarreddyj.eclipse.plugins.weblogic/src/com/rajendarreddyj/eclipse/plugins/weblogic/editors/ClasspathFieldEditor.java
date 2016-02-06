package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import java.io.File;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Widget;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

public class ClasspathFieldEditor extends ListFieldEditor {
    protected Button addJarZipButton;
    protected Button addDirButton;
    private String lastPath;

    public ClasspathFieldEditor(String name, String labelText, Composite parent) {
        super(name, labelText, parent);
    }

    protected String getNewInputObject() {
        return null;
    }

    protected String getNewJarZip() {
        FileDialog dialog = new FileDialog(this.addJarZipButton.getShell());
        if ((this.lastPath != null) && (new File(this.lastPath).exists())) {
            dialog.setFilterPath(this.lastPath);
        }
        String file = dialog.open();
        if (file != null) {
            file = file.trim();
            if (file.length() == 0) {
                return null;
            }
            this.lastPath = new File(file).getAbsolutePath();
        }
        return file;
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
        this.addJarZipButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_ADDJARZIP_LABEL);
        this.addDirButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_ADDDIR_LABEL);
        this.removeButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_REMOVE_LABEL);
        this.upButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_UP_LABEL);
        this.downButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_DOWN_LABEL);
    }

    public void createSelectionListener() {
        this.selectionListener = new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Widget widget = event.widget;
                if (widget == ClasspathFieldEditor.this.addJarZipButton) {
                    ClasspathFieldEditor.this.addJarZipPressed();
                } else if (widget == ClasspathFieldEditor.this.addDirButton) {
                    ClasspathFieldEditor.this.addDirPressed();
                } else if (widget == ClasspathFieldEditor.this.removeButton) {
                    ClasspathFieldEditor.this.removePressed();
                } else if (widget == ClasspathFieldEditor.this.upButton) {
                    ClasspathFieldEditor.this.upPressed();
                } else if (widget == ClasspathFieldEditor.this.downButton) {
                    ClasspathFieldEditor.this.downPressed();
                } else if (widget == ClasspathFieldEditor.this.list) {
                    ClasspathFieldEditor.this.selectionChanged();
                }
            }
        };
    }

    protected void addJarZipPressed() {
        setPresentsDefaultValue(false);
        String input = getNewJarZip();
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
