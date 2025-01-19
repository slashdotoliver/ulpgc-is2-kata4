package software.ulpgc.kata4.apps.swing.view.dialogs;

import software.ulpgc.kata4.architecture.view.MovieFileDialog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Optional;

public class SwingMovieFileDialog extends JPanel implements MovieFileDialog {

    private static final String SELECT_FILE_TEXT = "Select file";

    private final JButton button;
    private File file = null;

    public SwingMovieFileDialog() {
        this.setLayout(new FlowLayout());
        add(button = new JButton(SELECT_FILE_TEXT + "..."));

        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(new JFrame(SELECT_FILE_TEXT));
            setCurrentFile(option, fileChooser);
        });
    }

    private void setCurrentFile(int option, JFileChooser fileChooser) {
        if (option == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            button.setText(file.getAbsolutePath());
        } else {
            file = null;
            button.setText(SELECT_FILE_TEXT + "...");
        }
    }

    @Override
    public Optional<File> get() {
        return Optional.ofNullable(file);
    }
}
