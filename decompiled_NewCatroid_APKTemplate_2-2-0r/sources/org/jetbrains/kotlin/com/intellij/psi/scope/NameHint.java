package org.jetbrains.kotlin.com.intellij.psi.scope;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;

/* JADX INFO: loaded from: classes3.dex */
public interface NameHint {
    public static final Key<NameHint> KEY = Key.create("NameHint");

    String getName(ResolveState resolveState);
}
