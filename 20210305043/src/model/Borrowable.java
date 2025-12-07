package model;

// Ödünç verilebilen nesneler için arayüz
public interface Borrowable {

    void borrowTo(Member member);  // bir üyeye ödünç ver
    void takeBack();               // geri al
    boolean isBorrowed();          // şu anda ödünçte mi?
}
