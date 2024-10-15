package com.tushuoit.plugin;

import com.tushuoit.ButtonConfig;
import com.tushuoit.EncoderDecoder;
import com.tushuoit.Vig;

import java.util.HashMap;
import java.util.Map;

public class VigenereEncoderDecoder implements EncoderDecoder {
    @Override
    public ButtonConfig[] getButtonConfigs() {
        return new ButtonConfig[]{
                new ButtonConfig("Vigenere 加密↓", true, this::encode), // 绑定 encode 方法
                new ButtonConfig("Vigenere 解密↑", false, this::decode) // 绑定 decode 方法
        };
    }


    public String encode(String text, String key) {
        return Vig.encrypt(text, key);
    }


    public String decode(String text, String key) {
        if (key == null || key.isEmpty()) {
            return Vig.crack(text);
        }
        return Vig.decrypt(text, key);
    }

    @Override
    public boolean isKeyRequired() {
        return true;  // Vigenere 算法需要密钥
    }

    @Override
    public String getTitle() {
        return "Vigenere";
    }

    @Override
    public Map<String, String> getExampleText() {
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("output", "kxccxfkzcfcqi wymui zwbz cyiq ej e k cbxcregmfr ikt wg zlr wnmau qmue frxnimp 1914 qil 1940.");
        return ret;
    }
}
