package com.example.peter.filedemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by peter on 2018/6/8.
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * Write byte stream to file
     *
     * @param fileName The name of the file to open.
     * @param content The input content
     *
     * */
    public static void writeByteData(String fileName, String content) {

        FileOutputStream fos = null;

        try {
            File file = new File(fileName);

            if(!file.exists()) {
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Read byte stream from file
     *
     * @param fileName The name of the file to open.
     *
     * */
    public static String readByteData(String fileName) {
        FileInputStream fis = null;
        StringBuffer content = new StringBuffer();

        try {
            File file = new File(fileName);

            if(!file.exists()) {
                return null;
            }

            fis = new FileInputStream(file);
            int length;
            byte[] buf = new byte[1024];

            while ((length = fis.read(buf)) != -1) {
                content.append(new String(buf,0,length));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content.toString();
    }

    /**
     * 1.先将文件中的内容读入到输入流中
     * 2.将输入流中的数据通过输出流写入到目标文件中
     * 3.关闭输入流和输出流
     */

    public static void copyFileByByte(String fromFilename, String toFilename) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            long begin=System.currentTimeMillis();
            //从输入流中读取数据
            fis = new FileInputStream(fromFilename);
            //向输出流中写入数据
            fos = new FileOutputStream(toFilename);
            //先定义一个字节缓冲区，减少I/O次数，提高读写效率
            byte[] buffer = new byte[1024*8];
            int size;
            while((size = fis.read(buffer)) != -1){
                fos.write(buffer, 0, size);
            }

            long end=System.currentTimeMillis();
            System.out.println("使用文件输入流和文件输出流实现文件的复制完毕！耗时："+(end-begin)+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Write byte stream to file by buffered stream
     *
     * @param fileName The name of the file to open.
     * @param content The input content
     *
     * */
    public static void writeByteDataWithBuffer(String fileName, String content) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            File file = new File(fileName);

            if(!file.exists()) {
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(content.getBytes());
            bos.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(fos != null) {
                    fos.close();
                }
                if(bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read byte stream from file by buffered stream
     *
     * @param fileName The name of the file to open.
     *
     * */
    public static String readByteDataWithBuffer(String fileName) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        StringBuffer content = new StringBuffer();

        try {
            File file = new File(fileName);

            if(!file.exists()) {
                return null;
            }

            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int length;
            byte[] buf = new byte[1024];

            while ((length = bis.read(buf)) != -1) {
                content.append(new String(buf,0,length));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content.toString();
    }

    /**
     * Input character stream to file
     *
     * @param fileName The name of the file to open; can not contain path separators.
     * @param content The input content
     *
     * */
    public static void writeCharacterData(String fileName, String content) {
        BufferedWriter bw = null;
        try {
            File file = new File(fileName);

            if(!file.exists()) {
                file.createNewFile();
            }

            bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get character byte stream from file
     *
     * @param fileName The name of the file to open; can not contain path separators.
     *
     * */
    public static String readCharacterData(String fileName) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();

        try{
            File file = new File(fileName);

            if(!file.exists()) {
                return null;
            }

            br = new BufferedReader(new FileReader(fileName));
            String content;
            while ((content = br.readLine()) != null ) {
                sb.append(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * 从Asset目录下的txt文件中读取数据
     *
     * @param context
     * @param fileName 文件名字，如："xxxx.txt"
     * @return String 以字符串的形式返回
     */

    public static String getContentFromAsset(Context context, String fileName) {
        if(fileName == null) {
            throw new RuntimeException("File name is null!");
        }

        StringBuilder stringBuilder = new StringBuilder();

        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                stringBuilder.append(content);
//                Log.d(TAG,"getContentFromAsset: " + content);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            Log.e(TAG,"getContentFromAsset",e);
        }

        return null;
    }

    /** buffer size in bytes */
    private final static int BUFFER_SIZE = 1024*8;

    /**
     * copy file using FileInputStream & FileOutputStream

     * @param src copy from
     * @param dest copy to

     * @return;
     */
    public static void copyWithFileStream(File src, File dest){
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            if(!dest.exists()) {
                dest.createNewFile();
            }

            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);

            byte[] buffer = new byte[BUFFER_SIZE];
            int copySize;

            while ((copySize = fis.read(buffer)) != -1){
                fos.write(buffer, 0, copySize);
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * copy file using BufferedInputStream & BufferedOutputStream

     * @param src copy from file
     * @param dest copy to file

     * @return;
     */
    public static void copyWithBufferedStream(File src, File dest){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            if(!dest.exists()) {
                dest.createNewFile();
            }

            bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] buffer = new byte[BUFFER_SIZE];
            int copySize;

            while ((copySize = bis.read(buffer)) != -1){
                bos.write(buffer, 0, copySize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null) {
                    bis.close();
                }
                if(bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

