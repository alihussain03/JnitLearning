package bookstoread;

import java.time.LocalDate;

public class BookPublishedYearFilter implements BookFilter {

  private LocalDate startDate;

  public static BookFilter After(int year) {
    BookPublishedYearFilter filter = new BookPublishedYearFilter();
    filter.startDate = LocalDate.of(year, 12, 31);
    return filter;
  }

  /* before need to implement

  public static BookFilter before(int year) {
    BookPublishedYearFilter filter = new BookPublishedYearFilter();
    filter.startDate = LocalDate.of(year, 12, 31);
    return filter;
  }*/

  @Override
  public boolean apply(Book b) {
    return b.getPublishedOn().isAfter(startDate);
  }
}
