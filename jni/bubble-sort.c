#include <jni.h>
#include <android/log.h>
#include <time.h>

jint cpu_time_used = 0;

jint Java_org_divenvrsk_android_hellondk_HelloNDK_getExecutionTime(JNIEnv* env, jobject this)
{
    return cpu_time_used;
}

int int_cmp(const void *a, const void *b)
{
    const int *ia = (const int *)a;
    const int *ib = (const int *)b;
    return *ia  - *ib;
}

jintArray Java_org_divenvrsk_android_hellondk_HelloNDK_sortBubble( JNIEnv* env, jobject this, jintArray array_to_sort, int array_size)
 {
    __android_log_print(ANDROID_LOG_INFO, "HelloNDK!", "array size = %d", array_size);

    struct timeval start_time, end_time;
    jint *bubbles_array = (*env)->GetIntArrayElements(env, array_to_sort, 0);

    gettimeofday(&start_time, NULL);
    qsort(bubbles_array, array_size, sizeof(int), int_cmp);
    gettimeofday(&end_time, NULL);

    cpu_time_used = end_time.tv_usec - start_time.tv_usec;

    __android_log_print(ANDROID_LOG_INFO, "HelloNDK!", "Start = %ld, End = %ld", start_time.tv_usec, end_time.tv_usec);
    __android_log_print(ANDROID_LOG_INFO, "HelloNDK!", "Sorted in: %ld microseconds", cpu_time_used);

    (*env)->SetIntArrayRegion(env, array_to_sort, 0, array_size, bubbles_array);
    return array_to_sort;
 }