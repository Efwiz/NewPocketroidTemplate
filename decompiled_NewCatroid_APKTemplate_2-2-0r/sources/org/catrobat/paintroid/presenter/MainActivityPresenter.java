package org.catrobat.paintroid.presenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.UserPreferences;
import org.catrobat.paintroid.colorpicker.ColorHistory;
import org.catrobat.paintroid.command.CommandFactory;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.command.serialization.CommandSerializer;
import org.catrobat.paintroid.common.ConstantsKt;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.controller.ToolController;
import org.catrobat.paintroid.dialog.PermissionInfoDialog;
import org.catrobat.paintroid.iotasks.BitmapReturnValue;
import org.catrobat.paintroid.iotasks.CreateFile;
import org.catrobat.paintroid.iotasks.LoadImage;
import org.catrobat.paintroid.iotasks.SaveImage;
import org.catrobat.paintroid.iotasks.WorkspaceReturnValue;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.implementation.BaseToolWithShape;
import org.catrobat.paintroid.tools.implementation.ClippingTool;
import org.catrobat.paintroid.tools.implementation.DefaultToolPaint;
import org.catrobat.paintroid.tools.implementation.EraserTool;
import org.catrobat.paintroid.tools.implementation.LineTool;
import org.catrobat.paintroid.ui.LayerAdapter;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: MainActivityPresenter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0090\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0019\b\u0016\u0018\u0000 Ç\u00012\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0002Ç\u0001B\u0097\u0001\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u001a\u0012\u0006\u0010\u001b\u001a\u00020\u001c\u0012\u0006\u0010\u001d\u001a\u00020\u001e\u0012\u0006\u0010\u001f\u001a\u00020 \u0012\u0006\u0010!\u001a\u00020\"\u0012\u0006\u0010#\u001a\u00020$\u0012\u0006\u0010%\u001a\u00020&\u0012\u0006\u0010'\u001a\u00020(¢\u0006\u0002\u0010)J\b\u0010R\u001a\u00020SH\u0016J\b\u0010T\u001a\u00020SH\u0016J\b\u0010U\u001a\u00020SH\u0016J\u0006\u0010V\u001a\u00020SJ\u0010\u0010W\u001a\u00020S2\u0006\u0010X\u001a\u00020EH\u0002J\b\u0010Y\u001a\u00020SH\u0016J\u0010\u0010Z\u001a\u00020S2\u0006\u0010[\u001a\u00020+H\u0016J\b\u0010\\\u001a\u00020SH\u0002J\b\u0010]\u001a\u00020SH\u0002J\b\u0010^\u001a\u00020/H\u0016J\u0006\u0010_\u001a\u00020SJ\b\u0010`\u001a\u00020SH\u0002J\b\u0010a\u001a\u00020SH\u0016J\b\u0010b\u001a\u00020SH\u0002J\b\u0010c\u001a\u00020SH\u0016J\b\u0010d\u001a\u00020SH\u0002J\b\u0010e\u001a\u00020SH\u0016J\b\u0010f\u001a\u00020SH\u0016J\b\u0010g\u001a\u00020SH\u0016J\u0012\u0010h\u001a\u0004\u0018\u00010i2\u0006\u0010j\u001a\u00020kH\u0002J\"\u0010l\u001a\u00020S2\u0006\u0010X\u001a\u00020E2\u0006\u0010m\u001a\u00020E2\b\u0010n\u001a\u0004\u0018\u00010oH\u0016J+\u0010p\u001a\u00020S2\u0006\u0010X\u001a\u00020E2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020i0r2\u0006\u0010s\u001a\u00020tH\u0016¢\u0006\u0002\u0010uJ\u0006\u0010v\u001a\u00020SJ\b\u0010w\u001a\u00020SH\u0016J\b\u0010x\u001a\u00020SH\u0016J\u001c\u0010y\u001a\u00020S2\b\u0010z\u001a\u0004\u0018\u00010i2\b\u0010{\u001a\u0004\u0018\u00010iH\u0016J\b\u0010|\u001a\u00020SH\u0016J\u001a\u0010}\u001a\u00020S2\b\u0010j\u001a\u0004\u0018\u00010k2\u0006\u0010X\u001a\u00020EH\u0016J\b\u0010~\u001a\u00020SH\u0016J\b\u0010\u007f\u001a\u00020SH\u0016J\t\u0010\u0080\u0001\u001a\u00020SH\u0016J\u001c\u0010\u0081\u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020E2\t\u0010\u0082\u0001\u001a\u0004\u0018\u00010&H\u0016J\t\u0010\u0083\u0001\u001a\u00020SH\u0016J'\u0010\u0084\u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020E2\b\u0010j\u001a\u0004\u0018\u00010k2\n\u0010\u0085\u0001\u001a\u0005\u0018\u00010\u0086\u0001H\u0016J\u0011\u0010\u0087\u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020EH\u0016J\t\u0010\u0088\u0001\u001a\u00020SH\u0016J$\u0010\u0089\u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020E2\b\u0010j\u001a\u0004\u0018\u00010k2\u0007\u0010\u008a\u0001\u001a\u00020/H\u0016J\u0011\u0010\u008b\u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020EH\u0016J\f\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0016J\t\u0010\u008e\u0001\u001a\u00020SH\u0016J\t\u0010\u008f\u0001\u001a\u00020SH\u0016J\t\u0010\u0090\u0001\u001a\u00020SH\u0002J\u0015\u0010\u0091\u0001\u001a\u00020S2\n\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u0001H\u0016J\t\u0010\u0094\u0001\u001a\u00020SH\u0016JC\u0010\u0095\u0001\u001a\u00020S2\u0007\u0010\u0096\u0001\u001a\u00020/2\u0007\u0010\u0097\u0001\u001a\u00020/2\u0007\u0010\u0098\u0001\u001a\u00020/2\u0007\u0010\u0099\u0001\u001a\u00020/2\t\u0010\u009a\u0001\u001a\u0004\u0018\u00010k2\t\u0010\u009b\u0001\u001a\u0004\u0018\u00010kH\u0016J\t\u0010\u009c\u0001\u001a\u00020SH\u0016J\t\u0010\u009d\u0001\u001a\u00020SH\u0016J\t\u0010\u009e\u0001\u001a\u00020SH\u0016J\u0011\u0010\u009f\u0001\u001a\u00020S2\u0006\u0010H\u001a\u00020/H\u0016J\u001b\u0010 \u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020E2\b\u0010j\u001a\u0004\u0018\u00010kH\u0016J\t\u0010¡\u0001\u001a\u00020SH\u0016J\u001b\u0010¢\u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020E2\b\u0010j\u001a\u0004\u0018\u00010kH\u0016J\t\u0010£\u0001\u001a\u00020SH\u0016J\t\u0010¤\u0001\u001a\u00020SH\u0016J\t\u0010¥\u0001\u001a\u00020SH\u0016J\u0012\u0010¦\u0001\u001a\u00020S2\u0007\u0010§\u0001\u001a\u00020EH\u0016J\u0015\u0010¨\u0001\u001a\u00020S2\n\u0010©\u0001\u001a\u0005\u0018\u00010ª\u0001H\u0016J\t\u0010«\u0001\u001a\u00020SH\u0002J\u0011\u0010¬\u0001\u001a\u00020S2\u0006\u0010K\u001a\u00020LH\u0016J\u0013\u0010\u00ad\u0001\u001a\u00020S2\b\u0010®\u0001\u001a\u00030¯\u0001H\u0002J\t\u0010°\u0001\u001a\u00020SH\u0016J\t\u0010±\u0001\u001a\u00020SH\u0016J\t\u0010²\u0001\u001a\u00020SH\u0016J\t\u0010³\u0001\u001a\u00020SH\u0016J\t\u0010´\u0001\u001a\u00020SH\u0016J\t\u0010µ\u0001\u001a\u00020SH\u0016J\t\u0010¶\u0001\u001a\u00020SH\u0016J\t\u0010·\u0001\u001a\u00020SH\u0016J\t\u0010¸\u0001\u001a\u00020SH\u0016J\t\u0010¹\u0001\u001a\u00020SH\u0016J\t\u0010º\u0001\u001a\u00020SH\u0002J\t\u0010»\u0001\u001a\u00020SH\u0016J\u001a\u0010¼\u0001\u001a\u00020S2\u0007\u0010½\u0001\u001a\u00020E2\u0006\u0010H\u001a\u00020/H\u0016J\t\u0010¾\u0001\u001a\u00020SH\u0016J\t\u0010¿\u0001\u001a\u00020SH\u0016J\t\u0010À\u0001\u001a\u00020SH\u0002J\u0011\u0010Á\u0001\u001a\u00020S2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u000f\u0010Â\u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020EJ\u0019\u0010Â\u0001\u001a\u00020S2\u0006\u0010X\u001a\u00020E2\u0006\u0010H\u001a\u00020/H\u0016J\u0013\u0010Ã\u0001\u001a\u00020S2\b\u0010Ä\u0001\u001a\u00030¯\u0001H\u0002J\u0013\u0010Å\u0001\u001a\u00020S2\b\u0010®\u0001\u001a\u00030¯\u0001H\u0016J\t\u0010Æ\u0001\u001a\u00020SH\u0016R\u0016\u0010*\u001a\u0004\u0018\u00010+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010.\u001a\u00020/X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u000205X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010:\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0014\u0010#\u001a\u00020$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R\u0010\u0010@\u001a\u0004\u0018\u00010AX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bB\u0010CR\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010D\u001a\u00020E8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010GR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010I\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u00101R\u0014\u0010J\u001a\u00020/8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bJ\u00101R\u0010\u0010K\u001a\u0004\u0018\u00010LX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010N\u001a\u00020/X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u00101\"\u0004\bP\u00103R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006È\u0001"}, d2 = {"Lorg/catrobat/paintroid/presenter/MainActivityPresenter;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "Lorg/catrobat/paintroid/iotasks/SaveImage$SaveImageCallback;", "Lorg/catrobat/paintroid/iotasks/LoadImage$LoadImageCallback;", "Lorg/catrobat/paintroid/iotasks/CreateFile$CreateFileCallback;", "fileActivity", "Landroid/app/Activity;", Promotion.ACTION_VIEW, "Lorg/catrobat/paintroid/contract/MainActivityContracts$MainView;", "model", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "navigator", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Navigator;", "interactor", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Interactor;", "topBarViewHolder", "Lorg/catrobat/paintroid/contract/MainActivityContracts$TopBarViewHolder;", "bottomBarViewHolder", "Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomBarViewHolder;", "drawerLayoutViewHolder", "Lorg/catrobat/paintroid/contract/MainActivityContracts$DrawerLayoutViewHolder;", "bottomNavigationViewHolder", "Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomNavigationViewHolder;", "commandFactory", "Lorg/catrobat/paintroid/command/CommandFactory;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "toolController", "Lorg/catrobat/paintroid/controller/ToolController;", "sharedPreferences", "Lorg/catrobat/paintroid/UserPreferences;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "context", "Landroid/content/Context;", "internalMemoryPath", "Ljava/io/File;", "commandSerializer", "Lorg/catrobat/paintroid/command/serialization/CommandSerializer;", "(Landroid/app/Activity;Lorg/catrobat/paintroid/contract/MainActivityContracts$MainView;Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;Lorg/catrobat/paintroid/tools/Workspace;Lorg/catrobat/paintroid/contract/MainActivityContracts$Navigator;Lorg/catrobat/paintroid/contract/MainActivityContracts$Interactor;Lorg/catrobat/paintroid/contract/MainActivityContracts$TopBarViewHolder;Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomBarViewHolder;Lorg/catrobat/paintroid/contract/MainActivityContracts$DrawerLayoutViewHolder;Lorg/catrobat/paintroid/contract/MainActivityContracts$BottomNavigationViewHolder;Lorg/catrobat/paintroid/command/CommandFactory;Lorg/catrobat/paintroid/command/CommandManager;Lorg/catrobat/paintroid/controller/ToolController;Lorg/catrobat/paintroid/UserPreferences;Landroidx/test/espresso/idling/CountingIdlingResource;Landroid/content/Context;Ljava/io/File;Lorg/catrobat/paintroid/command/serialization/CommandSerializer;)V", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "clippingToolInUseAndUndoRedoClicked", "", "getClippingToolInUseAndUndoRedoClicked", "()Z", "setClippingToolInUseAndUndoRedoClicked", "(Z)V", "clippingToolPaint", "Landroid/graphics/Paint;", "getClippingToolPaint", "()Landroid/graphics/Paint;", "setClippingToolPaint", "(Landroid/graphics/Paint;)V", "contentResolver", "Landroid/content/ContentResolver;", "getContentResolver", "()Landroid/content/ContentResolver;", "getContext", "()Landroid/content/Context;", "downTimer", "Landroid/os/CountDownTimer;", "getFileActivity", "()Landroid/app/Activity;", "imageNumber", "", "getImageNumber", "()I", "isExport", "isFinishing", "isImageUnchanged", "layerAdapter", "Lorg/catrobat/paintroid/ui/LayerAdapter;", "resetPerspectiveAfterNextCommand", "toolOptionsViewWasShown", "getToolOptionsViewWasShown", "setToolOptionsViewWasShown", "wasImageLoaded", "actionCurrentToolClicked", "", "actionToolsClicked", "addImageToCurrentLayerClicked", "adjustClippingToolPostCommandExecute", "askForReadAndWriteExternalStoragePermission", "requestCode", "backToPocketCodeClicked", "bitmapLoadedFromSource", "loadedImage", "checkForDefaultFilename", "checkForImplicitToolApplication", "checkForTemporaryFile", "checkIfClippingToolNeedsAdjustment", "countUpImageNumber", "discardImageClicked", "enterHideButtons", "enterHideButtonsClicked", "exitHideButtons", "exitHideButtonsClicked", "finishActivity", "finishInitialize", "getFileName", "", "uri", "Landroid/net/Uri;", "handleActivityResult", "resultCode", "data", "Landroid/content/Intent;", "handleRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "hideBottomBarViewHolder", "importFromGalleryClicked", "importStickersClicked", "initializeFromCleanState", "extraPicturePath", "extraPictureName", "loadNewImage", "loadScaledImage", "newImageClicked", "onBackPressed", "onCommandPostExecute", "onCreateFilePostExecute", "file", "onCreateTool", "onLoadImagePostExecute", "result", "Lorg/catrobat/paintroid/iotasks/BitmapReturnValue;", "onLoadImagePreExecute", "onNewImage", "onSaveImagePostExecute", "saveAsCopy", "onSaveImagePreExecute", "openTemporaryFile", "Lorg/catrobat/paintroid/iotasks/WorkspaceReturnValue;", "rateUsClicked", "redoClicked", "refreshTopBarButtons", "removeMoreOptionsItems", LineReader.MENU, "Landroid/view/Menu;", "replaceImageClicked", "restoreState", "isFullscreen", "isSaved", "isOpenedFromCatroid", "isOpenedFromFormulaEditorInCatroid", "savedPictureUri", "cameraImageUri", "saveBeforeFinish", "saveBeforeLoadImage", "saveBeforeNewImage", "saveCopyClicked", "saveCopyConfirmClicked", "saveImageClicked", "saveImageConfirmClicked", "saveNewTemporaryImage", "sendFeedback", "setAntialiasingOnOkClicked", "setBottomNavigationColor", TypedValues.Custom.S_COLOR, "setColorHistoryAfterLoadImage", "colorHistory", "Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "setFirstCheckBoxInLayerMenu", "setLayerAdapter", "setTool", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "shareImageClicked", "showAboutClicked", "showAdvancedSettingsClicked", "showCatrobatInformationDialog", "showColorPickerClicked", "showFeedbackDialog", "showHelpClicked", "showImportDialog", "showJpgInformationDialog", "showLayerMenuClicked", "showLikeUsDialogIfFirstTimeSave", "showOraInformationDialog", "showOverwriteDialog", "permissionCode", "showPngInformationDialog", "showRateUsDialog", "showSecurityQuestionBeforeExit", "showZoomWindowSettingsClicked", "switchBetweenVersions", "switchTool", ModuleXmlParser.TYPE, "toolClicked", "undoClicked", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class MainActivityPresenter implements MainActivityContracts.Presenter, SaveImage.SaveImageCallback, LoadImage.LoadImageCallback, CreateFile.CreateFileCallback {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final MainActivityContracts.BottomBarViewHolder bottomBarViewHolder;
    private final MainActivityContracts.BottomNavigationViewHolder bottomNavigationViewHolder;
    private boolean clippingToolInUseAndUndoRedoClicked;
    private Paint clippingToolPaint;
    private final CommandFactory commandFactory;
    private final CommandManager commandManager;
    private final CommandSerializer commandSerializer;
    private final Context context;
    private CountDownTimer downTimer;
    private final MainActivityContracts.DrawerLayoutViewHolder drawerLayoutViewHolder;
    private final Activity fileActivity;
    private final CountingIdlingResource idlingResource;
    private final MainActivityContracts.Interactor interactor;
    private final File internalMemoryPath;
    private boolean isExport;
    private LayerAdapter layerAdapter;
    private final MainActivityContracts.Model model;
    private final MainActivityContracts.Navigator navigator;
    private boolean resetPerspectiveAfterNextCommand;
    private final UserPreferences sharedPreferences;
    private final ToolController toolController;
    private boolean toolOptionsViewWasShown;
    private final MainActivityContracts.TopBarViewHolder topBarViewHolder;
    private final MainActivityContracts.MainView view;
    private boolean wasImageLoaded;
    private final Workspace workspace;

    @JvmStatic
    public static final String getPathFromUri(Context context, Uri uri) {
        return INSTANCE.getPathFromUri(context, uri);
    }

    @Override // org.catrobat.paintroid.iotasks.LoadImage.LoadImageCallback
    public void onLoadImagePreExecute(int requestCode) {
    }

    public MainActivityPresenter(Activity activity, MainActivityContracts.MainView view, MainActivityContracts.Model model, Workspace workspace, MainActivityContracts.Navigator navigator, MainActivityContracts.Interactor interactor, MainActivityContracts.TopBarViewHolder topBarViewHolder, MainActivityContracts.BottomBarViewHolder bottomBarViewHolder, MainActivityContracts.DrawerLayoutViewHolder drawerLayoutViewHolder, MainActivityContracts.BottomNavigationViewHolder bottomNavigationViewHolder, CommandFactory commandFactory, CommandManager commandManager, ToolController toolController, UserPreferences sharedPreferences, CountingIdlingResource idlingResource, Context context, File internalMemoryPath, CommandSerializer commandSerializer) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(navigator, "navigator");
        Intrinsics.checkNotNullParameter(interactor, "interactor");
        Intrinsics.checkNotNullParameter(topBarViewHolder, "topBarViewHolder");
        Intrinsics.checkNotNullParameter(bottomBarViewHolder, "bottomBarViewHolder");
        Intrinsics.checkNotNullParameter(drawerLayoutViewHolder, "drawerLayoutViewHolder");
        Intrinsics.checkNotNullParameter(bottomNavigationViewHolder, "bottomNavigationViewHolder");
        Intrinsics.checkNotNullParameter(commandFactory, "commandFactory");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Intrinsics.checkNotNullParameter(toolController, "toolController");
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(internalMemoryPath, "internalMemoryPath");
        Intrinsics.checkNotNullParameter(commandSerializer, "commandSerializer");
        this.fileActivity = activity;
        this.view = view;
        this.model = model;
        this.workspace = workspace;
        this.navigator = navigator;
        this.interactor = interactor;
        this.topBarViewHolder = topBarViewHolder;
        this.bottomBarViewHolder = bottomBarViewHolder;
        this.drawerLayoutViewHolder = drawerLayoutViewHolder;
        this.bottomNavigationViewHolder = bottomNavigationViewHolder;
        this.commandFactory = commandFactory;
        this.commandManager = commandManager;
        this.toolController = toolController;
        this.sharedPreferences = sharedPreferences;
        this.idlingResource = idlingResource;
        this.context = context;
        this.internalMemoryPath = internalMemoryPath;
        this.commandSerializer = commandSerializer;
        this.clippingToolPaint = new Paint();
    }

    @Override // org.catrobat.paintroid.iotasks.CreateFile.CreateFileCallback
    public Activity getFileActivity() {
        return this.fileActivity;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public Context getContext() {
        return this.context;
    }

    private final boolean isImageUnchanged() {
        return !this.commandManager.isUndoAvailable();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public Bitmap getBitmap() {
        return this.workspace.getBitmapOfAllLayers();
    }

    @Override // org.catrobat.paintroid.iotasks.SaveImage.SaveImageCallback, org.catrobat.paintroid.iotasks.LoadImage.LoadImageCallback, org.catrobat.paintroid.iotasks.CreateFile.CreateFileCallback
    public boolean isFinishing() {
        return this.view.getFinishing();
    }

    @Override // org.catrobat.paintroid.iotasks.SaveImage.SaveImageCallback, org.catrobat.paintroid.iotasks.LoadImage.LoadImageCallback
    public ContentResolver getContentResolver() {
        return this.view.getMyContentResolver();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public int getImageNumber() {
        if (this.sharedPreferences.getPreferenceImageNumber() == 0) {
            countUpImageNumber();
        }
        return this.sharedPreferences.getPreferenceImageNumber();
    }

    public final boolean getClippingToolInUseAndUndoRedoClicked() {
        return this.clippingToolInUseAndUndoRedoClicked;
    }

    public final void setClippingToolInUseAndUndoRedoClicked(boolean z) {
        this.clippingToolInUseAndUndoRedoClicked = z;
    }

    public final Paint getClippingToolPaint() {
        return this.clippingToolPaint;
    }

    public final void setClippingToolPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "<set-?>");
        this.clippingToolPaint = paint;
    }

    public final boolean getToolOptionsViewWasShown() {
        return this.toolOptionsViewWasShown;
    }

    public final void setToolOptionsViewWasShown(boolean z) {
        this.toolOptionsViewWasShown = z;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void replaceImageClicked() {
        checkIfClippingToolNeedsAdjustment();
        switchBetweenVersions(6, false);
        setFirstCheckBoxInLayerMenu();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void addImageToCurrentLayerClicked() {
        checkIfClippingToolNeedsAdjustment();
        setTool(ToolType.IMPORTPNG);
        switchBetweenVersions(7);
    }

    private final void setFirstCheckBoxInLayerMenu() {
        LayerContracts.LayerViewHolder viewHolderAt;
        LayerAdapter layerAdapter = this.layerAdapter;
        if (layerAdapter == null || (viewHolderAt = layerAdapter.getViewHolderAt(0)) == null) {
            return;
        }
        viewHolderAt.setLayerVisibilityCheckbox(true);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void saveBeforeLoadImage() {
        this.navigator.showSaveImageInformationDialogWhenStandalone(3, getImageNumber(), false);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void loadNewImage() {
        checkIfClippingToolNeedsAdjustment();
        this.navigator.startLoadImageActivity(2);
        setFirstCheckBoxInLayerMenu();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void newImageClicked() {
        checkIfClippingToolNeedsAdjustment();
        if (isImageUnchanged() || this.model.getIsSaved()) {
            onNewImage();
            setFirstCheckBoxInLayerMenu();
        } else {
            this.navigator.showSaveBeforeNewImageDialog();
            setFirstCheckBoxInLayerMenu();
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void saveBeforeNewImage() {
        checkIfClippingToolNeedsAdjustment();
        this.navigator.showSaveImageInformationDialogWhenStandalone(4, getImageNumber(), false);
    }

    private final void showSecurityQuestionBeforeExit() {
        if ((isImageUnchanged() || this.model.getIsSaved()) && (!this.model.getIsOpenedFromCatroid() || !this.wasImageLoaded)) {
            finishActivity();
        } else if (this.model.getIsOpenedFromCatroid()) {
            saveBeforeFinish();
        } else {
            this.navigator.showSaveBeforeFinishDialog();
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void finishActivity() {
        this.navigator.finishActivity();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void saveBeforeFinish() {
        checkIfClippingToolNeedsAdjustment();
        this.navigator.showSaveImageInformationDialogWhenStandalone(5, getImageNumber(), false);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void saveCopyClicked(boolean isExport) {
        this.navigator.showSaveImageInformationDialogWhenStandalone(2, getImageNumber(), isExport);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void saveImageClicked() {
        this.navigator.showSaveImageInformationDialogWhenStandalone(1, getImageNumber(), false);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void shareImageClicked() {
        checkIfClippingToolNeedsAdjustment();
        this.view.refreshDrawingSurface();
        this.navigator.startShareImageActivity(this.workspace.getBitmapOfAllLayers());
    }

    private final void showLikeUsDialogIfFirstTimeSave() {
        if (this.sharedPreferences.getPreferenceLikeUsDialogValue() || this.model.getIsOpenedFromCatroid()) {
            return;
        }
        this.navigator.showLikeUsDialog();
        this.sharedPreferences.setPreferenceLikeUsDialogValue();
    }

    private final void countUpImageNumber() {
        this.sharedPreferences.setPreferenceImageNumber(this.sharedPreferences.getPreferenceImageNumber() + 1);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void enterHideButtonsClicked() {
        this.model.setFullscreen(true);
        enterHideButtons();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void exitHideButtonsClicked() {
        this.model.setFullscreen(false);
        exitHideButtons();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void backToPocketCodeClicked() {
        showSecurityQuestionBeforeExit();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showHelpClicked() {
        this.navigator.startWelcomeActivity(3);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showAboutClicked() {
        this.navigator.showAboutDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showZoomWindowSettingsClicked(UserPreferences sharedPreferences) {
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        this.navigator.showZoomWindowSettingsDialog(sharedPreferences);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showAdvancedSettingsClicked() {
        this.navigator.showAdvancedSettingsDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showRateUsDialog() {
        this.navigator.showRateUsDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showFeedbackDialog() {
        this.navigator.showFeedbackDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showOverwriteDialog(int permissionCode, boolean isExport) {
        this.navigator.showOverwriteDialog(permissionCode, isExport);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showPngInformationDialog() {
        this.navigator.showPngInformationDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showJpgInformationDialog() {
        this.navigator.showJpgInformationDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showOraInformationDialog() {
        this.navigator.showOraInformationDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showCatrobatInformationDialog() {
        this.navigator.showCatrobatInformationDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void sendFeedback() {
        this.navigator.sendFeedback();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void onNewImage() {
        DisplayMetrics displayMetrics = this.view.getDisplayMetrics();
        this.resetPerspectiveAfterNextCommand = true;
        this.model.setSavedPictureUri(null);
        FileIO fileIO = FileIO.INSTANCE;
        FileIO.filename = Constants.TMP_IMAGE_FILE_NAME;
        FileIO fileIO2 = FileIO.INSTANCE;
        FileIO.compressFormat = Bitmap.CompressFormat.PNG;
        FileIO fileIO3 = FileIO.INSTANCE;
        FileIO.fileType = FileIO.FileType.PNG;
        FileIO.INSTANCE.deleteTempFile(this.internalMemoryPath);
        this.commandManager.setInitialStateCommand(this.commandFactory.createInitCommand(displayMetrics.widthPixels, displayMetrics.heightPixels));
        this.commandManager.reset();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void discardImageClicked() {
        this.commandManager.addCommand(this.commandFactory.createResetCommand());
    }

    public final void switchBetweenVersions(int requestCode) {
        switchBetweenVersions(requestCode, false);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void switchBetweenVersions(int requestCode, boolean isExport) {
        this.isExport = isExport;
        if (this.model.getIsOpenedFromCatroid()) {
            FileIO fileIO = FileIO.INSTANCE;
            FileIO.storeImageUri = this.model.getSavedPictureUri();
        }
        if (this.navigator.isSdkAboveOrEqualM()) {
            askForReadAndWriteExternalStoragePermission(requestCode);
            if (requestCode == 1 || requestCode == 2 || requestCode == 3 || requestCode == 4 || requestCode == 5) {
                checkForDefaultFilename();
                return;
            }
            return;
        }
        if (requestCode == 6) {
            if (isImageUnchanged() || this.model.getIsSaved()) {
                this.navigator.startLoadImageActivity(2);
                setFirstCheckBoxInLayerMenu();
                return;
            } else {
                this.navigator.showSaveBeforeLoadImageDialog();
                setFirstCheckBoxInLayerMenu();
                return;
            }
        }
        askForReadAndWriteExternalStoragePermission(requestCode);
    }

    private final void askForReadAndWriteExternalStoragePermission(int requestCode) {
        if (this.model.getIsOpenedFromCatroid() && requestCode == 5) {
            handleRequestPermissionsResult(requestCode, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, new int[]{0});
            return;
        }
        if (this.navigator.isSdkAboveOrEqualT()) {
            if (!this.navigator.doIHavePermission("android.permission.READ_MEDIA_IMAGES")) {
                this.navigator.askForPermission(new String[]{"android.permission.READ_MEDIA_IMAGES"}, requestCode);
                return;
            } else {
                handleRequestPermissionsResult(requestCode, new String[]{"android.permission.READ_MEDIA_IMAGES"}, new int[]{0});
                return;
            }
        }
        if (this.navigator.isSdkAboveOrEqualQ()) {
            if (!this.navigator.doIHavePermission("android.permission.READ_EXTERNAL_STORAGE")) {
                this.navigator.askForPermission(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, requestCode);
                return;
            } else {
                handleRequestPermissionsResult(requestCode, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, new int[]{0});
                return;
            }
        }
        if (this.navigator.isSdkAboveOrEqualM() && !this.navigator.doIHavePermission("android.permission.WRITE_EXTERNAL_STORAGE")) {
            this.navigator.askForPermission(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, requestCode);
        } else {
            handleRequestPermissionsResult(requestCode, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, new int[]{0});
        }
    }

    private final void checkForDefaultFilename() {
        if (Intrinsics.areEqual(FileIO.filename, Intrinsics.stringPlus(Constants.TMP_IMAGE_FILE_NAME, Integer.valueOf(getImageNumber())))) {
            countUpImageNumber();
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        Uri data2 = data == null ? null : data.getData();
        if (requestCode == 1) {
            if (resultCode != -1) {
                return;
            }
            setTool(ToolType.IMPORTPNG);
            this.toolController.switchTool(ToolType.IMPORTPNG);
            this.interactor.loadFile(this, 2, data2, getContext(), false, this.commandSerializer);
            return;
        }
        if (requestCode == 2) {
            if (resultCode != -1) {
                return;
            }
            this.interactor.loadFile(this, 1, data2, getContext(), false, this.commandSerializer);
        } else if (requestCode != 3) {
            this.view.superHandleActivityResult(requestCode, resultCode, data);
        } else if (resultCode == 10) {
            this.navigator.showToast(R.string.pocketpaint_intro_split_screen_not_supported, 1);
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        if (permissions.length == 1 && (Intrinsics.areEqual(permissions[0], "android.permission.READ_EXTERNAL_STORAGE") || Intrinsics.areEqual(permissions[0], "android.permission.WRITE_EXTERNAL_STORAGE") || Intrinsics.areEqual(permissions[0], "android.permission.READ_MEDIA_IMAGES"))) {
            if (!(grantResults.length == 0) && grantResults[0] == 0) {
                switch (requestCode) {
                    case 1:
                        saveImageConfirmClicked(1, FileIO.storeImageUri);
                        checkForDefaultFilename();
                        showLikeUsDialogIfFirstTimeSave();
                        break;
                    case 2:
                        saveCopyConfirmClicked(1, FileIO.storeImageUri);
                        checkForDefaultFilename();
                        break;
                    case 3:
                        saveImageConfirmClicked(3, FileIO.storeImageUri);
                        checkForDefaultFilename();
                        break;
                    case 4:
                        saveImageConfirmClicked(2, FileIO.storeImageUri);
                        checkForDefaultFilename();
                        break;
                    case 5:
                        saveImageConfirmClicked(4, FileIO.storeImageUri);
                        checkForDefaultFilename();
                        break;
                    case 6:
                        if (isImageUnchanged() || this.model.getIsSaved()) {
                            this.navigator.startLoadImageActivity(2);
                        } else {
                            this.navigator.showSaveBeforeLoadImageDialog();
                        }
                        break;
                    case 7:
                        this.navigator.startImportImageActivity(1);
                        break;
                    default:
                        this.view.superHandleRequestPermissionsResult(requestCode, permissions, grantResults);
                        break;
                }
            }
            if (this.navigator.isPermissionPermanentlyDenied(permissions)) {
                this.navigator.showRequestPermanentlyDeniedPermissionRationaleDialog();
                return;
            } else {
                this.navigator.showRequestPermissionRationaleDialog(PermissionInfoDialog.PermissionType.EXTERNAL_STORAGE, permissions, requestCode);
                return;
            }
        }
        this.view.superHandleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void onBackPressed() {
        if (this.drawerLayoutViewHolder.isDrawerOpen(8388611)) {
            this.drawerLayoutViewHolder.closeDrawer(8388611, true);
            return;
        }
        if (this.drawerLayoutViewHolder.isDrawerOpen(GravityCompat.END)) {
            this.drawerLayoutViewHolder.closeDrawer(GravityCompat.END, true);
            return;
        }
        if (this.model.getIsFullscreen()) {
            exitHideButtonsClicked();
            return;
        }
        if (!this.toolController.isDefaultTool()) {
            Tool currentTool = this.toolController.getCurrentTool();
            if ((currentTool == null ? null : currentTool.getToolType()) == ToolType.CLIP) {
                this.toolController.adjustClippingToolOnBackPressed(true);
            }
            switchTool(ToolType.BRUSH);
            return;
        }
        showSecurityQuestionBeforeExit();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void saveImageConfirmClicked(int requestCode, Uri uri) {
        checkIfClippingToolNeedsAdjustment();
        this.view.refreshDrawingSurface();
        this.interactor.saveImage(this, requestCode, this.workspace.getLayerModel(), this.commandSerializer, uri, getContext());
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void saveCopyConfirmClicked(int requestCode, Uri uri) {
        checkIfClippingToolNeedsAdjustment();
        this.view.refreshDrawingSurface();
        this.interactor.saveCopy(this, requestCode, this.workspace.getLayerModel(), this.commandSerializer, uri, getContext());
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void undoClicked() {
        this.idlingResource.increment();
        if (this.view.isKeyboardShown()) {
            this.view.hideKeyboard();
        } else {
            if (!(this.toolController.getCurrentTool() instanceof EraserTool) && (this.commandManager.isLastColorCommandOnTop() || this.commandManager.getColorCommandCount() == 0)) {
                Tool currentTool = this.toolController.getCurrentTool();
                if (currentTool != null) {
                    Tool.DefaultImpls.changePaintColor$default(currentTool, ViewCompat.MEASURED_STATE_MASK, false, 2, null);
                }
                setBottomNavigationColor(ViewCompat.MEASURED_STATE_MASK);
            }
            if (this.toolController.getCurrentTool() instanceof ClippingTool) {
                Tool currentTool2 = this.toolController.getCurrentTool();
                if (currentTool2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
                }
                this.clippingToolPaint = ((ClippingTool) currentTool2).getDrawPaint();
                this.commandManager.undo();
                this.clippingToolInUseAndUndoRedoClicked = true;
            } else if (this.toolController.getCurrentTool() instanceof LineTool) {
                Tool currentTool3 = this.toolController.getCurrentTool();
                if (currentTool3 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.LineTool");
                }
                ((LineTool) currentTool3).undoChangePaintColor(ViewCompat.MEASURED_STATE_MASK, false);
            } else if (this.toolController.getCurrentTool() instanceof EraserTool) {
                this.commandManager.undoIgnoringColorChanges();
            } else {
                this.commandManager.undo();
            }
        }
        this.idlingResource.decrement();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void redoClicked() {
        this.idlingResource.increment();
        if (this.view.isKeyboardShown()) {
            this.view.hideKeyboard();
        } else if (this.toolController.getCurrentTool() instanceof LineTool) {
            Tool currentTool = this.toolController.getCurrentTool();
            if (currentTool == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.LineTool");
            }
            ((LineTool) currentTool).redoLineTool();
        } else {
            this.commandManager.redo();
            if (this.toolController.getCurrentTool() instanceof ClippingTool) {
                this.clippingToolInUseAndUndoRedoClicked = true;
            }
        }
        this.idlingResource.decrement();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showColorPickerClicked() {
        this.navigator.showColorPickerDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showLayerMenuClicked() {
        int itemCount;
        this.idlingResource.increment();
        LayerAdapter layerAdapter = this.layerAdapter;
        if (layerAdapter != null && (itemCount = layerAdapter.getItemCount()) > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                LayerContracts.LayerViewHolder viewHolderAt = layerAdapter.getViewHolderAt(i);
                if (viewHolderAt != null) {
                    LayerContracts.Layer layerItem = layerAdapter.getPresenter().getLayerItem(i);
                    if (viewHolderAt.getCurrentBitmap() != null && layerItem != null) {
                        viewHolderAt.updateImageView(layerItem);
                    }
                }
                if (i2 >= itemCount) {
                    break;
                } else {
                    i = i2;
                }
            }
        }
        checkIfClippingToolNeedsAdjustment();
        this.drawerLayoutViewHolder.openDrawer(GravityCompat.END);
        this.idlingResource.decrement();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void onCommandPostExecute() {
        this.navigator.dismissIndeterminateProgressDialog();
        if (this.resetPerspectiveAfterNextCommand) {
            this.resetPerspectiveAfterNextCommand = false;
            this.workspace.resetPerspective();
        }
        this.model.setSaved(false);
        if (this.clippingToolInUseAndUndoRedoClicked) {
            adjustClippingToolPostCommandExecute();
        }
        this.toolController.resetToolInternalState();
        this.view.refreshDrawingSurface();
        refreshTopBarButtons();
    }

    public final void adjustClippingToolPostCommandExecute() {
        Tool currentTool = this.toolController.getCurrentTool();
        if (currentTool == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
        }
        ClippingTool clippingTool = (ClippingTool) currentTool;
        if (clippingTool.getAreaClosed()) {
            this.commandManager.popFirstCommandInRedo();
        }
        clippingTool.setAreaClosed(false);
        clippingTool.pathToDraw.rewind();
        clippingTool.getPointArray().clear();
        clippingTool.setInitialEventCoordinate(null);
        clippingTool.previousEventCoordinate = null;
        Tool.DefaultImpls.changePaintColor$default(clippingTool, this.clippingToolPaint.getColor(), false, 2, null);
        clippingTool.getMainActivity().getBottomNavigationViewHolder().setColorButtonColor(this.clippingToolPaint.getColor());
        Tool currentTool2 = this.toolController.getCurrentTool();
        if (currentTool2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
        }
        ((ClippingTool) currentTool2).setWasRecentlyApplied(true);
        this.clippingToolInUseAndUndoRedoClicked = false;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void setBottomNavigationColor(int color) {
        this.bottomNavigationViewHolder.setColorButtonColor(color);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void initializeFromCleanState(String extraPicturePath, String extraPictureName) {
        this.model.setOpenedFromCatroid(extraPicturePath != null);
        this.wasImageLoaded = false;
        if (extraPicturePath != null) {
            File file = new File(extraPicturePath);
            if (file.exists()) {
                this.model.setSavedPictureUri(this.view.getUriFromFile(file));
                this.interactor.loadFile(this, 3, this.model.getSavedPictureUri(), getContext(), false, this.commandSerializer);
                return;
            } else {
                if (extraPictureName != null) {
                    this.interactor.createFile(this, 1, extraPictureName);
                    return;
                }
                return;
            }
        }
        this.toolController.resetToolInternalStateOnImageLoaded();
        this.model.setSavedPictureUri(null);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void finishInitialize() {
        refreshTopBarButtons();
        Integer toolColor = this.toolController.getToolColor();
        if (toolColor != null) {
            this.bottomNavigationViewHolder.setColorButtonColor(toolColor.intValue());
        }
        this.bottomNavigationViewHolder.showCurrentTool(this.toolController.getToolType());
        if (this.model.getIsFullscreen()) {
            enterHideButtons();
        } else {
            exitHideButtons();
        }
        this.view.initializeActionBar(this.model.getIsOpenedFromCatroid());
        if (this.commandManager.isBusy()) {
            this.navigator.showIndeterminateProgressDialog();
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void removeMoreOptionsItems(Menu menu) {
        if (this.model.getIsOpenedFromCatroid()) {
            this.topBarViewHolder.removeStandaloneMenuItems(menu);
            this.topBarViewHolder.hideTitleIfNotStandalone();
        } else {
            this.topBarViewHolder.removeCatroidMenuItems(menu);
        }
    }

    private final void exitHideButtons() {
        this.view.exitHideButtons();
        this.topBarViewHolder.show();
        this.bottomNavigationViewHolder.show();
        this.toolController.enableToolOptionsView();
        this.toolController.enableHideOption();
        if (this.toolOptionsViewWasShown) {
            this.toolController.showToolOptionsView();
            this.toolOptionsViewWasShown = false;
        }
    }

    private final void enterHideButtons() {
        if (this.toolController.toolOptionsViewVisible()) {
            this.toolOptionsViewWasShown = true;
        }
        this.view.hideKeyboard();
        this.view.enterHideButtons();
        this.topBarViewHolder.hide();
        this.bottomBarViewHolder.hide();
        this.bottomNavigationViewHolder.hide();
        this.toolController.disableToolOptionsView();
        this.toolController.disableHideOption();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void restoreState(boolean isFullscreen, boolean isSaved, boolean isOpenedFromCatroid, boolean isOpenedFromFormulaEditorInCatroid, Uri savedPictureUri, Uri cameraImageUri) {
        this.model.setFullscreen(isFullscreen);
        this.model.setSaved(isSaved);
        this.model.setOpenedFromCatroid(isOpenedFromCatroid);
        this.model.setOpenedFromFormulaEditorInCatroid(isOpenedFromFormulaEditorInCatroid);
        this.model.setSavedPictureUri(savedPictureUri);
        this.model.setCameraImageUri(cameraImageUri);
        this.navigator.restoreFragmentListeners();
        this.toolController.resetToolInternalStateOnImageLoaded();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void onCreateTool() {
        this.toolController.createTool();
    }

    private final void refreshTopBarButtons() {
        if (this.commandManager.isUndoAvailable()) {
            this.topBarViewHolder.enableUndoButton();
        } else {
            this.topBarViewHolder.disableUndoButton();
        }
        if (this.commandManager.isRedoAvailable()) {
            this.topBarViewHolder.enableRedoButton();
        } else {
            this.topBarViewHolder.disableRedoButton();
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void toolClicked(ToolType toolType) {
        Intrinsics.checkNotNullParameter(toolType, "toolType");
        this.idlingResource.increment();
        this.bottomBarViewHolder.hide();
        if (this.toolController.getToolType() == toolType && this.toolController.hasToolOptionsView()) {
            this.toolController.toggleToolOptionsView();
        } else {
            checkForImplicitToolApplication();
            switchTool(toolType);
        }
        this.idlingResource.decrement();
    }

    private final void checkForImplicitToolApplication() {
        Tool currentTool = this.toolController.getCurrentTool();
        ToolType toolType = currentTool == null ? null : currentTool.getToolType();
        if (CollectionsKt.contains(this.toolController.getToolList(), toolType)) {
            if (currentTool == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.BaseToolWithShape");
            }
            ((BaseToolWithShape) currentTool).onClickOnButton();
        } else if (toolType == ToolType.CLIP) {
            if (currentTool == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
            }
            ((ClippingTool) currentTool).onClickOnButton();
        }
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [org.catrobat.paintroid.presenter.MainActivityPresenter$switchTool$1] */
    private final void switchTool(final ToolType type) {
        this.navigator.setMaskFilterToNull();
        this.view.hideKeyboard();
        HashSet<ToolType> toolList = this.toolController.getToolList();
        Tool currentTool = this.toolController.getCurrentTool();
        this.downTimer = new CountDownTimer(CollectionsKt.contains(toolList, currentTool == null ? null : currentTool.getToolType()) ? 250L : 0L, 83L) { // from class: org.catrobat.paintroid.presenter.MainActivityPresenter.switchTool.1
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                MainActivityPresenter.this.workspace.invalidate();
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                CountDownTimer countDownTimer = MainActivityPresenter.this.downTimer;
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                MainActivityPresenter.this.workspace.invalidate();
                MainActivityPresenter.this.setTool(type);
                MainActivityPresenter.this.toolController.switchTool(type);
                if (type == ToolType.IMPORTPNG) {
                    MainActivityPresenter.this.showImportDialog();
                } else if (type == ToolType.CLIP) {
                    Tool currentTool2 = MainActivityPresenter.this.toolController.getCurrentTool();
                    if (currentTool2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
                    }
                    ((ClippingTool) currentTool2).copyBitmapOfCurrentLayer();
                }
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setTool(ToolType toolType) {
        this.idlingResource.increment();
        this.bottomBarViewHolder.hide();
        this.bottomNavigationViewHolder.showCurrentTool(toolType);
        this.navigator.showToolChangeToast(this.topBarViewHolder.getHeight(), toolType.getNameResource());
        this.idlingResource.decrement();
    }

    @Override // org.catrobat.paintroid.iotasks.CreateFile.CreateFileCallback
    public void onCreateFilePostExecute(int requestCode, File file) {
        if (file == null) {
            this.navigator.showSaveErrorDialog();
        } else {
            if (requestCode == 1) {
                this.model.setSavedPictureUri(this.view.getUriFromFile(file));
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void loadScaledImage(Uri uri, int requestCode) {
        if (requestCode != 1) {
            if (requestCode == 2) {
                setTool(ToolType.IMPORTPNG);
                this.toolController.switchTool(ToolType.IMPORTPNG);
                this.interactor.loadFile(this, 2, uri, getContext(), true, this.commandSerializer);
                return;
            } else if (requestCode != 3) {
                Log.e(MainActivity.TAG, "wrong request code for loading pictures");
                return;
            }
        }
        this.interactor.loadFile(this, 1, uri, getContext(), true, this.commandSerializer);
    }

    @Override // org.catrobat.paintroid.iotasks.LoadImage.LoadImageCallback
    public void onLoadImagePostExecute(int requestCode, Uri uri, BitmapReturnValue result) {
        String fileName;
        String fileName2;
        if (result == null) {
            this.navigator.showLoadErrorDialog();
            return;
        }
        if (result.model != null) {
            this.commandManager.loadCommandsCatrobatImage(result.model);
            this.resetPerspectiveAfterNextCommand = true;
            setColorHistoryAfterLoadImage(result.colorHistory);
            FileIO fileIO = FileIO.INSTANCE;
            FileIO.fileType = FileIO.FileType.CATROBAT;
            if (uri == null || (fileName2 = getFileName(uri)) == null) {
                return;
            }
            FileIO fileIO2 = FileIO.INSTANCE;
            String strSubstring = fileName2.substring(0, fileName2.length() - FileIO.fileType.toExtension().length());
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            FileIO.filename = strSubstring;
            return;
        }
        if (result.toBeScaled) {
            this.navigator.showScaleImageRequestDialog(uri, requestCode);
            return;
        }
        if (requestCode != 1) {
            if (requestCode == 2) {
                if (this.toolController.getToolType() == ToolType.IMPORTPNG) {
                    this.toolController.setBitmapFromSource(result.bitmap);
                    return;
                } else {
                    Log.e(MainActivity.TAG, "importPngToFloatingBox: Current tool is no ImportTool as required");
                    return;
                }
            }
            if (requestCode == 3) {
                this.resetPerspectiveAfterNextCommand = true;
                Bitmap bitmap = result.bitmap;
                if (bitmap != null) {
                    this.commandManager.setInitialStateCommand(this.commandFactory.createInitCommand(bitmap));
                }
                this.commandManager.reset();
                this.model.setSavedPictureUri(uri);
                this.model.setCameraImageUri(null);
                return;
            }
            throw new IllegalArgumentException();
        }
        this.resetPerspectiveAfterNextCommand = true;
        if (result.bitmap != null) {
            Bitmap bitmap2 = result.bitmap;
            if (bitmap2 != null) {
                this.commandManager.setInitialStateCommand(this.commandFactory.createInitCommand(bitmap2));
            }
        } else {
            List<? extends LayerContracts.Layer> list = result.layerList;
            if (list != null) {
                this.commandManager.setInitialStateCommand(this.commandFactory.createInitCommand(list));
            }
        }
        this.commandManager.reset();
        if (!this.model.getIsOpenedFromCatroid()) {
            this.model.setSavedPictureUri(null);
        }
        this.model.setCameraImageUri(null);
        this.wasImageLoaded = true;
        if (uri == null || (fileName = getFileName(uri)) == null) {
            return;
        }
        if (StringsKt.endsWith$default(fileName, FileIO.FileType.JPG.getValue(), false, 2, (Object) null) || StringsKt.endsWith$default(fileName, "jpeg", false, 2, (Object) null)) {
            FileIO fileIO3 = FileIO.INSTANCE;
            FileIO.compressFormat = Bitmap.CompressFormat.JPEG;
            FileIO fileIO4 = FileIO.INSTANCE;
            FileIO.fileType = FileIO.FileType.JPG;
        } else if (StringsKt.endsWith$default(fileName, FileIO.FileType.PNG.getValue(), false, 2, (Object) null)) {
            FileIO fileIO5 = FileIO.INSTANCE;
            FileIO.compressFormat = Bitmap.CompressFormat.PNG;
            FileIO fileIO6 = FileIO.INSTANCE;
            FileIO.fileType = FileIO.FileType.PNG;
        } else {
            FileIO fileIO7 = FileIO.INSTANCE;
            FileIO.fileType = FileIO.FileType.ORA;
        }
        FileIO fileIO8 = FileIO.INSTANCE;
        String strSubstring2 = fileName.substring(0, fileName.length() - FileIO.fileType.toExtension().length());
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        FileIO.filename = strSubstring2;
    }

    @Override // org.catrobat.paintroid.iotasks.SaveImage.SaveImageCallback
    public void onSaveImagePreExecute(int requestCode) {
        this.view.showContentLoadingProgressBar();
    }

    @Override // org.catrobat.paintroid.iotasks.SaveImage.SaveImageCallback
    public void onSaveImagePostExecute(int requestCode, Uri uri, boolean saveAsCopy) {
        this.view.hideContentLoadingProgressBar();
        if (uri == null) {
            this.navigator.showSaveErrorDialog();
            return;
        }
        if (saveAsCopy) {
            if (this.model.getIsOpenedFromCatroid() && !this.isExport) {
                this.navigator.showToast(R.string.copy, 1);
            } else {
                String string = getContext().getString(R.string.copy_to);
                Activity fileActivity = getFileActivity();
                if (fileActivity != null) {
                    string = Intrinsics.stringPlus(string, INSTANCE.getPathFromUri(fileActivity, uri));
                }
                this.navigator.showToast(string != null ? string : "null", 1);
            }
        } else {
            if (this.model.getIsOpenedFromCatroid() && !this.isExport) {
                this.navigator.showToast(R.string.saved, 1);
            } else {
                String string2 = getContext().getString(R.string.saved_to);
                Activity fileActivity2 = getFileActivity();
                if (fileActivity2 != null) {
                    string2 = Intrinsics.stringPlus(string2, INSTANCE.getPathFromUri(fileActivity2, uri));
                }
                this.navigator.showToast(string2 != null ? string2 : "null", 1);
            }
            this.model.setSavedPictureUri(uri);
            this.model.setSaved(true);
        }
        if (!this.model.getIsOpenedFromCatroid() || saveAsCopy) {
            this.navigator.broadcastAddPictureToGallery(uri);
        }
        if (requestCode != 1) {
            if (requestCode == 2) {
                onNewImage();
                return;
            }
            if (requestCode == 3) {
                this.navigator.startLoadImageActivity(2);
            } else {
                if (requestCode == 4) {
                    if (this.model.getIsOpenedFromCatroid()) {
                        this.navigator.returnToPocketCode(uri.getPath());
                        return;
                    } else {
                        this.navigator.finishActivity();
                        return;
                    }
                }
                throw new IllegalArgumentException();
            }
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void actionToolsClicked() {
        LayerContracts.Presenter presenter;
        if (this.toolController.toolOptionsViewVisible()) {
            this.toolController.hideToolOptionsView();
        }
        if (this.bottomBarViewHolder.isVisible()) {
            this.bottomBarViewHolder.hide();
            return;
        }
        LayerAdapter layerAdapter = this.layerAdapter;
        LayerContracts.Layer layerItem = null;
        if (layerAdapter != null && (presenter = layerAdapter.getPresenter()) != null) {
            layerItem = presenter.getLayerItem(this.workspace.getCurrentLayerIndex());
        }
        if (layerItem != null && !layerItem.getIsVisible()) {
            this.navigator.showToast(R.string.no_tools_on_hidden_layer, 0);
        } else {
            this.bottomBarViewHolder.show();
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void actionCurrentToolClicked() {
        if (this.toolController.getToolType() == ToolType.IMPORTPNG) {
            showImportDialog();
            return;
        }
        if (this.bottomBarViewHolder.isVisible()) {
            this.bottomBarViewHolder.hide();
        }
        if (this.toolController.toolOptionsViewVisible()) {
            this.toolController.hideToolOptionsView();
        } else if (this.toolController.hasToolOptionsView()) {
            this.toolController.showToolOptionsView();
        }
    }

    private final String getFileName(Uri uri) {
        ContentResolver contentResolver;
        if (!Intrinsics.areEqual(uri.getScheme(), FirebaseAnalytics.Param.CONTENT)) {
            return null;
        }
        Activity fileActivity = getFileActivity();
        Cursor cursorQuery = (fileActivity == null || (contentResolver = fileActivity.getContentResolver()) == null) ? null : contentResolver.query(uri, null, null, null, null);
        if (cursorQuery == null) {
            return null;
        }
        Cursor cursor = cursorQuery;
        try {
            Cursor cursor2 = cursor;
            String string = cursor2.moveToFirst() ? cursor2.getString(cursor2.getColumnIndexOrThrow("_display_name")) : null;
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            return string;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(cursor, th);
                throw th2;
            }
        }
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void rateUsClicked() {
        this.navigator.rateUsClicked();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void setLayerAdapter(LayerAdapter layerAdapter) {
        Intrinsics.checkNotNullParameter(layerAdapter, "layerAdapter");
        this.layerAdapter = layerAdapter;
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void importFromGalleryClicked() {
        switchBetweenVersions(7);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void showImportDialog() {
        this.navigator.showImageImportDialog();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void importStickersClicked() {
        this.navigator.showCatroidMediaGallery();
        if (INSTANCE.checkForInternet(getContext())) {
            return;
        }
        Toast.makeText(getContext(), getContext().getString(R.string.no_connection_sticker), 1).show();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void bitmapLoadedFromSource(Bitmap loadedImage) {
        Intrinsics.checkNotNullParameter(loadedImage, "loadedImage");
        this.toolController.setBitmapFromSource(loadedImage);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void setAntialiasingOnOkClicked() {
        this.navigator.setAntialiasingOnToolPaint();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void saveNewTemporaryImage() {
        FileIO.INSTANCE.saveTemporaryPictureFile(this.internalMemoryPath, this.commandSerializer);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public WorkspaceReturnValue openTemporaryFile() {
        return FileIO.INSTANCE.openTemporaryPictureFile(this.commandSerializer);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public boolean checkForTemporaryFile() {
        return FileIO.INSTANCE.checkForTemporaryFile(this.internalMemoryPath);
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Presenter
    public void setColorHistoryAfterLoadImage(ColorHistory colorHistory) {
        if (colorHistory == null) {
            colorHistory = new ColorHistory();
        }
        this.model.setColorHistory(colorHistory);
        int color = new DefaultToolPaint(getContext()).getColor();
        if (!colorHistory.getColors().isEmpty()) {
            color = ((Number) CollectionsKt.last((List) colorHistory.getColors())).intValue();
        }
        Tool currentTool = this.toolController.getCurrentTool();
        if (currentTool != null) {
            Tool.DefaultImpls.changePaintColor$default(currentTool, color, false, 2, null);
        }
        setBottomNavigationColor(color);
    }

    public final void checkIfClippingToolNeedsAdjustment() {
        if (this.toolController.getCurrentTool() instanceof ClippingTool) {
            Tool currentTool = this.toolController.getCurrentTool();
            if (currentTool == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
            }
            ClippingTool clippingTool = (ClippingTool) currentTool;
            if (clippingTool.getAreaClosed()) {
                PointF initialEventCoordinate = clippingTool.getInitialEventCoordinate();
                PointF pointF = null;
                if (initialEventCoordinate != null) {
                    float f = initialEventCoordinate.x;
                    PointF initialEventCoordinate2 = clippingTool.getInitialEventCoordinate();
                    if (initialEventCoordinate2 != null) {
                        pointF = new PointF(f, initialEventCoordinate2.y);
                    }
                }
                clippingTool.handleDown(pointF);
                Tool currentTool2 = this.toolController.getCurrentTool();
                if (currentTool2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
                }
                ((ClippingTool) currentTool2).setWasRecentlyApplied(true);
                clippingTool.resetInternalState(Tool.StateChange.NEW_IMAGE_LOADED);
                return;
            }
            Tool currentTool3 = this.toolController.getCurrentTool();
            if (currentTool3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.implementation.ClippingTool");
            }
            ((ClippingTool) currentTool3).setWasRecentlyApplied(true);
            clippingTool.resetInternalState(Tool.StateChange.NEW_IMAGE_LOADED);
        }
    }

    public final void hideBottomBarViewHolder() {
        if (this.bottomBarViewHolder.isVisible()) {
            this.bottomBarViewHolder.hide();
        }
    }

    /* JADX INFO: compiled from: MainActivityPresenter.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J9\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\b2\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\rH\u0002¢\u0006\u0002\u0010\u000eJ\u0018\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/presenter/MainActivityPresenter$Companion;", "", "()V", "checkForInternet", "", "context", "Landroid/content/Context;", "getDataColumn", "", "uri", "Landroid/net/Uri;", "selection", "selectionArgs", "", "(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getPathFromUri", "isDownloadsDocument", "isExternalStorageDocument", "isGooglePhotosUri", "isMediaDocument", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final String getPathFromUri(Context context, Uri uri) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(uri, "uri");
            Uri uri2 = null;
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    Intrinsics.checkNotNullExpressionValue(docId, "docId");
                    Object[] array = StringsKt.split$default((CharSequence) docId, new String[]{":"}, false, 0, 6, (Object) null).toArray(new String[0]);
                    if (array != null) {
                        String[] strArr = (String[]) array;
                        if (StringsKt.equals("primary", strArr[0], true)) {
                            return Environment.getExternalStorageDirectory().toString() + '/' + strArr[1];
                        }
                        return "";
                    }
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                }
                if (isDownloadsDocument(uri)) {
                    String id2 = DocumentsContract.getDocumentId(uri);
                    Uri uri3 = Uri.parse("content://downloads/public_downloads");
                    Intrinsics.checkNotNullExpressionValue(id2, "id");
                    Uri uriWithAppendedId = ContentUris.withAppendedId(uri3, Long.parseLong(id2));
                    Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(\n        …g()\n                    )");
                    return getDataColumn(context, uriWithAppendedId, null, null);
                }
                if (isMediaDocument(uri)) {
                    String docId2 = DocumentsContract.getDocumentId(uri);
                    Intrinsics.checkNotNullExpressionValue(docId2, "docId");
                    Object[] array2 = StringsKt.split$default((CharSequence) docId2, new String[]{":"}, false, 0, 6, (Object) null).toArray(new String[0]);
                    if (array2 != null) {
                        String[] strArr2 = (String[]) array2;
                        String str = strArr2[0];
                        int iHashCode = str.hashCode();
                        if (iHashCode != 93166550) {
                            if (iHashCode != 100313435) {
                                if (iHashCode == 112202875 && str.equals("video")) {
                                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                                }
                            } else if (str.equals(Constants.TMP_IMAGE_FILE_NAME)) {
                                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            }
                        } else if (str.equals("audio")) {
                            uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        return getDataColumn(context, uri2, "_id=?", new String[]{strArr2[1]});
                    }
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                }
                return "";
            }
            if (StringsKt.equals(FirebaseAnalytics.Param.CONTENT, uri.getScheme(), true)) {
                if (isGooglePhotosUri(uri)) {
                    return String.valueOf(uri.getLastPathSegment());
                }
                return getDataColumn(context, uri, null, null);
            }
            if (StringsKt.equals("file", uri.getScheme(), true)) {
                return String.valueOf(uri.getPath());
            }
            return "";
        }

        /* JADX WARN: Code duplicated, block: B:26:0x005d  */
        private final String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
            if (uri == null) {
                return "";
            }
            Cursor cursorQuery = null;
            try {
                try {
                    cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
                    if (cursorQuery == null || !cursorQuery.moveToFirst()) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return "";
                    }
                    String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                    Intrinsics.checkNotNullExpressionValue(string, "cursor.getString(index)");
                    cursorQuery.close();
                    return string;
                } catch (IllegalArgumentException unused) {
                    File file = new File(context.getCacheDir(), ConstantsKt.TEMP_PICTURE_NAME);
                    FileIO.saveFileFromUri(uri, file, context);
                    String absolutePath = file.getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath, "file.absolutePath");
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return absolutePath;
                }
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean checkForInternet(Context context) {
            NetworkCapabilities networkCapabilities;
            Object systemService = context.getSystemService("connectivity");
            if (systemService == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.net.ConnectivityManager");
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
                return false;
            }
            return networkCapabilities.hasTransport(1) || networkCapabilities.hasTransport(0);
        }

        private final boolean isExternalStorageDocument(Uri uri) {
            return Intrinsics.areEqual("com.android.externalstorage.documents", uri.getAuthority());
        }

        private final boolean isDownloadsDocument(Uri uri) {
            return Intrinsics.areEqual("com.android.providers.downloads.documents", uri.getAuthority());
        }

        private final boolean isMediaDocument(Uri uri) {
            return Intrinsics.areEqual("com.android.providers.media.documents", uri.getAuthority());
        }

        private final boolean isGooglePhotosUri(Uri uri) {
            return Intrinsics.areEqual("com.google.android.apps.photos.content", uri.getAuthority());
        }
    }
}
