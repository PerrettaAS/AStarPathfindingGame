package org.game.misc;

import java.util.ArrayList;
import java.util.Collections;

public class SortedList {

	@SuppressWarnings("rawtypes")
	private ArrayList list = new ArrayList();

	public Object first() {
		return list.get(0);
	}

	public void clear() {
		list.clear();
	}

	@SuppressWarnings("unchecked")
	public void add(Object o) {
		list.add(o);
		Collections.sort(list);
	}

	public void remove(Object o) {
		list.remove(o);
	}

	public int size() {
		return list.size();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}
	
}
