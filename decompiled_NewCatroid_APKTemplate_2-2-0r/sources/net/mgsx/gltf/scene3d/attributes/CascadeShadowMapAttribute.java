package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import net.mgsx.gltf.scene3d.scene.CascadeShadowMap;

/* JADX INFO: loaded from: classes12.dex */
public class CascadeShadowMapAttribute extends Attribute {
    public static final String Alias = "CSM";
    public static final long Type = register(Alias);
    public final CascadeShadowMap cascadeShadowMap;

    public CascadeShadowMapAttribute(CascadeShadowMap cascadeShadowMap) {
        super(Type);
        this.cascadeShadowMap = cascadeShadowMap;
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        return (int) (this.type - attribute.type);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new CascadeShadowMapAttribute(this.cascadeShadowMap);
    }
}
