import java.util.NoSuchElementException;

import Interface_form.List;

public class SLinkedList<E> implements List<E> {

	private SNode<E> head; // 노드의 첫 부분
	private SNode<E> tail; // 노드의 마지막 부분
	private int size; // 요소 개수

	// 생성자
	public SLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	// 특정 위치의 노드를 반환하는 메소드
	private SNode<E> search(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		SNode<E> x = head; // head가 가리키는 노드부터 시작

		for (int i = 0; i < index; i++) {
			x = x.next; // x 노드의 다음 노드를 x에 저장
		}

		return x;
	}

	public void addFirst(E value) {

		SNode<E> newNode = new SNode<E>(value); // 새 노드 생성
		newNode.next = head; // 새 노드의 다음 노드로 head 노드 연결
		head = newNode; // head가 가리키는 노드를 새 노드로 변경
		size++;

		// 다음에 가리킬 노드가 없는 경우(데이터가 새 노드밖에 없는 경우)
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
		SNode<E> newNode = new SNode<E>(value); // 새 노드 생성

		if (size == 0) {
			addFirst(value);
			return;
		}

		tail.next = newNode; // 마지막 노드의 다음 노드가 새 노드를 가리키도록
		tail = newNode; // tail이 가리키는 노드를 새 노드로
		size++;
	}

	@Override
	public void add(int index, E value) {

		if (index > size || size < 0) {
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

		// 추가하려는 위치의 이전 노드
		SNode<E> prev_Node = search(index - 1);

		// 추가하려는 위치의 노드
		SNode<E> next_Node = prev_Node.next;

		// 추가하려는 노드
		SNode<E> newNode = new SNode<E>(value);

		prev_Node.next = null;
		prev_Node.next = newNode;
		newNode.next = next_Node;
		size++;
	}

	// 가장 앞에 있는 요소 제거
	public E remove() {
		SNode<E> headNode = head;

		if (headNode == null) {
			throw new NoSuchElementException();
		}

		// 삭제된 노드를 반환하기 위한 임시 변수
		E element = headNode.data;

		SNode<E> nextNode = head.next;

		head.data = null;
		head.next = null;

		// head가 다음 노드를 가리키도록
		head = nextNode;
		size--;

		if (size == 0) {
			tail = null;
		}

		return element;
	}

	@Override
	public E remove(int index) {

		if (index > size || size < 0) {
			throw new IndexOutOfBoundsException();
		}

		// 삭제하려는 노드가 첫 번째 원소일 경우
		if (index == 0) {
			return remove();
		}

		// 삭제할 노드의 이전 노드
		SNode<E> prevNode = search(index - 1);

		// 삭제할 노드
		SNode<E> removedNode = prevNode.next;

		// 삭제할 노드의 다음 노드
		SNode<E> nextNode = removedNode.next;

		E element = removedNode.data;

		prevNode.next = nextNode;

		removedNode.next = null;
		removedNode.data = null;
		size--;

		return element;

	}

	@Override
	public boolean remove(Object value) {

		SNode<E> prevNode = head;
		boolean hasValue = false;
		SNode<E> x = head;

		// value와 일치하는 노드 찾기
		for (; x != null; x = x.next) {
			if (value.equals(x.data)) {
				hasValue = true;
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
			prevNode.next = x.next;

			x.data = null;
			x.next = null;
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

		SNode<E> replaceNode = search(index);
		replaceNode.data = null;
		replaceNode.data = value;

	}

	@Override
	public boolean contains(Object value) {

		return indexOf(value) >= 0;
	}

	@Override
	public int indexOf(Object value) {

		int index = 0;

		for (SNode<E> x = head; x != null; x = x.next) {
			if (value.equals(x.data)) {
				return index;
			}
			index++;
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

		for (SNode<E> x = head; x != null;) {
			SNode<E> nextNode = x.next;

			// 객체 자체를 null해주기 보다는 모든 노드를 하나하나 null로 처리하는 것이
			// 가비지 컬렉터가 명시적으로 해당 메모리를 안 쓴다고 인지하기 때문에
			// 메모리 관리 효율 측면에서 조금 더 좋다.
			x.data = null;
			x.next = null;
			x = nextNode;
		}

		head = tail = null;
		size = 0;

	}
}
