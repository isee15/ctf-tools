package com.tushuoit;

import ctf.pcap.Pcap;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class UsbMiceDataHacker {
    public ArrayList<Point> leftPoint = new ArrayList<>();
    public ArrayList<Point> rightPoint = new ArrayList<>();
    public ArrayList<Point> movePoint = new ArrayList<>();
    public ArrayList<Point> allPoint = new ArrayList<>();

    public String tshark(String filePath) {
        final int[] mousePositionX = {0};
        final int[] mousePositionY = {0};

        StringBuilder sb = new StringBuilder();
        Pcap data = null;
        try {
            data = Pcap.fromFile(filePath);
            ArrayList<Pcap.Packet> packets = data.packets();

            packets.forEach(packet -> {
                byte[] bytes = (byte[]) packet.body();
                if ((bytes[23] == 0x08 || bytes[23] == 0x04) && bytes[24] == 0 && bytes[25] == 0 && bytes[26] == 0) {
                    for (int i = 27; i < 27 + bytes[23]; i++) {
                        sb.append(String.format("%02x", bytes[i]));
                        if (i != bytes.length - 1) {
                            sb.append(":");
                        }
                    }

                    int horizontal = 0;
                    int vertical = 0;
                    if (bytes[23] == 0x08) {
                        horizontal = 2;
                        vertical = 4;
                    } else {
                        horizontal = 1;
                        vertical = 2;
                    }
                    int offsetX = bytes[27 + horizontal];
                    int offsetY = bytes[27 + vertical];
                    if (offsetX > 127) {
                        offsetX -= 256;
                    }
                    if (offsetY > 127) {
                        offsetY -= 256;
                    }
                    mousePositionX[0] += offsetX;
                    mousePositionY[0] += offsetY;
                    if (bytes[27] == 0x01) {
                        leftPoint.add(new Point(mousePositionX[0], -mousePositionY[0]));
                    }
                    if (bytes[27] == 0x02) {
                        rightPoint.add(new Point(mousePositionX[0], -mousePositionY[0]));
                    } else if (bytes[27] == 0x00) {
                        movePoint.add(new Point(mousePositionX[0], -mousePositionY[0]));
                    }
                    allPoint.add(new Point(mousePositionX[0], -mousePositionY[0]));
                    sb.append("\n");
                }
            });
            System.out.println(sb);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }
}
