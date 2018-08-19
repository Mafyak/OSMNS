package by.epam.entity;

public class Page {

    private String page = null;
    private boolean toRedirect = false;

    public Page(String page, boolean toRedirect) {
        this.page = page;
        this.toRedirect = toRedirect;
    }

    public Page(String page) {
        this.page = page;
    }

    public Page() {
    }

    public String getPage() {
//        if (this.toRedirect)
//            return "/OSMNS" + page;
        return page;
    }

    public boolean isToRedirect() {
        return toRedirect;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setToRedirect(boolean toRedirect) {
        this.toRedirect = toRedirect;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page='" + page + '\'' +
                ", toRedirect=" + toRedirect +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page1 = (Page) o;

        if (toRedirect != page1.toRedirect) return false;
        return page != null ? page.equals(page1.page) : page1.page == null;
    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (toRedirect ? 1 : 0);
        return result;
    }
}
