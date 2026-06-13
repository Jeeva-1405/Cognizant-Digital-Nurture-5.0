class Book {
    int bookId;
    String title;
    String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public String toString() {
        return "Book{ID=" + bookId + ", Title=" + title + ", Author=" + author + "}";
    }
}

public class LibraryManagement {

    // Linear Search by title - O(n)
    public static Book linearSearchByTitle(Book[] books, String targetTitle) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(targetTitle)) {
                return book;
            }
        }
        return null;
    }

    // Binary Search by title - O(log n) - array must be sorted by title
    public static Book binarySearchByTitle(Book[] books, String targetTitle) {
        int left = 0;
        int right = books.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = books[mid].title.compareToIgnoreCase(targetTitle);

            if (comparison == 0) {
                return books[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Unsorted array for linear search
        Book[] library = {
            new Book(1, "Clean Code", "Robert C. Martin"),
            new Book(2, "Effective Java", "Joshua Bloch"),
            new Book(3, "Design Patterns", "Gang of Four"),
            new Book(4, "The Pragmatic Programmer", "Andrew Hunt"),
            new Book(5, "Refactoring", "Martin Fowler")
        };

        // Sorted array for binary search (sorted alphabetically by title)
        Book[] sortedLibrary = {
            new Book(1, "Clean Code", "Robert C. Martin"),
            new Book(3, "Design Patterns", "Gang of Four"),
            new Book(2, "Effective Java", "Joshua Bloch"),
            new Book(5, "Refactoring", "Martin Fowler"),
            new Book(4, "The Pragmatic Programmer", "Andrew Hunt")
        };

        System.out.println("Linear Search for 'Design Patterns':");
        Book result = linearSearchByTitle(library, "Design Patterns");
        System.out.println(result != null ? "Found: " + result : "Not found");

        System.out.println();

        System.out.println("Binary Search for 'Effective Java' (sorted array):");
        result = binarySearchByTitle(sortedLibrary, "Effective Java");
        System.out.println(result != null ? "Found: " + result : "Not found");

        System.out.println();

        System.out.println("Binary Search for 'Unknown Book':");
        result = binarySearchByTitle(sortedLibrary, "Unknown Book");
        System.out.println(result != null ? "Found: " + result : "Not found");

        /*
         * Time Complexity:
         * Linear Search - O(n) - works on any array, no sorting required
         * Binary Search - O(log n) - requires sorted array
         *
         * For small libraries, linear search is sufficient.
         * For large libraries with thousands of books, binary search
         * is significantly faster when data is pre-sorted.
         */
    }
}
