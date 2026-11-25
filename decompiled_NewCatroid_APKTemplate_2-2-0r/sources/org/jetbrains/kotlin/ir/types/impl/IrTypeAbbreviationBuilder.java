package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;

/* JADX INFO: compiled from: IrTypeAbbreviationImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0007\"\u0004\b\r\u0010\tR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrTypeAbbreviationBuilder;", "", "()V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "arguments", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getArguments", "setArguments", "hasQuestionMark", "", "getHasQuestionMark", "()Z", "setHasQuestionMark", "(Z)V", "typeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getTypeAlias", "()Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "setTypeAlias", "(Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeAbbreviationBuilder {
    private boolean hasQuestionMark;
    private IrTypeAliasSymbol typeAlias;
    private List<? extends IrTypeArgument> arguments = CollectionsKt.emptyList();
    private List<? extends IrConstructorCall> annotations = CollectionsKt.emptyList();

    public final IrTypeAliasSymbol getTypeAlias() {
        return this.typeAlias;
    }

    public final void setTypeAlias(IrTypeAliasSymbol irTypeAliasSymbol) {
        this.typeAlias = irTypeAliasSymbol;
    }

    public final boolean getHasQuestionMark() {
        return this.hasQuestionMark;
    }

    public final void setHasQuestionMark(boolean z) {
        this.hasQuestionMark = z;
    }

    public final List<IrTypeArgument> getArguments() {
        return this.arguments;
    }

    public final void setArguments(List<? extends IrTypeArgument> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.arguments = list;
    }

    public final List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }

    public final void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations = list;
    }
}
