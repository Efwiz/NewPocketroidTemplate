package com.artemis;

/* JADX INFO: loaded from: classes8.dex */
public class InvocationStrategy extends SystemInvocationStrategy {
    @Override // com.artemis.SystemInvocationStrategy
    protected void process() {
        BaseSystem[] data = this.systems.getData();
        int size = this.systems.size();
        for (int i = 0; size > i; i++) {
            if (!this.disabled.get(i)) {
                updateEntityStates();
                data[i].process();
            }
        }
        updateEntityStates();
    }
}
