package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: InlineObjectsWithPureInitializationLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R!\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR!\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/InlineObjectsWithPureInitializationLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "hasPureInitialization", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getHasPureInitialization", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Boolean;", "hasPureInitialization$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "instanceField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getInstanceField", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "instanceField$delegate", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineObjectsWithPureInitializationLowering implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property2(new PropertyReference2Impl(Reflection.getOrCreateKotlinClass(InlineObjectsWithPureInitializationLowering.class), "instanceField", "getInstanceField(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrField;")), Reflection.property2(new PropertyReference2Impl(Reflection.getOrCreateKotlinClass(InlineObjectsWithPureInitializationLowering.class), "hasPureInitialization", "getHasPureInitialization(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Boolean;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: hasPureInitialization$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate hasPureInitialization;

    /* JADX INFO: renamed from: instanceField$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate instanceField;

    public InlineObjectsWithPureInitializationLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.instanceField = context.getMapping().getObjectToInstanceField();
        this.hasPureInitialization = context.getMapping().getObjectsWithPureInitialization();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrField getInstanceField(IrClass irClass) {
        return (IrField) this.instanceField.getValue(irClass, $$delegatedProperties[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Boolean getHasPureInitialization(IrClass irClass) {
        return (Boolean) this.hasPureInitialization.getValue(irClass, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.InlineObjectsWithPureInitializationLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                IrClass owner;
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (!IrJsUtilsKt.isObjectInstanceGetter(expression.getSymbol().getOwner())) {
                    return super.visitCall(expression);
                }
                IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(expression.getSymbol().getOwner().getReturnType());
                if (classOrNull != null && (owner = classOrNull.getOwner()) != null) {
                    if (!Intrinsics.areEqual((Object) InlineObjectsWithPureInitializationLowering.this.getHasPureInitialization(owner), (Object) true)) {
                        return super.visitCall(expression);
                    }
                    IrField instanceField = InlineObjectsWithPureInitializationLowering.this.getInstanceField(owner);
                    if (instanceField == null) {
                        throw new IllegalStateException("An instance field for an object should exist".toString());
                    }
                    return JsIrBuilder.INSTANCE.buildGetField(instanceField.getSymbol(), (124 & 2) != 0 ? null : null, (124 & 4) != 0 ? null : null, (124 & 8) == 0 ? null : null, (124 & 16) != 0 ? -1 : expression.getStartOffset(), (124 & 32) == 0 ? expression.getEndOffset() : -1, (124 & 64) != 0 ? JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE : expression.getOrigin());
                }
                throw new IllegalStateException("Expect return type of an object getter is an object type".toString());
            }
        });
    }
}
