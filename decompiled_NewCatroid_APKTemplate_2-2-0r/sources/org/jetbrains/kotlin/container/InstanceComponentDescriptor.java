package org.jetbrains.kotlin.container;

import java.lang.reflect.Type;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: Components.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016J\b\u0010\u000f\u001a\u00020\u0003H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/container/InstanceComponentDescriptor;", "Lorg/jetbrains/kotlin/container/ComponentDescriptor;", "instance", "", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getInstance", "()Ljava/lang/Object;", "getDependencies", "", "Ljava/lang/Class;", "context", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "getRegistrations", "", "Ljava/lang/reflect/Type;", "getValue", "toString", "", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class InstanceComponentDescriptor implements ComponentDescriptor {
    private final Object instance;

    public InstanceComponentDescriptor(Object instance) {
        Intrinsics.checkNotNullParameter(instance, "instance");
        this.instance = instance;
    }

    public final Object getInstance() {
        return this.instance;
    }

    @Override // org.jetbrains.kotlin.container.ValueDescriptor
    public Object getValue() {
        return this.instance;
    }

    @Override // org.jetbrains.kotlin.container.ComponentDescriptor
    public Iterable<Type> getRegistrations() {
        return CacheKt.getInfo(this.instance.getClass()).getRegistrations();
    }

    @Override // org.jetbrains.kotlin.container.ComponentDescriptor
    public Collection<Class<?>> getDependencies(ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return CollectionsKt.emptyList();
    }

    public String toString() {
        return "Instance: " + this.instance.getClass().getSimpleName();
    }
}
