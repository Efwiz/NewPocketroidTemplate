package org.jetbrains.kotlin.container;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: Components.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/container/DefaultInstanceComponentDescriptor;", "Lorg/jetbrains/kotlin/container/InstanceComponentDescriptor;", "instance", "", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "toString", "", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultInstanceComponentDescriptor extends InstanceComponentDescriptor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultInstanceComponentDescriptor(Object instance) {
        super(instance);
        Intrinsics.checkNotNullParameter(instance, "instance");
    }

    @Override // org.jetbrains.kotlin.container.InstanceComponentDescriptor
    public String toString() {
        return "Default instance: " + getInstance().getClass().getSimpleName();
    }
}
