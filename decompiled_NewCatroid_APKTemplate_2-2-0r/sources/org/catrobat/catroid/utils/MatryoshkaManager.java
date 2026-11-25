package org.catrobat.catroid.utils;

import android.content.Context;
import android.util.Log;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.ZipArchiver;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/MatryoshkaManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MatryoshkaManager {
    private static final String DUMMY_PATH = "Scene/sounds/dummy.mp3";
    private static final String TAG = "MatryoshkaManager";

    public static File packForUpload(Context context, Project realProject) throws Exception {
        return null;
    }

    private static void copyScreenshots(File sourceDir, File destDir) {
        String[] strArr = {Constants.SCREENSHOT_AUTOMATIC_FILE_NAME, Constants.SCREENSHOT_MANUAL_FILE_NAME};
        for (int i = 0; i < 2; i++) {
            String str = strArr[i];
            File file = new File(sourceDir, str);
            if (file.exists()) {
                try {
                    StorageOperations.copyFile(file, new File(destDir, str));
                } catch (Exception unused) {
                    Log.e(TAG, "Ошибка копирования скриншота: " + str);
                }
            }
        }
    }

    private static File findFileRecursive(File dir, String name2) {
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles == null) {
            return null;
        }
        for (File file : fileArrListFiles) {
            if (file.isDirectory()) {
                File fileFindFileRecursive = findFileRecursive(file, name2);
                if (fileFindFileRecursive != null) {
                    return fileFindFileRecursive;
                }
            } else if (file.getName().equalsIgnoreCase(name2)) {
                return file;
            }
        }
        return null;
    }

    private static void modifyStubXml(File xmlFile, Project realProject) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                bufferedReader.close();
                String strReplace = sb.toString().replace("###PROJECT_NAME###", escapeXml(realProject.getName())).replace("###DESCRIPTION###", escapeXml("Made with NewCatroid and some love :3 \n" + realProject.getDescription()));
                FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
                try {
                    fileOutputStream.write(strReplace.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.close();
                    return;
                } catch (Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            sb.append(line).append("\n");
        }
    }

    private static String escapeXml(String s) {
        if (s == null) {
            return "";
        }
        return s.replace(ContainerUtils.FIELD_DELIMITER, "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    public static boolean unpackIfMatryoshka(File projectDir) {
        Log.d(TAG, "Проверка проекта на наличие матрешки в: " + projectDir.getAbsolutePath());
        ArrayList<File> arrayList = new ArrayList();
        findWavFilesRecursive(projectDir, arrayList);
        for (File file : arrayList) {
            File file2 = new File(projectDir.getParent(), "payload_extract_" + UUID.randomUUID() + Constants.ZIP_EXTENSION);
            try {
                try {
                    if (NewCatroidPayloadHelper.extractPayloadFromAudio(file, file2)) {
                        StorageOperations.deleteDir(projectDir);
                        projectDir.mkdirs();
                        new ZipArchiver().unzip(file2, projectDir);
                        file2.delete();
                        if (!file2.exists()) {
                            return true;
                        }
                        file2.delete();
                        return true;
                    }
                    if (file2.exists()) {
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Ошибка при попытке распаковки: " + e.getMessage());
                    if (file2.exists()) {
                    }
                }
                file2.delete();
            } catch (Throwable th) {
                if (file2.exists()) {
                    file2.delete();
                }
                throw th;
            }
        }
        Log.d(TAG, "Это обычный проект.");
        return false;
    }

    private static void findWavFilesRecursive(File dir, List<File> fileList) {
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            if (file.isDirectory()) {
                findWavFilesRecursive(file, fileList);
            } else if (file.getName().toLowerCase().endsWith(".mp3")) {
                fileList.add(file);
            }
        }
    }
}
