package by.epam.entity;

import java.util.ArrayList;
import java.util.List;

public class User extends Entity {
    private String email;
    private String pass;
    private Company company;
    private String fName;
    private String mName;
    private String lName;
    private int SSN;
    private int id;
    private UserType type;
    private List<UserHistory> history;

    public User() {
        history = new ArrayList<>();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {

        this.mName = mName;
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

    @Override
    public String getName() {
        return fName + " " + mName + " " + lName;
    }

    public UserType getType() {
        return type;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
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

    @Override
    public void setName(String name) {
        switch (name.split(" ").length) {
            case (1):
                fName = name.split(" ")[0];
                lName = name.split(" ")[1];
                break;
            case (2):
                fName = name.split(" ")[0];
                mName = name.split(" ")[1];
                lName = name.split(" ")[2];
                break;
            default:
                fName = name;
        }
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public void addHistory(UserHistory history) {
        this.history.add(history);
    }

    public List<UserHistory> getHistory() {
        return history;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public int getSSN() {
        return SSN;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", company='" + company + '\'' +
                ", fName='" + fName + '\'' +
                ", mName='" + mName + '\'' +
                ", lName='" + lName + '\'' +
                ", SSN=" + SSN +
                ", id=" + id +
                ", type=" + type +
                ", history=" + history +
                '}';
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + pass.hashCode();
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (fName != null ? fName.hashCode() : 0);
        result = 31 * result + (lName != null ? lName.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!email.equals(user.email)) return false;
        if (!pass.equals(user.pass)) return false;
        if (company != null ? !company.equals(user.company) : user.company != null) return false;
        if (fName != null ? !fName.equals(user.fName) : user.fName != null) return false;
        if (lName != null ? !lName.equals(user.lName) : user.lName != null) return false;
        return type == user.type;
    }
}