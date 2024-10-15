package com.tushuoit;

import com.google.gson.Gson;
import ctf.pcap.Pcap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class UsbKeyboardDataHacker {
    private static final String normalKeys = "{\"04\": \"a\", \"05\": \"b\", \"06\": \"c\", \"07\": \"d\", \"08\": \"e\", \"09\": \"f\", \"0a\": \"g\", \"0b\": \"h\", \"0c\": \"i\",\n" +
            "            \"0d\": \"j\", \"0e\": \"k\", \"0f\": \"l\", \"10\": \"m\", \"11\": \"n\", \"12\": \"o\", \"13\": \"p\", \"14\": \"q\", \"15\": \"r\",\n" +
            "            \"16\": \"s\", \"17\": \"t\", \"18\": \"u\", \"19\": \"v\", \"1a\": \"w\", \"1b\": \"x\", \"1c\": \"y\", \"1d\": \"z\", \"1e\": \"1\",\n" +
            "            \"1f\": \"2\", \"20\": \"3\", \"21\": \"4\", \"22\": \"5\", \"23\": \"6\", \"24\": \"7\", \"25\": \"8\", \"26\": \"9\", \"27\": \"0\",\n" +
            "            \"28\": \"<RET>\", \"29\": \"<ESC>\", \"2a\": \"<DEL>\", \"2b\": \"\\t\", \"2c\": \"<SPACE>\", \"2d\": \"-\", \"2e\": \"=\", \"2f\": \"[\",\n" +
            "            \"30\": \"]\", \"31\": \"\\\\\", \"32\": \"<NON>\", \"33\": \";\", \"34\": \"'\", \"35\": \"<GA>\", \"36\": \",\", \"37\": \".\", \"38\": \"/\",\n" +
            "            \"39\": \"<CAP>\", \"3a\": \"<F1>\", \"3b\": \"<F2>\", \"3c\": \"<F3>\", \"3d\": \"<F4>\", \"3e\": \"<F5>\", \"3f\": \"<F6>\",\n" +
            "            \"40\": \"<F7>\", \"41\": \"<F8>\", \"42\": \"<F9>\", \"43\": \"<F10>\", \"44\": \"<F11>\", \"45\": \"<F12>\"}";

    private static final String shiftKeys = "{\"04\": \"A\", \"05\": \"B\", \"06\": \"C\", \"07\": \"D\", \"08\": \"E\", \"09\": \"F\", \"0a\": \"G\", \"0b\": \"H\", \"0c\": \"I\",\n" +
            "             \"0d\": \"J\", \"0e\": \"K\", \"0f\": \"L\", \"10\": \"M\", \"11\": \"N\", \"12\": \"O\", \"13\": \"P\", \"14\": \"Q\", \"15\": \"R\",\n" +
            "             \"16\": \"S\", \"17\": \"T\", \"18\": \"U\", \"19\": \"V\", \"1a\": \"W\", \"1b\": \"X\", \"1c\": \"Y\", \"1d\": \"Z\", \"1e\": \"!\",\n" +
            "             \"1f\": \"@\", \"20\": \"#\", \"21\": \"$\", \"22\": \"%\", \"23\": \"^\", \"24\": \"&\", \"25\": \"*\", \"26\": \"(\", \"27\": \")\",\n" +
            "             \"28\": \"<RET>\", \"29\": \"<ESC>\", \"2a\": \"<DEL>\", \"2b\": \"\\t\", \"2c\": \"<SPACE>\", \"2d\": \"_\", \"2e\": \"+\", \"2f\": \"{\",\n" +
            "             \"30\": \"}\", \"31\": \"|\", \"32\": \"<NON>\", \"33\": \"\\\"\", \"34\": \":\", \"35\": \"<GA>\", \"36\": \"<\", \"37\": \">\", \"38\": \"?\",\n" +
            "             \"39\": \"<CAP>\", \"3a\": \"<F1>\", \"3b\": \"<F2>\", \"3c\": \"<F3>\", \"3d\": \"<F4>\", \"3e\": \"<F5>\", \"3f\": \"<F6>\",\n" +
            "             \"40\": \"<F7>\", \"41\": \"<F8>\", \"42\": \"<F9>\", \"43\": \"<F10>\", \"44\": \"<F11>\", \"45\": \"<F12>\"}";

    public static String tshark(String filePath) {
        Gson gson = new Gson();
        Map normalKeysMap = gson.fromJson(normalKeys, Map.class);
        Map shiftKeysMap = gson.fromJson(shiftKeys, Map.class);

        StringBuilder sb = new StringBuilder();
        StringBuilder result = new StringBuilder();
        Pcap data = null;
        try {
            data = Pcap.fromFile(filePath);
            ArrayList<Pcap.Packet> packets = data.packets();

            packets.forEach(packet -> {
                byte[] bytes = (byte[]) packet.body();
                //23-26 USB packet长度
                if (bytes[23] == 0x08 && bytes[24] == 0 && bytes[25] == 0 && bytes[26] == 0) {
                    for (int i = 27; i < 27 + 8; i++) {
                        sb.append(String.format("%02x", bytes[i]));
                        if (i != bytes.length - 1) {
                            sb.append(":");
                        }
                    }
                    int start = 27;
                    if (bytes.length >= 8) {
                        if (bytes[start] == 0) {
                            String hex2 = String.format("%02x", bytes[start + 2]);
                            if (bytes[start + 2] != 0 && normalKeysMap.containsKey(hex2)) {
                                result.append(normalKeysMap.get(hex2));
                            }
                        } else if ((bytes[start] & 0b10) > 0 || (bytes[start] & 0b100000) > 0) {
                            String hex2 = String.format("%02x", bytes[start + 2]);
                            if (bytes[start + 2] != 0 && normalKeysMap.containsKey(hex2)) {
                                result.append(shiftKeysMap.get(hex2));
                            }
                        } else {
                            System.out.println("[-] Unknow Key :" + bytes[start]);
                        }
                    }
                    sb.append("\n");
                }


            });
            System.out.println(sb);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result + "\n" + sb;

    }
}
