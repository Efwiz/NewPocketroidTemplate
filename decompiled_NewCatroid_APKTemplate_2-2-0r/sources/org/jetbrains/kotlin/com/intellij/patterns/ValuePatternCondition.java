package org.jetbrains.kotlin.com.intellij.patterns;

import java.util.Collection;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ValuePatternCondition<T> extends PatternCondition<T> {
    public abstract Collection<T> getValues();

    protected ValuePatternCondition(String str) {
        super(str);
    }
}
