package sample.view;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.constants.constants;

import java.io.File;
import java.util.ArrayList;

public class media_files {
    private  static constants constant = new constants();

    static  File parent_directory;


    public static VBox show_files(File parent){

        if (parent==null) return null;
        parent_directory= parent;

        ObservableList<Text> file_names = FXCollections.observableArrayList();

        VBox file_list = new VBox(5);

        ListView<Text> listView = new ListView<>(file_names);

        if (parent.isDirectory()){
            File[] files = parent.listFiles();

            for (File file : files){
                if (file.getName().endsWith("mp4") | file.getName().endsWith("Mp4") |
                       file.getName().endsWith("MP4") | file.getName().endsWith("mp3")) {

                    Text text = new Text(file.toString().substring(file.toString().lastIndexOf("\\")+1));
                    text.setFont(new Font("Nyala",20));

                    file_names.add(text);
                    new main_scene().brother_files.add( file);
                }
            }
        }else {
            System.out.println("the parent is not directory");
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //file_list.getChildren().addAll(listView);

        create_button create_button = new create_button();
        Button play = create_button.create_buttons("play");
        play.setDefaultButton(true);
        play.setTextFill(Paint.valueOf("Black"));

        play.setOnAction(event -> {
            System.out.println("playing the selected media");
            try {
                File to_play = new File(parent_directory.toString().concat( "\\" + listView.getSelectionModel().getSelectedItem().getText()));
            System.out.println(to_play.toString());
            constant.music_file_name.setValue(null);
            constant.music_file_name.setValue(to_play.toString());

        }catch (Exception e){}
        });

        play.setAlignment(Pos.BOTTOM_CENTER);

        constant.brother_musics.addListener(new ListChangeListener<File>() {
            @Override
            public void onChanged(Change<? extends File> c) {
                ObservableList<Text> file_names = FXCollections.observableArrayList();

                for (File f : c.getList()){
                    Text text = new Text(f.toString().substring(f.toString().lastIndexOf("\\")+1));
                    text.setFont(new Font("Nyala",20));
                   file_names.add(text);
                   parent_directory = f.getParentFile();
                }
                listView.setItems(file_names);
            }
        });


        file_list.getChildren().add(listView);
        file_list.getChildren().addAll(play);

        return file_list;
    }

    public static ArrayList<File> get_brother_files(File parent) {

        ArrayList<File> brother_files = new ArrayList<>();

        if (parent.isDirectory()) {
            File[] files = parent.listFiles();
            for (File file : files) {
                if (file.getName().endsWith("mp4") | file.getName().endsWith("Mp4") |
                        file.getName().endsWith("MP4") | file.getName().endsWith("mp3")) {
                    brother_files.add(file);
                }
            }
            return brother_files;
        } else {
            System.out.println("the parent is not directory");
            return null;
        }


    }

}
