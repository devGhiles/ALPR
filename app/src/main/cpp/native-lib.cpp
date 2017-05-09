#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_esi_mxt07_pfe2017_alpr_MainActivity_recognizeLicensePlate(
        JNIEnv* env,
        jobject, jlong imgAddr) {
    std::string plate_number = "ABC123";
    return env->NewStringUTF(plate_number.c_str());
}
