package org.catrobat.paintroid.contract;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.LinearLayout;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import org.catrobat.paintroid.controller.DefaultToolController;
import org.catrobat.paintroid.ui.DrawingSurface;
import org.catrobat.paintroid.ui.dragndrop.ListItemDragHandler;
import org.catrobat.paintroid.ui.viewholder.BottomNavigationViewHolder;

/* JADX INFO: compiled from: LayerContracts.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\bf\u0018\u00002\u00020\u0001:\u0007\u0002\u0003\u0004\u0005\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/contract/LayerContracts;", "", "Adapter", "Layer", "LayerMenuViewHolder", "LayerViewHolder", ExifInterface.TAG_MODEL, "Navigator", "Presenter", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface LayerContracts {

    /* JADX INFO: compiled from: LayerContracts.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lorg/catrobat/paintroid/contract/LayerContracts$Adapter;", "", "getViewHolderAt", "Lorg/catrobat/paintroid/contract/LayerContracts$LayerViewHolder;", "position", "", "notifyDataSetChanged", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Adapter {
        LayerViewHolder getViewHolderAt(int position);

        void notifyDataSetChanged();
    }

    /* JADX INFO: compiled from: LayerContracts.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\b\u0010\u0013\u001a\u00020\u000eH&R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\n\"\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u00020\u000eX¦\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "isVisible", "", "()Z", "setVisible", "(Z)V", "opacityPercentage", "", "getOpacityPercentage", "()I", "setOpacityPercentage", "(I)V", "getValueForOpacityPercentage", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Layer {
        Bitmap getBitmap();

        int getOpacityPercentage();

        int getValueForOpacityPercentage();

        boolean isVisible();

        void setBitmap(Bitmap bitmap);

        void setOpacityPercentage(int i);

        void setVisible(boolean z);
    }

    /* JADX INFO: compiled from: LayerContracts.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lorg/catrobat/paintroid/contract/LayerContracts$LayerMenuViewHolder;", "", "disableAddLayerButton", "", "disableLayerOpacityButton", "disableLayerVisibilityButton", "disableRemoveLayerButton", "enableAddLayerButton", "enableRemoveLayerButton", "isShown", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface LayerMenuViewHolder {
        void disableAddLayerButton();

        void disableLayerOpacityButton();

        void disableLayerVisibilityButton();

        void disableRemoveLayerButton();

        void enableAddLayerButton();

        void enableRemoveLayerButton();

        boolean isShown();
    }

    /* JADX INFO: compiled from: LayerContracts.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000fH&J\b\u0010\u0012\u001a\u00020\u000bH&J\u0010\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/contract/LayerContracts$LayerViewHolder;", "", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", Promotion.ACTION_VIEW, "Landroid/view/View;", "getView", "()Landroid/view/View;", "bindView", "", "getViewLayout", "Landroid/widget/LinearLayout;", "isSelected", "", "setLayerVisibilityCheckbox", "setTo", "setMergable", "setSelected", "updateImageView", "layer", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface LayerViewHolder {
        void bindView();

        Bitmap getBitmap();

        View getView();

        LinearLayout getViewLayout();

        boolean isSelected();

        void setLayerVisibilityCheckbox(boolean setTo);

        void setMergable();

        void setSelected(boolean isSelected);

        void updateImageView(Layer layer);
    }

    /* JADX INFO: compiled from: LayerContracts.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010*\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0003H&J\u0010\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u0011H&J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001cH&J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0019\u001a\u00020\tH&J\u0010\u0010\u001f\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0003H&J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00030!2\u0006\u0010\u0019\u001a\u00020\tH&J\u0010\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\tH&J\b\u0010$\u001a\u00020%H&J\u0018\u0010&\u001a\u00020%2\u0006\u0010#\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0003H&R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u000b\"\u0004\b\u0016\u0010\r¨\u0006'"}, d2 = {"Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "", "currentLayer", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "getCurrentLayer", "()Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "setCurrentLayer", "(Lorg/catrobat/paintroid/contract/LayerContracts$Layer;)V", "height", "", "getHeight", "()I", "setHeight", "(I)V", "layerCount", "getLayerCount", "layers", "", "getLayers", "()Ljava/util/List;", "width", "getWidth", "setWidth", "addLayerAt", "", "index", "layer", "getBitmapListOfAllLayers", "Landroid/graphics/Bitmap;", "getBitmapOfAllLayers", "getLayerAt", "getLayerIndexOf", "listIterator", "", "removeLayerAt", "position", "reset", "", "setLayerAt", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Model {
        boolean addLayerAt(int index, Layer layer);

        List<Bitmap> getBitmapListOfAllLayers();

        Bitmap getBitmapOfAllLayers();

        Layer getCurrentLayer();

        int getHeight();

        Layer getLayerAt(int index);

        int getLayerCount();

        int getLayerIndexOf(Layer layer);

        List<Layer> getLayers();

        int getWidth();

        ListIterator<Layer> listIterator(int index);

        boolean removeLayerAt(int position);

        void reset();

        void setCurrentLayer(Layer layer);

        void setHeight(int i);

        void setLayerAt(int position, Layer layer);

        void setWidth(int i);
    }

    /* JADX INFO: compiled from: LayerContracts.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/contract/LayerContracts$Navigator;", "", "showToast", "", "id", "", "length", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Navigator {
        void showToast(int id2, int length);
    }

    /* JADX INFO: compiled from: LayerContracts.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\t\u001a\u00020\nH&J\u0018\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H&J\b\u0010\u000e\u001a\u00020\nH&J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\f\u001a\u00020\u0003H&J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u0003H&J\b\u0010\u0013\u001a\u00020\u0014H&J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0010H&J\b\u0010\u0016\u001a\u00020\nH&J\b\u0010\u0017\u001a\u00020\u0018H&J\b\u0010\u0019\u001a\u00020\nH&J\b\u0010\u001a\u001a\u00020\nH&J\u0018\u0010\u001b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u001dH&J\b\u0010\u001e\u001a\u00020\nH&J\b\u0010\u001f\u001a\u00020\nH&J\b\u0010 \u001a\u00020\nH&J\b\u0010!\u001a\u00020\nH&J\u0010\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020$H&J\u0010\u0010%\u001a\u00020\n2\u0006\u0010&\u001a\u00020'H&J\u0010\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020*H&J\u0010\u0010+\u001a\u00020\n2\u0006\u0010,\u001a\u00020-H&J\u0010\u0010.\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0003H&J\u0018\u0010/\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00032\u0006\u00100\u001a\u00020\u0018H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u00061"}, d2 = {"Lorg/catrobat/paintroid/contract/LayerContracts$Presenter;", "", "layerCount", "", "getLayerCount", "()I", "presenter", "getPresenter", "()Lorg/catrobat/paintroid/contract/LayerContracts$Presenter;", "addLayer", "", "changeLayerOpacity", "position", "opacityPercentage", "disableVisibilityAndOpacityButtons", "getLayerItem", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "getLayerItemId", "", "getListItemDragHandler", "Lorg/catrobat/paintroid/ui/dragndrop/ListItemDragHandler;", "getSelectedLayer", "invalidate", "isShown", "", "onSelectedLayerInvisible", "onSelectedLayerVisible", "onStartDragging", Promotion.ACTION_VIEW, "Landroid/view/View;", "onStopDragging", "refreshDrawingSurface", "refreshLayerMenuViewHolder", "removeLayer", "setAdapter", "layerAdapter", "Lorg/catrobat/paintroid/contract/LayerContracts$Adapter;", "setBottomNavigationViewHolder", "bottomNavigationViewHolder", "Lorg/catrobat/paintroid/ui/viewholder/BottomNavigationViewHolder;", "setDefaultToolController", "defaultToolController", "Lorg/catrobat/paintroid/controller/DefaultToolController;", "setDrawingSurface", "drawingSurface", "Lorg/catrobat/paintroid/ui/DrawingSurface;", "setLayerSelected", "setLayerVisibility", "isVisible", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Presenter {
        void addLayer();

        void changeLayerOpacity(int position, int opacityPercentage);

        void disableVisibilityAndOpacityButtons();

        int getLayerCount();

        Layer getLayerItem(int position);

        long getLayerItemId(int position);

        ListItemDragHandler getListItemDragHandler();

        Presenter getPresenter();

        Layer getSelectedLayer();

        void invalidate();

        boolean isShown();

        void onSelectedLayerInvisible();

        void onSelectedLayerVisible();

        void onStartDragging(int position, View view);

        void onStopDragging();

        void refreshDrawingSurface();

        void refreshLayerMenuViewHolder();

        void removeLayer();

        void setAdapter(Adapter layerAdapter);

        void setBottomNavigationViewHolder(BottomNavigationViewHolder bottomNavigationViewHolder);

        void setDefaultToolController(DefaultToolController defaultToolController);

        void setDrawingSurface(DrawingSurface drawingSurface);

        void setLayerSelected(int position);

        void setLayerVisibility(int position, boolean isVisible);
    }
}
