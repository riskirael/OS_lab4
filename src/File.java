import java.util.List;

public class File extends Blocks {
    private static final Blocks prev_blocks =null ;
    private String name;
    private int size;
    private List<Integer> blocks;
    private Blocks prev;

    public List<Integer> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Integer> blocks) {
        this.blocks = blocks;
    }
    public Blocks getPrev() {
        return prev;
    }

    public void setPrev(Blocks n) {
        prev = n;
    }

    public File(String name, int size) {
        super(prev_blocks);
        this.name = name;
        this.size = size;
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