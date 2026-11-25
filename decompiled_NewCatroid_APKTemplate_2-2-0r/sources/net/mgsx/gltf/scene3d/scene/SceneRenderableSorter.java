package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.util.Comparator;

/* JADX INFO: loaded from: classes12.dex */
public class SceneRenderableSorter implements RenderableSorter, Comparator<Renderable> {
    private Camera camera;
    private final Vector3 tmpV1 = new Vector3();
    private final Vector3 tmpV2 = new Vector3();

    public enum Hints {
        OPAQUE_LAST
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.RenderableSorter
    public void sort(Camera camera, Array<Renderable> array) {
        this.camera = camera;
        array.sort(this);
    }

    private Vector3 getTranslation(Matrix4 matrix4, Vector3 vector3, Vector3 vector4) {
        if (vector3.isZero()) {
            matrix4.getTranslation(vector4);
        } else if (!matrix4.hasRotationOrScaling()) {
            matrix4.getTranslation(vector4).add(vector3);
        } else {
            vector4.set(vector3).mul(matrix4);
        }
        return vector4;
    }

    @Override // java.util.Comparator
    public int compare(Renderable renderable, Renderable renderable2) {
        int i = 0;
        boolean z = renderable.material.has(BlendingAttribute.Type) && ((BlendingAttribute) renderable.material.get(BlendingAttribute.Type)).blended;
        boolean z2 = renderable2.material.has(BlendingAttribute.Type) && ((BlendingAttribute) renderable2.material.get(BlendingAttribute.Type)).blended;
        Hints hints = renderable.userData instanceof Hints ? (Hints) renderable.userData : null;
        Hints hints2 = renderable2.userData instanceof Hints ? (Hints) renderable2.userData : null;
        if (hints != hints2) {
            if (hints == Hints.OPAQUE_LAST) {
                return z2 ? -1 : 1;
            }
            if (hints2 == Hints.OPAQUE_LAST) {
                return z ? 1 : -1;
            }
        }
        if (!z && !z2) {
            int iCompareIdentity = compareIdentity(renderable.shader, renderable2.shader);
            if (iCompareIdentity != 0) {
                return iCompareIdentity;
            }
            int iCompareIdentityNullable = compareIdentityNullable(renderable.environment, renderable2.environment);
            if (iCompareIdentityNullable != 0) {
                return iCompareIdentityNullable;
            }
            int iCompareIdentity2 = compareIdentity(renderable.material, renderable2.material);
            if (iCompareIdentity2 != 0) {
                return iCompareIdentity2;
            }
            int iCompareIdentity3 = compareIdentity(renderable.meshPart.mesh, renderable2.meshPart.mesh);
            if (iCompareIdentity3 != 0) {
                return iCompareIdentity3;
            }
        } else {
            if (z && !z2) {
                return 1;
            }
            if (!z && z2) {
                return -1;
            }
        }
        getTranslation(renderable.worldTransform, renderable.meshPart.center, this.tmpV1);
        getTranslation(renderable2.worldTransform, renderable2.meshPart.center, this.tmpV2);
        float fDst2 = ((int) this.camera.position.dst2(this.tmpV1)) - ((int) this.camera.position.dst2(this.tmpV2));
        if (fDst2 < 0.0f) {
            i = -1;
        } else if (fDst2 > 0.0f) {
            i = 1;
        }
        return z ? -i : i;
    }

    private int compareIdentityNullable(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return 0;
        }
        if (obj == null) {
            return -1;
        }
        if (obj2 == null) {
            return 1;
        }
        return compareIdentity(obj, obj2);
    }

    private int compareIdentity(Object obj, Object obj2) {
        if (obj == obj2) {
            return 0;
        }
        return Integer.compare(obj.hashCode(), obj2.hashCode());
    }
}
