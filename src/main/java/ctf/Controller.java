package ctf;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import org.gizmore.jpk.JPK;
import stegsolve.StegSolve;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

public class Controller {

    @FXML
    private TextArea srcTextArea;
    @FXML
    private TextArea destTextArea;

    @FXML
    private TextArea plainTextArea;
    @FXML
    private TextArea cryptTextArea;
    @FXML
    private TextArea keyTextArea;
    @FXML
    private ImageView imgView;
    @FXML
    private TextArea imgOutputTextArea;

    @FXML
    private TextArea usbKeyboardPathTextArea;
    @FXML
    private TextArea usbKeyboardTextArea;

    @FXML
    private TextArea crcSrcTextArea;
    @FXML
    private TextArea crcDestTextArea;


    private File imgFile;
    private Alert alert = new Alert(Alert.AlertType.NONE);


    @FXML
    private void onImgDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        } else {
            event.consume();
        }
    }

    @FXML
    private void onDragImg(DragEvent event) {


        try {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                for (File file : db.getFiles()) {
                    System.out.println(file.getAbsolutePath());
                    imgView.setImage(new Image("file://" + file.getAbsolutePath()));
                    String meta = new ImageSteg().readMeta(file);
                    imgOutputTextArea.setText(meta);
                    imgFile = file;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        } catch (Exception exc) {
            System.out.println("Could not load image ");
        }
    }


    @FXML
    private void base64Encode(ActionEvent event) {
        String originalInput = srcTextArea.getText();
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        destTextArea.setText(encodedString);
    }

    @FXML
    private void base64Decode(ActionEvent event) {
        String encodedString = destTextArea.getText();
        encodedString = CtfUtil.trimAscii(encodedString);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        StringBuilder sb = new StringBuilder();
        sb.append(decodedString + "\n");
        try {
            int i = 1;
            while (i++ < 100) {
                decodedBytes = Base64.getDecoder().decode(decodedString.replaceAll("%3D", "="));
                decodedString = new String(decodedBytes);
                System.out.println("第" + i + "层\n" + decodedString + "\n");
                sb.append("第" + i + "层\n" + decodedString + "\n");
            }
        } catch (IllegalArgumentException ex) {

        }
        srcTextArea.setText(sb.toString());
    }

    @FXML
    private void base32Encode(ActionEvent event) {
        String originalInput = srcTextArea.getText();

        String encodedString = BaseEncoding.base32().encode(originalInput.getBytes(Charsets.US_ASCII));
        destTextArea.setText(encodedString);
    }

    @FXML
    private void base32Decode(ActionEvent event) {
        String encodedString = destTextArea.getText();
        encodedString = CtfUtil.trimAscii(encodedString);
        byte[] decodedBytes = BaseEncoding.base32().decode(encodedString);
        String decodedString = new String(decodedBytes);
        StringBuilder sb = new StringBuilder();
        sb.append(decodedString + "\n");
        try {
            int i = 1;
            while (i++ < 100) {
                decodedBytes = BaseEncoding.base32().decode(decodedString);
                decodedString = new String(decodedBytes);
                sb.append("第" + i + "层\n" + decodedString + "\n");
            }
        } catch (IllegalArgumentException ex) {

        }
        srcTextArea.setText(sb.toString());
    }

    @FXML
    private void base16Encode(ActionEvent event) {
        String originalInput = srcTextArea.getText();
        String encodedString = BaseEncoding.base16().encode(originalInput.getBytes(Charsets.US_ASCII));
        destTextArea.setText(encodedString);
    }

    @FXML
    private void base16Decode(ActionEvent event) {
        String encodedString = destTextArea.getText();
        encodedString = CtfUtil.trimAscii(encodedString);
        byte[] decodedBytes = BaseEncoding.base16().decode(encodedString);
        String decodedString = new String(decodedBytes);
        StringBuilder sb = new StringBuilder();
        sb.append(decodedString + "\n");
        try {
            int i = 1;
            while (i++ < 100) {
                decodedBytes = BaseEncoding.base16().decode(decodedString);
                decodedString = new String(decodedBytes);
                sb.append("第" + i + "层\n" + decodedString + "\n");

            }
        } catch (IllegalArgumentException ex) {

        }
        srcTextArea.setText(sb.toString());
    }

    @FXML
    private void fenceDecode(ActionEvent event) {
        String encodedString = srcTextArea.getText();
        String decodedString = Fence.decode(encodedString);
        destTextArea.setText(decodedString);
    }

    // Encrypts text using a shift od s
    @FXML
    public void caesarCipher() {
        String text = srcTextArea.getText();

        StringBuffer result = new StringBuffer();
        for (int s = 1; s < 26; s++) {
            result.append("keyLen: " + s + "\n");
            for (int i = 0; i < text.length(); i++) {
                if (!Character.isLetter(text.charAt(i))) {
                    result.append(text.charAt(i));
                } else if (Character.isUpperCase(text.charAt(i))) {
                    char ch = (char) (((int) text.charAt(i) +
                            s - 65) % 26 + 65);
                    result.append(ch);
                } else {
                    char ch = (char) (((int) text.charAt(i) +
                            s - 97) % 26 + 97);
                    result.append(ch);
                }
            }
            result.append("\n");
        }
        destTextArea.setText(result.toString());
    }

    @FXML
    private void callJPK(ActionEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("JPK");
                JPK jpk = new JPK();
                jpk.init();
                frame.add(jpk);
                frame.setSize(640, 480);
                frame.setLocation(20, 20);

//                frame.addWindowListener(new WindowAdapter() {
//                    public void windowClosing(WindowEvent e) {
//                        System.exit(0);
//                    }
//                });

                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            }
        });
    }

    @FXML
    private void vigenereEncrypt(ActionEvent event) {
        String originalInput = plainTextArea.getText();
        String key = keyTextArea.getText();
        String encodedString = Vig.encrypt(originalInput, key);
        cryptTextArea.setText(encodedString);
    }

    @FXML
    private void vigenereDecrypt(ActionEvent event) {
        String encodedString = cryptTextArea.getText();
        String key = keyTextArea.getText();
        if ("".equals(key) || key == null) {
            String decodedString = new Vig().crack(encodedString);
            plainTextArea.setText(decodedString);
        } else {
            String decodedString = Vig.decrypt(encodedString, key);
            plainTextArea.setText(decodedString);
        }

    }

    @FXML
    private void readImageInfo(ActionEvent event) {
        if (imgFile == null) {
            // set alert type
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Drag a image first");
            // show the dialog
            alert.show();
            return;
        }
        String meta = null;
        try {
            meta = new ImageSteg().readMeta(this.imgFile);
            imgOutputTextArea.setText(meta);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void analyzeKeyboard(ActionEvent event) {
        String path = usbKeyboardPathTextArea.getText();
        String decodedString = UsbKeyboardDataHacker.tshark(path);
        usbKeyboardTextArea.setText(decodedString);
    }

    @FXML
    private void analyzeMice(ActionEvent event) {
        String path = usbKeyboardPathTextArea.getText();
        UsbMiceDataHacker hacker = new UsbMiceDataHacker();
        String decodedString = hacker.tshark(path);
        usbKeyboardTextArea.setText(decodedString);


        showScatter(hacker.allPoint, "All Event");
        showScatter(hacker.movePoint, "Move Event");
        showScatter(hacker.rightPoint, "Right Event");
        showScatter(hacker.leftPoint, "Left Event");
    }

    @FXML
    private void crcCrack(ActionEvent event) {

        String srcList = crcSrcTextArea.getText();
        List<Long> checkList = Arrays.stream(srcList.split(",")).map(s -> Long.decode(s)).collect(Collectors.toList());
        String decodedString = new CrcCrack().crack(checkList);
        crcDestTextArea.setText(decodedString);

    }

    @FXML
    private void calcCrc32(ActionEvent event) {

        String input = crcSrcTextArea.getText();
        CRC32 crc32 = new CRC32();
        crc32.update(input.getBytes());
        long checksum = crc32.getValue();
        String decodedString = String.format("0x%2x", checksum);
        crcDestTextArea.setText(decodedString);

    }


    private void showScatter(ArrayList<Point> points, String title) {
        Stage stage = new Stage();
        ScatterChart<Number, Number> chart = new ScatterChart<>(new NumberAxis(), new NumberAxis());
        chart.setTitle(title);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        chart.getData().add(series);
        for (Point point : points) {
            series.getData().add(new XYChart.Data<>(point.x, point.y));
        }
        Scene scene = new Scene(chart, 640, 480);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void stegSolve(ActionEvent event) {
//        Stage stage = new Stage();
//        SwingNode swingNode = new SwingNode();
//
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                JRootPane rootPane = new StegSolve().getRootPane();
//                rootPane.setLayout();
//                swingNode.setContent(rootPane);
//            }
//        });
//        AnchorPane pane = new AnchorPane();
//        pane.getChildren().add(swingNode);
//        AnchorPane.setLeftAnchor(swingNode,0.0);
//        AnchorPane.setRightAnchor(swingNode,0.0);
//        AnchorPane.setTopAnchor(swingNode,0.0);
//        AnchorPane.setBottomAnchor(swingNode,0.0);
//
//        stage.setTitle("StegSolve");
//        stage.setScene(new Scene(pane, 320, 240));
//        stage.show();


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame jFrame = new StegSolve();
                jFrame.setMinimumSize(new Dimension(320, 240));
                jFrame.setVisible(true);
                jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            }
        });
    }

}
