package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTypeParameterStub extends NamedStub<PsiTypeParameter> {
    List<PsiAnnotationStub> getAnnotations();
}
