package org.eclipse.jdt.internal.compiler.env;

/* JADX INFO: loaded from: classes14.dex */
public interface IBinaryMethod extends IGenericMethod {
    int getAnnotatedParametersCount();

    IBinaryAnnotation[] getAnnotations();

    Object getDefaultValue();

    char[][] getExceptionTypeNames();

    char[] getGenericSignature();

    char[] getMethodDescriptor();

    IBinaryAnnotation[] getParameterAnnotations(int i, char[] cArr);

    char[] getSelector();

    long getTagBits();

    IBinaryTypeAnnotation[] getTypeAnnotations();

    boolean isClinit();
}
