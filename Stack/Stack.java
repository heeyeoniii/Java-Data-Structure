package Stack;

import java.util.Arrays;
import java.util.EmptyStackException;

import Interface_form.StackInterface;

public class Stack<E> implements StackInterface<E> {

	private static final int DEFAULT_CAPACITY = 10; // 최소 용적 크기
	private static final Object[] EMPTY_ARRAY = {}; // 빈 배열

	private Object[] array; // 요소를 담을 배열
	private int size; // 요소 개수

	// 생성자 1 : 초기 공간 할당 X
	public Stack() {
		this.array = EMPTY_ARRAY;
		this.size = 0;
	}

	// 생성자 2 : 초기 공간 할당 O
	public Stack(int capacity) {
		this.array = new Object[capacity];
		this.size = 0;
	}

	// 동적할당을 위한 resize 메소드
	public void resize() {

		if (Arrays.equals(array, EMPTY_ARRAY)) {
			array = new Object[DEFAULT_CAPACITY];
			return;
		}

		int arrayCapacity = array.length; // 현재 용적 크기

		if (size == arrayCapacity) {
			int newSize = arrayCapacity * 2;

			array = Arrays.copyOf(array, newSize);
			return;
		}

		if (size < (arrayCapacity / 2)) {

			int newCapacity = (arrayCapacity / 2);

			array = Arrays.copyOf(array, Math.max(DEFAULT_CAPACITY, newCapacity));
			return;
		}
	}

	@Override
	public E push(E item) {

		if (size == array.length) {
			resize();
		}

		array[size] = item; // 마지막 위치에 요소 추가
		size++;

		return item;
	}

	@Override
	public E pop() {

		if (size == 0) {
			throw new EmptyStackException();
		}

		E obj = (E) array[size - 1]; // 삭제할 요소를 반환하기 위한 임시 변수

		array[size - 1] = null;

		size--;
		resize();

		return obj;
	}

	@Override
	public E peek() {

		if (size == 0) {
			throw new EmptyStackException();
		}

		return (E) array[size - 1];
	}

	// Top으로부터 떨어져있는 거리 의미(1부터 시작)
	@Override
	public int search(Object value) {

		for (int idx = size - 1; idx >= 0; idx--) {

			if (array[idx].equals(value)) {
				return size - idx;
			}
		}
		return -1;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public void clear() {

		for (int i = 0; i < size; i++) {
			array[i] = null;
		}

		size = 0;
		resize();
	}

	@Override
	public boolean empty() {

		return size == 0;
	}

}
