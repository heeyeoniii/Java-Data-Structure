public class DNode<E> {

	E data;
	DNode<E> next;
	DNode<E> prev;

	DNode(E data) {
		this.data = data;
		this.prev = null;
		this.next = null;
	}
}
