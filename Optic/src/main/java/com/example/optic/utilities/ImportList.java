package com.example.optic.utilities;

import com.example.optic.GraphicController;
import com.example.optic.Optic;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImportList {

    private ImportList(){/*Does nothing*/}

    public static void eventList(Label userType, Pane id) {
        try {
            Stage list = new Stage();
            Stage obj = (Stage) id.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/eventList.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 580);
            GraphicController controller = fxmlLoader.getController();
            controller.setUserVariables(userType.getText());
            scene.setFill(Color.TRANSPARENT);
            list.setResizable(false);
            list.initOwner(obj);
            list.initModality(Modality.APPLICATION_MODAL);
            list.initStyle(StageStyle.TRANSPARENT);
            list.setScene(scene);
            list.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
