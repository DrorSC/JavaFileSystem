import java.util.Scanner;

public class FileSystem {
    private Directory root;

    public FileSystem(){
        root = new Directory("root");
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
            System.out.println("Chosen: "+ n);
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
                    String parentDirName;
                    Long fileSize;
                    System.out.println("Enter File Name");

                    fileName = reader.nextLine();
                    while ((fileName.length() == 0) || (fileName.length() > 32)){
                        System.out.println("Please enter a valid name (up to 32 characters long)");
                        fileName = reader.nextLine();
                    }
                    System.out.println("Enter Parent Dir Name");
                    parentDirName = reader.nextLine();

                    System.out.println("Enter File Size");
                    String input = reader.nextLine();
                    while (!tryParseLong(input)){
                        System.out.println("Please enter a valid size (positive long integer)");
                        input = reader.nextLine();
                    }
                    fileSize = Long.parseLong(input);

                    System.out.println("Adding file: " + fileName + " " + parentDirName + " " + fileSize);
                    addFile(parentDirName, fileName, fileSize);
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    System.out.println("case 4");
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
        File file = new File(parentDirName + fileName, fileSize);


    }

    // Adds new Directory under the parent Directory
    public void addDir(String parentDirName, String dirName){

    }

    // Deletes the Directory or the File with this name
    public void delete(String name){

    }

    // Displays all files & directories with their hierarchical structure (for file display
    // all file properties and for Directory all its properties
    public void showFileSystem(){
        System.out.println(root.getInfo());
    }

    //newFolder.mkdir();
}
