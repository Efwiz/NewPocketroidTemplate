package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: ObjectLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\f\u0010\u001b\u001a\u00020\u0014*\u00020\nH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ObjectUsageLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "instanceField", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInstanceField", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setInstanceField", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "instanceField$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "generateInitInstanceField", "Lorg/jetbrains/kotlin/ir/IrStatement;", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getValueForInstanceFieldForTheFirstTime", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectUsageLowering implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(ObjectUsageLowering.class), "instanceField", "getInstanceField(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrField;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: instanceField$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate instanceField;

    public ObjectUsageLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.instanceField = context.getMapping().getObjectToInstanceField();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrField getInstanceField(IrClass irClass) {
        return (IrField) this.instanceField.getValue(irClass, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        IrField instanceField;
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (!(container instanceof IrConstructor) || !((IrConstructor) container).getIsPrimary()) {
            container = null;
        }
        final IrClass parentAsClass = container != null ? IrUtilsKt.getParentAsClass(container) : null;
        if (parentAsClass != null && (instanceField = getInstanceField(parentAsClass)) != null && (!this.context.getEs6mode() || IrUtilsKt.getSuperClass(parentAsClass) != null)) {
            ((IrBlockBody) irBody).getStatements().add(0, generateInitInstanceField(instanceField, getValueForInstanceFieldForTheFirstTime(parentAsClass)));
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ObjectUsageLowering.lower.2
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetObjectValue(IrGetObjectValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrClass owner = expression.getSymbol().getOwner();
                return IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner) ? expression : JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, ObjectLoweringKt.getOrCreateGetInstanceFunction(ObjectUsageLowering.this.getContext(), owner).getSymbol(), null, null, null, null, 30, null);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrClass irClass = parentAsClass;
                IrField instanceField2 = irClass != null ? ObjectUsageLowering.this.getInstanceField(irClass) : null;
                if (!ObjectUsageLowering.this.getContext().getEs6mode() || instanceField2 == null) {
                    return super.visitDelegatingConstructorCall(expression);
                }
                JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
                IrType unitType = ObjectUsageLowering.this.getContext().getIrBuiltIns().getUnitType();
                ObjectUsageLowering objectUsageLowering = ObjectUsageLowering.this;
                JsIrBuilder jsIrBuilder2 = JsIrBuilder.INSTANCE;
                IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver);
                return jsIrBuilder.buildComposite(unitType, CollectionsKt.listOf((Object[]) new IrStatement[]{super.visitDelegatingConstructorCall(expression), objectUsageLowering.generateInitInstanceField(instanceField2, JsIrBuilder.buildGetValue$default(jsIrBuilder2, thisReceiver.getSymbol(), null, 2, null))}));
            }
        });
    }

    private final IrExpression getValueForInstanceFieldForTheFirstTime(IrClass irClass) {
        if (this.context.getEs6mode() && (this.context instanceof JsIrBackendContext)) {
            JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
            IrValueParameter thisReceiver = irClass.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            return jsIrBuilder.buildNull(thisReceiver.getType());
        }
        JsIrBuilder jsIrBuilder2 = JsIrBuilder.INSTANCE;
        IrValueParameter thisReceiver2 = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver2);
        return JsIrBuilder.buildGetValue$default(jsIrBuilder2, thisReceiver2.getSymbol(), null, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrStatement generateInitInstanceField(IrField instanceField, IrExpression value) {
        return JsIrBuilder.buildSetField$default(JsIrBuilder.INSTANCE, instanceField.getSymbol(), null, value, this.context.getIrBuiltIns().getUnitType(), null, 16, null);
    }
}
