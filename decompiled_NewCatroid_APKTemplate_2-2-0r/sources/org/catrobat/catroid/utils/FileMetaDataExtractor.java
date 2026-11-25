package org.catrobat.catroid.utils;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.bytebuddy.description.type.TypeDescription;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.backwardcompatibility.ProjectMetaDataParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/FileMetaDataExtractor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FileMetaDataExtractor {
    private static final String TAG = "FileMetaDataExtractor";

    private FileMetaDataExtractor() {
        throw new AssertionError();
    }

    public static long getSizeOfFileOrDirectoryInByte(File fileOrDirectory) {
        long sizeOfFileOrDirectoryInByte = 0;
        if (!fileOrDirectory.exists()) {
            return 0L;
        }
        if (fileOrDirectory.isFile()) {
            return fileOrDirectory.length();
        }
        File[] fileArrListFiles = fileOrDirectory.listFiles();
        if (fileArrListFiles == null) {
            return 0L;
        }
        for (File file : fileArrListFiles) {
            sizeOfFileOrDirectoryInByte += file.isDirectory() ? getSizeOfFileOrDirectoryInByte(file) : file.length();
        }
        return sizeOfFileOrDirectoryInByte;
    }

    public static String getSizeAsString(File fileOrDirectory, Context context) {
        return Formatter.formatShortFileSize(context, getSizeOfFileOrDirectoryInByte(fileOrDirectory));
    }

    public static List<String> getProjectNames(File directory) {
        if (directory.listFiles() == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (File file : directory.listFiles()) {
            File file2 = new File(file, Constants.CODE_XML_FILE_NAME);
            if (file2.exists()) {
                try {
                    arrayList.add(new ProjectMetaDataParser(file2).getProjectMetaData().getName());
                } catch (IOException e) {
                    Log.e(TAG, "Well, that's awkward.", e);
                }
            }
        }
        return arrayList;
    }

    public static String encodeSpecialCharsForFileSystem(String projectName) {
        if (projectName.equals(".") || projectName.equals("..")) {
            return projectName.replace(".", "%2E");
        }
        return projectName.replace("%", "%25").replace("\"", "%22").replace("/", "%2F").replace(":", "%3A").replace("<", "%3C").replace(">", "%3E").replace(TypeDescription.Generic.OfWildcardType.SYMBOL, "%3F").replace("\\", "%5C").replace("|", "%7C").replace("*", "%2A");
    }

    public static String decodeSpecialCharsForFileSystem(String projectName) {
        return projectName.replace("%2E", ".").replace("%2A", "*").replace("%7C", "|").replace("%5C", "\\").replace("%3F", TypeDescription.Generic.OfWildcardType.SYMBOL).replace("%3E", ">").replace("%3C", "<").replace("%3A", ":").replace("%2F", "/").replace("%22", "\"").replace("%25", "%");
    }
}
