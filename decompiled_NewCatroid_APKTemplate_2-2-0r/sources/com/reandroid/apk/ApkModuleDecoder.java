package com.reandroid.apk;

import com.reandroid.archive.ArchiveInfo;
import com.reandroid.archive.InputSource;
import com.reandroid.archive.block.ApkSignatureBlock;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.dex.model.DexDirectory;
import com.reandroid.dex.sections.Marker;
import com.reandroid.identifiers.PackageIdentifier;
import com.reandroid.identifiers.TableIdentifier;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ApkModuleDecoder extends ApkModuleCoder {
    private final ApkModule apkModule;
    private DecodeFilter mDecodeFilter;
    private final Set<String> mDecodedPaths = new HashSet();
    private DexDecoder mDexDecoder;
    private boolean mLogErrors;

    abstract void decodeAndroidManifest(File file) throws IOException;

    public abstract void decodeResourceTable(File file) throws IOException;

    public ApkModuleDecoder(ApkModule apkModule) {
        this.apkModule = apkModule;
        setApkLogger(apkModule.getApkLogger());
    }

    public final void decode(File file) throws IOException {
        initialize();
        decodeArchiveInfo(file);
        decodeUncompressedFiles(file);
        decodeAndroidManifest(file);
        decodeResourceTable(file);
        decodeDexFiles(file);
        extractRootFiles(file);
        decodePathMap(file);
        dumpSignatures(file);
    }

    public void extractRootFiles(File file) throws IOException {
        logMessage("Extracting root files ...");
        File file2 = new File(file, "root");
        for (InputSource inputSource : this.apkModule.getInputSources()) {
            if (!containsDecodedPath(inputSource.getAlias())) {
                extractRootFile(file2, inputSource);
                addDecodedPath(inputSource.getAlias());
            }
        }
    }

    public void decodeDexInfo(File file) throws IOException {
        File file2 = new File(file, "dex-info.json");
        logMessage("Decode: " + file2.getName());
        DexDirectory strings = DexDirectory.readStrings(this.apkModule.getZipEntryMap());
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        Iterator<Marker> markers = strings.getMarkers();
        while (markers.hasNext()) {
            jSONArray.put(markers.next().getJsonObject());
        }
        jSONObject.put("markers", jSONArray);
        jSONObject.write(file2);
    }

    public void decodeArchiveInfo(File file) throws IOException {
        logMessage("Decode: " + new File(file, ArchiveInfo.JSON_FILE).getName());
        this.apkModule.getZipEntryMap().getOrCreateArchiveInfo().writeToDirectory(file);
    }

    public void decodeUncompressedFiles(File file) throws IOException {
        File file2 = new File(file, UncompressedFiles.JSON_FILE);
        logMessage("Decode: " + file2.getName());
        UncompressedFiles uncompressedFiles = new UncompressedFiles();
        uncompressedFiles.addCommonExtensions();
        uncompressedFiles.addPath(getApkModule().getZipEntryMap());
        uncompressedFiles.toJson().write(file2);
    }

    public void decodeDexFiles(File file) throws IOException {
        ApkModule apkModule = getApkModule();
        List<DexFileInputSource> listListDexFiles = apkModule.listDexFiles();
        getDexDecoder().decodeDex(apkModule, file);
        Iterator<DexFileInputSource> it = listListDexFiles.iterator();
        while (it.hasNext()) {
            addDecodedPath(it.next().getAlias());
        }
    }

    @Override // com.reandroid.apk.ApkModuleCoder
    public ApkModule getApkModule() {
        return this.apkModule;
    }

    public DexDecoder getDexDecoder() {
        if (this.mDexDecoder == null) {
            DexFileRawDecoder dexFileRawDecoder = new DexFileRawDecoder();
            dexFileRawDecoder.setApkLogger(getApkLogger());
            this.mDexDecoder = dexFileRawDecoder;
        }
        return this.mDexDecoder;
    }

    public void setDexDecoder(DexDecoder dexDecoder) {
        this.mDexDecoder = dexDecoder;
    }

    public void sanitizeFilePaths() {
        PathSanitizer.create(getApkModule()).sanitize();
    }

    public void dumpSignatures(File file) throws IOException {
        ApkSignatureBlock apkSignatureBlock = getApkModule().getApkSignatureBlock();
        if (apkSignatureBlock == null) {
            return;
        }
        File file2 = new File(file, "signatures");
        logMessage("Dumping signatures ...");
        apkSignatureBlock.writeSplitRawToDirectory(file2);
    }

    public void decodePathMap(File file) throws IOException {
        File file2 = new File(file, PathMap.JSON_FILE);
        PathMap pathMap = new PathMap();
        pathMap.add(getApkModule().getZipEntryMap());
        pathMap.toJson().write(file2);
    }

    public boolean containsDecodedPath(String str) {
        return this.mDecodedPaths.contains(str);
    }

    public void addDecodedPath(String str) {
        this.mDecodedPaths.add(str);
    }

    public DecodeFilter getDecodeFilter() {
        if (this.mDecodeFilter == null) {
            this.mDecodeFilter = new DecodeFilter();
        }
        return this.mDecodeFilter;
    }

    public void setDecodeFilter(DecodeFilter decodeFilter) {
        this.mDecodeFilter = decodeFilter;
    }

    boolean isExcluded(String str) {
        return getDecodeFilter().isExcluded(str);
    }

    private void extractRootFile(File file, InputSource inputSource) throws IOException {
        inputSource.write(inputSource.toFile(file));
    }

    void logOrThrow(String str, Throwable th) throws IOException {
        if (isLogErrors()) {
            logError(str, th);
            return;
        }
        if (str == null && th == null) {
            return;
        }
        if (th == null) {
            th = new IOException(str);
        }
        if (th instanceof IOException) {
            throw ((IOException) th);
        }
        throw new IOException(th);
    }

    public void validateResourceNames() {
        logMessage("Validating resource names ...");
        TableBlock tableBlock = this.apkModule.getTableBlock();
        TableIdentifier tableIdentifier = new TableIdentifier();
        tableIdentifier.load(tableBlock);
        String strValidateSpecNames = tableIdentifier.validateSpecNames();
        if (strValidateSpecNames == null) {
            logMessage("All resource names are valid");
            return;
        }
        if (tableBlock.removeUnusedSpecs()) {
            strValidateSpecNames = strValidateSpecNames + ", removed specs";
        }
        logMessage(strValidateSpecNames);
    }

    public void validateResourceNames(PackageBlock packageBlock) {
        logMessage("Validating: " + packageBlock.getName());
        PackageIdentifier packageIdentifier = new PackageIdentifier();
        packageIdentifier.load(packageBlock);
        String strValidateSpecNames = packageIdentifier.validateSpecNames();
        if (strValidateSpecNames == null) {
            logMessage("[" + packageBlock.getName() + "] All resource names are valid");
        } else if (packageBlock.removeUnusedSpecs()) {
            logMessage("[" + packageBlock.getName() + "]" + strValidateSpecNames + ", removed specs");
        }
    }

    void initialize() {
        this.mDecodedPaths.clear();
        ensureTableBlock();
    }

    private void ensureTableBlock() {
        if (getApkModule().ensureTableBlock()) {
            logMessage("Missing " + TableBlock.FILE_NAME + ", created empty");
        }
    }

    public boolean isLogErrors() {
        return this.mLogErrors;
    }

    public void setLogErrors(boolean z) {
        this.mLogErrors = z;
    }

    static File toPackageDirectory(File file, PackageBlock packageBlock) {
        return new File(new File(file, TableBlock.DIRECTORY_NAME), packageBlock.buildDecodeDirectoryName());
    }
}
