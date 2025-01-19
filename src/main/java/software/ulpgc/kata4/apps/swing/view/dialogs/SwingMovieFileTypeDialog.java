package software.ulpgc.kata4.apps.swing.view.dialogs;

import software.ulpgc.kata4.architecture.persistence.movie.MoviesFileType;
import software.ulpgc.kata4.architecture.view.MovieFileTypeDialog;

import javax.swing.*;
import java.util.Arrays;

public class SwingMovieFileTypeDialog extends JPanel implements MovieFileTypeDialog {

    private final JComboBox<MoviesFileType> fileTypeSelector;

    public SwingMovieFileTypeDialog() {
        add(fileTypeSelector = new JComboBox<>());
        Arrays.stream(MoviesFileType.values())
                .filter(SwingMovieFileTypeDialog::notInvalidType)
                .forEach(fileTypeSelector::addItem);
        fileTypeSelector.setSelectedIndex(0);
    }

    private static boolean notInvalidType(MoviesFileType type) {
        return !type.equals(MoviesFileType.INVALID_FILE_TYPE);
    }

    @Override
    public MoviesFileType get() {
        return fileTypeSelector.getItemAt(fileTypeSelector.getSelectedIndex());
    }
}
