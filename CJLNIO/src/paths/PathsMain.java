package paths;

/*
java.nio.file package

Each folder, which is also referred to as directory, is also a node in a path.
And then, of course, there's the file itself. If we downloaded a file and placed it
in the C:\downloads directory, then the file path has three nodes:
    1. C:\
    2. downloads
    3. the file
This path is unique. We can't have two files with the same name in the same directory,
because each file is identified by its path.

The character used to separate the directory names in a path is called the Delimiter.
File paths can be Absolute or Relative. The absolute path starts at a root node.

When using relative paths in applications, there's usually the concept of a current or
Working Directory that we can combine with relative paths. For example, when we were
running applications that used Path, we didn't specify the entire Path.

Relative path:

        Path dataPath = FileSystems.getDefault().getPath("data.txt");

Absolute path:

        Path dataPath = Paths.get("C:\\MyIdeaProjects\\Project1\\data.txt");
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsMain {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("WorkingDirectoryFile.txt");
        printFile(path);

        Path filePath = FileSystems.getDefault().getPath("CJLNIO/files", "SubdirectoryFile.txt");
        printFile(filePath);

        filePath = Paths.get("D:\\code\\java\\JavaTimBuchalka\\OutThere.txt");
        printFile(filePath);
    }

    private static void printFile(Path path) {
        try (BufferedReader fileReader = Files.newBufferedReader(path)) {
            String line;
            while((line = fileReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
