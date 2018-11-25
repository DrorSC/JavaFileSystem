import java.text.SimpleDateFormat;
import java.util.Date;

public class File implements AbstractFile {
    private Directory parentDir;
    private String name;
    private Long size;
    private Date creationDate;

    public File(String name, Directory parentDir, Long size){
        this.name = name;
        this.parentDir = parentDir;
        this.size = size;
        creationDate = new Date();
    }

    @Override
    public String getInfo(int depth){
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatDate = new SimpleDateFormat();
        for(int i=0; i< depth; i++)
            sb.append("\t");
        sb.append(name);
        sb.append(" (" + formatDate.format(creationDate) + ", " + size + "bytes)\n");
        return sb.toString();
    }

    public String getName(){
        return this.name;
    }
}
