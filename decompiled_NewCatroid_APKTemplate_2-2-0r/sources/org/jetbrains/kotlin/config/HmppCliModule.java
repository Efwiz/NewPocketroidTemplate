package org.jetbrains.kotlin.config;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HmppCliModule.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/config/HmppCliModule;", "", "name", "", "sources", "", "(Ljava/lang/String;Ljava/util/Set;)V", "getName", "()Ljava/lang/String;", "getSources", "()Ljava/util/Set;", "toString", "config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HmppCliModule {
    private final String name;
    private final Set<String> sources;

    public HmppCliModule(String name2, Set<String> sources) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(sources, "sources");
        this.name = name2;
        this.sources = sources;
    }

    public final String getName() {
        return this.name;
    }

    public final Set<String> getSources() {
        return this.sources;
    }

    public String toString() {
        return "Module " + this.name;
    }
}
