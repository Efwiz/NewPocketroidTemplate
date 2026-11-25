package org.eclipse.jdt.internal.compiler.env;

/* JADX INFO: loaded from: classes14.dex */
public interface ISourceMethod extends IGenericMethod {
    int getDeclarationSourceEnd();

    int getDeclarationSourceStart();

    char[][] getExceptionTypeNames();

    int getNameSourceEnd();

    int getNameSourceStart();

    char[] getReturnTypeName();

    char[][][] getTypeParameterBounds();

    char[][] getTypeParameterNames();
}
