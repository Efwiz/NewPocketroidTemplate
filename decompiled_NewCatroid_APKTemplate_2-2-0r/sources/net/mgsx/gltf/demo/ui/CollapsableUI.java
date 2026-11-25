package net.mgsx.gltf.demo.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/* JADX INFO: loaded from: classes12.dex */
public class CollapsableUI extends Table {
    private Cell<Table> optCell;
    protected Table optTable;
    public TextButton toggle;

    public CollapsableUI(Skin skin, String str, boolean z) {
        TextButton textButton = new TextButton(str, skin, "toggle");
        this.toggle = textButton;
        textButton.setChecked(z);
        add(this.toggle).row();
        this.optTable = new Table(skin);
        this.optCell = add();
        row();
        show(z);
        this.toggle.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.CollapsableUI.1
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                CollapsableUI collapsableUI = CollapsableUI.this;
                collapsableUI.show(collapsableUI.toggle.isChecked());
            }
        });
    }

    public void show(boolean z) {
        if (z) {
            this.optCell.setActor(this.optTable);
        } else {
            this.optCell.setActor(null);
        }
    }
}
