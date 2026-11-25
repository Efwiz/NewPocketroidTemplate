package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: FragmentLocalFunctionPatchLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R&\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FragmentLocalFunctionPatchLowering;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "localDeclarationsData", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext$LocalFunctionData;", "getLocalDeclarationsData", "()Ljava/util/Map;", "setLocalDeclarationsData", "(Ljava/util/Map;)V", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FragmentLocalFunctionPatchLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final JvmBackendContext context;
    public Map<IrFunction, JvmBackendContext.LocalFunctionData> localDeclarationsData;

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public FragmentLocalFunctionPatchLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final Map<IrFunction, JvmBackendContext.LocalFunctionData> getLocalDeclarationsData() {
        Map<IrFunction, JvmBackendContext.LocalFunctionData> map = this.localDeclarationsData;
        if (map != null) {
            return map;
        }
        Intrinsics.throwUninitializedPropertyAccessException("localDeclarationsData");
        return null;
    }

    public final void setLocalDeclarationsData(Map<IrFunction, JvmBackendContext.LocalFunctionData> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.localDeclarationsData = map;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Unit unit;
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Map<IrFunction, JvmBackendContext.LocalFunctionData> localDeclarationsLoweringData = this.context.getLocalDeclarationsLoweringData();
        if (localDeclarationsLoweringData != null) {
            setLocalDeclarationsData(localDeclarationsLoweringData);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            return;
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitSimpleFunction(final IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrBody body = declaration.getBody();
        if (body != null) {
            IrElementTransformerVoidKt.transformChildrenVoid(body, new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FragmentLocalFunctionPatchLowering.visitSimpleFunction.1
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitCall(IrCall expression) {
                    IrGetValueImpl irGetValueImplIrGet;
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                    JvmBackendContext.LocalFunctionData localFunctionData = FragmentLocalFunctionPatchLowering.this.getLocalDeclarationsData().get(expression.getSymbol().getOwner().getOrigin() instanceof IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER ? FragmentLocalFunctionPatchLowering.this.getContext().getMapping().getDefaultArgumentsOriginalFunction().get(expression.getSymbol().getOwner()) : expression.getSymbol().getOwner());
                    if (localFunctionData == null) {
                        return expression;
                    }
                    LocalDeclarationsLowering.LocalFunctionContext localContext = localFunctionData.getLocalContext();
                    JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(FragmentLocalFunctionPatchLowering.this.getContext(), declaration.getSymbol(), 0, 0, 6, null);
                    IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilderCreateJvmIrBuilder$default, localContext.getTransformedDeclaration());
                    IrSimpleFunction irSimpleFunction = declaration;
                    FragmentLocalFunctionPatchLowering fragmentLocalFunctionPatchLowering = FragmentLocalFunctionPatchLowering.this;
                    IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionAccessExpressionIrCall, expression, 0, 2, null);
                    irFunctionAccessExpressionIrCall.setExtensionReceiver(expression.getExtensionReceiver());
                    irFunctionAccessExpressionIrCall.setDispatchReceiver(expression.getDispatchReceiver());
                    List<IrValueParameter> valueParameters = localContext.getTransformedDeclaration().getValueParameters();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
                    for (IrValueParameter irValueParameter : valueParameters) {
                        IrValueParameter irValueParameter2 = localFunctionData.getNewParameterToOld().get(irValueParameter);
                        if (irValueParameter2 != null) {
                            irGetValueImplIrGet = expression.getValueArgument(irValueParameter2.getIndex());
                            Intrinsics.checkNotNull(irGetValueImplIrGet);
                        } else {
                            IrValueSymbol irValueSymbol = localFunctionData.getNewParameterToCaptured().get(irValueParameter);
                            if (irValueSymbol == null) {
                                throw new IllegalStateException(("Non-mapped parameter " + irValueParameter).toString());
                            }
                            IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                            IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                            irValueParameterBuilder.setType(irValueSymbol.getOwner().getType());
                            irValueParameterBuilder.setName(irValueSymbol.getOwner().getName());
                            if (irValueParameterBuilder.getIndex() == -1) {
                                irValueParameterBuilder.setIndex(irSimpleFunction2.getValueParameters().size());
                            }
                            IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder, irSimpleFunction2);
                            irSimpleFunction2.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction2.getValueParameters(), irValueParameterBuildValueParameter));
                            GenerationState state = fragmentLocalFunctionPatchLowering.getContext().getState();
                            String strAsString = irValueParameterBuildValueParameter.getName().asString();
                            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                            state.recordNewFragmentCaptureParameter(strAsString, IrBasedDescriptorsKt.toIrBasedKotlinType$default(irValueSymbol.getOwner().getType(), null, 1, null), IrBasedDescriptorsKt.toIrBasedDescriptor(irValueSymbol.getOwner()));
                            irGetValueImplIrGet = ExpressionHelpersKt.irGet(jvmIrBuilderCreateJvmIrBuilder$default, irValueParameterBuildValueParameter);
                        }
                        irFunctionAccessExpressionIrCall.putValueArgument(irValueParameter.getIndex(), irGetValueImplIrGet);
                        arrayList.add(Unit.INSTANCE);
                    }
                    return irFunctionAccessExpressionIrCall;
                }
            });
        }
        return declaration;
    }
}
