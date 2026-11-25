package androidx.core.os;

import android.os.Parcel;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/ParcelableCompatCreatorCallbacks.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/os/ParcelableCompatCreatorCallbacks.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface ParcelableCompatCreatorCallbacks<T> {
    T createFromParcel(Parcel parcel, ClassLoader classLoader);

    T[] newArray(int i);
}
