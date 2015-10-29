package com.flyeek.dev.demo.util;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by flyeek on 10/27/15.
 */
public class SecureUtil {

    public static final String MD5 = "MD5";
    public static final String SHA = "SHA";

    /**
     * get digest of string.
     * @param source source string
     * @param algorithm must be {@link #MD5} or {@link #SHA}.
     * @return message digest.
     */
    public static byte[] getMessageDigest(String source, String algorithm) {
        if (!MD5.equals(algorithm) && !SHA.equals(algorithm)) {
            return null;
        }

        try {
            MessageDigest thisMD = MessageDigest.getInstance(algorithm);
            return thisMD.digest(source.getBytes("UTF8"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get digest of bytes.
     * @param source source bytes
     * @param algorithm must be {@link #MD5} or {@link #SHA}.
     * @return message digest.
     */
    public static byte[] getMessageDigest(byte[] source, String algorithm) {
        if (!MD5.equals(algorithm) && !SHA.equals(algorithm)) {
            return null;
        }

        try {
            MessageDigest thisMD = MessageDigest.getInstance(algorithm);
            return thisMD.digest(source);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get hash of file.
     * @param fileName source file
     * @param algorithm must be {@link #MD5} or {@link #SHA}.
     * @return message digest.
     */
    private static String getFileHash(String fileName, String algorithm) {
        if (!MD5.equals(algorithm) && !SHA.equals(algorithm)) {
            return "";
        }

        String ret;
        try {
            InputStream fis = new FileInputStream(fileName);
            byte buffer[] = new byte[1024];
            MessageDigest md = MessageDigest.getInstance(algorithm);
            for (int numRead = 0; (numRead = fis.read(buffer)) > 0;) {
                md.update(buffer, 0, numRead);
            }
            fis.close();

            byte[] encryption = md.digest();
            StringBuilder mdBuilder = new StringBuilder();
            final int length = encryption.length;
            for (int i = 0; i < length; i++) {
                String hexStr = Integer.toHexString(0xff & encryption[i]);
                if (hexStr.length() == 1) {
                    mdBuilder.append("0");
                }
                mdBuilder.append(hexStr);
            }

            ret = mdBuilder.toString();
        } catch (Exception e) {
            ret = "";
        }

        return ret;
    }

    /**
     * Encodes a string using the default UTF-8 charset.
     *
     * @param str String to encode
     * @return the encoded String
     */
    public static String MD5Encrypt(String str) {
        return MD5Encrypt(str, "UTF-8");
    }

    /**
     * Encode a string with given charset.
     *
     * @param str String to encode
     * @param charSet encoding charset
     * @return the encoded String
     */
    public static String MD5Encrypt(String str, String charSet) {
        if (str == null || str.length() == 0 || charSet == null) {
            throw new IllegalArgumentException("String or charset to encript cannot be null or zero length");
        }
        try {
            return MD5Encrypt(str.getBytes(charSet));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Encode bytes
     *
     * @param source source bytes
     * @return the encoded String
     */
    public static String MD5Encrypt(byte[] source) {
        String ret;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] encryption = md.digest();

            StringBuilder md5Builder = new StringBuilder();
            final int length = encryption.length;
            for (int i = 0; i < length; i++) {
                String hexStr = Integer.toHexString(0xff & encryption[i]);
                if (hexStr.length() == 1) {
                    md5Builder.append("0");
                }
                md5Builder.append(hexStr);
            }

            ret = md5Builder.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return ret;
    }


    private static byte[] desIv = {1,2,3,4,5,6,7,8};
    private static final String ALGORITHM_DES = "DES";
    private static final String ALGORITHM_DES3 = "DESede";
    private static final String DES_TRANSFORMATION = "DES/CBC/PKCS5Padding";
    private static final String DES3_TRANSFORMATION = "DESede/CBC/PKCS5Padding";

    public static String DESEncrypt(String msg, String desKey, boolean isNative) {
        byte[] desKeyBytes = formatDESKey(desKey, ALGORITHM_DES);
        if (desKeyBytes == null) {
            return "";
        }

        if (isNative) {
            final byte[] cipherBytes = DESEncryptNative(msg, desKeyBytes);
            return Base64.encodeToString(cipherBytes, Base64.URL_SAFE);
        } else {
            IvParameterSpec zeroIv = new IvParameterSpec(desIv);
            try {
                SecretKeySpec key = new SecretKeySpec(desKeyBytes, ALGORITHM_DES);
                Cipher cipher = Cipher.getInstance(DES_TRANSFORMATION);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] encryptedData = cipher.doFinal(msg.getBytes());

                return Base64.encodeToString(encryptedData, Base64.URL_SAFE);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    public static String DESDecrypt(String cipherMsg, String desKey, boolean isNative) {
        byte[] desKeyBytes = formatDESKey(desKey, ALGORITHM_DES);
        if (desKeyBytes == null) {
            return "";
        }

        byte[] cipherBytes = Base64.decode(cipherMsg, Base64.URL_SAFE);

        if (isNative) {
            final byte[] msgBytes = DESDecryptNative(cipherBytes, cipherBytes.length, desKeyBytes);
            return new String(msgBytes);
        } else {
            IvParameterSpec zeroIv = new IvParameterSpec(desIv);
            try {
                SecretKeySpec key = new SecretKeySpec(desKeyBytes, ALGORITHM_DES);
                Cipher cipher = Cipher.getInstance(DES_TRANSFORMATION);
                cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
                byte decryptedData[] = cipher.doFinal(cipherBytes);

                return new String(decryptedData);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    public static String DES3Encrypt(String msg, String desKey, boolean isNative) {
        byte[] desKeyBytes = formatDESKey(desKey, ALGORITHM_DES3);
        if (desKeyBytes == null) {
            return "";
        }

        if (isNative) {
            final byte[] msgBytes = DES3EncryptNative(msg, desKeyBytes);
            return Base64.encodeToString(msgBytes, Base64.URL_SAFE);
        } else {
            IvParameterSpec zeroIv = new IvParameterSpec(desIv);
            try {
                SecretKeySpec key = new SecretKeySpec(desKeyBytes, ALGORITHM_DES3);
                Cipher cipher = Cipher.getInstance(DES3_TRANSFORMATION);
                cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
                byte[] encryptedData = cipher.doFinal(msg.getBytes());

                return Base64.encodeToString(encryptedData, Base64.URL_SAFE);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    public static String DES3Decrypt(String cipherMsg, String desKey, boolean isNative) {
        byte[] desKeyBytes = formatDESKey(desKey, ALGORITHM_DES3);
        if (desKeyBytes == null) {
            return "";
        }

        byte[] cipherBytes = Base64.decode(cipherMsg, Base64.URL_SAFE);

        if (isNative) {
            final byte[] msgBytes = DES3DecryptNative(cipherBytes, cipherBytes.length, desKeyBytes);
            return new String(msgBytes);
        } else {
            IvParameterSpec zeroIv = new IvParameterSpec(desIv);
            try {
                SecretKeySpec key = new SecretKeySpec(desKeyBytes, ALGORITHM_DES3);
                Cipher cipher = Cipher.getInstance(DES3_TRANSFORMATION);
                cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
                byte decryptedData[] = cipher.doFinal(cipherBytes);

                return new String(decryptedData);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    private static byte[] formatDESKey(String keyStr, String algorithm) {
        int keyLength;

        if (ALGORITHM_DES.equals(algorithm)) {
            keyLength = 8;
        } else if (ALGORITHM_DES3.equals(algorithm)) {
            keyLength = 24;
        } else {
            return null;
        }

        byte[] keyBytes;
        try {
            keyBytes = keyStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        byte[] formatedKeyBytes = new byte[keyLength];

        int copyCount = Math.min(formatedKeyBytes.length, keyBytes.length);
        System.arraycopy(keyBytes, 0, formatedKeyBytes, 0, copyCount);

        return formatedKeyBytes;
    }


    /**
     * 建立新的密钥对，返回打包的byte[]形式私钥和公钥
     *
     * @return
     *         包含打包成byte[]形式的私钥和公钥的object[],其中，object[0]为私钥byte[],object[1]为公钥byte
     *         []
     */
    public static Object[] GenerateRSAKeyPair() {
        KeyPair newKeyPair = createRSAKeyPair();
        if (newKeyPair == null) {
            return null;
        } else {
            Object[] re = new Object[2];
            PrivateKey priv = newKeyPair.getPrivate();
            byte[] b_priv = priv.getEncoded();
            PublicKey pub = newKeyPair.getPublic();
            byte[] b_pub = pub.getEncoded();
            re[0] = b_priv;
            re[1] = b_pub;
            return re;
        }
    }

    /**
     * 新建密钥对
     *
     * @return KeyPair对象
     */
    private static KeyPair createRSAKeyPair() {
        KeyPair myPair;
        long mySeed;
        mySeed = System.currentTimeMillis();
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            random.setSeed(mySeed);
            keyGen.initialize(1024, random);
            myPair = keyGen.generateKeyPair();
        } catch (Exception e1) {
            return null;
        }
        return myPair;
    }

    /**
     * 使用私钥加密数据 用一个已打包成byte[]形式的私钥加密数据，即数字签名
     *
     * @param privateKey
     *            打包成byte[]的私钥
     * @param source
     *            要签名的数据，一般应是数字摘要
     * @return 签名 byte[]
     */
    public static byte[] RSASign(byte[] privateKey, byte[] source) {
        try {
            PKCS8EncodedKeySpec priv_spec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privKey = mykeyFactory.generatePrivate(priv_spec);
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initSign(privKey);
            sig.update(source);
            return sig.sign();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证数字签名
     *
     * @param publicKey
     *            打包成byte[]形式的公钥
     * @param source
     *            原文的数字摘要
     * @param sign
     *            签名（对原文的数字摘要的签名）
     * @return 是否证实 boolean
     */
    public static boolean RSAVerify(byte[] publicKey, byte[] source, byte[] sign) {
        try {
            KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
            Signature sig = Signature.getInstance("SHA1withRSA");
            X509EncodedKeySpec pub_spec = new X509EncodedKeySpec(publicKey);
            PublicKey pubKey = mykeyFactory.generatePublic(pub_spec);
            sig.initVerify(pubKey);
            sig.update(source);
            return sig.verify(sign);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 使用RSA公钥加密数据
     *
     * @param publicKey
     *            打包的byte[]形式公钥
     * @param data
     *            要加密的数据
     * @return 加密数据
     */
    public static byte[] RSAEncrypt(byte[] publicKey, byte[] data) {
        try {
            KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec pub_spec = new X509EncodedKeySpec(publicKey);
            PublicKey pubKey = mykeyFactory.generatePublic(pub_spec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 用RSA私钥解密
     *
     * @param privateKey
     *            私钥打包成byte[]形式
     * @param data
     *            要解密的数据
     * @return 解密数据
     */
    public static byte[] RSADecrypt(byte[] privateKey, byte[] data) {
        try {
            PKCS8EncodedKeySpec priv_spec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privKey = mykeyFactory.generatePrivate(priv_spec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }

    }


    public static native String helloJni();
    public static native String MD5EncryptNative(String source);

    private static native byte[] DESEncryptNative(String msg, byte[] key);
    private static native byte[] DESDecryptNative(byte[] cipherBytes, int cipherCount, byte[] key);
    private static native byte[] DES3EncryptNative(String msg, byte[] key);
    private static native byte[] DES3DecryptNative(byte[] cipherBytes, int cipherCount, byte[] key);

    static {
        System.loadLibrary("secureUtil");
    }
}
