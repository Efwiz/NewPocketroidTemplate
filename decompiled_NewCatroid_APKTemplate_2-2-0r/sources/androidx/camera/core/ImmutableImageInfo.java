package androidx.camera.core;

/* JADX INFO: loaded from: classes.dex */
abstract class ImmutableImageInfo implements ImageInfo {
    @Override // androidx.camera.core.ImageInfo
    public abstract int getRotationDegrees();

    @Override // androidx.camera.core.ImageInfo
    public abstract Object getTag();

    @Override // androidx.camera.core.ImageInfo
    public abstract long getTimestamp();

    ImmutableImageInfo() {
    }

    public static ImageInfo create(Object obj, long j, int i) {
        return new AutoValue_ImmutableImageInfo(obj, j, i);
    }
}
