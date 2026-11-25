package com.badlogic.gdx.math;

import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.OrientedBoundingBox;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Frustum.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Frustum {
    protected static final Vector3[] clipSpacePlanePoints;
    protected static final float[] clipSpacePlanePointsArray;
    private static final Vector3 tmpV;
    public final Plane[] planes = new Plane[6];
    public final Vector3[] planePoints = {new Vector3(), new Vector3(), new Vector3(), new Vector3(), new Vector3(), new Vector3(), new Vector3(), new Vector3()};
    protected final float[] planePointsArray = new float[24];

    static {
        Vector3[] vector3Arr = {new Vector3(-1.0f, -1.0f, -1.0f), new Vector3(1.0f, -1.0f, -1.0f), new Vector3(1.0f, 1.0f, -1.0f), new Vector3(-1.0f, 1.0f, -1.0f), new Vector3(-1.0f, -1.0f, 1.0f), new Vector3(1.0f, -1.0f, 1.0f), new Vector3(1.0f, 1.0f, 1.0f), new Vector3(-1.0f, 1.0f, 1.0f)};
        clipSpacePlanePoints = vector3Arr;
        clipSpacePlanePointsArray = new float[24];
        int i = 0;
        for (Vector3 vector3 : vector3Arr) {
            float[] fArr = clipSpacePlanePointsArray;
            fArr[i] = vector3.x;
            int i2 = i + 2;
            fArr[i + 1] = vector3.y;
            i += 3;
            fArr[i2] = vector3.z;
        }
        tmpV = new Vector3();
    }

    public Frustum() {
        for (int i = 0; i < 6; i++) {
            this.planes[i] = new Plane(new Vector3(), 0.0f);
        }
    }

    public void update(Matrix4 matrix4) {
        float[] fArr = clipSpacePlanePointsArray;
        System.arraycopy(fArr, 0, this.planePointsArray, 0, fArr.length);
        Matrix4.prj(matrix4.val, this.planePointsArray, 0, 8, 3);
        int i = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            Vector3 vector3 = this.planePoints[i2];
            vector3.x = this.planePointsArray[i];
            int i3 = i + 2;
            vector3.y = this.planePointsArray[i + 1];
            i += 3;
            vector3.z = this.planePointsArray[i3];
        }
        Plane plane = this.planes[0];
        Vector3[] vector3Arr = this.planePoints;
        plane.set(vector3Arr[1], vector3Arr[0], vector3Arr[2]);
        Plane plane2 = this.planes[1];
        Vector3[] vector3Arr2 = this.planePoints;
        plane2.set(vector3Arr2[4], vector3Arr2[5], vector3Arr2[7]);
        Plane plane3 = this.planes[2];
        Vector3[] vector3Arr3 = this.planePoints;
        plane3.set(vector3Arr3[0], vector3Arr3[4], vector3Arr3[3]);
        Plane plane4 = this.planes[3];
        Vector3[] vector3Arr4 = this.planePoints;
        plane4.set(vector3Arr4[5], vector3Arr4[1], vector3Arr4[6]);
        Plane plane5 = this.planes[4];
        Vector3[] vector3Arr5 = this.planePoints;
        plane5.set(vector3Arr5[2], vector3Arr5[3], vector3Arr5[6]);
        Plane plane6 = this.planes[5];
        Vector3[] vector3Arr6 = this.planePoints;
        plane6.set(vector3Arr6[4], vector3Arr6[0], vector3Arr6[1]);
    }

    public boolean pointInFrustum(Vector3 vector3) {
        int i = 0;
        while (true) {
            Plane[] planeArr = this.planes;
            if (i >= planeArr.length) {
                return true;
            }
            if (planeArr[i].testPoint(vector3) == Plane.PlaneSide.Back) {
                return false;
            }
            i++;
        }
    }

    public boolean pointInFrustum(float f, float f2, float f3) {
        int i = 0;
        while (true) {
            Plane[] planeArr = this.planes;
            if (i >= planeArr.length) {
                return true;
            }
            if (planeArr[i].testPoint(f, f2, f3) == Plane.PlaneSide.Back) {
                return false;
            }
            i++;
        }
    }

    public boolean sphereInFrustum(Vector3 vector3, float f) {
        for (int i = 0; i < 6; i++) {
            if ((this.planes[i].normal.x * vector3.x) + (this.planes[i].normal.y * vector3.y) + (this.planes[i].normal.z * vector3.z) < (-f) - this.planes[i].d) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustum(float f, float f2, float f3, float f4) {
        for (int i = 0; i < 6; i++) {
            if ((this.planes[i].normal.x * f) + (this.planes[i].normal.y * f2) + (this.planes[i].normal.z * f3) < (-f4) - this.planes[i].d) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustumWithoutNearFar(Vector3 vector3, float f) {
        for (int i = 2; i < 6; i++) {
            if ((this.planes[i].normal.x * vector3.x) + (this.planes[i].normal.y * vector3.y) + (this.planes[i].normal.z * vector3.z) < (-f) - this.planes[i].d) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustumWithoutNearFar(float f, float f2, float f3, float f4) {
        for (int i = 2; i < 6; i++) {
            if ((this.planes[i].normal.x * f) + (this.planes[i].normal.y * f2) + (this.planes[i].normal.z * f3) < (-f4) - this.planes[i].d) {
                return false;
            }
        }
        return true;
    }

    public boolean boundsInFrustum(BoundingBox boundingBox) {
        int length = this.planes.length;
        for (int i = 0; i < length; i++) {
            Plane plane = this.planes[i];
            Vector3 vector3 = tmpV;
            if (plane.testPoint(boundingBox.getCorner000(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(boundingBox.getCorner001(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(boundingBox.getCorner010(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(boundingBox.getCorner011(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(boundingBox.getCorner100(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(boundingBox.getCorner101(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(boundingBox.getCorner110(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(boundingBox.getCorner111(vector3)) == Plane.PlaneSide.Back) {
                return false;
            }
        }
        return true;
    }

    public boolean boundsInFrustum(Vector3 vector3, Vector3 vector4) {
        return boundsInFrustum(vector3.x, vector3.y, vector3.z, vector4.x / 2.0f, vector4.y / 2.0f, vector4.z / 2.0f);
    }

    public boolean boundsInFrustum(float f, float f2, float f3, float f4, float f5, float f6) {
        int length = this.planes.length;
        for (int i = 0; i < length; i++) {
            float f7 = f + f4;
            float f8 = f2 + f5;
            float f9 = f3 + f6;
            if (this.planes[i].testPoint(f7, f8, f9) == Plane.PlaneSide.Back) {
                float f10 = f3 - f6;
                if (this.planes[i].testPoint(f7, f8, f10) != Plane.PlaneSide.Back) {
                    continue;
                } else {
                    float f11 = f2 - f5;
                    if (this.planes[i].testPoint(f7, f11, f9) == Plane.PlaneSide.Back && this.planes[i].testPoint(f7, f11, f10) == Plane.PlaneSide.Back) {
                        float f12 = f - f4;
                        if (this.planes[i].testPoint(f12, f8, f9) == Plane.PlaneSide.Back && this.planes[i].testPoint(f12, f8, f10) == Plane.PlaneSide.Back && this.planes[i].testPoint(f12, f11, f9) == Plane.PlaneSide.Back && this.planes[i].testPoint(f12, f11, f10) == Plane.PlaneSide.Back) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean boundsInFrustum(OrientedBoundingBox orientedBoundingBox) {
        int length = this.planes.length;
        for (int i = 0; i < length; i++) {
            Plane plane = this.planes[i];
            Vector3 vector3 = tmpV;
            if (plane.testPoint(orientedBoundingBox.getCorner000(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(orientedBoundingBox.getCorner001(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(orientedBoundingBox.getCorner010(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(orientedBoundingBox.getCorner011(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(orientedBoundingBox.getCorner100(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(orientedBoundingBox.getCorner101(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(orientedBoundingBox.getCorner110(vector3)) == Plane.PlaneSide.Back && this.planes[i].testPoint(orientedBoundingBox.getCorner111(vector3)) == Plane.PlaneSide.Back) {
                return false;
            }
        }
        return true;
    }
}
