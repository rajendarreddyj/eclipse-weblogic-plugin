package com.rajendarreddyj.eclipse.plugins.weblogic.dialogs;

import org.eclipse.jface.viewers.IStructuredContentProvider;

/**
 * This Class is use to handle Simple Content in project preference Page
 * 
 * @author rajendarreddyj
 *
 */
public class SimpleListContentProvider implements IStructuredContentProvider {
    private Object[] elements;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.
     * lang.Object)
     */
    @Override
    public Object[] getElements(final Object inputElement) {
        return this.elements;
    }

    /**
     * @param items
     */
    public void setElements(final Object[] items) {
        this.elements = items;
    }
}
