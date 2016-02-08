package com.rajendarreddyj.eclipse.plugins.weblogic.editors;

import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import com.rajendarreddyj.eclipse.plugins.weblogic.WeblogicPluginResources;
import com.rajendarreddyj.eclipse.plugins.weblogic.dialogs.SimpleListContentProvider;

public class ProjectFieldEditor extends ListFieldEditor {
    public ProjectFieldEditor(String name, String labelText, Composite parent) {
        super(name, labelText, parent);
    }

    protected String getNewInputObject() {
        String[] currentItems = this.list.getItems();
        IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

        ILabelProvider labelProvider = new LabelProvider() {
            public String getText(Object element) {
                return (String) element;
            }
        };
        SimpleListContentProvider contentsProvider = new SimpleListContentProvider();
        contentsProvider.setElements(sortedDifference(allProjects, currentItems));

        ListSelectionDialog dialog = new ListSelectionDialog(getShell(), this, contentsProvider, labelProvider,
                WeblogicPluginResources.PROJECT_SELECTION_LABEL);
        if (dialog.open() != 0) {
            return null;
        }
        Object[] result = dialog.getResult();

        int currentItemsLength = currentItems.length;
        int resultLength = result.length;
        String[] newItems = new String[currentItemsLength + resultLength];

        System.arraycopy(currentItems, 0, newItems, 0, currentItemsLength);
        System.arraycopy(result, 0, newItems, currentItemsLength, result.length);

        this.list.setItems(newItems);
        return null;
    }

    private String[] sortedDifference(IProject[] allProjects, String[] currentlyDisplayed) {
        TreeSet<String> current = new TreeSet<>();
        for (int i = 0; i < currentlyDisplayed.length; i++) {
            current.add(currentlyDisplayed[i]);
        }
        TreeSet<String> difference = new TreeSet<>();
        for (int i = 0; i < allProjects.length; i++) {
            if (!current.contains(allProjects[i].getName())) {
                difference.add(allProjects[i].getName());
            }
        }
        String[] returnValue = new String[difference.size()];
        difference.toArray(returnValue);
        return returnValue;
    }
}