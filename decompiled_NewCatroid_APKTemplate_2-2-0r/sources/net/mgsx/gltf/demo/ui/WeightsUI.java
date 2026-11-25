package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
public class WeightsUI extends Table {
    private Array<Slider> sliders;
    public WeightVector value;

    public WeightsUI(Skin skin, WeightVector weightVector, Array<String> array) {
        super(skin);
        this.sliders = new Array<>();
        this.value = weightVector;
        final int i = 0;
        while (i < weightVector.count) {
            final Slider slider = new Slider(0.0f, 1.0f, 0.01f, false, skin);
            slider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.WeightsUI.1
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    WeightsUI.this.set(i, slider.getValue());
                }
            });
            add((array == null || i >= array.size) ? "#" + (i + 1) : array.get(i));
            add(slider).row();
            slider.setValue(get(i));
            this.sliders.add(slider);
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set(int i, float f) {
        this.value.values[i] = f;
    }

    public float get(int i) {
        return this.value.values[i];
    }
}
