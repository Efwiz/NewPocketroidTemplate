package org.jetbrains.kotlin.types;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;

/* JADX INFO: compiled from: FunctionPlaceholders.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000f\u001a\u00020\bH\u0016J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0003H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0006H\u0016J\b\u0010\u0017\u001a\u00020\u0006H\u0016J\u0010\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001aH\u0017J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/types/FunctionPlaceholderTypeConstructor;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "argumentTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "hasDeclaredArguments", "", "kotlinBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Ljava/util/List;ZLorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "getArgumentTypes", "()Ljava/util/List;", "errorTypeConstructor", "getHasDeclaredArguments", "()Z", "getBuiltIns", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getSupertypes", "", "isDenotable", "isFinal", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionPlaceholderTypeConstructor implements TypeConstructor {
    private final List<KotlinType> argumentTypes;
    private final TypeConstructor errorTypeConstructor;
    private final boolean hasDeclaredArguments;
    private final KotlinBuiltIns kotlinBuiltIns;

    /* JADX WARN: Multi-variable type inference failed */
    public FunctionPlaceholderTypeConstructor(List<? extends KotlinType> argumentTypes, boolean z, KotlinBuiltIns kotlinBuiltIns) {
        Intrinsics.checkNotNullParameter(argumentTypes, "argumentTypes");
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "kotlinBuiltIns");
        this.argumentTypes = argumentTypes;
        this.hasDeclaredArguments = z;
        this.kotlinBuiltIns = kotlinBuiltIns;
        this.errorTypeConstructor = org.jetbrains.kotlin.types.error.ErrorUtils.INSTANCE.createErrorTypeConstructor(ErrorTypeKind.FUNCTION_PLACEHOLDER_TYPE, argumentTypes.toString());
    }

    public final List<KotlinType> getArgumentTypes() {
        return this.argumentTypes;
    }

    public final boolean getHasDeclaredArguments() {
        return this.hasDeclaredArguments;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        List<TypeParameterDescriptor> parameters = this.errorTypeConstructor.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        return parameters;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes */
    public Collection<KotlinType> mo7438getSupertypes() {
        Collection<KotlinType> collectionMo7438getSupertypes = this.errorTypeConstructor.mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        return collectionMo7438getSupertypes;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isFinal() {
        return this.errorTypeConstructor.isFinal();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isDenotable() {
        return this.errorTypeConstructor.isDenotable();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public ClassifierDescriptor mo7434getDeclarationDescriptor() {
        return this.errorTypeConstructor.mo7434getDeclarationDescriptor();
    }

    public String toString() {
        return this.errorTypeConstructor.toString();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getBuiltIns, reason: from getter */
    public KotlinBuiltIns getKotlinBuiltIns() {
        return this.kotlinBuiltIns;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }
}
