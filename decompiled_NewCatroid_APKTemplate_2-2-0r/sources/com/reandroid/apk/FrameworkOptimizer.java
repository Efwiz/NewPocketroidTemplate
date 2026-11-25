package com.reandroid.apk;

import com.reandroid.archive.InputSource;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.chunk.xml.ResXmlElement;
import com.reandroid.arsc.chunk.xml.ResXmlNode;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.model.FrameworkTable;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResTableEntry;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.TableEntry;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.utils.collection.ArrayCollection;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public class FrameworkOptimizer {
    private APKLogger apkLogger;
    private final ApkModule frameworkApk;
    private boolean mOptimizing;

    public FrameworkOptimizer(ApkModule apkModule) {
        this.frameworkApk = apkModule;
        this.apkLogger = apkModule.getApkLogger();
    }

    public void optimize() {
        if (this.mOptimizing) {
            return;
        }
        this.mOptimizing = true;
        if (!this.frameworkApk.hasTableBlock()) {
            logMessage("Don't have: " + TableBlock.FILE_NAME);
            this.mOptimizing = false;
            return;
        }
        optimizeTable(getFrameworkTable(), this.frameworkApk.hasAndroidManifest() ? this.frameworkApk.getAndroidManifest() : null);
        UncompressedFiles uncompressedFiles = this.frameworkApk.getUncompressedFiles();
        uncompressedFiles.clearExtensions();
        uncompressedFiles.clearPaths();
        clearFiles(this.frameworkApk.getZipEntryMap());
        logMessage("Optimized");
    }

    private void clearFiles(ZipEntryMap zipEntryMap) {
        int size = zipEntryMap.size();
        if (size == 2) {
            return;
        }
        logMessage("Removing files from: " + size);
        InputSource inputSource = zipEntryMap.getInputSource(TableBlock.FILE_NAME);
        InputSource inputSource2 = zipEntryMap.getInputSource(AndroidManifestBlock.FILE_NAME);
        zipEntryMap.clear();
        if (inputSource != null) {
            inputSource.setMethod(8);
        }
        if (inputSource2 != null) {
            inputSource2.setMethod(8);
        }
        zipEntryMap.add(inputSource);
        zipEntryMap.add(inputSource2);
        logMessage("Removed files: " + (size - zipEntryMap.size()));
    }

    private void optimizeTable(FrameworkTable frameworkTable, AndroidManifestBlock androidManifestBlock) {
        String packageName;
        int iIntValue;
        if (frameworkTable.isOptimized()) {
            return;
        }
        logMessage("Optimizing ...");
        int iCountBytes = frameworkTable.countBytes();
        if (androidManifestBlock == null) {
            packageName = "framework";
            iIntValue = 0;
        } else {
            Integer versionCode = androidManifestBlock.getVersionCode();
            iIntValue = versionCode != null ? versionCode.intValue() : 0;
            packageName = androidManifestBlock.getPackageName();
            compressManifest(androidManifestBlock);
            backupManifestValue(androidManifestBlock, frameworkTable);
        }
        logMessage("Optimizing table ...");
        frameworkTable.optimize(packageName, iIntValue);
        logMessage("Table size reduced by: " + ((((long) (iCountBytes - frameworkTable.countBytes())) * 100) / ((long) iCountBytes)) + " %");
        this.mOptimizing = false;
    }

    private FrameworkTable getFrameworkTable() {
        TableBlock tableBlock = this.frameworkApk.getTableBlock();
        if (tableBlock instanceof FrameworkTable) {
            return (FrameworkTable) tableBlock;
        }
        FrameworkTable framework = toFramework(tableBlock);
        this.frameworkApk.setTableBlock(framework);
        return framework;
    }

    private FrameworkTable toFramework(TableBlock tableBlock) {
        logMessage("Converting to framework ...");
        BlockReader blockReader = new BlockReader(tableBlock.getBytes());
        FrameworkTable frameworkTable = new FrameworkTable();
        try {
            frameworkTable.readBytes(blockReader);
        } catch (IOException e) {
            logError("Error re-loading framework: ", e);
        }
        return frameworkTable;
    }

    private void compressManifest(AndroidManifestBlock androidManifestBlock) {
        logMessage("Compressing manifest ...");
        int iCountBytes = androidManifestBlock.countBytes();
        ResXmlElement documentElement = androidManifestBlock.getDocumentElement();
        Iterator<ResXmlNode> it = getManifestElementToRemove(documentElement).iterator();
        while (it.hasNext()) {
            documentElement.remove(it.next());
        }
        ResXmlElement applicationElement = androidManifestBlock.getApplicationElement();
        if (applicationElement != null) {
            Iterator<ResXmlNode> it2 = applicationElement.listXmlNodes().iterator();
            while (it2.hasNext()) {
                applicationElement.remove(it2.next());
            }
        }
        androidManifestBlock.getStringPool().removeUnusedStrings();
        androidManifestBlock.refresh();
        logMessage("Manifest size reduced by: " + ((((long) (iCountBytes - androidManifestBlock.countBytes())) * 100) / ((long) iCountBytes)) + " %");
    }

    private List<ResXmlNode> getManifestElementToRemove(ResXmlElement resXmlElement) {
        ArrayCollection arrayCollection = new ArrayCollection();
        for (ResXmlNode resXmlNode : resXmlElement.listXmlNodes()) {
            if (resXmlNode instanceof ResXmlElement) {
                ResXmlElement resXmlElement2 = (ResXmlElement) resXmlNode;
                if (!AndroidManifestBlock.TAG_application.equals(resXmlElement2.getName())) {
                    arrayCollection.add(resXmlElement2);
                }
            }
        }
        return arrayCollection;
    }

    private void backupManifestValue(AndroidManifestBlock androidManifestBlock, TableBlock tableBlock) {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        int data;
        logMessage("Backup manifest values ...");
        ResXmlElement applicationElement = androidManifestBlock.getApplicationElement();
        if (applicationElement == null || (resXmlAttributeSearchAttributeByResourceId = applicationElement.searchAttributeByResourceId(AndroidManifestBlock.ID_icon)) == null || resXmlAttributeSearchAttributeByResourceId.getValueType() != ValueType.REFERENCE) {
            resXmlAttributeSearchAttributeByResourceId = null;
            data = 0;
        } else {
            data = resXmlAttributeSearchAttributeByResourceId.getData();
        }
        backupAttributeValues(tableBlock, androidManifestBlock.getDocumentElement());
        if (resXmlAttributeSearchAttributeByResourceId != null) {
            resXmlAttributeSearchAttributeByResourceId.setTypeAndData(ValueType.REFERENCE, data);
        }
    }

    private void backupAttributeValues(TableBlock tableBlock, ResXmlElement resXmlElement) {
        if (resXmlElement == null) {
            return;
        }
        Iterator<ResXmlAttribute> attributes = resXmlElement.getAttributes();
        while (attributes.hasNext()) {
            backupAttributeValues(tableBlock, attributes.next());
        }
        Iterator<ResXmlElement> elements = resXmlElement.getElements();
        while (elements.hasNext()) {
            backupAttributeValues(tableBlock, elements.next());
        }
    }

    private void backupAttributeValues(TableBlock tableBlock, ResXmlAttribute resXmlAttribute) {
        Entry entryWithValue;
        if (resXmlAttribute == null) {
            return;
        }
        ValueType valueType = resXmlAttribute.getValueType();
        if ((valueType != ValueType.REFERENCE && valueType != ValueType.ATTRIBUTE) || (entryWithValue = getEntryWithValue(tableBlock, resXmlAttribute.getData())) == null || isReferenceEntry(entryWithValue) || entryWithValue.isComplex()) {
            return;
        }
        ResValue value = ((ResTableEntry) entryWithValue.getTableEntry()).getValue();
        ValueType valueType2 = value.getValueType();
        if (valueType2 == ValueType.STRING) {
            resXmlAttribute.setValueAsString(value.getValueAsString());
        } else {
            resXmlAttribute.setTypeAndData(valueType2, value.getData());
        }
    }

    private Entry getEntryWithValue(TableBlock tableBlock, int i) {
        return getEntryWithValue(tableBlock, i, new HashSet());
    }

    private Entry getEntryWithValue(TableBlock tableBlock, int i, Set<Integer> set) {
        if (set.contains(Integer.valueOf(i))) {
            return null;
        }
        set.add(Integer.valueOf(i));
        ResourceEntry resource = tableBlock.getResource(i);
        Entry entry = resource.get();
        if (entry == null) {
            return null;
        }
        if (isReferenceEntry(entry)) {
            return getEntryWithValue(tableBlock, ((ResValue) entry.getTableEntry().getValue()).getData(), set);
        }
        if (!entry.isNull()) {
            return entry;
        }
        Iterator<Entry> it = resource.iterator(true);
        while (it.hasNext()) {
            Entry next = it.next();
            if (!isReferenceEntry(next) && !next.isNull()) {
                return next;
            }
        }
        return null;
    }

    private boolean isReferenceEntry(Entry entry) {
        if (entry == null || entry.isNull()) {
            return false;
        }
        TableEntry<?, ?> tableEntry = entry.getTableEntry();
        if ((tableEntry instanceof CompoundEntry) || !(tableEntry instanceof ResTableEntry)) {
            return false;
        }
        ValueType valueType = ((ResTableEntry) tableEntry).getValue().getValueType();
        return valueType == ValueType.REFERENCE || valueType == ValueType.ATTRIBUTE;
    }

    APKLogger getApkLogger() {
        return this.apkLogger;
    }

    public void setAPKLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }

    private void logError(String str, Throwable th) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logError(str, th);
        }
    }

    private void logVerbose(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logVerbose(str);
        }
    }
}
