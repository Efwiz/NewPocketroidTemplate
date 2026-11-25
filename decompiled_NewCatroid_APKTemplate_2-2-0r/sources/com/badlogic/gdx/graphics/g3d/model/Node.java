package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/Node.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Node {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public String f104id;
    public boolean isAnimated;
    protected Node parent;
    public boolean inheritTransform = true;
    public final Vector3 translation = new Vector3();
    public final Quaternion rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    public final Vector3 scale = new Vector3(1.0f, 1.0f, 1.0f);
    public final Matrix4 localTransform = new Matrix4();
    public final Matrix4 globalTransform = new Matrix4();
    public Array<NodePart> parts = new Array<>(2);
    private final Array<Node> children = new Array<>(2);

    public Matrix4 calculateLocalTransform() {
        if (!this.isAnimated) {
            this.localTransform.set(this.translation, this.rotation, this.scale);
        }
        return this.localTransform;
    }

    public Matrix4 calculateWorldTransform() {
        Node node;
        if (this.inheritTransform && (node = this.parent) != null) {
            this.globalTransform.set(node.globalTransform).mul(this.localTransform);
        } else {
            this.globalTransform.set(this.localTransform);
        }
        return this.globalTransform;
    }

    public void calculateTransforms(boolean z) {
        calculateLocalTransform();
        calculateWorldTransform();
        if (z) {
            Array.ArrayIterator<Node> it = this.children.iterator();
            while (it.hasNext()) {
                it.next().calculateTransforms(true);
            }
        }
    }

    public void calculateBoneTransforms(boolean z) {
        Array.ArrayIterator<NodePart> it = this.parts.iterator();
        while (it.hasNext()) {
            NodePart next = it.next();
            if (next.invBoneBindTransforms != null && next.bones != null && next.invBoneBindTransforms.size == next.bones.length) {
                int i = next.invBoneBindTransforms.size;
                for (int i2 = 0; i2 < i; i2++) {
                    next.bones[i2].set(next.invBoneBindTransforms.keys[i2].globalTransform).mul(next.invBoneBindTransforms.values[i2]);
                }
            }
        }
        if (z) {
            Array.ArrayIterator<Node> it2 = this.children.iterator();
            while (it2.hasNext()) {
                it2.next().calculateBoneTransforms(true);
            }
        }
    }

    public BoundingBox calculateBoundingBox(BoundingBox boundingBox) {
        boundingBox.inf();
        return extendBoundingBox(boundingBox);
    }

    public BoundingBox calculateBoundingBox(BoundingBox boundingBox, boolean z) {
        boundingBox.inf();
        return extendBoundingBox(boundingBox, z);
    }

    public BoundingBox extendBoundingBox(BoundingBox boundingBox) {
        return extendBoundingBox(boundingBox, true);
    }

    public BoundingBox extendBoundingBox(BoundingBox boundingBox, boolean z) {
        int i = this.parts.size;
        for (int i2 = 0; i2 < i; i2++) {
            NodePart nodePart = this.parts.get(i2);
            if (nodePart.enabled) {
                MeshPart meshPart = nodePart.meshPart;
                if (z) {
                    meshPart.mesh.extendBoundingBox(boundingBox, meshPart.offset, meshPart.size, this.globalTransform);
                } else {
                    meshPart.mesh.extendBoundingBox(boundingBox, meshPart.offset, meshPart.size);
                }
            }
        }
        int i3 = this.children.size;
        for (int i4 = 0; i4 < i3; i4++) {
            this.children.get(i4).extendBoundingBox(boundingBox);
        }
        return boundingBox;
    }

    public <T extends Node> void attachTo(T t) {
        t.addChild(this);
    }

    public void detach() {
        Node node = this.parent;
        if (node != null) {
            node.removeChild(this);
            this.parent = null;
        }
    }

    public boolean hasChildren() {
        Array<Node> array = this.children;
        return array != null && array.size > 0;
    }

    public int getChildCount() {
        return this.children.size;
    }

    public Node getChild(int i) {
        return this.children.get(i);
    }

    public Node getChild(String str, boolean z, boolean z2) {
        return getNode(this.children, str, z, z2);
    }

    public <T extends Node> int addChild(T t) {
        return insertChild(-1, t);
    }

    public <T extends Node> int addChildren(Iterable<T> iterable) {
        return insertChildren(-1, iterable);
    }

    public <T extends Node> int insertChild(int i, T t) {
        for (Node parent = this; parent != null; parent = parent.getParent()) {
            if (parent == t) {
                throw new GdxRuntimeException("Cannot add a parent as a child");
            }
        }
        Node parent2 = t.getParent();
        if (parent2 != null && !parent2.removeChild(t)) {
            throw new GdxRuntimeException("Could not remove child from its current parent");
        }
        if (i < 0 || i >= this.children.size) {
            i = this.children.size;
            this.children.add(t);
        } else {
            this.children.insert(i, t);
        }
        t.parent = this;
        return i;
    }

    public <T extends Node> int insertChildren(int i, Iterable<T> iterable) {
        if (i < 0 || i > this.children.size) {
            i = this.children.size;
        }
        Iterator<T> it = iterable.iterator();
        int i2 = i;
        while (it.hasNext()) {
            insertChild(i2, it.next());
            i2++;
        }
        return i;
    }

    public <T extends Node> boolean removeChild(T t) {
        if (!this.children.removeValue(t, true)) {
            return false;
        }
        t.parent = null;
        return true;
    }

    public Iterable<Node> getChildren() {
        return this.children;
    }

    public Node getParent() {
        return this.parent;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public Node copy() {
        return new Node().set(this);
    }

    protected Node set(Node node) {
        detach();
        this.f104id = node.f104id;
        this.isAnimated = node.isAnimated;
        this.inheritTransform = node.inheritTransform;
        this.translation.set(node.translation);
        this.rotation.set(node.rotation);
        this.scale.set(node.scale);
        this.localTransform.set(node.localTransform);
        this.globalTransform.set(node.globalTransform);
        this.parts.clear();
        Array.ArrayIterator<NodePart> it = node.parts.iterator();
        while (it.hasNext()) {
            this.parts.add(it.next().copy());
        }
        this.children.clear();
        Iterator<Node> it2 = node.getChildren().iterator();
        while (it2.hasNext()) {
            addChild(it2.next().copy());
        }
        return this;
    }

    public static Node getNode(Array<Node> array, String str, boolean z, boolean z2) {
        int i = array.size;
        if (z2) {
            for (int i2 = 0; i2 < i; i2++) {
                Node node = array.get(i2);
                if (node.f104id.equalsIgnoreCase(str)) {
                    return node;
                }
            }
        } else {
            for (int i3 = 0; i3 < i; i3++) {
                Node node2 = array.get(i3);
                if (node2.f104id.equals(str)) {
                    return node2;
                }
            }
        }
        if (!z) {
            return null;
        }
        for (int i4 = 0; i4 < i; i4++) {
            Node node3 = getNode(array.get(i4).children, str, true, z2);
            if (node3 != null) {
                return node3;
            }
        }
        return null;
    }
}
