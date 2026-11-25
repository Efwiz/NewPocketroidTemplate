package android.support.v4.app;

import androidx.core.app.RemoteActionCompat;
import androidx.versionedparcelable.VersionedParcel;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:android/support/v4/app/RemoteActionCompatParcelizer.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:android/support/v4/app/RemoteActionCompatParcelizer.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class RemoteActionCompatParcelizer extends androidx.core.app.RemoteActionCompatParcelizer {
    public static RemoteActionCompat read(VersionedParcel versionedParcel) {
        return androidx.core.app.RemoteActionCompatParcelizer.read(versionedParcel);
    }

    public static void write(RemoteActionCompat remoteActionCompat, VersionedParcel versionedParcel) {
        androidx.core.app.RemoteActionCompatParcelizer.write(remoteActionCompat, versionedParcel);
    }
}
