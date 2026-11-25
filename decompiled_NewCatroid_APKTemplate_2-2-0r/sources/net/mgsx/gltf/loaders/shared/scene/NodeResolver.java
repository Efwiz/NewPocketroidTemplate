package net.mgsx.gltf.loaders.shared.scene;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.ObjectMap;

/* JADX INFO: loaded from: classes12.dex */
public class NodeResolver {
    ObjectMap<Integer, Node> nodeMap = new ObjectMap<>();

    public Node get(int i) {
        return this.nodeMap.get(Integer.valueOf(i));
    }

    public void put(int i, Node node) {
        this.nodeMap.put(Integer.valueOf(i), node);
    }
}
