package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor;
import org.jetbrains.kotlin.types.error.ErrorTypeConstructor;
import org.jetbrains.kotlin.types.model.StubTypeMarker;

/* JADX INFO: compiled from: StubTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/types/StubTypeForBuilderInference;", "Lorg/jetbrains/kotlin/types/AbstractStubType;", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "originalTypeVariable", "Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;", "isMarkedNullable", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "(Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;ZLorg/jetbrains/kotlin/types/TypeConstructor;)V", "getConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "materialize", "newNullability", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StubTypeForBuilderInference extends AbstractStubType implements StubTypeMarker {
    private final TypeConstructor constructor;
    private final MemberScope memberScope;

    public /* synthetic */ StubTypeForBuilderInference(NewTypeVariableConstructor newTypeVariableConstructor, boolean z, ErrorTypeConstructor errorTypeConstructor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(newTypeVariableConstructor, z, (i & 4) != 0 ? AbstractStubType.INSTANCE.createConstructor(newTypeVariableConstructor) : errorTypeConstructor);
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeConstructor getConstructor() {
        return this.constructor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StubTypeForBuilderInference(NewTypeVariableConstructor originalTypeVariable, boolean z, TypeConstructor constructor) {
        super(originalTypeVariable, z);
        Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        this.constructor = constructor;
        this.memberScope = originalTypeVariable.getBuiltIns().getAnyType().getMemberScope();
    }

    @Override // org.jetbrains.kotlin.types.AbstractStubType
    public AbstractStubType materialize(boolean newNullability) {
        return new StubTypeForBuilderInference(getOriginalTypeVariable(), newNullability, getConstructor());
    }

    @Override // org.jetbrains.kotlin.types.AbstractStubType, org.jetbrains.kotlin.types.KotlinType
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    @Override // org.jetbrains.kotlin.types.SimpleType
    public String toString() {
        return "Stub (BI): " + getOriginalTypeVariable() + (getIsMarkedNullable() ? TypeDescription.Generic.OfWildcardType.SYMBOL : "");
    }
}
