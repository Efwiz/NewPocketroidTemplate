package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: ForLoopsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J<\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n0\u000eH&¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopBodyTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "()V", "transform", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "loopBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "loopVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "forLoopHeader", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopHeader;", "loopComponents", "", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ForLoopBodyTransformer extends IrElementTransformerVoid {
    public abstract void transform(CommonBackendContext context, IrExpression loopBody, IrVariable loopVariable, ForLoopHeader forLoopHeader, Map<Integer, ? extends IrVariable> loopComponents);
}
