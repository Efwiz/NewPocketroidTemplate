package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/* JADX INFO: loaded from: classes12.dex */
public class FloatUI extends Table {
    private Slider slider;

    protected void onChange(float f) {
    }

    public FloatUI(Skin skin, float f) {
        this(skin, f, null);
    }

    public FloatUI(Skin skin, float f, String str) {
        this(skin, f, str, 0.0f, 1.0f);
    }

    public FloatUI(Skin skin, float f, String str, float f2, float f3) {
        super(skin);
        this.slider = new Slider(f2, f3, 0.01f, false, skin);
        if (str != null) {
            add(str);
        }
        add(this.slider).row();
        this.slider.setValue(f);
        this.slider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.FloatUI.1
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                FloatUI floatUI = FloatUI.this;
                floatUI.onChange(floatUI.slider.getValue());
            }
        });
    }

    public float getValue() {
        return this.slider.getValue();
    }
}
