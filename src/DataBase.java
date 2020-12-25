
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class DataBase {
    public List<Course> courses;
    public List<Student> students;
    public List<Program> programs;

    public DataBase(){
        courses = new ArrayList<>();
        students = new ArrayList<>();
        programs = new ArrayList<>();
    }

    public void generateCSV() throws IOException {
        File result = new File("./result.csv");
        result.createNewFile();
        PrintWriter pw = new PrintWriter(result);

        pw.print("N° Étudiant,Nom,Prénom,");
        for (Program p : programs) {
            pw.print(p.getID() + " - " + p.getName() + ",");
            for (Bloc b : p.getBlocs()) {
                pw.print(b.getID() + " - " + b.getName() + ",");
                for (Course c : b.getCourses())
                    pw.print(c.getID() + " - " + c.getName() + ",");

            }
        }
        pw.println();

        for (Student s : students) {
            pw.print(s.getId() + ",");
            pw.print(s.getName() + ",");
            pw.print(s.getSurname() + ",");

            for (Program p : programs) {
                double pg = s.getProgramGrade(p);
                pw.print((pg == -1 ? "" : pg) + ",");
                for (Bloc b : p.getBlocs()) {
                    double bg = s.getBlocGrade(b);
                    pw.print((bg == -1 ? "" : bg) + ",");
                    for (Course c : b.getCourses()) {
                        double cg = s.getCourseGrade(c);
                        pw.print((cg == -1 ? "" : cg) + ",");
                    }

                }
            }
        pw.println();

        }
        pw.println();

        pw.print("Note Max,,,");
        for (Program p : programs) {
            pw.print(",");
            for (Bloc b : p.getBlocs()) {
                pw.print(",");
                for (Course c : b.getCourses()) {
                    pw.print(maxGradeByCourse(c) + ",");
                }

            }
        }
        pw.println();

        pw.print("Note Min,,,");
        for (Program p : programs) {
            pw.print(",");
            for (Bloc b : p.getBlocs()) {
                pw.print(",");
                for (Course c : b.getCourses()) {
                    pw.print(minGradeByCourse(c) + ",");
                }

            }
        }

        pw.println();
        pw.print("Note moyenne,,,");
        for (Program p : programs) {
            pw.print(",");
            for (Bloc b : p.getBlocs()) {
                pw.print(",");
                for (Course c : b.getCourses()) {
                    pw.print(averageGradeByCourse(c) + ",");
                }

            }
        }

        pw.println();
        pw.print("Écart-type,,,");
        for (Program p : programs) {
            pw.print(",");
            for (Bloc b : p.getBlocs()) {
                pw.print(",");
                for (Course c : b.getCourses()) {
                    pw.print(ecartGradeByCourse(c) + ",");
                }

            }
        }

        pw.flush();
        pw.close();
    }

    public Course getCoursByID(String id) {
        for (Course c : courses) {
            if (c.getID().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public Program getProgramByID(String id) {
        for (Program p : programs) {
            if (p.getID().equals(id)) {
                return p;
            }
        }
        return null;
    }

    private double minGradeByCourse(Course course) {
        double res = Double.MAX_VALUE;
        for (Student s : students) {
            for (Grade g : s.getGrades()) {
                if (g.getCourseID().equals(course.getID()))
                    if (g.getGrade() < res && g.getGrade() >= 0)
                        res = g.getGrade();

            }
        }
        return res;
    }

    private double maxGradeByCourse(Course course) {
        double res = Double.MIN_VALUE;
        for (Student s : students) {
            for (Grade g : s.getGrades()) {
                if (g.getCourseID().equals(course.getID()))
                    if (g.getGrade() > res && g.getGrade() >= 0)
                        res = g.getGrade();

            }
        }
        return res;
    }

    private double averageGradeByCourse(Course course) {
        double res = 0;
        int count = 0;
        for (Student s : students) {
            for (Grade g : s.getGrades()) {
                if (g.getCourseID().equals(course.getID())){
                    if (g.getGrade() >= 0){
                        res += g.getGrade();
                        count++;
                    }
                }

            }
        }
        return res/count;
    }

    private double ecartGradeByCourse(Course course) {
        double sigma = 0;
        int count = 0;
        for (Student s : students) {
            for (Grade g : s.getGrades()) {
                if (g.getCourseID().equals(course.getID())) {
                    if (g.getGrade() >= 0) {
                        sigma += g.getGrade() * g.getGrade();
                        count++;
                    }
                }

            }
        }
        return sigma / count - Math.pow(averageGradeByCourse(course), 2);
    }

}
