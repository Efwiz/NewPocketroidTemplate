package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Ellipse;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/objects/EllipseMapObject.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class EllipseMapObject extends MapObject {
    private Ellipse ellipse;

    public Ellipse getEllipse() {
        return this.ellipse;
    }

    public EllipseMapObject() {
        this(0.0f, 0.0f, 1.0f, 1.0f);
    }

    public EllipseMapObject(float f, float f2, float f3, float f4) {
        this.ellipse = new Ellipse(f, f2, f3, f4);
    }
}
