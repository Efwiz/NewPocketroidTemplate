package org.jetbrains.kotlin.config;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HmppCliModule.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0006\u0018\u00002\u00020\u0001B-\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0006¢\u0006\u0002\u0010\u0007R#\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/config/HmppCliModuleStructure;", "", "modules", "", "Lorg/jetbrains/kotlin/config/HmppCliModule;", "dependenciesMap", "", "(Ljava/util/List;Ljava/util/Map;)V", "getDependenciesMap", "()Ljava/util/Map;", "getModules", "()Ljava/util/List;", "config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HmppCliModuleStructure {
    private final Map<HmppCliModule, List<HmppCliModule>> dependenciesMap;
    private final List<HmppCliModule> modules;

    /* JADX WARN: Multi-variable type inference failed */
    public HmppCliModuleStructure(List<HmppCliModule> modules, Map<HmppCliModule, ? extends List<HmppCliModule>> dependenciesMap) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(dependenciesMap, "dependenciesMap");
        this.modules = modules;
        this.dependenciesMap = dependenciesMap;
    }

    public final List<HmppCliModule> getModules() {
        return this.modules;
    }

    public final Map<HmppCliModule, List<HmppCliModule>> getDependenciesMap() {
        return this.dependenciesMap;
    }
}
