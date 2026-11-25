package com.badlogic.gdx.scenes.scene2d.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/utils/Layout.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Layout {
    float getMaxHeight();

    float getMaxWidth();

    float getMinHeight();

    float getMinWidth();

    float getPrefHeight();

    float getPrefWidth();

    void invalidate();

    void invalidateHierarchy();

    void layout();

    void pack();

    void setFillParent(boolean z);

    void setLayoutEnabled(boolean z);

    void validate();
}
