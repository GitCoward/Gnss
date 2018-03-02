package ensemble.samplepage;

import ensemble.Page;
import ensemble.PageBrowser;
import ensemble.SampleInfo;
import static ensemble.SampleInfo.SampleRuntimeInfo;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * Page for showing a sample
 */
public class SamplePage extends StackPane implements Page {
    static final double INDENT = 8;
    final ObjectProperty<SampleInfo> sampleInfoProperty = new SimpleObjectProperty<>();
    private final StringProperty titleProperty = new SimpleStringProperty();
    PageBrowser pageBrowser;
    final ObjectProperty<SampleRuntimeInfo> sampleRuntimeInfoProperty = new SimpleObjectProperty<>();

    public SamplePage(SampleInfo sampleInfo, String url, final PageBrowser pageBrowser) {
        sampleInfoProperty.set(sampleInfo);
        this.pageBrowser = pageBrowser;
        getStyleClass().add("sample-page");
        titleProperty.bind(new StringBinding() {
            { bind(sampleInfoProperty); }
            @Override protected String computeValue() {
                SampleInfo sample = SamplePage.this.sampleInfoProperty.get();
                if (sample != null) {
//                    System.out.println(sample);
                    return sample.name;
                } else {
                    return null;
                }
            }
        });
        sampleRuntimeInfoProperty.bind(new ObjectBinding<SampleRuntimeInfo>() {
            { bind(sampleInfoProperty); }
            @Override protected SampleRuntimeInfo computeValue() {
                return sampleInfoProperty.get().buildSampleNode();
            }
        });

        SamplePageContent frontPage = new SamplePageContent(this);
        getChildren().setAll(frontPage);
    }

    public void update(SampleInfo sampleInfo, String url) {
        sampleInfoProperty.set(sampleInfo);
    }

    @Override public ReadOnlyStringProperty titleProperty() {
        return titleProperty;
    }

    @Override public String getTitle() {
        return titleProperty.get();
    }

    @Override public String getUrl() {
        return sampleInfoProperty.get().ensemblePath;
    }

    @Override public Node getNode() {
        return this;
    }

    String apiClassToUrl(String classname) {
        String urlEnd = classname.replaceAll("\\.([a-z])", "/$1").replaceFirst("\\.([A-Z])", "/$1");
        if (classname.startsWith("javafx")) {
            return "https://docs.oracle.com/javase/8/javafx/api/"+urlEnd+".html";
        } else {
            return "https://docs.oracle.com/javase/8/docs/api/"+urlEnd+".html";
        }
    }

    /**
     * This method is equivalent to bind(ObjectBinding) as it would invoke
     * updater immediately as well as on any change to SampleInfo
     * @param updater a method that updates content for a given SampleInfo
     */
    void registerSampleInfoUpdater(final Callback<SampleInfo, Void> updater) {
        sampleInfoProperty.addListener((ObservableValue<? extends SampleInfo> ov, SampleInfo t, SampleInfo sampleInfo) -> {
            updater.call(sampleInfo);
        });
        updater.call(sampleInfoProperty.get());
    }
}
