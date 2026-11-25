package com.google.android.gms.internal.cast;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.collection.SimpleArrayMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzee extends AnimatorListenerAdapter {
    private final SimpleArrayMap zza = new SimpleArrayMap();

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.zza.put(animator, true);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        this.zza.put(animator, false);
    }

    protected final boolean zza(Animator animator) {
        return this.zza.containsKey(animator) && ((Boolean) this.zza.get(animator)).booleanValue();
    }
}
