package com.dc;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class Encoder {

    public static void main(String[] args) throws IOException {
        int result = 0;
        File file = null;
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        fileChooser.setDialogTitle("请选择要转化的二进制文件...");
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        result = fileChooser.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == result) {
            file = fileChooser.getSelectedFile();
            path=file.getPath();
            System.out.println("文件路径: "+path);
        }else{
            return ;
        }
	    String outFileName=file.getName()+".base64.txt";
        System.out.println(outFileName);
        Path in=file.toPath();
        Path out=in.getParent().resolve(outFileName);
        try(OutputStream outputStream=Base64.getEncoder().wrap(Files.newOutputStream(out,StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
            InputStream inputStream=Files.newInputStream(in)
        ){
            int len;
            byte[] buf=new byte[1024*1024];
            while(-1 != (len=inputStream.read(buf))){
                outputStream.write(buf,0,len);
            }
        }
        System.out.println("finished.");
    }
}
