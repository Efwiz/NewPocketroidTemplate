package org.catrobat.catroid.fast2d.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/components/TextureComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TextureComponent extends PooledComponent {
    public TextureRegion region = null;
    public String textureName = "";
    public Color color = new Color(Color.WHITE);
    public float width = 0.0f;
    public float height = 0.0f;
    public float originX = 0.0f;
    public float originY = 0.0f;

    @Override // com.artemis.PooledComponent
    protected void reset() {
        this.region = null;
        this.textureName = "";
        this.color.set(Color.WHITE);
        this.width = 0.0f;
        this.height = 0.0f;
        this.originX = 0.0f;
        this.originY = 0.0f;
    }
}
