package ctf;

import com.google.common.io.BaseEncoding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class ManchemsterController {

    @FXML
    private TextArea pathTextArea;
    @FXML
    private TextArea outputTextArea;


    @FXML
    private void decode1(ActionEvent event) {
        try {
            String src = pathTextArea.getText();
            src = new BigInteger(src, 16).toString(2);
            src = "00" + src;
            StringBuilder sb = new StringBuilder();
            char[] srcArr = src.toCharArray();
            for (int i = 0; i < srcArr.length / 2; i++) {
                char c = srcArr[i * 2];
                char c1 = srcArr[i * 2 + 1];
                if (c == '1' && c1 == '0') {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            packRet(sb);
        } catch (Exception ex) {
            outputTextArea.setText(ex.getMessage());
        }
    }

    private void packRet(StringBuilder sb) {
        String bin = sb.toString();
        byte[] ret = new BigInteger(bin, 2).toByteArray();
        sb.append("\n");
        sb.append(new String(ret, StandardCharsets.UTF_8));
        sb.append("\n");
        sb.append(BaseEncoding.base16().encode(ret));
        outputTextArea.setText(sb.toString());
    }

    @FXML
    public void decode2() throws IOException {
        try {
            String src = pathTextArea.getText();
            src = new BigInteger(src, 16).toString(2);
            src = "00" + src;
            StringBuilder sb = new StringBuilder();
            char[] srcArr = src.toCharArray();
            for (int i = 0; i < srcArr.length / 2; i++) {
                char c = srcArr[i * 2];
                char c1 = '0';
                if (i > 0) {
                    c1 = srcArr[i * 2 - 1];
                }
                if (c == c1) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            packRet(sb);
        } catch (Exception ex) {
            outputTextArea.setText(ex.getMessage());
        }
    }


}
