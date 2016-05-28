package com.mygdx.game.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.GameSoundListener;

public class SoundHandler implements GameSoundListener{
	private float volume = 0.5f;
	Sound HitNormal1, HitNormal2, HitNormal3, Begin1, Begin2, Begin3, Player1, Player2, Player3, HitInvurnerable1, HitInvurnerable2, HitInvurnerable3, Steel1, Steel2, Steel3, Button1, Button2, Button3;
	
	public SoundHandler(){
		HitNormal1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/HitNormal1.wav"));
		HitNormal2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/HitNormal2.wav"));
		HitNormal3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/HitNormal3.wav"));
		
		Begin1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Begin1.wav"));
		Begin2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Begin2.wav"));
		Begin3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Begin3.wav"));
		
		Player1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Player1.wav"));
		Player2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Player2.wav"));
		Player3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Player3.wav"));
		
		HitInvurnerable1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Invurnerable1.wav"));
		HitInvurnerable2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Invurnerable2.wav"));
		HitInvurnerable3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Invurnerable3.wav"));
		
		Steel1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Steel1.wav"));
		Steel2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Steel2.wav"));
		Steel3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Steel3.wav"));
		
		Button1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Button1.wav"));
		Button2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Button2.wav"));
		Button3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Button3.wav"));
	}
	
	private int getNumber(){
    	int number =  1 + (int) (Math.random() * ((3 - 1) + 1));
    	return number;
	}

	@Override
	public void PlayerHit() {
		if(BreakoutSettings.Sound_On){
			int number = getNumber();
			switch(number){
			case 1:
				Player1.play();
				break;
			case 2:
				Player2.play();
				break;
			case 3:
				Player3.play();
				break;
			}
		}
	}

	@Override
	public void InvurnerableHit() {
		if(BreakoutSettings.Sound_On){
			int number = getNumber();
			switch(number){
			case 1:
				HitInvurnerable1.play();
				break;
			case 2:
				HitInvurnerable2.play();
				break;
			case 3:
				HitInvurnerable3.play();
				break;
			}
		}
	}

	@Override
	public void SteelHit() {
		int number = getNumber();
		switch(number){
		case 1:
			Steel1.play();
			break;
		case 2:
			Steel2.play();
			break;
		case 3:
			Steel3.play();
			break;
		}
		
	}

	@Override
	public void PowerupGrab() {
		if(BreakoutSettings.Sound_On){
			int number = getNumber();
			switch(number){
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
		}
	}

	@Override
	public void Begin() {
		if(BreakoutSettings.Sound_On){
			int number = getNumber();
			switch(number){
			case 1:
				Begin1.play(volume);
				break;
			case 2:
				Begin2.play(volume);
				break;
			case 3:
				Begin3.play(volume);
				break;
			}
		}
	}

	@Override
	public void Normalhit() {
		if(BreakoutSettings.Sound_On){
			int number = getNumber();
			switch(number){
			case 1:
				HitNormal1.play();
				break;
			case 2:
				HitNormal2.play();
				break;
			case 3:
				HitNormal3.play();
				break;
			}
		}
	}

	@Override
	public void ButtonSound() {
		if(BreakoutSettings.Sound_On){
			int number = getNumber();
			switch(number){
			case 1:
				Button1.play();
				break;
			case 2:
				Button2.play();
				break;
			case 3:
				Button3.play();
				break;
			}
		}
	}
}
