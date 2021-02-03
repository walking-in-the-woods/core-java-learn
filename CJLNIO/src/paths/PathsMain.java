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

/*
    || - logical pipe character
    | - pipe character (bitwise-onclusive or)
    used to chain exceptions together
*/

/*
What if we want to retrieve specific files in a directory (.pdf for example)? The newDirectoryStream method accepts
a filter as a second parameter, and only paths that match the filter will actually be included in the
DirectoryStream. Now, the second filtering parameter is referred to as a "glob". Now, what a glob is a pattern
that's similar to a regular expression, but the syntax is more geared towards the types of things we'd wanna do
when working with paths.

Examples:

    The * character matches any string (which can contain any number of characters).
    *.dat will match any path with the .dat extension.
    *.{dat,txt} will match any path that has the extension .dat or .txt
    ? matches exactly one character. For example, the glob ??? would match any path that contains exactly three
      characters.
    myfile* matches any paths that begin with myfile.
    b?*.txt matches any paths that are at least two characters long and begin with the character b
      (the ? forces a second character, and the * matches 0 or more characters).
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class PathsMain {
    public static void main(String[] args) {

//        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
//            public boolean accept(Path path) throws IOException {
//                return Files.isRegularFile(path);
//            }
//        };

        DirectoryStream.Filter<Path> filter = p -> Files.isRegularFile(p);

        Path directory = FileSystems.getDefault().getPath("CJLNIO/FileTree/Dir2");
        //try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory, "*.dat")) {
        try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory, filter)) {
            for (Path file : contents) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.out.println(e.getMessage());
        }
    }
}
