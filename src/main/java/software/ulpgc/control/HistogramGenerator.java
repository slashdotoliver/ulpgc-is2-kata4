package software.ulpgc.control;

import software.ulpgc.model.Histogram;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

public class HistogramGenerator {
    public static <T> Histogram generateBy(Function<T, String> mapper, List<T> elements, String title) {
        Map<String, Integer> histogram = elements.stream()
                .map(mapper)
                .collect(groupingBy(text -> text, reducing(0, text -> 1, Integer::sum)));
        return new MapHistogram(histogram, title);
    }

    private record MapHistogram(Map<String, Integer> map, String title) implements Histogram {
        @Override
        public List<String> labels() {
            return map.keySet().stream().toList();
        }

        @Override
        public int valueOf(String label) {
            return map.get(label);
        }
    }
}
