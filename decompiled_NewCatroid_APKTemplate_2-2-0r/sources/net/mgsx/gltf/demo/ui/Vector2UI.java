package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/* JADX INFO: loaded from: classes12.dex */
public class Vector2UI extends Table {
    private Vector2 value;

    public Vector2UI(Skin skin, Vector2 vector2) {
        super(skin);
        this.value = vector2;
        for (final int i = 0; i < 2; i++) {
            final Slider slider = new Slider(0.0f, 1.0f, 0.01f, false, skin);
            add(name(i));
            add(slider).row();
            slider.setValue(get(i));
            slider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.Vector2UI.1
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    Vector2UI.this.set(i, slider.getValue());
                }
            });
        }
    }

    private String name(int i) {
        if (i == 0) {
            return "x";
        }
        if (i != 1) {
            return null;
        }
        return "y";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set(int i, float f) {
        if (i == 0) {
            this.value.x = f;
        } else {
            if (i != 1) {
                return;
            }
            this.value.y = f;
        }
    }

    private float get(int i) {
        if (i == 0) {
            return this.value.x;
        }
        if (i != 1) {
            return 0.0f;
        }
        return this.value.y;
    }
}
