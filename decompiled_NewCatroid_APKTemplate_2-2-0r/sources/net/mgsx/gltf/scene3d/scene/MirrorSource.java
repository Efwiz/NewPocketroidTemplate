package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import net.mgsx.gltf.scene3d.attributes.ClippingPlaneAttribute;
import net.mgsx.gltf.scene3d.attributes.MirrorSourceAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class MirrorSource implements Disposable {
    private Camera camera;
    private ClippingPlaneAttribute clippingPlane;
    private Environment environment;
    protected FrameBuffer fbo;
    private int height;
    protected final MirrorSourceAttribute mirrorAttribute;
    private SceneSkybox skyBox;
    private int width;
    private final Vector3 originalCameraPosition = new Vector3();
    private final Vector3 originalCameraDirection = new Vector3();
    private final Vector3 originalCameraUp = new Vector3();
    private final Vector3 planeOrigin = new Vector3();
    private final Vector3 planeToCamera = new Vector3();
    public boolean clipScene = true;

    public MirrorSource() {
        MirrorSourceAttribute mirrorSourceAttribute = new MirrorSourceAttribute();
        this.mirrorAttribute = mirrorSourceAttribute;
        this.clippingPlane = new ClippingPlaneAttribute(Vector3.Y, 0.0f);
        mirrorSourceAttribute.textureDescription.minFilter = Texture.TextureFilter.MipMap;
        mirrorSourceAttribute.textureDescription.magFilter = Texture.TextureFilter.Linear;
        mirrorSourceAttribute.normal.set(this.clippingPlane.plane.normal);
    }

    protected FrameBuffer createFrameBuffer(int i, int i2) {
        return new FrameBuffer(Pixmap.Format.RGBA8888, i, i2, true);
    }

    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public void setPlane(float f, float f2, float f3, float f4) {
        this.clippingPlane.plane.normal.set(f, f2, f3).nor();
        this.clippingPlane.plane.d = f4;
        this.mirrorAttribute.normal.set(this.clippingPlane.plane.normal);
    }

    public MirrorSource set(float f, float f2, float f3, float f4, boolean z) {
        setPlane(f, f2, f3, f4);
        this.clipScene = z;
        return this;
    }

    public void begin(Camera camera, Environment environment, SceneSkybox sceneSkybox) {
        this.camera = camera;
        this.environment = environment;
        this.skyBox = sceneSkybox;
        setupCamera(camera, this.clippingPlane.plane);
        if (sceneSkybox != null) {
            sceneSkybox.update(camera, 0.0f);
        }
        if (this.clipScene) {
            environment.set(this.clippingPlane);
        }
        ensureFrameBufferSize(this.width, this.height);
        this.fbo.begin();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(16640);
    }

    public void end() {
        this.fbo.end();
        Texture colorBufferTexture = this.fbo.getColorBufferTexture();
        colorBufferTexture.bind();
        Gdx.gl.glGenerateMipmap(colorBufferTexture.glTarget);
        this.mirrorAttribute.textureDescription.texture = this.fbo.getColorBufferTexture();
        restoreCamera(this.camera);
        this.environment.set(this.mirrorAttribute);
        this.environment.remove(ClippingPlaneAttribute.Type);
        SceneSkybox sceneSkybox = this.skyBox;
        if (sceneSkybox != null) {
            sceneSkybox.update(this.camera, 0.0f);
        }
        this.camera = null;
        this.environment = null;
        this.skyBox = null;
    }

    private void setupCamera(Camera camera, Plane plane) {
        this.originalCameraPosition.set(camera.position);
        this.originalCameraDirection.set(camera.direction);
        this.originalCameraUp.set(camera.up);
        this.planeOrigin.set(this.clippingPlane.plane.normal).scl(this.clippingPlane.plane.d);
        this.planeToCamera.set(camera.position).sub(this.planeOrigin);
        camera.position.sub(this.planeToCamera);
        reflect(this.planeToCamera, this.clippingPlane.plane.normal);
        camera.position.add(this.planeToCamera);
        reflect(camera.direction, this.clippingPlane.plane.normal);
        reflect(camera.up, this.clippingPlane.plane.normal);
        camera.update();
    }

    private void reflect(Vector3 vector3, Vector3 vector4) {
        vector3.mulAdd(vector4, vector3.dot(vector4) * (-2.0f));
    }

    private void restoreCamera(Camera camera) {
        camera.position.set(this.originalCameraPosition);
        camera.direction.set(this.originalCameraDirection);
        camera.up.set(this.originalCameraUp);
        camera.update();
    }

    private void ensureFrameBufferSize(int i, int i2) {
        if (i <= 0) {
            i = Gdx.graphics.getBackBufferWidth();
        }
        if (i2 <= 0) {
            i2 = Gdx.graphics.getBackBufferHeight();
        }
        FrameBuffer frameBuffer = this.fbo;
        if (frameBuffer != null && frameBuffer.getWidth() == i && this.fbo.getHeight() == i2) {
            return;
        }
        FrameBuffer frameBuffer2 = this.fbo;
        if (frameBuffer2 != null) {
            frameBuffer2.dispose();
        }
        this.fbo = createFrameBuffer(i, i2);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        FrameBuffer frameBuffer = this.fbo;
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
    }
}
