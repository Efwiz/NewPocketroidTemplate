package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ModuleCapability.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class ModuleCapability<T> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f298name;

    public ModuleCapability(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.f298name = name2;
    }

    public String toString() {
        return this.f298name;
    }
}
