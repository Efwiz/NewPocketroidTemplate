package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference;

/* JADX INFO: loaded from: classes14.dex */
public interface IQualifiedTypeResolutionListener {
    void recordResolution(QualifiedTypeReference qualifiedTypeReference, TypeBinding typeBinding);
}
