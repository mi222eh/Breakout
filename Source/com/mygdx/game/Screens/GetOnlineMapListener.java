package com.mygdx.game.Screens;

import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

public class GetOnlineMapListener implements HttpResponseListener{
	
	public MainMenuScreen menuScreen;

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		this.menuScreen.toGameScreenWidthJson(httpResponse.getResultAsString());
	}

	@Override
	public void failed(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub
		
	}

}
