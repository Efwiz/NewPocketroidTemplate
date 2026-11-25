package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: importedFromObject.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\b\u0010\u0005\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00038DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0005\u001a\u0004\u0018\u00018\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ImportedFromObjectCallableDescriptor;", "TCallable", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "callableFromObject", "originalOrNull", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)V", "_original", "get_original", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getCallableFromObject", "()Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "containingObject", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getContainingObject", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ImportedFromObjectCallableDescriptor<TCallable extends CallableMemberDescriptor> implements CallableDescriptor {
    private final TCallable callableFromObject;
    private final ClassDescriptor containingObject;
    private final TCallable originalOrNull;

    public ImportedFromObjectCallableDescriptor(TCallable callableFromObject, TCallable tcallable) {
        Intrinsics.checkNotNullParameter(callableFromObject, "callableFromObject");
        this.callableFromObject = callableFromObject;
        this.originalOrNull = tcallable;
        DeclarationDescriptor containingDeclaration = callableFromObject.getModuleDescriptor();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        this.containingObject = (ClassDescriptor) containingDeclaration;
    }

    public final TCallable getCallableFromObject() {
        return this.callableFromObject;
    }

    public final ClassDescriptor getContainingObject() {
        return this.containingObject;
    }

    protected final CallableDescriptor get_original() {
        TCallable tcallable = this.originalOrNull;
        return tcallable != null ? tcallable : this;
    }
}
