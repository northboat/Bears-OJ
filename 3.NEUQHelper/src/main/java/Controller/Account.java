package Controller;

public class Account{
    private String table;
    private String nums;
    private String password;

    public Account(String table, String nums, String password) {
        this.table = table;
        this.nums = nums;
        this.password = password;
    }

    public Account(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
