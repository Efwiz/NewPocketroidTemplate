package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/* JADX INFO: loaded from: classes12.dex */
public class FloatAttributeUI extends Table {
    protected FloatAttribute attribute;
    private Slider slider;

    public FloatAttributeUI(Skin skin, FloatAttribute floatAttribute) {
        this(skin, floatAttribute, 0.0f, 1.0f);
    }

    public FloatAttributeUI(Skin skin, final FloatAttribute floatAttribute, float f, float f2) {
        super(skin);
        this.attribute = floatAttribute;
        this.slider = new Slider(f, f2, 0.01f, false, skin);
        if (floatAttribute != null) {
            add(FloatAttribute.getAttributeAlias(floatAttribute.type));
            add(this.slider).row();
            this.slider.setValue(floatAttribute.value);
            this.slider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.FloatAttributeUI.1
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    floatAttribute.value = FloatAttributeUI.this.slider.getValue();
                }
            });
        }
    }
}
