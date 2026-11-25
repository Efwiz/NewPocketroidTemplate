package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import net.mgsx.gltf.scene3d.animation.AnimationControllerHack;
import net.mgsx.gltf.scene3d.animation.AnimationsPlayer;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.lights.PointLightEx;
import net.mgsx.gltf.scene3d.lights.SpotLightEx;
import net.mgsx.gltf.scene3d.model.ModelInstanceHack;

/* JADX INFO: loaded from: classes12.dex */
public class Scene implements RenderableProvider, Updatable {
    private static final Matrix4 transform = new Matrix4();
    public AnimationController animationController;
    public final AnimationsPlayer animations;
    public final ObjectMap<Node, Camera> cameras;
    public final ObjectMap<Node, BaseLight> lights;
    public ModelInstance modelInstance;

    public Scene(SceneModel sceneModel) {
        this(new ModelInstanceHack(sceneModel.model), sceneModel);
    }

    public Scene(SceneModel sceneModel, String... strArr) {
        this(new ModelInstanceHack(sceneModel.model, strArr), sceneModel);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Scene(ModelInstance modelInstance, SceneModel sceneModel) {
        this(modelInstance);
        ObjectMap.Entries<Node, Camera> it = sceneModel.cameras.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            Node node = modelInstance.getNode(((Node) next.key).f104id, true);
            if (node != null) {
                this.cameras.put(node, createCamera((Camera) next.value));
            }
        }
        ObjectMap.Entries<Node, BaseLight> it2 = sceneModel.lights.iterator();
        while (it2.hasNext()) {
            ObjectMap.Entry next2 = it2.next();
            Node node2 = modelInstance.getNode(((Node) next2.key).f104id, true);
            if (node2 != null) {
                this.lights.put(node2, createLight((BaseLight) next2.value));
            }
        }
        syncCameras();
        syncLights();
    }

    public Camera createCamera(Camera camera) {
        Camera camera2;
        if (camera instanceof PerspectiveCamera) {
            PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
            perspectiveCamera.fieldOfView = ((PerspectiveCamera) camera).fieldOfView;
            camera2 = perspectiveCamera;
        } else if (camera instanceof OrthographicCamera) {
            OrthographicCamera orthographicCamera = new OrthographicCamera();
            orthographicCamera.zoom = ((OrthographicCamera) camera).zoom;
            camera2 = orthographicCamera;
        } else {
            throw new GdxRuntimeException("unknown camera type " + camera.getClass().getName());
        }
        camera2.position.set(camera.position);
        camera2.direction.set(camera.direction);
        camera2.up.set(camera.up);
        camera2.near = camera.near;
        camera2.far = camera.far;
        camera2.viewportWidth = camera.viewportWidth;
        camera2.viewportHeight = camera.viewportHeight;
        return camera2;
    }

    protected BaseLight createLight(BaseLight baseLight) {
        if (baseLight instanceof DirectionalLight) {
            return new DirectionalLightEx().set((DirectionalLight) baseLight);
        }
        if (baseLight instanceof PointLight) {
            return new PointLightEx().set((PointLight) baseLight);
        }
        if (baseLight instanceof SpotLight) {
            return new SpotLightEx().set((SpotLight) baseLight);
        }
        throw new GdxRuntimeException("unknown light type " + baseLight.getClass().getName());
    }

    public Scene(Model model) {
        this(new ModelInstanceHack(model));
    }

    public Scene(ModelInstance modelInstance) {
        this(modelInstance, modelInstance.animations.size > 0);
    }

    public Scene(ModelInstance modelInstance, boolean z) {
        this.lights = new ObjectMap<>();
        this.cameras = new ObjectMap<>();
        this.modelInstance = modelInstance;
        if (z) {
            this.animationController = new AnimationControllerHack(modelInstance);
        }
        this.animations = new AnimationsPlayer(this);
    }

    public Scene(Model model, boolean z) {
        this(new ModelInstanceHack(model), z);
    }

    @Override // net.mgsx.gltf.scene3d.scene.Updatable
    public void update(Camera camera, float f) {
        this.animations.update(f);
        syncCameras();
        syncLights();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void syncCameras() {
        ObjectMap.Entries<Node, Camera> it = this.cameras.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            Node node = (Node) next.key;
            Camera camera = (Camera) next.value;
            Matrix4 matrix4 = transform;
            matrix4.set(this.modelInstance.transform).mul(node.globalTransform);
            camera.position.setZero().mul(matrix4);
            camera.direction.set(0.0f, 0.0f, -1.0f).rot(matrix4);
            camera.up.set(Vector3.Y).rot(matrix4);
            camera.update();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void syncLights() {
        ObjectMap.Entries<Node, BaseLight> it = this.lights.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            Node node = (Node) next.key;
            BaseLight baseLight = (BaseLight) next.value;
            Matrix4 matrix4 = transform;
            matrix4.set(this.modelInstance.transform).mul(node.globalTransform);
            if (baseLight instanceof DirectionalLight) {
                ((DirectionalLight) baseLight).direction.set(0.0f, 0.0f, -1.0f).rot(matrix4);
            } else if (baseLight instanceof PointLight) {
                ((PointLight) baseLight).position.setZero().mul(matrix4);
            } else if (baseLight instanceof SpotLight) {
                SpotLight spotLight = (SpotLight) baseLight;
                spotLight.position.setZero().mul(matrix4);
                spotLight.direction.set(0.0f, 0.0f, -1.0f).rot(matrix4);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Camera getCamera(String str) {
        ObjectMap.Entries<Node, Camera> it = this.cameras.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            if (str.equals(((Node) next.key).f104id)) {
                return (Camera) next.value;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BaseLight getLight(String str) {
        ObjectMap.Entries<Node, BaseLight> it = this.lights.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            if (str.equals(((Node) next.key).f104id)) {
                return (BaseLight) next.value;
            }
        }
        return null;
    }

    public int getDirectionalLightCount() {
        ObjectMap.Entries<Node, BaseLight> it = this.lights.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().value instanceof DirectionalLight) {
                i++;
            }
        }
        return i;
    }

    @Override // com.badlogic.gdx.graphics.g3d.RenderableProvider
    public void getRenderables(Array<Renderable> array, Pool<Renderable> pool) {
        this.modelInstance.getRenderables(array, pool);
    }
}
