package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/decals/PluggableGroupStrategy.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class PluggableGroupStrategy implements GroupStrategy {
    private IntMap<GroupPlug> plugs = new IntMap<>();

    @Override // com.badlogic.gdx.graphics.g3d.decals.GroupStrategy
    public void beforeGroup(int i, Array<Decal> array) {
        this.plugs.get(i).beforeGroup(array);
    }

    @Override // com.badlogic.gdx.graphics.g3d.decals.GroupStrategy
    public void afterGroup(int i) {
        this.plugs.get(i).afterGroup();
    }

    public void plugIn(GroupPlug groupPlug, int i) {
        this.plugs.put(i, groupPlug);
    }

    public GroupPlug unPlug(int i) {
        return this.plugs.remove(i);
    }
}
