package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: EscapedIdentifiersLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EscapedIdentifiersLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "isEscapedIdentifiersResolved", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "transformer", "Lorg/jetbrains/kotlin/ir/backend/js/lower/EscapedIdentifiersLowering$ReferenceTransformer;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ReferenceTransformer", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EscapedIdentifiersLowering implements BodyLoweringPass {
    private final boolean isEscapedIdentifiersResolved;
    private final ModuleKind moduleKind;
    private final ReferenceTransformer transformer;

    public EscapedIdentifiersLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.transformer = new ReferenceTransformer(context);
        Object obj = context.getConfiguration().get(JSConfigurationKeys.MODULE_KIND);
        Intrinsics.checkNotNull(obj);
        this.moduleKind = (ModuleKind) obj;
        this.isEscapedIdentifiersResolved = CommonConfigurationKeysKt.getLanguageVersionSettings(context.getConfiguration()).supportsFeature(LanguageFeature.JsAllowInvalidCharsIdentifiersEscaping);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        if (this.isEscapedIdentifiersResolved && this.moduleKind == ModuleKind.PLAIN) {
            LowerKt.runOnFilePostfix(this, irFile, true);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (this.isEscapedIdentifiersResolved && this.moduleKind == ModuleKind.PLAIN) {
            IrElementTransformerVoidKt.transformChildrenVoid(irBody, this.transformer);
        }
    }

    /* JADX INFO: compiled from: EscapedIdentifiersLowering.kt */
    @Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0019H\u0016J\f\u0010\u001a\u001a\u00020\u001b*\u00020\u001cH\u0002J\f\u0010\u001d\u001a\u00020\u001b*\u00020\u001eH\u0002J\u0014\u0010\u001f\u001a\u00020 *\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\u0011H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\f*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EscapedIdentifiersLowering$ReferenceTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "globalThisReceiver", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCallImpl;", "getGlobalThisReceiver", "()Lorg/jetbrains/kotlin/ir/expressions/impl/IrCallImpl;", "dummyDispatchReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getDummyDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "isThisReceiver", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "needToBeWrappedWithGlobalThis", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "wrapInGlobalThis", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ReferenceTransformer extends IrElementTransformerVoid {
        private final JsIrBackendContext context;

        public ReferenceTransformer(JsIrBackendContext context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.context = context;
        }

        public final JsIrBackendContext getContext() {
            return this.context;
        }

        private final IrCallImpl getGlobalThisReceiver() {
            IrDynamicType dynamicType = this.context.getDynamicType();
            IrSimpleFunction getter = this.context.getIntrinsics().getGlobalThis().getOwner().getGetter();
            Intrinsics.checkNotNull(getter);
            return new IrCallImpl(-1, -1, dynamicType, getter.getSymbol(), 0, 0, null, null, 192, null);
        }

        private final IrValueParameter getDummyDispatchReceiverParameter(IrFunction irFunction) {
            IrValueParameter irValueParameterCreateValueParameter = this.context.getIrFactory().createValueParameter(irFunction.getStartOffset(), irFunction.getEndOffset(), irFunction.getOrigin(), SpecialNames.THIS, this.context.getIrBuiltIns().getAnyType(), false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, null), -1, (IrType) null, false, false, false);
            irValueParameterCreateValueParameter.setParent(irFunction);
            return irValueParameterCreateValueParameter;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetValue(IrGetValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrValueDeclaration owner = expression.getSymbol().getOwner();
            if (IrUtilsKt.isEffectivelyExternal(owner) && !isThisReceiver(owner)) {
                IrValueDeclaration irValueDeclaration = owner;
                if (needToBeWrappedWithGlobalThis(irValueDeclaration)) {
                    return wrapInGlobalThis(irValueDeclaration, expression);
                }
            }
            return super.visitGetValue(expression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitSetValue(IrSetValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrValueDeclaration owner = expression.getSymbol().getOwner();
            if (IrUtilsKt.isEffectivelyExternal(owner)) {
                IrValueDeclaration irValueDeclaration = owner;
                if (needToBeWrappedWithGlobalThis(irValueDeclaration)) {
                    return new IrSetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), new IrFieldSymbolImpl(null, 1, null), wrapInGlobalThis(irValueDeclaration, expression), expression.getValue(), expression.getType(), (IrStatementOrigin) null, (IrClassSymbol) null);
                }
            }
            return super.visitSetValue(expression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetObjectValue(IrGetObjectValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrClass owner = expression.getSymbol().getOwner();
            if (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner)) {
                IrClass irClass = owner;
                if (needToBeWrappedWithGlobalThis(irClass)) {
                    return wrapInGlobalThis(irClass, expression);
                }
            }
            return super.visitGetObjectValue(expression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitCall(IrCall expression) {
            IrMemberWithContainerSource irMemberWithContainerSource;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrSimpleFunction realOverrideTarget = IrResolveUtilsKt.getRealOverrideTarget(expression.getSymbol().getOwner());
            IrPropertySymbol correspondingPropertySymbol = realOverrideTarget.getCorrespondingPropertySymbol();
            IrSimpleFunction irSimpleFunction = (correspondingPropertySymbol == null || (irMemberWithContainerSource = (IrProperty) correspondingPropertySymbol.getOwner()) == null) ? realOverrideTarget : irMemberWithContainerSource;
            if (expression.getDispatchReceiver() == null && IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irSimpleFunction) && needToBeWrappedWithGlobalThis(irSimpleFunction)) {
                expression.setDispatchReceiver(getGlobalThisReceiver());
                if (realOverrideTarget.getDispatchReceiverParameter() == null) {
                    realOverrideTarget.setDispatchReceiverParameter(getDummyDispatchReceiverParameter(realOverrideTarget));
                }
            }
            return super.visitCall(expression);
        }

        private final boolean needToBeWrappedWithGlobalThis(IrDeclarationWithName irDeclarationWithName) {
            String string = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName).toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return !IdentifierPolicyKt.isValidES5Identifier(string);
        }

        private final IrDynamicMemberExpression wrapInGlobalThis(IrDeclarationWithName irDeclarationWithName, IrExpression irExpression) {
            int startOffset = irExpression.getStartOffset();
            int endOffset = irExpression.getEndOffset();
            IrType type = irExpression.getType();
            String strAsString = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return new IrDynamicMemberExpressionImpl(startOffset, endOffset, type, strAsString, getGlobalThisReceiver());
        }

        private final boolean isThisReceiver(IrValueDeclaration irValueDeclaration) {
            if (!(irValueDeclaration instanceof IrVariable)) {
                IrDeclarationParent parent = irValueDeclaration.getParent();
                if (!(parent instanceof IrSimpleFunction) ? !(!(parent instanceof IrClass) || irValueDeclaration != ((IrClass) parent).getThisReceiver()) : irValueDeclaration == ((IrSimpleFunction) parent).getDispatchReceiverParameter()) {
                    return true;
                }
            }
            return false;
        }
    }
}
