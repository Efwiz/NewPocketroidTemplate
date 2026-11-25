package org.catrobat.catroid.utils;

import android.content.Context;
import com.google.android.gms.common.GoogleApiAvailability;
import com.huawei.hms.api.HuaweiApiAvailability;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/MobileServiceAvailability.class
  classes14.dex
 */
/* JADX INFO: compiled from: MobileServiceAvailability.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/utils/MobileServiceAvailability;", "", "googleApiAvailability", "Lcom/google/android/gms/common/GoogleApiAvailability;", "huaweiApiAvailability", "Lcom/huawei/hms/api/HuaweiApiAvailability;", "(Lcom/google/android/gms/common/GoogleApiAvailability;Lcom/huawei/hms/api/HuaweiApiAvailability;)V", "isGmsAvailable", "", "context", "Landroid/content/Context;", "isHmsAvailable", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MobileServiceAvailability {
    private final GoogleApiAvailability googleApiAvailability;
    private final HuaweiApiAvailability huaweiApiAvailability;

    public MobileServiceAvailability(GoogleApiAvailability googleApiAvailability, HuaweiApiAvailability huaweiApiAvailability) {
        Intrinsics.checkNotNullParameter(googleApiAvailability, "googleApiAvailability");
        Intrinsics.checkNotNullParameter(huaweiApiAvailability, "huaweiApiAvailability");
        this.googleApiAvailability = googleApiAvailability;
        this.huaweiApiAvailability = huaweiApiAvailability;
    }

    public final boolean isGmsAvailable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return this.googleApiAvailability.isGooglePlayServicesAvailable(context) == 0;
    }

    public final boolean isHmsAvailable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return this.huaweiApiAvailability.isHuaweiMobileServicesAvailable(context) == 0;
    }
}
