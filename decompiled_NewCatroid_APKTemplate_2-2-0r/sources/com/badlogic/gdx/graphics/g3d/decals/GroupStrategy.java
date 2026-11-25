package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/decals/GroupStrategy.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface GroupStrategy {
    void afterGroup(int i);

    void afterGroups();

    void beforeGroup(int i, Array<Decal> array);

    void beforeGroups();

    int decideGroup(Decal decal);

    ShaderProgram getGroupShader(int i);
}
