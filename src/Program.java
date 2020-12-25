import java.util.List;

public class Program {
    private String id;
    private String name;
    private List<Bloc> blocs;

    public Program(String id, String name, List<Bloc> blocs) {
        this.id = id;
        this.name = name;
        this.blocs = blocs;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Bloc> getBlocs() {
        return blocs;
    }
} 
