import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class XMLTest {
    public static void main(String[] args) {
        try {
            File file = new File("./remp_urg.xml");
            DataBase db = parse(file);
            db.generateCSV();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataBase parse(File f) {
        Element root = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();
            root = doc.getDocumentElement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        DataBase db = new DataBase();
        for (Element e : getChildren(root, "course")) {
            Element id = getChildren(e, "identifier").get(0);
            Element name = getChildren(e, "name").get(0);
            Element credits = getChildren(e, "credits").get(0);

            db.courses.add(new Course(id.getTextContent(), name.getTextContent(), credits.getTextContent()));
        }
        for (Element e : getChildren(root, "program")) {
            String id = getChildren(e, "identifier").get(0).getTextContent();
            String name = getChildren(e, "name").get(0).getTextContent();

            List<Bloc> blocs = new ArrayList<>();

            List<Element> simpleBlocsElement = getChildren(e, "item");
            List<Element> optionBlocsElement = getChildren(e, "option");
            List<Element> compositeBlocsElement = getChildren(e, "composite");
            for (Element sb : simpleBlocsElement) {
                blocs.add(new SimpleBloc(db.getCoursByID(sb.getTextContent())));
            }

            for (Element ob : optionBlocsElement) {
                String obID = getChildren(ob, "identifier").get(0).getTextContent();
                String obName = getChildren(ob, "name").get(0).getTextContent();
                List<Element> obItems = getChildren(ob, "item");
                List<Course> courses = new ArrayList<>();
                for (Element item : obItems) {
                    courses.add(db.getCoursByID(item.getTextContent()));
                }
                blocs.add(new OptionBloc(obID, obName, courses));
            }

            for (Element cb : compositeBlocsElement) {
                String cbID = getChildren(cb, "identifier").get(0).getTextContent();
                String cbName = getChildren(cb, "name").get(0).getTextContent();
                List<Element> obItems = getChildren(cb, "item");
                List<Course> courses = new ArrayList<>();
                for (Element item : obItems) {
                    courses.add(db.getCoursByID(item.getTextContent()));
                }
                blocs.add(new CompositeBloc(cbID, cbName, courses));
            }

            db.programs.add(new Program(id, name, blocs));
        }

        for (Element e : getChildren(root, "student")) {
            String id = getChildren(e, "identifier").get(0).getTextContent();
            String name = getChildren(e, "name").get(0).getTextContent();
            String surname = getChildren(e, "surname").get(0).getTextContent();
            List<Grade> grades = new ArrayList<>();

            List<Element> program = getChildren(e, "program");

            List<Element> gradesElement = getChildren(e, "grade");
            for (Element g : gradesElement) {
                String item = getChildren(g, "item").get(0).getTextContent();
                String value = getChildren(g, "value").get(0).getTextContent();
                grades.add(new Grade(value, item));
            }

            db.students.add(new Student(id, name, surname,
                    program.isEmpty() ? null : db.getProgramByID(program.get(0).getTextContent()), grades));
        }

        return db;
    }

    // Extrait la liste des fils de l'élément item dont le tag est name
    private static List<Element> getChildren(Element item, String name) {
        NodeList nodeList = item.getChildNodes();
        List<Element> children = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeList.item(i); // cas particulier pour nous où tous
                if (element.getTagName().equals(name)) {
                    children.add(element);
                }
            }
        }
        return children;
    }
}
