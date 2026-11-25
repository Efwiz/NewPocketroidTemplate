package com.reandroid.dex.model;

import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.sections.DexLayout;
import com.reandroid.dex.sections.MergeOptions;
import com.reandroid.dex.sections.SectionList;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class DexMergeOptions implements MergeOptions {
    private int mergeStartDexFile;
    private final Set<TypeKey> mergedSet;
    private final boolean relocate;

    @Override // com.reandroid.dex.sections.MergeOptions
    public void onMergeError(DexLayout dexLayout, ClassId classId, String str) {
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public void onMergeError(DexLayout dexLayout, SectionList sectionList, String str) {
    }

    public DexMergeOptions(boolean z) {
        this.relocate = z;
        this.mergedSet = new HashSet();
    }

    public DexMergeOptions() {
        this(true);
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public boolean skipMerging(ClassId classId, TypeKey typeKey) {
        return this.mergedSet.contains(typeKey);
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public void onDuplicate(ClassId classId) {
        if (this.relocate) {
            classId.removeSelf();
        }
        this.mergedSet.add(classId.getKey());
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public void onDexFull(DexLayout dexLayout, ClassId classId) {
        DexFile dexFileFindDexFile;
        DexDirectory dexDirectory;
        DexFile dexFileFindDexFile2 = DexFile.findDexFile(classId);
        if (dexFileFindDexFile2 == null || (dexFileFindDexFile = DexFile.findDexFile(dexLayout)) == null || (dexDirectory = dexFileFindDexFile.getDexDirectory()) == null || dexDirectory == dexFileFindDexFile2.getDexDirectory()) {
            return;
        }
        onCreateNext(dexLayout);
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public void onMergeSuccess(ClassId classId, TypeKey typeKey) {
        if (this.relocate) {
            return;
        }
        this.mergedSet.add(typeKey);
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public boolean relocateClass() {
        return this.relocate;
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public int getMergeStartDexFile() {
        return this.mergeStartDexFile;
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public void setMergeStartDexFile(int i) {
        this.mergeStartDexFile = i;
    }

    @Override // com.reandroid.dex.sections.MergeOptions
    public DexLayout onCreateNext(DexLayout dexLayout) {
        DexDirectory dexDirectory;
        DexFile dexFileFindDexFile = DexFile.findDexFile(dexLayout);
        if (dexFileFindDexFile == null || (dexDirectory = dexFileFindDexFile.getDexDirectory()) == null || dexDirectory.getLast() != dexFileFindDexFile) {
            return null;
        }
        DexFile dexFileCreateDefault = dexDirectory.createDefault();
        setMergeStartDexFile(dexFileCreateDefault.getIndex());
        return dexFileCreateDefault.getDexLayout();
    }
}
