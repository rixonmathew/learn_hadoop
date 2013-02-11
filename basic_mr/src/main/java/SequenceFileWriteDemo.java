import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 11/2/13
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SequenceFileWriteDemo {
    private static final String[] DATA = {
            "One, two, buckle my shoe",
            "Three, four, shut the door",
            "Five, six, pick up sticks",
            "Seven, eight, lay them straight",
            "Nine, ten, a big fat hen"
    };

    public static void main(String[] args) throws IOException {
        if (args.length!=1) {
            System.err.println("Usage: hadoop <basic_mr_jar> <input_file>");
            return;
        }
        String uri = args[0];
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
        Path path = new Path(uri);
        IntWritable key = new IntWritable();
        Text value = new Text();
        SequenceFile.Writer writer = null;
        try{
          writer =  SequenceFile.createWriter(fileSystem,configuration,path,key.getClass(),value.getClass());
          for (int i=0;i<100;i++) {
            key.set(100-i);
            value.set(DATA[i%DATA.length]);
            System.out.printf("[%s]\t%s\t%s\n",writer.getLength(),key,value);
            writer.append(key,value);
          }
        } finally {
            IOUtils.closeStream(writer);
        }
    }
}
