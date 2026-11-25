package com.artemis.managers;

import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.utils.Bag;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: classes8.dex */
public class UuidEntityManager extends Manager {
    private final Map<UUID, Entity> uuidToEntity = new HashMap();
    private final Bag<UUID> entityToUuid = new Bag<>();

    @Override // com.artemis.Manager
    public void deleted(Entity entity) {
        UUID uuidSafeGet = this.entityToUuid.safeGet(entity.getId());
        if (uuidSafeGet == null) {
            return;
        }
        Entity entity2 = this.uuidToEntity.get(uuidSafeGet);
        if (entity2 != null && entity2.equals(entity)) {
            this.uuidToEntity.remove(uuidSafeGet);
        }
        this.entityToUuid.set(entity.getId(), null);
    }

    public void updatedUuid(Entity entity, UUID uuid) {
        setUuid(entity, uuid);
    }

    public Entity getEntity(UUID uuid) {
        return this.uuidToEntity.get(uuid);
    }

    public UUID getUuid(Entity entity) {
        UUID uuidSafeGet = this.entityToUuid.safeGet(entity.getId());
        if (uuidSafeGet != null) {
            return uuidSafeGet;
        }
        UUID uuidRandomUUID = UUID.randomUUID();
        setUuid(entity, uuidRandomUUID);
        return uuidRandomUUID;
    }

    public void setUuid(Entity entity, UUID uuid) {
        UUID uuidSafeGet = this.entityToUuid.safeGet(entity.getId());
        if (uuidSafeGet != null) {
            this.uuidToEntity.remove(uuidSafeGet);
        }
        this.uuidToEntity.put(uuid, entity);
        this.entityToUuid.set(entity.getId(), uuid);
    }
}
