package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

/* JADX INFO: loaded from: classes12.dex */
public class Vector3UI extends Table {
    private Array<Slider> sliders;
    public Vector3 value;

    public Vector3UI(Skin skin, Vector3 vector3) {
        super(skin);
        this.sliders = new Array<>();
        this.value = vector3;
        for (final int i = 0; i < 3; i++) {
            final Slider slider = new Slider(-1.0f, 1.0f, 0.01f, false, skin);
            add(name(i));
            add(slider).row();
            slider.setValue(get(i));
            slider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.Vector3UI.1
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    Vector3UI.this.set(i, slider.getValue());
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
        if (i != 2) {
            return null;
        }
        return CompressorStreamFactory.Z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set(int i, float f) {
        if (i == 0) {
            this.value.x = f;
        } else if (i == 1) {
            this.value.y = f;
        } else {
            if (i != 2) {
                return;
            }
            this.value.z = f;
        }
    }

    private float get(int i) {
        if (i == 0) {
            return this.value.x;
        }
        if (i == 1) {
            return this.value.y;
        }
        if (i != 2) {
            return 0.0f;
        }
        return this.value.z;
    }

    public void set(Vector3 vector3) {
        this.value.set(vector3);
        this.sliders.get(0).setValue(vector3.x);
        this.sliders.get(1).setValue(vector3.y);
        this.sliders.get(2).setValue(vector3.z);
    }
}
