package com.rajendarreddyj.eclipse.plugins.weblogic.dialogs;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

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
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.
	 * lang.Object)
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		return elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}

	/**
	 * @param items
	 */
	public void setElements(final Object[] items) {
		elements = items;
	}
}
