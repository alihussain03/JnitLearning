package bookstored;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import bookstoread.Book;
import bookstoread.BookShelf;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("<= BookShelf Specification =>")
public class BookShelfSpec {

  private BookShelf shelf;
  private Book effectiveJava;
  private Book codeComplete;
  private Book mythicalManMonth;
  private Book cleanCode;

  @BeforeEach
  void init() throws Exception {
    shelf = new BookShelf();
    effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
    codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
    mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks",
        LocalDate.of(1975, Month.JANUARY, 1));
    cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
  }

  @Nested
  @DisplayName("is empty")
  class IsEmpty {

    @Test
    @DisplayName("when no book is added to it")
    public void emptyBookShelfWhenNoBookAdded() {
      List<Book> books = shelf.books();
      assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("when add is called without books")
    void emptyBookShelfWhenAddIsCalledWithoutBooks() {
      shelf.add();
      List<Book> books = shelf.books();
      assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }
  }

  @Nested
  @DisplayName("after adding books")
  class BooksAreAdded {

    @Test
    public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
      shelf.add(effectiveJava, codeComplete);
      List<Book> books = shelf.books();
      assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    void booksReturnedFromBookShelfIsImmutableForClient() {
      shelf.add(effectiveJava, codeComplete);
      List<Book> books = shelf.books();
      try {
        books.add(mythicalManMonth);
        fail(() -> "Should not be able to add book to books");
      } catch (Exception e) {
        assertTrue(e instanceof UnsupportedOperationException,
            () -> "Should throw UnsupportedOperationException.");
      }
    }
  }

  @Nested
  @DisplayName("is arranged")
  class BooksArrange {

    /*
     * Second Feature
     * */
    @Test
    @DisplayName("bookshelf is arranged lexicographically by book title")
    void bookshelfArrangedByBookTitle() {
      shelf.add(effectiveJava, codeComplete, mythicalManMonth);
      List<Book> books = shelf.arrange();
      assertEquals(asList(codeComplete, effectiveJava, mythicalManMonth), books,
          () -> "Books in a bookshelf should be arranged lexicographically by book title");
    }

    @Test
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
      shelf.add(effectiveJava, codeComplete, mythicalManMonth);
      shelf.arrange();
      List<Book> books = shelf.books();
      assertEquals(asList(effectiveJava, codeComplete, mythicalManMonth), books,
          () -> "Books in bookshelf are in insertion order");
    }

    // @Disabled("Needs to implement Comparator")
    @Test
    void bookshelfArrangedByUserProvidedCriteria() {
      shelf.add(effectiveJava, codeComplete, mythicalManMonth);
      Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
      List<Book> books = shelf.arrange(reversed);
      assertEquals(
          asList(mythicalManMonth, effectiveJava, codeComplete),
          books,
          () -> "Books in a bookshelf are arranged in descending order of book title");

      assertThat(books).isSortedAccordingTo(reversed);
    }

    @Test
    @DisplayName("books inside bookshelf are grouped by publication year")
    void groupBooksInsideBookShelfByPublicationYear() {
      shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);

      Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

      assertThat(booksByPublicationYear)
          .containsKey(Year.of(2008))
          .containsValues(Arrays.asList(effectiveJava, cleanCode));

      assertThat(booksByPublicationYear)
          .containsKey(Year.of(2004))
          .containsValues(singletonList(codeComplete));

      assertThat(booksByPublicationYear)
          .containsKey(Year.of(1975))
          .containsValues(singletonList(mythicalManMonth));

    }

    @Test
    @DisplayName("books inside bookshelf are grouped according to user provided criteria(group by author name)")
    void groupBooksByUserProvidedCriteria() {
      shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
      Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);

      assertThat(booksByAuthor)
          .containsKey("Joshua Bloch")
          .containsValues(singletonList(effectiveJava));

      assertThat(booksByAuthor)
          .containsKey("Steve McConnel")
          .containsValues(singletonList(codeComplete));

      assertThat(booksByAuthor)
          .containsKey("Frederick Phillips Brooks")
          .containsValues(singletonList(mythicalManMonth));

      assertThat(booksByAuthor)
          .containsKey("Robert C. Martin")
          .containsValues(singletonList(cleanCode));
    }

  }
}
