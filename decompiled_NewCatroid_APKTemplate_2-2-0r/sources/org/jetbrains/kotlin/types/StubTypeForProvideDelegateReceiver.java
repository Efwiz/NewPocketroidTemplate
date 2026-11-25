package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor;
import org.jetbrains.kotlin.types.error.ErrorTypeConstructor;

/* JADX INFO: compiled from: StubTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0005H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/types/StubTypeForProvideDelegateReceiver;", "Lorg/jetbrains/kotlin/types/AbstractStubType;", "originalTypeVariable", "Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;", "isMarkedNullable", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "(Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;ZLorg/jetbrains/kotlin/types/TypeConstructor;)V", "getConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "materialize", "newNullability", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StubTypeForProvideDelegateReceiver extends AbstractStubType {
    private final TypeConstructor constructor;

    public /* synthetic */ StubTypeForProvideDelegateReceiver(NewTypeVariableConstructor newTypeVariableConstructor, boolean z, ErrorTypeConstructor errorTypeConstructor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(newTypeVariableConstructor, z, (i & 4) != 0 ? AbstractStubType.INSTANCE.createConstructor(newTypeVariableConstructor) : errorTypeConstructor);
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeConstructor getConstructor() {
        return this.constructor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StubTypeForProvideDelegateReceiver(NewTypeVariableConstructor originalTypeVariable, boolean z, TypeConstructor constructor) {
        super(originalTypeVariable, z);
        Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        this.constructor = constructor;
    }

    @Override // org.jetbrains.kotlin.types.AbstractStubType
    public StubTypeForProvideDelegateReceiver materialize(boolean newNullability) {
        return new StubTypeForProvideDelegateReceiver(getOriginalTypeVariable(), newNullability, getConstructor());
    }

    @Override // org.jetbrains.kotlin.types.SimpleType
    public String toString() {
        return "Stub (delegation): " + getOriginalTypeVariable() + (getIsMarkedNullable() ? TypeDescription.Generic.OfWildcardType.SYMBOL : "");
    }
}
