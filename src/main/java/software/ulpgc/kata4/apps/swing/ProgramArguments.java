package software.ulpgc.kata4.apps.swing;

import software.ulpgc.kata4.architecture.persistence.movie.MoviesFileType;

import java.io.File;

public record ProgramArguments(String[] args) {

    public static String validFormat() {
        return "Needs exactly 2 args <file_type=tsv-with-header|tsv|sqlite> <file_path>";
    }

    public boolean valid() {
        if (args.length == 2) return false;
        if (fileType().equals(MoviesFileType.INVALID_FILE_TYPE)) return false;
        if (!file().canRead()) return false;
        if (!file().isFile()) return false;
        return true;
    }

    public MoviesFileType fileType() {
        return switch (args[0]) {
            case "tsv-with-header" -> MoviesFileType.TSV_WITH_HEADER;
            case  "tsv" -> MoviesFileType.TSV;
            case "sqlite" -> MoviesFileType.SQLITE;
            default -> MoviesFileType.INVALID_FILE_TYPE;
        };
    }

    public File file() {
        return new File(args[1]);
    }
}
