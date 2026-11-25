package org.eclipse.jgit.merge;

/* JADX INFO: loaded from: classes13.dex */
public enum ContentMergeStrategy {
    CONFLICT,
    OURS,
    THEIRS;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static ContentMergeStrategy[] valuesCustom() {
        ContentMergeStrategy[] contentMergeStrategyArrValuesCustom = values();
        int length = contentMergeStrategyArrValuesCustom.length;
        ContentMergeStrategy[] contentMergeStrategyArr = new ContentMergeStrategy[length];
        System.arraycopy(contentMergeStrategyArrValuesCustom, 0, contentMergeStrategyArr, 0, length);
        return contentMergeStrategyArr;
    }
}
