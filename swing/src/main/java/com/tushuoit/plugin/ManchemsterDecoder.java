package com.tushuoit.plugin;

import com.google.common.io.BaseEncoding;
import com.tushuoit.ButtonConfig;
import com.tushuoit.EncoderDecoder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ManchemsterDecoder implements EncoderDecoder {
    private String decode1(String src, String key) {
        try {
            src = new BigInteger(src, 16).toString(2);
            src = "00" + src;
            StringBuilder sb = new StringBuilder();
            char[] srcArr = src.toCharArray();
            for (int i = 0; i < srcArr.length / 2; i++) {
                char c = srcArr[i * 2];
                char c1 = srcArr[i * 2 + 1];
                if (c == '1' && c1 == '0') {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            return packRet(sb);
        } catch (Exception ex) {
            return (ex.getMessage());
        }
    }

    private static String packRet(StringBuilder sb) {
        String bin = sb.toString();
        byte[] ret = new BigInteger(bin, 2).toByteArray();
        sb.append("\n");
        sb.append(new String(ret, StandardCharsets.UTF_8));
        sb.append("\n");
        sb.append(BaseEncoding.base16().encode(ret));
        return sb.toString();
    }

    public String decode2(String src, String key) {
        try {
            src = new BigInteger(src, 16).toString(2);
            src = "00" + src;
            StringBuilder sb = new StringBuilder();
            char[] srcArr = src.toCharArray();
            for (int i = 0; i < srcArr.length / 2; i++) {
                char c = srcArr[i * 2];
                char c1 = '0';
                if (i > 0) {
                    c1 = srcArr[i * 2 - 1];
                }
                if (c == c1) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            return packRet(sb);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }


    @Override
    public ButtonConfig[] getButtonConfigs() {
        return new ButtonConfig[]{
                new ButtonConfig("曼彻斯特↓", true, this::decode1),
                new ButtonConfig("差分曼彻斯特↓", true, this::decode2)
        };
    }

    @Override
    public boolean isKeyRequired() {
        return false;
    }

    @Override
    public String getTitle() {
        return "";
    }


    @Override
    public Map<String, String> getExampleText() {
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("input", "95965569a596696995a9aa969996a6a9a669965656969996959669566a5655699669aa5656966a566a56656");
        return ret;
    }
}
