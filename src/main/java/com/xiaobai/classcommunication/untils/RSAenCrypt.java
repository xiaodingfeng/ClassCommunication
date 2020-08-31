package com.xiaobai.classcommunication.untils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
@Service
public class RSAenCrypt {
    String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFT83XQg++lqhI2Oi3gBCLRD1byttmjJFiIBOe75DO/w+aM01no0bwzJNW8wtqks6R986U4Xq6FBHyDcSP/5ICaAAk5dlffQBN0O2E3gsLXHlQlhIa6W7k68Ryog5MFJlP+wG1LvPGEhoUNJjqOR+5cyF2LYgsgu/R+jumpqkv/wIDAQAB";
    String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIVPzddCD76WqEjY" +
            "6LeAEItEPVvK22aMkWIgE57vkM7/D5ozTWejRvDMk1bzC2qSzpH3zpTheroUEfINxI//kgJoACTl2V99AE3Q" +
            "7YTeCwtceVCWEhrpbuTrxHKiDkwUmU/7AbUu88YSGhQ0mOo5H7lzIXYtiCyC79H6O6amqS//AgMBAAECgYAV040WE" +
            "0DUqYH/+1OOx67tvkx1fO8TCSy8bhgIS5zf5y68xm2D7F2vCy+MatqRpluf1nzCL3CSirqtcMef8+4JrX7sqXNB9siJOA" +
            "c+je0yUyM9a719q5kcSgxefkXV04MisYHhS/C2OIjHZmHX7paTRI0rsQdnhqU0W7dcC8OXoQJBANn3HkILp0tFhJ9smugTAMI" +
            "GPEqoNBcAqGypTwdC+fufktIvxvtprNDJKbj7QM3FVhiElrYeRXxU++NOnCv1UtkCQQCckxEIfNqMnjQc7TeFLDCT7" +
            "Df2mXEH/3DcnXC/SiY2ulGLAd3WuvTpu2vQqkBidzZgwf2eXz+WNvD609opyKKXAkEAlJyVGNrxObUwc7KM++bZAX" +
            "FW8mA1A+oC6/OUWpMj9pgadwz0Ur7+gWxm8iT9Tk9aIGVmjM2e+uLGy3jxqlceIQJAI9TsMvsPsNqLposLB6kD" +
            "PPb0H7UhzujAVVyabsxzTlb5TkyRDhEe91Zy5//uWBpcEWyTs+isTp4oK97LsIA/5QJBAK+jnf6cof" +
            "6SUn6jC4BOsT9Gea203BWe0iV528/ptc93yJ7Xfp3gzvx1W41Zu0MpgrJwQ0h/cAsYAh6UqIlEmXM=";
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public  String encrypt( String str ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public  String decrypt(String str) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }

}

