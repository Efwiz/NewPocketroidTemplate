package com.reandroid.apkeditor.merge;

import com.gaurav.avnc.vnc.XKeySym;
import com.reandroid.apk.ApkBundle;
import com.reandroid.apk.ApkModule;
import com.reandroid.apkeditor.CommandExecutor;
import com.reandroid.apkeditor.Util;
import com.reandroid.apkeditor.common.AndroidManifestHelper;
import com.reandroid.app.AndroidManifest;
import com.reandroid.archive.ArchiveEntry;
import com.reandroid.archive.ArchiveFile;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.chunk.xml.ResXmlElement;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.utils.HexUtil;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class Merger extends CommandExecutor<MergerOptions> {
    public Merger(MergerOptions mergerOptions) {
        super(mergerOptions, "[MERGE] ");
    }

    @Override // com.reandroid.apkeditor.CommandExecutor
    public void runCommand() throws IOException {
        boolean z;
        MergerOptions options = getOptions();
        delete(options.outputFile);
        File fileExtractFile = options.inputFile;
        if (fileExtractFile.isFile()) {
            fileExtractFile = extractFile(fileExtractFile);
            z = true;
        } else {
            z = false;
        }
        logMessage("Searching apk files ...");
        ApkBundle apkBundle = new ApkBundle();
        apkBundle.setAPKLogger(this);
        apkBundle.loadApkDirectory(fileExtractFile, z);
        logMessage("Found modules: " + apkBundle.getApkModuleList().size());
        Iterator<ApkModule> it = apkBundle.getApkModuleList().iterator();
        while (it.hasNext()) {
            String strIsProtected = Util.isProtected(it.next());
            if (strIsProtected != null) {
                logMessage(options.inputFile.getAbsolutePath());
                logMessage(strIsProtected);
                return;
            }
        }
        ApkModule apkModuleMergeModules = apkBundle.mergeModules(options.validateModules);
        if (options.resDirName != null) {
            logMessage("Renaming resources root dir: " + options.resDirName);
            apkModuleMergeModules.setResourcesRootDir(options.resDirName);
        }
        if (options.validateResDir) {
            logMessage("Validating resources dir ...");
            apkModuleMergeModules.validateResourcesDir();
        }
        if (options.cleanMeta) {
            logMessage("Clearing META-INF ...");
            clearMeta(apkModuleMergeModules);
        }
        sanitizeManifest(apkModuleMergeModules);
        Util.addApkEditorInfo(apkModuleMergeModules, getClass().getSimpleName());
        apkModuleMergeModules.refreshTable();
        apkModuleMergeModules.refreshManifest();
        logMessage("Writing apk ...");
        apkModuleMergeModules.writeApk(options.outputFile);
        apkModuleMergeModules.close();
        apkBundle.close();
        if (z) {
            Util.deleteDir(fileExtractFile);
            fileExtractFile.deleteOnExit();
        }
        logMessage("Saved to: " + options.outputFile);
    }

    private File extractFile(File file) throws IOException {
        File tmpDir = toTmpDir(file);
        logMessage("Extracting to: " + tmpDir);
        if (tmpDir.exists()) {
            logMessage("Delete: " + tmpDir);
            Util.deleteDir(tmpDir);
        }
        tmpDir.deleteOnExit();
        ArchiveFile archiveFile = new ArchiveFile(file);
        fixFilePermissions(archiveFile);
        int iExtractAll = archiveFile.extractAll(tmpDir, new Predicate() { // from class: com.reandroid.apkeditor.merge.Merger$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ArchiveEntry) obj).getName().endsWith(".apk");
            }
        }, this);
        archiveFile.close();
        if (iExtractAll != 0) {
            return tmpDir;
        }
        throw new IOException("No *.apk files found on: " + file);
    }

    private void fixFilePermissions(ArchiveFile archiveFile) {
        Iterator<ArchiveEntry> it = archiveFile.iterator();
        while (it.hasNext()) {
            it.next().getCentralEntryHeader().getFilePermissions().permissions(XKeySym.XK_sacute);
        }
    }

    private File toTmpDir(File file) {
        File file2;
        String hex8 = HexUtil.toHex8("tmp_", file.getName().hashCode());
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            file2 = new File(hex8);
        } else {
            file2 = new File(parentFile, hex8);
        }
        return Util.ensureUniqueFile(file2);
    }

    private void sanitizeManifest(ApkModule apkModule) {
        if (apkModule.hasAndroidManifest()) {
            AndroidManifestBlock androidManifest = apkModule.getAndroidManifest();
            logMessage("Sanitizing manifest ...");
            AndroidManifestHelper.removeAttributeFromManifestById(androidManifest, AndroidManifest.ID_requiredSplitTypes, this);
            AndroidManifestHelper.removeAttributeFromManifestById(androidManifest, AndroidManifest.ID_splitTypes, this);
            AndroidManifestHelper.removeAttributeFromManifestByName(androidManifest, AndroidManifest.NAME_splitTypes, this);
            AndroidManifestHelper.removeAttributeFromManifestByName(androidManifest, AndroidManifest.NAME_requiredSplitTypes, this);
            AndroidManifestHelper.removeAttributeFromManifestByName(androidManifest, AndroidManifest.NAME_splitTypes, this);
            AndroidManifestHelper.removeAttributeFromManifestAndApplication(androidManifest, AndroidManifest.ID_extractNativeLibs, this, AndroidManifest.NAME_extractNativeLibs);
            AndroidManifestHelper.removeAttributeFromManifestAndApplication(androidManifest, AndroidManifest.ID_isSplitRequired, this, AndroidManifest.NAME_isSplitRequired);
            ResXmlElement applicationElement = androidManifest.getApplicationElement();
            boolean zRemoveSplitsTableEntry = false;
            for (ResXmlElement resXmlElement : AndroidManifestHelper.listSplitRequired(applicationElement)) {
                if (!zRemoveSplitsTableEntry) {
                    zRemoveSplitsTableEntry = removeSplitsTableEntry(resXmlElement, apkModule);
                }
                logMessage("Removed-element : <" + resXmlElement.getName() + "> name=\"" + AndroidManifestHelper.getNamedValue(resXmlElement) + "\"");
                applicationElement.remove(resXmlElement);
            }
            androidManifest.refresh();
        }
    }

    private boolean removeSplitsTableEntry(ResXmlElement resXmlElement, ApkModule apkModule) {
        ResourceEntry resource;
        ResValue resValue;
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = resXmlElement.searchAttributeByResourceId(AndroidManifest.ID_name);
        boolean z = false;
        if (resXmlAttributeSearchAttributeByResourceId == null || !"com.android.vending.splits".equals(resXmlAttributeSearchAttributeByResourceId.getValueAsString())) {
            return false;
        }
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId2 = resXmlElement.searchAttributeByResourceId(AndroidManifest.ID_value);
        if (resXmlAttributeSearchAttributeByResourceId2 == null) {
            resXmlAttributeSearchAttributeByResourceId2 = resXmlElement.searchAttributeByResourceId(AndroidManifest.ID_resource);
        }
        if (resXmlAttributeSearchAttributeByResourceId2 != null && resXmlAttributeSearchAttributeByResourceId2.getValueType() == ValueType.REFERENCE) {
            if (!apkModule.hasTableBlock() || (resource = apkModule.getTableBlock().getResource(resXmlAttributeSearchAttributeByResourceId2.getData())) == null) {
                return false;
            }
            ZipEntryMap zipEntryMap = apkModule.getZipEntryMap();
            Iterator<Entry> it = resource.iterator();
            while (true) {
                z = true;
                if (!it.hasNext()) {
                    break;
                }
                Entry next = it.next();
                if (next != null && (resValue = next.getResValue()) != null) {
                    String valueAsString = resValue.getValueAsString();
                    logMessage("Removed-table-entry : " + valueAsString);
                    zipEntryMap.remove(valueAsString);
                    next.setNull(true);
                    next.getTypeBlock().getParentSpecTypePair().removeNullEntries(next.getId());
                }
            }
        }
        return z;
    }
}
