package com.tushuoit.plugin;

import com.google.common.io.BaseEncoding;
import com.tushuoit.ButtonConfig;
import com.tushuoit.CrcCrack;
import com.tushuoit.EncoderDecoder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

public class ZipCrcDecoder implements EncoderDecoder {
    private String decode1(String src, String key) {
        List<Long> checkList = Arrays.stream(src.split(",")).map(Long::decode).collect(Collectors.toList());
        return new CrcCrack().crack(checkList);
    }

    public String decode2(String src, String key) {
        CRC32 crc32 = new CRC32();
        crc32.update(src.getBytes());
        long checksum = crc32.getValue();
        return String.format("0x%2x", checksum);
    }


    @Override
    public ButtonConfig[] getButtonConfigs() {
        return new ButtonConfig[]{
                new ButtonConfig("4/5/6位Crack↓", true, this::decode1),
                new ButtonConfig("Calc Crc32↓", true, this::decode2)
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
        ret.put("input", "0x7c2df918,0x5246b5a8");
        return ret;
    }
}
