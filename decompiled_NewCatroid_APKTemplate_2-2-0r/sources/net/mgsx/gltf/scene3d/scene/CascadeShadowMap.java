package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import net.mgsx.gltf.scene3d.attributes.CascadeShadowMapAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalShadowLight;

/* JADX INFO: loaded from: classes12.dex */
public class CascadeShadowMap implements Disposable {
    protected final int cascadeCount;
    public final Array<DirectionalShadowLight> lights;
    protected final FloatArray splitRates;
    private final Matrix4 lightMatrix = new Matrix4();
    private final BoundingBox box = new BoundingBox();
    private final Vector3 center = new Vector3();
    private final Vector3 offset = new Vector3();
    public final CascadeShadowMapAttribute attribute = new CascadeShadowMapAttribute(this);
    private final Vector3[] splitPoints = new Vector3[8];

    public CascadeShadowMap(int i) {
        this.cascadeCount = i;
        this.lights = new Array<>(i);
        this.splitRates = new FloatArray(i + 2);
        int i2 = 0;
        while (true) {
            Vector3[] vector3Arr = this.splitPoints;
            if (i2 >= vector3Arr.length) {
                return;
            }
            vector3Arr[i2] = new Vector3();
            i2++;
        }
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        Array.ArrayIterator<DirectionalShadowLight> it = this.lights.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.lights.clear();
    }

    public void setCascades(Camera camera, DirectionalShadowLight directionalShadowLight, float f, float f2) {
        this.splitRates.clear();
        float f3 = 1.0f;
        for (int i = 0; i < this.cascadeCount + 1; i++) {
            this.splitRates.add(f3);
            f3 /= f2;
        }
        this.splitRates.add(0.0f);
        this.splitRates.reverse();
        setCascades(camera, directionalShadowLight, f, this.splitRates);
    }

    public void setCascades(Camera camera, DirectionalShadowLight directionalShadowLight, float f, FloatArray floatArray) {
        if (floatArray.size != this.cascadeCount + 2) {
            throw new IllegalArgumentException("Invalid splitRates, expected " + (this.cascadeCount + 2) + " items.");
        }
        syncExtraCascades(directionalShadowLight);
        setBaseLightBounds(directionalShadowLight, camera, f);
        int i = 0;
        while (i < this.cascadeCount) {
            int i2 = i + 1;
            setCascades(this.lights.get(i), directionalShadowLight, camera, floatArray.get(i), floatArray.get(i2), f);
            i = i2;
        }
    }

    protected void setBaseLightBounds(DirectionalShadowLight directionalShadowLight, Camera camera, float f) {
        this.lightMatrix.setToLookAt(directionalShadowLight.direction, directionalShadowLight.getCamera().up);
        this.box.inf();
        int i = 0;
        while (true) {
            Vector3[] vector3Arr = this.splitPoints;
            if (i < vector3Arr.length) {
                this.box.ext(vector3Arr[i].set(camera.frustum.planePoints[i]).mul(this.lightMatrix));
                i++;
            } else {
                this.box.getCenter(this.center);
                this.center.mul(this.lightMatrix.tra());
                float depth = this.box.getDepth() / 2.0f;
                centerLight(directionalShadowLight, this.center, this.box.getWidth(), this.box.getHeight(), (-depth) - f, depth);
                return;
            }
        }
    }

    private void setCascades(DirectionalShadowLight directionalShadowLight, DirectionalShadowLight directionalShadowLight2, Camera camera, float f, float f2, float f3) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            Vector3 vector3 = camera.frustum.planePoints[i2];
            int i3 = i2 + 4;
            Vector3 vector4 = camera.frustum.planePoints[i3];
            this.splitPoints[i2].set(vector3).lerp(vector4, f);
            this.splitPoints[i3].set(vector3).lerp(vector4, f2);
        }
        this.lightMatrix.setToLookAt(directionalShadowLight.direction, directionalShadowLight.getCamera().up);
        this.box.inf();
        while (true) {
            Vector3[] vector3Arr = this.splitPoints;
            if (i < vector3Arr.length) {
                this.box.ext(vector3Arr[i].mul(this.lightMatrix));
                i++;
            } else {
                this.box.getCenter(this.center);
                this.center.mul(this.lightMatrix.tra());
                centerLight(directionalShadowLight, this.center, this.box.getWidth(), this.box.getHeight(), directionalShadowLight2.getCamera().near + (-this.offset.set(this.center).sub(directionalShadowLight2.getCamera().position).dot(directionalShadowLight2.direction)), this.box.getDepth() / 2.0f);
                return;
            }
        }
    }

    private void centerLight(DirectionalShadowLight directionalShadowLight, Vector3 vector3, float f, float f2, float f3, float f4) {
        float f5 = (f4 - f3) / 2.0f;
        directionalShadowLight.setCenter(vector3.mulAdd(directionalShadowLight.direction, f3 + f5));
        directionalShadowLight.setViewport(f, f2, -f5, f5);
    }

    protected void syncExtraCascades(DirectionalShadowLight directionalShadowLight) {
        DirectionalShadowLight directionalShadowLightCreateLight;
        DirectionalShadowLight directionalShadowLight2;
        int width = directionalShadowLight.getFrameBuffer().getWidth();
        int height = directionalShadowLight.getFrameBuffer().getHeight();
        for (int i = 0; i < this.cascadeCount; i++) {
            if (i < this.lights.size) {
                directionalShadowLight2 = this.lights.get(i);
                if (directionalShadowLight2.getFrameBuffer().getWidth() != width || directionalShadowLight2.getFrameBuffer().getHeight() != height) {
                    directionalShadowLight2.dispose();
                    Array<DirectionalShadowLight> array = this.lights;
                    directionalShadowLightCreateLight = createLight(width, height);
                    array.set(i, directionalShadowLightCreateLight);
                }
                directionalShadowLight2.direction.set(directionalShadowLight.direction);
                directionalShadowLight2.getCamera().up.set(directionalShadowLight.getCamera().up);
            } else {
                Array<DirectionalShadowLight> array2 = this.lights;
                directionalShadowLightCreateLight = createLight(width, height);
                array2.add(directionalShadowLightCreateLight);
            }
            directionalShadowLight2 = directionalShadowLightCreateLight;
            directionalShadowLight2.direction.set(directionalShadowLight.direction);
            directionalShadowLight2.getCamera().up.set(directionalShadowLight.getCamera().up);
        }
    }

    protected DirectionalShadowLight createLight(int i, int i2) {
        return new DirectionalShadowLight(i, i2);
    }
}
