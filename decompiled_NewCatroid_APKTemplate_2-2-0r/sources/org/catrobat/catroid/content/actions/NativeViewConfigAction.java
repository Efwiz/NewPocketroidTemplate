package org.catrobat.catroid.content.actions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.InputDeviceCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.OverlayViewManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewConfigAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewConfigAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0004H\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0014J\u0018\u0010!\u001a\u00020\"*\u0004\u0018\u00010\u00102\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/content/actions/NativeViewConfigAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "propertySelection", "", "getPropertySelection", "()I", "setPropertySelection", "(I)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "value", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getValue", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setValue", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "viewId", "getViewId", "setViewId", "boxBlur", "Landroid/graphics/Bitmap;", "src", "range", "restart", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "interpretSafeBoolean", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeViewConfigAction extends TemporalAction {
    private int propertySelection;
    private Scope scope;
    private Formula value;
    private Formula viewId;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewId() {
        return this.viewId;
    }

    public final void setViewId(Formula formula) {
        this.viewId = formula;
    }

    public final Formula getValue() {
        return this.value;
    }

    public final void setValue(Formula formula) {
        this.value = formula;
    }

    public final int getPropertySelection() {
        return this.propertySelection;
    }

    public final void setPropertySelection(int i) {
        this.propertySelection = i;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x004a  */
    private final boolean interpretSafeBoolean(Formula formula, Scope scope) {
        String lowerCase;
        String string;
        String string2;
        if (formula == null) {
            return false;
        }
        try {
            Object objInterpretObject = formula.interpretObject(scope);
            if (objInterpretObject instanceof Boolean) {
                return ((Boolean) objInterpretObject).booleanValue();
            }
            if (objInterpretObject instanceof Number) {
                return !(((Number) objInterpretObject).doubleValue() == 0.0d);
            }
            if (objInterpretObject == null || (string = objInterpretObject.toString()) == null || (string2 = StringsKt.trim((CharSequence) string).toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string2.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                if (lowerCase == null) {
                    lowerCase = "";
                }
            }
            return Intrinsics.areEqual(lowerCase, "true") || Intrinsics.areEqual(lowerCase, "истина") || Intrinsics.areEqual(lowerCase, "да") || Intrinsics.areEqual(lowerCase, "yes") || Intrinsics.areEqual(lowerCase, SchemaSymbols.ATTVAL_TRUE_1);
        } catch (Exception unused) {
            return false;
        }
    }

    private final Bitmap boxBlur(Bitmap src, int range) {
        int i;
        int i2;
        int i3;
        int i4;
        int width = src.getWidth();
        int height = src.getHeight();
        int i5 = width * height;
        int[] iArr = new int[i5];
        int i6 = 0;
        src.getPixels(iArr, 0, width, 0, 0, width, height);
        int[] iArr2 = new int[i5];
        int i7 = (range * 2) + 1;
        int i8 = 0;
        while (i8 < height) {
            int i9 = i8 * width;
            int i10 = -range;
            if (i10 <= range) {
                i = i6;
                i2 = i;
                i3 = i2;
                i4 = i3;
                while (true) {
                    int i11 = iArr[Math.max(i6, Math.min(width - 1, i10)) + i9];
                    i += (i11 >> 16) & 255;
                    i2 += (i11 >> 8) & 255;
                    i3 += i11 & 255;
                    i4 += (i11 >> 24) & 255;
                    if (i10 == range) {
                        break;
                    }
                    i10++;
                    i6 = 0;
                }
            } else {
                i = 0;
                i2 = 0;
                i3 = 0;
                i4 = 0;
            }
            int i12 = 0;
            while (i12 < width) {
                iArr2[i9 + i12] = ((i4 / i7) << 24) | ((i / i7) << 16) | ((i2 / i7) << 8) | (i3 / i7);
                int iMax = Math.max(0, i12 - range) + i9;
                int i13 = i7;
                int iMin = Math.min(width - 1, i12 + range + 1) + i9;
                int i14 = iArr[iMax];
                int i15 = iArr[iMin];
                i += ((i15 >> 16) & 255) - ((i14 >> 16) & 255);
                i2 += ((i15 >> 8) & 255) - ((i14 >> 8) & 255);
                i3 += (i15 & 255) - (i14 & 255);
                i4 += ((i15 >> 24) & 255) - ((i14 >> 24) & 255);
                i12++;
                i9 = i9;
                i7 = i13;
            }
            i8++;
            i6 = 0;
        }
        int i16 = i7;
        int[] iArr3 = new int[i5];
        for (int i17 = 0; i17 < width; i17++) {
            int i18 = -range;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            if (i18 <= range) {
                while (true) {
                    int i23 = iArr2[(Math.max(0, Math.min(height - 1, i18)) * width) + i17];
                    i19 += (i23 >> 16) & 255;
                    i20 += (i23 >> 8) & 255;
                    i21 += i23 & 255;
                    i22 += (i23 >> 24) & 255;
                    if (i18 == range) {
                        break;
                    }
                    i18++;
                }
            }
            for (int i24 = 0; i24 < height; i24++) {
                iArr3[(i24 * width) + i17] = ((i22 / i16) << 24) | ((i19 / i16) << 16) | ((i20 / i16) << 8) | (i21 / i16);
                int iMax2 = (Math.max(0, i24 - range) * width) + i17;
                int iMin2 = (Math.min(height - 1, i24 + range + 1) * width) + i17;
                int i25 = iArr2[iMax2];
                int i26 = iArr2[iMin2];
                i19 += ((i26 >> 16) & 255) - ((i25 >> 16) & 255);
                i20 += ((i26 >> 8) & 255) - ((i25 >> 8) & 255);
                i21 += (i26 & 255) - (i25 & 255);
                i22 += ((i26 >> 24) & 255) - ((i25 >> 24) & 255);
            }
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        bitmapCreateBitmap.setPixels(iArr3, 0, width, 0, 0, width, height);
        return bitmapCreateBitmap;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        final StageActivity stageActivity;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        Formula formula = this.viewId;
        final String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.value;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        final String str = strInterpretString2 == null ? "" : strInterpretString2;
        Formula formula3 = this.value;
        final boolean zInterpretBoolean = formula3 != null ? formula3.interpretBoolean(this.scope) : false;
        final View viewFromStage = stageActivity.getViewFromStage(strInterpretString);
        if (viewFromStage == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.NativeViewConfigAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewConfigAction.update$lambda$11(this.f$0, viewFromStage, zInterpretBoolean, str, stageActivity, strInterpretString);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:430:0x097a A[Catch: Exception -> 0x0b80, TryCatch #1 {Exception -> 0x0b80, blocks: (B:3:0x0026, B:7:0x0042, B:8:0x0047, B:9:0x004c, B:10:0x0051, B:12:0x0055, B:13:0x007e, B:15:0x0084, B:16:0x0098, B:20:0x00a4, B:24:0x00ae, B:29:0x00be, B:31:0x00c9, B:34:0x00d0, B:35:0x00db, B:37:0x00ec, B:38:0x0104, B:40:0x0108, B:42:0x0111, B:43:0x011a, B:44:0x0138, B:46:0x013c, B:47:0x0165, B:49:0x016b, B:50:0x017f, B:52:0x0189, B:54:0x018f, B:56:0x0195, B:58:0x019d, B:60:0x01a3, B:62:0x01a9, B:66:0x01b4, B:75:0x0238, B:76:0x023d, B:78:0x0241, B:79:0x026a, B:81:0x0270, B:82:0x0284, B:86:0x0290, B:90:0x029a, B:95:0x02aa, B:97:0x02b5, B:100:0x02bc, B:101:0x02c7, B:103:0x02d8, B:104:0x02f0, B:106:0x02f4, B:108:0x02fd, B:109:0x0306, B:110:0x0324, B:112:0x0328, B:113:0x0351, B:115:0x0357, B:116:0x036b, B:118:0x0375, B:120:0x037b, B:122:0x0381, B:124:0x0389, B:126:0x038f, B:128:0x0395, B:132:0x03a0, B:141:0x0424, B:142:0x0429, B:144:0x042d, B:146:0x0433, B:147:0x0437, B:148:0x0441, B:150:0x0453, B:152:0x0465, B:157:0x0472, B:158:0x0477, B:160:0x0488, B:165:0x049e, B:167:0x04a6, B:170:0x04ac, B:174:0x04b8, B:176:0x04c9, B:179:0x04cf, B:180:0x04d8, B:183:0x04de, B:184:0x04e6, B:185:0x04ee, B:186:0x0510, B:188:0x0516, B:189:0x052e, B:191:0x053a, B:193:0x0545, B:195:0x054d, B:197:0x0558, B:196:0x0552, B:192:0x053f, B:198:0x0560, B:200:0x0566, B:202:0x056c, B:206:0x0577, B:207:0x0582, B:210:0x0590, B:212:0x0594, B:214:0x059f, B:216:0x05a7, B:218:0x05ad, B:219:0x05b0, B:220:0x05e0, B:222:0x05e6, B:223:0x05ed, B:226:0x05f7, B:227:0x0602, B:228:0x060b, B:230:0x060f, B:232:0x0617, B:235:0x061d, B:236:0x0629, B:238:0x062f, B:240:0x0635, B:242:0x0639, B:244:0x063d, B:245:0x0646, B:247:0x064c, B:248:0x0650, B:249:0x0655, B:250:0x067e, B:252:0x0684, B:253:0x0698, B:255:0x06a2, B:257:0x06a8, B:259:0x06ae, B:263:0x06b8, B:265:0x06c0, B:268:0x06c7, B:273:0x06e2, B:274:0x06e7, B:276:0x06ed, B:277:0x06f1, B:279:0x06f7, B:280:0x06fe, B:282:0x0704, B:290:0x0713, B:292:0x0719, B:294:0x071f, B:296:0x0723, B:300:0x072b, B:301:0x0730, B:303:0x0736, B:305:0x073c, B:307:0x0740, B:308:0x0746, B:310:0x074a, B:311:0x0751, B:313:0x0755, B:315:0x0760, B:318:0x0767, B:319:0x0775, B:321:0x078f, B:323:0x0795, B:324:0x07ac, B:326:0x07b0, B:328:0x07bb, B:331:0x07c2, B:332:0x07d0, B:334:0x07d6, B:335:0x07da, B:336:0x07ef, B:338:0x07f3, B:340:0x07fe, B:343:0x0805, B:344:0x0813, B:346:0x0819, B:348:0x081f, B:349:0x0833, B:351:0x0837, B:353:0x0841, B:356:0x084a, B:358:0x084e, B:360:0x0854, B:363:0x085c, B:365:0x0862, B:366:0x0871, B:367:0x0886, B:369:0x088a, B:371:0x0890, B:372:0x0894, B:378:0x089f, B:382:0x08aa, B:379:0x08a2, B:380:0x08a5, B:381:0x08a8, B:383:0x08af, B:385:0x08b3, B:386:0x08bb, B:388:0x08bf, B:389:0x08c7, B:391:0x08cb, B:392:0x08d6, B:394:0x08da, B:395:0x08e6, B:397:0x08ea, B:398:0x08f5, B:400:0x08f9, B:402:0x08ff, B:405:0x0907, B:406:0x0919, B:408:0x091d, B:410:0x0926, B:411:0x092a, B:412:0x0932, B:414:0x0936, B:415:0x094f, B:439:0x0997, B:441:0x099b, B:443:0x09a1, B:446:0x09a9, B:452:0x09b9, B:450:0x09b4, B:451:0x09b7, B:417:0x0953, B:420:0x095c, B:421:0x095f, B:438:0x0994, B:424:0x0968, B:430:0x097a, B:427:0x0971, B:431:0x097f, B:434:0x0988, B:435:0x098b, B:453:0x09be, B:455:0x09c2, B:457:0x09c8, B:458:0x09cc, B:465:0x09de, B:466:0x09e3, B:468:0x09e9, B:470:0x09ef, B:471:0x09f4, B:473:0x09fa, B:477:0x0a06, B:478:0x0a15, B:479:0x0a1e, B:481:0x0a22, B:483:0x0a2d, B:484:0x0a38, B:485:0x0a41, B:487:0x0a45, B:489:0x0a4e, B:491:0x0a55, B:492:0x0a5a, B:494:0x0a5e, B:495:0x0a6a, B:497:0x0a6f, B:499:0x0a80, B:503:0x0a89, B:504:0x0a8e, B:506:0x0a93, B:508:0x0aa4, B:510:0x0aa9, B:512:0x0aaf, B:515:0x0ab7, B:516:0x0abe, B:520:0x0acb, B:521:0x0ad0, B:523:0x0ad4, B:524:0x0ae0, B:526:0x0ae4, B:527:0x0af0, B:529:0x0af4, B:530:0x0b00, B:532:0x0b04, B:533:0x0b0d, B:535:0x0b13, B:536:0x0b17, B:537:0x0b1e, B:539:0x0b24, B:540:0x0b28, B:541:0x0b2e, B:543:0x0b35, B:545:0x0b3b, B:546:0x0b3f, B:548:0x0b47, B:550:0x0b4d, B:552:0x0b55, B:555:0x0b5b, B:556:0x0b5f, B:557:0x0b66, B:559:0x0b6c, B:560:0x0b70, B:565:0x0b7c, B:269:0x06d2, B:518:0x0ac2, B:68:0x01e8, B:70:0x01fa, B:71:0x022c, B:448:0x09af, B:134:0x03d4, B:136:0x03e6, B:137:0x0418), top: B:572:0x0026, inners: #0, #3, #5, #6, #7 }] */
    /* JADX WARN: Code duplicated, block: B:438:0x0994 A[Catch: Exception -> 0x0b80, TryCatch #1 {Exception -> 0x0b80, blocks: (B:3:0x0026, B:7:0x0042, B:8:0x0047, B:9:0x004c, B:10:0x0051, B:12:0x0055, B:13:0x007e, B:15:0x0084, B:16:0x0098, B:20:0x00a4, B:24:0x00ae, B:29:0x00be, B:31:0x00c9, B:34:0x00d0, B:35:0x00db, B:37:0x00ec, B:38:0x0104, B:40:0x0108, B:42:0x0111, B:43:0x011a, B:44:0x0138, B:46:0x013c, B:47:0x0165, B:49:0x016b, B:50:0x017f, B:52:0x0189, B:54:0x018f, B:56:0x0195, B:58:0x019d, B:60:0x01a3, B:62:0x01a9, B:66:0x01b4, B:75:0x0238, B:76:0x023d, B:78:0x0241, B:79:0x026a, B:81:0x0270, B:82:0x0284, B:86:0x0290, B:90:0x029a, B:95:0x02aa, B:97:0x02b5, B:100:0x02bc, B:101:0x02c7, B:103:0x02d8, B:104:0x02f0, B:106:0x02f4, B:108:0x02fd, B:109:0x0306, B:110:0x0324, B:112:0x0328, B:113:0x0351, B:115:0x0357, B:116:0x036b, B:118:0x0375, B:120:0x037b, B:122:0x0381, B:124:0x0389, B:126:0x038f, B:128:0x0395, B:132:0x03a0, B:141:0x0424, B:142:0x0429, B:144:0x042d, B:146:0x0433, B:147:0x0437, B:148:0x0441, B:150:0x0453, B:152:0x0465, B:157:0x0472, B:158:0x0477, B:160:0x0488, B:165:0x049e, B:167:0x04a6, B:170:0x04ac, B:174:0x04b8, B:176:0x04c9, B:179:0x04cf, B:180:0x04d8, B:183:0x04de, B:184:0x04e6, B:185:0x04ee, B:186:0x0510, B:188:0x0516, B:189:0x052e, B:191:0x053a, B:193:0x0545, B:195:0x054d, B:197:0x0558, B:196:0x0552, B:192:0x053f, B:198:0x0560, B:200:0x0566, B:202:0x056c, B:206:0x0577, B:207:0x0582, B:210:0x0590, B:212:0x0594, B:214:0x059f, B:216:0x05a7, B:218:0x05ad, B:219:0x05b0, B:220:0x05e0, B:222:0x05e6, B:223:0x05ed, B:226:0x05f7, B:227:0x0602, B:228:0x060b, B:230:0x060f, B:232:0x0617, B:235:0x061d, B:236:0x0629, B:238:0x062f, B:240:0x0635, B:242:0x0639, B:244:0x063d, B:245:0x0646, B:247:0x064c, B:248:0x0650, B:249:0x0655, B:250:0x067e, B:252:0x0684, B:253:0x0698, B:255:0x06a2, B:257:0x06a8, B:259:0x06ae, B:263:0x06b8, B:265:0x06c0, B:268:0x06c7, B:273:0x06e2, B:274:0x06e7, B:276:0x06ed, B:277:0x06f1, B:279:0x06f7, B:280:0x06fe, B:282:0x0704, B:290:0x0713, B:292:0x0719, B:294:0x071f, B:296:0x0723, B:300:0x072b, B:301:0x0730, B:303:0x0736, B:305:0x073c, B:307:0x0740, B:308:0x0746, B:310:0x074a, B:311:0x0751, B:313:0x0755, B:315:0x0760, B:318:0x0767, B:319:0x0775, B:321:0x078f, B:323:0x0795, B:324:0x07ac, B:326:0x07b0, B:328:0x07bb, B:331:0x07c2, B:332:0x07d0, B:334:0x07d6, B:335:0x07da, B:336:0x07ef, B:338:0x07f3, B:340:0x07fe, B:343:0x0805, B:344:0x0813, B:346:0x0819, B:348:0x081f, B:349:0x0833, B:351:0x0837, B:353:0x0841, B:356:0x084a, B:358:0x084e, B:360:0x0854, B:363:0x085c, B:365:0x0862, B:366:0x0871, B:367:0x0886, B:369:0x088a, B:371:0x0890, B:372:0x0894, B:378:0x089f, B:382:0x08aa, B:379:0x08a2, B:380:0x08a5, B:381:0x08a8, B:383:0x08af, B:385:0x08b3, B:386:0x08bb, B:388:0x08bf, B:389:0x08c7, B:391:0x08cb, B:392:0x08d6, B:394:0x08da, B:395:0x08e6, B:397:0x08ea, B:398:0x08f5, B:400:0x08f9, B:402:0x08ff, B:405:0x0907, B:406:0x0919, B:408:0x091d, B:410:0x0926, B:411:0x092a, B:412:0x0932, B:414:0x0936, B:415:0x094f, B:439:0x0997, B:441:0x099b, B:443:0x09a1, B:446:0x09a9, B:452:0x09b9, B:450:0x09b4, B:451:0x09b7, B:417:0x0953, B:420:0x095c, B:421:0x095f, B:438:0x0994, B:424:0x0968, B:430:0x097a, B:427:0x0971, B:431:0x097f, B:434:0x0988, B:435:0x098b, B:453:0x09be, B:455:0x09c2, B:457:0x09c8, B:458:0x09cc, B:465:0x09de, B:466:0x09e3, B:468:0x09e9, B:470:0x09ef, B:471:0x09f4, B:473:0x09fa, B:477:0x0a06, B:478:0x0a15, B:479:0x0a1e, B:481:0x0a22, B:483:0x0a2d, B:484:0x0a38, B:485:0x0a41, B:487:0x0a45, B:489:0x0a4e, B:491:0x0a55, B:492:0x0a5a, B:494:0x0a5e, B:495:0x0a6a, B:497:0x0a6f, B:499:0x0a80, B:503:0x0a89, B:504:0x0a8e, B:506:0x0a93, B:508:0x0aa4, B:510:0x0aa9, B:512:0x0aaf, B:515:0x0ab7, B:516:0x0abe, B:520:0x0acb, B:521:0x0ad0, B:523:0x0ad4, B:524:0x0ae0, B:526:0x0ae4, B:527:0x0af0, B:529:0x0af4, B:530:0x0b00, B:532:0x0b04, B:533:0x0b0d, B:535:0x0b13, B:536:0x0b17, B:537:0x0b1e, B:539:0x0b24, B:540:0x0b28, B:541:0x0b2e, B:543:0x0b35, B:545:0x0b3b, B:546:0x0b3f, B:548:0x0b47, B:550:0x0b4d, B:552:0x0b55, B:555:0x0b5b, B:556:0x0b5f, B:557:0x0b66, B:559:0x0b6c, B:560:0x0b70, B:565:0x0b7c, B:269:0x06d2, B:518:0x0ac2, B:68:0x01e8, B:70:0x01fa, B:71:0x022c, B:448:0x09af, B:134:0x03d4, B:136:0x03e6, B:137:0x0418), top: B:572:0x0026, inners: #0, #3, #5, #6, #7 }] */
    /* JADX WARN: Code duplicated, block: B:441:0x099b A[Catch: Exception -> 0x0b80, TryCatch #1 {Exception -> 0x0b80, blocks: (B:3:0x0026, B:7:0x0042, B:8:0x0047, B:9:0x004c, B:10:0x0051, B:12:0x0055, B:13:0x007e, B:15:0x0084, B:16:0x0098, B:20:0x00a4, B:24:0x00ae, B:29:0x00be, B:31:0x00c9, B:34:0x00d0, B:35:0x00db, B:37:0x00ec, B:38:0x0104, B:40:0x0108, B:42:0x0111, B:43:0x011a, B:44:0x0138, B:46:0x013c, B:47:0x0165, B:49:0x016b, B:50:0x017f, B:52:0x0189, B:54:0x018f, B:56:0x0195, B:58:0x019d, B:60:0x01a3, B:62:0x01a9, B:66:0x01b4, B:75:0x0238, B:76:0x023d, B:78:0x0241, B:79:0x026a, B:81:0x0270, B:82:0x0284, B:86:0x0290, B:90:0x029a, B:95:0x02aa, B:97:0x02b5, B:100:0x02bc, B:101:0x02c7, B:103:0x02d8, B:104:0x02f0, B:106:0x02f4, B:108:0x02fd, B:109:0x0306, B:110:0x0324, B:112:0x0328, B:113:0x0351, B:115:0x0357, B:116:0x036b, B:118:0x0375, B:120:0x037b, B:122:0x0381, B:124:0x0389, B:126:0x038f, B:128:0x0395, B:132:0x03a0, B:141:0x0424, B:142:0x0429, B:144:0x042d, B:146:0x0433, B:147:0x0437, B:148:0x0441, B:150:0x0453, B:152:0x0465, B:157:0x0472, B:158:0x0477, B:160:0x0488, B:165:0x049e, B:167:0x04a6, B:170:0x04ac, B:174:0x04b8, B:176:0x04c9, B:179:0x04cf, B:180:0x04d8, B:183:0x04de, B:184:0x04e6, B:185:0x04ee, B:186:0x0510, B:188:0x0516, B:189:0x052e, B:191:0x053a, B:193:0x0545, B:195:0x054d, B:197:0x0558, B:196:0x0552, B:192:0x053f, B:198:0x0560, B:200:0x0566, B:202:0x056c, B:206:0x0577, B:207:0x0582, B:210:0x0590, B:212:0x0594, B:214:0x059f, B:216:0x05a7, B:218:0x05ad, B:219:0x05b0, B:220:0x05e0, B:222:0x05e6, B:223:0x05ed, B:226:0x05f7, B:227:0x0602, B:228:0x060b, B:230:0x060f, B:232:0x0617, B:235:0x061d, B:236:0x0629, B:238:0x062f, B:240:0x0635, B:242:0x0639, B:244:0x063d, B:245:0x0646, B:247:0x064c, B:248:0x0650, B:249:0x0655, B:250:0x067e, B:252:0x0684, B:253:0x0698, B:255:0x06a2, B:257:0x06a8, B:259:0x06ae, B:263:0x06b8, B:265:0x06c0, B:268:0x06c7, B:273:0x06e2, B:274:0x06e7, B:276:0x06ed, B:277:0x06f1, B:279:0x06f7, B:280:0x06fe, B:282:0x0704, B:290:0x0713, B:292:0x0719, B:294:0x071f, B:296:0x0723, B:300:0x072b, B:301:0x0730, B:303:0x0736, B:305:0x073c, B:307:0x0740, B:308:0x0746, B:310:0x074a, B:311:0x0751, B:313:0x0755, B:315:0x0760, B:318:0x0767, B:319:0x0775, B:321:0x078f, B:323:0x0795, B:324:0x07ac, B:326:0x07b0, B:328:0x07bb, B:331:0x07c2, B:332:0x07d0, B:334:0x07d6, B:335:0x07da, B:336:0x07ef, B:338:0x07f3, B:340:0x07fe, B:343:0x0805, B:344:0x0813, B:346:0x0819, B:348:0x081f, B:349:0x0833, B:351:0x0837, B:353:0x0841, B:356:0x084a, B:358:0x084e, B:360:0x0854, B:363:0x085c, B:365:0x0862, B:366:0x0871, B:367:0x0886, B:369:0x088a, B:371:0x0890, B:372:0x0894, B:378:0x089f, B:382:0x08aa, B:379:0x08a2, B:380:0x08a5, B:381:0x08a8, B:383:0x08af, B:385:0x08b3, B:386:0x08bb, B:388:0x08bf, B:389:0x08c7, B:391:0x08cb, B:392:0x08d6, B:394:0x08da, B:395:0x08e6, B:397:0x08ea, B:398:0x08f5, B:400:0x08f9, B:402:0x08ff, B:405:0x0907, B:406:0x0919, B:408:0x091d, B:410:0x0926, B:411:0x092a, B:412:0x0932, B:414:0x0936, B:415:0x094f, B:439:0x0997, B:441:0x099b, B:443:0x09a1, B:446:0x09a9, B:452:0x09b9, B:450:0x09b4, B:451:0x09b7, B:417:0x0953, B:420:0x095c, B:421:0x095f, B:438:0x0994, B:424:0x0968, B:430:0x097a, B:427:0x0971, B:431:0x097f, B:434:0x0988, B:435:0x098b, B:453:0x09be, B:455:0x09c2, B:457:0x09c8, B:458:0x09cc, B:465:0x09de, B:466:0x09e3, B:468:0x09e9, B:470:0x09ef, B:471:0x09f4, B:473:0x09fa, B:477:0x0a06, B:478:0x0a15, B:479:0x0a1e, B:481:0x0a22, B:483:0x0a2d, B:484:0x0a38, B:485:0x0a41, B:487:0x0a45, B:489:0x0a4e, B:491:0x0a55, B:492:0x0a5a, B:494:0x0a5e, B:495:0x0a6a, B:497:0x0a6f, B:499:0x0a80, B:503:0x0a89, B:504:0x0a8e, B:506:0x0a93, B:508:0x0aa4, B:510:0x0aa9, B:512:0x0aaf, B:515:0x0ab7, B:516:0x0abe, B:520:0x0acb, B:521:0x0ad0, B:523:0x0ad4, B:524:0x0ae0, B:526:0x0ae4, B:527:0x0af0, B:529:0x0af4, B:530:0x0b00, B:532:0x0b04, B:533:0x0b0d, B:535:0x0b13, B:536:0x0b17, B:537:0x0b1e, B:539:0x0b24, B:540:0x0b28, B:541:0x0b2e, B:543:0x0b35, B:545:0x0b3b, B:546:0x0b3f, B:548:0x0b47, B:550:0x0b4d, B:552:0x0b55, B:555:0x0b5b, B:556:0x0b5f, B:557:0x0b66, B:559:0x0b6c, B:560:0x0b70, B:565:0x0b7c, B:269:0x06d2, B:518:0x0ac2, B:68:0x01e8, B:70:0x01fa, B:71:0x022c, B:448:0x09af, B:134:0x03d4, B:136:0x03e6, B:137:0x0418), top: B:572:0x0026, inners: #0, #3, #5, #6, #7 }] */
    /* JADX WARN: Code duplicated, block: B:444:0x09a6  */
    /* JADX WARN: Code duplicated, block: B:446:0x09a9 A[Catch: Exception -> 0x0b80, TRY_LEAVE, TryCatch #1 {Exception -> 0x0b80, blocks: (B:3:0x0026, B:7:0x0042, B:8:0x0047, B:9:0x004c, B:10:0x0051, B:12:0x0055, B:13:0x007e, B:15:0x0084, B:16:0x0098, B:20:0x00a4, B:24:0x00ae, B:29:0x00be, B:31:0x00c9, B:34:0x00d0, B:35:0x00db, B:37:0x00ec, B:38:0x0104, B:40:0x0108, B:42:0x0111, B:43:0x011a, B:44:0x0138, B:46:0x013c, B:47:0x0165, B:49:0x016b, B:50:0x017f, B:52:0x0189, B:54:0x018f, B:56:0x0195, B:58:0x019d, B:60:0x01a3, B:62:0x01a9, B:66:0x01b4, B:75:0x0238, B:76:0x023d, B:78:0x0241, B:79:0x026a, B:81:0x0270, B:82:0x0284, B:86:0x0290, B:90:0x029a, B:95:0x02aa, B:97:0x02b5, B:100:0x02bc, B:101:0x02c7, B:103:0x02d8, B:104:0x02f0, B:106:0x02f4, B:108:0x02fd, B:109:0x0306, B:110:0x0324, B:112:0x0328, B:113:0x0351, B:115:0x0357, B:116:0x036b, B:118:0x0375, B:120:0x037b, B:122:0x0381, B:124:0x0389, B:126:0x038f, B:128:0x0395, B:132:0x03a0, B:141:0x0424, B:142:0x0429, B:144:0x042d, B:146:0x0433, B:147:0x0437, B:148:0x0441, B:150:0x0453, B:152:0x0465, B:157:0x0472, B:158:0x0477, B:160:0x0488, B:165:0x049e, B:167:0x04a6, B:170:0x04ac, B:174:0x04b8, B:176:0x04c9, B:179:0x04cf, B:180:0x04d8, B:183:0x04de, B:184:0x04e6, B:185:0x04ee, B:186:0x0510, B:188:0x0516, B:189:0x052e, B:191:0x053a, B:193:0x0545, B:195:0x054d, B:197:0x0558, B:196:0x0552, B:192:0x053f, B:198:0x0560, B:200:0x0566, B:202:0x056c, B:206:0x0577, B:207:0x0582, B:210:0x0590, B:212:0x0594, B:214:0x059f, B:216:0x05a7, B:218:0x05ad, B:219:0x05b0, B:220:0x05e0, B:222:0x05e6, B:223:0x05ed, B:226:0x05f7, B:227:0x0602, B:228:0x060b, B:230:0x060f, B:232:0x0617, B:235:0x061d, B:236:0x0629, B:238:0x062f, B:240:0x0635, B:242:0x0639, B:244:0x063d, B:245:0x0646, B:247:0x064c, B:248:0x0650, B:249:0x0655, B:250:0x067e, B:252:0x0684, B:253:0x0698, B:255:0x06a2, B:257:0x06a8, B:259:0x06ae, B:263:0x06b8, B:265:0x06c0, B:268:0x06c7, B:273:0x06e2, B:274:0x06e7, B:276:0x06ed, B:277:0x06f1, B:279:0x06f7, B:280:0x06fe, B:282:0x0704, B:290:0x0713, B:292:0x0719, B:294:0x071f, B:296:0x0723, B:300:0x072b, B:301:0x0730, B:303:0x0736, B:305:0x073c, B:307:0x0740, B:308:0x0746, B:310:0x074a, B:311:0x0751, B:313:0x0755, B:315:0x0760, B:318:0x0767, B:319:0x0775, B:321:0x078f, B:323:0x0795, B:324:0x07ac, B:326:0x07b0, B:328:0x07bb, B:331:0x07c2, B:332:0x07d0, B:334:0x07d6, B:335:0x07da, B:336:0x07ef, B:338:0x07f3, B:340:0x07fe, B:343:0x0805, B:344:0x0813, B:346:0x0819, B:348:0x081f, B:349:0x0833, B:351:0x0837, B:353:0x0841, B:356:0x084a, B:358:0x084e, B:360:0x0854, B:363:0x085c, B:365:0x0862, B:366:0x0871, B:367:0x0886, B:369:0x088a, B:371:0x0890, B:372:0x0894, B:378:0x089f, B:382:0x08aa, B:379:0x08a2, B:380:0x08a5, B:381:0x08a8, B:383:0x08af, B:385:0x08b3, B:386:0x08bb, B:388:0x08bf, B:389:0x08c7, B:391:0x08cb, B:392:0x08d6, B:394:0x08da, B:395:0x08e6, B:397:0x08ea, B:398:0x08f5, B:400:0x08f9, B:402:0x08ff, B:405:0x0907, B:406:0x0919, B:408:0x091d, B:410:0x0926, B:411:0x092a, B:412:0x0932, B:414:0x0936, B:415:0x094f, B:439:0x0997, B:441:0x099b, B:443:0x09a1, B:446:0x09a9, B:452:0x09b9, B:450:0x09b4, B:451:0x09b7, B:417:0x0953, B:420:0x095c, B:421:0x095f, B:438:0x0994, B:424:0x0968, B:430:0x097a, B:427:0x0971, B:431:0x097f, B:434:0x0988, B:435:0x098b, B:453:0x09be, B:455:0x09c2, B:457:0x09c8, B:458:0x09cc, B:465:0x09de, B:466:0x09e3, B:468:0x09e9, B:470:0x09ef, B:471:0x09f4, B:473:0x09fa, B:477:0x0a06, B:478:0x0a15, B:479:0x0a1e, B:481:0x0a22, B:483:0x0a2d, B:484:0x0a38, B:485:0x0a41, B:487:0x0a45, B:489:0x0a4e, B:491:0x0a55, B:492:0x0a5a, B:494:0x0a5e, B:495:0x0a6a, B:497:0x0a6f, B:499:0x0a80, B:503:0x0a89, B:504:0x0a8e, B:506:0x0a93, B:508:0x0aa4, B:510:0x0aa9, B:512:0x0aaf, B:515:0x0ab7, B:516:0x0abe, B:520:0x0acb, B:521:0x0ad0, B:523:0x0ad4, B:524:0x0ae0, B:526:0x0ae4, B:527:0x0af0, B:529:0x0af4, B:530:0x0b00, B:532:0x0b04, B:533:0x0b0d, B:535:0x0b13, B:536:0x0b17, B:537:0x0b1e, B:539:0x0b24, B:540:0x0b28, B:541:0x0b2e, B:543:0x0b35, B:545:0x0b3b, B:546:0x0b3f, B:548:0x0b47, B:550:0x0b4d, B:552:0x0b55, B:555:0x0b5b, B:556:0x0b5f, B:557:0x0b66, B:559:0x0b6c, B:560:0x0b70, B:565:0x0b7c, B:269:0x06d2, B:518:0x0ac2, B:68:0x01e8, B:70:0x01fa, B:71:0x022c, B:448:0x09af, B:134:0x03d4, B:136:0x03e6, B:137:0x0418), top: B:572:0x0026, inners: #0, #3, #5, #6, #7 }] */
    /* JADX WARN: Code duplicated, block: B:451:0x09b7 A[Catch: Exception -> 0x0b80, TryCatch #1 {Exception -> 0x0b80, blocks: (B:3:0x0026, B:7:0x0042, B:8:0x0047, B:9:0x004c, B:10:0x0051, B:12:0x0055, B:13:0x007e, B:15:0x0084, B:16:0x0098, B:20:0x00a4, B:24:0x00ae, B:29:0x00be, B:31:0x00c9, B:34:0x00d0, B:35:0x00db, B:37:0x00ec, B:38:0x0104, B:40:0x0108, B:42:0x0111, B:43:0x011a, B:44:0x0138, B:46:0x013c, B:47:0x0165, B:49:0x016b, B:50:0x017f, B:52:0x0189, B:54:0x018f, B:56:0x0195, B:58:0x019d, B:60:0x01a3, B:62:0x01a9, B:66:0x01b4, B:75:0x0238, B:76:0x023d, B:78:0x0241, B:79:0x026a, B:81:0x0270, B:82:0x0284, B:86:0x0290, B:90:0x029a, B:95:0x02aa, B:97:0x02b5, B:100:0x02bc, B:101:0x02c7, B:103:0x02d8, B:104:0x02f0, B:106:0x02f4, B:108:0x02fd, B:109:0x0306, B:110:0x0324, B:112:0x0328, B:113:0x0351, B:115:0x0357, B:116:0x036b, B:118:0x0375, B:120:0x037b, B:122:0x0381, B:124:0x0389, B:126:0x038f, B:128:0x0395, B:132:0x03a0, B:141:0x0424, B:142:0x0429, B:144:0x042d, B:146:0x0433, B:147:0x0437, B:148:0x0441, B:150:0x0453, B:152:0x0465, B:157:0x0472, B:158:0x0477, B:160:0x0488, B:165:0x049e, B:167:0x04a6, B:170:0x04ac, B:174:0x04b8, B:176:0x04c9, B:179:0x04cf, B:180:0x04d8, B:183:0x04de, B:184:0x04e6, B:185:0x04ee, B:186:0x0510, B:188:0x0516, B:189:0x052e, B:191:0x053a, B:193:0x0545, B:195:0x054d, B:197:0x0558, B:196:0x0552, B:192:0x053f, B:198:0x0560, B:200:0x0566, B:202:0x056c, B:206:0x0577, B:207:0x0582, B:210:0x0590, B:212:0x0594, B:214:0x059f, B:216:0x05a7, B:218:0x05ad, B:219:0x05b0, B:220:0x05e0, B:222:0x05e6, B:223:0x05ed, B:226:0x05f7, B:227:0x0602, B:228:0x060b, B:230:0x060f, B:232:0x0617, B:235:0x061d, B:236:0x0629, B:238:0x062f, B:240:0x0635, B:242:0x0639, B:244:0x063d, B:245:0x0646, B:247:0x064c, B:248:0x0650, B:249:0x0655, B:250:0x067e, B:252:0x0684, B:253:0x0698, B:255:0x06a2, B:257:0x06a8, B:259:0x06ae, B:263:0x06b8, B:265:0x06c0, B:268:0x06c7, B:273:0x06e2, B:274:0x06e7, B:276:0x06ed, B:277:0x06f1, B:279:0x06f7, B:280:0x06fe, B:282:0x0704, B:290:0x0713, B:292:0x0719, B:294:0x071f, B:296:0x0723, B:300:0x072b, B:301:0x0730, B:303:0x0736, B:305:0x073c, B:307:0x0740, B:308:0x0746, B:310:0x074a, B:311:0x0751, B:313:0x0755, B:315:0x0760, B:318:0x0767, B:319:0x0775, B:321:0x078f, B:323:0x0795, B:324:0x07ac, B:326:0x07b0, B:328:0x07bb, B:331:0x07c2, B:332:0x07d0, B:334:0x07d6, B:335:0x07da, B:336:0x07ef, B:338:0x07f3, B:340:0x07fe, B:343:0x0805, B:344:0x0813, B:346:0x0819, B:348:0x081f, B:349:0x0833, B:351:0x0837, B:353:0x0841, B:356:0x084a, B:358:0x084e, B:360:0x0854, B:363:0x085c, B:365:0x0862, B:366:0x0871, B:367:0x0886, B:369:0x088a, B:371:0x0890, B:372:0x0894, B:378:0x089f, B:382:0x08aa, B:379:0x08a2, B:380:0x08a5, B:381:0x08a8, B:383:0x08af, B:385:0x08b3, B:386:0x08bb, B:388:0x08bf, B:389:0x08c7, B:391:0x08cb, B:392:0x08d6, B:394:0x08da, B:395:0x08e6, B:397:0x08ea, B:398:0x08f5, B:400:0x08f9, B:402:0x08ff, B:405:0x0907, B:406:0x0919, B:408:0x091d, B:410:0x0926, B:411:0x092a, B:412:0x0932, B:414:0x0936, B:415:0x094f, B:439:0x0997, B:441:0x099b, B:443:0x09a1, B:446:0x09a9, B:452:0x09b9, B:450:0x09b4, B:451:0x09b7, B:417:0x0953, B:420:0x095c, B:421:0x095f, B:438:0x0994, B:424:0x0968, B:430:0x097a, B:427:0x0971, B:431:0x097f, B:434:0x0988, B:435:0x098b, B:453:0x09be, B:455:0x09c2, B:457:0x09c8, B:458:0x09cc, B:465:0x09de, B:466:0x09e3, B:468:0x09e9, B:470:0x09ef, B:471:0x09f4, B:473:0x09fa, B:477:0x0a06, B:478:0x0a15, B:479:0x0a1e, B:481:0x0a22, B:483:0x0a2d, B:484:0x0a38, B:485:0x0a41, B:487:0x0a45, B:489:0x0a4e, B:491:0x0a55, B:492:0x0a5a, B:494:0x0a5e, B:495:0x0a6a, B:497:0x0a6f, B:499:0x0a80, B:503:0x0a89, B:504:0x0a8e, B:506:0x0a93, B:508:0x0aa4, B:510:0x0aa9, B:512:0x0aaf, B:515:0x0ab7, B:516:0x0abe, B:520:0x0acb, B:521:0x0ad0, B:523:0x0ad4, B:524:0x0ae0, B:526:0x0ae4, B:527:0x0af0, B:529:0x0af4, B:530:0x0b00, B:532:0x0b04, B:533:0x0b0d, B:535:0x0b13, B:536:0x0b17, B:537:0x0b1e, B:539:0x0b24, B:540:0x0b28, B:541:0x0b2e, B:543:0x0b35, B:545:0x0b3b, B:546:0x0b3f, B:548:0x0b47, B:550:0x0b4d, B:552:0x0b55, B:555:0x0b5b, B:556:0x0b5f, B:557:0x0b66, B:559:0x0b6c, B:560:0x0b70, B:565:0x0b7c, B:269:0x06d2, B:518:0x0ac2, B:68:0x01e8, B:70:0x01fa, B:71:0x022c, B:448:0x09af, B:134:0x03d4, B:136:0x03e6, B:137:0x0418), top: B:572:0x0026, inners: #0, #3, #5, #6, #7 }] */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final void update$lambda$11(NativeViewConfigAction this$0, final View view, boolean z, String valStr, StageActivity stage, String idStr) {
        float f;
        final float fFloatValue;
        Typeface typefaceDefaultFromStyle;
        Scope scope;
        File file;
        Project project;
        ImageView.ScaleType scaleType;
        Project project2;
        Integer intOrNull;
        Bitmap bitmap;
        boolean z2;
        Integer intOrNull2;
        Integer intOrNull3;
        int color;
        Integer intOrNull4;
        Integer intOrNull5;
        int color2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(valStr, "$valStr");
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(idStr, "$idStr");
        try {
            String str = "#FFFF00";
            String str2 = "";
            String str3 = "#FFFFFF";
            int i = 1;
            int iIntValue = 0;
            switch (this$0.propertySelection) {
                case 0:
                    if (!z) {
                        iIntValue = 8;
                    }
                    view.setVisibility(iIntValue);
                    return;
                case 1:
                    Float floatOrNull = StringsKt.toFloatOrNull(valStr);
                    view.setAlpha(floatOrNull != null ? floatOrNull.floatValue() : 1.0f);
                    return;
                case 2:
                    view.setClickable(z);
                    view.setFocusable(z);
                    return;
                case 3:
                    Float floatOrNull2 = StringsKt.toFloatOrNull(valStr);
                    float fFloatValue2 = floatOrNull2 != null ? floatOrNull2.floatValue() : 0.0f;
                    ViewParent parent = view.getParent();
                    if ((parent instanceof ViewGroup ? (ViewGroup) parent : null) != null) {
                        view.setElevation(fFloatValue2);
                        return;
                    }
                    return;
                case 4:
                    Float floatOrNull3 = StringsKt.toFloatOrNull(valStr);
                    view.setRotation(floatOrNull3 != null ? floatOrNull3.floatValue() : 0.0f);
                    return;
                case 5:
                    Float floatOrNull4 = StringsKt.toFloatOrNull(valStr);
                    view.setScaleX(floatOrNull4 != null ? floatOrNull4.floatValue() : 1.0f);
                    return;
                case 6:
                    Float floatOrNull5 = StringsKt.toFloatOrNull(valStr);
                    view.setScaleY(floatOrNull5 != null ? floatOrNull5.floatValue() : 1.0f);
                    return;
                case 7:
                    if (view instanceof WebView) {
                        CookieManager.getInstance().setAcceptCookie(z);
                        return;
                    }
                    return;
                case 8:
                    if (view instanceof WebView) {
                        ((WebView) view).getSettings().setBuiltInZoomControls(z);
                        return;
                    }
                    return;
                case 9:
                    if (view instanceof WebView) {
                        ((WebView) view).getSettings().setDomStorageEnabled(z);
                        return;
                    }
                    return;
                case 10:
                    if (view instanceof WebView) {
                        ((WebView) view).getSettings().setUserAgentString(valStr);
                        return;
                    }
                    return;
                case 11:
                    if (view instanceof WebView) {
                        try {
                            view.setBackgroundColor(Color.parseColor(valStr));
                            return;
                        } catch (Exception unused) {
                            view.setBackgroundColor(0);
                            return;
                        }
                    }
                    return;
                case 12:
                    if (view instanceof VideoView) {
                        Field declaredField = VideoView.class.getDeclaredField("mMediaPlayer");
                        declaredField.setAccessible(true);
                        Object obj = declaredField.get(view);
                        MediaPlayer mediaPlayer = obj instanceof MediaPlayer ? (MediaPlayer) obj : null;
                        Float floatOrNull6 = StringsKt.toFloatOrNull(valStr);
                        float fFloatValue3 = floatOrNull6 != null ? floatOrNull6.floatValue() : 1.0f;
                        if (mediaPlayer != null) {
                            mediaPlayer.setVolume(fFloatValue3, fFloatValue3);
                            Unit unit = Unit.INSTANCE;
                            return;
                        }
                        return;
                    }
                    return;
                case 13:
                    if (view instanceof VideoView) {
                        Field declaredField2 = VideoView.class.getDeclaredField("mMediaPlayer");
                        declaredField2.setAccessible(true);
                        Object obj2 = declaredField2.get(view);
                        MediaPlayer mediaPlayer2 = obj2 instanceof MediaPlayer ? (MediaPlayer) obj2 : null;
                        if (mediaPlayer2 == null) {
                            return;
                        }
                        mediaPlayer2.setLooping(z);
                        return;
                    }
                    return;
                case 14:
                    if (view instanceof TextView) {
                        ((TextView) view).setTextColor(Color.parseColor(valStr));
                        return;
                    }
                    return;
                case 15:
                    if (view instanceof TextView) {
                        TextView textView = (TextView) view;
                        Float floatOrNull7 = StringsKt.toFloatOrNull(valStr);
                        textView.setTextSize(floatOrNull7 != null ? floatOrNull7.floatValue() : 20.0f);
                        return;
                    }
                    return;
                case 16:
                    if (view instanceof TextView) {
                        Drawable background = ((TextView) view).getBackground();
                        if (background instanceof GradientDrawable) {
                            ((GradientDrawable) background).setColor(Color.parseColor(valStr));
                            return;
                        } else {
                            view.setBackgroundColor(Color.parseColor(valStr));
                            return;
                        }
                    }
                    return;
                case 17:
                    Float floatOrNull8 = StringsKt.toFloatOrNull(valStr);
                    if (floatOrNull8 != null) {
                        fFloatValue = floatOrNull8.floatValue();
                        f = 0.0f;
                    } else {
                        f = 0.0f;
                        fFloatValue = 0.0f;
                    }
                    if (fFloatValue > f) {
                        view.setOutlineProvider(new ViewOutlineProvider() { // from class: org.catrobat.catroid.content.actions.NativeViewConfigAction$update$1$1
                            @Override // android.view.ViewOutlineProvider
                            public void getOutline(View v, Outline outline) {
                                Intrinsics.checkNotNullParameter(v, "v");
                                Intrinsics.checkNotNullParameter(outline, "outline");
                                outline.setRoundRect(0, 0, v.getWidth(), v.getHeight(), fFloatValue);
                            }
                        });
                        view.setClipToOutline(true);
                        return;
                    } else {
                        view.setClipToOutline(false);
                        view.setOutlineProvider(null);
                        return;
                    }
                case 18:
                    Float floatOrNull9 = StringsKt.toFloatOrNull(valStr);
                    view.setElevation(floatOrNull9 != null ? floatOrNull9.floatValue() : 0.0f);
                    return;
                case 19:
                    if (view instanceof TextView) {
                        Integer intOrNull6 = StringsKt.toIntOrNull(valStr);
                        iIntValue = intOrNull6 != null ? intOrNull6.intValue() : 0;
                        ((TextView) view).setGravity(iIntValue != 1 ? iIntValue != 2 ? 8388627 : 8388629 : 17);
                        return;
                    }
                    return;
                case 20:
                    if (view instanceof TextView) {
                        String lowerCase = valStr.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        String string = StringsKt.trim((CharSequence) lowerCase).toString();
                        TextView textView2 = (TextView) view;
                        switch (string.hashCode()) {
                            case -1536685117:
                                if (string.equals("sans-serif")) {
                                    typefaceDefaultFromStyle = Typeface.SANS_SERIF;
                                } else {
                                    scope = this$0.scope;
                                    if (scope != null || (project = scope.getProject()) == null) {
                                        file = null;
                                    } else {
                                        file = project.getFile(valStr);
                                    }
                                    if (file == null && file.exists()) {
                                        try {
                                            typefaceDefaultFromStyle = Typeface.createFromFile(file);
                                        } catch (Exception unused2) {
                                            typefaceDefaultFromStyle = Typeface.DEFAULT;
                                        }
                                    } else {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    }
                                }
                                textView2.setTypeface(typefaceDefaultFromStyle);
                                break;
                            case -1431958525:
                                if (string.equals("monospace")) {
                                    typefaceDefaultFromStyle = Typeface.MONOSPACE;
                                } else {
                                    scope = this$0.scope;
                                    if (scope != null) {
                                        file = null;
                                    } else {
                                        file = null;
                                    }
                                    if (file == null) {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    } else {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    }
                                }
                                textView2.setTypeface(typefaceDefaultFromStyle);
                                break;
                            case -696957295:
                                if (string.equals("default-bold")) {
                                    typefaceDefaultFromStyle = Typeface.defaultFromStyle(1);
                                } else {
                                    scope = this$0.scope;
                                    if (scope != null) {
                                        file = null;
                                    } else {
                                        file = null;
                                    }
                                    if (file == null) {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    } else {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    }
                                }
                                textView2.setTypeface(typefaceDefaultFromStyle);
                                break;
                            case 3029637:
                                if (string.equals("bold")) {
                                    typefaceDefaultFromStyle = Typeface.defaultFromStyle(1);
                                } else {
                                    scope = this$0.scope;
                                    if (scope != null) {
                                        file = null;
                                    } else {
                                        file = null;
                                    }
                                    if (file == null) {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    } else {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    }
                                }
                                textView2.setTypeface(typefaceDefaultFromStyle);
                                break;
                            case 3522707:
                                if (string.equals("sans")) {
                                    typefaceDefaultFromStyle = Typeface.SANS_SERIF;
                                } else {
                                    scope = this$0.scope;
                                    if (scope != null) {
                                        file = null;
                                    } else {
                                        file = null;
                                    }
                                    if (file == null) {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    } else {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    }
                                }
                                textView2.setTypeface(typefaceDefaultFromStyle);
                                break;
                            case 109326717:
                                if (string.equals("serif")) {
                                    typefaceDefaultFromStyle = Typeface.SERIF;
                                } else {
                                    scope = this$0.scope;
                                    if (scope != null) {
                                        file = null;
                                    } else {
                                        file = null;
                                    }
                                    if (file == null) {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    } else {
                                        typefaceDefaultFromStyle = Typeface.DEFAULT;
                                    }
                                }
                                textView2.setTypeface(typefaceDefaultFromStyle);
                                break;
                            default:
                                scope = this$0.scope;
                                if (scope != null) {
                                    file = null;
                                } else {
                                    file = null;
                                }
                                if (file == null) {
                                    typefaceDefaultFromStyle = Typeface.DEFAULT;
                                } else {
                                    typefaceDefaultFromStyle = Typeface.DEFAULT;
                                }
                                textView2.setTypeface(typefaceDefaultFromStyle);
                                break;
                        }
                        return;
                    }
                    return;
                case 21:
                    if (view instanceof TextView) {
                        TextView textView3 = (TextView) view;
                        Float floatOrNull10 = StringsKt.toFloatOrNull(valStr);
                        textView3.setLineSpacing(0.0f, floatOrNull10 != null ? floatOrNull10.floatValue() : 1.0f);
                        return;
                    }
                    return;
                case 22:
                    if (view instanceof EditText) {
                        Integer intOrNull7 = StringsKt.toIntOrNull(valStr);
                        int iIntValue2 = intOrNull7 != null ? intOrNull7.intValue() : -1;
                        if (iIntValue2 > 0) {
                            ((EditText) view).setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(iIntValue2)});
                            return;
                        }
                        return;
                    }
                    return;
                case 23:
                    if (view instanceof EditText) {
                        ((EditText) view).setHint(valStr);
                        return;
                    }
                    return;
                case 24:
                    if (view instanceof EditText) {
                        ((EditText) view).setHintTextColor(Color.parseColor(valStr));
                        return;
                    }
                    return;
                case 25:
                    if (view instanceof SwitchCompat) {
                        ((SwitchCompat) view).setText(valStr);
                        return;
                    }
                    return;
                case 26:
                    if (view instanceof SwitchCompat) {
                        ((SwitchCompat) view).setChecked(z);
                        return;
                    }
                    return;
                case 27:
                    if (view instanceof Button) {
                        ((Button) view).setAllCaps(z);
                        return;
                    }
                    return;
                case 28:
                    if (view instanceof ImageView) {
                        Integer intOrNull8 = StringsKt.toIntOrNull(valStr);
                        iIntValue = intOrNull8 != null ? intOrNull8.intValue() : 0;
                        ImageView imageView = (ImageView) view;
                        if (iIntValue == 1) {
                            scaleType = ImageView.ScaleType.CENTER_CROP;
                        } else if (iIntValue != 2) {
                            scaleType = iIntValue != 3 ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER;
                        } else {
                            scaleType = ImageView.ScaleType.FIT_XY;
                        }
                        imageView.setScaleType(scaleType);
                        return;
                    }
                    return;
                case 29:
                    if (view instanceof ImageView) {
                        if (!StringsKt.startsWith$default(valStr, "http://", false, 2, (Object) null) && !StringsKt.startsWith$default(valStr, "https://", false, 2, (Object) null)) {
                            Scope scope2 = this$0.scope;
                            File file2 = (scope2 == null || (project2 = scope2.getProject()) == null) ? null : project2.getFile(valStr);
                            if (file2 == null || !file2.exists()) {
                                return;
                            }
                            ((ImageView) view).setImageBitmap(BitmapFactory.decodeFile(file2.getAbsolutePath()));
                            return;
                        }
                        ThreadsKt.thread$default(true, false, null, null, 0, new NativeViewConfigAction$update$1$2(valStr, stage, view), 30, null);
                        return;
                    }
                    return;
                case 30:
                    if (view instanceof SeekBar) {
                        Object tag = ((SeekBar) view).getTag();
                        Pair pair = tag instanceof Pair ? (Pair) tag : null;
                        if (pair == null) {
                            pair = new Pair(Float.valueOf(0.0f), Float.valueOf(100.0f));
                        }
                        Float floatOrNull11 = StringsKt.toFloatOrNull(valStr);
                        ((SeekBar) view).setTag(new Pair(Float.valueOf(floatOrNull11 != null ? floatOrNull11.floatValue() : 0.0f), pair.getSecond()));
                        return;
                    }
                    return;
                case 31:
                    if (view instanceof SeekBar) {
                        Object tag2 = ((SeekBar) view).getTag();
                        Pair pair2 = tag2 instanceof Pair ? (Pair) tag2 : null;
                        if (pair2 == null) {
                            pair2 = new Pair(Float.valueOf(0.0f), Float.valueOf(100.0f));
                        }
                        Float floatOrNull12 = StringsKt.toFloatOrNull(valStr);
                        ((SeekBar) view).setTag(new Pair(pair2.getFirst(), Float.valueOf(floatOrNull12 != null ? floatOrNull12.floatValue() : 100.0f)));
                        return;
                    }
                    return;
                case 32:
                    if (view instanceof SeekBar) {
                        Object tag3 = ((SeekBar) view).getTag();
                        Pair pair3 = tag3 instanceof Pair ? (Pair) tag3 : null;
                        if (pair3 == null) {
                            pair3 = new Pair(Float.valueOf(0.0f), Float.valueOf(100.0f));
                        }
                        float fFloatValue4 = ((Number) pair3.getFirst()).floatValue();
                        float fFloatValue5 = ((Number) pair3.getSecond()).floatValue();
                        Float floatOrNull13 = StringsKt.toFloatOrNull(valStr);
                        ((SeekBar) view).setProgress(Math.max(0, Math.min(1000, (int) ((((floatOrNull13 != null ? floatOrNull13.floatValue() : fFloatValue4) - fFloatValue4) / (fFloatValue5 - fFloatValue4)) * 1000))));
                        return;
                    }
                    return;
                case 33:
                    View viewFindViewWithTag = view.findViewWithTag("scroll_content");
                    ViewParent parent2 = viewFindViewWithTag != null ? viewFindViewWithTag.getParent() : null;
                    if (parent2 instanceof ScrollView) {
                        ((ScrollView) parent2).setVerticalScrollBarEnabled(z);
                    }
                    if (parent2 instanceof HorizontalScrollView) {
                        ((HorizontalScrollView) parent2).setHorizontalScrollBarEnabled(z);
                        return;
                    }
                    return;
                case 34:
                    Integer intOrNull9 = StringsKt.toIntOrNull(valStr);
                    int iIntValue3 = intOrNull9 != null ? intOrNull9.intValue() : 1;
                    if (iIntValue3 == 0) {
                        i = 0;
                    } else if (iIntValue3 == 2) {
                        i = 2;
                    }
                    View viewFindViewWithTag2 = view.findViewWithTag("scroll_content");
                    Object parent3 = viewFindViewWithTag2 != null ? viewFindViewWithTag2.getParent() : null;
                    View view2 = parent3 instanceof View ? (View) parent3 : null;
                    if (view2 == null) {
                        return;
                    }
                    view2.setOverScrollMode(i);
                    return;
                case 35:
                    Integer intOrNull10 = StringsKt.toIntOrNull(valStr);
                    iIntValue = intOrNull10 != null ? intOrNull10.intValue() : 0;
                    View viewFindViewWithTag3 = view.findViewWithTag("scroll_content");
                    if (viewFindViewWithTag3 != null) {
                        viewFindViewWithTag3.setPadding(iIntValue, iIntValue, iIntValue, iIntValue);
                        Unit unit2 = Unit.INSTANCE;
                        return;
                    }
                    return;
                case 36:
                    List listSplit$default = StringsKt.split$default((CharSequence) valStr, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                    Iterator it = listSplit$default.iterator();
                    while (it.hasNext()) {
                        arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
                    }
                    ArrayList arrayList2 = arrayList;
                    String str4 = (String) CollectionsKt.getOrNull(arrayList2, 0);
                    int iIntValue4 = (str4 == null || (intOrNull = StringsKt.toIntOrNull(str4)) == null) ? 0 : intOrNull.intValue();
                    String str5 = (String) CollectionsKt.getOrNull(arrayList2, 1);
                    if (str5 != null) {
                        str3 = str5;
                    }
                    Drawable background2 = view.getBackground();
                    GradientDrawable gradientDrawable = background2 instanceof GradientDrawable ? (GradientDrawable) background2 : null;
                    if (gradientDrawable == null) {
                        gradientDrawable = new GradientDrawable();
                        gradientDrawable.setShape(0);
                        gradientDrawable.setColor(0);
                    }
                    try {
                        gradientDrawable.setStroke(iIntValue4, Color.parseColor(str3));
                        view.setBackground(gradientDrawable);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 37:
                    Integer intOrNull11 = StringsKt.toIntOrNull(valStr);
                    iIntValue = intOrNull11 != null ? intOrNull11.intValue() : 0;
                    view.setPadding(iIntValue, iIntValue, iIntValue, iIntValue);
                    return;
                case 38:
                    Float floatOrNull14 = StringsKt.toFloatOrNull(valStr);
                    float fFloatValue6 = floatOrNull14 != null ? floatOrNull14.floatValue() : 0.0f;
                    if (Build.VERSION.SDK_INT >= 31) {
                        if (fFloatValue6 > 0.0f) {
                            view.setRenderEffect(RenderEffect.createBlurEffect(fFloatValue6, fFloatValue6, Shader.TileMode.CLAMP));
                            return;
                        } else {
                            view.setRenderEffect(null);
                            return;
                        }
                    }
                    if (fFloatValue6 <= 0.0f) {
                        if (view instanceof ImageView) {
                            Object tag4 = view.getTag(R.id.brick_layout);
                            Bitmap bitmap2 = tag4 instanceof Bitmap ? (Bitmap) tag4 : null;
                            if (bitmap2 != null) {
                                ((ImageView) view).setImageBitmap(bitmap2);
                                view.setTag(R.id.brick_layout, null);
                                return;
                            }
                            return;
                        }
                        Object tag5 = view.getTag(R.id.brick_layout);
                        if (tag5 == null && view.getBackground() == null) {
                            return;
                        }
                        view.setBackground(tag5 instanceof Drawable ? (Drawable) tag5 : null);
                        view.setTag(R.id.brick_layout, null);
                        return;
                    }
                    if (!(view instanceof ImageView)) {
                        if (view.getTag(R.id.brick_layout) == null) {
                            view.setTag(R.id.brick_layout, view.getBackground());
                        }
                        Drawable background3 = view.getBackground();
                        if (background3 instanceof GradientDrawable) {
                            ((GradientDrawable) background3).setColor(Color.parseColor("#A0E0E0E0"));
                            return;
                        } else {
                            view.setBackgroundColor(Color.parseColor("#A0E0E0E0"));
                            return;
                        }
                    }
                    Drawable drawable = ((ImageView) view).getDrawable();
                    if (!(drawable instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null) {
                        return;
                    }
                    if (view.getTag(R.id.brick_layout) == null) {
                        view.setTag(R.id.brick_layout, bitmap);
                    }
                    Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.max(8, bitmap.getWidth() / 4), Math.max(8, bitmap.getHeight() / 4), false);
                    Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
                    ((ImageView) view).setImageBitmap(this$0.boxBlur(bitmapCreateScaledBitmap, Math.max(1, (int) (fFloatValue6 / 4))));
                    return;
                case 39:
                    List listSplit$default2 = StringsKt.split$default((CharSequence) valStr, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default2, 10));
                    Iterator it2 = listSplit$default2.iterator();
                    while (it2.hasNext()) {
                        arrayList3.add(StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) it2.next()).toString()));
                    }
                    ArrayList arrayList4 = arrayList3;
                    Float f2 = (Float) CollectionsKt.getOrNull(arrayList4, 0);
                    float fFloatValue7 = f2 != null ? f2.floatValue() : view.getWidth() / 2.0f;
                    Float f3 = (Float) CollectionsKt.getOrNull(arrayList4, 1);
                    float fFloatValue8 = f3 != null ? f3.floatValue() : view.getHeight() / 2.0f;
                    view.setPivotX(fFloatValue7);
                    view.setPivotY(fFloatValue8);
                    return;
                case 40:
                    String lowerCase2 = valStr.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                    if (!Intrinsics.areEqual(lowerCase2, "background")) {
                        String lowerCase3 = valStr.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                        z2 = Intrinsics.areEqual(lowerCase3, "задний");
                    }
                    ViewParent parent4 = view.getParent();
                    ViewGroup viewGroup = parent4 instanceof ViewGroup ? (ViewGroup) parent4 : null;
                    if (viewGroup != null) {
                        viewGroup.removeView(view);
                        Unit unit3 = Unit.INSTANCE;
                    }
                    Field declaredField3 = StageActivity.class.getDeclaredField(z2 ? "backgroundLayout" : "foregroundLayout");
                    declaredField3.setAccessible(true);
                    Object obj3 = declaredField3.get(stage);
                    ViewGroup viewGroup2 = obj3 instanceof ViewGroup ? (ViewGroup) obj3 : null;
                    if (viewGroup2 != null) {
                        viewGroup2.addView(view, view.getLayoutParams());
                        Unit unit4 = Unit.INSTANCE;
                    }
                    if (view instanceof SurfaceView) {
                        if (z2) {
                            ((SurfaceView) view).setZOrderOnTop(false);
                            return;
                        } else {
                            ((SurfaceView) view).setZOrderOnTop(true);
                            return;
                        }
                    }
                    return;
                case 41:
                    String lowerCase4 = valStr.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase4, "toLowerCase(...)");
                    if (!Intrinsics.areEqual(lowerCase4, "true")) {
                        String lowerCase5 = valStr.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase5, "toLowerCase(...)");
                        if (!Intrinsics.areEqual(lowerCase5, "истина") && !Intrinsics.areEqual(valStr, SchemaSymbols.ATTVAL_TRUE_1) && !z) {
                            iIntValue = 8;
                        }
                    }
                    NativeViewBindingManager.setDefaultVisibility(iIntValue);
                    return;
                case 42:
                    if (view instanceof WebView) {
                        Float floatOrNull15 = StringsKt.toFloatOrNull(valStr);
                        ((WebView) view).setInitialScale((int) (floatOrNull15 != null ? floatOrNull15.floatValue() : 100.0f));
                        return;
                    }
                    return;
                case 43:
                    if (view instanceof TextView) {
                        List listSplit$default3 = StringsKt.split$default((CharSequence) valStr, new String[]{","}, false, 0, 6, (Object) null);
                        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default3, 10));
                        Iterator it3 = listSplit$default3.iterator();
                        while (it3.hasNext()) {
                            arrayList5.add(StringsKt.trim((CharSequence) it3.next()).toString());
                        }
                        ArrayList arrayList6 = arrayList5;
                        String str6 = (String) CollectionsKt.getOrNull(arrayList6, 0);
                        int iIntValue5 = (str6 == null || (intOrNull3 = StringsKt.toIntOrNull(str6)) == null) ? 0 : intOrNull3.intValue();
                        String str7 = (String) CollectionsKt.getOrNull(arrayList6, 1);
                        int iIntValue6 = (str7 == null || (intOrNull2 = StringsKt.toIntOrNull(str7)) == null) ? 0 : intOrNull2.intValue();
                        String str8 = (String) CollectionsKt.getOrNull(arrayList6, 2);
                        if (str8 != null) {
                            str3 = str8;
                        }
                        SpannableString spannableString = new SpannableString(((TextView) view).getText());
                        int iCoerceAtLeast = RangesKt.coerceAtLeast(0, RangesKt.coerceAtMost(((TextView) view).getText().length(), iIntValue5));
                        int iCoerceAtLeast2 = RangesKt.coerceAtLeast(0, RangesKt.coerceAtMost(((TextView) view).getText().length(), iIntValue6));
                        if (iCoerceAtLeast < iCoerceAtLeast2) {
                            try {
                                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(str3)), iCoerceAtLeast, iCoerceAtLeast2, 33);
                                if (view instanceof EditText) {
                                    int selectionStart = ((EditText) view).getSelectionStart();
                                    int selectionEnd = ((EditText) view).getSelectionEnd();
                                    ((TextView) view).setText(spannableString, TextView.BufferType.SPANNABLE);
                                    int length = ((EditText) view).getText().length();
                                    ((EditText) view).setSelection(RangesKt.coerceAtMost(selectionStart, length), RangesKt.coerceAtMost(selectionEnd, length));
                                } else {
                                    ((TextView) view).setText(spannableString);
                                }
                                return;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                    return;
                case 44:
                    if (view instanceof TextView) {
                        List listSplit$default4 = StringsKt.split$default((CharSequence) valStr, new String[]{","}, false, 0, 6, (Object) null);
                        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default4, 10));
                        Iterator it4 = listSplit$default4.iterator();
                        while (it4.hasNext()) {
                            arrayList7.add(StringsKt.trim((CharSequence) it4.next()).toString());
                        }
                        ArrayList arrayList8 = arrayList7;
                        String str9 = (String) CollectionsKt.getOrNull(arrayList8, 0);
                        if (str9 != null) {
                            str2 = str9;
                        }
                        String str10 = (String) CollectionsKt.getOrNull(arrayList8, 1);
                        if (str10 != null) {
                            str3 = str10;
                        }
                        if (str2.length() > 0) {
                            try {
                                color = Color.parseColor(str3);
                                break;
                            } catch (Exception unused3) {
                                color = InputDeviceCompat.SOURCE_ANY;
                            }
                            Object tag6 = ((TextView) view).getTag();
                            final SyntaxConfig syntaxConfig = tag6 instanceof SyntaxConfig ? (SyntaxConfig) tag6 : null;
                            if (syntaxConfig == null) {
                                syntaxConfig = new SyntaxConfig();
                                ((TextView) view).setTag(syntaxConfig);
                            }
                            syntaxConfig.getForegroundWords().put(str2, Integer.valueOf(color));
                            if (syntaxConfig.getTextWatcher() == null) {
                                final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                                TextWatcher textWatcher = new TextWatcher() { // from class: org.catrobat.catroid.content.actions.NativeViewConfigAction$update$1$watcher$1
                                    @Override // android.text.TextWatcher
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }

                                    @Override // android.text.TextWatcher
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    }

                                    @Override // android.text.TextWatcher
                                    public void afterTextChanged(Editable s) {
                                        if (booleanRef.element || s == null) {
                                            return;
                                        }
                                        booleanRef.element = true;
                                        View view3 = view;
                                        if (!(view3 instanceof EditText)) {
                                            NativeViewConfigActionKt.applySyntaxHighlighting(s, syntaxConfig);
                                        } else {
                                            int selectionStart2 = ((EditText) view3).getSelectionStart();
                                            int selectionEnd2 = ((EditText) view).getSelectionEnd();
                                            NativeViewConfigActionKt.applySyntaxHighlighting(s, syntaxConfig);
                                            int length2 = ((EditText) view).getText().length();
                                            ((EditText) view).setSelection(RangesKt.coerceAtMost(selectionStart2, length2), RangesKt.coerceAtMost(selectionEnd2, length2));
                                        }
                                        booleanRef.element = false;
                                    }
                                };
                                syntaxConfig.setTextWatcher(textWatcher);
                                ((TextView) view).addTextChangedListener(textWatcher);
                            }
                            if (!(view instanceof EditText)) {
                                Editable editableNewEditable = Editable.Factory.getInstance().newEditable(((TextView) view).getText());
                                Intrinsics.checkNotNull(editableNewEditable);
                                NativeViewConfigActionKt.applySyntaxHighlighting(editableNewEditable, syntaxConfig);
                                ((TextView) view).setText(editableNewEditable);
                                return;
                            }
                            Editable text = ((EditText) view).getText();
                            if (text != null) {
                                NativeViewConfigActionKt.applySyntaxHighlighting(text, syntaxConfig);
                                Unit unit5 = Unit.INSTANCE;
                                Unit unit6 = Unit.INSTANCE;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 45:
                    if (view instanceof TextView) {
                        List listSplit$default5 = StringsKt.split$default((CharSequence) valStr, new String[]{","}, false, 0, 6, (Object) null);
                        ArrayList arrayList9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default5, 10));
                        Iterator it5 = listSplit$default5.iterator();
                        while (it5.hasNext()) {
                            arrayList9.add(StringsKt.trim((CharSequence) it5.next()).toString());
                        }
                        ArrayList arrayList10 = arrayList9;
                        String str11 = (String) CollectionsKt.getOrNull(arrayList10, 0);
                        int iIntValue7 = (str11 == null || (intOrNull5 = StringsKt.toIntOrNull(str11)) == null) ? 0 : intOrNull5.intValue();
                        String str12 = (String) CollectionsKt.getOrNull(arrayList10, 1);
                        int iIntValue8 = (str12 == null || (intOrNull4 = StringsKt.toIntOrNull(str12)) == null) ? 0 : intOrNull4.intValue();
                        String str13 = (String) CollectionsKt.getOrNull(arrayList10, 2);
                        if (str13 != null) {
                            str = str13;
                        }
                        SpannableString spannableString2 = new SpannableString(((TextView) view).getText());
                        int iCoerceAtLeast3 = RangesKt.coerceAtLeast(0, RangesKt.coerceAtMost(((TextView) view).getText().length(), iIntValue7));
                        int iCoerceAtLeast4 = RangesKt.coerceAtLeast(0, RangesKt.coerceAtMost(((TextView) view).getText().length(), iIntValue8));
                        if (iCoerceAtLeast3 < iCoerceAtLeast4) {
                            try {
                                spannableString2.setSpan(new BackgroundColorSpan(Color.parseColor(str)), iCoerceAtLeast3, iCoerceAtLeast4, 33);
                                if (view instanceof EditText) {
                                    int selectionStart2 = ((EditText) view).getSelectionStart();
                                    int selectionEnd2 = ((EditText) view).getSelectionEnd();
                                    ((TextView) view).setText(spannableString2, TextView.BufferType.SPANNABLE);
                                    int length2 = ((EditText) view).getText().length();
                                    ((EditText) view).setSelection(RangesKt.coerceAtMost(selectionStart2, length2), RangesKt.coerceAtMost(selectionEnd2, length2));
                                } else {
                                    ((TextView) view).setText(spannableString2);
                                }
                                return;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                    return;
                case 46:
                    if (view instanceof TextView) {
                        List listSplit$default6 = StringsKt.split$default((CharSequence) valStr, new String[]{","}, false, 0, 6, (Object) null);
                        ArrayList arrayList11 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default6, 10));
                        Iterator it6 = listSplit$default6.iterator();
                        while (it6.hasNext()) {
                            arrayList11.add(StringsKt.trim((CharSequence) it6.next()).toString());
                        }
                        ArrayList arrayList12 = arrayList11;
                        String str14 = (String) CollectionsKt.getOrNull(arrayList12, 0);
                        if (str14 != null) {
                            str2 = str14;
                        }
                        String str15 = (String) CollectionsKt.getOrNull(arrayList12, 1);
                        if (str15 != null) {
                            str = str15;
                        }
                        if (str2.length() > 0) {
                            try {
                                color2 = Color.parseColor(str);
                                break;
                            } catch (Exception unused4) {
                                color2 = InputDeviceCompat.SOURCE_ANY;
                            }
                            Object tag7 = ((TextView) view).getTag();
                            final SyntaxConfig syntaxConfig2 = tag7 instanceof SyntaxConfig ? (SyntaxConfig) tag7 : null;
                            if (syntaxConfig2 == null) {
                                syntaxConfig2 = new SyntaxConfig();
                                ((TextView) view).setTag(syntaxConfig2);
                            }
                            syntaxConfig2.getBackgroundWords().put(str2, Integer.valueOf(color2));
                            if (syntaxConfig2.getTextWatcher() == null) {
                                final Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
                                TextWatcher textWatcher2 = new TextWatcher() { // from class: org.catrobat.catroid.content.actions.NativeViewConfigAction$update$1$watcher$2
                                    @Override // android.text.TextWatcher
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }

                                    @Override // android.text.TextWatcher
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    }

                                    @Override // android.text.TextWatcher
                                    public void afterTextChanged(Editable s) {
                                        if (booleanRef2.element || s == null) {
                                            return;
                                        }
                                        booleanRef2.element = true;
                                        View view3 = view;
                                        if (!(view3 instanceof EditText)) {
                                            NativeViewConfigActionKt.applySyntaxHighlighting(s, syntaxConfig2);
                                        } else {
                                            int selectionStart3 = ((EditText) view3).getSelectionStart();
                                            int selectionEnd3 = ((EditText) view).getSelectionEnd();
                                            NativeViewConfigActionKt.applySyntaxHighlighting(s, syntaxConfig2);
                                            int length3 = ((EditText) view).getText().length();
                                            ((EditText) view).setSelection(RangesKt.coerceAtMost(selectionStart3, length3), RangesKt.coerceAtMost(selectionEnd3, length3));
                                        }
                                        booleanRef2.element = false;
                                    }
                                };
                                syntaxConfig2.setTextWatcher(textWatcher2);
                                ((TextView) view).addTextChangedListener(textWatcher2);
                            }
                            if (!(view instanceof EditText)) {
                                Editable editableNewEditable2 = Editable.Factory.getInstance().newEditable(((TextView) view).getText());
                                Intrinsics.checkNotNull(editableNewEditable2);
                                NativeViewConfigActionKt.applySyntaxHighlighting(editableNewEditable2, syntaxConfig2);
                                ((TextView) view).setText(editableNewEditable2);
                                return;
                            }
                            Editable text2 = ((EditText) view).getText();
                            if (text2 != null) {
                                NativeViewConfigActionKt.applySyntaxHighlighting(text2, syntaxConfig2);
                                Unit unit7 = Unit.INSTANCE;
                                Unit unit8 = Unit.INSTANCE;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 47:
                    OverlayViewManager.setViewAsOverlay(idStr, z);
                    return;
                case 48:
                    OverlayViewManager.setViewDraggable(idStr, z);
                    return;
                case 49:
                    OverlayViewManager.setDragHandle(idStr, valStr);
                    return;
                default:
                    return;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        e4.printStackTrace();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }
}
