package org.catrobat.catroid.raptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/GameObject.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GameObject {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public String f591id;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f592name;
    public boolean isActive = true;
    public TransformComponent transform = new TransformComponent();
    public List<Component> components = new ArrayList();
    public String parentId = null;
    public List<String> childrenIds = new ArrayList();
    public transient boolean isPrefabInstance = false;

    public GameObject() {
    }

    public GameObject(String name2) {
        this.f591id = name2;
        this.f592name = name2;
    }

    public boolean addComponent(Component component) {
        if (component instanceof KeyframeComponent) {
            KeyframeComponent keyframeComponent = (KeyframeComponent) component;
            if (keyframeComponent.keyframes.isEmpty()) {
                KeyframeData keyframeData = new KeyframeData();
                keyframeData.time = 0.0f;
                keyframeData.position.set(this.transform.position);
                keyframeData.rotation.set(this.transform.rotation);
                keyframeData.scale.set(this.transform.scale);
                keyframeComponent.keyframes.add(keyframeData);
            }
        }
        this.components.add(component);
        return true;
    }

    public <T extends Component> T getComponent(Class<T> type) {
        Iterator<Component> it = this.components.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            if (type.isInstance(t)) {
                return t;
            }
        }
        return null;
    }

    public <T extends Component> boolean hasComponent(Class<T> type) {
        return getComponent(type) != null;
    }

    public <T extends Component> List<T> getComponents(Class<T> type) {
        ArrayList arrayList = new ArrayList();
        for (Component component : this.components) {
            if (type.isInstance(component)) {
                arrayList.add(component);
            }
        }
        return arrayList;
    }
}
