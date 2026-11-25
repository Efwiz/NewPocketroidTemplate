package com.reandroid.dex.sections;

import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.key.TypeKey;

/* JADX INFO: loaded from: classes11.dex */
public interface MergeOptions {
    public static final MergeOptions DEFAULT = new MergeOptions() { // from class: com.reandroid.dex.sections.MergeOptions.1
        @Override // com.reandroid.dex.sections.MergeOptions
        public DexLayout onCreateNext(DexLayout dexLayout) {
            return null;
        }

        @Override // com.reandroid.dex.sections.MergeOptions
        public void onDexFull(DexLayout dexLayout, ClassId classId) {
        }

        @Override // com.reandroid.dex.sections.MergeOptions
        public void onMergeError(DexLayout dexLayout, ClassId classId, String str) {
        }

        @Override // com.reandroid.dex.sections.MergeOptions
        public void onMergeError(DexLayout dexLayout, SectionList sectionList, String str) {
        }

        @Override // com.reandroid.dex.sections.MergeOptions
        public void onMergeSuccess(ClassId classId, TypeKey typeKey) {
        }

        @Override // com.reandroid.dex.sections.MergeOptions
        public boolean relocateClass() {
            return true;
        }

        @Override // com.reandroid.dex.sections.MergeOptions
        public boolean skipMerging(ClassId classId, TypeKey typeKey) {
            return false;
        }

        @Override // com.reandroid.dex.sections.MergeOptions
        public void onDuplicate(ClassId classId) {
            classId.removeSelf();
        }
    };

    default int getMergeStartDexFile() {
        return 0;
    }

    DexLayout onCreateNext(DexLayout dexLayout);

    void onDexFull(DexLayout dexLayout, ClassId classId);

    void onDuplicate(ClassId classId);

    void onMergeError(DexLayout dexLayout, ClassId classId, String str);

    void onMergeError(DexLayout dexLayout, SectionList sectionList, String str);

    void onMergeSuccess(ClassId classId, TypeKey typeKey);

    boolean relocateClass();

    default void setMergeStartDexFile(int i) {
    }

    boolean skipMerging(ClassId classId, TypeKey typeKey);

    default boolean isEmptyDexFile(DexLayout dexLayout) {
        if (dexLayout != null && !dexLayout.isEmpty()) {
            for (ClassId classId : dexLayout.get(SectionType.CLASS_ID)) {
                if (!skipMerging(classId, classId.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }
}
