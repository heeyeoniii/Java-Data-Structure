package LinkedList;

import java.util.NoSuchElementException;

import Interface_form.List;

public class DLinkedList<E> implements List<E> {

	private DNode<E> head;
	private DNode<E> tail;
	private int size;

	public DLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	// 특정 위치의 노드를 반환하는 메소드
	private DNode<E> search(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		// 뒤에서부터 검색
		if (index > size / 2) {
			DNode<E> x = tail;

			for (int i = size - 1; i > index; i--) {
				x = x.prev;
			}
			return x;

		} else {
			DNode<E> x = head;

			for (int i = 0; i < index; i++) {
				x = x.next;
			}
			return x;
		}
	}

	public void addFirst(E value) {

		DNode<E> newNode = new DNode<E>(value);
		newNode.next = head;

		if (head != null) {
			head.prev = newNode;
		}

		head = newNode;
		size++;

		if (head.next == null) {
			tail = head;
		}
	}

	@Override
	public boolean add(E value) {

		addLast(value);

		return true;
	}

	public void addLast(E value) {
		DNode<E> newNode = new DNode<E>(value);

		if (size == 0) {
			addFirst(value);
			return;
		}

		tail.next = newNode;
		newNode.prev = tail;
		tail = newNode;
		size++;
	}

	@Override
	public void add(int index, E value) {

		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			addFirst(value);
			return;
		}

		if (index == size) {
			addLast(value);
			return;
		}

		DNode<E> prev_Node = search(index - 1);
		DNode<E> next_Node = prev_Node.next;
		DNode<E> newNode = new DNode<E>(value);

		prev_Node.next = null;
		next_Node.prev = null;

		prev_Node.next = newNode;

		newNode.prev = prev_Node;
		newNode.next = next_Node;

		next_Node.prev = newNode;
		size++;
	}

	// 가장 앞에 있는 요소 제거
	public E remove() {
		DNode<E> headNode = head;

		if (headNode == null) {
			throw new NoSuchElementException();
		}

		E element = headNode.data;

		DNode<E> nextNode = head.next;

		head.data = null;
		head.next = null;

		if (nextNode != null) {
			nextNode.prev = null;
		}

		head = nextNode;
		size--;

		if (size == 0) {
			tail = null;
		}

		return element;
	}

	@Override
	public E remove(int index) {

		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			E element = head.data;
			remove();
			return element;
		}

		DNode<E> prevNode = search(index - 1);
		DNode<E> removedNode = prevNode.next;
		DNode<E> nextNode = removedNode.next;

		E element = removedNode.data;

		prevNode.next = null;
		removedNode.next = null;
		removedNode.prev = null;
		removedNode.data = null;

		if (nextNode != null) {
			nextNode.prev = null;

			nextNode.prev = prevNode;
			prevNode.next = nextNode;

		} else {
			tail = prevNode;
		}

		size--;

		return element;
	}

	@Override
	public boolean remove(Object value) {

		DNode<E> prevNode = head;
		DNode<E> x = head;

		for (; x != null; x = x.next) {
			if (value.equals(x.data)) {
				break;
			}
			prevNode = x;
		}

		if (x == null) {
			return false;
		}

		if (x.equals(head)) {
			remove();
			return true;
		} else {
			DNode<E> nextNode = x.next;

			prevNode.next = null;
			x.data = null;
			x.next = null;
			x.prev = null;

			if (nextNode != null) {
				nextNode.prev = null;

				nextNode.prev = prevNode;
				prevNode.next = nextNode;

			} else {
				tail = prevNode;
			}

			size--;

			return true;
		}
	}

	@Override
	public E get(int index) {

		return search(index).data;
	}

	@Override
	public void set(int index, E value) {

		DNode<E> replaceNode = search(index);

		replaceNode.data = null;
		replaceNode.data = value;

	}

	@Override
	public boolean contains(Object value) {

		return indexOf(value) >= 0;
	}

	// 정방향 탐색
	@Override
	public int indexOf(Object value) {
		int index = 0;

		for (DNode<E> x = head; x != null; x = x.next) {
			if (value.equals(x.data)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	// 역방향 탐색
	public int lastIndexOf(Object value) {
		int index = size;

		for (DNode<E> x = tail; x != null; x = x.prev) {

			index--;
			if (value.equals(x.data)) {
				return index;
			}
		}
		return -1;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public boolean isEmpty() {

		return size == 0;
	}

	@Override
	public void clear() {

		for (DNode<E> x = head; x != null;) {

			DNode<E> nextNode = x.next;

			x.data = null;
			x.next = null;
			x.prev = null;
			x = nextNode;
		}
		
		head = tail = null;
		size = 0;

	}
}
