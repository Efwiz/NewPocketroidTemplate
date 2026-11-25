package com.hcaptcha.sdk;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
abstract class HCaptchaStateListener implements Parcelable {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public abstract void onFailure(HCaptchaException hCaptchaException);

    public void onLoaded() {
    }

    public abstract void onOpen();

    public abstract void onSuccess(String str);

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }
}
