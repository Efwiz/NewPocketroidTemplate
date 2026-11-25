package org.jetbrains.kotlin.backend.common.lower;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterConfiguration;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformerKt;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: ConstEvaluationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ConstEvaluationLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "suppressErrors", "", "configuration", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;ZLorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstEvaluationLowering implements FileLoweringPass {
    private final CommonBackendContext context;
    private final EvaluatedConstTracker evaluatedConstTracker;
    private final InlineConstTracker inlineConstTracker;
    private final IrInterpreter interpreter;
    private final EvaluationMode mode;
    private final boolean suppressErrors;

    public ConstEvaluationLowering(CommonBackendContext context, boolean z, IrInterpreterConfiguration configuration) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.context = context;
        this.suppressErrors = z;
        this.interpreter = new IrInterpreter(new IrInterpreterEnvironment(context.getIrBuiltIns(), configuration), (Map<IdSignature, ? extends IrBody>) MapsKt.emptyMap());
        this.evaluatedConstTracker = (EvaluatedConstTracker) context.getConfiguration().get(CommonConfigurationKeys.EVALUATED_CONST_TRACKER);
        this.inlineConstTracker = (InlineConstTracker) context.getConfiguration().get(CommonConfigurationKeys.INLINE_CONST_TRACKER);
        this.mode = EvaluationMode.ONLY_INTRINSIC_CONST;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    public /* synthetic */ ConstEvaluationLowering(CommonBackendContext commonBackendContext, boolean z, IrInterpreterConfiguration irInterpreterConfiguration, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? commonBackendContext.getConfiguration().getBoolean(CommonConfigurationKeys.IGNORE_CONST_OPTIMIZATION_ERRORS) : z, (i & 4) != 0 ? new IrInterpreterConfiguration(null, 0, 0, false, true, false, false, 111, null) : irInterpreterConfiguration);
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        if (!Intrinsics.areEqual(this.context.getConfiguration().get(CommonConfigurationKeys.USE_FIR), (Object) true)) {
            irFile = IrConstTransformerKt.preprocessForConstTransformer(irFile, this.interpreter, this.mode);
        }
        IrConstTransformerKt.runConstOptimizations(irFile, this.interpreter, this.mode, this.evaluatedConstTracker, this.inlineConstTracker, this.suppressErrors);
    }
}
