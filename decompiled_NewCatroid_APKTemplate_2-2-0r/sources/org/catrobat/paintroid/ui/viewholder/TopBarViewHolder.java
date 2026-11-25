package org.catrobat.paintroid.ui.viewholder;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.appcompat.widget.Toolbar;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: TopBarViewHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001aH\u0016J\b\u0010\u001c\u001a\u00020\u001aH\u0016J\b\u0010\u001d\u001a\u00020\u001aH\u0016J\b\u0010\u001e\u001a\u00020\u001aH\u0016J\u0006\u0010\u001f\u001a\u00020\u001aJ\b\u0010 \u001a\u00020\u001aH\u0016J\u0012\u0010!\u001a\u00020\u001a2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\u0012\u0010$\u001a\u00020\u001a2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\b\u0010%\u001a\u00020\u001aH\u0016J\u0006\u0010&\u001a\u00020\u001aR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\b\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\bR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\b¨\u0006'"}, d2 = {"Lorg/catrobat/paintroid/ui/viewholder/TopBarViewHolder;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$TopBarViewHolder;", "layout", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "checkmarkButton", "Landroid/widget/ImageButton;", "getCheckmarkButton", "()Landroid/widget/ImageButton;", "height", "", "getHeight", "()I", "getLayout", "()Landroid/view/ViewGroup;", "plusButton", "getPlusButton", "setPlusButton", "(Landroid/widget/ImageButton;)V", "redoButton", "getRedoButton", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "undoButton", "getUndoButton", "disableRedoButton", "", "disableUndoButton", "enableRedoButton", "enableUndoButton", "hide", "hidePlusButton", "hideTitleIfNotStandalone", "removeCatroidMenuItems", LineReader.MENU, "Landroid/view/Menu;", "removeStandaloneMenuItems", "show", "showPlusButton", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class TopBarViewHolder implements MainActivityContracts.TopBarViewHolder {
    private final ImageButton checkmarkButton;
    private final ViewGroup layout;
    private ImageButton plusButton;
    private final ImageButton redoButton;
    private final Toolbar toolbar;
    private final ImageButton undoButton;

    public TopBarViewHolder(ViewGroup layout) {
        Intrinsics.checkNotNullParameter(layout, "layout");
        this.layout = layout;
        View viewFindViewById = layout.findViewById(R.id.pocketpaint_toolbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "layout.findViewById(R.id.pocketpaint_toolbar)");
        this.toolbar = (Toolbar) viewFindViewById;
        View viewFindViewById2 = layout.findViewById(R.id.pocketpaint_btn_top_undo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "layout.findViewById(R.id.pocketpaint_btn_top_undo)");
        this.undoButton = (ImageButton) viewFindViewById2;
        View viewFindViewById3 = layout.findViewById(R.id.pocketpaint_btn_top_redo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "layout.findViewById(R.id.pocketpaint_btn_top_redo)");
        this.redoButton = (ImageButton) viewFindViewById3;
        View viewFindViewById4 = layout.findViewById(R.id.pocketpaint_btn_top_checkmark);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "layout.findViewById(R.id…tpaint_btn_top_checkmark)");
        this.checkmarkButton = (ImageButton) viewFindViewById4;
        View viewFindViewById5 = layout.findViewById(R.id.pocketpaint_btn_top_plus);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "layout.findViewById(R.id.pocketpaint_btn_top_plus)");
        this.plusButton = (ImageButton) viewFindViewById5;
    }

    public final ViewGroup getLayout() {
        return this.layout;
    }

    public final ImageButton getUndoButton() {
        return this.undoButton;
    }

    public final ImageButton getRedoButton() {
        return this.redoButton;
    }

    public final ImageButton getCheckmarkButton() {
        return this.checkmarkButton;
    }

    public final ImageButton getPlusButton() {
        return this.plusButton;
    }

    public final void setPlusButton(ImageButton imageButton) {
        Intrinsics.checkNotNullParameter(imageButton, "<set-?>");
        this.plusButton = imageButton;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public int getHeight() {
        return this.layout.getHeight();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void enableUndoButton() {
        this.undoButton.setEnabled(true);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void disableUndoButton() {
        this.undoButton.setEnabled(false);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void enableRedoButton() {
        this.redoButton.setEnabled(true);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void disableRedoButton() {
        this.redoButton.setEnabled(false);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void hide() {
        this.layout.setVisibility(8);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void show() {
        this.layout.setVisibility(0);
    }

    public final void hidePlusButton() {
        this.plusButton.setVisibility(8);
    }

    public final void showPlusButton() {
        this.plusButton.setVisibility(0);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void removeStandaloneMenuItems(Menu menu) {
        if (menu == null) {
            return;
        }
        menu.removeItem(R.id.pocketpaint_options_save_image);
        menu.removeItem(R.id.pocketpaint_options_save_duplicate);
        menu.removeItem(R.id.pocketpaint_options_new_image);
        menu.removeItem(R.id.pocketpaint_options_rate_us);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void removeCatroidMenuItems(Menu menu) {
        if (menu == null) {
            return;
        }
        menu.removeItem(R.id.pocketpaint_options_export);
        menu.removeItem(R.id.pocketpaint_options_discard_image);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.TopBarViewHolder
    public void hideTitleIfNotStandalone() {
        this.toolbar.setTitle("");
    }
}
