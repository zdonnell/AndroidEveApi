package com.zdonnell.androideveapi.link;

public interface IApiTask<T> {

	abstract int requestTypeHash();
	
	abstract T buildResponseFromDatabase();
	
}
