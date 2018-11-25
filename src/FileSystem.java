import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileSystem {
    private Directory root;
    private Map<String, AbstractFile> filesMap;

    public FileSystem(){
        root = new Directory("root", null);
        filesMap = new HashMap<String, AbstractFile>();
        filesMap.put("root", root);
    }

    public void run(){
        System.out.println("Welcome to Dror's File System!\n" +
                "Please choose an action (1-5):\n" +
                "1 - Add New File\n" +
                "2 - Add new Directory\n" +
                "3 - Delete File/Directory\n" +
                "4 - Show Files in system\n" +
                "5 - Exit\n" +
                "*For help at any point, Press 0");
        boolean exit = false;
        Scanner reader = new Scanner(System.in);
        int n = 0;
        String parentDirName;

        while (!exit){
            // Get user input number for performing action (0-5)
            String input = reader.nextLine();
            while (!isValidInputNumber(input)){
                System.out.println("Please enter a valid number (0-5)");
                input = reader.nextLine();
            }
            n = Integer.parseInt(input);
            // Switch cases according to user's chosen action (0-5)
            switch(n){
                case 0: // Show actions info
                    System.out.println("Please choose an action (1-5):\n" +
                            "1 - Add New File\n" +
                            "2 - Add new Directory\n" +
                            "3 - Delete File/Directory\n" +
                            "4 - Show Files in system\n" +
                            "5 - Exit");
                    break;
                case 1: // Add new File
                    String fileName;
                    Long fileSize;
                    // Get parent directory name
                    System.out.println("Enter Parent Directory Name");
                    parentDirName = reader.nextLine();
                    while (!filesMap.containsKey(parentDirName)){
                        System.out.println("Directory doesn't exist! Please Enter a valid parent directory name");
                        parentDirName = reader.nextLine();
                    }
                    // Get file name
                    System.out.println("Enter File Name");
                    fileName = reader.nextLine();
                    while (true) {
                        if((fileName.length() == 0) || (fileName.length() > 32)){
                            System.out.println("Please enter a valid name (up to 32 characters long)");
                        } else if (filesMap.containsKey(fileName))
                            System.out.println("A file with this name already exists! Please choose another");
                        else {
                            break;
                        }
                        fileName = reader.nextLine();
                    }
                    // Get file size
                    System.out.println("Enter File Size");
                    input = reader.nextLine();
                    while (!tryParseLong(input)){
                        System.out.println("Please enter a valid size (positive long integer)");
                        input = reader.nextLine();
                    }
                    fileSize = Long.parseLong(input);
                    // Add file to system
                    addFile(parentDirName, fileName, fileSize);
                    break;
                case 2: // Add Directory
                    // Get parent directory name
                    System.out.println("Enter Parent Directory Name");
                    parentDirName = reader.nextLine();
                    while (true) {
                        if((parentDirName.length() == 0) || (parentDirName.length() > 32)){
                            System.out.println("Please enter a valid name (up to 32 characters long)");
                        } else if (!filesMap.containsKey(parentDirName))
                            System.out.println("Directory does not exists! Please choose another");
                        else {
                            break;
                        }
                        parentDirName = reader.nextLine();
                    }
                    // Get new Directory name
                    String dirName;
                    System.out.println("Enter Directory Name");
                    dirName = reader.nextLine();
                    while (true) {
                        if((dirName.length() == 0) || (dirName.length() > 32)){
                            System.out.println("Please enter a valid name (up to 32 characters long)");
                        } else if (filesMap.containsKey(dirName))
                            System.out.println("Directory exists! Please choose another");
                        else {
                            break;
                        }
                        dirName = reader.nextLine();
                    }
                    // Add new directory to file system
                    addDir(parentDirName, dirName);
                    break;
                case 3: // Delete file/directory
                    // Get the file/directory name
                    System.out.println("Enter File Name you wish to delete");
                    fileName = reader.nextLine();
                    while (true) {
                        if((fileName.length() == 0) || (fileName.length() > 32)){
                            System.out.println("Please enter a valid name (up to 32 characters long)");
                        } else if (!filesMap.containsKey(fileName)) {
                            System.out.println("File does not exists! Canceling action");
                            break;
                        } else {
                            break;
                        }
                        fileName = reader.nextLine();
                    }
                    // Delete the file/directory by its name
                    delete(fileName);
                    break;
                case 4: // show all the files in the system
                    showFileSystem();
                    break;
                case 5: // Exit
                    exit = true;
                    break;
            }
        }
        reader.close();
    }

    boolean tryParseLong(String value){
        try {
            Long.parseLong(value);
            return true;
        }catch (NumberFormatException e ){
            return false;
        }
    }

    boolean tryParseInteger(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean isValidInputNumber(String value){
        if(tryParseInteger(value)){
            int n = Integer.parseInt(value);
            if(n >= 0 && n <= 5)
                return true;
        }
        return false;
    }

    // Adds new File under the Directory branch
    public void addFile(String parentDirName, String fileName, Long fileSize){
        // get the parent dir
        Directory parentDir = (Directory) filesMap.get(parentDirName);
        // create the file
        File newFile = new File(fileName, parentDir, fileSize);
        // add file to directory filesArray & to filesMap
        parentDir.addFileToDir(newFile);
        filesMap.put(fileName, newFile);
    }

    // Adds new Directory under the parent Directory
    public void addDir(String parentDirName, String dirName){
        // get the parent dir
        Directory parentDir = (Directory) filesMap.get(parentDirName);
        // create the new directory
        Directory newDir = new Directory(dirName, parentDir);
        // Add to filesArray & to mapper
        parentDir.addDirToDir(newDir);
        filesMap.put(dirName, newDir);
    }

    // Deletes the Directory or the File with this name
    public void delete(String fileName){
        Object obj = filesMap.get(fileName);
        String className = obj.getClass().getSimpleName();
        if(className.equals("Directory")){
            ((Directory)obj).delete(filesMap);
        }else {
            ((File)obj).delete(filesMap);
        }
    }

    // Displays all files & directories with their hierarchical structure (for file display
    // all file properties and for Directory all its properties
    public void showFileSystem(){
        System.out.println(root.getInfo(0));
    }
}
