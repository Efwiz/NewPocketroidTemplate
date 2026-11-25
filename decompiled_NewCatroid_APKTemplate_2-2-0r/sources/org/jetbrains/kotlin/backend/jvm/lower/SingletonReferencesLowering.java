package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: SingletonReferencesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0018\u001a\u00020\rH\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020 H\u0016R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SingletonReferencesLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "constructingEnums", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lkotlin/collections/ArrayList;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "isThisAccessible", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isVisitingSuperConstructor", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "thisOfClass", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "declaration", "allowConstructorReceiver", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SingletonReferencesLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final ArrayList<IrDeclarationParent> constructingEnums;
    private final JvmBackendContext context;

    public SingletonReferencesLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.constructingEnums = new ArrayList<>();
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitEnumConstructorCall(IrEnumConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        UtilsKt.push(this.constructingEnums, expression.getSymbol().getOwner().getParent());
        IrExpression irExpressionVisitEnumConstructorCall = super.visitEnumConstructorCall(expression);
        UtilsKt.pop(this.constructingEnums);
        return irExpressionVisitEnumConstructorCall;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetEnumValue(IrGetEnumValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClass correspondingClass = expression.getSymbol().getOwner().getCorrespondingClass();
        IrValueParameter irValueParameterThisOfClass$default = thisOfClass$default(this, correspondingClass, false, 2, null);
        if (correspondingClass != null && irValueParameterThisOfClass$default != null && isThisAccessible(correspondingClass) && !isVisitingSuperConstructor(correspondingClass)) {
            return new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irValueParameterThisOfClass$default.getSymbol(), null, 16, null);
        }
        return new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), this.context.getCachedDeclarations().getFieldForEnumEntry(expression.getSymbol().getOwner()).getSymbol(), expression.getType(), null, null, 48, null);
    }

    private final boolean isThisAccessible(IrClass irClass) {
        Iterator it = CollectionsKt.asReversedMutable(getAllScopes()).iterator();
        while (it.hasNext()) {
            IrElement irElement = ((ScopeWithIr) it.next()).getIrElement();
            if (Intrinsics.areEqual(irElement, irClass)) {
                return true;
            }
            if (irElement instanceof IrClass) {
                IrClass irClass2 = (IrClass) irElement;
                if (!irClass2.getIsInner() && !IrUtilsKt.isAnonymousObject(irClass2)) {
                    return false;
                }
            } else if (irElement instanceof IrField) {
                if (((IrField) irElement).getIsStatic()) {
                    return false;
                }
            } else if (irElement instanceof IrFunction) {
                IrFunction irFunction = (IrFunction) irElement;
                if (irFunction.getDispatchReceiverParameter() == null && !Intrinsics.areEqual(irFunction.getVisibility(), DescriptorVisibilities.LOCAL)) {
                    break;
                }
            } else {
                continue;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetObjectValue(IrGetObjectValue expression) {
        IrField privateFieldForObjectInstance;
        Intrinsics.checkNotNullParameter(expression, "expression");
        List<ScopeWithIr> allScopes = getAllScopes();
        if ((allScopes instanceof Collection) && allScopes.isEmpty()) {
            privateFieldForObjectInstance = this.context.getCachedDeclarations().getFieldForObjectInstance(expression.getSymbol().getOwner());
        } else {
            Iterator<T> it = allScopes.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((ScopeWithIr) it.next()).getIrElement(), expression.getSymbol().getOwner())) {
                    privateFieldForObjectInstance = this.context.getCachedDeclarations().getPrivateFieldForObjectInstance(expression.getSymbol().getOwner());
                }
            }
            privateFieldForObjectInstance = this.context.getCachedDeclarations().getFieldForObjectInstance(expression.getSymbol().getOwner());
        }
        return new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), privateFieldForObjectInstance.getSymbol(), expression.getType(), null, null, 48, null);
    }

    static /* synthetic */ IrValueParameter thisOfClass$default(SingletonReferencesLowering singletonReferencesLowering, IrClass irClass, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return singletonReferencesLowering.thisOfClass(irClass, z);
    }

    private final IrValueParameter thisOfClass(IrClass declaration, boolean allowConstructorReceiver) {
        if (declaration == null) {
            return null;
        }
        Iterator it = CollectionsKt.reversed(getAllScopes()).iterator();
        while (it.hasNext()) {
            IrElement irElement = ((ScopeWithIr) it.next()).getIrElement();
            if (irElement instanceof IrFunction) {
                IrValueParameter dispatchReceiverParameter = ((IrFunction) irElement).getDispatchReceiverParameter();
                if (dispatchReceiverParameter != null && Intrinsics.areEqual(IrTypesKt.getClassOrNull(dispatchReceiverParameter.getType()), declaration.getSymbol())) {
                    return dispatchReceiverParameter;
                }
            } else if ((irElement instanceof IrClass) && allowConstructorReceiver && Intrinsics.areEqual(irElement, declaration)) {
                return ((IrClass) irElement).getThisReceiver();
            }
        }
        return null;
    }

    private final boolean isVisitingSuperConstructor(IrClass irClass) {
        return Intrinsics.areEqual(irClass.getParent(), CollectionsKt.lastOrNull((List) this.constructingEnums));
    }
}
