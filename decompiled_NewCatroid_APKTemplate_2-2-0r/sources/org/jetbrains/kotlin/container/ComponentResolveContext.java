package org.jetbrains.kotlin.container;

import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Resolve.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0007J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/container/ComponentResolveContext;", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "requestingDescriptor", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", "parentContext", "(Lorg/jetbrains/kotlin/container/StorageComponentContainer;Lorg/jetbrains/kotlin/container/ValueDescriptor;Lorg/jetbrains/kotlin/container/ValueResolveContext;)V", "getContainer", "()Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "getParentContext", "()Lorg/jetbrains/kotlin/container/ValueResolveContext;", "getRequestingDescriptor", "()Lorg/jetbrains/kotlin/container/ValueDescriptor;", "resolve", "registration", "Ljava/lang/reflect/Type;", "toString", ""}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComponentResolveContext implements ValueResolveContext {
    private final StorageComponentContainer container;
    private final ValueResolveContext parentContext;
    private final ValueDescriptor requestingDescriptor;

    public ComponentResolveContext(StorageComponentContainer container, ValueDescriptor requestingDescriptor, ValueResolveContext valueResolveContext) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(requestingDescriptor, "requestingDescriptor");
        this.container = container;
        this.requestingDescriptor = requestingDescriptor;
        this.parentContext = valueResolveContext;
    }

    public /* synthetic */ ComponentResolveContext(StorageComponentContainer storageComponentContainer, ValueDescriptor valueDescriptor, ValueResolveContext valueResolveContext, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(storageComponentContainer, valueDescriptor, (i & 4) != 0 ? null : valueResolveContext);
    }

    public final StorageComponentContainer getContainer() {
        return this.container;
    }

    public final ValueDescriptor getRequestingDescriptor() {
        return this.requestingDescriptor;
    }

    public final ValueResolveContext getParentContext() {
        return this.parentContext;
    }

    @Override // org.jetbrains.kotlin.container.ValueResolveContext
    public ValueDescriptor resolve(Type registration) {
        Intrinsics.checkNotNullParameter(registration, "registration");
        ValueDescriptor valueDescriptorResolve = this.container.resolve(registration, this);
        if (valueDescriptorResolve != null) {
            return valueDescriptorResolve;
        }
        ValueResolveContext valueResolveContext = this.parentContext;
        if (valueResolveContext != null) {
            return valueResolveContext.resolve(registration);
        }
        return null;
    }

    public String toString() {
        return "for " + this.requestingDescriptor + " in " + this.container;
    }
}
