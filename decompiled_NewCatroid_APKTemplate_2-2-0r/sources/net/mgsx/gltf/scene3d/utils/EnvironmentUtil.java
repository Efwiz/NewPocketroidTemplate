package net.mgsx.gltf.scene3d.utils;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class EnvironmentUtil {
    public static final String[] FACE_NAMES_FULL = {"right", "left", "top", "bottom", "front", "back"};
    public static final String[] FACE_NAMES_NP = {"px", "nx", "py", "ny", "pz", "nz"};
    public static final String[] FACE_NAMES_NEG_POS = {"posx", "negx", "posy", "negy", "posz", "negz"};

    public static Cubemap createCubemap(FileHandleResolver fileHandleResolver, String str, String str2, String[] strArr) {
        Cubemap cubemap = new Cubemap(fileHandleResolver.resolve(str + strArr[0] + str2), fileHandleResolver.resolve(str + strArr[1] + str2), fileHandleResolver.resolve(str + strArr[2] + str2), fileHandleResolver.resolve(str + strArr[3] + str2), fileHandleResolver.resolve(str + strArr[4] + str2), fileHandleResolver.resolve(str + strArr[5] + str2));
        cubemap.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return cubemap;
    }

    public static Cubemap createCubemap(FileHandleResolver fileHandleResolver, String str, String str2, String str3, int i, String[] strArr) {
        FileHandle[] fileHandleArr = new FileHandle[i * 6];
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < 6; i3++) {
                fileHandleArr[(i2 * 6) + i3] = fileHandleResolver.resolve(str + strArr[i3] + str2 + i2 + str3);
            }
        }
        Cubemap cubemap = new Cubemap(new FacedMultiCubemapData(fileHandleArr, i));
        cubemap.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Linear);
        return cubemap;
    }

    public static int getLightCount(Environment environment) {
        DirectionalLightsAttribute directionalLightsAttribute = (DirectionalLightsAttribute) environment.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
        int i = directionalLightsAttribute != null ? directionalLightsAttribute.lights.size : 0;
        PointLightsAttribute pointLightsAttribute = (PointLightsAttribute) environment.get(PointLightsAttribute.class, PointLightsAttribute.Type);
        if (pointLightsAttribute != null) {
            i += pointLightsAttribute.lights.size;
        }
        SpotLightsAttribute spotLightsAttribute = (SpotLightsAttribute) environment.get(SpotLightsAttribute.class, SpotLightsAttribute.Type);
        return spotLightsAttribute != null ? i + spotLightsAttribute.lights.size : i;
    }
}
