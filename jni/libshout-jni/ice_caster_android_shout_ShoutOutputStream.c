/*
 * Created by Luis Revilla on 06.18.2012
 * Updated by Fatih Sokmen on 01.11.2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <jni.h>
#include <android/log.h>
#include <shout/shout.h>
#include <pthread.h>


void close_shout();

shout_t *shout = NULL;

void close_shout() {
	if (shout != NULL) {
		shout_close(shout);
		shout_free(shout);
		shout_shutdown();
	}
}

int init_icecast(unsigned char *urlservercastA,int puertocast,unsigned char *montajecastA,unsigned char *usuariocastA,unsigned char *passwdcastA,int IsMp3Ogg) {
	
	shout_init();

	if (!(shout = shout_new())) {
		return -1;
	}
	if (shout_set_host(shout,urlservercastA) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_protocol(shout, SHOUT_PROTOCOL_HTTP) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_port(shout,puertocast) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_password(shout,passwdcastA) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_mount(shout,montajecastA) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_user(shout,usuariocastA) != SHOUTERR_SUCCESS) {
		return -1;
	}	
	if (shout_set_format(shout, SHOUT_FORMAT_MP3) != SHOUTERR_SUCCESS) {
		return -1;
	}

	if (shout_open(shout) == SHOUTERR_SUCCESS) {
		return 1;
	} else {
		return -1;
	}	
}

jint Java_ice_caster_android_shout_ShoutOutputStream_jniSend(JNIEnv *env, jobject obj, jbyteArray array, jint lengthOfArray) {
	jbyte* pBuffer = (*env)->GetByteArrayElements(env, array, NULL);

	if (shout_send(shout, (char *)pBuffer, (int) lengthOfArray) != SHOUTERR_SUCCESS) {
		return -1;
	}		
	shout_sync(shout);
	(*env)->ReleaseByteArrayElements(env, array, pBuffer, 0);
	return 1;
} 

jint Java_ice_caster_android_shout_ShoutOutputStream_jniInit(JNIEnv*  env,jobject  this,jstring urlservercast,jint puertocast,jstring montajecast,jstring usuariocast,jstring passwdcast) {
	const unsigned char  *urlservercastA=NULL;
	const unsigned char  *montajecastA=NULL;
	const unsigned char  *usuariocastA=NULL;
	const unsigned char  *passwdcastA=NULL;
	urlservercastA = (*env)->GetStringUTFChars(env,urlservercast, 0);
	montajecastA = (*env)->GetStringUTFChars(env,montajecast, 0);
	usuariocastA = (*env)->GetStringUTFChars(env,usuariocast, 0);
	passwdcastA = (*env)->GetStringUTFChars(env,passwdcast, 0);	
	return init_icecast((unsigned char*)urlservercastA,puertocast,(unsigned char*)montajecastA,(unsigned char*)usuariocastA,(unsigned char*)passwdcastA, 0);
}

jint Java_ice_caster_android_shout_ShoutOutputStream_jniClose(JNIEnv* env, jobject obj) {
	close_shout();
	return 1;
}