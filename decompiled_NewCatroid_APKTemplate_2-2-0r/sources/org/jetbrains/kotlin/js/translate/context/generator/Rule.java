package org.jetbrains.kotlin.js.translate.context.generator;

import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: loaded from: classes5.dex */
public interface Rule<V> {
    V apply(DeclarationDescriptor declarationDescriptor);
}
