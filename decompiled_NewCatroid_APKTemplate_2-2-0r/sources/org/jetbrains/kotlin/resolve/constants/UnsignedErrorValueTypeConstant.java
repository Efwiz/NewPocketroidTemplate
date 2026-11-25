package org.jetbrains.kotlin.resolve.constants;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CompileTimeConstant.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0013\u0010\u0016\u001a\u00020\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/UnsignedErrorValueTypeConstant;", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "", "value", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "parameters", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "(Ljava/lang/Number;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;)V", "errorValue", "Lorg/jetbrains/kotlin/resolve/constants/ErrorValue$ErrorValueWithMessage;", "getErrorValue", "()Lorg/jetbrains/kotlin/resolve/constants/ErrorValue$ErrorValueWithMessage;", "hasIntegerLiteralType", "", "getHasIntegerLiteralType", "()Z", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getParameters", "()Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toConstantValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsignedErrorValueTypeConstant implements CompileTimeConstant<Unit> {
    private final ErrorValue.ErrorValueWithMessage errorValue;
    private final ModuleDescriptor moduleDescriptor;
    private final CompileTimeConstant.Parameters parameters;
    private final Number value;

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getHasIntegerLiteralType() {
        return false;
    }

    public UnsignedErrorValueTypeConstant(Number value, ModuleDescriptor moduleDescriptor, CompileTimeConstant.Parameters parameters) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.value = value;
        this.moduleDescriptor = moduleDescriptor;
        this.parameters = parameters;
        this.errorValue = new ErrorValue.ErrorValueWithMessage("Type cannot be resolved. Please make sure you have the required dependencies for unsigned types in the classpath");
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getCanBeUsedInAnnotations() {
        return CompileTimeConstant.DefaultImpls.getCanBeUsedInAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getUsesNonConstValAsConstant() {
        return CompileTimeConstant.DefaultImpls.getUsesNonConstValAsConstant(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getUsesVariableAsConstant() {
        return CompileTimeConstant.DefaultImpls.getUsesVariableAsConstant(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public /* bridge */ /* synthetic */ Unit getValue(KotlinType kotlinType) {
        getValue2(kotlinType);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: getValue, reason: avoid collision after fix types in other method */
    public void getValue2(KotlinType kotlinType) {
        CompileTimeConstant.DefaultImpls.getValue(this, kotlinType);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isError() {
        return CompileTimeConstant.DefaultImpls.isError(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isPure() {
        return CompileTimeConstant.DefaultImpls.isPure(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isUnsignedNumberLiteral() {
        return CompileTimeConstant.DefaultImpls.isUnsignedNumberLiteral(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public CompileTimeConstant.Parameters getParameters() {
        return this.parameters;
    }

    public final ErrorValue.ErrorValueWithMessage getErrorValue() {
        return this.errorValue;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public ConstantValue<Unit> toConstantValue(KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        return this.errorValue;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean equals(Object other) {
        return (other instanceof UnsignedErrorValueTypeConstant) && Intrinsics.areEqual(this.value, ((UnsignedErrorValueTypeConstant) other).value);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public int hashCode() {
        return this.value.hashCode();
    }
}
