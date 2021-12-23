package Set;

public class HNode<E> {

	final int hash;
	final E key;
	HNode<E> next;

	public HNode(int hash, E key, HNode<E> next) {
		this.hash = hash;
		this.key = key;
		this.next = next;
	}
}
