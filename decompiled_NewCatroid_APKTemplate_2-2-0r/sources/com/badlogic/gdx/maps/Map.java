package com.badlogic.gdx.maps;

import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/Map.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Map implements Disposable {
    private MapLayers layers = new MapLayers();
    private MapProperties properties = new MapProperties();

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
    }

    public MapLayers getLayers() {
        return this.layers;
    }

    public MapProperties getProperties() {
        return this.properties;
    }
}
