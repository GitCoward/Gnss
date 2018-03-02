package ensemble.control;


import java.io.IOException;
import java.io.InputStream;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * Special ListView designed to look like "Text... &gt;" tree list. Perhaps we ought to have customized
 * a TreeView instead of a ListView (as the TreeView already has the data model all defined).
 *
 * This implementation minimizes classes by just having the PopoverTreeList implementing everything
 * (it is the Control, the Skin, and the CellFactory all in one).
 */
public class PopoverTreeList<T> extends ListView<T> implements Callback<ListView<T>, ListCell<T>> {

    protected static final Image RIGHT_ARROW = new Image(new InputStream() {
		
		@Override
		public int read() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}
	});
    public PopoverTreeList(){
        getStyleClass().clear();
        setCellFactory(this);
    }

    @Override public ListCell<T> call(ListView<T> p) {
        return new TreeItemListCell();
    }

    protected void itemClicked(T item) {}

    private class TreeItemListCell extends ListCell<T> implements EventHandler<MouseEvent> {
        private ImageView arrow = new ImageView(RIGHT_ARROW);

        private TreeItemListCell() {
            super();
            getStyleClass().setAll("popover-tree-list-cell");
            setOnMouseClicked(this);
        }

        @Override public void handle(MouseEvent t) {
            itemClicked(getItem());
        }

        @Override protected double computePrefWidth(double height) {
            return 100;
        }

        @Override protected double computePrefHeight(double width) {
            return 44;
        }

        @Override protected void layoutChildren() {
            if (getChildren().size() < 2) getChildren().add(arrow);
            super.layoutChildren();
            final int w = (int)getWidth();
            final int h = (int)getHeight();
            final int centerX = (int)(w/2d);
            final int centerY = (int)(h/2d);
            final Bounds arrowBounds = arrow.getLayoutBounds();
            arrow.setLayoutX(w - arrowBounds.getWidth() - 12);
            arrow.setLayoutY((int)((h - arrowBounds.getHeight())/2d));
        }

        // CELL METHODS
        @Override protected void updateItem(T item, boolean empty) {
            // let super do its work
            super.updateItem(item,empty);
            // update our state
            if (item == null) { // empty item
                setText(null);
                arrow.setVisible(false);
            } else {
                setText(item.toString());
                arrow.setVisible(true);
            }
        }
    }
}
