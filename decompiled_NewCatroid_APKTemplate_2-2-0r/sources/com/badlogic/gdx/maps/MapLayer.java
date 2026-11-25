package com.badlogic.gdx.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/MapLayer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class MapLayer {
    private float offsetX;
    private float offsetY;
    private MapLayer parent;
    private float renderOffsetX;
    private float renderOffsetY;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f116name = "";
    private float opacity = 1.0f;
    private Color tintColor = new Color(Color.WHITE);
    private Color tempColor = new Color(Color.WHITE);
    private boolean visible = true;
    private float parallaxX = 1.0f;
    private float parallaxY = 1.0f;
    private boolean renderOffsetDirty = true;
    private MapObjects objects = new MapObjects();
    private MapProperties properties = new MapProperties();

    public String getName() {
        return this.f116name;
    }

    public void setName(String str) {
        this.f116name = str;
    }

    public float getOpacity() {
        MapLayer mapLayer = this.parent;
        if (mapLayer != null) {
            return this.opacity * mapLayer.getOpacity();
        }
        return this.opacity;
    }

    public void setOpacity(float f) {
        this.opacity = f;
    }

    public Color getCombinedTintColor() {
        if (this.parent != null) {
            return this.tempColor.set(this.tintColor).mul(this.parent.getCombinedTintColor());
        }
        return this.tempColor.set(this.tintColor);
    }

    public Color getTintColor() {
        return this.tintColor;
    }

    public void setTintColor(Color color) {
        this.tintColor.set(color);
    }

    public float getOffsetX() {
        return this.offsetX;
    }

    public void setOffsetX(float f) {
        this.offsetX = f;
        invalidateRenderOffset();
    }

    public float getOffsetY() {
        return this.offsetY;
    }

    public void setOffsetY(float f) {
        this.offsetY = f;
        invalidateRenderOffset();
    }

    public float getParallaxX() {
        return this.parallaxX;
    }

    public void setParallaxX(float f) {
        this.parallaxX = f;
    }

    public float getParallaxY() {
        return this.parallaxY;
    }

    public void setParallaxY(float f) {
        this.parallaxY = f;
    }

    public float getRenderOffsetX() {
        if (this.renderOffsetDirty) {
            calculateRenderOffsets();
        }
        return this.renderOffsetX;
    }

    public float getRenderOffsetY() {
        if (this.renderOffsetDirty) {
            calculateRenderOffsets();
        }
        return this.renderOffsetY;
    }

    public void invalidateRenderOffset() {
        this.renderOffsetDirty = true;
    }

    public MapLayer getParent() {
        return this.parent;
    }

    public void setParent(MapLayer mapLayer) {
        if (mapLayer == this) {
            throw new GdxRuntimeException("Can't set self as the parent");
        }
        this.parent = mapLayer;
    }

    public MapObjects getObjects() {
        return this.objects;
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

    protected void calculateRenderOffsets() {
        MapLayer mapLayer = this.parent;
        if (mapLayer != null) {
            mapLayer.calculateRenderOffsets();
            this.renderOffsetX = this.parent.getRenderOffsetX() + this.offsetX;
            this.renderOffsetY = this.parent.getRenderOffsetY() + this.offsetY;
        } else {
            this.renderOffsetX = this.offsetX;
            this.renderOffsetY = this.offsetY;
        }
        this.renderOffsetDirty = false;
    }
}
