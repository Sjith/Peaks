/*
 * Klasa wyszukująca krawędzie
 *
 * Author: Daniel Wojda
 *
 */

#include <jni.h>
#include <android/log.h>


#define  LOG_TAG    "imageoperations"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)



JNIEXPORT void JNICALL Java_org_danielwojda_imageoperations_EdgesDetector_converttogray(JNIEnv * env, jobject  obj)
{
	LOGE("convertogray() start");
}
