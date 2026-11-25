package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.values.PointSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/SpawnInfluencer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SpawnInfluencer extends Influencer {
    ParallelArray.FloatChannel positionChannel;
    ParallelArray.FloatChannel rotationChannel;
    public SpawnShapeValue spawnShapeValue;

    public SpawnInfluencer() {
        this.spawnShapeValue = new PointSpawnShapeValue();
    }

    public SpawnInfluencer(SpawnShapeValue spawnShapeValue) {
        this.spawnShapeValue = spawnShapeValue;
    }

    public SpawnInfluencer(SpawnInfluencer spawnInfluencer) {
        this.spawnShapeValue = spawnInfluencer.spawnShapeValue.copy();
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void init() {
        this.spawnShapeValue.init();
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        this.positionChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
        this.rotationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation3D);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void start() {
        this.spawnShapeValue.start();
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void activateParticles(int i, int i2) {
        int i3 = this.positionChannel.strideSize * i;
        int i4 = (this.positionChannel.strideSize * i2) + i3;
        while (i3 < i4) {
            this.spawnShapeValue.spawn(TMP_V1, this.controller.emitter.percent);
            TMP_V1.mul(this.controller.transform);
            this.positionChannel.data[i3] = TMP_V1.x;
            this.positionChannel.data[i3 + 1] = TMP_V1.y;
            this.positionChannel.data[i3 + 2] = TMP_V1.z;
            i3 += this.positionChannel.strideSize;
        }
        int i5 = i * this.rotationChannel.strideSize;
        int i6 = (i2 * this.rotationChannel.strideSize) + i5;
        while (i5 < i6) {
            this.controller.transform.getRotation(TMP_Q, true);
            this.rotationChannel.data[i5] = TMP_Q.x;
            this.rotationChannel.data[i5 + 1] = TMP_Q.y;
            this.rotationChannel.data[i5 + 2] = TMP_Q.z;
            this.rotationChannel.data[i5 + 3] = TMP_Q.w;
            i5 += this.rotationChannel.strideSize;
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public SpawnInfluencer copy() {
        return new SpawnInfluencer(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        json.writeValue("spawnShape", this.spawnShapeValue, SpawnShapeValue.class);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        this.spawnShapeValue = (SpawnShapeValue) json.readValue("spawnShape", SpawnShapeValue.class, jsonValue);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void save(AssetManager assetManager, ResourceData resourceData) {
        this.spawnShapeValue.save(assetManager, resourceData);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void load(AssetManager assetManager, ResourceData resourceData) {
        this.spawnShapeValue.load(assetManager, resourceData);
    }
}
