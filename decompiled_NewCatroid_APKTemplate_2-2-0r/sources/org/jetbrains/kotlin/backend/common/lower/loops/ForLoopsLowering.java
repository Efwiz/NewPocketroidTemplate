package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: ForLoopsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopsLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "loopBodyTransformer", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopBodyTransformer;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopBodyTransformer;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForLoopsLowering implements BodyLoweringPass {
    private final CommonBackendContext context;
    private final ForLoopBodyTransformer loopBodyTransformer;

    public ForLoopsLowering(CommonBackendContext context, ForLoopBodyTransformer forLoopBodyTransformer) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.loopBodyTransformer = forLoopBodyTransformer;
    }

    public /* synthetic */ ForLoopsLowering(CommonBackendContext commonBackendContext, ForLoopBodyTransformer forLoopBodyTransformer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? null : forLoopBodyTransformer);
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        IrBody irBody2 = irBody;
        IrElementTransformerVoidKt.transformChildrenVoid(irBody2, new RangeLoopTransformer(this.context, container, linkedHashMap, this.loopBodyTransformer));
        IrElementTransformerVoidKt.transformChildrenVoid(irBody2, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.loops.ForLoopsLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitBreakContinue(IrBreakContinue jump) {
                Intrinsics.checkNotNullParameter(jump, "jump");
                IrLoop irLoop = linkedHashMap.get(jump.getLoop());
                if (irLoop != null) {
                    jump.setLoop(irLoop);
                }
                return jump;
            }
        });
    }
}
