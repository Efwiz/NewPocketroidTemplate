package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.ai.KoveAutocompleteController;
import org.catrobat.catroid.codeanalysis.AnalysisManager;
import org.catrobat.catroid.codeanalysis.AnalysisResult;
import org.catrobat.catroid.codeanalysis.Severity;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.EmptyEventBrick;
import org.catrobat.catroid.content.bricks.EndBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.ListSelectorBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.SetParticleColorBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.ui.dragndrop.BrickAdapterInterface;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.ViewStateManager;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.util.IndentedBrickLayout;
import org.catrobat.catroid.utils.BrickCollapseManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b \u0018\u0000 \u0085\u00012\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\f\u0083\u0001\u0084\u0001\u0085\u0001\u0086\u0001\u0087\u0001\u0088\u0001B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\n2\b\u0010+\u001a\u0004\u0018\u00010\u0012J\u0018\u0010,\u001a\u00020)2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0002J\b\u00101\u001a\u00020)H\u0002J\u0010\u00102\u001a\u00020\n2\u0006\u00103\u001a\u00020\u0012H\u0002J \u00104\u001a\u00020)2\u0006\u0010+\u001a\u00020\u00122\u0006\u00105\u001a\u0002062\u0006\u0010*\u001a\u00020\nH\u0002J\b\u00107\u001a\u00020)H\u0002J\u0010\u00108\u001a\u00020)2\u0006\u0010-\u001a\u00020.H\u0002J\u0006\u00109\u001a\u00020)J\u0018\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u000206H\u0002J\u0010\u0010?\u001a\u0004\u0018\u00010\u00122\u0006\u0010@\u001a\u00020\nJ\u0010\u0010A\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\nH\u0002J\u0010\u0010B\u001a\u00020\n2\u0006\u00103\u001a\u00020\u0012H\u0002J\b\u0010C\u001a\u00020\nH\u0016J\u0010\u0010D\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\nH\u0016J\u0010\u0010E\u001a\u00020F2\u0006\u0010*\u001a\u00020\nH\u0016J&\u0010G\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\n\u0018\u00010H2\u0006\u0010I\u001a\u00020\u00122\u0006\u0010J\u001a\u00020\u0012H\u0002J\u0012\u0010K\u001a\u00020\n2\b\u00103\u001a\u0004\u0018\u00010\u0012H\u0016J\u0010\u0010L\u001a\u00020$2\u0006\u0010M\u001a\u00020NH\u0002J\"\u0010O\u001a\u00020;2\u0006\u0010*\u001a\u00020\n2\b\u0010P\u001a\u0004\u0018\u00010;2\u0006\u0010Q\u001a\u000206H\u0016J\u0010\u0010R\u001a\u00020)2\u0006\u0010+\u001a\u00020\u0012H\u0002J \u0010S\u001a\u00020)2\u0006\u0010+\u001a\u00020\u00122\u0006\u00105\u001a\u0002062\u0006\u0010*\u001a\u00020\nH\u0002J\u0010\u0010T\u001a\u00020)2\u0006\u0010+\u001a\u00020\u0012H\u0002J\u0010\u0010U\u001a\u00020)2\u0006\u0010+\u001a\u00020\u0012H\u0002J\u0010\u0010V\u001a\u00020$2\u0006\u00103\u001a\u00020\u0012H\u0002J\u0010\u0010W\u001a\u00020$2\u0006\u00103\u001a\u00020\u0012H\u0002J\u0010\u0010X\u001a\u00020$2\u0006\u00103\u001a\u00020\u0012H\u0002J\u0018\u0010Y\u001a\u00020$2\u0006\u0010Z\u001a\u00020\n2\u0006\u0010[\u001a\u00020$H\u0002J\u0018\u0010\\\u001a\u00020$2\u0006\u0010Z\u001a\u00020\n2\u0006\u0010[\u001a\u00020$H\u0002J \u0010]\u001a\u00020$2\u0006\u0010*\u001a\u00020\n2\u0006\u0010^\u001a\u00020\u00122\u0006\u0010_\u001a\u00020\u0012H\u0002J \u0010`\u001a\u00020)2\u0006\u0010*\u001a\u00020\n2\u0006\u0010^\u001a\u00020\u00122\u0006\u0010_\u001a\u00020\u0012H\u0002J\u001a\u0010a\u001a\u00020)2\u0006\u0010*\u001a\u00020\n2\b\u0010b\u001a\u0004\u0018\u00010\u0012H\u0016J\u0018\u0010c\u001a\u00020)2\u0006\u0010b\u001a\u00020d2\u0006\u0010_\u001a\u00020\u0012H\u0002J\b\u0010e\u001a\u00020$H\u0002J\b\u0010f\u001a\u00020)H\u0016J\u0010\u0010g\u001a\u00020)2\u0006\u0010*\u001a\u00020\nH\u0002J.\u0010h\u001a\u00020)2\f\u0010Q\u001a\b\u0012\u0002\b\u0003\u0018\u00010i2\u0006\u0010j\u001a\u00020;2\u0006\u0010*\u001a\u00020\n2\u0006\u0010k\u001a\u00020FH\u0016J.\u0010l\u001a\u00020$2\f\u0010Q\u001a\b\u0012\u0002\b\u0003\u0018\u00010i2\u0006\u0010j\u001a\u00020;2\u0006\u0010*\u001a\u00020\n2\u0006\u0010k\u001a\u00020FH\u0016J\u0018\u0010m\u001a\u00020$2\u0006\u0010n\u001a\u00020\n2\u0006\u0010o\u001a\u00020\nH\u0016J\u0016\u0010p\u001a\u00020$2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u001dH\u0016J\u0006\u0010q\u001a\u00020)J\u0018\u0010r\u001a\u00020$2\u0006\u0010Z\u001a\u00020\n2\u0006\u0010[\u001a\u00020$H\u0002J\b\u0010s\u001a\u00020)H\u0016J\u000e\u0010t\u001a\u00020)2\u0006\u0010\f\u001a\u00020\nJ\u0018\u0010u\u001a\u00020)2\u0006\u0010*\u001a\u00020\n2\u0006\u0010v\u001a\u00020$H\u0016J\u0010\u0010w\u001a\u00020)2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017J\u0010\u0010x\u001a\u00020)2\b\u0010y\u001a\u0004\u0018\u00010\u0019J\u0010\u0010z\u001a\u00020)2\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u0018\u0010{\u001a\u00020)2\u0006\u0010|\u001a\u00020$2\u0006\u0010*\u001a\u00020\nH\u0002J1\u0010}\u001a\u00020)2\u0006\u0010~\u001a\u00020\n2\u0006\u0010\u007f\u001a\u00020\n2\u0007\u0010\u0080\u0001\u001a\u00020\n2\u0006\u0010|\u001a\u00020$2\u0006\u0010[\u001a\u00020$H\u0002J\u0011\u0010\u0081\u0001\u001a\u00020)2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\t\u0010\u0082\u0001\u001a\u00020)H\u0002R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\b\n\u0000\u0012\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\u001d8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0014R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0004\n\u0002\u0010%R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0089\u0001"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter;", "Landroid/widget/BaseAdapter;", "Lorg/catrobat/catroid/ui/dragndrop/BrickAdapterInterface;", "Landroid/widget/AdapterView$OnItemClickListener;", "Landroid/widget/AdapterView$OnItemLongClickListener;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "(Lorg/catrobat/catroid/content/Sprite;)V", "brickDepthCache", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "checkBoxMode", "getCheckBoxMode$annotations", "()V", "firstConnectedItem", FirebaseAnalytics.Param.ITEMS, "", "Lorg/catrobat/catroid/content/bricks/Brick;", "getItems", "()Ljava/util/List;", "lastConnectedItem", "onItemClickListener", "Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$OnBrickClickListener;", "scriptChangedListener", "Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$OnScriptChangedListener;", "scripts", "Lorg/catrobat/catroid/content/Script;", "selectedItems", "", "getSelectedItems", "selectionListener", "Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$SelectionListener;", "selectionManager", "Lorg/catrobat/catroid/ui/recyclerview/adapter/multiselection/MultiSelectionManager;", "useIndentationCached", "", "Ljava/lang/Boolean;", "viewStateManager", "Lorg/catrobat/catroid/ui/recyclerview/adapter/draganddrop/ViewStateManager;", "addItem", "", "position", "item", "applyHighlightOptimized", "holder", "Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$BrickViewHolder;", "severity", "Lorg/catrobat/catroid/codeanalysis/Severity;", "cacheBrickProperties", "calculateBrickDepth", "brick", "checkBoxClickListener", "itemView", "Landroid/view/ViewGroup;", "clearConnectedItems", "clearHighlightsOptimized", "clearSelection", "createUnknownView", "Landroid/view/View;", "className", "", "container", "findByHash", "hashCode", "getBrickAbovePosition", "getBrickDepthCached", "getCount", "getItem", "getItemId", "", "getParentBrickInDragAndDropList", "Lkotlin/Pair;", "brickAboveTarget", "enclosureBrick", "getPosition", "getUseIndentation", "context", "Landroid/content/Context;", "getView", "convertView", "parent", "handleCheckBoxModeAll", "handleCheckBoxModeConnectedOnly", "handleCheckBoxModeNone", "handleCheckBoxModeScriptsOnly", "isBrickVisibleInCollapsedHierarchy", "isElseBrick", "isEndOrElseBrick", "isItemOfNewScript", "brickPosition", "scriptSelected", "isItemWithinConnectedRange", "moveEndIntoExtendedSection", "endBrick", "brickAboveTargetPosition", "moveEndTo", "moveItemTo", "itemToMove", "moveScript", "Lorg/catrobat/catroid/content/bricks/ScriptBrick;", "noConnectedItemsSelected", "notifyDataSetChanged", "onCheckBoxClick", "onItemClick", "Landroid/widget/AdapterView;", Promotion.ACTION_VIEW, "id", "onItemLongClick", "onItemMove", "sourcePosition", "targetPosition", "removeItems", "selectAllCommentedOutBricks", "selectableForCopy", "setAllPositionsVisible", "setCheckBoxMode", "setItemVisible", "visible", "setOnItemClickListener", "setOnScriptChangedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setSelectionListener", "setSelectionTo", "selected", "updateConnectedItems", "selectedPosition", "firstFlatListPosition", "lastFlatListPosition", "updateItems", "updateItemsFromCurrentScripts", "BrickViewHolder", "CheckBoxMode", "Companion", "OnBrickClickListener", "OnScriptChangedListener", "SelectionListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickAdapter extends BaseAdapter implements BrickAdapterInterface, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public static final int ALL = 1;
    public static final int CONNECTED_ONLY = 3;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final float DISABLED_BRICK_ALPHA = 0.8f;
    public static final int NONE = 0;
    public static final int SCRIPTS_ONLY = 2;
    private final HashMap<Integer, Integer> brickDepthCache;
    private int checkBoxMode;
    private int firstConnectedItem;
    private final List<Brick> items;
    private int lastConnectedItem;
    private OnBrickClickListener onItemClickListener;
    private OnScriptChangedListener scriptChangedListener;
    private List<Script> scripts;
    private SelectionListener selectionListener;
    private final MultiSelectionManager selectionManager;
    private final Sprite sprite;
    private Boolean useIndentationCached;
    private final ViewStateManager viewStateManager;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$CheckBoxMode.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickAdapter.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0081\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$CheckBoxMode;", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface CheckBoxMode {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$OnBrickClickListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickAdapter.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$OnBrickClickListener;", "", "onBrickClick", "", "item", "Lorg/catrobat/catroid/content/bricks/Brick;", "position", "", "onBrickLongClick", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnBrickClickListener {
        void onBrickClick(Brick item, int position);

        boolean onBrickLongClick(Brick item, int position);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$OnScriptChangedListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickAdapter.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$OnScriptChangedListener;", "", "onScriptChanged", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnScriptChangedListener {
        void onScriptChanged();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$SelectionListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickAdapter.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$SelectionListener;", "", "onSelectionChanged", "", "selectedItemCnt", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface SelectionListener {
        void onSelectionChanged(int selectedItemCnt);
    }

    @JvmStatic
    public static final void colorAsCommentedOut(Drawable drawable) {
        INSTANCE.colorAsCommentedOut(drawable);
    }

    private static /* synthetic */ void getCheckBoxMode$annotations() {
    }

    public BrickAdapter(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        this.sprite = sprite;
        this.scripts = new ArrayList();
        this.firstConnectedItem = -1;
        this.lastConnectedItem = -1;
        this.selectionManager = new MultiSelectionManager();
        this.viewStateManager = new ViewStateManager();
        this.items = new ArrayList();
        this.brickDepthCache = new HashMap<>();
        updateItems(sprite);
    }

    public final void setOnScriptChangedListener(OnScriptChangedListener listener) {
        this.scriptChangedListener = listener;
    }

    public final List<Brick> getItems() {
        return this.items;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickAdapter.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$Companion;", "", "()V", "ALL", "", "CONNECTED_ONLY", "DISABLED_BRICK_ALPHA", "", "NONE", "SCRIPTS_ONLY", "colorAsCommentedOut", "", "background", "Landroid/graphics/drawable/Drawable;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void colorAsCommentedOut(Drawable background) {
            Intrinsics.checkNotNullParameter(background, "background");
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0.0f);
            ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
            background.mutate();
            background.setColorFilter(colorMatrixColorFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$BrickViewHolder.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickAdapter.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00032\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00060-H\u0002R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001e\u0010\u001a\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u001b\u0010\u0016\"\u0004\b\u001c\u0010\u0018R\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\f\"\u0004\b\u001f\u0010\u0004R!\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060!8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b\"\u0010#R\u001c\u0010&\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\f\"\u0004\b(\u0010\u0004¨\u0006."}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter$BrickViewHolder;", "", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "collapseToggle", "Landroid/widget/TextView;", "getCollapseToggle", "()Landroid/widget/TextView;", "setCollapseToggle", "(Landroid/widget/TextView;)V", "getItemView", "()Landroid/view/View;", "lastAppliedSeverity", "Lorg/catrobat/catroid/codeanalysis/Severity;", "getLastAppliedSeverity", "()Lorg/catrobat/catroid/codeanalysis/Severity;", "setLastAppliedSeverity", "(Lorg/catrobat/catroid/codeanalysis/Severity;)V", "lastIsCollapsed", "", "getLastIsCollapsed", "()Ljava/lang/Boolean;", "setLastIsCollapsed", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "lastIsCommentedOutOrEmpty", "getLastIsCommentedOutOrEmpty", "setLastIsCommentedOutOrEmpty", "originalContainer", "getOriginalContainer", "setOriginalContainer", "textViews", "", "getTextViews", "()Ljava/util/List;", "textViews$delegate", "Lkotlin/Lazy;", "toggleWrapper", "getToggleWrapper", "setToggleWrapper", "findTextViewsRecursive", "", Promotion.ACTION_VIEW, SchemaSymbols.ATTVAL_LIST, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class BrickViewHolder {
        private TextView collapseToggle;
        private final View itemView;
        private Severity lastAppliedSeverity;
        private Boolean lastIsCollapsed;
        private Boolean lastIsCommentedOutOrEmpty;
        private View originalContainer;

        /* JADX INFO: renamed from: textViews$delegate, reason: from kotlin metadata */
        private final Lazy textViews;
        private View toggleWrapper;

        public BrickViewHolder(View itemView) {
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.itemView = itemView;
            this.textViews = LazyKt.lazy(new Function0<ArrayList<TextView>>() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter$BrickViewHolder$textViews$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ArrayList<TextView> invoke() {
                    ArrayList<TextView> arrayList = new ArrayList<>();
                    BrickAdapter.BrickViewHolder brickViewHolder = this.this$0;
                    brickViewHolder.findTextViewsRecursive(brickViewHolder.getItemView(), arrayList);
                    return arrayList;
                }
            });
        }

        public final View getItemView() {
            return this.itemView;
        }

        public final List<TextView> getTextViews() {
            return (List) this.textViews.getValue();
        }

        public final View getToggleWrapper() {
            return this.toggleWrapper;
        }

        public final void setToggleWrapper(View view) {
            this.toggleWrapper = view;
        }

        public final TextView getCollapseToggle() {
            return this.collapseToggle;
        }

        public final void setCollapseToggle(TextView textView) {
            this.collapseToggle = textView;
        }

        public final View getOriginalContainer() {
            return this.originalContainer;
        }

        public final void setOriginalContainer(View view) {
            this.originalContainer = view;
        }

        public final Severity getLastAppliedSeverity() {
            return this.lastAppliedSeverity;
        }

        public final void setLastAppliedSeverity(Severity severity) {
            this.lastAppliedSeverity = severity;
        }

        public final Boolean getLastIsCommentedOutOrEmpty() {
            return this.lastIsCommentedOutOrEmpty;
        }

        public final void setLastIsCommentedOutOrEmpty(Boolean bool) {
            this.lastIsCommentedOutOrEmpty = bool;
        }

        public final Boolean getLastIsCollapsed() {
            return this.lastIsCollapsed;
        }

        public final void setLastIsCollapsed(Boolean bool) {
            this.lastIsCollapsed = bool;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void findTextViewsRecursive(View view, List<TextView> list) {
            if (view instanceof TextView) {
                list.add(view);
                return;
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(...)");
                    findTextViewsRecursive(childAt, list);
                }
            }
        }
    }

    public final void setOnItemClickListener(OnBrickClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public final void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public final void setCheckBoxMode(int checkBoxMode) {
        this.checkBoxMode = checkBoxMode;
        notifyDataSetChanged();
    }

    public final void updateItems(Sprite sprite) {
        List<Script> scriptList;
        if (sprite != null && (scriptList = sprite.getScriptList()) != null) {
            this.scripts = scriptList;
        }
        updateItemsFromCurrentScripts();
    }

    private final void updateItemsFromCurrentScripts() {
        this.items.clear();
        this.sprite.removeAllEmptyScriptBricks();
        ArrayList<Brick> arrayList = new ArrayList();
        for (Script script : this.scripts) {
            script.setParents();
            script.addToFlatList(arrayList);
        }
        for (Brick brick : arrayList) {
            Intrinsics.checkNotNull(brick);
            if (isBrickVisibleInCollapsedHierarchy(brick)) {
                this.items.add(brick);
            }
        }
        notifyDataSetChanged();
        OnScriptChangedListener onScriptChangedListener = this.scriptChangedListener;
        if (onScriptChangedListener != null) {
            onScriptChangedListener.onScriptChanged();
        }
    }

    private final boolean isBrickVisibleInCollapsedHierarchy(Brick brick) {
        for (Brick parent = brick.getParent(); parent != null; parent = parent.getParent()) {
            if (BrickCollapseManager.INSTANCE.isCollapsed(parent)) {
                return false;
            }
        }
        return true;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        final Brick brick = this.items.get(position);
        if ((brick instanceof SetParticleColorBrick) && ((SetParticleColorBrick) brick).getFormulaWithBrickField(Brick.BrickField.COLOR, true) == null) {
            return createUnknownView("NoneBrick", parent);
        }
        View view = brick.getView(parent.getContext());
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) view;
        Object tag = viewGroup.getTag();
        BrickViewHolder brickViewHolder = tag instanceof BrickViewHolder ? (BrickViewHolder) tag : null;
        if (brickViewHolder == null) {
            brickViewHolder = new BrickViewHolder(viewGroup);
            viewGroup.setTag(brickViewHolder);
        }
        AnalysisResult resultFor = AnalysisManager.INSTANCE.getResultFor(brick);
        Severity severity = resultFor != null ? resultFor.getSeverity() : null;
        if (brickViewHolder.getLastAppliedSeverity() != severity) {
            if (severity == null) {
                clearHighlightsOptimized(brickViewHolder);
            } else {
                applyHighlightOptimized(brickViewHolder, severity);
            }
            brickViewHolder.setLastAppliedSeverity(severity);
        }
        float f = this.viewStateManager.isEnabled(position) ? 1.0f : 0.8f;
        float f2 = brick.isPhantom() ? 0.5f * f : f;
        if (viewGroup.getAlpha() != f2) {
            viewGroup.setAlpha(f2);
        }
        View childAt = viewGroup.getChildAt(1);
        if (brick instanceof UserDefinedReceiverBrick) {
            View childAt2 = viewGroup.getChildAt(1);
            Intrinsics.checkNotNull(childAt2, "null cannot be cast to non-null type android.view.ViewGroup");
            childAt = ((ViewGroup) childAt2).getChildAt(0);
        }
        boolean z = brick.isCommentedOut() || (brick instanceof EmptyEventBrick);
        if (!Intrinsics.areEqual(brickViewHolder.getLastIsCommentedOutOrEmpty(), Boolean.valueOf(z))) {
            Drawable background = childAt.getBackground();
            if (z) {
                Companion companion = INSTANCE;
                Intrinsics.checkNotNull(background);
                companion.colorAsCommentedOut(background);
            } else {
                background.clearColorFilter();
            }
            brickViewHolder.setLastIsCommentedOutOrEmpty(Boolean.valueOf(z));
        }
        if (brick instanceof CompositeBrick) {
            ViewGroup viewGroup2 = viewGroup instanceof ViewGroup ? viewGroup : null;
            if (viewGroup2 != null) {
                boolean zIsCollapsed = BrickCollapseManager.INSTANCE.isCollapsed(brick);
                float f3 = parent.getContext().getResources().getDisplayMetrics().density;
                View toggleWrapper = brickViewHolder.getToggleWrapper();
                TextView collapseToggle = brickViewHolder.getCollapseToggle();
                if (toggleWrapper == null && (toggleWrapper = viewGroup2.findViewWithTag("toggle_wrapper")) != null) {
                    brickViewHolder.setToggleWrapper(toggleWrapper);
                    collapseToggle = (TextView) toggleWrapper.findViewWithTag("collapse_toggle");
                    brickViewHolder.setCollapseToggle(collapseToggle);
                }
                if (toggleWrapper == null) {
                    View childAt3 = viewGroup2.getChildAt(1);
                    childAt3.setPadding(childAt3.getPaddingLeft(), childAt3.getPaddingTop(), childAt3.getPaddingRight() + ((int) (42 * f3)), childAt3.getPaddingBottom());
                    RelativeLayout relativeLayout = new RelativeLayout(parent.getContext());
                    relativeLayout.setTag("toggle_wrapper");
                    relativeLayout.setLayoutParams(childAt3.getLayoutParams());
                    RelativeLayout relativeLayout2 = relativeLayout;
                    viewGroup2.removeViewAt(1);
                    childAt3.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                    collapseToggle = new TextView(parent.getContext());
                    collapseToggle.setTag("collapse_toggle");
                    collapseToggle.setTextSize(16.0f);
                    collapseToggle.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
                    collapseToggle.setTextColor(-1);
                    collapseToggle.setGravity(17);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (70 * f3), -1);
                    layoutParams.addRule(11);
                    layoutParams.addRule(15);
                    collapseToggle.setLayoutParams(layoutParams);
                    collapseToggle.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            BrickAdapter.getView$lambda$5$lambda$4(brick, this, view2);
                        }
                    });
                    RelativeLayout relativeLayout3 = relativeLayout2;
                    relativeLayout3.addView(childAt3);
                    relativeLayout3.addView(collapseToggle);
                    viewGroup2.addView(relativeLayout2, 1);
                    brickViewHolder.setToggleWrapper(relativeLayout2);
                    brickViewHolder.setCollapseToggle(collapseToggle);
                    brickViewHolder.setOriginalContainer(childAt3);
                }
                if (!Intrinsics.areEqual(brickViewHolder.getLastIsCollapsed(), Boolean.valueOf(zIsCollapsed))) {
                    if (collapseToggle != null) {
                        collapseToggle.setText(zIsCollapsed ? "[＋]" : "[－]");
                    }
                    if (zIsCollapsed) {
                        f *= 0.82f;
                    }
                    viewGroup.setAlpha(f);
                    brickViewHolder.setLastIsCollapsed(Boolean.valueOf(zIsCollapsed));
                }
            }
        }
        checkBoxClickListener(brick, viewGroup, position);
        brick.getCheckBox().setChecked(this.selectionManager.isPositionSelected(position));
        brick.getCheckBox().setEnabled(this.viewStateManager.isEnabled(position));
        Context context = parent.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        IndentedBrickLayout indentedBrickLayout = viewGroup;
        if (getUseIndentation(context)) {
            int brickDepthCached = getBrickDepthCached(brick);
            Object parent2 = viewGroup.getParent();
            if (parent2 instanceof IndentedBrickLayout) {
                IndentedBrickLayout indentedBrickLayout2 = (IndentedBrickLayout) parent2;
                Object tag2 = indentedBrickLayout2.getTag();
                Integer num = tag2 instanceof Integer ? (Integer) tag2 : null;
                if (num == null || num.intValue() != brickDepthCached) {
                    indentedBrickLayout2.setDepth(brickDepthCached);
                    indentedBrickLayout2.setTag(Integer.valueOf(brickDepthCached));
                }
                indentedBrickLayout = (View) parent2;
            } else {
                ViewGroup viewGroup3 = parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null;
                if (viewGroup3 != null) {
                    viewGroup3.removeView(indentedBrickLayout);
                }
                Context context2 = parent.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                IndentedBrickLayout indentedBrickLayout3 = new IndentedBrickLayout(context2, brickDepthCached);
                indentedBrickLayout3.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
                indentedBrickLayout3.setTag(Integer.valueOf(brickDepthCached));
                indentedBrickLayout3.addView(indentedBrickLayout, new LinearLayout.LayoutParams(-1, -2));
                if (indentedBrickLayout3.getMeasuredWidth() == 0 || indentedBrickLayout3.getMeasuredHeight() == 0) {
                    indentedBrickLayout3.measure(View.MeasureSpec.makeMeasureSpec(parent.getWidth() > 0 ? parent.getWidth() : parent.getResources().getDisplayMetrics().widthPixels, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                    indentedBrickLayout3.layout(0, 0, indentedBrickLayout3.getMeasuredWidth(), indentedBrickLayout3.getMeasuredHeight());
                }
                indentedBrickLayout = indentedBrickLayout3;
            }
        } else {
            ViewParent parent3 = viewGroup.getParent();
            if (parent3 instanceof IndentedBrickLayout) {
                ((IndentedBrickLayout) parent3).removeView(indentedBrickLayout);
            }
        }
        viewGroup.setVisibility(0);
        indentedBrickLayout.setVisibility(this.viewStateManager.isVisible(position) ? 0 : 4);
        return indentedBrickLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getView$lambda$5$lambda$4(Brick item, BrickAdapter this$0, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BrickCollapseManager.INSTANCE.toggleCollapsed(item);
        this$0.updateItemsFromCurrentScripts();
    }

    private final void clearHighlightsOptimized(BrickViewHolder holder) {
        List<TextView> textViews = holder.getTextViews();
        int size = textViews.size();
        for (int i = 0; i < size; i++) {
            textViews.get(i).setForeground(null);
        }
    }

    private final void applyHighlightOptimized(BrickViewHolder holder, Severity severity) {
        List<TextView> textViews = holder.getTextViews();
        Context context = holder.getItemView().getContext();
        final int i = severity == Severity.ERROR ? -48060 : -17613;
        final float f = context.getResources().getDisplayMetrics().density;
        int size = textViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            TextView textView = textViews.get(i2);
            final RectShape rectShape = new RectShape();
            textView.setForeground(new ShapeDrawable(i, f, rectShape) { // from class: org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter$applyHighlightOptimized$underline$1
                final /* synthetic */ float $density;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(rectShape);
                    this.$density = f;
                    getPaint().setColor(i);
                }

                @Override // android.graphics.drawable.ShapeDrawable, android.graphics.drawable.Drawable
                public void draw(Canvas canvas) {
                    Intrinsics.checkNotNullParameter(canvas, "canvas");
                    Rect bounds = getBounds();
                    Intrinsics.checkNotNullExpressionValue(bounds, "getBounds(...)");
                    canvas.drawRect(bounds.left, bounds.bottom - (this.$density * 2.0f), bounds.right, bounds.bottom, getPaint());
                }
            });
        }
    }

    private final View createUnknownView(String className, ViewGroup container) {
        View viewInflate = LayoutInflater.from(container.getContext()).inflate(R.layout.brick_none, container, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        return viewInflate;
    }

    private final void checkBoxClickListener(Brick item, ViewGroup itemView, final int position) {
        item.getCheckBox().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BrickAdapter.checkBoxClickListener$lambda$6(this.f$0, position, view);
            }
        });
        if (item.isPhantom()) {
            item.getCheckBox().setVisibility(8);
            if (item instanceof FormulaBrick) {
                item.disableSpinners();
                return;
            }
            return;
        }
        int i = this.checkBoxMode;
        if (i == 0) {
            handleCheckBoxModeNone(item);
            return;
        }
        if (i == 1) {
            handleCheckBoxModeAll(item);
        } else if (i == 2) {
            handleCheckBoxModeScriptsOnly(item);
        } else {
            if (i != 3) {
                return;
            }
            handleCheckBoxModeConnectedOnly(item, itemView, position);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkBoxClickListener$lambda$6(BrickAdapter this$0, int i, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onCheckBoxClick(i);
    }

    private final void handleCheckBoxModeScriptsOnly(Brick item) {
        item.getCheckBox().setVisibility(item instanceof ScriptBrick ? 0 : 4);
        item.disableSpinners();
    }

    private final void handleCheckBoxModeAll(Brick item) {
        item.getCheckBox().setVisibility(0);
        item.disableSpinners();
    }

    private final void handleCheckBoxModeNone(Brick item) {
        item.getCheckBox().setVisibility(8);
        if (item instanceof FormulaBrick) {
            ((FormulaBrick) item).setClickListeners();
        } else if (item instanceof ListSelectorBrick) {
            ((ListSelectorBrick) item).setClickListeners();
        }
    }

    private final void handleCheckBoxModeConnectedOnly(Brick item, ViewGroup itemView, int position) {
        if (item instanceof UserDefinedReceiverBrick) {
            this.viewStateManager.setEnabled(false, position);
            itemView.setAlpha(0.8f);
        }
        item.getCheckBox().setVisibility(0);
        item.disableSpinners();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (this.checkBoxMode == 0) {
            Brick brick = this.items.get(position);
            if (brick.isPhantom()) {
                KoveAutocompleteController.INSTANCE.getInstance().acceptSuggestions();
                return;
            }
            OnBrickClickListener onBrickClickListener = this.onItemClickListener;
            if (onBrickClickListener != null) {
                onBrickClickListener.onBrickClick(brick, position);
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id2) {
        OnBrickClickListener onBrickClickListener;
        Intrinsics.checkNotNullParameter(view, "view");
        if (this.checkBoxMode != 0) {
            return false;
        }
        Brick brick = this.items.get(position);
        if (!brick.isPhantom() && (onBrickClickListener = this.onItemClickListener) != null) {
            onBrickClickListener.onBrickLongClick(brick, position);
        }
        return true;
    }

    private final void onCheckBoxClick(int position) {
        setSelectionTo(!this.selectionManager.isPositionSelected(position), position);
        SelectionListener selectionListener = this.selectionListener;
        if (selectionListener != null) {
            selectionListener.onSelectionChanged(this.selectionManager.getSelectedPositions().size());
        }
        notifyDataSetChanged();
    }

    private final void setSelectionTo(boolean selected, int position) {
        Brick brick = this.items.get(position);
        ArrayList arrayList = new ArrayList();
        brick.addToFlatList(arrayList);
        boolean z = brick instanceof ScriptBrick;
        if (selected && noConnectedItemsSelected()) {
            this.firstConnectedItem = position - 1;
            this.lastConnectedItem = position + 1;
        }
        int size = arrayList.size();
        int iIndexOf = -1;
        for (int i = 0; i < size; i++) {
            iIndexOf = this.items.indexOf(arrayList.get(i));
            this.selectionManager.setSelectionTo(selected, iIndexOf);
            if (i > 0) {
                this.viewStateManager.setEnabled(!selected, iIndexOf);
            }
        }
        if (this.checkBoxMode == 3) {
            updateConnectedItems(position, this.items.indexOf(arrayList.get(0)), iIndexOf, selected, z);
        }
    }

    private final void updateConnectedItems(int selectedPosition, int firstFlatListPosition, int lastFlatListPosition, boolean selected, boolean scriptSelected) {
        if (selected) {
            if (lastFlatListPosition >= this.lastConnectedItem) {
                this.lastConnectedItem = lastFlatListPosition + 1;
            }
            if (firstFlatListPosition <= this.firstConnectedItem) {
                this.firstConnectedItem = firstFlatListPosition - 1;
            }
        } else {
            if (selectedPosition == this.firstConnectedItem + 1) {
                this.firstConnectedItem = firstFlatListPosition;
            }
            if (selectedPosition == this.lastConnectedItem - 1) {
                this.lastConnectedItem = firstFlatListPosition;
            }
            if (this.selectionManager.getSelectedPositions().isEmpty()) {
                clearConnectedItems();
            }
        }
        Iterator<Brick> it = this.items.iterator();
        while (it.hasNext()) {
            int iIndexOf = this.items.indexOf(it.next());
            this.viewStateManager.setEnabled(selectableForCopy(iIndexOf, scriptSelected), iIndexOf);
        }
    }

    private final boolean selectableForCopy(int brickPosition, boolean scriptSelected) {
        return noConnectedItemsSelected() || (isItemWithinConnectedRange(brickPosition, scriptSelected) && !isItemOfNewScript(brickPosition, scriptSelected));
    }

    private final boolean isItemWithinConnectedRange(int brickPosition, boolean scriptSelected) {
        int i = this.firstConnectedItem;
        if (brickPosition >= i && brickPosition <= i + 1) {
            return true;
        }
        int i2 = this.lastConnectedItem;
        return brickPosition <= i2 && brickPosition >= i2 - 1 && !scriptSelected;
    }

    private final boolean isItemOfNewScript(int brickPosition, boolean scriptSelected) {
        return (this.lastConnectedItem == brickPosition && (this.items.get(brickPosition) instanceof ScriptBrick)) || (scriptSelected && brickPosition <= this.firstConnectedItem);
    }

    private final boolean noConnectedItemsSelected() {
        return this.firstConnectedItem == -1 && this.lastConnectedItem == -1;
    }

    private final void clearConnectedItems() {
        this.firstConnectedItem = -1;
        this.lastConnectedItem = -1;
    }

    public final List<Brick> getSelectedItems() {
        ArrayList arrayList = new ArrayList();
        for (Integer num : this.selectionManager.getSelectedPositions()) {
            List<Brick> list = this.items;
            Intrinsics.checkNotNull(num);
            arrayList.add(list.get(num.intValue()));
        }
        return arrayList;
    }

    public final void clearSelection() {
        this.selectionManager.clearSelection();
        this.viewStateManager.clearDisabledPositions();
        clearConnectedItems();
        notifyDataSetChanged();
    }

    @Override // org.catrobat.catroid.ui.dragndrop.BrickAdapterInterface
    public void setItemVisible(int position, boolean visible) {
        this.viewStateManager.setVisible(position, visible);
    }

    @Override // org.catrobat.catroid.ui.dragndrop.BrickAdapterInterface
    public void setAllPositionsVisible() {
        this.viewStateManager.setAllPositionsVisible();
    }

    public final void selectAllCommentedOutBricks() {
        int size = this.items.size();
        for (int i = 0; i < size; i++) {
            setSelectionTo(this.items.get(i).isCommentedOut(), i);
        }
        notifyDataSetChanged();
    }

    public final void addItem(int position, Brick item) {
        if (item != null) {
            this.items.add(position, item);
        }
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public Brick getItem(int position) {
        return this.items.get(position);
    }

    public final Brick findByHash(int hashCode) {
        for (Brick brick : this.items) {
            if (brick.hashCode() == hashCode) {
                return brick;
            }
        }
        return null;
    }

    @Override // org.catrobat.catroid.ui.dragndrop.BrickAdapterInterface
    public boolean removeItems(List<? extends Brick> items) {
        Intrinsics.checkNotNullParameter(items, "items");
        if (!this.items.removeAll(items)) {
            return false;
        }
        notifyDataSetChanged();
        return true;
    }

    private final boolean getUseIndentation(Context context) {
        if (this.useIndentationCached == null) {
            this.useIndentationCached = Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("pref_enable_brick_indentation", false));
        }
        Boolean bool = this.useIndentationCached;
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    private final void cacheBrickProperties() {
        this.brickDepthCache.clear();
        for (Brick brick : this.items) {
            this.brickDepthCache.put(Integer.valueOf(brick.hashCode()), Integer.valueOf(calculateBrickDepth(brick)));
        }
    }

    private final int getBrickDepthCached(Brick brick) {
        Integer num = this.brickDepthCache.get(Integer.valueOf(brick.hashCode()));
        return num == null ? calculateBrickDepth(brick) : num.intValue();
    }

    private final int calculateBrickDepth(Brick brick) {
        int i = 0;
        for (Brick parent = brick.getParent(); parent != null; parent = parent.getParent()) {
            if (!isElseBrick(parent)) {
                i++;
            }
        }
        return (i <= 0 || !isEndOrElseBrick(brick)) ? i : i - 1;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        this.useIndentationCached = null;
        cacheBrickProperties();
        super.notifyDataSetChanged();
    }

    @Override // org.catrobat.catroid.ui.dragndrop.BrickAdapterInterface
    public int getPosition(Brick brick) {
        return CollectionsKt.indexOf((List<? extends Brick>) this.items, brick);
    }

    @Override // org.catrobat.catroid.ui.dragndrop.BrickAdapterInterface
    public boolean onItemMove(int sourcePosition, int targetPosition) {
        if (sourcePosition < 0 || sourcePosition >= this.items.size() || targetPosition < 0 || targetPosition >= this.items.size()) {
            return false;
        }
        Brick brick = this.items.get(sourcePosition);
        if (!(brick instanceof ScriptBrick) && targetPosition == 0) {
            return false;
        }
        if (!(brick instanceof EndBrick) && brick.getAllParts().contains(this.items.get(targetPosition))) {
            return false;
        }
        Collections.swap(this.items, sourcePosition, targetPosition);
        return true;
    }

    private final Pair<Brick, Integer> getParentBrickInDragAndDropList(Brick brickAboveTarget, Brick enclosureBrick) {
        if (Intrinsics.areEqual(brickAboveTarget, enclosureBrick)) {
            return TuplesKt.to(brickAboveTarget, 0);
        }
        while (brickAboveTarget.getParent() != null && !enclosureBrick.getAllParts().contains(brickAboveTarget.getParent()) && !enclosureBrick.getDragAndDropTargetList().contains(brickAboveTarget)) {
            brickAboveTarget = brickAboveTarget.getParent();
            Intrinsics.checkNotNullExpressionValue(brickAboveTarget, "getParent(...)");
        }
        if (brickAboveTarget.getParent() == enclosureBrick || enclosureBrick.getDragAndDropTargetList().contains(brickAboveTarget)) {
            return TuplesKt.to(brickAboveTarget, Integer.valueOf(enclosureBrick.getDragAndDropTargetList().indexOf(brickAboveTarget) + 1));
        }
        return null;
    }

    private final boolean moveEndIntoExtendedSection(int position, Brick endBrick, Brick brickAboveTargetPosition) {
        Brick parent = endBrick.getParent();
        while (brickAboveTargetPosition.getParent() != null) {
            if (brickAboveTargetPosition instanceof CompositeBrick) {
                moveItemTo(position, parent);
                return true;
            }
            brickAboveTargetPosition = brickAboveTargetPosition.getParent();
            Intrinsics.checkNotNullExpressionValue(brickAboveTargetPosition, "getParent(...)");
            if (!(brickAboveTargetPosition instanceof CompositeBrick) || Intrinsics.areEqual(brickAboveTargetPosition, parent.getParent())) {
                return false;
            }
        }
        return false;
    }

    private final void moveEndTo(int position, Brick endBrick, Brick brickAboveTargetPosition) {
        if (endBrick.getScript() != brickAboveTargetPosition.getScript()) {
            return;
        }
        Brick parent = endBrick.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.CompositeBrick");
        CompositeBrick compositeBrick = (CompositeBrick) parent;
        List<Brick> nestedBricks = compositeBrick.getNestedBricks();
        if (compositeBrick.hasSecondaryList()) {
            nestedBricks = compositeBrick.getSecondaryNestedBricks();
            parent = compositeBrick.getAllParts().get(1);
        }
        Intrinsics.checkNotNull(parent);
        Pair<Brick, Integer> parentBrickInDragAndDropList = getParentBrickInDragAndDropList(brickAboveTargetPosition, parent);
        if (parentBrickInDragAndDropList == null) {
            parentBrickInDragAndDropList = TuplesKt.to(null, 0);
        }
        Brick brickComponent1 = parentBrickInDragAndDropList.component1();
        int iIntValue = parentBrickInDragAndDropList.component2().intValue();
        if (getPosition(brickAboveTargetPosition) + 1 < getPosition(parent)) {
            return;
        }
        if (brickComponent1 == null) {
            if (moveEndIntoExtendedSection(position, endBrick, brickAboveTargetPosition)) {
                return;
            }
            Brick parent2 = endBrick.getParent().getParent();
            Intrinsics.checkNotNullExpressionValue(parent2, "getParent(...)");
            Pair<Brick, Integer> parentBrickInDragAndDropList2 = getParentBrickInDragAndDropList(brickAboveTargetPosition, parent2);
            if (parentBrickInDragAndDropList2 == null) {
                return;
            }
            Brick brickComponent2 = parentBrickInDragAndDropList2.component1();
            int iIntValue2 = parentBrickInDragAndDropList2.component2().intValue();
            Brick parent3 = brickComponent2.getParent();
            Intrinsics.checkNotNullExpressionValue(parent3, "getParent(...)");
            int iIndexOf = parent3.getDragAndDropTargetList().indexOf(endBrick.getParent()) + 1;
            for (int i = iIndexOf; i < iIntValue2; i++) {
                Brick brickRemove = parent3.getDragAndDropTargetList().remove(iIndexOf);
                brickRemove.setParent(parent);
                nestedBricks.add(brickRemove);
            }
            return;
        }
        Brick parent4 = endBrick.getParent().getParent();
        int iIndexOf2 = parent4.getDragAndDropTargetList().indexOf(endBrick.getParent());
        for (IndexedValue indexedValue : CollectionsKt.withIndex(RangesKt.until(iIntValue, nestedBricks.size()))) {
            Brick brickRemove2 = nestedBricks.remove(iIntValue);
            brickRemove2.setParent(parent4);
            parent4.getDragAndDropTargetList().add(indexedValue.getIndex() + iIndexOf2 + 1, brickRemove2);
        }
    }

    @Override // org.catrobat.catroid.ui.dragndrop.BrickAdapterInterface
    public void moveItemTo(int position, Brick itemToMove) {
        Brick brickAbovePosition = getBrickAbovePosition(position);
        if (itemToMove instanceof ScriptBrick) {
            moveScript((ScriptBrick) itemToMove, brickAbovePosition);
        } else if (itemToMove instanceof EndBrick) {
            moveEndTo(position, itemToMove, brickAbovePosition);
        } else {
            Iterator<Script> it = this.scripts.iterator();
            while (it.hasNext()) {
                it.next().removeBrick(itemToMove);
            }
            int positionInDragAndDropTargetList = brickAbovePosition.getPositionInDragAndDropTargetList() + 1;
            List<Brick> dragAndDropTargetList = brickAbovePosition.getDragAndDropTargetList();
            if (positionInDragAndDropTargetList < dragAndDropTargetList.size()) {
                dragAndDropTargetList.add(positionInDragAndDropTargetList, itemToMove);
            } else {
                dragAndDropTargetList.add(itemToMove);
            }
        }
        updateItemsFromCurrentScripts();
    }

    private final void moveScript(ScriptBrick itemToMove, Brick brickAboveTargetPosition) {
        Script script = itemToMove.getScript();
        Script script2 = brickAboveTargetPosition.getScript();
        List<Brick> brickList = script.getBrickList();
        List<Brick> brickList2 = script2.getBrickList();
        if (brickList.isEmpty()) {
            Intrinsics.checkNotNull(brickList2);
            if (!brickList2.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                int size = brickList2.size();
                for (int positionInScript = brickAboveTargetPosition.getPositionInScript() + 1; positionInScript < size; positionInScript++) {
                    Brick brick = brickList2.get(positionInScript);
                    Intrinsics.checkNotNullExpressionValue(brick, "get(...)");
                    arrayList.add(brick);
                }
                ArrayList arrayList2 = arrayList;
                brickList.addAll(arrayList2);
                brickList2.removeAll(arrayList2);
            }
        }
        this.scripts.remove(script);
        int iIndexOf = this.scripts.indexOf(script2) + 1;
        if (iIndexOf == this.scripts.size()) {
            List<Script> list = this.scripts;
            Intrinsics.checkNotNull(script);
            list.add(script);
        } else {
            List<Script> list2 = this.scripts;
            Intrinsics.checkNotNull(script);
            list2.add(iIndexOf, script);
        }
    }

    private final Brick getBrickAbovePosition(int position) {
        if (position > 0) {
            position--;
        }
        return this.items.get(position);
    }

    private final boolean isElseBrick(Brick brick) {
        if (brick instanceof CompositeBrick) {
            return false;
        }
        String simpleName = brick.getClass().getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        return StringsKt.endsWith$default(simpleName, "ElseBrick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) simpleName, (CharSequence) "Else", false, 2, (Object) null);
    }

    private final boolean isEndOrElseBrick(Brick brick) {
        if (brick instanceof CompositeBrick) {
            return false;
        }
        if (brick instanceof EndBrick) {
            return true;
        }
        String simpleName = brick.getClass().getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        return StringsKt.endsWith$default(simpleName, "EndBrick", false, 2, (Object) null) || StringsKt.endsWith$default(simpleName, "ElseBrick", false, 2, (Object) null);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.items.size();
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return this.items.get(position).hashCode();
    }
}
