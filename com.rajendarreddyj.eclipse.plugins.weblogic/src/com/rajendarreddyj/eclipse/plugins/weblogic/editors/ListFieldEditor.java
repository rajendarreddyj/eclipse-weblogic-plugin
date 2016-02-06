package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;

public abstract class ListFieldEditor extends ListEditor implements WeblogicPluginResources {
    protected List list;
    protected Composite buttonBox;
    protected Button addButton;
    protected Button removeButton;
    protected Button upButton;
    protected Button downButton;
    protected SelectionListener selectionListener;  

    public ListFieldEditor(String name, String labelText, Composite parent) {
        init(name, labelText);
        createControl(parent);
    }

    protected void addPressed() {
        setPresentsDefaultValue(false);
        String input = getNewInputObject();
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

    protected void adjustForNumColumns(int numColumns) {
        Control control = getLabelControl();
        ((GridData) control.getLayoutData()).horizontalSpan = numColumns;
        ((GridData) this.list.getLayoutData()).horizontalSpan = (numColumns - 1);
    }

    protected void createButtons(Composite buttonBox) {
        this.addButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_ADD_LABEL);
        this.removeButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_REMOVE_LABEL);
        this.upButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_UP_LABEL);
        this.downButton = createPushButton(buttonBox, WeblogicPluginResources.BUTTON_DOWN_LABEL);
    }

    protected Button createPushButton(Composite parent, String label) {
        Button button = new Button(parent, 8);
        button.setText(label);
        GridData data = new GridData(768);
        data.heightHint = convertVerticalDLUsToPixels(button, 14);
        int widthHint = convertHorizontalDLUsToPixels(button, 61);
        data.widthHint = Math.max(widthHint, button.computeSize(-1, -1, true).x);
        button.setLayoutData(data);
        button.addSelectionListener(getSelectionListener());
        return button;
    }

    public void createSelectionListener() {
        this.selectionListener = new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Widget widget = event.widget;
                if (widget == ListFieldEditor.this.addButton) {
                    ListFieldEditor.this.addPressed();
                } else if (widget == ListFieldEditor.this.removeButton) {
                    ListFieldEditor.this.removePressed();
                } else if (widget == ListFieldEditor.this.upButton) {
                    ListFieldEditor.this.upPressed();
                } else if (widget == ListFieldEditor.this.downButton) {
                    ListFieldEditor.this.downPressed();
                } else if (widget == ListFieldEditor.this.list) {
                    ListFieldEditor.this.selectionChanged();
                }
            }
        };
    }

    protected void doFillIntoGrid(Composite parent, int numColumns) {
        Control control = getLabelControl(parent);
        GridData gd = new GridData();
        gd.horizontalSpan = numColumns;
        control.setLayoutData(gd);

        this.list = getListControl(parent);
        gd = new GridData(768);
        gd.verticalAlignment = 4;
        gd.horizontalSpan = (numColumns - 1);
        gd.horizontalAlignment = 4;
        gd.grabExcessHorizontalSpace = true;
        this.list.setLayoutData(gd);

        this.buttonBox = getButtonBoxControl(parent);
        gd = new GridData();
        gd.verticalAlignment = 1;
        this.buttonBox.setLayoutData(gd);
    }

    protected void doLoad() {
        if (this.list != null) {
            String s = getPreferenceStore().getString(getPreferenceName());
            String[] array = parseString(s);
            for (int i = 0; i < array.length; i++) {
                this.list.add(array[i]);
            }
        }
    }

    protected void doLoadDefault() {
        if (this.list != null) {
            this.list.removeAll();
            String s = getPreferenceStore().getDefaultString(getPreferenceName());
            String[] array = parseString(s);
            for (int i = 0; i < array.length; i++) {
                this.list.add(array[i]);
            }
        }
    }

    protected void doStore() {
        String s = createList(this.list.getItems());
        if (s != null) {
            getPreferenceStore().setValue(getPreferenceName(), s);
        }
    }

    protected void downPressed() {
        swap(false);
    }

    public Composite getButtonBoxControl(Composite parent) {
        if (this.buttonBox == null) {
            this.buttonBox = new Composite(parent, 0);
            GridLayout layout = new GridLayout();
            layout.numColumns = 1;
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            layout.verticalSpacing = 0;
            this.buttonBox.setLayout(layout);
            createButtons(this.buttonBox);
            this.buttonBox.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent event) {
                    ListFieldEditor.this.addButton = null;
                    ListFieldEditor.this.removeButton = null;
                    ListFieldEditor.this.upButton = null;
                    ListFieldEditor.this.downButton = null;
                    ListFieldEditor.this.buttonBox = null;
                }
            });
        } else {
            checkParent(this.buttonBox, parent);
        }
        selectionChanged();
        return this.buttonBox;
    }

    public List getListControl(Composite parent) {
        if (this.list == null) {
            this.list = new List(parent, 2820);

            this.list.addSelectionListener(getSelectionListener());
            this.list.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent event) {
                    ListFieldEditor.this.list = null;
                }
            });
        } else {
            checkParent(this.list, parent);
        }
        return this.list;
    }

    public int getNumberOfControls() {
        return 2;
    }

    protected SelectionListener getSelectionListener() {
        if (this.selectionListener == null) {
            createSelectionListener();
        }
        return this.selectionListener;
    }

    protected Shell getShell() {
        if (this.addButton == null) {
            return null;
        }
        return this.addButton.getShell();
    }

    protected void removePressed() {
        setPresentsDefaultValue(false);
        int index = this.list.getSelectionIndex();
        if (index >= 0) {
            this.list.remove(index);
            selectionChanged();
        }
    }

    protected void selectionChanged() {
        int index = this.list.getSelectionIndex();
        int size = this.list.getItemCount();

        this.removeButton.setEnabled(index >= 0);
        this.upButton.setEnabled((size > 1) && (index > 0));
        this.downButton.setEnabled((size > 1) && (index >= 0) && (index < size - 1));
    }

    public void setFocus() {
        if (this.list != null) {
            this.list.setFocus();
        }
    }

    protected void swap(boolean up) {
        setPresentsDefaultValue(false);
        int index = this.list.getSelectionIndex();
        int target = up ? index - 1 : index + 1;
        if (index >= 0) {
            String[] selection = this.list.getSelection();
            Assert.isTrue(selection.length == 1);
            this.list.remove(index);
            this.list.add(selection[0], target);
            this.list.setSelection(target);
        }
        selectionChanged();
    }

    protected void upPressed() {
        swap(true);
    }

    protected String createList(String[] items) {
        StringBuffer path = new StringBuffer("");
        for (int i = 0; i < items.length; i++) {
            path.append(items[i]);
            path.append(";");
        }
        return path.toString();
    }

    protected String[] parseString(String stringList) {
        StringTokenizer st = new StringTokenizer(stringList, ";");
        java.util.List<String> v = new ArrayList<>();
        while (st.hasMoreElements()) {
            v.add((String) st.nextElement());
        }
        return (String[]) v.toArray(new String[v.size()]);
    }
}