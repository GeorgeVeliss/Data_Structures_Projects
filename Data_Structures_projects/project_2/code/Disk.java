public class Disk implements Comparable<Disk> {

    private static int staticID = 0;
    private int ID;
    private int size = 0;
    private List<Integer> folders = new List<>();
    private int freeSpace = 1_000_000;

    public Disk() {
        ID = staticID;
        staticID++;
    }

    public void insert(int folderSize) {
        folders.insertAtFront(folderSize);
        freeSpace -= folderSize;
        size++;
    }

    public int getID() {
        return ID;
    }

    public int getDiskSize() {
        return size;
    }

    public List<Integer> getFolders() {
        return folders;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public int compareTo(Disk other) {
        if (this.freeSpace > other.freeSpace)
            return 1;
        else if (this.freeSpace < other.freeSpace)
            return -1;
        else
            return 0;
    }
}
