package business;

import data.InMemoryDataStore;
import data.Repository;
import model.Book;
import model.Member;

import java.util.List;
import java.util.Map;

// İş mantığını barındıran sınıf
public class LibraryService {

    private final Repository<Book> bookRepo = InMemoryDataStore.getBookRepository();
    private final Repository<Member> memberRepo = InMemoryDataStore.getMemberRepository();
    private final Map<Integer, Book> bookByIdMap = InMemoryDataStore.getBookByIdMap();

    // Yeni kitap ekle
    public void addBook(int id, String title, String author) {
        Book book = new Book(id, title, author);
        bookRepo.add(book);
        bookByIdMap.put(id, book); // Map ile ID'den hızlı erişim
    }

    // Yeni üye ekle
    public void addMember(int id, String name) {
        Member member = new Member(id, name);
        memberRepo.add(member);
    }

    public List<Book> getAllBooks() {
        return bookRepo.getAll();
    }

    public List<Member> getAllMembers() {
        return memberRepo.getAll();
    }

    // Kitabı bir üyeye ödünç ver
    public void borrowBook(int bookId, int memberId) {
        Book book = bookByIdMap.get(bookId);
        if (book == null) {
            System.out.println("Kitap bulunamadı.");
            return;
        }

        // Lambda ile üye arama
        Member member = memberRepo
                .find(m -> m.getId() == memberId)
                .orElse(null);

        if (member == null) {
            System.out.println("Üye bulunamadı.");
            return;
        }

        // Book, Borrowable arayüzünü implement ettiği için
        // polimorfizm ile borrowTo çağrılır
        book.borrowTo(member);
    }

    // Kitabı geri al
    public void returnBook(int bookId) {
        Book book = bookByIdMap.get(bookId);
        if (book == null) {
            System.out.println("Kitap bulunamadı.");
            return;
        }
        book.takeBack();
    }

    // Ödev için basit generic metod örneği
    public <T> void printAll(List<T> list) {
        list.forEach(System.out::println); // lambda + generic method
    }
}
