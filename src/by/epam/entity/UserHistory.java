package by.epam.entity;

public class UserHistory {

    private String company;
    private int idCompany;
    private int idHR;
    private int yearEmployed;
    private int yearTerminated;
    private int rating1;
    private int rating2;
    private int rating3;
    private int rating4;
    private int rating5;
    private int hireAgain;

    public UserHistory() {
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setIdHR(int idHR) {
        this.idHR = idHR;
    }

    public void setYearEmployed(int yearEmployed) {
        this.yearEmployed = yearEmployed;
    }

    public void setYearTerminated(int yearTerminated) {
        this.yearTerminated = yearTerminated;
    }

    public void setRating1(int rating1) {
        this.rating1 = rating1;
    }

    public void setRating2(int rating2) {
        this.rating2 = rating2;
    }

    public void setRating3(int rating3) {
        this.rating3 = rating3;
    }

    public void setRating4(int rating4) {
        this.rating4 = rating4;
    }

    public void setRating5(int rating5) {
        this.rating5 = rating5;
    }

    public void setHireAgain(int hireAgain) {
        this.hireAgain = hireAgain;
    }

    public String getCompany() {

        return company;
    }

    public int getIdHR() {
        return idHR;
    }

    public int getYearEmployed() {
        return yearEmployed;
    }

    public int getYearTerminated() {
        return yearTerminated;
    }

    public int getRating1() {
        return rating1;
    }

    public int getRating2() {
        return rating2;
    }

    public int getRating3() {
        return rating3;
    }

    public int getRating4() {
        return rating4;
    }

    public int getRating5() {
        return rating5;
    }

    public String getHireAgain() {
        return (hireAgain == 1) ? "Yes" : "No";
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {

        this.idCompany = idCompany;
    }


    @Override
    public String toString() {
        return "UserHistory{" +
                "company=" + company +
                ", idHR=" + idHR +
                ", yearEmployed=" + yearEmployed +
                ", yearTerminated=" + yearTerminated +
                ", rating1=" + rating1 +
                ", rating2=" + rating2 +
                ", rating3=" + rating3 +
                ", rating4=" + rating4 +
                ", rating5=" + rating5 +
                ", hireAgain=" + hireAgain +
                '}';
    }
}
