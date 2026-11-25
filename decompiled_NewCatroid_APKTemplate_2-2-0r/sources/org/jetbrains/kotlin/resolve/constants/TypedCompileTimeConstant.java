package org.jetbrains.kotlin.resolve.constants;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CompileTimeConstant.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B#\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0013\u0010\u0019\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0096\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u001f\u001a\u00020\u0016H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/TypedCompileTimeConstant;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "constantValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "parameters", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "(Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;)V", "getConstantValue", "()Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "hasIntegerLiteralType", "", "getHasIntegerLiteralType", "()Z", "isError", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getParameters", "()Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toConstantValue", "expectedType", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypedCompileTimeConstant<T> implements CompileTimeConstant<T> {
    private final ConstantValue<T> constantValue;
    private final ModuleDescriptor moduleDescriptor;
    private final CompileTimeConstant.Parameters parameters;
    private final KotlinType type;

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean getHasIntegerLiteralType() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TypedCompileTimeConstant(ConstantValue<? extends T> constantValue, ModuleDescriptor moduleDescriptor, CompileTimeConstant.Parameters parameters) {
        Intrinsics.checkNotNullParameter(constantValue, "constantValue");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.constantValue = constantValue;
        this.moduleDescriptor = moduleDescriptor;
        this.parameters = parameters;
        this.type = constantValue.getType(getModuleDescriptor());
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
    public T getValue(KotlinType kotlinType) {
        return (T) CompileTimeConstant.DefaultImpls.getValue(this, kotlinType);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isPure() {
        return CompileTimeConstant.DefaultImpls.isPure(this);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isUnsignedNumberLiteral() {
        return CompileTimeConstant.DefaultImpls.isUnsignedNumberLiteral(this);
    }

    public final ConstantValue<T> getConstantValue() {
        return this.constantValue;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public CompileTimeConstant.Parameters getParameters() {
        return this.parameters;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean isError() {
        return this.constantValue instanceof ErrorValue;
    }

    public final KotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public ConstantValue<T> toConstantValue(KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        return this.constantValue;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TypedCompileTimeConstant)) {
            return false;
        }
        if (isError()) {
            return ((TypedCompileTimeConstant) other).isError();
        }
        TypedCompileTimeConstant typedCompileTimeConstant = (TypedCompileTimeConstant) other;
        if (typedCompileTimeConstant.isError()) {
            return false;
        }
        return Intrinsics.areEqual(this.constantValue.getValue(), typedCompileTimeConstant.constantValue.getValue()) && Intrinsics.areEqual(this.type, typedCompileTimeConstant.type);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
    public int hashCode() {
        if (isError()) {
            return 13;
        }
        T value = this.constantValue.getValue();
        return ((value != null ? value.hashCode() : 0) * 31) + this.type.hashCode();
    }
}
