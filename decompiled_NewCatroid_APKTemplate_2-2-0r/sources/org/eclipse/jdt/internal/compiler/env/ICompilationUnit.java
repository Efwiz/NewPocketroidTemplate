package org.eclipse.jdt.internal.compiler.env;

/* JADX INFO: loaded from: classes14.dex */
public interface ICompilationUnit extends IDependent {
    char[] getContents();

    char[] getMainTypeName();

    char[][] getPackageName();

    boolean ignoreOptionalProblems();
}
