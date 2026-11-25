package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0002$%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0011H\u0002J \u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\u0011H\u0002J\u0018\u0010#\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\bH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR3\u0010\u0012\u001a\u0004\u0018\u00010\u0011*\u00020\u00112\b\u0010\u0007\u001a\u0004\u0018\u00010\u00118B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0017\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumClassConstructorBodyTransformer;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "correspondingEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getCorrespondingEntry", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "setCorrespondingEntry", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)V", "correspondingEntry$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "newConstructor", "getNewConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setNewConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "newConstructor$delegate", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "lowerEnumConstructorsBody", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "lowerEnumEntryClassConstructors", "irClass", "entry", "lowerEnumEntryInitializerExpression", "IrEnumClassConstructorTransformer", "IrEnumEntryClassConstructorTransformer", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumClassConstructorBodyTransformer implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumClassConstructorBodyTransformer.class), "newConstructor", "getNewConstructor(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumClassConstructorBodyTransformer.class), "correspondingEntry", "getCorrespondingEntry(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: correspondingEntry$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate correspondingEntry;

    /* JADX INFO: renamed from: newConstructor$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate newConstructor;

    public EnumClassConstructorBodyTransformer(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.newConstructor = context.getMapping().getEnumConstructorToNewConstructor();
        this.correspondingEntry = context.getMapping().getEnumClassToCorrespondingEnumEntry();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstructor getNewConstructor(IrConstructor irConstructor) {
        return (IrConstructor) this.newConstructor.getValue(irConstructor, $$delegatedProperties[0]);
    }

    private final IrEnumEntry getCorrespondingEntry(IrClass irClass) {
        return (IrEnumEntry) this.correspondingEntry.getValue(irClass, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrDeclarationParent parent = container.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null) {
            EnumClassLoweringKt.fixReferencesToConstructorParameters(this.context, irClass, irBody);
            if (container instanceof IrConstructor) {
                if (IrJsUtilsKt.isInstantiableEnum(irClass)) {
                    lowerEnumConstructorsBody((IrConstructor) container);
                }
                IrEnumEntry correspondingEntry = getCorrespondingEntry(irClass);
                if (correspondingEntry != null) {
                    lowerEnumEntryClassConstructors(IrUtilsKt.getParentAsClass(correspondingEntry), correspondingEntry, (IrConstructor) container);
                }
            }
            if (container instanceof IrEnumEntry) {
                lowerEnumEntryInitializerExpression(irClass, (IrEnumEntry) container);
            }
        }
    }

    private final void lowerEnumConstructorsBody(IrConstructor constructor) {
        new IrEnumClassConstructorTransformer(this, constructor).transformBody();
    }

    /* JADX INFO: compiled from: EnumClassLowering.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0012H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumClassConstructorBodyTransformer$IrEnumClassConstructorTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumClassConstructorBodyTransformer;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "getBuilder", "()Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "getConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "transformBody", "", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class IrEnumClassConstructorTransformer extends IrElementTransformerVoid {
        private final DeclarationIrBuilder builder;
        private final IrConstructor constructor;
        final /* synthetic */ EnumClassConstructorBodyTransformer this$0;

        public IrEnumClassConstructorTransformer(EnumClassConstructorBodyTransformer enumClassConstructorBodyTransformer, IrConstructor constructor) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            this.this$0 = enumClassConstructorBodyTransformer;
            this.constructor = constructor;
            this.builder = LowerUtilsKt.createIrBuilder$default(enumClassConstructorBodyTransformer.getContext(), constructor.getSymbol(), 0, 0, 6, (Object) null);
        }

        public final IrConstructor getConstructor() {
            return this.constructor;
        }

        public final DeclarationIrBuilder getBuilder() {
            return this.builder;
        }

        public final void transformBody() {
            IrBody body = this.constructor.getBody();
            if (body != null) {
                IrElementTransformerVoidKt.transformChildrenVoid(body, this);
            }
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrDelegatingConstructorCall visitEnumConstructorCall(IrEnumConstructorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(this.builder, expression.getSymbol().getOwner());
            for (int i = 0; i < 2; i++) {
                irDelegatingConstructorCall.putValueArgument(i, ExpressionHelpersKt.irGet(this.builder, this.constructor.getValueParameters().get(i)));
            }
            int typeArgumentsCount = expression.getTypeArgumentsCount();
            for (int i2 = 0; i2 < typeArgumentsCount; i2++) {
                irDelegatingConstructorCall.putTypeArgument(i2, expression.getTypeArgument(i2));
            }
            return irDelegatingConstructorCall;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrConstructor owner = expression.getSymbol().getOwner();
            IrConstructor newConstructor = this.this$0.getNewConstructor(owner);
            if (newConstructor != null) {
                owner = newConstructor;
            }
            IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(this.builder, owner);
            int i = 0;
            int i2 = 0;
            while (i < 2) {
                irDelegatingConstructorCall.putValueArgument(i2, ExpressionHelpersKt.irGet(this.builder, this.constructor.getValueParameters().get(i)));
                i++;
                i2++;
            }
            int valueArgumentsCount = expression.getValueArgumentsCount();
            int i3 = 0;
            while (i3 < valueArgumentsCount) {
                irDelegatingConstructorCall.putValueArgument(i2, expression.getValueArgument(i3));
                i3++;
                i2++;
            }
            int typeArgumentsCount = expression.getTypeArgumentsCount();
            for (int i4 = 0; i4 < typeArgumentsCount; i4++) {
                irDelegatingConstructorCall.putTypeArgument(i4, expression.getTypeArgument(i4));
            }
            return irDelegatingConstructorCall;
        }
    }

    private final void lowerEnumEntryClassConstructors(IrClass irClass, IrEnumEntry entry, IrConstructor constructor) {
        IrElementTransformerVoidKt.transformChildrenVoid(constructor, new IrEnumEntryClassConstructorTransformer(this, irClass, entry, true));
    }

    /* JADX INFO: compiled from: EnumClassLowering.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019*\u00020\u0005H\u0002J\u0012\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0019*\u00020\u0005H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumClassConstructorBodyTransformer$IrEnumEntryClassConstructorTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "entry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "isInsideConstructor", "", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumClassConstructorBodyTransformer;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;Z)V", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "getEntry", "()Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "enumEntries", "", "()Z", "buildConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "getNameExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "getOrdinalExpression", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class IrEnumEntryClassConstructorTransformer extends IrElementTransformerVoid {
        private final DeclarationIrBuilder builder;
        private final IrEnumEntry entry;
        private final List<IrEnumEntry> enumEntries;
        private final boolean isInsideConstructor;
        final /* synthetic */ EnumClassConstructorBodyTransformer this$0;

        public IrEnumEntryClassConstructorTransformer(EnumClassConstructorBodyTransformer enumClassConstructorBodyTransformer, IrClass irClass, IrEnumEntry entry, boolean z) {
            Intrinsics.checkNotNullParameter(irClass, "irClass");
            Intrinsics.checkNotNullParameter(entry, "entry");
            this.this$0 = enumClassConstructorBodyTransformer;
            this.entry = entry;
            this.isInsideConstructor = z;
            this.enumEntries = EnumClassLoweringKt.getEnumEntries(irClass);
            this.builder = LowerUtilsKt.createIrBuilder$default(enumClassConstructorBodyTransformer.getContext(), irClass.getSymbol(), 0, 0, 6, (Object) null);
        }

        public final IrEnumEntry getEntry() {
            return this.entry;
        }

        /* JADX INFO: renamed from: isInsideConstructor, reason: from getter */
        public final boolean getIsInsideConstructor() {
            return this.isInsideConstructor;
        }

        private final IrConstImpl<String> getNameExpression(IrEnumEntry irEnumEntry) {
            DeclarationIrBuilder declarationIrBuilder = this.builder;
            String identifier = irEnumEntry.getName().getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            return ExpressionHelpersKt.irString(declarationIrBuilder, identifier);
        }

        private final IrConstImpl<Integer> getOrdinalExpression(IrEnumEntry irEnumEntry) {
            return ExpressionHelpersKt.irInt$default(this.builder, this.enumEntries.indexOf(irEnumEntry), null, 2, null);
        }

        private final IrFunctionAccessExpression buildConstructorCall(IrConstructor constructor) {
            if (this.isInsideConstructor) {
                return ExpressionHelpersKt.irDelegatingConstructorCall(this.builder, constructor);
            }
            return ExpressionHelpersKt.irCall(this.builder, constructor);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitEnumConstructorCall(IrEnumConstructorCall expression) {
            int i;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrConstructor owner = expression.getSymbol().getOwner();
            int i2 = 0;
            boolean z = this.this$0.getNewConstructor(owner) != null;
            if (z) {
                owner = this.this$0.getNewConstructor(owner);
                Intrinsics.checkNotNull(owner);
            }
            IrFunctionAccessExpression irFunctionAccessExpressionBuildConstructorCall = buildConstructorCall(owner);
            if (z) {
                irFunctionAccessExpressionBuildConstructorCall.putValueArgument(0, getNameExpression(this.entry));
                irFunctionAccessExpressionBuildConstructorCall.putValueArgument(1, getOrdinalExpression(this.entry));
                i = 2;
            } else {
                i = 0;
            }
            int valueArgumentsCount = expression.getValueArgumentsCount();
            while (i2 < valueArgumentsCount) {
                irFunctionAccessExpressionBuildConstructorCall.putValueArgument(i, expression.getValueArgument(i2));
                i2++;
                i++;
            }
            return irFunctionAccessExpressionBuildConstructorCall;
        }
    }

    private final void lowerEnumEntryInitializerExpression(IrClass irClass, IrEnumEntry entry) {
        IrExpressionBody initializerExpression = entry.getInitializerExpression();
        entry.setInitializerExpression(initializerExpression != null ? initializerExpression.transform((IrElementTransformer<? super Object>) new IrEnumEntryClassConstructorTransformer(this, irClass, entry, false), (Object) null) : null);
    }
}
