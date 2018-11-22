import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Directory implements AbstractFile {
    private String name;
    private Date creationDate;
    private ArrayList includedFiles;
    private Map<String, AbstractFile> includedFilesMap;

    public Directory(String name){
        this.name = name;
        this.creationDate = new Date();
        this.includedFiles = new ArrayList();
        this.includedFilesMap = new HashMap<String, AbstractFile>();
    }

    public String getInfo(){
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatDate = new SimpleDateFormat();
        // first we add this directory info
        sb.append(name);
        sb.append(" (" + formatDate.format(creationDate) + ")");
        // now we check the files/folders in current directory
        System.out.println(includedFiles.isEmpty());
        for (Object obj: includedFiles){
            String className = obj.getClass().getSimpleName();
            if(name.equals("Directory")){
                ((Directory)obj).getInfo();
            }else {
                ((File)obj).getInfo();
            }
        }
        return sb.toString();
    }

    public void addFileToDir(File newFile){
        includedFiles.add(newFile);
        includedFilesMap.put(newFile.getName(), newFile);
    }

    @Override
    public void ls() {

    }
}
