package org.jetbrains.kotlin.com.intellij.util;

import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.serviceContainer.BaseKeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;

/* JADX INFO: loaded from: classes3.dex */
public class KeyedLazyInstanceEP<T> extends BaseKeyedLazyInstance<T> implements KeyedLazyInstance<T> {

    @Attribute("implementationClass")
    public String implementationClass;

    @Attribute(ASTExpr.DEFAULT_MAP_KEY_NAME)
    public String key;

    @Override // org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance
    public String getKey() {
        return this.key;
    }

    @Override // org.jetbrains.kotlin.com.intellij.serviceContainer.BaseKeyedLazyInstance, org.jetbrains.kotlin.com.intellij.serviceContainer.LazyExtensionInstance
    protected String getImplementationClassName() {
        return this.implementationClass;
    }
}
