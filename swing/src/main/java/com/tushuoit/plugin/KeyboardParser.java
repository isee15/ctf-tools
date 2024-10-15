package com.tushuoit.plugin;

import com.google.gson.Gson;
import ctf.pcap.Pcap;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class KeyboardParser {

    // 键盘映射表
    private static final Map<Integer, String[]> BOOT_KEYBOARD_MAP = new HashMap<>();

    static {
        BOOT_KEYBOARD_MAP.put(0x00, new String[]{"", ""});                             // Reserved (no event indicated)
        BOOT_KEYBOARD_MAP.put(0x01, new String[]{"", ""});                             // ErrorRollOver
        BOOT_KEYBOARD_MAP.put(0x02, new String[]{"", ""});                             // POSTFail
        BOOT_KEYBOARD_MAP.put(0x03, new String[]{"", ""});                             // ErrorUndefined
        BOOT_KEYBOARD_MAP.put(0x04, new String[]{"a", "A"});                           // a
        BOOT_KEYBOARD_MAP.put(0x05, new String[]{"b", "B"});                           // b
        BOOT_KEYBOARD_MAP.put(0x06, new String[]{"c", "C"});                           // c
        BOOT_KEYBOARD_MAP.put(0x07, new String[]{"d", "D"});                           // d
        BOOT_KEYBOARD_MAP.put(0x08, new String[]{"e", "E"});                           // e
        BOOT_KEYBOARD_MAP.put(0x09, new String[]{"f", "F"});                           // f
        BOOT_KEYBOARD_MAP.put(0x0a, new String[]{"g", "G"});                           // g
        BOOT_KEYBOARD_MAP.put(0x0b, new String[]{"h", "H"});                           // h
        BOOT_KEYBOARD_MAP.put(0x0c, new String[]{"i", "I"});                           // i
        BOOT_KEYBOARD_MAP.put(0x0d, new String[]{"j", "J"});                           // j
        BOOT_KEYBOARD_MAP.put(0x0e, new String[]{"k", "K"});                           // k
        BOOT_KEYBOARD_MAP.put(0x0f, new String[]{"l", "L"});                           // l
        BOOT_KEYBOARD_MAP.put(0x10, new String[]{"m", "M"});                           // m
        BOOT_KEYBOARD_MAP.put(0x11, new String[]{"n", "N"});                           // n
        BOOT_KEYBOARD_MAP.put(0x12, new String[]{"o", "O"});                           // o
        BOOT_KEYBOARD_MAP.put(0x13, new String[]{"p", "P"});                           // p
        BOOT_KEYBOARD_MAP.put(0x14, new String[]{"q", "Q"});                           // q
        BOOT_KEYBOARD_MAP.put(0x15, new String[]{"r", "R"});                           // r
        BOOT_KEYBOARD_MAP.put(0x16, new String[]{"s", "S"});                           // s
        BOOT_KEYBOARD_MAP.put(0x17, new String[]{"t", "T"});                           // t
        BOOT_KEYBOARD_MAP.put(0x18, new String[]{"u", "U"});                           // u
        BOOT_KEYBOARD_MAP.put(0x19, new String[]{"v", "V"});                           // v
        BOOT_KEYBOARD_MAP.put(0x1a, new String[]{"w", "W"});                           // w
        BOOT_KEYBOARD_MAP.put(0x1b, new String[]{"x", "X"});                           // x
        BOOT_KEYBOARD_MAP.put(0x1c, new String[]{"y", "Y"});                           // y
        BOOT_KEYBOARD_MAP.put(0x1d, new String[]{"z", "Z"});                           // z
        BOOT_KEYBOARD_MAP.put(0x1e, new String[]{"1", "!"});                           // 1
        BOOT_KEYBOARD_MAP.put(0x1f, new String[]{"2", "@"});                           // 2
        BOOT_KEYBOARD_MAP.put(0x20, new String[]{"3", "#"});                           // 3
        BOOT_KEYBOARD_MAP.put(0x21, new String[]{"4", "$"});                           // 4
        BOOT_KEYBOARD_MAP.put(0x22, new String[]{"5", "%"});                           // 5
        BOOT_KEYBOARD_MAP.put(0x23, new String[]{"6", "^"});                           // 6
        BOOT_KEYBOARD_MAP.put(0x24, new String[]{"7", "&"});                           // 7
        BOOT_KEYBOARD_MAP.put(0x25, new String[]{"8", "*"});                           // 8
        BOOT_KEYBOARD_MAP.put(0x26, new String[]{"9", "("});                           // 9
        BOOT_KEYBOARD_MAP.put(0x27, new String[]{"0", ")"});                           // 0
        BOOT_KEYBOARD_MAP.put(0x28, new String[]{"\n", "\n"});                         // Return (ENTER)
        BOOT_KEYBOARD_MAP.put(0x29, new String[]{"[ESC]", "[ESC]"});                   // Escape
        BOOT_KEYBOARD_MAP.put(0x2a, new String[]{"\b", "\b"});                         // Backspace
        BOOT_KEYBOARD_MAP.put(0x2b, new String[]{"\t", "\t"});                         // Tab
        BOOT_KEYBOARD_MAP.put(0x2c, new String[]{" ", " "});                           // Spacebar
        BOOT_KEYBOARD_MAP.put(0x2d, new String[]{"-", "_"});                           // -
        BOOT_KEYBOARD_MAP.put(0x2e, new String[]{"=", "+"});                           // =
        BOOT_KEYBOARD_MAP.put(0x2f, new String[]{"[", "{"});                           // [
        BOOT_KEYBOARD_MAP.put(0x30, new String[]{"]", "}"});                           // ]
        BOOT_KEYBOARD_MAP.put(0x31, new String[]{"\\", "|"});                          // \
        BOOT_KEYBOARD_MAP.put(0x32, new String[]{"", ""});                             // Non-US // and ~
        BOOT_KEYBOARD_MAP.put(0x33, new String[]{";", ":"});                           // ;
        BOOT_KEYBOARD_MAP.put(0x34, new String[]{"'", "\""});                          // '
        BOOT_KEYBOARD_MAP.put(0x35, new String[]{"`", "~"});                           // `
        BOOT_KEYBOARD_MAP.put(0x36, new String[]{",", "<"});                           // ,
        BOOT_KEYBOARD_MAP.put(0x37, new String[]{".", ">"});                           // .
        BOOT_KEYBOARD_MAP.put(0x38, new String[]{"/", "?"});                           // /
        BOOT_KEYBOARD_MAP.put(0x39, new String[]{"[CAPSLOCK]", "[CAPSLOCK]"});         // Caps Lock
        BOOT_KEYBOARD_MAP.put(0x3a, new String[]{"[F1]", "[F1]"});                     // F1
        BOOT_KEYBOARD_MAP.put(0x3b, new String[]{"[F2]", "[F2]"});                     // F2
        BOOT_KEYBOARD_MAP.put(0x3c, new String[]{"[F3]", "[F3]"});                     // F3
        BOOT_KEYBOARD_MAP.put(0x3d, new String[]{"[F4]", "[F4]"});                     // F4
        BOOT_KEYBOARD_MAP.put(0x3e, new String[]{"[F5]", "[F5]"});                     // F5
        BOOT_KEYBOARD_MAP.put(0x3f, new String[]{"[F6]", "[F6]"});                     // F6
        BOOT_KEYBOARD_MAP.put(0x40, new String[]{"[F7]", "[F7]"});                     // F7
        BOOT_KEYBOARD_MAP.put(0x41, new String[]{"[F8]", "[F8]"});                     // F8
        BOOT_KEYBOARD_MAP.put(0x42, new String[]{"[F9]", "[F9]"});                     // F9
        BOOT_KEYBOARD_MAP.put(0x43, new String[]{"[F10]", "[F10]"});                   // F10
        BOOT_KEYBOARD_MAP.put(0x44, new String[]{"[F11]", "[F11]"});                   // F11
        BOOT_KEYBOARD_MAP.put(0x45, new String[]{"[F12]", "[F12]"});                   // F12
        BOOT_KEYBOARD_MAP.put(0x46, new String[]{"[PRINTSCREEN]", "[PRINTSCREEN]"});   // Print Screen
        BOOT_KEYBOARD_MAP.put(0x47, new String[]{"[SCROLLLOCK]", "[SCROLLLOCK]"});     // Scroll Lock
        BOOT_KEYBOARD_MAP.put(0x48, new String[]{"[PAUSE]", "[PAUSE]"});               // Pause
        BOOT_KEYBOARD_MAP.put(0x49, new String[]{"[INSERT]", "[INSERT]"});             // Insert
        BOOT_KEYBOARD_MAP.put(0x4a, new String[]{"[HOME]", "[HOME]"});                 // Home
        BOOT_KEYBOARD_MAP.put(0x4b, new String[]{"[PAGEUP]", "[PAGEUP]"});             // Page Up
        BOOT_KEYBOARD_MAP.put(0x4c, new String[]{"[DELETE]", "[DELETE]"});             // Delete Forward
        BOOT_KEYBOARD_MAP.put(0x4d, new String[]{"[END]", "[END]"});                   // End
        BOOT_KEYBOARD_MAP.put(0x4e, new String[]{"[PAGEDOWN]", "[PAGEDOWN]"});         // Page Down
        BOOT_KEYBOARD_MAP.put(0x4f, new String[]{"[RIGHTARROW]", "[RIGHTARROW]"});     // Right Arrow
        BOOT_KEYBOARD_MAP.put(0x50, new String[]{"[LEFTARROW]", "[LEFTARROW]"});       // Left Arrow
        BOOT_KEYBOARD_MAP.put(0x51, new String[]{"[DOWNARROW]", "[DOWNARROW]"});       // Down Arrow
        BOOT_KEYBOARD_MAP.put(0x52, new String[]{"[UPARROW]", "[UPARROW]"});           // Up Arrow
        BOOT_KEYBOARD_MAP.put(0x53, new String[]{"[NUMLOCK]", "[NUMLOCK]"});           // Num Lock
        BOOT_KEYBOARD_MAP.put(0x54, new String[]{"[KEYPADSLASH]", "/"});               // Keypad /
        BOOT_KEYBOARD_MAP.put(0x55, new String[]{"[KEYPADASTERISK]", "*"});            // Keypad *
        BOOT_KEYBOARD_MAP.put(0x56, new String[]{"[KEYPADMINUS]", "-"});               // Keypad -
        BOOT_KEYBOARD_MAP.put(0x57, new String[]{"[KEYPADPLUS]", "+"});                // Keypad +
        BOOT_KEYBOARD_MAP.put(0x58, new String[]{"[KEYPADENTER]", "[KEYPADENTER]"});   // Keypad ENTER
        BOOT_KEYBOARD_MAP.put(0x59, new String[]{"[KEYPAD1]", "1"});                   // Keypad 1 and End
        BOOT_KEYBOARD_MAP.put(0x5a, new String[]{"[KEYPAD2]", "2"});                   // Keypad 2 and Down Arrow
        BOOT_KEYBOARD_MAP.put(0x5b, new String[]{"[KEYPAD3]", "3"});                   // Keypad 3 and PageDn
        BOOT_KEYBOARD_MAP.put(0x5c, new String[]{"[KEYPAD4]", "4"});                   // Keypad 4 and Left Arrow
        BOOT_KEYBOARD_MAP.put(0x5d, new String[]{"[KEYPAD5]", "5"});                   // Keypad 5
        BOOT_KEYBOARD_MAP.put(0x5e, new String[]{"[KEYPAD6]", "6"});                   // Keypad 6 and Right Arrow
        BOOT_KEYBOARD_MAP.put(0x5f, new String[]{"[KEYPAD7]", "7"});                   // Keypad 7 and Home
        BOOT_KEYBOARD_MAP.put(0x60, new String[]{"[KEYPAD8]", "8"});                   // Keypad 8 and Up Arrow
        BOOT_KEYBOARD_MAP.put(0x61, new String[]{"[KEYPAD9]", "9"});                   // Keypad 9 and Page Up
        BOOT_KEYBOARD_MAP.put(0x62, new String[]{"[KEYPAD0]", "0"});                   // Keypad 0 and Insert
        BOOT_KEYBOARD_MAP.put(0x63, new String[]{"[KEYPADPERIOD]", "."});              // Keypad . and Delete
        BOOT_KEYBOARD_MAP.put(0x64, new String[]{"", ""});                             // Non-US \ and |
        BOOT_KEYBOARD_MAP.put(0x65, new String[]{"", ""});                             // Application
        BOOT_KEYBOARD_MAP.put(0x66, new String[]{"", ""});                             // Power
        BOOT_KEYBOARD_MAP.put(0x67, new String[]{"[KEYPADEQUALS]", "="});              // Keypad =
        BOOT_KEYBOARD_MAP.put(0x68, new String[]{"[F13]", "[F13]"});                   // F13
        BOOT_KEYBOARD_MAP.put(0x69, new String[]{"[F14]", "[F14]"});                   // F14
        BOOT_KEYBOARD_MAP.put(0x6a, new String[]{"[F15]", "[F15]"});                   // F15
        BOOT_KEYBOARD_MAP.put(0x6b, new String[]{"[F16]", "[F16]"});                   // F16
        BOOT_KEYBOARD_MAP.put(0x6c, new String[]{"[F17]", "[F17]"});                   // F17
        BOOT_KEYBOARD_MAP.put(0x6d, new String[]{"[F18]", "[F18]"});                   // F18
        BOOT_KEYBOARD_MAP.put(0x6e, new String[]{"[F19]", "[F19]"});                   // F19
        BOOT_KEYBOARD_MAP.put(0x6f, new String[]{"[F20]", "[F20]"});                   // F20
        BOOT_KEYBOARD_MAP.put(0x70, new String[]{"[F21]", "[F21]"});                   // F21
        BOOT_KEYBOARD_MAP.put(0x71, new String[]{"[F22]", "[F22]"});                   // F22
        BOOT_KEYBOARD_MAP.put(0x72, new String[]{"[F23]", "[F23]"});                   // F23
        BOOT_KEYBOARD_MAP.put(0x73, new String[]{"[F24]", "[F24]"});                   // F24
        BOOT_KEYBOARD_MAP.put(0x74, new String[]{"", ""});                             // Execute
        BOOT_KEYBOARD_MAP.put(0x75, new String[]{"", ""});                             // Help
        BOOT_KEYBOARD_MAP.put(0x76, new String[]{"", ""});                             // Menu
        BOOT_KEYBOARD_MAP.put(0x77, new String[]{"", ""});                             // Select
        BOOT_KEYBOARD_MAP.put(0x78, new String[]{"", ""});                             // Stop
        BOOT_KEYBOARD_MAP.put(0x79, new String[]{"", ""});                             // Again
        BOOT_KEYBOARD_MAP.put(0x7a, new String[]{"", ""});                             // Undo
        BOOT_KEYBOARD_MAP.put(0x7b, new String[]{"", ""});                             // Cut
        BOOT_KEYBOARD_MAP.put(0x7c, new String[]{"", ""});                             // Copy
        BOOT_KEYBOARD_MAP.put(0x7d, new String[]{"", ""});                             // Paste
        BOOT_KEYBOARD_MAP.put(0x7e, new String[]{"", ""});                             // Find
        BOOT_KEYBOARD_MAP.put(0x7f, new String[]{"", ""});                             // Mute
        BOOT_KEYBOARD_MAP.put(0x80, new String[]{"", ""});                             // Volume Up
        BOOT_KEYBOARD_MAP.put(0x81, new String[]{"", ""});                             // Volume Down
        BOOT_KEYBOARD_MAP.put(0x82, new String[]{"", ""});                             // Locking Caps Lock
        BOOT_KEYBOARD_MAP.put(0x83, new String[]{"", ""});                             // Locking Num Lock
        BOOT_KEYBOARD_MAP.put(0x84, new String[]{"", ""});                             // Locking Scroll Lock
        BOOT_KEYBOARD_MAP.put(0x85, new String[]{"", ""});                             // Keypad Comma
        BOOT_KEYBOARD_MAP.put(0x86, new String[]{"", ""});                             // Keypad Equal Sign
        BOOT_KEYBOARD_MAP.put(0x87, new String[]{"", ""});                             // International1
        BOOT_KEYBOARD_MAP.put(0x88, new String[]{"", ""});                             // International2
        BOOT_KEYBOARD_MAP.put(0x89, new String[]{"", ""});                             // International3
        BOOT_KEYBOARD_MAP.put(0x8a, new String[]{"", ""});                             // International4
        BOOT_KEYBOARD_MAP.put(0x8b, new String[]{"", ""});                             // International5
        BOOT_KEYBOARD_MAP.put(0x8c, new String[]{"", ""});                             // International6
        BOOT_KEYBOARD_MAP.put(0x8d, new String[]{"", ""});                             // International7
        BOOT_KEYBOARD_MAP.put(0x8e, new String[]{"", ""});                             // International8
        BOOT_KEYBOARD_MAP.put(0x8f, new String[]{"", ""});                             // International9
        BOOT_KEYBOARD_MAP.put(0x90, new String[]{"", ""});                             // LANG1
        BOOT_KEYBOARD_MAP.put(0x91, new String[]{"", ""});                             // LANG2
        BOOT_KEYBOARD_MAP.put(0x92, new String[]{"", ""});                             // LANG3
        BOOT_KEYBOARD_MAP.put(0x93, new String[]{"", ""});                             // LANG4
        BOOT_KEYBOARD_MAP.put(0x94, new String[]{"", ""});                             // LANG5
        BOOT_KEYBOARD_MAP.put(0x95, new String[]{"", ""});                             // LANG6
        BOOT_KEYBOARD_MAP.put(0x96, new String[]{"", ""});                             // LANG7
        BOOT_KEYBOARD_MAP.put(0x97, new String[]{"", ""});                             // LANG8
        BOOT_KEYBOARD_MAP.put(0x98, new String[]{"", ""});                             // LANG9
        BOOT_KEYBOARD_MAP.put(0x99, new String[]{"", ""});                             // Alternate Erase
        BOOT_KEYBOARD_MAP.put(0x9a, new String[]{"", ""});                             // SysReq/Attention
        BOOT_KEYBOARD_MAP.put(0x9b, new String[]{"", ""});                             // Cancel
        BOOT_KEYBOARD_MAP.put(0x9c, new String[]{"", ""});                             // Clear
        BOOT_KEYBOARD_MAP.put(0x9d, new String[]{"", ""});                             // Prior
        BOOT_KEYBOARD_MAP.put(0x9e, new String[]{"", ""});                             // Return
        BOOT_KEYBOARD_MAP.put(0x9f, new String[]{"", ""});                             // Separator
        BOOT_KEYBOARD_MAP.put(0xa0, new String[]{"", ""});                             // Out
        BOOT_KEYBOARD_MAP.put(0xa1, new String[]{"", ""});                             // Oper
        BOOT_KEYBOARD_MAP.put(0xa2, new String[]{"", ""});                             // Clear/Again
        BOOT_KEYBOARD_MAP.put(0xa3, new String[]{"", ""});                             // CrSel/Props
        BOOT_KEYBOARD_MAP.put(0xa4, new String[]{"", ""});                             // ExSel
        BOOT_KEYBOARD_MAP.put(0xa5, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xa6, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xa7, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xa8, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xa9, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xaa, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xab, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xac, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xad, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xae, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xaf, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xb0, new String[]{"", ""});                             // Keypad 00
        BOOT_KEYBOARD_MAP.put(0xb1, new String[]{"", ""});                             // Keypad 000
        BOOT_KEYBOARD_MAP.put(0xb2, new String[]{"", ""});                             // Thousands Separator
        BOOT_KEYBOARD_MAP.put(0xb3, new String[]{"", ""});                             // Decimal Separator
        BOOT_KEYBOARD_MAP.put(0xb4, new String[]{"", ""});                             // Currency Unit
        BOOT_KEYBOARD_MAP.put(0xb5, new String[]{"", ""});                             // Currency Sub-unit
        BOOT_KEYBOARD_MAP.put(0xb6, new String[]{"", ""});                             // Keypad (
        BOOT_KEYBOARD_MAP.put(0xb7, new String[]{"", ""});                             // Keypad )
        BOOT_KEYBOARD_MAP.put(0xb8, new String[]{"", ""});                             // Keypad {
        BOOT_KEYBOARD_MAP.put(0xb9, new String[]{"", ""});                             // Keypad }
        BOOT_KEYBOARD_MAP.put(0xba, new String[]{"", ""});                             // Keypad Tab
        BOOT_KEYBOARD_MAP.put(0xbb, new String[]{"", ""});                             // Keypad Backspace
        BOOT_KEYBOARD_MAP.put(0xbc, new String[]{"", ""});                             // Keypad A
        BOOT_KEYBOARD_MAP.put(0xbd, new String[]{"", ""});                             // Keypad B
        BOOT_KEYBOARD_MAP.put(0xbe, new String[]{"", ""});                             // Keypad C
        BOOT_KEYBOARD_MAP.put(0xbf, new String[]{"", ""});                             // Keypad D
        BOOT_KEYBOARD_MAP.put(0xc0, new String[]{"", ""});                             // Keypad E
        BOOT_KEYBOARD_MAP.put(0xc1, new String[]{"", ""});                             // Keypad F
        BOOT_KEYBOARD_MAP.put(0xc2, new String[]{"", ""});                             // Keypad XOR
        BOOT_KEYBOARD_MAP.put(0xc3, new String[]{"", ""});                             // Keypad ^
        BOOT_KEYBOARD_MAP.put(0xc4, new String[]{"", ""});                             // Keypad %
        BOOT_KEYBOARD_MAP.put(0xc5, new String[]{"", ""});                             // Keypad <
        BOOT_KEYBOARD_MAP.put(0xc6, new String[]{"", ""});                             // Keypad >
        BOOT_KEYBOARD_MAP.put(0xc7, new String[]{"", ""});                             // Keypad &
        BOOT_KEYBOARD_MAP.put(0xc8, new String[]{"", ""});                             // Keypad &&
        BOOT_KEYBOARD_MAP.put(0xc9, new String[]{"", ""});                             // Keypad |
        BOOT_KEYBOARD_MAP.put(0xca, new String[]{"", ""});                             // Keypad ||
        BOOT_KEYBOARD_MAP.put(0xcb, new String[]{"", ""});                             // Keypad :
        BOOT_KEYBOARD_MAP.put(0xcc, new String[]{"", ""});                             // Keypad #
        BOOT_KEYBOARD_MAP.put(0xcd, new String[]{"", ""});                             // Keypad Space
        BOOT_KEYBOARD_MAP.put(0xce, new String[]{"", ""});                             // Keypad @
        BOOT_KEYBOARD_MAP.put(0xcf, new String[]{"", ""});                             // Keypad !
        BOOT_KEYBOARD_MAP.put(0xd0, new String[]{"", ""});                             // Keypad Memory Store
        BOOT_KEYBOARD_MAP.put(0xd1, new String[]{"", ""});                             // Keypad Memory Recall
        BOOT_KEYBOARD_MAP.put(0xd2, new String[]{"", ""});                             // Keypad Memory Clear
        BOOT_KEYBOARD_MAP.put(0xd3, new String[]{"", ""});                             // Keypad Memory Add
        BOOT_KEYBOARD_MAP.put(0xd4, new String[]{"", ""});                             // Keypad Memory Subtract
        BOOT_KEYBOARD_MAP.put(0xd5, new String[]{"", ""});                             // Keypad Memory Multiply
        BOOT_KEYBOARD_MAP.put(0xd6, new String[]{"", ""});                             // Keypad Memory Divide
        BOOT_KEYBOARD_MAP.put(0xd7, new String[]{"", ""});                             // Keypad +/-
        BOOT_KEYBOARD_MAP.put(0xd8, new String[]{"", ""});                             // Keypad Clear
        BOOT_KEYBOARD_MAP.put(0xd9, new String[]{"", ""});                             // Keypad Clear Entry
        BOOT_KEYBOARD_MAP.put(0xda, new String[]{"", ""});                             // Keypad Binary
        BOOT_KEYBOARD_MAP.put(0xdb, new String[]{"", ""});                             // Keypad Octal
        BOOT_KEYBOARD_MAP.put(0xdc, new String[]{"", ""});                             // Keypad Decimal
        BOOT_KEYBOARD_MAP.put(0xdd, new String[]{"", ""});                             // Keypad Hexadecimal
        BOOT_KEYBOARD_MAP.put(0xde, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xdf, new String[]{"", ""});                             // Reserved
        BOOT_KEYBOARD_MAP.put(0xe0, new String[]{"", ""});                             // Left Control
        BOOT_KEYBOARD_MAP.put(0xe1, new String[]{"", ""});                             // Left Shift
        BOOT_KEYBOARD_MAP.put(0xe2, new String[]{"", ""});                             // Left Alt
        BOOT_KEYBOARD_MAP.put(0xe3, new String[]{"", ""});                             // Left GUI
        BOOT_KEYBOARD_MAP.put(0xe4, new String[]{"", ""});                             // Right Control
        BOOT_KEYBOARD_MAP.put(0xe5, new String[]{"", ""});                             // Right Shift
        BOOT_KEYBOARD_MAP.put(0xe6, new String[]{"", ""});                             // Right Alt
        BOOT_KEYBOARD_MAP.put(0xe7, new String[]{"", ""});                             // Right GUI
    }

    // 解析键盘数据
    public static Object[] parseBootKeyboardReport(byte[] data) {
        System.out.println(Arrays.toString(data));

        int modifiers = data[2]; // 修改键字节
        byte[] keys = Arrays.copyOfRange(data, 2, 8); // 键码字节

        boolean ctrl = (modifiers & 0x11) != 0;
        boolean shift = (modifiers & 0x22) != 0;
        boolean alt = (modifiers & 0x44) != 0;
        boolean gui = (modifiers & 0x88) != 0;

        List<String> characters = new ArrayList<>();
        for (byte key : keys) {
            if (key != 0) {
                String[] mappedKey = BOOT_KEYBOARD_MAP.get((int) key & 0xFF);
                if (mappedKey != null) {
                    characters.add(mappedKey[shift ? 1 : 0]);
                } else {
                    characters.add(null);
                }
            }
        }
        return new Object[]{ctrl, shift, alt, gui, characters};
    }


    public static String parseFile(String pcapngFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(new File(pcapngFile).getAbsolutePath()));

            StringBuilder text = new StringBuilder();
            Map<String, Integer> lastCharactersCount = new HashMap<>();
            int repeatLimit = 2;

            for (String line : lines) {
                String capdata = line.trim().replace(":", "");
                if (capdata.length() >= 8) {
                    byte[] data = hexStringToByteArray(capdata);
                    List<String> characters = Collections.unmodifiableList((List<String>) parseBootKeyboardReport(data)[4]);

                    if (characters.isEmpty()) {
                        lastCharactersCount.clear();
                    } else {
                        for (String character : characters) {
                            if (character != null) {
                                lastCharactersCount = lastCharactersCount.entrySet().stream()
                                        .filter(entry -> characters.contains(entry.getKey()))
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                                if (lastCharactersCount.containsKey(character)) {
                                    lastCharactersCount.put(character, lastCharactersCount.get(character) + 1);
                                    if (lastCharactersCount.get(character) <= repeatLimit) {
                                        continue;
                                    }
                                } else {
                                    lastCharactersCount.put(character, 1);
                                }
                                text.append(character);
                            }
                        }
                    }
                }
            }

            String rawText = text.toString();
            System.out.println("Raw output:\n" + rawText);
            System.out.println("Text output:\n" + text);
            return text.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 辅助函数：将十六进制字符串转换为字节数组
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
