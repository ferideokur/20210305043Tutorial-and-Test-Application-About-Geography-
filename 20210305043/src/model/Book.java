package model;

// Kitap sınıfı, ödünç verilebilir (Borrowable)
public class Book implements Borrowable {

    private int id;
    private String title;
    private String author;
    private boolean borrowed;
    private Member borrowedBy;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowed = false;
        this.borrowedBy = null;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    // Borrowable metotları

    @Override
    public void borrowTo(Member member) {
        if (!borrowed) {
            borrowed = true;
            borrowedBy = member;
            System.out.println(title + " ödünç verildi: " + member.getName());
        } else {
            System.out.println(title + " zaten ödünçte.");
        }
    }

    @Override
    public void takeBack() {
        if (borrowed) {
            System.out.println(title + " iade edildi.");
            borrowed = false;
            borrowedBy = null;
        } else {
            System.out.println(title + " zaten kütüphanede.");
        }
    }

    @Override
    public boolean isBorrowed() {
        return borrowed;
    }

    @Override
    public String toString() {
        return "Book{id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", borrowed=" + borrowed +
                '}';
    }
}
