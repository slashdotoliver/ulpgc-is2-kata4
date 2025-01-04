package software.ulpgc.kata4.apps.swing.view;

import software.ulpgc.kata4.architecture.control.commands.Command;
import software.ulpgc.kata4.architecture.control.commands.CommandName;
import software.ulpgc.kata4.architecture.model.Histogram;
import software.ulpgc.kata4.architecture.view.MovieDisplay;
import software.ulpgc.kata4.architecture.view.MovieFileDialog;
import software.ulpgc.kata4.architecture.view.MovieFileTypeDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;

public class SwingMainFrame extends JFrame {

    public static final Dimension INITIAL_FRAME_SIZE = new Dimension(1025, 770);
    private final JFreeChartHistogramDisplay histogramDisplay;
    private final Map<CommandName, Command> commands;

    private MovieFileTypeDialog typeDialog;
    private MovieFileDialog fileDialog;
    private MovieDisplay movieDisplay;

    public SwingMainFrame(Map<CommandName, Command> commands) throws HeadlessException {
        this.commands = commands;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Histogram");
        setSize(INITIAL_FRAME_SIZE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        add(createFileTypeDialog());
        add(createTitleFileDialog());
        add(createRandomTitleButton());
        add(createTitleDisplay());
        add(histogramDisplay = new JFreeChartHistogramDisplay());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                histogramDisplay.updateSize(getSize());
            }
        });
    }

    private Component createRandomTitleButton() {
        JButton displayRandomMovie = new JButton("Display Random Title");
        displayRandomMovie.addActionListener(e -> commands.get(CommandName.DisplayRandomMovie).execute());
        return displayRandomMovie;
    }

    private Component createTitleFileDialog() {
        var fileDialog = new SwingMovieFileDialog();
        this.fileDialog = fileDialog;
        return fileDialog;
    }

    private Component createFileTypeDialog() {
        var typeDialog = new SwingMovieFileTypeDialog();
        this.typeDialog = typeDialog;
        return typeDialog;
    }

    private Component createTitleDisplay() {
        var titleDisplay = new SwingMovieDisplay();
        this.movieDisplay = titleDisplay;
        return titleDisplay;
    }

    public MovieFileTypeDialog getTypeDialog() {
        return typeDialog;
    }

    public MovieFileDialog getFileDialog() {
        return fileDialog;
    }

    public MovieDisplay getMovieDisplay() {
        return movieDisplay;
    }

    public void put(Histogram histogram) {
        histogramDisplay.display(histogram);
    }
}
