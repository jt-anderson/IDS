import java.util.Arrays;

public class Template {
    public String type;
    public String pattern;
    public int timestampID;
    public int subjectID;
    public int actionID;
    public int objectID;

    public Template(String type, String pattern, int timestampID, int subjectID, int actionID, int objectID){
        this.type = type;
        this.pattern = pattern;
        this.timestampID = timestampID;
        this.subjectID = subjectID;
        this.actionID = actionID;
        this.objectID = objectID;
    }
}
