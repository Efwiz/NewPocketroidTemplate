package com.artemis;

import com.artemis.annotations.SkipWire;

/* JADX INFO: loaded from: classes8.dex */
public abstract class BaseSystem {

    @SkipWire
    protected World world;

    protected void begin() {
    }

    protected boolean checkProcessing() {
        return true;
    }

    protected void dispose() {
    }

    protected void end() {
    }

    protected void initialize() {
    }

    protected abstract void processSystem();

    public final void process() {
        if (checkProcessing()) {
            begin();
            processSystem();
            end();
        }
    }

    public boolean isEnabled() {
        return this.world.invocationStrategy.isEnabled(this);
    }

    public void setEnabled(boolean z) {
        this.world.invocationStrategy.setEnabled(this, z);
    }

    protected void setWorld(World world) {
        this.world = world;
    }

    protected World getWorld() {
        return this.world;
    }
}
