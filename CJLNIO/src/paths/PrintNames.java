package paths;

/*
So, our method is specifying path as the top of SimpleFileVisitor. This will also be the path of reference passed
to the methods. In this case, as wwe can see from the visitFile() declaration, a path is passed as the first parameter.
When we were describing the method, it accepts a reference to a file. We couldn't be more specific than that
because the parameter top has to match the top we specify for the FileVisitor.

Inside the visitFile() method, we're putting the absolute path for the file, and then we're returning the
FileVisitResult.CONTINUE constant. We can also return skip underscore siblings, which means the traversal should skip
all the other entries in the same directory as the file. And skip underscore subtree, which we can use when we want
to skip a directory as well as terminate, which means that we want to stop the traversal.

For example we'd return terminate when we found the file that we're searching for so that the traversal's actually
stopped. It would only make sense to return underscore subtree from the preVisitDirectory() method.
Returning it from the other methods is equivalent to Continue. Also, if we return skip underscore siblings from the
preVisitDirectory(), then the directory itself is also skipped, and the postVisitDirectory() method is never called
for that directory. So basically, that's another way of saying that we want to completely ignore the directory
and all its descendants and siblings.
*/

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class PrintNames extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file.toAbsolutePath());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println(dir.toAbsolutePath());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error accessing file: " + file.toAbsolutePath() + " " + exc.getMessage());
        return FileVisitResult.CONTINUE;
    }
}
