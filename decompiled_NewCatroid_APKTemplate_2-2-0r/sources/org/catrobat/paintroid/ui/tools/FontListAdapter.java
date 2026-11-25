package org.catrobat.paintroid.ui.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.FontType;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: FontListAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001$B1\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\u001a\u001a\u00020\u0014H\u0016J\u0006\u0010\u001b\u001a\u00020\u0007J\u001c\u0010\u001c\u001a\u00020\n2\n\u0010\u001d\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u0014H\u0016J\u001c\u0010\u001f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0014H\u0016J\u000e\u0010#\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n \u0011*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\n \u0011*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\n \u0011*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0018X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0019¨\u0006%"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/FontListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lorg/catrobat/paintroid/ui/tools/FontListAdapter$ViewHolder;", "context", "Landroid/content/Context;", "fontTypes", "", "Lorg/catrobat/paintroid/tools/FontType;", "onFontChanged", "Lkotlin/Function1;", "", "(Landroid/content/Context;Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "dubai", "Landroid/graphics/Typeface;", "mInflater", "Landroid/view/LayoutInflater;", "monospace", JvmProtoBufUtil.PLATFORM_TYPE_ID, "sansSerif", "selectedIndex", "", "serif", "stc", "typeFaces", "", "[Landroid/graphics/Typeface;", "getItemCount", "getSelectedItem", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setSelectedIndex", "ViewHolder", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class FontListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Typeface dubai;
    private final List<FontType> fontTypes;
    private final LayoutInflater mInflater;
    private final Typeface monospace;
    private final Function1<FontType, Unit> onFontChanged;
    private final Typeface sansSerif;
    private int selectedIndex;
    private final Typeface serif;
    private final Typeface stc;
    private final Typeface[] typeFaces;

    /* JADX WARN: Multi-variable type inference failed */
    public FontListAdapter(Context context, List<? extends FontType> fontTypes, Function1<? super FontType, Unit> onFontChanged) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fontTypes, "fontTypes");
        Intrinsics.checkNotNullParameter(onFontChanged, "onFontChanged");
        this.fontTypes = fontTypes;
        this.onFontChanged = onFontChanged;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        Intrinsics.checkNotNullExpressionValue(layoutInflaterFrom, "from(context)");
        this.mInflater = layoutInflaterFrom;
        Typeface typefaceCreate = Typeface.create(Typeface.SANS_SERIF, 0);
        this.sansSerif = typefaceCreate;
        Typeface typefaceCreate2 = Typeface.create(Typeface.MONOSPACE, 0);
        this.monospace = typefaceCreate2;
        Typeface typefaceCreate3 = Typeface.create(Typeface.SERIF, 0);
        this.serif = typefaceCreate3;
        Typeface font = ResourcesCompat.getFont(context, R.font.dubai);
        this.dubai = font;
        Typeface font2 = ResourcesCompat.getFont(context, R.font.stc_regular);
        this.stc = font2;
        this.typeFaces = new Typeface[]{typefaceCreate, typefaceCreate2, typefaceCreate3, font, font2};
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = this.mInflater.inflate(R.layout.pocketpaint_item_font, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "mInflater.inflate(R.layo…item_font, parent, false)");
        return new ViewHolder(this, viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.getFontChip().setText(this.fontTypes.get(position).getNameResource());
        holder.getFontChip().setTypeface(this.typeFaces[position]);
        holder.getFontChip().setChecked(position == this.selectedIndex);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.fontTypes.size();
    }

    public final void setSelectedIndex(int selectedIndex) {
        int i = this.selectedIndex;
        this.selectedIndex = selectedIndex;
        notifyItemChanged(i);
        notifyItemChanged(selectedIndex);
    }

    public final FontType getSelectedItem() {
        return this.fontTypes.get(this.selectedIndex);
    }

    /* JADX INFO: compiled from: FontListAdapter.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0016R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/FontListAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "itemView", "Landroid/view/View;", "(Lorg/catrobat/paintroid/ui/tools/FontListAdapter;Landroid/view/View;)V", "fontChip", "Lcom/google/android/material/chip/Chip;", "getFontChip", "()Lcom/google/android/material/chip/Chip;", "setFontChip", "(Lcom/google/android/material/chip/Chip;)V", "onClick", "", "p0", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Chip fontChip;
        final /* synthetic */ FontListAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(FontListAdapter this$0, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = this$0;
            View viewFindViewById = itemView.findViewById(R.id.pocketpaint_font_type);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "itemView.findViewById(R.id.pocketpaint_font_type)");
            Chip chip = (Chip) viewFindViewById;
            this.fontChip = chip;
            chip.setOnClickListener(this);
        }

        public final Chip getFontChip() {
            return this.fontChip;
        }

        public final void setFontChip(Chip chip) {
            Intrinsics.checkNotNullParameter(chip, "<set-?>");
            this.fontChip = chip;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View p0) {
            this.this$0.setSelectedIndex(getLayoutPosition());
            this.this$0.onFontChanged.invoke(this.this$0.fontTypes.get(getLayoutPosition()));
        }
    }
}
