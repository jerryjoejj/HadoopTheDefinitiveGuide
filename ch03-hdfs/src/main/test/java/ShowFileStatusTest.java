import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hdfs.MiniDFSCluster;

public class ShowFileStatusTest {

	private MiniDFSCluster cluster;

	public void setUp() {
		Configuration configuration = new Configuration();
		if(System.getProperty("test.build.data") == null) {
			System.setProperty("test.build.data", "/tmp");
		}
	}

}
