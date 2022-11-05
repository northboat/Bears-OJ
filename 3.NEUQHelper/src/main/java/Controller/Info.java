package Controller;

public class Info {
    private String nums;
    private String gender;
    private String major;

    public Info(String nums, String major, String gender) {
        this.nums = nums;
        this.gender = gender;
        this.major = major;
    }

    public String getNums() {
        return nums;
    }

    public String getMajor() {
        return major;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return getNums()+"\t"+getMajor()+"\t"+getGender();
    }
}
