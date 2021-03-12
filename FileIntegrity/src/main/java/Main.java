import org.apache.commons.cli.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args){
        try{
            parseCommand(args);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private static void parseCommand(String[] args) throws Exception{
        Options options = generateOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        cmd = parser.parse(options, args);
        if(cmd.hasOption("h")){
            printHelpDialog(options);
        }
        else if (cmd.hasOption("i")){
            FileHashMaker.writeCSVHashTable(
                    cmd.hasOption("o") ? cmd.getOptionValue("o") : "./src/main/resources/data/HashTables/table.csv",
                    getFiles(cmd.getArgList(), cmd.hasOption("l") ? cmd.getOptionValue("l") : null),
                    cmd.hasOption("r")
            );
        }
        else {
            printHelpDialog(options);
        }
    }

    private static void printHelpDialog(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("fileIntegrity [OPTIONS] [file [file2 [file3] ...]]", options);
    }

    private static Options generateOptions(){
        Options options = new Options();
        options.addOption("h", false, "Print this message.");
        options.addOption("i", false, "Initialize a hash table CSV with specified files.");
        options.addOption("r",false,"Recursively hash all files in directory and subdirectories");
        options.addOption(Option.builder("l").hasArg(true).argName("File Path").desc("Input a list of files to use").build());
        options.addOption(Option.builder("o").hasArg(true).argName("String").desc("Specify name of Hash Table CSV").build());
        options.addOption(Option.builder("c").hasArg(false).desc("Check integrity of specified hash tables files").build());
        return options;
    }

    private static Set<Path> getFiles(List<String> pathArgs, String pathList) throws Exception{
        Set<Path> paths = new HashSet<>();
        for (String path : pathArgs) paths.add(Paths.get(path));
        if(pathList != null){
            Scanner sc = new Scanner(new File(pathList));
            while (sc.hasNextLine()) paths.add(Paths.get(sc.nextLine()));
            sc.close();
        }
        return paths;
    }
}




