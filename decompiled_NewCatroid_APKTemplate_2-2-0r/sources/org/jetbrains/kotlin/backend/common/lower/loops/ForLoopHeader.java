package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;

/* JADX INFO: compiled from: HeaderProcessor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H&J<\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00150\u00172\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopHeader;", "", "consumesLoopVariableComponents", "", "getConsumesLoopVariableComponents", "()Z", "loopInitStatements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "getLoopInitStatements", "()Ljava/util/List;", "buildLoop", "Lorg/jetbrains/kotlin/backend/common/lower/loops/LoopReplacement;", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "oldLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "newBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "initializeIteration", "loopVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "loopVariableComponents", "", "", "backendContext", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ForLoopHeader {
    LoopReplacement buildLoop(DeclarationIrBuilder builder, IrLoop oldLoop, IrExpression newBody);

    boolean getConsumesLoopVariableComponents();

    List<IrStatement> getLoopInitStatements();

    List<IrStatement> initializeIteration(IrVariable loopVariable, Map<Integer, ? extends IrVariable> loopVariableComponents, DeclarationIrBuilder builder, CommonBackendContext backendContext);
}
