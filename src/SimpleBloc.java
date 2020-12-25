public class SimpleBloc extends Bloc{
   


    public SimpleBloc(Course course){
        super();
        this.courses.add(course);
        id = course.getID();
        name = course.getName();
        credits = course.getCredits();
    }
} 

