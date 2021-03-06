package com.xenojoshua.xjf.util;

import com.xenojoshua.xjf.log.XjfLogger;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class XjfFile {

    /**
     * Read whole content of a file.
     * @param filePath
     * @return fileContent
     */
    public static String readWholeFile(String filePath) {
        String fileContent = null;
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(new File(filePath));
            FileChannel fc = stream.getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            fileContent = Charset.forName("UTF-8").decode(mbb).toString();
        } catch (FileNotFoundException fnfe) {
            XjfLogger.get().error(String.format("[Xjf System] File not found: %s", filePath));
        } catch (IOException ioe) {
            XjfLogger.get().error(
                String.format(
                    "[Xjf System] Exception in reading file: %s\n%s",
                        filePath,
                        ExceptionUtils.getStackTrace(ioe)
                )
            );
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ioe) {
                XjfLogger.get().error(
                    String.format(
                        "[Xjf System] Exception in closing file: %s\n%s",
                            filePath, ExceptionUtils.getStackTrace(ioe)
                    )
                );
            }
        }

        return fileContent;
    }

}
