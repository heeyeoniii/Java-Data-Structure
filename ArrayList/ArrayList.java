import java.util.Arrays;
import Interface_form.List;

public class ArrayList<E> implements List<E> {

	private static final int DEFAULT_CAPACITY = 10; // 최소 용적 크기
	private static final Object[] EMPTY_ARRAY = {}; // 빈 배열

	private int size; // 요소 개수

	Object[] array; // 요소를 담을 배열

	// 생성자 1 : 초기 공간 할당 X
	public ArrayList() {
		this.array = EMPTY_ARRAY;
		this.size = 0;
	}

	// 생성자 2 : 초기 공간 할당 O
	public ArrayList(int capacity) {
		this.array = new Object[capacity];
		this.size = 0;
	}

	// 동적 할당을 위한 resize 메소드
	public void resize() {
		int array_capacity = array.length;

		if (Arrays.equals(array, EMPTY_ARRAY)) {
			array = new Object[DEFAULT_CAPACITY];
			return;
		}

		if (size == array_capacity) {
			int new_capacity = array_capacity * 2; // 새로운 용적을 현재 용적의 2배로

			// Arrays.copyOf('복사할 배열', '용적의 크기');
			// 복사할 배열보다 용적의 크기가 클 경우 나머지 빈 공간은 null로 채워진다.
			array = Arrays.copyOf(array, new_capacity);
			return;
		}

		if (size < (array_capacity / 2)) {
			int new_capacity = array_capacity / 2;

			// 복사할 배열보다 용적의 크기가 작을 경우 새로운 용적까지만 복사하고 반환한다.
			array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
			return;
		}
	}

	@Override
	public boolean add(E value) {
		addLast(value);

		return false;
	}

	public void addLast(E value) {
		if (size == array.length) {
			resize();
		}
		array[size] = value;
		size++;
	}

	@Override
	public void add(int index, E value) {

		// 영역을 벗어날 경우 예외 발생
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (index == size) {
			addLast(value);
		} else if (size == array.length) {
			resize();
		}

		for (int i = size; i > index; i--) {
			array[i] = array[i - 1];
		}

		array[index] = value;
		size++;
	}

	public void addFirst(E value) {
		add(0, value);
	}

	@Override
	public E remove(int index) {

		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		E element = (E) array[index];
		array[index] = null;

		for (int i = index; i < size; i++) {
			array[i] = array[i + 1];
			array[i + 1] = null;
		}

		size--;
		resize();

		return element;
	}

	@Override
	public boolean remove(Object value) {

		int index = indexOf(value);

		if (index == -1) {
			return false;
		}

		remove(index);

		return true;
	}

	@Override
	public E get(int index) {

		// 영역을 벗어날 경우 예외 발생
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		// Object 타입 -> E 타입
		return (E) array[index];
	}

	@Override
	public void set(int index, E value) {

		// 영역을 벗어날 경우 예외 발생
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		} else {
			array[index] = value;
		}

	}

	@Override
	public boolean contains(Object value) {
		if (indexOf(value) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int indexOf(Object value) {
		int i = 0;

		for (i = 0; i < size; i++) {
			if (array[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object value) {
		for (int i = size - 1; i >= 0; i--) {
			if (array[i].equals(value)) {
				return i;
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

		// 요소가 0개일 경우 비어있다는 의미로 true 반환
		return size == 0;
	}

	@Override
	public void clear() {

		for (int i = 0; i < size; i++) {
			array[i] = null;
		}

		size = 0;
		resize();

	}
}
