package com.artemis.managers;

import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class PlayerManager extends Manager {
    private final Map<Entity, String> playerByEntity = new HashMap();
    private final Map<String, Bag<Entity>> entitiesByPlayer = new HashMap();

    public void setPlayer(Entity entity, String str) {
        this.playerByEntity.put(entity, str);
        Bag<Entity> bag = this.entitiesByPlayer.get(str);
        if (bag == null) {
            bag = new Bag<>();
            this.entitiesByPlayer.put(str, bag);
        }
        bag.add(entity);
    }

    public ImmutableBag<Entity> getEntitiesOfPlayer(String str) {
        Bag<Entity> bag = this.entitiesByPlayer.get(str);
        return bag == null ? new Bag() : bag;
    }

    public void removeFromPlayer(Entity entity) {
        Bag<Entity> bag;
        String str = this.playerByEntity.get(entity);
        if (str == null || (bag = this.entitiesByPlayer.get(str)) == null) {
            return;
        }
        bag.remove(entity);
    }

    public String getPlayer(Entity entity) {
        return this.playerByEntity.get(entity);
    }

    @Override // com.artemis.Manager
    public void deleted(Entity entity) {
        removeFromPlayer(entity);
    }
}
