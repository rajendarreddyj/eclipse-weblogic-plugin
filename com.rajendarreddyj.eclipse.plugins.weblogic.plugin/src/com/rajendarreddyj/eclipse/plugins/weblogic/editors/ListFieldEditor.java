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

/**
 * Abstract Class Represents List Field Editor Which is Used for
 * ClassPath/Project and other fields
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
		init(name, labelText);
		createControl(parent);
	}

	/**
	 * 
	 */
	protected void addPressed() {
		setPresentsDefaultValue(false);
		final String input = getNewInputObject();
		if (input != null) {
			final int index = list.getSelectionIndex();
			if (index >= 0) {
				list.add(input, index + 1);
			} else {
				list.add(input, 0);
			}
			selectionChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ListEditor#adjustForNumColumns(int)
	 */
	@Override
	protected void adjustForNumColumns(final int numColumns) {
		final Control control = getLabelControl();
		((GridData) control.getLayoutData()).horizontalSpan = numColumns;
		((GridData) list.getLayoutData()).horizontalSpan = numColumns - 1;
	}

	/**
	 * @param buttonBox
	 */
	protected void createButtons(final Composite buttonBox) {
		addButton = createPushButton(buttonBox, BUTTON_ADD_LABEL);
		removeButton = createPushButton(buttonBox, BUTTON_REMOVE_LABEL);
		upButton = createPushButton(buttonBox, BUTTON_UP_LABEL);
		downButton = createPushButton(buttonBox, BUTTON_DOWN_LABEL);
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
		data.heightHint = convertVerticalDLUsToPixels(button, 14);
		final int widthHint = convertHorizontalDLUsToPixels(button, 61);
		data.widthHint = Math.max(widthHint, button.computeSize(-1, -1, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(getSelectionListener());
		return button;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ListEditor#createSelectionListener()
	 */
	@Override
	public void createSelectionListener() {
		selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				final Widget widget = event.widget;
				if (widget == addButton) {
					ListFieldEditor.this.addPressed();
				} else if (widget == removeButton) {
					ListFieldEditor.this.removePressed();
				} else if (widget == upButton) {
					ListFieldEditor.this.upPressed();
				} else if (widget == downButton) {
					ListFieldEditor.this.downPressed();
				} else if (widget == list) {
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
		final Control control = getLabelControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns;
		control.setLayoutData(gd);

		list = getListControl(parent);
		gd = new GridData(768);
		gd.verticalAlignment = 4;
		gd.horizontalSpan = numColumns - 1;
		gd.horizontalAlignment = 4;
		gd.grabExcessHorizontalSpace = true;
		list.setLayoutData(gd);

		buttonBox = getButtonBoxControl(parent);
		gd = new GridData();
		gd.verticalAlignment = 1;
		buttonBox.setLayoutData(gd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ListEditor#doLoad()
	 */
	@Override
	protected void doLoad() {
		if (list != null) {
			final String s = getPreferenceStore().getString(getPreferenceName());
			final String[] array = parseString(s);
			for (final String element : array) {
				list.add(element);
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
		if (list != null) {
			list.removeAll();
			final String s = getPreferenceStore().getDefaultString(getPreferenceName());
			final String[] array = parseString(s);
			for (final String element : array) {
				list.add(element);
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
		final String s = createList(list.getItems());
		if (s != null) {
			getPreferenceStore().setValue(getPreferenceName(), s);
		}
	}

	/**
	 * 
	 */
	protected void downPressed() {
		swap(false);
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
		if (buttonBox == null) {
			buttonBox = new Composite(parent, 0);
			final GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			layout.verticalSpacing = 0;
			buttonBox.setLayout(layout);
			createButtons(buttonBox);
			buttonBox.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(final DisposeEvent event) {
					addButton = null;
					removeButton = null;
					upButton = null;
					downButton = null;
					buttonBox = null;
				}
			});
		} else {
			checkParent(buttonBox, parent);
		}
		selectionChanged();
		return buttonBox;
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
		if (list == null) {
			list = new List(parent, 2820);

			list.addSelectionListener(getSelectionListener());
			list.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(final DisposeEvent event) {
					list = null;
				}
			});
		} else {
			checkParent(list, parent);
		}
		return list;
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
		if (selectionListener == null) {
			createSelectionListener();
		}
		return selectionListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ListEditor#getShell()
	 */
	@Override
	protected Shell getShell() {
		if (addButton == null) {
			return null;
		}
		return addButton.getShell();
	}

	/**
	 * 
	 */
	protected void removePressed() {
		setPresentsDefaultValue(false);
		final int index = list.getSelectionIndex();
		if (index >= 0) {
			list.remove(index);
			selectionChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ListEditor#selectionChanged()
	 */
	@Override
	protected void selectionChanged() {
		final int index = list.getSelectionIndex();
		final int size = list.getItemCount();

		removeButton.setEnabled(index >= 0);
		upButton.setEnabled(size > 1 && index > 0);
		downButton.setEnabled(size > 1 && index >= 0 && index < size - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ListEditor#setFocus()
	 */
	@Override
	public void setFocus() {
		if (list != null) {
			list.setFocus();
		}
	}

	/**
	 * @param up
	 */
	protected void swap(final boolean up) {
		setPresentsDefaultValue(false);
		final int index = list.getSelectionIndex();
		final int target = up ? index - 1 : index + 1;
		if (index >= 0) {
			final String[] selection = list.getSelection();
			Assert.isTrue(selection.length == 1);
			list.remove(index);
			list.add(selection[0], target);
			list.setSelection(target);
		}
		selectionChanged();
	}

	/**
	 * 
	 */
	protected void upPressed() {
		swap(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.ListEditor#createList(java.lang.String[])
	 */
	@Override
	protected String createList(final String[] items) {
		final StringBuffer path = new StringBuffer(STRING_EMPTY);
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