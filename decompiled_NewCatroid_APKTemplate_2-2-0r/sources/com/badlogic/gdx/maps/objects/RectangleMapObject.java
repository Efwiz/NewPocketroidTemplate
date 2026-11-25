package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/objects/RectangleMapObject.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RectangleMapObject extends MapObject {
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public RectangleMapObject() {
        this(0.0f, 0.0f, 1.0f, 1.0f);
    }

    public RectangleMapObject(float f, float f2, float f3, float f4) {
        this.rectangle = new Rectangle(f, f2, f3, f4);
    }
}
