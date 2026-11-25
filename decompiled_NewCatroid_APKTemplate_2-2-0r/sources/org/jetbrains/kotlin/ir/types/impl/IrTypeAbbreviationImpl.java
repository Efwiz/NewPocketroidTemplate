package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;

/* JADX INFO: compiled from: IrTypeAbbreviationImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007¢\u0006\u0002\u0010\u000bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrTypeAbbreviationImpl;", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "typeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "hasQuestionMark", "", "arguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "annotations", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "(Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;ZLjava/util/List;Ljava/util/List;)V", "getAnnotations", "()Ljava/util/List;", "getArguments", "getHasQuestionMark", "()Z", "getTypeAlias", "()Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeAbbreviationImpl implements IrTypeAbbreviation {
    private final List<IrConstructorCall> annotations;
    private final List<IrTypeArgument> arguments;
    private final boolean hasQuestionMark;
    private final IrTypeAliasSymbol typeAlias;

    /* JADX WARN: Multi-variable type inference failed */
    public IrTypeAbbreviationImpl(IrTypeAliasSymbol typeAlias, boolean z, List<? extends IrTypeArgument> arguments, List<? extends IrConstructorCall> annotations) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.typeAlias = typeAlias;
        this.hasQuestionMark = z;
        this.arguments = arguments;
        this.annotations = annotations;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeAbbreviation
    public IrTypeAliasSymbol getTypeAlias() {
        return this.typeAlias;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeAbbreviation
    public boolean getHasQuestionMark() {
        return this.hasQuestionMark;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeAbbreviation
    public List<IrTypeArgument> getArguments() {
        return this.arguments;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }
}
