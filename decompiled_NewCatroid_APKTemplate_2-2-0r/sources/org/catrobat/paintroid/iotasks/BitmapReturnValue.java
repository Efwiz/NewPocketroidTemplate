package org.catrobat.paintroid.iotasks;

import android.graphics.Bitmap;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.colorpicker.ColorHistory;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.model.CommandManagerModel;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BitmapReturnValue.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B)\b\u0016\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB\u001b\b\u0016\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eB;\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u0010J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u000bHÆ\u0003J\u0011\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u0014\u001a\u00020\bHÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\rHÆ\u0003JI\u0010\u0016\u001a\u00020\u00002\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/paintroid/iotasks/BitmapReturnValue;", "", "bitmapList", "", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "bitmap", "Landroid/graphics/Bitmap;", "toBeScaled", "", "(Ljava/util/List;Landroid/graphics/Bitmap;Z)V", "model", "Lorg/catrobat/paintroid/model/CommandManagerModel;", "colorHistory", "Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "(Lorg/catrobat/paintroid/model/CommandManagerModel;Lorg/catrobat/paintroid/colorpicker/ColorHistory;)V", "layerList", "(Lorg/catrobat/paintroid/model/CommandManagerModel;Ljava/util/List;Landroid/graphics/Bitmap;ZLorg/catrobat/paintroid/colorpicker/ColorHistory;)V", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final /* data */ class BitmapReturnValue {
    public Bitmap bitmap;
    public ColorHistory colorHistory;
    public List<? extends LayerContracts.Layer> layerList;
    public CommandManagerModel model;
    public boolean toBeScaled;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BitmapReturnValue copy$default(BitmapReturnValue bitmapReturnValue, CommandManagerModel commandManagerModel, List list, Bitmap bitmap, boolean z, ColorHistory colorHistory, int i, Object obj) {
        if ((i & 1) != 0) {
            commandManagerModel = bitmapReturnValue.model;
        }
        if ((i & 2) != 0) {
            list = bitmapReturnValue.layerList;
        }
        List list2 = list;
        if ((i & 4) != 0) {
            bitmap = bitmapReturnValue.bitmap;
        }
        Bitmap bitmap2 = bitmap;
        if ((i & 8) != 0) {
            z = bitmapReturnValue.toBeScaled;
        }
        boolean z2 = z;
        if ((i & 16) != 0) {
            colorHistory = bitmapReturnValue.colorHistory;
        }
        return bitmapReturnValue.copy(commandManagerModel, list2, bitmap2, z2, colorHistory);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CommandManagerModel getModel() {
        return this.model;
    }

    public final List<LayerContracts.Layer> component2() {
        return this.layerList;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getToBeScaled() {
        return this.toBeScaled;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final ColorHistory getColorHistory() {
        return this.colorHistory;
    }

    public final BitmapReturnValue copy(CommandManagerModel model, List<? extends LayerContracts.Layer> layerList, Bitmap bitmap, boolean toBeScaled, ColorHistory colorHistory) {
        return new BitmapReturnValue(model, layerList, bitmap, toBeScaled, colorHistory);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BitmapReturnValue)) {
            return false;
        }
        BitmapReturnValue bitmapReturnValue = (BitmapReturnValue) other;
        return Intrinsics.areEqual(this.model, bitmapReturnValue.model) && Intrinsics.areEqual(this.layerList, bitmapReturnValue.layerList) && Intrinsics.areEqual(this.bitmap, bitmapReturnValue.bitmap) && this.toBeScaled == bitmapReturnValue.toBeScaled && Intrinsics.areEqual(this.colorHistory, bitmapReturnValue.colorHistory);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [int] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v7, types: [int] */
    /* JADX WARN: Type inference failed for: r2v9 */
    public int hashCode() {
        CommandManagerModel commandManagerModel = this.model;
        int iHashCode = (commandManagerModel == null ? 0 : commandManagerModel.hashCode()) * 31;
        List<? extends LayerContracts.Layer> list = this.layerList;
        int iHashCode2 = (iHashCode + (list == null ? 0 : list.hashCode())) * 31;
        Bitmap bitmap = this.bitmap;
        int iHashCode3 = (iHashCode2 + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        boolean z = this.toBeScaled;
        ?? r2 = z;
        if (z) {
            r2 = 1;
        }
        int i = (iHashCode3 + r2) * 31;
        ColorHistory colorHistory = this.colorHistory;
        return i + (colorHistory != null ? colorHistory.hashCode() : 0);
    }

    public String toString() {
        return "BitmapReturnValue(model=" + this.model + ", layerList=" + this.layerList + ", bitmap=" + this.bitmap + ", toBeScaled=" + this.toBeScaled + ", colorHistory=" + this.colorHistory + ')';
    }

    public BitmapReturnValue(CommandManagerModel commandManagerModel, List<? extends LayerContracts.Layer> list, Bitmap bitmap, boolean z, ColorHistory colorHistory) {
        this.model = commandManagerModel;
        this.layerList = list;
        this.bitmap = bitmap;
        this.toBeScaled = z;
        this.colorHistory = colorHistory;
    }

    public BitmapReturnValue(List<? extends LayerContracts.Layer> list, Bitmap bitmap, boolean z) {
        this(null, list, bitmap, z, null);
    }

    public BitmapReturnValue(CommandManagerModel commandManagerModel, ColorHistory colorHistory) {
        this(commandManagerModel, null, null, false, colorHistory);
    }
}
