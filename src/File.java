import java.text.SimpleDateFormat;
import java.util.Date;

public class File implements AbstractFile {

    private String name;
    private Long size;
    private Date creationDate;

    public File(String name, Long size){
        this.name = name;
        this.size = size;
        creationDate = new Date();
    }

    public String getInfo(){
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatDate = new SimpleDateFormat();
        sb.append("file name: " + name);
        sb.append(" file size: " + size);
        sb.append(" creation date: " + formatDate.format(creationDate));
        return sb.toString();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void ls() {
        System.out.println(name);
    }
}
