package bookstoread;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BookShelf {

  private List<Book> books = new ArrayList<>();

  /* public List<String> books() {
     return books;
   }
 */
  public List<Book> books() {
    return Collections.unmodifiableList(books);
  }

  public void add(Book... booksToAdd) {
    Arrays.stream(booksToAdd).forEach(books::add);
  }

  public List<Book> arrange() {
    return arrange(Comparator.naturalOrder());
  }

  public List<Book> arrange(Comparator<Book> reversed) {
    return books.stream().sorted(reversed).collect(toList());
  }

  public Map<Year, List<Book>> groupByPublicationYear() {
    return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
  }

  public <K> Map<K, List<Book>> groupBy(Function<Book, K> fx) {
    return books
        .stream()
        .collect(groupingBy(fx));
  }

  /*public Progress progress() {
    return new Progress(0, 100, 0);
  }*/

  public Progress progress() {
    int booksRead = Long.valueOf(books.stream().filter(Book::isRead).count()).intValue();
    int booksToRead = books.size() - booksRead;
    int percentageCompleted = booksRead * 100 / books.size();
    int percentageToRead = booksToRead * 100 / books.size();
    return new Progress(percentageCompleted, percentageToRead, 0);
  }

  public List<Book> findBooksByTitle(String code) {
    return findBooksByTitle(code, b -> true);
  }

  public List<Book> findBooksByTitle(String title, BookFilter filter) {
    return books.stream()
        .filter(b -> b.getTitle().toLowerCase().contains(title))
        .filter(b -> filter.apply(b))
        .collect(toList());
  }
}
