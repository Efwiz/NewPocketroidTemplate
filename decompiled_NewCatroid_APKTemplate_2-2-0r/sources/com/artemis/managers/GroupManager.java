package com.artemis.managers;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.artemis.EntitySubscription;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.ImmutableIntBag;
import com.artemis.utils.IntBag;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class GroupManager extends BaseSystem {
    private static final ImmutableBag<String> EMPTY_BAG = new Bag();
    private final Map<String, Bag<Entity>> entitiesByGroup = new HashMap();
    private final Map<String, IntBag> identitiesByGroup = new HashMap();
    private final Map<Entity, Bag<String>> groupsByEntity = new HashMap();

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
    }

    @Override // com.artemis.BaseSystem
    protected void initialize() {
        this.world.getAspectSubscriptionManager().get(Aspect.all()).addSubscriptionListener(new EntitySubscription.SubscriptionListener() { // from class: com.artemis.managers.GroupManager.1
            @Override // com.artemis.EntitySubscription.SubscriptionListener
            public void inserted(IntBag intBag) {
            }

            @Override // com.artemis.EntitySubscription.SubscriptionListener
            public void removed(IntBag intBag) {
                GroupManager.this.deleted(intBag);
            }
        });
    }

    public void add(Entity entity, String str) {
        Bag<Entity> bag = this.entitiesByGroup.get(str);
        if (bag == null) {
            bag = new Bag<>();
            this.entitiesByGroup.put(str, bag);
        }
        IntBag intBag = this.identitiesByGroup.get(str);
        if (intBag == null) {
            intBag = new IntBag();
            this.identitiesByGroup.put(str, intBag);
        }
        if (!bag.contains(entity)) {
            bag.add(entity);
            intBag.add(entity.getId());
        }
        Bag<String> bag2 = this.groupsByEntity.get(entity);
        if (bag2 == null) {
            bag2 = new Bag<>();
            this.groupsByEntity.put(entity, bag2);
        }
        if (bag2.contains(str)) {
            return;
        }
        bag2.add(str);
    }

    public void add(int i, String str) {
        add(this.world.getEntity(i), str);
    }

    public void add(Entity entity, String str, String str2) {
        add(entity, str);
        add(entity, str2);
    }

    public void add(int i, String str, String str2) {
        add(this.world.getEntity(i), str, str2);
    }

    public void add(Entity entity, String str, String str2, String str3) {
        add(entity, str);
        add(entity, str2);
        add(entity, str3);
    }

    public void add(int i, String str, String str2, String str3) {
        add(this.world.getEntity(i), str, str2, str3);
    }

    public void add(Entity entity, String... strArr) {
        for (String str : strArr) {
            add(entity, str);
        }
    }

    public void add(int i, String... strArr) {
        add(this.world.getEntity(i), strArr);
    }

    public void remove(Entity entity, String str) {
        Bag<Entity> bag = this.entitiesByGroup.get(str);
        if (bag != null) {
            bag.remove(entity);
        }
        IntBag intBag = this.identitiesByGroup.get(str);
        if (intBag != null) {
            intBag.removeValue(entity.getId());
        }
        Bag<String> bag2 = this.groupsByEntity.get(entity);
        if (bag2 != null) {
            bag2.remove(str);
            if (bag2.size() == 0) {
                this.groupsByEntity.remove(entity);
            }
        }
    }

    public void remove(int i, String str) {
        remove(this.world.getEntity(i), str);
    }

    public void remove(Entity entity, String str, String str2) {
        remove(entity, str);
        remove(entity, str2);
    }

    public void remove(int i, String str, String str2) {
        remove(this.world.getEntity(i), str, str2);
    }

    public void remove(Entity entity, String str, String str2, String str3) {
        remove(entity, str);
        remove(entity, str2);
        remove(entity, str3);
    }

    public void remove(int i, String str, String str2, String str3) {
        remove(this.world.getEntity(i), str, str2, str3);
    }

    public void remove(Entity entity, String... strArr) {
        for (String str : strArr) {
            remove(entity, str);
        }
    }

    public void remove(int i, String... strArr) {
        remove(this.world.getEntity(i), strArr);
    }

    public void removeFromAllGroups(Entity entity) {
        Bag<String> bag = this.groupsByEntity.get(entity);
        if (bag == null) {
            return;
        }
        int size = bag.size();
        for (int i = 0; size > i; i++) {
            Bag<Entity> bag2 = this.entitiesByGroup.get(bag.get(i));
            if (bag2 != null) {
                bag2.remove(entity);
            }
            IntBag intBag = this.identitiesByGroup.get(bag.get(i));
            if (intBag != null) {
                intBag.removeValue(entity.getId());
            }
        }
        this.groupsByEntity.remove(entity);
    }

    public void removeFromAllGroups(int i) {
        removeFromAllGroups(this.world.getEntity(i));
    }

    public ImmutableBag<Entity> getEntities(String str) {
        Bag<Entity> bag = this.entitiesByGroup.get(str);
        if (bag != null) {
            return bag;
        }
        Bag<Entity> bag2 = new Bag<>();
        this.entitiesByGroup.put(str, bag2);
        return bag2;
    }

    public ImmutableIntBag getEntityIds(String str) {
        IntBag intBag = this.identitiesByGroup.get(str);
        if (intBag != null) {
            return intBag;
        }
        IntBag intBag2 = new IntBag();
        this.identitiesByGroup.put(str, intBag2);
        return intBag2;
    }

    public ImmutableBag<String> getGroups(Entity entity) {
        Bag<String> bag = this.groupsByEntity.get(entity);
        return bag != null ? bag : EMPTY_BAG;
    }

    public ImmutableBag<String> getGroups(int i) {
        return getGroups(this.world.getEntity(i));
    }

    public boolean isInAnyGroup(Entity entity) {
        return getGroups(entity).size() > 0;
    }

    public boolean isInAnyGroup(int i) {
        return isInAnyGroup(this.world.getEntity(i));
    }

    public boolean isInGroup(Entity entity, String str) {
        Bag<String> bag;
        if (str != null && (bag = this.groupsByEntity.get(entity)) != null) {
            String[] data = bag.getData();
            int size = bag.size();
            for (int i = 0; size > i; i++) {
                if (str.equals(data[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInGroup(int i, String str) {
        return isInGroup(this.world.getEntity(i), str);
    }

    void deleted(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            removeFromAllGroups(this.world.getEntity(data[i]));
        }
    }
}
