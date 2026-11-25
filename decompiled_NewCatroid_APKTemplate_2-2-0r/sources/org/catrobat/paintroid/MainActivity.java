package org.catrobat.paintroid;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.badlogic.gdx.graphics.GL20;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.catroid.common.Constants;
import org.catrobat.paintroid.colorpicker.ColorHistory;
import org.catrobat.paintroid.command.CommandFactory;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.command.implementation.AsyncCommandManager;
import org.catrobat.paintroid.command.implementation.DefaultCommandFactory;
import org.catrobat.paintroid.command.implementation.DefaultCommandManager;
import org.catrobat.paintroid.command.implementation.LayerOpacityCommand;
import org.catrobat.paintroid.command.serialization.CatrobatFileContent;
import org.catrobat.paintroid.command.serialization.CommandSerializer;
import org.catrobat.paintroid.common.CommonFactory;
import org.catrobat.paintroid.common.ConstantsKt;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.controller.DefaultToolController;
import org.catrobat.paintroid.iotasks.OpenRasterFileFormatConversion;
import org.catrobat.paintroid.iotasks.WorkspaceReturnValue;
import org.catrobat.paintroid.listener.DrawerLayoutListener;
import org.catrobat.paintroid.listener.PresenterColorPickedListener;
import org.catrobat.paintroid.model.LayerModel;
import org.catrobat.paintroid.model.MainActivityModel;
import org.catrobat.paintroid.presenter.LayerPresenter;
import org.catrobat.paintroid.presenter.MainActivityPresenter;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolReference;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.implementation.BaseToolWithShape;
import org.catrobat.paintroid.tools.implementation.ClippingTool;
import org.catrobat.paintroid.tools.implementation.DefaultContextCallback;
import org.catrobat.paintroid.tools.implementation.DefaultToolFactory;
import org.catrobat.paintroid.tools.implementation.DefaultToolPaint;
import org.catrobat.paintroid.tools.implementation.DefaultToolReference;
import org.catrobat.paintroid.tools.implementation.DefaultWorkspace;
import org.catrobat.paintroid.tools.implementation.LineTool;
import org.catrobat.paintroid.tools.implementation.TransformTool;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.ui.DrawingSurface;
import org.catrobat.paintroid.ui.KeyboardListener;
import org.catrobat.paintroid.ui.LayerAdapter;
import org.catrobat.paintroid.ui.LayerNavigator;
import org.catrobat.paintroid.ui.MainActivityInteractor;
import org.catrobat.paintroid.ui.MainActivityNavigator;
import org.catrobat.paintroid.ui.Perspective;
import org.catrobat.paintroid.ui.dragndrop.DragAndDropListView;
import org.catrobat.paintroid.ui.tools.DefaultToolOptionsViewController;
import org.catrobat.paintroid.ui.viewholder.BottomBarViewHolder;
import org.catrobat.paintroid.ui.viewholder.BottomNavigationViewHolder;
import org.catrobat.paintroid.ui.viewholder.DrawerLayoutViewHolder;
import org.catrobat.paintroid.ui.viewholder.LayerMenuViewHolder;
import org.catrobat.paintroid.ui.viewholder.TopBarViewHolder;
import org.catrobat.paintroid.ui.zoomwindow.DefaultZoomWindowController;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Æ\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 Î\u00012\u00020\u00012\u00020\u00022\u00020\u0003:\u0002Î\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u0089\u0001\u001a\u00020KH\u0002J\n\u0010\u008a\u0001\u001a\u00030\u0088\u0001H\u0016J\n\u0010\u008b\u0001\u001a\u00030\u0088\u0001H\u0016J\n\u0010\u008c\u0001\u001a\u00030\u0088\u0001H\u0016J\n\u0010\u008d\u0001\u001a\u00030\u0088\u0001H\u0002J\u0014\u0010\u008e\u0001\u001a\u00030\u008f\u00012\b\u0010\u0090\u0001\u001a\u00030\u0091\u0001H\u0016J\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001J\u0013\u0010\u0094\u0001\u001a\u00020(2\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0002J\n\u0010\u0097\u0001\u001a\u00030\u0088\u0001H\u0016J\n\u0010\u0098\u0001\u001a\u00030\u0088\u0001H\u0016J\u0013\u0010\u0099\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u009a\u0001\u001a\u00020(H\u0016J(\u0010\u009b\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u009c\u0001\u001a\u00020K2\u0007\u0010\u009d\u0001\u001a\u00020K2\n\u0010\u009e\u0001\u001a\u0005\u0018\u00010\u0096\u0001H\u0016J\n\u0010\u009f\u0001\u001a\u00030\u0088\u0001H\u0016J\u0016\u0010 \u0001\u001a\u00030\u0088\u00012\n\u0010¡\u0001\u001a\u0005\u0018\u00010¢\u0001H\u0016J\n\u0010£\u0001\u001a\u00030\u0088\u0001H\u0002J\n\u0010¤\u0001\u001a\u00030\u0088\u0001H\u0002J\n\u0010¥\u0001\u001a\u00030\u0088\u0001H\u0002J\n\u0010¦\u0001\u001a\u00030\u0088\u0001H\u0002J\u0013\u0010§\u0001\u001a\u00020(2\b\u0010¨\u0001\u001a\u00030©\u0001H\u0016J\n\u0010ª\u0001\u001a\u00030\u0088\u0001H\u0014J\u0013\u0010«\u0001\u001a\u00020(2\b\u0010¬\u0001\u001a\u00030\u00ad\u0001H\u0016J4\u0010®\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u009c\u0001\u001a\u00020K2\u000f\u0010¯\u0001\u001a\n\u0012\u0005\u0012\u00030\u0093\u00010°\u00012\b\u0010±\u0001\u001a\u00030²\u0001H\u0016¢\u0006\u0003\u0010³\u0001J\n\u0010´\u0001\u001a\u00030\u0088\u0001H\u0014J\u0014\u0010µ\u0001\u001a\u00030\u0088\u00012\b\u0010¶\u0001\u001a\u00030¢\u0001H\u0014J\n\u0010·\u0001\u001a\u00030\u0088\u0001H\u0016J\u0013\u0010¸\u0001\u001a\u00030\u0088\u00012\u0007\u0010¹\u0001\u001a\u00020(H\u0016J\n\u0010º\u0001\u001a\u00030\u0088\u0001H\u0016J\u001e\u0010»\u0001\u001a\u00030\u0088\u00012\b\u0010¼\u0001\u001a\u00030½\u00012\b\u0010¾\u0001\u001a\u00030¿\u0001H\u0002J\u0014\u0010À\u0001\u001a\u00030\u0088\u00012\b\u0010Á\u0001\u001a\u00030Â\u0001H\u0002J\u0013\u0010Ã\u0001\u001a\u00030\u0088\u00012\u0007\u0010Á\u0001\u001a\u00020\bH\u0002J\u0014\u0010Ä\u0001\u001a\u00030\u0088\u00012\b\u0010Å\u0001\u001a\u00030Æ\u0001H\u0002J\n\u0010Ç\u0001\u001a\u00030\u0088\u0001H\u0002J\u0014\u0010È\u0001\u001a\u00030\u0088\u00012\b\u0010¼\u0001\u001a\u00030½\u0001H\u0002J\n\u0010É\u0001\u001a\u00030\u0088\u0001H\u0016J\n\u0010Ê\u0001\u001a\u00030\u0088\u0001H\u0002J(\u0010Ë\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u009c\u0001\u001a\u00020K2\u0007\u0010\u009d\u0001\u001a\u00020K2\n\u0010\u009e\u0001\u001a\u0005\u0018\u00010\u0096\u0001H\u0016J4\u0010Ì\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u009c\u0001\u001a\u00020K2\u000f\u0010¯\u0001\u001a\n\u0012\u0005\u0012\u00030\u0093\u00010°\u00012\b\u0010±\u0001\u001a\u00030²\u0001H\u0016¢\u0006\u0003\u0010³\u0001J\u0013\u0010Í\u0001\u001a\u00020(2\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020$X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010'\u001a\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0014\u00101\u001a\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010*R\u001b\u00102\u001a\u00020(8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b3\u00104\u001a\u0004\b2\u0010*R\u000e\u00105\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000209X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010:\u001a\u00020;8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b<\u0010\u0004\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R$\u0010A\u001a\u00020B8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\bC\u0010\u0004\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u000e\u0010H\u001a\u00020IX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020KX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010L\u001a\u00020MX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u0014\u0010R\u001a\u00020S8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bT\u0010UR$\u0010V\u001a\u00020W8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\bX\u0010\u0004\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u0014\u0010]\u001a\u00020^8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b_\u0010`R\u000e\u0010a\u001a\u00020^X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020cX\u0082.¢\u0006\u0002\n\u0000R$\u0010d\u001a\u00020e8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\bf\u0010\u0004\u001a\u0004\bg\u0010h\"\u0004\bi\u0010jR\u001a\u0010k\u001a\u00020lX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010n\"\u0004\bo\u0010pR$\u0010q\u001a\u00020r8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\bs\u0010\u0004\u001a\u0004\bt\u0010u\"\u0004\bv\u0010wR\u000e\u0010x\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010y\u001a\u00020z8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b{\u0010\u0004\u001a\u0004\b|\u0010}\"\u0004\b~\u0010\u007fR+\u0010\u0080\u0001\u001a\u00030\u0081\u00018\u0006@\u0006X\u0087.¢\u0006\u0019\n\u0000\u0012\u0005\b\u0082\u0001\u0010\u0004\u001a\u0006\b\u0083\u0001\u0010\u0084\u0001\"\u0006\b\u0085\u0001\u0010\u0086\u0001¨\u0006Ï\u0001"}, d2 = {"Lorg/catrobat/paintroid/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$MainView;", "Lorg/catrobat/paintroid/command/CommandManager$CommandListener;", "()V", "appFragment", "Lorg/catrobat/paintroid/PaintroidApplicationFragment;", "bottomNavigationViewHolder", "Lorg/catrobat/paintroid/ui/viewholder/BottomNavigationViewHolder;", "getBottomNavigationViewHolder", "()Lorg/catrobat/paintroid/ui/viewholder/BottomNavigationViewHolder;", "setBottomNavigationViewHolder", "(Lorg/catrobat/paintroid/ui/viewholder/BottomNavigationViewHolder;)V", "commandFactory", "Lorg/catrobat/paintroid/command/CommandFactory;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "getCommandManager", "()Lorg/catrobat/paintroid/command/CommandManager;", "setCommandManager", "(Lorg/catrobat/paintroid/command/CommandManager;)V", "commandSerializer", "Lorg/catrobat/paintroid/command/serialization/CommandSerializer;", "defaultToolController", "Lorg/catrobat/paintroid/controller/DefaultToolController;", "getDefaultToolController", "()Lorg/catrobat/paintroid/controller/DefaultToolController;", "setDefaultToolController", "(Lorg/catrobat/paintroid/controller/DefaultToolController;)V", "deferredRequestPermissionsResult", "Ljava/lang/Runnable;", "displayMetrics", "Landroid/util/DisplayMetrics;", "getDisplayMetrics", "()Landroid/util/DisplayMetrics;", "drawerLayoutViewHolder", "Lorg/catrobat/paintroid/ui/viewholder/DrawerLayoutViewHolder;", "drawingSurface", "Lorg/catrobat/paintroid/ui/DrawingSurface;", "finishing", "", "getFinishing", "()Z", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "getIdlingResource", "()Landroidx/test/espresso/idling/CountingIdlingResource;", "setIdlingResource", "(Landroidx/test/espresso/idling/CountingIdlingResource;)V", "isKeyboardShown", "isRunningEspressoTests", "isRunningEspressoTests$delegate", "Lkotlin/Lazy;", "isTemporaryFileSavingTest", "keyboardListener", "Lorg/catrobat/paintroid/ui/KeyboardListener;", "lastInteractionTime", "", "layerAdapter", "Lorg/catrobat/paintroid/ui/LayerAdapter;", "getLayerAdapter$annotations", "getLayerAdapter", "()Lorg/catrobat/paintroid/ui/LayerAdapter;", "setLayerAdapter", "(Lorg/catrobat/paintroid/ui/LayerAdapter;)V", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "getLayerModel$annotations", "getLayerModel", "()Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "setLayerModel", "(Lorg/catrobat/paintroid/contract/LayerContracts$Model;)V", "layerPresenter", "Lorg/catrobat/paintroid/presenter/LayerPresenter;", "minuteTemporaryCopiesCounter", "", "model", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;", "getModel", "()Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;", "setModel", "(Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;)V", "myContentResolver", "Landroid/content/ContentResolver;", "getMyContentResolver", "()Landroid/content/ContentResolver;", "perspective", "Lorg/catrobat/paintroid/ui/Perspective;", "getPerspective$annotations", "getPerspective", "()Lorg/catrobat/paintroid/ui/Perspective;", "setPerspective", "(Lorg/catrobat/paintroid/ui/Perspective;)V", "presenter", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "getPresenter", "()Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "presenterMain", "progressBar", "Landroidx/core/widget/ContentLoadingProgressBar;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "getToolOptionsViewController$annotations", "getToolOptionsViewController", "()Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "setToolOptionsViewController", "(Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;)V", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "getToolPaint", "()Lorg/catrobat/paintroid/tools/ToolPaint;", "setToolPaint", "(Lorg/catrobat/paintroid/tools/ToolPaint;)V", "toolReference", "Lorg/catrobat/paintroid/tools/ToolReference;", "getToolReference$annotations", "getToolReference", "()Lorg/catrobat/paintroid/tools/ToolReference;", "setToolReference", "(Lorg/catrobat/paintroid/tools/ToolReference;)V", "userInteraction", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "getWorkspace$annotations", "getWorkspace", "()Lorg/catrobat/paintroid/tools/Workspace;", "setWorkspace", "(Lorg/catrobat/paintroid/tools/Workspace;)V", "zoomWindowController", "Lorg/catrobat/paintroid/ui/zoomwindow/DefaultZoomWindowController;", "getZoomWindowController$annotations", "getZoomWindowController", "()Lorg/catrobat/paintroid/ui/zoomwindow/DefaultZoomWindowController;", "setZoomWindowController", "(Lorg/catrobat/paintroid/ui/zoomwindow/DefaultZoomWindowController;)V", "addToMinuteTemporaryCopiesCounter", "", "seconds", "commandPostExecute", "enterHideButtons", "exitHideButtons", "getAppFragment", "getUriFromFile", "Landroid/net/Uri;", "file", "Ljava/io/File;", "getVersionCode", "", "handleIntent", "receivedIntent", "Landroid/content/Intent;", "hideContentLoadingProgressBar", "hideKeyboard", "initializeActionBar", MainActivity.IS_OPENED_FROM_CATROID_KEY, "onActivityResult", "requestCode", "resultCode", "data", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDrawingSurface", "onCreateGlobals", "onCreateLayerMenu", "onCreateMainView", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onDestroy", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "onSaveInstanceState", "outState", "onUserInteraction", "onWindowFocusChanged", "hasFocus", "refreshDrawingSurface", "setActionBarToolTips", "topBar", "Lorg/catrobat/paintroid/ui/viewholder/TopBarViewHolder;", "context", "Landroid/content/Context;", "setBottomBarListeners", "viewHolder", "Lorg/catrobat/paintroid/ui/viewholder/BottomBarViewHolder;", "setBottomNavigationListeners", "setLayerMenuListeners", "layerMenuViewHolder", "Lorg/catrobat/paintroid/ui/viewholder/LayerMenuViewHolder;", "setLayoutDirection", "setTopBarListeners", "showContentLoadingProgressBar", "startAutoSaveCoroutine", "superHandleActivityResult", "superHandleRequestPermissionsResult", "validateIntent", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MainActivity extends AppCompatActivity implements MainActivityContracts.MainView, CommandManager.CommandListener {
    private static final String APP_FRAGMENT_KEY = "customActivityState";
    private static final String CAMERA_IMAGE_URI_KEY = "cameraImageUri";
    private static final String FIRST_LAUNCH_AFTER_INSTALL = "firstLaunchAfterInstall";
    private static final String IS_FULLSCREEN_KEY = "isFullscreen";
    private static final String IS_OPENED_FROM_CATROID_KEY = "isOpenedFromCatroid";
    private static final String IS_OPENED_FROM_FORMULA_EDITOR_IN_CATROID_KEY = "isOpenedFromFormulaEditorInCatroid";
    private static final String IS_SAVED_KEY = "isSaved";
    private static final String SAVED_PICTURE_URI_KEY = "savedPictureUri";
    private static final String SHARED_PREFS_NAME = "preferences";
    public static final String TAG = "MainActivity";
    private PaintroidApplicationFragment appFragment;
    public BottomNavigationViewHolder bottomNavigationViewHolder;
    private CommandFactory commandFactory;
    public CommandManager commandManager;
    private CommandSerializer commandSerializer;
    public DefaultToolController defaultToolController;
    private Runnable deferredRequestPermissionsResult;
    private DrawerLayoutViewHolder drawerLayoutViewHolder;
    private DrawingSurface drawingSurface;
    private boolean isTemporaryFileSavingTest;
    private KeyboardListener keyboardListener;
    public LayerAdapter layerAdapter;
    public LayerContracts.Model layerModel;
    private LayerPresenter layerPresenter;
    private volatile int minuteTemporaryCopiesCounter;
    public MainActivityContracts.Model model;
    public Perspective perspective;
    private MainActivityContracts.Presenter presenterMain;
    private ContentLoadingProgressBar progressBar;
    public ToolOptionsViewController toolOptionsViewController;
    public ToolPaint toolPaint;
    public ToolReference toolReference;
    private volatile boolean userInteraction;
    public Workspace workspace;
    public DefaultZoomWindowController zoomWindowController;
    private CountingIdlingResource idlingResource = new CountingIdlingResource("MainIdleResource");
    private volatile long lastInteractionTime = System.currentTimeMillis();

    /* JADX INFO: renamed from: isRunningEspressoTests$delegate, reason: from kotlin metadata */
    private final Lazy isRunningEspressoTests = LazyKt.lazy(new Function0<Boolean>() { // from class: org.catrobat.paintroid.MainActivity.isRunningEspressoTests.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            boolean z;
            try {
                Class.forName("androidx.test.espresso.Espresso");
                z = true;
            } catch (ClassNotFoundException unused) {
                Log.e(MainActivity.TAG, "Application is not in test mode.");
                z = false;
            }
            return Boolean.valueOf(z);
        }
    });

    public static /* synthetic */ void getLayerAdapter$annotations() {
    }

    public static /* synthetic */ void getLayerModel$annotations() {
    }

    public static /* synthetic */ void getPerspective$annotations() {
    }

    public static /* synthetic */ void getToolOptionsViewController$annotations() {
    }

    public static /* synthetic */ void getToolReference$annotations() {
    }

    public static /* synthetic */ void getWorkspace$annotations() {
    }

    public static /* synthetic */ void getZoomWindowController$annotations() {
    }

    public final Perspective getPerspective() {
        Perspective perspective = this.perspective;
        if (perspective != null) {
            return perspective;
        }
        Intrinsics.throwUninitializedPropertyAccessException("perspective");
        return null;
    }

    public final void setPerspective(Perspective perspective) {
        Intrinsics.checkNotNullParameter(perspective, "<set-?>");
        this.perspective = perspective;
    }

    public final Workspace getWorkspace() {
        Workspace workspace = this.workspace;
        if (workspace != null) {
            return workspace;
        }
        Intrinsics.throwUninitializedPropertyAccessException("workspace");
        return null;
    }

    public final void setWorkspace(Workspace workspace) {
        Intrinsics.checkNotNullParameter(workspace, "<set-?>");
        this.workspace = workspace;
    }

    public final LayerContracts.Model getLayerModel() {
        LayerContracts.Model model = this.layerModel;
        if (model != null) {
            return model;
        }
        Intrinsics.throwUninitializedPropertyAccessException("layerModel");
        return null;
    }

    public final void setLayerModel(LayerContracts.Model model) {
        Intrinsics.checkNotNullParameter(model, "<set-?>");
        this.layerModel = model;
    }

    public final ToolReference getToolReference() {
        ToolReference toolReference = this.toolReference;
        if (toolReference != null) {
            return toolReference;
        }
        Intrinsics.throwUninitializedPropertyAccessException("toolReference");
        return null;
    }

    public final void setToolReference(ToolReference toolReference) {
        Intrinsics.checkNotNullParameter(toolReference, "<set-?>");
        this.toolReference = toolReference;
    }

    public final ToolOptionsViewController getToolOptionsViewController() {
        ToolOptionsViewController toolOptionsViewController = this.toolOptionsViewController;
        if (toolOptionsViewController != null) {
            return toolOptionsViewController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("toolOptionsViewController");
        return null;
    }

    public final void setToolOptionsViewController(ToolOptionsViewController toolOptionsViewController) {
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "<set-?>");
        this.toolOptionsViewController = toolOptionsViewController;
    }

    public final LayerAdapter getLayerAdapter() {
        LayerAdapter layerAdapter = this.layerAdapter;
        if (layerAdapter != null) {
            return layerAdapter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("layerAdapter");
        return null;
    }

    public final void setLayerAdapter(LayerAdapter layerAdapter) {
        Intrinsics.checkNotNullParameter(layerAdapter, "<set-?>");
        this.layerAdapter = layerAdapter;
    }

    public final CountingIdlingResource getIdlingResource() {
        return this.idlingResource;
    }

    public final void setIdlingResource(CountingIdlingResource countingIdlingResource) {
        Intrinsics.checkNotNullParameter(countingIdlingResource, "<set-?>");
        this.idlingResource = countingIdlingResource;
    }

    public final DefaultZoomWindowController getZoomWindowController() {
        DefaultZoomWindowController defaultZoomWindowController = this.zoomWindowController;
        if (defaultZoomWindowController != null) {
            return defaultZoomWindowController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("zoomWindowController");
        return null;
    }

    public final void setZoomWindowController(DefaultZoomWindowController defaultZoomWindowController) {
        Intrinsics.checkNotNullParameter(defaultZoomWindowController, "<set-?>");
        this.zoomWindowController = defaultZoomWindowController;
    }

    public final CommandManager getCommandManager() {
        CommandManager commandManager = this.commandManager;
        if (commandManager != null) {
            return commandManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("commandManager");
        return null;
    }

    public final void setCommandManager(CommandManager commandManager) {
        Intrinsics.checkNotNullParameter(commandManager, "<set-?>");
        this.commandManager = commandManager;
    }

    public final ToolPaint getToolPaint() {
        ToolPaint toolPaint = this.toolPaint;
        if (toolPaint != null) {
            return toolPaint;
        }
        Intrinsics.throwUninitializedPropertyAccessException("toolPaint");
        return null;
    }

    public final void setToolPaint(ToolPaint toolPaint) {
        Intrinsics.checkNotNullParameter(toolPaint, "<set-?>");
        this.toolPaint = toolPaint;
    }

    public final BottomNavigationViewHolder getBottomNavigationViewHolder() {
        BottomNavigationViewHolder bottomNavigationViewHolder = this.bottomNavigationViewHolder;
        if (bottomNavigationViewHolder != null) {
            return bottomNavigationViewHolder;
        }
        Intrinsics.throwUninitializedPropertyAccessException("bottomNavigationViewHolder");
        return null;
    }

    public final void setBottomNavigationViewHolder(BottomNavigationViewHolder bottomNavigationViewHolder) {
        Intrinsics.checkNotNullParameter(bottomNavigationViewHolder, "<set-?>");
        this.bottomNavigationViewHolder = bottomNavigationViewHolder;
    }

    public final MainActivityContracts.Model getModel() {
        MainActivityContracts.Model model = this.model;
        if (model != null) {
            return model;
        }
        Intrinsics.throwUninitializedPropertyAccessException("model");
        return null;
    }

    public final void setModel(MainActivityContracts.Model model) {
        Intrinsics.checkNotNullParameter(model, "<set-?>");
        this.model = model;
    }

    public final DefaultToolController getDefaultToolController() {
        DefaultToolController defaultToolController = this.defaultToolController;
        if (defaultToolController != null) {
            return defaultToolController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("defaultToolController");
        return null;
    }

    public final void setDefaultToolController(DefaultToolController defaultToolController) {
        Intrinsics.checkNotNullParameter(defaultToolController, "<set-?>");
        this.defaultToolController = defaultToolController;
    }

    private final boolean isRunningEspressoTests() {
        return ((Boolean) this.isRunningEspressoTests.getValue()).booleanValue();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public MainActivityContracts.Presenter getPresenter() {
        MainActivityContracts.Presenter presenter = this.presenterMain;
        if (presenter != null) {
            return presenter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
        return null;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Intrinsics.checkNotNullExpressionValue(displayMetrics, "resources.displayMetrics");
        return displayMetrics;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public boolean isKeyboardShown() {
        KeyboardListener keyboardListener = this.keyboardListener;
        if (keyboardListener == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keyboardListener");
            keyboardListener = null;
        }
        return keyboardListener.getIsSoftKeyboardVisible();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public ContentResolver getMyContentResolver() {
        ContentResolver contentResolver = getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "contentResolver");
        return contentResolver;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public boolean getFinishing() {
        return isFinishing();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Runnable runnable = this.deferredRequestPermissionsResult;
        if (runnable == null) {
            return;
        }
        this.deferredRequestPermissionsResult = null;
        runnable.run();
    }

    private final boolean handleIntent(Intent receivedIntent) {
        String mimeTypeFromExtension;
        Uri data = (Uri) receivedIntent.getParcelableExtra("android.intent.extra.STREAM");
        if (data == null) {
            data = receivedIntent.getData();
        }
        CommandFactory commandFactory = null;
        if (Intrinsics.areEqual(data == null ? null : data.getScheme(), FirebaseAnalytics.Param.CONTENT)) {
            mimeTypeFromExtension = getMyContentResolver().getType(data);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(data == null ? null : data.toString());
            MimeTypeMap singleton = MimeTypeMap.getSingleton();
            Intrinsics.checkNotNullExpressionValue(fileExtension, "fileExtension");
            Locale US = Locale.US;
            Intrinsics.checkNotNullExpressionValue(US, "US");
            String lowerCase = fileExtension.toLowerCase(US);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
            mimeTypeFromExtension = singleton.getMimeTypeFromExtension(lowerCase);
        }
        if (data == null) {
            return true;
        }
        try {
            if (StringsKt.equals$default(mimeTypeFromExtension, "application/zip", false, 2, null) || StringsKt.equals$default(mimeTypeFromExtension, "application/octet-stream", false, 2, null)) {
                try {
                    CommandSerializer commandSerializer = this.commandSerializer;
                    if (commandSerializer == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("commandSerializer");
                        commandSerializer = null;
                    }
                    CatrobatFileContent fromFile = commandSerializer.readFromFile(data);
                    getCommandManager().loadCommandsCatrobatImage(fromFile.getCommandModel());
                    MainActivityContracts.Presenter presenter = this.presenterMain;
                    if (presenter == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
                        presenter = null;
                    }
                    presenter.setColorHistoryAfterLoadImage(fromFile.getColorHistory());
                    return false;
                } catch (CommandSerializer.NotCatrobatImageException unused) {
                    Log.e(TAG, "Image might be an ora file instead");
                    OpenRasterFileFormatConversion.INSTANCE.setMainActivity(this);
                    List<? extends LayerContracts.Layer> list = OpenRasterFileFormatConversion.INSTANCE.importOraFile(getMyContentResolver(), data).layerList;
                    if (list != null) {
                        CommandManager commandManager = getCommandManager();
                        CommandFactory commandFactory2 = this.commandFactory;
                        if (commandFactory2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("commandFactory");
                            commandFactory2 = null;
                        }
                        commandManager.setInitialStateCommand(commandFactory2.createInitCommand(list));
                    }
                    Paint paint = new Paint();
                    PointF pointF = new PointF(0.0f, 0.0f);
                    paint.setColor(0);
                    CommandManager commandManager2 = getCommandManager();
                    CommandFactory commandFactory3 = this.commandFactory;
                    if (commandFactory3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("commandFactory");
                    } else {
                        commandFactory = commandFactory3;
                    }
                    commandManager2.addCommand(commandFactory.createPointCommand(paint, pointF));
                    getCommandManager().undo();
                    getCommandManager().reset();
                }
            } else {
                FileIO fileIO = FileIO.INSTANCE;
                FileIO.filename = Constants.TMP_IMAGE_FILE_NAME;
                ContentResolver myContentResolver = getMyContentResolver();
                Context applicationContext = getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
                Bitmap bitmapFromUri = FileIO.getBitmapFromUri(myContentResolver, data, applicationContext);
                if (bitmapFromUri != null) {
                    CommandManager commandManager3 = getCommandManager();
                    CommandFactory commandFactory4 = this.commandFactory;
                    if (commandFactory4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("commandFactory");
                    } else {
                        commandFactory = commandFactory4;
                    }
                    commandManager3.setInitialStateCommand(commandFactory.createInitCommand(bitmapFromUri));
                }
            }
        } catch (Exception unused2) {
            Log.e("Can not read", "Unable to retrieve Bitmap from Uri");
        }
        return true;
    }

    private final boolean validateIntent(Intent receivedIntent) {
        String action = receivedIntent.getAction();
        String type = receivedIntent.getType();
        if (!Intrinsics.areEqual(action, "android.intent.action.EDIT")) {
            if (action == null || type == null) {
                return false;
            }
            if (!Intrinsics.areEqual(action, "android.intent.action.SEND") && !Intrinsics.areEqual(action, "android.intent.action.VIEW")) {
                return false;
            }
            if (!StringsKt.startsWith$default(type, "image/", false, 2, (Object) null) && !StringsKt.startsWith$default(type, "application/", false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        MainActivityContracts.Presenter presenter;
        setTheme(R.style.PocketPaintTheme);
        super.onCreate(savedInstanceState);
        getAppFragment();
        PaintroidApplication.INSTANCE.setCacheDir(getCacheDir());
        setContentView(R.layout.activity_pocketpaint_main);
        onCreateGlobals();
        onCreateMainView();
        onCreateLayerMenu();
        onCreateDrawingSurface();
        MainActivityContracts.Presenter presenter2 = this.presenterMain;
        MainActivityContracts.Presenter presenter3 = null;
        if (presenter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter2 = null;
        }
        presenter2.onCreateTool();
        OpenRasterFileFormatConversion.INSTANCE.setMainActivity(this);
        Intent receivedIntent = getIntent();
        this.isTemporaryFileSavingTest = getIntent().getBooleanExtra("isTemporaryFileSavingTest", false);
        Intrinsics.checkNotNullExpressionValue(receivedIntent, "receivedIntent");
        if (validateIntent(receivedIntent) && savedInstanceState == null) {
            if (handleIntent(receivedIntent)) {
                getCommandManager().reset();
            }
            getModel().setSavedPictureUri(null);
            getModel().setCameraImageUri(null);
            getWorkspace().resetPerspective();
            MainActivityContracts.Presenter presenter4 = this.presenterMain;
            if (presenter4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
                presenter4 = null;
            }
            presenter4.initializeFromCleanState(null, null);
        } else if (savedInstanceState == null) {
            Intent intent = getIntent();
            String stringExtra = intent.getStringExtra("org.catrobat.extra.PAINTROID_PICTURE_PATH");
            String stringExtra2 = intent.getStringExtra(ConstantsKt.PAINTROID_PICTURE_NAME);
            MainActivityContracts.Presenter presenter5 = this.presenterMain;
            if (presenter5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
                presenter5 = null;
            }
            presenter5.initializeFromCleanState(stringExtra, stringExtra2);
            if (!getModel().getIsOpenedFromCatroid()) {
                MainActivityContracts.Presenter presenter6 = this.presenterMain;
                if (presenter6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
                    presenter6 = null;
                }
                if (presenter6.checkForTemporaryFile() && (!isRunningEspressoTests() || this.isTemporaryFileSavingTest)) {
                    MainActivityContracts.Presenter presenter7 = this.presenterMain;
                    if (presenter7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
                        presenter7 = null;
                    }
                    WorkspaceReturnValue workspaceReturnValueOpenTemporaryFile = presenter7.openTemporaryFile();
                    getCommandManager().loadCommandsCatrobatImage(workspaceReturnValueOpenTemporaryFile == null ? null : workspaceReturnValueOpenTemporaryFile.getCommandManagerModel());
                    MainActivityContracts.Model model = getModel();
                    ColorHistory colorHistory = workspaceReturnValueOpenTemporaryFile == null ? null : workspaceReturnValueOpenTemporaryFile.getColorHistory();
                    if (colorHistory == null) {
                        colorHistory = new ColorHistory();
                    }
                    model.setColorHistory(colorHistory);
                    Integer num = (Integer) CollectionsKt.lastOrNull((List) getModel().getColorHistory().getColors());
                    if (num != null) {
                        int iIntValue = num.intValue();
                        Tool tool = getToolReference().getTool();
                        if (tool != null) {
                            Tool.DefaultImpls.changePaintColor$default(tool, iIntValue, false, 2, null);
                        }
                        MainActivityContracts.Presenter presenter8 = this.presenterMain;
                        if (presenter8 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
                            presenter8 = null;
                        }
                        presenter8.setBottomNavigationColor(iIntValue);
                    }
                }
            }
            getWorkspace().getPerspective().setBitmapDimensions(getLayerModel().getWidth(), getLayerModel().getHeight());
        } else {
            boolean z = savedInstanceState.getBoolean(IS_FULLSCREEN_KEY, false);
            boolean z2 = savedInstanceState.getBoolean(IS_SAVED_KEY, false);
            boolean z3 = savedInstanceState.getBoolean(IS_OPENED_FROM_CATROID_KEY, false);
            boolean z4 = savedInstanceState.getBoolean(IS_OPENED_FROM_FORMULA_EDITOR_IN_CATROID_KEY, false);
            Uri uri = (Uri) savedInstanceState.getParcelable(SAVED_PICTURE_URI_KEY);
            Uri uri2 = (Uri) savedInstanceState.getParcelable(CAMERA_IMAGE_URI_KEY);
            MainActivityContracts.Presenter presenter9 = this.presenterMain;
            if (presenter9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
                presenter = null;
            } else {
                presenter = presenter9;
            }
            presenter.restoreState(z, z2, z3, z4, uri, uri2);
        }
        getCommandManager().addCommandListener(this);
        this.lastInteractionTime = System.currentTimeMillis();
        if ((!isRunningEspressoTests() || this.isTemporaryFileSavingTest) && !getModel().getIsOpenedFromCatroid()) {
            startAutoSaveCoroutine();
        }
        MainActivityContracts.Presenter presenter10 = this.presenterMain;
        if (presenter10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter10 = null;
        }
        presenter10.finishInitialize();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, 0);
        if (sharedPreferences.getBoolean(FIRST_LAUNCH_AFTER_INSTALL, true)) {
            sharedPreferences.edit().putBoolean(FIRST_LAUNCH_AFTER_INSTALL, false).apply();
            MainActivityContracts.Presenter presenter11 = this.presenterMain;
            if (presenter11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter3 = presenter11;
            }
            presenter3.showHelpClicked();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            MainActivityContracts.Presenter presenter = this.presenterMain;
            if (presenter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
                presenter = null;
            }
            presenter.saveNewTemporaryImage();
            this.minuteTemporaryCopiesCounter = 0;
            this.userInteraction = false;
        }
        getWindow().getDecorView().setSystemUiVisibility(4);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        getMenuInflater().inflate(R.menu.menu_pocketpaint_more_options, menu);
        MainActivityContracts.Presenter presenter = this.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.removeMoreOptionsItems(menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        MainActivityContracts.Presenter presenter = null;
        if (itemId == R.id.pocketpaint_options_export) {
            MainActivityContracts.Presenter presenter2 = this.presenterMain;
            if (presenter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter2;
            }
            presenter.saveCopyClicked(true);
        } else if (itemId == R.id.pocketpaint_options_save_image) {
            MainActivityContracts.Presenter presenter3 = this.presenterMain;
            if (presenter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter3;
            }
            presenter.saveImageClicked();
        } else if (itemId == R.id.pocketpaint_options_save_duplicate) {
            MainActivityContracts.Presenter presenter4 = this.presenterMain;
            if (presenter4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter4;
            }
            presenter.saveCopyClicked(false);
        } else if (itemId == R.id.pocketpaint_replace_image) {
            MainActivityContracts.Presenter presenter5 = this.presenterMain;
            if (presenter5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter5;
            }
            presenter.replaceImageClicked();
        } else if (itemId == R.id.pocketpaint_add_to_current_layer) {
            MainActivityContracts.Presenter presenter6 = this.presenterMain;
            if (presenter6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter6;
            }
            presenter.addImageToCurrentLayerClicked();
        } else if (itemId == R.id.pocketpaint_options_new_image) {
            MainActivityContracts.Presenter presenter7 = this.presenterMain;
            if (presenter7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter7;
            }
            presenter.newImageClicked();
        } else if (itemId == R.id.pocketpaint_options_discard_image) {
            MainActivityContracts.Presenter presenter8 = this.presenterMain;
            if (presenter8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter8;
            }
            presenter.discardImageClicked();
        } else if (itemId == R.id.pocketpaint_options_fullscreen_mode) {
            getPerspective().setMainActivity(this);
            MainActivityContracts.Presenter presenter9 = this.presenterMain;
            if (presenter9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter9;
            }
            presenter.enterHideButtonsClicked();
        } else if (itemId == R.id.pocketpaint_options_rate_us) {
            MainActivityContracts.Presenter presenter10 = this.presenterMain;
            if (presenter10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter10;
            }
            presenter.rateUsClicked();
        } else if (itemId == R.id.pocketpaint_options_help) {
            MainActivityContracts.Presenter presenter11 = this.presenterMain;
            if (presenter11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter11;
            }
            presenter.showHelpClicked();
        } else if (itemId == R.id.pocketpaint_options_about) {
            MainActivityContracts.Presenter presenter12 = this.presenterMain;
            if (presenter12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter12;
            }
            presenter.showAboutClicked();
        } else if (itemId == R.id.pocketpaint_share_image_button) {
            MainActivityContracts.Presenter presenter13 = this.presenterMain;
            if (presenter13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter13;
            }
            presenter.shareImageClicked();
        } else if (itemId == R.id.pocketpaint_options_feedback) {
            MainActivityContracts.Presenter presenter14 = this.presenterMain;
            if (presenter14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter14;
            }
            presenter.sendFeedback();
        } else if (itemId == R.id.pocketpaint_zoom_window_settings) {
            MainActivityContracts.Presenter presenter15 = this.presenterMain;
            if (presenter15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter15;
            }
            SharedPreferences preferences = getPreferences(0);
            Intrinsics.checkNotNullExpressionValue(preferences, "getPreferences(MODE_PRIVATE)");
            presenter.showZoomWindowSettingsClicked(new UserPreferences(preferences));
        } else if (itemId == R.id.pocketpaint_advanced_settings) {
            MainActivityContracts.Presenter presenter16 = this.presenterMain;
            if (presenter16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter16;
            }
            presenter.showAdvancedSettingsClicked();
        } else {
            if (itemId != 16908332) {
                return false;
            }
            MainActivityContracts.Presenter presenter17 = this.presenterMain;
            if (presenter17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter17;
            }
            presenter.backToPocketCodeClicked();
        }
        return true;
    }

    private final void getAppFragment() {
        Fragment fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(APP_FRAGMENT_KEY);
        if (fragmentFindFragmentByTag != null) {
            this.appFragment = (PaintroidApplicationFragment) fragmentFindFragmentByTag;
        }
        if (this.appFragment == null) {
            this.appFragment = new PaintroidApplicationFragment();
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            PaintroidApplicationFragment paintroidApplicationFragment = this.appFragment;
            if (paintroidApplicationFragment == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appFragment");
                paintroidApplicationFragment = null;
            }
            fragmentTransactionBeginTransaction.add(paintroidApplicationFragment, APP_FRAGMENT_KEY).commit();
        }
    }

    private final void onCreateGlobals() {
        PaintroidApplicationFragment paintroidApplicationFragment = this.appFragment;
        PaintroidApplicationFragment paintroidApplicationFragment2 = null;
        if (paintroidApplicationFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appFragment");
            paintroidApplicationFragment = null;
        }
        LayerModel layerModel = paintroidApplicationFragment.getLayerModel();
        if (layerModel == null) {
            layerModel = new LayerModel();
        }
        PaintroidApplicationFragment paintroidApplicationFragment3 = this.appFragment;
        if (paintroidApplicationFragment3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appFragment");
            paintroidApplicationFragment3 = null;
        }
        paintroidApplicationFragment3.setLayerModel(layerModel);
        setLayerModel(layerModel);
        this.commandFactory = new DefaultCommandFactory();
        PaintroidApplicationFragment paintroidApplicationFragment4 = this.appFragment;
        if (paintroidApplicationFragment4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appFragment");
            paintroidApplicationFragment4 = null;
        }
        CommandManager commandManager = paintroidApplicationFragment4.getCommandManager();
        if (commandManager == null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            setCommandManager(new AsyncCommandManager(new DefaultCommandManager(new CommonFactory(), getLayerModel()), getLayerModel()));
            CommandFactory commandFactory = this.commandFactory;
            if (commandFactory == null) {
                Intrinsics.throwUninitializedPropertyAccessException("commandFactory");
                commandFactory = null;
            }
            getCommandManager().setInitialStateCommand(commandFactory.createInitCommand(displayMetrics.widthPixels, displayMetrics.heightPixels));
            getCommandManager().reset();
            PaintroidApplicationFragment paintroidApplicationFragment5 = this.appFragment;
            if (paintroidApplicationFragment5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appFragment");
                paintroidApplicationFragment5 = null;
            }
            paintroidApplicationFragment5.setCommandManager(getCommandManager());
        } else {
            setCommandManager(commandManager);
        }
        PaintroidApplicationFragment paintroidApplicationFragment6 = this.appFragment;
        if (paintroidApplicationFragment6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appFragment");
            paintroidApplicationFragment6 = null;
        }
        DefaultToolPaint toolPaint = paintroidApplicationFragment6.getToolPaint();
        if (toolPaint == null) {
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            toolPaint = new DefaultToolPaint(applicationContext);
        }
        PaintroidApplicationFragment paintroidApplicationFragment7 = this.appFragment;
        if (paintroidApplicationFragment7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appFragment");
            paintroidApplicationFragment7 = null;
        }
        paintroidApplicationFragment7.setToolPaint(toolPaint);
        setToolPaint(toolPaint);
        PaintroidApplicationFragment paintroidApplicationFragment8 = this.appFragment;
        if (paintroidApplicationFragment8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appFragment");
            paintroidApplicationFragment8 = null;
        }
        DefaultToolReference currentTool = paintroidApplicationFragment8.getCurrentTool();
        if (currentTool == null) {
            currentTool = new DefaultToolReference();
        }
        PaintroidApplicationFragment paintroidApplicationFragment9 = this.appFragment;
        if (paintroidApplicationFragment9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appFragment");
        } else {
            paintroidApplicationFragment2 = paintroidApplicationFragment9;
        }
        paintroidApplicationFragment2.setCurrentTool(currentTool);
        setToolReference(currentTool);
    }

    private final void onCreateMainView() {
        CommandSerializer commandSerializer;
        MainActivity mainActivity = this;
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.pocketpaint_drawer_layout);
        ViewGroup topBarLayout = (ViewGroup) findViewById(R.id.pocketpaint_layout_top_bar);
        View bottomBarLayout = findViewById(R.id.pocketpaint_main_bottom_bar);
        View bottomNavigationView = findViewById(R.id.pocketpaint_main_bottom_navigation);
        MainActivity mainActivity2 = this;
        setToolOptionsViewController(new DefaultToolOptionsViewController(mainActivity2, this.idlingResource));
        Intrinsics.checkNotNullExpressionValue(drawerLayout, "drawerLayout");
        this.drawerLayoutViewHolder = new DrawerLayoutViewHolder(drawerLayout);
        Intrinsics.checkNotNullExpressionValue(topBarLayout, "topBarLayout");
        TopBarViewHolder topBarViewHolder = new TopBarViewHolder(topBarLayout);
        Intrinsics.checkNotNullExpressionValue(bottomBarLayout, "bottomBarLayout");
        BottomBarViewHolder bottomBarViewHolder = new BottomBarViewHolder(bottomBarLayout);
        Intrinsics.checkNotNullExpressionValue(bottomNavigationView, "bottomNavigationView");
        int i = getResources().getConfiguration().orientation;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        setBottomNavigationViewHolder(new BottomNavigationViewHolder(bottomNavigationView, i, applicationContext));
        setPerspective(new Perspective(getLayerModel().getWidth(), getLayerModel().getHeight()));
        setWorkspace(new DefaultWorkspace(getLayerModel(), getPerspective(), new DefaultWorkspace.Listener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda4
            @Override // org.catrobat.paintroid.tools.implementation.DefaultWorkspace.Listener
            public final void invalidate() {
                MainActivity.m5877onCreateMainView$lambda6(this.f$0);
            }
        }));
        setModel(new MainActivityModel());
        this.commandSerializer = new CommandSerializer(mainActivity, getCommandManager(), getModel());
        LayerContracts.Model layerModel = getLayerModel();
        Workspace workspace = getWorkspace();
        ToolReference toolReference = getToolReference();
        SharedPreferences preferences = getPreferences(0);
        Intrinsics.checkNotNullExpressionValue(preferences, "getPreferences(MODE_PRIVATE)");
        setZoomWindowController(new DefaultZoomWindowController(this, layerModel, workspace, toolReference, new UserPreferences(preferences)));
        setDefaultToolController(new DefaultToolController(getToolReference(), getToolOptionsViewController(), new DefaultToolFactory(this), getCommandManager(), getWorkspace(), this.idlingResource, getToolPaint(), new DefaultContextCallback(mainActivity)));
        SharedPreferences preferences2 = getPreferences(0);
        Intrinsics.checkNotNullExpressionValue(preferences2, "getPreferences(MODE_PRIVATE)");
        UserPreferences userPreferences = new UserPreferences(preferences2);
        MainActivityNavigator mainActivityNavigator = new MainActivityNavigator(this, getToolReference());
        MainActivity mainActivity3 = this;
        MainActivityContracts.Model model = getModel();
        Workspace workspace2 = getWorkspace();
        MainActivityNavigator mainActivityNavigator2 = new MainActivityNavigator(this, getToolReference());
        MainActivityInteractor mainActivityInteractor = new MainActivityInteractor(this.idlingResource);
        TopBarViewHolder topBarViewHolder2 = topBarViewHolder;
        BottomBarViewHolder bottomBarViewHolder2 = bottomBarViewHolder;
        DrawerLayoutViewHolder drawerLayoutViewHolder = this.drawerLayoutViewHolder;
        if (drawerLayoutViewHolder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayoutViewHolder");
            drawerLayoutViewHolder = null;
        }
        DrawerLayoutViewHolder drawerLayoutViewHolder2 = drawerLayoutViewHolder;
        BottomNavigationViewHolder bottomNavigationViewHolder = getBottomNavigationViewHolder();
        DefaultCommandFactory defaultCommandFactory = new DefaultCommandFactory();
        CommandManager commandManager = getCommandManager();
        DefaultToolController defaultToolController = getDefaultToolController();
        CountingIdlingResource countingIdlingResource = this.idlingResource;
        File filesDir = getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "filesDir");
        CommandSerializer commandSerializer2 = this.commandSerializer;
        if (commandSerializer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandSerializer");
            commandSerializer = null;
        } else {
            commandSerializer = commandSerializer2;
        }
        this.presenterMain = new MainActivityPresenter(mainActivity2, mainActivity3, model, workspace2, mainActivityNavigator2, mainActivityInteractor, topBarViewHolder2, bottomBarViewHolder2, drawerLayoutViewHolder2, bottomNavigationViewHolder, defaultCommandFactory, commandManager, defaultToolController, userPreferences, countingIdlingResource, mainActivity, filesDir, commandSerializer);
        FileIO.INSTANCE.setNavigator(mainActivityNavigator);
        DefaultToolController defaultToolController2 = getDefaultToolController();
        MainActivityContracts.Presenter presenter = this.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        defaultToolController2.setOnColorPickedListener(new PresenterColorPickedListener(presenter));
        this.keyboardListener = new KeyboardListener(drawerLayout);
        setTopBarListeners(topBarViewHolder);
        setBottomBarListeners(bottomBarViewHolder);
        setBottomNavigationListeners(getBottomNavigationViewHolder());
        setActionBarToolTips(topBarViewHolder, mainActivity);
        View viewFindViewById = findViewById(R.id.pocketpaint_content_loading_progress_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocket…ent_loading_progress_bar)");
        this.progressBar = (ContentLoadingProgressBar) viewFindViewById;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateMainView$lambda-6, reason: not valid java name */
    public static final void m5877onCreateMainView$lambda6(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawingSurface drawingSurface = this$0.drawingSurface;
        if (drawingSurface == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawingSurface");
            drawingSurface = null;
        }
        drawingSurface.refreshDrawingSurface();
    }

    private final void onCreateLayerMenu() {
        setLayoutDirection();
        NavigationView layerLayout = (NavigationView) findViewById(R.id.pocketpaint_nav_view_layer);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.pocketpaint_drawer_layout);
        DragAndDropListView layerListView = (DragAndDropListView) findViewById(R.id.pocketpaint_layer_side_nav_list);
        Intrinsics.checkNotNullExpressionValue(layerLayout, "layerLayout");
        LayerMenuViewHolder layerMenuViewHolder = new LayerMenuViewHolder(layerLayout);
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        LayerNavigator layerNavigator = new LayerNavigator(applicationContext);
        LayerContracts.Model layerModel = getLayerModel();
        Intrinsics.checkNotNullExpressionValue(layerListView, "layerListView");
        this.layerPresenter = new LayerPresenter(layerModel, layerListView, layerMenuViewHolder, getCommandManager(), new DefaultCommandFactory(), layerNavigator);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), 1, false);
        layerListView.setLayoutManager(linearLayoutManager);
        layerListView.setManager$Paintroid_signedRelease(linearLayoutManager);
        LayerPresenter layerPresenter = this.layerPresenter;
        LayerPresenter layerPresenter2 = null;
        if (layerPresenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter = null;
        }
        setLayerAdapter(new LayerAdapter(layerPresenter, this));
        layerListView.setLayerAdapter(getLayerAdapter());
        MainActivityContracts.Presenter presenter = this.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.setLayerAdapter(getLayerAdapter());
        LayerPresenter layerPresenter3 = this.layerPresenter;
        if (layerPresenter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter3 = null;
        }
        layerPresenter3.setAdapter(getLayerAdapter());
        LayerPresenter layerPresenter4 = this.layerPresenter;
        if (layerPresenter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter4 = null;
        }
        layerListView.setPresenter(layerPresenter4);
        layerListView.setAdapter(getLayerAdapter());
        LayerPresenter layerPresenter5 = this.layerPresenter;
        if (layerPresenter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter5 = null;
        }
        layerPresenter5.refreshLayerMenuViewHolder();
        LayerPresenter layerPresenter6 = this.layerPresenter;
        if (layerPresenter6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter6 = null;
        }
        layerPresenter6.disableVisibilityAndOpacityButtons();
        setLayerMenuListeners(layerMenuViewHolder);
        LayerPresenter layerPresenter7 = this.layerPresenter;
        if (layerPresenter7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
        } else {
            layerPresenter2 = layerPresenter7;
        }
        drawerLayout.addDrawerListener(new DrawerLayoutListener(this, layerPresenter2));
    }

    private final void setLayoutDirection() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.pocketpaint_layer_side_nav_button_visibility);
        NavigationView navigationView = (NavigationView) findViewById(R.id.pocketpaint_nav_view_layer);
        if (getResources().getConfiguration().getLayoutDirection() == 1) {
            imageButton.setBackgroundResource(R.drawable.rounded_corner_top_rtl);
            navigationView.setBackgroundResource(R.drawable.layer_nav_view_background_rtl);
        } else {
            imageButton.setBackgroundResource(R.drawable.rounded_corner_top_ltr);
            navigationView.setBackgroundResource(R.drawable.layer_nav_view_background_ltr);
        }
    }

    private final void onCreateDrawingSurface() {
        DrawingSurface drawingSurface;
        DrawerLayoutViewHolder drawerLayoutViewHolder;
        View viewFindViewById = findViewById(R.id.pocketpaint_drawing_surface_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocket…int_drawing_surface_view)");
        DrawingSurface drawingSurface2 = (DrawingSurface) viewFindViewById;
        this.drawingSurface = drawingSurface2;
        LayerPresenter layerPresenter = null;
        if (drawingSurface2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawingSurface");
            drawingSurface = null;
        } else {
            drawingSurface = drawingSurface2;
        }
        LayerContracts.Model layerModel = getLayerModel();
        Perspective perspective = getPerspective();
        ToolReference toolReference = getToolReference();
        CountingIdlingResource countingIdlingResource = this.idlingResource;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "supportFragmentManager");
        ToolOptionsViewController toolOptionsViewController = getToolOptionsViewController();
        DrawerLayoutViewHolder drawerLayoutViewHolder2 = this.drawerLayoutViewHolder;
        if (drawerLayoutViewHolder2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayoutViewHolder");
            drawerLayoutViewHolder = null;
        } else {
            drawerLayoutViewHolder = drawerLayoutViewHolder2;
        }
        DefaultZoomWindowController zoomWindowController = getZoomWindowController();
        SharedPreferences preferences = getPreferences(0);
        Intrinsics.checkNotNullExpressionValue(preferences, "getPreferences(MODE_PRIVATE)");
        drawingSurface.setArguments(layerModel, perspective, toolReference, countingIdlingResource, supportFragmentManager, toolOptionsViewController, drawerLayoutViewHolder, zoomWindowController, new UserPreferences(preferences));
        LayerPresenter layerPresenter2 = this.layerPresenter;
        if (layerPresenter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter2 = null;
        }
        DrawingSurface drawingSurface3 = this.drawingSurface;
        if (drawingSurface3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawingSurface");
            drawingSurface3 = null;
        }
        layerPresenter2.setDrawingSurface(drawingSurface3);
        PaintroidApplicationFragment paintroidApplicationFragment = this.appFragment;
        if (paintroidApplicationFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appFragment");
            paintroidApplicationFragment = null;
        }
        paintroidApplicationFragment.setPerspective(getPerspective());
        LayerPresenter layerPresenter3 = this.layerPresenter;
        if (layerPresenter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter3 = null;
        }
        layerPresenter3.setDefaultToolController(getDefaultToolController());
        LayerPresenter layerPresenter4 = this.layerPresenter;
        if (layerPresenter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
        } else {
            layerPresenter = layerPresenter4;
        }
        layerPresenter.setBottomNavigationViewHolder(getBottomNavigationViewHolder());
    }

    private final void setLayerMenuListeners(LayerMenuViewHolder layerMenuViewHolder) {
        layerMenuViewHolder.getLayerAddButton().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m5881setLayerMenuListeners$lambda7(this.f$0, view);
            }
        });
        layerMenuViewHolder.getLayerDeleteButton().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m5882setLayerMenuListeners$lambda8(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setLayerMenuListeners$lambda-7, reason: not valid java name */
    public static final void m5881setLayerMenuListeners$lambda7(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LayerPresenter layerPresenter = this$0.layerPresenter;
        if (layerPresenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter = null;
        }
        layerPresenter.addLayer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setLayerMenuListeners$lambda-8, reason: not valid java name */
    public static final void m5882setLayerMenuListeners$lambda8(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LayerPresenter layerPresenter = this$0.layerPresenter;
        if (layerPresenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
            layerPresenter = null;
        }
        layerPresenter.removeLayer();
    }

    private final void setActionBarToolTips(TopBarViewHolder topBar, Context context) {
        TooltipCompat.setTooltipText(topBar.getUndoButton(), context.getString(R.string.button_undo));
        TooltipCompat.setTooltipText(topBar.getRedoButton(), context.getString(R.string.button_redo));
    }

    private final void setTopBarListeners(TopBarViewHolder topBar) {
        topBar.getUndoButton().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m5886setTopBarListeners$lambda9(this.f$0, view);
            }
        });
        topBar.getRedoButton().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m5883setTopBarListeners$lambda10(this.f$0, view);
            }
        });
        topBar.getCheckmarkButton().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m5884setTopBarListeners$lambda11(this.f$0, view);
            }
        });
        topBar.getPlusButton().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.m5885setTopBarListeners$lambda12(this.f$0, view);
            }
        });
        LineTool.INSTANCE.setTopBarViewHolder(topBar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setTopBarListeners$lambda-9, reason: not valid java name */
    public static final void m5886setTopBarListeners$lambda9(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MainActivityContracts.Presenter presenter = this$0.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.undoClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setTopBarListeners$lambda-10, reason: not valid java name */
    public static final void m5883setTopBarListeners$lambda10(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MainActivityContracts.Presenter presenter = this$0.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.redoClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setTopBarListeners$lambda-11, reason: not valid java name */
    public static final void m5884setTopBarListeners$lambda11(MainActivity this$0, View view) {
        ToolType toolType;
        ToolType toolType2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Tool tool = this$0.getToolReference().getTool();
        if (StringsKt.equals$default((tool == null || (toolType = tool.getToolType()) == null) ? null : toolType.name(), ToolType.TRANSFORM.name(), false, 2, null)) {
            Tool tool2 = this$0.getToolReference().getTool();
            if (tool2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.TransformTool");
            }
            ((TransformTool) tool2).setCheckMarkClicked(true);
            BaseToolWithShape baseToolWithShape = (BaseToolWithShape) this$0.getToolReference().getTool();
            if (baseToolWithShape == null) {
                return;
            }
            baseToolWithShape.onClickOnButton();
            return;
        }
        Tool tool3 = this$0.getToolReference().getTool();
        if (StringsKt.equals$default((tool3 == null || (toolType2 = tool3.getToolType()) == null) ? null : toolType2.name(), ToolType.CLIP.name(), false, 2, null)) {
            ClippingTool clippingTool = (ClippingTool) this$0.getToolReference().getTool();
            if (clippingTool == null) {
                return;
            }
            clippingTool.onClickOnButton();
            return;
        }
        BaseToolWithShape baseToolWithShape2 = (BaseToolWithShape) this$0.getToolReference().getTool();
        if (baseToolWithShape2 == null) {
            return;
        }
        baseToolWithShape2.onClickOnButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setTopBarListeners$lambda-12, reason: not valid java name */
    public static final void m5885setTopBarListeners$lambda12(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Tool tool = this$0.getToolReference().getTool();
        if (tool == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.LineTool");
        }
        ((LineTool) tool).onClickOnPlus();
    }

    private final void setBottomBarListeners(BottomBarViewHolder viewHolder) {
        ToolType[] toolTypeArrValues = ToolType.values();
        int length = toolTypeArrValues.length;
        int i = 0;
        while (i < length) {
            final ToolType toolType = toolTypeArrValues[i];
            i++;
            View viewFindViewById = viewHolder.getLayout().findViewById(toolType.getToolButtonID());
            if (viewFindViewById != null) {
                viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MainActivity.m5879setBottomBarListeners$lambda13(this.f$0, toolType, view);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setBottomBarListeners$lambda-13, reason: not valid java name */
    public static final void m5879setBottomBarListeners$lambda13(MainActivity this$0, ToolType type, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(type, "$type");
        MainActivityContracts.Presenter presenter = this$0.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.toolClicked(type);
    }

    private final void setBottomNavigationListeners(BottomNavigationViewHolder viewHolder) {
        viewHolder.getBottomNavigationView().setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: org.catrobat.paintroid.MainActivity$$ExternalSyntheticLambda2
            @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
            public final boolean onNavigationItemSelected(MenuItem menuItem) {
                return MainActivity.m5880setBottomNavigationListeners$lambda14(this.f$0, menuItem);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setBottomNavigationListeners$lambda-14, reason: not valid java name */
    public static final boolean m5880setBottomNavigationListeners$lambda14(MainActivity this$0, MenuItem item) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        MainActivityContracts.Presenter presenter = null;
        if (itemId == R.id.action_tools) {
            MainActivityContracts.Presenter presenter2 = this$0.presenterMain;
            if (presenter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter2;
            }
            presenter.actionToolsClicked();
            return true;
        }
        if (itemId == R.id.action_current_tool) {
            MainActivityContracts.Presenter presenter3 = this$0.presenterMain;
            if (presenter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter3;
            }
            presenter.actionCurrentToolClicked();
            return true;
        }
        if (itemId == R.id.action_color_picker) {
            MainActivityContracts.Presenter presenter4 = this$0.presenterMain;
            if (presenter4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            } else {
                presenter = presenter4;
            }
            presenter.showColorPickerClicked();
            return true;
        }
        if (itemId != R.id.action_layers) {
            return false;
        }
        MainActivityContracts.Presenter presenter5 = this$0.presenterMain;
        if (presenter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
        } else {
            presenter = presenter5;
        }
        presenter.showLayerMenuClicked();
        return true;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void initializeActionBar(boolean isOpenedFromCatroid) {
        setSupportActionBar((Toolbar) findViewById(R.id.pocketpaint_toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            return;
        }
        supportActionBar.setDisplayShowTitleEnabled(!isOpenedFromCatroid);
        supportActionBar.setDisplayHomeAsUpEnabled(isOpenedFromCatroid);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(false);
    }

    @Override // org.catrobat.paintroid.command.CommandManager.CommandListener
    public void commandPostExecute() {
        if (getFinishing()) {
            return;
        }
        MainActivityContracts.Presenter presenter = null;
        if (!(getCommandManager().getLastExecutedCommand() instanceof LayerOpacityCommand)) {
            LayerPresenter layerPresenter = this.layerPresenter;
            if (layerPresenter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layerPresenter");
                layerPresenter = null;
            }
            layerPresenter.invalidate();
        }
        MainActivityContracts.Presenter presenter2 = this.presenterMain;
        if (presenter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
        } else {
            presenter = presenter2;
        }
        presenter.onCommandPostExecute();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        getCommandManager().removeCommandListener(this);
        MainActivityContracts.Presenter presenter = this.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.saveNewTemporaryImage();
        if (getFinishing()) {
            getCommandManager().shutdown();
            PaintroidApplicationFragment paintroidApplicationFragment = this.appFragment;
            if (paintroidApplicationFragment == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appFragment");
                paintroidApplicationFragment = null;
            }
            paintroidApplicationFragment.setCurrentTool(null);
            PaintroidApplicationFragment paintroidApplicationFragment2 = this.appFragment;
            if (paintroidApplicationFragment2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appFragment");
                paintroidApplicationFragment2 = null;
            }
            paintroidApplicationFragment2.setCommandManager(null);
            PaintroidApplicationFragment paintroidApplicationFragment3 = this.appFragment;
            if (paintroidApplicationFragment3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appFragment");
                paintroidApplicationFragment3 = null;
            }
            paintroidApplicationFragment3.setLayerModel(null);
        }
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_FULLSCREEN_KEY, getModel().getIsFullscreen());
        outState.putBoolean(IS_SAVED_KEY, getModel().getIsSaved());
        outState.putBoolean(IS_OPENED_FROM_CATROID_KEY, getModel().getIsOpenedFromCatroid());
        outState.putBoolean(IS_OPENED_FROM_FORMULA_EDITOR_IN_CATROID_KEY, getModel().getIsOpenedFromFormulaEditorInCatroid());
        outState.putParcelable(SAVED_PICTURE_URI_KEY, getModel().getSavedPictureUri());
        outState.putParcelable(CAMERA_IMAGE_URI_KEY, getModel().getCameraImageUri());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (getSupportFragmentManager().isStateSaved()) {
            super.onBackPressed();
            return;
        }
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return;
        }
        MainActivityContracts.Presenter presenter = this.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.onBackPressed();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MainActivityContracts.Presenter presenter = this.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.handleActivityResult(requestCode, resultCode, data);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void superHandleActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onRequestPermissionsResult$lambda-17, reason: not valid java name */
    public static final void m5878onRequestPermissionsResult$lambda17(MainActivity this$0, int i, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(permissions, "$permissions");
        Intrinsics.checkNotNullParameter(grantResults, "$grantResults");
        MainActivityContracts.Presenter presenter = this$0.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.handleRequestPermissionsResult(i, permissions, grantResults);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        MainActivityContracts.Presenter presenter = this.presenterMain;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenterMain");
            presenter = null;
        }
        presenter.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void superHandleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void refreshDrawingSurface() {
        DrawingSurface drawingSurface = this.drawingSurface;
        if (drawingSurface == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawingSurface");
            drawingSurface = null;
        }
        drawingSurface.refreshDrawingSurface();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void enterHideButtons() {
        if (Build.VERSION.SDK_INT >= 30) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController == null) {
                return;
            }
            insetsController.hide(WindowInsets.Type.statusBars());
            return;
        }
        getWindow().addFlags(1024);
        getWindow().clearFlags(2048);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void exitHideButtons() {
        getWindow().addFlags(2048);
        getWindow().clearFlags(1024);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public Uri getUriFromFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        Uri uriFromFile = Uri.fromFile(file);
        Intrinsics.checkNotNullExpressionValue(uriFromFile, "fromFile(file)");
        return uriFromFile;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void showContentLoadingProgressBar() {
        ContentLoadingProgressBar contentLoadingProgressBar = this.progressBar;
        if (contentLoadingProgressBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progressBar");
            contentLoadingProgressBar = null;
        }
        contentLoadingProgressBar.show();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.MainView
    public void hideContentLoadingProgressBar() {
        ContentLoadingProgressBar contentLoadingProgressBar = this.progressBar;
        if (contentLoadingProgressBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progressBar");
            contentLoadingProgressBar = null;
        }
        contentLoadingProgressBar.hide();
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        super.onUserInteraction();
        this.lastInteractionTime = System.currentTimeMillis();
        this.userInteraction = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void addToMinuteTemporaryCopiesCounter(int seconds) {
        this.minuteTemporaryCopiesCounter += seconds;
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.MainActivity$startAutoSaveCoroutine$1, reason: invalid class name */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.MainActivity$startAutoSaveCoroutine$1", f = "MainActivity.kt", i = {}, l = {GL20.GL_SRC_ALPHA_SATURATE}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x0027 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:14:0x003e  */
        /* JADX WARN: Code duplicated, block: B:16:0x0048  */
        /* JADX WARN: Code duplicated, block: B:20:0x0058  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x0025 -> B:12:0x0028). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:11:0x0027
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 1
                if (r1 == 0) goto L17
                if (r1 != r2) goto Lf
                kotlin.ResultKt.throwOnFailure(r8)
                goto L28
            Lf:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L17:
                kotlin.ResultKt.throwOnFailure(r8)
            L1a:
                r8 = r7
                kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
                r7.label = r2
                r3 = 1000(0x3e8, double:4.94E-321)
                java.lang.Object r8 = kotlinx.coroutines.DelayKt.delay(r3, r8)
                if (r8 != r0) goto L28
                return r0
            L28:
                org.catrobat.paintroid.MainActivity r8 = org.catrobat.paintroid.MainActivity.this
                org.catrobat.paintroid.MainActivity.access$addToMinuteTemporaryCopiesCounter(r8, r2)
                long r3 = java.lang.System.currentTimeMillis()
                org.catrobat.paintroid.MainActivity r8 = org.catrobat.paintroid.MainActivity.this
                long r5 = org.catrobat.paintroid.MainActivity.access$getLastInteractionTime$p(r8)
                long r3 = r3 - r5
                r5 = 2000(0x7d0, double:9.88E-321)
                int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r8 >= 0) goto L48
                org.catrobat.paintroid.MainActivity r8 = org.catrobat.paintroid.MainActivity.this
                int r8 = org.catrobat.paintroid.MainActivity.access$getMinuteTemporaryCopiesCounter$p(r8)
                r1 = 60
                if (r8 < r1) goto L1a
            L48:
                org.catrobat.paintroid.MainActivity r8 = org.catrobat.paintroid.MainActivity.this
                boolean r8 = org.catrobat.paintroid.MainActivity.access$getUserInteraction$p(r8)
                if (r8 == 0) goto L1a
                org.catrobat.paintroid.MainActivity r8 = org.catrobat.paintroid.MainActivity.this
                org.catrobat.paintroid.contract.MainActivityContracts$Presenter r8 = org.catrobat.paintroid.MainActivity.access$getPresenterMain$p(r8)
                if (r8 != 0) goto L5e
                java.lang.String r8 = "presenterMain"
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r8)
                r8 = 0
            L5e:
                r8.saveNewTemporaryImage()
                org.catrobat.paintroid.MainActivity r8 = org.catrobat.paintroid.MainActivity.this
                r1 = 0
                org.catrobat.paintroid.MainActivity.access$setMinuteTemporaryCopiesCounter$p(r8, r1)
                org.catrobat.paintroid.MainActivity r8 = org.catrobat.paintroid.MainActivity.this
                org.catrobat.paintroid.MainActivity.access$setUserInteraction$p(r8, r1)
                goto L1a
            */
            throw new UnsupportedOperationException("Method not decompiled: org.catrobat.paintroid.MainActivity.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final void startAutoSaveCoroutine() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new AnonymousClass1(null), 3, null);
    }

    public final String getVersionCode() {
        Object objM2898constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            MainActivity mainActivity = this;
            objM2898constructorimpl = Result.m2898constructorimpl(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2904isFailureimpl(objM2898constructorimpl)) {
            objM2898constructorimpl = "";
        }
        return (String) objM2898constructorimpl;
    }
}
