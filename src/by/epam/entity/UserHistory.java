package by.epam.entity;

public class UserHistory {

    private String company;
    private int ratingID;
    private int idCompany;
    private int idOfficialCompany;
    private int idHR;
    private int idEmployee;
    private int yearEmployed;
    private int yearTerminated;
    private int rating1;
    private int rating2;
    private int rating3;
    private int rating4;
    private int rating5;
    private int hireAgain;
    private int confirmed;

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

    public int getHireAgain() {
        return hireAgain;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getRatingID() {
        return ratingID;
    }

    public void setRatingID(int ratingID) {
        this.ratingID = ratingID;
    }

    public int getIdOfficialCompany() {
        return idOfficialCompany;
    }

    public void setIdOfficialCompany(int idOfficialCompany) {
        this.idOfficialCompany = idOfficialCompany;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHistory that = (UserHistory) o;

        if (ratingID != that.ratingID) return false;
        if (idCompany != that.idCompany) return false;
        if (idOfficialCompany != that.idOfficialCompany) return false;
        if (idHR != that.idHR) return false;
        if (idEmployee != that.idEmployee) return false;
        if (yearEmployed != that.yearEmployed) return false;
        if (yearTerminated != that.yearTerminated) return false;
        if (rating1 != that.rating1) return false;
        if (rating2 != that.rating2) return false;
        if (rating3 != that.rating3) return false;
        if (rating4 != that.rating4) return false;
        if (rating5 != that.rating5) return false;
        if (hireAgain != that.hireAgain) return false;
        if (confirmed != that.confirmed) return false;
        return company != null ? company.equals(that.company) : that.company == null;
    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + ratingID;
        result = 31 * result + idCompany;
        result = 31 * result + idOfficialCompany;
        result = 31 * result + idHR;
        result = 31 * result + idEmployee;
        result = 31 * result + yearEmployed;
        result = 31 * result + yearTerminated;
        result = 31 * result + rating1;
        result = 31 * result + rating2;
        result = 31 * result + rating3;
        result = 31 * result + rating4;
        result = 31 * result + rating5;
        result = 31 * result + hireAgain;
        result = 31 * result + confirmed;
        return result;
    }
}
