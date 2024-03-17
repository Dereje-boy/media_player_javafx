package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.view.main_scene;
import sample.constants.*;
import sample.view.media_files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    List<File> files_dragged= new ArrayList<>();
    main_scene main_scene = new main_scene();
    full_screened full_screened;
    zooming zooming;
    constants constant = new constants();

    @Override
    public void start(Stage primaryStage) throws Exception{

        constant.music_file_name.addListener((type,before,after )->{
            if (after!=null) {
                System.out.println("music file changed to " + constant.music_file_name.getValue());
                primaryStage.setTitle(new File(after).getName());
            }
        });

        if (full_screened==null & zooming==null){
            full_screened  = main_scene;
            zooming  = main_scene;
        }
        Scene scene = main_scene.root();
        primaryStage.setFullScreenExitHint("");
        scene.setOnMouseClicked(event -> {

            if (event.getButton()== MouseButton.MIDDLE){
                if (primaryStage.isFullScreen()) {
                    primaryStage.setFullScreen(false);
                    full_screened.full_screen(false,primaryStage.getMaxWidth(),
                            primaryStage.getScene().getHeight());
                } else {
                    primaryStage.setFullScreen(true);
                    full_screened.full_screen(true,primaryStage.getScene().getWidth(),
                            primaryStage.getScene().getHeight());
                }

            }

            if (event.getClickCount()==2){
                if (event.getButton()== MouseButton.SECONDARY) {
                    if (primaryStage.isFullScreen()) {
                        primaryStage.setFullScreen(false);
                    } else {
                        primaryStage.setFullScreen(true);
                    }
                }
                return;
            }
            if (main_scene.hide.get())
            main_scene.hide.setValue(false);
            else main_scene.hide.setValue(true);
        });
        main_scene.isFullscreen.addListener((one,two,three)->{
            if (three.booleanValue()) primaryStage.setFullScreen(true);
            else primaryStage.setFullScreen(false);
        });
        scene.setOnScroll(event -> {
            if (!event.isControlDown()) {
                if (event.getDeltaY() > 0) {
                    main_scene.media_volume_slider.setValue(main_scene.media_volume_slider.getValue() + 5);
                } else if (event.getDeltaY() < 0) {
                    main_scene.media_volume_slider.setValue(main_scene.media_volume_slider.getValue() - 5);
                }
            }else {
                System.out.println("zooming up the media view");
                zooming.zoom_in(false,event.getDeltaY());
            }
        });
        primaryStage.setTitle("DiT video player");
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

        primaryStage.getScene().setOnDragOver(event->{
            if (event.getDragboard().hasFiles()){
                event.acceptTransferModes(TransferMode.COPY);
            }
        });

        primaryStage.getScene().setOnDragDropped(event -> {
            files_dragged = event.getDragboard().getFiles();

            if (files_dragged.get(0).isDirectory()){
                File[] f = files_dragged.get(0).listFiles();
                for (File file:f){
                    if (file.toString().endsWith(".mp3") || file.toString().endsWith(".mp4") || file.toString().endsWith(".MP4")) {
                        constant.music_file_name.setValue(file.toString());
                        break;
                    }
                }
            }else {
                constant.music_file_name.setValue(files_dragged.get(0).toString());
        }
            constant.brother_musics.remove(0, constant.brother_musics.size());
            constant.brother_musics.addAll(media_files.get_brother_files(new File(constant.music_file_name.getValue()).getParentFile()));
            constant.files_opened_by_open_button = false;
        });
        primaryStage.show();
        primaryStage.setAlwaysOnTop(true);
    }


    public static void main(String[] args) {
        launch(args);
    }


    public interface full_screened{
        void full_screen(boolean isFullscreen,double width, double height);
    }


    public interface zooming{
        void zoom_in(boolean isZoom_in,double zooming_amount);
    }

}
