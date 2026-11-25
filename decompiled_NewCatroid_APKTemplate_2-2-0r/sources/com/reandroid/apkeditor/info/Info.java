package com.reandroid.apkeditor.info;

import android.R;
import com.reandroid.apk.ApkModule;
import com.reandroid.apk.ResFile;
import com.reandroid.apkeditor.CommandExecutor;
import com.reandroid.apkeditor.Options;
import com.reandroid.apkeditor.Util;
import com.reandroid.app.AndroidManifest;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.chunk.xml.ResXmlElement;
import com.reandroid.arsc.coder.EncodeResult;
import com.reandroid.arsc.coder.ReferenceString;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.AttributeDataFormat;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.dex.model.DexDirectory;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class Info extends CommandExecutor<InfoOptions> {
    private InfoWriter mInfoWriter;

    public Info(InfoOptions infoOptions) {
        super(infoOptions, "[INFO] ");
        super.setEnableLog(infoOptions.outputFile != null);
    }

    @Override // com.reandroid.apkeditor.CommandExecutor
    public void runCommand() throws IOException {
        InfoOptions options = getOptions();
        setEnableLog(options.outputFile != null);
        delete(options.outputFile);
        logMessage("Loading: " + options.inputFile);
        ApkModule apkModuleLoadApkFile = ApkModule.loadApkFile(this, options.inputFile, options.getFrameworks());
        String strIsProtected = Util.isProtected(apkModuleLoadApkFile);
        if (strIsProtected != null) {
            logWarn(strIsProtected);
            return;
        }
        apkModuleLoadApkFile.setAPKLogger(this);
        apkModuleLoadApkFile.setLoadDefaultFramework(options.verbose);
        File file = options.outputFile;
        if (file != null) {
            logMessage("Writing ...");
        }
        print(apkModuleLoadApkFile);
        flush();
        close();
        if (file != null) {
            logMessage("Saved to: " + file);
        }
    }

    private void print(ApkModule apkModule) throws IOException {
        printSourceFile();
        printPackage(apkModule);
        AndroidManifestBlock androidManifest = apkModule.getAndroidManifest();
        printVersionCode(androidManifest);
        printVersionName(androidManifest);
        printMinSdkVersion(androidManifest);
        printTargetSdkVersion(androidManifest);
        printAppName(apkModule);
        printAppIcon(apkModule);
        printAppRoundIcon(apkModule);
        printAppClass(apkModule);
        printActivities(apkModule);
        printUsesPermissions(apkModule);
        printResList(apkModule);
        printResources(apkModule);
        printDex(apkModule);
        printSignatures(apkModule);
        printXmlTree(apkModule);
        printXmlStrings(apkModule);
        printStrings(apkModule);
        listFiles(apkModule);
        listXmlFiles(apkModule);
        printConfigurations(apkModule);
        printLanguages(apkModule);
        printLocales(apkModule);
    }

    private void printSourceFile() throws IOException {
        InfoOptions options = getOptions();
        if (options.outputFile == null) {
            return;
        }
        if (options.verbose || !options.resources) {
            getInfoWriter().writeNameValue("source-file", options.inputFile.getAbsolutePath());
        }
    }

    private void printResources(ApkModule apkModule) throws IOException {
        InfoOptions options = getOptions();
        if (options.resources && apkModule.hasTableBlock()) {
            TableBlock tableBlock = apkModule.getTableBlock();
            InfoWriter infoWriter = getInfoWriter();
            boolean z = options.verbose;
            Iterator<PackageBlock> it = tableBlock.listPackages().iterator();
            while (it.hasNext()) {
                infoWriter.writeResources(it.next(), options.typeFilterList, z);
            }
        }
    }

    private void printDex(ApkModule apkModule) throws IOException {
        if (getOptions().dex) {
            getInfoWriter().writeDexInfo(DexDirectory.readStrings(apkModule.getZipEntryMap()));
        }
    }

    private void printSignatures(ApkModule apkModule) throws IOException {
        InfoOptions options = getOptions();
        if (options.signatures || options.signatures_base64) {
            getInfoWriter().writeSignatureInfo(apkModule.getApkSignatureBlock(), options.signatures_base64);
        }
    }

    private void printResList(ApkModule apkModule) throws IOException {
        InfoOptions options = getOptions();
        if (options.resList.size() != 0 && apkModule.hasTableBlock()) {
            Iterator<String> it = options.resList.iterator();
            while (it.hasNext()) {
                printRes(apkModule, it.next());
            }
        }
    }

    private void printRes(ApkModule apkModule, String str) throws IOException {
        if (str == null || str.length() < 3) {
            return;
        }
        if (str.startsWith("@0x")) {
            str = str.substring(1);
        }
        EncodeResult encodeResultEncode = ValueCoder.encode(str, AttributeDataFormat.INTEGER.valueTypes());
        if (encodeResultEncode != null) {
            printEntries(apkModule, "resource", encodeResultEncode.value);
            return;
        }
        ReferenceString reference = ReferenceString.parseReference(str);
        if (reference == null) {
            logWarn("WARN: Invalid resource: " + str);
            return;
        }
        Iterator<PackageBlock> it = apkModule.getTableBlock().listPackages().iterator();
        while (it.hasNext()) {
            ResourceEntry resource = it.next().getResource(reference.type, reference.f225name);
            if (resource != null) {
                printEntries(apkModule, "resource", resource.getResourceId());
                return;
            }
        }
        logMessage("WARN: resource not found: " + str);
    }

    private void printPackage(ApkModule apkModule) throws IOException {
        InfoOptions options = getOptions();
        if (options.packageName) {
            AndroidManifestBlock androidManifest = apkModule.getAndroidManifest();
            if (androidManifest != null) {
                getInfoWriter().writeNameValue("package", androidManifest.getPackageName());
            }
            if (options.verbose && apkModule.hasTableBlock()) {
                getInfoWriter().writePackageNames(CollectionUtil.collect(apkModule.getTableBlock().iterator()));
            }
        }
    }

    private void printVersionCode(AndroidManifestBlock androidManifestBlock) throws IOException {
        if (!getOptions().versionCode || androidManifestBlock == null) {
            return;
        }
        getInfoWriter().writeNameValue("VersionCode", androidManifestBlock.getVersionCode());
    }

    private void printVersionName(AndroidManifestBlock androidManifestBlock) throws IOException {
        if (!getOptions().versionName || androidManifestBlock == null) {
            return;
        }
        getInfoWriter().writeNameValue("VersionName", androidManifestBlock.getVersionName());
    }

    private void printMinSdkVersion(AndroidManifestBlock androidManifestBlock) throws IOException {
        if (!getOptions().minSdkVersion || androidManifestBlock == null) {
            return;
        }
        getInfoWriter().writeNameValue("MinSdkVersion", androidManifestBlock.getMinSdkVersion());
    }

    private void printTargetSdkVersion(AndroidManifestBlock androidManifestBlock) throws IOException {
        if (!getOptions().targetSdkVersion || androidManifestBlock == null) {
            return;
        }
        getInfoWriter().writeNameValue("TargetSdkVersion", androidManifestBlock.getTargetSdkVersion());
    }

    private void printAppName(ApkModule apkModule) throws IOException {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        if (getOptions().appName && (resXmlAttributeSearchAttributeByResourceId = apkModule.getAndroidManifest().getApplicationElement().searchAttributeByResourceId(AndroidManifest.ID_label)) != null) {
            if (resXmlAttributeSearchAttributeByResourceId.getValueType() == ValueType.STRING) {
                getInfoWriter().writeNameValue("AppName", resXmlAttributeSearchAttributeByResourceId.getValueAsString());
            } else {
                printEntries(apkModule, "AppName", resXmlAttributeSearchAttributeByResourceId.getData());
            }
        }
    }

    private void printAppIcon(ApkModule apkModule) throws IOException {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        if (getOptions().appIcon && (resXmlAttributeSearchAttributeByResourceId = apkModule.getAndroidManifest().getApplicationElement().searchAttributeByResourceId(AndroidManifest.ID_icon)) != null) {
            if (resXmlAttributeSearchAttributeByResourceId.getValueType() == ValueType.STRING) {
                getInfoWriter().writeNameValue("AppIcon", resXmlAttributeSearchAttributeByResourceId.getValueAsString());
            } else {
                printEntries(apkModule, "AppIcon", resXmlAttributeSearchAttributeByResourceId.getData());
            }
        }
    }

    private void printAppRoundIcon(ApkModule apkModule) throws IOException {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId;
        if (getOptions().appRoundIcon && (resXmlAttributeSearchAttributeByResourceId = apkModule.getAndroidManifest().getApplicationElement().searchAttributeByResourceId(R.attr.roundIcon)) != null) {
            if (resXmlAttributeSearchAttributeByResourceId.getValueType() == ValueType.STRING) {
                getInfoWriter().writeNameValue("AppRoundIcon", resXmlAttributeSearchAttributeByResourceId.getValueAsString());
            } else {
                printEntries(apkModule, "AppRoundIcon", resXmlAttributeSearchAttributeByResourceId.getData());
            }
        }
    }

    private void printUsesPermissions(ApkModule apkModule) throws IOException {
        AndroidManifestBlock androidManifest;
        if (getOptions().permissions && (androidManifest = apkModule.getAndroidManifest()) != null) {
            List<String> usesPermissions = androidManifest.getUsesPermissions();
            if (usesPermissions.size() == 0) {
                return;
            }
            usesPermissions.sort(CompareUtil.getComparableComparator());
            getInfoWriter().writeArray(AndroidManifest.TAG_uses_permission, usesPermissions.toArray(new String[0]));
        }
    }

    private void printActivities(ApkModule apkModule) throws IOException {
        AndroidManifestBlock androidManifest;
        InfoOptions options = getOptions();
        if (options.activities && (androidManifest = apkModule.getAndroidManifest()) != null) {
            List<ResXmlElement> listListActivities = androidManifest.listActivities(true);
            if (listListActivities.size() == 0) {
                return;
            }
            ResXmlElement mainActivity = androidManifest.getMainActivity();
            if (mainActivity != null) {
                getInfoWriter().writeNameValue("activity-main", getValueOfName(mainActivity));
            }
            if (options.verbose) {
                String[] strArr = new String[listListActivities.size()];
                for (int i = 0; i < listListActivities.size(); i++) {
                    strArr[i] = getValueOfName(listListActivities.get(i));
                }
                getInfoWriter().writeArray("activities", strArr);
            }
        }
    }

    private void printAppClass(ApkModule apkModule) throws IOException {
        AndroidManifestBlock androidManifest;
        ResXmlElement applicationElement;
        String valueOfName;
        if (!getOptions().appClass || (androidManifest = apkModule.getAndroidManifest()) == null || (applicationElement = androidManifest.getApplicationElement()) == null || (valueOfName = getValueOfName(applicationElement)) == null) {
            return;
        }
        getInfoWriter().writeNameValue("application-class", valueOfName);
    }

    private void printXmlStrings(ApkModule apkModule) throws IOException {
        List<String> list = getOptions().xmlStrings;
        if (list.isEmpty()) {
            return;
        }
        InfoWriter infoWriter = getInfoWriter();
        for (String str : list) {
            ResXmlDocument resXmlDocumentLoadResXmlDocument = apkModule.loadResXmlDocument(str);
            resXmlDocumentLoadResXmlDocument.setApkFile(null);
            resXmlDocumentLoadResXmlDocument.setPackageBlock(null);
            infoWriter.writeStringPool(str, resXmlDocumentLoadResXmlDocument.getStringPool());
        }
    }

    private void printStrings(ApkModule apkModule) throws IOException {
        if (getOptions().strings && apkModule.hasTableBlock()) {
            getInfoWriter().writeStringPool(TableBlock.FILE_NAME, apkModule.getTableBlock().getStringPool());
        }
    }

    private void printXmlTree(ApkModule apkModule) throws IOException {
        InfoOptions options = getOptions();
        InfoWriter infoWriter = getInfoWriter();
        for (String str : options.xmlTree) {
            logMessage("Writing: " + str);
            ResXmlDocument resXmlDocumentLoadResXmlDocument = apkModule.loadResXmlDocument(str);
            resXmlDocumentLoadResXmlDocument.setApkFile(null);
            resXmlDocumentLoadResXmlDocument.setPackageBlock(null);
            infoWriter.writeXmlDocument(str, resXmlDocumentLoadResXmlDocument);
        }
    }

    private void listFiles(ApkModule apkModule) throws IOException {
        if (getOptions().listFiles) {
            InputSource[] inputSources = apkModule.getInputSources();
            int length = inputSources.length;
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = inputSources[i].getAlias();
            }
            getInfoWriter().writeArray("Files", strArr);
        }
    }

    private void listXmlFiles(ApkModule apkModule) throws IOException {
        if (getOptions().listXmlFiles) {
            List<ResFile> listListResFiles = apkModule.listResFiles();
            ArrayList arrayList = new ArrayList();
            for (ResFile resFile : listListResFiles) {
                if (resFile.isBinaryXml()) {
                    arrayList.add(resFile.getFilePath());
                }
            }
            getInfoWriter().writeArray("CompiledXmlFiles", arrayList.toArray(new String[0]));
        }
    }

    private void printConfigurations(ApkModule apkModule) throws IOException {
        if (getOptions().configurations && apkModule.hasTableBlock()) {
            List uniqueList = CollectionUtil.toUniqueList(ComputeIterator.of(apkModule.getTableBlock().getResConfigs(), new Function() { // from class: com.reandroid.apkeditor.info.Info$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Info.lambda$printConfigurations$0((ResConfig) obj);
                }
            }));
            uniqueList.sort(CompareUtil.getComparableComparator());
            getInfoWriter().writeArray("configurations", uniqueList.toArray(new String[0]));
        }
    }

    static /* synthetic */ String lambda$printConfigurations$0(ResConfig resConfig) {
        String qualifiers = resConfig.getQualifiers();
        return qualifiers.length() != 0 ? qualifiers.substring(1) : qualifiers;
    }

    private void printLanguages(ApkModule apkModule) throws IOException {
        if (getOptions().languages && apkModule.hasTableBlock()) {
            List uniqueList = CollectionUtil.toUniqueList(ComputeIterator.of(apkModule.getTableBlock().getResConfigs(), new Function() { // from class: com.reandroid.apkeditor.info.Info$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ResConfig) obj).getLanguage();
                }
            }));
            uniqueList.sort(CompareUtil.getComparableComparator());
            getInfoWriter().writeArray("languages", uniqueList.toArray(new String[0]));
        }
    }

    private void printLocales(ApkModule apkModule) throws IOException {
        if (getOptions().locales && apkModule.hasTableBlock()) {
            List uniqueList = CollectionUtil.toUniqueList(ComputeIterator.of(apkModule.getTableBlock().getResConfigs(), new Function() { // from class: com.reandroid.apkeditor.info.Info$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ResConfig) obj).getLocale();
                }
            }));
            uniqueList.remove("");
            uniqueList.sort(CompareUtil.getComparableComparator());
            getInfoWriter().writeArray("locales", uniqueList.toArray(new String[0]));
        }
    }

    private String getValueOfName(ResXmlElement resXmlElement) {
        ResXmlAttribute resXmlAttributeSearchAttributeByResourceId = resXmlElement.searchAttributeByResourceId(AndroidManifest.ID_name);
        if (resXmlAttributeSearchAttributeByResourceId == null) {
            return null;
        }
        return resXmlAttributeSearchAttributeByResourceId.getValueAsString();
    }

    private void printEntries(ApkModule apkModule, String str, int i) throws IOException {
        TableBlock tableBlock = apkModule.getTableBlock();
        InfoWriter infoWriter = getInfoWriter();
        if (tableBlock == null) {
            infoWriter.writeNameValue(str, HexUtil.toHex8("@0x", i));
            return;
        }
        List<Entry> listResolveReference = tableBlock.resolveReference(i);
        if (listResolveReference.size() == 0) {
            logWarn("WARN: Can't find resource: " + HexUtil.toHex8("@0x", i));
            return;
        }
        List<Entry> listSortEntries = sortEntries(listResolveReference);
        if (!getOptions().verbose) {
            infoWriter.writeNameValue(str, getValueAsString(listSortEntries.get(0)));
        } else {
            infoWriter.writeEntries(str, listSortEntries);
        }
    }

    private String getValueAsString(Entry entry) {
        ResValue resValue = entry.getResValue();
        if (resValue == null) {
            return "";
        }
        ValueType valueType = resValue.getValueType();
        if (valueType == ValueType.STRING) {
            return resValue.getValueAsString();
        }
        String strDecode = ValueCoder.decode(valueType, resValue.getData());
        return strDecode != null ? strDecode : HexUtil.toHex8("@0x", resValue.getData());
    }

    private InfoWriter getInfoWriter() throws IOException {
        InfoWriter infoWriterText;
        InfoWriter infoWriter = this.mInfoWriter;
        if (infoWriter != null) {
            return infoWriter;
        }
        InfoOptions options = getOptions();
        Writer writerCreateWriter = createWriter();
        if (Options.TYPE_JSON.equals(options.type)) {
            infoWriterText = new InfoWriterJson(writerCreateWriter);
        } else if ("xml".equals(options.type)) {
            infoWriterText = new InfoWriterXml(writerCreateWriter);
        } else {
            infoWriterText = new InfoWriterText(writerCreateWriter);
        }
        this.mInfoWriter = infoWriterText;
        return infoWriterText;
    }

    private Writer createWriter() throws IOException {
        File file = getOptions().outputFile;
        if (file == null) {
            return new PrintWriter(System.out);
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        return new OutputStreamWriter(new FileOutputStream(file));
    }

    private void flush() throws IOException {
        InfoWriter infoWriter = this.mInfoWriter;
        if (infoWriter != null) {
            infoWriter.flush();
        }
    }

    private void close() throws IOException {
        InfoWriter infoWriter = this.mInfoWriter;
        if (infoWriter != null) {
            infoWriter.close();
            this.mInfoWriter = null;
        }
    }

    private static List<Entry> sortEntries(Collection<Entry> collection) {
        ArrayList arrayList;
        if (collection instanceof ArrayList) {
            arrayList = (ArrayList) collection;
        } else {
            arrayList = new ArrayList(collection);
        }
        arrayList.sort(new Comparator<Entry>() { // from class: com.reandroid.apkeditor.info.Info.1
            @Override // java.util.Comparator
            public int compare(Entry entry, Entry entry2) {
                return entry.getResConfig().compareTo(entry2.getResConfig());
            }
        });
        return arrayList;
    }
}
