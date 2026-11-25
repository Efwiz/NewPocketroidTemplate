package org.catrobat.catroid.content;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.sensing.CollisionInformation;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/DynamicLookData.class
  classes14.dex
 */
/* JADX INFO: compiled from: DynamicLookData.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0010H\u0016J\b\u0010\u0018\u001a\u00020\u0012H\u0016J\b\u0010\u0019\u001a\u00020\u0005H\u0016R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/content/DynamicLookData;", "Lorg/catrobat/catroid/common/LookData;", "bufferName", "", "region", "Lcom/badlogic/gdx/graphics/g2d/TextureRegion;", "bufferWidth", "", "bufferHeight", "(Ljava/lang/String;Lcom/badlogic/gdx/graphics/g2d/TextureRegion;II)V", "getBufferHeight", "()I", "getBufferName", "()Ljava/lang/String;", "getBufferWidth", "customCollision", "Lorg/catrobat/catroid/sensing/CollisionInformation;", "dummyPixmap", "Lcom/badlogic/gdx/graphics/Pixmap;", "getRegion", "()Lcom/badlogic/gdx/graphics/g2d/TextureRegion;", "dispose", "", "getCollisionInformation", "getPixmap", "getTextureRegion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DynamicLookData extends LookData {
    private final int bufferHeight;
    private final String bufferName;
    private final int bufferWidth;
    private CollisionInformation customCollision;
    private Pixmap dummyPixmap;
    private final TextureRegion region;

    public final String getBufferName() {
        return this.bufferName;
    }

    public final TextureRegion getRegion() {
        return this.region;
    }

    public final int getBufferWidth() {
        return this.bufferWidth;
    }

    public final int getBufferHeight() {
        return this.bufferHeight;
    }

    public DynamicLookData(String bufferName, TextureRegion region, int i, int i2) {
        Intrinsics.checkNotNullParameter(bufferName, "bufferName");
        Intrinsics.checkNotNullParameter(region, "region");
        this.bufferName = bufferName;
        this.region = region;
        this.bufferWidth = i;
        this.bufferHeight = i2;
        i = i <= 0 ? 1 : i;
        i2 = i2 <= 0 ? 1 : i2;
        this.f512name = "Buffer_" + bufferName;
        setLookId("buffer_" + bufferName + "_" + System.currentTimeMillis());
        this.dummyPixmap = new Pixmap(i, i2, Pixmap.Format.RGBA8888);
        CollisionInformation collisionInformation = new CollisionInformation(this);
        this.customCollision = collisionInformation;
        float f = i;
        float f2 = i2;
        Intrinsics.checkNotNull(collisionInformation);
        collisionInformation.collisionPolygons = new Polygon[]{new Polygon(new float[]{0.0f, 0.0f, f, 0.0f, f, f2, 0.0f, f2})};
    }

    @Override // org.catrobat.catroid.common.LookData
    public Pixmap getPixmap() {
        Pixmap pixmap = this.dummyPixmap;
        Intrinsics.checkNotNull(pixmap);
        return pixmap;
    }

    @Override // org.catrobat.catroid.common.LookData
    public TextureRegion getTextureRegion() {
        return this.region;
    }

    @Override // org.catrobat.catroid.common.LookData
    public CollisionInformation getCollisionInformation() {
        CollisionInformation collisionInformation = this.customCollision;
        Intrinsics.checkNotNull(collisionInformation);
        return collisionInformation;
    }

    @Override // org.catrobat.catroid.common.LookData
    public void dispose() {
        super.dispose();
        Pixmap pixmap = this.dummyPixmap;
        if (pixmap != null) {
            pixmap.dispose();
        }
    }
}
