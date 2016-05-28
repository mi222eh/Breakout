package com.mygdx.game.Model.Entities;

public class BottomSensor {
	public void handle(Powerup powerup){
		System.out.println("Die Poerup!");
		powerup.destroy();
	}
	public void handle(Ball ball){
		ball.destroy();
	}
}
