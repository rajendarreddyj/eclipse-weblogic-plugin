package com.rajendarreddyj.eclipse.plugins.weblogic.dialogs;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SimpleListContentProvider implements IStructuredContentProvider {
    private Object[] elements;

    public void dispose() {
    }

    public Object[] getElements(Object inputElement) {
        return this.elements;
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    public void setElements(Object[] items) {
        this.elements = items;
    }
}
