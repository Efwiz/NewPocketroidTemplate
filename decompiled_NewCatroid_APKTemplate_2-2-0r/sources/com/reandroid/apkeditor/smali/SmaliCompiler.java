package com.reandroid.apkeditor.smali;

import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkModuleEncoder;
import com.reandroid.apk.DexEncoder;
import com.reandroid.apkeditor.APKEditor;
import com.reandroid.archive.FileInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.dex.model.DexFile;
import com.reandroid.dex.sections.Marker;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.io.FileIterator;
import com.reandroid.utils.io.IOUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jf.dexlib2.extra.DexMarker;
import org.jf.smali.Smali;
import org.jf.smali.SmaliOptions;

/* JADX INFO: loaded from: classes10.dex */
public class SmaliCompiler implements DexEncoder {
    private APKLogger apkLogger;
    private Integer minSdkVersion;
    private final boolean noCache;

    public static int apiToDexVersion(int i) {
        if (i <= 23) {
            return 35;
        }
        switch (i) {
            case 24:
            case 25:
                return 37;
            case 26:
            case 27:
                return 38;
            case 28:
            default:
                return 39;
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                return 40;
            case 35:
                return 41;
        }
    }

    public SmaliCompiler(boolean z) {
        this.noCache = z;
    }

    @Override // com.reandroid.apk.DexEncoder
    public List<InputSource> buildDexFiles(ApkModuleEncoder apkModuleEncoder, File file) throws IOException {
        File file2 = new File(file, "smali");
        if (!file2.isDirectory()) {
            return null;
        }
        AndroidManifestBlock androidManifest = apkModuleEncoder.getApkModule().getAndroidManifest();
        if (androidManifest != null) {
            this.minSdkVersion = androidManifest.getMinSdkVersion();
        }
        if (this.minSdkVersion == null) {
            this.minSdkVersion = 30;
        }
        ArrayList arrayList = new ArrayList();
        List<File> listListClassesDirectories = listClassesDirectories(file2);
        int size = listListClassesDirectories.size();
        Iterator<File> it = listListClassesDirectories.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            arrayList.add(build("(" + StringsUtil.formatNumber(i, size) + "/" + size + ") ", it.next()));
        }
        return arrayList;
    }

    private InputSource build(String str, File file) throws IOException {
        File dexCache = toDexCache(file);
        if (isModified(file, dexCache)) {
            return build(str, file, dexCache);
        }
        logMessage(str + "Cached: " + dexCache.getName());
        return new FileInputSource(dexCache, dexCache.getName());
    }

    private InputSource build(String str, File file, File file2) throws IOException {
        if (APKEditor.isExperimental()) {
            return buildExperimental(str, file, file2);
        }
        return buildJesusFreke(str, file, file2);
    }

    private InputSource buildJesusFreke(String str, File file, File file2) throws IOException {
        logMessage(str + "Smali: " + file2.getName());
        SmaliOptions smaliOptions = new SmaliOptions();
        File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        smaliOptions.outputDexFile = file2.getAbsolutePath();
        File file3 = new File(file, DexMarker.FILE_NAME);
        if (file3.isFile()) {
            smaliOptions.markersListFile = file3.getAbsolutePath();
        }
        if (smaliOptions.jobs <= 0) {
            smaliOptions.jobs = 1;
        }
        Integer num = this.minSdkVersion;
        if (num != null) {
            smaliOptions.apiLevel = num.intValue();
        }
        if (!Smali.assemble(smaliOptions, file.getAbsolutePath())) {
            throw new IOException("Failed to build smali, check the logs");
        }
        return new FileInputSource(file2, file2.getName());
    }

    private InputSource buildExperimental(String str, File file, File file2) throws IOException {
        logMessage(str + "Smali: " + file2.getName());
        DexFile dexFileCreateDefault = DexFile.createDefault();
        FileIterator fileIterator = new FileIterator(file, FileIterator.getExtensionFilter(".smali"));
        while (fileIterator.hasNext()) {
            File next = fileIterator.next();
            try {
                dexFileCreateDefault.fromSmali(SmaliReader.of(next));
            } catch (Exception e) {
                throw new IOException("Error at: " + next, e);
            }
        }
        dexFileCreateDefault.refresh();
        readMarkers(dexFileCreateDefault, file);
        Integer num = this.minSdkVersion;
        dexFileCreateDefault.setVersion(apiToDexVersion(num != null ? num.intValue() : 0));
        dexFileCreateDefault.clearEmptySections();
        dexFileCreateDefault.sortSection(SectionType.getR8Order());
        dexFileCreateDefault.shrink();
        dexFileCreateDefault.refreshFull();
        dexFileCreateDefault.write(file2);
        dexFileCreateDefault.close();
        return new FileInputSource(file2, file2.getName());
    }

    private void readMarkers(DexFile dexFile, File file) throws IOException {
        File file2 = new File(file, DexMarker.FILE_NAME);
        if (file2.isFile()) {
            logMessage("Reading markers ...");
            for (String str : StringsUtil.split(IOUtil.readUtf8(file2), '\n')) {
                Marker marker = Marker.parse(str);
                if (marker != null) {
                    dexFile.addMarker(marker);
                }
            }
        }
    }

    private boolean isModified(File file, File file2) {
        if (this.noCache || !file2.isFile()) {
            return true;
        }
        return isModified(file, file2.lastModified());
    }

    private boolean isModified(File file, long j) {
        File[] fileArrListFiles;
        if (file.lastModified() > j) {
            return true;
        }
        if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
            return false;
        }
        for (File file2 : fileArrListFiles) {
            if (isModified(file2, j)) {
                return true;
            }
        }
        return false;
    }

    private File toDexCache(File file) {
        return new File(new File(file.getParentFile().getParentFile(), ".cache"), SmaliUtil.getDexFileName(SmaliUtil.getDexNumber(file.getName())));
    }

    private List<File> listClassesDirectories(File file) {
        File[] fileArrListFiles;
        ArrayList arrayList = new ArrayList();
        if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
            return arrayList;
        }
        for (File file2 : fileArrListFiles) {
            if (!SmaliUtil.isClassesDir(file2)) {
                logMessage("WARN: Ignore: " + file2);
            } else {
                arrayList.add(file2);
            }
        }
        SmaliUtil.sortDexFiles(arrayList);
        return arrayList;
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    private void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }
}
