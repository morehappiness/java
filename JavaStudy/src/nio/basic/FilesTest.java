package basic;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class FilesTest {

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("data/logging.properties");
		for (LinkOption c : LinkOption.values())
			System.out.println(c);
		print(Files
				.exists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS }));

		path = Paths.get("data/nio/subdir");

		try {
			Path newDir = Files.createDirectory(path);
			print("dir created");

			Files.delete(newDir);
		} catch (FileAlreadyExistsException e) {
			// the directory already exists.
		} catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		} finally {
		}

		Path sourcePath = Paths.get("data/nio/logging.properties");
		Path destinationPath = Paths.get("data/nio/logging-copy.properties");

		try {
			Files.copy(sourcePath, destinationPath,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (FileAlreadyExistsException e) {
			// destination file already exists
		} catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}

		sourcePath = Paths.get("data/nio/logging-copy.properties");
		destinationPath = Paths.get("data/nio/subdir/logging-moved.properties");

		try {
			Files.move(sourcePath, destinationPath,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// moving file failed.
			e.printStackTrace();
		}

		path = Paths.get("/Users/lzj/Downloads");
		Set<FileVisitOption> ops = new HashSet<>();
		ops.add(FileVisitOption.FOLLOW_LINKS);
		Files.walkFileTree(path, ops, Integer.MAX_VALUE,
				new FileVisitor<Path>() {
					@Override
					public FileVisitResult preVisitDirectory(Path dir,
							BasicFileAttributes attrs) throws IOException {
						System.out.println("pre visit dir:" + dir);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path file,
							BasicFileAttributes attrs) throws IOException {
						System.out.println("visit file: " + file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file,
							IOException exc) throws IOException {
						System.out.println("visit file failed: " + file);
						throw exc;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir,
							IOException exc) throws IOException {
						System.out.println("post visit directory: " + dir);
						return FileVisitResult.CONTINUE;
					}
				});

		Path rootPath = Paths.get("data");
		String fileToFind = File.separator + "README.txt";

		try {
			Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					String fileString = file.toAbsolutePath().toString();
					System.out.println("pathString = " + fileString);

					if (fileString.endsWith(fileToFind)) {
						System.out.println("file found at path: "
								+ file.toAbsolutePath());
						return FileVisitResult.TERMINATE;
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		rootPath = Paths.get("data/to-delete");

		try {
		  Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
		    @Override
		    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		      System.out.println("delete file: " + file.toString());
		      Files.delete(file);
		      return FileVisitResult.CONTINUE;
		    }

		    @Override
		    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		      Files.delete(dir);
		      System.out.println("delete dir: " + dir.toString());
		      return FileVisitResult.CONTINUE;
		    }
		  });
		} catch(IOException e){
		  e.printStackTrace();
		}
	}

	private static void print(Object obj) {
		System.out.println(obj);
	}

}
