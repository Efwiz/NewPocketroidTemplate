package com.reandroid.apk.xmlencoder;

import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.ApkUtil;
import com.reandroid.apkeditor.Options;
import com.reandroid.archive.FileInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.coder.xml.XmlEncodeException;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.xml.source.XMLFileParserSource;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class FilePathEncoder {
    private final ApkModule apkModule;
    private PackageBlock mCurrentPackage;
    private APKLogger mLogger;

    public FilePathEncoder(ApkModule apkModule) {
        this.apkModule = apkModule;
        this.mLogger = apkModule.getApkLogger();
    }

    private PackageBlock getCurrentPackage() {
        if (this.mCurrentPackage == null) {
            TableBlock tableBlock = this.apkModule.getTableBlock();
            if (tableBlock == null) {
                throw new NullPointerException("TableBlock == null");
            }
            PackageBlock packageBlockPickOne = tableBlock.pickOne();
            if (packageBlockPickOne == null) {
                throw new NullPointerException("PackageBlock == null");
            }
            this.mCurrentPackage = packageBlockPickOne;
        }
        return this.mCurrentPackage;
    }

    public void encodePackageResDir(PackageBlock packageBlock, File file) throws IOException {
        this.mCurrentPackage = packageBlock;
        String str = file.getParentFile().getName() + File.separator + file.getName();
        logMessage("Scan: " + str);
        int iEncodeTypeDir = 0;
        for (File file2 : ApkUtil.listDirectories(file)) {
            if (!ApkUtil.isValuesDirectoryName(file2.getName(), true)) {
                iEncodeTypeDir += encodeTypeDir(file2);
            }
        }
        logMessage("Scanned " + iEncodeTypeDir + " files: " + str);
    }

    public int encodeTypeDir(File file) throws IOException {
        List<File> listListFiles = ApkUtil.listFiles(file, null);
        Iterator<File> it = listListFiles.iterator();
        while (it.hasNext()) {
            encodeTypeFileEntry(it.next());
        }
        return listListFiles.size();
    }

    public void encodeTypeFileEntry(File file) throws IOException {
        String typeNameFromResFile = EncodeUtil.getTypeNameFromResFile(file);
        String qualifiersFromResFile = EncodeUtil.getQualifiersFromResFile(file);
        String entryNameFromResFile = EncodeUtil.getEntryNameFromResFile(file);
        String entryPathFromResFile = EncodeUtil.getEntryPathFromResFile(file);
        PackageBlock currentPackage = getCurrentPackage();
        ResourceEntry localResource = currentPackage.getTableBlock().getLocalResource(currentPackage, typeNameFromResFile, entryNameFromResFile);
        if (localResource == null) {
            throw new XmlEncodeException("Local resource not defined: @" + typeNameFromResFile + "/" + entryNameFromResFile + ", for path: " + entryPathFromResFile);
        }
        localResource.getOrCreate(qualifiersFromResFile).setValueAsString(entryPathFromResFile);
        addInputSource(createInputSource(localResource.getPackageBlock(), entryPathFromResFile, file));
    }

    private InputSource createInputSource(PackageBlock packageBlock, String str, File file) {
        if (isXmlFile(file)) {
            return createXMLEncodeInputSource(packageBlock, str, file);
        }
        return createRawFileInputSource(str, file);
    }

    private InputSource createRawFileInputSource(String str, File file) {
        FileInputSource fileInputSource = new FileInputSource(file, str);
        fileInputSource.setMethod(0);
        return fileInputSource;
    }

    private InputSource createXMLEncodeInputSource(PackageBlock packageBlock, String str, File file) {
        XMLEncodeSource xMLEncodeSource = new XMLEncodeSource(packageBlock, new XMLFileParserSource(str, file));
        xMLEncodeSource.setApkLogger(this.mLogger);
        return xMLEncodeSource;
    }

    private boolean isXmlFile(File file) {
        if (!file.getName().endsWith(".xml")) {
            return false;
        }
        if (!Options.TYPE_RAW.equals(EncodeUtil.getTypeNameFromResFile(file))) {
            return true;
        }
        logMessage("WARN: Using un-encoded raw xml: " + file);
        return false;
    }

    private void addInputSource(InputSource inputSource) {
        if (inputSource != null) {
            this.apkModule.add(inputSource);
        }
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.mLogger = aPKLogger;
    }

    private void logMessage(String str) {
        APKLogger aPKLogger = this.mLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }
}
