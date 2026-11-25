package org.jetbrains.kotlin.com.intellij.patterns;

import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public class CaseInsensitiveValuePatternCondition extends PatternCondition<String> {
    private final String[] myValues;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "str", "org/jetbrains/kotlin/com/intellij/patterns/CaseInsensitiveValuePatternCondition", "accepts"));
    }

    public String[] getValues() {
        return this.myValues;
    }

    @Override // org.jetbrains.kotlin.com.intellij.patterns.PatternCondition
    public boolean accepts(String str, ProcessingContext processingContext) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        for (String str2 : this.myValues) {
            if (str.equalsIgnoreCase(str2)) {
                return true;
            }
        }
        return false;
    }
}
