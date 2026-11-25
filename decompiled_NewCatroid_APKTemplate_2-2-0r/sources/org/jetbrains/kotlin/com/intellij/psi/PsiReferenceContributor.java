package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiReferenceContributor implements Disposable {
    public static final ExtensionPointName<KeyedLazyInstance<PsiReferenceContributor>> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.psi.referenceContributor");

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
    }

    public abstract void registerReferenceProviders(PsiReferenceRegistrar psiReferenceRegistrar);
}
