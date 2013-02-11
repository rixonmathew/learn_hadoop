import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 11/2/13
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class SequenceFileReadDemo {

    public static void main(String[] args) throws IOException {
        if (args.length!=1) {
            System.err.println("Usage: hadoop <basic_mr_jar> <input_file>");
            return;
        }
        String uri = args[0];
        Configuration configuration= new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
        Path path = new Path(uri);
        SequenceFile.Reader reader = null;
        try{
            reader = new SequenceFile.Reader(fileSystem,path,configuration);
            Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(),configuration);
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(),configuration);
            long position = reader.getPosition();
            while(reader.next(key,value)){
                String syncScreen = reader.syncSeen()?"*":"";
                System.out.printf("[%s%s]\t%s\t%s\n",position,syncScreen,key,value);
                position = reader.getPosition();
            }
        } finally {
            IOUtils.closeStream(reader);
        }
    }
}
