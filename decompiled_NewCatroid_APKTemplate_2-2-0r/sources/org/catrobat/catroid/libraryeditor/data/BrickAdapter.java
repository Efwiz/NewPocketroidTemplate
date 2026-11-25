package org.catrobat.catroid.libraryeditor.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/BrickAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0011B-\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\fH\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/BrickAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lorg/catrobat/catroid/libraryeditor/data/EditableBrick;", "Lorg/catrobat/catroid/libraryeditor/data/BrickAdapter$ViewHolder;", "onItemClick", "Lkotlin/Function1;", "", "onItemLongClick", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickAdapter extends ListAdapter<EditableBrick, ViewHolder> {
    private final Function1<EditableBrick, Unit> onItemClick;
    private final Function1<EditableBrick, Unit> onItemLongClick;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BrickAdapter(Function1<? super EditableBrick, Unit> onItemClick, Function1<? super EditableBrick, Unit> onItemLongClick) {
        super(new BrickDiffCallback());
        Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
        Intrinsics.checkNotNullParameter(onItemLongClick, "onItemLongClick");
        this.onItemClick = onItemClick;
        this.onItemLongClick = onItemLongClick;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_editable_brick, parent, false);
        Intrinsics.checkNotNull(viewInflate);
        return new ViewHolder(viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final EditableBrick item = getItem(position);
        Intrinsics.checkNotNull(item);
        holder.bind(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.data.BrickAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BrickAdapter.onBindViewHolder$lambda$0(this.f$0, item, view);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.libraryeditor.data.BrickAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return BrickAdapter.onBindViewHolder$lambda$1(this.f$0, item, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(BrickAdapter this$0, EditableBrick editableBrick, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Function1<EditableBrick, Unit> function1 = this$0.onItemClick;
        Intrinsics.checkNotNull(editableBrick);
        function1.invoke(editableBrick);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBindViewHolder$lambda$1(BrickAdapter this$0, EditableBrick editableBrick, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Function1<EditableBrick, Unit> function1 = this$0.onItemLongClick;
        Intrinsics.checkNotNull(editableBrick);
        function1.invoke(editableBrick);
        return true;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/BrickAdapter$ViewHolder.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickAdapter.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/BrickAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "functionName", "Landroid/widget/TextView;", "headerText", "id", "bind", "", "brick", "Lorg/catrobat/catroid/libraryeditor/data/EditableBrick;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView functionName;
        private final TextView headerText;
        private final TextView id;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View viewFindViewById = itemView.findViewById(R.id.brick_header_text);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.headerText = (TextView) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.brick_function_name);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
            this.functionName = (TextView) viewFindViewById2;
            View viewFindViewById3 = itemView.findViewById(R.id.brick_id);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
            this.id = (TextView) viewFindViewById3;
        }

        public final void bind(EditableBrick brick) {
            Intrinsics.checkNotNullParameter(brick, "brick");
            this.headerText.setText(brick.getHeaderText());
            this.functionName.setText(brick.getFunctionName() + "(...)");
            this.id.setText("ID: " + brick.getId());
        }
    }
}
