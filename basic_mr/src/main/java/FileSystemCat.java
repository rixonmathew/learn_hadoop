import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * User: rixonmathew
 * Date: 12/01/13
 * Time: 11:02 AM
 */
public class FileSystemCat {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: hadoop FileSystemCat <hdfs_file>");
            return;
        }
        FSDataInputStream inputStream = null;
        Configuration configuration = new Configuration();
        String uri = args[0];
        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
        try{
            inputStream = fileSystem.open(new Path(uri));
            IOUtils.copyBytes(inputStream,System.out,4096,false);
            inputStream.seek(20);
            System.out.println("After seeking");
            IOUtils.copyBytes(inputStream,System.out,4096,false);
        } finally {
            inputStream.close();
        }
    }
}
