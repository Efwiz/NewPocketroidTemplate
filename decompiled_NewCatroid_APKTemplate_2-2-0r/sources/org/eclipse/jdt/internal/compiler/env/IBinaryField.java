package org.eclipse.jdt.internal.compiler.env;

import org.eclipse.jdt.internal.compiler.impl.Constant;

/* JADX INFO: loaded from: classes14.dex */
public interface IBinaryField extends IGenericField {
    IBinaryAnnotation[] getAnnotations();

    Constant getConstant();

    char[] getGenericSignature();

    char[] getName();

    long getTagBits();

    IBinaryTypeAnnotation[] getTypeAnnotations();

    char[] getTypeName();
}
