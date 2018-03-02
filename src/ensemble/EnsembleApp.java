package ensemble;


import ensemble.control.Popover;
import ensemble.control.TitledToolBar;
import ensemble.generated.Samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stagecontroller.ControlledStage;
import stagecontroller.StageController;
//显示界面的主控制程序
public class EnsembleApp extends Application implements ControlledStage{
    //主界面唤醒ID
	public static String mainViewID = "Ensemble";
	//登陆界面唤醒ID
    public static String loginViewID = "LoginView";
    //登陆界面的样式
    public static String loginViewRes = "/login/welcome_fxml.fxml";
    //界面控制器，负责登陆界面到主界面的切换
    private static StageController controller;
    
    private Button login;
    
    private Stage stage1;
    private Stage stage2;

    private static int count = 0;
    
    private static final String OS_ARCH = System.getProperty("ensemble.os.arch", System.getProperty("os.arch"));
    public static final boolean IS_EMBEDDED = "arm".equals(OS_ARCH)  ;
    public static final boolean IS_DESKTOP = !IS_EMBEDDED;
    public static final boolean PRELOAD_PREVIEW_IMAGES = true;
    public static final boolean SHOW_HIGHLIGHTS = IS_DESKTOP;
    public static final boolean SHOW_MENU = Boolean.getBoolean("ensemble.menu.show");
    public static final boolean SELECT_IOS_THEME = false;
    private static final int TOOL_BAR_BUTTON_SIZE = 30;
    private Scene scene;
    private Pane root;
    private TitledToolBar toolBar;
    private Button backButton;
    private Button forwardButton;
    private Button homeButton;
    private ToggleButton listButton;
    private PageBrowser pageBrowser;
    private Popover sampleListPopover;
    private MenuBar menuBar;

    static {
        System.setProperty("java.net.useSystemProxies", "true");
        Logger.getLogger(EnsembleApp.class.getName()).finer("IS_EMBEDDED = " + IS_EMBEDDED);
        Logger.getLogger(EnsembleApp.class.getName()).finer("IS_DESKTOP = " + IS_DESKTOP);
    }

    @Override public void init() throws Exception {
        // CREATE ROOT
        root = new Pane() {
            @Override protected void layoutChildren() {
                super.layoutChildren();
                final double w = getWidth();
                final double h = getHeight();
                final double menuHeight = SHOW_MENU ? menuBar.prefHeight(w) : 0;
                final double toolBarHeight = toolBar.prefHeight(w);
                if (menuBar != null) {
                    menuBar.resize(w, menuHeight);
                }
                toolBar.resizeRelocate(0, menuHeight, w, toolBarHeight);
                pageBrowser.setLayoutY(toolBarHeight + menuHeight);
                pageBrowser.resize(w, h-toolBarHeight);
                pageBrowser.resize(w, h - toolBarHeight - menuHeight);
                sampleListPopover.autosize();
                Point2D listBtnBottomCenter = listButton.localToScene(listButton.getWidth()/2, listButton.getHeight());
                sampleListPopover.setLayoutX((int)listBtnBottomCenter.getX()-50);
                sampleListPopover.setLayoutY((int)listBtnBottomCenter.getY()+20);      
            }
        };
        // CREATE MENUBAR
        if (SHOW_MENU) {
            menuBar = new MenuBar();
            menuBar.setUseSystemMenuBar(true);
            ToggleGroup screenSizeToggle = new ToggleGroup();
            Menu menu = new Menu("Screen size");
            menuBar.getMenus().add(menu);
            screenSizeToggle.selectToggle(screenSizeToggle.getToggles().get(0));

            root.getChildren().add(menuBar);
        }
        // CREATE TOOLBAR
        toolBar = new TitledToolBar();
        root.getChildren().add(toolBar);
        backButton = new Button();
        backButton.setId("back");
        backButton.getStyleClass().add("left-pill");
        backButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        forwardButton = new Button();
        forwardButton.setId("forward");
        forwardButton.getStyleClass().add("center-pill");
        forwardButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        homeButton = new Button();
        homeButton.setId("home");
        homeButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        homeButton.getStyleClass().add("right-pill");
        HBox navButtons = new HBox(0,backButton,forwardButton,homeButton);
        listButton = new ToggleButton();
        listButton.setId("list");
        listButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        HBox.setMargin(listButton, new Insets(0, 0, 0, 7));
        backButton.setGraphic(new Region());
        forwardButton.setGraphic(new Region());
        homeButton.setGraphic(new Region());
        listButton.setGraphic(new Region());
        toolBar.addLeftItems(navButtons,listButton);

        // create PageBrowser
        pageBrowser = new PageBrowser();
        toolBar.titleTextProperty().bind(pageBrowser.currentPageTitleProperty());
        
        root.getChildren().add(0, pageBrowser);
        pageBrowser.goHome();
        // wire nav buttons
        backButton.setOnAction((ActionEvent event) -> {
            pageBrowser.backward();
        });
        backButton.disableProperty().bind(pageBrowser.backPossibleProperty().not());
        forwardButton.setOnAction((ActionEvent event) -> {
            pageBrowser.forward();
        });
        forwardButton.disableProperty().bind(pageBrowser.forwardPossibleProperty().not());
        homeButton.setOnAction((ActionEvent event) -> {
            pageBrowser.goHome();
        });
        homeButton.disableProperty().bind(pageBrowser.atHomeProperty());

        // create and setup list popover
        sampleListPopover = new Popover();
        sampleListPopover.setPrefWidth(440);
        root.getChildren().add(sampleListPopover);
        final SamplePopoverTreeList rootPage = new SamplePopoverTreeList(Samples.ROOT,pageBrowser);
        listButton.setOnMouseClicked((MouseEvent e) -> {
            if (sampleListPopover.isVisible()) {
                sampleListPopover.hide();
            } else {
                sampleListPopover.clearPages();
                sampleListPopover.pushPage(rootPage);
                sampleListPopover.show(() -> {
                    listButton.setSelected(false);
                });
            }
        });

       
    }

    private void setStylesheets() {
        final String EXTERNAL_STYLESHEET = "http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600";
        
        scene.getStylesheets().setAll("EnsembleStylesCommon.css");
        Thread backgroundThread = new Thread(() -> {
            try {
                URL url = new URL(EXTERNAL_STYLESHEET);
                try (
                        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                        Reader newReader = Channels.newReader(rbc, "ISO-8859-1");
                        BufferedReader bufferedReader = new BufferedReader(newReader)
                        ) {
                    // Checking whether we can read a line from this url
                    // without exception
                    bufferedReader.readLine();
                }
                Platform.runLater(() -> {
                    // when succeeded add this stylesheet to the scene
                    scene.getStylesheets().add(EXTERNAL_STYLESHEET);
                });
            }catch (MalformedURLException ex) {
                Logger.getLogger(EnsembleApp.class.getName()).log(Level.FINE, "Failed to load external stylesheet", ex);
            }catch (IOException ex) {
                    Logger.getLogger(EnsembleApp.class.getName()).log(Level.FINE, "Failed to load external stylesheet", ex);
                }
        }, "Trying to reach external styleshet");
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    @Override public void start(final Stage stage) throws Exception {
        controller = new StageController();
        
        controller.loadStage(loginViewID, loginViewRes, StageStyle.UNDECORATED);
                       
        // CREATE SCENE
        scene = new Scene(root, 1024, 768, Color.BLACK);      
        setStylesheets();
                                     
        controller.addStage(mainViewID, stage);
        stage1 = controller.getStage(mainViewID);
        stage1.setScene(scene);
        stage.setTitle(mainViewID);
        //返回舞台1实例
        stage1=controller.getStage(mainViewID);
        //返回舞台2实例
        stage2 = controller.getStage(loginViewID);
        Parent root2 = FXMLLoader.load(getClass().getResource("/login/welcome_fxml.fxml"));
        Scene scene2 = new Scene(root2, 300, 275);
        stage2.setTitle("Welcome");
        stage2.setScene(scene2);
        //切换当前舞台
        controller.setStage(loginViewID);
        stage2.show();
        controller.setPrimaryStage(mainViewID, controller.getStage(mainViewID));                       
    }
    //登陆按钮点击事件
    @FXML protected void clicked(ActionEvent e){
        if(count == 0){
            controller.setStage(mainViewID, loginViewID);
        }
        
    }
    //注册界面控制器
    public void setStageController(StageController stageController) {
        this.controller = stageController;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
