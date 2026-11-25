package net.mgsx.gltf.loaders.shared.scene;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import java.nio.FloatBuffer;
import net.mgsx.gltf.data.scene.GLTFNode;
import net.mgsx.gltf.data.scene.GLTFSkin;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.shared.data.DataResolver;

/* JADX INFO: loaded from: classes12.dex */
public class SkinLoader {
    private int maxBones;

    public void load(Array<GLTFSkin> array, Array<GLTFNode> array2, NodeResolver nodeResolver, DataResolver dataResolver) {
        if (array2 != null) {
            for (int i = 0; i < array2.size; i++) {
                GLTFNode gLTFNode = array2.get(i);
                if (gLTFNode.skin != null) {
                    load(array.get(gLTFNode.skin.intValue()), gLTFNode, nodeResolver.get(i), nodeResolver, dataResolver);
                }
            }
        }
    }

    private void load(GLTFSkin gLTFSkin, GLTFNode gLTFNode, Node node, NodeResolver nodeResolver, DataResolver dataResolver) {
        Array array = new Array();
        Array array2 = new Array();
        int i = gLTFSkin.joints.size;
        this.maxBones = Math.max(this.maxBones, i);
        FloatBuffer bufferFloat = dataResolver.getBufferFloat(gLTFSkin.inverseBindMatrices.intValue());
        for (int i2 = 0; i2 < i; i2++) {
            float[] fArr = new float[16];
            bufferFloat.get(fArr);
            array.add(new Matrix4(fArr));
        }
        array2.addAll(gLTFSkin.joints);
        if (array.size > 0) {
            for (int i3 = 0; i3 < node.parts.size; i3++) {
                NodePart nodePart = node.parts.get(i3);
                if (nodePart.bones != null) {
                    NodePart nodePart2 = new NodePart();
                    nodePart2.material = nodePart.material;
                    nodePart2.meshPart = nodePart.meshPart;
                    node.parts.set(i3, nodePart2);
                    nodePart = nodePart2;
                }
                nodePart.bones = new Matrix4[array.size];
                nodePart.invBoneBindTransforms = new ArrayMap<>();
                for (int i4 = 0; i4 < array2.size; i4++) {
                    nodePart.bones[i4] = new Matrix4().idt();
                    int iIntValue = ((Integer) array2.get(i4)).intValue();
                    Node node2 = nodeResolver.get(iIntValue);
                    if (node2 == null) {
                        throw new GLTFIllegalException("node not found for bone: " + iIntValue);
                    }
                    nodePart.invBoneBindTransforms.put(node2, (Matrix4) array.get(i4));
                }
            }
        }
    }

    public int getMaxBones() {
        return this.maxBones;
    }
}
