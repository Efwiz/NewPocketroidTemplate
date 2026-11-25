package org.eclipse.jdt.internal.compiler.env;

/* JADX INFO: loaded from: classes14.dex */
public interface ISourceField extends IGenericField {
    int getDeclarationSourceEnd();

    int getDeclarationSourceStart();

    char[] getInitializationSource();

    int getNameSourceEnd();

    int getNameSourceStart();

    char[] getTypeName();
}
