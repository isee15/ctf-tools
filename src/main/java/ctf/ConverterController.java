package ctf;

import com.google.common.io.BaseEncoding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ConverterController {

    @FXML
    private TextArea textTextArea;
    @FXML
    private TextArea binTextArea;
    @FXML
    private TextArea hexTextArea;
    @FXML
    private TextArea octTextArea;
    @FXML
    private TextArea encodeurlTextArea;
    @FXML
    private TextArea base64TextArea;

    @FXML
    private void doConverter(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String id = btn.getId();
        //text,bin,hex,oct,encodeurl,base64
        byte[] src = null;
        switch (id) {
            case "text":

                src = textTextArea.getText().getBytes(StandardCharsets.UTF_8);

                break;
            case "bin": {
                String b = binTextArea.getText();
                src = new BigInteger(b, 2).toByteArray();
            }
            break;
            case "hex": {
                String b = hexTextArea.getText();
                src = new BigInteger(b, 16).toByteArray();
            }
            break;
            case "oct": {
                String b = octTextArea.getText();
                src = new BigInteger(b, 8).toByteArray();
            }
            break;
            case "base64": {

                src = Base64.getDecoder().decode(new String(base64TextArea.getText()).getBytes(StandardCharsets.UTF_8));

            }
            break;
            case "encodeurl": {
                String oriUrl = null;
                try {
                    oriUrl = URLDecoder.decode(encodeurlTextArea.getText(), StandardCharsets.UTF_8.toString());
                    src = oriUrl.getBytes(StandardCharsets.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
            break;
        }
        if (src != null && src.length > 0) {
            textTextArea.setText(new String(src, StandardCharsets.UTF_8));
            base64TextArea.setText(Base64.getEncoder().encodeToString(src));
            try {
                encodeurlTextArea.setText(URLEncoder.encode(textTextArea.getText(), StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            hexTextArea.setText(BaseEncoding.base16().encode(src));
            binTextArea.setText(new BigInteger(hexTextArea.getText(), 16).toString(2));
            octTextArea.setText(new BigInteger(hexTextArea.getText(), 16).toString(8));
        }
    }
}
