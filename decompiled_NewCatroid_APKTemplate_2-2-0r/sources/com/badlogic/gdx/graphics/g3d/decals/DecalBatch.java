package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SortedIntList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/decals/DecalBatch.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DecalBatch implements Disposable {
    private static final int DEFAULT_SIZE = 1000;
    private final SortedIntList<Array<Decal>> groupList;
    private final Pool<Array<Decal>> groupPool;
    private GroupStrategy groupStrategy;
    private Mesh mesh;
    private final Array<Array<Decal>> usedGroups;
    private float[] vertices;

    public DecalBatch(GroupStrategy groupStrategy) {
        this(1000, groupStrategy);
    }

    public DecalBatch(int i, GroupStrategy groupStrategy) {
        this.groupList = new SortedIntList<>();
        this.groupPool = new Pool<Array<Decal>>(16) { // from class: com.badlogic.gdx.graphics.g3d.decals.DecalBatch.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.badlogic.gdx.utils.Pool
            public Array<Decal> newObject() {
                return new Array<>(false, 100);
            }
        };
        this.usedGroups = new Array<>(16);
        initialize(i);
        setGroupStrategy(groupStrategy);
    }

    public void setGroupStrategy(GroupStrategy groupStrategy) {
        this.groupStrategy = groupStrategy;
    }

    public void initialize(int i) {
        this.vertices = new float[i * 24];
        Mesh.VertexDataType vertexDataType = Mesh.VertexDataType.VertexArray;
        if (Gdx.gl30 != null) {
            vertexDataType = Mesh.VertexDataType.VertexBufferObjectWithVAO;
        }
        int i2 = i * 4;
        int i3 = i * 6;
        int i4 = 0;
        this.mesh = new Mesh(vertexDataType, false, i2, i3, new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"));
        short[] sArr = new short[i3];
        int i5 = 0;
        while (i4 < i3) {
            sArr[i4] = (short) i5;
            short s = (short) (i5 + 2);
            sArr[i4 + 1] = s;
            short s2 = (short) (i5 + 1);
            sArr[i4 + 2] = s2;
            sArr[i4 + 3] = s2;
            sArr[i4 + 4] = s;
            sArr[i4 + 5] = (short) (i5 + 3);
            i4 += 6;
            i5 += 4;
        }
        this.mesh.setIndices(sArr);
    }

    public int getSize() {
        return this.vertices.length / 24;
    }

    public void add(Decal decal) {
        int iDecideGroup = this.groupStrategy.decideGroup(decal);
        Array<Decal> arrayObtain = this.groupList.get(iDecideGroup);
        if (arrayObtain == null) {
            arrayObtain = this.groupPool.obtain();
            arrayObtain.clear();
            this.usedGroups.add(arrayObtain);
            this.groupList.insert(iDecideGroup, arrayObtain);
        }
        arrayObtain.add(decal);
    }

    public void flush() {
        render();
        clear();
    }

    protected void render() {
        this.groupStrategy.beforeGroups();
        for (SortedIntList.Node<Array<Decal>> node : this.groupList) {
            this.groupStrategy.beforeGroup(node.index, node.value);
            render(this.groupStrategy.getGroupShader(node.index), node.value);
            this.groupStrategy.afterGroup(node.index);
        }
        this.groupStrategy.afterGroups();
    }

    private void render(ShaderProgram shaderProgram, Array<Decal> array) {
        int length;
        Array.ArrayIterator<Decal> it = array.iterator();
        DecalMaterial decalMaterial = null;
        loop0: while (true) {
            length = 0;
            do {
                if (!it.hasNext()) {
                    break loop0;
                }
                Decal next = it.next();
                if (decalMaterial == null || !decalMaterial.equals(next.getMaterial())) {
                    if (length > 0) {
                        flush(shaderProgram, length);
                        length = 0;
                    }
                    next.material.set();
                    decalMaterial = next.material;
                }
                next.update();
                System.arraycopy(next.vertices, 0, this.vertices, length, next.vertices.length);
                length += next.vertices.length;
            } while (length != this.vertices.length);
            flush(shaderProgram, length);
        }
        if (length > 0) {
            flush(shaderProgram, length);
        }
    }

    protected void flush(ShaderProgram shaderProgram, int i) {
        this.mesh.setVertices(this.vertices, 0, i);
        this.mesh.render(shaderProgram, 4, 0, i / 4);
    }

    protected void clear() {
        this.groupList.clear();
        this.groupPool.freeAll(this.usedGroups);
        this.usedGroups.clear();
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        clear();
        this.vertices = null;
        this.mesh.dispose();
    }
}
