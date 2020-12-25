
import java.util.List;
import java.util.ArrayList;

public class Bloc {
    protected String id;
    protected String name;
    protected int credits;
    protected List<Course> courses;

    public Bloc() {
        courses = new ArrayList<>();
    }

    public Bloc(String id, String name, String credits) {
        this.id = id;
        this.name = name;
        this.credits = Integer.valueOf(credits);
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
