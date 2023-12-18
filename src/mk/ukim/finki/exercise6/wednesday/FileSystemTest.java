package mk.ukim.finki.exercise6.wednesday;

import java.util.*;
import java.util.stream.Collectors;

interface IFile extends Comparable<IFile> {
    String getFileName();

    long getFileSize(); //recursion

    String getFileInfo(String indent); //?? indentation, recursion

    void sortBySize(); //recursion

    long findLargestFile(); //recursion

    @Override
    default int compareTo(IFile o){
        return Long.compare(this.getFileSize(), o.getFileSize());
    }
}

class File implements IFile {

    String name;
    long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(String indent) {
        return String.format("%sFile name: %10s File size: %10d", indent, name, size);
    }

    @Override
    public void sortBySize() {
        //DO NOTHING
    }

    @Override
    public long findLargestFile() {
        return size;
    }
}

class FileNameExistsException extends Exception {
    public FileNameExistsException(String message) {
        super(message);
    }
}


class Folder extends File {
    List<IFile> files;

    public Folder(String name) {
        super(name, 0);
        files = new ArrayList<>();
    }

    void addFile (IFile file) throws FileNameExistsException {
        for (IFile iFile : files) {
            if (iFile.getFileName().equals(file.getFileName())){
                throw new FileNameExistsException(String.format("There is already a file named %s in the folder %s", file.getFileName(), this.name));
            }
        }

        files.add(file);
    }

    @Override
    public long getFileSize() {
        return files.stream()
                .mapToLong(file -> file.getFileSize())
                .sum();
    }

    @Override
    public String getFileInfo(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%sFolder name: %10s Folder size: %10d", indent, name, getFileSize())).append("\n");

        sb.append(files.stream()
                .map(file -> file.getFileInfo(indent+"    "))
                .collect(Collectors.joining("\n")));

        return sb.toString();
    }

    @Override
    public void sortBySize() {
        Collections.sort(files);
    }

    @Override
    public long findLargestFile() {
        return files.stream().mapToLong(file -> file.findLargestFile()).max().orElse(0);
    }
}

class FileSystem {
    Folder rootDirectory;

    FileSystem() {
        rootDirectory = new Folder("root");
    }

    void addFile (IFile file) throws FileNameExistsException {
        rootDirectory.addFile(file);
    }

    long findLargestFile () {
        return rootDirectory.findLargestFile();
    }

    void sortBySize() {
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        return rootDirectory.getFileInfo("");
    }
}

public class FileSystemTest {

    public static Folder readFolder(Scanner sc) {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < totalFiles; i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String[] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args) {

        //file reading from input

        Scanner sc = new Scanner(System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());


    }
}