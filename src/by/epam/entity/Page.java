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
}
