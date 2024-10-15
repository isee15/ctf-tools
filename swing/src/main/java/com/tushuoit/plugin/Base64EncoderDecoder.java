package com.tushuoit.plugin;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import com.tushuoit.ButtonConfig;
import com.tushuoit.EncoderDecoder;
import com.tushuoit.Fence;
import org.gizmore.jpk.JPK;

import javax.swing.*;
public class Base64EncoderDecoder implements EncoderDecoder {

    @Override
    public ButtonConfig[] getButtonConfigs() {
        return new ButtonConfig[]{
                new ButtonConfig("Base64 Encode↓", true, this::base64Encode),
                new ButtonConfig("Base64 Decode↑", false, this::base64Decode),
                new ButtonConfig("Base32 Encode↓", true, this::base32Encode),
                new ButtonConfig("Base32 Decode↑", false, this::base32Decode),
                new ButtonConfig("Base16 Encode↓", true, this::base16Encode),
                new ButtonConfig("Base16 Decode↑", false, this::base16Decode),
                new ButtonConfig("栅栏↓", true, this::fenceDecode),
                new ButtonConfig("凯撒↓", true, this::caesarCipher),
                new ButtonConfig("JPocketKnife", true, this::callJPK)
        };
    }

    public String base64Encode(String text, String key) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public String base64Decode(String text, String key) {
        return new String(Base64.getDecoder().decode(text));
    }

    public String base32Encode(String text, String key) {
        // 实现 Base32 编码
        return BaseEncoding.base32().encode(text.getBytes(Charsets.US_ASCII));
    }

    public String base32Decode(String text, String key) {
        // 实现 Base32 解码
        return new String(BaseEncoding.base32().decode(text));
    }

    public String base16Encode(String text, String key) {
        // 实现 Base16 编码
        return BaseEncoding.base16().encode(text.getBytes(Charsets.US_ASCII));
    }

    public String base16Decode(String text, String key) {
        // 实现 Base16 解码
        return new String(BaseEncoding.base16().decode(text));
    }

    public String fenceDecode(String text, String key) {
        // 实现栅栏解码
        return Fence.decode(text);
    }

    public String caesarCipher(String text, String key) {
        // 实现凯撒密码编码
        StringBuilder result = new StringBuilder();
        for (int s = 1; s < 26; s++) {
            result.append("keyLen: ").append(s).append("\n");
            for (int i = 0; i < text.length(); i++) {
                char ch;
                if (!Character.isLetter(text.charAt(i))) {
                    result.append(text.charAt(i));
                } else if (Character.isUpperCase(text.charAt(i))) {
                    ch = (char) (((int) text.charAt(i) + s - 65) % 26 + 65);
                    result.append(ch);
                } else {
                    ch = (char) (((int) text.charAt(i) + s - 97) % 26 + 97);
                    result.append(ch);
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String callJPK(String text, String key) {
        // 实现调用 JPocketKnife 的逻辑
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JPK");
            JPK jpk = new JPK();
            jpk.init();
            frame.add(jpk);
            frame.setSize(640, 480);
            frame.setLocation(20, 20);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        });
        return null;
    }

    @Override
    public boolean isKeyRequired() {
        return false;  // 大部分不需要密钥
    }

    @Override
    public String getTitle() {
        return "编码与解码工具";
    }

    @Override
    public Map<String, String> getExampleText() {
        Map<String, String> examples = new HashMap<>();

        return examples;
    }
}
