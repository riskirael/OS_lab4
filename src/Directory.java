import java.util.ArrayList;
import java.util.List;

public class Directory extends ElementsFileSystem {
    private String name;
    private int size;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;
    List<File> list_f;
    List<Directory> list_d;
    public Directory(String name){
        this.name = "d"+name;
        this.size = 1;
        list_f = new ArrayList<>();
        list_d = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
}

