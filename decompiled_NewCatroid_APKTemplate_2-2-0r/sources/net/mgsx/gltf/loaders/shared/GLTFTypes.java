package net.mgsx.gltf.loaders.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.data.camera.GLTFCamera;
import net.mgsx.gltf.data.data.GLTFAccessor;
import net.mgsx.gltf.data.texture.GLTFSampler;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.shared.animation.Interpolation;
import net.mgsx.gltf.scene3d.model.CubicQuaternion;
import net.mgsx.gltf.scene3d.model.CubicVector3;
import net.mgsx.gltf.scene3d.model.CubicWeightVector;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFTypes {
    public static final int C_BYTE = 5120;
    public static final int C_FLOAT = 5126;
    public static final int C_SHORT = 5122;
    public static final int C_UBYTE = 5121;
    public static final int C_UINT = 5125;
    public static final int C_USHORT = 5123;
    public static final String TYPE_MAT2 = "MAT2";
    public static final String TYPE_MAT3 = "MAT3";
    public static final String TYPE_MAT4 = "MAT4";
    public static final String TYPE_SCALAR = "SCALAR";
    public static final String TYPE_VEC2 = "VEC2";
    public static final String TYPE_VEC3 = "VEC3";
    public static final String TYPE_VEC4 = "VEC4";

    public static int mapPrimitiveMode(Integer num) {
        if (num == null) {
            return 4;
        }
        switch (num.intValue()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                throw new GLTFIllegalException("unsupported mode " + num);
        }
    }

    public static Color mapColor(float[] fArr, Color color) {
        if (fArr == null) {
            return new Color(color);
        }
        if (fArr.length < 4) {
            return new Color(fArr[0], fArr[1], fArr[2], 1.0f);
        }
        return new Color(fArr[0], fArr[1], fArr[2], fArr[3]);
    }

    public static Quaternion map(Quaternion quaternion, float[] fArr) {
        return quaternion.set(fArr[0], fArr[1], fArr[2], fArr[3]);
    }

    public static Quaternion map(Quaternion quaternion, float[] fArr, int i) {
        return quaternion.set(fArr[i], fArr[i + 1], fArr[i + 2], fArr[i + 3]);
    }

    public static Vector3 map(Vector3 vector3, float[] fArr) {
        return vector3.set(fArr[0], fArr[1], fArr[2]);
    }

    public static Vector3 map(Vector3 vector3, float[] fArr, int i) {
        return vector3.set(fArr[i], fArr[i + 1], fArr[i + 2]);
    }

    public static CubicVector3 map(CubicVector3 cubicVector3, float[] fArr, int i) {
        cubicVector3.tangentIn.set(fArr[i], fArr[i + 1], fArr[i + 2]);
        cubicVector3.set(fArr[i + 3], fArr[i + 4], fArr[i + 5]);
        cubicVector3.tangentOut.set(fArr[i + 6], fArr[i + 7], fArr[i + 8]);
        return cubicVector3;
    }

    public static CubicQuaternion map(CubicQuaternion cubicQuaternion, float[] fArr, int i) {
        cubicQuaternion.tangentIn.set(fArr[i], fArr[i + 1], fArr[i + 2], fArr[i + 3]);
        cubicQuaternion.set(fArr[i + 4], fArr[i + 5], fArr[i + 6], fArr[i + 7]);
        cubicQuaternion.tangentOut.set(fArr[i + 8], fArr[i + 9], fArr[i + 10], fArr[i + 11]);
        return cubicQuaternion;
    }

    public static WeightVector map(WeightVector weightVector, float[] fArr, int i) {
        for (int i2 = 0; i2 < weightVector.count; i2++) {
            weightVector.values[i2] = fArr[i + i2];
        }
        return weightVector;
    }

    public static CubicWeightVector map(CubicWeightVector cubicWeightVector, float[] fArr, int i) {
        for (int i2 = 0; i2 < cubicWeightVector.count; i2++) {
            cubicWeightVector.tangentIn.values[i2] = fArr[i + i2];
        }
        int i3 = i + cubicWeightVector.count;
        for (int i4 = 0; i4 < cubicWeightVector.count; i4++) {
            cubicWeightVector.values[i4] = fArr[i3 + i4];
        }
        int i5 = i3 + cubicWeightVector.count;
        for (int i6 = 0; i6 < cubicWeightVector.count; i6++) {
            cubicWeightVector.tangentOut.values[i6] = fArr[i5 + i6];
        }
        return cubicWeightVector;
    }

    public static int accessorTypeSize(GLTFAccessor gLTFAccessor) {
        if (TYPE_SCALAR.equals(gLTFAccessor.type)) {
            return 1;
        }
        if (TYPE_VEC2.equals(gLTFAccessor.type)) {
            return 2;
        }
        if (TYPE_VEC3.equals(gLTFAccessor.type)) {
            return 3;
        }
        if (TYPE_VEC4.equals(gLTFAccessor.type) || TYPE_MAT2.equals(gLTFAccessor.type)) {
            return 4;
        }
        if (TYPE_MAT3.equals(gLTFAccessor.type)) {
            return 9;
        }
        if (TYPE_MAT4.equals(gLTFAccessor.type)) {
            return 16;
        }
        throw new GLTFIllegalException("illegal accessor type: " + gLTFAccessor.type);
    }

    public static int accessorComponentTypeSize(GLTFAccessor gLTFAccessor) {
        switch (gLTFAccessor.componentType) {
            case 5120:
            case 5121:
                return 1;
            case 5122:
            case 5123:
                return 2;
            case GL20.GL_INT /* 5124 */:
            default:
                throw new GLTFIllegalException("illegal accessor component type: " + gLTFAccessor.componentType);
            case 5125:
            case 5126:
                return 4;
        }
    }

    public static int accessorStrideSize(GLTFAccessor gLTFAccessor) {
        return accessorTypeSize(gLTFAccessor) * accessorComponentTypeSize(gLTFAccessor);
    }

    public static int accessorSize(GLTFAccessor gLTFAccessor) {
        return accessorStrideSize(gLTFAccessor) * gLTFAccessor.count;
    }

    public static Camera map(GLTFCamera gLTFCamera) {
        if ("perspective".equals(gLTFCamera.type)) {
            float f = gLTFCamera.perspective.znear;
            float fFloatValue = gLTFCamera.perspective.zfar != null ? gLTFCamera.perspective.zfar.floatValue() : 16384.0f * f;
            float width = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
            float fAtan = ((float) Math.atan((Math.tan(((double) gLTFCamera.perspective.yfov) * 0.5d) * ((double) (gLTFCamera.perspective.aspectRatio != null ? gLTFCamera.perspective.aspectRatio.floatValue() : width))) / ((double) width))) * 2.0f;
            PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
            perspectiveCamera.fieldOfView = fAtan * 57.295776f;
            perspectiveCamera.near = f;
            perspectiveCamera.far = fFloatValue;
            perspectiveCamera.viewportWidth = Gdx.graphics.getWidth();
            perspectiveCamera.viewportHeight = Gdx.graphics.getHeight();
            return perspectiveCamera;
        }
        if ("orthographic".equals(gLTFCamera.type)) {
            OrthographicCamera orthographicCamera = new OrthographicCamera();
            orthographicCamera.near = gLTFCamera.orthographic.znear.floatValue();
            orthographicCamera.far = gLTFCamera.orthographic.zfar.floatValue();
            float width2 = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
            orthographicCamera.viewportWidth = gLTFCamera.orthographic.xmag.floatValue();
            orthographicCamera.viewportHeight = gLTFCamera.orthographic.ymag.floatValue() / width2;
            return orthographicCamera;
        }
        throw new GLTFIllegalException("unknow camera type " + gLTFCamera.type);
    }

    public static void mapTextureSampler(TextureDescriptor<Texture> textureDescriptor, GLTFSampler gLTFSampler) {
        textureDescriptor.minFilter = mapTextureMinFilter(gLTFSampler.minFilter);
        textureDescriptor.magFilter = mapTextureMagFilter(gLTFSampler.magFilter);
        textureDescriptor.uWrap = mapTextureWrap(gLTFSampler.wrapS);
        textureDescriptor.vWrap = mapTextureWrap(gLTFSampler.wrapT);
    }

    public static void mapTextureSampler(TextureLoader.TextureParameter textureParameter, GLTFSampler gLTFSampler) {
        textureParameter.minFilter = mapTextureMinFilter(gLTFSampler.minFilter);
        textureParameter.magFilter = mapTextureMagFilter(gLTFSampler.magFilter);
        textureParameter.wrapU = mapTextureWrap(gLTFSampler.wrapS);
        textureParameter.wrapV = mapTextureWrap(gLTFSampler.wrapT);
    }

    public static void mapTextureSampler(TextureLoader.TextureParameter textureParameter) {
        textureParameter.minFilter = mapTextureMinFilter(null);
        textureParameter.magFilter = mapTextureMagFilter(null);
        textureParameter.wrapU = mapTextureWrap(null);
        textureParameter.wrapV = mapTextureWrap(null);
    }

    private static Texture.TextureWrap mapTextureWrap(Integer num) {
        if (num == null) {
            return Texture.TextureWrap.Repeat;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 10497) {
            return Texture.TextureWrap.Repeat;
        }
        if (iIntValue == 33071) {
            return Texture.TextureWrap.ClampToEdge;
        }
        if (iIntValue == 33648) {
            return Texture.TextureWrap.MirroredRepeat;
        }
        throw new GLTFIllegalException("unexpected texture wrap " + num);
    }

    public static Texture.TextureFilter mapTextureMagFilter(Integer num) {
        if (num == null) {
            return Texture.TextureFilter.Linear;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 9728) {
            return Texture.TextureFilter.Nearest;
        }
        if (iIntValue == 9729) {
            return Texture.TextureFilter.Linear;
        }
        throw new GLTFIllegalException("unexpected texture mag filter " + num);
    }

    public static Texture.TextureFilter mapTextureMinFilter(Integer num) {
        if (num == null) {
            return Texture.TextureFilter.Linear;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 9728) {
            return Texture.TextureFilter.Nearest;
        }
        if (iIntValue == 9729) {
            return Texture.TextureFilter.Linear;
        }
        switch (iIntValue) {
            case GL20.GL_NEAREST_MIPMAP_NEAREST /* 9984 */:
                return Texture.TextureFilter.MipMapNearestNearest;
            case GL20.GL_LINEAR_MIPMAP_NEAREST /* 9985 */:
                return Texture.TextureFilter.MipMapLinearNearest;
            case GL20.GL_NEAREST_MIPMAP_LINEAR /* 9986 */:
                return Texture.TextureFilter.MipMapNearestLinear;
            case GL20.GL_LINEAR_MIPMAP_LINEAR /* 9987 */:
                return Texture.TextureFilter.MipMapLinearLinear;
            default:
                throw new GLTFIllegalException("unexpected texture mag filter " + num);
        }
    }

    /* JADX INFO: renamed from: net.mgsx.gltf.loaders.shared.GLTFTypes$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter;

        static {
            int[] iArr = new int[Texture.TextureFilter.values().length];
            $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter = iArr;
            try {
                iArr[Texture.TextureFilter.Nearest.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.Linear.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMapNearestNearest.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMapLinearNearest.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMapNearestLinear.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMapLinearLinear.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static boolean isMipMapFilter(GLTFSampler gLTFSampler) {
        Texture.TextureFilter textureFilterMapTextureMinFilter = mapTextureMinFilter(gLTFSampler.minFilter);
        switch (AnonymousClass1.$SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[textureFilterMapTextureMinFilter.ordinal()]) {
            case 1:
            case 2:
                return false;
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                throw new GLTFIllegalException("unexpected texture min filter " + textureFilterMapTextureMinFilter);
        }
    }

    public static Interpolation mapInterpolation(String str) {
        if (str == null) {
            return Interpolation.LINEAR;
        }
        if ("LINEAR".equals(str)) {
            return Interpolation.LINEAR;
        }
        if ("STEP".equals(str)) {
            return Interpolation.STEP;
        }
        if ("CUBICSPLINE".equals(str)) {
            return Interpolation.CUBICSPLINE;
        }
        throw new GLTFIllegalException("unexpected interpolation type " + str);
    }
}
