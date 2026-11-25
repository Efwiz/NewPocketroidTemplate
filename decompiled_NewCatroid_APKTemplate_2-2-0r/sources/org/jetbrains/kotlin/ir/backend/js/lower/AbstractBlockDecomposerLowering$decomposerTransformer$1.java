package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: BlockDecomposerLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class AbstractBlockDecomposerLowering$decomposerTransformer$1 extends FunctionReference implements Function0<IrExpression> {
    AbstractBlockDecomposerLowering$decomposerTransformer$1(Object obj) {
        super(0, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "unreachableExpression()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "unreachableExpression";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(AbstractBlockDecomposerLowering.class);
    }

    @Override // kotlin.jvm.functions.Function0
    public final IrExpression invoke() {
        return ((AbstractBlockDecomposerLowering) this.receiver).unreachableExpression();
    }
}
