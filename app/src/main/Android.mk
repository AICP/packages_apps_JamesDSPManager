LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_PROGUARD_FLAG_FILES := proguard.flags

LOCAL_STATIC_JAVA_LIBRARIES := \
    androidx.core_core \
    androidx.preference_preference \
    androidx.legacy_legacy-support-v13


LOCAL_SRC_FILES := $(call all-java-files-under, java)

LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res
LOCAL_RESOURCE_DIR += $(appcompat_dir)

LOCAL_AAPT_FLAGS := --auto-add-overlay

LOCAL_PRIVATE_PLATFORM_APIS := true

LOCAL_PACKAGE_NAME := JamesDSPManager

LOCAL_OVERRIDES_PACKAGES := MusicFX

LOCAL_REQUIRED_MODULES := \
    libjamesDSPImpulseToolbox \
    libjamesdsp

include $(BUILD_PACKAGE)

# Use the folloing include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
