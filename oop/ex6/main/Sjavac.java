package oop.ex6.main;


import oop.ex6.main.compilationexceptions.CompilationException;

import java.io.*;
import java.util.ArrayList;

/**
 * Main class for running the project
 *
 * @author lioraryepaz, shahar.mazia
 */

public class Sjavac {

    private final static int NUM_OF_ARGS = 1;
    private final static String WRONG_NUM_OF_ARGS_MESSAGE = "Wrong number of Args";
    private final static String DIR_ERR = "Source dir is not a file";
    private static final String SJAVA_REGEX = ".*\\.sjava";
    private static final String WRONG_FILE_ERR = "Wrong File Type";
    private static final String NAME_DELIMITER = "\\.";

    /**
     * main method
     *
     * @param args file dir
     */
    public static void main(String[] args) {
        final String FILE_ERROR = "2";
        final String COMPILATION_ERROR = "1";
        final String COMPILED_SUCCESSFULLY = "0";
        final String TAB = "    ";

        ArrayList<String> fileLines;
        try {
            fileLines = generateFile(args);
        } catch (Exception e) {
            System.out.println(FILE_ERROR);
            System.err.println(e.getMessage());
            return;
        }
        try {
            new MainScope(fileLines);
            System.out.println(COMPILED_SUCCESSFULLY);
        } catch (CompilationException e) {
            System.out.println(COMPILATION_ERROR);
            String[] errorMessage = e.getClass().getName().split(NAME_DELIMITER);
            System.err.print(errorMessage[errorMessage.length - 1] + TAB + e.getMessage());
        }

    }

    /**
     * read the file stream and create a line list out of it
     *
     * @param args arguments of the run received from main
     * @return list of file lines
     * @throws IllegalArgumentException exception for argument other than file directory
     * @throws IOException              exception for file problems
     */
    static private ArrayList<String> generateFile(String[] args) throws IllegalArgumentException, IOException {
        if (args.length != NUM_OF_ARGS) {
            throw new IllegalArgumentException(WRONG_NUM_OF_ARGS_MESSAGE);
        }
        File file = new File(args[0]);
        if (!file.isFile()) {
            throw new IllegalArgumentException(DIR_ERR);
        }
        if (!file.getName().matches(SJAVA_REGEX)) {
            throw new IOException(WRONG_FILE_ERR);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        ArrayList<String> fileLines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            fileLines.add(line);
            line = bufferedReader.readLine();
        }
        return fileLines;
    }
}
