import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 25/2/13
 * Time: 7:50 PM
 * The main driver class for Positions
 */
public class PopulationsDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        if (args.length!=2){
            System.err.printf("Usage: %s <input path> <output path>", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }


        Job job = new Job();
        job.setJarByClass(PopulationsDriver.class);
        //TODO add configuration for map and reduce
        return job.waitForCompletion(true)?1:0;
    }


    public static void main(String[] args) throws Exception {
     int exitCode = ToolRunner.run(new PopulationsDriver(),args);
     System.exit(exitCode);
    }
}
