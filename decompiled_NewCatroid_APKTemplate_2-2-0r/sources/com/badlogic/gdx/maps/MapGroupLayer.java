package com.badlogic.gdx.maps;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/MapGroupLayer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class MapGroupLayer extends MapLayer {
    private MapLayers layers = new MapLayers();

    public MapLayers getLayers() {
        return this.layers;
    }

    @Override // com.badlogic.gdx.maps.MapLayer
    public void invalidateRenderOffset() {
        super.invalidateRenderOffset();
        for (int i = 0; i < this.layers.size(); i++) {
            this.layers.get(i).invalidateRenderOffset();
        }
    }
}
