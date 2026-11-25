package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: loaded from: classes3.dex */
public class ChangedRangesInfo {
    public final List<TextRange> allChangedRanges;
    public final List<TextRange> insertedRanges;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "allChangedRanges", "org/jetbrains/kotlin/com/intellij/psi/codeStyle/ChangedRangesInfo", "<init>"));
    }

    public ChangedRangesInfo(List<TextRange> list, List<TextRange> list2) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        this.insertedRanges = list2;
        this.allChangedRanges = list;
    }
}
