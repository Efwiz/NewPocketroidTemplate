package org.jetbrains.kotlin.com.intellij.psi.meta;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MetaDataRegistrar {
    public abstract <T extends PsiMetaData> void registerMetaData(ElementFilter elementFilter, Class<T> cls);

    public static MetaDataRegistrar getInstance() {
        return (MetaDataRegistrar) ApplicationManager.getApplication().getService(MetaDataRegistrar.class);
    }
}
