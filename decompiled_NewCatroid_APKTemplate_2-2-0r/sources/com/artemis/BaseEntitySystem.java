package com.artemis;

import com.artemis.utils.IntBag;
import com.artemis.utils.reflect.ReflectionUtil;
import com.artemis.utils.reflect.SystemMetadata;

/* JADX INFO: loaded from: classes8.dex */
public abstract class BaseEntitySystem extends BaseSystem implements EntitySubscription.SubscriptionListener {
    private final Aspect.Builder aspectConfiguration;
    protected EntitySubscription subscription;

    protected void inserted(int i) {
    }

    protected void removed(int i) {
    }

    public BaseEntitySystem() {
        this(null);
    }

    public BaseEntitySystem(Aspect.Builder builder) {
        if (builder == null && (builder = new SystemMetadata(getClass()).getAspect()) == null) {
            throw new NullPointerException("Aspect was null and no aspect annotations set on system (@All); to use systems which do not subscribe to entities, extend BaseSystem directly.");
        }
        this.aspectConfiguration = builder;
    }

    @Override // com.artemis.BaseSystem
    protected void setWorld(World world) {
        super.setWorld(world);
        this.subscription = getSubscription();
        if (ReflectionUtil.implementsAnyObserver(this)) {
            this.subscription.addSubscriptionListener(this);
        }
    }

    public EntitySubscription getSubscription() {
        return this.world.getAspectSubscriptionManager().get(this.aspectConfiguration);
    }

    @Override // com.artemis.EntitySubscription.SubscriptionListener
    public void inserted(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            inserted(data[i]);
        }
    }

    public IntBag getEntityIds() {
        return this.subscription.getEntities();
    }

    @Override // com.artemis.EntitySubscription.SubscriptionListener
    public void removed(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            removed(data[i]);
        }
    }
}
