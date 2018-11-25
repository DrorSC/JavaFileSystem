import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Directory implements AbstractFile {
    private Directory parentDir;
    private String name;
    private Date creationDate;
    private ArrayList includedFiles;

    public Directory(String name, Directory parentDir){
        this.name = name;
        this.parentDir = parentDir;
        this.creationDate = new Date();
        this.includedFiles = new ArrayList();
    }

    @Override
    public String getInfo(int depth){
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatDate = new SimpleDateFormat();
        // Keep tracking on depth tree for the tabs - for better UI of the files in system and their relation
        for(int i=0; i< depth; i++)
            sb.append("\t");
        sb.append(name);
        sb.append(" (" + formatDate.format(creationDate) + ")\n");
        // now we check the files/folders in current directory
        for (Object obj: includedFiles){
            String className = obj.getClass().getSimpleName();
            if(className.equals("Directory")){
                sb.append(((Directory)obj).getInfo(depth + 1));
            }else {
                sb.append(((File)obj).getInfo(depth +1));
            }
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void addFileToDir(File newFile){
        includedFiles.add(newFile);
        System.out.println("File " + newFile.getName() + " has been added to directory " + this.name);
    }

    public void addDirToDir(Directory newDir) {
        includedFiles.add(newDir);
        System.out.println("Directory " + newDir.getName() + " has been added to directory " + this.name);
    }

    public void deleteFile(File fileToDel) {
        this.includedFiles.remove(fileToDel);
        System.out.println("File " + fileToDel.getName() + " has been deleted from directory " + this.name);
    }

    private void deleteDirectory(Directory dirToDel) {
        this.includedFiles.remove(dirToDel);
        System.out.println("Directory " + dirToDel.getName() + " has been deleted from directory " + this.name);
    }

    public void delete(Map<String, AbstractFile> filesMap) {
        // Delete the dir folders and files
        if(!includedFiles.isEmpty()){
            Object obj = includedFiles.get(0);
            while(obj != null){
                String className = obj.getClass().getSimpleName();
                if(className.equals("Directory")){
                    ((Directory)obj).delete(filesMap);
                }else {
                    ((File)obj).delete(filesMap);
                }
                if(includedFiles.isEmpty())
                    break;
                obj = includedFiles.get(0);
            }
        }
        // in case it isn't the root folder, remove directory from parent array & mapper
        if(!(this.name == "root")) {
            this.parentDir.deleteDirectory(this);
            filesMap.remove(this.name);
        }
    }
}
