package portfolioProject;

/******************************************************************************
 * PersonQueueDemo.java - enqueue five people, print, quicksort twice
 * Benjamin Mossberg, CSC400
 ******************************************************************************/

import java.util.Scanner;

public class PersonQueueDemo {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		ArrayQueue queue = new ArrayQueue();

		System.out.println("Enter first name, last name, and age for 5 people.");
		for (int n = 1; n <= 5; n++) {
			System.out.println("Person " + n + ":");
			System.out.print("  First name: ");
			String first = in.nextLine().trim();
			System.out.print("  Last name:  ");
			String last = in.nextLine().trim();
			System.out.print("  Age:        ");
			int age = readAge(in);
			queue.enqueue(new Person(first, last, age));
		}

		Person[] original = queue.toArray();

		System.out.println();
		System.out.println("Queue in arrival order:");
		System.out.println(queue);

		queue.sortByLastNameDescending();
		System.out.println();
		System.out.println("Descending by last name:");
		System.out.println(queue);

		restore(queue, original);
		queue.sortByAgeDescending();
		System.out.println();
		System.out.println("Descending by age:");
		System.out.println(queue);

		in.close();
	}

	private static int readAge(Scanner in) {
		while (true) {
			String line = in.nextLine().trim();
			try {
				int age = Integer.parseInt(line);
				if (age >= 0) {
					return age;
				}
			} catch (NumberFormatException ignored) {
			}
			System.out.print("  Age (integer): ");
		}
	}

	/** Reload the queue from a FIFO snapshot so the second sort starts clean. */
	private static void restore(ArrayQueue queue, Person[] original) {
		while (!queue.isEmpty()) {
			queue.dequeue();
		}
		for (Person p : original) {
			queue.enqueue(p);
		}
	}
}
