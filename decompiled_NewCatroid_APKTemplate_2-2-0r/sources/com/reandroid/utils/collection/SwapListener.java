package com.reandroid.utils.collection;

/* JADX INFO: loaded from: classes11.dex */
public interface SwapListener {
    void onSwap(int i, int i2);

    static SwapListener redirectTo(final Swappable swappable) {
        swappable.getClass();
        return new SwapListener() { // from class: com.reandroid.utils.collection.SwapListener$$ExternalSyntheticLambda0
            @Override // com.reandroid.utils.collection.SwapListener
            public final void onSwap(int i, int i2) {
                swappable.swap(i, i2);
            }
        };
    }
}
