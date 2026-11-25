package com.artemis.link;

import com.artemis.ComponentType;
import com.artemis.World;
import com.artemis.annotations.LinkPolicy;
import com.artemis.utils.IntBag;
import com.artemis.utils.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
class UniLinkSite extends LinkSite {
    UniFieldMutator fieldMutator;
    private final IntBag sourceToTarget;

    protected UniLinkSite(World world, ComponentType componentType, Field field) {
        super(world, componentType, field, LinkPolicy.Policy.CHECK_SOURCE_AND_TARGETS);
        this.sourceToTarget = new IntBag();
    }

    @Override // com.artemis.link.LinkSite
    protected void check(int i) {
        int i2 = this.fieldMutator.read(this.mapper.get(i), this.field);
        if (i2 != -1 && !this.activeEntityIds.unsafeGet(i2)) {
            this.fieldMutator.write(-1, this.mapper.get(i), this.field);
            i2 = -1;
        }
        if (i2 != this.sourceToTarget.get(i)) {
            if (this.listener != null) {
                fireLinkListener(i, i2);
            }
            this.sourceToTarget.set(i, i2);
        }
    }

    private void fireLinkListener(int i, int i2) {
        int i3 = this.sourceToTarget.get(i);
        if (i3 == -1) {
            this.listener.onLinkEstablished(i, i2);
        } else if (i2 != -1) {
            this.listener.onTargetChanged(i, i2, i3);
        } else {
            this.listener.onTargetDead(i, i3);
        }
    }

    @Override // com.artemis.link.LinkSite
    protected void insert(int i) {
        int i2 = this.fieldMutator.read(this.mapper.get(i), this.field);
        this.sourceToTarget.set(i, i2);
        if (i2 == -1 || this.listener == null) {
            return;
        }
        this.listener.onLinkEstablished(i, i2);
    }

    @Override // com.artemis.link.LinkSite
    protected void removed(int i) {
        int i2 = this.sourceToTarget.size() > i ? this.sourceToTarget.get(i) : -1;
        if (i2 != -1) {
            this.sourceToTarget.set(i, -1);
        }
        if (this.listener != null) {
            this.listener.onLinkKilled(i, i2);
        }
    }
}
