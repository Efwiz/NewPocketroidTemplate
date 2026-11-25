package com.reandroid.apk;

import com.reandroid.app.AndroidManifest;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.coder.xml.XmlCoder;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.utils.io.IOUtil;
import com.reandroid.xml.XMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class ApkModuleXmlDecoder extends ApkModuleDecoder implements Predicate<Entry> {
    private final Map<Integer, Set<ResConfig>> decodedEntries;
    private boolean keepResPath;

    public ApkModuleXmlDecoder(ApkModule apkModule) {
        super(apkModule);
        this.decodedEntries = new HashMap();
    }

    public void setKeepResPath(boolean z) {
        this.keepResPath = z;
    }

    public boolean keepResPath() {
        return this.keepResPath;
    }

    @Override // com.reandroid.apk.ApkModuleDecoder
    void initialize() {
        super.initialize();
        validateResourceNames();
    }

    @Override // com.reandroid.apk.ApkModuleDecoder
    public void decodeResourceTable(File file) throws IOException {
        TableBlock tableBlock = getApkModule().getTableBlock();
        decodeTableBlock(file, tableBlock);
        decodeResFiles(file);
        decodeValues(file, tableBlock);
    }

    private void decodeTableBlock(File file, TableBlock tableBlock) throws IOException {
        try {
            decodePackageInfo(file, tableBlock);
            decodePublicXml(file, tableBlock);
            addDecodedPath(TableBlock.FILE_NAME);
        } catch (IOException e) {
            logOrThrow("Error decoding resource table", e);
        }
    }

    private void decodePackageInfo(File file, TableBlock tableBlock) throws IOException {
        Iterator<PackageBlock> it = tableBlock.listPackages().iterator();
        while (it.hasNext()) {
            decodePackageInfo(file, it.next());
        }
    }

    private void decodePackageInfo(File file, PackageBlock packageBlock) throws IOException {
        packageBlock.toJson(false).write(new File(toPackageDirectory(file, packageBlock), PackageBlock.JSON_FILE_NAME));
    }

    private void decodeResFiles(File file) throws IOException {
        if (keepResPath()) {
            logMessage("Res files: " + TableBlock.RES_FILES_DIRECTORY_NAME);
        } else {
            logMessage("Res files: " + TableBlock.DIRECTORY_NAME);
        }
        Iterator<ResFile> it = getApkModule().listResFiles().iterator();
        while (it.hasNext()) {
            decodeResFile(file, it.next());
        }
    }

    private void decodeResFile(File file, ResFile resFile) throws IOException {
        if (resFile.isBinaryXml()) {
            try {
                decodeResXml(file, resFile);
                return;
            } catch (Exception e) {
                logOrThrow("Failed to decode: " + resFile.getFilePath(), e);
                return;
            }
        }
        String filePath = resFile.getFilePath();
        if (filePath.endsWith(".xml")) {
            logMessage("Ignore non bin xml: " + filePath);
        } else {
            decodeResRaw(file, resFile);
        }
    }

    private void decodeResRaw(File file, ResFile resFile) throws IOException {
        Entry entryPickOne = resFile.pickOne();
        File decodeResFile = toDecodeResFile(file, resFile, entryPickOne.getPackageBlock());
        InputSource inputSource = resFile.getInputSource();
        logVerbose(inputSource.getAlias());
        inputSource.write(decodeResFile);
        if (!keepResPath()) {
            addDecodedEntry(entryPickOne);
        }
        addDecodedPath(inputSource.getAlias());
    }

    private void decodeResXml(File file, ResFile resFile) throws IOException {
        Entry entryPickOne = resFile.pickOne();
        PackageBlock packageBlock = entryPickOne.getPackageBlock();
        File decodeResFile = toDecodeResFile(file, resFile, packageBlock);
        InputSource inputSource = resFile.getInputSource();
        logVerbose(inputSource.getAlias());
        serializeXml(packageBlock, resFile.getInputSource(), decodeResFile);
        if (!keepResPath()) {
            addDecodedEntry(entryPickOne);
        }
        addDecodedPath(inputSource.getAlias());
    }

    private File toDecodeResFile(File file, ResFile resFile, PackageBlock packageBlock) {
        File packageDirectory;
        String alias;
        if (keepResPath()) {
            alias = resFile.getInputSource().getAlias();
            packageDirectory = new File(file, TableBlock.RES_FILES_DIRECTORY_NAME);
        } else {
            String strBuildPath = resFile.buildPath(PackageBlock.RES_DIRECTORY_NAME);
            packageDirectory = toPackageDirectory(file, packageBlock);
            resFile.setFilePath(strBuildPath);
            alias = strBuildPath;
        }
        return new File(packageDirectory, alias.replace('/', File.separatorChar));
    }

    private void decodePublicXml(File file, TableBlock tableBlock) throws IOException {
        Iterator<PackageBlock> it = tableBlock.listPackages().iterator();
        while (it.hasNext()) {
            decodePublicXml(file, it.next());
        }
        if (tableBlock.size() == 0) {
            decodeEmptyTable(file, tableBlock);
        }
    }

    private void decodePublicXml(File file, PackageBlock packageBlock) throws IOException {
        File packageDirectory = toPackageDirectory(file, packageBlock);
        logMessage("public.xml: " + packageBlock.getName() + " -> " + packageDirectory.getName());
        packageBlock.serializePublicXml(new File(new File(new File(packageDirectory, PackageBlock.RES_DIRECTORY_NAME), PackageBlock.VALUES_DIRECTORY_NAME), PackageBlock.PUBLIC_XML));
    }

    private void decodeEmptyTable(File file, TableBlock tableBlock) throws IOException {
        logMessage("Decoding empty table ...");
        File file2 = new File(new File(file, TableBlock.DIRECTORY_NAME), PackageBlock.DIRECTORY_NAME_PREFIX + SchemaSymbols.ATTVAL_TRUE_1);
        logMessage("Empty public.xml: " + file2.getName());
        tableBlock.pickOrEmptyPackage().serializePublicXml(new File(new File(new File(file2, PackageBlock.RES_DIRECTORY_NAME), PackageBlock.VALUES_DIRECTORY_NAME), PackageBlock.PUBLIC_XML));
    }

    @Override // com.reandroid.apk.ApkModuleDecoder
    public void decodeAndroidManifest(File file) throws IOException {
        if (containsDecodedPath(AndroidManifest.FILE_NAME)) {
            return;
        }
        if (!getApkModule().hasAndroidManifest()) {
            decodeEmptyAndroidManifestXml(file);
        } else if (isExcluded(AndroidManifest.FILE_NAME)) {
            decodeAndroidManifestBin(file);
        } else {
            decodeAndroidManifestXml(file);
        }
    }

    private void decodeEmptyAndroidManifestXml(File file) throws IOException {
        logMessage("WARN: Missing " + AndroidManifest.FILE_NAME + ", could be framework apk or you are decompiling wrong apk file");
        XmlSerializer xmlSerializerNewSerializer = XMLFactory.newSerializer(new File(file, AndroidManifest.FILE_NAME));
        xmlSerializerNewSerializer.startDocument("utf-8", null);
        xmlSerializerNewSerializer.text("\n");
        xmlSerializerNewSerializer.startTag(null, AndroidManifest.EMPTY_MANIFEST_TAG);
        xmlSerializerNewSerializer.endTag(null, AndroidManifest.EMPTY_MANIFEST_TAG);
        xmlSerializerNewSerializer.endDocument();
        xmlSerializerNewSerializer.flush();
        IOUtil.close(xmlSerializerNewSerializer);
        addDecodedPath(AndroidManifest.FILE_NAME);
    }

    private void decodeAndroidManifestXml(File file) throws IOException {
        AndroidManifestBlock androidManifest = getApkModule().getAndroidManifest();
        File file2 = new File(file, AndroidManifest.FILE_NAME);
        logMessage("Decoding: " + file2.getName());
        PackageBlock packageBlock = androidManifest.getPackageBlock();
        if (packageBlock == null) {
            int iGuessCurrentPackageId = androidManifest.guessCurrentPackageId();
            TableBlock tableBlock = getApkModule().getTableBlock();
            packageBlock = tableBlock.pickOne(iGuessCurrentPackageId);
            if (packageBlock == null) {
                packageBlock = tableBlock.pickOne();
            }
        }
        serializeXml(packageBlock, androidManifest, file2);
        addDecodedPath(AndroidManifest.FILE_NAME);
    }

    private void decodeAndroidManifestBin(File file) throws IOException {
        File file2 = new File(file, AndroidManifest.FILE_NAME_BIN);
        logMessage("Decode manifest binary: " + file2.getName());
        ApkModule apkModule = getApkModule();
        InputSource manifestOriginalSource = apkModule.getManifestOriginalSource();
        if (manifestOriginalSource == null) {
            manifestOriginalSource = apkModule.getInputSource(AndroidManifest.FILE_NAME);
        }
        manifestOriginalSource.write(file2);
        addDecodedPath(AndroidManifest.FILE_NAME);
    }

    private void serializeXml(PackageBlock packageBlock, ResXmlDocument resXmlDocument, File file) throws IOException {
        if (packageBlock != null && resXmlDocument.getPackageBlock() == null) {
            resXmlDocument.setPackageBlock(packageBlock);
        }
        XmlSerializer xmlSerializerNewSerializer = XMLFactory.newSerializer(file);
        resXmlDocument.serialize(xmlSerializerNewSerializer);
        IOUtil.close(xmlSerializerNewSerializer);
    }

    private void serializeXml(PackageBlock packageBlock, InputSource inputSource, File file) throws IOException {
        ResXmlDocument resXmlDocument = new ResXmlDocument();
        resXmlDocument.readBytes(inputSource.openStream());
        resXmlDocument.setPackageBlock(packageBlock);
        serializeXml(packageBlock, resXmlDocument, file);
    }

    private void addDecodedEntry(Entry entry) {
        if (entry.isNull()) {
            return;
        }
        int resourceId = entry.getResourceId();
        Set<ResConfig> hashSet = this.decodedEntries.get(Integer.valueOf(resourceId));
        if (hashSet == null) {
            hashSet = new HashSet<>();
            this.decodedEntries.put(Integer.valueOf(resourceId), hashSet);
        }
        hashSet.add(entry.getResConfig());
    }

    private boolean containsDecodedEntry(Entry entry) {
        Set<ResConfig> set = this.decodedEntries.get(Integer.valueOf(entry.getResourceId()));
        if (set == null) {
            return false;
        }
        return set.contains(entry.getResConfig());
    }

    private void decodeValues(File file, TableBlock tableBlock) throws IOException {
        XmlCoder.getInstance().VALUES_XML.decodeTable(new File(file, TableBlock.DIRECTORY_NAME), tableBlock, this);
    }

    @Override // java.util.function.Predicate
    public boolean test(Entry entry) {
        return containsDecodedEntry(entry);
    }
}
