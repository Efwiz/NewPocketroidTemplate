package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;

/* JADX INFO: compiled from: IrPossiblyExternalDeclaration.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrPossiblyExternalDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "isExternal", "", "()Z", "setExternal", "(Z)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrPossiblyExternalDeclaration extends IrDeclarationWithName {
    boolean isExternal();

    void setExternal(boolean z);
}
