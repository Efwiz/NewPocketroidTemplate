package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ModelInfluencer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class ModelInfluencer extends Influencer {
    ParallelArray.ObjectChannel<ModelInstance> modelChannel;
    public Array<Model> models;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ModelInfluencer$Single.class
      classes8.dex
     */
    public static class Single extends ModelInfluencer {
        public Single() {
        }

        public Single(Single single) {
            super(single);
        }

        public Single(Model... modelArr) {
            super(modelArr);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void init() {
            Model modelFirst = this.models.first();
            int i = this.controller.emitter.maxParticleCount;
            for (int i2 = 0; i2 < i; i2++) {
                this.modelChannel.data[i2] = new ModelInstance(modelFirst);
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Single copy() {
            return new Single(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ModelInfluencer$Random.class
      classes8.dex
     */
    public static class Random extends ModelInfluencer {
        ModelInstancePool pool;

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ModelInfluencer$Random$ModelInstancePool.class
          classes8.dex
         */
        private class ModelInstancePool extends Pool<ModelInstance> {
            public ModelInstancePool() {
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.badlogic.gdx.utils.Pool
            public ModelInstance newObject() {
                return new ModelInstance(Random.this.models.random());
            }
        }

        public Random() {
            this.pool = new ModelInstancePool();
        }

        public Random(Random random) {
            super(random);
            this.pool = new ModelInstancePool();
        }

        public Random(Model... modelArr) {
            super(modelArr);
            this.pool = new ModelInstancePool();
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void init() {
            this.pool.clear();
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void activateParticles(int i, int i2) {
            int i3 = i2 + i;
            while (i < i3) {
                this.modelChannel.data[i] = this.pool.obtain();
                i++;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void killParticles(int i, int i2) {
            int i3 = i2 + i;
            while (i < i3) {
                this.pool.free(this.modelChannel.data[i]);
                this.modelChannel.data[i] = null;
                i++;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Random copy() {
            return new Random(this);
        }
    }

    public ModelInfluencer() {
        this.models = new Array<>(true, 1, Model.class);
    }

    public ModelInfluencer(Model... modelArr) {
        this.models = new Array<>(modelArr);
    }

    public ModelInfluencer(ModelInfluencer modelInfluencer) {
        this((Model[]) modelInfluencer.models.toArray(Model.class));
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        this.modelChannel = (ParallelArray.ObjectChannel) this.controller.particles.addChannel(ParticleChannels.ModelInstance);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void save(AssetManager assetManager, ResourceData resourceData) {
        ResourceData.SaveData saveDataCreateSaveData = resourceData.createSaveData();
        Array.ArrayIterator<Model> it = this.models.iterator();
        while (it.hasNext()) {
            saveDataCreateSaveData.saveAsset(assetManager.getAssetFileName(it.next()), Model.class);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void load(AssetManager assetManager, ResourceData resourceData) {
        ResourceData.SaveData saveData = resourceData.getSaveData();
        while (true) {
            AssetDescriptor assetDescriptorLoadAsset = saveData.loadAsset();
            if (assetDescriptorLoadAsset == null) {
                return;
            }
            Model model = (Model) assetManager.get(assetDescriptorLoadAsset);
            if (model == null) {
                throw new RuntimeException("Model is null");
            }
            this.models.add(model);
        }
    }
}
