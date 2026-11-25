package com.reandroid.apkeditor.protect;

import com.android.apksig.apk.ApkUtils;
import com.android.dex.DexFormat;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.DexFileInputSource;
import com.reandroid.apk.ResFile;
import com.reandroid.apk.UncompressedFiles;
import com.reandroid.apkeditor.utils.CyclicIterator;
import com.reandroid.archive.Archive;
import com.reandroid.utils.collection.CollectionUtil;
import java.util.List;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;

/* JADX INFO: loaded from: classes10.dex */
public class DirectoryConfuser extends Confuser {
    private final CyclicIterator<String> namesIterator;

    public DirectoryConfuser(Protector protector) {
        super(protector, "DirectoryConfuser: ");
        this.namesIterator = new CyclicIterator<>(loadDirNameList(protector.getApkModule()));
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
            String strReplaceDirectory = replaceDirectory(str, cyclicIterator.next());
            if (!containsFilePath(strReplaceDirectory)) {
                return strReplaceDirectory;
            }
        }
        return null;
    }

    private static String replaceDirectory(String str, String str2) {
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            iLastIndexOf = 0;
        } else {
            int i = iLastIndexOf + 1;
            if (i != str.length()) {
                iLastIndexOf = i;
            }
        }
        String strSubstring = str.substring(iLastIndexOf);
        if (str2.length() != 0) {
            str2 = str2 + "/";
        }
        return str2 + strSubstring;
    }

    private static String[] loadDirNameList(ApkModule apkModule) {
        List listAsList = CollectionUtil.asList(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME, "/AndroidManifest.xml", "resources.arsc", "/resources.arsc", DexFormat.DEX_IN_JAR_NAME, "/classes.dex", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "META-INF", "", "kotlin/annotation", "kotlin/collections", "kotlin/coroutines", "kotlin/internal", "kotlin/ranges", "kotlin/reflect", "res/values/arrays.xml", "res/values/attrs.xml", "res/values/bools.xml", "res/values/colors.xml", "res/values/dimens.xml", "res/values/drawables.xml", "res/values/ids.xml", "res/values/integers.xml", "res/values/plurals.xml", "res/values/public.xml", "res/values/strings.xml", "res/values/styles.xml");
        List<DexFileInputSource> listListDexFiles = apkModule.listDexFiles();
        int size = listListDexFiles.size();
        for (int i = 1; i < size; i++) {
            listAsList.add(listListDexFiles.get(i).getAlias());
        }
        return (String[]) listAsList.toArray(new String[0]);
    }
}
