package Set;

import Interface_form.Set;

public class HashSet<E> implements Set<E> {

	private final static int DEFAULT_CAPACITY = 1 << 4;

	private final static float LOAD_FACTOR = 0.75f; // 3/4 이상 채워질 경우 resize

	HNode<E>[] table;
	private int size;

	public HashSet() {
		table = (HNode<E>[]) new HNode[DEFAULT_CAPACITY];
		size = 0;
	}

	// 보조 해시 함수
	private static final int hash(Object key) {
		int hash;
		if (key == null) {
			return 0;
		} else {
			return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
		}
	}

	@Override
	public boolean add(E e) {

		return add(hash(e), e) == null;
	}

	private E add(int hash, E key) {
		int idx = hash & table.length;

		if (table[idx] == null) {
			table[idx] = new HNode<E>(hash, key, null);

		} else {
			// table[idx]에 요소가 이미 존재할 경우(해시 충돌)
			HNode<E> temp = table[idx];
			HNode<E> prev = null;

			while (temp != null) {
				if ((temp.hash == hash) && (temp.key == key || temp.key.equals(key))) {
					return key;
				}
				prev = temp;
				temp = temp.next;
			}

			prev.next = new HNode<E>(hash, key, null);

		}
		size++;

		if (size >= LOAD_FACTOR * table.length) {
			resize();
		}
		return null;
	}

	private void resize() {
		int newCapacity = table.length * 2;

		final HNode<E>[] newTable = (HNode<E>[]) new HNode[newCapacity];

		for (int i = 0; i < table.length; i++) {
			HNode<E> value = table[i];

			if (value == null) {
				continue;
			}

			table[i] = null;

			HNode<E> nextNode;

			while (value != null) {
				int idx = value.hash % newCapacity;

				if (newTable[idx] != null) {
					HNode<E> tail = newTable[idx];

					while (tail.next != null) {
						tail = tail.next;
					}

					nextNode = value.next;
					value.next = null;
					tail.next = value;
				} else {
					nextNode = value.next;
					value.next = null;
					newTable[idx] = value;
				}
				value = nextNode;
			}
		}
		table = null;
		table = newTable;
	}

	@Override
	public boolean remove(Object o) {

		return remove(hash(o), o) != null;
	}

	private Object remove(int hash, Object key) {
		int idx = hash % table.length;

		HNode<E> node = table[idx];
		HNode<E> removedNode = null;
		HNode<E> prev = null;

		if (node == null) {
			return null;
		}

		while (node != null) {
			if (node.hash == hash && (node.key == key || node.key.equals(key))) {

				removedNode = node;

				if (prev == null) {
					table[idx] = node.next;
					node = null;
				}

				else {
					prev.next = node.next;
					node = null;
				}
				size--;
				break;
			}
			prev = node;
			node = node.next;
		}

		return removedNode;
	}

	@Override
	public boolean contains(Object o) {
		int idx = hash(o) % table.length;
		HNode<E> temp = table[idx];

		while (temp != null) {
			if (o == temp.key || (o != null && temp.key.equals(o))) {
				return true;
			}
			temp = temp.next;
		}

		return false;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		if (table != null && size > 0) {
			for (int i = 0; i < table.length; i++) {
				table[i] = null;
			}
			size = 0;
		}

	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof HashSet)) {
			return false;
		}

		HashSet<E> oSet;

		try {

			oSet = (HashSet<E>) o;
			if (oSet.size() != size) {
				return false;
			}

			for (int i = 0; i < oSet.table.length; i++) {
				HNode<E> oTable = oSet.table[i];

				while (oTable != null) {
					if (!contains(oTable)) {
						return false;
					}
					oTable = oTable.next;
				}
			}

		} catch (ClassCastException e) {
			return false;
		}
		return true;

	}
}
