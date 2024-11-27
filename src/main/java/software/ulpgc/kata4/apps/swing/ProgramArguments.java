package software.ulpgc.kata4.apps.swing;

import java.io.File;

public record ProgramArguments(String[] arguments) {

    public static String validFormat() {
        return "Needs exactly 1 argument <file_path>";
    }

    public boolean valid() {
        if (!file().canRead()) return false;
        if (!file().isFile()) return false;
        return arguments.length == 1;
    }

    public File file() {
        return new File(arguments[0]);
    }
}
