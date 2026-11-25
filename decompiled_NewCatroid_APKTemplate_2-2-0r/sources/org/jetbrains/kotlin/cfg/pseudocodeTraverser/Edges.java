package org.jetbrains.kotlin.cfg.pseudocodeTraverser;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PseudocodeTraverser.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\n\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0007J\u000e\u0010\u000b\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0007J(\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\rJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\t\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/Edges;", ExifInterface.GPS_DIRECTION_TRUE, "", "incoming", "outgoing", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getIncoming", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getOutgoing", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/Edges;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Edges<T> {
    private final T incoming;
    private final T outgoing;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Edges copy$default(Edges edges, Object obj, Object obj2, int i, Object obj3) {
        if ((i & 1) != 0) {
            obj = edges.incoming;
        }
        if ((i & 2) != 0) {
            obj2 = edges.outgoing;
        }
        return edges.copy(obj, obj2);
    }

    public final T component1() {
        return this.incoming;
    }

    public final T component2() {
        return this.outgoing;
    }

    public final Edges<T> copy(T incoming, T outgoing) {
        return new Edges<>(incoming, outgoing);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Edges)) {
            return false;
        }
        Edges edges = (Edges) other;
        return Intrinsics.areEqual(this.incoming, edges.incoming) && Intrinsics.areEqual(this.outgoing, edges.outgoing);
    }

    public int hashCode() {
        T t = this.incoming;
        int iHashCode = (t == null ? 0 : t.hashCode()) * 31;
        T t2 = this.outgoing;
        return iHashCode + (t2 != null ? t2.hashCode() : 0);
    }

    public String toString() {
        return "Edges(incoming=" + this.incoming + ", outgoing=" + this.outgoing + ')';
    }

    public Edges(T t, T t2) {
        this.incoming = t;
        this.outgoing = t2;
    }

    public final T getIncoming() {
        return this.incoming;
    }

    public final T getOutgoing() {
        return this.outgoing;
    }
}
