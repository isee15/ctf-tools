package com.tushuoit;

import java.util.Map;

public interface EncoderDecoder {
    // 获取按钮的配置数组，每个按钮有独立的逻辑
    ButtonConfig[] getButtonConfigs();

    // 是否需要密钥
    boolean isKeyRequired();

    // 获取面板标题
    String getTitle();

    Map<String, String> getExampleText();
}
