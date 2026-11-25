package org.jetbrains.kotlin.fir.resolve;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitDispatchReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;

/* JADX INFO: compiled from: ImplicitReceiverStack.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\b&\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0019\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H¦\u0002J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\"\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0016\u0010\t\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u000b0\nH&J\u0012\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\rH&¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "()V", "get", "name", "", "lastDispatchReceiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitDispatchReceiverValue;", "lookupCondition", "Lkotlin/Function1;", "", "receiversAsReversed", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ImplicitReceiverStack implements Iterable<ImplicitReceiverValue<?>>, KMappedMarker {
    public abstract ImplicitReceiverValue<?> get(String name2);

    @Override // java.lang.Iterable
    public Iterator<ImplicitReceiverValue<?>> iterator() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public abstract ImplicitDispatchReceiverValue lastDispatchReceiver();

    public abstract ImplicitDispatchReceiverValue lastDispatchReceiver(Function1<? super ImplicitReceiverValue<?>, Boolean> lookupCondition);

    public abstract List<ImplicitReceiverValue<?>> receiversAsReversed();
}
