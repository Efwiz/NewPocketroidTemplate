package org.jetbrains.kotlin.daemon.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty1;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0016\b\u0001\u0010\u0002 \u0001*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00040\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00020\u0005B%\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0001\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/BoolPropMapper;", "C", "P", "Lkotlin/reflect/KMutableProperty1;", "", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "dest", "prop", "names", "", "", "(Ljava/lang/Object;Lkotlin/reflect/KMutableProperty1;Ljava/util/List;)V", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BoolPropMapper<C, P extends KMutableProperty1<C, Boolean>> extends PropMapper<C, Boolean, P> {
    public /* synthetic */ BoolPropMapper(Object obj, KMutableProperty1 kMutableProperty1, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, kMutableProperty1, (i & 4) != 0 ? CollectionsKt.emptyList() : list);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BoolPropMapper(final C c, final P prop, List<String> names) {
        super(c, prop, CollectionsKt.any(names) ? names : CollectionsKt.listOf(prop.getName()), new Function1<String, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.BoolPropMapper.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return true;
            }
        }, new Function1<Boolean, String>() { // from class: org.jetbrains.kotlin.daemon.common.BoolPropMapper.2
            public final String invoke(boolean z) {
                return null;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ String invoke(Boolean bool) {
                return invoke(bool.booleanValue());
            }
        }, new Function1<Boolean, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.BoolPropMapper.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            public final Boolean invoke(boolean z) {
                return Boolean.valueOf(!((Boolean) prop.get(c)).booleanValue());
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Boolean bool) {
                return invoke(bool.booleanValue());
            }
        }, null, 64, null);
        Intrinsics.checkNotNullParameter(prop, "prop");
        Intrinsics.checkNotNullParameter(names, "names");
    }
}
