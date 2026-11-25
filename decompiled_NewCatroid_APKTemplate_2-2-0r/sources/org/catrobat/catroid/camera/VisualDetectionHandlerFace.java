package org.catrobat.catroid.camera;

import android.graphics.Rect;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/VisualDetectionHandlerFace.class
  classes12.dex
 */
/* JADX INFO: compiled from: VisualDetectionHandler.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/camera/VisualDetectionHandlerFace;", "", "id", "", "boundingBox", "Landroid/graphics/Rect;", "(ILandroid/graphics/Rect;)V", "getBoundingBox", "()Landroid/graphics/Rect;", "getId", "()I", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class VisualDetectionHandlerFace {
    private final Rect boundingBox;
    private final int id;

    public static /* synthetic */ VisualDetectionHandlerFace copy$default(VisualDetectionHandlerFace visualDetectionHandlerFace, int i, Rect rect, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = visualDetectionHandlerFace.id;
        }
        if ((i2 & 2) != 0) {
            rect = visualDetectionHandlerFace.boundingBox;
        }
        return visualDetectionHandlerFace.copy(i, rect);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Rect getBoundingBox() {
        return this.boundingBox;
    }

    public final VisualDetectionHandlerFace copy(int id2, Rect boundingBox) {
        Intrinsics.checkNotNullParameter(boundingBox, "boundingBox");
        return new VisualDetectionHandlerFace(id2, boundingBox);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VisualDetectionHandlerFace)) {
            return false;
        }
        VisualDetectionHandlerFace visualDetectionHandlerFace = (VisualDetectionHandlerFace) other;
        return this.id == visualDetectionHandlerFace.id && Intrinsics.areEqual(this.boundingBox, visualDetectionHandlerFace.boundingBox);
    }

    public int hashCode() {
        return (Integer.hashCode(this.id) * 31) + this.boundingBox.hashCode();
    }

    public String toString() {
        return "VisualDetectionHandlerFace(id=" + this.id + ", boundingBox=" + this.boundingBox + Constant.AFTER_QUTO;
    }

    public VisualDetectionHandlerFace(int i, Rect boundingBox) {
        Intrinsics.checkNotNullParameter(boundingBox, "boundingBox");
        this.id = i;
        this.boundingBox = boundingBox;
    }

    public final Rect getBoundingBox() {
        return this.boundingBox;
    }

    public final int getId() {
        return this.id;
    }
}
