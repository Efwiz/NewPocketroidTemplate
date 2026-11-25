package org.jetbrains.kotlin.codegen;

import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;

/* JADX INFO: loaded from: classes3.dex */
public interface ClassBuilderFactory {
    /* JADX INFO: renamed from: asBytes */
    byte[] mo6372asBytes(ClassBuilder classBuilder);

    String asText(ClassBuilder classBuilder);

    void close();

    ClassBuilderMode getClassBuilderMode();

    ClassBuilder newClassBuilder(JvmDeclarationOrigin jvmDeclarationOrigin);
}
