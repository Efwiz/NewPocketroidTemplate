package org.catrobat.catroid.camera;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/Position.class
  classes12.dex
 */
/* JADX INFO: compiled from: Position.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/camera/Position;", "", "x", "", "y", "(DD)V", "getX", "()D", "getY", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Position {
    private final double x;
    private final double y;

    public static /* synthetic */ Position copy$default(Position position, double d, double d2, int i, Object obj) {
        if ((i & 1) != 0) {
            d = position.x;
        }
        if ((i & 2) != 0) {
            d2 = position.y;
        }
        return position.copy(d, d2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final double getX() {
        return this.x;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final double getY() {
        return this.y;
    }

    public final Position copy(double x, double y) {
        return new Position(x, y);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position)) {
            return false;
        }
        Position position = (Position) other;
        return Double.compare(this.x, position.x) == 0 && Double.compare(this.y, position.y) == 0;
    }

    public int hashCode() {
        return (Double.hashCode(this.x) * 31) + Double.hashCode(this.y);
    }

    public String toString() {
        return "Position(x=" + this.x + ", y=" + this.y + Constant.AFTER_QUTO;
    }

    public Position(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }
}
