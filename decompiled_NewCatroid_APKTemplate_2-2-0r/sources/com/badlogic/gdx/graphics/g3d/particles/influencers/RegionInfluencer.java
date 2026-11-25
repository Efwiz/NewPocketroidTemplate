package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/RegionInfluencer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class RegionInfluencer extends Influencer {
    private static final String ASSET_DATA = "atlasAssetData";
    public String atlasName;
    ParallelArray.FloatChannel regionChannel;
    public Array<AspectTextureRegion> regions;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/RegionInfluencer$Single.class
      classes8.dex
     */
    public static class Single extends RegionInfluencer {
        public Single() {
        }

        public Single(Single single) {
            super(single);
        }

        public Single(TextureRegion textureRegion) {
            super(textureRegion);
        }

        public Single(Texture texture) {
            super(texture);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void init() {
            int i = 0;
            AspectTextureRegion aspectTextureRegion = this.regions.items[0];
            int i2 = this.controller.emitter.maxParticleCount * this.regionChannel.strideSize;
            while (i < i2) {
                this.regionChannel.data[i] = aspectTextureRegion.u;
                this.regionChannel.data[i + 1] = aspectTextureRegion.v;
                this.regionChannel.data[i + 2] = aspectTextureRegion.u2;
                this.regionChannel.data[i + 3] = aspectTextureRegion.v2;
                this.regionChannel.data[i + 4] = 0.5f;
                this.regionChannel.data[i + 5] = aspectTextureRegion.halfInvAspectRatio;
                i += this.regionChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Single copy() {
            return new Single(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/RegionInfluencer$Random.class
      classes8.dex
     */
    public static class Random extends RegionInfluencer {
        public Random() {
        }

        public Random(Random random) {
            super(random);
        }

        public Random(TextureRegion textureRegion) {
            super(textureRegion);
        }

        public Random(Texture texture) {
            super(texture);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void activateParticles(int i, int i2) {
            int i3 = i * this.regionChannel.strideSize;
            int i4 = (i2 * this.regionChannel.strideSize) + i3;
            while (i3 < i4) {
                AspectTextureRegion aspectTextureRegionRandom = this.regions.random();
                this.regionChannel.data[i3] = aspectTextureRegionRandom.u;
                this.regionChannel.data[i3 + 1] = aspectTextureRegionRandom.v;
                this.regionChannel.data[i3 + 2] = aspectTextureRegionRandom.u2;
                this.regionChannel.data[i3 + 3] = aspectTextureRegionRandom.v2;
                this.regionChannel.data[i3 + 4] = 0.5f;
                this.regionChannel.data[i3 + 5] = aspectTextureRegionRandom.halfInvAspectRatio;
                i3 += this.regionChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Random copy() {
            return new Random(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/RegionInfluencer$Animated.class
      classes8.dex
     */
    public static class Animated extends RegionInfluencer {
        ParallelArray.FloatChannel lifeChannel;

        public Animated() {
        }

        public Animated(Animated animated) {
            super(animated);
        }

        public Animated(TextureRegion textureRegion) {
            super(textureRegion);
        }

        public Animated(Texture texture) {
            super(texture);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            this.lifeChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Life);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            int i = this.controller.particles.size * this.regionChannel.strideSize;
            int i2 = 0;
            int i3 = 2;
            while (i2 < i) {
                AspectTextureRegion aspectTextureRegion = this.regions.get((int) (this.lifeChannel.data[i3] * (this.regions.size - 1)));
                this.regionChannel.data[i2] = aspectTextureRegion.u;
                this.regionChannel.data[i2 + 1] = aspectTextureRegion.v;
                this.regionChannel.data[i2 + 2] = aspectTextureRegion.u2;
                this.regionChannel.data[i2 + 3] = aspectTextureRegion.v2;
                this.regionChannel.data[i2 + 4] = 0.5f;
                this.regionChannel.data[i2 + 5] = aspectTextureRegion.halfInvAspectRatio;
                i2 += this.regionChannel.strideSize;
                i3 += this.lifeChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Animated copy() {
            return new Animated(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/RegionInfluencer$AspectTextureRegion.class
      classes8.dex
     */
    public static class AspectTextureRegion {
        public float halfInvAspectRatio;
        public String imageName;
        public float u;
        public float u2;
        public float v;
        public float v2;

        public AspectTextureRegion() {
        }

        public AspectTextureRegion(AspectTextureRegion aspectTextureRegion) {
            set(aspectTextureRegion);
        }

        public AspectTextureRegion(TextureRegion textureRegion) {
            set(textureRegion);
        }

        public void set(TextureRegion textureRegion) {
            this.u = textureRegion.getU();
            this.v = textureRegion.getV();
            this.u2 = textureRegion.getU2();
            this.v2 = textureRegion.getV2();
            this.halfInvAspectRatio = (textureRegion.getRegionHeight() / textureRegion.getRegionWidth()) * 0.5f;
            if (textureRegion instanceof TextureAtlas.AtlasRegion) {
                this.imageName = ((TextureAtlas.AtlasRegion) textureRegion).f97name;
            }
        }

        public void set(AspectTextureRegion aspectTextureRegion) {
            this.u = aspectTextureRegion.u;
            this.v = aspectTextureRegion.v;
            this.u2 = aspectTextureRegion.u2;
            this.v2 = aspectTextureRegion.v2;
            this.halfInvAspectRatio = aspectTextureRegion.halfInvAspectRatio;
            this.imageName = aspectTextureRegion.imageName;
        }

        public void updateUV(TextureAtlas textureAtlas) {
            String str = this.imageName;
            if (str == null) {
                return;
            }
            TextureAtlas.AtlasRegion atlasRegionFindRegion = textureAtlas.findRegion(str);
            this.u = atlasRegionFindRegion.getU();
            this.v = atlasRegionFindRegion.getV();
            this.u2 = atlasRegionFindRegion.getU2();
            this.v2 = atlasRegionFindRegion.getV2();
            this.halfInvAspectRatio = (atlasRegionFindRegion.getRegionHeight() / atlasRegionFindRegion.getRegionWidth()) * 0.5f;
        }
    }

    public RegionInfluencer(int i) {
        this.regions = new Array<>(false, i, AspectTextureRegion.class);
    }

    public RegionInfluencer() {
        this(1);
        AspectTextureRegion aspectTextureRegion = new AspectTextureRegion();
        aspectTextureRegion.v = 0.0f;
        aspectTextureRegion.u = 0.0f;
        aspectTextureRegion.v2 = 1.0f;
        aspectTextureRegion.u2 = 1.0f;
        aspectTextureRegion.halfInvAspectRatio = 0.5f;
        this.regions.add(aspectTextureRegion);
    }

    public RegionInfluencer(TextureRegion... textureRegionArr) {
        setAtlasName(null);
        this.regions = new Array<>(false, textureRegionArr.length, AspectTextureRegion.class);
        add(textureRegionArr);
    }

    public RegionInfluencer(Texture texture) {
        this(new TextureRegion(texture));
    }

    public RegionInfluencer(RegionInfluencer regionInfluencer) {
        this(regionInfluencer.regions.size);
        this.regions.ensureCapacity(regionInfluencer.regions.size);
        for (int i = 0; i < regionInfluencer.regions.size; i++) {
            this.regions.add(new AspectTextureRegion(regionInfluencer.regions.get(i)));
        }
    }

    public void setAtlasName(String str) {
        this.atlasName = str;
    }

    public void add(TextureRegion... textureRegionArr) {
        this.regions.ensureCapacity(textureRegionArr.length);
        for (TextureRegion textureRegion : textureRegionArr) {
            this.regions.add(new AspectTextureRegion(textureRegion));
        }
    }

    public void clear() {
        this.atlasName = null;
        this.regions.clear();
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void load(AssetManager assetManager, ResourceData resourceData) {
        super.load(assetManager, resourceData);
        ResourceData.SaveData saveData = resourceData.getSaveData(ASSET_DATA);
        if (saveData == null) {
            return;
        }
        TextureAtlas textureAtlas = (TextureAtlas) assetManager.get(saveData.loadAsset());
        Array.ArrayIterator<AspectTextureRegion> it = this.regions.iterator();
        while (it.hasNext()) {
            it.next().updateUV(textureAtlas);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void save(AssetManager assetManager, ResourceData resourceData) {
        super.save(assetManager, resourceData);
        if (this.atlasName != null) {
            ResourceData.SaveData saveData = resourceData.getSaveData(ASSET_DATA);
            if (saveData == null) {
                saveData = resourceData.createSaveData(ASSET_DATA);
            }
            saveData.saveAsset(this.atlasName, TextureAtlas.class);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        this.regionChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.TextureRegion);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        json.writeValue("regions", this.regions, Array.class, AspectTextureRegion.class);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        this.regions.clear();
        this.regions.addAll((Array<? extends AspectTextureRegion>) json.readValue("regions", Array.class, AspectTextureRegion.class, jsonValue));
    }
}
