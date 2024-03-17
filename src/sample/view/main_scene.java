package sample.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import sample.Main;
import sample.constants.constants;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class main_scene implements Main.full_screened ,Main.zooming{
    public static MediaView mediaView = new MediaView();
    public static StackPane media_stack = new StackPane(mediaView);
    public static File file = null;
    Slider media_slider = new Slider();
    Label media_running_time = new Label("00 : 00");
    Label media_full_time = new Label("00 : 00");
    public BooleanProperty hide = new SimpleBooleanProperty(false);
    public BooleanProperty isFullscreen = new SimpleBooleanProperty(false);
    public Slider media_volume_slider = new Slider(0,100,50);
    private Duration back_forth_duration = new Duration(10_000);
    public static ArrayList<File> brother_files = new ArrayList<>();
    private int current_playlist = 0;

    public static constants constant = new constants();
    media_files media_files = new media_files();

    int playlist_window_added = 0;
    VBox vBox;

    public Scene root(){


        constant.music_file_name.addListener((type,before,after )->{
            if (after!=null) {
                media_running_time.setText("00 : 00 : 00");
                try {
                    mediaView.getMediaPlayer().stop();
                } catch (Exception e) {}
                mediaView.setMediaPlayer(Media_handle(new File(after)));
                if (constant.files_opened_by_open_button) {
                    constant.brother_musics.remove(0, constant.brother_musics.size());
                    constant.brother_musics.addAll(media_files.get_brother_files(new File(after).getParentFile()));
                    constant.files_opened_by_open_button = false;
                }
            }
        });

        constant.music_file_name.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(" new change listener" + newValue);
            }
        });



        {//bottom buttons setting up
            VBox bottom = all_bottom();
            StackPane.setAlignment(bottom,Pos.BOTTOM_CENTER);
            media_stack.getChildren().add(bottom);
            media_stack.setStyle("-fx-background-color: black");
        }

        Scene main_scene = new Scene(media_stack,500,400);
        mediaView.setFitHeight(400);
        mediaView.setFitWidth(500);
        main_scene.widthProperty().addListener((one,two,three)->{
            mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(three.doubleValue());
            System.out.println(three.doubleValue());
        });
        main_scene.heightProperty().addListener((one,two,three)->{
            mediaView.setPreserveRatio(true);
            mediaView.setFitHeight(three.doubleValue());
            System.out.println(three.doubleValue());
        });


        media_volume_slider.valueProperty().addListener((one, two, three) -> {
            try {
                mediaView.getMediaPlayer().setVolume(three.doubleValue() * 0.01);
            }catch (Exception e){}
            System.out.println(media_volume_slider.getValue());
        });

        return main_scene;
    }

    private VBox all_bottom(){
        VBox Bottom = new VBox();
        HBox bottom_center;
        HBox bottom_right;
        HBox bottom_left;

        hide.addListener((one,two,three)->{
            if (three.booleanValue()) Bottom.setVisible(false);
            else Bottom.setVisible(true);
        });

        { //button of bottom left and adding them to the hbox


            //open Button
                    Button open = new create_button().create_buttons("Open");

                    open.setOnAction(event -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("open your file");


                        File dialog_file;
                        //setting initial directory for the dialog from the existing file if exist and null if does not exist
                        try {
                            if (mediaView.getMediaPlayer().getMedia().getSource() != null) {
                                try {
                                    fileChooser.setInitialDirectory(new File(new URI(mediaView.getMediaPlayer().getMedia().getSource())).getParentFile());
                                } catch (URISyntaxException e) {
                                    dialog_file = fileChooser.showOpenDialog(null);
                                }
                            }
                        }catch (Exception e){}

                        dialog_file = fileChooser.showOpenDialog(null);
                        if (dialog_file!=null){
                            try {
                                mediaView.getMediaPlayer().stop();
                            }catch (Exception e){}
                            //mediaView.setMediaPlayer( Media_handle(dialog_file));

                            constant.files_opened_by_open_button = true;
                            constant.music_file_name.setValue(dialog_file.getAbsolutePath());
                            file = dialog_file;
                        }
                    });

            //playlist button
            Button playlist = new create_button().create_buttons("PlayList");
            playlist.setOnAction(event -> {
                try {

                    if (playlist_window_added == 1){
                        media_stack.getChildren().remove(vBox);
                        playlist_window_added = -1;
                    }else if (playlist_window_added == -1){
                        media_stack.getChildren().add(vBox);
                        playlist_window_added = 1;
                    }else if (playlist_window_added==0){
//                        if (file != null){
                            vBox = media_files.show_files(new File(constant.music_file_name.getValue()).getParentFile());
                            vBox.setPrefSize(200,300);
                            media_stack.getChildren().add(vBox);
                            playlist_window_added = 1;
                            StackPane.setAlignment(vBox,Pos.BOTTOM_CENTER);
                            StackPane.setMargin(vBox,new Insets(10,10,100,10));
//                        }else System.out.println("the file is not selected");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            bottom_left = bottom_left(open,playlist);
            bottom_left.setAlignment(Pos.CENTER_LEFT);
        }

        { //button of bottom center and adding them to the hbox

            //pre button
            Button pre = new create_button().create_buttons("Pre");
            pre.setOnAction(event -> {
                    System.out.println("playing previous item");
                    if (brother_files!=null) {
                        try {
                            mediaView.getMediaPlayer().stop();
                        } catch (Exception e) {
                        }
                        current_playlist -= 1;
//                        mediaView.setMediaPlayer(Media_handle(brother_files.get(current_playlist<=0?0:current_playlist)));
                        constant.music_file_name.setValue(brother_files.get(current_playlist<=0?0:current_playlist).toString());
                    }
            });
            //backward button
            Button backward = new create_button().create_buttons("  <  ");
            backward.setOnAction(event->{
                if (mediaView.getMediaPlayer()!=null)
                switch (mediaView.getMediaPlayer().getStatus()){
                    case PAUSED:
                    case PLAYING:
                    case STOPPED:
                        mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getCurrentTime().subtract(back_forth_duration));
                        break;
                }
            });
            //pause button
            Button pause = new create_button().create_buttons("Pause");
            pause.setOnAction(event -> {
                try {
                    switch (mediaView.getMediaPlayer().getStatus()) {
                        case PLAYING:
                            mediaView.getMediaPlayer().pause();
                            try {
                                ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("playbutton.png")));
                                imageView.setFitHeight(25);
                                imageView.setFitWidth(25);
                                pause.setGraphic(imageView);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            break;
                        case PAUSED:
                            mediaView.getMediaPlayer().play();
                            break;
                        case STOPPED:
                            mediaView.getMediaPlayer().play();
                            break;
                        default:
                            mediaView.getMediaPlayer().play();
                    }
                }catch (Exception e){}
            });
            //forward button
            Button forward = new create_button().create_buttons("  >  ");
            forward.setOnAction(event->{
                if (mediaView.getMediaPlayer()!=null)
                switch (mediaView.getMediaPlayer().getStatus()){
                    case PAUSED:
                    case PLAYING:
                    case STOPPED:
                        mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getCurrentTime().add(back_forth_duration));
                        break;
                }
            });
            //next button
            Button next =new create_button().create_buttons("Next");
            next.setOnAction(event->{
                System.out.println("playing next item");
                if (brother_files!=null){
                    try {
                        mediaView.getMediaPlayer().stop();
                    }catch (Exception e){}
                    current_playlist+=1;
                    int temp = current_playlist<=0?1: current_playlist;
                    current_playlist = temp;
//                    mediaView.setMediaPlayer( Media_handle(brother_files.get(current_playlist<=0?1:current_playlist)));
                    constant.music_file_name.setValue(brother_files.get(current_playlist<=0?1:current_playlist).toString());
                }
            });


            bottom_center = bottom_center(pre,backward,pause,forward,next);
            bottom_center.setAlignment(Pos.CENTER);
        }

        {//setting up the button of the bottom right
            //full_screen button
            Button full_screen = new create_button().create_buttons(" [ ] ");
            full_screen.setOnAction(event -> {
                if (isFullscreen.getValue())
                    isFullscreen.setValue(false);
                else
                    isFullscreen.setValue(true);
            });

            bottom_right = bottom_right(full_screen);
            bottom_right.setAlignment(Pos.CENTER_RIGHT);

        }

        HBox hBox_slider = slider_hbox();

        HBox all_hbox = new HBox(10);
        all_hbox.getChildren().addAll(bottom_left,bottom_center,bottom_right);
        HBox.setHgrow(bottom_left,Priority.ALWAYS);
        HBox.setHgrow(bottom_right,Priority.ALWAYS);
        HBox.setHgrow(bottom_center,Priority.ALWAYS);
        all_hbox.setAlignment(Pos.CENTER);
        all_hbox.setPadding(new Insets(0,10,5,10));

        Bottom.getChildren().addAll(hBox_slider,all_hbox);
        Bottom.setAlignment(Pos.BOTTOM_CENTER);
        return Bottom;
    }
    private HBox bottom_left(Button... buttons){
        HBox bottomLeft = new HBox(5);
        bottomLeft.getChildren().addAll(buttons);
        bottomLeft.setAlignment(Pos.CENTER_LEFT);
        return bottomLeft;
    }
    private HBox bottom_right(Button... buttons){
        HBox bottomRight = new HBox(5);
        bottomRight.getChildren().addAll(buttons);
        bottomRight.setAlignment(Pos.CENTER_RIGHT);
        return bottomRight;
    }
    private HBox bottom_center(Button... buttons){
        HBox bottomCenter = new HBox(10);
        bottomCenter.getChildren().addAll(buttons);
        bottomCenter.setAlignment(Pos.CENTER);
        return bottomCenter;
    }


    private HBox slider_hbox(){
        HBox hBox = new HBox(5);
        {
            media_full_time.setTextFill(Paint.valueOf("White"));
            media_running_time.setTextFill(Paint.valueOf("White"));

            media_slider.setOnMousePressed(event -> {
                try {
                    if (mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING |
                            mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED) {
                                mediaView.getMediaPlayer().seek(Duration.seconds(media_slider.getValue()));
                    }
                }catch (NullPointerException e){}
            });

            media_slider.setOnMouseDragged(event -> {
                try {
                    if (mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING |
                            mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED) {
                        mediaView.getMediaPlayer().seek(Duration.seconds(media_slider.getValue()));
                    }
                }catch (NullPointerException e){ }
            });
        }
        hBox.setPadding(new Insets(10));
        HBox.setHgrow(media_slider, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(media_running_time,media_slider,media_full_time);
        return hBox;
    }

    private void media_changed(Duration max){
        media_slider.setMax(max.toSeconds());
        media_full_time.setText(String.valueOf((int)max.toHours()<10?"0"+(int)max.toHours():
                (int)max.toHours()) +" : "+
                String.valueOf((int)max.toMinutes()%60<10?"0"+(int)max.toMinutes()%60:
                        (int)max.toMinutes()%60) +" : "+
                String.valueOf((int)(max.toSeconds()%60)<10?"0"+(int)(max.toSeconds()%60):
                        (int)(max.toSeconds()%60)));

    }

    public MediaPlayer Media_handle(File media_file) {
        if (media_file != null) {
            Media media = new Media(media_file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    mediaView.setMediaPlayer(mediaPlayer);
                    media_changed(mediaPlayer.getTotalDuration());
                    mediaPlayer.setVolume(media_volume_slider.getValue());
                }
            });

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaView.getMediaPlayer().stop();
                    mediaView.getMediaPlayer().seek(new Duration(0));
                    media_slider.setValue(0);
                }
            });

            mediaPlayer.currentTimeProperty().addListener((one,two,three)->{
                if ( (int) two.toSeconds() != (int) three.toSeconds() ) {
                    media_slider.setValue(three.toSeconds());
                    media_running_time.setText(
                            String.valueOf((int) three.toHours() % 60 < 10 ? "0" + (int) three.toHours() % 60 :
                                    (int) three.toHours() % 60) + " : " +
                            String.valueOf((int) three.toMinutes() % 60 < 10 ? "0" + (int) three.toMinutes() % 60 :
                                    (int) three.toMinutes() % 60) + " : " +
                            String.valueOf((int) three.toSeconds() % 60 < 10 ? "0" + (int) three.toSeconds() % 60 :
                                    (int) three.toSeconds() % 60));
                }
            });

            mediaPlayer.play();
            return mediaPlayer;
        }else return null;
    }

    @Override
    public void full_screen(boolean isFullscreen, double width, double height) {
        if (isFullscreen){
            mediaView.setPreserveRatio(false);
            mediaView.setFitWidth(width);
            mediaView.setFitHeight(height);
            System.out.println("full screen media view " + width +height);
        }else{
            mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(width);
            mediaView.setFitHeight(height);
            System.out.println("not full screen media view");
        }
    }

    @Override
    public void zoom_in(boolean isZoom_in, double zooming_amount) {
            mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(mediaView.getFitWidth() + zooming_amount);
            mediaView.setFitHeight(mediaView.getFitHeight() + zooming_amount);

    }
}
