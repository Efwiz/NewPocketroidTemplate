package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B#\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0005H\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0001\u0005\u0014\u0015\u0016\u0017\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewTypeVariable;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "name", "", "originalTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Ljava/lang/String;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)V", "defaultType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getDefaultType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "freshTypeConstructor", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableTypeConstructor;", "getFreshTypeConstructor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableTypeConstructor;", "hasOnlyInputTypesAnnotation", "", "toString", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForCallableReferenceParameterType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForCallableReferenceReturnType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForLambdaParameterType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForLambdaReturnType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableFromCallableDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NewTypeVariable implements TypeVariableMarker {
    private final SimpleType defaultType;
    private final TypeVariableTypeConstructor freshTypeConstructor;

    public /* synthetic */ NewTypeVariable(KotlinBuiltIns kotlinBuiltIns, String str, TypeParameterDescriptor typeParameterDescriptor, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinBuiltIns, str, typeParameterDescriptor);
    }

    public abstract boolean hasOnlyInputTypesAnnotation();

    private NewTypeVariable(KotlinBuiltIns kotlinBuiltIns, String str, TypeParameterDescriptor typeParameterDescriptor) {
        TypeVariableTypeConstructor typeVariableTypeConstructor = new TypeVariableTypeConstructor(kotlinBuiltIns, str, typeParameterDescriptor);
        this.freshTypeConstructor = typeVariableTypeConstructor;
        this.defaultType = TypeVariableKt.typeForTypeVariable(typeVariableTypeConstructor);
    }

    public /* synthetic */ NewTypeVariable(KotlinBuiltIns kotlinBuiltIns, String str, TypeParameterDescriptor typeParameterDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinBuiltIns, str, (i & 4) != 0 ? null : typeParameterDescriptor, null);
    }

    public final TypeVariableTypeConstructor getFreshTypeConstructor() {
        return this.freshTypeConstructor;
    }

    public final SimpleType getDefaultType() {
        return this.defaultType;
    }

    public String toString() {
        return this.freshTypeConstructor.toString();
    }
}
