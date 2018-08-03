import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * 显示本地文件复制到Hadoop
 */
public class FileCopyWithProgress {

	public static void main(String[] args) throws Exception {

		String localStr = args[0];

		String dst = args[1];

		InputStream inputStream = new BufferedInputStream(new FileInputStream(localStr));

		Configuration configuration = new Configuration();

		FileSystem fileSystem = FileSystem.get(URI.create(dst), configuration);
		OutputStream outputStream = fileSystem.create(new Path(dst), new Progressable() {
			public void progress() {
				System.out.println(".");
			}
		});

		IOUtils.copyBytes(inputStream, outputStream, 4096, false);
	}
}
