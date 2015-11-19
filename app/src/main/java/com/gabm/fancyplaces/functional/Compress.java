// by jon simon
// http://www.jondev.net/articles/Zipping_Files_with_Android_%28Programmatically%29

package com.gabm.fancyplaces.functional;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compress {
    private static final int BUFFER = 2048;

    public static void zip(String folder, String zipFile)
    {
        zip(folder, getListFiles(new File(folder)), zipFile);
    }
    public static void zip(String baseFolder, List<File> files, String zipFile) {
        try  {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFile);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for (File file : files) {
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);
                String relativeFileName = file.getAbsolutePath().replace(baseFolder + File.separator , "");
                ZipEntry entry = new ZipEntry(relativeFileName);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private static List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }


}