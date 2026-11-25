package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/TiledMapRenderer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface TiledMapRenderer extends MapRenderer {
    void renderImageLayer(TiledMapImageLayer tiledMapImageLayer);

    void renderObject(MapObject mapObject);

    void renderObjects(MapLayer mapLayer);

    void renderTileLayer(TiledMapTileLayer tiledMapTileLayer);
}
