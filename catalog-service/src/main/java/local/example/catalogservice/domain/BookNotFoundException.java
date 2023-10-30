package local.example.catalogservice.domain;

public class BookNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 556109179671639350L;

	public BookNotFoundException(String isbn) {
        super("The book with ISBN " + isbn + " was not found.");
    }

}
