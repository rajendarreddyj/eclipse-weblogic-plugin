package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import com.rajendarreddyj.eclipse.plugins.weblogic.dialogs.SimpleListContentProvider;

/**
 * This Class Represents Project Field Editor
 * 
 * @author rajendarreddyj
 *
 */
public class ProjectFieldEditor extends ListFieldEditor {

	/**
	 * @param name
	 * @param labelText
	 * @param parent
	 */
	public ProjectFieldEditor(final String name, final String labelText, final Composite parent) {
		super(name, labelText, parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.ListEditor#getNewInputObject()
	 */
	@Override
	protected String getNewInputObject() {
		final String[] currentItems = list.getItems();
		final IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		final ILabelProvider labelProvider = new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return (String) element;
			}
		};
		final SimpleListContentProvider contentsProvider = new SimpleListContentProvider();
		contentsProvider.setElements(sortedDifference(allProjects, currentItems));

		final ListSelectionDialog dialog = new ListSelectionDialog(getShell(), this, contentsProvider, labelProvider,
				PROJECT_SELECTION_LABEL);
		if (dialog.open() != 0) {
			return null;
		}
		final Object[] result = dialog.getResult();

		final int currentItemsLength = currentItems.length;
		final int resultLength = result.length;
		final String[] newItems = new String[currentItemsLength + resultLength];

		System.arraycopy(currentItems, 0, newItems, 0, currentItemsLength);
		System.arraycopy(result, 0, newItems, currentItemsLength, result.length);

		list.setItems(newItems);
		return null;
	}

	/**
	 * @param allProjects
	 * @param currentlyDisplayed
	 * @return
	 */
	private String[] sortedDifference(final IProject[] allProjects, final String[] currentlyDisplayed) {
		final TreeSet<String> current = new TreeSet<>();
		for (final String element : currentlyDisplayed) {
			current.add(element);
		}
		final TreeSet<String> difference = new TreeSet<>();
		for (int i = 0; i < allProjects.length; i++) {
			if (!current.contains(allProjects[i].getName())) {
				difference.add(allProjects[i].getName());
			}
		}
		final String[] returnValue = new String[difference.size()];
		difference.toArray(returnValue);
		return returnValue;
	}
}