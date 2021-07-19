package test;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class NioFileAPITest {
	private static String HOME = "/home/sanket/IdeaProjects/File-IO/data";
	private static String TEMP = "/temp";

	@Test
	public void givenPathWhenCheckedThenConfirmed() throws IOException {
		Path homePath = Paths.get(HOME);
		Assert.assertTrue(Files.exists(homePath));
		Path filePath = Paths.get(HOME + "/" + TEMP);
		if (Files.exists(filePath)) {
			FileUtils.deleteFiles(filePath.toFile());
		}
		Assert.assertTrue(Files.notExists(filePath));

		Files.createDirectory(filePath);
		Assert.assertTrue(Files.exists(filePath));

		IntStream.range(1, 10).forEach(cntr -> {
			Path tempFile = Paths.get(filePath + "/temp" + cntr);
			Assert.assertTrue(Files.notExists(tempFile));
			try {
				Files.createFile(tempFile);
			} catch (IOException e) {
				Assert.assertTrue(Files.exists(tempFile));
			}
		});
		Files.list(filePath).filter(Files::isRegularFile).forEach(System.out::println);
		Files.newDirectoryStream(filePath).forEach(System.out::println);
		Files.newDirectoryStream(filePath, path -> path.toFile().isFile() && path.toString().startsWith("temp"))
				.forEach(System.out::println);

	}
}
