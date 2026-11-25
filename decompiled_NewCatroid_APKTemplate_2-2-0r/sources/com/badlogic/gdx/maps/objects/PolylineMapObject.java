package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Polyline;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/objects/PolylineMapObject.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PolylineMapObject extends MapObject {
    private Polyline polyline;

    public Polyline getPolyline() {
        return this.polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public PolylineMapObject() {
        this(new float[0]);
    }

    public PolylineMapObject(float[] fArr) {
        this.polyline = new Polyline(fArr);
    }

    public PolylineMapObject(Polyline polyline) {
        this.polyline = polyline;
    }
}
