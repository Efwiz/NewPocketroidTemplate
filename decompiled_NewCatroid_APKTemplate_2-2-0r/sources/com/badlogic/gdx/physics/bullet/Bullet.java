package com.badlogic.gdx.physics.bullet;

import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import com.badlogic.gdx.physics.bullet.linearmath.LinearMath;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: loaded from: classes8.dex */
public class Bullet {
    public static final int VERSION = 287;
    protected static boolean enableLogging = true;
    protected static boolean useRefCounting = false;
    private static final Pool<ShapePart> shapePartPool = new Pool<ShapePart>() { // from class: com.badlogic.gdx.physics.bullet.Bullet.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public ShapePart newObject() {
            return new ShapePart();
        }
    };
    private static final Array<ShapePart> shapePartArray = new Array<>();
    private static final Matrix4 idt = new Matrix4();
    private static final Matrix4 tmpM = new Matrix4();

    public static void init() {
        init(false);
    }

    public static void init(boolean z) {
        init(z, true);
    }

    public static void init(boolean z, boolean z2) {
        useRefCounting = z;
        enableLogging = z2;
        new SharedLibraryLoader().load("gdx-bullet");
        int iBtGetVersion = LinearMath.btGetVersion();
        if (iBtGetVersion != 287) {
            throw new GdxRuntimeException("Bullet binaries version (" + iBtGetVersion + ") does not match source version (287)");
        }
    }

    protected static class ShapePart {
        public Array<MeshPart> parts = new Array<>();
        public Matrix4 transform = new Matrix4();

        protected ShapePart() {
        }
    }

    public static void getShapeParts(Node node, boolean z, Array<ShapePart> array, int i, Pool<ShapePart> pool) {
        ShapePart shapePartObtain;
        Matrix4 matrix4 = z ? node.localTransform : idt;
        boolean z2 = false;
        if (node.parts.size > 0) {
            int i2 = array.size;
            int i3 = i;
            while (true) {
                if (i3 >= i2) {
                    shapePartObtain = null;
                    break;
                }
                shapePartObtain = array.get(i3);
                if (Arrays.equals(shapePartObtain.transform.val, matrix4.val)) {
                    break;
                } else {
                    i3++;
                }
            }
            if (shapePartObtain == null) {
                shapePartObtain = pool.obtain();
                shapePartObtain.parts.clear();
                shapePartObtain.transform.set(matrix4);
                array.add(shapePartObtain);
            }
            int i4 = node.parts.size;
            for (int i5 = 0; i5 < i4; i5++) {
                shapePartObtain.parts.add(node.parts.get(i5).meshPart);
            }
        }
        if (node.hasChildren()) {
            if (z && !Arrays.equals(matrix4.val, idt.val)) {
                z2 = true;
            }
            if (z2) {
                i = array.size;
            }
            getShapeParts(node.getChildren(), array, i, pool);
            if (z2) {
                int i6 = array.size;
                while (i < i6) {
                    ShapePart shapePart = array.get(i);
                    Matrix4 matrix5 = tmpM;
                    matrix5.set(shapePart.transform);
                    shapePart.transform.set(matrix4).mul(matrix5);
                    i++;
                }
            }
        }
    }

    public static <T extends Node> void getShapeParts(Iterable<T> iterable, Array<ShapePart> array, int i, Pool<ShapePart> pool) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            getShapeParts(it.next(), true, array, i, pool);
        }
    }

    public static btCollisionShape obtainStaticNodeShape(Node node, boolean z) {
        Array<ShapePart> array = shapePartArray;
        Pool<ShapePart> pool = shapePartPool;
        getShapeParts(node, z, array, 0, pool);
        btCollisionShape btcollisionshapeObtainStaticShape = obtainStaticShape(array);
        pool.freeAll(array);
        array.clear();
        return btcollisionshapeObtainStaticShape;
    }

    public static btCollisionShape obtainStaticNodeShape(Array<Node> array) {
        Array<ShapePart> array2 = shapePartArray;
        Pool<ShapePart> pool = shapePartPool;
        getShapeParts(array, array2, 0, pool);
        btCollisionShape btcollisionshapeObtainStaticShape = obtainStaticShape(array2);
        pool.freeAll(array2);
        array2.clear();
        return btcollisionshapeObtainStaticShape;
    }

    public static btCollisionShape obtainStaticShape(Array<ShapePart> array) {
        if (array.size == 0) {
            return null;
        }
        if (array.size == 1 && Arrays.equals(array.get(0).transform.val, idt.val)) {
            return btBvhTriangleMeshShape.obtain(array.get(0).parts);
        }
        btCompoundShape btcompoundshape = new btCompoundShape();
        btcompoundshape.obtain();
        int i = array.size;
        for (int i2 = 0; i2 < i; i2++) {
            btBvhTriangleMeshShape btbvhtrianglemeshshapeObtain = btBvhTriangleMeshShape.obtain(array.get(i2).parts);
            btcompoundshape.addChildShape(array.get(i2).transform, btbvhtrianglemeshshapeObtain);
            btbvhtrianglemeshshapeObtain.release();
        }
        return btcompoundshape;
    }
}
