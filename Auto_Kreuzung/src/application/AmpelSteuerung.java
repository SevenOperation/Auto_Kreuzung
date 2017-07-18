package application;


import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class AmpelSteuerung {
	@FXML
	Circle green;

	@FXML
	Circle yellow;

	@FXML
	Circle red;

	@FXML
	Polygon car;
	
	@FXML
	AnchorPane screen;
	
	boolean moveable = true;
	double speed = 1.0;
	Thread movecarThread;

	@FXML
	public void initialize() {
		Timer ampel = new Timer();
		ampel.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				switchAmpel();
			}
		}, 3000);
		movecarThread = new Thread(moveCar);
		movecarThread.start();
	}
    
	Runnable moveCar = new Runnable() {
    	public void run() {
				while (moveable) {
						synchronized (this) {
							try {
								wait(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						car.setLayoutX(car.getLayoutX() + speed);
						if(car.getLayoutX() > 1024){
							car.setLayoutX(90);
						}
					}
				}
    	
	};
	
    @FXML
	public void keyPressed(KeyEvent event){
		
		if(event.getText().equals("s")){
			car.setLayoutY(car.getLayoutY() + 1);
		}
		if(event.getText().equals("w")){
			car.setLayoutY(car.getLayoutY() - 1);
		}
		if(event.getText().equals("d")){
			car.setLayoutX(car.getLayoutX() + 1);
		}
		if(event.getText().equals("a")){
			car.setLayoutX(car.getLayoutX() - 1);
		}
		
		
	}
	
	
	public void switchAmpel() {
		
		if (green.getFill().equals(Color.web("90FF92")) && yellow.getFill().equals(Color.web("000000"))) {
			yellow.setFill(Color.YELLOW);
			speed = 2.0;
			startTimer(2000);
		} else if (yellow.getFill().equals(Color.YELLOW) && green.getFill().equals(Color.web("90FF92"))) {
			green.setFill(Color.BLACK);
			speed = 3.0;
			startTimer(2000);
		} else if (yellow.getFill().equals(Color.YELLOW) && green.getFill().equals(Color.web("000000"))
				&& red.getFill().equals(Color.BLACK)) {
			red.setFill(Color.RED);
			speed = 1.0;
			yellow.setFill(Color.BLACK);
			moveable = false;
			movecarThread.currentThread().interrupt();
			startTimer(10000);
		} else if (red.getFill().equals(Color.RED) && yellow.getFill().equals(Color.BLACK)) {
			yellow.setFill(Color.YELLOW);
			startTimer(1500);
		} else if (red.getFill().equals(Color.RED) && yellow.getFill().equals(Color.YELLOW)) {
			yellow.setFill(Color.BLACK);
			red.setFill(Color.BLACK);
			green.setFill(Color.web("90FF92"));
			moveable = true;
			movecarThread = new Thread(moveCar);
			movecarThread.start();
			startTimer(1500);
		}

	}

	public void startTimer(long delay) {
		Timer ampel = new Timer();
		ampel.schedule(new TimerTask() {

			@Override
			public void run() {

				// TODO Auto-generated method stub
				switchAmpel();
			}
		}, delay);
	}
    }
