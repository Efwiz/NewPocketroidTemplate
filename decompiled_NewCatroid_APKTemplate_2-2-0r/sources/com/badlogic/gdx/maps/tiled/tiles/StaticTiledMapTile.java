package com.badlogic.gdx.maps.tiled.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/tiles/StaticTiledMapTile.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class StaticTiledMapTile implements TiledMapTile {
    private TiledMapTile.BlendMode blendMode = TiledMapTile.BlendMode.ALPHA;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private int f121id;
    private MapObjects objects;
    private float offsetX;
    private float offsetY;
    private MapProperties properties;
    private TextureRegion textureRegion;

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public int getId() {
        return this.f121id;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public void setId(int i) {
        this.f121id = i;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public TiledMapTile.BlendMode getBlendMode() {
        return this.blendMode;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public void setBlendMode(TiledMapTile.BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public MapProperties getProperties() {
        if (this.properties == null) {
            this.properties = new MapProperties();
        }
        return this.properties;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public MapObjects getObjects() {
        if (this.objects == null) {
            this.objects = new MapObjects();
        }
        return this.objects;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public float getOffsetX() {
        return this.offsetX;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public void setOffsetX(float f) {
        this.offsetX = f;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public float getOffsetY() {
        return this.offsetY;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapTile
    public void setOffsetY(float f) {
        this.offsetY = f;
    }

    public StaticTiledMapTile(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public StaticTiledMapTile(StaticTiledMapTile staticTiledMapTile) {
        if (staticTiledMapTile.properties != null) {
            getProperties().putAll(staticTiledMapTile.properties);
        }
        this.objects = staticTiledMapTile.objects;
        this.textureRegion = staticTiledMapTile.textureRegion;
        this.f121id = staticTiledMapTile.f121id;
    }
}
