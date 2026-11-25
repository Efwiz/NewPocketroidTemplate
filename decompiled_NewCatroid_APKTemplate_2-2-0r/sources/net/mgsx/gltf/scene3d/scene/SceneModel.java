package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/* JADX INFO: loaded from: classes12.dex */
public class SceneModel implements Disposable {
    public ObjectMap<Node, Camera> cameras = new ObjectMap<>();
    public ObjectMap<Node, BaseLight> lights = new ObjectMap<>();
    public Model model;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f412name;

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.model.dispose();
    }
}
