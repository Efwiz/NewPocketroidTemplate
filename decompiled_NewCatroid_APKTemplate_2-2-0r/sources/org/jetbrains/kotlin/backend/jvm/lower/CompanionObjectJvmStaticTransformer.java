package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: JvmStaticAnnotationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\f\u0010\u0013\u001a\u00020\b*\u00020\nH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/CompanionObjectJvmStaticTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "shouldReplaceWithStaticCall", "", "callee", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "needsStaticProxy", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CompanionObjectJvmStaticTransformer extends IrElementTransformerVoid {
    private final JvmBackendContext context;

    public CompanionObjectJvmStaticTransformer(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    private final boolean needsStaticProxy(IrSimpleFunction irSimpleFunction) {
        if (irSimpleFunction.getIsExternal()) {
            return true;
        }
        return (Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE) || JvmIrInlineUtilsKt.isEffectivelyInlineOnly(irSimpleFunction) || ((!Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.PROTECTED) || irSimpleFunction.getIsInline()) && irSimpleFunction.getOrigin().getIsSynthetic())) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0055  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitClass(IrClass declaration) {
        List<IrDeclaration> declarations;
        IrDeclaration irDeclaration;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrStatement irStatementVisitClass = super.visitClass(declaration);
        IrClass irClassCompanionObject = AdditionalIrUtilsKt.companionObject(declaration);
        if (irClassCompanionObject != null && (declarations = irClassCompanionObject.getDeclarations()) != null) {
            int size = declarations.size();
            for (int i = 0; i < size; i++) {
                IrDeclaration irDeclaration2 = declarations.get(i);
                if ((irDeclaration2 instanceof IrSimpleFunction) && JvmStaticAnnotationLoweringKt.isJvmStaticDeclaration(irDeclaration2)) {
                    IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclaration2;
                    if (needsStaticProxy(irSimpleFunction)) {
                        Pair<IrSimpleFunction, IrSimpleFunction> staticAndCompanionDeclaration = this.context.getCachedDeclarations().getStaticAndCompanionDeclaration(irSimpleFunction);
                        IrSimpleFunction irSimpleFunctionComponent1 = staticAndCompanionDeclaration.component1();
                        IrMutableAnnotationContainer irMutableAnnotationContainer = (IrSimpleFunction) staticAndCompanionDeclaration.component2();
                        declaration.getDeclarations().add(irSimpleFunctionComponent1);
                        irDeclaration = (IrElement) irMutableAnnotationContainer;
                    } else {
                        irDeclaration = irDeclaration2;
                    }
                } else {
                    irDeclaration = irDeclaration2;
                }
                if (irDeclaration == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                }
                declarations.set(i, (IrDeclaration) irDeclaration);
            }
        }
        return irStatementVisitClass;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrCall irCall = expression;
        IrElementTransformerVoidKt.transformChildrenVoid(irCall, this);
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        if (shouldReplaceWithStaticCall(owner)) {
            return JvmStaticAnnotationLoweringKt.makeStatic(expression, this.context.getIrBuiltIns(), this.context.getCachedDeclarations().getStaticAndCompanionDeclaration(owner).component1());
        }
        if (Intrinsics.areEqual(owner.getSymbol(), this.context.getIr().getSymbols().getIndyLambdaMetafactoryIntrinsic())) {
            IrExpression valueArgument = expression.getValueArgument(1);
            IrFunctionReference irFunctionReference = valueArgument instanceof IrFunctionReference ? (IrFunctionReference) valueArgument : null;
            if (irFunctionReference == null) {
                throw new AssertionError("'implMethodReference' is expected to be 'IrFunctionReference': " + DumpIrTreeKt.dump$default(irCall, null, 1, null));
            }
            IrFunction owner2 = irFunctionReference.getSymbol().getOwner();
            if (irFunctionReference.getDispatchReceiver() != null && (owner2 instanceof IrSimpleFunction)) {
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) owner2;
                if (shouldReplaceWithStaticCall(irSimpleFunction)) {
                    IrSimpleFunction irSimpleFunctionComponent1 = this.context.getCachedDeclarations().getStaticAndCompanionDeclaration(irSimpleFunction).component1();
                    expression.putValueArgument(1, new IrFunctionReferenceImpl(irFunctionReference.getStartOffset(), irFunctionReference.getEndOffset(), irFunctionReference.getType(), irSimpleFunctionComponent1.getSymbol(), irSimpleFunctionComponent1.getTypeParameters().size(), irSimpleFunctionComponent1.getValueParameters().size(), irFunctionReference.getReflectionTarget(), irFunctionReference.getOrigin()));
                }
            }
            return expression;
        }
        return expression;
    }

    private final boolean shouldReplaceWithStaticCall(IrSimpleFunction callee) {
        return JvmStaticAnnotationLoweringKt.isJvmStaticInCompanion(callee) && Intrinsics.areEqual(callee.getVisibility(), DescriptorVisibilities.PROTECTED) && !JvmIrInlineUtilsKt.isInlineFunctionCall(callee, this.context);
    }
}
