package org.catrobat.catroid.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/TextActor.class
  classes14.dex
 */
/* JADX INFO: compiled from: TextActor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000bH\u0002J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u0003R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/stage/TextActor;", "Lcom/badlogic/gdx/scenes/scene2d/Actor;", "text", "", "posX", "", "posY", "(Ljava/lang/String;II)V", "font", "Lcom/badlogic/gdx/graphics/g2d/BitmapFont;", "draw", "", "batch", "Lcom/badlogic/gdx/graphics/g2d/Batch;", "parentAlpha", "", ConfigConstants.CONFIG_INIT_SECTION, "setPosX", "setPosY", "setText", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TextActor extends Actor {
    private BitmapFont font;
    private int posX;
    private int posY;
    private String text;

    public TextActor(String text, int i, int i2) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
        this.posX = i;
        this.posY = i2;
        init();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        Intrinsics.checkNotNullParameter(batch, "batch");
        BitmapFont bitmapFont = this.font;
        Intrinsics.checkNotNull(bitmapFont);
        bitmapFont.draw(batch, this.text, this.posX, this.posY);
    }

    private final void init() {
        BitmapFont bitmapFont = new BitmapFont();
        this.font = bitmapFont;
        Intrinsics.checkNotNull(bitmapFont);
        bitmapFont.setColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public final void setPosX(int posX) {
        this.posX = posX;
    }

    public final void setPosY(int posY) {
        this.posY = posY;
    }

    public final void setText(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
    }
}
