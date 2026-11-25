package com.artemis;

import com.artemis.utils.IntBag;
import com.artemis.utils.reflect.ReflectionUtil;

/* JADX INFO: loaded from: classes8.dex */
public abstract class Manager extends BaseSystem {
    private int methodFlags;

    @Override // com.artemis.BaseSystem
    protected final void processSystem() {
    }

    public void added(Entity entity) {
        throw new RuntimeException("I shouldn't be here...");
    }

    public void deleted(Entity entity) {
        throw new RuntimeException("... if it weren't for the tests.");
    }

    @Override // com.artemis.BaseSystem
    protected void setWorld(World world) {
        super.setWorld(world);
        if (ReflectionUtil.implementsObserver(this, "added")) {
            this.methodFlags |= 1;
        }
        if (ReflectionUtil.implementsObserver(this, "deleted")) {
            this.methodFlags |= 2;
        }
    }

    protected void registerManager() {
        this.world.getAspectSubscriptionManager().get(Aspect.all()).addSubscriptionListener(new EntitySubscription.SubscriptionListener() { // from class: com.artemis.Manager.1
            @Override // com.artemis.EntitySubscription.SubscriptionListener
            public void inserted(IntBag intBag) {
                Manager.this.added(intBag);
            }

            @Override // com.artemis.EntitySubscription.SubscriptionListener
            public void removed(IntBag intBag) {
                Manager.this.deleted(intBag);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void added(IntBag intBag) {
        if ((this.methodFlags & 1) == 0) {
            return;
        }
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            added(this.world.getEntity(data[i]));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleted(IntBag intBag) {
        if ((this.methodFlags & 2) == 0) {
            return;
        }
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            deleted(this.world.getEntity(data[i]));
        }
    }
}
