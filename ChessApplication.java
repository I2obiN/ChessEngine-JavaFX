//Chess  application -- Thomas Hood (2847586) & Julie-Anne Glennon (2864184) -- BScH Yr 3

//imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.PrintStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.util.Duration;

//class definition
public class ChessApplication extends Application {
	
	// private fields for this class
	private StackPane sp_mainlayout;	//layout which allows items to be positioned on top of each other
	private CustomControl cc_custom;	//control which has a board and detects mouse and keyboard events
	
	private Button btn1, btn2, btn3, btn4;
		 private Timeline timeline;
		 private Timeline timeline2;
		 private int type;
		 private TextArea status;
		 private static Label timerwhite = new Label();
		 private static Label timerblack = new Label();

		 private SimpleDoubleProperty TimeSeconds = new SimpleDoubleProperty();
		 private SimpleDoubleProperty timeSeconds = new SimpleDoubleProperty();
		 private Duration time1 = Duration.minutes(15), Time2 = Duration.seconds(900);
		 
		 
		// private PrintStream ps;
	// overridden init method
	@Override
	public void init() {
		
		// initialize the layout, create a CustomControl and it to the layout
 		sp_mainlayout = new StackPane();
 		cc_custom = new CustomControl();
 		sp_mainlayout.getChildren().add(cc_custom);
 		
		// Configure the Labels
        // Bind the timerLabel text property to the timeSeconds property
 		timerwhite.setText("15");
 		timerblack.setText("15");
 		timeSeconds.set(Duration.minutes(15).toMinutes());
 		TimeSeconds.set(Duration.minutes(15).toMinutes());
		timerwhite.textProperty().bind(timeSeconds.asString("%.2f"));
        timerwhite.setTextFill(Color.WHITE);
        timerwhite.setStyle("-fx-font-size: 2em;");
        timerblack.textProperty().bind(TimeSeconds.asString("%.2f"));
        timerblack.setTextFill(Color.BLACK);
        timerblack.setStyle("-fx-font-size: 2em;");
		
		btn1 = new Button("START");
		btn2 = new Button("STOP");
		
		btn3 = new Button("START");
		btn4 = new Button("STOP");
		
		btn1.setOnAction(new EventHandler<ActionEvent>() {
		//overridden handle method
			@Override
		public void handle(ActionEvent event) {
				 
				   if (timeline != null && type == 1){
	                    timeSeconds.set(Duration.minutes(15).toMinutes());
	                } 
				   else {
	                    timeline = new Timeline(
	                        new KeyFrame(Duration.millis(1),
	                        new EventHandler<ActionEvent>() {
	                            @Override
	                            public void handle(ActionEvent t) {
	                                Duration duration = ((KeyFrame)t.getSource()).getTime();
	                                if(Time2.greaterThan(Duration.ZERO)){
	                                time1 = time1.subtract(duration);}
	                                else{time1 = Duration.ZERO;}
	                                timeSeconds.set(time1.toMinutes());
	                                
	                            }
	                        })
	                    );
	                    
	                    timeline.setCycleCount(Timeline.INDEFINITE);
	                    timeline.play();
	                    //event.consume();
	                }
				
		}
	});
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			//overridden handle method
			@Override
		public void handle(ActionEvent event) {
				timeline.stop();
				//event.consume();
		}
	});	
	
	btn3.setOnAction(new EventHandler<ActionEvent>() {
		//overridden handle method
			public void handle(ActionEvent event) {
					
				   if (timeline2 != null && type == 2){
	                    TimeSeconds.set(Duration.minutes(15).toMinutes());
	          
	                   // TimeSeconds.set(Time2.toSeconds());
	                } 
				   else {
	                    timeline2 = new Timeline(
	                        new KeyFrame(Duration.millis(1),
	                        new EventHandler<ActionEvent>() {
	                            @Override
	                            public void handle(ActionEvent t) {
	                                Duration duration = ((KeyFrame)t.getSource()).getTime();
	                                if(Time2.greaterThan(Duration.ZERO)){
	                                Time2 = Time2.subtract(duration);}
	                                else{Time2 = Duration.ZERO;}
	                              TimeSeconds.set(Time2.toMinutes());
	                            }
	                        })
	                    );
	                    
	                    timeline2.setCycleCount(Timeline.INDEFINITE);
	                    timeline2.play();
	                    //event.consume();
	                }
				
		}
	});
		btn4.setOnAction(new EventHandler<ActionEvent>() {
			//overridden handle method
			@Override
		public void handle(ActionEvent event) {
				timeline2.stop();
				//event.consume();
		}
	});	
		
					
	}
	// overridden start method
	@Override
	public void start(Stage primaryStage) {
		
		// set the title and scene, and show the stage
		primaryStage.setTitle("Chess");
		primaryStage.setScene(new Scene(sp_mainlayout, 1000, 800));
		// create text box 
		
		TextArea status = new TextArea();
        status.setEditable(false);
        status.setPromptText("Status Updates");
        status.setStyle("-fx-font-size: 12;");
        status.setPrefWidth(200);
        status.setPrefHeight(800);
        PrintStream ps = System.out;
        System.setOut(new TextAreaPrintStream(status, ps));
	
        VBox vb = new VBox();
        vb.getChildren().add(status);
		vb.setPrefWidth(200);
				BorderPane bp = new BorderPane();
				HBox hb1 = new HBox();
				HBox hb2 = new HBox();

				hb1.setAlignment(Pos.CENTER);
				hb1.getChildren().addAll(btn3,timerblack,btn4);
				hb1.setSpacing(50);
				hb1.setStyle("-fx-background-color: #D3D3D3");
				hb1.setLayoutY(30);
				
				hb2.setAlignment(Pos.CENTER);
				hb2.getChildren().addAll(btn1,timerwhite,btn2);
				hb2.setSpacing(50);
				hb2.setStyle("-fx-background-color: #D3D3D3");
				hb2.setLayoutY(30);
				
				primaryStage.setScene(new Scene(bp, 1000, 800 ));
				bp.setCenter(sp_mainlayout);
				bp.setTop(hb1);
				bp.setBottom(hb2);
				//bp.setLeft(btn1);
				bp.setRight(vb);
				
		primaryStage.setMaxHeight(800);
		primaryStage.setMaxWidth(1000);
		primaryStage.setMinWidth(700);
		primaryStage.setMinHeight(500);
		primaryStage.show();
	}
	
	// overridden stop method
	@Override
	public void stop(){}
	
	public void appendToStatus(String Text) {
       status.appendText(Text);     
    }
	 
	public String getStatusText() {
	  return status.getText();
	}
	 
	 // Returns timer for white
	 public static double getTimerWhite(){
		 double wt = Double.parseDouble(timerwhite.getText());
		 return wt;
	 }
	 
	 // Returns timer for black
	 public static double getTimerBlack(){
		 double bt = Double.parseDouble(timerblack.getText());
		 return bt;
	 }

	// entry point into our program to launch our JavaFX application
	public static void main(String[] args) {
		launch(args);
	}
}
