package org.catrobat.catroid.io;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.InvalidPathException;
import org.catrobat.catroid.common.Constants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/StorageOperations.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class StorageOperations {
    private static final String FILE_NAME_APPENDIX = "_#";
    public static final String TAG = "StorageOperations";

    private StorageOperations() {
        throw new AssertionError();
    }

    public static void createDir(File dir) throws IOException {
        if (dir.isFile()) {
            throw new IOException(dir.getAbsolutePath() + " exists, but is not a directory.");
        }
        if (!dir.exists() && !dir.mkdir()) {
            throw new IOException("Cannot create directory at: " + dir.getAbsolutePath());
        }
    }

    public static void createSceneDirectory(File sceneDir) throws IOException {
        createDir(sceneDir);
        File file = new File(sceneDir, Constants.IMAGE_DIRECTORY_NAME);
        createDir(file);
        new File(file, Constants.NO_MEDIA_FILE).createNewFile();
        File file2 = new File(sceneDir, Constants.SOUND_DIRECTORY_NAME);
        createDir(file2);
        new File(file2, Constants.NO_MEDIA_FILE).createNewFile();
    }

    public static String getSanitizedFileName(String fileName) {
        int iLastIndexOf = fileName.lastIndexOf(46);
        int iLastIndexOf2 = fileName.lastIndexOf(FILE_NAME_APPENDIX);
        if (iLastIndexOf2 != -1) {
            iLastIndexOf = iLastIndexOf2;
        }
        return iLastIndexOf == -1 ? fileName : fileName.substring(0, iLastIndexOf);
    }

    public static String resolveFileName(ContentResolver contentResolver, Uri uri) {
        int columnIndex;
        String string = null;
        if (uri.getScheme().equals(FirebaseAnalytics.Param.CONTENT)) {
            try {
                Cursor cursorQuery = contentResolver.query(uri, null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst() && (columnIndex = cursorQuery.getColumnIndex("_display_name")) >= 0) {
                            string = cursorQuery.getString(columnIndex);
                        }
                    } catch (Throwable th) {
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Exception unused) {
                Log.e(TAG, "Cannot query content resolver for filename.");
            }
        }
        if (string != null) {
            return string;
        }
        String path = uri.getPath();
        int iLastIndexOf = path.lastIndexOf(47);
        return iLastIndexOf != -1 ? path.substring(iLastIndexOf + 1) : path;
    }

    public static void writeToFile(File file, String content) throws Throwable {
        BufferedWriter bufferedWriter = null;
        try {
            BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file), 8192);
            try {
                bufferedWriter2.write(content);
                bufferedWriter2.flush();
                try {
                    bufferedWriter2.close();
                } catch (IOException unused) {
                    Log.e(TAG, "Cannot close buffered writer after exception occurred during writing process.");
                }
            } catch (Throwable th) {
                th = th;
                bufferedWriter = bufferedWriter2;
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException unused2) {
                        Log.e(TAG, "Cannot close buffered writer after exception occurred during writing process.");
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static File compressBitmapToPng(Bitmap bitmap, File destinationFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return destinationFile;
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static File duplicateFile(File source) throws IOException {
        return copyFileToDir(source, source.getParentFile());
    }

    public static File copyFile(File sourceFile, File destinationFile) throws IOException {
        if (!sourceFile.exists()) {
            throw new FileNotFoundException(sourceFile.getAbsolutePath() + " does not exist.");
        }
        transferData(sourceFile, destinationFile);
        return destinationFile;
    }

    public static File copyFileToDir(File sourceFile, File destinationDir) throws IOException {
        if (!sourceFile.exists()) {
            throw new FileNotFoundException(sourceFile.getAbsolutePath() + " does not exist.");
        }
        if (!destinationDir.exists()) {
            throw new FileNotFoundException(destinationDir.getAbsolutePath() + " does not exist.");
        }
        if (!destinationDir.isDirectory()) {
            throw new IOException(destinationDir.getAbsolutePath() + " is not a directory.");
        }
        File uniqueFile = getUniqueFile(sourceFile.getName(), destinationDir);
        transferData(sourceFile, uniqueFile);
        return uniqueFile;
    }

    public static File copyStreamToFile(InputStream inputStream, File destinationFile) throws IOException {
        if (!destinationFile.exists() && !destinationFile.createNewFile()) {
            throw new IOException("Cannot create file: " + destinationFile.getAbsolutePath() + ".");
        }
        return transferData(inputStream, destinationFile);
    }

    private static File getDestinationFile(File destinationDir, String fileName) throws InvalidPathException, IOException {
        if (!destinationDir.exists()) {
            throw new FileNotFoundException("Destination directory: " + destinationDir.getAbsolutePath() + " does not exist.");
        }
        if (!destinationDir.isDirectory()) {
            throw new IOException(destinationDir.getAbsolutePath() + " is not a directory.");
        }
        File uniqueFile = getUniqueFile(fileName, destinationDir);
        if (uniqueFile.createNewFile()) {
            return uniqueFile;
        }
        throw new IOException("Cannot create file: " + uniqueFile.getAbsolutePath() + ".");
    }

    public static File copyStreamToDir(InputStream inputStream, File destinationDir, String fileName) throws InvalidPathException, IOException {
        return transferData(inputStream, getDestinationFile(destinationDir, fileName));
    }

    public static File compressAndCopyStreamToDir(InputStream inputStream, File destinationDir, String fileName) throws InvalidPathException, IOException {
        File destinationFile = getDestinationFile(destinationDir, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
        BitmapFactory.decodeStream(inputStream).compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        inputStream.close();
        fileOutputStream.close();
        return destinationFile;
    }

    public static File copyUriToDir(ContentResolver contentResolver, Uri uri, File destinationDir, String fileName) throws IOException {
        InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
        if ("image/webp".equals(contentResolver.getType(uri))) {
            return compressAndCopyStreamToDir(inputStreamOpenInputStream, destinationDir, fileName);
        }
        return copyStreamToDir(inputStreamOpenInputStream, destinationDir, fileName);
    }

    public static void copyFileContentToUri(ContentResolver contentResolver, Uri uri, File sourceFile) throws IOException {
        byte[] bArr = new byte[8192];
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        try {
            OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uri);
            while (true) {
                try {
                    int i = fileInputStream.read(bArr);
                    if (i == -1) {
                        break;
                    } else {
                        outputStreamOpenOutputStream.write(bArr, 0, i);
                    }
                } catch (Throwable th) {
                    if (outputStreamOpenOutputStream != null) {
                        try {
                            outputStreamOpenOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
                try {
                    fileInputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th;
            }
            if (outputStreamOpenOutputStream != null) {
                outputStreamOpenOutputStream.close();
            }
            fileInputStream.close();
        } catch (Throwable th4) {
            fileInputStream.close();
            throw th4;
        }
    }

    public static void transferData(File sourceFile, File destinationFile) throws IOException {
        FileChannel channel = new FileInputStream(sourceFile).getChannel();
        try {
            FileChannel channel2 = new FileOutputStream(destinationFile).getChannel();
            try {
                channel.transferTo(0L, channel.size(), channel2);
                if (channel2 != null) {
                    channel2.close();
                }
                if (channel != null) {
                    channel.close();
                }
            } catch (Throwable th) {
                if (channel2 != null) {
                    try {
                        channel2.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    private static File transferData(InputStream inputStream, File destinationFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
        byte[] bArr = new byte[8192];
        while (true) {
            try {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    inputStream.close();
                    fileOutputStream.close();
                    return destinationFile;
                }
            } catch (Throwable th) {
                inputStream.close();
                fileOutputStream.close();
                throw th;
            }
        }
    }

    public static File copyDir(File sourceDir, File destinationDir) throws IOException {
        if (!sourceDir.exists()) {
            throw new FileNotFoundException("Directory: " + sourceDir.getAbsolutePath() + " does not exist.");
        }
        if (!sourceDir.isDirectory()) {
            throw new IOException(sourceDir.getAbsolutePath() + " is not a directory.");
        }
        destinationDir.mkdir();
        if (!destinationDir.isDirectory()) {
            throw new IOException("Cannot create directory: " + destinationDir.getAbsolutePath());
        }
        File[] fileArrListFiles = sourceDir.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    copyDir(file, new File(destinationDir, file.getName()));
                } else {
                    copyFileToDir(file, destinationDir);
                }
            }
        }
        return destinationDir;
    }

    public static synchronized File getUniqueFile(String originalName, File destinationDir) throws IOException {
        File file = new File(destinationDir, originalName);
        if (!file.exists()) {
            return file;
        }
        int iLastIndexOf = originalName.lastIndexOf(46);
        if (iLastIndexOf == -1) {
            iLastIndexOf = originalName.length();
        }
        int iLastIndexOf2 = originalName.lastIndexOf(FILE_NAME_APPENDIX);
        if (iLastIndexOf2 == -1) {
            iLastIndexOf2 = iLastIndexOf;
        }
        String strSubstring = originalName.substring(iLastIndexOf);
        String strSubstring2 = originalName.substring(0, iLastIndexOf2);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file2 = new File(destinationDir, strSubstring2 + FILE_NAME_APPENDIX + i + strSubstring);
            if (!file2.exists()) {
                return file2;
            }
        }
        throw new IOException("Cannot find a unique file name in " + destinationDir.getAbsolutePath() + ".");
    }

    public static void deleteFile(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File: " + file.getAbsolutePath() + " does not exist.");
        }
        if (!file.delete()) {
            throw new IOException("Cannot delete file: " + file.getAbsolutePath());
        }
    }

    public static void deleteDir(File dir) throws IOException {
        if (!dir.exists()) {
            throw new FileNotFoundException(dir.getAbsolutePath() + " does not exist.");
        }
        if (!dir.isDirectory()) {
            throw new FileNotFoundException(dir.getAbsolutePath() + " is not a directory.");
        }
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    deleteFile(file);
                }
            }
        }
        if (!dir.delete()) {
            throw new IOException("Cannot delete directory: " + dir.getAbsolutePath());
        }
    }
}
