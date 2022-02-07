package app;

public class PgmProduct {
    String CompanyName;
    int price;
    int year;
    int month;
    int day;
    int year1;
    int month1;
    int day1;
    String comment;
    String subtotaltitle;
    int subtotal;
    String taxtitle;
    int tax;
    String totaltitle;
    int total;

    public PgmProduct(String companyName, int price, int year, int month, int day, int year1, int month1, int day1, String comment, String subtotaltitle, int subtotal, String taxtitle, int tax, String totaltitle, int total) {
        CompanyName = companyName;
        this.price = price;
        this.year = year;
        this.month = month;
        this.day = day;
        this.year1 = year1;
        this.month1 = month1;
        this.day1 = day1;
        this.comment = comment;
        this.subtotaltitle = subtotaltitle;
        this.subtotal = subtotal;
        this.taxtitle = taxtitle;
        this.tax = tax;
        this.totaltitle = totaltitle;
        this.total = total;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear1() {
        return year1;
    }

    public void setYear1(int year1) {
        this.year1 = year1;
    }

    public int getMonth1() {
        return month1;
    }

    public void setMonth1(int month1) {
        this.month1 = month1;
    }

    public int getDay1() {
        return day1;
    }

    public void setDay1(int day1) {
        this.day1 = day1;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSubtotaltitle() {
        return subtotaltitle;
    }

    public void setSubtotaltitle(String subtotaltitle) {
        this.subtotaltitle = subtotaltitle;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public String getTaxtitle() {
        return taxtitle;
    }

    public void setTaxtitle(String taxtitle) {
        this.taxtitle = taxtitle;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getTotaltitle() {
        return totaltitle;
    }

    public void setTotaltitle(String totaltitle) {
        this.totaltitle = totaltitle;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PgmProduct{" +
                "CompanyName='" + CompanyName + '\'' +
                ", price=" + price +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", year1=" + year1 +
                ", month1=" + month1 +
                ", day1=" + day1 +
                ", comment='" + comment + '\'' +
                ", subtotaltitle='" + subtotaltitle + '\'' +
                ", subtotal=" + subtotal +
                ", taxtitle='" + taxtitle + '\'' +
                ", tax=" + tax +
                ", totaltitle='" + totaltitle + '\'' +
                ", total=" + total +
                '}';
    }
}