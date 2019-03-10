package com.imagine.chattingapp.client.view;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.imagine.chattingapp.common.clientservices.ReceiveFileService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.pdfsam.ui.FillProgressIndicator;

/**
 * FXML Controller class
 *
 * @author Mahmoud Shereif
 */
public class FileTransferProgressController implements Initializable {
    
    @FXML
    private Button btnOk;
    @FXML
    private FillProgressIndicator progInd;
    
    File choosenFile;
    RemoteInputStream remoteInputStream;
    String extention;
    String fileName;
    double fileLength;
    double bytesSent = 0;
    
    InputStream istream = null;
    
    FileOutputStream ostream = null;
    
    byte[] buf;
    int bytesRead;
    
    public FileTransferProgressController() {
        
    }
    
    FileTransferProgressController(File choosenFile, RemoteInputStream remoteInputStream, String ext, String fileName, double fileLength) {
        this.remoteInputStream = remoteInputStream;
        this.extention = ext;
        this.fileName = fileName;
        this.fileLength = fileLength;
        this.choosenFile = choosenFile;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Thread fileThread = new Thread(() -> {
            try {
                
                istream = RemoteInputStreamClient.wrap(remoteInputStream);
                
                ostream = new FileOutputStream(choosenFile);
                System.out.println("Writing file " + choosenFile);
                
                buf = new byte[1024 * 1024];
                
                bytesRead = 0;
                while ((bytesRead = istream.read(buf)) >= 0) {
                    
                    
                    try {
                        ostream.write(buf, 0, bytesRead);
                        bytesSent += 1024 * 1024;
                        Platform.runLater(() -> {
                            progInd.setProgress((int) (bytesSent * 100 / fileLength));
                            System.out.println((int) (bytesSent * 100 / fileLength));
                        });
                        ostream.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(FileTransferProgressController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                System.out.println("Finished writing file " + choosenFile);
                
            } catch (IOException ex) {
                Logger.getLogger(FileTransferProgressController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (istream != null) {
                        istream.close();
                    }
                    if (ostream != null) {
                        ostream.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FileTransferProgressController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        fileThread.start();
        
        
    }
    
    @FXML
    private void btnOkOnAction(ActionEvent event) {
    }
    
}
