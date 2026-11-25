package org.jetbrains.kotlin.daemon.common;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty1;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0016\b\u0002\u0010\u0003 \u0001*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00042\u00020\u0005Bs\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0002\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u00010\f\u0012\u0016\b\u0002\u0010\r\u001a\u0010\u0012\u0004\u0012\u00028\u0001\u0012\u0006\u0012\u0004\u0018\u00010\n0\f\u0012\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u000f0\f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u0011J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\nH\u0016J\u0018\u0010#\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0002\u0010$\u001a\u00020\nH\u0016R\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u0007\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u000f0\f¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u001f\u0010\r\u001a\u0010\u0012\u0004\u0012\u00028\u0001\u0012\u0006\u0012\u0004\u0018\u00010\n0\f¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "C", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "P", "Lkotlin/reflect/KMutableProperty1;", "", "dest", "prop", "names", "", "", "fromString", "Lkotlin/Function1;", "toString", "skipIf", "", "mergeDelimiter", "(Ljava/lang/Object;Lkotlin/reflect/KMutableProperty1;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Ljava/lang/String;)V", "getDest", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getFromString", "()Lkotlin/jvm/functions/Function1;", "getMergeDelimiter", "()Ljava/lang/String;", "getNames", "()Ljava/util/List;", "getProp", "()Lkotlin/reflect/KMutableProperty1;", "Lkotlin/reflect/KMutableProperty1;", "getSkipIf", "getToString", "apply", "", "s", "toArgs", "prefix", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class PropMapper<C, V, P extends KMutableProperty1<C, V>> {
    private final C dest;
    private final Function1<String, V> fromString;
    private final String mergeDelimiter;
    private final List<String> names;
    private final P prop;
    private final Function1<V, Boolean> skipIf;
    private final Function1<V, String> toString;

    /* JADX WARN: Multi-variable type inference failed */
    public PropMapper(C c, P prop, List<String> names, Function1<? super String, ? extends V> fromString, Function1<? super V, String> toString, Function1<? super V, Boolean> skipIf, String str) {
        Intrinsics.checkNotNullParameter(prop, "prop");
        Intrinsics.checkNotNullParameter(names, "names");
        Intrinsics.checkNotNullParameter(fromString, "fromString");
        Intrinsics.checkNotNullParameter(toString, "toString");
        Intrinsics.checkNotNullParameter(skipIf, "skipIf");
        this.dest = c;
        this.prop = prop;
        this.names = names;
        this.fromString = fromString;
        this.toString = toString;
        this.skipIf = skipIf;
        this.mergeDelimiter = str;
    }

    public final C getDest() {
        return this.dest;
    }

    public final P getProp() {
        return this.prop;
    }

    public /* synthetic */ PropMapper(Object obj, KMutableProperty1 kMutableProperty1, List list, Function1 function1, Function1 function2, Function1 function3, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, kMutableProperty1, (i & 4) != 0 ? CollectionsKt.listOf(kMutableProperty1.getName()) : list, function1, (i & 16) != 0 ? new Function1<V, String>() { // from class: org.jetbrains.kotlin.daemon.common.PropMapper.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(V v) {
                return String.valueOf(v);
            }
        } : function2, (i & 32) != 0 ? new Function1<V, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.PropMapper.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(V v) {
                return false;
            }
        } : function3, (i & 64) != 0 ? null : str);
    }

    public final List<String> getNames() {
        return this.names;
    }

    public final Function1<String, V> getFromString() {
        return this.fromString;
    }

    public final Function1<V, String> getToString() {
        return this.toString;
    }

    public final Function1<V, Boolean> getSkipIf() {
        return this.skipIf;
    }

    public final String getMergeDelimiter() {
        return this.mergeDelimiter;
    }

    public static /* synthetic */ List toArgs$default(PropMapper propMapper, String str, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toArgs");
        }
        if ((i & 1) != 0) {
            str = DaemonParamsKt.COMPILE_DAEMON_CMDLINE_OPTIONS_PREFIX;
        }
        return propMapper.toArgs(str);
    }

    public List<String> toArgs(String prefix) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (this.skipIf.invoke((V) this.prop.get(this.dest)).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        return this.mergeDelimiter != null ? CollectionsKt.listOf(CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) new String[]{prefix + ((String) CollectionsKt.first((List) this.names)), this.toString.invoke((V) this.prop.get(this.dest))}), this.mergeDelimiter, null, null, 0, null, null, 62, null)) : CollectionsKt.listOfNotNull((Object[]) new String[]{prefix + ((String) CollectionsKt.first((List) this.names)), this.toString.invoke((V) this.prop.get(this.dest))});
    }

    public void apply(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        this.prop.set(this.dest, this.fromString.invoke(s));
    }
}
