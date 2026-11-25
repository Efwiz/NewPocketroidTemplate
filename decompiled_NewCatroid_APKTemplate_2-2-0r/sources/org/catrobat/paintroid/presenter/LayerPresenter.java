package org.catrobat.paintroid.presenter;

import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.CommandFactory;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.controller.DefaultToolController;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.implementation.ClippingTool;
import org.catrobat.paintroid.tools.implementation.LineTool;
import org.catrobat.paintroid.ui.DrawingSurface;
import org.catrobat.paintroid.ui.dragndrop.DragAndDropPresenter;
import org.catrobat.paintroid.ui.dragndrop.ListItemDragHandler;
import org.catrobat.paintroid.ui.viewholder.BottomNavigationViewHolder;

/* JADX INFO: compiled from: LayerPresenter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 M2\u00020\u00012\u00020\u0002:\u0001MB5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\b\u0010\"\u001a\u00020#H\u0016J\u0018\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u0019H\u0016J\b\u0010'\u001a\u00020(H\u0002J\b\u0010)\u001a\u00020#H\u0002J\b\u0010*\u001a\u00020#H\u0016J\u001a\u0010+\u001a\u0004\u0018\u00010\u001e2\u0006\u0010%\u001a\u00020\u00192\u0006\u0010,\u001a\u00020(H\u0002J\u0012\u0010-\u001a\u0004\u0018\u00010\u001e2\u0006\u0010%\u001a\u00020\u0019H\u0016J\u0010\u0010.\u001a\u00020/2\u0006\u0010%\u001a\u00020\u0019H\u0016J\b\u00100\u001a\u00020\u0006H\u0016J\n\u00101\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u00102\u001a\u00020#H\u0016J\u0010\u00103\u001a\u00020(2\u0006\u0010%\u001a\u00020\u0019H\u0002J\b\u00104\u001a\u00020(H\u0016J\u0018\u00105\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00192\u0006\u00106\u001a\u00020\u0019H\u0016J\u0018\u00107\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00192\u0006\u00106\u001a\u00020\u0019H\u0016J\b\u00108\u001a\u00020#H\u0016J\b\u00109\u001a\u00020#H\u0016J\u0018\u0010:\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00192\u0006\u0010;\u001a\u00020<H\u0016J\b\u0010=\u001a\u00020#H\u0016J\b\u0010>\u001a\u00020#H\u0016J\b\u0010?\u001a\u00020#H\u0016J\b\u0010@\u001a\u00020#H\u0016J\u0018\u0010A\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00192\u0006\u0010B\u001a\u00020\u0019H\u0016J\u000e\u0010C\u001a\u00020#2\u0006\u0010D\u001a\u00020\u0019J\u0010\u0010E\u001a\u00020#2\u0006\u0010F\u001a\u00020\u0011H\u0016J\u0010\u0010G\u001a\u00020#2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010H\u001a\u00020#2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010I\u001a\u00020#2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010J\u001a\u00020#2\u0006\u0010%\u001a\u00020\u0019H\u0016J\u0018\u0010K\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00192\u0006\u0010,\u001a\u00020(H\u0016J\u0018\u0010L\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u00192\u0006\u0010B\u001a\u00020\u0019H\u0016R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\u00020\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!¨\u0006N"}, d2 = {"Lorg/catrobat/paintroid/presenter/LayerPresenter;", "Lorg/catrobat/paintroid/contract/LayerContracts$Presenter;", "Lorg/catrobat/paintroid/ui/dragndrop/DragAndDropPresenter;", "model", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "listItemDragHandler", "Lorg/catrobat/paintroid/ui/dragndrop/ListItemDragHandler;", "layerMenuViewHolder", "Lorg/catrobat/paintroid/contract/LayerContracts$LayerMenuViewHolder;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "commandFactory", "Lorg/catrobat/paintroid/command/CommandFactory;", "navigator", "Lorg/catrobat/paintroid/contract/LayerContracts$Navigator;", "(Lorg/catrobat/paintroid/contract/LayerContracts$Model;Lorg/catrobat/paintroid/ui/dragndrop/ListItemDragHandler;Lorg/catrobat/paintroid/contract/LayerContracts$LayerMenuViewHolder;Lorg/catrobat/paintroid/command/CommandManager;Lorg/catrobat/paintroid/command/CommandFactory;Lorg/catrobat/paintroid/contract/LayerContracts$Navigator;)V", "adapter", "Lorg/catrobat/paintroid/contract/LayerContracts$Adapter;", "bottomNavigationViewHolder", "Lorg/catrobat/paintroid/ui/viewholder/BottomNavigationViewHolder;", "defaultToolController", "Lorg/catrobat/paintroid/controller/DefaultToolController;", "drawingSurface", "Lorg/catrobat/paintroid/ui/DrawingSurface;", "layerCount", "", "getLayerCount", "()I", "layers", "", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", "presenter", "getPresenter", "()Lorg/catrobat/paintroid/presenter/LayerPresenter;", "addLayer", "", "changeLayerOpacity", "position", "opacityPercentage", "checkIfClippingToolInUse", "", "checkIfLineToolInUse", "disableVisibilityAndOpacityButtons", "getDestinationLayer", "isVisible", "getLayerItem", "getLayerItemId", "", "getListItemDragHandler", "getSelectedLayer", "invalidate", "isPositionValid", "isShown", "markMergeable", "mergeWith", "mergeItems", "onSelectedLayerInvisible", "onSelectedLayerVisible", "onStartDragging", Promotion.ACTION_VIEW, "Landroid/view/View;", "onStopDragging", "refreshDrawingSurface", "refreshLayerMenuViewHolder", "removeLayer", "reorderItems", "swapWith", "resetMergeColor", "layerPosition", "setAdapter", "layerAdapter", "setBottomNavigationViewHolder", "setDefaultToolController", "setDrawingSurface", "setLayerSelected", "setLayerVisibility", "swapItemsVisually", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LayerPresenter implements LayerContracts.Presenter, DragAndDropPresenter {
    private static final String TAG = "LayerPresenter";
    private LayerContracts.Adapter adapter;
    private BottomNavigationViewHolder bottomNavigationViewHolder;
    private final CommandFactory commandFactory;
    private final CommandManager commandManager;
    private DefaultToolController defaultToolController;
    private DrawingSurface drawingSurface;
    private final LayerContracts.LayerMenuViewHolder layerMenuViewHolder;
    private final List<LayerContracts.Layer> layers;
    private final ListItemDragHandler listItemDragHandler;
    private final LayerContracts.Model model;
    private final LayerContracts.Navigator navigator;

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public long getLayerItemId(int position) {
        return position;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public LayerPresenter getPresenter() {
        return this;
    }

    public LayerPresenter(LayerContracts.Model model, ListItemDragHandler listItemDragHandler, LayerContracts.LayerMenuViewHolder layerMenuViewHolder, CommandManager commandManager, CommandFactory commandFactory, LayerContracts.Navigator navigator) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(listItemDragHandler, "listItemDragHandler");
        Intrinsics.checkNotNullParameter(layerMenuViewHolder, "layerMenuViewHolder");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Intrinsics.checkNotNullParameter(commandFactory, "commandFactory");
        Intrinsics.checkNotNullParameter(navigator, "navigator");
        this.model = model;
        this.listItemDragHandler = listItemDragHandler;
        this.layerMenuViewHolder = layerMenuViewHolder;
        this.commandManager = commandManager;
        this.commandFactory = commandFactory;
        this.navigator = navigator;
        this.layers = new ArrayList(model.getLayers());
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public int getLayerCount() {
        return this.layers.size();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public ListItemDragHandler getListItemDragHandler() {
        return this.listItemDragHandler;
    }

    private final boolean isPositionValid(int position) {
        return position >= 0 && position < this.layers.size();
    }

    private final void checkIfLineToolInUse() {
        DefaultToolController defaultToolController = this.defaultToolController;
        if (defaultToolController != null && defaultToolController.getToolType() == ToolType.LINE) {
            Tool currentTool = defaultToolController.getCurrentTool();
            if (currentTool == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.LineTool");
            }
            LineTool lineTool = (LineTool) currentTool;
            if (!lineTool.getLineFinalized() && lineTool.getStartpointSet() && !lineTool.getEndpointSet()) {
                if (this.commandManager.isUndoAvailable()) {
                    this.commandManager.undoIgnoringColorChanges();
                }
                lineTool.setStartPointToDraw(null);
                lineTool.setStartpointSet(false);
                return;
            }
            if (!lineTool.getLineFinalized() && lineTool.getStartpointSet() && lineTool.getEndpointSet()) {
                lineTool.setToolSwitched(true);
                lineTool.onClickOnButton();
            }
        }
    }

    private final boolean checkIfClippingToolInUse() {
        Tool currentTool;
        DefaultToolController defaultToolController = this.defaultToolController;
        if (((defaultToolController == null || (currentTool = defaultToolController.getCurrentTool()) == null) ? null : currentTool.getToolType()) != ToolType.CLIP) {
            return false;
        }
        DefaultToolController defaultToolController2 = this.defaultToolController;
        Tool currentTool2 = defaultToolController2 == null ? null : defaultToolController2.getCurrentTool();
        if (currentTool2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
        }
        ClippingTool clippingTool = (ClippingTool) currentTool2;
        clippingTool.setWasRecentlyApplied(true);
        if (clippingTool.getAreaClosed()) {
            clippingTool.handleDown(new PointF(0.0f, 0.0f));
            clippingTool.setInitialEventCoordinate(null);
            clippingTool.previousEventCoordinate = null;
            clippingTool.pathToDraw.rewind();
            clippingTool.getPointArray().clear();
        }
        return true;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void setAdapter(LayerContracts.Adapter layerAdapter) {
        Intrinsics.checkNotNullParameter(layerAdapter, "layerAdapter");
        this.adapter = layerAdapter;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void setDrawingSurface(DrawingSurface drawingSurface) {
        Intrinsics.checkNotNullParameter(drawingSurface, "drawingSurface");
        this.drawingSurface = drawingSurface;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void setDefaultToolController(DefaultToolController defaultToolController) {
        Intrinsics.checkNotNullParameter(defaultToolController, "defaultToolController");
        this.defaultToolController = defaultToolController;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void setBottomNavigationViewHolder(BottomNavigationViewHolder bottomNavigationViewHolder) {
        Intrinsics.checkNotNullParameter(bottomNavigationViewHolder, "bottomNavigationViewHolder");
        this.bottomNavigationViewHolder = bottomNavigationViewHolder;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void onSelectedLayerInvisible() {
        DefaultToolController defaultToolController = this.defaultToolController;
        if (defaultToolController != null) {
            defaultToolController.hideToolOptionsView();
        }
        DefaultToolController defaultToolController2 = this.defaultToolController;
        if (defaultToolController2 != null) {
            defaultToolController2.switchTool(ToolType.HAND);
        }
        BottomNavigationViewHolder bottomNavigationViewHolder = this.bottomNavigationViewHolder;
        if (bottomNavigationViewHolder == null) {
            return;
        }
        bottomNavigationViewHolder.showCurrentTool(ToolType.HAND);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void onSelectedLayerVisible() {
        DefaultToolController defaultToolController = this.defaultToolController;
        if (defaultToolController != null) {
            defaultToolController.switchTool(ToolType.BRUSH);
        }
        BottomNavigationViewHolder bottomNavigationViewHolder = this.bottomNavigationViewHolder;
        if (bottomNavigationViewHolder == null) {
            return;
        }
        bottomNavigationViewHolder.showCurrentTool(ToolType.BRUSH);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void refreshLayerMenuViewHolder() {
        Runtime runtime = Runtime.getRuntime();
        long jMaxMemory = (runtime.maxMemory() / 1048576) - ((runtime.totalMemory() - runtime.freeMemory()) / 1048576);
        if (getLayerCount() < 100 && jMaxMemory > 40) {
            this.layerMenuViewHolder.enableAddLayerButton();
        } else {
            this.layerMenuViewHolder.disableAddLayerButton();
        }
        if (getLayerCount() > 1) {
            this.layerMenuViewHolder.enableRemoveLayerButton();
        } else {
            this.layerMenuViewHolder.disableRemoveLayerButton();
        }
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void disableVisibilityAndOpacityButtons() {
        this.layerMenuViewHolder.disableLayerVisibilityButton();
        this.layerMenuViewHolder.disableLayerOpacityButton();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public boolean isShown() {
        return this.layerMenuViewHolder.isShown();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public LayerContracts.Layer getLayerItem(int position) {
        if (isPositionValid(position)) {
            return this.layers.get(position);
        }
        Log.w("LayerPresenter.kt", "LayerPresenter.getLayerItem(position) - tried to access position out of range of the layers array!");
        return null;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void addLayer() {
        if (getLayerCount() < 100) {
            checkIfLineToolInUse();
            boolean zCheckIfClippingToolInUse = checkIfClippingToolInUse();
            this.commandManager.addCommand(this.commandFactory.createAddEmptyLayerCommand());
            if (zCheckIfClippingToolInUse) {
                DefaultToolController defaultToolController = this.defaultToolController;
                Tool currentTool = defaultToolController == null ? null : defaultToolController.getCurrentTool();
                if (currentTool == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
                }
                ((ClippingTool) currentTool).copyBitmapOfCurrentLayer();
                return;
            }
            return;
        }
        this.navigator.showToast(R.string.layer_too_many_layers, 0);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void removeLayer() {
        if (getLayerCount() > 1) {
            checkIfLineToolInUse();
            boolean zCheckIfClippingToolInUse = checkIfClippingToolInUse();
            LayerContracts.Layer currentLayer = this.model.getCurrentLayer();
            if (currentLayer == null) {
                return;
            }
            this.commandManager.addCommand(this.commandFactory.createRemoveLayerCommand(this.model.getLayerIndexOf(currentLayer)));
            if (zCheckIfClippingToolInUse) {
                DefaultToolController defaultToolController = this.defaultToolController;
                Tool currentTool = defaultToolController == null ? null : defaultToolController.getCurrentTool();
                if (currentTool == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
                }
                ((ClippingTool) currentTool).copyBitmapOfCurrentLayer();
            }
        }
    }

    private final LayerContracts.Layer getDestinationLayer(int position, boolean isVisible) {
        LayerContracts.Layer layerAt = this.model.getLayerAt(position);
        if (layerAt == null) {
            return null;
        }
        layerAt.setVisible(isVisible);
        return layerAt;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public LayerContracts.Layer getSelectedLayer() {
        return this.model.getCurrentLayer();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void setLayerSelected(int position) {
        if (!isPositionValid(position)) {
            Log.e(TAG, "onClickLayerAtPosition at invalid position");
            return;
        }
        LayerContracts.Layer currentLayer = this.model.getCurrentLayer();
        if (currentLayer != null && position == this.model.getLayerIndexOf(currentLayer)) {
            return;
        }
        checkIfLineToolInUse();
        this.commandManager.addCommand(this.commandFactory.createSelectLayerCommand(position));
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void changeLayerOpacity(int position, int opacityPercentage) {
        if (!isPositionValid(position)) {
            Log.e(TAG, "invalid layer position to change opacity");
        } else {
            this.commandManager.addCommand(this.commandFactory.createLayerOpacityCommand(position, opacityPercentage));
        }
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void setLayerVisibility(int position, boolean isVisible) {
        refreshDrawingSurface();
        LayerContracts.Layer destinationLayer = getDestinationLayer(position, isVisible);
        if (destinationLayer == null) {
            return;
        }
        destinationLayer.setVisible(isVisible);
        if (this.model.getCurrentLayer() == destinationLayer) {
            if (isVisible) {
                onSelectedLayerVisible();
            } else {
                onSelectedLayerInvisible();
            }
        }
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void refreshDrawingSurface() {
        DrawingSurface drawingSurface = this.drawingSurface;
        if (drawingSurface == null) {
            return;
        }
        drawingSurface.refreshDrawingSurface();
    }

    @Override // org.catrobat.paintroid.ui.dragndrop.DragAndDropPresenter
    public int swapItemsVisually(int position, int swapWith) {
        Collections.swap(this.layers, position, swapWith);
        return swapWith;
    }

    @Override // org.catrobat.paintroid.ui.dragndrop.DragAndDropPresenter
    public void mergeItems(int position, int mergeWith) {
        checkIfLineToolInUse();
        LayerContracts.Layer layer = (LayerContracts.Layer) CollectionsKt.getOrNull(this.layers, mergeWith);
        if (layer == null) {
            return;
        }
        int layerIndexOf = this.model.getLayerIndexOf(layer);
        boolean zCheckIfClippingToolInUse = checkIfClippingToolInUse();
        if (position == layerIndexOf || layerIndexOf <= -1) {
            return;
        }
        this.commandManager.addCommand(this.commandFactory.createMergeLayersCommand(position, layerIndexOf));
        this.navigator.showToast(R.string.layer_merged, 0);
        if (zCheckIfClippingToolInUse) {
            DefaultToolController defaultToolController = this.defaultToolController;
            Tool currentTool = defaultToolController == null ? null : defaultToolController.getCurrentTool();
            if (currentTool == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
            }
            ((ClippingTool) currentTool).copyBitmapOfCurrentLayer();
        }
    }

    @Override // org.catrobat.paintroid.ui.dragndrop.DragAndDropPresenter
    public void reorderItems(int position, int swapWith) {
        if (position != swapWith) {
            checkIfLineToolInUse();
            this.commandManager.addCommand(this.commandFactory.createReorderLayersCommand(position, swapWith));
            checkIfClippingToolInUse();
        }
    }

    @Override // org.catrobat.paintroid.ui.dragndrop.DragAndDropPresenter
    public void markMergeable(int position, int mergeWith) {
        LayerContracts.LayerViewHolder viewHolderAt;
        if (!isPositionValid(position) || !isPositionValid(mergeWith)) {
            Log.e(TAG, "onLongClickLayerAtPosition at invalid position");
            return;
        }
        LayerContracts.Adapter adapter = this.adapter;
        if (adapter == null || (viewHolderAt = adapter.getViewHolderAt(mergeWith)) == null) {
            return;
        }
        viewHolderAt.setMergable();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void onStopDragging() {
        this.listItemDragHandler.stopDragging();
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void onStartDragging(int position, View view) {
        boolean z;
        Intrinsics.checkNotNullParameter(view, "view");
        if (!isPositionValid(position)) {
            Log.e(TAG, "onLongClickLayerAtPosition at invalid position");
            return;
        }
        int size = this.layers.size() - 1;
        if (size >= 0) {
            int i = 0;
            z = true;
            while (true) {
                int i2 = i + 1;
                if (!this.layers.get(i).getIsVisible()) {
                    z = false;
                }
                if (i2 > size) {
                    break;
                } else {
                    i = i2;
                }
            }
        } else {
            z = true;
        }
        if (z) {
            if (getLayerCount() > 1) {
                this.listItemDragHandler.startDragging(position, view);
                return;
            }
            return;
        }
        this.navigator.showToast(R.string.no_longclick_on_hidden_layer, 0);
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Presenter
    public void invalidate() {
        synchronized (this.model) {
            this.layers.clear();
            this.layers.addAll(this.model.getLayers());
        }
        refreshLayerMenuViewHolder();
        LayerContracts.Adapter adapter = this.adapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        this.listItemDragHandler.stopDragging();
    }

    public final void resetMergeColor(int layerPosition) {
        LayerContracts.LayerViewHolder viewHolderAt;
        LayerContracts.Adapter adapter = this.adapter;
        if (adapter == null || (viewHolderAt = adapter.getViewHolderAt(layerPosition)) == null) {
            return;
        }
        boolean isSelected = viewHolderAt.getIsSelected();
        LayerContracts.LayerViewHolder viewHolderAt2 = adapter.getViewHolderAt(layerPosition);
        if (viewHolderAt2 == null) {
            return;
        }
        viewHolderAt2.setSelected(isSelected);
    }
}
