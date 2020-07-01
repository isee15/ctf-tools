package ctf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipController {

    @FXML
    private TextArea pathTextArea;
    @FXML
    private TextArea outputTextArea;

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

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
                    pathTextArea.setText(file.getAbsolutePath());
                    outputTextArea.setText(zipInfo(file.getAbsolutePath()));
                }
            }
            event.setDropCompleted(success);
            event.consume();
        } catch (Exception exc) {
            System.out.println("Could not load image ");
        }
    }

    @FXML
    private void cenOp(ActionEvent event) {
        try {
            String ret = ZipCenOp.operate(pathTextArea.getText(), "r");
            outputTextArea.setText(ret);
        } catch (Exception ex) {
            outputTextArea.setText(ex.getMessage());

        }

    }

    @FXML
    private void readCrc(ActionEvent event) {
        outputTextArea.setText(zipInfo(pathTextArea.getText()));
    }

    @FXML
    public void unzip() throws IOException {
        String fileZip = pathTextArea.getText();
        String dir = Paths.get(fileZip).getParent().toString();
        File destDir = new File(dir);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        outputTextArea.setText("unzip done");
    }

    private String zipInfo(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            ZipFile zf = new ZipFile(filePath);
            Enumeration e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) e.nextElement();
                String name = ze.getName();

                long crc = ze.getCrc();
                System.out.println("Its CRC is " + crc);
                sb.append("crc:" + String.format("0x%02x", crc) + "        " + name + "\n");
                String comment = ze.getComment();
                if (comment != null && !comment.equals("")) {
                    sb.append("comment: " + comment);
                }
                if (ze.isDirectory()) {
                    System.out.println(name + " is a directory");
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return sb.toString();
    }
}
