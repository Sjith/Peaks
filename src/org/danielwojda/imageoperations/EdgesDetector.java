package org.danielwojda.imageoperations;

public class EdgesDetector {

	public native void converttogray();
	
	public void runNative(){
		converttogray();
	}
		
	static {
		System.loadLibrary("imageoperations");
	}


}
