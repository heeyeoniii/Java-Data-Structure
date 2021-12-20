package Queue;

import java.util.NoSuchElementException;
import Interface_form.Queue;

public class ArrayQueue<E> implements Queue<E> {

	private static final int DEFAULT_CAPACITY = 64; // 최소 용적 크기

	private Object[] array; // 요소를 담을 배열
	private int size; // 요소 개수

	private int front; // 시작 인덱스를 가리키는 변수
	private int rear; // 마지막 요소의 인덱스를 가리키는 변수

	// 생성자 1 : 초기 용적 할당 X
	public ArrayQueue() {
		this.array = new Object[DEFAULT_CAPACITY];
		this.size = 0;
		this.front = 0;
		this.rear = 0;
	}

	// 생성자 2 : 초기 용적 할당 O
	public ArrayQueue(int capacity) {
		this.array = new Object[capacity];
		this.size = 0;
		this.front = 0;
		this.rear = 0;
	}

	// 동적할당을 위한 메소드
	private void resize(int newCapacity) {
		int arrayCapacity = array.length; // 현재 용적 크기

		Object[] newArray = new Object[newCapacity];

		// i : new array index
		// j : original array
		for (int i = 1, j = front + 1; i <= size; i++, j++) {
			newArray[i] = array[j % arrayCapacity];
		}

		this.array = null;
		this.array = newArray; // 새 배열을 기존 array 배열로 덮어씌움

		front = 0;
		rear = size;

	}

	@Override
	public boolean offer(E e) {
		// 용적이 가득 찼을 경우
		if ((rear + 1) % array.length == front) { // rear + 1이 front와 같을 때
			resize(array.length * 2); // 용적을 두배로 늘려준다.
		}

		rear = (rear + 1) % array.length; // rear를 rear의 다음 위치로

		array[rear] = e;
		size++;

		return true;
	}

	@Override
	public E poll() {
		// 삭제할 요소가 없을 경우 null 반환
		if (size == 0) {
			return null;
		}

		front = (front + 1) % array.length; // front를 한 칸 옮긴다.

		E item = (E) array[front];

		array[front] = null;
		size--;

		if (array.length > DEFAULT_CAPACITY && size < (array.length / 4)) {
			resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
		}

		return item;
	}

	public E remove() {
		E item = poll();

		if (item == null) {
			throw new NoSuchElementException();
		}

		return item;
	}

	@Override
	public E peek() {

		if (size == 0) {
			return null;
		}

		E item = (E) array[(front + 1) % array.length];

		return item;
	}

	public E element() {
		E item = peek();

		if (item == null) {
			throw new NoSuchElementException();
		}

		return item;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(Object value) {
		int start = (front + 1) % array.length;

		for (int i = 0, idx = start; i < size; i++, idx = (idx + 1) % array.length) {
			if (array[idx].equals(value)) {
				return true;
			}
		}
		return false;
	}

	public void clear() {
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}

		front = rear = size = 0;
	}
}
