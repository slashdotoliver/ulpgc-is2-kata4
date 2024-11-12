package software.ulpgc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistogramGenerator {
    static Map<Title.TitleType, Integer> generate(List<Title> titles) {
        Map<Title.TitleType, Integer> histogram = new HashMap<>();
        for (Title title : titles) {
            histogram.putIfAbsent(title.type(), 0);
            histogram.compute(title.type(), (k, v) -> v + 1);
        }
        return histogram;
    }
}
