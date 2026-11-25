package org.catrobat.catroid.sensing;

import android.graphics.PointF;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/CollisionPolygonVertex.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CollisionPolygonVertex {
    public float endX;
    public float endY;
    public float startX;
    public float startY;

    public CollisionPolygonVertex(float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public boolean equals(Object o) {
        if (!(o instanceof CollisionPolygonVertex)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        CollisionPolygonVertex collisionPolygonVertex = (CollisionPolygonVertex) o;
        return collisionPolygonVertex.startX == this.startX && collisionPolygonVertex.startY == this.startY && collisionPolygonVertex.endX == this.endX && collisionPolygonVertex.endY == this.endY;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public void extend(float x, float y) {
        this.endX = x;
        this.endY = y;
    }

    public String toString() {
        return this.startX + "/" + this.startY + " -> " + this.endX + "/" + this.endY;
    }

    public void flip() {
        float f = this.startX;
        float f2 = this.startY;
        this.startX = this.endX;
        this.startY = this.endY;
        this.endX = f;
        this.endY = f2;
    }

    public PointF getStartPoint() {
        return new PointF(this.startX, this.startY);
    }

    public PointF getEndPoint() {
        return new PointF(this.endX, this.endY);
    }

    public boolean isConnected(CollisionPolygonVertex other) {
        if (other.startX == this.endX && other.startY == this.endY) {
            return true;
        }
        if (!isConnectedBackwards(other)) {
            return false;
        }
        other.flip();
        return true;
    }

    private boolean isConnectedBackwards(CollisionPolygonVertex other) {
        return other.endX == this.endX && other.endY == this.endY;
    }
}
