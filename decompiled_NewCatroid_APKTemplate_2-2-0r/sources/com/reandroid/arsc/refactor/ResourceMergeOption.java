package com.reandroid.arsc.refactor;

import com.reandroid.archive.ByteInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.ApkFile;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.model.ResourceName;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.common.BytesOutputStream;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.io.FileUtil;
import com.reandroid.utils.io.StringLineStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class ResourceMergeOption {
    private Predicate<? super ResConfig> keepConfigs;
    private Predicate<? super ResourceEntry> keepEntries;
    private Predicate<? super ResourceName> keepResourceNameFilter;
    private final Set<ResourceName> keepResourceNameList = new HashSet();

    public Predicate<? super Entry> getKeepEntryConfigs() {
        final Predicate<? super ResConfig> keepConfigs = getKeepConfigs();
        return new Predicate() { // from class: com.reandroid.arsc.refactor.ResourceMergeOption$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return keepConfigs.test(((Entry) obj).getResConfig());
            }
        };
    }

    public Predicate<? super ResConfig> getKeepConfigs() {
        Predicate<? super ResConfig> predicate = this.keepConfigs;
        return predicate == null ? CollectionUtil.getAcceptAll() : predicate;
    }

    public void setKeepConfigs(Predicate<? super ResConfig> predicate) {
        this.keepConfigs = predicate;
    }

    public Predicate<? super ResourceEntry> getKeepEntries() {
        final Predicate<? super ResourceEntry> keepEntriesInternal = getKeepEntriesInternal();
        final Predicate<? super ResourceName> keepResourceName = getKeepResourceName();
        return keepResourceName != null ? new Predicate() { // from class: com.reandroid.arsc.refactor.ResourceMergeOption$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResourceMergeOption.lambda$getKeepEntries$1(keepEntriesInternal, keepResourceName, (ResourceEntry) obj);
            }
        } : keepEntriesInternal;
    }

    static /* synthetic */ boolean lambda$getKeepEntries$1(Predicate predicate, Predicate predicate2, ResourceEntry resourceEntry) {
        if (predicate.test(resourceEntry)) {
            return true;
        }
        ResourceName resourceName = resourceEntry.toResourceName();
        if (resourceName != null) {
            return predicate2.test(resourceName);
        }
        return false;
    }

    private Predicate<? super ResourceEntry> getKeepEntriesInternal() {
        Predicate<? super ResourceEntry> predicate = this.keepEntries;
        if (predicate == null) {
            predicate = new Predicate() { // from class: com.reandroid.arsc.refactor.ResourceMergeOption$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ResourceMergeOption.lambda$getKeepEntriesInternal$2((ResourceEntry) obj);
                }
            };
            this.keepEntries = predicate;
        }
        return CollectionUtil.orFilter(predicate, getKeepStyleEntries());
    }

    static /* synthetic */ boolean lambda$getKeepEntriesInternal$2(ResourceEntry resourceEntry) {
        return !resourceEntry.isEmpty();
    }

    private Predicate<? super ResourceEntry> getKeepStyleEntries() {
        return new Predicate() { // from class: com.reandroid.arsc.refactor.ResourceMergeOption$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResourceMergeOption.lambda$getKeepStyleEntries$3((ResourceEntry) obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$getKeepStyleEntries$3(ResourceEntry resourceEntry) {
        return TypeString.isTypeStyle(resourceEntry.getType()) && resourceEntry.getName().indexOf(46) > 0;
    }

    public void setKeepEntries(Predicate<? super ResourceEntry> predicate) {
        this.keepEntries = predicate;
    }

    public Predicate<? super ResourceName> getKeepResourceName() {
        return CollectionUtil.orFilter(getKeepResourceNameFilter(), getKeepResourceNameListFilter());
    }

    public void setKeepResourceNameFilter(Predicate<? super ResourceName> predicate) {
        this.keepResourceNameFilter = predicate;
    }

    private Predicate<? super ResourceName> getKeepResourceNameFilter() {
        return this.keepResourceNameFilter;
    }

    private Predicate<? super ResourceName> getKeepResourceNameListFilter() {
        final Set<ResourceName> set = this.keepResourceNameList;
        if (set.isEmpty()) {
            return null;
        }
        set.getClass();
        return new Predicate() { // from class: com.reandroid.arsc.refactor.ResourceMergeOption$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return set.contains((ResourceName) obj);
            }
        };
    }

    public void clearKeepResourceNameList() {
        this.keepResourceNameList.clear();
    }

    public void addKeepResourceName(ResourceName resourceName) {
        this.keepResourceNameList.add(resourceName);
    }

    public void readKeepResourceNameList(File file) throws IOException {
        StringLineStream stringLineStream = new StringLineStream(FileUtil.inputStream(file));
        while (stringLineStream.hasNext()) {
            addKeepResourceName(ResourceName.parse(stringLineStream.next().trim()));
        }
        IOException error = stringLineStream.getError();
        if (error != null) {
            throw error;
        }
        stringLineStream.close();
    }

    public ResourceEntry resolveUndeclared(PackageBlock packageBlock, ResourceEntry resourceEntry) {
        ResourceEntry last = resourceEntry.getLast();
        String name2 = last.getName();
        while (name2 == null) {
            last = last.previous();
            if (last == null) {
                break;
            }
            if (!last.isEmpty()) {
                name2 = last.getName();
            }
        }
        String type = resourceEntry.getType();
        if (name2 == null) {
            name2 = type;
        }
        int i = 0;
        String str = name2;
        while (packageBlock.getResource(type, str) != null) {
            str = name2 + i;
            i++;
        }
        Entry orCreate = packageBlock.getOrCreate(ResConfig.getDefault(), type, str);
        orCreate.ensureComplex(false);
        orCreate.getResValue().setTypeAndData(ValueType.REFERENCE, 0);
        return packageBlock.getResource(orCreate.getResourceId());
    }

    public void mergeFileWithName(ApkFile apkFile, ApkFile apkFile2, String str) {
        if (!apkFile.containsFile(str) || apkFile2.containsFile(str) || mergeResXmlDocument(apkFile, apkFile2, str)) {
            return;
        }
        mergeRawFile(apkFile, apkFile2, str);
    }

    private boolean mergeResXmlDocument(ApkFile apkFile, ApkFile apkFile2, String str) {
        if (!str.endsWith(".xml") || !ResXmlDocument.isResXmlBlock(apkFile.getInputSource(str))) {
            return false;
        }
        try {
            ResXmlDocument resXmlDocumentLoadResXmlDocument = apkFile.loadResXmlDocument(str);
            ResXmlDocument resXmlDocument = new ResXmlDocument();
            PackageBlock packageBlockPickOne = apkFile2.getTableBlock().pickOne(resXmlDocumentLoadResXmlDocument.getPackageBlock().getId());
            if (packageBlockPickOne == null) {
                packageBlockPickOne = apkFile2.getTableBlock().pickOne();
            }
            resXmlDocument.setPackageBlock(packageBlockPickOne);
            resXmlDocument.mergeWithName(this, resXmlDocumentLoadResXmlDocument);
            resXmlDocument.refreshFull();
            apkFile2.add(new ByteInputSource(resXmlDocument.getBytes(), str));
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    private void mergeRawFile(ApkFile apkFile, ApkFile apkFile2, String str) {
        InputSource inputSource = apkFile.getInputSource(str);
        BytesOutputStream bytesOutputStream = new BytesOutputStream();
        try {
            bytesOutputStream.write(inputSource.openStream());
            bytesOutputStream.close();
            ByteInputSource byteInputSource = new ByteInputSource(bytesOutputStream.toByteArray(), inputSource.getAlias());
            byteInputSource.setSort(inputSource.getSort());
            byteInputSource.setMethod(inputSource.getMethod());
            apkFile2.add(byteInputSource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
