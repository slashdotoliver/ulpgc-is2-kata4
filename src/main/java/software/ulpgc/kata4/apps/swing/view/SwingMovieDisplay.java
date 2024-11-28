package software.ulpgc.kata4.apps.swing.view;

import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.view.MovieDisplay;

import javax.swing.*;

public class SwingMovieDisplay extends JLabel implements MovieDisplay {
    @Override
    public void show(Movie movie) {
        this.setText(movie.toString());
    }
}
