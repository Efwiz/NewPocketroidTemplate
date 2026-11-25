package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Visibility.kt */
/* JADX INFO: loaded from: classes12.dex */
public abstract class Visibility {
    private final boolean isPublicAPI;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f299name;

    public Visibility normalize() {
        return this;
    }

    protected Visibility(String name2, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.f299name = name2;
        this.isPublicAPI = z;
    }

    public final boolean isPublicAPI() {
        return this.isPublicAPI;
    }

    public String getInternalDisplayName() {
        return this.f299name;
    }

    public Integer compareTo(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        return Visibilities.INSTANCE.compareLocal$compiler_common(this, visibility);
    }

    public final String toString() {
        return getInternalDisplayName();
    }
}
