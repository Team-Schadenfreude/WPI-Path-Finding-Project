package GUI;

import java.util.HashMap;

import javafx.geometry.Side;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ChangeListView {
	private void changeListView(ComboBox comboBox) {
    	comboBox.setButtonCell(new ListCell<Object>() {
            @Override
            protected void updateItem(Object t, boolean bln) {
                super.updateItem(t, bln);
                if (bln) {
                    setText("");
                } else {
                    setText(getStringField(t));
                }
            }
        });

        comboBox.setConverter(new StringConverter() {
        private java.util.Map<String, Object> map = new HashMap<>();

            @Override
            public String toString(Object t) {
                if (t != null) {
                    String str = getStringField(t);
                    map.put(str, t);
                    return str;
                } else {
                    return "";
                }
            }

            @Override
            public Object fromString(String string) {
                if (!map.containsKey(string)) {
                    comboBox.setValue(null);
                    comboBox.getEditor().clear();
                    return null;
                }
                return map.get(string);
            }
        });

        comboBox.setCellFactory(new Callback<ListView<Object>, ListCell<Object>>() {
            @Override
            public ListCell<Object> call(ListView<Object> p) {
                ListCell cell = new ListCell<Object>() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            //setText(getStringField(item));
                        	((MenuButton)item).setPopupSide(Side.RIGHT);
                        	((MenuButton)item).setPrefWidth(150); 
                        	((MenuButton)item).setPrefHeight(this.getMaxHeight());
                            setGraphic((MenuButton)item);
                            setOnMouseEntered(e -> ((MenuButton)item).show());
                            
                            //setOnMouseExited(e -> ((MenuButton)item).hide());
                            
                            
                        }
                    }
                };return cell;

            }
        });
    }
    
    public String getStringField(Object o) {
        return ((MenuButton) o).getText();
    }
    
}
