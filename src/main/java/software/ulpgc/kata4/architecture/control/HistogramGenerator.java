package software.ulpgc.kata4.architecture.control;

import software.ulpgc.kata4.architecture.model.Histogram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistogramGenerator {

    private final Map<String, Integer> map = new HashMap<>();
    private String title = "Histogram";

    public void feed(String category) {
        map.merge(category, 1, Integer::sum);
    }

    public HistogramGenerator withTitle(String title) {
        this.title = title;
        return this;
    }

    public Histogram build() {
        return new MapHistogram(map, title);
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
