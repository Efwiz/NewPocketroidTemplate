package org.catrobat.paintroid.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.iotasks.OpenRasterFileFormatConversion;
import org.catrobat.paintroid.tools.helper.DefaultNumberRangeFilter;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: LayerAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001d2\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u00012\u00020\u0003:\u0003\u001c\u001d\u001eB\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u0010\u001a\u00020\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u0013H\u0016J\u001c\u0010\u0016\u001a\u00020\u00112\n\u0010\u0017\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0013H\u0016J\u001c\u0010\u0018\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0013H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/paintroid/ui/LayerAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lorg/catrobat/paintroid/ui/LayerAdapter$LayerViewHolder;", "Lorg/catrobat/paintroid/contract/LayerContracts$Adapter;", "presenter", "Lorg/catrobat/paintroid/contract/LayerContracts$Presenter;", "mainActivity", "Lorg/catrobat/paintroid/MainActivity;", "(Lorg/catrobat/paintroid/contract/LayerContracts$Presenter;Lorg/catrobat/paintroid/MainActivity;)V", "getMainActivity", "()Lorg/catrobat/paintroid/MainActivity;", "getPresenter", "()Lorg/catrobat/paintroid/contract/LayerContracts$Presenter;", "viewHolders", "Landroid/util/SparseArray;", "Lorg/catrobat/paintroid/contract/LayerContracts$LayerViewHolder;", "clearViewHolders", "", "getItemCount", "", "getViewHolderAt", "position", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "BackgroundType", "Companion", "LayerViewHolder", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LayerAdapter extends RecyclerView.Adapter<LayerViewHolder> implements LayerContracts.Adapter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "LayerAdapter";
    private final MainActivity mainActivity;
    private final LayerContracts.Presenter presenter;
    private final SparseArray<LayerContracts.LayerViewHolder> viewHolders;

    /* JADX INFO: compiled from: LayerAdapter.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/ui/LayerAdapter$BackgroundType;", "", "(Ljava/lang/String;I)V", "TOP", "BOTTOM", "CENTER", "SINGLE", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum BackgroundType {
        TOP,
        BOTTOM,
        CENTER,
        SINGLE
    }

    public final LayerContracts.Presenter getPresenter() {
        return this.presenter;
    }

    public final MainActivity getMainActivity() {
        return this.mainActivity;
    }

    public LayerAdapter(LayerContracts.Presenter presenter, MainActivity mainActivity) {
        Intrinsics.checkNotNullParameter(presenter, "presenter");
        Intrinsics.checkNotNullParameter(mainActivity, "mainActivity");
        this.presenter = presenter;
        this.mainActivity = mainActivity;
        this.viewHolders = new SparseArray<>();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public LayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pocketpaint_item_layer, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return new LayerViewHolder(this, itemView, this.presenter);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(LayerViewHolder holder, int position) throws InterruptedException {
        Intrinsics.checkNotNullParameter(holder, "holder");
        this.viewHolders.put(position, holder);
        holder.bindView();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.presenter.getLayerCount();
    }

    public final void clearViewHolders() {
        this.viewHolders.clear();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Adapter
    public LayerContracts.LayerViewHolder getViewHolderAt(int position) {
        return this.viewHolders.get(position);
    }

    /* JADX INFO: compiled from: LayerAdapter.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u0013H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\"\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u0011H\u0016J\b\u0010%\u001a\u00020\u001eH\u0016J\u0010\u0010&\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020)H\u0016R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006*"}, d2 = {"Lorg/catrobat/paintroid/ui/LayerAdapter$LayerViewHolder;", "Lorg/catrobat/paintroid/contract/LayerContracts$LayerViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "layerPresenter", "Lorg/catrobat/paintroid/contract/LayerContracts$Presenter;", "(Lorg/catrobat/paintroid/ui/LayerAdapter;Landroid/view/View;Lorg/catrobat/paintroid/contract/LayerContracts$Presenter;)V", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "currentBitmap", "dragHandle", "Landroid/widget/ImageView;", "imageView", "isSelected", "", "layerBackground", "Landroid/widget/LinearLayout;", "layerVisibilityCheckbox", "Landroid/widget/CheckBox;", "opacityEditText", "Landroidx/appcompat/widget/AppCompatEditText;", "opacitySeekBar", "Landroid/widget/SeekBar;", Promotion.ACTION_VIEW, "getView", "()Landroid/view/View;", "bindView", "", "getBackgroundType", "Lorg/catrobat/paintroid/ui/LayerAdapter$BackgroundType;", "getViewLayout", "resizeBitmap", "setLayerVisibilityCheckbox", "setTo", "setMergable", "setSelected", "updateImageView", "layer", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public final class LayerViewHolder extends RecyclerView.ViewHolder implements LayerContracts.LayerViewHolder {
        private Bitmap currentBitmap;
        private final ImageView dragHandle;
        private final ImageView imageView;
        private boolean isSelected;
        private final LinearLayout layerBackground;
        private final LayerContracts.Presenter layerPresenter;
        private final CheckBox layerVisibilityCheckbox;
        private final AppCompatEditText opacityEditText;
        private final SeekBar opacitySeekBar;
        final /* synthetic */ LayerAdapter this$0;

        /* JADX INFO: compiled from: LayerAdapter.kt */
        @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[BackgroundType.values().length];
                iArr[BackgroundType.SINGLE.ordinal()] = 1;
                iArr[BackgroundType.TOP.ordinal()] = 2;
                iArr[BackgroundType.BOTTOM.ordinal()] = 3;
                iArr[BackgroundType.CENTER.ordinal()] = 4;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LayerViewHolder(LayerAdapter this$0, View itemView, LayerContracts.Presenter layerPresenter) {
            super(itemView);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            Intrinsics.checkNotNullParameter(layerPresenter, "layerPresenter");
            this.this$0 = this$0;
            this.layerPresenter = layerPresenter;
            View viewFindViewById = itemView.findViewById(R.id.pocketpaint_item_layer);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "itemView.findViewById(R.id.pocketpaint_item_layer)");
            this.layerBackground = (LinearLayout) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.pocketpaint_item_layer_image);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "itemView.findViewById(R.…etpaint_item_layer_image)");
            this.imageView = (ImageView) viewFindViewById2;
            View viewFindViewById3 = itemView.findViewById(R.id.pocketpaint_layer_drag_handle);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "itemView.findViewById(R.…tpaint_layer_drag_handle)");
            this.dragHandle = (ImageView) viewFindViewById3;
            View viewFindViewById4 = itemView.findViewById(R.id.pocketpaint_layer_opacity_seekbar);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "itemView.findViewById(R.…nt_layer_opacity_seekbar)");
            this.opacitySeekBar = (SeekBar) viewFindViewById4;
            View viewFindViewById5 = itemView.findViewById(R.id.pocketpaint_layer_opacity_value);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "itemView.findViewById(R.…aint_layer_opacity_value)");
            this.opacityEditText = (AppCompatEditText) viewFindViewById5;
            View viewFindViewById6 = itemView.findViewById(R.id.pocketpaint_checkbox_layer);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "itemView.findViewById(R.…cketpaint_checkbox_layer)");
            this.layerVisibilityCheckbox = (CheckBox) viewFindViewById6;
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        /* JADX INFO: renamed from: getBitmap, reason: from getter */
        public Bitmap getCurrentBitmap() {
            return this.currentBitmap;
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        public View getView() {
            View itemView = this.itemView;
            Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
            return itemView;
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        public void bindView() throws InterruptedException {
            LayerContracts.Layer layerItem = this.layerPresenter.getLayerItem(getPosition());
            if (layerItem == null) {
                return;
            }
            setSelected(layerItem == this.layerPresenter.getSelectedLayer());
            setLayerVisibilityCheckbox(layerItem.getIsVisible());
            updateImageView(layerItem);
            this.layerVisibilityCheckbox.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LayerAdapter.LayerViewHolder.m5974bindView$lambda0(this.f$0, view);
                }
            });
            this.layerBackground.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LayerAdapter.LayerViewHolder.m5975bindView$lambda1(this.f$0, view);
                }
            });
            this.dragHandle.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$$ExternalSyntheticLambda2
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return LayerAdapter.LayerViewHolder.m5976bindView$lambda2(this.f$0, view, motionEvent);
                }
            });
            this.opacitySeekBar.setProgress(layerItem.getOpacityPercentage());
            this.opacityEditText.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(0, 100)});
            this.opacityEditText.setText(String.valueOf(layerItem.getOpacityPercentage()));
            this.opacityEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$bindView$4
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                    int i;
                    try {
                        i = Integer.parseInt(String.valueOf(s));
                    } catch (NumberFormatException e) {
                        String localizedMessage = e.getLocalizedMessage();
                        if (localizedMessage != null) {
                            Log.d(LayerAdapter.TAG, localizedMessage);
                        }
                        i = 100;
                    }
                    if (i != this.this$0.opacitySeekBar.getProgress()) {
                        this.this$0.opacitySeekBar.setProgress(i);
                        this.this$0.layerPresenter.changeLayerOpacity(this.this$0.getPosition(), i);
                    }
                    this.this$0.layerPresenter.refreshDrawingSurface();
                }
            });
            this.opacitySeekBar.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$$ExternalSyntheticLambda3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return LayerAdapter.LayerViewHolder.m5977bindView$lambda3(view, motionEvent);
                }
            });
            this.opacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$bindView$6
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                    if (fromUser) {
                        this.this$0.opacityEditText.setText(String.valueOf(progress));
                    }
                    this.this$0.imageView.setAlpha(progress / 100);
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                    this.this$0.layerPresenter.changeLayerOpacity(this.this$0.getPosition(), seekBar.getProgress());
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bindView$lambda-0, reason: not valid java name */
        public static final void m5974bindView$lambda0(LayerViewHolder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.layerPresenter.setLayerVisibility(this$0.getPosition(), this$0.layerVisibilityCheckbox.isChecked());
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bindView$lambda-1, reason: not valid java name */
        public static final void m5975bindView$lambda1(LayerViewHolder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.layerPresenter.setLayerSelected(this$0.getPosition());
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bindView$lambda-2, reason: not valid java name */
        public static final boolean m5976bindView$lambda2(LayerViewHolder this$0, View view, MotionEvent motionEvent) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            int action = motionEvent.getAction();
            if (action == 0) {
                LayerContracts.Presenter presenter = this$0.layerPresenter;
                int position = this$0.getPosition();
                View itemView = this$0.itemView;
                Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
                presenter.onStartDragging(position, itemView);
            } else if (action == 1) {
                this$0.layerPresenter.onStopDragging();
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bindView$lambda-3, reason: not valid java name */
        public static final boolean m5977bindView$lambda3(View view, MotionEvent motionEvent) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        public void setSelected(boolean isSelected) {
            Drawable singleBackground;
            int i = WhenMappings.$EnumSwitchMapping$0[getBackgroundType().ordinal()];
            if (i == 1) {
                singleBackground = LayerAdapter.INSTANCE.getSingleBackground();
            } else if (i == 2) {
                singleBackground = LayerAdapter.INSTANCE.getTopBackground(isSelected);
            } else if (i == 3) {
                singleBackground = LayerAdapter.INSTANCE.getBottomBackground(isSelected);
            } else {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                singleBackground = LayerAdapter.INSTANCE.getCenterBackground(isSelected);
            }
            this.layerBackground.setBackground(singleBackground);
            this.isSelected = isSelected;
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        /* JADX INFO: renamed from: isSelected, reason: from getter */
        public boolean getIsSelected() {
            return this.isSelected;
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        /* JADX INFO: renamed from: getViewLayout, reason: from getter */
        public LinearLayout getLayerBackground() {
            return this.layerBackground;
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        public void updateImageView(LayerContracts.Layer layer) throws InterruptedException {
            Intrinsics.checkNotNullParameter(layer, "layer");
            BuildersKt__BuildersKt.runBlocking$default(null, new LayerAdapter$LayerViewHolder$updateImageView$1(this, layer, null), 1, null);
            this.currentBitmap = getCurrentBitmap();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Bitmap resizeBitmap(Bitmap bitmap) {
            float height;
            float width = 400.0f;
            if (bitmap.getWidth() > bitmap.getHeight()) {
                height = ((bitmap.getHeight() / bitmap.getWidth()) * 400.0f) + 1;
            } else {
                width = ((bitmap.getWidth() / bitmap.getHeight()) * 400.0f) + 1;
                height = 400.0f;
            }
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, false);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(bitma…newHeight.toInt(), false)");
            return bitmapCreateScaledBitmap;
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        public void setLayerVisibilityCheckbox(boolean setTo) {
            this.layerVisibilityCheckbox.setChecked(setTo);
        }

        @Override // org.catrobat.paintroid.contract.LayerContracts.LayerViewHolder
        public void setMergable() {
            this.layerBackground.setBackgroundResource(R.color.pocketpaint_color_merge_layer);
        }

        private final BackgroundType getBackgroundType() {
            if (this.this$0.getPresenter().getLayerCount() > 2 && getAdapterPosition() > 0 && getAdapterPosition() < this.this$0.getPresenter().getLayerCount() - 1) {
                return BackgroundType.CENTER;
            }
            if (this.this$0.getPresenter().getLayerCount() == 1) {
                return BackgroundType.SINGLE;
            }
            if (getAdapterPosition() == this.this$0.getPresenter().getLayerCount() - 1) {
                return BackgroundType.BOTTOM;
            }
            return BackgroundType.TOP;
        }
    }

    /* JADX INFO: compiled from: LayerAdapter.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0006\u0010\u000f\u001a\u00020\u0007J\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/ui/LayerAdapter$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getBottomBackground", "Landroid/graphics/drawable/Drawable;", "isSelected", "", "getCenterBackground", "getRadius", "", "backgroundType", "Lorg/catrobat/paintroid/ui/LayerAdapter$BackgroundType;", "getSingleBackground", "getTopBackground", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: LayerAdapter.kt */
        @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[BackgroundType.values().length];
                iArr[BackgroundType.TOP.ordinal()] = 1;
                iArr[BackgroundType.BOTTOM.ordinal()] = 2;
                iArr[BackgroundType.SINGLE.ordinal()] = 3;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Drawable getSingleBackground() {
            MainActivity mainActivity = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
            Drawable drawable = mainActivity == null ? null : ContextCompat.getDrawable(mainActivity, R.drawable.layer_item_single_selected);
            if (drawable == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
            }
            ((GradientDrawable) drawable).setCornerRadii(getRadius(BackgroundType.SINGLE));
            return drawable;
        }

        public final Drawable getTopBackground(boolean isSelected) {
            Drawable drawable = null;
            if (isSelected) {
                MainActivity mainActivity = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
                if (mainActivity != null) {
                    drawable = ContextCompat.getDrawable(mainActivity, R.drawable.layer_item_top_selected);
                }
            } else {
                MainActivity mainActivity2 = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
                if (mainActivity2 != null) {
                    drawable = ContextCompat.getDrawable(mainActivity2, R.drawable.layer_item_top_unselected);
                }
            }
            if (drawable == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
            }
            ((GradientDrawable) drawable).setCornerRadii(getRadius(BackgroundType.TOP));
            return drawable;
        }

        public final Drawable getBottomBackground(boolean isSelected) {
            Drawable drawable = null;
            if (isSelected) {
                MainActivity mainActivity = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
                if (mainActivity != null) {
                    drawable = ContextCompat.getDrawable(mainActivity, R.drawable.layer_item_bottom_selected);
                }
            } else {
                MainActivity mainActivity2 = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
                if (mainActivity2 != null) {
                    drawable = ContextCompat.getDrawable(mainActivity2, R.drawable.layer_item_bottom_unselected);
                }
            }
            if (drawable == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
            }
            ((GradientDrawable) drawable).setCornerRadii(getRadius(BackgroundType.BOTTOM));
            return drawable;
        }

        public final Drawable getCenterBackground(boolean isSelected) {
            if (isSelected) {
                MainActivity mainActivity = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
                if (mainActivity == null) {
                    return null;
                }
                return ContextCompat.getDrawable(mainActivity, R.drawable.layer_item_center_selected);
            }
            MainActivity mainActivity2 = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
            if (mainActivity2 == null) {
                return null;
            }
            return ContextCompat.getDrawable(mainActivity2, R.drawable.layer_item_center_unselected);
        }

        private final float[] getRadius(BackgroundType backgroundType) {
            Resources resources;
            Configuration configuration;
            MainActivity mainActivity = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
            boolean z = (mainActivity == null || (resources = mainActivity.getResources()) == null || (configuration = resources.getConfiguration()) == null || configuration.getLayoutDirection() != 1) ? false : true;
            MainActivity mainActivity2 = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
            float f = mainActivity2 == null ? 0.0f : mainActivity2.getResources().getDisplayMetrics().density * 20.0f;
            int i = WhenMappings.$EnumSwitchMapping$0[backgroundType.ordinal()];
            if (i == 1) {
                if (z) {
                    return new float[]{0.0f, 0.0f, f, f, 0.0f, 0.0f, 0.0f, 0.0f};
                }
                return new float[]{f, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
            }
            if (i == 2) {
                if (z) {
                    return new float[]{0.0f, 0.0f, 0.0f, 0.0f, f, f, 0.0f, 0.0f};
                }
                return new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f, f};
            }
            if (i != 3) {
                return new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
            }
            if (z) {
                return new float[]{0.0f, 0.0f, f, f, f, f, 0.0f, 0.0f};
            }
            return new float[]{f, f, 0.0f, 0.0f, 0.0f, 0.0f, f, f};
        }
    }
}
