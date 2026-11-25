package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

/* JADX INFO: loaded from: classes12.dex */
public class ColorAttributeUI extends Table {
    private ColorAttribute attribute;
    private Array<Slider> sliders;

    public ColorAttributeUI(Skin skin, ColorAttribute colorAttribute) {
        super(skin);
        this.sliders = new Array<>();
        if (colorAttribute == null) {
            return;
        }
        this.attribute = colorAttribute;
        add(ColorAttribute.getAttributeAlias(colorAttribute.type));
        for (final int i = 0; i < 4; i++) {
            if (i != 0) {
                add();
            }
            final Slider slider = new Slider(0.0f, 1.0f, 0.01f, false, skin);
            add(slider).row();
            slider.setValue(get(i));
            slider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.ColorAttributeUI.1
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    ColorAttributeUI.this.set(i, slider.getValue());
                }
            });
            this.sliders.add(slider);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set(int i, float f) {
        if (i == 0) {
            this.attribute.color.r = f;
            return;
        }
        if (i == 1) {
            this.attribute.color.g = f;
        } else if (i == 2) {
            this.attribute.color.b = f;
        } else {
            if (i != 3) {
                return;
            }
            this.attribute.color.a = f;
        }
    }

    public float get(int i) {
        if (i == 0) {
            return this.attribute.color.r;
        }
        if (i == 1) {
            return this.attribute.color.g;
        }
        if (i == 2) {
            return this.attribute.color.b;
        }
        if (i != 3) {
            return 0.0f;
        }
        return this.attribute.color.a;
    }

    public void set(Color color) {
        this.attribute.color.set(color);
        this.sliders.get(0).setValue(color.r);
        this.sliders.get(1).setValue(color.g);
        this.sliders.get(2).setValue(color.b);
        this.sliders.get(3).setValue(color.a);
    }

    public void set(float f, float f2, float f3, float f4) {
        this.attribute.color.set(f, f2, f3, f4);
        this.sliders.get(0).setValue(f);
        this.sliders.get(1).setValue(f2);
        this.sliders.get(2).setValue(f3);
        this.sliders.get(3).setValue(f4);
    }
}
