package sample.resources;

import java.io.File;
import java.net.URISyntaxException;

public class get_files {
    public static String get_file(String file_name) throws URISyntaxException {
        get_files f = new get_files();
        String file = new get_files().getClass().getResource(file_name).toURI().toString();
        return file;
    }
}
