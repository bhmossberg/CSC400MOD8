package portfolioProject;

/******************************************************************************
 * ArrayQueue.java - circular array queue of Person
 * Benjamin Mossberg, CSC400
 * Front index f, live count sz, next rear = (f + sz) % capacity.
 * Default capacity of 5 for demonstration
 ******************************************************************************/

import java.util.Comparator;

public class ArrayQueue {
	private static final int CAPACITY = 5;

	private Person[] data;
	private int f = 0;     // index of the front element
	private int sz = 0;    // current number of elements

	public ArrayQueue() {
		this(CAPACITY);
	}

	public ArrayQueue(int capacity) {
		data = new Person[capacity];
	}

	public int size() {
		return sz;
	}

	public boolean isEmpty() {
		return sz == 0;
	}

	public boolean isFull() {
		return sz == data.length;
	}

	/** Adds a person at the rear. Rear slot is (f + sz) mod capacity. */
	public void enqueue(Person p) {
		if (isFull()) {
			throw new IllegalStateException("Queue is full");
		}
		int avail = (f + sz) % data.length;
		data[avail] = p;
		sz++;
	}

	/** Returns the front person without removing it. */
	public Person first() {
		if (isEmpty()) {
			return null;
		}
		return data[f];
	}

	/** Removes and returns the front person. */
	public Person dequeue() {
		if (isEmpty()) {
			return null;
		}
		Person answer = data[f];
		data[f] = null;
		f = (f + 1) % data.length;
		sz--;
		return answer;
	}

	/** FIFO snapshot, index 0 is the current front. */
	public Person[] toArray() {
		Person[] copy = new Person[sz];
		for (int i = 0; i < sz; i++) {
			copy[i] = data[(f + i) % data.length];
		}
		return copy;
	}

	/** Replace the live contents with a linear array starting at slot 0. */
	private void rebuild(Person[] ordered) {
		for (int i = 0; i < data.length; i++) {
			data[i] = null;
		}
		for (int i = 0; i < ordered.length; i++) {
			data[i] = ordered[i];
		}
		f = 0;
		sz = ordered.length;
	}

	public void sortByLastNameDescending() {
		Person[] a = toArray();
		quicksort(a, 0, a.length - 1, (x, y) ->
				y.getLastName().compareToIgnoreCase(x.getLastName()));
		rebuild(a);
	}

	public void sortByAgeDescending() {
		Person[] a = toArray();
		quicksort(a, 0, a.length - 1, (x, y) ->
				Integer.compare(y.getAge(), x.getAge()));
		rebuild(a);
	}

	private void quicksort(Person[] a, int lo, int hi, Comparator<Person> cmp) {
		if (hi <= lo) {
			return;
		}
		int p = partition(a, lo, hi, cmp);
		quicksort(a, lo, p - 1, cmp);
		quicksort(a, p + 1, hi, cmp);
	}

	/** Sort partition; pivot is a[hi]. */
	private int partition(Person[] a, int lo, int hi, Comparator<Person> cmp) {
		Person pivot = a[hi];
		int i = lo;
		for (int j = lo; j < hi; j++) {
			if (cmp.compare(a[j], pivot) <= 0) {
				swap(a, i, j);
				i++;
			}
		}
		swap(a, i, hi);
		return i;
	}

	private void swap(Person[] a, int i, int j) {
		Person tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "[empty]";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sz; i++) {
			if (i > 0) {
				sb.append('\n');
			}
			sb.append(data[(f + i) % data.length]);
		}
		return sb.toString();
	}
}
