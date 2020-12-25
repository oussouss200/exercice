import java.util.List;

public class CompositeBloc extends Bloc {

    public CompositeBloc(String id, String name, List<Course> courses){
        super();
        this.id = id;
        this.name = name;
        this.courses = courses;
        for(Course c:courses)
            this.credits += c.getCredits();
    }
} 
