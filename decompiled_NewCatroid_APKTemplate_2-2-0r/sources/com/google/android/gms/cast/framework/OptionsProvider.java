package com.google.android.gms.cast.framework;

import android.content.Context;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public interface OptionsProvider {
    List<SessionProvider> getAdditionalSessionProviders(Context context);

    CastOptions getCastOptions(Context context);
}
