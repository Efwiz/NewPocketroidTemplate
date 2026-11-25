package com.badlogic.gdx.maps;

import com.badlogic.gdx.graphics.Color;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/MapObject.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class MapObject {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f117name = "";
    private float opacity = 1.0f;
    private boolean visible = true;
    private MapProperties properties = new MapProperties();
    private Color color = Color.WHITE.cpy();

    public String getName() {
        return this.f117name;
    }

    public void setName(String str) {
        this.f117name = str;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setOpacity(float f) {
        this.opacity = f;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean z) {
        this.visible = z;
    }

    public MapProperties getProperties() {
        return this.properties;
    }
}
