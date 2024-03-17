package sample.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.constants.constants;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class create_button {
    constants constant = new constants();
    Stage playlist_stage = new Stage();
//    VBox playlist_container = media_files.show_files(new main_scene().file == null? null : new main_scene().file.getParentFile());

    public Button create_buttons(String name){
        Button button = new Button(name);

        button.setTextFill(Paint.valueOf("white"));
        button.setBackground(new Background(new BackgroundFill
                (Paint.valueOf(Color.web("black",0).toString()),new CornerRadii(0),new Insets(0))));
        button.setBorder(new Border(
                new BorderStroke(Paint.valueOf("white"),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));

        button.setOnMouseExited(event -> {
            button.setBackground(new Background(new BackgroundFill
                    (Paint.valueOf(Color.web("black",0).toString()),
                            new CornerRadii(0),new Insets(0))));
            button.setTextFill(Paint.valueOf("white"));
            button.setBorder(new Border(
                    new BorderStroke(Paint.valueOf("white"),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));
        });

        button.setOnMouseEntered(event -> {
            button.setBackground(new Background(new BackgroundFill
                    (Paint.valueOf(Color.web("white",1).toString()),
                            new CornerRadii(0),new Insets(0))));
            button.setTextFill(Paint.valueOf("black"));
            button.setBorder(new Border(
                    new BorderStroke(Paint.valueOf("Black"),BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        });

        if (name.equals("Open")){
            button.setText("");
            try {
                ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("playButton2.png")));
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                button.setGraphic(imageView);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else if (name.equals("PlayList")){
            button.setText("");
//            button.setOnAction(event -> {
//                try {
//                    if (new main_scene().file != null){
//                        if (playlist_stage.isShowing()) {
//                            constant.playlist_stage_height = playlist_stage.getHeight();
//                            constant.playlist_stage_width = playlist_stage.getWidth();
//                            new main_scene().media_stack.getChildren().remove(playlist_container);
////                            playlist_stage.hide();
//                        }
//                        else {
//                            if (playlist_stage.getScene() == null) {
//                                playlist_stage.setScene(new Scene(
//                                        media_files.show_files(
//                                                new main_scene().file.getParentFile()),
//                                        constant.playlist_stage_width,constant.playlist_stage_height));
//                                playlist_stage.centerOnScreen();
//                            }
//                            playlist_stage.setWidth(constant.playlist_stage_width);
//                            playlist_stage.setHeight(constant.playlist_stage_height);
//                            new main_scene().media_stack.getChildren().add(playlist_container);
//                            playlist_stage.show();
//                        }
//                    }else System.out.println("the file is not selected");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            });
            try {
                ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("play_list2.png")));
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                button.setGraphic(imageView);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else if (name.equals("  <  ")){
            button.setText("");
            try {
                ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("backward.png")));
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                button.setGraphic(imageView);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else if (name.equals("  >  ")){
            button.setText("");
            try {
                ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("forward.png")));
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                button.setGraphic(imageView);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else if (name.equals("Pre")){
            button.setText("");
            try {
                ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("pre1.png")));
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                button.setGraphic(imageView);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            button.setBackground(new Background(new BackgroundFill
                    (Paint.valueOf(Color.web("black",0).toString()),new CornerRadii(0),new Insets(0))));
            button.setBorder(new Border(
                    new BorderStroke(Paint.valueOf("white"),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));

            button.setOnMouseExited(event -> {
                try {
                    ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("pre1.png")));
                    imageView.setFitHeight(25);
                    imageView.setFitWidth(25);
                    button.setGraphic(imageView);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                button.setBackground(new Background(new BackgroundFill
                        (Paint.valueOf(Color.web("black",0).toString()),
                                new CornerRadii(0),new Insets(0))));
                button.setTextFill(Paint.valueOf("white"));
                button.setBorder(new Border(
                        new BorderStroke(Paint.valueOf("white"),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));
            });

            button.setOnMouseEntered(event -> {

                button.setText("");
                try {
                    ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("pre.png")));
                    imageView.setFitHeight(25);
                    imageView.setFitWidth(25);
                    button.setGraphic(imageView);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                button.setBackground(new Background(new BackgroundFill
                        (Paint.valueOf(Color.web("white",1).toString()),
                                new CornerRadii(0),new Insets(0))));
                button.setTextFill(Paint.valueOf("black"));
                button.setBorder(new Border(
                        new BorderStroke(Paint.valueOf("Black"),BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
            });
        }
        else if (name.equals("Next")){
            button.setText("");
            try {
                ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("next1.png")));
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                button.setGraphic(imageView);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            button.setBackground(new Background(new BackgroundFill
                    (Paint.valueOf(Color.web("black",0).toString()),new CornerRadii(0),new Insets(0))));
            button.setBorder(new Border(
                    new BorderStroke(Paint.valueOf("white"),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));

            button.setOnMouseExited(event -> {
                try {
                    ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("next1.png")));
                    imageView.setFitHeight(25);
                    imageView.setFitWidth(25);
                    button.setGraphic(imageView);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                button.setBackground(new Background(new BackgroundFill
                        (Paint.valueOf(Color.web("black",0).toString()),
                                new CornerRadii(0),new Insets(0))));
                button.setTextFill(Paint.valueOf("white"));
                button.setBorder(new Border(
                        new BorderStroke(Paint.valueOf("white"),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));
            });

            button.setOnMouseEntered(event -> {

                button.setText("");
                try {
                    ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("next.png")));
                    imageView.setFitHeight(25);
                    imageView.setFitWidth(25);
                    button.setGraphic(imageView);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                button.setBackground(new Background(new BackgroundFill
                        (Paint.valueOf(Color.web("white",1).toString()),
                                new CornerRadii(0),new Insets(0))));
                button.setTextFill(Paint.valueOf("black"));
                button.setBorder(new Border(
                        new BorderStroke(Paint.valueOf("Black"),BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
            });
        }
        else if (name.equals("Pause")){
            button.setText("");
            try {
                ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("pause_black.png")));
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                button.setGraphic(imageView);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            button.setBackground(new Background(new BackgroundFill
                    (Paint.valueOf(Color.web("black",0).toString()),new CornerRadii(0),new Insets(0))));
            button.setBorder(new Border(
                    new BorderStroke(Paint.valueOf("white"),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));

            button.setOnMouseExited(event -> {
                try {
                    ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("pause_black.png")));
                    imageView.setFitHeight(25);
                    imageView.setFitWidth(25);
                    button.setGraphic(imageView);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                button.setBackground(new Background(new BackgroundFill
                        (Paint.valueOf(Color.web("black",0).toString()),
                                new CornerRadii(0),new Insets(0))));
                button.setTextFill(Paint.valueOf("white"));
                button.setBorder(new Border(
                        new BorderStroke(Paint.valueOf("white"),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));
            });

            button.setOnMouseEntered(event -> {

                button.setText("");
                try {
                    ImageView imageView = new ImageView(new Image(sample.resources.get_files.get_file("pause_white.png")));
                    imageView.setFitHeight(25);
                    imageView.setFitWidth(25);
                    button.setGraphic(imageView);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                button.setBackground(new Background(new BackgroundFill
                        (Paint.valueOf(Color.web("white",1).toString()),
                                new CornerRadii(0),new Insets(0))));
                button.setTextFill(Paint.valueOf("black"));
                button.setBorder(new Border(
                        new BorderStroke(Paint.valueOf("Black"),BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
            });
        }


        playlist_stage.setOnCloseRequest(event -> {
            constant.playlist_stage_width = playlist_stage.getWidth();
            constant.playlist_stage_height = playlist_stage.getHeight();
        });

        return button;
    }
}
