package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Polygon;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/objects/PolygonMapObject.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PolygonMapObject extends MapObject {
    private Polygon polygon;

    public Polygon getPolygon() {
        return this.polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public PolygonMapObject() {
        this(new float[0]);
    }

    public PolygonMapObject(float[] fArr) {
        this.polygon = new Polygon(fArr);
    }

    public PolygonMapObject(Polygon polygon) {
        this.polygon = polygon;
    }
}
