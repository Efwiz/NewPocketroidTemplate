package com.artemis;

import com.artemis.annotations.SkipWire;
import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
@SkipWire
public class AspectSubscriptionManager extends BaseSystem {
    private final Bag<EntitySubscription> subscriptions = new Bag<>(EntitySubscription.class);
    private final IntBag changed = new IntBag();
    private final IntBag deleted = new IntBag();
    private final Map<Aspect.Builder, EntitySubscription> subscriptionMap = new HashMap();

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
    }

    protected AspectSubscriptionManager() {
    }

    @Override // com.artemis.BaseSystem
    protected void setWorld(World world) {
        super.setWorld(world);
        get(Aspect.all());
    }

    public EntitySubscription get(Aspect.Builder builder) {
        EntitySubscription entitySubscription = this.subscriptionMap.get(builder);
        return entitySubscription != null ? entitySubscription : createSubscription(builder);
    }

    private EntitySubscription createSubscription(Aspect.Builder builder) {
        EntitySubscription entitySubscription = new EntitySubscription(this.world, builder);
        this.subscriptionMap.put(builder, entitySubscription);
        this.subscriptions.add(entitySubscription);
        this.world.getComponentManager().synchronize(entitySubscription);
        return entitySubscription;
    }

    void process(BitVector bitVector, BitVector bitVector2) {
        toEntityIntBags(bitVector, bitVector2);
        this.subscriptions.get(0).processAll(this.changed, this.deleted);
        int size = this.subscriptions.size();
        for (int i = 1; size > i; i++) {
            this.subscriptions.get(i).process(this.changed, this.deleted);
        }
    }

    private void toEntityIntBags(BitVector bitVector, BitVector bitVector2) {
        bitVector.toIntBagIdCid(this.world.getComponentManager(), this.changed);
        bitVector2.toIntBag(this.deleted);
        bitVector.clear();
        bitVector2.clear();
    }

    void processComponentIdentity(int i, BitVector bitVector) {
        int size = this.subscriptions.size();
        for (int i2 = 0; size > i2; i2++) {
            this.subscriptions.get(i2).processComponentIdentity(i, bitVector);
        }
    }

    public ImmutableBag<EntitySubscription> getSubscriptions() {
        return this.subscriptions;
    }
}
