package org.jetbrains.kotlin.com.intellij.psi.meta;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;

/* JADX INFO: loaded from: classes3.dex */
public interface MetaDataContributor {
    public static final ExtensionPointName<MetaDataContributor> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.metaDataContributor");

    void contributeMetaData(MetaDataRegistrar metaDataRegistrar);
}
