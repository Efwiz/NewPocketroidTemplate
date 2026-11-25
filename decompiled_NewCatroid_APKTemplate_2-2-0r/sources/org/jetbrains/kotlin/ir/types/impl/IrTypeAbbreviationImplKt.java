package org.jetbrains.kotlin.ir.types.impl;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;

/* JADX INFO: compiled from: IrTypeAbbreviationImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0002*\u00020\u0001¨\u0006\u0004"}, d2 = {JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/ir/types/impl/IrTypeAbbreviationImpl;", "Lorg/jetbrains/kotlin/ir/types/impl/IrTypeAbbreviationBuilder;", "toBuilder", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypeAbbreviationImplKt {
    public static final IrTypeAbbreviationBuilder toBuilder(IrTypeAbbreviationImpl irTypeAbbreviationImpl) {
        Intrinsics.checkNotNullParameter(irTypeAbbreviationImpl, "<this>");
        IrTypeAbbreviationBuilder irTypeAbbreviationBuilder = new IrTypeAbbreviationBuilder();
        irTypeAbbreviationBuilder.setTypeAlias(irTypeAbbreviationImpl.getTypeAlias());
        irTypeAbbreviationBuilder.setHasQuestionMark(irTypeAbbreviationImpl.getHasQuestionMark());
        irTypeAbbreviationBuilder.setArguments(irTypeAbbreviationImpl.getArguments());
        irTypeAbbreviationBuilder.setAnnotations(irTypeAbbreviationImpl.getAnnotations());
        return irTypeAbbreviationBuilder;
    }

    public static final IrTypeAbbreviationImpl build(IrTypeAbbreviationBuilder irTypeAbbreviationBuilder) {
        Intrinsics.checkNotNullParameter(irTypeAbbreviationBuilder, "<this>");
        IrTypeAliasSymbol typeAlias = irTypeAbbreviationBuilder.getTypeAlias();
        if (typeAlias != null) {
            return new IrTypeAbbreviationImpl(typeAlias, irTypeAbbreviationBuilder.getHasQuestionMark(), irTypeAbbreviationBuilder.getArguments(), irTypeAbbreviationBuilder.getAnnotations());
        }
        throw new AssertionError("typeAlias not provided");
    }
}
