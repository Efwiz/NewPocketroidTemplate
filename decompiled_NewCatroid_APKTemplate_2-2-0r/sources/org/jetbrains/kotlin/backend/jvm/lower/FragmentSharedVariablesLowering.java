package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: FragmentSharedVariablesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u00012\u00020\u0002:\u0001\u0015B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J$\u0010\u0011\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\rH\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FragmentSharedVariablesLowering;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "promoteParametersForCapturesToRefs", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "replaceUseOfPromotedParametersWithRefs", "promotedParameters", "visitFunctionNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Companion", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FragmentSharedVariablesLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    public static final String GENERATED_FUNCTION_NAME = "generated_for_debugger_fun";
    private final JvmBackendContext context;

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public FragmentSharedVariablesLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitFunctionNew(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!Intrinsics.areEqual(declaration.getName().asString(), GENERATED_FUNCTION_NAME)) {
            return super.visitFunctionNew(declaration);
        }
        replaceUseOfPromotedParametersWithRefs(declaration, promoteParametersForCapturesToRefs(declaration));
        return declaration;
    }

    private final Map<IrValueParameterSymbol, IrValueParameterSymbol> promoteParametersForCapturesToRefs(IrFunction declaration) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<IrValueParameter> valueParameters = declaration.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        for (IrValueParameter irValueParameter : valueParameters) {
            if (Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT.INSTANCE)) {
                IrValueParameter irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(irValueParameter, declaration, IrDeclarationOrigin.DEFINED.INSTANCE, 0, 0, 0, null, null, this.context.getSharedVariablesManager().getIrType(irValueParameter.getType()), null, null, false, false, false, 8060, null);
                linkedHashMap.put(irValueParameter.getSymbol(), irValueParameterCopyTo$default.getSymbol());
                irValueParameter = irValueParameterCopyTo$default;
            }
            arrayList.add(irValueParameter);
        }
        declaration.setValueParameters(arrayList);
        return linkedHashMap;
    }

    private final void replaceUseOfPromotedParametersWithRefs(IrFunction declaration, final Map<IrValueParameterSymbol, ? extends IrValueParameterSymbol> promotedParameters) {
        IrBody body = declaration.getBody();
        Intrinsics.checkNotNull(body);
        IrElementTransformerVoidKt.transformChildrenVoid(body, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FragmentSharedVariablesLowering.replaceUseOfPromotedParametersWithRefs.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrValueParameterSymbol irValueParameterSymbol = promotedParameters.get(expression.getSymbol());
                return irValueParameterSymbol == null ? expression : this.getContext().getSharedVariablesManager().getSharedValue(irValueParameterSymbol, expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitSetValue(IrSetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrValueParameterSymbol irValueParameterSymbol = promotedParameters.get(expression.getSymbol());
                return irValueParameterSymbol == null ? expression : this.getContext().getSharedVariablesManager().setSharedValue(irValueParameterSymbol, expression);
            }
        });
    }
}
