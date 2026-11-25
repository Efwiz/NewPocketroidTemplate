package com.artemis.utils.reflect;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.annotations.One;

/* JADX INFO: loaded from: classes8.dex */
public class SystemMetadata {
    private final Class<? extends BaseSystem> c;

    public SystemMetadata(Class<? extends BaseSystem> cls) {
        this.c = cls;
    }

    public Aspect.Builder getAspect() {
        Aspect.Builder builderAll = Aspect.all();
        All all = (All) ClassReflection.getAnnotation(this.c, All.class);
        if (all != null) {
            builderAll.all(all.value());
        }
        One one = (One) ClassReflection.getAnnotation(this.c, One.class);
        if (one != null) {
            builderAll.one(one.value());
        }
        Exclude exclude = (Exclude) ClassReflection.getAnnotation(this.c, Exclude.class);
        if (exclude != null) {
            builderAll.exclude(exclude.value());
        }
        if (all == null && exclude == null && one == null) {
            return null;
        }
        return builderAll;
    }
}
