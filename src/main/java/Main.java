import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileContent = readFileContent();
        for (int i = 0; i < 10000; ++i) {
            calculate(i, fileContent);
        }
    }

    public static String readFileContent() throws IOException {
        Stream<String> entries =
                Stream.of(System.getProperty("java.class.path").split(File.pathSeparator));
        File targetClassDir =
                entries.filter(
                                entry ->
                                        entry.endsWith("target/classes")
                                                || entry.endsWith("target\\classes"))
                        .findFirst()
                        .map(File::new)
                        .orElseThrow(IllegalStateException::new);
        File mainDotJava = new File(targetClassDir, "../../src/main/java/Main.java");

        return IOUtils.toString(new FileReader(mainDotJava)).replaceAll("\\s", "");
    }

    public static String calculate(int i, String fileContent) throws IOException {
        // Please set a condition breakpoint with "i==5000" here to catch the value of result
        // 请在这里设置一个i==5000的条件断点，捕捉result的值
        String result = DigestUtils.md5Hex(fileContent + i);
        return result;
    }
}
