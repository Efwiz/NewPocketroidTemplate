package org.catrobat.catroid.utils;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jgit.transport.WalkEncryption;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ModelPathProcessor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ModelPathProcessor {
    private static final List<String> PATH_KEYWORDS = Arrays.asList("map_Kd", "map_Bump", "map_Ks", "map_Ka", "map_d", "refl");
    private static final String TAG = "3DModelPathProcessor";

    public static FileHandle process(FileHandle originalObjHandle) throws IOException {
        Gdx.f82app.log(TAG, "--- Starting processing for: " + originalObjHandle.path());
        String strFindMtlFileName = findMtlFileName(originalObjHandle);
        if (strFindMtlFileName == null) {
            Gdx.f82app.error(TAG, "No mtllib found in .obj file. Aborting.");
            return originalObjHandle;
        }
        Gdx.f82app.log(TAG, "Found mtllib: " + strFindMtlFileName);
        FileHandle fileHandleSibling = originalObjHandle.sibling(strFindMtlFileName);
        if (!fileHandleSibling.exists()) {
            Gdx.f82app.error(TAG, "MTL file specified in .obj does not exist: " + fileHandleSibling.path());
            return originalObjHandle;
        }
        List<String> listFindTextureFileNames = findTextureFileNames(fileHandleSibling);
        Gdx.f82app.log(TAG, "Found " + listFindTextureFileNames.size() + " texture references in .mtl: " + listFindTextureFileNames);
        for (String str : listFindTextureFileNames) {
            FileHandle fileHandleChild = originalObjHandle.parent().child(str);
            FileHandle fileHandleLocal = Gdx.files.local(str);
            Gdx.f82app.log(TAG, "Attempting to copy from: " + fileHandleChild.path() + " (Exists: " + fileHandleChild.exists() + Constant.AFTER_QUTO);
            Gdx.f82app.log(TAG, "Attempting to copy to:   " + fileHandleLocal.path());
            if (fileHandleChild.exists()) {
                try {
                    fileHandleChild.copyTo(fileHandleLocal);
                    Gdx.f82app.log(TAG, "COPY SUCCESS: " + str);
                } catch (Exception e) {
                    Gdx.f82app.error(TAG, "COPY FAILED for: " + str, e);
                }
            } else {
                Gdx.f82app.error(TAG, "Source texture not found at path: " + fileHandleChild.path());
            }
        }
        Gdx.f82app.log(TAG, "Finished copying all textures.");
        String strPatchMtlFile = patchMtlFile(fileHandleSibling);
        FileHandle fileHandleLocal2 = Gdx.files.local("patched_" + fileHandleSibling.name());
        fileHandleLocal2.writeString(strPatchMtlFile, false);
        Gdx.f82app.log(TAG, "Created patched MTL file: " + fileHandleLocal2.path());
        String strPatchObjFile = patchObjFile(originalObjHandle, fileHandleLocal2.name());
        FileHandle fileHandleLocal3 = Gdx.files.local("patched_" + originalObjHandle.name());
        fileHandleLocal3.writeString(strPatchObjFile, false);
        Gdx.f82app.log(TAG, "Created patched OBJ file: " + fileHandleLocal3.path());
        Gdx.f82app.log(TAG, "--- Processing finished. ---");
        return fileHandleLocal3;
    }

    private static List<String> findTextureFileNames(FileHandle mtlHandle) throws IOException {
        ArrayList arrayList = new ArrayList();
        BufferedReader erVar = mtlHandle.reader(8192);
        while (true) {
            try {
                String line = erVar.readLine();
                if (line == null) {
                    break;
                }
                String strTrim = line.trim();
                Iterator<String> it = PATH_KEYWORDS.iterator();
                while (it.hasNext()) {
                    if (strTrim.startsWith(it.next())) {
                        String[] strArrSplit = strTrim.split(WalkEncryption.Vals.REGEX_WS);
                        if (strArrSplit.length > 1) {
                            String name2 = new File(strArrSplit[strArrSplit.length - 1]).getName();
                            Log.d("3DProcessor", "added: " + name2);
                            arrayList.add(name2);
                        } else {
                            continue;
                        }
                    }
                }
            } catch (Throwable th) {
                if (erVar != null) {
                    try {
                        erVar.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (erVar != null) {
            erVar.close();
        }
        return arrayList;
    }

    private static String findMtlFileName(FileHandle objHandle) throws IOException {
        String line;
        BufferedReader erVar = objHandle.reader(8192);
        do {
            try {
                line = erVar.readLine();
                if (line == null) {
                    if (erVar == null) {
                        return null;
                    }
                    erVar.close();
                    return null;
                }
            } catch (Throwable th) {
                if (erVar != null) {
                    try {
                        erVar.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } while (!line.trim().startsWith("mtllib"));
        String strTrim = line.trim().substring("mtllib".length()).trim();
        if (erVar != null) {
            erVar.close();
        }
        return strTrim;
    }

    private static String patchMtlFile(FileHandle mtlHandle) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader erVar = mtlHandle.reader(8192);
        while (true) {
            try {
                String line = erVar.readLine();
                if (line == null) {
                    break;
                }
                sb.append(patchLine(line)).append("\n");
            } catch (Throwable th) {
                if (erVar != null) {
                    try {
                        erVar.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (erVar != null) {
            erVar.close();
        }
        return sb.toString();
    }

    private static String patchLine(String line) {
        String strTrim = line.trim();
        Iterator<String> it = PATH_KEYWORDS.iterator();
        while (it.hasNext()) {
            if (strTrim.startsWith(it.next())) {
                String[] strArrSplit = strTrim.split(WalkEncryption.Vals.REGEX_WS);
                if (strArrSplit.length > 1) {
                    strArrSplit[strArrSplit.length - 1] = new File(strArrSplit[strArrSplit.length - 1]).getName();
                    return String.join(" ", strArrSplit);
                }
            }
        }
        return line;
    }

    private static String patchObjFile(FileHandle objHandle, String newMtlName) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader erVar = objHandle.reader(8192);
        while (true) {
            try {
                String line = erVar.readLine();
                if (line == null) {
                    break;
                }
                if (line.trim().startsWith("mtllib")) {
                    sb.append("mtllib ").append(newMtlName).append("\n");
                } else {
                    sb.append(line).append("\n");
                }
            } catch (Throwable th) {
                if (erVar != null) {
                    try {
                        erVar.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (erVar != null) {
            erVar.close();
        }
        return sb.toString();
    }
}
