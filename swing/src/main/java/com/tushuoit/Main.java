package com.tushuoit;

import com.tushuoit.plugin.*;

import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Jiayu工具箱 by 五矿FLA9");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Vigenere", new EncodeDecodePanel(new VigenereEncoderDecoder()));
        tabbedPane.addTab("Base64", new EncodeDecodePanel(new Base64EncoderDecoder()));
//        tabbedPane.addTab("加解密", new JPanel());
//        tabbedPane.addTab("图片", new JPanel());
        tabbedPane.addTab("PCAP", new EncodeDecodePanel(new PcapDecoder()));
        tabbedPane.addTab("zipCRC32", new EncodeDecodePanel(new ZipCrcDecoder()));
//        tabbedPane.addTab("Converter", new JPanel());
//        tabbedPane.addTab("Zip", new JPanel());
        tabbedPane.addTab("曼彻斯特解密", new EncodeDecodePanel(new ManchemsterDecoder()));

        add(tabbedPane);
    }

    public static void main(String[] args) {
        com.formdev.flatlaf.FlatDarkLaf.setup();
        SwingUtilities.invokeLater(() -> {
            Main mainFrame = new Main();
            mainFrame.setVisible(true);
        });
    }
}
