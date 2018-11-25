import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileSystem {
    private Directory root;
    private Map<String, AbstractFile> filesMap;

    public FileSystem(){
        root = new Directory("root", null);
        filesMap = new HashMap<String, AbstractFile>();
        initSystem(root);
    }

    private void initSystem(Directory dir) {
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

        while (!exit){
            int n = reader.nextInt();

            String parentDirName = "";
            String input = "";
            boolean validName = false;

            switch(n){
                case 0:
                    System.out.println("Please choose an action (1-5):\n" +
                            "1 - Add New File\n" +
                            "2 - Add new Directory\n" +
                            "3 - Delete File/Directory\n" +
                            "4 - Show Files in system\n" +
                            "5 - Exit");
                    break;
                case 1:
                    String fileName;
                    Long fileSize;

                    System.out.println("Enter Parent Directory Name");
                    parentDirName = reader.nextLine();
                    while (!filesMap.containsKey(parentDirName)){
                        System.out.println("Directory doesn't exist! Please Enter a valid parent directory name");
                        parentDirName = reader.nextLine();
                    }

                    System.out.println("Enter File Name");
                    fileName = reader.nextLine();
                    while (validName) {
                        if((fileName.length() == 0) || (fileName.length() > 32)){
                            System.out.println("Please enter a valid name (up to 32 characters long)");
                        }
                        fileName = reader.nextLine();
                    }

                    System.out.println("Enter File Size");
                    input = reader.nextLine();
                    while (!tryParseLong(input)){
                        System.out.println("Please enter a valid size (positive long integer)");
                        input = reader.nextLine();
                    }
                    fileSize = Long.parseLong(input);

                    System.out.println("Adding file: " + fileName + " " + parentDirName + " " + fileSize);
                    addFile(parentDirName, fileName, fileSize);
                    break;
                case 2: // Add Directory

                    System.out.println("Enter Parent Directory Name");
                    parentDirName = reader.nextLine();
                    while (!filesMap.containsKey(parentDirName)){
                        System.out.println("Directory doesn't exist! Please Enter a valid parent directory name");
                        parentDirName = reader.nextLine();
                    }

                    String dirName;
                    System.out.println("Enter Directory Name");
                    dirName = reader.nextLine();
                    while ((dirName.length() == 0) || (dirName.length() > 32)){
                        System.out.println("Please enter a valid name (up to 32 characters long)");
                        dirName = reader.nextLine();
                    }

                    System.out.println("Adding directory: " + dirName + " to " + parentDirName);
                    addDir(parentDirName, dirName);
                    break;
                case 3:

                    break;
                case 4:
                    showFileSystem();
                    break;
                case 5:
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

    // Adds new File under the Directory branch
    public void addFile(String parentDirName, String fileName, Long fileSize){
        // get the partent dir
        Directory parentDir = (Directory) filesMap.get(parentDirName);
        // create the file
        File newFile = new File(fileName, parentDir, fileSize);
        // add file to directory filesArray & to filesMap
        parentDir.addFileToDir(newFile);
        filesMap.put(fileName, newFile);
    }

    // Adds new Directory under the parent Directory
    public void addDir(String parentDirName, String dirName){
        //
        Directory parentDir = (Directory) filesMap.get(parentDirName);
        Directory newDir = new Directory(dirName, parentDir);
        // Add tp files mapper
        parentDir.addDirToDir(newDir);
        filesMap.put(dirName, newDir);

    }

    // Deletes the Directory or the File with this name
    public void delete(String name){

    }

    // Displays all files & directories with their hierarchical structure (for file display
    // all file properties and for Directory all its properties
    public void showFileSystem(){
        System.out.println(root.getInfo(0));
    }

}
