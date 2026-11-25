package org.jetbrains.kotlin.types.typesApproximation;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CapturedTypeApproximation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\n\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0007J\u000e\u0010\u000b\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u0007J(\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\rJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\t\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/types/typesApproximation/ApproximationBounds;", ExifInterface.GPS_DIRECTION_TRUE, "", "lower", "upper", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getLower", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getUpper", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/types/typesApproximation/ApproximationBounds;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ApproximationBounds<T> {
    private final T lower;
    private final T upper;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ApproximationBounds copy$default(ApproximationBounds approximationBounds, Object obj, Object obj2, int i, Object obj3) {
        if ((i & 1) != 0) {
            obj = approximationBounds.lower;
        }
        if ((i & 2) != 0) {
            obj2 = approximationBounds.upper;
        }
        return approximationBounds.copy(obj, obj2);
    }

    public final T component1() {
        return this.lower;
    }

    public final T component2() {
        return this.upper;
    }

    public final ApproximationBounds<T> copy(T lower, T upper) {
        return new ApproximationBounds<>(lower, upper);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ApproximationBounds)) {
            return false;
        }
        ApproximationBounds approximationBounds = (ApproximationBounds) other;
        return Intrinsics.areEqual(this.lower, approximationBounds.lower) && Intrinsics.areEqual(this.upper, approximationBounds.upper);
    }

    public int hashCode() {
        T t = this.lower;
        int iHashCode = (t == null ? 0 : t.hashCode()) * 31;
        T t2 = this.upper;
        return iHashCode + (t2 != null ? t2.hashCode() : 0);
    }

    public String toString() {
        return "ApproximationBounds(lower=" + this.lower + ", upper=" + this.upper + ')';
    }

    public ApproximationBounds(T t, T t2) {
        this.lower = t;
        this.upper = t2;
    }

    public final T getLower() {
        return this.lower;
    }

    public final T getUpper() {
        return this.upper;
    }
}
