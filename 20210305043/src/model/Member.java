package model;

// Library member, inherits from Person
public class Member extends Person {

    public Member(int id, String name) {
        super(id, name);
    }

    @Override
    public String getInfo() {
        return "Member -> " + super.getInfo();
    }
}
