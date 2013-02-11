import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * User: rixonmathew
 * Date: 12/01/13
 * Time: 12:10 PM
 */
public class ShowFileStatusTest {

    private MiniDFSCluster dfsCluster;
    private FileSystem fileSystem;

    //@Before
    public void setup() throws IOException {
        Configuration configuration = new Configuration();
        if (System.getProperty("test.build.data")==null)  {
            System.setProperty("test.build.data","/tmp");
        }
        dfsCluster = new MiniDFSCluster(configuration,1,true,null);
        fileSystem = dfsCluster.getFileSystem();
        OutputStream outputStream = fileSystem.create(new Path("/dir/file"));
        outputStream.write("content".getBytes("UTF-8"));
        outputStream.close();
    }

    //@After
    public void teardown() throws IOException {
        if (fileSystem!=null) fileSystem.close();
        if (dfsCluster!=null) dfsCluster.shutdown();
    }

    //@Test(expected = FileNotFoundException.class)
    public void throwsFileNotFoundForNonExistentFile() throws IOException {
        fileSystem.getFileStatus(new Path("/dir/file-not-exists"));
    }

    //@Test
    public void testFileStatus() throws IOException {
        Path path = new Path("/dir/file");
        FileStatus fileStatus = fileSystem.getFileStatus(path);
        assertThat(fileStatus.getPath().toUri().getPath(),is("/dir/file"));
        assertThat(fileStatus.getReplication(),is((short)1));
        assertThat(fileStatus.getModificationTime(),is(lessThanOrEqualTo(System.currentTimeMillis())));
        assertThat(fileStatus.getBlockSize(),is(64*1024*1024L));
        assertThat(fileStatus.getLen(),is(7L));
        assertThat(fileStatus.getOwner(),is("rixonmathew"));
        assertThat(fileStatus.getGroup(),is("supergroup"));
        assertThat(fileStatus.getPermission().toString(),is("-rw-r--r--"));
    }
}
