package androidx.camera.core;

/* JADX INFO: loaded from: classes.dex */
final class AutoValue_ImmutableImageInfo extends ImmutableImageInfo {
    private final int rotationDegrees;
    private final Object tag;
    private final long timestamp;

    AutoValue_ImmutableImageInfo(Object obj, long j, int i) {
        this.tag = obj;
        this.timestamp = j;
        this.rotationDegrees = i;
    }

    @Override // androidx.camera.core.ImmutableImageInfo, androidx.camera.core.ImageInfo
    public Object getTag() {
        return this.tag;
    }

    @Override // androidx.camera.core.ImmutableImageInfo, androidx.camera.core.ImageInfo
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override // androidx.camera.core.ImmutableImageInfo, androidx.camera.core.ImageInfo
    public int getRotationDegrees() {
        return this.rotationDegrees;
    }

    public String toString() {
        return "ImmutableImageInfo{tag=" + this.tag + ", timestamp=" + this.timestamp + ", rotationDegrees=" + this.rotationDegrees + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ImmutableImageInfo)) {
            return false;
        }
        ImmutableImageInfo immutableImageInfo = (ImmutableImageInfo) obj;
        Object obj2 = this.tag;
        if (obj2 != null ? obj2.equals(immutableImageInfo.getTag()) : immutableImageInfo.getTag() == null) {
            if (this.timestamp == immutableImageInfo.getTimestamp() && this.rotationDegrees == immutableImageInfo.getRotationDegrees()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        Object obj = this.tag;
        int iHashCode = obj == null ? 0 : obj.hashCode();
        long j = this.timestamp;
        return ((((iHashCode ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ this.rotationDegrees;
    }
}
