package com.github.difflib.unifieddiff;

import com.github.difflib.patch.PatchFailedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public final class UnifiedDiff {
    private final List<UnifiedDiffFile> files = new ArrayList();
    private String header;
    private String tail;

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String str) {
        this.header = str;
    }

    void addFile(UnifiedDiffFile unifiedDiffFile) {
        this.files.add(unifiedDiffFile);
    }

    public List<UnifiedDiffFile> getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    void setTailTxt(String str) {
        this.tail = str;
    }

    public String getTail() {
        return this.tail;
    }

    public List<String> applyPatchTo(final Predicate<String> predicate, List<String> list) throws PatchFailedException {
        UnifiedDiffFile unifiedDiffFileOrElse = this.files.stream().filter(new Predicate() { // from class: com.github.difflib.unifieddiff.UnifiedDiff$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return predicate.test(((UnifiedDiffFile) obj).getFromFile());
            }
        }).findFirst().orElse(null);
        return unifiedDiffFileOrElse != null ? unifiedDiffFileOrElse.getPatch().applyTo(list) : list;
    }

    public static UnifiedDiff from(String str, String str2, UnifiedDiffFile... unifiedDiffFileArr) {
        UnifiedDiff unifiedDiff = new UnifiedDiff();
        unifiedDiff.setHeader(str);
        unifiedDiff.setTailTxt(str2);
        for (UnifiedDiffFile unifiedDiffFile : unifiedDiffFileArr) {
            unifiedDiff.addFile(unifiedDiffFile);
        }
        return unifiedDiff;
    }
}
