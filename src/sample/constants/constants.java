package sample.constants;

import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Slider;

import java.io.File;

public class constants {

    public static SimpleStringProperty music_file_name  = new SimpleStringProperty();
    public static ObservableList<File>  brother_musics =  FXCollections.observableArrayList();

    public static boolean files_opened_by_open_button  = false;


    public static double playlist_stage_width = 700;
    public static double playlist_stage_height = 400;
}
