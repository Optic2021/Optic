package com.example.optic;

import com.example.optic.AppControllers.ModPGPageAppController;
import com.example.optic.entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerEventList extends GraphicController {
    @FXML
    private Pane id2;
    @FXML
    private ListView events;

    @Override
    public void setUserVariables(String user) throws IOException {
        this.setList();
    }

    public void setList() throws IOException {
        this.events.getItems().clear();
        ArrayList<Event> list = ModPGPageAppController.getEventList();
        for(int i = 0; i < list.size();i++){
            events.getItems().add(list.get(i).getFormattedText());
        }
        events.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> cell = new ListCell<String>() {

                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            Text text = new Text(item);
                            text.wrappingWidthProperty().bind(events.widthProperty().subtract(40));
                            setGraphic(text);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void exitListButton(ActionEvent e){
        Stage list = (Stage) id2.getScene().getWindow();
        list.close();
    }
}
