package org.jetbrains.kotlin.ir.builders;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;

/* JADX INFO: compiled from: IrBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ4\u0010\u000e\u001a\u0002H\u000f\"\b\b\u0000\u0010\u000f*\u00020\u00102\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u0002H\u000f0\u0012¢\u0006\u0002\b\u0013H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrSingleStatementBuilder;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/builders/Scope;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "statementBuilder", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/IrElement;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSingleStatementBuilder extends IrBuilderWithScope {
    private final IrStatementOrigin origin;

    public /* synthetic */ IrSingleStatementBuilder(IrGeneratorContext irGeneratorContext, Scope scope, int i, int i2, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(irGeneratorContext, scope, i, i2, (i3 & 16) != 0 ? null : irStatementOrigin);
    }

    public final IrStatementOrigin getOrigin() {
        return this.origin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrSingleStatementBuilder(IrGeneratorContext context, Scope scope, int i, int i2, IrStatementOrigin irStatementOrigin) {
        super(context, scope, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.origin = irStatementOrigin;
    }

    public final <T extends IrElement> T build(Function1<? super IrSingleStatementBuilder, ? extends T> statementBuilder) {
        Intrinsics.checkNotNullParameter(statementBuilder, "statementBuilder");
        return statementBuilder.invoke(this);
    }
}
