package org.catrobat.paintroid.model;

import android.net.Uri;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.colorpicker.ColorHistory;
import org.catrobat.paintroid.contract.MainActivityContracts;

/* JADX INFO: compiled from: MainActivityModel.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u0010H\u0016J\b\u0010\u001d\u001a\u00020\u0010H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0011\"\u0004\b\u0015\u0010\u0013R\u001a\u0010\u0016\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0011\"\u0004\b\u0017\u0010\u0013R\u001a\u0010\u0018\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\bR\u000e\u0010\u001d\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/catrobat/paintroid/model/MainActivityModel;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;", "()V", "cameraImageUri", "Landroid/net/Uri;", "getCameraImageUri", "()Landroid/net/Uri;", "setCameraImageUri", "(Landroid/net/Uri;)V", "colorHistory", "Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "getColorHistory", "()Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "setColorHistory", "(Lorg/catrobat/paintroid/colorpicker/ColorHistory;)V", "isFullscreen", "", "()Z", "setFullscreen", "(Z)V", "isOpenedFromCatroid", "setOpenedFromCatroid", "isOpenedFromFormulaEditorInCatroid", "setOpenedFromFormulaEditorInCatroid", "isSaved", "setSaved", "savedPictureUri", "getSavedPictureUri", "setSavedPictureUri", "wasInitialAnimationPlayed", "setInitialAnimationPlayed", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class MainActivityModel implements MainActivityContracts.Model {
    private Uri cameraImageUri;
    private ColorHistory colorHistory = new ColorHistory();
    private boolean isFullscreen;
    private boolean isOpenedFromCatroid;
    private boolean isOpenedFromFormulaEditorInCatroid;
    private boolean isSaved;
    private Uri savedPictureUri;
    private boolean wasInitialAnimationPlayed;

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    /* JADX INFO: renamed from: isOpenedFromCatroid, reason: from getter */
    public boolean getIsOpenedFromCatroid() {
        return this.isOpenedFromCatroid;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public void setOpenedFromCatroid(boolean z) {
        this.isOpenedFromCatroid = z;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    /* JADX INFO: renamed from: isOpenedFromFormulaEditorInCatroid, reason: from getter */
    public boolean getIsOpenedFromFormulaEditorInCatroid() {
        return this.isOpenedFromFormulaEditorInCatroid;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public void setOpenedFromFormulaEditorInCatroid(boolean z) {
        this.isOpenedFromFormulaEditorInCatroid = z;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    /* JADX INFO: renamed from: isFullscreen, reason: from getter */
    public boolean getIsFullscreen() {
        return this.isFullscreen;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public void setFullscreen(boolean z) {
        this.isFullscreen = z;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    /* JADX INFO: renamed from: isSaved, reason: from getter */
    public boolean getIsSaved() {
        return this.isSaved;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public void setSaved(boolean z) {
        this.isSaved = z;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public Uri getSavedPictureUri() {
        return this.savedPictureUri;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public void setSavedPictureUri(Uri uri) {
        this.savedPictureUri = uri;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public Uri getCameraImageUri() {
        return this.cameraImageUri;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public void setCameraImageUri(Uri uri) {
        this.cameraImageUri = uri;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public ColorHistory getColorHistory() {
        return this.colorHistory;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public void setColorHistory(ColorHistory colorHistory) {
        Intrinsics.checkNotNullParameter(colorHistory, "<set-?>");
        this.colorHistory = colorHistory;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    /* JADX INFO: renamed from: wasInitialAnimationPlayed, reason: from getter */
    public boolean getWasInitialAnimationPlayed() {
        return this.wasInitialAnimationPlayed;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Model
    public void setInitialAnimationPlayed(boolean wasInitialAnimationPlayed) {
        this.wasInitialAnimationPlayed = wasInitialAnimationPlayed;
    }
}
