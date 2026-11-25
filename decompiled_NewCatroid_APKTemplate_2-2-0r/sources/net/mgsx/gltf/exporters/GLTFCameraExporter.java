package net.mgsx.gltf.exporters;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.data.camera.GLTFCamera;
import net.mgsx.gltf.data.camera.GLTFOrthographic;
import net.mgsx.gltf.data.camera.GLTFPerspective;
import net.mgsx.gltf.data.scene.GLTFNode;
import net.mgsx.gltf.loaders.exceptions.GLTFRuntimeException;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;

/* JADX INFO: loaded from: classes12.dex */
class GLTFCameraExporter {
    private GLTFExporter base;

    public GLTFCameraExporter(GLTFExporter gLTFExporter) {
        this.base = gLTFExporter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void export(ObjectMap<Node, Camera> objectMap) {
        ObjectMap.Entries<Node, Camera> it = objectMap.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            int iIndexOf = this.base.nodeMapping.indexOf(next.key, true);
            if (iIndexOf < 0) {
                throw new GLTFRuntimeException("node not found");
            }
            GLTFNode gLTFNode = this.base.root.nodes.get(iIndexOf);
            if (this.base.root.cameras == null) {
                this.base.root.cameras = new Array<>();
            }
            gLTFNode.camera = Integer.valueOf(this.base.root.cameras.size);
            this.base.root.cameras.add(export((Camera) next.value));
        }
    }

    private GLTFCamera export(Camera camera) {
        GLTFCamera gLTFCamera = new GLTFCamera();
        if (camera instanceof PerspectiveCamera) {
            gLTFCamera.type = "perspective";
            gLTFCamera.perspective = new GLTFPerspective();
            gLTFCamera.perspective.yfov = ((PerspectiveCamera) camera).fieldOfView * 0.017453292f;
            gLTFCamera.perspective.znear = camera.near;
            gLTFCamera.perspective.zfar = Float.valueOf(camera.far);
            gLTFCamera.perspective.aspectRatio = Float.valueOf(camera.viewportWidth / camera.viewportHeight);
        } else if (camera instanceof OrthographicCamera) {
            OrthographicCamera orthographicCamera = (OrthographicCamera) camera;
            gLTFCamera.type = "orthographic";
            gLTFCamera.orthographic = new GLTFOrthographic();
            gLTFCamera.orthographic.znear = Float.valueOf(camera.near);
            gLTFCamera.orthographic.zfar = Float.valueOf(camera.far);
            gLTFCamera.orthographic.xmag = Float.valueOf(camera.viewportWidth * orthographicCamera.zoom);
            gLTFCamera.orthographic.ymag = Float.valueOf(camera.viewportHeight * orthographicCamera.zoom);
        } else {
            throw new GLTFUnsupportedException("unsupported camera type " + camera.getClass());
        }
        return gLTFCamera;
    }
}
