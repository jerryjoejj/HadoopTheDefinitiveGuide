import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.net.URI;

public class FileSystemDoubleCat {

	public static void main(String[] args) throws Exception {

		String uri = args[0];

		Configuration configuration = new Configuration();
		FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
		FSDataInputStream inputStream = fileSystem.open(new Path(uri));
		IOUtils.copyBytes(inputStream, System.out, 4096, false);
		inputStream.seek(1);
		System.out.println();
		IOUtils.copyBytes(inputStream, System.out, 4096, false);
		IOUtils.closeStream(inputStream);
 	}
}
