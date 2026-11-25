package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.C3405y10;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.l3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC3758l3 {
    KEEP,
    KEEP_CLASS_MEMBERS,
    KEEP_CLASSES_WITH_MEMBERS,
    CONDITIONAL;

    @Override // java.lang.Enum
    public String toString() {
        int i = AbstractC3748k3.a[ordinal()];
        if (i == 1) {
            return "keep";
        }
        if (i == 2) {
            return "keepclassmembers";
        }
        if (i == 3) {
            return "keepclasseswithmembers";
        }
        if (i == 4) {
            return PsiKeyword.IF;
        }
        throw new C3405y10("Unknown ProguardKeepRuleType.");
    }
}
