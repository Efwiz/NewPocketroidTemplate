package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ArrayMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/NodePart.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class NodePart {
    public Matrix4[] bones;
    public boolean enabled = true;
    public ArrayMap<Node, Matrix4> invBoneBindTransforms;
    public Material material;
    public MeshPart meshPart;

    public NodePart() {
    }

    public NodePart(MeshPart meshPart, Material material) {
        this.meshPart = meshPart;
        this.material = material;
    }

    public Renderable setRenderable(Renderable renderable) {
        renderable.material = this.material;
        renderable.meshPart.set(this.meshPart);
        renderable.bones = this.bones;
        return renderable;
    }

    public NodePart copy() {
        return new NodePart().set(this);
    }

    protected NodePart set(NodePart nodePart) {
        this.meshPart = new MeshPart(nodePart.meshPart);
        this.material = nodePart.material;
        this.enabled = nodePart.enabled;
        if (nodePart.invBoneBindTransforms == null) {
            this.invBoneBindTransforms = null;
            this.bones = null;
        } else {
            ArrayMap<Node, Matrix4> arrayMap = this.invBoneBindTransforms;
            if (arrayMap == null) {
                this.invBoneBindTransforms = new ArrayMap<>(true, nodePart.invBoneBindTransforms.size, Node.class, Matrix4.class);
            } else {
                arrayMap.clear();
            }
            this.invBoneBindTransforms.putAll(nodePart.invBoneBindTransforms);
            Matrix4[] matrix4Arr = this.bones;
            if (matrix4Arr == null || matrix4Arr.length != this.invBoneBindTransforms.size) {
                this.bones = new Matrix4[this.invBoneBindTransforms.size];
            }
            int i = 0;
            while (true) {
                Matrix4[] matrix4Arr2 = this.bones;
                if (i >= matrix4Arr2.length) {
                    break;
                }
                if (matrix4Arr2[i] == null) {
                    matrix4Arr2[i] = new Matrix4();
                }
                i++;
            }
        }
        return this;
    }
}
