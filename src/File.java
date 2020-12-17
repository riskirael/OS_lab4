import java.util.List;

public class File extends ElementFileSystem {
    private String name;
    private int size;
    private List<Integer> blocks;
    private File prev;

    public List<Integer> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Integer> blocks) {
        this.blocks = blocks;
    }
    public File getPrev() {
        return prev;
    }

    public void setPrev(File n) {
        prev = n;
    }

    public File(String name, int size, File prev) {
        this.name = name;
        this.size = size;
        this.prev=prev;
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

    @Override
    public String toString() {
        return name;
    }
}