package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: ImplicitClassReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0000H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u000e\u0010\u0005\u001a\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitClassReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ThisClassReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitReceiver;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "original", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitClassReceiver;)V", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "declarationDescriptor", "getDeclarationDescriptor", Namer.EQUALS_METHOD_NAME, "", "other", "", "getOriginal", "getType", "Lorg/jetbrains/kotlin/types/SimpleType;", "hashCode", "", "replaceType", "", "newType", "Lorg/jetbrains/kotlin/types/KotlinType;", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ImplicitClassReceiver implements ImplicitReceiver, ThisClassReceiver {
    private final ClassDescriptor classDescriptor;
    private final ClassDescriptor declarationDescriptor;
    private final ImplicitClassReceiver original;

    public ImplicitClassReceiver(ClassDescriptor classDescriptor, ImplicitClassReceiver implicitClassReceiver) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        this.classDescriptor = classDescriptor;
        this.original = implicitClassReceiver == null ? this : implicitClassReceiver;
        this.declarationDescriptor = classDescriptor;
    }

    public /* synthetic */ ImplicitClassReceiver(ClassDescriptor classDescriptor, ImplicitClassReceiver implicitClassReceiver, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(classDescriptor, (i & 2) != 0 ? null : implicitClassReceiver);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    /* JADX INFO: renamed from: replaceType, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ReceiverValue mo7466replaceType(KotlinType kotlinType) {
        return (ReceiverValue) replaceType(kotlinType);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ThisClassReceiver
    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    public SimpleType getType() {
        SimpleType defaultType = this.classDescriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return defaultType;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver
    public ClassDescriptor getDeclarationDescriptor() {
        return this.declarationDescriptor;
    }

    public boolean equals(Object other) {
        ClassDescriptor classDescriptor = this.classDescriptor;
        ImplicitClassReceiver implicitClassReceiver = other instanceof ImplicitClassReceiver ? (ImplicitClassReceiver) other : null;
        return Intrinsics.areEqual(classDescriptor, implicitClassReceiver != null ? implicitClassReceiver.classDescriptor : null);
    }

    public int hashCode() {
        return this.classDescriptor.hashCode();
    }

    public String toString() {
        return "Class{" + getType() + '}';
    }

    public Void replaceType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        throw new UnsupportedOperationException("Replace type should not be called for this receiver");
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    public ImplicitClassReceiver getOriginal() {
        return this.original;
    }
}
