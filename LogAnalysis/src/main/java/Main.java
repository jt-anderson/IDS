
import org.apache.commons.cli.*;

import java.util.Set;

public class Main {
    public static void main(String[] args){
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            parseCommand(args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void parseCommand(String[] args) throws Exception{
        LogParser logParser = new LogParser();
        Options options = generateOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        cmd = parser.parse(options, args);
        if(cmd.hasOption("l")) listTemplates();
        if(cmd.hasOption("h")) printHelpDialog(options);
        else if (cmd.hasOption("t")){
            if(logParser.templates.containsKey(cmd.getOptionValue("t"))){
                logParser.parse(cmd.getArgList().get(0), cmd.getOptionValue("t"));
            }
            else {
                listTemplates();
            }
        }
        else printHelpDialog(options);
    }

    private static void printHelpDialog(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("LogAnalysis -t <template> [file]", options);
    }

    private static void listTemplates() throws Exception{
        Options options = new Options();
        LogParser logParser = new LogParser();
        Set<String> keys = logParser.templates.keySet();
        for (String key: keys) {
            options.addOption(key, false, logParser.templates.get(key).pattern);
        }
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("LogAnalysis -t <template> [file]", options);
    }

    private static Options generateOptions(){
        Options options = new Options();
        options.addOption("h", false, "Print this message.");
        options.addOption("l", false, "List available log templates.");
        options.addOption(Option.builder("t").hasArg(true).argName("Log Template").desc("Specify the template in config file that will be used to parse the log").build());
        return options;
    }
}
