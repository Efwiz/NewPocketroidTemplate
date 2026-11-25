package com.artemis.link;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.ComponentType;
import com.artemis.EntitySubscription;
import com.artemis.World;
import com.artemis.annotations.LinkPolicy;
import com.artemis.utils.BitVector;
import com.artemis.utils.IntBag;
import com.artemis.utils.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
abstract class LinkSite implements EntitySubscription.SubscriptionListener {
    protected final BitVector activeEntityIds;
    protected final Field field;
    protected LinkListener listener;
    protected final ComponentMapper<? extends Component> mapper;
    protected final LinkPolicy.Policy policy;
    protected final EntitySubscription subscription;
    protected final ComponentType type;

    protected abstract void check(int i);

    protected abstract void insert(int i);

    protected abstract void removed(int i);

    protected LinkSite(World world, ComponentType componentType, Field field, LinkPolicy.Policy policy) {
        this.type = componentType;
        this.field = field;
        LinkPolicy.Policy policy2 = LinkFactory.getPolicy(field);
        this.policy = policy2 != null ? policy2 : policy;
        this.mapper = world.getMapper(componentType.getType());
        this.activeEntityIds = world.getAspectSubscriptionManager().get(Aspect.all()).getActiveEntityIds();
        EntitySubscription entitySubscription = world.getAspectSubscriptionManager().get(Aspect.all((Class<? extends Component>[]) new Class[]{componentType.getType()}));
        this.subscription = entitySubscription;
        entitySubscription.addSubscriptionListener(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LinkSite linkSite = (LinkSite) obj;
        return this.type.equals(linkSite.type) && this.field.equals(linkSite.field);
    }

    public int hashCode() {
        return this.type.hashCode() ^ this.field.hashCode();
    }

    @Override // com.artemis.EntitySubscription.SubscriptionListener
    public void inserted(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            insert(data[i]);
        }
    }

    @Override // com.artemis.EntitySubscription.SubscriptionListener
    public void removed(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            removed(data[i]);
        }
    }

    protected void process() {
        if (LinkPolicy.Policy.CHECK_SOURCE_AND_TARGETS != this.policy) {
            return;
        }
        IntBag entities = this.subscription.getEntities();
        int[] data = entities.getData();
        int size = entities.size();
        for (int i = 0; size > i; i++) {
            check(data[i]);
        }
    }
}
