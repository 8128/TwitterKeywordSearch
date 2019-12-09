import java.util.List;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-12-09 00:39
 */
public class TopK {

    private String filePath = null;
    private List<String>

    public TopK() {
        generateFilePath();

    }

    public void setFilePath(String string){
        this.filePath = string;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void generateFilePath () throws NullPointerException{
        this.filePath = this.getClass().getClassLoader().getResource("abcnews-date-text.csv").getPath();
    }

    public static void main(String[] args) {
        System.out.println("Loading CSV File");

    }
}
