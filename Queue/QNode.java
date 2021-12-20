package Queue;

public class QNode<E> {
	E data;
	QNode<E> next;

	QNode(E data) {
		this.data = data;
		this.next = null;
	}
