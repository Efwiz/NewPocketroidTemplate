package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;

/* JADX INFO: compiled from: JsCodeCallsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u000e\u0010\u0018\u001a\u0004\u0018\u00010\u0019*\u00020\u001aH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00060\bR\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/JsCodeCallsLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "jsRelatedSymbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsRelatedSymbols;", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "getJsRelatedSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols$JsRelatedSymbols;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "transformFunction", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "transformProperty", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getJsCode", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCodeCallsLowering implements FileLoweringPass {
    private final WasmBackendContext context;

    public JsCodeCallsLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    private final WasmSymbols.JsRelatedSymbols getJsRelatedSymbols() {
        return this.context.getWasmSymbols().getJsRelatedSymbols();
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        if (this.context.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.WASI) {
            return;
        }
        TransformKt.transformDeclarationsFlat(irFile, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.JsCodeCallsLowering.lower.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<IrDeclaration> invoke(IrDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (declaration instanceof IrSimpleFunction) {
                    return JsCodeCallsLowering.this.transformFunction((IrSimpleFunction) declaration);
                }
                if (declaration instanceof IrProperty) {
                    return JsCodeCallsLowering.this.transformProperty((IrProperty) declaration);
                }
                return null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrDeclaration> transformFunction(IrSimpleFunction function) {
        String jsCode;
        boolean z;
        IrBody body = function.getBody();
        if (body == null) {
            return null;
        }
        if (!(body instanceof IrBlockBody)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        IrStatement irStatement = (IrStatement) CollectionsKt.singleOrNull((List) ((IrBlockBody) body).getStatements());
        if (irStatement == null) {
            return null;
        }
        if (irStatement instanceof IrReturn) {
            jsCode = getJsCode(((IrReturn) irStatement).getValue());
            if (jsCode == null) {
                return null;
            }
            z = true;
        } else {
            if (!(irStatement instanceof IrCall) || (jsCode = getJsCode((IrExpression) irStatement)) == null) {
                return null;
            }
            z = false;
        }
        List<IrValueParameter> valueParameters = function.getValueParameters();
        StringBuilder sb = new StringBuilder("(");
        sb.append(CollectionsKt.joinToString$default(valueParameters, null, null, null, 0, null, new Function1<IrValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.JsCodeCallsLowering$transformFunction$jsFunCode$1$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrValueParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String identifier = it.getName().getIdentifier();
                Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                return identifier;
            }
        }, 31, null));
        sb.append(") => ");
        if (!z) {
            sb.append("{ ");
        }
        sb.append(jsCode);
        if (!z) {
            sb.append(" }");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        List<IrValueParameter> valueParameters2 = function.getValueParameters();
        if (!(valueParameters2 instanceof Collection) || !valueParameters2.isEmpty()) {
            Iterator<T> it = valueParameters2.iterator();
            while (it.hasNext()) {
                if (((IrValueParameter) it.next()).getDefaultValue() != null) {
                    IrSimpleFunction irSimpleFunctionCreateExternalJsFunction = ComplexExternalDeclarationsToTopLevelFunctionsLoweringKt.createExternalJsFunction(this.context, function.getName(), "_js_code", function.getReturnType(), string);
                    IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionCreateExternalJsFunction, function, null, null, 6, null);
                    List<IrValueParameter> valueParameters3 = function.getValueParameters();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters3, 10));
                    Iterator<T> it2 = valueParameters3.iterator();
                    while (it2.hasNext()) {
                        arrayList.add(IrUtilsKt.copyTo$default((IrValueParameter) it2.next(), irSimpleFunctionCreateExternalJsFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 7678, null));
                    }
                    irSimpleFunctionCreateExternalJsFunction.setValueParameters(arrayList);
                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, function.getSymbol(), 0, 0, 6, (Object) null);
                    IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                    IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                    IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, irSimpleFunctionCreateExternalJsFunction.getSymbol());
                    int i = 0;
                    for (Object obj : function.getValueParameters()) {
                        int i2 = i + 1;
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        irCall.putValueArgument(i, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueParameter) obj));
                        i = i2;
                    }
                    int i3 = 0;
                    for (Object obj2 : function.getTypeParameters()) {
                        int i4 = i3 + 1;
                        if (i3 < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        irCall.putTypeArgument(i3, IrTypesKt.getDefaultType((IrTypeParameter) obj2));
                        i3 = i4;
                    }
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irCall));
                    function.setBody(irBlockBodyBuilder.getIrBlockBody());
                    return CollectionsKt.listOf((Object[]) new IrSimpleFunction[]{function, irSimpleFunctionCreateExternalJsFunction});
                }
            }
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default2 = LowerUtilsKt.createIrBuilder$default(this.context, function.getSymbol(), 0, 0, 6, (Object) null);
        List<IrConstructorCall> annotations = function.getAnnotations();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default2;
        IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor(declarationIrBuilder, getJsRelatedSymbols().getJsFunConstructor(), CollectionsKt.emptyList());
        irConstructorCallIrCallConstructor.putValueArgument(0, ExpressionHelpersKt.irString(declarationIrBuilder, string));
        Unit unit = Unit.INSTANCE;
        function.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) annotations, irConstructorCallIrCallConstructor));
        function.setBody(null);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrDeclaration> transformProperty(IrProperty property) {
        IrExpressionBody initializer;
        String jsCode;
        IrField backingField = property.getBackingField();
        if (backingField == null || (initializer = backingField.getInitializer()) == null || (jsCode = getJsCode(initializer.getExpression())) == null) {
            return null;
        }
        IrSimpleFunction irSimpleFunctionCreateExternalJsFunction = ComplexExternalDeclarationsToTopLevelFunctionsLoweringKt.createExternalJsFunction(this.context, property.getName(), "_js_code", backingField.getType(), "() => (" + jsCode + ')');
        initializer.setExpression(ExpressionHelpersKt.irCall(LowerUtilsKt.createIrBuilder$default(this.context, backingField.getSymbol(), 0, 0, 6, (Object) null), irSimpleFunctionCreateExternalJsFunction));
        return CollectionsKt.listOf((Object[]) new IrDeclaration[]{property, irSimpleFunctionCreateExternalJsFunction});
    }

    private final String getJsCode(IrExpression irExpression) {
        IrCall irCall = irExpression instanceof IrCall ? (IrCall) irExpression : null;
        if (irCall == null || !Intrinsics.areEqual(irCall.getSymbol(), getJsRelatedSymbols().getJsCode())) {
            return null;
        }
        IrExpression valueArgument = irCall.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<kotlin.String>");
        return (String) ((IrConst) valueArgument).getValue();
    }
}
