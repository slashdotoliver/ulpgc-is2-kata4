package software.ulpgc.kata4.architecture.model.entities;

import java.util.List;

public interface Histogram {

    String title();

    List<String> labels();

    int valueOf(String label);
}
