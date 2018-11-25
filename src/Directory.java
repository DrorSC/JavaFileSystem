import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public void addFileToDir(File newFile){
        includedFiles.add(newFile);
    }

    public void addDirToDir(Directory newDir) {
        includedFiles.add(newDir);
    }
}
