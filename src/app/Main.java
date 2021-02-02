package app;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

    //END TABLE
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {


            Parent root = FXMLLoader.load(getClass().getResource("/app/views/Login.fxml"));
            primaryStage.setTitle("NexTrack | Authentification");
            primaryStage.getIcons().add(new Image("/app/images/icon.png"));
            primaryStage.setResizable(false);
            //primaryStage.setScene(new Scene(root, 1100, 560));
            primaryStage.initStyle(StageStyle.UNDECORATED);

            primaryStage.setScene(new Scene(root, 900, 600));
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            //move around here
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }

            });

            primaryStage.setOnShown(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {

                }
            });
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
