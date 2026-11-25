package com.artemis.link;

import com.artemis.ComponentType;
import com.artemis.World;
import com.artemis.annotations.LinkPolicy;
import com.artemis.utils.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
class MultiLinkSite extends LinkSite {
    MultiFieldMutator fieldMutator;

    protected MultiLinkSite(World world, ComponentType componentType, Field field) {
        super(world, componentType, field, LinkPolicy.Policy.CHECK_SOURCE);
    }

    @Override // com.artemis.link.LinkSite
    protected void check(int i) {
        this.fieldMutator.validate(i, this.fieldMutator.read(this.mapper.get(i), this.field), this.listener);
    }

    @Override // com.artemis.link.LinkSite
    protected void insert(int i) {
        if (this.listener != null) {
            this.listener.onLinkEstablished(i, -1);
        }
    }

    @Override // com.artemis.link.LinkSite
    protected void removed(int i) {
        if (this.listener != null) {
            this.listener.onLinkKilled(i, -1);
        }
    }
}
