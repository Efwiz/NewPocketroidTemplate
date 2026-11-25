package org.jetbrains.kotlin.container;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Descriptors.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/container/IterableDescriptor;", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", "descriptors", "", "(Ljava/lang/Iterable;)V", "getDescriptors", "()Ljava/lang/Iterable;", "getValue", "", "toString", "", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IterableDescriptor implements ValueDescriptor {
    private final Iterable<ValueDescriptor> descriptors;

    /* JADX WARN: Multi-variable type inference failed */
    public IterableDescriptor(Iterable<? extends ValueDescriptor> descriptors) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        this.descriptors = descriptors;
    }

    public final Iterable<ValueDescriptor> getDescriptors() {
        return this.descriptors;
    }

    @Override // org.jetbrains.kotlin.container.ValueDescriptor
    public Object getValue() {
        Iterable<ValueDescriptor> iterable = this.descriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        Iterator<ValueDescriptor> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getValue());
        }
        return arrayList;
    }

    public String toString() {
        return "Iterable: " + this.descriptors;
    }
}
