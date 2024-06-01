PRODUCT_COPY_FILES += \
    frameworks/av/media/libeffects/data/audio_effects.conf:system/etc/audio_effects.conf \
    frameworks/native/data/etc/android.hardware.location.gps.xml:system/etc/permissions/android.hardware.location.gps.xml \
    frameworks/native/data/etc/android.hardware.sensor.barometer.xml:system/etc/permissions/android.hardware.sensor.barometer.xml \
    frameworks/native/data/etc/android.hardware.sensor.gyroscope.xml:system/etc/permissions/android.hardware.sensor.gyroscope.xml \
    frameworks/native/data/etc/android.hardware.sensor.light.xml:system/etc/permissions/android.hardware.sensor.light.xml \
    frameworks/native/data/etc/android.hardware.usb.accessory.xml:system/etc/permissions/android.hardware.usb.accessory.xml \
    frameworks/native/data/etc/handheld_core_hardware.xml:system/etc/permissions/handheld_core_hardware.xml \

# Keylayout
PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/keylayout/och1970_holl_key.kl:$(TARGET_COPY_OUT_PRODUCT)/usr/keylayout/och1970_holl_key.kl 

PRODUCT_PROPERTY_OVERRIDES += ro.sf.lcd_density=280

# A/B
$(call inherit-product, $(SRC_TARGET_DIR)/product/virtual_ab_ota.mk)
AB_OTA_POSTINSTALL_CONFIG += \
    RUN_POSTINSTALL_system=true \
    POSTINSTALL_PATH_system=system/bin/otapreopt_script \
    FILESYSTEM_TYPE_system=ext4 \
    POSTINSTALL_OPTIONAL_system=true

PRODUCT_PACKAGES += \
    otapreopt_script \
    cppreopts.sh

# Bootctrl
PRODUCT_PACKAGES += \
    android.hardware.boot@1.1 \
    android.hardware.boot@1.1-service

PRODUCT_PROPERTY_OVERRIDES += \
    ro.cp_system_other_odex=1

PRODUCT_PACKAGES += \
    update_engine \
    update_engine_sideload \
    update_verifier

PRODUCT_PACKAGES_DEBUG += \
    update_engine_client

# Ims
PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/configs/permissions/privapp-permissions-mtkimsservice.xml:$(TARGET_COPY_OUT_SYSTEM)/etc/permissions/privapp-permissions-mtkimsservice.xml

SKIP_BOOT_JARS_CHECK := true

PRODUCT_BOOT_JARS += \
    mediatek-common \
    mediatek-framework \
    mediatek-ims-base \
    mediatek-ims-common \
    mediatek-telecom-common \
    mediatek-telephony-base \
    mediatek-telephony-common

# Step Motor
PRODUCT_PACKAGES += \
    StepMotorControls

# Inherit the proprietary files
$(call inherit-product, vendor/rabbit/r1/r1-vendor.mk)
