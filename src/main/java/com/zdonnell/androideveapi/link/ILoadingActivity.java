package com.zdonnell.androideveapi.link;

public interface ILoadingActivity {

	abstract public void dataLoading();
	
	abstract public void loadingFinished(boolean dataError);
	
}
