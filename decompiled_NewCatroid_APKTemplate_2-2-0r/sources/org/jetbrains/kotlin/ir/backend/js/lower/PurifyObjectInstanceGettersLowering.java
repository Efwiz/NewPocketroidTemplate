package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrExpressionBodyImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: PurifyObjectInstanceGettersLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J\f\u0010\u0019\u001a\u00020\u001a*\u00020\u0017H\u0002J\f\u0010\u001b\u001a\u00020\u001a*\u00020\nH\u0002J\u0014\u0010\u001c\u001a\u00020\u001a*\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0014\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016*\u00020\u0012H\u0002J\u0014\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016*\u00020\bH\u0002J\u0014\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016*\u00020\"H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PurifyObjectInstanceGettersLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "instanceField", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInstanceField", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setInstanceField", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "instanceField$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "primaryConstructorReplacement", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getPrimaryConstructorReplacement", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "isObjectConstructor", "", "isPureObject", "isPureStatementForObjectInitialization", "Lorg/jetbrains/kotlin/ir/IrStatement;", "owner", "purifyObjectGetterIfPossible", "purifyObjectInstanceFieldIfPossible", "removeInstanceFieldInitializationIfPossible", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PurifyObjectInstanceGettersLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(PurifyObjectInstanceGettersLowering.class), "instanceField", "getInstanceField(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrField;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: instanceField$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate instanceField;

    public PurifyObjectInstanceGettersLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.instanceField = context.getMapping().getObjectToInstanceField();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final IrField getInstanceField(IrClass irClass) {
        return (IrField) this.instanceField.getValue(irClass, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if ((declaration instanceof IrFunction) && isObjectConstructor(declaration)) {
            return removeInstanceFieldInitializationIfPossible((IrFunction) declaration);
        }
        if (declaration instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) declaration;
            if (IrJsUtilsKt.isObjectInstanceGetter(irSimpleFunction)) {
                return purifyObjectGetterIfPossible(irSimpleFunction);
            }
        }
        if (declaration instanceof IrField) {
            IrField irField = (IrField) declaration;
            if (IrJsUtilsKt.isObjectInstanceField(irField)) {
                return purifyObjectInstanceFieldIfPossible(irField);
            }
        }
        return null;
    }

    private final List<IrDeclaration> removeInstanceFieldInitializationIfPossible(IrFunction irFunction) {
        List<IrStatement> statements;
        if (isPureObject(IrUtilsKt.getParentAsClass(irFunction))) {
            IrBody body = irFunction.getBody();
            IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
            if (irBlockBody != null && (statements = irBlockBody.getStatements()) != null) {
                final C46791 c46791 = new Function1<IrStatement, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PurifyObjectInstanceGettersLowering.removeInstanceFieldInitializationIfPossible.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(IrStatement it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf((it instanceof IrSetField) && IrJsUtilsKt.isObjectInstanceField(((IrSetField) it).getSymbol().getOwner()));
                    }
                };
                statements.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PurifyObjectInstanceGettersLowering$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return PurifyObjectInstanceGettersLowering.removeInstanceFieldInitializationIfPossible$lambda$0(c46791, obj);
                    }
                });
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean removeInstanceFieldInitializationIfPossible$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final List<IrDeclaration> purifyObjectGetterIfPossible(IrSimpleFunction irSimpleFunction) {
        IrClass owner;
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irSimpleFunction.getReturnType());
        if (classOrNull != null && (owner = classOrNull.getOwner()) != null && isPureObject(owner)) {
            IrBody body = irSimpleFunction.getBody();
            IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
            if (irBlockBody == null) {
                return null;
            }
            IrField instanceField = getInstanceField(owner);
            if (instanceField == null) {
                throw new IllegalStateException("Expect the object instance field to be created".toString());
            }
            irBlockBody.getStatements().clear();
            irBlockBody.getStatements().add(JsIrBuilder.INSTANCE.buildReturn(irSimpleFunction.getSymbol(), JsIrBuilder.INSTANCE.buildGetField(instanceField.getSymbol(), (124 & 2) != 0 ? null : null, (124 & 4) != 0 ? null : null, (124 & 8) == 0 ? null : null, (124 & 16) != 0 ? -1 : 0, (124 & 32) == 0 ? 0 : -1, (124 & 64) != 0 ? JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE : null), IrUtilsKt.getDefaultType(owner)));
        }
        return null;
    }

    private final List<IrDeclaration> purifyObjectInstanceFieldIfPossible(IrField irField) {
        IrClass owner;
        IrCall irCallBuildCall$default;
        IrConstructorCall irConstructorCallBuildConstructorCall$default;
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irField.getType());
        if (classOrNull != null && (owner = classOrNull.getOwner()) != null && isPureObject(owner)) {
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(owner);
            if (primaryConstructor == null || (irConstructorCallBuildConstructorCall$default = JsIrBuilder.buildConstructorCall$default(JsIrBuilder.INSTANCE, primaryConstructor.getSymbol(), null, null, null, 14, null)) == null) {
                IrSimpleFunction primaryConstructorReplacement = getPrimaryConstructorReplacement(owner);
                if (primaryConstructorReplacement == null) {
                    throw new IllegalStateException("Object should contain a primary constructor".toString());
                }
                irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, primaryConstructorReplacement.getSymbol(), null, null, null, null, 30, null);
            } else {
                irCallBuildCall$default = irConstructorCallBuildConstructorCall$default;
            }
            irField.setInitializer(new IrExpressionBodyImpl(irCallBuildCall$default));
        }
        return null;
    }

    private final boolean isObjectConstructor(IrDeclaration irDeclaration) {
        return ((irDeclaration instanceof IrConstructor) || ES6ConstructorLoweringKt.isEs6ConstructorReplacement(irDeclaration)) && IrUtilsKt.isObject(IrUtilsKt.getParentAsClass(irDeclaration));
    }

    private final boolean isPureObject(final IrClass irClass) {
        return ((Boolean) MappingsKt.getOrPut(this.context.getMapping().getObjectsWithPureInitialization(), irClass, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PurifyObjectInstanceGettersLowering.isPureObject.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                boolean z;
                IrBody body;
                List<IrStatement> statements;
                IrFunction primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass);
                if (primaryConstructor == null) {
                    primaryConstructor = this.getPrimaryConstructorReplacement(irClass);
                }
                IrFunction irFunction = primaryConstructor;
                if (IrUtilsKt.getSuperClass(irClass) != null) {
                    z = false;
                } else {
                    if (irFunction != null && (body = irFunction.getBody()) != null && (statements = IrUtilsKt.getStatements(body)) != null) {
                        List<IrStatement> list = statements;
                        PurifyObjectInstanceGettersLowering purifyObjectInstanceGettersLowering = this;
                        IrClass irClass2 = irClass;
                        if (!(list instanceof Collection) || !list.isEmpty()) {
                            Iterator<T> it = list.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (!purifyObjectInstanceGettersLowering.isPureStatementForObjectInitialization((IrStatement) it.next(), irClass2)) {
                                        z = false;
                                    }
                                }
                            }
                        }
                    }
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isPureStatementForObjectInitialization(IrStatement irStatement, IrClass irClass) {
        IrSetField irSetField;
        IrExpression receiver;
        IrExpression receiver2;
        IrExpression initializer;
        if (irStatement instanceof IrReturn) {
            return true;
        }
        if ((irStatement instanceof IrDelegatingConstructorCall) && Intrinsics.areEqual(((IrDelegatingConstructorCall) irStatement).getSymbol().getOwner().getParent(), this.context.getIrBuiltIns().getAnyClass().getOwner())) {
            return true;
        }
        if ((irStatement instanceof IrExpression) && org.jetbrains.kotlin.backend.common.ir.IrUtilsKt.isPure((IrExpression) irStatement, true, false, this.context)) {
            return true;
        }
        if (irStatement instanceof IrContainerExpression) {
            List<IrStatement> statements = ((IrContainerExpression) irStatement).getStatements();
            if ((statements instanceof Collection) && statements.isEmpty()) {
                return true;
            }
            Iterator<T> it = statements.iterator();
            while (it.hasNext()) {
                if (!isPureStatementForObjectInitialization((IrStatement) it.next(), irClass)) {
                }
            }
            return true;
        }
        if ((irStatement instanceof IrVariable) && (ES6ConstructorLoweringKt.isEs6DelegatingConstructorCallReplacement((IrDeclaration) irStatement) || (initializer = ((IrVariable) irStatement).getInitializer()) == null || isPureStatementForObjectInitialization(initializer, irClass))) {
            return true;
        }
        if ((irStatement instanceof IrGetField) && (receiver2 = ((IrGetField) irStatement).getReceiver()) != null && isPureStatementForObjectInitialization(receiver2, irClass)) {
            return true;
        }
        boolean z = irStatement instanceof IrSetField;
        if (z && (receiver = (irSetField = (IrSetField) irStatement).getReceiver()) != null && isPureStatementForObjectInitialization(receiver, irClass) && isPureStatementForObjectInitialization(irSetField.getValue(), irClass)) {
            return true;
        }
        if (z && IrJsUtilsKt.isObjectInstanceField(((IrSetField) irStatement).getSymbol().getOwner())) {
            return true;
        }
        if (irStatement instanceof IrSetValue) {
            IrSetValue irSetValue = (IrSetValue) irStatement;
            if (AdditionalIrUtilsKt.isLocal(irSetValue.getSymbol().getOwner()) && isPureStatementForObjectInitialization(irSetValue.getValue(), irClass)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction getPrimaryConstructorReplacement(IrClass irClass) {
        Object next;
        Iterator<T> it = irClass.getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if ((irDeclaration instanceof IrSimpleFunction) && ES6ConstructorLoweringKt.isEs6PrimaryConstructorReplacement((IrSimpleFunction) irDeclaration)) {
                break;
            }
        }
        return (IrSimpleFunction) (next instanceof IrSimpleFunction ? next : null);
    }
}
