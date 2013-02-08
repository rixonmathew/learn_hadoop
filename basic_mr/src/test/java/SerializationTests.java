import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 8/2/13
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class SerializationTests {

    @Test
    public void testIntWritableSerialization() throws IOException {

        int expectedInt = 500;
        IntWritable writable = new IntWritable(expectedInt);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteStream);
        writable.write(dataOutputStream);
        byte[] bytes = byteStream.toByteArray();
        assertThat(bytes.length,is(4));

        IntWritable anotherWritable = new IntWritable();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        anotherWritable.readFields(dataInputStream);
        assertThat(anotherWritable.get(),is(expectedInt));
    }

    @Test
    public void testTextField() {
        Text text = new Text("Hadoop Text Variable");
        assertThat("Find a substring",text.find("doop"),is(2));
        assertThat("Find first 'o'",text.find("o"),is(3));
        assertThat("Find first 'o'",text.find("o",4),is(4));
        assertThat("No match",text.find("pig"),is(-1));
    }

    @Test
    public void testStringWithUTF8() throws UnsupportedEncodingException {
        String string = "\u0041\u00DF\u6771\uD801\uDC00";
        assertThat("Length is correct",string.length(),is(5));
        assertThat("Length of bytes",string.getBytes("UTF-8").length,is(10));
        assertThat("Index of first character",string.indexOf('\u0041'),is(0));
        assertThat("Index of second character",string.indexOf('\u00DF'),is(1));
        assertThat("Index of third character",string.indexOf('\u6771'),is(2));
        assertThat("Index of fourth character",string.indexOf('\uD801'),is(3));
        assertThat("Index of fifth character",string.indexOf('\uDC00'),is(4));

        assertThat(string.codePointAt(0),is(0x0041));
        assertThat(string.codePointAt(1),is(0x00DF));
        assertThat(string.codePointAt(2),is(0x6771));
        assertThat(string.codePointAt(3),is(0x10400));
    }

    @Test
    public void testTextWithUTF8() {
        Text text = new Text("\u0041\u00DF\u6771\uD801\uDC00");
        assertThat("Length of text text",text.getLength(),is(10));
        assertThat("Index position",text.find("\u0041"),is(0));
        assertThat("Index position",text.find("\u00DF"),is(1));
        assertThat("Index position",text.find("\u6771"),is(3));
        assertThat("Index position",text.find("\uD801\uDC00"),is(6));

        assertThat(text.charAt(0),is(0x0041));
        assertThat(text.charAt(1),is(0x00DF));
        assertThat(text.charAt(3),is(0x6771));
        assertThat(text.charAt(6),is(0x10400));

    }
}
