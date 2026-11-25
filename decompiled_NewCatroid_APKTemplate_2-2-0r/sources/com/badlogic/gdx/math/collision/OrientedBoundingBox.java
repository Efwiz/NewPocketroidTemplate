package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import java.io.Serializable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/collision/OrientedBoundingBox.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class OrientedBoundingBox implements Serializable {
    private static final long serialVersionUID = 3864065514676250557L;
    private static final Vector3[] tempAxes = new Vector3[15];
    private static final Vector3[] tempVertices = new Vector3[8];
    private static final Vector3[] tmpVectors = new Vector3[9];
    private final Vector3[] axes;
    private final BoundingBox bounds;
    private final Matrix4 inverseTransform;
    public final Matrix4 transform;
    private final Vector3[] vertices;

    static {
        int i = 0;
        int i2 = 0;
        while (true) {
            Vector3[] vector3Arr = tmpVectors;
            if (i2 >= vector3Arr.length) {
                break;
            }
            vector3Arr[i2] = new Vector3();
            i2++;
        }
        while (true) {
            Vector3[] vector3Arr2 = tempVertices;
            if (i >= vector3Arr2.length) {
                return;
            }
            vector3Arr2[i] = new Vector3();
            i++;
        }
    }

    public OrientedBoundingBox() {
        BoundingBox boundingBox = new BoundingBox();
        this.bounds = boundingBox;
        this.transform = new Matrix4();
        this.inverseTransform = new Matrix4();
        this.axes = new Vector3[3];
        this.vertices = new Vector3[8];
        boundingBox.clr();
        init();
    }

    public OrientedBoundingBox(BoundingBox boundingBox) {
        BoundingBox boundingBox2 = new BoundingBox();
        this.bounds = boundingBox2;
        this.transform = new Matrix4();
        this.inverseTransform = new Matrix4();
        this.axes = new Vector3[3];
        this.vertices = new Vector3[8];
        boundingBox2.set(boundingBox.min, boundingBox.max);
        init();
    }

    public OrientedBoundingBox(BoundingBox boundingBox, Matrix4 matrix4) {
        BoundingBox boundingBox2 = new BoundingBox();
        this.bounds = boundingBox2;
        Matrix4 matrix5 = new Matrix4();
        this.transform = matrix5;
        this.inverseTransform = new Matrix4();
        this.axes = new Vector3[3];
        this.vertices = new Vector3[8];
        boundingBox2.set(boundingBox.min, boundingBox.max);
        matrix5.set(matrix4);
        init();
    }

    private void init() {
        int i = 0;
        int i2 = 0;
        while (true) {
            Vector3[] vector3Arr = this.axes;
            if (i2 >= vector3Arr.length) {
                break;
            }
            vector3Arr[i2] = new Vector3();
            i2++;
        }
        while (true) {
            Vector3[] vector3Arr2 = this.vertices;
            if (i < vector3Arr2.length) {
                vector3Arr2[i] = new Vector3();
                i++;
            } else {
                update();
                return;
            }
        }
    }

    public Vector3[] getVertices() {
        return this.vertices;
    }

    public BoundingBox getBounds() {
        return this.bounds;
    }

    public void setBounds(BoundingBox boundingBox) {
        this.bounds.set(boundingBox);
        boundingBox.getCorner000(this.vertices[0]).mul(this.transform);
        boundingBox.getCorner001(this.vertices[1]).mul(this.transform);
        boundingBox.getCorner010(this.vertices[2]).mul(this.transform);
        boundingBox.getCorner011(this.vertices[3]).mul(this.transform);
        boundingBox.getCorner100(this.vertices[4]).mul(this.transform);
        boundingBox.getCorner101(this.vertices[5]).mul(this.transform);
        boundingBox.getCorner110(this.vertices[6]).mul(this.transform);
        boundingBox.getCorner111(this.vertices[7]).mul(this.transform);
    }

    public Matrix4 getTransform() {
        return this.transform;
    }

    public void setTransform(Matrix4 matrix4) {
        this.transform.set(matrix4);
        update();
    }

    public OrientedBoundingBox set(BoundingBox boundingBox, Matrix4 matrix4) {
        setBounds(boundingBox);
        setTransform(matrix4);
        return this;
    }

    public Vector3 getCorner000(Vector3 vector3) {
        return vector3.set(this.vertices[0]);
    }

    public Vector3 getCorner001(Vector3 vector3) {
        return vector3.set(this.vertices[1]);
    }

    public Vector3 getCorner010(Vector3 vector3) {
        return vector3.set(this.vertices[2]);
    }

    public Vector3 getCorner011(Vector3 vector3) {
        return vector3.set(this.vertices[3]);
    }

    public Vector3 getCorner100(Vector3 vector3) {
        return vector3.set(this.vertices[4]);
    }

    public Vector3 getCorner101(Vector3 vector3) {
        return vector3.set(this.vertices[5]);
    }

    public Vector3 getCorner110(Vector3 vector3) {
        return vector3.set(this.vertices[6]);
    }

    public Vector3 getCorner111(Vector3 vector3) {
        return vector3.set(this.vertices[7]);
    }

    public boolean contains(Vector3 vector3) {
        return contains(vector3, this.inverseTransform);
    }

    private boolean contains(Vector3 vector3, Matrix4 matrix4) {
        return this.bounds.contains(tmpVectors[0].set(vector3).mul(matrix4));
    }

    public boolean contains(BoundingBox boundingBox) {
        Vector3 vector3 = tmpVectors[0];
        return contains(boundingBox.getCorner000(vector3), this.inverseTransform) && contains(boundingBox.getCorner001(vector3), this.inverseTransform) && contains(boundingBox.getCorner010(vector3), this.inverseTransform) && contains(boundingBox.getCorner011(vector3), this.inverseTransform) && contains(boundingBox.getCorner100(vector3), this.inverseTransform) && contains(boundingBox.getCorner101(vector3), this.inverseTransform) && contains(boundingBox.getCorner110(vector3), this.inverseTransform) && contains(boundingBox.getCorner111(vector3), this.inverseTransform);
    }

    public boolean contains(OrientedBoundingBox orientedBoundingBox) {
        Vector3[] vector3Arr = tmpVectors;
        return contains(orientedBoundingBox.getCorner000(vector3Arr[0]), this.inverseTransform) && contains(orientedBoundingBox.getCorner001(vector3Arr[0]), this.inverseTransform) && contains(orientedBoundingBox.getCorner010(vector3Arr[0]), this.inverseTransform) && contains(orientedBoundingBox.getCorner011(vector3Arr[0]), this.inverseTransform) && contains(orientedBoundingBox.getCorner100(vector3Arr[0]), this.inverseTransform) && contains(orientedBoundingBox.getCorner101(vector3Arr[0]), this.inverseTransform) && contains(orientedBoundingBox.getCorner110(vector3Arr[0]), this.inverseTransform) && contains(orientedBoundingBox.getCorner111(vector3Arr[0]), this.inverseTransform);
    }

    public boolean intersects(BoundingBox boundingBox) {
        Vector3[] vector3Arr = this.axes;
        Vector3[] vector3Arr2 = tempAxes;
        vector3Arr2[0] = vector3Arr[0];
        vector3Arr2[1] = vector3Arr[1];
        vector3Arr2[2] = vector3Arr[2];
        vector3Arr2[3] = Vector3.X;
        vector3Arr2[4] = Vector3.Y;
        vector3Arr2[5] = Vector3.Z;
        Vector3[] vector3Arr3 = tmpVectors;
        vector3Arr2[6] = vector3Arr3[0].set(vector3Arr[0]).crs(Vector3.X);
        vector3Arr2[7] = vector3Arr3[1].set(vector3Arr[0]).crs(Vector3.Y);
        vector3Arr2[8] = vector3Arr3[2].set(vector3Arr[0]).crs(Vector3.Z);
        vector3Arr2[9] = vector3Arr3[3].set(vector3Arr[1]).crs(Vector3.X);
        vector3Arr2[10] = vector3Arr3[4].set(vector3Arr[1]).crs(Vector3.Y);
        vector3Arr2[11] = vector3Arr3[5].set(vector3Arr[1]).crs(Vector3.Z);
        vector3Arr2[12] = vector3Arr3[6].set(vector3Arr[2]).crs(Vector3.X);
        vector3Arr2[13] = vector3Arr3[7].set(vector3Arr[2]).crs(Vector3.Y);
        vector3Arr2[14] = vector3Arr3[8].set(vector3Arr[2]).crs(Vector3.Z);
        return Intersector.hasOverlap(vector3Arr2, getVertices(), getVertices(boundingBox));
    }

    public boolean intersects(OrientedBoundingBox orientedBoundingBox) {
        Vector3[] vector3Arr = this.axes;
        Vector3[] vector3Arr2 = orientedBoundingBox.axes;
        Vector3[] vector3Arr3 = tempAxes;
        vector3Arr3[0] = vector3Arr[0];
        vector3Arr3[1] = vector3Arr[1];
        vector3Arr3[2] = vector3Arr[2];
        vector3Arr3[3] = vector3Arr2[0];
        vector3Arr3[4] = vector3Arr2[1];
        vector3Arr3[5] = vector3Arr2[2];
        Vector3[] vector3Arr4 = tmpVectors;
        vector3Arr3[6] = vector3Arr4[0].set(vector3Arr[0]).crs(vector3Arr2[0]);
        vector3Arr3[7] = vector3Arr4[1].set(vector3Arr[0]).crs(vector3Arr2[1]);
        vector3Arr3[8] = vector3Arr4[2].set(vector3Arr[0]).crs(vector3Arr2[2]);
        vector3Arr3[9] = vector3Arr4[3].set(vector3Arr[1]).crs(vector3Arr2[0]);
        vector3Arr3[10] = vector3Arr4[4].set(vector3Arr[1]).crs(vector3Arr2[1]);
        vector3Arr3[11] = vector3Arr4[5].set(vector3Arr[1]).crs(vector3Arr2[2]);
        vector3Arr3[12] = vector3Arr4[6].set(vector3Arr[2]).crs(vector3Arr2[0]);
        vector3Arr3[13] = vector3Arr4[7].set(vector3Arr[2]).crs(vector3Arr2[1]);
        vector3Arr3[14] = vector3Arr4[8].set(vector3Arr[2]).crs(vector3Arr2[2]);
        return Intersector.hasOverlap(vector3Arr3, this.vertices, orientedBoundingBox.vertices);
    }

    private Vector3[] getVertices(BoundingBox boundingBox) {
        Vector3[] vector3Arr = tempVertices;
        boundingBox.getCorner000(vector3Arr[0]);
        boundingBox.getCorner001(vector3Arr[1]);
        boundingBox.getCorner010(vector3Arr[2]);
        boundingBox.getCorner011(vector3Arr[3]);
        boundingBox.getCorner100(vector3Arr[4]);
        boundingBox.getCorner101(vector3Arr[5]);
        boundingBox.getCorner110(vector3Arr[6]);
        boundingBox.getCorner111(vector3Arr[7]);
        return vector3Arr;
    }

    public void mul(Matrix4 matrix4) {
        this.transform.mul(matrix4);
        update();
    }

    private void update() {
        this.bounds.getCorner000(this.vertices[0]).mul(this.transform);
        this.bounds.getCorner001(this.vertices[1]).mul(this.transform);
        this.bounds.getCorner010(this.vertices[2]).mul(this.transform);
        this.bounds.getCorner011(this.vertices[3]).mul(this.transform);
        this.bounds.getCorner100(this.vertices[4]).mul(this.transform);
        this.bounds.getCorner101(this.vertices[5]).mul(this.transform);
        this.bounds.getCorner110(this.vertices[6]).mul(this.transform);
        this.bounds.getCorner111(this.vertices[7]).mul(this.transform);
        this.axes[0].set(this.transform.val[0], this.transform.val[1], this.transform.val[2]).nor();
        this.axes[1].set(this.transform.val[4], this.transform.val[5], this.transform.val[6]).nor();
        this.axes[2].set(this.transform.val[8], this.transform.val[9], this.transform.val[10]).nor();
        this.inverseTransform.set(this.transform).inv();
    }
}
