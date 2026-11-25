package com.reandroid.apkeditor.protect;

import com.android.apksig.apk.ApkUtils;
import com.android.dex.DexFormat;
import com.reandroid.apk.ResFile;
import com.reandroid.apk.UncompressedFiles;
import com.reandroid.apkeditor.utils.CyclicIterator;
import com.reandroid.archive.Archive;

/* JADX INFO: loaded from: classes10.dex */
public class FileNameConfuser extends Confuser {
    private final CyclicIterator<String> namesIterator;

    public FileNameConfuser(Protector protector) {
        super(protector, "FileNameConfuser: ");
        this.namesIterator = new CyclicIterator<>(loadFileNames());
    }

    @Override // com.reandroid.apkeditor.protect.Confuser
    public void confuse() {
        logMessage("Confusing ...");
        UncompressedFiles uncompressedFiles = getApkModule().getUncompressedFiles();
        for (ResFile resFile : getApkModule().listResFiles()) {
            int method = resFile.getInputSource().getMethod();
            String strGenerateNewPath = generateNewPath(resFile);
            if (strGenerateNewPath != null) {
                String filePath = resFile.getFilePath();
                if (method == Archive.STORED) {
                    uncompressedFiles.replacePath(filePath, strGenerateNewPath);
                }
                resFile.setFilePath(strGenerateNewPath);
                onPathChanged(filePath, strGenerateNewPath);
            }
        }
    }

    private String generateNewPath(ResFile resFile) {
        if (isKeepType(resFile.pickOne().getTypeName())) {
            return null;
        }
        return generateNewPath(resFile.getFilePath());
    }

    private String generateNewPath(String str) {
        CyclicIterator<String> cyclicIterator = this.namesIterator;
        cyclicIterator.resetCycleCount();
        while (cyclicIterator.getCycleCount() == 0) {
            String strReplaceSimpleName = replaceSimpleName(str, cyclicIterator.next());
            if (!containsFilePath(strReplaceSimpleName)) {
                return strReplaceSimpleName;
            }
        }
        return null;
    }

    private static String replaceSimpleName(String str, String str2) {
        String strSubstring;
        int iLastIndexOf = str.lastIndexOf(47);
        String strSubstring2 = "";
        if (iLastIndexOf < 0) {
            strSubstring = "";
        } else {
            int i = iLastIndexOf + 1;
            strSubstring = str.substring(0, i);
            str = str.substring(i);
        }
        int iLastIndexOf2 = str.lastIndexOf(46);
        if (iLastIndexOf2 >= 0) {
            strSubstring2 = ".9.png";
            if (!str.endsWith(".9.png")) {
                strSubstring2 = str.substring(iLastIndexOf2);
            }
        }
        return strSubstring + str2 + strSubstring2;
    }

    private static String[] loadFileNames() {
        return new String[]{".", "//", "///", "////", "\\\\", "\\\\\\", "\\/", " ", "  ", DexFormat.DEX_IN_JAR_NAME, ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME, "AndroidManifest", "resources.arsc"};
    }
}
