import java.util.List;

public class OptionBloc extends Bloc{

    public OptionBloc(String id, String name, List<Course> courses){
        super();
        this.courses = courses;
        this.id = id;
        this.name = name;
        this.credits = courses.get(0).getCredits();
    }
}
