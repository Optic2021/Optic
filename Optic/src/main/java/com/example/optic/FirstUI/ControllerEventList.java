package com.example.optic.FirstUI;

import com.example.optic.app_controllers.ModPGPageAppController;
import com.example.optic.app_controllers.RefReportPlayer;
import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.entities.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class ControllerEventList extends GraphicController {
    @FXML
    private Pane id2;
    @FXML
    private ListView events;

    @Override
    public void setUserVariables(String user){
        List<Event> list;
        if (user.contains("Giocatore")) {//uso la dao del player
            list = UserProfileAppController.getEventList();
            this.setList(list);
        }else if (user.contains("Admin")) {//uso la dao dell'admin
            list = ModPGPageAppController.getEventList();
            this.setList(list);
        } else if (user.contains("Arbitro")) {//uso la dao del referee
            list = RefReportPlayer.getEventList();
            this.setList(list);
        }
    }

    public void setList(List<Event> list){
        this.events.getItems().clear();
        for(int i = 0; i < list.size();i++){
            events.getItems().add(list.get(i).getFormattedText());
        }
        events.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new ListCell<String>() {
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            Text text = new Text(item);
                            text.wrappingWidthProperty().bind(events.widthProperty().subtract(40));
                            setGraphic(text);
                        }
                    }
                };
            }
        });
    }

    public void exitListButton(){
        Stage list = (Stage) id2.getScene().getWindow();
        list.close();
    }
}