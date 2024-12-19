#include <jni.h>
#include <string>
#include <android/log.h>

#define LOGE(...) \
  ((void)__android_log_print(ANDROID_LOG_ERROR, "gen-libs::", __VA_ARGS__))

extern "C" JNIEXPORT jstring JNICALL
Java_com_xinayida_gen_1libs_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    LOGE("123123");
    return env->NewStringUTF(hello.c_str());
}