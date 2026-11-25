package org.catrobat.paintroid.command.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.colorpicker.ColorHistory;
import org.catrobat.paintroid.model.CommandManagerModel;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CatrobatFileContent.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/CatrobatFileContent;", "", "commandModel", "Lorg/catrobat/paintroid/model/CommandManagerModel;", "colorHistory", "Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "(Lorg/catrobat/paintroid/model/CommandManagerModel;Lorg/catrobat/paintroid/colorpicker/ColorHistory;)V", "getColorHistory", "()Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "getCommandModel", "()Lorg/catrobat/paintroid/model/CommandManagerModel;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final /* data */ class CatrobatFileContent {
    private final ColorHistory colorHistory;
    private final CommandManagerModel commandModel;

    public static /* synthetic */ CatrobatFileContent copy$default(CatrobatFileContent catrobatFileContent, CommandManagerModel commandManagerModel, ColorHistory colorHistory, int i, Object obj) {
        if ((i & 1) != 0) {
            commandManagerModel = catrobatFileContent.commandModel;
        }
        if ((i & 2) != 0) {
            colorHistory = catrobatFileContent.colorHistory;
        }
        return catrobatFileContent.copy(commandManagerModel, colorHistory);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CommandManagerModel getCommandModel() {
        return this.commandModel;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ColorHistory getColorHistory() {
        return this.colorHistory;
    }

    public final CatrobatFileContent copy(CommandManagerModel commandModel, ColorHistory colorHistory) {
        Intrinsics.checkNotNullParameter(commandModel, "commandModel");
        return new CatrobatFileContent(commandModel, colorHistory);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CatrobatFileContent)) {
            return false;
        }
        CatrobatFileContent catrobatFileContent = (CatrobatFileContent) other;
        return Intrinsics.areEqual(this.commandModel, catrobatFileContent.commandModel) && Intrinsics.areEqual(this.colorHistory, catrobatFileContent.colorHistory);
    }

    public int hashCode() {
        int iHashCode = this.commandModel.hashCode() * 31;
        ColorHistory colorHistory = this.colorHistory;
        return iHashCode + (colorHistory == null ? 0 : colorHistory.hashCode());
    }

    public String toString() {
        return "CatrobatFileContent(commandModel=" + this.commandModel + ", colorHistory=" + this.colorHistory + ')';
    }

    public CatrobatFileContent(CommandManagerModel commandModel, ColorHistory colorHistory) {
        Intrinsics.checkNotNullParameter(commandModel, "commandModel");
        this.commandModel = commandModel;
        this.colorHistory = colorHistory;
    }

    public final ColorHistory getColorHistory() {
        return this.colorHistory;
    }

    public final CommandManagerModel getCommandModel() {
        return this.commandModel;
    }
}
