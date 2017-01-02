package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import java.io.File;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Widget;

/**
 * This Class represents Classpath Field Edition
 * 
 * @author rajendarreddyj
 *
 */
public class ClasspathFieldEditor extends ListFieldEditor {
    protected Button addJarZipButton;
    protected Button addDirButton;
    private String lastPath;

    /**
     * @param name
     * @param labelText
     * @param parent
     */
    public ClasspathFieldEditor(final String name, final String labelText, final Composite parent) {
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
    protected String getNewJarZip() {
        final FileDialog dialog = new FileDialog(this.addJarZipButton.getShell());
        if ((this.lastPath != null) && new File(this.lastPath).exists()) {
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
        this.addJarZipButton = this.createPushButton(buttonBox, BUTTON_ADDJARZIP_LABEL);
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

    /**
     * 
     */
    protected void addJarZipPressed() {
        this.setPresentsDefaultValue(false);
        final String input = this.getNewJarZip();
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
