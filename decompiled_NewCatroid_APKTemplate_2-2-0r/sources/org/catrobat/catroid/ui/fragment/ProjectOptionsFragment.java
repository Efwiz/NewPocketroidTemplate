package org.catrobat.catroid.ui.fragment;

import android.app.ProgressDialog;
import android.content.ComponentCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import com.android.apksig.ApkSigner;
import com.badlogic.gdx.physics.bullet.collision.CollisionConstants;
import com.beust.jcommander.Parameters;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.hcaptcha.sdk.HCaptcha;
import com.hcaptcha.sdk.HCaptchaConfig;
import com.hcaptcha.sdk.HCaptchaException;
import com.hcaptcha.sdk.HCaptchaTokenResponse;
import com.hcaptcha.sdk.tasks.OnFailureListener;
import com.hcaptcha.sdk.tasks.OnSuccessListener;
import com.hivemq.client.mqtt.MqttProxyConfig;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.value.ResConfigBase;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.apkbuild.ApkToolboxManager;
import org.catrobat.catroid.apkbuild.ProjectScanner;
import org.catrobat.catroid.apkbuild.TemplateManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.databinding.FragmentProjectOptionsBinding;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.asynctask.ProjectExportTask;
import org.catrobat.catroid.io.asynctask.ProjectLoaderKt;
import org.catrobat.catroid.io.asynctask.ProjectRenamerKt;
import org.catrobat.catroid.io.asynctask.ProjectSaver;
import org.catrobat.catroid.io.asynctask.ProjectSaverKt;
import org.catrobat.catroid.merge.NewProjectNameTextWatcher;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.CommunityLoginActivity;
import org.catrobat.catroid.ui.ProjectUploadActivity;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.catrobat.catroid.ui.SettingsActivity;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;
import org.catrobat.catroid.utils.MatryoshkaManager;
import org.catrobat.catroid.utils.ProjectTrashManager;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.utils.community.CommunityTokenManager;
import org.catrobat.catroid.utils.git.Conflict;
import org.catrobat.catroid.utils.git.GitController;
import org.catrobat.catroid.utils.git.GitResult;
import org.catrobat.catroid.utils.git.MergeResult;
import org.catrobat.catroid.utils.git.TokenManager;
import org.catrobat.catroid.utils.lunoscript.baker.ProjectBaker;
import org.catrobat.catroid.utils.notifications.NotificationData;
import org.catrobat.catroid.utils.notifications.StatusBarNotificationManager;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectOptionsFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b)\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\bL\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 Ý\u00012\u00020\u0001:\u0002Ý\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020+H\u0002J\b\u0010-\u001a\u00020+H\u0002J\b\u0010.\u001a\u00020+H\u0003J\b\u0010/\u001a\u00020+H\u0002J\b\u00100\u001a\u00020+H\u0002J\b\u00101\u001a\u00020+H\u0002J&\u00102\u001a\u00020+2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00192\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\rJ(\u00109\u001a\u00020+2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00192\u0006\u0010:\u001a\u0002072\u0006\u00108\u001a\u00020\rH\u0002J\u001e\u0010;\u001a\u00020+2\u0006\u00103\u001a\u0002042\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u0019J\u001e\u0010?\u001a\u00020+2\u0006\u00103\u001a\u0002042\u0006\u0010@\u001a\u00020\r2\u0006\u0010A\u001a\u00020\u0019J\u0016\u0010B\u001a\u00020\u00192\u0006\u00103\u001a\u0002042\u0006\u0010C\u001a\u00020\u0019J\u0010\u0010D\u001a\u00020+2\u0006\u0010E\u001a\u00020FH\u0002J\u0088\u0001\u0010G\u001a\u00020+2\u0006\u0010H\u001a\u00020\r2\u0006\u0010I\u001a\u00020\r2\u0006\u0010J\u001a\u00020\r2\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\r2\u0006\u0010M\u001a\u00020N2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0006\u0010Q\u001a\u00020#2\b\u0010R\u001a\u0004\u0018\u00010\u00192\b\u0010S\u001a\u0004\u0018\u00010\r2\b\u0010T\u001a\u0004\u0018\u00010\r2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J&\u0010V\u001a\u00020+2\u0006\u0010W\u001a\u00020\r2\u0006\u0010X\u001a\u00020\r2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J\b\u0010Z\u001a\u00020+H\u0002J\b\u0010[\u001a\u00020+H\u0002J\b\u0010\\\u001a\u00020+H\u0002J\b\u0010]\u001a\u00020+H\u0002J\b\u0010^\u001a\u00020+H\u0003J\u0010\u0010_\u001a\u0004\u0018\u00010\rH\u0082@¢\u0006\u0002\u0010`J\u0010\u0010a\u001a\u00020\r2\u0006\u0010b\u001a\u00020\u0019H\u0002J\u0012\u0010c\u001a\u0004\u0018\u00010\u00192\u0006\u0010d\u001a\u00020\u0019H\u0002J\u0006\u0010e\u001a\u00020\rJ\u0006\u0010f\u001a\u00020\rJ\u0010\u0010g\u001a\u00020+2\u0006\u0010h\u001a\u00020#H\u0002J\u0010\u0010i\u001a\u00020+2\u0006\u0010h\u001a\u00020#H\u0002J\b\u0010j\u001a\u00020+H\u0002J\b\u0010k\u001a\u00020+H\u0002J\u0010\u0010l\u001a\u00020+2\u0006\u0010m\u001a\u00020\rH\u0002J\b\u0010n\u001a\u00020+H\u0002J\b\u0010o\u001a\u00020+H\u0002J\b\u0010p\u001a\u00020+H\u0002J\u0010\u0010q\u001a\u00020+2\u0006\u00108\u001a\u00020\rH\u0002J\u0010\u0010r\u001a\u00020#2\u0006\u0010I\u001a\u00020\rH\u0002J\b\u0010s\u001a\u00020+H\u0002J\"\u0010t\u001a\u00020+2\u0006\u0010u\u001a\u00020\u00042\u0006\u0010v\u001a\u00020\u00042\b\u0010w\u001a\u0004\u0018\u00010xH\u0016J$\u0010y\u001a\u00020\b2\u0006\u0010z\u001a\u00020{2\b\u0010|\u001a\u0004\u0018\u00010}2\b\u0010~\u001a\u0004\u0018\u00010\u007fH\u0016J\t\u0010\u0080\u0001\u001a\u00020+H\u0016J\t\u0010\u0081\u0001\u001a\u00020+H\u0016J\u0013\u0010\u0082\u0001\u001a\u00020+2\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0016J\t\u0010\u0085\u0001\u001a\u00020+H\u0016J\t\u0010\u0086\u0001\u001a\u00020+H\u0002J\u001c\u0010\u0087\u0001\u001a\u00020+2\u0007\u0010\u0088\u0001\u001a\u00020\b2\b\u0010~\u001a\u0004\u0018\u00010\u007fH\u0016J\u0014\u0010\u0089\u0001\u001a\u00020\r2\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\rH\u0002J9\u0010\u008b\u0001\u001a\u00020+2\u0007\u0010\u008c\u0001\u001a\u00020\u00192\u0006\u0010W\u001a\u00020\r2\u0006\u0010X\u001a\u00020\r2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0007\u0010\u008d\u0001\u001a\u00020\rH\u0002J\u0019\u0010\u008e\u0001\u001a\u00020#2\u0006\u00105\u001a\u00020\u00192\u0006\u0010A\u001a\u00020\u0019H\u0002J\u0007\u0010\u008f\u0001\u001a\u00020+J\u0007\u0010\u0090\u0001\u001a\u00020+J\u0007\u0010\u0091\u0001\u001a\u00020+J\t\u0010\u0092\u0001\u001a\u00020+H\u0002J\t\u0010\u0093\u0001\u001a\u00020+H\u0002J\t\u0010\u0094\u0001\u001a\u00020+H\u0002J\t\u0010\u0095\u0001\u001a\u00020+H\u0002J\t\u0010\u0096\u0001\u001a\u00020+H\u0002J\t\u0010\u0097\u0001\u001a\u00020+H\u0002J\t\u0010\u0098\u0001\u001a\u00020+H\u0002J\t\u0010\u0099\u0001\u001a\u00020+H\u0002J\t\u0010\u009a\u0001\u001a\u00020+H\u0002J\t\u0010\u009b\u0001\u001a\u00020+H\u0002J\t\u0010\u009c\u0001\u001a\u00020+H\u0002J\t\u0010\u009d\u0001\u001a\u00020+H\u0002J\t\u0010\u009e\u0001\u001a\u00020+H\u0002J\t\u0010\u009f\u0001\u001a\u00020+H\u0002J\t\u0010 \u0001\u001a\u00020+H\u0002J\t\u0010¡\u0001\u001a\u00020+H\u0002J\t\u0010¢\u0001\u001a\u00020+H\u0002J\t\u0010£\u0001\u001a\u00020+H\u0002J\t\u0010¤\u0001\u001a\u00020+H\u0002J\t\u0010¥\u0001\u001a\u00020+H\u0002J\t\u0010¦\u0001\u001a\u00020+H\u0002J\u001a\u0010§\u0001\u001a\u00020+2\u0007\u0010¨\u0001\u001a\u00020\u00192\u0006\u0010H\u001a\u00020\rH\u0002J\u0012\u0010©\u0001\u001a\u00020+2\u0007\u0010¨\u0001\u001a\u00020\u0019H\u0002J\t\u0010ª\u0001\u001a\u00020+H\u0002J\t\u0010«\u0001\u001a\u00020+H\u0002J\t\u0010¬\u0001\u001a\u00020+H\u0002J\t\u0010\u00ad\u0001\u001a\u00020+H\u0002J\t\u0010®\u0001\u001a\u00020+H\u0002J\t\u0010¯\u0001\u001a\u00020+H\u0002J\t\u0010°\u0001\u001a\u00020+H\u0002J\t\u0010±\u0001\u001a\u00020+H\u0002J\u0012\u0010²\u0001\u001a\u00020+2\u0007\u0010³\u0001\u001a\u00020\rH\u0002J\t\u0010´\u0001\u001a\u00020+H\u0002J\u0012\u0010µ\u0001\u001a\u00020+2\u0007\u0010¶\u0001\u001a\u00020\rH\u0002J\t\u0010·\u0001\u001a\u00020+H\u0002J\t\u0010¸\u0001\u001a\u00020+H\u0002J\u0010\u0010¹\u0001\u001a\u00020+2\u0007\u0010º\u0001\u001a\u00020\rJE\u0010»\u0001\u001a\u00020+2\u0006\u00103\u001a\u0002042\u0007\u0010¼\u0001\u001a\u00020\u00192\u0007\u0010½\u0001\u001a\u00020\u00192\u0007\u0010¾\u0001\u001a\u00020\r2\u0007\u0010¿\u0001\u001a\u00020\r2\u0007\u0010À\u0001\u001a\u00020\r2\u0007\u0010Á\u0001\u001a\u00020\rJ\u0012\u0010Â\u0001\u001a\u00020+2\u0007\u0010Ã\u0001\u001a\u000207H\u0002J\u0012\u0010Ä\u0001\u001a\u00020+2\u0007\u0010Ã\u0001\u001a\u000207H\u0002J\t\u0010Å\u0001\u001a\u00020+H\u0002J\t\u0010Æ\u0001\u001a\u00020+H\u0002J\u0018\u0010Ç\u0001\u001a\u00020+2\u0006\u0010<\u001a\u00020=2\u0007\u0010È\u0001\u001a\u00020\u0019J\t\u0010É\u0001\u001a\u00020+H\u0002J\u0012\u0010Ê\u0001\u001a\u00020+2\u0007\u0010³\u0001\u001a\u00020\rH\u0002J\u0012\u0010Ë\u0001\u001a\u00020+2\u0007\u0010¶\u0001\u001a\u00020\rH\u0002JC\u0010Ì\u0001\u001a\u00020+2\u0007\u0010¨\u0001\u001a\u00020\u00192\u0007\u0010Í\u0001\u001a\u00020\r2\u0007\u0010Î\u0001\u001a\u00020\u00042\u0007\u0010Ï\u0001\u001a\u00020\r2\u0014\u0010Ð\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020+0Ñ\u0001H\u0002J*\u0010Ò\u0001\u001a\u00020+2\u0007\u0010Ó\u0001\u001a\u00020\r2\u0016\u0010Ô\u0001\u001a\u0011\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0004\u0012\u00020+0Ñ\u0001H\u0002J\u0019\u0010Õ\u0001\u001a\u00020\u00192\u0007\u0010Ö\u0001\u001a\u00020\u00192\u0007\u0010\u008c\u0001\u001a\u00020\u0019J%\u0010×\u0001\u001a\u00020+2\u0007\u0010Ø\u0001\u001a\u00020\u00192\b\u0010Ù\u0001\u001a\u00030Ú\u00012\t\b\u0002\u0010Û\u0001\u001a\u00020\rJ'\u0010Ü\u0001\u001a\u00020+2\u0007\u0010Ø\u0001\u001a\u00020\u00192\b\u0010Ù\u0001\u001a\u00030Ú\u00012\t\b\u0002\u0010Û\u0001\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u001a\u001a\u00020\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001dR\u0010\u0010 \u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006Þ\u0001"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/ProjectOptionsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "REQUEST_CODE_SELECT_KEYSTORE", "", "_binding", "Lorg/catrobat/catroid/databinding/FragmentProjectOptionsBinding;", "activeDialogView", "Landroid/view/View;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/FragmentProjectOptionsBinding;", "buildFilename", "", "genresList", "", "gitController", "Lorg/catrobat/catroid/utils/git/GitController;", "httpClient", "Lokhttp3/OkHttpClient;", "progressDialog", "Landroidx/appcompat/app/AlertDialog;", "project", "Lorg/catrobat/catroid/content/Project;", "projectInZip", "Ljava/io/File;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "sceneName", "selectedCustomKeystoreFile", "shouldSaveOnPause", "", "tempGenAlias", "tempGenOwner", "tempGenPass", "tvSelectedKeystorePathTag", "Landroid/widget/TextView;", "zipTempDir", "addTags", "", "buildApk", "buildToExternalMemory", "buildUsingSystemFilePicker", "changeIcon", "changeOrientation", "clearVars", "copyFileToUri", "context", "Landroid/content/Context;", "sourceFile", "directoryUri", "Landroid/net/Uri;", PsiTreeChangeEvent.PROP_FILE_NAME, "copyFileToUri2", "destinationUri", "copyInputStreamToFile", "inputStream", "Ljava/io/InputStream;", "outputFile", "copyKeystoreFromAssets", "keystoreFileName", "destFile", "createApkFromTemplate", "projectZipFile", "deleteProject", ProjectDetailsFragment.SELECTED_PROJECT_KEY, "Lorg/catrobat/catroid/common/ProjectData;", "executeApkBuildProcess", "appName", "packageName", "versionName", "versionCode", ResConfigBase.NAME_orientation, ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE, "Lorg/catrobat/catroid/apkbuild/TemplateManager$TemplateRelease;", "excludedLibraries", "excludedArches", "useCustomKeystore", "keystoreFile", "keystoreAlias", "keystorePass", "allowedPermissions", "executePublicationStages", "title", MediaTrack.ROLE_DESCRIPTION, "genres", "exportBakedProject", "exportMatryoshkaForServer", "exportProject", "exportToExternalMemory", "exportUsingSystemFilePicker", "fetchCaptchaSiteKey", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProjectIconBase64", "iconFile", "getProjectIconFile", ProjectUploadActivityKt.PROJECT_DIR, "getRandomError", "getRandomMessage", "handleAspectRatioChecked", "checked", "handleCustomResolutionChecked", "handleDeleteButtonPressed", "handleGitConnect", "handleGitPublish", "commitMessage", "handleGitUpdate", "hideProgressDialog", "hidePublishPill", "insertImageMarkdown", "isValidPackageName", "moreDetails", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onPause", "onPrepareOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onResume", "onSaveProjectComplete", "onViewCreated", Promotion.ACTION_VIEW, "parseServerError", "errorJson", "performChunkedUpload", "zipFile", "captchaToken", "processProjectIcon", "projectUpload", "saveCreditsAndNotes", "saveDescription", "saveProject", "setPhysicsArea", "setProjectName", "setupBakeOption", "setupChangeIcon", "setupChangeOrientation", "setupClearVars", "setupCustomResolution", "setupDescriptionInputLayout", "setupDisableScientificNotation", "setupGitButtons", "setupMishkFrede", "setupNameInputLayout", "setupNotesAndCreditsInputLayout", "setupPhysicsInputLayout", "setupProjectAspectRatio", "setupProjectMoreDetails", "setupProjectOptionDelete", "setupProjectSaveExternal", "setupProjectUpload", "setupRebuildCache", "shareApkFile", "file", "shareFile", "showBuildDialog", "showCloneRepoDialog", "showCommitMessageDialog", "showCreateRepoDialog", "showGitConnectDialog", "showImagePickerDialog", "showLoginRequiredCommunityDialog", "showLoginRequiredDialog", "showProgressDialog", CrashHianalyticsData.MESSAGE, "showProjectReloadDialog", "showPublishPill", NotificationCompat.CATEGORY_STATUS, "showPublishPillSuccess", "showRebuildConfirmationDialog", "showToast", "toast", "signApkWithApksig", "unsignedApk", "signedApk", "keystoreAssetName", "keystorePassword", "keyAlias", "keyPassword", "startAsyncProjectBuild", "projectDestination", "startAsyncProjectExport", "startCacheRebuilding", "startCommunityPublishFlow", "unzip", "outDir", "updateGitButtonsVisibility", "updateProgressDialogMessage", "updatePublishPillStatus", "uploadChunks", "uploadId", "chunkSize", "token", "onProgress", "Lkotlin/Function1;", "verifyCaptchaForUpload", "siteKey", "onResult", "zipDirectory", "sourceDir", "zipDirectory3", "dir", "zos", "Ljava/util/zip/ZipOutputStream;", "basePath", "zipProjectWithoutSaves", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectOptionsFragment extends Fragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int PERMISSIONS_REQUEST_EXPORT_TO_EXTERNAL_STORAGE = 802;
    private static final int REQUEST_BUILD_PROJECT = 11;
    private static final int REQUEST_CODE_GENERATE_KEYSTORE = 1003;
    private static final int REQUEST_EXPORT_PROJECT = 10;
    private static final int REQUEST_SELECT_IMAGE = 12;
    private static final String TAG;
    private final int REQUEST_CODE_SELECT_KEYSTORE;
    private FragmentProjectOptionsBinding _binding;
    private View activeDialogView;
    private String buildFilename;
    private final List<String> genresList;
    private GitController gitController;
    private final OkHttpClient httpClient;
    private AlertDialog progressDialog;
    private Project project;
    private File projectInZip;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private String sceneName;
    private File selectedCustomKeystoreFile;
    private boolean shouldSaveOnPause;
    private String tempGenAlias;
    private String tempGenOwner;
    private String tempGenPass;
    private TextView tvSelectedKeystorePathTag;
    private File zipTempDir;

    private final void setupMishkFrede() {
    }

    public static final /* synthetic */ void access$showProjectReloadDialog(ProjectOptionsFragment projectOptionsFragment) {
        projectOptionsFragment.showProjectReloadDialog();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectOptionsFragment() {
        final ProjectOptionsFragment projectOptionsFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = projectOptionsFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
        this.httpClient = new OkHttpClient.Builder().connectTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).build();
        this.genresList = CollectionsKt.listOf((Object[]) new String[]{"Action", "Adventure", "Arcade", "Casual", "Platformer", "Puzzle", "RPG", "Racing", "Shooter", "Simulation", "Sports", "Strategy", "Fighting", "Survival", "Stealth", "Roguelike", "Sandbox", "Tower Defense", "MMO", "MOBA", "Indie", "Retro", "Pixel Art", "VR", "Multiplayer", "Singleplayer", "Co-op", "Educational", "Music", "Card", "Fantasy", "Sci-Fi", "Horror", "Cyberpunk", "Post-apocalyptic", "Steampunk", "Anime", "Cartoon", "Realistic", "Mystery", "Romance", "Comedy", "Thriller", "Historical", "Drama", "Other"});
        this.REQUEST_CODE_SELECT_KEYSTORE = 1002;
        this.shouldSaveOnPause = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentProjectOptionsBinding getBinding() {
        FragmentProjectOptionsBinding fragmentProjectOptionsBinding = this._binding;
        Intrinsics.checkNotNull(fragmentProjectOptionsBinding);
        return fragmentProjectOptionsBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = FragmentProjectOptionsBinding.inflate(inflater, container, false);
        ConstraintLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
        ActionBar supportActionBar = ((AppCompatActivity) fragmentActivityRequireActivity).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.project_options);
        }
        this.project = getProjectManager().getCurrentProject();
        this.sceneName = getProjectManager().getCurrentlyEditedScene().getName();
        Project project = this.project;
        Intrinsics.checkNotNull(project);
        File directory = project.getDirectory();
        Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
        this.gitController = new GitController(directory);
        setupNameInputLayout();
        setupPhysicsInputLayout();
        setupDescriptionInputLayout();
        setupNotesAndCreditsInputLayout();
        addTags();
        setupProjectAspectRatio();
        setupCustomResolution();
        setupDisableScientificNotation();
        getBinding().projectOptionsUpload.setVisibility(0);
        getBinding().projectOptionsUpload.setText(getString(R.string.community_publish_btn));
        getBinding().projectOptionsUpload.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda34
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ProjectOptionsFragment.onViewCreated$lambda$0(this.f$0, view2);
            }
        });
        setupProjectSaveExternal();
        setupRebuildCache();
        setupClearVars();
        setupChangeIcon();
        setupChangeOrientation();
        setupProjectMoreDetails();
        setupProjectOptionDelete();
        setupMishkFrede();
        setupGitButtons();
        setupBakeOption();
        TextView textView = (TextView) view.findViewById(R.id.project_options_build_apk);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda35
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ProjectOptionsFragment.onViewCreated$lambda$1(this.f$0, view2);
                }
            });
        }
        BottomBar.hideBottomBar(requireActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startCommunityPublishFlow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$1(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showBuildDialog();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.activeDialogView = null;
        this._binding = null;
        super.onDestroyView();
    }

    private final void setupDisableScientificNotation() {
        SwitchMaterial switchMaterial = getBinding().projectOptionsDisableScientificNotation;
        Project project = this.project;
        switchMaterial.setChecked(project != null ? project.isDisableScientificNotation() : false);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda31
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ProjectOptionsFragment.setupDisableScientificNotation$lambda$3$lambda$2(this.f$0, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupDisableScientificNotation$lambda$3$lambda$2(ProjectOptionsFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Project project = this$0.project;
        if (project != null) {
            project.setDisableScientificNotation(z);
        }
    }

    /* JADX WARN: Type inference failed for: r1v12, types: [T, java.util.List] */
    private final void showBuildDialog() {
        final Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        if (this.project == null) {
            return;
        }
        ProjectScanner projectScanner = ProjectScanner.INSTANCE;
        Project project = this.project;
        Intrinsics.checkNotNull(project);
        File directory = project.getDirectory();
        Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
        final ProjectScanner.ScanResult scanResultScanProject = projectScanner.scanProject(directory);
        final View viewInflate = getLayoutInflater().inflate(R.layout.dialog_apk_build_settings, (ViewGroup) null);
        this.activeDialogView = viewInflate;
        final TextView textView = (TextView) viewInflate.findViewById(R.id.tvStepSubtitle);
        final ProgressBar progressBar = (ProgressBar) viewInflate.findViewById(R.id.stepProgressIndicator);
        final LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.step1Container);
        final LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.step2Container);
        final LinearLayout linearLayout3 = (LinearLayout) viewInflate.findViewById(R.id.step3Container);
        final LinearLayout linearLayout4 = (LinearLayout) viewInflate.findViewById(R.id.step4Container);
        MaterialButton materialButton = (MaterialButton) viewInflate.findViewById(R.id.btnCancel);
        final MaterialButton materialButton2 = (MaterialButton) viewInflate.findViewById(R.id.btnBack);
        final MaterialButton materialButton3 = (MaterialButton) viewInflate.findViewById(R.id.btnNext);
        final TextInputEditText textInputEditText = (TextInputEditText) viewInflate.findViewById(R.id.etBuildAppName);
        final TextInputEditText textInputEditText2 = (TextInputEditText) viewInflate.findViewById(R.id.etBuildPackageName);
        final TextInputEditText textInputEditText3 = (TextInputEditText) viewInflate.findViewById(R.id.etBuildVersionName);
        final TextInputEditText textInputEditText4 = (TextInputEditText) viewInflate.findViewById(R.id.etBuildVersionCode);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) viewInflate.findViewById(R.id.etBuildTemplate);
        final MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) viewInflate.findViewById(R.id.toggleGroupOrientation);
        final SwitchMaterial switchMaterial = (SwitchMaterial) viewInflate.findViewById(R.id.switchPython);
        final SwitchMaterial switchMaterial2 = (SwitchMaterial) viewInflate.findViewById(R.id.switchQemu);
        final SwitchMaterial switchMaterial3 = (SwitchMaterial) viewInflate.findViewById(R.id.switchCompiler);
        final SwitchMaterial switchMaterial4 = (SwitchMaterial) viewInflate.findViewById(R.id.switchMkit);
        final SwitchMaterial switchMaterial5 = (SwitchMaterial) viewInflate.findViewById(R.id.switchOnnx);
        final SwitchMaterial switchMaterial6 = (SwitchMaterial) viewInflate.findViewById(R.id.switchMnn);
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.cbArm64);
        final CheckBox checkBox2 = (CheckBox) viewInflate.findViewById(R.id.cbArmv7);
        final ChipGroup chipGroup = (ChipGroup) viewInflate.findViewById(R.id.cgDetectedPermissions);
        final SwitchMaterial switchMaterial7 = (SwitchMaterial) viewInflate.findViewById(R.id.switchCustomizePermissions);
        final LinearLayout linearLayout5 = (LinearLayout) viewInflate.findViewById(R.id.llManualPermissions);
        final SwitchMaterial switchMaterial8 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermInternet);
        final SwitchMaterial switchMaterial9 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermStorage);
        final SwitchMaterial switchMaterial10 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermCamera);
        final SwitchMaterial switchMaterial11 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermAudio);
        final SwitchMaterial switchMaterial12 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermLocation);
        final SwitchMaterial switchMaterial13 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermBluetooth);
        final SwitchMaterial switchMaterial14 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermNfc);
        final SwitchMaterial switchMaterial15 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermVibrate);
        final SwitchMaterial switchMaterial16 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermOverlay);
        final SwitchMaterial switchMaterial17 = (SwitchMaterial) viewInflate.findViewById(R.id.switchPermBackground);
        final RadioGroup radioGroup = (RadioGroup) viewInflate.findViewById(R.id.rgKeystore);
        final LinearLayout linearLayout6 = (LinearLayout) viewInflate.findViewById(R.id.llGenerateKeystoreFields);
        final LinearLayout linearLayout7 = (LinearLayout) viewInflate.findViewById(R.id.llCustomKeystoreFields);
        final TextInputEditText textInputEditText5 = (TextInputEditText) viewInflate.findViewById(R.id.etGenAlias);
        final TextInputEditText textInputEditText6 = (TextInputEditText) viewInflate.findViewById(R.id.etGenPass);
        final TextInputEditText textInputEditText7 = (TextInputEditText) viewInflate.findViewById(R.id.etGenOwner);
        MaterialButton materialButton4 = (MaterialButton) viewInflate.findViewById(R.id.btnGenerateAndExportKeystore);
        MaterialButton materialButton5 = (MaterialButton) viewInflate.findViewById(R.id.btnSelectKeystore);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tvSelectedKeystorePath);
        final TextInputEditText textInputEditText8 = (TextInputEditText) viewInflate.findViewById(R.id.etKeystoreAlias);
        final TextInputEditText textInputEditText9 = (TextInputEditText) viewInflate.findViewById(R.id.etKeystorePass);
        Project project2 = this.project;
        Intrinsics.checkNotNull(project2);
        textInputEditText.setText(project2.getName());
        Project project3 = this.project;
        Intrinsics.checkNotNull(project3);
        String name2 = project3.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        String lowerCase = name2.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String strReplace = new Regex("[^a-z0-9]").replace(lowerCase, "");
        if (strReplace.length() == 0) {
            strReplace = "game";
        }
        String str = strReplace;
        if (str.length() > 0 && Character.isDigit(str.charAt(0))) {
            str = "project_" + str;
        }
        textInputEditText2.setText("com.newcatroid." + str);
        textInputEditText3.setText("1.0.0");
        textInputEditText4.setText(SchemaSymbols.ATTVAL_TRUE_1);
        switchMaterial8.setChecked(scanResultScanProject.getNeedsInternet());
        switchMaterial9.setChecked(scanResultScanProject.getNeedsStorage());
        switchMaterial10.setChecked(scanResultScanProject.getNeedsCamera());
        switchMaterial11.setChecked(scanResultScanProject.getNeedsAudioRecord());
        switchMaterial12.setChecked(scanResultScanProject.getNeedsLocation());
        switchMaterial13.setChecked(scanResultScanProject.getNeedsBluetooth());
        switchMaterial14.setChecked(scanResultScanProject.getNeedsNfc());
        switchMaterial15.setChecked(scanResultScanProject.getNeedsVibrate());
        switchMaterial16.setChecked(scanResultScanProject.getNeedsOverlay());
        switchMaterial17.setChecked(scanResultScanProject.getNeedsBackground());
        switchMaterial.setChecked(scanResultScanProject.getHasPython());
        switchMaterial2.setChecked(scanResultScanProject.getHasQemu());
        switchMaterial3.setChecked(scanResultScanProject.getHasCompiler());
        switchMaterial4.setChecked(scanResultScanProject.getHasMkit());
        switchMaterial5.setChecked(scanResultScanProject.getHasOnnx());
        switchMaterial6.setChecked(scanResultScanProject.getHasMnn());
        chipGroup.removeAllViews();
        Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showBuildDialog$addFriendlyPermissionChip$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                invoke2(str2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String text) {
                Intrinsics.checkNotNullParameter(text, "text");
                Chip chip = new Chip(contextRequireContext);
                Context context = contextRequireContext;
                chip.setText(text);
                chip.setClickable(false);
                chip.setCheckable(false);
                chip.setChipBackgroundColorResource(R.color.button_background);
                chip.setTextColor(context.getResources().getColor(R.color.solid_white));
                chip.setChipStrokeColorResource(R.color.accent);
                chip.setChipStrokeWidth(chip.getResources().getDisplayMetrics().density * 1.0f);
                chipGroup.addView(chip);
            }
        };
        if (scanResultScanProject.getNeedsInternet()) {
            String string = getString(R.string.perm_internet_short);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            function1.invoke(string);
        }
        if (scanResultScanProject.getNeedsStorage()) {
            String string2 = getString(R.string.perm_storage_short);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            function1.invoke(string2);
        }
        if (scanResultScanProject.getNeedsCamera()) {
            String string3 = getString(R.string.perm_camera_short);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            function1.invoke(string3);
        }
        if (scanResultScanProject.getNeedsAudioRecord()) {
            String string4 = getString(R.string.perm_audio_short);
            Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
            function1.invoke(string4);
        }
        if (scanResultScanProject.getNeedsLocation()) {
            String string5 = getString(R.string.perm_location_short);
            Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
            function1.invoke(string5);
        }
        if (scanResultScanProject.getNeedsBluetooth()) {
            String string6 = getString(R.string.perm_bluetooth_short);
            Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
            function1.invoke(string6);
        }
        if (scanResultScanProject.getNeedsNfc()) {
            String string7 = getString(R.string.perm_nfc_short);
            Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
            function1.invoke(string7);
        }
        if (scanResultScanProject.getNeedsVibrate()) {
            String string8 = getString(R.string.perm_vibrate_short);
            Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
            function1.invoke(string8);
        }
        if (scanResultScanProject.getNeedsOverlay()) {
            String string9 = getString(R.string.perm_overlay_short);
            Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
            function1.invoke(string9);
        }
        if (scanResultScanProject.getNeedsBackground()) {
            String string10 = getString(R.string.perm_background_short);
            Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
            function1.invoke(string10);
        }
        switchMaterial7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ProjectOptionsFragment.showBuildDialog$lambda$5(viewInflate, linearLayout5, compoundButton, z);
            }
        });
        Project project4 = this.project;
        Intrinsics.checkNotNull(project4);
        materialButtonToggleGroup.check(project4.getXmlHeader().islandscapeMode() ? R.id.btnLandscape : R.id.btnPortrait);
        showBuildDialog$updateOrientationTogglesVisuals(materialButtonToggleGroup, viewInflate, contextRequireContext);
        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda8
            @Override // com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
            public final void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup2, int i, boolean z) {
                ProjectOptionsFragment.showBuildDialog$lambda$6(materialButtonToggleGroup, viewInflate, contextRequireContext, materialButtonToggleGroup2, i, z);
            }
        });
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = CollectionsKt.emptyList();
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = -1;
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new AnonymousClass3(objectRef, contextRequireContext, autoCompleteTextView, intRef, null), 2, null);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda9
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                ProjectOptionsFragment.showBuildDialog$lambda$7(intRef, adapterView, view, i, j);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda10
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                ProjectOptionsFragment.showBuildDialog$lambda$8(viewInflate, linearLayout6, linearLayout7, radioGroup2, i);
            }
        });
        materialButton4.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.showBuildDialog$lambda$10(textInputEditText5, textInputEditText6, textInputEditText7, contextRequireContext, this, view);
            }
        });
        materialButton5.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.showBuildDialog$lambda$12(this.f$0, view);
            }
        });
        this.tvSelectedKeystorePathTag = textView2;
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(contextRequireContext, 2131952355).setView(viewInflate).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        materialButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.showBuildDialog$lambda$13(alertDialogCreate, view);
            }
        });
        final Ref.IntRef intRef2 = new Ref.IntRef();
        intRef2.element = 1;
        materialButton2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.showBuildDialog$lambda$14(intRef2, viewInflate, textView, this, progressBar, linearLayout, linearLayout2, linearLayout3, linearLayout4, materialButton2, materialButton3, view);
            }
        });
        materialButton3.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.showBuildDialog$lambda$16(intRef2, textInputEditText, textInputEditText2, textInputEditText3, contextRequireContext, this, checkBox, checkBox2, textInputEditText4, materialButtonToggleGroup, objectRef, intRef, radioGroup, textInputEditText8, textInputEditText9, alertDialogCreate, switchMaterial, switchMaterial2, switchMaterial3, switchMaterial4, switchMaterial5, switchMaterial6, switchMaterial7, switchMaterial8, scanResultScanProject, switchMaterial9, switchMaterial10, switchMaterial11, switchMaterial12, switchMaterial13, switchMaterial14, switchMaterial15, switchMaterial16, switchMaterial17, viewInflate, textView, progressBar, linearLayout, linearLayout2, linearLayout3, linearLayout4, materialButton2, materialButton3, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$5(View view, LinearLayout linearLayout, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.view.ViewGroup");
        TransitionManager.beginDelayedTransition((ViewGroup) view, new AutoTransition());
        linearLayout.setVisibility(z ? 0 : 8);
    }

    private static final void showBuildDialog$updateOrientationTogglesVisuals(MaterialButtonToggleGroup materialButtonToggleGroup, View view, Context context) {
        int checkedButtonId = materialButtonToggleGroup.getCheckedButtonId();
        Iterator it = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.id.btnPortrait), Integer.valueOf(R.id.btnLandscape), Integer.valueOf(R.id.btnSensor)}).iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            MaterialButton materialButton = (MaterialButton) view.findViewById(iIntValue);
            if (materialButton != null) {
                if (iIntValue == checkedButtonId) {
                    materialButton.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.accent)));
                    materialButton.setTextColor(context.getResources().getColor(R.color.app_background_dark));
                } else {
                    materialButton.setBackgroundTintList(ColorStateList.valueOf(0));
                    materialButton.setTextColor(context.getResources().getColor(R.color.solid_white));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$6(MaterialButtonToggleGroup materialButtonToggleGroup, View view, Context context, MaterialButtonToggleGroup materialButtonToggleGroup2, int i, boolean z) {
        Intrinsics.checkNotNullParameter(context, "$context");
        showBuildDialog$updateOrientationTogglesVisuals(materialButtonToggleGroup, view, context);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$showBuildDialog$3.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showBuildDialog$3, reason: invalid class name */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showBuildDialog$3", f = "ProjectOptionsFragment.kt", i = {}, l = {369}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.ObjectRef<List<TemplateManager.TemplateRelease>> $availableTemplates;
        final /* synthetic */ Context $context;
        final /* synthetic */ Ref.IntRef $selectedTemplateIndex;
        final /* synthetic */ AutoCompleteTextView $spinnerTemplates;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Ref.ObjectRef<List<TemplateManager.TemplateRelease>> objectRef, Context context, AutoCompleteTextView autoCompleteTextView, Ref.IntRef intRef, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$availableTemplates = objectRef;
            this.$context = context;
            this.$spinnerTemplates = autoCompleteTextView;
            this.$selectedTemplateIndex = intRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectOptionsFragment.this.new AnonymousClass3(this.$availableTemplates, this.$context, this.$spinnerTemplates, this.$selectedTemplateIndex, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Ref.ObjectRef<List<TemplateManager.TemplateRelease>> objectRef;
            T t;
            String str = "2.2.0";
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ProjectOptionsFragment projectOptionsFragment = ProjectOptionsFragment.this;
                    String string = projectOptionsFragment.getString(R.string.fetching_templates);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    projectOptionsFragment.showProgressDialog(string);
                    Ref.ObjectRef<List<TemplateManager.TemplateRelease>> objectRef2 = this.$availableTemplates;
                    this.L$0 = objectRef2;
                    this.label = 1;
                    Object objFetchAvailableTemplates = TemplateManager.INSTANCE.fetchAvailableTemplates(this);
                    if (objFetchAvailableTemplates == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    objectRef = objectRef2;
                    t = objFetchAvailableTemplates;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    objectRef = (Ref.ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    t = obj;
                }
                objectRef.element = t;
                if (this.$availableTemplates.element.isEmpty()) {
                    ToastUtil.showError(this.$context, ProjectOptionsFragment.this.getString(R.string.error_fetch_templates));
                } else {
                    List<TemplateManager.TemplateRelease> list = this.$availableTemplates.element;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (TemplateManager.TemplateRelease templateRelease : list) {
                        arrayList.add(templateRelease.getTagName() + " (" + templateRelease.getFileName() + Constant.AFTER_QUTO);
                    }
                    ArrayList arrayList2 = arrayList;
                    this.$spinnerTemplates.setAdapter(new ArrayAdapter(this.$context, android.R.layout.simple_dropdown_item_1line, arrayList2));
                    try {
                        String str2 = this.$context.getPackageManager().getPackageInfo(this.$context.getPackageName(), 0).versionName;
                        if (str2 != null) {
                            str = str2;
                        }
                    } catch (Exception unused) {
                    }
                    List<Integer> listInvokeSuspend$parseSemVer = invokeSuspend$parseSemVer(str);
                    int size = this.$availableTemplates.element.size();
                    List<Integer> list2 = null;
                    int i2 = -1;
                    for (int i3 = 0; i3 < size; i3++) {
                        List<Integer> listInvokeSuspend$parseSemVer2 = invokeSuspend$parseSemVer(this.$availableTemplates.element.get(i3).getTagName());
                        if (invokeSuspend$compareSemVer(listInvokeSuspend$parseSemVer2, listInvokeSuspend$parseSemVer) <= 0 && (list2 == null || invokeSuspend$compareSemVer(listInvokeSuspend$parseSemVer2, list2) > 0)) {
                            i2 = i3;
                            list2 = listInvokeSuspend$parseSemVer2;
                        }
                    }
                    if (i2 == -1) {
                        i2 = 0;
                    }
                    if (i2 != -1) {
                        this.$spinnerTemplates.setText((CharSequence) arrayList2.get(i2), false);
                        this.$selectedTemplateIndex.element = i2;
                    } else {
                        this.$spinnerTemplates.setText((CharSequence) arrayList2.get(0), false);
                        this.$selectedTemplateIndex.element = 0;
                    }
                }
            } catch (Exception e) {
                ToastUtil.showError(this.$context, ProjectOptionsFragment.this.getString(R.string.error_fetch_templates) + " " + e.getLocalizedMessage());
            } finally {
                ProjectOptionsFragment.this.hideProgressDialog();
            }
            return Unit.INSTANCE;
        }

        private static final List<Integer> invokeSuspend$parseSemVer(String str) {
            List listSplit$default = StringsKt.split$default((CharSequence) new Regex("[^0-9.]").replace(StringsKt.substringBefore$default(str, Parameters.DEFAULT_OPTION_PREFIXES, (String) null, 2, (Object) null), ""), new String[]{"."}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList();
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                Integer intOrNull = StringsKt.toIntOrNull((String) it.next());
                if (intOrNull != null) {
                    arrayList.add(intOrNull);
                }
            }
            return arrayList;
        }

        private static final int invokeSuspend$compareSemVer(List<Integer> list, List<Integer> list2) {
            int iMax = Math.max(list.size(), list2.size());
            for (int i = 0; i < iMax; i++) {
                Integer num = (Integer) CollectionsKt.getOrNull(list, i);
                int iIntValue = num != null ? num.intValue() : 0;
                Integer num2 = (Integer) CollectionsKt.getOrNull(list2, i);
                int iIntValue2 = num2 != null ? num2.intValue() : 0;
                if (iIntValue != iIntValue2) {
                    return Intrinsics.compare(iIntValue, iIntValue2);
                }
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$7(Ref.IntRef selectedTemplateIndex, AdapterView adapterView, View view, int i, long j) {
        Intrinsics.checkNotNullParameter(selectedTemplateIndex, "$selectedTemplateIndex");
        selectedTemplateIndex.element = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$8(View view, LinearLayout linearLayout, LinearLayout linearLayout2, RadioGroup radioGroup, int i) {
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.view.ViewGroup");
        TransitionManager.beginDelayedTransition((ViewGroup) view, new AutoTransition());
        switch (i) {
            case R.id.rbAutoDebug /* 2131365108 */:
                linearLayout.setVisibility(8);
                linearLayout2.setVisibility(8);
                break;
            case R.id.rbCustomKeystore /* 2131365109 */:
                linearLayout.setVisibility(8);
                linearLayout2.setVisibility(0);
                break;
            case R.id.rbGenerateNew /* 2131365110 */:
                linearLayout.setVisibility(0);
                linearLayout2.setVisibility(8);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$10(TextInputEditText textInputEditText, TextInputEditText textInputEditText2, TextInputEditText textInputEditText3, Context context, ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = StringsKt.trim((CharSequence) String.valueOf(textInputEditText.getText())).toString();
        String string2 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText2.getText())).toString();
        String string3 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText3.getText())).toString();
        if (string.length() == 0 || string2.length() == 0 || string3.length() == 0) {
            ToastUtil.showError(context, this$0.getString(R.string.error_fill_generation));
            return;
        }
        this$0.tempGenAlias = string;
        this$0.tempGenPass = string2;
        this$0.tempGenOwner = string3;
        Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("application/x-pkcs12");
        intent.putExtra("android.intent.extra.TITLE", "newcatroid_release_key.p12");
        this$0.startActivityForResult(intent, 1003);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$12(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.addCategory("android.intent.category.OPENABLE");
        this$0.startActivityForResult(intent, this$0.REQUEST_CODE_SELECT_KEYSTORE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$13(AlertDialog dialog, View view) {
        Intrinsics.checkNotNullParameter(dialog, "$dialog");
        dialog.dismiss();
    }

    private static final void showBuildDialog$updateStepUI(View view, Ref.IntRef intRef, TextView textView, ProjectOptionsFragment projectOptionsFragment, ProgressBar progressBar, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, MaterialButton materialButton, MaterialButton materialButton2) {
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.view.ViewGroup");
        TransitionManager.beginDelayedTransition((ViewGroup) view, new AutoTransition());
        int i = intRef.element;
        if (i == 1) {
            textView.setText(projectOptionsFragment.getString(R.string.step_format, 1, projectOptionsFragment.getString(R.string.step_basic)));
            progressBar.setProgress(25);
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(8);
            linearLayout4.setVisibility(8);
            materialButton.setVisibility(4);
            materialButton2.setText(projectOptionsFragment.getString(R.string.btn_next));
            return;
        }
        if (i == 2) {
            textView.setText(projectOptionsFragment.getString(R.string.step_format, 2, projectOptionsFragment.getString(R.string.step_modules)));
            progressBar.setProgress(50);
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(0);
            linearLayout3.setVisibility(8);
            linearLayout4.setVisibility(8);
            materialButton.setVisibility(0);
            materialButton2.setText(projectOptionsFragment.getString(R.string.btn_next));
            return;
        }
        if (i == 3) {
            textView.setText(projectOptionsFragment.getString(R.string.step_format, 3, projectOptionsFragment.getString(R.string.step_permissions)));
            progressBar.setProgress(75);
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(0);
            linearLayout4.setVisibility(8);
            materialButton.setVisibility(0);
            materialButton2.setText(projectOptionsFragment.getString(R.string.btn_next));
            return;
        }
        if (i != 4) {
            return;
        }
        textView.setText(projectOptionsFragment.getString(R.string.step_format, 4, projectOptionsFragment.getString(R.string.step_signature)));
        progressBar.setProgress(100);
        linearLayout.setVisibility(8);
        linearLayout2.setVisibility(8);
        linearLayout3.setVisibility(8);
        linearLayout4.setVisibility(0);
        materialButton.setVisibility(0);
        materialButton2.setText(projectOptionsFragment.getString(R.string.btn_build));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$14(Ref.IntRef currentStep, View view, TextView textView, ProjectOptionsFragment this$0, ProgressBar progressBar, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, MaterialButton materialButton, MaterialButton materialButton2, View view2) {
        Intrinsics.checkNotNullParameter(currentStep, "$currentStep");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (currentStep.element > 1) {
            currentStep.element--;
            showBuildDialog$updateStepUI(view, currentStep, textView, this$0, progressBar, linearLayout, linearLayout2, linearLayout3, linearLayout4, materialButton, materialButton2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showBuildDialog$lambda$16(Ref.IntRef currentStep, TextInputEditText textInputEditText, TextInputEditText textInputEditText2, TextInputEditText textInputEditText3, Context context, ProjectOptionsFragment this$0, CheckBox checkBox, CheckBox checkBox2, TextInputEditText textInputEditText4, MaterialButtonToggleGroup materialButtonToggleGroup, Ref.ObjectRef availableTemplates, Ref.IntRef selectedTemplateIndex, RadioGroup radioGroup, TextInputEditText textInputEditText5, TextInputEditText textInputEditText6, AlertDialog dialog, SwitchMaterial switchMaterial, SwitchMaterial switchMaterial2, SwitchMaterial switchMaterial3, SwitchMaterial switchMaterial4, SwitchMaterial switchMaterial5, SwitchMaterial switchMaterial6, SwitchMaterial switchMaterial7, SwitchMaterial switchMaterial8, ProjectScanner.ScanResult scanResult, SwitchMaterial switchMaterial9, SwitchMaterial switchMaterial10, SwitchMaterial switchMaterial11, SwitchMaterial switchMaterial12, SwitchMaterial switchMaterial13, SwitchMaterial switchMaterial14, SwitchMaterial switchMaterial15, SwitchMaterial switchMaterial16, SwitchMaterial switchMaterial17, View view, TextView textView, ProgressBar progressBar, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, MaterialButton materialButton, MaterialButton materialButton2, View view2) {
        String str;
        Intrinsics.checkNotNullParameter(currentStep, "$currentStep");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(availableTemplates, "$availableTemplates");
        Intrinsics.checkNotNullParameter(selectedTemplateIndex, "$selectedTemplateIndex");
        Intrinsics.checkNotNullParameter(dialog, "$dialog");
        Intrinsics.checkNotNullParameter(scanResult, "$scanResult");
        int i = currentStep.element;
        if (i == 1) {
            String string = StringsKt.trim((CharSequence) String.valueOf(textInputEditText.getText())).toString();
            String string2 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText2.getText())).toString();
            String string3 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText3.getText())).toString();
            if (string.length() == 0 || string2.length() == 0 || string3.length() == 0) {
                ToastUtil.showError(context, this$0.getString(R.string.error_fill_required));
                return;
            } else if (!this$0.isValidPackageName(string2)) {
                ToastUtil.showError(context, this$0.getString(R.string.apk_build_error_invalide_package));
                return;
            } else {
                currentStep.element = 2;
                showBuildDialog$updateStepUI(view, currentStep, textView, this$0, progressBar, linearLayout, linearLayout2, linearLayout3, linearLayout4, materialButton, materialButton2);
                return;
            }
        }
        if (i == 2) {
            if (!checkBox.isChecked() && !checkBox2.isChecked()) {
                ToastUtil.showError(context, this$0.getString(R.string.error_no_arch));
                return;
            } else {
                currentStep.element = 3;
                showBuildDialog$updateStepUI(view, currentStep, textView, this$0, progressBar, linearLayout, linearLayout2, linearLayout3, linearLayout4, materialButton, materialButton2);
                return;
            }
        }
        if (i == 3) {
            currentStep.element = 4;
            showBuildDialog$updateStepUI(view, currentStep, textView, this$0, progressBar, linearLayout, linearLayout2, linearLayout3, linearLayout4, materialButton, materialButton2);
            return;
        }
        if (i != 4) {
            return;
        }
        String string4 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText.getText())).toString();
        String string5 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText2.getText())).toString();
        String string6 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText3.getText())).toString();
        Integer intOrNull = StringsKt.toIntOrNull(String.valueOf(textInputEditText4.getText()));
        int iIntValue = intOrNull != null ? intOrNull.intValue() : 1;
        int checkedButtonId = materialButtonToggleGroup.getCheckedButtonId();
        if (checkedButtonId == R.id.btnLandscape) {
            str = "landscape";
        } else if (checkedButtonId == R.id.btnSensor) {
            str = "sensor";
        } else {
            str = "portrait";
        }
        TemplateManager.TemplateRelease templateRelease = (((Collection) availableTemplates.element).isEmpty() || selectedTemplateIndex.element < 0 || selectedTemplateIndex.element >= ((List) availableTemplates.element).size()) ? null : (TemplateManager.TemplateRelease) ((List) availableTemplates.element).get(selectedTemplateIndex.element);
        if (templateRelease == null) {
            ToastUtil.showError(context, this$0.getString(R.string.error_select_template));
            return;
        }
        boolean z = radioGroup.getCheckedRadioButtonId() == R.id.rbCustomKeystore;
        boolean z2 = radioGroup.getCheckedRadioButtonId() == R.id.rbGenerateNew;
        String string7 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText5.getText())).toString();
        String string8 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText6.getText())).toString();
        if (z2) {
            ToastUtil.showError(context, this$0.getString(R.string.error_key_generation_needed));
            return;
        }
        if (z && (this$0.selectedCustomKeystoreFile == null || string7.length() == 0 || string8.length() == 0)) {
            ToastUtil.showError(context, this$0.getString(R.string.error_fill_keystore));
            return;
        }
        dialog.dismiss();
        ArrayList arrayList = new ArrayList();
        if (!switchMaterial.isChecked()) {
            arrayList.add("python");
        }
        if (!switchMaterial2.isChecked()) {
            arrayList.add("qemu");
        }
        if (!switchMaterial3.isChecked()) {
            arrayList.add("compiler");
        }
        if (!switchMaterial4.isChecked()) {
            arrayList.add("mkit");
        }
        if (!switchMaterial5.isChecked()) {
            arrayList.add("onnx");
        }
        if (!switchMaterial6.isChecked()) {
            arrayList.add("mnn");
        }
        ArrayList arrayList2 = new ArrayList();
        if (!checkBox.isChecked()) {
            arrayList2.add("arm64-v8a");
        }
        if (!checkBox2.isChecked()) {
            arrayList2.add("armeabi-v7a");
        }
        arrayList2.add("x86");
        arrayList2.add("x86_64");
        boolean zIsChecked = switchMaterial7.isChecked();
        boolean zIsChecked2 = zIsChecked ? switchMaterial8.isChecked() : scanResult.getNeedsInternet();
        boolean zIsChecked3 = zIsChecked ? switchMaterial9.isChecked() : scanResult.getNeedsStorage();
        boolean zIsChecked4 = zIsChecked ? switchMaterial10.isChecked() : scanResult.getNeedsCamera();
        boolean zIsChecked5 = zIsChecked ? switchMaterial11.isChecked() : scanResult.getNeedsAudioRecord();
        boolean zIsChecked6 = zIsChecked ? switchMaterial12.isChecked() : scanResult.getNeedsLocation();
        boolean zIsChecked7 = zIsChecked ? switchMaterial13.isChecked() : scanResult.getNeedsBluetooth();
        boolean zIsChecked8 = zIsChecked ? switchMaterial14.isChecked() : scanResult.getNeedsNfc();
        boolean zIsChecked9 = zIsChecked ? switchMaterial15.isChecked() : scanResult.getNeedsVibrate();
        boolean zIsChecked10 = zIsChecked ? switchMaterial16.isChecked() : scanResult.getNeedsOverlay();
        boolean zIsChecked11 = zIsChecked ? switchMaterial17.isChecked() : scanResult.getNeedsBackground();
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("android.permission.POST_NOTIFICATIONS");
        if (zIsChecked2) {
            arrayList3.add("android.permission.INTERNET");
            arrayList3.add("android.permission.ACCESS_NETWORK_STATE");
            arrayList3.add("android.permission.ACCESS_WIFI_STATE");
        }
        if (zIsChecked3) {
            arrayList3.add("android.permission.READ_EXTERNAL_STORAGE");
            arrayList3.add("android.permission.WRITE_EXTERNAL_STORAGE");
            arrayList3.add("android.permission.MANAGE_EXTERNAL_STORAGE");
            arrayList3.add("android.permission.READ_MEDIA_AUDIO");
            arrayList3.add("android.permission.READ_MEDIA_VIDEO");
            arrayList3.add("android.permission.READ_MEDIA_IMAGES");
        }
        if (zIsChecked4) {
            arrayList3.add("android.permission.CAMERA");
        }
        if (zIsChecked5) {
            arrayList3.add("android.permission.RECORD_AUDIO");
        }
        if (zIsChecked6) {
            arrayList3.add("android.permission.ACCESS_FINE_LOCATION");
            arrayList3.add("android.permission.ACCESS_COARSE_LOCATION");
        }
        if (zIsChecked7) {
            arrayList3.add("android.permission.BLUETOOTH");
            arrayList3.add("android.permission.BLUETOOTH_ADMIN");
            arrayList3.add("android.permission.BLUETOOTH_SCAN");
            arrayList3.add("android.permission.BLUETOOTH_CONNECT");
            arrayList3.add("android.permission.BLUETOOTH_ADVERTISE");
            arrayList3.add("android.permission.NEARBY_WIFI_DEVICES");
        }
        if (zIsChecked8) {
            arrayList3.add("android.permission.NFC");
        }
        if (zIsChecked9) {
            arrayList3.add("android.permission.VIBRATE");
        }
        if (zIsChecked10) {
            arrayList3.add("android.permission.SYSTEM_ALERT_WINDOW");
        }
        if (zIsChecked11) {
            arrayList3.add("android.permission.FOREGROUND_SERVICE");
            arrayList3.add("android.permission.FOREGROUND_SERVICE_DATA_SYNC");
        }
        this$0.executeApkBuildProcess(string4, string5, string6, iIntValue, str, templateRelease, arrayList, arrayList2, z, this$0.selectedCustomKeystoreFile, string7, string8, arrayList3);
    }

    private final boolean isValidPackageName(String packageName) {
        String str = packageName;
        if (!new Regex("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)+$").matches(str)) {
            return false;
        }
        Set of = SetsKt.setOf((Object[]) new String[]{"abstract", PsiKeyword.ASSERT, "boolean", PsiKeyword.BREAK, "byte", PsiKeyword.CASE, PsiKeyword.CATCH, PsiKeyword.CHAR, "class", PsiKeyword.CONST, PsiKeyword.CONTINUE, "default", PsiKeyword.DO, "double", PsiKeyword.ELSE, PsiKeyword.ENUM, PsiKeyword.EXTENDS, "final", PsiKeyword.FINALLY, "float", PsiKeyword.FOR, PsiKeyword.GOTO, PsiKeyword.IF, PsiKeyword.IMPLEMENTS, PsiKeyword.IMPORT, PsiKeyword.INSTANCEOF, "int", PsiKeyword.INTERFACE, "long", "native", PsiKeyword.NEW, "package", "private", "protected", "public", "return", "short", "static", "strictfp", PsiKeyword.SUPER, PsiKeyword.SWITCH, "synchronized", "this", PsiKeyword.THROW, PsiKeyword.THROWS, "transient", PsiKeyword.TRY, PsiKeyword.VOID, "volatile", PsiKeyword.WHILE, "true", "false", "null"});
        Iterator it = StringsKt.split$default((CharSequence) str, new String[]{"."}, false, 0, 6, (Object) null).iterator();
        while (it.hasNext()) {
            if (of.contains((String) it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean processProjectIcon(File sourceFile, File destFile) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(sourceFile.getAbsolutePath(), options);
            if (bitmapDecodeFile == null) {
                return false;
            }
            int width = bitmapDecodeFile.getWidth();
            int height = bitmapDecodeFile.getHeight();
            int iMin = Math.min(width, height);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeFile, (width - iMin) / 2, (height - iMin) / 2, iMin, iMin);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, 512, 512, true);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            try {
                bitmapCreateScaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                CloseableKt.closeFinally(fileOutputStream, null);
                if (!Intrinsics.areEqual(bitmapDecodeFile, bitmapCreateScaledBitmap)) {
                    bitmapDecodeFile.recycle();
                }
                if (!Intrinsics.areEqual(bitmapCreateBitmap, bitmapCreateScaledBitmap)) {
                    bitmapCreateBitmap.recycle();
                }
                bitmapCreateScaledBitmap.recycle();
                return true;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            Log.e("ApkBuilder", "Failed to process launcher icon", e);
            return false;
        }
    }

    private final void executeApkBuildProcess(String appName, String packageName, String versionName, int versionCode, String orientation, TemplateManager.TemplateRelease template, List<String> excludedLibraries, List<String> excludedArches, boolean useCustomKeystore, File keystoreFile, String keystoreAlias, String keystorePass, List<String> allowedPermissions) {
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        Project project = this.project;
        Intrinsics.checkNotNull(project);
        File directory = project.getDirectory();
        String string = getString(R.string.downloading_template);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        showProgressDialog(string);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new C41881(contextRequireContext, template, this, appName, directory, packageName, versionName, versionCode, orientation, excludedArches, excludedLibraries, allowedPermissions, useCustomKeystore, keystoreFile, keystoreAlias, keystorePass, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$executeApkBuildProcess$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executeApkBuildProcess$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executeApkBuildProcess$1", f = "ProjectOptionsFragment.kt", i = {1, 2, 2, 2, 3, 3, 3, 4, 4}, l = {789, 803, 816, 845, CollisionConstants.GIM_MIN_RADIX_SORT_SIZE}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"downloadedTemplateApk", "projectZipFile", "unsignedApkFile", "finalApkFile", "projectZipFile", "unsignedApkFile", "finalApkFile", "unsignedApkFile", "finalApkFile"}, s = {"L$0", "L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$0", "L$1"})
    static final class C41881 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<String> $allowedPermissions;
        final /* synthetic */ String $appName;
        final /* synthetic */ Context $context;
        final /* synthetic */ List<String> $excludedArches;
        final /* synthetic */ List<String> $excludedLibraries;
        final /* synthetic */ String $keystoreAlias;
        final /* synthetic */ File $keystoreFile;
        final /* synthetic */ String $keystorePass;
        final /* synthetic */ String $orientation;
        final /* synthetic */ String $packageName;
        final /* synthetic */ File $projectDir;
        final /* synthetic */ TemplateManager.TemplateRelease $template;
        final /* synthetic */ boolean $useCustomKeystore;
        final /* synthetic */ int $versionCode;
        final /* synthetic */ String $versionName;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ ProjectOptionsFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41881(Context context, TemplateManager.TemplateRelease templateRelease, ProjectOptionsFragment projectOptionsFragment, String str, File file, String str2, String str3, int i, String str4, List<String> list, List<String> list2, List<String> list3, boolean z, File file2, String str5, String str6, Continuation<? super C41881> continuation) {
            super(2, continuation);
            this.$context = context;
            this.$template = templateRelease;
            this.this$0 = projectOptionsFragment;
            this.$appName = str;
            this.$projectDir = file;
            this.$packageName = str2;
            this.$versionName = str3;
            this.$versionCode = i;
            this.$orientation = str4;
            this.$excludedArches = list;
            this.$excludedLibraries = list2;
            this.$allowedPermissions = list3;
            this.$useCustomKeystore = z;
            this.$keystoreFile = file2;
            this.$keystoreAlias = str5;
            this.$keystorePass = str6;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41881(this.$context, this.$template, this.this$0, this.$appName, this.$projectDir, this.$packageName, this.$versionName, this.$versionCode, this.$orientation, this.$excludedArches, this.$excludedLibraries, this.$allowedPermissions, this.$useCustomKeystore, this.$keystoreFile, this.$keystoreAlias, this.$keystorePass, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41881) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:29:0x012a A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:30:0x012b  */
        /* JADX WARN: Code duplicated, block: B:33:0x014b  */
        /* JADX WARN: Code duplicated, block: B:38:0x0169  */
        /* JADX WARN: Code duplicated, block: B:41:0x01a8 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:42:0x01a9  */
        /* JADX WARN: Code duplicated, block: B:45:0x01b5  */
        /* JADX WARN: Code duplicated, block: B:47:0x01ce  */
        /* JADX WARN: Code duplicated, block: B:49:0x020f A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:50:0x0210  */
        /* JADX WARN: Code duplicated, block: B:56:0x023e  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object objDownloadTemplate;
            File file;
            Object objWithContext;
            File file2;
            File file3;
            File file4;
            File file5;
            File file6;
            File projectIconFile;
            File file7;
            Object objWithContext2;
            File file8;
            boolean zBooleanValue;
            Object objWithContext3;
            File file9;
            File file10;
            boolean zBooleanValue2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TemplateManager templateManager = TemplateManager.INSTANCE;
                Context context = this.$context;
                String downloadUrl = this.$template.getDownloadUrl();
                String fileName = this.$template.getFileName();
                final ProjectOptionsFragment projectOptionsFragment = this.this$0;
                this.label = 1;
                objDownloadTemplate = templateManager.downloadTemplate(context, downloadUrl, fileName, new Function1<Integer, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executeApkBuildProcess$1$downloadedTemplateApk$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                        invoke(num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(int i2) {
                        ProjectOptionsFragment projectOptionsFragment2 = projectOptionsFragment;
                        String string = projectOptionsFragment2.getString(R.string.downloading_template_progress, Integer.valueOf(i2));
                        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                        projectOptionsFragment2.updateProgressDialogMessage(string);
                    }
                }, this);
                if (objDownloadTemplate == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    objDownloadTemplate = obj;
                } else if (i == 2) {
                    file = (File) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objWithContext = obj;
                    file2 = (File) objWithContext;
                    ProjectOptionsFragment projectOptionsFragment2 = this.this$0;
                    String string = projectOptionsFragment2.getString(R.string.preparing_apk_structure);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    projectOptionsFragment2.updateProgressDialogMessage(string);
                    file3 = new File(this.$context.getCacheDir(), "build_unsigned.apk");
                    file4 = new File(this.$context.getCacheDir(), this.$appName + "_build.apk");
                    this.L$0 = file2;
                    this.L$1 = file3;
                    this.L$2 = file4;
                    this.label = 3;
                    if (BuildersKt.withContext(Dispatchers.getIO(), new C02601(file, file3, null), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    file5 = file2;
                    file6 = file4;
                    ProjectOptionsFragment projectOptionsFragment3 = this.this$0;
                    String string2 = projectOptionsFragment3.getString(R.string.customizing_apk);
                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                    projectOptionsFragment3.updateProgressDialogMessage(string2);
                    ProjectOptionsFragment projectOptionsFragment4 = this.this$0;
                    File projectDir = this.$projectDir;
                    Intrinsics.checkNotNullExpressionValue(projectDir, "$projectDir");
                    projectIconFile = projectOptionsFragment4.getProjectIconFile(projectDir);
                    if (projectIconFile == null) {
                        file7 = null;
                    } else {
                        file7 = null;
                    }
                    ApkToolboxManager.BuildOptions buildOptions = new ApkToolboxManager.BuildOptions(this.$appName, this.$packageName, this.$versionName, this.$versionCode, this.$orientation, file7, file5, this.$excludedArches, this.$excludedLibraries, this.$allowedPermissions);
                    this.L$0 = file5;
                    this.L$1 = file3;
                    this.L$2 = file6;
                    this.label = 4;
                    objWithContext2 = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executeApkBuildProcess$1$customizationSuccess$1(file3, buildOptions, null), this);
                    if (objWithContext2 == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    file8 = file3;
                    zBooleanValue = ((Boolean) objWithContext2).booleanValue();
                    file5.delete();
                    if (!zBooleanValue) {
                        this.this$0.hideProgressDialog();
                        ToastUtil.showError(this.$context, this.this$0.getString(R.string.error_build_optimization));
                        file8.delete();
                        return Unit.INSTANCE;
                    }
                    ProjectOptionsFragment projectOptionsFragment5 = this.this$0;
                    String string3 = projectOptionsFragment5.getString(R.string.signing_apk);
                    Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                    projectOptionsFragment5.updateProgressDialogMessage(string3);
                    this.L$0 = file8;
                    this.L$1 = file6;
                    this.L$2 = null;
                    this.label = 5;
                    objWithContext3 = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1(this.$useCustomKeystore, this.$keystoreFile, this.$context, file8, file6, this.$keystoreAlias, this.$keystorePass, null), this);
                    if (objWithContext3 == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    file9 = file6;
                    file10 = file8;
                } else if (i == 3) {
                    file6 = (File) this.L$2;
                    File file11 = (File) this.L$1;
                    File file12 = (File) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    file3 = file11;
                    file5 = file12;
                    ProjectOptionsFragment projectOptionsFragment6 = this.this$0;
                    String string4 = projectOptionsFragment6.getString(R.string.customizing_apk);
                    Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                    projectOptionsFragment6.updateProgressDialogMessage(string4);
                    ProjectOptionsFragment projectOptionsFragment7 = this.this$0;
                    File projectDir2 = this.$projectDir;
                    Intrinsics.checkNotNullExpressionValue(projectDir2, "$projectDir");
                    projectIconFile = projectOptionsFragment7.getProjectIconFile(projectDir2);
                    if (projectIconFile == null && projectIconFile.exists()) {
                        File file13 = new File(this.$context.getCacheDir(), "processed_icon.png");
                        if (this.this$0.processProjectIcon(projectIconFile, file13)) {
                            file7 = file13;
                        } else {
                            file7 = null;
                        }
                    } else {
                        file7 = null;
                    }
                    ApkToolboxManager.BuildOptions buildOptions2 = new ApkToolboxManager.BuildOptions(this.$appName, this.$packageName, this.$versionName, this.$versionCode, this.$orientation, file7, file5, this.$excludedArches, this.$excludedLibraries, this.$allowedPermissions);
                    this.L$0 = file5;
                    this.L$1 = file3;
                    this.L$2 = file6;
                    this.label = 4;
                    objWithContext2 = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executeApkBuildProcess$1$customizationSuccess$1(file3, buildOptions2, null), this);
                    if (objWithContext2 == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    file8 = file3;
                    zBooleanValue = ((Boolean) objWithContext2).booleanValue();
                    file5.delete();
                    if (!zBooleanValue) {
                        this.this$0.hideProgressDialog();
                        ToastUtil.showError(this.$context, this.this$0.getString(R.string.error_build_optimization));
                        file8.delete();
                        return Unit.INSTANCE;
                    }
                    ProjectOptionsFragment projectOptionsFragment8 = this.this$0;
                    String string5 = projectOptionsFragment8.getString(R.string.signing_apk);
                    Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
                    projectOptionsFragment8.updateProgressDialogMessage(string5);
                    this.L$0 = file8;
                    this.L$1 = file6;
                    this.L$2 = null;
                    this.label = 5;
                    objWithContext3 = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1(this.$useCustomKeystore, this.$keystoreFile, this.$context, file8, file6, this.$keystoreAlias, this.$keystorePass, null), this);
                    if (objWithContext3 == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    file9 = file6;
                    file10 = file8;
                } else if (i == 4) {
                    file6 = (File) this.L$2;
                    file8 = (File) this.L$1;
                    file5 = (File) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objWithContext2 = obj;
                    zBooleanValue = ((Boolean) objWithContext2).booleanValue();
                    file5.delete();
                    if (!zBooleanValue) {
                        this.this$0.hideProgressDialog();
                        ToastUtil.showError(this.$context, this.this$0.getString(R.string.error_build_optimization));
                        file8.delete();
                        return Unit.INSTANCE;
                    }
                    ProjectOptionsFragment projectOptionsFragment9 = this.this$0;
                    String string6 = projectOptionsFragment9.getString(R.string.signing_apk);
                    Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
                    projectOptionsFragment9.updateProgressDialogMessage(string6);
                    this.L$0 = file8;
                    this.L$1 = file6;
                    this.L$2 = null;
                    this.label = 5;
                    objWithContext3 = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1(this.$useCustomKeystore, this.$keystoreFile, this.$context, file8, file6, this.$keystoreAlias, this.$keystorePass, null), this);
                    if (objWithContext3 == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    file9 = file6;
                    file10 = file8;
                } else {
                    if (i != 5) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    file9 = (File) this.L$1;
                    file10 = (File) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objWithContext3 = obj;
                }
                zBooleanValue2 = ((Boolean) objWithContext3).booleanValue();
                file10.delete();
                this.this$0.hideProgressDialog();
                if (!zBooleanValue2 && file9.exists()) {
                    ToastUtil.showSuccess(this.$context, this.this$0.getString(R.string.success_build));
                    this.this$0.shareApkFile(file9, this.$appName);
                } else {
                    ToastUtil.showError(this.$context, this.this$0.getString(R.string.error_signing_apk));
                    file9.delete();
                }
                return Unit.INSTANCE;
            }
            file = (File) objDownloadTemplate;
            if (file == null || !file.exists()) {
                this.this$0.hideProgressDialog();
                ToastUtil.showError(this.$context, this.this$0.getString(R.string.error_download_template));
                return Unit.INSTANCE;
            }
            ProjectOptionsFragment projectOptionsFragment10 = this.this$0;
            String string7 = projectOptionsFragment10.getString(R.string.preparing_project);
            Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
            projectOptionsFragment10.updateProgressDialogMessage(string7);
            this.L$0 = file;
            this.label = 2;
            objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executeApkBuildProcess$1$projectZipFile$1(this.$context, this.this$0, this.$projectDir, null), this);
            if (objWithContext == coroutine_suspended) {
                return coroutine_suspended;
            }
            file2 = (File) objWithContext;
            ProjectOptionsFragment projectOptionsFragment11 = this.this$0;
            String string8 = projectOptionsFragment11.getString(R.string.preparing_apk_structure);
            Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
            projectOptionsFragment11.updateProgressDialogMessage(string8);
            file3 = new File(this.$context.getCacheDir(), "build_unsigned.apk");
            file4 = new File(this.$context.getCacheDir(), this.$appName + "_build.apk");
            this.L$0 = file2;
            this.L$1 = file3;
            this.L$2 = file4;
            this.label = 3;
            if (BuildersKt.withContext(Dispatchers.getIO(), new C02601(file, file3, null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            file5 = file2;
            file6 = file4;
            ProjectOptionsFragment projectOptionsFragment12 = this.this$0;
            String string9 = projectOptionsFragment12.getString(R.string.customizing_apk);
            Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
            projectOptionsFragment12.updateProgressDialogMessage(string9);
            ProjectOptionsFragment projectOptionsFragment13 = this.this$0;
            File projectDir3 = this.$projectDir;
            Intrinsics.checkNotNullExpressionValue(projectDir3, "$projectDir");
            projectIconFile = projectOptionsFragment13.getProjectIconFile(projectDir3);
            if (projectIconFile == null) {
                file7 = null;
            } else {
                file7 = null;
            }
            ApkToolboxManager.BuildOptions buildOptions3 = new ApkToolboxManager.BuildOptions(this.$appName, this.$packageName, this.$versionName, this.$versionCode, this.$orientation, file7, file5, this.$excludedArches, this.$excludedLibraries, this.$allowedPermissions);
            this.L$0 = file5;
            this.L$1 = file3;
            this.L$2 = file6;
            this.label = 4;
            objWithContext2 = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executeApkBuildProcess$1$customizationSuccess$1(file3, buildOptions3, null), this);
            if (objWithContext2 == coroutine_suspended) {
                return coroutine_suspended;
            }
            file8 = file3;
            zBooleanValue = ((Boolean) objWithContext2).booleanValue();
            file5.delete();
            if (!zBooleanValue) {
                this.this$0.hideProgressDialog();
                ToastUtil.showError(this.$context, this.this$0.getString(R.string.error_build_optimization));
                file8.delete();
                return Unit.INSTANCE;
            }
            ProjectOptionsFragment projectOptionsFragment14 = this.this$0;
            String string10 = projectOptionsFragment14.getString(R.string.signing_apk);
            Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
            projectOptionsFragment14.updateProgressDialogMessage(string10);
            this.L$0 = file8;
            this.L$1 = file6;
            this.L$2 = null;
            this.label = 5;
            objWithContext3 = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1(this.$useCustomKeystore, this.$keystoreFile, this.$context, file8, file6, this.$keystoreAlias, this.$keystorePass, null), this);
            if (objWithContext3 == coroutine_suspended) {
                return coroutine_suspended;
            }
            file9 = file6;
            file10 = file8;
            zBooleanValue2 = ((Boolean) objWithContext3).booleanValue();
            file10.delete();
            this.this$0.hideProgressDialog();
            if (!zBooleanValue2) {
                ToastUtil.showError(this.$context, this.this$0.getString(R.string.error_signing_apk));
                file9.delete();
            } else {
                ToastUtil.showError(this.$context, this.this$0.getString(R.string.error_signing_apk));
                file9.delete();
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$executeApkBuildProcess$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executeApkBuildProcess$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Ljava/io/File;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executeApkBuildProcess$1$1", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02601 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super File>, Object> {
            final /* synthetic */ File $downloadedTemplateApk;
            final /* synthetic */ File $unsignedApkFile;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02601(File file, File file2, Continuation<? super C02601> continuation) {
                super(2, continuation);
                this.$downloadedTemplateApk = file;
                this.$unsignedApkFile = file2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02601(this.$downloadedTemplateApk, this.$unsignedApkFile, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super File> continuation) {
                return ((C02601) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                return FilesKt.copyTo$default(this.$downloadedTemplateApk, this.$unsignedApkFile, true, 0, 4, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void shareApkFile(File file, String appName) {
        Uri uriForFile = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".fileProvider", file);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("application/vnd.android.package-archive");
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.addFlags(1);
        startActivity(Intent.createChooser(intent, getString(R.string.share_apk_title, appName)));
    }

    static /* synthetic */ void zipProjectWithoutSaves$default(ProjectOptionsFragment projectOptionsFragment, File file, ZipOutputStream zipOutputStream, String str, int i, Object obj) throws IOException {
        if ((i & 4) != 0) {
            str = "";
        }
        projectOptionsFragment.zipProjectWithoutSaves(file, zipOutputStream, str);
    }

    private final void zipProjectWithoutSaves(File dir, ZipOutputStream zos, String basePath) throws IOException {
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{Constants.DEVICE_VARIABLE_JSON_FILE_NAME, Constants.DEVICE_LIST_JSON_FILE_NAME, Constants.UNDO_CODE_XML_FILE_NAME});
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                String name2 = basePath.length() == 0 ? file.getName() : basePath + "/" + file.getName();
                if (!listListOf.contains(file.getName())) {
                    if (file.isDirectory()) {
                        zos.putNextEntry(new ZipEntry(name2 + "/"));
                        zos.closeEntry();
                        Intrinsics.checkNotNull(file);
                        Intrinsics.checkNotNull(name2);
                        zipProjectWithoutSaves(file, zos, name2);
                    } else {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        try {
                            zos.putNextEntry(new ZipEntry(name2));
                            ByteStreamsKt.copyTo$default(fileInputStream, zos, 0, 2, null);
                            zos.closeEntry();
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(fileInputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileInputStream, th);
                                throw th2;
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateProgressDialogMessage(final String message) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectOptionsFragment.updateProgressDialogMessage$lambda$22(this.f$0, message);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateProgressDialogMessage$lambda$22(ProjectOptionsFragment this$0, String message) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(message, "$message");
        AlertDialog alertDialog = this$0.progressDialog;
        if (alertDialog != null) {
            alertDialog.setMessage(message);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String parseServerError(String errorJson) {
        String str = errorJson;
        if (str == null || str.length() == 0) {
            return "Неизвестная ошибка сервера";
        }
        try {
            JSONObject jSONObject = new JSONObject(errorJson);
            String strOptString = jSONObject.optString(ProductAction.ACTION_DETAIL, jSONObject.optString(CrashHianalyticsData.MESSAGE, "Ошибка сервера"));
            Intrinsics.checkNotNull(strOptString);
            return strOptString;
        } catch (Exception unused) {
            return "Ошибка разбора ответа сервера";
        }
    }

    private final void startCommunityPublishFlow() {
        final Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        if (!CommunityTokenManager.isLoggedIn(contextRequireContext)) {
            showLoginRequiredCommunityDialog();
            return;
        }
        Project project = this.project;
        if (project == null) {
            return;
        }
        File directory = project.getDirectory();
        Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
        if (getProjectIconFile(directory) == null) {
            new AlertDialog.Builder(contextRequireContext, 2131952355).setTitle(R.string.community_publish_error_no_cover_title).setMessage(R.string.community_publish_error_no_cover).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
            return;
        }
        String description = project.getDescription();
        if (description == null) {
            description = "";
        }
        String string = StringsKt.trim((CharSequence) new Regex("!\\[.*?\\]\\(.*?\\)").replace(description, "")).toString();
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_publish_preparation, (ViewGroup) null);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(contextRequireContext, 2131952355).setView(viewInflate).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        final TextInputEditText textInputEditText = (TextInputEditText) viewInflate.findViewById(R.id.etPublishTitle);
        final TextInputEditText textInputEditText2 = (TextInputEditText) viewInflate.findViewById(R.id.etPublishDescription);
        final ChipGroup chipGroup = (ChipGroup) viewInflate.findViewById(R.id.chipGroupPublishGenres);
        MaterialButton materialButton = (MaterialButton) viewInflate.findViewById(R.id.btnSubmitPublish);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tvPublishRulesLink);
        textInputEditText.setText(project.getName());
        textInputEditText2.setText(string);
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda44
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.startCommunityPublishFlow$lambda$23(this.f$0, view);
            }
        });
        for (String str : this.genresList) {
            Chip chip = new Chip(contextRequireContext);
            int identifier = contextRequireContext.getResources().getIdentifier("genre_" + StringsKt.replace$default(StringsKt.replace$default(str, " ", "_", false, 4, (Object) null), Parameters.DEFAULT_OPTION_PREFIXES, "_", false, 4, (Object) null), "string", contextRequireContext.getPackageName());
            chip.setText(identifier != 0 ? contextRequireContext.getString(identifier) : str);
            chip.setCheckable(true);
            chip.setChipBackgroundColorResource(R.color.button_background);
            chip.setChipStrokeColorResource(R.color.accent);
            chip.setChipStrokeWidth(chip.getResources().getDisplayMetrics().density * 1.0f);
            chip.setTextColor(contextRequireContext.getResources().getColor(R.color.solid_white));
            chip.setTag(str);
            chipGroup.addView(chip);
        }
        materialButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda45
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.startCommunityPublishFlow$lambda$26(textInputEditText, textInputEditText2, contextRequireContext, chipGroup, alertDialogCreate, this, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCommunityPublishFlow$lambda$23(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://newcatroid.sois.site/market/documents")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startCommunityPublishFlow$lambda$26(TextInputEditText textInputEditText, TextInputEditText textInputEditText2, Context context, ChipGroup chipGroup, AlertDialog dialog, ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(dialog, "$dialog");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = StringsKt.trim((CharSequence) String.valueOf(textInputEditText.getText())).toString();
        String string2 = StringsKt.trim((CharSequence) String.valueOf(textInputEditText2.getText())).toString();
        if (string.length() == 0) {
            ToastUtil.showError(context, "Название проекта не может быть пустым");
            return;
        }
        if (string.length() > 30) {
            ToastUtil.showError(context, "Название проекта слишком длинное (макс. 30 символов)");
            return;
        }
        if (string2.length() == 0) {
            ToastUtil.showError(context, "Описание проекта не может быть пустым");
            return;
        }
        if (string2.length() > 400) {
            ToastUtil.showError(context, "Описание слишком длинное (макс. 400 символов)");
            return;
        }
        ArrayList arrayList = new ArrayList();
        int childCount = chipGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = chipGroup.getChildAt(i);
            Chip chip = childAt instanceof Chip ? (Chip) childAt : null;
            if (chip != null && chip.isChecked()) {
                Object tag = chip.getTag();
                Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.String");
                arrayList.add((String) tag);
            }
        }
        dialog.dismiss();
        this$0.executePublicationStages(string, string2, arrayList);
    }

    private final void showLoginRequiredCommunityDialog() {
        new AlertDialog.Builder(requireContext()).setTitle(R.string.community_publish_auth_required).setMessage(R.string.community_publish_auth_desc).setPositiveButton("Вход", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda36
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showLoginRequiredCommunityDialog$lambda$27(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showLoginRequiredCommunityDialog$lambda$27(ProjectOptionsFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0.requireContext(), (Class<?>) CommunityLoginActivity.class));
    }

    private final void executePublicationStages(String title, String description, List<String> genres) {
        Project project = this.project;
        if (project == null) {
            return;
        }
        String string = getString(R.string.community_stage_zipping);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        showPublishPill(string);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new C41891(project, title, description, genres, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$executePublicationStages$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executePublicationStages$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executePublicationStages$1", f = "ProjectOptionsFragment.kt", i = {1}, l = {1064, 1079}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"tempZipFile"}, s = {"L$0"})
    static final class C41891 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Project $currentProject;
        final /* synthetic */ String $description;
        final /* synthetic */ List<String> $genres;
        final /* synthetic */ String $title;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41891(Project project, String str, String str2, List<String> list, Continuation<? super C41891> continuation) {
            super(2, continuation);
            this.$currentProject = project;
            this.$title = str;
            this.$description = str2;
            this.$genres = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectOptionsFragment.this.new C41891(this.$currentProject, this.$title, this.$description, this.$genres, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41891) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:27:0x00ba A[Catch: Exception -> 0x0028, TryCatch #0 {Exception -> 0x0028, blocks: (B:7:0x0016, B:25:0x00b6, B:27:0x00ba, B:29:0x00d7, B:11:0x0024, B:19:0x0050, B:21:0x0086, B:30:0x00ed, B:31:0x00f5, B:16:0x002e), top: B:35:0x000c }] */
        /* JADX WARN: Code duplicated, block: B:29:0x00d7 A[Catch: Exception -> 0x0028, TryCatch #0 {Exception -> 0x0028, blocks: (B:7:0x0016, B:25:0x00b6, B:27:0x00ba, B:29:0x00d7, B:11:0x0024, B:19:0x0050, B:21:0x0086, B:30:0x00ed, B:31:0x00f5, B:16:0x002e), top: B:35:0x000c }] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            final File file;
            String str;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ProjectOptionsFragment.this.saveProject();
                    this.label = 1;
                    obj = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executePublicationStages$1$tempZipFile$1(ProjectOptionsFragment.this, this.$currentProject, null), this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i == 1) {
                        ResultKt.throwOnFailure(obj);
                    } else {
                        if (i != 2) {
                            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                        }
                        File file2 = (File) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        file = file2;
                    }
                    str = (String) obj;
                    if (str == null) {
                        ProjectOptionsFragment.this.hidePublishPill();
                        ToastUtil.showError(ProjectOptionsFragment.this.requireContext(), ProjectOptionsFragment.this.getString(R.string.community_error_config_failed));
                        file.delete();
                        return Unit.INSTANCE;
                    }
                    ProjectOptionsFragment projectOptionsFragment = ProjectOptionsFragment.this;
                    final ProjectOptionsFragment projectOptionsFragment2 = ProjectOptionsFragment.this;
                    final String str2 = this.$title;
                    final String str3 = this.$description;
                    final List<String> list = this.$genres;
                    projectOptionsFragment.verifyCaptchaForUpload(str, new Function1<String, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment.executePublicationStages.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(String str4) {
                            invoke2(str4);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(String str4) {
                            if (str4 != null) {
                                projectOptionsFragment2.performChunkedUpload(file, str2, str3, list, str4);
                                return;
                            }
                            projectOptionsFragment2.hidePublishPill();
                            ToastUtil.showError(projectOptionsFragment2.requireContext(), projectOptionsFragment2.getString(R.string.community_captcha_cancelled));
                            file.delete();
                        }
                    });
                    return Unit.INSTANCE;
                }
                File file3 = (File) obj;
                Log.d("UploadCommunity", "Создан ZIP архив публикации. Путь: " + file3.getAbsolutePath() + " | Размер: " + file3.length() + " байт");
                if (file3.length() <= 0) {
                    throw new IOException("Размер файла проекта равен 0 байт. Ошибка архивации.");
                }
                ProjectOptionsFragment projectOptionsFragment3 = ProjectOptionsFragment.this;
                String string = projectOptionsFragment3.getString(R.string.community_stage_captcha);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                projectOptionsFragment3.updatePublishPillStatus(string);
                this.L$0 = file3;
                this.label = 2;
                Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new ProjectOptionsFragment$executePublicationStages$1$siteKey$1(ProjectOptionsFragment.this, null), this);
                if (objWithContext == coroutine_suspended) {
                    return coroutine_suspended;
                }
                file = file3;
                obj = objWithContext;
                str = (String) obj;
                if (str == null) {
                    ProjectOptionsFragment.this.hidePublishPill();
                    ToastUtil.showError(ProjectOptionsFragment.this.requireContext(), ProjectOptionsFragment.this.getString(R.string.community_error_config_failed));
                    file.delete();
                    return Unit.INSTANCE;
                }
                ProjectOptionsFragment projectOptionsFragment4 = ProjectOptionsFragment.this;
                final ProjectOptionsFragment projectOptionsFragment5 = ProjectOptionsFragment.this;
                final String str4 = this.$title;
                final String str5 = this.$description;
                final List<String> list2 = this.$genres;
                projectOptionsFragment4.verifyCaptchaForUpload(str, new Function1<String, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment.executePublicationStages.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str6) {
                        invoke2(str6);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(String str6) {
                        if (str6 != null) {
                            projectOptionsFragment5.performChunkedUpload(file, str4, str5, list2, str6);
                            return;
                        }
                        projectOptionsFragment5.hidePublishPill();
                        ToastUtil.showError(projectOptionsFragment5.requireContext(), projectOptionsFragment5.getString(R.string.community_captcha_cancelled));
                        file.delete();
                    }
                });
                return Unit.INSTANCE;
            } catch (Exception e) {
                ProjectOptionsFragment.this.hidePublishPill();
                ToastUtil.showError(ProjectOptionsFragment.this.requireContext(), "Ошибка упаковки: " + e.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File getProjectIconFile(File projectDir) {
        File file = new File(projectDir, Constants.SCREENSHOT_MANUAL_FILE_NAME);
        File file2 = new File(projectDir, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
        if (file.exists() && file.length() > 0) {
            return file;
        }
        if (!file2.exists() || file2.length() <= 0) {
            return null;
        }
        return file2;
    }

    private final String getProjectIconBase64(File iconFile) {
        try {
            return "data:image/png;base64," + Base64.encodeToString(FilesKt.readBytes(iconFile), 2);
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void performChunkedUpload(File zipFile, String title, String description, List<String> genres, String captchaToken) {
        Project project = this.project;
        if (project == null) {
            return;
        }
        File directory = project.getDirectory();
        Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
        File projectIconFile = getProjectIconFile(directory);
        if (projectIconFile == null) {
            zipFile.delete();
            hidePublishPill();
            ToastUtil.showError(requireContext(), getString(R.string.community_publish_error_no_cover));
            return;
        }
        String projectIconBase64 = getProjectIconBase64(projectIconFile);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        String token = CommunityTokenManager.getToken(contextRequireContext);
        if (token == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C41951(zipFile, genres, token, this, title, description, projectIconBase64, captchaToken, projectIconFile, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$performChunkedUpload$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$performChunkedUpload$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$performChunkedUpload$1", f = "ProjectOptionsFragment.kt", i = {}, l = {XKeySym.XK_kana_SHI}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41951 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $captchaToken;
        final /* synthetic */ String $description;
        final /* synthetic */ List<String> $genres;
        final /* synthetic */ File $iconFile;
        final /* synthetic */ String $imageBase64;
        final /* synthetic */ String $title;
        final /* synthetic */ String $token;
        final /* synthetic */ File $zipFile;
        int label;
        final /* synthetic */ ProjectOptionsFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41951(File file, List<String> list, String str, ProjectOptionsFragment projectOptionsFragment, String str2, String str3, String str4, String str5, File file2, Continuation<? super C41951> continuation) {
            super(2, continuation);
            this.$zipFile = file;
            this.$genres = list;
            this.$token = str;
            this.this$0 = projectOptionsFragment;
            this.$title = str2;
            this.$description = str3;
            this.$imageBase64 = str4;
            this.$captchaToken = str5;
            this.$iconFile = file2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41951(this.$zipFile, this.$genres, this.$token, this.this$0, this.$title, this.$description, this.$imageBase64, this.$captchaToken, this.$iconFile, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41951) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Throwable th;
            String strString;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    long length = this.$zipFile.length();
                    JSONArray jSONArray = new JSONArray();
                    Iterator<T> it = this.$genres.iterator();
                    while (it.hasNext()) {
                        jSONArray.put((String) it.next());
                    }
                    try {
                        JSONObject jSONObject = new JSONObject();
                        String str = this.$title;
                        String str2 = this.$description;
                        ProjectOptionsFragment projectOptionsFragment = this.this$0;
                        String str3 = this.$imageBase64;
                        String str4 = this.$captchaToken;
                        jSONObject.put("title", str);
                        jSONObject.put(MediaTrack.ROLE_DESCRIPTION, str2);
                        jSONObject.put("genres", jSONArray);
                        jSONObject.put("file_size", length);
                        Project project = projectOptionsFragment.project;
                        Intrinsics.checkNotNull(project);
                        jSONObject.put("file_name", project.getName() + Constants.CATROBAT_EXTENSION);
                        jSONObject.put("image_b64", str3);
                        jSONObject.put("h-captcha-response", str4);
                        jSONObject.put("size", length);
                        jSONObject.put("quest_size_bytes", length);
                        jSONObject.put("total_size", length);
                        String string = jSONObject.toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        Response responseExecute = this.this$0.httpClient.newCall(new Request.Builder().url("https://backend.sois.site/games/upload/init").header("Authorization", "Bearer " + this.$token).post(RequestBody.INSTANCE.create(string, MediaType.INSTANCE.get("application/json; charset=utf-8"))).build()).execute();
                        final ProjectOptionsFragment projectOptionsFragment2 = this.this$0;
                        File file = this.$zipFile;
                        String str5 = this.$token;
                        File file2 = this.$iconFile;
                        try {
                            Response response = responseExecute;
                            ResponseBody responseBodyBody = response.body();
                            if (responseBodyBody != null) {
                                try {
                                    strString = responseBodyBody.string();
                                } catch (Throwable th2) {
                                    th = th2;
                                    coroutine_suspended = coroutine_suspended;
                                    try {
                                        throw th;
                                    } catch (Throwable th3) {
                                        CloseableKt.closeFinally(responseExecute, th);
                                        throw th3;
                                    }
                                }
                            } else {
                                strString = null;
                            }
                            Log.d("UploadCommunity", "Init Response: " + strString);
                            try {
                                if (!response.isSuccessful()) {
                                    throw new IOException(projectOptionsFragment2.parseServerError(strString));
                                }
                                Intrinsics.checkNotNull(strString);
                                JSONObject jSONObject2 = new JSONObject(strString);
                                String string2 = jSONObject2.getString("upload_id");
                                int i2 = jSONObject2.getInt("chunk_size");
                                Log.d("UploadCommunity", "Инициализация успешна. ID сессии: " + string2 + " | Размер чанка: " + i2 + " байт");
                                Intrinsics.checkNotNull(string2);
                                projectOptionsFragment2.uploadChunks(file, string2, i2, str5, new Function1<Integer, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$performChunkedUpload$1$2$1
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                                        invoke(num.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(int i3) {
                                        ProjectOptionsFragment projectOptionsFragment3 = projectOptionsFragment2;
                                        String string3 = projectOptionsFragment3.getString(R.string.community_stage_uploading, Integer.valueOf(i3));
                                        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                                        projectOptionsFragment3.updatePublishPillStatus(string3);
                                    }
                                });
                                String string3 = projectOptionsFragment2.getString(R.string.community_stage_finishing);
                                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                                projectOptionsFragment2.updatePublishPillStatus(string3);
                                Response responseExecute2 = projectOptionsFragment2.httpClient.newCall(new Request.Builder().url("https://backend.sois.site/games/upload/finish").header("Authorization", "Bearer " + str5).post(new MultipartBody.Builder(null, 1, null).setType(MultipartBody.FORM).addFormDataPart("upload_id", string2).addFormDataPart(Constants.TMP_IMAGE_FILE_NAME, file2.getName(), RequestBody.INSTANCE.create(file2, MediaType.INSTANCE.get("image/png"))).build()).build()).execute();
                                try {
                                    Response response2 = responseExecute2;
                                    ResponseBody responseBodyBody2 = response2.body();
                                    String strString2 = responseBodyBody2 != null ? responseBodyBody2.string() : null;
                                    file.delete();
                                    if (response2.isSuccessful()) {
                                        projectOptionsFragment2.showPublishPillSuccess();
                                        Unit unit = Unit.INSTANCE;
                                        CloseableKt.closeFinally(responseExecute2, null);
                                        Unit unit2 = Unit.INSTANCE;
                                        CloseableKt.closeFinally(responseExecute, null);
                                    } else {
                                        throw new IOException(projectOptionsFragment2.parseServerError(strString2));
                                    }
                                } catch (Throwable th4) {
                                    try {
                                        throw th4;
                                    } catch (Throwable th5) {
                                        CloseableKt.closeFinally(responseExecute2, th4);
                                        throw th5;
                                    }
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                th = th;
                                throw th;
                            }
                        } catch (Throwable th7) {
                            th = th7;
                        }
                    } catch (Exception e) {
                        e = e;
                        this.$zipFile.delete();
                        this.label = 1;
                        Object obj2 = coroutine_suspended;
                        if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass3(this.this$0, e, null), this) == obj2) {
                            return obj2;
                        }
                        return Unit.INSTANCE;
                    }
                } catch (Exception e2) {
                    e = e2;
                    coroutine_suspended = coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$performChunkedUpload$1$3.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$performChunkedUpload$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$performChunkedUpload$1$3", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Exception $e;
            int label;
            final /* synthetic */ ProjectOptionsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(ProjectOptionsFragment projectOptionsFragment, Exception exc, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = projectOptionsFragment;
                this.$e = exc;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, this.$e, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.hidePublishPill();
                    ToastUtil.showError(this.this$0.requireContext(), "Ошибка: " + this.$e.getMessage());
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void uploadChunks(final File file, String uploadId, int chunkSize, String token, final Function1<? super Integer, Unit> onProgress) throws IOException {
        final long length = file.length();
        int iCeil = (int) Math.ceil(length / ((double) chunkSize));
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = -1;
        int i = 0;
        while (i < iCeil) {
            long j = chunkSize;
            final long j2 = ((long) i) * j;
            long j3 = length - j2;
            final long j4 = j3 < j ? j3 : j;
            int i2 = i;
            Response responseExecute = this.httpClient.newCall(new Request.Builder().url("https://backend.sois.site/games/upload/chunk").header("Authorization", "Bearer " + token).header("X-Upload-Id", uploadId).header("X-Chunk-Index", String.valueOf(i2)).post(new RequestBody() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$uploadChunks$progressRequestBody$1
                @Override // okhttp3.RequestBody
                /* JADX INFO: renamed from: contentType */
                public MediaType getContentType() {
                    return MediaType.INSTANCE.get("application/octet-stream");
                }

                @Override // okhttp3.RequestBody
                /* JADX INFO: renamed from: contentLength, reason: from getter */
                public long get$currentChunkSize() {
                    return j4;
                }

                @Override // okhttp3.RequestBody
                public void writeTo(BufferedSink sink) {
                    BufferedSink sink2 = sink;
                    Intrinsics.checkNotNullParameter(sink2, "sink");
                    FileInputStream fileInputStream = new FileInputStream(file);
                    long j5 = j2;
                    long j6 = j4;
                    long j7 = length;
                    Ref.IntRef intRef2 = intRef;
                    Function1<Integer, Unit> function1 = onProgress;
                    try {
                        FileInputStream fileInputStream2 = fileInputStream;
                        fileInputStream2.skip(j5);
                        int i3 = 8192;
                        byte[] bArr = new byte[8192];
                        long j8 = 0;
                        for (long j9 = 0; j6 > j9; j9 = 0) {
                            int i4 = fileInputStream2.read(bArr, 0, j6 > ((long) i3) ? i3 : (int) j6);
                            if (i4 == -1) {
                                break;
                            }
                            sink2.write(bArr, 0, i4);
                            long j10 = i4;
                            j6 -= j10;
                            j8 += j10;
                            int i5 = (int) (((j5 + j8) / j7) * 100);
                            if (i5 != intRef2.element) {
                                intRef2.element = i5;
                                function1.invoke(Integer.valueOf(RangesKt.coerceIn(i5, 0, 100)));
                            }
                            sink2 = sink;
                            i3 = 8192;
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(fileInputStream, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(fileInputStream, th);
                            throw th2;
                        }
                    }
                }
            }).build()).execute();
            try {
                Response response = responseExecute;
                ResponseBody responseBodyBody = response.body();
                String strString = responseBodyBody != null ? responseBodyBody.string() : null;
                if (!response.isSuccessful()) {
                    throw new IOException("Чанк №" + i2 + ": " + parseServerError(strString));
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(responseExecute, null);
                i = i2 + 1;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(responseExecute, th);
                    throw th2;
                }
            }
        }
    }

    private final void showPublishPill(final String status) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectOptionsFragment.showPublishPill$lambda$29(this.f$0, status);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPublishPill$lambda$29(ProjectOptionsFragment this$0, String status) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(status, "$status");
        this$0.getBinding().tvPublishProgressTitle.setText("Публикация проекта");
        this$0.getBinding().tvPublishProgressStatus.setText(status);
        this$0.getBinding().publishProgressBar.setVisibility(0);
        this$0.getBinding().publishProgressBar.setIndeterminate(true);
        this$0.getBinding().btnDismissPublishNotification.setVisibility(8);
        if (this$0.getBinding().publishProgressPill.getVisibility() != 0) {
            this$0.getBinding().publishProgressPill.setTranslationY(300.0f);
            this$0.getBinding().publishProgressPill.setAlpha(0.0f);
            this$0.getBinding().publishProgressPill.setVisibility(0);
            this$0.getBinding().publishProgressPill.animate().translationY(0.0f).alpha(1.0f).setDuration(400L).setInterpolator(new OvershootInterpolator(1.1f)).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updatePublishPillStatus(final String status) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectOptionsFragment.updatePublishPillStatus$lambda$30(status, this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updatePublishPillStatus$lambda$30(String status, ProjectOptionsFragment this$0) {
        Intrinsics.checkNotNullParameter(status, "$status");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String str = status;
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) "%", false, 2, (Object) null)) {
            Integer intOrNull = StringsKt.toIntOrNull(StringsKt.substringBefore$default(StringsKt.substringAfterLast$default(status, ": ", (String) null, 2, (Object) null), "%", (String) null, 2, (Object) null));
            int iIntValue = intOrNull != null ? intOrNull.intValue() : 0;
            this$0.getBinding().publishProgressBar.setIndeterminate(false);
            this$0.getBinding().publishProgressBar.setProgress(iIntValue);
        } else {
            this$0.getBinding().publishProgressBar.setIndeterminate(true);
        }
        this$0.getBinding().tvPublishProgressStatus.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showPublishPillSuccess() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectOptionsFragment.showPublishPillSuccess$lambda$32(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPublishPillSuccess$lambda$32(final ProjectOptionsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().tvPublishProgressTitle.setText(this$0.getString(R.string.community_stage_success));
        this$0.getBinding().tvPublishProgressStatus.setText(this$0.getString(R.string.community_stage_success_desc));
        this$0.getBinding().publishProgressBar.setVisibility(8);
        this$0.getBinding().btnDismissPublishNotification.setVisibility(0);
        this$0.getBinding().btnDismissPublishNotification.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda30
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.showPublishPillSuccess$lambda$32$lambda$31(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPublishPillSuccess$lambda$32$lambda$31(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hidePublishPill();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hidePublishPill() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectOptionsFragment.hidePublishPill$lambda$34(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hidePublishPill$lambda$34(final ProjectOptionsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getBinding().publishProgressPill.getVisibility() == 0) {
            this$0.getBinding().publishProgressPill.animate().translationY(300.0f).alpha(0.0f).setDuration(300L).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectOptionsFragment.hidePublishPill$lambda$34$lambda$33(this.f$0);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hidePublishPill$lambda$34$lambda$33(ProjectOptionsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().publishProgressPill.setVisibility(8);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$fetchCaptchaSiteKey$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$fetchCaptchaSiteKey$2, reason: invalid class name */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$fetchCaptchaSiteKey$2", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
        final /* synthetic */ Request $request;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Request request, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$request = request;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectOptionsFragment.this.new AnonymousClass2(this.$request, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            ResponseBody responseBodyBody;
            String strString;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    Response responseExecute = ProjectOptionsFragment.this.httpClient.newCall(this.$request).execute();
                    try {
                        Response response = responseExecute;
                        String string = (!response.isSuccessful() || (responseBodyBody = response.body()) == null || (strString = responseBodyBody.string()) == null) ? null : new JSONObject(strString).getString("hcaptcha_site_key");
                        CloseableKt.closeFinally(responseExecute, null);
                        return string;
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(responseExecute, th);
                            throw th2;
                        }
                    }
                } catch (Exception unused) {
                    return null;
                }
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object fetchCaptchaSiteKey(Continuation<? super String> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(new Request.Builder().url("https://backend.sois.site/config").get().build(), null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void verifyCaptchaForUpload(String siteKey, final Function1<? super String, Unit> onResult) {
        HCaptchaConfig hCaptchaConfigBuild = HCaptchaConfig.builder().siteKey(siteKey).build();
        Intrinsics.checkNotNullExpressionValue(hCaptchaConfigBuild, "build(...)");
        HCaptcha.getClient(requireActivity()).verifyWithHCaptcha(hCaptchaConfigBuild).addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda1
            @Override // com.hcaptcha.sdk.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                ProjectOptionsFragment.verifyCaptchaForUpload$lambda$35(onResult, (HCaptchaTokenResponse) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda2
            @Override // com.hcaptcha.sdk.tasks.OnFailureListener
            public final void onFailure(HCaptchaException hCaptchaException) {
                ProjectOptionsFragment.verifyCaptchaForUpload$lambda$36(onResult, hCaptchaException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void verifyCaptchaForUpload$lambda$35(Function1 onResult, HCaptchaTokenResponse hCaptchaTokenResponse) {
        Intrinsics.checkNotNullParameter(onResult, "$onResult");
        onResult.invoke(hCaptchaTokenResponse.getTokenResult());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void verifyCaptchaForUpload$lambda$36(Function1 onResult, HCaptchaException hCaptchaException) {
        Intrinsics.checkNotNullParameter(onResult, "$onResult");
        onResult.invoke(null);
    }

    private final void setupBakeOption() {
        View view = getView();
        TextView textView = view != null ? (TextView) view.findViewById(R.id.project_options_bake) : null;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda27
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ProjectOptionsFragment.setupBakeOption$lambda$37(this.f$0, view2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupBakeOption$lambda$37(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.exportBakedProject();
    }

    private final void exportBakedProject() {
        saveProject();
        if (this.project == null) {
            return;
        }
        showProgressDialog("Запекание проекта...");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C41901(null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$exportBakedProject$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportBakedProject$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportBakedProject$1", f = "ProjectOptionsFragment.kt", i = {}, l = {1444, 1450}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41901 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C41901(Continuation<? super C41901> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectOptionsFragment.this.new C41901(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41901) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    File file = new File(ProjectOptionsFragment.this.requireContext().getCacheDir(), "bake_temp");
                    FilesKt.deleteRecursively(file);
                    file.mkdirs();
                    Context contextRequireContext = ProjectOptionsFragment.this.requireContext();
                    Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                    ProjectBaker projectBaker = new ProjectBaker(contextRequireContext);
                    Project project = ProjectOptionsFragment.this.project;
                    Intrinsics.checkNotNull(project);
                    FilesKt.writeText$default(new File(file, "init.bin"), projectBaker.bake(project), null, 2, null);
                    File file2 = new File(file, Constants.IMAGE_DIRECTORY_NAME);
                    File file3 = new File(file, Constants.SOUND_DIRECTORY_NAME);
                    file2.mkdirs();
                    file3.mkdirs();
                    Project project2 = ProjectOptionsFragment.this.project;
                    Intrinsics.checkNotNull(project2);
                    File directory = project2.getDirectory();
                    for (String str : CollectionsKt.listOf("files")) {
                        File file4 = new File(directory, str);
                        File file5 = new File(file, str);
                        if (file4.exists()) {
                            FilesKt.copyRecursively$default(file4, file5, true, null, 4, null);
                        } else {
                            file5.mkdirs();
                        }
                    }
                    Project project3 = ProjectOptionsFragment.this.project;
                    Intrinsics.checkNotNull(project3);
                    List<Scene> sceneList = project3.getSceneList();
                    Intrinsics.checkNotNullExpressionValue(sceneList, "getSceneList(...)");
                    Iterator<T> it = sceneList.iterator();
                    while (it.hasNext()) {
                        List<Sprite> spriteList = ((Scene) it.next()).getSpriteList();
                        Intrinsics.checkNotNullExpressionValue(spriteList, "getSpriteList(...)");
                        for (Sprite sprite : spriteList) {
                            List<LookData> lookList = sprite.getLookList();
                            Intrinsics.checkNotNullExpressionValue(lookList, "getLookList(...)");
                            Iterator<T> it2 = lookList.iterator();
                            while (it2.hasNext()) {
                                File file6 = ((LookData) it2.next()).getFile();
                                if (file6 != null && file6.exists()) {
                                    FilesKt.copyTo$default(file6, new File(file2, file6.getName()), true, 0, 4, null);
                                }
                            }
                            List<SoundInfo> soundList = sprite.getSoundList();
                            Intrinsics.checkNotNullExpressionValue(soundList, "getSoundList(...)");
                            Iterator<T> it3 = soundList.iterator();
                            while (it3.hasNext()) {
                                File file7 = ((SoundInfo) it3.next()).getFile();
                                if (file7 != null && file7.exists()) {
                                    FilesKt.copyTo$default(file7, new File(file3, file7.getName()), true, 0, 4, null);
                                }
                            }
                        }
                    }
                    File cacheDir = ProjectOptionsFragment.this.requireContext().getCacheDir();
                    Project project4 = ProjectOptionsFragment.this.project;
                    Intrinsics.checkNotNull(project4);
                    File file8 = new File(cacheDir, project4.getName() + "_baked.zip");
                    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file8));
                    try {
                        ProjectOptionsFragment.zipDirectory3$default(ProjectOptionsFragment.this, file, zipOutputStream, null, 4, null);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(zipOutputStream, null);
                        FilesKt.deleteRecursively(file);
                        this.label = 1;
                        if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass3(ProjectOptionsFragment.this, file8, null), this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(zipOutputStream, th);
                            throw th2;
                        }
                    }
                } else {
                    if (i != 1 && i != 2) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (Exception e) {
                this.label = 2;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass4(ProjectOptionsFragment.this, e, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$exportBakedProject$1$3.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportBakedProject$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportBakedProject$1$3", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ File $zipFile;
            int label;
            final /* synthetic */ ProjectOptionsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(ProjectOptionsFragment projectOptionsFragment, File file, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = projectOptionsFragment;
                this.$zipFile = file;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, this.$zipFile, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.hideProgressDialog();
                    this.this$0.shareFile(this.$zipFile);
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$exportBakedProject$1$4.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportBakedProject$1$4, reason: invalid class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportBakedProject$1$4", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Exception $e;
            int label;
            final /* synthetic */ ProjectOptionsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass4(ProjectOptionsFragment projectOptionsFragment, Exception exc, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.this$0 = projectOptionsFragment;
                this.$e = exc;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(this.this$0, this.$e, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.hideProgressDialog();
                    ToastUtil.showError(this.this$0.requireContext(), "Ошибка: " + this.$e.getMessage());
                    this.$e.printStackTrace();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void shareFile(File file) {
        Uri uriForFile = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".fileProvider", file);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("application/zip");
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.addFlags(1);
        startActivity(Intent.createChooser(intent, "Сохранить запеченный проект"));
    }

    private final void setupRebuildCache() {
        getBinding().projectOptionsRebuildCache.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda33
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupRebuildCache$lambda$38(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupRebuildCache$lambda$38(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showRebuildConfirmationDialog();
    }

    private final void showRebuildConfirmationDialog() {
        new AlertDialog.Builder(requireContext()).setTitle("Обновить кэш физики?").setMessage("Это может занять некоторое время, но исправит проблемы с хитбоксами в старых проектах. Продолжить?").setPositiveButton("Да", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda23
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showRebuildConfirmationDialog$lambda$39(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showRebuildConfirmationDialog$lambda$39(ProjectOptionsFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startCacheRebuilding();
    }

    private final void startCacheRebuilding() {
        if (this.project == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Project project = this.project;
        Intrinsics.checkNotNull(project);
        List<Scene> sceneList = project.getSceneList();
        Intrinsics.checkNotNullExpressionValue(sceneList, "getSceneList(...)");
        Iterator<T> it = sceneList.iterator();
        while (it.hasNext()) {
            List<Sprite> spriteList = ((Scene) it.next()).getSpriteList();
            Intrinsics.checkNotNullExpressionValue(spriteList, "getSpriteList(...)");
            Iterator<T> it2 = spriteList.iterator();
            while (it2.hasNext()) {
                List<LookData> lookList = ((Sprite) it2.next()).getLookList();
                Intrinsics.checkNotNullExpressionValue(lookList, "getLookList(...)");
                arrayList.addAll(lookList);
            }
        }
        if (arrayList.isEmpty()) {
            ToastUtil.showInfoLong(requireContext(), "В проекте нет образов для обработки.");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Обновление кэша");
        progressDialog.setMessage("Обработка образов...");
        progressDialog.setProgressStyle(1);
        progressDialog.setMax(arrayList.size());
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.show();
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C41972(arrayList, progressDialog, this, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$startCacheRebuilding$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$startCacheRebuilding$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$startCacheRebuilding$2", f = "ProjectOptionsFragment.kt", i = {0, 0, 0, 0, 1, 1}, l = {XKeySym.XK_Arabic_kasratan, 1526, 1531}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"successCount", "lookData", "index$iv", "index", "successCount", "index$iv"}, s = {"L$0", "L$3", "I$0", "I$1", "L$0", "I$0"})
    static final class C41972 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<LookData> $allLooks;
        final /* synthetic */ ProgressDialog $progressDialog;
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        final /* synthetic */ ProjectOptionsFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41972(List<LookData> list, ProgressDialog progressDialog, ProjectOptionsFragment projectOptionsFragment, Continuation<? super C41972> continuation) {
            super(2, continuation);
            this.$allLooks = list;
            this.$progressDialog = progressDialog;
            this.this$0 = projectOptionsFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41972(this.$allLooks, this.$progressDialog, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41972) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:15:0x006c  */
        /* JADX WARN: Code duplicated, block: B:17:0x0074  */
        /* JADX WARN: Code duplicated, block: B:20:0x009a A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:21:0x009b  */
        /* JADX WARN: Code duplicated, block: B:24:0x00ae  */
        /* JADX WARN: Code duplicated, block: B:27:0x00d2 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:28:0x00d3  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x00d3 -> B:13:0x0066). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r18) {
            /*
                Method dump skipped, instruction units count: 257
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment.C41972.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$startCacheRebuilding$2$2.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$startCacheRebuilding$2$2, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$startCacheRebuilding$2$2", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02652 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ List<LookData> $allLooks;
            final /* synthetic */ ProgressDialog $progressDialog;
            final /* synthetic */ Ref.IntRef $successCount;
            int label;
            final /* synthetic */ ProjectOptionsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02652(ProgressDialog progressDialog, ProjectOptionsFragment projectOptionsFragment, Ref.IntRef intRef, List<LookData> list, Continuation<? super C02652> continuation) {
                super(2, continuation);
                this.$progressDialog = progressDialog;
                this.this$0 = projectOptionsFragment;
                this.$successCount = intRef;
                this.$allLooks = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02652(this.$progressDialog, this.this$0, this.$successCount, this.$allLooks, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02652) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                this.$progressDialog.dismiss();
                ToastUtil.showSuccess(this.this$0.requireContext(), "Готово! Обработано " + this.$successCount.element + " из " + this.$allLooks.size() + " образов.");
                return Unit.INSTANCE;
            }
        }
    }

    private final void setupGitButtons() {
        updateGitButtonsVisibility();
        getBinding().gitConnectButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda28
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupGitButtons$lambda$43(this.f$0, view);
            }
        });
        getBinding().gitPublishButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda29
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupGitButtons$lambda$44(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupGitButtons$lambda$43(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleGitConnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupGitButtons$lambda$44(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showCommitMessageDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateGitButtonsVisibility() {
        XmlHeader xmlHeader;
        Project project = this.project;
        String gitRemoteUrl = (project == null || (xmlHeader = project.getXmlHeader()) == null) ? null : xmlHeader.getGitRemoteUrl();
        boolean z = gitRemoteUrl == null || gitRemoteUrl.length() == 0;
        getBinding().gitConnectButton.setVisibility(!z ? 8 : 0);
        getBinding().gitPublishButton.setVisibility(z ? 8 : 0);
    }

    private final void handleGitConnect() {
        TokenManager tokenManager = TokenManager.INSTANCE;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        if (tokenManager.getToken(contextRequireContext) == null) {
            showLoginRequiredDialog();
        } else {
            showGitConnectDialog();
        }
    }

    private final void showLoginRequiredDialog() {
        new AlertDialog.Builder(requireContext()).setTitle("Требуется вход").setMessage("Для подключения проекта к Git необходимо войти в свой аккаунт GitHub в настройках приложения.").setPositiveButton("В настройки", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda39
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showLoginRequiredDialog$lambda$45(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showLoginRequiredDialog$lambda$45(ProjectOptionsFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0.requireContext(), (Class<?>) SettingsActivity.class));
    }

    private final void showGitConnectDialog() {
        new AlertDialog.Builder(requireContext()).setTitle("Подключение к Git").setItems(new String[]{"Создать новый репозиторий", "Подключиться к существующему"}, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda21
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showGitConnectDialog$lambda$46(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGitConnectDialog$lambda$46(ProjectOptionsFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i == 0) {
            this$0.showCreateRepoDialog();
        } else {
            this$0.showCloneRepoDialog();
        }
    }

    private final void showCreateRepoDialog() {
        View viewInflate = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_create_repo, (ViewGroup) null);
        final TextInputEditText textInputEditText = (TextInputEditText) viewInflate.findViewById(R.id.repo_name_edit_text);
        final SwitchMaterial switchMaterial = (SwitchMaterial) viewInflate.findViewById(R.id.repo_private_switch);
        new AlertDialog.Builder(requireContext()).setTitle("Создание нового репозитория").setView(viewInflate).setPositiveButton("Создать", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda47
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showCreateRepoDialog$lambda$47(textInputEditText, this, switchMaterial, dialogInterface, i);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showCreateRepoDialog$lambda$47(TextInputEditText textInputEditText, ProjectOptionsFragment this$0, SwitchMaterial switchMaterial, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = StringsKt.trim((CharSequence) String.valueOf(textInputEditText.getText())).toString();
        if (string.length() == 0) {
            ToastUtil.showError(this$0.requireContext(), "Имя репозитория не может быть пустым");
            return;
        }
        boolean zIsChecked = switchMaterial.isChecked();
        TokenManager tokenManager = TokenManager.INSTANCE;
        Context contextRequireContext = this$0.requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        String token = tokenManager.getToken(contextRequireContext);
        if (token == null) {
            return;
        }
        this$0.showProgressDialog("Создание репозитория...");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), Dispatchers.getIO(), null, new ProjectOptionsFragment$showCreateRepoDialog$1$1(this$0, token, string, zIsChecked, null), 2, null);
    }

    private final void showCloneRepoDialog() {
        View viewInflate = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_clone_repo, (ViewGroup) null);
        final TextInputEditText textInputEditText = (TextInputEditText) viewInflate.findViewById(R.id.repo_url_edit_text);
        new AlertDialog.Builder(requireContext()).setTitle("Подключение к репозиторию").setView(viewInflate).setMessage("Внимание! Текущие файлы проекта будут ЗАМЕНЕНЫ файлами из удаленного репозитория. Это действие необратимо.").setPositiveButton("Подключить", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showCloneRepoDialog$lambda$48(textInputEditText, this, dialogInterface, i);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showCloneRepoDialog$lambda$48(TextInputEditText textInputEditText, ProjectOptionsFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = StringsKt.trim((CharSequence) String.valueOf(textInputEditText.getText())).toString();
        if (!StringsKt.startsWith$default(string, "https://", false, 2, (Object) null) || !StringsKt.endsWith$default(string, ".git", false, 2, (Object) null)) {
            ToastUtil.showError(this$0.requireContext(), "Введите корректный HTTPS URL репозитория");
            return;
        }
        TokenManager tokenManager = TokenManager.INSTANCE;
        Context contextRequireContext = this$0.requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        String token = tokenManager.getToken(contextRequireContext);
        if (token == null) {
            return;
        }
        Project project = this$0.project;
        File directory = project != null ? project.getDirectory() : null;
        if (directory == null) {
            return;
        }
        File file = new File(this$0.requireContext().getCacheDir(), "git_clone_temp_" + System.currentTimeMillis());
        file.mkdirs();
        this$0.showProgressDialog("Клонирование проекта...");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this$0), Dispatchers.getIO(), null, new ProjectOptionsFragment$showCloneRepoDialog$1$1(this$0, string, token, file, directory, null), 2, null);
    }

    private final void showCommitMessageDialog() {
        final TextInputEditText textInputEditText = new TextInputEditText(requireContext());
        new AlertDialog.Builder(requireContext()).setTitle("Синхронизировать").setMessage("Введите краткое описание сделанных изменений (коммит):").setView(textInputEditText).setPositiveButton("Начать", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda46
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showCommitMessageDialog$lambda$50(textInputEditText, this, dialogInterface, i);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showCommitMessageDialog$lambda$50(TextInputEditText editText, ProjectOptionsFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(editText, "$editText");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String strValueOf = String.valueOf(editText.getText());
        if (strValueOf.length() == 0) {
            strValueOf = "Update project";
        }
        this$0.handleGitPublish(strValueOf);
    }

    private final void handleGitPublish(String commitMessage) {
        TokenManager tokenManager = TokenManager.INSTANCE;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        String token = tokenManager.getToken(contextRequireContext);
        if (token == null) {
            return;
        }
        showProgressDialog("Синхронизация и публикация...");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C41931(commitMessage, token, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$handleGitPublish$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitPublish$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitPublish$1", f = "ProjectOptionsFragment.kt", i = {}, l = {XKeySym.XK_Cyrillic_ha}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41931 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $commitMessage;
        final /* synthetic */ String $token;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41931(String str, String str2, Continuation<? super C41931> continuation) {
            super(2, continuation);
            this.$commitMessage = str;
            this.$token = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectOptionsFragment.this.new C41931(this.$commitMessage, this.$token, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41931) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ProjectOptionsFragment.this.saveProject();
                GitController gitController = ProjectOptionsFragment.this.gitController;
                if (gitController == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("gitController");
                    gitController = null;
                }
                GitResult<Unit> gitResultCommitAndPush = gitController.commitAndPush(this.$commitMessage, "NewCatroid_user", "nc_user@email.com", this.$token);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02631(ProjectOptionsFragment.this, gitResultCommitAndPush, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$handleGitPublish$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitPublish$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitPublish$1$1", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02631 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ GitResult<Unit> $result;
            int label;
            final /* synthetic */ ProjectOptionsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02631(ProjectOptionsFragment projectOptionsFragment, GitResult<Unit> gitResult, Continuation<? super C02631> continuation) {
                super(2, continuation);
                this.this$0 = projectOptionsFragment;
                this.$result = gitResult;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02631(this.this$0, this.$result, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02631) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.hideProgressDialog();
                    GitResult<Unit> gitResult = this.$result;
                    if (gitResult instanceof GitResult.Success) {
                        ToastUtil.showSuccess(this.this$0.requireContext(), "Синхронизация завершена!");
                    } else if (gitResult instanceof GitResult.Error) {
                        ToastUtil.showError(this.this$0.requireContext(), "Ошибка: " + ((GitResult.Error) this.$result).getMessage());
                    }
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    private final void handleGitUpdate() {
        TokenManager tokenManager = TokenManager.INSTANCE;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        String token = tokenManager.getToken(contextRequireContext);
        if (token == null) {
            return;
        }
        showProgressDialog("Обновление проекта...");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C41941(token, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$handleGitUpdate$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitUpdate$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitUpdate$1", f = "ProjectOptionsFragment.kt", i = {}, l = {XKeySym.XK_Cyrillic_softsign}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41941 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $token;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41941(String str, Continuation<? super C41941> continuation) {
            super(2, continuation);
            this.$token = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectOptionsFragment.this.new C41941(this.$token, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41941) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                GitController gitController = ProjectOptionsFragment.this.gitController;
                if (gitController == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("gitController");
                    gitController = null;
                }
                GitResult<MergeResult> gitResultPullAndMerge = gitController.pullAndMerge(this.$token);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02641(ProjectOptionsFragment.this, gitResultPullAndMerge, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$handleGitUpdate$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitUpdate$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitUpdate$1$1", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02641 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ GitResult<MergeResult> $result;
            int label;
            final /* synthetic */ ProjectOptionsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02641(ProjectOptionsFragment projectOptionsFragment, GitResult<MergeResult> gitResult, Continuation<? super C02641> continuation) {
                super(2, continuation);
                this.this$0 = projectOptionsFragment;
                this.$result = gitResult;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02641(this.this$0, this.$result, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02641) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.hideProgressDialog();
                    GitResult<MergeResult> gitResult = this.$result;
                    if (gitResult instanceof GitResult.Success) {
                        ToastUtil.showSuccess(this.this$0.requireContext(), "Проект обновлен!");
                        this.this$0.shouldSaveOnPause = false;
                        Project mergedProject = ((MergeResult) ((GitResult.Success) this.$result).getData()).getMergedProject();
                        this.this$0.project = mergedProject;
                        this.this$0.getProjectManager().setCurrentProject(mergedProject);
                        if (((MergeResult) ((GitResult.Success) this.$result).getData()).getConflicts().isEmpty()) {
                            this.this$0.showProjectReloadDialog();
                        } else {
                            String strJoinToString$default = CollectionsKt.joinToString$default(((MergeResult) ((GitResult.Success) this.$result).getData()).getConflicts(), "\n", null, null, 0, null, new Function1<Conflict, CharSequence>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitUpdate$1$1$conflictsString$1
                                @Override // kotlin.jvm.functions.Function1
                                public final CharSequence invoke(Conflict it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    return "- " + it.getPath();
                                }
                            }, 30, null);
                            final ProjectOptionsFragment projectOptionsFragment = this.this$0;
                            new AlertDialog.Builder(this.this$0.requireContext()).setTitle("Обнаружены конфликты").setMessage("Система обнаружила конфликты, думайте сами какой вариант оставить. Конфликты:\n" + strJoinToString$default).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitUpdate$1$1$$ExternalSyntheticLambda0
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i) {
                                    ProjectOptionsFragment.access$showProjectReloadDialog(projectOptionsFragment);
                                }
                            }).show();
                        }
                    } else if (gitResult instanceof GitResult.Error) {
                        ToastUtil.showError(this.this$0.requireContext(), "Ошибка: " + ((GitResult.Error) this.$result).getMessage());
                    } else if (gitResult instanceof GitResult.MergeConflict) {
                        new AlertDialog.Builder(this.this$0.requireContext()).setTitle("Конфликты слияния!").setMessage("Не удалось автоматически обновить проект. Конфликты:\n" + CollectionsKt.joinToString$default(((GitResult.MergeConflict) gitResult).getConflicts(), "\n", null, null, 0, null, new Function1<Conflict, CharSequence>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$handleGitUpdate$1$1$conflictsString$2
                            @Override // kotlin.jvm.functions.Function1
                            public final CharSequence invoke(Conflict it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return "- " + it.getFieldName();
                            }
                        }, 30, null)).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
                    }
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showProgressDialog(String message) {
        this.progressDialog = new AlertDialog.Builder(requireContext()).setCancelable(false).setView(R.layout.dialog_progress).setMessage(message).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideProgressDialog() {
        AlertDialog alertDialog = this.progressDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showProjectReloadDialog() {
        new AlertDialog.Builder(requireContext()).setTitle("Требуется перезагрузка").setMessage("Проект был изменен. Чтобы увидеть изменения, необходимо его перезапустить.").setPositiveButton("Перезапустить", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda24
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showProjectReloadDialog$lambda$51(this.f$0, dialogInterface, i);
            }
        }).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showProjectReloadDialog$lambda$51(ProjectOptionsFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.requireActivity().finish();
    }

    private final void setupNameInputLayout() {
        final EditText editText = getBinding().projectOptionsNameLayout.getEditText();
        if (editText != null) {
            Project project = this.project;
            editText.setText(project != null ? project.getName() : null);
            editText.addTextChangedListener(new NewProjectNameTextWatcher<Nameable>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$setupNameInputLayout$1$1
                @Override // org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher.TextWatcher, android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                    String strValueOf = String.valueOf(s);
                    Project project2 = this.this$0.project;
                    Intrinsics.checkNotNull(project2);
                    this.this$0.getBinding().projectOptionsNameLayout.setError(!Intrinsics.areEqual(strValueOf, project2.getName()) ? validateInput(String.valueOf(s), editText.getContext()) : null);
                }
            });
        }
    }

    private final void setupPhysicsInputLayout() {
        Project project = this.project;
        XmlHeader xmlHeader = project != null ? project.getXmlHeader() : null;
        final EditText editText = getBinding().projectOptionsPhysicsWidthLayout.getEditText();
        if (editText != null) {
            editText.setText(String.valueOf(xmlHeader != null ? Float.valueOf(xmlHeader.getPhysicsWidthArea()) : null));
            editText.addTextChangedListener(new NewProjectNameTextWatcher<Nameable>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$setupPhysicsInputLayout$1$1
                @Override // org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher.TextWatcher, android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                    String strValueOf = String.valueOf(s);
                    Project project2 = this.this$0.project;
                    Intrinsics.checkNotNull(project2);
                    this.this$0.getBinding().projectOptionsPhysicsWidthLayout.setError(!Intrinsics.areEqual(strValueOf, project2.getName()) ? validatePhysicsInput(String.valueOf(s), editText.getContext()) : null);
                }
            });
        }
        final EditText editText2 = getBinding().projectOptionsPhysicsHeightLayout.getEditText();
        if (editText2 != null) {
            editText2.setText(String.valueOf(xmlHeader != null ? Float.valueOf(xmlHeader.getPhysicsHeightArea()) : null));
            editText2.addTextChangedListener(new NewProjectNameTextWatcher<Nameable>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$setupPhysicsInputLayout$2$1
                @Override // org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher.TextWatcher, android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                    String strValueOf = String.valueOf(s);
                    Project project2 = this.this$0.project;
                    Intrinsics.checkNotNull(project2);
                    this.this$0.getBinding().projectOptionsPhysicsHeightLayout.setError(!Intrinsics.areEqual(strValueOf, project2.getName()) ? validatePhysicsInput(String.valueOf(s), editText2.getContext()) : null);
                }
            });
        }
    }

    private final void setupDescriptionInputLayout() {
        EditText editText = getBinding().projectOptionsDescriptionLayout.getEditText();
        if (editText != null) {
            Project project = this.project;
            editText.setText(project != null ? project.getDescription() : null);
        }
        getBinding().projectOptionsDescriptionLayout.setEndIconOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda40
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupDescriptionInputLayout$lambda$55(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupDescriptionInputLayout$lambda$55(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showImagePickerDialog();
    }

    private final void showImagePickerDialog() {
        Project project = this.project;
        File filesDir = project != null ? project.getFilesDir() : null;
        if (filesDir == null) {
            return;
        }
        File[] fileArrListFiles = filesDir.listFiles(new FileFilter() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda17
            @Override // java.io.FileFilter
            public final boolean accept(File file) {
                return ProjectOptionsFragment.showImagePickerDialog$lambda$56(file);
            }
        });
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            ToastUtil.showInfoLong(requireContext(), "В проекте нет изображений.");
            return;
        }
        Intrinsics.checkNotNull(fileArrListFiles);
        ArrayList arrayList = new ArrayList(fileArrListFiles.length);
        for (File file : fileArrListFiles) {
            arrayList.add(file.getName());
        }
        final String[] strArr = (String[]) arrayList.toArray(new String[0]);
        new AlertDialog.Builder(requireContext()).setTitle("Вставить изображение").setItems(strArr, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda18
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectOptionsFragment.showImagePickerDialog$lambda$58(strArr, this, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean showImagePickerDialog$lambda$56(File file) {
        if (!file.isFile()) {
            return false;
        }
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"png", "jpg", "jpeg", "webp", "gif"});
        Intrinsics.checkNotNull(file);
        String lowerCase = FilesKt.getExtension(file).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return listListOf.contains(lowerCase);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showImagePickerDialog$lambda$58(String[] imageFileNames, ProjectOptionsFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(imageFileNames, "$imageFileNames");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String str = imageFileNames[i];
        Intrinsics.checkNotNull(str);
        this$0.insertImageMarkdown(str);
    }

    private final void insertImageMarkdown(String fileName) {
        EditText editText = getBinding().projectOptionsDescriptionLayout.getEditText();
        if (editText == null) {
            return;
        }
        String str = "\n![" + fileName + "](" + fileName + ")\n";
        int selectionStart = editText.getSelectionStart();
        Editable text = editText.getText();
        if (text != null) {
            text.insert(selectionStart, str);
        }
    }

    private final void setupNotesAndCreditsInputLayout() {
        EditText editText = getBinding().projectOptionsNotesAndCreditsLayout.getEditText();
        if (editText != null) {
            Project project = this.project;
            editText.setText(project != null ? project.getNotesAndCredits() : null);
        }
    }

    private final void addTags() {
        getBinding().chipGroupTags.removeAllViews();
        Project project = this.project;
        Intrinsics.checkNotNull(project);
        List<String> tags = project.getTags();
        if (tags.size() == 1) {
            String str = tags.get(0);
            Intrinsics.checkNotNullExpressionValue(str, "get(...)");
            if (str.length() == 0) {
                getBinding().tags.setVisibility(8);
                return;
            }
        }
        getBinding().tags.setVisibility(0);
        for (String str2 : tags) {
            Chip chip = new Chip(getContext());
            chip.setText(str2);
            chip.setClickable(false);
            getBinding().chipGroupTags.addView(chip);
        }
    }

    private final void setupProjectAspectRatio() {
        SwitchMaterial switchMaterial = getBinding().projectOptionsAspectRatio;
        Project project = this.project;
        switchMaterial.setChecked((project != null ? project.getScreenMode() : null) == ScreenModes.MAXIMIZE);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda38
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ProjectOptionsFragment.setupProjectAspectRatio$lambda$60$lambda$59(this.f$0, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupProjectAspectRatio$lambda$60$lambda$59(ProjectOptionsFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleAspectRatioChecked(z);
    }

    private final void setupCustomResolution() {
        XmlHeader xmlHeader;
        SwitchMaterial switchMaterial = getBinding().projectOptionsCustomResolution;
        Project project = this.project;
        switchMaterial.setChecked((project == null || (xmlHeader = project.getXmlHeader()) == null) ? false : Intrinsics.areEqual((Object) xmlHeader.customResolution, (Object) true));
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda42
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ProjectOptionsFragment.setupCustomResolution$lambda$62$lambda$61(this.f$0, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupCustomResolution$lambda$62$lambda$61(ProjectOptionsFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleCustomResolutionChecked(z);
    }

    private final void setupProjectUpload() {
        getBinding().projectOptionsUpload.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupProjectUpload$lambda$63(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupProjectUpload$lambda$63(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.exportMatryoshkaForServer();
    }

    private final void exportMatryoshkaForServer() {
        saveProject();
        Project project = this.project;
        if (project == null) {
            return;
        }
        showProgressDialog("Сборка матрешки для сервера...");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C41911(project, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$exportMatryoshkaForServer$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportMatryoshkaForServer$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportMatryoshkaForServer$1", f = "ProjectOptionsFragment.kt", i = {}, l = {XKeySym.XK_Greek_UPSILONdieresis, XKeySym.XK_Greek_horizbar}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41911 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Project $currentProject;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41911(Project project, Continuation<? super C41911> continuation) {
            super(2, continuation);
            this.$currentProject = project;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectOptionsFragment.this.new C41911(this.$currentProject, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41911) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    File filePackForUpload = MatryoshkaManager.packForUpload(ProjectOptionsFragment.this.requireContext(), this.$currentProject);
                    this.label = 1;
                    if (BuildersKt.withContext(Dispatchers.getMain(), new C02621(ProjectOptionsFragment.this, filePackForUpload, null), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i == 1) {
                        ResultKt.throwOnFailure(obj);
                    } else {
                        if (i != 2) {
                            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            } catch (Exception e) {
                this.label = 2;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass2(ProjectOptionsFragment.this, e, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$exportMatryoshkaForServer$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportMatryoshkaForServer$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportMatryoshkaForServer$1$1", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02621 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ File $readyToUploadZip;
            int label;
            final /* synthetic */ ProjectOptionsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02621(ProjectOptionsFragment projectOptionsFragment, File file, Continuation<? super C02621> continuation) {
                super(2, continuation);
                this.this$0 = projectOptionsFragment;
                this.$readyToUploadZip = file;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02621(this.this$0, this.$readyToUploadZip, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02621) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.hideProgressDialog();
                    ToastUtil.showSuccess(this.this$0.requireContext(), "Матрешка собрана!");
                    ProjectOptionsFragment projectOptionsFragment = this.this$0;
                    File readyToUploadZip = this.$readyToUploadZip;
                    Intrinsics.checkNotNullExpressionValue(readyToUploadZip, "$readyToUploadZip");
                    projectOptionsFragment.shareFile(readyToUploadZip);
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$exportMatryoshkaForServer$1$2.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportMatryoshkaForServer$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$exportMatryoshkaForServer$1$2", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Exception $e;
            int label;
            final /* synthetic */ ProjectOptionsFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(ProjectOptionsFragment projectOptionsFragment, Exception exc, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = projectOptionsFragment;
                this.$e = exc;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, this.$e, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.hideProgressDialog();
                    ToastUtil.showError(this.this$0.requireContext(), "Ошибка: " + this.$e.getMessage());
                    this.$e.printStackTrace();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    private final void setupProjectSaveExternal() {
        getBinding().projectOptionsSaveExternal.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupProjectSaveExternal$lambda$64(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupProjectSaveExternal$lambda$64(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.exportProject();
    }

    private final void setupClearVars() {
        getBinding().projectOptionsClearVars.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda37
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupClearVars$lambda$65(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupClearVars$lambda$65(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.clearVars();
    }

    private final void setupChangeIcon() {
        getBinding().projectOptionsChangeIcon.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupChangeIcon$lambda$66(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupChangeIcon$lambda$66(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeIcon();
    }

    private final void setupChangeOrientation() {
        getBinding().projectOptionsChangeOrientation.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupChangeOrientation$lambda$67(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupChangeOrientation$lambda$67(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeOrientation();
    }

    private final void setupProjectMoreDetails() {
        getBinding().projectOptionsMoreDetails.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupProjectMoreDetails$lambda$68(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupProjectMoreDetails$lambda$68(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.moreDetails();
    }

    private final void setupProjectOptionDelete() {
        getBinding().projectOptionsDelete.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda43
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectOptionsFragment.setupProjectOptionDelete$lambda$69(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupProjectOptionDelete$lambda$69(ProjectOptionsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleDeleteButtonPressed();
    }

    private final void handleAspectRatioChecked(boolean checked) {
        ScreenModes screenModes;
        Project project = this.project;
        if (project == null) {
            return;
        }
        if (checked) {
            screenModes = ScreenModes.MAXIMIZE;
        } else {
            screenModes = ScreenModes.STRETCH;
        }
        project.setScreenMode(screenModes);
    }

    private final void handleCustomResolutionChecked(boolean checked) {
        XmlHeader xmlHeader;
        Project project = this.project;
        if (project == null || (xmlHeader = project.getXmlHeader()) == null) {
            return;
        }
        xmlHeader.setCustomResolution(Boolean.valueOf(checked));
    }

    private final void handleDeleteButtonPressed() {
        Project project = this.project;
        if (project == null) {
            return;
        }
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        File directory = project.getDirectory();
        Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
        ProjectTrashManager.showDeleteProjectDialog(contextRequireContext, directory, new Runnable() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                ProjectOptionsFragment.handleDeleteButtonPressed$lambda$70(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDeleteButtonPressed$lambda$70(ProjectOptionsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.project = null;
        this$0.getProjectManager().setCurrentProject(null);
        this$0.requireActivity().onBackPressed();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            menu.getItem(i).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        if (this.shouldSaveOnPause) {
            saveProject();
        }
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void saveProject() {
        if (this.project == null) {
            return;
        }
        setProjectName();
        setPhysicsArea();
        saveDescription();
        saveCreditsAndNotes();
        Project project = this.project;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        ProjectSaverKt.saveProjectSerial(project, contextRequireContext);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getProjectManager().setCurrentProject(this.project);
        EditText editText = getBinding().projectOptionsNameLayout.getEditText();
        if (editText != null) {
            Project project = this.project;
            editText.setText(project != null ? project.getName() : null);
        }
        setupDescriptionInputLayout();
        setupNotesAndCreditsInputLayout();
        addTags();
        BottomBar.hideBottomBar(requireActivity());
    }

    private final void setProjectName() {
        EditText editText = getBinding().projectOptionsNameLayout.getEditText();
        String string = StringsKt.trim((CharSequence) String.valueOf(editText != null ? editText.getText() : null)).toString();
        Project project = this.project;
        if (project == null) {
            return;
        }
        Intrinsics.checkNotNull(project);
        if (Intrinsics.areEqual(project.getName(), string)) {
            return;
        }
        XstreamSerializer.getInstance().saveProject(this.project);
        Project project2 = this.project;
        Intrinsics.checkNotNull(project2);
        File directory = project2.getDirectory();
        Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
        File fileRenameProject = ProjectRenamerKt.renameProject(directory, string);
        if (fileRenameProject == null) {
            Log.e(TAG, "Creating renamed directory failed!");
            return;
        }
        Context applicationContext = requireContext().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        ProjectLoaderKt.loadProject(fileRenameProject, applicationContext);
        this.project = getProjectManager().getCurrentProject();
        ProjectManager projectManager = getProjectManager();
        Project project3 = this.project;
        Intrinsics.checkNotNull(project3);
        projectManager.setCurrentlyEditedScene(project3.getSceneByName(this.sceneName));
    }

    private final void setPhysicsArea() {
        EditText editText = getBinding().projectOptionsPhysicsWidthLayout.getEditText();
        float f = Float.parseFloat(String.valueOf(editText != null ? editText.getText() : null));
        EditText editText2 = getBinding().projectOptionsPhysicsHeightLayout.getEditText();
        float f2 = Float.parseFloat(String.valueOf(editText2 != null ? editText2.getText() : null));
        Project project = this.project;
        if (project == null) {
            return;
        }
        XmlHeader xmlHeader = project != null ? project.getXmlHeader() : null;
        if (xmlHeader != null) {
            xmlHeader.setPhysicsWidthArea(f);
        }
        if (xmlHeader != null) {
            xmlHeader.setPhysicsHeightArea(f2);
        }
    }

    public final void saveDescription() {
        EditText editText = getBinding().projectOptionsDescriptionLayout.getEditText();
        String string = StringsKt.trim((CharSequence) String.valueOf(editText != null ? editText.getText() : null)).toString();
        Project project = this.project;
        if ((project != null ? project.getDescription() : null) != null) {
            Project project2 = this.project;
            if (Intrinsics.areEqual(project2 != null ? project2.getDescription() : null, string)) {
                return;
            }
        }
        Project project3 = this.project;
        if (project3 != null) {
            project3.setDescription(string);
        }
        if (XstreamSerializer.getInstance().saveProject(this.project)) {
            return;
        }
        ToastUtil.showError(getActivity(), R.string.error_set_description);
    }

    public final void saveCreditsAndNotes() {
        EditText editText = getBinding().projectOptionsNotesAndCreditsLayout.getEditText();
        String string = StringsKt.trim((CharSequence) String.valueOf(editText != null ? editText.getText() : null)).toString();
        Project project = this.project;
        if ((project != null ? project.getNotesAndCredits() : null) != null) {
            Project project2 = this.project;
            if (Intrinsics.areEqual(project2 != null ? project2.getNotesAndCredits() : null, string)) {
                return;
            }
        }
        Project project3 = this.project;
        if (project3 != null) {
            project3.setNotesAndCredits(string);
        }
        if (XstreamSerializer.getInstance().saveProject(this.project)) {
            return;
        }
        ToastUtil.showError(requireContext(), R.string.error_set_notes_and_credits);
    }

    public final void projectUpload() {
        Project currentProject = getProjectManager().getCurrentProject();
        Intrinsics.checkNotNull(currentProject);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        ProjectSaver.saveProjectAsync$default(new ProjectSaver(currentProject, contextRequireContext), new Function1<Boolean, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment.projectUpload.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z) {
                ProjectOptionsFragment.this.onSaveProjectComplete();
            }
        }, null, 2, null);
        Utils.setLastUsedProjectName(requireContext(), currentProject.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSaveProjectComplete() {
        Project currentProject = getProjectManager().getCurrentProject();
        if (Utils.isDefaultProject(currentProject, getActivity())) {
            getBinding().getRoot();
            Snackbar.make(getBinding().getRoot(), R.string.error_upload_default_project, 0).show();
        } else {
            Intent intent = new Intent(requireContext(), (Class<?>) ProjectUploadActivity.class);
            intent.putExtra(ProjectUploadActivityKt.PROJECT_DIR, currentProject.getDirectory());
            startActivity(intent);
        }
    }

    public final void copyInputStreamToFile(Context context, InputStream inputStream, File outputFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        InputStream inputStream2 = inputStream;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            try {
                ByteStreamsKt.copyTo$default(inputStream2, fileOutputStream, 0, 2, null);
                CloseableKt.closeFinally(fileOutputStream, null);
                CloseableKt.closeFinally(inputStream2, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(inputStream2, th3);
                throw th4;
            }
        }
    }

    public final File createApkFromTemplate(Context context, File projectZipFile) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectZipFile, "projectZipFile");
        File file = new File(context.getCacheDir(), "apk_temp");
        file.mkdirs();
        InputStream inputStreamOpen = context.getAssets().open("apk_template.zip");
        Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "open(...)");
        unzip(inputStreamOpen, file);
        FilesKt.copyTo$default(projectZipFile, new File(file, "assets/project.zip"), true, 0, 4, null);
        File file2 = new File(context.getCacheDir(), "unsigned_project_build.apk");
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file2));
        try {
            zipDirectory3$default(this, file, zipOutputStream, null, 4, null);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipOutputStream, null);
            FilesKt.deleteRecursively(file);
            File file3 = new File(context.getCacheDir(), "signed_project_build.apk");
            InputStream inputStreamOpen2 = CatroidApplication.getAppContext().getAssets().open("debug.jks");
            Intrinsics.checkNotNullExpressionValue(inputStreamOpen2, "open(...)");
            File file4 = new File(context.getFilesDir(), "debug.jks");
            Context appContext = CatroidApplication.getAppContext();
            Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
            copyInputStreamToFile(appContext, inputStreamOpen2, file4);
            signApkWithApksig(context, file2, file3, "debug.p12", "keystore", "dbg", "keystore");
            file2.delete();
            return file3;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(zipOutputStream, th);
                throw th2;
            }
        }
    }

    public final void copyKeystoreFromAssets(Context context, String keystoreFileName, File destFile) throws IOException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(keystoreFileName, "keystoreFileName");
        Intrinsics.checkNotNullParameter(destFile, "destFile");
        InputStream inputStreamOpen = context.getAssets().open(keystoreFileName);
        try {
            InputStream inputStream = inputStreamOpen;
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            try {
                Intrinsics.checkNotNull(inputStream);
                ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                CloseableKt.closeFinally(fileOutputStream, null);
                CloseableKt.closeFinally(inputStreamOpen, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(inputStreamOpen, th3);
                throw th4;
            }
        }
    }

    public final void signApkWithApksig(Context context, File unsignedApk, File signedApk, String keystoreAssetName, String keystorePassword, String keyAlias, String keyPassword) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(unsignedApk, "unsignedApk");
        Intrinsics.checkNotNullParameter(signedApk, "signedApk");
        Intrinsics.checkNotNullParameter(keystoreAssetName, "keystoreAssetName");
        Intrinsics.checkNotNullParameter(keystorePassword, "keystorePassword");
        Intrinsics.checkNotNullParameter(keyAlias, "keyAlias");
        Intrinsics.checkNotNullParameter(keyPassword, "keyPassword");
        try {
            File file = new File(context.getFilesDir(), keystoreAssetName);
            if (!file.exists()) {
                copyKeystoreFromAssets(context, keystoreAssetName, file);
            }
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream fileInputStream = new FileInputStream(file);
            char[] charArray = keystorePassword.toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
            keyStore.load(fileInputStream, charArray);
            char[] charArray2 = keyPassword.toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray2, "toCharArray(...)");
            Key key = keyStore.getKey(keyAlias, charArray2);
            Intrinsics.checkNotNull(key, "null cannot be cast to non-null type java.security.PrivateKey");
            PrivateKey privateKey = (PrivateKey) key;
            Certificate[] certificateChain = keyStore.getCertificateChain(keyAlias);
            Intrinsics.checkNotNullExpressionValue(certificateChain, "getCertificateChain(...)");
            Certificate[] certificateArr = certificateChain;
            ArrayList arrayList = new ArrayList(certificateArr.length);
            for (Certificate certificate : certificateArr) {
                Intrinsics.checkNotNull(certificate, "null cannot be cast to non-null type java.security.cert.X509Certificate");
                arrayList.add((X509Certificate) certificate);
            }
            new ApkSigner.Builder((List<ApkSigner.SignerConfig>) CollectionsKt.listOf(new ApkSigner.SignerConfig.Builder(keyAlias, privateKey, CollectionsKt.toList(arrayList)).build())).setInputApk(unsignedApk).setOutputApk(signedApk).build().sign();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при подписании APK: " + e.getMessage(), e);
        }
    }

    public final void unzip(InputStream inputStream, File outDir) {
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        Intrinsics.checkNotNullParameter(outDir, "outDir");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        try {
            ZipInputStream zipInputStream2 = zipInputStream;
            while (true) {
                ZipEntry nextEntry = zipInputStream2.getNextEntry();
                if (nextEntry != null) {
                    Intrinsics.checkNotNull(nextEntry);
                    File file = new File(outDir, nextEntry.getName());
                    if (nextEntry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        file.getParentFile().mkdirs();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            ByteStreamsKt.copyTo$default(zipInputStream2, fileOutputStream, 0, 2, null);
                            CloseableKt.closeFinally(fileOutputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream, th);
                                throw th2;
                            }
                        }
                    }
                    zipInputStream2.closeEntry();
                } else {
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(zipInputStream, null);
                    return;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(zipInputStream, th3);
                throw th4;
            }
        }
    }

    public static /* synthetic */ void zipDirectory3$default(ProjectOptionsFragment projectOptionsFragment, File file, ZipOutputStream zipOutputStream, String str, int i, Object obj) throws IOException {
        if ((i & 4) != 0) {
            str = "";
        }
        projectOptionsFragment.zipDirectory3(file, zipOutputStream, str);
    }

    public final void zipDirectory3(File dir, ZipOutputStream zos, String basePath) throws IOException {
        Intrinsics.checkNotNullParameter(dir, "dir");
        Intrinsics.checkNotNullParameter(zos, "zos");
        Intrinsics.checkNotNullParameter(basePath, "basePath");
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                String name2 = basePath.length() == 0 ? file.getName() : basePath + "/" + file.getName();
                if (file.isDirectory()) {
                    zos.putNextEntry(new ZipEntry(name2 + "/"));
                    zos.closeEntry();
                    Intrinsics.checkNotNull(file);
                    Intrinsics.checkNotNull(name2);
                    zipDirectory3(file, zos, name2);
                } else {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        zos.putNextEntry(new ZipEntry(name2));
                        ByteStreamsKt.copyTo$default(fileInputStream, zos, 0, 2, null);
                        zos.closeEntry();
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(fileInputStream, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(fileInputStream, th);
                            throw th2;
                        }
                    }
                }
            }
        }
    }

    private final void exportProject() {
        saveProject();
        if (Build.VERSION.SDK_INT >= 29) {
            exportUsingSystemFilePicker();
        } else {
            exportToExternalMemory();
        }
    }

    public final void showToast(String toast) {
        Intrinsics.checkNotNullParameter(toast, "toast");
        if (StageActivity.messageHandler != null) {
            StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(toast))).sendToTarget();
        } else {
            Log.e("ShowToast", "messageHandler is null!");
        }
    }

    public final String getRandomMessage() {
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"Готово!", "Сделано!", "Успех!", "Завершено!", "Готово к использованию!", "Задача выполнена!", "Отличная работа!", "Все готово!", "Яйцо или курица..?", "Готово! Проверяй!", "Поехали!", "Вроде сделано..", "Проверяй, начальник э!", "Готово. Удачи с проектом!", "Работа завершена, как кофе на утро!", "Готово! Как будто я маг, а не программист!", "Все сделано! Как раз вовремя перед обедом.", "Все завершено! Можно идти за пирожками!", "Задача выполнена! Теперь можно отдохнуть и посмотреть котиков.", "Готово! Даже не успел заметить, как это произошло.", "Сделано! Осталось только отпраздновать с танцами.", "Готово! Минутка успокоения перед новыми приключениями.", "Отличная работа! Ты как супергерой, только без плаща.", "Готово! Наконец-то смогу отвлечься на онлайн-шопинг.", "Как сказать: «Сделай это» и получить: «Сделано!»? Вот так!", "Все готово! Теперь можем заниматься более важными делами.", "Задача выполнена! Как хорошая книга – не отпускает до последней страницы.", "Готово! Можно отдыхать, как будто мы все это сделали за пятюню.", "Сделано! Готовы к новым подвигам?"});
        return (String) listListOf.get(Random.INSTANCE.nextInt(listListOf.size()));
    }

    public final String getRandomError() {
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"Произошла ошибка! Кажется, я не тот алгоритм заказывал.", "Упс! Что-то пошло не так. Как будто кошка пробежала по клавиатуре.", "Произошла ошибка! Может, система решила немного отдохнуть?", "Ой! Похоже, произошла ошибка. Возможно, это программистская шутка?", "Произошла ошибка! Да кто придумал обновлять программу перед дедлайном?", "Упс! Ошибка. Наверное, мой код тоже решил поспать.", "Произошла ошибка! Как бы я ни старался, выводы не совпали.", "Ой-ой! Ошибка! Это как раз то, что нам нужно было избежать.", "Произошла ошибка! По всей видимости, сервер тоже устал.", "Упс! Ошибка. Это как забыть о важной встрече.", "Произошла ошибка! Может, стоит заказывать пиццу вместо кода?", "Ой! Ошибка. Обычно говорят, что все дороги ведут к Риму, но не сегодня.", "Произошла ошибка! Это не то, что я хотел об этом напомнить.", "Упс! Ошибка! Возможно, машина решила, что у нее выходной.", "Произошла ошибка! Я попытался угостить код печеньками и вот что вышло!", "Ой-ой! Ошибка. Наверное, в коде слишком много любопытных переменных.", "Произошла ошибка! Извините, не я такой - жизнь такая!", "Упс! Произошла ошибка. Код сам по себе иногда делает капризы.", "Ой! Произошла ошибка! Как будто интернет пошел на пикник без меня.", "Произошла ошибка! И тут, конечно, глюк всегда оказывается виноват.", "Упс! Ошибка. Вы знаете, прощать - это тоже искусство."});
        return (String) listListOf.get(Random.INSTANCE.nextInt(listListOf.size()));
    }

    private final void changeOrientation() {
        XmlHeader xmlHeader;
        XmlHeader xmlHeader2;
        XmlHeader xmlHeader3;
        XmlHeader xmlHeader4;
        saveProject();
        Project project = this.project;
        int virtualScreenWidth = (project == null || (xmlHeader4 = project.getXmlHeader()) == null) ? ParserBasicInformation.NUM_RULES : xmlHeader4.getVirtualScreenWidth();
        Project project2 = this.project;
        int virtualScreenHeight = (project2 == null || (xmlHeader3 = project2.getXmlHeader()) == null) ? MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT : xmlHeader3.getVirtualScreenHeight();
        Project project3 = this.project;
        if (project3 != null && (xmlHeader2 = project3.getXmlHeader()) != null) {
            xmlHeader2.setVirtualScreenWidth(virtualScreenHeight);
        }
        Project project4 = this.project;
        if (project4 != null && (xmlHeader = project4.getXmlHeader()) != null) {
            xmlHeader.setVirtualScreenHeight(virtualScreenWidth);
        }
        showToast(getRandomMessage());
    }

    private final void changeIcon() {
        Unit unit;
        saveProject();
        Project project = this.project;
        if (project != null) {
            File directory = project.getDirectory();
            Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
            File file = new File(directory, Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
            new File(directory, Constants.SCREENSHOT_MANUAL_FILE_NAME);
            if (file.exists()) {
                file.delete();
            }
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, 12);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            showToast(getRandomError());
        }
    }

    private final void clearVars() {
        Unit unit;
        saveProject();
        Project project = this.project;
        if (project != null) {
            File directory = project.getDirectory();
            Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
            File file = new File(directory, Constants.DEVICE_VARIABLE_JSON_FILE_NAME);
            File file2 = new File(directory, Constants.DEVICE_LIST_JSON_FILE_NAME);
            boolean zDelete = file.delete();
            boolean zDelete2 = file2.delete();
            if (zDelete || zDelete2) {
                showToast(getRandomMessage());
            } else {
                showToast(getRandomMessage());
            }
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            showToast(getRandomError());
        }
    }

    private final void buildApk() {
        saveProject();
        if (Build.VERSION.SDK_INT >= 29) {
            buildUsingSystemFilePicker();
        } else {
            buildToExternalMemory();
        }
    }

    private final void exportUsingSystemFilePicker() {
        Project project = this.project;
        String str = (project != null ? project.getName() : null) + Constants.CATROBAT_EXTENSION;
        Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.putExtra("android.intent.extra.TITLE", str);
        intent.setType("*/*");
        intent.putExtra("android.provider.extra.INITIAL_URI", Environment.DIRECTORY_DOWNLOADS);
        String string = requireContext().getString(R.string.export_project);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        startActivityForResult(Intent.createChooser(intent, string), 10);
    }

    public final File zipDirectory(File sourceDir, File zipFile) {
        Intrinsics.checkNotNullParameter(sourceDir, "sourceDir");
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        try {
            ZipOutputStream zipOutputStream2 = zipOutputStream;
            for (File file : FilesKt.walk$default(sourceDir, null, 1, null)) {
                if (!Intrinsics.areEqual(file.getName(), Constants.UNDO_CODE_XML_FILE_NAME)) {
                    zipOutputStream2.putNextEntry(new ZipEntry(FilesKt.relativeTo(file, sourceDir).getPath()));
                    if (file.isFile()) {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        try {
                            ByteStreamsKt.copyTo$default(fileInputStream, zipOutputStream2, 0, 2, null);
                            CloseableKt.closeFinally(fileInputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileInputStream, th);
                                throw th2;
                            }
                        }
                    }
                    zipOutputStream2.closeEntry();
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipOutputStream, null);
            return zipFile;
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(zipOutputStream, th3);
                throw th4;
            }
        }
    }

    private final void buildUsingSystemFilePicker() {
        File directory;
        try {
            Project project = this.project;
            Log.d("BUILD", String.valueOf((project == null || (directory = project.getDirectory()) == null) ? null : directory.getName()));
            Project project2 = this.project;
            Log.d("BUILD", String.valueOf(project2 != null ? project2.getDirectory() : null));
            Project project3 = this.project;
            File directory2 = project3 != null ? project3.getDirectory() : null;
            if (directory2 == null) {
                return;
            }
            File file = new File(CatroidApplication.getAppContext().getCacheDir(), "for_build");
            file.mkdirs();
            this.zipTempDir = file;
            File file2 = new File(file, "project.zip");
            this.projectInZip = zipDirectory(directory2, file2);
            Log.d("BUILD", "Zip файл успешно создан: " + file2.getAbsolutePath());
            Project project4 = this.project;
            String str = (project4 != null ? project4.getName() : null) + ".apk";
            this.buildFilename = str;
            Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.putExtra("android.intent.extra.TITLE", str);
            intent.setType("*/*");
            intent.putExtra("android.provider.extra.INITIAL_URI", Environment.DIRECTORY_DOWNLOADS);
            String string = requireContext().getString(R.string.build_apk);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            startActivityForResult(Intent.createChooser(intent, string), 11);
        } catch (IOException e) {
            Log.e("BUILD", "Can't start build: ", e);
        }
    }

    private final void buildToExternalMemory() {
        new RequiresPermissionTask(CollectionsKt.listOf((Object[]) new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"})) { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment.buildToExternalMemory.1
            @Override // org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask
            public void task() throws Throwable {
                File directory;
                Project project = ProjectOptionsFragment.this.project;
                Log.d("BUILD", String.valueOf((project == null || (directory = project.getDirectory()) == null) ? null : directory.getName()));
                Project project2 = ProjectOptionsFragment.this.project;
                Log.d("BUILD", String.valueOf(project2 != null ? project2.getDirectory() : null));
                Project project3 = ProjectOptionsFragment.this.project;
                File directory2 = project3 != null ? project3.getDirectory() : null;
                if (directory2 == null) {
                    return;
                }
                File file = new File(CatroidApplication.getAppContext().getCacheDir(), "for_build");
                file.mkdirs();
                ProjectOptionsFragment.this.zipTempDir = file;
                File file2 = new File(file, "project.zip");
                ProjectOptionsFragment projectOptionsFragment = ProjectOptionsFragment.this;
                projectOptionsFragment.projectInZip = projectOptionsFragment.zipDirectory(directory2, file2);
                Log.d("BUILD", "Zip файл успешно создан: " + file2.getAbsolutePath());
                Project project4 = ProjectOptionsFragment.this.project;
                String str = (project4 != null ? project4.getName() : null) + ".apk";
                ProjectOptionsFragment.this.buildFilename = str;
                File file3 = new File(Constants.DOWNLOAD_DIRECTORY, str);
                Constants.DOWNLOAD_DIRECTORY.mkdirs();
                if (Constants.DOWNLOAD_DIRECTORY.isDirectory()) {
                    if (file3.exists()) {
                        file3.delete();
                    }
                    Uri uriFromFile = Uri.fromFile(file3);
                    ProjectOptionsFragment projectOptionsFragment2 = ProjectOptionsFragment.this;
                    Intrinsics.checkNotNull(uriFromFile);
                    projectOptionsFragment2.startAsyncProjectBuild(uriFromFile);
                }
            }
        }.execute(requireActivity());
    }

    private final void exportToExternalMemory() {
        new RequiresPermissionTask(CollectionsKt.listOf((Object[]) new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"})) { // from class: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment.exportToExternalMemory.1
            @Override // org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask
            public void task() {
                Project project = ProjectOptionsFragment.this.project;
                File file = new File(Constants.DOWNLOAD_DIRECTORY, (project != null ? project.getName() : null) + Constants.CATROBAT_EXTENSION);
                Constants.DOWNLOAD_DIRECTORY.mkdirs();
                if (Constants.DOWNLOAD_DIRECTORY.isDirectory()) {
                    if (file.exists()) {
                        file.delete();
                    }
                    Uri uriFromFile = Uri.fromFile(file);
                    ProjectOptionsFragment projectOptionsFragment = ProjectOptionsFragment.this;
                    Intrinsics.checkNotNull(uriFromFile);
                    projectOptionsFragment.startAsyncProjectExport(uriFromFile);
                }
            }
        }.execute(requireActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) throws Throwable {
        Uri data2;
        Uri data3;
        Unit unit;
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 10 && resultCode == -1) {
            Uri data4 = data.getData();
            if (data4 == null) {
                return;
            } else {
                startAsyncProjectExport(data4);
            }
        }
        if (requestCode == 11 && resultCode == -1) {
            Uri data5 = data.getData();
            if (data5 == null) {
                return;
            } else {
                startAsyncProjectBuild(data5);
            }
        }
        if (requestCode == 12 && resultCode == -1) {
            Uri data6 = data.getData();
            if (data6 != null) {
                try {
                    ContentResolver contentResolver = CatroidApplication.getAppContext().getContentResolver();
                    Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
                    InputStream inputStreamOpenInputStream = contentResolver.openInputStream(data6);
                    Project project = this.project;
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(project != null ? project.getDirectory() : null, Constants.SCREENSHOT_MANUAL_FILE_NAME));
                    if (inputStreamOpenInputStream != null) {
                        ByteStreamsKt.copyTo$default(inputStreamOpenInputStream, fileOutputStream, 0, 2, null);
                    }
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                    fileOutputStream.close();
                    showToast(getRandomMessage());
                } catch (Exception e) {
                    showToast("Ошибка при сохранении изображения: " + e.getMessage());
                }
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                showToast(getRandomError());
            }
        }
        if (requestCode == this.REQUEST_CODE_SELECT_KEYSTORE && resultCode == -1 && (data3 = data.getData()) != null) {
            try {
                File file = new File(requireContext().getCacheDir(), "custom_user_signing.jks");
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                InputStream inputStreamOpenInputStream2 = requireContext().getContentResolver().openInputStream(data3);
                if (inputStreamOpenInputStream2 != null) {
                    InputStream inputStream = inputStreamOpenInputStream2;
                    try {
                        InputStream inputStream2 = inputStream;
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                        try {
                            Intrinsics.checkNotNull(inputStream2);
                            long jCopyTo$default = ByteStreamsKt.copyTo$default(inputStream2, fileOutputStream2, 0, 2, null);
                            CloseableKt.closeFinally(fileOutputStream2, null);
                            Long.valueOf(jCopyTo$default);
                            CloseableKt.closeFinally(inputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream2, th);
                                throw th2;
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            throw th3;
                        } catch (Throwable th4) {
                            CloseableKt.closeFinally(inputStream, th3);
                            throw th4;
                        }
                    }
                }
                this.selectedCustomKeystoreFile = file;
                TextView textView = this.tvSelectedKeystorePathTag;
                if (textView != null) {
                    textView.setText(getString(R.string.keystore_loaded));
                }
                ToastUtil.showSuccess(requireContext(), getString(R.string.keystore_loaded));
            } catch (Exception e2) {
                TextView textView2 = this.tvSelectedKeystorePathTag;
                if (textView2 != null) {
                    textView2.setText(getString(R.string.error_loading_key));
                }
                ToastUtil.showError(requireContext(), getString(R.string.error_load_keystore, e2.getMessage()));
            }
        }
        if (requestCode == 1003 && resultCode == -1 && (data2 = data.getData()) != null) {
            String str = this.tempGenAlias;
            if (str == null) {
                str = "debug";
            }
            String str2 = this.tempGenPass;
            if (str2 == null) {
                str2 = "android";
            }
            String str3 = this.tempGenOwner;
            if (str3 == null) {
                str3 = "NewCatroid Owner";
            }
            try {
                try {
                    File file2 = new File(requireContext().getCacheDir(), "user_generated_keystore.p12");
                    if (file2.exists()) {
                        file2.delete();
                    }
                    ApkToolboxManager apkToolboxManager = ApkToolboxManager.INSTANCE;
                    String absolutePath = file2.getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                    if (apkToolboxManager.generateKeyStore(absolutePath, str, str2, str3) && file2.exists()) {
                        OutputStream outputStreamOpenOutputStream = requireContext().getContentResolver().openOutputStream(data2);
                        if (outputStreamOpenOutputStream != null) {
                            OutputStream outputStream = outputStreamOpenOutputStream;
                            try {
                                OutputStream outputStream2 = outputStream;
                                FileInputStream fileInputStream = new FileInputStream(file2);
                                try {
                                    long jCopyTo$default2 = ByteStreamsKt.copyTo$default(fileInputStream, outputStream2, 0, 2, null);
                                    CloseableKt.closeFinally(fileInputStream, null);
                                    Long.valueOf(jCopyTo$default2);
                                    CloseableKt.closeFinally(outputStream, null);
                                } catch (Throwable th5) {
                                    try {
                                        throw th5;
                                    } catch (Throwable th6) {
                                        CloseableKt.closeFinally(fileInputStream, th5);
                                        throw th6;
                                    }
                                }
                            } catch (Throwable th7) {
                                try {
                                    throw th7;
                                } catch (Throwable th8) {
                                    CloseableKt.closeFinally(outputStream, th7);
                                    throw th8;
                                }
                            }
                        }
                        this.selectedCustomKeystoreFile = file2;
                        View view = this.activeDialogView;
                        if (view != null) {
                            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rgKeystore);
                            TextView textView3 = (TextView) view.findViewById(R.id.tvSelectedKeystorePath);
                            TextInputEditText textInputEditText = (TextInputEditText) view.findViewById(R.id.etKeystoreAlias);
                            TextInputEditText textInputEditText2 = (TextInputEditText) view.findViewById(R.id.etKeystorePass);
                            TextInputEditText textInputEditText3 = (TextInputEditText) view.findViewById(R.id.etGenAlias);
                            if (textInputEditText3 != null) {
                                textInputEditText3.setText("");
                            }
                            TextInputEditText textInputEditText4 = (TextInputEditText) view.findViewById(R.id.etGenPass);
                            if (textInputEditText4 != null) {
                                textInputEditText4.setText("");
                            }
                            TextInputEditText textInputEditText5 = (TextInputEditText) view.findViewById(R.id.etGenOwner);
                            if (textInputEditText5 != null) {
                                textInputEditText5.setText("");
                            }
                            if (textInputEditText != null) {
                                textInputEditText.setText(str);
                            }
                            if (textInputEditText2 != null) {
                                textInputEditText2.setText(str2);
                            }
                            if (textView3 != null) {
                                textView3.setText(getString(R.string.generated_path_label));
                            }
                            if (radioGroup != null) {
                                radioGroup.check(R.id.rbCustomKeystore);
                            }
                        }
                        ToastUtil.showSuccess(requireContext(), getString(R.string.success_key_generated));
                    } else {
                        ToastUtil.showError(requireContext(), getString(R.string.error_key_failed));
                    }
                } catch (Throwable th9) {
                    this.tempGenAlias = null;
                    this.tempGenPass = null;
                    this.tempGenOwner = null;
                    throw th9;
                }
            } catch (Exception e3) {
                ToastUtil.showError(requireContext(), "Ошибка: " + e3.getMessage());
            }
            this.tempGenAlias = null;
            this.tempGenPass = null;
            this.tempGenOwner = null;
        }
    }

    public final void copyFileToUri(Context context, File sourceFile, Uri directoryUri, String fileName) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(directoryUri, "directoryUri");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(Uri.withAppendedPath(directoryUri, fileName));
        if (outputStreamOpenOutputStream != null) {
            OutputStream outputStream = outputStreamOpenOutputStream;
            try {
                OutputStream outputStream2 = outputStream;
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                try {
                    ByteStreamsKt.copyTo$default(fileInputStream, outputStream2, 0, 2, null);
                    CloseableKt.closeFinally(fileInputStream, null);
                    CloseableKt.closeFinally(outputStream, null);
                    return;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileInputStream, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(outputStream, th3);
                    throw th4;
                }
            }
        }
        System.out.println((Object) "Ошибка: Не удалось создать файл в указанной директории.");
    }

    private final void copyFileToUri2(Context context, File sourceFile, Uri destinationUri, String fileName) throws FileNotFoundException {
        OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(destinationUri);
        try {
            OutputStream outputStream = outputStreamOpenOutputStream;
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            try {
                FileInputStream fileInputStream2 = fileInputStream;
                if (outputStream != null) {
                    Intrinsics.checkNotNull(outputStream);
                    Long.valueOf(ByteStreamsKt.copyTo$default(fileInputStream2, outputStream, 0, 2, null));
                } else {
                    Integer.valueOf(Log.e("BUILD", "Ошибка открытия OutputStream для " + destinationUri));
                }
                CloseableKt.closeFinally(fileInputStream, null);
                CloseableKt.closeFinally(outputStreamOpenOutputStream, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileInputStream, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(outputStreamOpenOutputStream, th3);
                throw th4;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startAsyncProjectExport(Uri projectDestination) {
        Project project = this.project;
        if (project != null) {
            NotificationData notificationDataCreateSaveProjectToExternalMemoryNotification = new StatusBarNotificationManager(requireContext()).createSaveProjectToExternalMemoryNotification(requireContext(), projectDestination, project.getName());
            File directory = project.getDirectory();
            Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
            Intrinsics.checkNotNull(notificationDataCreateSaveProjectToExternalMemoryNotification);
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            new ProjectExportTask(directory, projectDestination, notificationDataCreateSaveProjectToExternalMemoryNotification, contextRequireContext).execute(new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startAsyncProjectBuild(Uri projectDestination) throws Throwable {
        File file;
        Project project;
        String str = this.buildFilename;
        if (str == null || (file = this.projectInZip) == null || (project = this.project) == null) {
            return;
        }
        NotificationData notificationDataCreateBuildProjectToExternalMemoryNotification = new StatusBarNotificationManager(requireContext()).createBuildProjectToExternalMemoryNotification(requireContext(), projectDestination, project.getName());
        if (file.exists()) {
            Log.d("BUILD", "Project directory: " + file.getAbsolutePath());
            Context appContext = CatroidApplication.getAppContext();
            Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
            File fileCreateApkFromTemplate = createApkFromTemplate(appContext, file);
            requireContext().grantUriPermission(requireActivity().getPackageName(), projectDestination, 2);
            Context appContext2 = CatroidApplication.getAppContext();
            Intrinsics.checkNotNullExpressionValue(appContext2, "getAppContext(...)");
            copyFileToUri2(appContext2, fileCreateApkFromTemplate, projectDestination, str);
            fileCreateApkFromTemplate.delete();
            File file2 = this.zipTempDir;
            if (file2 != null) {
                FilesKt.deleteRecursively(file2);
            }
            new StatusBarNotificationManager(getContext()).showOrUpdateNotification(getContext(), notificationDataCreateBuildProjectToExternalMemoryNotification, 100, null);
            return;
        }
        Log.e("BUILD", "Файл project.zip не существует по состоянию на момент копирования!");
    }

    private final void moreDetails() {
        ProjectDetailsFragment projectDetailsFragment = new ProjectDetailsFragment();
        Bundle bundle = new Bundle();
        Project project = this.project;
        if (project != null) {
            bundle.putSerializable(ProjectDetailsFragment.SELECTED_PROJECT_KEY, new ProjectData(project.getName(), project.getDirectory(), project.getCatrobatLanguageVersion(), project.hasScene()));
        }
        projectDetailsFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, projectDetailsFragment, ProjectDetailsFragment.INSTANCE.getTAG()).addToBackStack(ProjectDetailsFragment.INSTANCE.getTAG()).commit();
    }

    private final void deleteProject(ProjectData selectedProject) {
        try {
            StorageOperations.deleteDir(selectedProject.getDirectory());
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        ToastUtil.showSuccess(requireContext(), getResources().getQuantityString(R.plurals.deleted_projects, 1, 1));
        this.project = null;
        getProjectManager().setCurrentProject(this.project);
        requireActivity().onBackPressed();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/ProjectOptionsFragment$Companion;", "", "()V", "PERMISSIONS_REQUEST_EXPORT_TO_EXTERNAL_STORAGE", "", "REQUEST_BUILD_PROJECT", "REQUEST_CODE_GENERATE_KEYSTORE", "REQUEST_EXPORT_PROJECT", "REQUEST_SELECT_IMAGE", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ProjectOptionsFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ProjectOptionsFragment", "getSimpleName(...)");
        TAG = "ProjectOptionsFragment";
    }
}
