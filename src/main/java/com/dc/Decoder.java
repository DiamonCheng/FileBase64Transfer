package com.dc;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class Decoder {

    public static void main(String[] args) throws IOException {
        int result = 0;
        File file = null;
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        fileChooser.setDialogTitle("请选择要转化的文本Base64文件...");
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
	    String outFileName=file.getName()+".bin";
        System.out.println(outFileName);
        Path in=file.toPath();
        Path out=in.getParent().resolve(outFileName);
        try(InputStream inputStream=Base64.getDecoder().wrap(Files.newInputStream(in));
            OutputStream outputStream=Files.newOutputStream(out,StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE)
        ){
            int len;
            byte[] buf=new byte[1024*1024];
            while(-1 != (len=inputStream.read(buf))){
                outputStream.write(buf,0,len);
            }
        }
        System.out.println("finished.请手动重命名文件");
    }
}
