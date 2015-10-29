#include <string.h>
#include <stdio.h>
#include <jni.h>
#include "md5/md5.h"
#include "des/des.h"
#include <android/log.h>

JNIEXPORT jstring JNICALL
Java_com_flyeek_dev_demo_util_SecureUtil_helloJni(JNIEnv *env, jclass type) {
    return (*env)->NewStringUTF(env, "Hello I am from jni!");
}

JNIEXPORT jstring JNICALL
Java_com_flyeek_dev_demo_util_SecureUtil_MD5EncryptNative(JNIEnv *env, jclass type, jstring
sourceStr) {
    char* szText = (char*)(*env)->GetStringUTFChars(env, sourceStr, 0);

    MD5_CTX context = { 0 };
    MD5Init(&context);
    MD5Update(&context, szText, strlen(szText));
    unsigned char dest[16] = { 0 };
    MD5Final(dest, &context);
    (*env)->ReleaseStringUTFChars(env, sourceStr, szText);

    int i = 0;
    char szMd5[32] = { 0 };
    for (i = 0; i < 16; i++)
    {
        sprintf(szMd5, "%s%02x", szMd5, dest[i]);
    }

    return (*env)->NewStringUTF(env, szMd5);
}

JNIEXPORT jbyteArray JNICALL
Java_com_flyeek_dev_demo_util_SecureUtil_DESEncryptNative(JNIEnv *env, jclass thiz, jstring msg,
                                                        jbyteArray key) {
    char* pMsg = (char*)(*env)->GetStringUTFChars(env, msg, 0);
    jbyte * pKey = (*env)->GetByteArrayElements(env, key, 0);

    int length = strlen(pMsg);
    length = 8 * ((length + 7) / 8);
    char pCipher[length];

    DES_Encrypt(pMsg, pKey, pCipher, strlen(pMsg));

    (*env)->ReleaseStringUTFChars(env, msg, pMsg);
    (*env)->ReleaseByteArrayElements(env, key, pKey, 0);

    jbyteArray resultArray = (*env)->NewByteArray(env, length);
    (*env)->SetByteArrayRegion(env, resultArray, 0, length, pCipher);
    return resultArray;
}

JNIEXPORT jbyteArray JNICALL
Java_com_flyeek_dev_demo_util_SecureUtil_DESDecryptNative(JNIEnv *env, jclass thiz,
                                                           jbyteArray cipher, jint
                                                          cipherLength, jbyteArray key) {
    jbyte * pCipher = (*env)->GetByteArrayElements(env, cipher, 0);
    jbyte * pKey = (*env)->GetByteArrayElements(env, key, 0);

    char pMsg[cipherLength];

    int msgLength = DES_Decrypt(pCipher, pKey, pMsg, cipherLength);

    (*env)->ReleaseByteArrayElements(env, cipher, pCipher, 0);
    (*env)->ReleaseByteArrayElements(env, key, pKey, 0);

    jbyteArray resultArray = (*env)->NewByteArray(env, msgLength);
    (*env)->SetByteArrayRegion(env, resultArray, 0, msgLength, pMsg);
    return resultArray;
}

JNIEXPORT jbyteArray JNICALL
Java_com_flyeek_dev_demo_util_SecureUtil_DES3EncryptNative(JNIEnv *env, jclass thiz, jstring msg,
                                                          jbyteArray key) {
    char* pMsg = (char*)(*env)->GetStringUTFChars(env, msg, 0);
    jbyte * pKey = (*env)->GetByteArrayElements(env, key, 0);

    int length = strlen(pMsg);
    length = 8 * ((length + 7) / 8);
    char pCipher[length];

    D3DES_Encrypt(pMsg, pKey, pCipher, strlen(pMsg));

    (*env)->ReleaseStringUTFChars(env, msg, pMsg);
    (*env)->ReleaseByteArrayElements(env, key, pKey, 0);

    jbyteArray resultArray = (*env)->NewByteArray(env, length);
    (*env)->SetByteArrayRegion(env, resultArray, 0, length, pCipher);
    return resultArray;
}

JNIEXPORT jbyteArray JNICALL
Java_com_flyeek_dev_demo_util_SecureUtil_DES3DecryptNative(JNIEnv *env, jclass thiz,
                                                          jbyteArray cipher, jint
                                                           cipherLength, jbyteArray key) {
    jbyte * pCipher = (*env)->GetByteArrayElements(env, cipher, 0);
    jbyte * pKey = (*env)->GetByteArrayElements(env, key, 0);

    char pMsg[cipherLength];

    int msgLength = D3DES_Decrypt(pCipher, pKey, pMsg, cipherLength);

    (*env)->ReleaseByteArrayElements(env, cipher, pCipher, 0);
    (*env)->ReleaseByteArrayElements(env, key, pKey, 0);

    jbyteArray resultArray = (*env)->NewByteArray(env, msgLength);
    (*env)->SetByteArrayRegion(env, resultArray, 0, msgLength, pMsg);
    return resultArray;
}
