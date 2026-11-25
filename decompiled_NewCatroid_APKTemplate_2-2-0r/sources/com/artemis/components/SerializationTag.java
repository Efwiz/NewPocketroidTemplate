package com.artemis.components;

import com.artemis.PooledComponent;
import com.artemis.annotations.Transient;

/* JADX INFO: loaded from: classes8.dex */
@Transient
public class SerializationTag extends PooledComponent {
    public String tag;

    @Override // com.artemis.PooledComponent
    protected void reset() {
        this.tag = null;
    }
}
