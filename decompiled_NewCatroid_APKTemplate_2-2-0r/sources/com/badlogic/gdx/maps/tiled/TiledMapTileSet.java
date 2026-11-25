package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.IntMap;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/TiledMapTileSet.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TiledMapTileSet implements Iterable<TiledMapTile> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f119name;
    private IntMap<TiledMapTile> tiles = new IntMap<>();
    private MapProperties properties = new MapProperties();

    public String getName() {
        return this.f119name;
    }

    public void setName(String str) {
        this.f119name = str;
    }

    public MapProperties getProperties() {
        return this.properties;
    }

    public TiledMapTile getTile(int i) {
        return this.tiles.get(i);
    }

    @Override // java.lang.Iterable
    public Iterator<TiledMapTile> iterator() {
        return this.tiles.values().iterator();
    }

    public void putTile(int i, TiledMapTile tiledMapTile) {
        this.tiles.put(i, tiledMapTile);
    }

    public void removeTile(int i) {
        this.tiles.remove(i);
    }

    public int size() {
        return this.tiles.size;
    }
}
