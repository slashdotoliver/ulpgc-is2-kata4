package software.ulpgc.view;

import software.ulpgc.model.Histogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {

    public static final Dimension INITIAL_FRAME_SIZE = new Dimension(1025, 770);
    private final JFreeBarChartHistogramDisplay histogramDisplay;

    public MainFrame() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Histogram");
        setSize(INITIAL_FRAME_SIZE);
        setLocationRelativeTo(null);

        add(histogramDisplay = new JFreeBarChartHistogramDisplay());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                histogramDisplay.updateSize(getSize());
            }
        });
    }

    public void put(Histogram histogram) {
        histogramDisplay.display(histogram);
    }
}
