import java.util.ArrayList;
import java.util.Arrays;

public class Work {
    private int[] hard_drive = new int[20];

    public Work() {
        for (int i = 0; i < hard_drive.length; i++) {
            hard_drive[i] = 0;
        }
    }

    public boolean loadFile(File file){
        int i = 0;
        int count = 0;
        while (hard_drive[i] == 1) {
            i++;
        }
        count = count0(hard_drive);
        ArrayList<Integer> blocks = new ArrayList<>(file.getSize());
        if (count < file.getSize()) {
            return false;
        } else {
            int c = 0;
            for (int j = i; j < hard_drive.length&&c<file.getSize(); j++) {
                if (hard_drive[j] == 0) {
                    blocks.add(j);
                    c++;
                    hard_drive[j] = 1;
                }
            }
            file.setBlocks(blocks);
            return true;
        }
    }

    public boolean load(Directory dir) {
        int i = 0;
        int count = 0;
        while (hard_drive[i] == 1) {
            i++;
        }
        count = count0(hard_drive);
        if (count < dir.getSize()) {
            return false;
        } else {
            for (int j = i; j < hard_drive.length; j++) {
                if (hard_drive[j] == 0) {
                    dir.setPosition(j);
                    hard_drive[j] = 1;
                    break;
                }
            }
            return true;
        }
    }

    public void freeFile(File file){
        for(int block:file.getBlocks()){
            hard_drive[block] = 0;
        }
    }

    public void freeDir(Directory dir){
        hard_drive[dir.getPosition()] = 0;
        for(File f : dir.list_f){
            freeFile(f);
        }
        for(Directory d : dir.list_d){
            freeDir(d);
        }
    }

    public int[] getHard_drive() {
        return hard_drive;
    }

    public void setHard_drive(int[] hard_drive) {
        this.hard_drive = hard_drive;
    }

    @Override
    public String toString() {
        return Arrays.toString(hard_drive);
    }

    private int count0(int[] mas) {
        int k = 0;
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] == 0)
                k++;
        }
        return k;
    }
}
