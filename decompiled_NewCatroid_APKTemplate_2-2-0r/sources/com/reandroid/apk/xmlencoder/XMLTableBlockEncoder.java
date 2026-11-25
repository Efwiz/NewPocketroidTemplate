package com.reandroid.apk.xmlencoder;

import com.reandroid.apk.APKLogger;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.ApkUtil;
import com.reandroid.apk.FrameworkApk;
import com.reandroid.archive.BlockInputSource;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.coder.ReferenceString;
import com.reandroid.arsc.coder.xml.XmlCoder;
import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.io.FileUtil;
import com.reandroid.utils.io.IOUtil;
import com.reandroid.xml.StyleDocument;
import com.reandroid.xml.XMLDocument;
import com.reandroid.xml.XMLElement;
import com.reandroid.xml.XMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.ASTExpr;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes10.dex */
public class XMLTableBlockEncoder {
    private APKLogger apkLogger;
    private final ApkModule apkModule;
    private Integer mMainPackageId;
    private final Set<File> parsedFiles;
    private final TableBlock tableBlock;

    public XMLTableBlockEncoder(ApkModule apkModule, TableBlock tableBlock) {
        this.parsedFiles = new HashSet();
        this.apkModule = apkModule;
        this.tableBlock = tableBlock;
        if (!apkModule.hasTableBlock()) {
            BlockInputSource blockInputSource = new BlockInputSource(TableBlock.FILE_NAME, tableBlock);
            blockInputSource.setMethod(0);
            blockInputSource.setSort(1);
            apkModule.setTableBlock(tableBlock);
            apkModule.setLoadDefaultFramework(true);
        }
        this.apkLogger = apkModule.getApkLogger();
    }

    public XMLTableBlockEncoder() {
        this(new ApkModule("encoded", new ZipEntryMap()), new TableBlock());
    }

    public Integer getMainPackageId() {
        return this.mMainPackageId;
    }

    public TableBlock getTableBlock() {
        return this.tableBlock;
    }

    public ApkModule getApkModule() {
        return this.apkModule;
    }

    public void scanMainDirectory(File file) throws IOException {
        scanResourcesDirectory(new File(file, TableBlock.DIRECTORY_NAME));
    }

    public void scanResourcesDirectory(File file) throws IOException {
        try {
            scanResourceFiles(file);
            ensureEmptyTable();
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
    }

    private void ensureEmptyTable() {
        if (getTableBlock().initializeAsEmpty()) {
            logMessage("Using <NULL> resource table");
        }
    }

    private void scanResourceFiles(File file) throws XmlPullParserException, IOException {
        List<File> listListPublicXmlFiles = ApkUtil.listPublicXmlFiles(file);
        if (listListPublicXmlFiles.size() == 0) {
            throw new IOException("No .*/values/" + PackageBlock.PUBLIC_XML + "  file found in '" + file + FormatHelper.QUOTE);
        }
        loadPublicXmlFiles(listListPublicXmlFiles);
        initializeFrameworkFromManifest(listListPublicXmlFiles);
        encodeAttrs(listListPublicXmlFiles);
        encodeValues(listListPublicXmlFiles);
        this.tableBlock.refresh();
    }

    private void loadPublicXmlFiles(List<File> list) throws IOException {
        Iterator<File> it = list.iterator();
        while (it.hasNext()) {
            loadPublicXmlFile(it.next());
        }
    }

    private void loadPublicXmlFile(File file) throws IOException {
        try {
            XmlPullParser xmlPullParserNewPullParser = XMLFactory.newPullParser(file);
            PackageBlock publicXml = this.tableBlock.parsePublicXml(xmlPullParserNewPullParser);
            publicXml.setTag(file);
            loadPackageJson(publicXml, file);
            IOUtil.close(xmlPullParserNewPullParser);
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
    }

    private void loadPackageJson(PackageBlock packageBlock, File file) throws IOException {
        File packageJson = toPackageJson(file);
        if (packageJson == null) {
            return;
        }
        packageBlock.fromJson(new JSONObject(packageJson));
    }

    private File toPackageJson(File file) {
        File parentFile;
        File parentFile2;
        File parentFile3 = file.getParentFile();
        if (parentFile3 == null || !CompoundEntry.NAME_values.equals(parentFile3.getName()) || (parentFile = parentFile3.getParentFile()) == null || (parentFile2 = parentFile.getParentFile()) == null) {
            return null;
        }
        File file2 = new File(parentFile2, "package.json");
        if (file2.isFile()) {
            return file2;
        }
        return null;
    }

    private void initializeFrameworkFromManifest(List<File> list) throws IOException {
        Iterator<File> it = list.iterator();
        while (it.hasNext()) {
            File androidManifest = toAndroidManifest(it.next());
            if (androidManifest.isFile()) {
                initializeFrameworkFromManifest(androidManifest);
                return;
            }
        }
    }

    private void encodeValues(List<File> list) throws XmlPullParserException, IOException {
        logMessage("Encoding values ...");
        FilePathEncoder filePathEncoder = new FilePathEncoder(getApkModule());
        TableBlock tableBlock = getTableBlock();
        for (File file : list) {
            addParsedFiles(file);
            PackageBlock packageBlockByTag = tableBlock.getPackageBlockByTag(file);
            tableBlock.setCurrentPackage(packageBlockByTag);
            File resDirectory = toResDirectory(file);
            encodeResDir(resDirectory);
            filePathEncoder.setApkLogger(getApkLogger());
            filePathEncoder.encodePackageResDir(packageBlockByTag, resDirectory);
            packageBlockByTag.sortTypes();
            packageBlockByTag.refresh();
        }
    }

    private void encodeAttrs(List<File> list) throws XmlPullParserException, IOException {
        logMessage("Encoding attrs ...");
        TableBlock tableBlock = getTableBlock();
        for (File file : list) {
            addParsedFiles(file);
            PackageBlock packageBlockByTag = tableBlock.getPackageBlockByTag(file);
            tableBlock.setCurrentPackage(packageBlockByTag);
            List<File> listListAttrs = listAttrs(file);
            if (listListAttrs.size() != 0) {
                for (File file2 : listListAttrs) {
                    logVerbose("Encoding: " + FileUtil.shortPath(file2, 4));
                    XmlCoder.getInstance().VALUES_XML.encode(file2, packageBlockByTag);
                    addParsedFiles(file2);
                }
                packageBlockByTag.sortTypes();
            }
        }
    }

    private void excludeIds(List<File> list) {
        Iterator<File> it = list.iterator();
        while (it.hasNext()) {
            addParsedFiles(it.next());
        }
    }

    private void initializeFrameworkFromManifest(File file) throws IOException {
        if (AndroidManifestBlock.FILE_NAME_BIN.equals(file.getName())) {
            initializeFrameworkFromBinaryManifest();
            return;
        }
        try {
            XMLDocument xMLDocumentLoad = XMLDocument.load(file);
            TableBlock tableBlock = getTableBlock();
            FrameworkApk frameworkApkInitializeAndroidFramework = getApkModule().initializeAndroidFramework(xMLDocumentLoad);
            if (frameworkApkInitializeAndroidFramework != null) {
                tableBlock.addFramework(frameworkApkInitializeAndroidFramework.getTableBlock());
            }
            initializeMainPackageId(xMLDocumentLoad);
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
    }

    private void initializeFrameworkFromBinaryManifest() throws IOException {
        ApkModule apkModule = getApkModule();
        if (apkModule.hasTableBlock() && apkModule.hasAndroidManifest()) {
            logMessage("Initialize framework from binary manifest ...");
            getTableBlock().addFramework(apkModule.initializeAndroidFramework(apkModule.getAndroidFrameworkVersion()).getTableBlock());
        }
    }

    private void initializeMainPackageId(XMLDocument xMLDocument) {
        XMLElement element;
        String attributeValue;
        XMLElement documentElement = xMLDocument.getDocumentElement();
        if (documentElement == null || (element = documentElement.getElement(AndroidManifestBlock.TAG_application)) == null || (attributeValue = element.getAttributeValue(AndroidManifestBlock.NAME_icon)) == null) {
            return;
        }
        logMessage("Set main package id from manifest: " + attributeValue);
        ReferenceString reference = ReferenceString.parseReference(attributeValue);
        if (reference == null) {
            logMessage("Something wrong on : " + AndroidManifestBlock.NAME_icon);
            return;
        }
        int iResolveResourceId = getTableBlock().resolveResourceId(reference.packageName, reference.type, reference.f225name);
        if (iResolveResourceId == 0) {
            logMessage("WARN: failed to resolve: " + reference);
            return;
        }
        int i = (iResolveResourceId >> 24) & 255;
        this.mMainPackageId = Integer.valueOf(i);
        logMessage("Main package id initialized: id = " + HexUtil.toHex2((byte) i) + ", from: " + reference);
    }

    private void encodeResDir(File file) throws XmlPullParserException, IOException {
        preloadStyledStrings(file);
        Iterator<File> it = ApkUtil.listValuesDirectory(file).iterator();
        while (it.hasNext()) {
            encodeValuesDir(it.next());
        }
    }

    private void preloadStyledStrings(File file) throws XmlPullParserException, IOException {
        logVerbose("Preloading styled strings ...");
        Iterator<File> it = ApkUtil.listValuesDirectory(file).iterator();
        while (it.hasNext()) {
            Iterator<File> it2 = ApkUtil.listFiles(it.next(), "strings.xml").iterator();
            while (it2.hasNext()) {
                preloadStyledStringsXml(it2.next());
            }
        }
    }

    private void preloadStyledStringsXml(File file) throws XmlPullParserException, IOException {
        Iterator<? extends XMLElement> elements = XMLDocument.load(file).getDocumentElement().getElements();
        TableStringPool stringPool = getTableBlock().getStringPool();
        while (elements.hasNext()) {
            XMLElement next = elements.next();
            if (next.hasChildElements()) {
                stringPool.getOrCreate(StyleDocument.copyInner(next));
            }
        }
    }

    private void encodeValuesDir(File file) throws XmlPullParserException, IOException {
        List<File> listListFiles = ApkUtil.listFiles(file, ".xml");
        EncodeUtil.sortValuesXml(listListFiles);
        for (File file2 : listListFiles) {
            if (!isAlreadyParsed(file2)) {
                addParsedFiles(file2);
                logVerbose("Encoding: " + FileUtil.shortPath(file2, 4));
                XmlCoder.getInstance().VALUES_XML.encode(file2, getTableBlock().getCurrentPackage());
            }
        }
    }

    private File toAndroidManifest(File file) {
        File parentFile = toResDirectory(file).getParentFile().getParentFile().getParentFile();
        File file2 = new File(parentFile, AndroidManifestBlock.FILE_NAME_BIN);
        return !file2.isFile() ? new File(parentFile, AndroidManifestBlock.FILE_NAME) : file2;
    }

    private File toResDirectory(File file) {
        return file.getParentFile().getParentFile();
    }

    private List<File> listAttrs(File file) {
        return listValuesXml(file, ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED);
    }

    private List<File> listValuesXml(File file, String str) {
        ArrayList arrayList = new ArrayList();
        Iterator<File> it = ApkUtil.listValuesDirectory(toResDirectory(file)).iterator();
        while (it.hasNext()) {
            arrayList.addAll(findValuesXml(it.next(), str));
        }
        return arrayList;
    }

    private List<File> findValuesXml(File file, String str) {
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return arrayList;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isFile()) {
                String name2 = file2.getName();
                if (name2.endsWith(".xml") && EncodeUtil.sanitizeType(name2).equals(str)) {
                    arrayList.add(file2);
                }
            }
        }
        return arrayList;
    }

    private boolean isAlreadyParsed(File file) {
        return this.parsedFiles.contains(file);
    }

    private void addParsedFiles(File file) {
        this.parsedFiles.add(file);
    }

    public APKLogger getApkLogger() {
        return this.apkLogger;
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
        if (aPKLogger == null || this.apkModule.getApkLogger() != null) {
            return;
        }
        this.apkModule.setAPKLogger(aPKLogger);
    }

    private void logMessage(String str) {
        APKLogger apkLogger = getApkLogger();
        if (apkLogger != null) {
            apkLogger.logMessage(str);
        }
    }

    private void logVerbose(String str) {
        APKLogger apkLogger = getApkLogger();
        if (apkLogger != null) {
            apkLogger.logVerbose(str);
        }
    }
}
