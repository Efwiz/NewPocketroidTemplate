package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: KotlinNothingValueExceptionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0014B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/KotlinNothingValueExceptionLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "backendContext", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", SchemaSymbols.ATTVAL_SKIP, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lkotlin/jvm/functions/Function1;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getSkip", "()Lkotlin/jvm/functions/Function1;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Transformer", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinNothingValueExceptionLowering implements BodyLoweringPass {
    private final CommonBackendContext backendContext;
    private final Function1<IrDeclaration, Boolean> skip;

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinNothingValueExceptionLowering(CommonBackendContext backendContext, Function1<? super IrDeclaration, Boolean> skip) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(skip, "skip");
        this.backendContext = backendContext;
        this.skip = skip;
    }

    public /* synthetic */ KotlinNothingValueExceptionLowering(CommonBackendContext commonBackendContext, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.KotlinNothingValueExceptionLowering.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return false;
            }
        } : anonymousClass1);
    }

    public final CommonBackendContext getBackendContext() {
        return this.backendContext;
    }

    public final Function1<IrDeclaration, Boolean> getSkip() {
        return this.skip;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix(this, irFile, true);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (this.skip.invoke(container).booleanValue()) {
            return;
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new Transformer(this, container.getSymbol()));
    }

    /* JADX INFO: compiled from: KotlinNothingValueExceptionLowering.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/KotlinNothingValueExceptionLowering$Transformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "parent", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/backend/common/lower/KotlinNothingValueExceptionLowering;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "getParent", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "visitBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Transformer extends IrElementTransformerVoid {
        private final IrSymbol parent;
        final /* synthetic */ KotlinNothingValueExceptionLowering this$0;

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrBody visitBody(IrBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            return body;
        }

        public Transformer(KotlinNothingValueExceptionLowering kotlinNothingValueExceptionLowering, IrSymbol parent) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            this.this$0 = kotlinNothingValueExceptionLowering;
            this.parent = parent;
        }

        public final IrSymbol getParent() {
            return this.parent;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitCall(IrCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (IrTypePredicatesKt.isNothing(expression.getType())) {
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.this$0.getBackendContext(), this.parent, expression.getStartOffset(), expression.getEndOffset());
                KotlinNothingValueExceptionLowering kotlinNothingValueExceptionLowering = this.this$0;
                DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                IrType nothingType = declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getNothingType();
                IrCall irCall = expression;
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irCall.getStartOffset(), irCall.getEndOffset(), null, nothingType, false, 64, null);
                irBlockBuilder.unaryPlus(super.visitCall(expression));
                irBlockBuilder.unaryPlus(ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder, kotlinNothingValueExceptionLowering.getBackendContext().getIr().getSymbols().getThrowKotlinNothingValueException()));
                return irBlockBuilder.getIrBlockBody();
            }
            return super.visitCall(expression);
        }
    }
}
