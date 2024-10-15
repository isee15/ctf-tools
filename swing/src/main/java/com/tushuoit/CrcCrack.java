package com.tushuoit;

import java.util.*;

public class CrcCrack {
    String permitted_characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";


    ArrayList<Long> table = new ArrayList<>();
    ArrayList<ArrayList<Long>> table_reverse = new ArrayList<>();

    void init_tables() {
        long poly = 0xEDB88320L;
        for (long k = 0; k < 256; k++) {
            long i = k;
            for (long j = 0; j < 8; j++) {
                if ((i & 1) > 0) {
                    i >>= 1;
                    i ^= poly;
                } else {
                    i >>= 1;
                }
            }
            table.add(i);
        }

        for (int i = 0; i < 256; i++) {
            ArrayList<Long> found = new ArrayList<>();
            for (int j = 0; j < 256; j++) {
                if (table.get(j) >> 24 == i) {
                    found.add((long) j);
                }
            }
            if (!found.isEmpty()) {
                table_reverse.add(found);
            }
        }
    }

    long calc(ArrayList<Long> data) {
        long accum = ~0;
        for (Long b : data) {
            accum = table.get((int) ((accum ^ b) & 0xff)) ^ ((accum >> 8) & 0x00FFFFFF);
        }
        accum = ~accum;
        return accum & 0xffffffffL;
    }

    HashSet<ArrayList<Long>> findReverse(long desired, long accum) {
        HashSet<ArrayList<Long>> solutions = new HashSet<>();
        accum = ~accum;
        Stack<ArrayList<Long>> stack = new Stack<>();
        stack.push(new ArrayList<Long>() {{
            add(~desired);
        }});
        while (!stack.empty()) {
            ArrayList<Long> node = stack.pop();
            for (Long j : table_reverse.get((int) ((node.get(0) >> 24) & 0xff))) {
                if (node.size() == 4) {
                    long a = accum;
                    ArrayList<Long> data = new ArrayList<>();
                    node.remove(0);
                    node.add(j);
                    for (int i = 3; i > -1; i--) {
                        data.add((a ^ node.get(i)) & 0xff);
                        a >>= 8;
                        a = a ^ table.get(Math.toIntExact(node.get(i)));
                    }
                    solutions.add(data);
                } else {
                    ArrayList<Long> merge = new ArrayList<>();
                    merge.add((node.get(0) ^ table.get(Math.toIntExact(j))) << 8);
                    for (int i = 1; i < node.size(); i++) {
                        merge.add(node.get(i));
                    }
                    merge.add(j);
                    stack.push(merge);
                }

            }
        }
        return solutions;
    }

    String reverse_callback(long desired) {
        List<String> ret = new ArrayList<>();
        init_tables();
        int accum = 0;
        // 4-byte patch
        HashSet<ArrayList<Long>> patches = findReverse(desired, accum);
        for (ArrayList<Long> patch : patches) {
            long checksum = calc(patch);
            if (checksum == desired) {
                boolean isValid = true;
                for (Long p : patch) {
                    if (permitted_characters.indexOf(Math.toIntExact(p)) < 0) {
                        isValid = false;
                        break;
                    }
                }
                if (isValid) {
                    StringBuilder sb = new StringBuilder();
                    patch.stream().map(cc -> (char) cc.intValue()).forEach(sb::append);
                    ret.add(sb.toString());
                }
            }
        }
        // 6-byte alphanumeric patches
        char[] permitChars = permitted_characters.toCharArray();
        for (char i : permitChars) {
            for (char j : permitChars) {
                ArrayList<Long> patch = new ArrayList<Long>() {{
                    add((long) i);
                    add((long) j);
                }};
                crackPatch(desired, ret, patch);
            }
        }
        for (char i : permitChars) {
            ArrayList<Long> patch = new ArrayList<Long>() {{
                add((long) i);
            }};
            crackPatch(desired, ret, patch);
        }

        Collections.sort(ret);
        return String.join("\n", ret);
    }

    private void crackPatch(long desired, List<String> ret, ArrayList<Long> patch) {
        HashSet<ArrayList<Long>> patches;
        patches = findReverse(desired, calc(patch));
        for (ArrayList<Long> last_4_bytes : patches) {
            boolean isValid = true;
            for (Long p : last_4_bytes) {
                if (permitted_characters.indexOf(Math.toIntExact(p)) < 0) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                patch.addAll(last_4_bytes);
                StringBuilder sb = new StringBuilder();
                patch.stream().map(cc -> (char) cc.intValue()).forEach(sb::append);
                ret.add(sb.toString());
            }
        }
    }

    public String crack(List<Long> checkSums) {
        StringBuilder sb = new StringBuilder();
        for (Long desired : checkSums) {
            String ret = reverse_callback(desired);
            sb.append(String.format("0x%02x\n", desired)).append("result:\n").append(ret).append("\n\n");
        }
        return sb.toString();
    }
}
