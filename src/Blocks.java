import java.util.List;

abstract class Blocks {
    private String name;
    private int size;
    private List<Integer> blocks;
    private Blocks prev_blocks;


    public Blocks(Blocks prev_blocks) {
        this.prev_blocks=prev_blocks;
    }

    public List<Integer> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Integer> blocks) {
        this.blocks = blocks;
    }
    public Blocks getPrev() {
        return prev_blocks;
    }

    public void setPrev(Blocks n) {
        prev_blocks = n;
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