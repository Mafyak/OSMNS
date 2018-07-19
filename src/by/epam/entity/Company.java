package by.epam.entity;

public class Company {
    private String name;
    private String niche;
    private String location;
    private int headcount;
    private int companyOfficialId;
    private int companyInnerId;

    public Company(){}

    public Company(String name, String niche, String location, int headcount) {
        this.name = name;
        this.niche = niche;
        this.location = location;
        this.headcount = headcount;
    }

    public Company(String name) {
        this.name = name;
        this.niche = "Unknown";
        this.location = "Unknown";
        this.headcount = 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNiche(String niche) {
        this.niche = niche;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public String getName() {

        return name;
    }

    public String getNiche() {
        return niche;
    }

    public String getLocation() {
        return location;
    }

    public int getHeadcount() {
        return headcount;
    }

    public int getCompanyOfficialId() {
        return companyOfficialId;
    }

    public int getCompanyInnerId() {
        return companyInnerId;
    }

    public void setCompanyOfficialId(int companyOfficialId) {
        this.companyOfficialId = companyOfficialId;
    }

    public void setCompanyInnerId(int companyInnerId) {
        this.companyInnerId = companyInnerId;
    }
}
