public class Course {
    private String id;
    private String name;
    private int credits;

    public Course(String id, String name, String credits) {
        this.id = id;
        this.name = name;
        this.credits = Integer.valueOf(credits);
    }

    public String getID() {
        return id;
    }

    public int getCredits() {
        return credits;
    }

    public String getName() {
        return name;
    }
}
