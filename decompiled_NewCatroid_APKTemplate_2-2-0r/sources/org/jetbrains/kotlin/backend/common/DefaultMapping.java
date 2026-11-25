package org.jetbrains.kotlin.backend.common;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: Mappings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR&\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\tR \u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR \u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\tR \u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR \u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00150\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR \u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\tR \u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\tR \u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\tR \u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\t¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/DefaultMapping;", "Lorg/jetbrains/kotlin/backend/common/Mapping;", "delegateFactory", "Lorg/jetbrains/kotlin/backend/common/DelegateFactory;", "(Lorg/jetbrains/kotlin/backend/common/DelegateFactory;)V", "capturedConstructors", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getCapturedConstructors", "()Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "capturedFields", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getCapturedFields", "defaultArgumentsDispatchFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getDefaultArgumentsDispatchFunction", "defaultArgumentsOriginalFunction", "getDefaultArgumentsOriginalFunction", "functionWithContinuationsToSuspendFunctions", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFunctionWithContinuationsToSuspendFunctions", "inlineClassMemberToStatic", "getInlineClassMemberToStatic", "lateInitFieldToNullableField", "getLateInitFieldToNullableField", "reflectedNameAccessor", "getReflectedNameAccessor", "suspendFunctionToCoroutineConstructor", "getSuspendFunctionToCoroutineConstructor", "suspendFunctionsToFunctionWithContinuations", "getSuspendFunctionsToFunctionWithContinuations", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DefaultMapping implements Mapping {
    private final Mapping.Delegate<IrConstructor, IrConstructor> capturedConstructors;
    private final Mapping.Delegate<IrClass, Collection<IrField>> capturedFields;
    private final Mapping.Delegate<IrFunction, IrFunction> defaultArgumentsDispatchFunction;
    private final Mapping.Delegate<IrFunction, IrFunction> defaultArgumentsOriginalFunction;
    private final Mapping.Delegate<IrSimpleFunction, IrSimpleFunction> functionWithContinuationsToSuspendFunctions;
    private final Mapping.Delegate<IrFunction, IrSimpleFunction> inlineClassMemberToStatic;
    private final Mapping.Delegate<IrField, IrField> lateInitFieldToNullableField;
    private final Mapping.Delegate<IrClass, IrSimpleFunction> reflectedNameAccessor;
    private final Mapping.Delegate<IrFunction, IrConstructor> suspendFunctionToCoroutineConstructor;
    private final Mapping.Delegate<IrSimpleFunction, IrSimpleFunction> suspendFunctionsToFunctionWithContinuations;

    /* JADX WARN: Multi-variable type inference failed */
    public DefaultMapping() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public DefaultMapping(DelegateFactory delegateFactory) {
        Intrinsics.checkNotNullParameter(delegateFactory, "delegateFactory");
        this.defaultArgumentsDispatchFunction = delegateFactory.newDeclarationToDeclarationMapping();
        this.defaultArgumentsOriginalFunction = delegateFactory.newDeclarationToDeclarationMapping();
        this.suspendFunctionToCoroutineConstructor = delegateFactory.newDeclarationToDeclarationMapping();
        this.lateInitFieldToNullableField = delegateFactory.newDeclarationToDeclarationMapping();
        this.inlineClassMemberToStatic = delegateFactory.newDeclarationToDeclarationMapping();
        this.capturedFields = delegateFactory.newDeclarationToDeclarationCollectionMapping();
        this.capturedConstructors = delegateFactory.newDeclarationToDeclarationMapping();
        this.reflectedNameAccessor = delegateFactory.newDeclarationToDeclarationMapping();
        this.suspendFunctionsToFunctionWithContinuations = delegateFactory.newDeclarationToDeclarationMapping();
        this.functionWithContinuationsToSuspendFunctions = delegateFactory.newDeclarationToDeclarationMapping();
    }

    public /* synthetic */ DefaultMapping(DefaultDelegateFactory defaultDelegateFactory, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? DefaultDelegateFactory.INSTANCE : defaultDelegateFactory);
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrFunction, IrFunction> getDefaultArgumentsDispatchFunction() {
        return this.defaultArgumentsDispatchFunction;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrFunction, IrFunction> getDefaultArgumentsOriginalFunction() {
        return this.defaultArgumentsOriginalFunction;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrFunction, IrConstructor> getSuspendFunctionToCoroutineConstructor() {
        return this.suspendFunctionToCoroutineConstructor;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrField, IrField> getLateInitFieldToNullableField() {
        return this.lateInitFieldToNullableField;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrFunction, IrSimpleFunction> getInlineClassMemberToStatic() {
        return this.inlineClassMemberToStatic;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrClass, Collection<IrField>> getCapturedFields() {
        return this.capturedFields;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrConstructor, IrConstructor> getCapturedConstructors() {
        return this.capturedConstructors;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrClass, IrSimpleFunction> getReflectedNameAccessor() {
        return this.reflectedNameAccessor;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrSimpleFunction, IrSimpleFunction> getSuspendFunctionsToFunctionWithContinuations() {
        return this.suspendFunctionsToFunctionWithContinuations;
    }

    @Override // org.jetbrains.kotlin.backend.common.Mapping
    public Mapping.Delegate<IrSimpleFunction, IrSimpleFunction> getFunctionWithContinuationsToSuspendFunctions() {
        return this.functionWithContinuationsToSuspendFunctions;
    }
}
