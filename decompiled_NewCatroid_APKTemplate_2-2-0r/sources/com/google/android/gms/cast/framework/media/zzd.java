package com.google.android.gms.cast.framework.media;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.dynamic.IObjectWrapper;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public interface zzd extends IInterface {
    WebImage zze(MediaMetadata mediaMetadata, int i) throws RemoteException;

    WebImage zzf(MediaMetadata mediaMetadata, ImageHints imageHints) throws RemoteException;

    IObjectWrapper zzg() throws RemoteException;
}
