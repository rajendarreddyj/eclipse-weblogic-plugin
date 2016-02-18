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
		addButton = createPushButton(buttonBox, BUTTON_ADD_LABEL);
		removeButton = createPushButton(buttonBox, BUTTON_REMOVE_LABEL);
		modifyButton = createPushButton(buttonBox, BUTTON_MODIFY_LABEL);
		upButton = createPushButton(buttonBox, BUTTON_UP_LABEL);
		downButton = createPushButton(buttonBox, BUTTON_DOWN_LABEL);
	}

	/**
	 * 
	 */
	protected void modifyPressed() {
		setPresentsDefaultValue(false);
		final int index = list.getSelectionIndex();
		if (index < 0) {
			return;
		}
		final String item = list.getItem(index);
		final String input = getInputObject(item);
		if (input != null) {
			list.setItem(index, input);
			selectionChanged();
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
		selectionListener = new SelectionAdapter() {
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
				} else if (widget == modifyButton) {
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
		final int index = list.getSelectionIndex();
		modifyButton.setEnabled(index >= 0);
		super.selectionChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ListEditor#getNewInputObject()
	 */
	@Override
	protected String getNewInputObject() {
		return getInputObject(STRING_EMPTY);
	}

	/**
	 * @param current
	 * @return
	 */
	protected String getInputObject(final String current) {
		final InputDialog dialog = new InputDialog(getShell(), JVM_NEW_LABEL, JVM_ENTER_LABEL, current, null);
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