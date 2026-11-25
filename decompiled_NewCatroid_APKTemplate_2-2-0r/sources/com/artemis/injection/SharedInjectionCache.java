package com.artemis.injection;

/* JADX INFO: loaded from: classes8.dex */
public class SharedInjectionCache extends ThreadLocal<InjectionCache> {
    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.ThreadLocal
    public InjectionCache initialValue() {
        return new InjectionCache();
    }
}
