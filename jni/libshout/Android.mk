LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libshout
LOCAL_CFLAGS += -I$(LOCAL_PATH)/../include -fsigned-char
LOCAL_CFLAGS += -DHAVE_CONFIG_H=1 -ffast-math -fsigned-char -pthread -O2 -ffast-math -Werror -DHAVE_CUALQUIERVARIABLE=1

LOCAL_SHARED_LIBRARIES := libogg libvorbis

LOCAL_SRC_FILES := \
	shout.c		\
	timing		\
	mp3.c		\
	ogg.c		\
	util.c 	\
	vorbis.c	 \
	avl/avl.c		\
	httpp/httpp.c		\
	net/resolver.c		\
	net/sock.c		\
	thread/thread.c		\
	timing/timing.c    

include $(BUILD_SHARED_LIBRARY)