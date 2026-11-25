package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;

/* JADX INFO: loaded from: classes3.dex */
public final class StubElementTypeHolderEP {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    @Attribute("externalIdPrefix")
    public String externalIdPrefix;

    @Attribute("class")
    public String holderClass;
    private static final Logger LOG = Logger.getInstance((Class<?>) StubElementTypeHolderEP.class);
    public static final ExtensionPointName<StubElementTypeHolderEP> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.stubElementTypeHolder");

    public String toString() {
        return this.holderClass;
    }
}
