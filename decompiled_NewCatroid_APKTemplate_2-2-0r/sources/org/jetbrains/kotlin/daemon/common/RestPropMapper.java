package org.jetbrains.kotlin.daemon.common;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty1;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u001c\b\u0001\u0010\u0002 \u0001*\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u0002H\u00020\u0006B\u0015\u0012\u0006\u0010\u0007\u001a\u00028\u0000\u0012\u0006\u0010\b\u001a\u00028\u0001¢\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005J\u0010\u0010\r\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/RestPropMapper;", "C", "P", "Lkotlin/reflect/KMutableProperty1;", "", "", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "dest", "prop", "(Ljava/lang/Object;Lkotlin/reflect/KMutableProperty1;)V", ProductAction.ACTION_ADD, "", "s", "apply", "toArgs", "", "prefix", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RestPropMapper<C, P extends KMutableProperty1<C, Collection<String>>> extends PropMapper<C, Collection<String>, P> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RestPropMapper(C c, P prop) {
        super(c, prop, null, new Function1<String, Collection<String>>() { // from class: org.jetbrains.kotlin.daemon.common.RestPropMapper.1
            @Override // kotlin.jvm.functions.Function1
            public final Collection<String> invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new ArrayList();
            }
        }, new Function1<Collection<String>, String>() { // from class: org.jetbrains.kotlin.daemon.common.RestPropMapper.2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Collection<String> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return null;
            }
        }, null, null, 100, null);
        Intrinsics.checkNotNullParameter(prop, "prop");
    }

    @Override // org.jetbrains.kotlin.daemon.common.PropMapper
    public List<String> toArgs(String prefix) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Iterable iterable = (Iterable) getProp().get(getDest());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(prefix + ((String) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.daemon.common.PropMapper
    public void apply(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        add(s);
    }

    public final void add(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        ((Collection) getProp().get(getDest())).add(s);
    }
}
