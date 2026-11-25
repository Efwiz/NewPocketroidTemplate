package net.mgsx.gltf.scene3d.utils;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import java.util.Iterator;

/* JADX INFO: loaded from: classes12.dex */
public class EnvironmentCache extends Environment {
    public void setCache(Environment environment) {
        this.mask = environment.getMask();
        this.attributes.clear();
        Iterator<Attribute> it = environment.iterator();
        while (it.hasNext()) {
            this.attributes.add(it.next());
        }
        this.shadowMap = environment.shadowMap;
        this.sorted = true;
    }

    public void replaceCache(Attribute attribute) {
        this.attributes.set(indexOf(attribute.type), attribute);
    }
}
