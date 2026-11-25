package org.jetbrains.kotlin.fir.backend;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.ir.declarations.IrClass;

/* JADX INFO: compiled from: Fir2IrLocalClassStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\u0004H\u0086\u0002J\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0005H\u0086\u0002R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrLocalClassStorage;", "", "localClassCache", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Ljava/util/Map;)V", "get", "localClass", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "firClass", "irClass", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrLocalClassStorage {
    private final Map<FirClass, IrClass> localClassCache;

    /* JADX WARN: Multi-variable type inference failed */
    public Fir2IrLocalClassStorage() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public Fir2IrLocalClassStorage(Map<FirClass, IrClass> localClassCache) {
        Intrinsics.checkNotNullParameter(localClassCache, "localClassCache");
        this.localClassCache = localClassCache;
    }

    public /* synthetic */ Fir2IrLocalClassStorage(LinkedHashMap linkedHashMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new LinkedHashMap() : linkedHashMap);
    }

    public final IrClass get(FirClass localClass) {
        Intrinsics.checkNotNullParameter(localClass, "localClass");
        return this.localClassCache.get(localClass);
    }

    public final void set(FirClass firClass, IrClass irClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        this.localClassCache.put(firClass, irClass);
    }
}
