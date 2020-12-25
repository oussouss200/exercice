import java.util.List;

public class Student {
    private String id;
    private String name;
    private String surname;
    private Program program;
    private List<Grade> grades; 

    public Student(String id, String name, String surname, Program program, List<Grade> grades) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.program = program;
        this.grades = grades;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public double getCourseGrade(Course course){
        for(Grade g:grades)
            if(g.getCourseID().equals(course.getID()))
                return g.getGrade();
        return -1;
    }

    public double getBlocGrade(Bloc b){
        if(b instanceof SimpleBloc)
            return getCourseGrade(((SimpleBloc) b).getCourses().get(0));
        if(b instanceof OptionBloc){
            OptionBloc ob = (OptionBloc) b;
            double res = -1;
            for(Course c : ob.getCourses())
                if(getCourseGrade(c)>res)
                    res = getCourseGrade(c);
            return res;
        }

        CompositeBloc cb = (CompositeBloc) b;
        
        double res = 0;
        boolean isABI = true;
        for(Course c : cb.getCourses()){
            double grade = getCourseGrade(c);
            if(grade>=0){
                res += c.getCredits() * grade;
                isABI = false;
            }
        }

        return isABI?-1:res;
    }

    public double getProgramGrade(Program program){
        double res = 0;
        boolean isABI = true;

        for(Bloc b : program.getBlocs()){
            double grade = getBlocGrade(b);
            if(grade>=0){
                res += b.getCredits()* grade;
                isABI = false;
            }
        }
        return isABI?-1:res;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    

}
