package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

/* JADX INFO: loaded from: classes12.dex */
public class Vector4UI extends Table {
    private Array<Slider> sliders;
    public Color value;

    public Vector4UI(Skin skin, Color color) {
        super(skin);
        this.sliders = new Array<>();
        this.value = color;
        for (final int i = 0; i < 4; i++) {
            final Slider slider = new Slider(0.0f, 1.0f, 0.01f, false, skin);
            add(name(i));
            add(slider).row();
            slider.setValue(get(i));
            slider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.Vector4UI.1
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    Vector4UI.this.set(i, slider.getValue());
                }
            });
            this.sliders.add(slider);
        }
    }

    private String name(int i) {
        if (i == 0) {
            return "x";
        }
        if (i == 1) {
            return "y";
        }
        if (i == 2) {
            return CompressorStreamFactory.Z;
        }
        if (i != 3) {
            return null;
        }
        return "w";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set(int i, float f) {
        if (i == 0) {
            this.value.r = f;
            return;
        }
        if (i == 1) {
            this.value.g = f;
        } else if (i == 2) {
            this.value.b = f;
        } else {
            if (i != 3) {
                return;
            }
            this.value.a = f;
        }
    }

    public float get(int i) {
        if (i == 0) {
            return this.value.r;
        }
        if (i == 1) {
            return this.value.g;
        }
        if (i == 2) {
            return this.value.b;
        }
        if (i != 3) {
            return 0.0f;
        }
        return this.value.a;
    }

    public Color getValue() {
        return this.value;
    }

    public void set(Color color) {
        this.value.set(color);
        this.sliders.get(0).setValue(color.r);
        this.sliders.get(1).setValue(color.g);
        this.sliders.get(2).setValue(color.b);
        this.sliders.get(3).setValue(color.a);
    }

    public void set(float f, float f2, float f3, float f4) {
        this.value.set(f, f2, f3, f4);
        this.sliders.get(0).setValue(f);
        this.sliders.get(1).setValue(f2);
        this.sliders.get(2).setValue(f3);
        this.sliders.get(3).setValue(f4);
    }
}
