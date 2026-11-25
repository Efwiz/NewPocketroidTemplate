package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.artemis.utils.ImmutableBag;

/* JADX INFO: loaded from: classes8.dex */
public abstract class SystemInvocationStrategy {
    protected final BitVector disabled = new BitVector();
    protected Bag<BaseSystem> systems;
    protected World world;

    protected void initialize() {
    }

    protected abstract void process();

    protected final void setWorld(World world) {
        this.world = world;
    }

    protected void setSystems(Bag<BaseSystem> bag) {
        this.systems = bag;
    }

    protected final void updateEntityStates() {
        this.world.batchProcessor.update();
    }

    @Deprecated
    protected final void process(Bag<BaseSystem> bag) {
        throw new RuntimeException("wrong process method");
    }

    public boolean isEnabled(BaseSystem baseSystem) {
        Class<?> cls = baseSystem.getClass();
        ImmutableBag<BaseSystem> systems = this.world.getSystems();
        for (int i = 0; i < systems.size(); i++) {
            if (cls == systems.get(i).getClass()) {
                return !this.disabled.get(i);
            }
        }
        throw new RuntimeException("huh?");
    }

    public void setEnabled(BaseSystem baseSystem, boolean z) {
        Class<?> cls = baseSystem.getClass();
        ImmutableBag<BaseSystem> systems = this.world.getSystems();
        for (int i = 0; i < systems.size(); i++) {
            if (cls == systems.get(i).getClass()) {
                this.disabled.set(i, !z);
            }
        }
    }
}
