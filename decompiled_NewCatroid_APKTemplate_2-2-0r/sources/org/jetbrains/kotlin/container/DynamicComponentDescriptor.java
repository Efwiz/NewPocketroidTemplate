package org.jetbrains.kotlin.container;

import kotlin.Metadata;

/* JADX INFO: compiled from: Container.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/container/DynamicComponentDescriptor;", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", "()V", "getValue", "", "toString", "", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DynamicComponentDescriptor implements ValueDescriptor {
    public static final DynamicComponentDescriptor INSTANCE = new DynamicComponentDescriptor();

    private DynamicComponentDescriptor() {
    }

    @Override // org.jetbrains.kotlin.container.ValueDescriptor
    public Object getValue() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "Dynamic";
    }
}
