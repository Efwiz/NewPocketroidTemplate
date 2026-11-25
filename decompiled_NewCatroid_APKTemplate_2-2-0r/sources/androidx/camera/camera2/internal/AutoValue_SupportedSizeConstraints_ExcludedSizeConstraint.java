package androidx.camera.camera2.internal;

import android.util.Range;
import android.util.Size;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class AutoValue_SupportedSizeConstraints_ExcludedSizeConstraint extends SupportedSizeConstraints.ExcludedSizeConstraint {
    private final Range<Integer> affectedApiLevels;
    private final Set<Integer> affectedFormats;
    private final List<Size> excludedSizes;

    AutoValue_SupportedSizeConstraints_ExcludedSizeConstraint(Set<Integer> set, Range<Integer> range, List<Size> list) {
        if (set == null) {
            throw new NullPointerException("Null affectedFormats");
        }
        this.affectedFormats = set;
        if (range == null) {
            throw new NullPointerException("Null affectedApiLevels");
        }
        this.affectedApiLevels = range;
        if (list == null) {
            throw new NullPointerException("Null excludedSizes");
        }
        this.excludedSizes = list;
    }

    @Override // androidx.camera.camera2.internal.SupportedSizeConstraints.ExcludedSizeConstraint, androidx.camera.camera2.internal.SupportedSizeConstraints.Constraint
    public Set<Integer> getAffectedFormats() {
        return this.affectedFormats;
    }

    @Override // androidx.camera.camera2.internal.SupportedSizeConstraints.ExcludedSizeConstraint, androidx.camera.camera2.internal.SupportedSizeConstraints.Constraint
    public Range<Integer> getAffectedApiLevels() {
        return this.affectedApiLevels;
    }

    @Override // androidx.camera.camera2.internal.SupportedSizeConstraints.ExcludedSizeConstraint
    public List<Size> getExcludedSizes() {
        return this.excludedSizes;
    }

    public String toString() {
        return "ExcludedSizeConstraint{affectedFormats=" + this.affectedFormats + ", affectedApiLevels=" + this.affectedApiLevels + ", excludedSizes=" + this.excludedSizes + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SupportedSizeConstraints.ExcludedSizeConstraint)) {
            return false;
        }
        SupportedSizeConstraints.ExcludedSizeConstraint excludedSizeConstraint = (SupportedSizeConstraints.ExcludedSizeConstraint) obj;
        return this.affectedFormats.equals(excludedSizeConstraint.getAffectedFormats()) && this.affectedApiLevels.equals(excludedSizeConstraint.getAffectedApiLevels()) && this.excludedSizes.equals(excludedSizeConstraint.getExcludedSizes());
    }

    public int hashCode() {
        return ((((this.affectedFormats.hashCode() ^ 1000003) * 1000003) ^ this.affectedApiLevels.hashCode()) * 1000003) ^ this.excludedSizes.hashCode();
    }
}
