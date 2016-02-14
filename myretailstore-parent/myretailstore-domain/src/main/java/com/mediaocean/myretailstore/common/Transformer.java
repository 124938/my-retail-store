package com.mediaocean.myretailstore.common;

public interface Transformer<I, O> {
	
	O transform(I input);
}
