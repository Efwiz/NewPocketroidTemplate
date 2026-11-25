package com.badlogic.gdx.math;

import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Octree.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Octree<T> {
    static final Vector3 tmp = new Vector3();
    final Collider<T> collider;
    final int maxItemsPerNode;
    final Pool<Octree<T>.OctreeNode> nodePool = new Pool<Octree<T>.OctreeNode>() { // from class: com.badlogic.gdx.math.Octree.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.badlogic.gdx.utils.Pool
        public Octree<T>.OctreeNode newObject() {
            return new OctreeNode();
        }
    };
    protected Octree<T>.OctreeNode root;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Octree$Collider.class
      classes8.dex
     */
    public interface Collider<T> {
        float intersects(Ray ray, T t);

        boolean intersects(Frustum frustum, T t);

        boolean intersects(BoundingBox boundingBox, T t);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Octree$RayCastResult.class
      classes8.dex
     */
    public static class RayCastResult<T> {
        float distance;
        T geometry;
        float maxDistanceSq = Float.MAX_VALUE;
    }

    public Octree(Vector3 vector3, Vector3 vector4, int i, int i2, Collider<T> collider) {
        this.root = createNode(new Vector3(Math.min(vector3.x, vector4.x), Math.min(vector3.y, vector4.y), Math.min(vector3.z, vector4.z)), new Vector3(Math.max(vector3.x, vector4.x), Math.max(vector3.y, vector4.y), Math.max(vector3.z, vector4.z)), i);
        this.collider = collider;
        this.maxItemsPerNode = i2;
    }

    Octree<T>.OctreeNode createNode(Vector3 vector3, Vector3 vector4, int i) {
        Octree<T>.OctreeNode octreeNodeObtain = this.nodePool.obtain();
        octreeNodeObtain.bounds.set(vector3, vector4);
        octreeNodeObtain.level = i;
        octreeNodeObtain.leaf = true;
        return octreeNodeObtain;
    }

    public void add(T t) {
        this.root.add(t);
    }

    public void remove(T t) {
        this.root.remove(t);
    }

    public void update(T t) {
        this.root.remove(t);
        this.root.add(t);
    }

    public ObjectSet<T> getAll(ObjectSet<T> objectSet) {
        this.root.getAll(objectSet);
        return objectSet;
    }

    public ObjectSet<T> query(BoundingBox boundingBox, ObjectSet<T> objectSet) {
        this.root.query(boundingBox, objectSet);
        return objectSet;
    }

    public ObjectSet<T> query(Frustum frustum, ObjectSet<T> objectSet) {
        this.root.query(frustum, objectSet);
        return objectSet;
    }

    public T rayCast(Ray ray, RayCastResult<T> rayCastResult) {
        rayCastResult.distance = rayCastResult.maxDistanceSq;
        this.root.rayCast(ray, rayCastResult);
        return rayCastResult.geometry;
    }

    public ObjectSet<BoundingBox> getNodesBoxes(ObjectSet<BoundingBox> objectSet) {
        this.root.getBoundingBox(objectSet);
        return objectSet;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Octree$OctreeNode.class
      classes8.dex
     */
    protected class OctreeNode {
        final BoundingBox bounds = new BoundingBox();
        private OctreeNode[] children;
        private final Array<T> geometries;
        boolean leaf;
        int level;

        protected OctreeNode() {
            this.geometries = new Array<>(Math.min(16, Octree.this.maxItemsPerNode));
        }

        private void split() {
            float f = (this.bounds.max.x + this.bounds.min.x) * 0.5f;
            float f2 = (this.bounds.max.y + this.bounds.min.y) * 0.5f;
            float f3 = (this.bounds.max.z + this.bounds.min.z) * 0.5f;
            int i = this.level - 1;
            this.leaf = false;
            if (this.children == null) {
                this.children = new OctreeNode[8];
            }
            this.children[0] = Octree.this.createNode(new Vector3(this.bounds.min.x, f2, f3), new Vector3(f, this.bounds.max.y, this.bounds.max.z), i);
            this.children[1] = Octree.this.createNode(new Vector3(f, f2, f3), new Vector3(this.bounds.max.x, this.bounds.max.y, this.bounds.max.z), i);
            this.children[2] = Octree.this.createNode(new Vector3(f, f2, this.bounds.min.z), new Vector3(this.bounds.max.x, this.bounds.max.y, f3), i);
            this.children[3] = Octree.this.createNode(new Vector3(this.bounds.min.x, f2, this.bounds.min.z), new Vector3(f, this.bounds.max.y, f3), i);
            this.children[4] = Octree.this.createNode(new Vector3(this.bounds.min.x, this.bounds.min.y, f3), new Vector3(f, f2, this.bounds.max.z), i);
            this.children[5] = Octree.this.createNode(new Vector3(f, this.bounds.min.y, f3), new Vector3(this.bounds.max.x, f2, this.bounds.max.z), i);
            this.children[6] = Octree.this.createNode(new Vector3(f, this.bounds.min.y, this.bounds.min.z), new Vector3(this.bounds.max.x, f2, f3), i);
            this.children[7] = Octree.this.createNode(new Vector3(this.bounds.min.x, this.bounds.min.y, this.bounds.min.z), new Vector3(f, f2, f3), i);
            for (OctreeNode octreeNode : this.children) {
                Array.ArrayIterator<T> it = this.geometries.iterator();
                while (it.hasNext()) {
                    octreeNode.add(it.next());
                }
            }
            this.geometries.clear();
        }

        private void merge() {
            clearChildren();
            this.leaf = true;
        }

        private void free() {
            this.geometries.clear();
            if (!this.leaf) {
                clearChildren();
            }
            Octree.this.nodePool.free(this);
        }

        private void clearChildren() {
            for (int i = 0; i < 8; i++) {
                this.children[i].free();
                this.children[i] = null;
            }
        }

        protected void add(T t) {
            if (Octree.this.collider.intersects(this.bounds, t)) {
                int i = 0;
                if (!this.leaf) {
                    OctreeNode[] octreeNodeArr = this.children;
                    int length = octreeNodeArr.length;
                    while (i < length) {
                        octreeNodeArr[i].add(t);
                        i++;
                    }
                    return;
                }
                if (this.geometries.size >= Octree.this.maxItemsPerNode && this.level > 0) {
                    split();
                    OctreeNode[] octreeNodeArr2 = this.children;
                    int length2 = octreeNodeArr2.length;
                    while (i < length2) {
                        octreeNodeArr2[i].add(t);
                        i++;
                    }
                    return;
                }
                this.geometries.add(t);
            }
        }

        protected boolean remove(T t) {
            if (!this.leaf) {
                boolean zRemove = false;
                for (OctreeNode octreeNode : this.children) {
                    zRemove |= octreeNode.remove(t);
                }
                if (zRemove) {
                    ObjectSet<T> objectSet = new ObjectSet<>();
                    for (OctreeNode octreeNode2 : this.children) {
                        octreeNode2.getAll(objectSet);
                    }
                    if (objectSet.size <= Octree.this.maxItemsPerNode) {
                        ObjectSet.ObjectSetIterator<T> it = objectSet.iterator();
                        while (it.hasNext()) {
                            this.geometries.add(it.next());
                        }
                        merge();
                    }
                }
                return zRemove;
            }
            return this.geometries.removeValue(t, true);
        }

        protected boolean isLeaf() {
            return this.leaf;
        }

        protected void query(BoundingBox boundingBox, ObjectSet<T> objectSet) {
            if (boundingBox.intersects(this.bounds)) {
                if (!this.leaf) {
                    for (OctreeNode octreeNode : this.children) {
                        octreeNode.query(boundingBox, objectSet);
                    }
                    return;
                }
                Array.ArrayIterator<T> it = this.geometries.iterator();
                while (it.hasNext()) {
                    T next = it.next();
                    if (Octree.this.collider.intersects(this.bounds, next)) {
                        objectSet.add(next);
                    }
                }
            }
        }

        protected void query(Frustum frustum, ObjectSet<T> objectSet) {
            if (Intersector.intersectFrustumBounds(frustum, this.bounds)) {
                if (!this.leaf) {
                    for (OctreeNode octreeNode : this.children) {
                        octreeNode.query(frustum, objectSet);
                    }
                    return;
                }
                Array.ArrayIterator<T> it = this.geometries.iterator();
                while (it.hasNext()) {
                    T next = it.next();
                    if (Octree.this.collider.intersects(frustum, next)) {
                        objectSet.add(next);
                    }
                }
            }
        }

        protected void rayCast(Ray ray, RayCastResult<T> rayCastResult) {
            if (Intersector.intersectRayBounds(ray, this.bounds, Octree.tmp) && Octree.tmp.dst2(ray.origin) < rayCastResult.maxDistanceSq) {
                if (!this.leaf) {
                    for (OctreeNode octreeNode : this.children) {
                        octreeNode.rayCast(ray, rayCastResult);
                    }
                    return;
                }
                Array.ArrayIterator<T> it = this.geometries.iterator();
                while (it.hasNext()) {
                    T next = it.next();
                    float fIntersects = Octree.this.collider.intersects(ray, next);
                    if (rayCastResult.geometry == null || fIntersects < rayCastResult.distance) {
                        rayCastResult.geometry = next;
                        rayCastResult.distance = fIntersects;
                    }
                }
            }
        }

        protected void getAll(ObjectSet<T> objectSet) {
            if (!this.leaf) {
                for (OctreeNode octreeNode : this.children) {
                    octreeNode.getAll(objectSet);
                }
            }
            objectSet.addAll(this.geometries);
        }

        protected void getBoundingBox(ObjectSet<BoundingBox> objectSet) {
            if (!this.leaf) {
                for (OctreeNode octreeNode : this.children) {
                    octreeNode.getBoundingBox(objectSet);
                }
            }
            objectSet.add(this.bounds);
        }
    }
}
