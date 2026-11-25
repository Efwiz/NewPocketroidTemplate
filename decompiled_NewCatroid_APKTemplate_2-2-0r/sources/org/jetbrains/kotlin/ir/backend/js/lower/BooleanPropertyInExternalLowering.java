package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.config.RuntimeDiagnostic;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: BooleanPropertyInExternalLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/BooleanPropertyInExternalLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ExternalBooleanPropertyProcessor", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BooleanPropertyInExternalLowering implements BodyLoweringPass {
    private final JsIrBackendContext context;

    public BooleanPropertyInExternalLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (this.context.getSafeExternalBoolean() || this.context.getSafeExternalBooleanDiagnostic() != null) {
            IrElementTransformerVoidKt.transformChildrenVoid(irBody, new ExternalBooleanPropertyProcessor(this.context));
        }
    }

    /* JADX INFO: compiled from: BooleanPropertyInExternalLowering.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\f\u0010\u0015\u001a\u00020\u0016*\u00020\u000eH\u0002R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/BooleanPropertyInExternalLowering$ExternalBooleanPropertyProcessor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "booleanType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getBooleanType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "safeExternalBoolean", "", "getSafeExternalBoolean", "()Z", "safeExternalBooleanDiagnostic", "Lorg/jetbrains/kotlin/js/config/RuntimeDiagnostic;", "getSafeExternalBooleanDiagnostic", "()Lorg/jetbrains/kotlin/js/config/RuntimeDiagnostic;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "diagnosticMethod", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ExternalBooleanPropertyProcessor extends IrElementTransformerVoid {
        private final JsIrBackendContext context;

        /* JADX INFO: compiled from: BooleanPropertyInExternalLowering.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[RuntimeDiagnostic.values().length];
                try {
                    iArr[RuntimeDiagnostic.LOG.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[RuntimeDiagnostic.EXCEPTION.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public ExternalBooleanPropertyProcessor(JsIrBackendContext context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.context = context;
        }

        private final boolean getSafeExternalBoolean() {
            return this.context.getSafeExternalBoolean();
        }

        private final RuntimeDiagnostic getSafeExternalBooleanDiagnostic() {
            return this.context.getSafeExternalBooleanDiagnostic();
        }

        private final IrType getBooleanType() {
            return this.context.getIrBuiltIns().getBooleanType();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitCall(IrCall expression) {
            IrProperty owner;
            IrGetValueImpl irGetValueImplIrGet;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            IrSimpleFunctionSymbol symbol = expression.getSymbol();
            IrSimpleFunction owner2 = symbol.getOwner();
            IrPropertySymbol correspondingPropertySymbol = owner2.getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
                return expression;
            }
            if (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner) && Intrinsics.areEqual(owner2, owner.getGetter()) && Intrinsics.areEqual(owner2.getReturnType(), getBooleanType())) {
                RuntimeDiagnostic safeExternalBooleanDiagnostic = getSafeExternalBooleanDiagnostic();
                IrSimpleFunctionSymbol irSimpleFunctionSymbolDiagnosticMethod = safeExternalBooleanDiagnostic != null ? diagnosticMethod(safeExternalBooleanDiagnostic) : null;
                if (!getSafeExternalBoolean() && irSimpleFunctionSymbolDiagnosticMethod == null) {
                    return expression;
                }
                if (getSafeExternalBoolean() && irSimpleFunctionSymbolDiagnosticMethod == null) {
                    IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.context.getIntrinsics().getJsNativeBoolean(), null, null, null, null, 30, null);
                    irCallBuildCall$default.putValueArgument(0, expression);
                    return irCallBuildCall$default;
                }
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, symbol, 0, 0, 6, (Object) null);
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset(), null, null, false, 64, null);
                IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, (IrExpression) expression, (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
                JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
                Intrinsics.checkNotNull(irSimpleFunctionSymbolDiagnosticMethod);
                IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(jsIrBuilder, irSimpleFunctionSymbolDiagnosticMethod, null, null, null, null, 30, null);
                FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(owner);
                irCallBuildCall$default2.putValueArgument(0, IrUtilsKt.toIrConst$default(fqNameWhenAvailable != null ? fqNameWhenAvailable.asString() : null, irBlockBuilder.getContext().getIrBuiltIns().getStringType(), 0, 0, 6, null));
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                IrVariable irVariable = irVariableCreateTmpVariable$default;
                irCallBuildCall$default2.putValueArgument(1, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
                irBlockBuilder.unaryPlus(irCallBuildCall$default2);
                if (getSafeExternalBoolean()) {
                    IrCall irCallBuildCall$default3 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.context.getIntrinsics().getJsNativeBoolean(), null, null, null, null, 30, null);
                    irCallBuildCall$default3.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
                    irGetValueImplIrGet = irCallBuildCall$default3;
                } else {
                    irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable);
                }
                irBlockBuilder.unaryPlus(irGetValueImplIrGet);
                return irBlockBuilder.getIrBlockBody();
            }
            return expression;
        }

        private final IrSimpleFunctionSymbol diagnosticMethod(RuntimeDiagnostic runtimeDiagnostic) {
            int i = WhenMappings.$EnumSwitchMapping$0[runtimeDiagnostic.ordinal()];
            if (i == 1) {
                return this.context.getIntrinsics().getJsBooleanInExternalLog();
            }
            if (i == 2) {
                return this.context.getIntrinsics().getJsBooleanInExternalException();
            }
            throw new NoWhenBranchMatchedException();
        }
    }
}
