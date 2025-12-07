package data;

import model.Book;
import model.Member;

import java.util.HashMap;
import java.util.Map;

// Verileri bellekte (RAM'de) tutan sınıf
public class InMemoryDataStore {

    private static final Repository<Book> bookRepository = new Repository<>();
    private static final Repository<Member> memberRepository = new Repository<>();
    private static final Map<Integer, Book> bookById = new HashMap<>();

    public static Repository<Book> getBookRepository() {
        return bookRepository;
    }

    public static Repository<Member> getMemberRepository() {
        return memberRepository;
    }

    public static Map<Integer, Book> getBookByIdMap() {
        return bookById;
    }
}
