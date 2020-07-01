package ctf;

import sun.misc.Cleaner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class ZipCenOp {
    static int[] cenFlag = new int[]{80, 75, 1, 2};
    static byte[] cenEncyptedFlag = new byte[]{9, 8};
    static byte[] cenNotEncyptedFlag = new byte[]{0, 8};

    public ZipCenOp() {
    }

    public static void main(String[] arg) {
        if (arg.length != 2 || arg[1].equals("r") || arg[1].equals("e")) {
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
        } catch (FileNotFoundException var2) {
            System.out.println("file not available");
        } catch (IOException var3) {
            System.out.println("internal error");
        }

    }

    public static String operate(String file, String method) throws FileNotFoundException, IOException {
        File zip = new File(file);
        int length = (int) zip.length();
        MappedByteBuffer buffer = (new RandomAccessFile(file, "rw")).getChannel().map(MapMode.READ_WRITE, 0L, (long) length);
        int startPosition = 0 * length;
        int count = 0;

        for (int position = startPosition; position < length; ++position) {
            for (int offset = 0; offset < 4 && buffer.get(position + offset) == cenFlag[offset]; ++offset) {
                if (offset == 3) {
                    if (method.equals("r")) {
                        buffer.put(position + 8, cenNotEncyptedFlag[0]);
                    } else if (method.equals("e")) {
                        buffer.put(position + 8, cenEncyptedFlag[0]);
                    }

                    offset += 10;
                    ++count;
                }
            }
        }

        try {
            clean(buffer);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return String.format("%s %d %s", "success", count, "flag(s) found");
    }

    public static void clean(final Object buffer) throws Exception {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method getCleanerMethod = buffer.getClass().getMethod("cleaner");
                    getCleanerMethod.setAccessible(true);
                    Cleaner cleaner = (Cleaner) getCleanerMethod.invoke(buffer);
                    cleaner.clean();
                } catch (Exception var3) {
                    var3.printStackTrace();
                }

                return null;
            }
        });
    }
}
