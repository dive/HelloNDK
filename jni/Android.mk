LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE    := libbubble-sort
LOCAL_SRC_FILES := bubble-sort.c
LOCAL_CFLAGS := -DANDROID_NDK
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
