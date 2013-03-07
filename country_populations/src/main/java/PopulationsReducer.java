import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 25/2/13
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class PopulationsReducer extends Reducer<Text,Text,Text,Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        context.write(new Text("1971"),new Text("1971:"));
    }
}
