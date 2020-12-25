
public class Grade {
    private String val;
    private String courseID;

    public Grade(String val, String coursID){
        this.val = val;
        this.courseID = coursID;
    }

    public double getGrade(){
        if(val.equals("ABI")) return -1;
        return Double.parseDouble(val);
    }

    public String getCourseID() {
        return courseID;
    }
    public String getVal() {
        return val;
    }

}
