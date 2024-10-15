package com.tushuoit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.lang.ref.Cleaner;

public class ZipCenOp {

    static int[] cenFlag = new int[]{80, 75, 1, 2}; // PKZip signature
    static byte[] cenEncyptedFlag = new byte[]{9, 8}; // Encrypted flag
    static byte[] cenNotEncyptedFlag = new byte[]{0, 8}; // Not encrypted flag

    public ZipCenOp() {
    }

    public static void main(String[] arg) {
        if (arg.length != 2 || (!arg[1].equals("r") && !arg[1].equals("e"))) {
            System.out.println("tastypear@gmail.com\ncoolapk.com");
            System.out.println("no source here, but u can reverse as u like");
            System.out.println("\nusage:");
            System.out.println("ZipCenOp.jar <option> <file>");
            System.out.println("option:");
            System.out.println("\tr : recover a PKZip");
            System.out.println("\te : do a fake encryption");
            System.exit(0);
        }

        try {
            operate(arg[1], arg[0]);
        } catch (FileNotFoundException e) {
            System.out.println("file not available");
        } catch (IOException e) {
            System.out.println("internal error");
        }
    }

    public static String operate(String file, String method) throws IOException {
        File zip = new File(file);
        int length = (int) zip.length();

        // Mapping the zip file into memory
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            MappedByteBuffer buffer = raf.getChannel().map(MapMode.READ_WRITE, 0L, length);
            int count = 0;

            // Searching for the central directory header signature (PKZip)
            for (int position = 0; position < length; ++position) {
                for (int offset = 0; offset < 4 && buffer.get(position + offset) == cenFlag[offset]; ++offset) {
                    if (offset == 3) {
                        // Found a central directory header, modify flag based on method (r or e)
                        if (method.equals("r")) {
                            buffer.put(position + 8, cenNotEncyptedFlag[0]); // Recover PKZip
                        } else if (method.equals("e")) {
                            buffer.put(position + 8, cenEncyptedFlag[0]); // Fake encryption
                        }
                        count++;
                    }
                }
            }

            // Clean up the buffer manually to release memory-mapped file
            clean(buffer);

            return String.format("success %d flag(s) found", count);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error during operation";
        }
    }

    // Clean the MappedByteBuffer by calling the `cleaner` method via reflection
    public static void clean(MappedByteBuffer buffer) throws Exception {
        Method cleanerMethod = buffer.getClass().getMethod("cleaner");
        cleanerMethod.setAccessible(true);
        Object cleaner = cleanerMethod.invoke(buffer);

        if (cleaner != null) {
            Method cleanMethod = cleaner.getClass().getMethod("clean");
            cleanMethod.invoke(cleaner);
        }
    }
}
