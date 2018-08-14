import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class ShowFileStatusTest {

	private MiniDFSCluster cluster;
	private FileSystem fileSystem;

	@Before
	public void setUp() throws IOException {
		Configuration configuration = new Configuration();
		if(System.getProperty("test.build.data") == null) {
			System.setProperty("test.build.data", "/tmp");
		}
		cluster = new MiniDFSCluster.Builder(configuration).build();
		fileSystem = cluster.getFileSystem();

        OutputStream out = fileSystem.create(new Path("/dir/file"));
        out.write("content".getBytes("UTF-8"));
        out.close();
	}

	@After
	public void tearDown() throws IOException {
	    if(fileSystem != null) {
            fileSystem.close();
        }
        if(cluster != null) {
            cluster.close();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void throwsFileNotFoundForExistentFile() throws IOException {
        fileSystem.getFileStatus(new Path("no-such-file"));
    }

    @Test
    public void fileStatusForFile() throws IOException {
        Path path = new Path("/dir/file");
        FileStatus fileStatus = fileSystem.getFileStatus(path);
        Assert.assertThat(fileStatus.getPath().toUri(), CoreMatchers.is(URI.create("/dir")));
    }

}
