package ctf;

public class Vig {

    final static double freq[] = {0.0749, 0.0129, 0.0354, 0.0362, 0.1400, 0.0218, 0.0174, 0.0422, 0.0665, 0.0027, 0.0047,
            0.0357, 0.0339, 0.0674, 0.0737, 0.0243, 0.0026, 0.0614, 0.0695, 0.0985, 0.0300, 0.0116,
            0.0169, 0.0028, 0.0164, 0.0004};

    public static String encrypt(String oriText, String key) {
        String res = "";
        key = key.toUpperCase();
        String text = oriText.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                res += c;
                continue;
            }
            if (Character.isUpperCase(oriText.charAt(i))) {
                res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            } else {
                res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'a');
            }
            j = ++j % key.length();
        }
        return res;
    }

    public static String decrypt(String oriText, String key) {
        System.out.println(oriText);
        System.out.println(key);
        String res = "";
        key = key.toUpperCase();
        String text = oriText.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                res += c;
                continue;
            }
            if (Character.isUpperCase(oriText.charAt(i))) {
                res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
            } else {
                res += (char) ((c - key.charAt(j) + 26) % 26 + 'a');
            }

            j = ++j % key.length();
        }
        System.out.println(res);
        return res;
    }

    static int best_match(final double[] a, final double[] b) {
        double sum = 0, fit, d, best_fit = 1e100;
        int i, rotate, best_rotate = 0;
        for (i = 0; i < 26; i++)
            sum += a[i];
        for (rotate = 0; rotate < 26; rotate++) {
            fit = 0;
            for (i = 0; i < 26; i++) {
                d = a[(i + rotate) % 26] / sum - b[i];
                fit += d * d / b[i];
            }

            if (fit < best_fit) {
                best_fit = fit;
                best_rotate = rotate;
            }
        }

        return best_rotate;
    }

    static double freq_every_nth(final int[] msg, int len, int interval, char[] key) {
        double sum, d, ret;
        double[] accu = new double[26];
        double[] out = new double[26];
        int i, j, rot;

        for (j = 0; j < interval; j++) {
            for (i = 0; i < 26; i++)
                out[i] = 0;
            for (i = j; i < len; i += interval)
                out[msg[i]]++;
            rot = best_match(out, freq);
            try {
                key[j] = (char) (rot + 'A');
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            for (i = 0; i < 26; i++)
                accu[i] += out[(i + rot) % 26];
        }

        for (i = 0, sum = 0; i < 26; i++)
            sum += accu[i];

        for (i = 0, ret = 0; i < 26; i++) {
            d = accu[i] / sum - freq[i];
            ret += d * d / freq[i];
        }

        key[interval] = '\0';
        return ret;
    }

    public String crack(String encodedMessage) {
        StringBuilder sb = new StringBuilder();
        int lenghtOfEncodedMessage = encodedMessage.length();
        char[] encoded = new char[lenghtOfEncodedMessage];
        char[] key = new char[lenghtOfEncodedMessage];

        encodedMessage.toUpperCase().getChars(0, lenghtOfEncodedMessage, encoded, 0);
        int txt[] = new int[lenghtOfEncodedMessage];
        int len = 0, j;

        double fit, best_fit = 1e100;
        String bestKey = "";

        for (j = 0; j < lenghtOfEncodedMessage; j++) {
            if (Character.isUpperCase(encoded[j])) {
                txt[len++] = encoded[j] - 'A';
            } else if (Character.isLowerCase(encoded[j])) {
                txt[len++] = encoded[j] - 'a';
            }

        }


        for (j = 1; j < 30; j++) {
            fit = freq_every_nth(txt, len, j, key);
            sb.append(String.format("%f, key length: %2d ", fit, j));
            sb.append(key);
            if (fit < best_fit) {
                best_fit = fit;
                bestKey = new String(key, 0, j);
                sb.append(" <--- best so far");
            }
            sb.append("\n");

        }

        return bestKey + "\n" + decrypt(encodedMessage, bestKey);
    }

}
