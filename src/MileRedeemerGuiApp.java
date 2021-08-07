/**********************************************************
 *                                                        *
 *  CSCI 470/502        Assignment 6         Summer 2021  *
 *                                                        *
 *  Developer(s):  Leonart Jaos                           *
 *                                                        *
 *  Section:  0Y01                                        *
 *                                                        *
 *  Due Date/Time:  Aug 5 @ 11:59 PM                      *
 *                                                        *
 *  Purpose:  The following code loads the MileRedeemerGUI*
 *  App.FXML file and launches stages a window for the Us-*
 *  er interface.Once the Window has been exited, the prog*
 *  fram closes.                                          *
 *                                                        *
 **********************************************************/


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class MileRedeemerGuiApp extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        // load .fxml file
        Parent root = FXMLLoader.load(getClass().getResource("MileRedeemerGuiApp.fxml"));
        primaryStage.setTitle("Mile Redeemer");
        // set window dimensions
        primaryStage.setScene(new Scene(root, 800, 314));
        primaryStage.show();
        // close app after stage exits
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
