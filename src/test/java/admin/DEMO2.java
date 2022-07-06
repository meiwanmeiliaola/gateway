package admin;


import org.apache.commons.codec.digest.DigestUtils;
import sm.sm2.SM2Utils;
import sm.util.Util;

import static sm.sm2.SM2Utils.generateKeyPair;


/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/4/12 17:50
 */
public class DEMO2 {
    public static void main(String[] args) throws  Exception  {
        generateKeyPair();
        String plainText = "783d2ae3f80495b10f980197862593c3ef658c84---"+System.currentTimeMillis();
        //String plainText = "783d2ae3f80495b10f980197862593c3ef658c84---1649752514585";
        byte[] sourceData = plainText.getBytes();

        //下面的秘钥可以使用generateKeyPair()生成的秘钥内容
        // 国密规范正式私钥
        String prik = "05DFB1B07D215DCF93F75CE5F8CB2EC1D23E5EAE17914181B69D18EC61E215FE";
        // 国密规范正式公钥
        String pubk = "045DEEA30461AAFE3061D80B806CD10BAE0A4A8C77B6905893F5776DCD99D38F193BA89A3DAF891AE82049B2E79A9631EED29B0C1EF54532CC6F3CE1837E9E9A8F";

        System.out.println("加密: ");
        String cipherText = SM2Utils.encrypt(Util.hexToByte(pubk), sourceData);
        System.out.println(cipherText);
        System.out.println("解密: ");
        plainText = new String(SM2Utils.decrypt(Util.hexToByte(prik), Util.hexToByte(cipherText)));
        System.out.println(plainText);
    }
    public static String encryptToMD5(String str) {
        return DigestUtils.md5Hex(str);
    }
}
