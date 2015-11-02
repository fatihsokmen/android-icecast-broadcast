LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := shout-jni
LOCAL_CFLAGS += -I$(LOCAL_PATH)/../include -fsigned-char
ifeq ($(TARGET_ARCH),arm)
	LOCAL_CFLAGS += -march=armv6 -marm -mfloat-abi=softfp -mfpu=vfp
endif

LOCAL_SHARED_LIBRARIES := libshout

LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog

LOCAL_SRC_FILES := \
	ice_caster_android_shout_ShoutOutputStream.c

include $(BUILD_SHARED_LIBRARY)
