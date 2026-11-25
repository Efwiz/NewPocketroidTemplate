package org.eclipse.jdt.internal.compiler.batch;

import java.io.File;
import java.io.IOException;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class CompilationUnit implements ICompilationUnit {
    public char[] contents;
    public String destinationPath;
    String encoding;
    public char[] fileName;
    private boolean ignoreOptionalProblems;
    public char[] mainTypeName;

    @Override // org.eclipse.jdt.internal.compiler.env.ICompilationUnit
    public char[][] getPackageName() {
        return null;
    }

    public CompilationUnit(char[] cArr, String str, String str2) {
        this(cArr, str, str2, null);
    }

    public CompilationUnit(char[] cArr, String str, String str2, String str3) {
        this(cArr, str, str2, str3, false);
    }

    public CompilationUnit(char[] cArr, String str, String str2, String str3, boolean z) {
        this.contents = cArr;
        char[] charArray = str.toCharArray();
        char c = File.separatorChar;
        if (c == '/') {
            if (CharOperation.indexOf('\\', charArray) != -1) {
                CharOperation.replace(charArray, '\\', '/');
            }
        } else if (c == '\\' && CharOperation.indexOf('/', charArray) != -1) {
            CharOperation.replace(charArray, '/', '\\');
        }
        this.fileName = charArray;
        int iLastIndexOf = CharOperation.lastIndexOf(File.separatorChar, charArray) + 1;
        int iLastIndexOf2 = CharOperation.lastIndexOf('.', charArray);
        this.mainTypeName = CharOperation.subarray(charArray, iLastIndexOf, iLastIndexOf2 == -1 ? charArray.length : iLastIndexOf2);
        this.encoding = str2;
        this.destinationPath = str3;
        this.ignoreOptionalProblems = z;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ICompilationUnit
    public char[] getContents() {
        char[] cArr = this.contents;
        if (cArr != null) {
            return cArr;
        }
        try {
            return Util.getFileCharContent(new File(new String(this.fileName)), this.encoding);
        } catch (IOException e) {
            this.contents = CharOperation.NO_CHAR;
            throw new AbortCompilationUnit(null, e, this.encoding);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IDependent
    public char[] getFileName() {
        return this.fileName;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ICompilationUnit
    public char[] getMainTypeName() {
        return this.mainTypeName;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ICompilationUnit
    public boolean ignoreOptionalProblems() {
        return this.ignoreOptionalProblems;
    }

    public String toString() {
        return "CompilationUnit[" + new String(this.fileName) + "]";
    }
}
