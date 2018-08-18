import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * 从标准输入读取数据压缩，再写入标准输出
 */
public class StreamCompressor {

	public static void main(String[] args) throws Exception {
		String codecClassname = args[0];
		Class<?> codecClass = Class.forName(codecClassname);
		Configuration configuration = new Configuration();
		CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, configuration);

		CompressionOutputStream outputStream = codec.createOutputStream(System.out);
		IOUtils.copyBytes(System.in, outputStream, 4096, false);

		outputStream.finish();
	}


}
