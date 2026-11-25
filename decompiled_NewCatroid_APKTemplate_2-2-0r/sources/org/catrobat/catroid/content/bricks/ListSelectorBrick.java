package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.recyclerview.fragment.ListSelectorFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ListSelectorBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: ListSelectorBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\u00132\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0017H\u0016J\u0016\u0010\u001c\u001a\u00020\u00132\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015H\u0016J\u0006\u0010\u001d\u001a\u00020\u0013J\b\u0010\u001e\u001a\u00020\u0013H\u0014R\u0014\u0010\u0005\u001a\u00020\u00068eX¤\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/bricks/ListSelectorBrick;", "Lorg/catrobat/catroid/content/bricks/BrickBaseType;", "Landroid/view/View$OnClickListener;", "Lorg/catrobat/catroid/ui/recyclerview/fragment/ListSelectorFragment$ListSelectorInterface;", "()V", "selectorId", "", "getSelectorId", "()I", "userLists", "", "Lorg/catrobat/catroid/formulaeditor/UserList;", "getUserLists", "()Ljava/util/List;", "setUserLists", "(Ljava/util/List;)V", "clone", "Lorg/catrobat/catroid/content/bricks/Brick;", "deselectElements", "", "deletedElements", "", "getView", "Landroid/view/View;", "context", "Landroid/content/Context;", "onClick", Promotion.ACTION_VIEW, "onUserListSelected", "setClickListeners", "updateSelectorText", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ListSelectorBrick extends BrickBaseType implements View.OnClickListener, ListSelectorFragment.ListSelectorInterface {
    private List<UserList> userLists = new ArrayList();

    protected abstract int getSelectorId();

    public final List<UserList> getUserLists() {
        return this.userLists;
    }

    public final void setUserLists(List<UserList> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.userLists = list;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        Brick brickClone = super.clone();
        Intrinsics.checkNotNull(brickClone, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.ListSelectorBrick");
        ListSelectorBrick listSelectorBrick = (ListSelectorBrick) brickClone;
        listSelectorBrick.userLists = CollectionsKt.toMutableList((Collection) this.userLists);
        return listSelectorBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.getView(context);
        updateSelectorText();
        View view = this.view;
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    protected void updateSelectorText() {
        ((TextView) this.view.findViewById(getSelectorId())).setText(this.view.getResources().getQuantityString(R.plurals.list_selection_plural, this.userLists.size(), Integer.valueOf(this.userLists.size())));
    }

    public final void setClickListeners() {
        ((TextView) this.view.findViewById(getSelectorId())).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        ListSelectorFragment.Companion companion = ListSelectorFragment.INSTANCE;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        companion.showFragment(context, this);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.ListSelectorFragment.ListSelectorInterface
    public void onUserListSelected(List<? extends UserList> userLists) {
        Intrinsics.checkNotNullParameter(userLists, "userLists");
        this.userLists = CollectionsKt.toMutableList((Collection) userLists);
        updateSelectorText();
    }

    public final void deselectElements(List<?> deletedElements) {
        Intrinsics.checkNotNullParameter(deletedElements, "deletedElements");
        ArrayList<UserData> arrayList = new ArrayList();
        for (Object obj : deletedElements) {
            if (obj instanceof UserData) {
                arrayList.add(obj);
            }
        }
        for (final UserData userData : arrayList) {
            CollectionsKt.removeAll((List) this.userLists, (Function1) new Function1<UserList, Boolean>() { // from class: org.catrobat.catroid.content.bricks.ListSelectorBrick$deselectElements$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UserList list) {
                    Intrinsics.checkNotNullParameter(list, "list");
                    return Boolean.valueOf(Intrinsics.areEqual(list.getName(), userData.getName()));
                }
            });
        }
    }
}
