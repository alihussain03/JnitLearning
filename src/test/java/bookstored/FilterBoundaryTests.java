package bookstored;

import static org.assertj.core.api.Assertions.assertThat;

import bookstoread.BookFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public interface FilterBoundaryTests {

  BookFilter get();

  @Test
  @DisplayName("should not fail for null book.")
  default void nullTest() {
    assertThat(get().apply(null)).isFalse();
  }
}
