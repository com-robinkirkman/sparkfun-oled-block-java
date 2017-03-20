#include "jni.h"
#include "oled/Edison_OLED.h"
#include "gpio/gpio.h"

#include <stdlib.h>
#include <string.h>

#define BUFFER_SIZE 384

// Define an edOLED object:
static edOLED oled;

// Pin definitions:
// All buttons have pull-up resistors on-board, so just declare
// them as regular INPUT's
static gpio BUTTON_UP(47, INPUT);
static gpio BUTTON_DOWN(44, INPUT);
static gpio BUTTON_LEFT(165, INPUT);
static gpio BUTTON_RIGHT(45, INPUT);
static gpio BUTTON_SELECT(48, INPUT);
static gpio BUTTON_A(49, INPUT);
static gpio BUTTON_B(46, INPUT);


/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    isUpPressed0
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_robinkirkman_edison_sfo_SFOled_isUpPressed0
  (JNIEnv *jni, jclass clazz) {
	return !BUTTON_UP.pinRead();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    isDownPressed0
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_robinkirkman_edison_sfo_SFOled_isDownPressed0
  (JNIEnv *jni, jclass clazz) {
	return !BUTTON_DOWN.pinRead();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    isLeftPressed0
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_robinkirkman_edison_sfo_SFOled_isLeftPressed0
  (JNIEnv *jni, jclass clazz) {
	return !BUTTON_LEFT.pinRead();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    isRightPressed0
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_robinkirkman_edison_sfo_SFOled_isRightPressed0
  (JNIEnv *jni, jclass clazz) {
	return !BUTTON_RIGHT.pinRead();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    isSelectPressed0
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_robinkirkman_edison_sfo_SFOled_isSelectPressed0
  (JNIEnv *jni, jclass clazz) {
	return !BUTTON_SELECT.pinRead();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    isAPressed0
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_robinkirkman_edison_sfo_SFOled_isAPressed0
  (JNIEnv *jni, jclass clazz) {
	return !BUTTON_A.pinRead();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    isBPressed0
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_robinkirkman_edison_sfo_SFOled_isBPressed0
  (JNIEnv *jni, jclass clazz) {
	return !BUTTON_B.pinRead();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    begin0
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_robinkirkman_edison_sfo_SFOled_begin0
  (JNIEnv *jni, jclass clazz) {
	oled.begin();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    invert0
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_robinkirkman_edison_sfo_SFOled_invert0
  (JNIEnv *jni, jclass, jboolean invert) {
	oled.invert(invert);
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    display0
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_robinkirkman_edison_sfo_SFOled_display0
  (JNIEnv *jni, jclass clazz) {
	oled.display();
}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    write0
 * Signature: ([BII)V
 */
JNIEXPORT void JNICALL Java_com_robinkirkman_edison_sfo_SFOled_write0
  (JNIEnv *jni, jclass, jbyteArray b) {

	jboolean isCopy;
	jbyte* b_bytes = jni->GetByteArrayElements(b, &isCopy);

	memmove(oled.screenmemory, b_bytes, BUFFER_SIZE);

	jni->ReleaseByteArrayElements(b, b_bytes, 0);

}

/*
 * Class:     com_robinkirkman_edison_sfo_SFOled
 * Method:    read0
 * Signature: ([BII)V
 */
JNIEXPORT void JNICALL Java_com_robinkirkman_edison_sfo_SFOled_read0
  (JNIEnv *jni, jclass, jbyteArray b) {
	jboolean isCopy;
	jbyte* b_bytes = jni->GetByteArrayElements(b, &isCopy);

	memmove(b_bytes, oled.screenmemory, BUFFER_SIZE);

	jni->ReleaseByteArrayElements(b, b_bytes, 0);

}
