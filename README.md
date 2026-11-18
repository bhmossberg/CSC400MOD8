# CSC400MOD8 – Portfolio Project: Person Queue with Quicksort

A circular-array queue of `Person` objects (first name, last name, age). The demo reads five people, prints them in arrival order, and then uses quicksort to order them by last name and by age, both descending.

## Overview

This is **CSU Global CSC400, Module 8 – Portfolio Project** (commit "Portfolio v1.0"). It is an Eclipse Java project with all classes in the `portfolioProject` package under `src/`.

## Classes

### `Person.java`
An immutable data class with `firstName`, `lastName` and `age`, getters, and `toString()` → `Last, First (age)`.

### `ArrayQueue.java` – circular array queue
- Storage is a `Person[] data` with a default capacity of **5**. The class tracks the **front index `f`** and the **live count `sz`**. The next rear slot is `(f + sz) % capacity`, so the array wraps around and there's no need for a separate rear index.

| Method | Behaviour | Time |
|---|---|---|
| `enqueue(Person)` | Writes to the rear slot. Throws `IllegalStateException("Queue is full")` at capacity. | O(1) |
| `dequeue()` | Removes and returns `data[f]`, clears the slot, and advances `f = (f + 1) % capacity`. Returns `null` if the queue is empty. | O(1) |
| `first()`, `size()`, `isEmpty()`, `isFull()` | Standard queue queries | O(1) |
| `toArray()` | A snapshot in FIFO order (index 0 is the front) | O(n) |
| `sortByLastNameDescending()` | Copies the queue to an array, quicksorts it with the comparator `y.lastName.compareToIgnoreCase(x.lastName)`, then calls `rebuild()` | O(n log n) average |
| `sortByAgeDescending()` | Same approach with `Integer.compare(y.age, x.age)` | O(n log n) average |
| `quicksort` / `partition` / `swap` (private) | Recursive quicksort using the **Lomuto partition** (the last element is the pivot). The `Comparator<Person>` is passed in as a lambda. | – |
| `rebuild(Person[])` (private) | Clears the array, writes the sorted elements starting at slot 0, and resets `f = 0` | O(n) |
| `toString()` | One person per line in queue order, or `[empty]` | O(n) |

### `PersonQueueDemo.java` (`main`)
1. Prompts for the first name, last name and age of 5 people. `readAge` keeps re-prompting until it gets a non-negative integer.
2. Saves a FIFO snapshot and prints the queue in arrival order.
3. Sorts by last name (descending) and prints.
4. Calls `restore()`, which dequeues everything and re-enqueues the snapshot, so the age sort starts from the original order. Then it sorts by age (descending) and prints.

## Build and run

JDK 8 or newer:

```bash
mkdir -p bin
javac -d bin src/portfolioProject/*.java
java -cp bin portfolioProject.PersonQueueDemo
```

Sample output (verified, after entering five people):
```
Queue in arrival order:
Lovelace, Ada (36)
Turing, Alan (41)
Hopper, Grace (85)
Torvalds, Linus (55)
Ritchie, Dennis (70)

Descending by last name:
Turing, Alan (41)
Torvalds, Linus (55)
Ritchie, Dennis (70)
Lovelace, Ada (36)
Hopper, Grace (85)

Descending by age:
Hopper, Grace (85)
Ritchie, Dennis (70)
Torvalds, Linus (55)
Turing, Alan (41)
Lovelace, Ada (36)
```

## Concepts demonstrated
- The queue ADT (FIFO) on a circular array with modular arithmetic
- Quicksort with Lomuto partitioning and pluggable `Comparator` lambdas
- Sorting by different keys (strings ignoring case, and integers)
- Validating input in a retry loop
- Separating the data structure, the data class and the driver
