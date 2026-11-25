package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CommonReferenceProviderTypes {
    public static final ReferenceProviderType PROPERTIES_FILE_KEY_PROVIDER = new ReferenceProviderType("Properties File Key Provider");

    public abstract PsiReferenceProvider getClassReferenceProvider();

    public static CommonReferenceProviderTypes getInstance() {
        return (CommonReferenceProviderTypes) ApplicationManager.getApplication().getService(CommonReferenceProviderTypes.class);
    }
}
