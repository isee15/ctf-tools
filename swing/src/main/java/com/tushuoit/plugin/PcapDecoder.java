package com.tushuoit.plugin;

import com.tushuoit.ButtonConfig;
import com.tushuoit.CrcCrack;
import com.tushuoit.EncoderDecoder;
import com.tushuoit.UsbKeyboardDataHacker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

public class PcapDecoder implements EncoderDecoder {
    private String decode1(String src, String key) {
        return UsbKeyboardDataHacker.tshark(src);
    }

    public String decode2(String src, String key) {
        return KeyboardParser.parseFile(src);
    }


    @Override
    public ButtonConfig[] getButtonConfigs() {
        return new ButtonConfig[]{
                new ButtonConfig("键盘↓", true, this::decode1),
                new ButtonConfig("HID Data↓", true, this::decode2)
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
        ret.put("input", "./example/hiddata.pcap.txt");
        return ret;
    }
}
