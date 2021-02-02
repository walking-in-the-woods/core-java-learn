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

/*
java.io.File class has some problems:

1. Many methods in the class don't throw exceptions, or don't provide specific error messages when they fail.
   One example would be the File.delete() method, which returns a boolean. If the deletion fails, you can't tell
   if it was because the file didn't exist, or the application didn't have permission to delete the file, or .. ?
   Unfortunately, the method doesn't provide that information.

2. The File.rename() method works differently on different platforms. Java is supposed to be portable across platforms,
   meaning the code shouldn't have to wirry about which operating system it's running on.

3. There's no support for symbolic links. A symbolic link is a kind of file that points to another file. They're often
   used with networks, to point to a remote location. The File class doesn't understand them.

4. The File class doesn't provide a way to get metadata about a file, such as its permissions, its owner, and the
   other security information. The metadata it does provide is retrieved inefficiently.

5. Many of the methods don't perform well when working with lots of data. For example, if we use the File class
   to request a list of all the files in a directory, and there are a lot of files, the method can hang.

6. Since the File class doesn't understand symbolic links, walking a directory tree is problematic.
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

//        Path filePath = FileSystems.getDefault().getPath("CJLNIO/files", "SubdirectoryFile.txt");
        Path filePath = Paths.get(".","CJLNIO/files", "SubdirectoryFile.txt");
        printFile(filePath);

//        filePath = Paths.get("D:\\code\\java\\JavaTimBuchalka\\OutThere.txt");
        filePath = Paths.get("D:\\", "code\\java\\JavaTimBuchalka\\", "OutThere.txt");
        printFile(filePath);

        filePath = Paths.get(".");
        System.out.println(filePath.toAbsolutePath());

        // D:\Examples\.\subfolder\..\directory
        // D:\Examples\directory           // .. means the parent directory (move up th the previous node)

        Path path2 = FileSystems.getDefault().getPath(
                ".", "CJLNIO/files", "..", "files", "SubdirectoryFile.txt");
        System.out.println(path2.normalize().toAbsolutePath());
        printFile(path2.normalize());
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
