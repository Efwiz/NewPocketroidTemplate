package net.mgsx.gltf.demo.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

/* JADX INFO: loaded from: classes12.dex */
public class IBLStudio {
    public static final IBLPreset defaultPreset;
    public static final IBLPreset indoor;
    public static final IBLPreset outdoor;
    public static final IBLPreset[] presets;
    public static final IBLPreset space;

    public static abstract class IBLPreset {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final String f411name;

        public abstract IBLBuilder createBuilder(DirectionalLight directionalLight);

        public IBLPreset(String str) {
            this.f411name = str;
        }

        public String toString() {
            return this.f411name;
        }
    }

    static {
        IBLPreset iBLPreset = new IBLPreset("default") { // from class: net.mgsx.gltf.demo.model.IBLStudio.1
            @Override // net.mgsx.gltf.demo.model.IBLStudio.IBLPreset
            public IBLBuilder createBuilder(DirectionalLight directionalLight) {
                return null;
            }
        };
        defaultPreset = iBLPreset;
        IBLPreset iBLPreset2 = new IBLPreset("outdoor") { // from class: net.mgsx.gltf.demo.model.IBLStudio.2
            @Override // net.mgsx.gltf.demo.model.IBLStudio.IBLPreset
            public IBLBuilder createBuilder(DirectionalLight directionalLight) {
                return IBLBuilder.createOutdoor(directionalLight);
            }
        };
        outdoor = iBLPreset2;
        IBLPreset iBLPreset3 = new IBLPreset("indoor") { // from class: net.mgsx.gltf.demo.model.IBLStudio.3
            @Override // net.mgsx.gltf.demo.model.IBLStudio.IBLPreset
            public IBLBuilder createBuilder(DirectionalLight directionalLight) {
                return IBLBuilder.createIndoor(directionalLight);
            }
        };
        indoor = iBLPreset3;
        IBLPreset iBLPreset4 = new IBLPreset("space") { // from class: net.mgsx.gltf.demo.model.IBLStudio.4
            @Override // net.mgsx.gltf.demo.model.IBLStudio.IBLPreset
            public IBLBuilder createBuilder(DirectionalLight directionalLight) {
                IBLBuilder iBLBuilderCreateCustom = IBLBuilder.createCustom(directionalLight);
                iBLBuilderCreateCustom.lights.first().exponent = 100.0f;
                iBLBuilderCreateCustom.lights.first().color.set(Color.WHITE);
                return iBLBuilderCreateCustom;
            }
        };
        space = iBLPreset4;
        presets = new IBLPreset[]{iBLPreset, iBLPreset2, iBLPreset3, iBLPreset4};
    }
}
