package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.CustomLoadingExtensionPointBean;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceContributor;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiReferenceContributorEP extends CustomLoadingExtensionPointBean<PsiReferenceContributor> implements KeyedLazyInstance<PsiReferenceContributor> {

    @Attribute("implementation")
    public String implementationClass;

    @Attribute("language")
    public String language = Language.ANY.getID();

    @Override // org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance
    public String getKey() {
        return this.language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.serviceContainer.BaseKeyedLazyInstance, org.jetbrains.kotlin.com.intellij.serviceContainer.LazyExtensionInstance
    protected String getImplementationClassName() {
        return this.implementationClass;
    }
}
