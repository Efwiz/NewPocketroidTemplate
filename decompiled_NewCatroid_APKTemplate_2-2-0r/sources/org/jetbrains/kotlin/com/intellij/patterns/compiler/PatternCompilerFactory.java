package org.jetbrains.kotlin.com.intellij.patterns.compiler;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PatternCompilerFactory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/patterns/compiler/PatternCompilerFactory", "getPatternCompiler"));
    }

    public abstract Class<?>[] getPatternClasses(String str);

    public abstract <T> PatternCompiler<T> getPatternCompiler(Class[] clsArr);

    public static PatternCompilerFactory getFactory() {
        return (PatternCompilerFactory) ApplicationManager.getApplication().getService(PatternCompilerFactory.class);
    }

    public <T> PatternCompiler<T> getPatternCompiler(String str) {
        PatternCompiler<T> patternCompiler = getPatternCompiler(getPatternClasses(str));
        if (patternCompiler == null) {
            $$$reportNull$$$0(0);
        }
        return patternCompiler;
    }
}
