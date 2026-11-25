package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\nH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumUsageLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getInstanceFun", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getGetInstanceFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setGetInstanceFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getInstanceFun$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "lowerEnumEntry", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "enumEntry", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumUsageLowering implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumUsageLowering.class), "getInstanceFun", "getGetInstanceFun(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: getInstanceFun$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate getInstanceFun;

    public EnumUsageLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.getInstanceFun = context.getMapping().getEnumEntryToGetInstanceFun();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final IrSimpleFunction getGetInstanceFun(IrEnumEntry irEnumEntry) {
        return (IrSimpleFunction) this.getInstanceFun.getValue(irEnumEntry, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.EnumUsageLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetEnumValue(IrGetEnumValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrEnumEntry owner = expression.getSymbol().getOwner();
                IrDeclarationParent parent = owner.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                return ((IrClass) parent).getIsExternal() ? expression : EnumUsageLowering.this.lowerEnumEntry(owner);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrCall lowerEnumEntry(IrEnumEntry enumEntry) {
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrSimpleFunction getInstanceFun = getGetInstanceFun(enumEntry);
        Intrinsics.checkNotNull(getInstanceFun);
        return JsIrBuilder.buildCall$default(jsIrBuilder, getInstanceFun.getSymbol(), null, null, null, null, 30, null);
    }
}
