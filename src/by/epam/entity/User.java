package by.epam.entity;

public class User {
    private String email, pass, company, fName, lName;
    private int id;
    private UserType type;

    public User() {
    }

    public User(String email, String pass, int id, UserType type) {
        this.pass = pass;
        this.email = email;
        this.id = id;
        this.type = type;
    }

    public User(String email, String pass, int id, UserType type, String fName, String lName) {
        this.pass = pass;
        this.email = email;
        this.id = id;
        this.type = type;
        this.fName = fName;
        this.lName = lName;
    }

    public User(String email, String pass, int id) {
        this.pass = pass;
        this.email = email;
        this.id = id;
        this.type = UserType.HR;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {

        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public UserType getType() {
        return type;
    }

    public String getCompany() {

        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", id=" + id + "}\n";
    }
}