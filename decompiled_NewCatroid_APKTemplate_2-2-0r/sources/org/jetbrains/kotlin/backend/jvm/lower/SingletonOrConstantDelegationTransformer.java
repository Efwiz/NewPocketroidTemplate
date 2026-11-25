package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: SingletonOrConstantDelegationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\u0014\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\rH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SingletonOrConstantDelegationTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "declaration", "transform", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SingletonOrConstantDelegationTransformer extends IrElementTransformerVoid {
    private final JvmBackendContext context;

    public SingletonOrConstantDelegationTransformer(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrClass visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        TransformKt.transformDeclarationsFlat(declaration, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.SingletonOrConstantDelegationTransformer.visitClass.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<IrDeclaration> invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrProperty irProperty = it instanceof IrProperty ? (IrProperty) it : null;
                if (irProperty != null) {
                    return SingletonOrConstantDelegationTransformer.this.transform(irProperty);
                }
                return null;
            }
        });
        return declaration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrDeclaration> transform(IrProperty irProperty) {
        IrExpression singletonOrConstantForOptimizableDelegatedProperty = JvmIrLowerUtilsKt.getSingletonOrConstantForOptimizableDelegatedProperty(irProperty);
        IrAnonymousInitializer irAnonymousInitializer = null;
        if (singletonOrConstantForOptimizableDelegatedProperty == null) {
            return null;
        }
        IrProperty irProperty2 = irProperty;
        IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(irProperty2).getThisReceiver();
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter != null) {
            IrSimpleFunction getter2 = irProperty.getGetter();
            getter.transform(new DelegateFieldAccessTransformer(irProperty, IrUtilsKt.remapReceiver(singletonOrConstantForOptimizableDelegatedProperty, thisReceiver, getter2 != null ? getter2.getDispatchReceiverParameter() : null)), null);
        }
        IrSimpleFunction setter = irProperty.getSetter();
        if (setter != null) {
            IrSimpleFunction setter2 = irProperty.getSetter();
            setter.transform(new DelegateFieldAccessTransformer(irProperty, IrUtilsKt.remapReceiver(singletonOrConstantForOptimizableDelegatedProperty, thisReceiver, setter2 != null ? setter2.getDispatchReceiverParameter() : null)), null);
        }
        irProperty.setBackingField(null);
        if (!(singletonOrConstantForOptimizableDelegatedProperty instanceof IrConst) && !(singletonOrConstantForOptimizableDelegatedProperty instanceof IrGetValue)) {
            IrAnonymousInitializer irAnonymousInitializerCreateAnonymousInitializer = this.context.getIrFactory().createAnonymousInitializer(singletonOrConstantForOptimizableDelegatedProperty.getStartOffset(), singletonOrConstantForOptimizableDelegatedProperty.getEndOffset(), IrDeclarationOrigin.DEFINED.INSTANCE, new IrAnonymousInitializerSymbolImpl(IrUtilsKt.getParentAsClass(irProperty2).getSymbol()), IrUtilsKt.isFileClass(IrUtilsKt.getParentAsClass(irProperty2)));
            irAnonymousInitializerCreateAnonymousInitializer.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), singletonOrConstantForOptimizableDelegatedProperty.getStartOffset(), singletonOrConstantForOptimizableDelegatedProperty.getEndOffset(), (List<? extends IrStatement>) CollectionsKt.listOf(IrUtilsKt.remapReceiver(singletonOrConstantForOptimizableDelegatedProperty, null, null))));
            irAnonymousInitializer = irAnonymousInitializerCreateAnonymousInitializer;
        }
        IrSimpleFunction irSimpleFunctionCreateSyntheticMethodForPropertyDelegate = JvmPropertiesLowering.INSTANCE.createSyntheticMethodForPropertyDelegate(this.context, irProperty);
        irSimpleFunctionCreateSyntheticMethodForPropertyDelegate.setBody(ExpressionHelpersKt.irExprBody(JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irSimpleFunctionCreateSyntheticMethodForPropertyDelegate.getSymbol(), 0, 0, 6, null), IrUtilsKt.remapReceiver(singletonOrConstantForOptimizableDelegatedProperty, thisReceiver, irSimpleFunctionCreateSyntheticMethodForPropertyDelegate.getDispatchReceiverParameter())));
        return CollectionsKt.listOfNotNull((Object[]) new IrDeclarationBase[]{irProperty, irAnonymousInitializer, irSimpleFunctionCreateSyntheticMethodForPropertyDelegate});
    }

    /* JADX INFO: compiled from: SingletonOrConstantDelegationLowering.kt */
    @Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"org/jetbrains/kotlin/backend/jvm/lower/SingletonOrConstantDelegationTransformer$transform$DelegateFieldAccessTransformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "newReceiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getNewReceiver", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitGetField", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DelegateFieldAccessTransformer extends IrElementTransformerVoid {
        final /* synthetic */ IrProperty $this_transform;
        private final IrExpression newReceiver;

        public DelegateFieldAccessTransformer(IrProperty this_transform, IrExpression newReceiver) {
            Intrinsics.checkNotNullParameter(this_transform, "$this_transform");
            Intrinsics.checkNotNullParameter(newReceiver, "newReceiver");
            this.$this_transform = this_transform;
            this.newReceiver = newReceiver;
        }

        public final IrExpression getNewReceiver() {
            return this.newReceiver;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetField(IrGetField expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrFieldSymbol symbol = expression.getSymbol();
            IrField backingField = this.$this_transform.getBackingField();
            return Intrinsics.areEqual(symbol, backingField != null ? backingField.getSymbol() : null) ? this.newReceiver : super.visitGetField(expression);
        }
    }
}
