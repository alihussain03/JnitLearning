package bookstoread;

import static java.util.stream.Collectors.groupingBy;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookShelf {

  private final List<Book> books = new ArrayList<>();

  public List<Book> books() {
    // return Collections.emptyList();
    //  return books;
    return Collections.unmodifiableList(books);
  }

  public void add(Book... booksToAdd) {
    Arrays.stream(booksToAdd).forEach(books::add);
  }

  /*public List<String> arrange() {
    return books.stream().sorted().collect(Collectors.toList());
 //   books.sort(Comparator.naturalOrder());
 //   return books;
  //  return null;
  }*/

  public List<Book> arrange() {
    //return books.stream().sorted().collect(Collectors.toList());
    return arrange(Comparator.naturalOrder());
  }

  public List<Book> arrange(Comparator<Book> criteria) {
    return books.stream().sorted(criteria).collect(Collectors.toList());
  }

  public Map<Year, List<Book>> groupByPublicationYear() {
    return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
  }

  public <K> Map<K, List<Book>> groupBy(Function<Book, K> fx) {
    return books
        .stream()
        .collect(groupingBy(fx));
  }
}
