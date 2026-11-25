package org.jetbrains.kotlin.com.intellij.patterns.compiler;

import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;

/* JADX INFO: loaded from: classes3.dex */
public interface PatternCompiler<T> {
    ElementPattern<T> compileElementPattern(String str);
}
