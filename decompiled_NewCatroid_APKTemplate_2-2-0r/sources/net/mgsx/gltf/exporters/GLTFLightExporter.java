package net.mgsx.gltf.exporters;

import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.data.GLTFExtensions;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import net.mgsx.gltf.data.scene.GLTFNode;
import net.mgsx.gltf.loaders.exceptions.GLTFRuntimeException;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.lights.PointLightEx;
import net.mgsx.gltf.scene3d.lights.SpotLightEx;

/* JADX INFO: loaded from: classes12.dex */
class GLTFLightExporter {
    private GLTFExporter base;

    public GLTFLightExporter(GLTFExporter gLTFExporter) {
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
    public void export(ObjectMap<Node, BaseLight> objectMap) {
        this.base.useExtension(KHRLightsPunctual.EXT, true);
        ObjectMap.Entries<Node, BaseLight> it = objectMap.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            int iIndexOf = this.base.nodeMapping.indexOf(next.key, true);
            if (iIndexOf < 0) {
                throw new GLTFRuntimeException("node not found");
            }
            GLTFNode gLTFNode = this.base.root.nodes.get(iIndexOf);
            if (this.base.root.extensions == null) {
                this.base.root.extensions = new GLTFExtensions();
            }
            KHRLightsPunctual.GLTFLights gLTFLights = (KHRLightsPunctual.GLTFLights) this.base.root.extensions.get(KHRLightsPunctual.GLTFLights.class, KHRLightsPunctual.EXT);
            if (gLTFLights == null) {
                GLTFExtensions gLTFExtensions = this.base.root.extensions;
                KHRLightsPunctual.GLTFLights gLTFLights2 = new KHRLightsPunctual.GLTFLights();
                gLTFExtensions.set(KHRLightsPunctual.EXT, gLTFLights2);
                gLTFLights = gLTFLights2;
            }
            if (gLTFLights.lights == null) {
                gLTFLights.lights = new Array<>();
            }
            KHRLightsPunctual.GLTFLight map = map(new KHRLightsPunctual.GLTFLight(), (BaseLight) next.value);
            map.f409name = gLTFNode.f408name;
            gLTFLights.lights.add(map);
            if (gLTFNode.extensions == null) {
                gLTFNode.extensions = new GLTFExtensions();
            }
            KHRLightsPunctual.GLTFLightNode gLTFLightNode = (KHRLightsPunctual.GLTFLightNode) gLTFNode.extensions.get(KHRLightsPunctual.GLTFLightNode.class, KHRLightsPunctual.EXT);
            if (gLTFLightNode == null) {
                GLTFExtensions gLTFExtensions2 = gLTFNode.extensions;
                KHRLightsPunctual.GLTFLightNode gLTFLightNode2 = new KHRLightsPunctual.GLTFLightNode();
                gLTFExtensions2.set(KHRLightsPunctual.EXT, gLTFLightNode2);
                gLTFLightNode = gLTFLightNode2;
            }
            gLTFLightNode.light = Integer.valueOf(gLTFLights.lights.size - 1);
        }
    }

    public static KHRLightsPunctual.GLTFLight map(KHRLightsPunctual.GLTFLight gLTFLight, BaseLight baseLight) {
        float f;
        if (baseLight instanceof DirectionalLight) {
            gLTFLight.type = KHRLightsPunctual.GLTFLight.TYPE_DIRECTIONAL;
            if (baseLight instanceof DirectionalLightEx) {
                gLTFLight.intensity = ((DirectionalLightEx) baseLight).intensity;
            } else {
                gLTFLight.intensity = 1.0f;
            }
            f = 1.0f;
        } else {
            f = 10.0f;
            if (baseLight instanceof PointLight) {
                gLTFLight.type = KHRLightsPunctual.GLTFLight.TYPE_POINT;
                if (baseLight instanceof PointLightEx) {
                    PointLightEx pointLightEx = (PointLightEx) baseLight;
                    gLTFLight.intensity = pointLightEx.intensity;
                    gLTFLight.range = pointLightEx.range;
                } else {
                    gLTFLight.intensity = 1.0f;
                }
            } else if (baseLight instanceof SpotLight) {
                gLTFLight.type = KHRLightsPunctual.GLTFLight.TYPE_SPOT;
                gLTFLight.spot = new KHRLightsPunctual.GLTFSpotLight();
                if (baseLight instanceof SpotLightEx) {
                    SpotLightEx spotLightEx = (SpotLightEx) baseLight;
                    gLTFLight.intensity = spotLightEx.intensity;
                    gLTFLight.range = spotLightEx.range;
                } else {
                    gLTFLight.intensity = 1.0f;
                }
                SpotLight spotLight = (SpotLight) baseLight;
                float f2 = 1.0f / spotLight.exponent;
                float f3 = (-spotLight.cutoffAngle) / spotLight.exponent;
                gLTFLight.spot.outerConeAngle = (float) Math.acos(f3);
                gLTFLight.spot.innerConeAngle = (float) Math.acos(f3 + f2);
            } else {
                throw new GLTFUnsupportedException("unsupported light type " + baseLight.getClass());
            }
        }
        gLTFLight.color = GLTFExportTypes.rgb(baseLight.color.cpy().mul(1.0f / gLTFLight.intensity));
        gLTFLight.intensity *= f;
        return gLTFLight;
    }
}
