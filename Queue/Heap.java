package Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<E> {

	private final Comparator<? super E> comparator;
	private static final int DEFAULT_CAPACITY = 10;

	private int size;

	private Object[] array;

	public Heap() {
		this(null);
	}

	public Heap(Comparator<? super E> comparator) {
		this.array = new Object[DEFAULT_CAPACITY];
		this.size = 0;
		this.comparator = comparator;
	}

	public Heap(int capacity) {
		this(capacity, null);
	}

	public Heap(int capacity, Comparator<? super E> comparator) {
		this.array = new Object[capacity];
		this.size = 0;
		this.comparator = comparator;
	}

	// 받은 인덱스의 부모 노드 인덱스 반환
	private int getParent(int index) {
		return index / 2;
	}

	// 받은 인덱스의 왼쪽 자식 노드 인덱스 반환
	private int getLeftChild(int index) {
		return index * 2;
	}

	// 받은 인덱스의 오른쪽 자식 노드 인덱스 반환
	private int getRightChild(int index) {
		return index * 2 + 1;
	}

	private void resize(int newCapacity) {

		Object[] newArray = new Object[newCapacity];

		for (int i = 1; i <= size; i++) {
			newArray[i] = array[i];
		}

		this.array = null;
		this.array = newArray;
	}

	// sift-up(상향 선별)
	// 배열의 마지막 부분에 원소를 넣고 부모노드를 찾아가며 부모노드가 삽입노드보다
	// 작을 때까지 요소를 교환하며 올라간다.
	public void add(E value) {
		if (size + 1 == array.length) {
			resize(array.length * 2);
		}

		siftUp(size + 1, value);
		size++;
	}

	private void siftUp(int idx, E target) {
		// comparator가 존재할 경우 comparator를 인자로 넘겨준다.
		if (comparator != null) {
			siftUpComparator(idx, target, comparator);

		} else {
			siftUpComparable(idx, target);
		}
	}

	// Comparator을 이용한 sift-up -> compare()
	private void siftUpComparator(int idx, E target, Comparator<? super E> comp) {

		while (idx > 1) { // 루트노드보다 클 때까지
			int parent = getParent(idx);
			Object parentVal = array[parent];

			if (comp.compare(target, (E) parentVal) >= 0) {
				// 타겟노드 값이 부모노드보다 크면 반복문 종료
				break;
			}

			array[idx] = parentVal;
			idx = parent;
		}
		array[idx] = target;
	}

	// 삽입할 객체의 Comparable을 이용한 sift-up -> compareTo()
	private void siftUpComparable(int idx, E target) {

		Comparable<? super E> comp = (Comparable<? super E>) target;

		while (idx > 1) {
			int parent = getParent(idx);
			Object parentVal = array[parent];

			if (comp.compareTo((E) parentVal) >= 0) {
				break;
			}
			array[idx] = parentVal;
			idx = parent;
		}
		array[idx] = comp;
	}

	// sift-down(하향 선별)
	public E remove() {

		// root가 비어있을경우
		if (array[1] == null) {
			throw new NoSuchElementException();
		}

		E result = (E) array[1];
		E target = (E) array[size];
		array[size] = null;

		siftDown(1, target);

		return result;
	}

	private void siftDown(int idx, E target) {
		if (comparator != null) {
			siftDownComparator(idx, target, comparator);

		} else {
			siftDownComparable(idx, target);
		}
	}

	private void siftDownComparator(int idx, E target, Comparator<? super E> comp) {
		array[idx] = null;
		size--;

		int parent = idx;
		int child;

		while ((child = getLeftChild(parent)) <= size) {
			int right = getRightChild(parent);
			Object childVal = array[child];

			if (right <= size && comp.compare((E) childVal, (E) array[right]) > 0) {
				child = right;
				childVal = array[child];
			}

			if (comp.compare(target, (E) childVal) <= 0) {
				break;
			}

			array[parent] = childVal;
			parent = child;
		}

		array[parent] = target;

		if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
			resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
		}
	}

	private void siftDownComparable(int idx, E target) {

		Comparable<? super E> comp = (Comparable<? super E>) target;

		array[idx] = null;
		size--;

		int parent = idx;
		int child;

		while ((child = getLeftChild(parent)) <= size) {

			int right = getRightChild(parent);

			Object childVal = array[child];

			if (right <= size && ((Comparable<? super E>) childVal).compareTo((E) array[right]) > 0) {
				child = right;
				childVal = array[child];
			}

			if (comp.compareTo((E) childVal) <= 0) {
				break;
			}
			array[parent] = childVal;
			parent = child;

		}
		array[parent] = comp;

		if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
			resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
		}
	}

	public int size() {
		return this.size;
	}

	public E peek() {
		if (array[1] == null) {
			throw new NoSuchElementException();
		}
		return (E) array[1];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Object[] toArray() {
		return Arrays.copyOf(array, size + 1);
	}
}
