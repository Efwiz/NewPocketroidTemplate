package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.common.HasSymbol;

/* JADX INFO: loaded from: classes5.dex */
public interface HasName extends HasSymbol {
    JsName getName();

    void setName(JsName jsName);
}
