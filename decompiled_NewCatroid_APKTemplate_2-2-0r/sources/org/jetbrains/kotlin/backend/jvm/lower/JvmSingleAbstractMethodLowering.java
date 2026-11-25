package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;

/* JADX INFO: compiled from: JvmSingleAbstractMethodLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000bH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0014J\u0010\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\u001e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0016J\u0014\u0010\u001e\u001a\u00020\u001f*\u00020 2\u0006\u0010!\u001a\u00020\"H\u0014R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\u00020\u0006*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0018\u0010\r\u001a\u00020\u0006*\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\f¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmSingleAbstractMethodLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/SingleAbstractMethodLowering;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "inInlineFunctionScope", "", "getInInlineFunctionScope", "()Z", "isJavaSamConversionWithEqualsHashCode", "isKotlinFunInterface", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "needEqualsHashCodeMethods", "getNeedEqualsHashCodeMethods", "getSuperTypeForWrapper", "typeOperand", "getSuspendFunctionWithoutContinuation", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "function", "getWrappedFunctionType", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getWrapperVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "scopes", "", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "setConstructorSourceRange", "", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrFunctionBuilder;", "createFor", "Lorg/jetbrains/kotlin/ir/IrElement;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JvmSingleAbstractMethodLowering extends SingleAbstractMethodLowering {
    private final boolean isJavaSamConversionWithEqualsHashCode;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmSingleAbstractMethodLowering(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.isJavaSamConversionWithEqualsHashCode = context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.JavaSamConversionEqualsHashCode);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    public boolean getInInlineFunctionScope() {
        List<ScopeWithIr> allScopes = getAllScopes();
        if ((allScopes instanceof Collection) && allScopes.isEmpty()) {
            return false;
        }
        Iterator<T> it = allScopes.iterator();
        while (it.hasNext()) {
            IrElement irElement = ((ScopeWithIr) it.next()).getIrElement();
            IrDeclaration irDeclaration = irElement instanceof IrDeclaration ? (IrDeclaration) irElement : null;
            if (irDeclaration != null && JvmIrInlineUtilsKt.isInPublicInlineScope(irDeclaration)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    public DescriptorVisibility getWrapperVisibility(IrTypeOperatorCall expression, List<? extends ScopeWithIr> scopes) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        DescriptorVisibility descriptorVisibility = getInInlineFunctionScope() ? DescriptorVisibilities.PUBLIC : JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkNotNull(descriptorVisibility);
        return descriptorVisibility;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    public IrType getSuperTypeForWrapper(IrType typeOperand) {
        Intrinsics.checkNotNullParameter(typeOperand, "typeOperand");
        IrClass erasedUpperBound = JvmIrTypeUtilsKt.getErasedUpperBound(typeOperand);
        CommonBackendContext context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.JvmBackendContext");
        return JvmIrUtilsKt.rawType(erasedUpperBound, (JvmBackendContext) context);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    protected IrType getWrappedFunctionType(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        CommonBackendContext context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.JvmBackendContext");
        return JvmIrUtilsKt.rawType(klass, (JvmBackendContext) context);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    protected IrSimpleFunction getSuspendFunctionWithoutContinuation(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return JvmIrUtilsKt.suspendFunctionOriginal(function);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    protected void setConstructorSourceRange(IrFunctionBuilder irFunctionBuilder, IrElement createFor) {
        Intrinsics.checkNotNullParameter(irFunctionBuilder, "<this>");
        Intrinsics.checkNotNullParameter(createFor, "createFor");
        irFunctionBuilder.setStartOffset(-1);
        irFunctionBuilder.setEndOffset(-1);
    }

    private final boolean isKotlinFunInterface(IrType irType) {
        IrClass irClass = IrTypesKt.getClass(irType);
        return !Intrinsics.areEqual(irClass != null ? irClass.getOrigin() : null, IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    public boolean getNeedEqualsHashCodeMethods(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isKotlinFunInterface(irType) || this.isJavaSamConversionWithEqualsHashCode;
    }
}
