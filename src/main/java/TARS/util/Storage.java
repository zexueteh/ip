package TARS.util;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

import java.io.IOException;
import java.io.FileNotFoundException;


public class Storage {
    private static Storage instance = null;
    private static File dataFile;
    private static String dataFilePath;

    /**
     * On initialisation, check if data directory and file exists.
     * If data directory or file do not exist, they are created.
     * @param dataFolderPath
     * @param dataFilePath
     * @throws IOException
     * @throws SecurityException
     */
    private Storage(String dataFolderPath, String dataFilePath) throws TARSStorageFilePathException {
        File dataFolder = new File(dataFolderPath);
        Storage.dataFile = new File(dataFilePath);
        Storage.dataFilePath = dataFilePath;
        try {
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
                dataFolder = new File(dataFolderPath);
            }
            if (!dataFile.exists()) {
                dataFile.createNewFile();
                dataFile = new File(dataFilePath);
            }
        } catch (SecurityException e) {
            throw new TARSStorageFilePathException("SecurityException: " + e.getMessage());
        } catch (IOException e) {
            throw new TARSStorageFilePathException("IOException: " + e.getMessage());
        }
    }

    /**
     * Loads task data from file path, processes each line and appends StringBuilder
     * @return list of strings containing raw Task data
     * @throws TARSStorageOperationException if data file is missing/corrupted
     */
    public ArrayList<String> load() throws TARSStorageOperationException {
        ArrayList<String> rawData = new ArrayList<String>();

        try {
            Scanner fileScanner = new Scanner(dataFile);
            while (fileScanner.hasNextLine()) {
                rawData.add(fileScanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            throw new TARSStorageOperationException("FileNotFoundException: " + e.getMessage());
        }

        return rawData;
    }

    public static Storage getInstance(String dataFolderPath, String dataFilepath) throws TARSStorageFilePathException {
        if (instance == null) {
            instance = new Storage(dataFolderPath, dataFilepath);
        }
        return instance;
    }

    public static void write(String data) throws TARSStorageOperationException {
        try {
            FileWriter fileWriter = new FileWriter(dataFilePath);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            throw new TARSStorageOperationException(e.getMessage());
        }
    }

    /* Use of Nested Exception classes
     */

    /**
     * Signals errors in initialising dataFile due to issues with the File Path
     */
    public static class TARSStorageFilePathException extends Exception {
        public TARSStorageFilePathException(String message) {
            super("File Path Error: " + message);
        }
    }

    /**
     * Signals that some error has occurred while trying to read/write data from dataFile
     */
    public static class TARSStorageOperationException extends Exception {
        public TARSStorageOperationException(String message) {
            super("Read Write Error: " + message);
        }
    }

}
