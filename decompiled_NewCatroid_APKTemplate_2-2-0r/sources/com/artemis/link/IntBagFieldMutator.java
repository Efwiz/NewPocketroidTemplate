package com.artemis.link;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.EntitySubscription;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;

/* JADX INFO: loaded from: classes8.dex */
class IntBagFieldMutator implements MultiFieldMutator<IntBag, Component> {
    private EntitySubscription all;
    private final IntBag empty = new IntBag();

    IntBagFieldMutator() {
    }

    @Override // com.artemis.link.MultiFieldMutator
    public void validate(int i, IntBag intBag, LinkListener linkListener) {
        int i2 = 0;
        while (intBag.size() > i2) {
            int i3 = intBag.get(i2);
            if (!this.all.getActiveEntityIds().unsafeGet(i3)) {
                int i4 = i2 - 1;
                intBag.removeIndex(i2);
                if (linkListener != null) {
                    linkListener.onTargetDead(i, i3);
                }
                i2 = i4;
            }
            i2++;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.artemis.link.MultiFieldMutator
    public IntBag read(Component component, Field field) {
        try {
            boolean zIsAccessible = field.isAccessible();
            if (!zIsAccessible) {
                field.setAccessible(true);
            }
            IntBag intBag = (IntBag) field.get(component);
            if (!zIsAccessible) {
                field.setAccessible(false);
            }
            return intBag != null ? intBag : this.empty;
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.artemis.link.MultiFieldMutator
    public void setWorld(World world) {
        this.all = world.getAspectSubscriptionManager().get(Aspect.all());
    }
}
