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
		final DirectoryDialog dialog = new DirectoryDialog(addDirButton.getShell());
		if (lastPath != null && new File(lastPath).exists()) {
			dialog.setFilterPath(lastPath);
		}
		String dir = dialog.open();
		if (dir != null) {
			dir = dir.trim();
			if (dir.length() == 0) {
				return null;
			}
			lastPath = dir;
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
		addDirButton = createPushButton(buttonBox, BUTTON_ADDDIR_LABEL);
		removeButton = createPushButton(buttonBox, BUTTON_REMOVE_LABEL);
		upButton = createPushButton(buttonBox, BUTTON_UP_LABEL);
		downButton = createPushButton(buttonBox, BUTTON_DOWN_LABEL);
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
				if (widget == addDirButton) {
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
		setPresentsDefaultValue(false);
		final String input = getNewDir();
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
}