package bookstoread;

public class MockedFilter implements BookFilter{
  boolean returnValue;
  boolean invoked;

  MockedFilter(boolean returnValue){
    this.returnValue=returnValue;
  }
  @Override
  public boolean apply(Book b) {
    invoked= true;
    return returnValue;
  }
}
