package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.attributes.PBRHDRColorAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class HDRColorAttributeUI extends Table {
    private PBRHDRColorAttribute attribute;
    private Array<Slider> sliders;

    public HDRColorAttributeUI(Skin skin, PBRHDRColorAttribute pBRHDRColorAttribute, float f) {
        super(skin);
        this.sliders = new Array<>();
        if (pBRHDRColorAttribute == null) {
            return;
        }
        this.attribute = pBRHDRColorAttribute;
        add(ColorAttribute.getAttributeAlias(pBRHDRColorAttribute.type));
        for (final int i = 0; i < 3; i++) {
            if (i != 0) {
                add();
            }
            final Slider slider = new Slider(0.0f, f, 0.01f, false, skin);
            add(slider).row();
            slider.setValue(get(i));
            slider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.HDRColorAttributeUI.1
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    HDRColorAttributeUI.this.set(i, slider.getValue());
                }
            });
            this.sliders.add(slider);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set(int i, float f) {
        if (i == 0) {
            this.attribute.r = f;
        } else if (i == 1) {
            this.attribute.g = f;
        } else {
            if (i != 2) {
                return;
            }
            this.attribute.b = f;
        }
    }

    public float get(int i) {
        if (i == 0) {
            return this.attribute.r;
        }
        if (i == 1) {
            return this.attribute.g;
        }
        if (i != 2) {
            return 0.0f;
        }
        return this.attribute.b;
    }

    public void set(float f, float f2, float f3) {
        this.attribute.set(f, f2, f3);
        this.sliders.get(0).setValue(f);
        this.sliders.get(1).setValue(f2);
        this.sliders.get(2).setValue(f3);
    }
}
