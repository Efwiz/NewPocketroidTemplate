package net.mgsx.gltf.exporters;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import java.nio.FloatBuffer;
import net.mgsx.gltf.data.data.GLTFAccessor;
import net.mgsx.gltf.data.scene.GLTFNode;
import net.mgsx.gltf.data.scene.GLTFSkin;
import net.mgsx.gltf.loaders.shared.GLTFTypes;

/* JADX INFO: loaded from: classes12.dex */
class GLTFSkinExporter {
    private final GLTFExporter base;

    public GLTFSkinExporter(GLTFExporter gLTFExporter) {
        this.base = gLTFExporter;
    }

    public void export() {
        for (int i = 0; i < this.base.nodeMapping.size; i++) {
            Node node = this.base.nodeMapping.get(i);
            GLTFNode gLTFNode = this.base.root.nodes.get(i);
            if (gLTFNode.skin == null && node.parts != null) {
                Array.ArrayIterator<NodePart> it = node.parts.iterator();
                while (it.hasNext()) {
                    NodePart next = it.next();
                    if (next.invBoneBindTransforms != null) {
                        GLTFSkin gLTFSkin = new GLTFSkin();
                        if (this.base.root.skins == null) {
                            this.base.root.skins = new Array<>();
                        }
                        this.base.root.skins.add(gLTFSkin);
                        gLTFNode.skin = Integer.valueOf(this.base.root.skins.size - 1);
                        gLTFSkin.joints = new Array<>();
                        FloatBuffer floatBufferBeginFloats = this.base.binManager.beginFloats(next.invBoneBindTransforms.size * 16);
                        for (ObjectMap.Entry<Node, Matrix4> entry : next.invBoneBindTransforms) {
                            gLTFSkin.joints.add(Integer.valueOf(this.base.nodeMapping.indexOf(entry.key, true)));
                            floatBufferBeginFloats.put(entry.value.val);
                        }
                        GLTFAccessor gLTFAccessorObtainAccessor = this.base.obtainAccessor();
                        gLTFAccessorObtainAccessor.bufferView = Integer.valueOf(this.base.binManager.end());
                        gLTFAccessorObtainAccessor.type = GLTFTypes.TYPE_MAT4;
                        gLTFAccessorObtainAccessor.componentType = 5126;
                        gLTFAccessorObtainAccessor.count = next.invBoneBindTransforms.size;
                        gLTFSkin.inverseBindMatrices = Integer.valueOf(this.base.root.accessors.size - 1);
                    }
                }
            }
        }
    }
}
