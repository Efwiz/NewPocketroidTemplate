package net.mgsx.gltf.demo.util;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.Array;

/* JADX INFO: loaded from: classes12.dex */
public class NodeUtil {
    public static Array<Node> getAllNodes(Array<Node> array, ModelInstance modelInstance) {
        return getAllNodes(array, modelInstance.nodes);
    }

    public static Array<Node> getAllNodes(Array<Node> array, Array<Node> array2) {
        Array.ArrayIterator<Node> it = array2.iterator();
        while (it.hasNext()) {
            getAllNodes(array, it.next());
        }
        return array;
    }

    public static Array<Node> getAllNodes(Array<Node> array, Node node) {
        array.add(node);
        for (int i = 0; i < node.getChildCount(); i++) {
            getAllNodes(array, node.getChild(i));
        }
        return array;
    }

    public static Array<Node> getAllNodes(Array<Node> array, Model model) {
        return getAllNodes(array, model.nodes);
    }
}
