package org.objenesis;

import org.objenesis.strategy.SerializingInstantiatorStrategy;

/* JADX INFO: loaded from: classes6.dex */
public class ObjenesisSerializer extends ObjenesisBase {
    public ObjenesisSerializer() {
        super(new SerializingInstantiatorStrategy());
    }

    public ObjenesisSerializer(boolean z) {
        super(new SerializingInstantiatorStrategy(), z);
    }
}
