package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: PropertiesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LocalDelegatedPropertiesLowering;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "()V", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalDelegatedPropertiesLowering extends IrElementTransformerVoid implements BodyLoweringPass {
    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        irBody.accept(this, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
        IrExpression initializer = declaration.getDelegate().getInitializer();
        Intrinsics.checkNotNull(initializer);
        declaration.getDelegate().setInitializer(new IrBlockImpl(initializer.getStartOffset(), initializer.getEndOffset(), initializer.getType(), null, CollectionsKt.listOfNotNull((Object[]) new IrElement[]{declaration.getGetter(), declaration.getSetter(), initializer})));
        return declaration.getDelegate();
    }
}
