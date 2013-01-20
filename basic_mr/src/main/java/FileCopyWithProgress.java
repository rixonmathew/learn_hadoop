import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;

/**
 * User: rixonmathew
 * Date: 12/01/13
 * Time: 11:32 AM
 */
public class FileCopyWithProgress {

    public static void main(String[] args) throws IOException {
        if (args.length!=2) {
            System.err.println("Usage hadoop FileCopyWithProgress <local_file> <hdfs_destination>");
            return;
        }
        String localFile = args[0];
        String destination = args[1];
        InputStream inputStream = new BufferedInputStream(new FileInputStream(localFile));
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(destination),configuration);

        OutputStream outputStream = fileSystem.create(new Path(destination),new Progressable() {
            @Override
            public void progress() {
                System.out.print("..>>");
            }
        });
        IOUtils.copyBytes(inputStream,outputStream,4096,true);
    }
}
