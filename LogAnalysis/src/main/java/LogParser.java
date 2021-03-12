import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser {

    public HashMap<String,Template> templates = new HashMap<>();

    public LogParser() throws Exception{
        Consumer<? super MatchResult> matchResult = (Consumer<MatchResult>) matchResult1 -> templates.put(matchResult1.group(1),new Template(
                matchResult1.group(2),
                matchResult1.group(3),
                Integer.parseInt(matchResult1.group(4)),
                Integer.parseInt(matchResult1.group(5)),
                Integer.parseInt(matchResult1.group(6)),
                Integer.parseInt(matchResult1.group(7))));
        Scanner sc = new Scanner(new File("./src/main/resources/config/LogParser.conf"));
        Pattern p = Pattern.compile("NAME=(.+)\\sTYPE=(.+)\\sPATTERN=(.+)\\sTIMESTAMP=(.+)\\sSUBJECT=(.+)\\sACTION=(.+)\\sOBJECT=(.+)");
        sc.findAll(p).forEach(matchResult);
        sc.close();
    }
    public List<Event> parse(String logPath, String templateName) throws Exception{
        Pattern p = Pattern.compile(this.templates.get(templateName).pattern);
        Scanner sc = new Scanner(new File(logPath)).useDelimiter(p);
        String fileName = "./src/main/resources/parsedLogs/" + this.templates.get(templateName).type + "/"+templateName+".csv";
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(fileName,false));
        Function<? super MatchResult,Event> logEvent = (Function<MatchResult, Event>) matchResult -> {
            Event e = new Event(
                    matchResult.group(templates.get(templateName).timestampID),
                    matchResult.group(templates.get(templateName).subjectID),
                    matchResult.group(templates.get(templateName).actionID),
                    matchResult.group(templates.get(templateName).objectID)
            );
            try {
                csvWriter.append(e.timestamp).append(',').append(e.subject).append(',').append(e.action).append(',').append(e.object).append("\n");
            } catch (IOException ioException) {
                System.out.println(ioException);
            }
            return e;
        };
        List<Event> e = sc.findAll(p).map(logEvent).collect(Collectors.toList());
        csvWriter.flush();
        csvWriter.close();
        sc.close();
        return e;
    }
}
