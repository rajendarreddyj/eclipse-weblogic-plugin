package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.events.DisposeEvent;
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

/**
 * Abstract Class Represents List Field Editor Which is Used for ClassPath/Project and other fields
 * 
 * @author rajendarreddyj
 *
 */
public abstract class ListFieldEditor extends ListEditor implements WeblogicPluginResources {
    protected List list;
    protected Composite buttonBox;
    protected Button addButton;
    protected Button removeButton;
    protected Button upButton;
    protected Button downButton;
    protected SelectionListener selectionListener;

    /**
     * @param name
     * @param labelText
     * @param parent
     */
    public ListFieldEditor(final String name, final String labelText, final Composite parent) {
        this.init(name, labelText);
        this.createControl(parent);
    }

    /**
     * 
     */
    protected void addPressed() {
        this.setPresentsDefaultValue(false);
        final String input = this.getNewInputObject();
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

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#adjustForNumColumns(int)
     */
    @Override
    protected void adjustForNumColumns(final int numColumns) {
        final Control control = this.getLabelControl();
        ((GridData) control.getLayoutData()).horizontalSpan = numColumns;
        ((GridData) this.list.getLayoutData()).horizontalSpan = numColumns - 1;
    }

    /**
     * @param buttonBox
     */
    protected void createButtons(final Composite buttonBox) {
        this.addButton = this.createPushButton(buttonBox, BUTTON_ADD_LABEL);
        this.removeButton = this.createPushButton(buttonBox, BUTTON_REMOVE_LABEL);
        this.upButton = this.createPushButton(buttonBox, BUTTON_UP_LABEL);
        this.downButton = this.createPushButton(buttonBox, BUTTON_DOWN_LABEL);
    }

    /**
     * @param parent
     * @param label
     * @return
     */
    protected Button createPushButton(final Composite parent, final String label) {
        final Button button = new Button(parent, 8);
        button.setText(label);
        final GridData data = new GridData(768);
        data.heightHint = this.convertVerticalDLUsToPixels(button, 14);
        final int widthHint = this.convertHorizontalDLUsToPixels(button, 61);
        data.widthHint = Math.max(widthHint, button.computeSize(-1, -1, true).x);
        button.setLayoutData(data);
        button.addSelectionListener(this.getSelectionListener());
        return button;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#createSelectionListener()
     */
    @Override
    public void createSelectionListener() {
        this.selectionListener = new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                final Widget widget = event.widget;
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.preference.ListEditor#doFillIntoGrid(org.eclipse.swt.
     * widgets.Composite, int)
     */
    @Override
    protected void doFillIntoGrid(final Composite parent, final int numColumns) {
        final Control control = this.getLabelControl(parent);
        GridData gd = new GridData();
        gd.horizontalSpan = numColumns;
        control.setLayoutData(gd);

        this.list = this.getListControl(parent);
        gd = new GridData(768);
        gd.verticalAlignment = 4;
        gd.horizontalSpan = numColumns - 1;
        gd.horizontalAlignment = 4;
        gd.grabExcessHorizontalSpace = true;
        this.list.setLayoutData(gd);

        this.buttonBox = this.getButtonBoxControl(parent);
        gd = new GridData();
        gd.verticalAlignment = 1;
        this.buttonBox.setLayoutData(gd);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#doLoad()
     */
    @Override
    protected void doLoad() {
        if (this.list != null) {
            final String s = this.getPreferenceStore().getString(this.getPreferenceName());
            final String[] array = this.parseString(s);
            for (final String element : array) {
                this.list.add(element);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#doLoadDefault()
     */
    @Override
    protected void doLoadDefault() {
        if (this.list != null) {
            this.list.removeAll();
            final String s = this.getPreferenceStore().getDefaultString(this.getPreferenceName());
            final String[] array = this.parseString(s);
            for (final String element : array) {
                this.list.add(element);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#doStore()
     */
    @Override
    protected void doStore() {
        final String s = this.createList(this.list.getItems());
        if (s != null) {
            this.getPreferenceStore().setValue(this.getPreferenceName(), s);
        }
    }

    /**
     * 
     */
    protected void downPressed() {
        this.swap(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.preference.ListEditor#getButtonBoxControl(org.eclipse.
     * swt.widgets.Composite)
     */
    @Override
    public Composite getButtonBoxControl(final Composite parent) {
        if (this.buttonBox == null) {
            this.buttonBox = new Composite(parent, 0);
            final GridLayout layout = new GridLayout();
            layout.numColumns = 1;
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            layout.verticalSpacing = 0;
            this.buttonBox.setLayout(layout);
            this.createButtons(this.buttonBox);
            this.buttonBox.addDisposeListener((DisposeEvent event) -> {
                ListFieldEditor.this.addButton = null;
                ListFieldEditor.this.removeButton = null;
                ListFieldEditor.this.upButton = null;
                ListFieldEditor.this.downButton = null;
                ListFieldEditor.this.buttonBox = null;
            });
        } else {
            this.checkParent(this.buttonBox, parent);
        }
        this.selectionChanged();
        return this.buttonBox;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.preference.ListEditor#getListControl(org.eclipse.swt.
     * widgets.Composite)
     */
    @Override
    public List getListControl(final Composite parent) {
        if (this.list == null) {
            this.list = new List(parent, 2820);

            this.list.addSelectionListener(this.getSelectionListener());
            this.list.addDisposeListener((DisposeEvent event) -> ListFieldEditor.this.list = null);
        } else {
            this.checkParent(this.list, parent);
        }
        return this.list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#getNumberOfControls()
     */
    @Override
    public int getNumberOfControls() {
        return 2;
    }

    /**
     * @return
     */
    protected SelectionListener getSelectionListener() {
        if (this.selectionListener == null) {
            this.createSelectionListener();
        }
        return this.selectionListener;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#getShell()
     */
    @Override
    protected Shell getShell() {
        if (this.addButton == null) {
            return null;
        }
        return this.addButton.getShell();
    }

    /**
     * 
     */
    protected void removePressed() {
        this.setPresentsDefaultValue(false);
        final int index = this.list.getSelectionIndex();
        if (index >= 0) {
            this.list.remove(index);
            this.selectionChanged();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#selectionChanged()
     */
    @Override
    protected void selectionChanged() {
        final int index = this.list.getSelectionIndex();
        final int size = this.list.getItemCount();

        this.removeButton.setEnabled(index >= 0);
        this.upButton.setEnabled((size > 1) && (index > 0));
        this.downButton.setEnabled((size > 1) && (index >= 0) && (index < (size - 1)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.ListEditor#setFocus()
     */
    @Override
    public void setFocus() {
        if (this.list != null) {
            this.list.setFocus();
        }
    }

    /**
     * @param up
     */
    protected void swap(final boolean up) {
        this.setPresentsDefaultValue(false);
        final int index = this.list.getSelectionIndex();
        final int target = up ? index - 1 : index + 1;
        if (index >= 0) {
            final String[] selection = this.list.getSelection();
            Assert.isTrue(selection.length == 1);
            this.list.remove(index);
            this.list.add(selection[0], target);
            this.list.setSelection(target);
        }
        this.selectionChanged();
    }

    /**
     * 
     */
    protected void upPressed() {
        this.swap(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.preference.ListEditor#createList(java.lang.String[])
     */
    @Override
    protected String createList(final String[] items) {
        final StringBuilder path = new StringBuilder(STRING_EMPTY);
        for (final String item : items) {
            path.append(item);
            path.append(PATH_SEPARATOR);
        }
        return path.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.preference.ListEditor#parseString(java.lang.String)
     */
    @Override
    protected String[] parseString(final String stringList) {
        final StringTokenizer st = new StringTokenizer(stringList, PATH_SEPARATOR);
        final java.util.List<String> v = new ArrayList<>();
        while (st.hasMoreElements()) {
            v.add((String) st.nextElement());
        }
        return v.toArray(new String[v.size()]);
    }
}