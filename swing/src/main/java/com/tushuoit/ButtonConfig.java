package com.tushuoit;

public class ButtonConfig {
    private final String title;
    private final boolean isEncode;  // true 表示 encode，false 表示 decode
    private final EncoderDecoderAction action;

    public ButtonConfig(String title, boolean isEncode, EncoderDecoderAction action) {
        this.title = title;
        this.isEncode = isEncode;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEncode() {
        return isEncode;
    }

    public EncoderDecoderAction getAction() {
        return action;
    }

    // Functional interface for defining encode/decode actions
    @FunctionalInterface
    public interface EncoderDecoderAction {
        String execute(String text, String key);
    }
}
