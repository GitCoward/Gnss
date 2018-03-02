package ensemble;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.scene.Node;
//显示界面的接口
public interface Page {
    public ReadOnlyStringProperty titleProperty();
    public String getTitle();
    public String getUrl();
    public Node getNode();
}
