package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes12.dex */
public class BooleanUI extends Table {
    private TextButton bt;

    public BooleanUI(Skin skin, boolean z) {
        super(skin);
        TextButton textButton = new TextButton(CompilerOptions.ENABLED, skin, "toggle");
        this.bt = textButton;
        textButton.setChecked(z);
        add(this.bt);
    }

    public boolean isOn() {
        return this.bt.isChecked();
    }
}
