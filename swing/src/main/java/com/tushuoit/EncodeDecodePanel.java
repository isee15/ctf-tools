package com.tushuoit;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class EncodeDecodePanel extends JPanel {
    private final JTextArea inputTextArea;
    private final JTextArea outputTextArea;
    private final JTextField keyField;
    private final JLabel keyLabel;

    public EncodeDecodePanel(EncoderDecoder encoderDecoder) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Map<String, String> exampleMap = encoderDecoder.getExampleText();
        inputTextArea = new JTextArea(10, 60);
        inputTextArea.append(exampleMap.get("input"));
        outputTextArea = new JTextArea(10, 60);
        outputTextArea.append(exampleMap.get("output"));

        keyField = new JTextField(20);
        keyField.setText(exampleMap.get("key"));
        keyLabel = new JLabel("密钥:");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        add(new JScrollPane(inputTextArea), gbc);

        // 根据是否需要密钥动态显示密钥输入区域
        if (encoderDecoder.isKeyRequired()) {
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0.0;
            gbc.weighty = 0.0;
            gbc.insets = new Insets(5, 5, 5, 5);
            add(keyLabel, gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            add(keyField, gbc);
        }

        // 动态生成按钮
        ButtonConfig[] buttonConfigs = encoderDecoder.getButtonConfigs();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3; // 让按钮面板占据整个宽度
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel buttonPanel = getButtonPanel(encoderDecoder, buttonConfigs);

        // 将按钮面板加入到界面中
        gbc.gridy = 3;
        add(buttonPanel, gbc);

        // 输出区域
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        add(new JScrollPane(outputTextArea), gbc);
    }

    private JPanel getButtonPanel(EncoderDecoder encoderDecoder, ButtonConfig[] buttonConfigs) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 使用 FlowLayout 以支持换行

        for (ButtonConfig buttonConfig : buttonConfigs) {
            String buttonTitle = buttonConfig.getTitle();
            boolean isEncode = buttonConfig.isEncode();

            JButton button = new JButton(buttonTitle);
            buttonPanel.add(button);

            // 绑定不同的事件，根据是 encode 还是 decode 执行不同的逻辑
            button.addActionListener(e -> {
                if (isEncode) {
                    String input = inputTextArea.getText();
                    String key = encoderDecoder.isKeyRequired() ? keyField.getText() : "";
                    String result = buttonConfig.getAction().execute(input, key);
                    outputTextArea.setText(result);
                } else {
                    String input = outputTextArea.getText();
                    String key = encoderDecoder.isKeyRequired() ? keyField.getText() : "";
                    String result = buttonConfig.getAction().execute(input, key);
                    inputTextArea.setText(result);
                }
            });
        }
        return buttonPanel;
    }
}
