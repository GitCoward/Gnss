package ensemble;

import ensemble.Page;
import ensemble.SampleInfo;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import ensemble.samplepage.*;

/**
 * Page showing tabs with all the source code and resources for a sample
 */
public class SourcePage extends TabPane implements Page {
    private final ObjectProperty<SampleInfo> sampleInfoProperty = new SimpleObjectProperty<>();
    private final StringProperty titleProperty = new SimpleStringProperty();

    public SourcePage() {
        getStyleClass().add("source-page");
        titleProperty.bind(new StringBinding() {
            { bind(sampleInfoProperty); }
            @Override protected String computeValue() {
                SampleInfo sample = sampleInfoProperty.get();
                if (sample != null) {
                    return sample.name+" :: Source";
                } else {
                    return null;
                }
            }
        });
        setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public void setSampleInfo(SampleInfo sampleInfo) {
        sampleInfoProperty.set(sampleInfo);
        getTabs().clear();
        for (SampleInfo.URL sourceURL : sampleInfo.getSources()) {
            getTabs().add(new SourceTab(sourceURL));
        }
    }

    @Override public ReadOnlyStringProperty titleProperty() {
        return titleProperty;
    }

    @Override public String getTitle() {
        return titleProperty.get();
    }

    @Override public String getUrl() {
        return "" + sampleInfoProperty.get().ensemblePath;
    }

    @Override public Node getNode() {
        return this;
    }
}
