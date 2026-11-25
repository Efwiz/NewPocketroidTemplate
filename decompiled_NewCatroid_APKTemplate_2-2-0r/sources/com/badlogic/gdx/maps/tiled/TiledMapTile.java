package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/TiledMapTile.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface TiledMapTile {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/TiledMapTile$BlendMode.class
      classes8.dex
     */
    public enum BlendMode {
        NONE,
        ALPHA
    }

    BlendMode getBlendMode();

    int getId();

    MapObjects getObjects();

    float getOffsetX();

    float getOffsetY();

    MapProperties getProperties();

    TextureRegion getTextureRegion();

    void setBlendMode(BlendMode blendMode);

    void setId(int i);

    void setOffsetX(float f);

    void setOffsetY(float f);

    void setTextureRegion(TextureRegion textureRegion);
}
