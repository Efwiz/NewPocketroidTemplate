package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.lower.TailrecLowering;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: JvmTailrecLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmTailrecLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/TailrecLowering;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "useProperComputationOrderOfTailrecDefaultParameters", "", "getUseProperComputationOrderOfTailrecDefaultParameters", "()Z", "followFunctionReference", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "nullConst", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmTailrecLowering extends TailrecLowering {
    private final boolean useProperComputationOrderOfTailrecDefaultParameters;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmTailrecLowering(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.useProperComputationOrderOfTailrecDefaultParameters = CommonConfigurationKeysKt.getLanguageVersionSettings(context.getIr().getContext().getConfiguration()).supportsFeature(LanguageFeature.ProperComputationOrderOfTailrecDefaultParameters);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.TailrecLowering
    public boolean getUseProperComputationOrderOfTailrecDefaultParameters() {
        return this.useProperComputationOrderOfTailrecDefaultParameters;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.TailrecLowering
    public boolean followFunctionReference(IrFunctionReference reference) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        return Intrinsics.areEqual(reference.getOrigin(), JvmLoweredStatementOrigin.INLINE_LAMBDA.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.TailrecLowering
    public IrExpression nullConst(int startOffset, int endOffset, IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        BackendContext context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.JvmBackendContext");
        return JvmIrTypeUtilsKt.defaultValue(type, startOffset, endOffset, (JvmBackendContext) context);
    }
}
