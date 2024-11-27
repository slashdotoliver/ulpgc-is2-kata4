package software.ulpgc.kata4.apps.swing.view;

import software.ulpgc.kata4.architecture.model.Histogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {

    public static final Dimension INITIAL_FRAME_SIZE = new Dimension(1025, 770);
    private final JFreeChartHistogramDisplay histogramDisplay;

    public MainFrame() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Histogram");
        setSize(INITIAL_FRAME_SIZE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        add(histogramDisplay = new JFreeChartHistogramDisplay());

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
