import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;

/**
 * User: rixonmathew
 * Date: 12/01/13
 * Time: 9:12 PM
 */
public class StreamCompressor {

    public static void main(String[] args) {
        if (args.length!=1) {
            System.err.println("Usage: hadoop StreamCompressor <fully_qualified_codec>");
            return;
        }
        Class<?> compressionCodecClass;
        Configuration configuration = new Configuration();
        try {
            compressionCodecClass= Class.forName(args[0]);
            CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(compressionCodecClass, configuration);
            CompressionOutputStream stream = codec.createOutputStream(System.out);
            IOUtils.copyBytes(System.in,stream,4096,false);
            stream.finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
