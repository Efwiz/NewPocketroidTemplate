package org.jetbrains.kotlin.com.intellij.openapi.util.text;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.mediarouter.media.GlobalMediaRouter;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.physics.bullet.collision.CollisionConstants;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.apk.ApkUtil;
import java.beans.Introspector;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xml.serialize.Method;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.LineSeparator;
import org.jetbrains.kotlin.com.intellij.util.NotNullFunction;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.text.CharSequenceSubSequence;
import org.jetbrains.kotlin.com.intellij.util.text.MergingCharSequence;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.JvmNames;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: loaded from: classes3.dex */
public class StringUtil extends StringUtilRt {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ELLIPSIS = "…";
    public static final String THREE_DOTS = "...";
    public static final Function<String, String> QUOTER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return StringUtil.lambda$static$0((String) obj);
        }
    };
    public static final Function<String, String> SINGLE_QUOTER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil$$ExternalSyntheticLambda2
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return StringUtil.lambda$static$1((String) obj);
        }
    };
    public static final Function<String, String> TRIMMER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil$$ExternalSyntheticLambda3
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return StringUtil.trim((String) obj);
        }
    };
    private static final String[] ourPrepositions = {"a", "an", "and", "as", "at", "but", "by", "down", PsiKeyword.FOR, TypedValues.TransitionType.S_FROM, PsiKeyword.IF, "in", "into", "not", "of", "on", "onto", "or", "out", "over", "per", "nor", "the", "to", "up", "upon", "via", "with"};
    private static final String[] ourOtherNonCapitalizableWords = {"iOS", "iPhone", "iPad", "iMac"};
    private static final List<String> MN_QUOTED = Arrays.asList("&&", JvmNames.MULTIFILE_PART_NAME_DELIMITER);
    private static final List<String> MN_CHARS = Arrays.asList(ContainerUtils.FIELD_DELIMITER, "_");
    private static final Pattern UNICODE_CHAR = Pattern.compile("\\\\u[0-9a-fA-F]{4}");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 6:
            case 10:
            case 14:
            case 16:
            case 34:
            case 37:
            case 41:
            case 53:
            case 56:
            case 65:
            case 67:
            case 69:
            case 71:
            case 75:
            case 77:
            case 81:
            case 83:
            case 87:
            case 89:
            case 91:
            case 92:
            case 94:
            case 96:
            case 109:
            case 112:
            case 114:
            case 116:
            case 117:
            case 119:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
            case 133:
            case 136:
            case 138:
            case 140:
            case 142:
            case 144:
            case 146:
            case 149:
            case 151:
            case 162:
            case 163:
            case 167:
            case 169:
            case 170:
            case 173:
            case 178:
            case 181:
            case 185:
            case 189:
            case 193:
            case 196:
            case 200:
            case 207:
            case 213:
            case 215:
            case 216:
            case 218:
            case 219:
            case 220:
            case 222:
            case 223:
            case 225:
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_iacute /* 237 */:
            case 240:
            case 245:
            case 251:
            case 263:
            case 270:
            case 303:
            case 304:
            case 311:
            case 313:
            case 314:
            case 315:
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 325:
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
            case 334:
            case 340:
            case 342:
            case 344:
            case 347:
            case 349:
            case BrickValues.DRONE_ROTATION_MAX /* 350 */:
            case 351:
            case 354:
            case 356:
            case 359:
            case 365:
            case 367:
            case 368:
            case 377:
            case 379:
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
            case 385:
            case 386:
            case 387:
            case 389:
            case 392:
            case 395:
            case 397:
            case 399:
            case 401:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zcaron /* 430 */:
            case XKeySym.XK_ogonek /* 434 */:
            case 436:
            case XKeySym.XK_sacute /* 438 */:
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
            case 452:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 6:
            case 10:
            case 14:
            case 16:
            case 34:
            case 37:
            case 41:
            case 53:
            case 56:
            case 65:
            case 67:
            case 69:
            case 71:
            case 75:
            case 77:
            case 81:
            case 83:
            case 87:
            case 89:
            case 91:
            case 92:
            case 94:
            case 96:
            case 109:
            case 112:
            case 114:
            case 116:
            case 117:
            case 119:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
            case 133:
            case 136:
            case 138:
            case 140:
            case 142:
            case 144:
            case 146:
            case 149:
            case 151:
            case 162:
            case 163:
            case 167:
            case 169:
            case 170:
            case 173:
            case 178:
            case 181:
            case 185:
            case 189:
            case 193:
            case 196:
            case 200:
            case 207:
            case 213:
            case 215:
            case 216:
            case 218:
            case 219:
            case 220:
            case 222:
            case 223:
            case 225:
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_iacute /* 237 */:
            case 240:
            case 245:
            case 251:
            case 263:
            case 270:
            case 303:
            case 304:
            case 311:
            case 313:
            case 314:
            case 315:
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 325:
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
            case 334:
            case 340:
            case 342:
            case 344:
            case 347:
            case 349:
            case BrickValues.DRONE_ROTATION_MAX /* 350 */:
            case 351:
            case 354:
            case 356:
            case 359:
            case 365:
            case 367:
            case 368:
            case 377:
            case 379:
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
            case 385:
            case 386:
            case 387:
            case 389:
            case 392:
            case 395:
            case 397:
            case 399:
            case 401:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zcaron /* 430 */:
            case XKeySym.XK_ogonek /* 434 */:
            case 436:
            case XKeySym.XK_sacute /* 438 */:
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
            case 452:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 250:
                objArr[0] = "replacement";
                break;
            case 2:
                objArr[0] = "find";
                break;
            case 3:
            case 6:
            case 10:
            case 14:
            case 16:
            case 34:
            case 37:
            case 41:
            case 53:
            case 56:
            case 65:
            case 67:
            case 69:
            case 71:
            case 75:
            case 77:
            case 81:
            case 83:
            case 87:
            case 89:
            case 91:
            case 92:
            case 94:
            case 96:
            case 109:
            case 112:
            case 114:
            case 116:
            case 117:
            case 119:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
            case 133:
            case 136:
            case 138:
            case 140:
            case 142:
            case 144:
            case 146:
            case 149:
            case 151:
            case 162:
            case 163:
            case 167:
            case 169:
            case 170:
            case 173:
            case 178:
            case 181:
            case 185:
            case 189:
            case 193:
            case 196:
            case 200:
            case 207:
            case 213:
            case 215:
            case 216:
            case 218:
            case 219:
            case 220:
            case 222:
            case 223:
            case 225:
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_iacute /* 237 */:
            case 240:
            case 245:
            case 251:
            case 263:
            case 270:
            case 303:
            case 304:
            case 311:
            case 313:
            case 314:
            case 315:
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 325:
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
            case 334:
            case 340:
            case 342:
            case 344:
            case 347:
            case 349:
            case BrickValues.DRONE_ROTATION_MAX /* 350 */:
            case 351:
            case 354:
            case 356:
            case 359:
            case 365:
            case 367:
            case 368:
            case 377:
            case 379:
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
            case 385:
            case 386:
            case 387:
            case 389:
            case 392:
            case 395:
            case 397:
            case 399:
            case 401:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zcaron /* 430 */:
            case XKeySym.XK_ogonek /* 434 */:
            case 436:
            case XKeySym.XK_sacute /* 438 */:
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
            case 452:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/StringUtil";
                break;
            case 4:
            case 7:
            case 11:
            case 17:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 44:
            case 45:
            case 84:
            case 85:
            case 115:
            case 168:
            case 171:
            case 172:
            case 217:
            case 252:
            case 253:
            case 255:
            case 257:
            case 259:
            case 287:
            case 299:
            case 301:
            case 305:
            case 307:
            case 310:
            case 312:
            case 316:
            case 320:
            case 341:
            case 343:
            case 352:
            case 353:
            case 355:
            case 357:
            case 361:
            case 362:
            case 369:
            case 370:
            case 371:
            case 372:
            case 375:
            case 381:
            case 382:
            case HttpStatus.SC_CONFLICT /* 409 */:
            case HttpStatus.SC_GONE /* 410 */:
            case 411:
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE /* 415 */:
            case 419:
            case 420:
            case 421:
            case 423:
            case 425:
            case XKeySym.XK_Zacute /* 428 */:
            case XKeySym.XK_Lacute /* 453 */:
            case XKeySym.XK_Cacute /* 454 */:
                objArr[0] = "text";
                break;
            case 5:
                objArr[0] = "cls";
                break;
            case 8:
            case 12:
            case 18:
                objArr[0] = "oldS";
                break;
            case 9:
            case 13:
            case 19:
                objArr[0] = "newS";
                break;
            case 15:
            case 58:
            case 60:
            case 62:
            case 64:
            case 79:
            case 141:
            case XKeySym.XK_scedilla /* 442 */:
                objArr[0] = "buffer";
                break;
            case 20:
            case 22:
            case 24:
            case 25:
            case 26:
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
                objArr[0] = "where";
                break;
            case 21:
            case 23:
            case 27:
                objArr[0] = "what";
                break;
            case 28:
            case 30:
            case 57:
            case 59:
            case 61:
            case 63:
            case MediaError.DetailedErrorCode.DASH_NO_INIT /* 322 */:
            case 323:
            case 327:
            case 329:
            case 330:
            case MediaError.DetailedErrorCode.SMOOTH_NETWORK /* 331 */:
            case 333:
            case 336:
            case 339:
            case XKeySym.XK_Abreve /* 451 */:
                objArr[0] = "str";
                break;
            case 29:
            case 108:
            case 111:
            case 183:
            case 258:
            case 260:
                objArr[0] = "suffix";
                break;
            case 31:
            case 135:
            case 182:
            case 254:
            case 256:
                objArr[0] = "prefix";
                break;
            case 32:
            case 33:
                objArr[0] = Method.HTML;
                break;
            case 35:
            case 36:
            case XKeySym.XK_lstroke /* 435 */:
            case XKeySym.XK_lcaron /* 437 */:
            case XKeySym.XK_caron /* 439 */:
                objArr[0] = "fqName";
                break;
            case 46:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 266:
            case 268:
            case 271:
            case 402:
                objArr[0] = "s1";
                break;
            case 47:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case 265:
            case 267:
            case 269:
            case 272:
            case 403:
                objArr[0] = "s2";
                break;
            case 48:
            case 49:
            case 50:
            case 51:
            case 54:
            case 66:
            case 68:
            case 70:
            case 72:
            case 73:
            case 74:
            case 76:
            case 78:
            case 80:
            case 88:
            case 90:
            case 93:
            case 95:
            case 107:
            case 110:
            case 113:
            case 134:
            case 148:
            case 150:
            case 152:
            case 154:
            case 156:
            case 158:
            case 160:
            case 164:
            case XKeySym.XK_ediaeresis /* 235 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case XKeySym.XK_ntilde /* 241 */:
            case 243:
            case 246:
            case 273:
            case 274:
            case 275:
            case 276:
            case 285:
            case 286:
            case 289:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 293:
            case 295:
            case 297:
            case 309:
            case 348:
            case 383:
            case 390:
            case 391:
            case 400:
            case 404:
            case XKeySym.XK_zabovedot /* 447 */:
            case XKeySym.XK_Racute /* 448 */:
                objArr[0] = "s";
                break;
            case 52:
            case 55:
                objArr[0] = "prepositions";
                break;
            case 82:
            case XKeySym.XK_adiaeresis /* 228 */:
                objArr[0] = "word";
                break;
            case 86:
                objArr[0] = "tokenizerDelim";
                break;
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case XKeySym.XK_ccedilla /* 231 */:
            case XKeySym.XK_eacute /* 233 */:
            case ParserBasicInformation.SCOPE_UBOUND /* 290 */:
            case 292:
            case 294:
            case 296:
            case 298:
            case 360:
                objArr[0] = "chars";
                break;
            case 105:
            case 118:
            case 120:
            case 122:
            case 126:
            case 128:
            case 130:
            case 248:
            case 393:
            case 394:
            case 396:
            case 398:
            case 431:
            case XKeySym.XK_zacute /* 444 */:
                objArr[0] = "string";
                break;
            case 106:
                objArr[0] = "prefixes";
                break;
            case 124:
            case 132:
            case 337:
            case 338:
            case 358:
                objArr[0] = "builder";
                break;
            case 137:
            case AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP /* 384 */:
            case 388:
                objArr[0] = "name";
                break;
            case 139:
                objArr[0] = ApkUtil.DEF_MODULE_NAME;
                break;
            case 143:
                objArr[0] = "defaultValue";
                break;
            case 145:
                objArr[0] = "aThrowable";
                break;
            case 147:
                objArr[0] = "e";
                break;
            case 153:
            case 155:
            case 157:
            case 159:
            case 161:
            case 174:
            case 176:
            case 187:
            case 192:
            case 195:
            case 199:
            case 203:
            case 206:
            case 209:
            case 211:
                objArr[0] = "separator";
                break;
            case 165:
                objArr[0] = "separators";
                break;
            case 166:
                objArr[0] = "tokenizer";
                break;
            case 175:
            case 177:
            case 184:
            case 205:
            case 208:
            case 212:
            case 214:
            case 366:
                objArr[0] = "strings";
                break;
            case 179:
                objArr[0] = "strings1";
                break;
            case 180:
                objArr[0] = "strings2";
                break;
            case 186:
            case 191:
            case 198:
            case 202:
                objArr[0] = "f";
                break;
            case 188:
            case 190:
            case 194:
            case 197:
            case 201:
                objArr[0] = FirebaseAnalytics.Param.ITEMS;
                break;
            case 204:
            case 210:
                objArr[0] = "result";
                break;
            case 221:
            case 226:
                objArr[0] = "unitSeparator";
                break;
            case 224:
                objArr[0] = "duration";
                break;
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_ae /* 230 */:
            case XKeySym.XK_egrave /* 232 */:
            case XKeySym.XK_ecircumflex /* 234 */:
                objArr[0] = "value";
                break;
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_idiaeresis /* 239 */:
            case 247:
                objArr[0] = "filter";
                break;
            case 242:
            case 244:
                objArr[0] = "pattern";
                break;
            case 249:
                objArr[0] = "range";
                break;
            case 277:
            case 279:
            case 281:
            case 283:
            case XKeySym.XK_breve /* 418 */:
            case XKeySym.XK_scaron /* 441 */:
                objArr[0] = "sequence";
                break;
            case 278:
            case 280:
            case 282:
            case 284:
                objArr[0] = "infix";
                break;
            case 288:
            case 300:
            case 302:
            case 306:
            case 308:
                objArr[0] = "subString";
                break;
            case 324:
                objArr[0] = FirebaseAnalytics.Param.CHARACTER;
                break;
            case 326:
            case 328:
            case 335:
                objArr[0] = "buf";
                break;
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
            case 346:
                objArr[0] = "htmlString";
                break;
            case 363:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
            case 364:
                objArr[0] = "to";
                break;
            case 373:
                objArr[0] = "args";
                break;
            case 374:
                objArr[0] = "methodName";
                break;
            case 376:
                objArr[0] = "input";
                break;
            case 378:
                objArr[0] = "className";
                break;
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            case 412:
                objArr[0] = NativeSymbol.TYPE_NAME;
                break;
            case 416:
            case 417:
                objArr[0] = "path";
                break;
            case XKeySym.XK_Scedilla /* 426 */:
            case Constants.ERROR_TOO_MANY_REQUESTS /* 429 */:
                objArr[0] = "newSeparator";
                break;
            case 432:
                objArr[0] = "clazz";
                break;
            case XKeySym.XK_aogonek /* 433 */:
                objArr[0] = "aClass";
                break;
            case 440:
                objArr[0] = "shortName";
                break;
            case 443:
                objArr[0] = "end";
                break;
            case XKeySym.XK_doubleacute /* 445 */:
                objArr[0] = "smallPart";
                break;
            case XKeySym.XK_zcaron /* 446 */:
                objArr[0] = "bigPart";
                break;
            case 449:
                objArr[0] = "bytes";
                break;
            default:
                objArr[0] = "charSeq";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getWordsInStringLongestFirst";
                break;
            case 6:
                objArr[1] = "createToStringFunction";
                break;
            case 10:
            case 365:
                objArr[1] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 14:
                objArr[1] = "replaceIgnoreCase";
                break;
            case 16:
                objArr[1] = "replaceChar";
                break;
            case 34:
                objArr[1] = "stripHtml";
                break;
            case 37:
                objArr[1] = "getPackageName";
                break;
            case 41:
                objArr[1] = "escapeLineBreak";
                break;
            case 53:
                objArr[1] = "fixCapitalization";
                break;
            case 56:
                objArr[1] = "escaper";
                break;
            case 65:
            case 67:
                objArr[1] = "escapeStringCharacters";
                break;
            case 69:
                objArr[1] = "escapeCharCharacters";
                break;
            case 71:
                objArr[1] = "unescapeStringCharacters";
                break;
            case 75:
            case 77:
                objArr[1] = "unquoteString";
                break;
            case 81:
                objArr[1] = "unescapeAnsiStringCharacters";
                break;
            case 83:
            case 140:
                objArr[1] = "pluralize";
                break;
            case 87:
                objArr[1] = "capitalizeWords";
                break;
            case 89:
            case 91:
            case 92:
                objArr[1] = "decapitalize";
                break;
            case 94:
                objArr[1] = "capitalize";
                break;
            case 96:
                objArr[1] = "capitalizeWithJavaBeanConvention";
                break;
            case 109:
            case 112:
            case 114:
                objArr[1] = "trimEnd";
                break;
            case 116:
            case 117:
                objArr[1] = "trimLog";
                break;
            case 119:
            case 121:
            case 123:
            case 125:
                objArr[1] = "trimLeading";
                break;
            case 127:
            case 129:
            case 131:
            case 133:
                objArr[1] = "trimTrailing";
                break;
            case 136:
                objArr[1] = "trimStart";
                break;
            case 138:
                objArr[1] = "trimExtensions";
                break;
            case 142:
            case 144:
                objArr[1] = "notNullize";
                break;
            case 146:
                objArr[1] = "getThrowableText";
                break;
            case 149:
                objArr[1] = "repeat";
                break;
            case 151:
                objArr[1] = "splitHonorQuotes";
                break;
            case 162:
            case 163:
                objArr[1] = "split";
                break;
            case 167:
                objArr[1] = "tokenize";
                break;
            case 169:
            case 170:
                objArr[1] = "getWordsIn";
                break;
            case 173:
                objArr[1] = "getWordIndicesIn";
                break;
            case 178:
            case 189:
            case 193:
            case 196:
            case 200:
            case 207:
            case 213:
            case 215:
                objArr[1] = "join";
                break;
            case 181:
                objArr[1] = ArchiveStreamFactory.ZIP;
                break;
            case 185:
                objArr[1] = "surround";
                break;
            case 216:
                objArr[1] = "joining";
                break;
            case 218:
            case 219:
                objArr[1] = "stripQuotesAroundValue";
                break;
            case 220:
            case 222:
                objArr[1] = "formatFileSize";
                break;
            case 223:
            case 225:
            case XKeySym.XK_atilde /* 227 */:
                objArr[1] = "formatDuration";
                break;
            case XKeySym.XK_iacute /* 237 */:
                objArr[1] = "strip";
                break;
            case 240:
                objArr[1] = "trim";
                break;
            case 245:
                objArr[1] = "findMatches";
                break;
            case 251:
                objArr[1] = "replaceSubstring";
                break;
            case 263:
                objArr[1] = "commonPrefix";
                break;
            case 270:
                objArr[1] = "commonSuffix";
                break;
            case 303:
            case 304:
                objArr[1] = "substringBeforeLast";
                break;
            case 311:
            case 313:
            case 314:
            case 315:
                objArr[1] = "first";
                break;
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case 319:
                objArr[1] = "last";
                break;
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
                objArr[1] = "firstLast";
                break;
            case 325:
                objArr[1] = "escapeChars";
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
                objArr[1] = "unescapeBackSlashes";
                break;
            case 334:
                objArr[1] = "unescapeChar";
                break;
            case 340:
                objArr[1] = "wrapWithDoubleQuote";
                break;
            case 342:
                objArr[1] = "unescapeXmlEntities";
                break;
            case 344:
                objArr[1] = "escapeXmlEntities";
                break;
            case 347:
                objArr[1] = "removeHtmlTags";
                break;
            case 349:
            case BrickValues.DRONE_ROTATION_MAX /* 350 */:
            case 351:
                objArr[1] = "removeEllipsisSuffix";
                break;
            case 354:
                objArr[1] = "htmlEmphasize";
                break;
            case 356:
            case 359:
                objArr[1] = "escapeToRegexp";
                break;
            case 367:
            case 368:
                objArr[1] = "filterEmptyStrings";
                break;
            case 377:
                objArr[1] = "escapeProperty";
                break;
            case 379:
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
                objArr[1] = "getQualifiedName";
                break;
            case 385:
            case 386:
            case 387:
                objArr[1] = "fixVariableNameDerivedFromPropertyName";
                break;
            case 389:
                objArr[1] = "sanitizeJavaIdentifier";
                break;
            case 392:
                objArr[1] = "tail";
                break;
            case 395:
                objArr[1] = "splitByLines";
                break;
            case 397:
                objArr[1] = "splitByLinesDontTrim";
                break;
            case 399:
                objArr[1] = "splitByLinesKeepSeparators";
                break;
            case 401:
                objArr[1] = "getWordsWithOffset";
                break;
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
                objArr[1] = "collapseWhiteSpace";
                break;
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
                objArr[1] = "formatLinks";
                break;
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
                objArr[1] = "shortenTextWithEllipsis";
                break;
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zcaron /* 430 */:
                objArr[1] = "convertLineSeparators";
                break;
            case XKeySym.XK_ogonek /* 434 */:
            case 436:
            case XKeySym.XK_sacute /* 438 */:
                objArr[1] = "getShortName";
                break;
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
                objArr[1] = "toHexString";
                break;
            case 452:
                objArr[1] = "parseHexString";
                break;
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
                objArr[1] = "naturalJoin";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/StringUtil";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "getWordsInStringLongestFirst";
                break;
            case 3:
            case 6:
            case 10:
            case 14:
            case 16:
            case 34:
            case 37:
            case 41:
            case 53:
            case 56:
            case 65:
            case 67:
            case 69:
            case 71:
            case 75:
            case 77:
            case 81:
            case 83:
            case 87:
            case 89:
            case 91:
            case 92:
            case 94:
            case 96:
            case 109:
            case 112:
            case 114:
            case 116:
            case 117:
            case 119:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
            case 133:
            case 136:
            case 138:
            case 140:
            case 142:
            case 144:
            case 146:
            case 149:
            case 151:
            case 162:
            case 163:
            case 167:
            case 169:
            case 170:
            case 173:
            case 178:
            case 181:
            case 185:
            case 189:
            case 193:
            case 196:
            case 200:
            case 207:
            case 213:
            case 215:
            case 216:
            case 218:
            case 219:
            case 220:
            case 222:
            case 223:
            case 225:
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_iacute /* 237 */:
            case 240:
            case 245:
            case 251:
            case 263:
            case 270:
            case 303:
            case 304:
            case 311:
            case 313:
            case 314:
            case 315:
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 325:
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
            case 334:
            case 340:
            case 342:
            case 344:
            case 347:
            case 349:
            case BrickValues.DRONE_ROTATION_MAX /* 350 */:
            case 351:
            case 354:
            case 356:
            case 359:
            case 365:
            case 367:
            case 368:
            case 377:
            case 379:
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
            case 385:
            case 386:
            case 387:
            case 389:
            case 392:
            case 395:
            case 397:
            case 399:
            case 401:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zcaron /* 430 */:
            case XKeySym.XK_ogonek /* 434 */:
            case 436:
            case XKeySym.XK_sacute /* 438 */:
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
            case 452:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
                break;
            case 4:
                objArr[2] = "escapePattern";
                break;
            case 5:
                objArr[2] = "createToStringFunction";
                break;
            case 7:
            case 8:
            case 9:
            case 17:
            case 18:
            case 19:
            case 362:
            case 363:
            case 364:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "replaceIgnoreCase";
                break;
            case 15:
                objArr[2] = "replaceChar";
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "indexOfIgnoreCase";
                break;
            case 25:
                objArr[2] = "lastIndexOfIgnoreCase";
                break;
            case 26:
            case 27:
                objArr[2] = "containsIgnoreCase";
                break;
            case 28:
            case 29:
                objArr[2] = "endsWithIgnoreCase";
                break;
            case 30:
            case 31:
                objArr[2] = "startsWithIgnoreCase";
                break;
            case 32:
            case 33:
                objArr[2] = "stripHtml";
                break;
            case 35:
            case 36:
                objArr[2] = "getPackageName";
                break;
            case 38:
                objArr[2] = "getLineBreakCount";
                break;
            case 39:
                objArr[2] = "containsLineBreak";
                break;
            case 40:
                objArr[2] = "escapeLineBreak";
                break;
            case 42:
                objArr[2] = "endsWithLineBreak";
                break;
            case 43:
                objArr[2] = "lineColToOffset";
                break;
            case 44:
                objArr[2] = "offsetToLineNumber";
                break;
            case 45:
                objArr[2] = "offsetToLineColumn";
                break;
            case 46:
            case 47:
                objArr[2] = "difference";
                break;
            case 48:
                objArr[2] = "wordsToBeginFromUpperCase";
                break;
            case 49:
                objArr[2] = "wordsToBeginFromLowerCase";
                break;
            case 50:
                objArr[2] = "toTitleCase";
                break;
            case 51:
            case 52:
                objArr[2] = "fixCapitalization";
                break;
            case 54:
            case 55:
                objArr[2] = "isPreposition";
                break;
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 66:
                objArr[2] = "escapeStringCharacters";
                break;
            case 68:
                objArr[2] = "escapeCharCharacters";
                break;
            case 70:
            case 78:
            case 79:
                objArr[2] = "unescapeStringCharacters";
                break;
            case 72:
                objArr[2] = "isQuoteAt";
                break;
            case 73:
                objArr[2] = "isQuotedString";
                break;
            case 74:
            case 76:
                objArr[2] = "unquoteString";
                break;
            case 80:
                objArr[2] = "unescapeAnsiStringCharacters";
                break;
            case 82:
            case 139:
                objArr[2] = "pluralize";
                break;
            case 84:
            case 85:
            case 86:
                objArr[2] = "capitalizeWords";
                break;
            case 88:
            case 90:
                objArr[2] = "decapitalize";
                break;
            case 93:
                objArr[2] = "capitalize";
                break;
            case 95:
                objArr[2] = "capitalizeWithJavaBeanConvention";
                break;
            case 97:
            case 98:
            case 99:
                objArr[2] = "stringHashCode";
                break;
            case 100:
            case 101:
            case 102:
            case 103:
                objArr[2] = "stringHashCodeInsensitive";
                break;
            case 104:
                objArr[2] = "stringHashCodeIgnoreWhitespaces";
                break;
            case 105:
            case 106:
                objArr[2] = "startsWithConcatenation";
                break;
            case 107:
            case 108:
            case 110:
            case 111:
            case 113:
            case XKeySym.XK_scedilla /* 442 */:
            case 443:
                objArr[2] = "trimEnd";
                break;
            case 115:
                objArr[2] = "trimLog";
                break;
            case 118:
            case 120:
            case 122:
            case 124:
                objArr[2] = "trimLeading";
                break;
            case 126:
            case 128:
            case 130:
            case 132:
                objArr[2] = "trimTrailing";
                break;
            case 134:
            case 135:
                objArr[2] = "trimStart";
                break;
            case 137:
                objArr[2] = "trimExtensions";
                break;
            case 141:
                objArr[2] = "repeatSymbol";
                break;
            case 143:
                objArr[2] = "notNullize";
                break;
            case 145:
                objArr[2] = "getThrowableText";
                break;
            case 147:
                objArr[2] = "getMessage";
                break;
            case 148:
                objArr[2] = "repeat";
                break;
            case 150:
                objArr[2] = "splitHonorQuotes";
                break;
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
                objArr[2] = "split";
                break;
            case 164:
            case 165:
            case 166:
                objArr[2] = "tokenize";
                break;
            case 168:
                objArr[2] = "getWordsIn";
                break;
            case 171:
            case 172:
                objArr[2] = "getWordIndicesIn";
                break;
            case 174:
            case 175:
            case 176:
            case 177:
            case 186:
            case 187:
            case 188:
            case 190:
            case 191:
            case 192:
            case 194:
            case 195:
            case 197:
            case 198:
            case 199:
            case 201:
            case 202:
            case 203:
            case 204:
            case 205:
            case 206:
            case 208:
            case 209:
            case 210:
            case 211:
            case 212:
            case 214:
                objArr[2] = "join";
                break;
            case 179:
            case 180:
                objArr[2] = ArchiveStreamFactory.ZIP;
                break;
            case 182:
            case 183:
            case 184:
                objArr[2] = "surround";
                break;
            case 217:
                objArr[2] = "stripQuotesAroundValue";
                break;
            case 221:
                objArr[2] = "formatFileSize";
                break;
            case 224:
            case 226:
                objArr[2] = "formatDuration";
                break;
            case XKeySym.XK_adiaeresis /* 228 */:
                objArr[2] = "unpluralize";
                break;
            case XKeySym.XK_aring /* 229 */:
                objArr[2] = "containsAlphaCharacters";
                break;
            case XKeySym.XK_ae /* 230 */:
            case XKeySym.XK_ccedilla /* 231 */:
            case XKeySym.XK_egrave /* 232 */:
            case XKeySym.XK_eacute /* 233 */:
                objArr[2] = "containsAnyChar";
                break;
            case XKeySym.XK_ecircumflex /* 234 */:
                objArr[2] = "containsChar";
                break;
            case XKeySym.XK_ediaeresis /* 235 */:
            case XKeySym.XK_igrave /* 236 */:
                objArr[2] = "strip";
                break;
            case XKeySym.XK_icircumflex /* 238 */:
            case XKeySym.XK_idiaeresis /* 239 */:
                objArr[2] = "trim";
                break;
            case XKeySym.XK_ntilde /* 241 */:
            case 242:
            case 243:
            case 244:
                objArr[2] = "findMatches";
                break;
            case 246:
            case 247:
                objArr[2] = "findFirst";
                break;
            case 248:
            case 249:
            case 250:
                objArr[2] = "replaceSubstring";
                break;
            case 252:
                objArr[2] = "startsWithWhitespace";
                break;
            case 253:
            case 254:
            case 255:
            case 256:
                objArr[2] = "startsWith";
                break;
            case 257:
            case 258:
            case 259:
            case 260:
                objArr[2] = "endsWith";
                break;
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
                objArr[2] = "commonPrefix";
                break;
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 265:
            case 266:
            case 267:
                objArr[2] = "commonPrefixLength";
                break;
            case 268:
            case 269:
                objArr[2] = "commonSuffix";
                break;
            case 271:
            case 272:
                objArr[2] = "commonSuffixLength";
                break;
            case 273:
            case 277:
            case 278:
                objArr[2] = "contains";
                break;
            case 274:
            case 275:
            case 276:
            case 279:
            case 280:
            case 281:
            case 282:
            case 283:
            case 284:
            case 285:
            case 286:
                objArr[2] = "indexOf";
                break;
            case 287:
            case 288:
                objArr[2] = "indexOfSubstringEnd";
                break;
            case 289:
            case ParserBasicInformation.SCOPE_UBOUND /* 290 */:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 292:
            case 293:
            case 294:
            case 295:
            case 296:
                objArr[2] = "indexOfAny";
                break;
            case 297:
            case 298:
                objArr[2] = "lastIndexOfAny";
                break;
            case 299:
            case 300:
                objArr[2] = "substringBefore";
                break;
            case 301:
            case 302:
                objArr[2] = "substringBeforeLast";
                break;
            case 305:
            case 306:
                objArr[2] = "substringAfter";
                break;
            case 307:
            case 308:
                objArr[2] = "substringAfterLast";
                break;
            case 309:
                objArr[2] = "lastIndexOf";
                break;
            case 310:
            case 312:
                objArr[2] = "first";
                break;
            case 316:
                objArr[2] = "last";
                break;
            case 320:
                objArr[2] = "firstLast";
                break;
            case MediaError.DetailedErrorCode.DASH_NO_INIT /* 322 */:
            case 326:
                objArr[2] = "escapeChar";
                break;
            case 323:
            case 324:
                objArr[2] = "escapeChars";
                break;
            case 327:
            case 328:
                objArr[2] = "escapeQuotes";
                break;
            case 329:
                objArr[2] = "escapeSlashes";
                break;
            case 330:
                objArr[2] = "escapeBackSlashes";
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NETWORK /* 331 */:
                objArr[2] = "unescapeBackSlashes";
                break;
            case 333:
            case 335:
            case 336:
                objArr[2] = "unescapeChar";
                break;
            case 337:
            case 338:
                objArr[2] = "quote";
                break;
            case 339:
                objArr[2] = "wrapWithDoubleQuote";
                break;
            case 341:
                objArr[2] = "unescapeXmlEntities";
                break;
            case 343:
                objArr[2] = "escapeXmlEntities";
                break;
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
            case 346:
                objArr[2] = "removeHtmlTags";
                break;
            case 348:
                objArr[2] = "removeEllipsisSuffix";
                break;
            case 352:
                objArr[2] = "escapeMnemonics";
                break;
            case 353:
                objArr[2] = "htmlEmphasize";
                break;
            case 355:
            case 357:
            case 358:
                objArr[2] = "escapeToRegexp";
                break;
            case 360:
            case 361:
                objArr[2] = "isEscapedBackslash";
                break;
            case 366:
                objArr[2] = "filterEmptyStrings";
                break;
            case 369:
                objArr[2] = "countNewLines";
                break;
            case 370:
            case 371:
            case 372:
                objArr[2] = "countChars";
                break;
            case 373:
                objArr[2] = "joinOrNull";
                break;
            case 374:
                objArr[2] = "getPropertyName";
                break;
            case 375:
                objArr[2] = "isJavaIdentifier";
                break;
            case 376:
                objArr[2] = "escapeProperty";
                break;
            case 378:
                objArr[2] = "getQualifiedName";
                break;
            case 381:
            case 382:
            case 383:
                objArr[2] = "getOccurrenceCount";
                break;
            case AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP /* 384 */:
                objArr[2] = "fixVariableNameDerivedFromPropertyName";
                break;
            case 388:
                objArr[2] = "sanitizeJavaIdentifier";
                break;
            case 390:
                objArr[2] = "assertValidSeparators";
                break;
            case 391:
                objArr[2] = "tail";
                break;
            case 393:
            case 394:
                objArr[2] = "splitByLines";
                break;
            case 396:
                objArr[2] = "splitByLinesDontTrim";
                break;
            case 398:
                objArr[2] = "splitByLinesKeepSeparators";
                break;
            case 400:
                objArr[2] = "getWordsWithOffset";
                break;
            case 402:
            case 403:
                objArr[2] = "equalsTrimWhitespaces";
                break;
            case 404:
                objArr[2] = "collapseWhiteSpace";
                break;
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
                objArr[2] = "findIgnoreCase";
                break;
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
                objArr[2] = "formatLinks";
                break;
            case HttpStatus.SC_CONFLICT /* 409 */:
            case 411:
            case 412:
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE /* 415 */:
                objArr[2] = "shortenTextWithEllipsis";
                break;
            case HttpStatus.SC_GONE /* 410 */:
                objArr[2] = "trimMiddle";
                break;
            case 416:
            case 417:
                objArr[2] = "shortenPathWithEllipsis";
                break;
            case XKeySym.XK_breve /* 418 */:
                objArr[2] = "isUpperCase";
                break;
            case 419:
                objArr[2] = "detectSeparators";
                break;
            case 420:
                objArr[2] = "getLineSeparatorAt";
                break;
            case 421:
            case 423:
            case 425:
            case XKeySym.XK_Scedilla /* 426 */:
            case XKeySym.XK_Zacute /* 428 */:
            case Constants.ERROR_TOO_MANY_REQUESTS /* 429 */:
                objArr[2] = "convertLineSeparators";
                break;
            case 431:
            case 432:
                objArr[2] = "parseEnum";
                break;
            case XKeySym.XK_aogonek /* 433 */:
            case XKeySym.XK_lstroke /* 435 */:
            case XKeySym.XK_lcaron /* 437 */:
                objArr[2] = "getShortName";
                break;
            case XKeySym.XK_caron /* 439 */:
            case 440:
                objArr[2] = "isShortNameOf";
                break;
            case XKeySym.XK_scaron /* 441 */:
                objArr[2] = "newBombedCharSequence";
                break;
            case XKeySym.XK_zacute /* 444 */:
            case XKeySym.XK_doubleacute /* 445 */:
            case XKeySym.XK_zcaron /* 446 */:
                objArr[2] = "isBetween";
                break;
            case XKeySym.XK_zabovedot /* 447 */:
                objArr[2] = "hasUpperCaseChar";
                break;
            case XKeySym.XK_Racute /* 448 */:
                objArr[2] = "hasLowerCaseChar";
                break;
            case 449:
                objArr[2] = "toHexString";
                break;
            case XKeySym.XK_Abreve /* 451 */:
                objArr[2] = "parseHexString";
                break;
            case XKeySym.XK_Lacute /* 453 */:
                objArr[2] = "skipWhitespaceForward";
                break;
            case XKeySym.XK_Cacute /* 454 */:
                objArr[2] = "skipWhitespaceBackward";
                break;
            default:
                objArr[2] = "replaceSubSequence";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 6:
            case 10:
            case 14:
            case 16:
            case 34:
            case 37:
            case 41:
            case 53:
            case 56:
            case 65:
            case 67:
            case 69:
            case 71:
            case 75:
            case 77:
            case 81:
            case 83:
            case 87:
            case 89:
            case 91:
            case 92:
            case 94:
            case 96:
            case 109:
            case 112:
            case 114:
            case 116:
            case 117:
            case 119:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
            case 133:
            case 136:
            case 138:
            case 140:
            case 142:
            case 144:
            case 146:
            case 149:
            case 151:
            case 162:
            case 163:
            case 167:
            case 169:
            case 170:
            case 173:
            case 178:
            case 181:
            case 185:
            case 189:
            case 193:
            case 196:
            case 200:
            case 207:
            case 213:
            case 215:
            case 216:
            case 218:
            case 219:
            case 220:
            case 222:
            case 223:
            case 225:
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_iacute /* 237 */:
            case 240:
            case 245:
            case 251:
            case 263:
            case 270:
            case 303:
            case 304:
            case 311:
            case 313:
            case 314:
            case 315:
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 325:
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
            case 334:
            case 340:
            case 342:
            case 344:
            case 347:
            case 349:
            case BrickValues.DRONE_ROTATION_MAX /* 350 */:
            case 351:
            case 354:
            case 356:
            case 359:
            case 365:
            case 367:
            case 368:
            case 377:
            case 379:
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
            case 385:
            case 386:
            case 387:
            case 389:
            case 392:
            case 395:
            case 397:
            case 399:
            case 401:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zcaron /* 430 */:
            case XKeySym.XK_ogonek /* 434 */:
            case 436:
            case XKeySym.XK_sacute /* 438 */:
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
            case 452:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public static boolean isDecimalDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isHexDigit(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }

    public static boolean isLineBreak(char c) {
        return c == '\n' || c == '\r';
    }

    public static boolean isOctalDigit(char c) {
        return '0' <= c && c <= '7';
    }

    public static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y';
    }

    public static boolean isWhiteSpace(char c) {
        return c == '\n' || c == '\t' || c == ' ';
    }

    private static boolean isWhitespaceOrTab(char c) {
        return c == ' ' || c == '\t';
    }

    private static final class Splitters {
        private static final Pattern EOL_SPLIT_KEEP_SEPARATORS = Pattern.compile("(?<=(\r\n|\n))|(?<=\r)(?=[^\n])");
        private static final Pattern EOL_SPLIT_PATTERN = Pattern.compile(" *(\r|\n|\r\n)+ *");
        private static final Pattern EOL_SPLIT_PATTERN_WITH_EMPTY = Pattern.compile(" *(\r|\n|\r\n) *");
        private static final Pattern EOL_SPLIT_DONT_TRIM_PATTERN = Pattern.compile("(\r|\n|\r\n)+");
    }

    public static MergingCharSequence replaceSubSequence(CharSequence charSequence, int i, int i2, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(1);
        }
        return new MergingCharSequence(new MergingCharSequence(new CharSequenceSubSequence(charSequence, 0, i), charSequence2), new CharSequenceSubSequence(charSequence, i2, charSequence.length()));
    }

    private static final class MyHtml2Text extends HTMLEditorKit.ParserCallback {
        private final StringBuilder myBuffer;
        private final boolean myIsSkipStyleTag;
        private boolean myIsStyleTagOpened;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 5 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "text";
            } else if (i == 2 || i == 3 || i == 4) {
                objArr[0] = "tag";
            } else if (i != 5) {
                objArr[0] = "in";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/StringUtil$MyHtml2Text";
            }
            if (i != 5) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/StringUtil$MyHtml2Text";
            } else {
                objArr[1] = "getText";
            }
            if (i == 1) {
                objArr[2] = "handleText";
            } else if (i == 2) {
                objArr[2] = "handleStartTag";
            } else if (i == 3) {
                objArr[2] = "handleEndTag";
            } else if (i == 4) {
                objArr[2] = "handleTag";
            } else if (i != 5) {
                objArr[2] = "parse";
            }
            String str2 = String.format(str, objArr);
            if (i == 5) {
                throw new IllegalStateException(str2);
            }
        }

        private MyHtml2Text(boolean z) {
            this.myBuffer = new StringBuilder();
            this.myIsSkipStyleTag = z;
        }

        public void parse(Reader reader) throws IOException {
            if (reader == null) {
                $$$reportNull$$$0(0);
            }
            this.myBuffer.setLength(0);
            new ParserDelegator().parse(reader, this, Boolean.TRUE.booleanValue());
        }

        public void handleText(char[] cArr, int i) {
            if (cArr == null) {
                $$$reportNull$$$0(1);
            }
            if (this.myIsStyleTagOpened) {
                return;
            }
            this.myBuffer.append(cArr);
        }

        public void handleStartTag(HTML.Tag tag, MutableAttributeSet mutableAttributeSet, int i) {
            if (tag == null) {
                $$$reportNull$$$0(2);
            }
            if (this.myIsSkipStyleTag && "style".equals(tag.toString())) {
                this.myIsStyleTagOpened = true;
            }
            handleTag(tag);
        }

        public void handleEndTag(HTML.Tag tag, int i) {
            if (tag == null) {
                $$$reportNull$$$0(3);
            }
            if (this.myIsSkipStyleTag && "style".equals(tag.toString())) {
                this.myIsStyleTagOpened = false;
            }
        }

        public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet mutableAttributeSet, int i) {
            handleTag(tag);
        }

        private void handleTag(HTML.Tag tag) {
            if (tag == null) {
                $$$reportNull$$$0(4);
            }
            if (!tag.breaksFlow() || this.myBuffer.length() <= 0) {
                return;
            }
            this.myBuffer.append(System.lineSeparator());
        }

        public String getText() {
            String string = this.myBuffer.toString();
            if (string == null) {
                $$$reportNull$$$0(5);
            }
            return string;
        }
    }

    static /* synthetic */ String lambda$static$0(String str) {
        return "\"" + str + "\"";
    }

    static /* synthetic */ String lambda$static$1(String str) {
        return FormatHelper.QUOTE + str + FormatHelper.QUOTE;
    }

    public static List<String> getWordsInStringLongestFirst(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        List<String> wordsIn = getWordsIn(str);
        wordsIn.sort(new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return StringUtil.lambda$getWordsInStringLongestFirst$2((String) obj, (String) obj2);
            }
        });
        if (wordsIn == null) {
            $$$reportNull$$$0(3);
        }
        return wordsIn;
    }

    static /* synthetic */ int lambda$getWordsInStringLongestFirst$2(String str, String str2) {
        return str2.length() - str.length();
    }

    public static String escapePattern(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return replace(replace(str, FormatHelper.QUOTE, "''"), "{", "'{'");
    }

    @Deprecated
    public static <T> org.jetbrains.kotlin.com.intellij.util.Function<T, String> createToStringFunction(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(5);
        }
        return new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return obj.toString();
            }
        };
    }

    public static String replace(String str, String str2, String str3) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (str2 == null) {
            $$$reportNull$$$0(8);
        }
        if (str3 == null) {
            $$$reportNull$$$0(9);
        }
        String strReplace = replace(str, str2, str3, false);
        if (strReplace == null) {
            $$$reportNull$$$0(10);
        }
        return strReplace;
    }

    public static String replaceIgnoreCase(String str, String str2, String str3) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        if (str2 == null) {
            $$$reportNull$$$0(12);
        }
        if (str3 == null) {
            $$$reportNull$$$0(13);
        }
        String strReplace = replace(str, str2, str3, true);
        if (strReplace == null) {
            $$$reportNull$$$0(14);
        }
        return strReplace;
    }

    @Deprecated
    public static String replaceChar(String str, char c, char c2) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        String strReplace = str.replace(c, c2);
        if (strReplace == null) {
            $$$reportNull$$$0(16);
        }
        return strReplace;
    }

    public static String replace(String str, String str2, String str3, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        if (str2 == null) {
            $$$reportNull$$$0(18);
        }
        if (str3 == null) {
            $$$reportNull$$$0(19);
        }
        if (str.length() < str2.length()) {
            return str;
        }
        StringBuilder sb = null;
        int length = 0;
        while (length < str.length()) {
            int iIndexOfIgnoreCase = z ? indexOfIgnoreCase(str, str2, length) : str.indexOf(str2, length);
            if (iIndexOfIgnoreCase < 0) {
                if (length != 0) {
                    sb.append((CharSequence) str, length, str.length());
                    break;
                }
                return str;
            }
            if (sb == null) {
                if (str.length() == str2.length()) {
                    return str3;
                }
                sb = new StringBuilder(str.length() - length);
            }
            sb.append((CharSequence) str, length, iIndexOfIgnoreCase);
            sb.append(str3);
            length = str2.length() + iIndexOfIgnoreCase;
        }
        return sb != null ? sb.toString() : "";
    }

    public static int indexOfIgnoreCase(String str, String str2, int i) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        if (str2 == null) {
            $$$reportNull$$$0(21);
        }
        return Strings.indexOfIgnoreCase(str, str2, i);
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(22);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(23);
        }
        return Strings.indexOfIgnoreCase(charSequence, charSequence2, i);
    }

    public static int indexOfIgnoreCase(String str, char c, int i) {
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        return Strings.indexOfIgnoreCase(str, c, i);
    }

    public static int lastIndexOfIgnoreCase(String str, char c, int i) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        for (int iMin = Math.min(i, str.length() - 1); iMin >= 0; iMin--) {
            if (charsEqualIgnoreCase(str.charAt(iMin), c)) {
                return iMin;
            }
        }
        return -1;
    }

    public static boolean containsIgnoreCase(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        if (str2 == null) {
            $$$reportNull$$$0(27);
        }
        return indexOfIgnoreCase(str, str2, 0) >= 0;
    }

    public static boolean endsWithIgnoreCase(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        if (str2 == null) {
            $$$reportNull$$$0(29);
        }
        return Strings.endsWithIgnoreCase(str, str2);
    }

    public static boolean startsWithIgnoreCase(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        if (str2 == null) {
            $$$reportNull$$$0(31);
        }
        return StringUtilRt.startsWithIgnoreCase(str, str2);
    }

    public static String stripHtml(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        return stripHtml(str, z ? "\n\n" : null);
    }

    public static String stripHtml(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(33);
        }
        if (str2 != null) {
            str = str.replaceAll("<br/?>", str2);
        }
        String strReplaceAll = str.replaceAll("<(.|\n)*?>", "");
        if (strReplaceAll == null) {
            $$$reportNull$$$0(34);
        }
        return strReplaceAll;
    }

    public static String toLowerCase(String str) {
        return Strings.toLowerCase(str);
    }

    public static String getPackageName(String str) {
        if (str == null) {
            $$$reportNull$$$0(35);
        }
        return getPackageName(str, '.');
    }

    public static String getPackageName(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(36);
        }
        int iLastIndexOf = str.lastIndexOf(c);
        if (iLastIndexOf >= 0) {
            String strSubstring = str.substring(0, iLastIndexOf);
            if (strSubstring == null) {
                $$$reportNull$$$0(37);
            }
            return strSubstring;
        }
        return "";
    }

    public static int getLineBreakCount(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(38);
        }
        int i = 0;
        int i2 = 0;
        while (i < charSequence.length()) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt == '\n') {
                i2++;
            } else if (cCharAt == '\r') {
                int i3 = i + 1;
                if (i3 < charSequence.length() && charSequence.charAt(i3) == '\n') {
                    i = i3;
                }
                i2++;
            }
            i++;
        }
        return i2;
    }

    public static boolean containsLineBreak(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(39);
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (isLineBreak(charSequence.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String escapeLineBreak(String str) {
        if (str == null) {
            $$$reportNull$$$0(40);
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(41);
        }
        return string;
    }

    public static boolean endsWithLineBreak(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(42);
        }
        int length = charSequence.length();
        return length > 0 && isLineBreak(charSequence.charAt(length - 1));
    }

    public static int lineColToOffset(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(43);
        }
        int i3 = 0;
        int i4 = 0;
        while (i != i3) {
            if (i4 == charSequence.length()) {
                return -1;
            }
            char cCharAt = charSequence.charAt(i4);
            if (cCharAt == '\n') {
                i3++;
            } else if (cCharAt == '\r') {
                i3++;
                if (i4 < charSequence.length() - 1) {
                    int i5 = i4 + 1;
                    if (charSequence.charAt(i5) == '\n') {
                        i4 = i5;
                    }
                }
            }
            i4++;
        }
        return i4 + i2;
    }

    public static int offsetToLineNumber(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(44);
        }
        LineColumn lineColumnOffsetToLineColumn = offsetToLineColumn(charSequence, i);
        if (lineColumnOffsetToLineColumn != null) {
            return lineColumnOffsetToLineColumn.line;
        }
        return -1;
    }

    public static LineColumn offsetToLineColumn(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(45);
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < i) {
            if (i2 == charSequence.length()) {
                return null;
            }
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt == '\n') {
                i3++;
            } else {
                if (cCharAt == '\r') {
                    i3++;
                    if (i2 < charSequence.length() - 1) {
                        int i5 = i2 + 1;
                        if (charSequence.charAt(i5) == '\n') {
                            i2 = i5;
                        }
                    }
                }
                i2++;
            }
            i4 = i2 + 1;
            i2++;
        }
        return LineColumn.of(i3, i - i4);
    }

    public static int difference(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(46);
        }
        if (str2 == null) {
            $$$reportNull$$$0(47);
        }
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, str.length(), str2.length());
        for (int i = 0; i < str.length(); i++) {
            iArr[i][0] = i;
        }
        for (int i2 = 0; i2 < str2.length(); i2++) {
            iArr[0][i2] = i2;
        }
        for (int i3 = 1; i3 < str.length(); i3++) {
            for (int i4 = 1; i4 < str2.length(); i4++) {
                int i5 = i3 - 1;
                int i6 = i4 - 1;
                iArr[i3][i4] = Math.min(Math.min(iArr[i5][i6] + (str.charAt(i3) == str2.charAt(i4) ? 0 : 1), iArr[i5][i4] + 1), iArr[i3][i6] + 1);
            }
        }
        return iArr[str.length() - 1][str2.length() - 1];
    }

    public static String wordsToBeginFromUpperCase(String str) {
        if (str == null) {
            $$$reportNull$$$0(48);
        }
        return fixCapitalization(str, ArrayUtil.mergeArrays(ourPrepositions, ourOtherNonCapitalizableWords), true);
    }

    public static String wordsToBeginFromLowerCase(String str) {
        if (str == null) {
            $$$reportNull$$$0(49);
        }
        return fixCapitalization(str, ourPrepositions, false);
    }

    public static String toTitleCase(String str) {
        if (str == null) {
            $$$reportNull$$$0(50);
        }
        return fixCapitalization(str, ArrayUtilRt.EMPTY_STRING_ARRAY, true);
    }

    private static String fixCapitalization(String str, String[] strArr, boolean z) {
        int i;
        if (str == null) {
            $$$reportNull$$$0(51);
        }
        if (strArr == null) {
            $$$reportNull$$$0(52);
        }
        StringBuilder sb = null;
        int i2 = 0;
        while (i2 < str.length()) {
            char cCharAt = i2 == 0 ? ' ' : str.charAt(i2 - 1);
            char cCharAt2 = str.charAt(i2);
            if (!Character.isLetterOrDigit(cCharAt) && cCharAt != '\'' && Character.isLetterOrDigit(cCharAt2) && (z || Character.isUpperCase(cCharAt2))) {
                int i3 = i2;
                while (i3 < str.length() && Character.isLetterOrDigit(str.charAt(i3))) {
                    i3++;
                }
                if (z || i3 <= (i = i2 + 1) || Character.isLowerCase(str.charAt(i))) {
                    char cCharAt3 = i2 > 1 ? str.charAt(i2 - 2) : (char) 0;
                    if ((cCharAt != '.' || (cCharAt3 != ' ' && cCharAt3 != '*')) && ((cCharAt != '~' || cCharAt3 != ' ') && !isPreposition(str, i2, i3 - 1, strArr))) {
                        if (sb == null) {
                            sb = new StringBuilder(str);
                        }
                        sb.setCharAt(i2, z ? toUpperCase(cCharAt2) : toLowerCase(cCharAt2));
                    }
                }
            }
            i2++;
        }
        if (sb != null) {
            str = sb.toString();
        }
        if (str == null) {
            $$$reportNull$$$0(53);
        }
        return str;
    }

    public static boolean isPreposition(String str, int i, int i2, String[] strArr) {
        if (str == null) {
            $$$reportNull$$$0(54);
        }
        if (strArr == null) {
            $$$reportNull$$$0(55);
        }
        for (String str2 : strArr) {
            if ((i2 - i) + 1 == str2.length()) {
                for (int i3 = 0; i3 < str2.length(); i3++) {
                    if (toLowerCase(str.charAt(i + i3)) == toLowerCase(str2.charAt(i3))) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static NotNullFunction<String, String> escaper(final boolean z, final String str) {
        return new NotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return StringUtil.lambda$escaper$3(str, z, (String) obj);
            }
        };
    }

    static /* synthetic */ String lambda$escaper$3(String str, boolean z, String str2) {
        StringBuilder sb = new StringBuilder(str2.length());
        escapeStringCharacters(str2.length(), str2, str, z, sb);
        return sb.toString();
    }

    public static void escapeStringCharacters(int i, String str, StringBuilder sb) {
        if (str == null) {
            $$$reportNull$$$0(57);
        }
        if (sb == null) {
            $$$reportNull$$$0(58);
        }
        escapeStringCharacters(i, str, "\"", sb);
    }

    public static StringBuilder escapeStringCharacters(int i, String str, String str2, StringBuilder sb) {
        if (str == null) {
            $$$reportNull$$$0(59);
        }
        if (sb == null) {
            $$$reportNull$$$0(60);
        }
        return escapeStringCharacters(i, str, str2, true, sb);
    }

    public static StringBuilder escapeStringCharacters(int i, String str, String str2, boolean z, StringBuilder sb) {
        if (str == null) {
            $$$reportNull$$$0(61);
        }
        if (sb == null) {
            $$$reportNull$$$0(62);
        }
        return escapeStringCharacters(i, str, str2, z, true, sb);
    }

    public static StringBuilder escapeStringCharacters(int i, String str, String str2, boolean z, boolean z2, StringBuilder sb) {
        if (str == null) {
            $$$reportNull$$$0(63);
        }
        if (sb == null) {
            $$$reportNull$$$0(64);
        }
        int i2 = 0;
        char c = 0;
        while (i2 < i) {
            char cCharAt = str.charAt(i2);
            switch (cCharAt) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case 11:
                default:
                    if (z && cCharAt == '\\') {
                        sb.append("\\\\");
                    } else if (str2 != null && str2.indexOf(cCharAt) > -1 && (z || c != '\\')) {
                        sb.append("\\").append(cCharAt);
                    } else if (z2 && !isPrintableUnicode(cCharAt)) {
                        String upperCase = toUpperCase(Integer.toHexString(cCharAt));
                        sb.append("\\u");
                        int length = 4 - upperCase.length();
                        while (true) {
                            int i3 = length - 1;
                            if (length > 0) {
                                sb.append(0);
                                length = i3;
                            } else {
                                sb.append((CharSequence) upperCase);
                            }
                        }
                    } else {
                        sb.append(cCharAt);
                    }
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
            }
            i2++;
            c = cCharAt;
        }
        if (sb == null) {
            $$$reportNull$$$0(65);
        }
        return sb;
    }

    public static boolean isPrintableUnicode(char c) {
        int type = Character.getType(c);
        return (type == 0 || type == 13 || type == 14 || type == 15 || type == 16 || type == 18 || type == 19) ? false : true;
    }

    public static String escapeStringCharacters(String str) {
        if (str == null) {
            $$$reportNull$$$0(66);
        }
        StringBuilder sb = new StringBuilder(str.length());
        escapeStringCharacters(str.length(), str, "\"", sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(67);
        }
        return string;
    }

    public static String escapeCharCharacters(String str) {
        if (str == null) {
            $$$reportNull$$$0(68);
        }
        StringBuilder sb = new StringBuilder(str.length());
        escapeStringCharacters(str.length(), str, FormatHelper.QUOTE, sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(69);
        }
        return string;
    }

    public static String unescapeStringCharacters(String str) {
        if (str == null) {
            $$$reportNull$$$0(70);
        }
        StringBuilder sb = new StringBuilder(str.length());
        unescapeStringCharacters(str.length(), str, sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(71);
        }
        return string;
    }

    private static boolean isQuoteAt(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(72);
        }
        char cCharAt = str.charAt(i);
        return cCharAt == '\'' || cCharAt == '\"';
    }

    public static boolean isQuotedString(String str) {
        if (str == null) {
            $$$reportNull$$$0(73);
        }
        return StringUtilRt.isQuotedString(str);
    }

    public static String unquoteString(String str) {
        if (str == null) {
            $$$reportNull$$$0(74);
        }
        String strUnquoteString = StringUtilRt.unquoteString(str);
        if (strUnquoteString == null) {
            $$$reportNull$$$0(75);
        }
        return strUnquoteString;
    }

    public static String unquoteString(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(76);
        }
        String strUnquoteString = StringUtilRt.unquoteString(str, c);
        if (strUnquoteString == null) {
            $$$reportNull$$$0(77);
        }
        return strUnquoteString;
    }

    private static void unescapeStringCharacters(int i, String str, StringBuilder sb) {
        int i2;
        int i3;
        if (str == null) {
            $$$reportNull$$$0(78);
        }
        if (sb == null) {
            $$$reportNull$$$0(79);
        }
        int i4 = 0;
        boolean z = false;
        while (i4 < i) {
            char cCharAt = str.charAt(i4);
            if (z) {
                if (cCharAt == '\"') {
                    sb.append('\"');
                } else if (cCharAt == '\'') {
                    sb.append('\'');
                } else if (cCharAt == '\\') {
                    sb.append('\\');
                } else if (cCharAt == 'b') {
                    sb.append('\b');
                } else if (cCharAt == 'f') {
                    sb.append('\f');
                } else if (cCharAt == 'n') {
                    sb.append('\n');
                } else if (cCharAt == 'r') {
                    sb.append('\r');
                } else if (cCharAt == 't') {
                    sb.append('\t');
                } else if (cCharAt != 'u') {
                    switch (cCharAt) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                            i2 = 3;
                            i3 = i4 + 1;
                            while (i3 < i && i3 < i4 + i2 && isOctalDigit(str.charAt(i3))) {
                                i3++;
                            }
                            try {
                                sb.append((char) Integer.parseInt(str.substring(i4, i3), 8));
                                i4 = i3 - 1;
                            } catch (NumberFormatException e) {
                                throw new RuntimeException("Couldn't parse " + str.substring(i4, i3), e);
                            }
                            break;
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                            i2 = 2;
                            i3 = i4 + 1;
                            while (i3 < i) {
                                i3++;
                            }
                            sb.append((char) Integer.parseInt(str.substring(i4, i3), 8));
                            i4 = i3 - 1;
                            break;
                        default:
                            sb.append(cCharAt);
                            break;
                    }
                } else {
                    int i5 = i4 + 4;
                    if (i5 < i) {
                        try {
                            try {
                                sb.append((char) Integer.parseInt(str.substring(i4 + 1, i4 + 5), 16));
                                i4 = i5;
                            } catch (NumberFormatException unused) {
                                i4 = i5;
                                sb.append("\\u");
                            }
                        } catch (NumberFormatException unused2) {
                        }
                    } else {
                        sb.append("\\u");
                    }
                }
                z = false;
            } else if (cCharAt == '\\') {
                z = true;
            } else {
                sb.append(cCharAt);
            }
            i4++;
        }
        if (z) {
            sb.append('\\');
        }
    }

    /* JADX WARN: Code duplicated, block: B:71:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:81:0x00dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static String unescapeAnsiStringCharacters(String str) {
        if (str == null) {
            $$$reportNull$$$0(80);
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int length2 = 0;
        boolean z = false;
        boolean z2 = false;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (length2 < length) {
            char cCharAt = str.charAt(length2);
            if (z) {
                if (cCharAt == '\"') {
                    sb.append('\"');
                } else if (cCharAt == '\'') {
                    sb.append('\'');
                } else if (cCharAt == '?') {
                    sb.append(MangleConstant.Q_MARK);
                } else if (cCharAt == 'U') {
                    z2 = true;
                    i2 = 1;
                    i3 = 16;
                    i = 8;
                } else if (cCharAt == '\\') {
                    sb.append('\\');
                } else if (cCharAt == 'f') {
                    sb.append('\f');
                } else if (cCharAt == 'n') {
                    sb.append('\n');
                } else if (cCharAt != 'r') {
                    if (cCharAt != 'x') {
                        if (cCharAt != 'a') {
                            if (cCharAt == 'b') {
                                sb.append('\b');
                            } else {
                                switch (cCharAt) {
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                        i = 3;
                                        i2 = 0;
                                        z2 = true;
                                        i3 = 8;
                                        break;
                                    default:
                                        switch (cCharAt) {
                                            case 't':
                                                sb.append('\t');
                                                break;
                                            case 'u':
                                                i = 4;
                                                break;
                                            case 'v':
                                                sb.append((char) 11);
                                                break;
                                            default:
                                                sb.append(cCharAt);
                                                break;
                                        }
                                        break;
                                }
                            }
                        } else {
                            sb.append((char) 7);
                        }
                    } else {
                        i = 2;
                    }
                    z2 = true;
                    i2 = 1;
                    i3 = 16;
                } else {
                    sb.append('\r');
                }
                if (z2) {
                    StringBuilder sb2 = new StringBuilder(i);
                    for (int i4 = length2 + i2; i4 < length && i > 0; i4++) {
                        char cCharAt2 = str.charAt(i4);
                        if ((i3 == 16 && isHexDigit(cCharAt2)) || (i3 == 8 && isOctalDigit(cCharAt2))) {
                            sb2.append(cCharAt2);
                            i--;
                        } else {
                            if (sb2.length() != 0) {
                                try {
                                    long j = Long.parseLong(sb2.toString(), i3);
                                    length2 += (sb2.length() + i2) - 1;
                                    sb.append((char) j);
                                } catch (NumberFormatException unused) {
                                    sb.append('\\').append(cCharAt);
                                }
                            } else {
                                sb.append('\\').append(cCharAt);
                            }
                            z2 = false;
                        }
                    }
                    if (sb2.length() != 0) {
                        long j2 = Long.parseLong(sb2.toString(), i3);
                        length2 += (sb2.length() + i2) - 1;
                        sb.append((char) j2);
                    } else {
                        sb.append('\\').append(cCharAt);
                    }
                    z2 = false;
                }
                z = false;
            } else if (cCharAt == '\\') {
                z = true;
            } else {
                sb.append(cCharAt);
            }
            length2++;
        }
        if (z) {
            sb.append('\\');
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(81);
        }
        return string;
    }

    public static String pluralize(String str) {
        if (str == null) {
            $$$reportNull$$$0(82);
        }
        String strPluralize = Strings.pluralize(str);
        if (strPluralize == null) {
            $$$reportNull$$$0(83);
        }
        return strPluralize;
    }

    public static String capitalizeWords(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(84);
        }
        return capitalizeWords(str, " \t\n\r\f([<", z, true);
    }

    public static String capitalizeWords(String str, String str2, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(85);
        }
        if (str2 == null) {
            $$$reportNull$$$0(86);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, str2, z2);
        StringBuilder sb = new StringBuilder(str.length());
        boolean z3 = true;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (!z2 && sb.length() > 0) {
                sb.append(io.netty.util.internal.StringUtil.SPACE);
            }
            if (z3) {
                strNextToken = capitalize(strNextToken);
            }
            sb.append(strNextToken);
            if (!z) {
                z3 = false;
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(87);
        }
        return string;
    }

    public static String decapitalize(String str) {
        if (str == null) {
            $$$reportNull$$$0(88);
        }
        String strDecapitalize = Introspector.decapitalize(str);
        if (strDecapitalize == null) {
            $$$reportNull$$$0(89);
        }
        return strDecapitalize;
    }

    public static String decapitalize(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(90);
        }
        if (str == null || str.length() == 0) {
            if (str == null) {
                $$$reportNull$$$0(91);
            }
            return str;
        }
        if (z || str.length() <= 1 || !Character.isUpperCase(str.charAt(1)) || !Character.isUpperCase(str.charAt(0))) {
            char[] charArray = str.toCharArray();
            charArray[0] = Character.toLowerCase(charArray[0]);
            return new String(charArray);
        }
        if (str == null) {
            $$$reportNull$$$0(92);
        }
        return str;
    }

    public static String capitalize(String str) {
        if (str == null) {
            $$$reportNull$$$0(93);
        }
        String strCapitalize = Strings.capitalize(str);
        if (strCapitalize == null) {
            $$$reportNull$$$0(94);
        }
        return strCapitalize;
    }

    public static boolean isCapitalized(String str) {
        return Strings.isCapitalized(str);
    }

    public static String capitalizeWithJavaBeanConvention(String str) {
        if (str == null) {
            $$$reportNull$$$0(95);
        }
        if (str.length() <= 1 || !Character.isUpperCase(str.charAt(1))) {
            return capitalize(str);
        }
        if (str == null) {
            $$$reportNull$$$0(96);
        }
        return str;
    }

    public static int stringHashCode(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(97);
        }
        return Strings.stringHashCode(charSequence);
    }

    public static int stringHashCode(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(98);
        }
        return Strings.stringHashCode(charSequence, i, i2);
    }

    public static int stringHashCode(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(99);
        }
        return Strings.stringHashCode(charSequence, i, i2, i3);
    }

    public static int stringHashCode(char[] cArr, int i, int i2) {
        return Strings.stringHashCode(cArr, i, i2);
    }

    public static int stringHashCodeInsensitive(char[] cArr, int i, int i2) {
        if (cArr == null) {
            $$$reportNull$$$0(100);
        }
        return Strings.stringHashCodeInsensitive(cArr, i, i2);
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(101);
        }
        return Strings.stringHashCodeInsensitive(charSequence, i, i2);
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(102);
        }
        return Strings.stringHashCodeInsensitive(charSequence, i, i2, i3);
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(103);
        }
        return Strings.stringHashCodeInsensitive(charSequence);
    }

    public static int stringHashCodeIgnoreWhitespaces(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(104);
        }
        int i = 0;
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            char cCharAt = charSequence.charAt(i2);
            if (!isWhiteSpace(cCharAt)) {
                i = (i * 31) + cCharAt;
            }
        }
        return i;
    }

    public static boolean startsWithConcatenation(String str, String... strArr) {
        if (str == null) {
            $$$reportNull$$$0(105);
        }
        if (strArr == null) {
            $$$reportNull$$$0(106);
        }
        int i = 0;
        for (String str2 : strArr) {
            int length = str2.length();
            if (!str.regionMatches(i, str2, 0, length)) {
                return false;
            }
            i += length;
        }
        return true;
    }

    public static String trim(String str) {
        return Strings.trim(str);
    }

    public static String trimEnd(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(107);
        }
        if (str2 == null) {
            $$$reportNull$$$0(108);
        }
        String strTrimEnd = Strings.trimEnd(str, str2);
        if (strTrimEnd == null) {
            $$$reportNull$$$0(109);
        }
        return strTrimEnd;
    }

    public static String trimEnd(String str, String str2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(110);
        }
        if (str2 == null) {
            $$$reportNull$$$0(111);
        }
        String strTrimEnd = Strings.trimEnd(str, str2, z);
        if (strTrimEnd == null) {
            $$$reportNull$$$0(112);
        }
        return strTrimEnd;
    }

    public static String trimEnd(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(113);
        }
        String strTrimEnd = Strings.trimEnd(str, c);
        if (strTrimEnd == null) {
            $$$reportNull$$$0(114);
        }
        return strTrimEnd;
    }

    public static String trimLog(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(115);
        }
        if (i <= 5 || str.length() <= i) {
            if (str == null) {
                $$$reportNull$$$0(117);
            }
            return str;
        }
        String str2 = str.substring(0, i - 5) + " ...\n";
        if (str2 == null) {
            $$$reportNull$$$0(116);
        }
        return str2;
    }

    public static String trimLeading(String str) {
        if (str == null) {
            $$$reportNull$$$0(118);
        }
        String string = trimLeading((CharSequence) str).toString();
        if (string == null) {
            $$$reportNull$$$0(119);
        }
        return string;
    }

    public static CharSequence trimLeading(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(120);
        }
        int i = 0;
        while (i < charSequence.length() && Character.isWhitespace(charSequence.charAt(i))) {
            i++;
        }
        CharSequence charSequenceSubSequence = charSequence.subSequence(i, charSequence.length());
        if (charSequenceSubSequence == null) {
            $$$reportNull$$$0(121);
        }
        return charSequenceSubSequence;
    }

    public static String trimLeading(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(122);
        }
        int i = 0;
        while (i < str.length() && str.charAt(i) == c) {
            i++;
        }
        String strSubstring = str.substring(i);
        if (strSubstring == null) {
            $$$reportNull$$$0(123);
        }
        return strSubstring;
    }

    public static StringBuilder trimLeading(StringBuilder sb, char c) {
        if (sb == null) {
            $$$reportNull$$$0(124);
        }
        int i = 0;
        while (i < sb.length() && sb.charAt(i) == c) {
            i++;
        }
        if (i > 0) {
            sb.delete(0, i);
        }
        if (sb == null) {
            $$$reportNull$$$0(125);
        }
        return sb;
    }

    public static String trimTrailing(String str) {
        if (str == null) {
            $$$reportNull$$$0(126);
        }
        String string = trimTrailing((CharSequence) str).toString();
        if (string == null) {
            $$$reportNull$$$0(127);
        }
        return string;
    }

    public static CharSequence trimTrailing(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(128);
        }
        int length = charSequence.length() - 1;
        while (length >= 0 && Character.isWhitespace(charSequence.charAt(length))) {
            length--;
        }
        CharSequence charSequenceSubSequence = charSequence.subSequence(0, length + 1);
        if (charSequenceSubSequence == null) {
            $$$reportNull$$$0(129);
        }
        return charSequenceSubSequence;
    }

    public static String trimTrailing(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(130);
        }
        int length = str.length() - 1;
        while (length >= 0 && str.charAt(length) == c) {
            length--;
        }
        String strSubstring = str.substring(0, length + 1);
        if (strSubstring == null) {
            $$$reportNull$$$0(131);
        }
        return strSubstring;
    }

    public static StringBuilder trimTrailing(StringBuilder sb, char c) {
        if (sb == null) {
            $$$reportNull$$$0(132);
        }
        int length = sb.length() - 1;
        while (length >= 0 && sb.charAt(length) == c) {
            length--;
        }
        sb.setLength(length + 1);
        if (sb == null) {
            $$$reportNull$$$0(133);
        }
        return sb;
    }

    public static CharSequence trim(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        if (length == 0) {
            return charSequence;
        }
        int i = 0;
        while (i < length && Character.isWhitespace(charSequence.charAt(i))) {
            i++;
        }
        if (i == length) {
            return Strings.EMPTY_CHAR_SEQUENCE;
        }
        int i2 = length - 1;
        while (i2 >= i && Character.isWhitespace(charSequence.charAt(i2))) {
            i2--;
        }
        int i3 = i2 + 1;
        return (i > 0 || i3 < length) ? charSequence.subSequence(i, i3) : charSequence;
    }

    public static boolean startsWithChar(CharSequence charSequence, char c) {
        return (charSequence == null || charSequence.length() == 0 || charSequence.charAt(0) != c) ? false : true;
    }

    public static boolean endsWithChar(CharSequence charSequence, char c) {
        return Strings.endsWithChar(charSequence, c);
    }

    public static String trimStart(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(134);
        }
        if (str2 == null) {
            $$$reportNull$$$0(135);
        }
        String strTrimStart = Strings.trimStart(str, str2);
        if (strTrimStart == null) {
            $$$reportNull$$$0(136);
        }
        return strTrimStart;
    }

    public static String trimExtensions(String str) {
        if (str == null) {
            $$$reportNull$$$0(137);
        }
        int iIndexOf = str.indexOf(46);
        if (iIndexOf >= 0) {
            str = str.substring(0, iIndexOf);
        }
        if (str == null) {
            $$$reportNull$$$0(138);
        }
        return str;
    }

    public static String pluralize(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(139);
        }
        if (i != 1) {
            return pluralize(str);
        }
        if (str == null) {
            $$$reportNull$$$0(140);
        }
        return str;
    }

    public static void repeatSymbol(Appendable appendable, char c, int i) {
        if (appendable == null) {
            $$$reportNull$$$0(141);
        }
        for (int i2 = 0; i2 < i; i2++) {
            try {
                appendable.append(c);
            } catch (IOException e) {
                Logger.getInstance((Class<?>) StringUtil.class).error((Throwable) e);
                return;
            }
        }
    }

    public static String defaultIfEmpty(String str, String str2) {
        return isEmpty(str) ? str2 : str;
    }

    public static boolean isNotEmpty(String str) {
        return Strings.isNotEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return Strings.isEmpty(str);
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return Strings.isEmpty(charSequence);
    }

    public static int length(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        return charSequence.length();
    }

    public static String notNullize(String str) {
        String strNotNullize = Strings.notNullize(str);
        if (strNotNullize == null) {
            $$$reportNull$$$0(142);
        }
        return strNotNullize;
    }

    public static String notNullize(String str, String str2) {
        if (str2 == null) {
            $$$reportNull$$$0(143);
        }
        String strNotNullize = Strings.notNullize(str, str2);
        if (strNotNullize == null) {
            $$$reportNull$$$0(144);
        }
        return strNotNullize;
    }

    public static String nullize(String str) {
        return Strings.nullize(str, false);
    }

    public static String nullize(String str, String str2) {
        return Strings.nullize(str, str2);
    }

    public static String nullize(String str, boolean z) {
        return Strings.nullize(str, z);
    }

    public static boolean isEmptyOrSpaces(String str) {
        return isEmptyOrSpaces((CharSequence) str);
    }

    public static boolean isEmptyOrSpaces(CharSequence charSequence) {
        return Strings.isEmptyOrSpaces(charSequence);
    }

    public static String getThrowableText(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(145);
        }
        String throwableText = ExceptionUtil.getThrowableText(th);
        if (throwableText == null) {
            $$$reportNull$$$0(146);
        }
        return throwableText;
    }

    public static String getMessage(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(147);
        }
        return ExceptionUtil.getMessage(th);
    }

    public static String repeatSymbol(char c, int i) {
        char[] cArr = new char[i];
        Arrays.fill(cArr, c);
        return new String(cArr);
    }

    public static String repeat(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(148);
        }
        if (i == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str.length() * i);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(149);
        }
        return string;
    }

    public static List<String> splitHonorQuotes(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(150);
        }
        List<String> listSplitHonorQuotes = StringUtilRt.splitHonorQuotes(str, c);
        if (listSplitHonorQuotes == null) {
            $$$reportNull$$$0(151);
        }
        return listSplitHonorQuotes;
    }

    public static List<String> split(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(152);
        }
        if (str2 == null) {
            $$$reportNull$$$0(153);
        }
        return split(str, str2, true);
    }

    public static List<CharSequence> split(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(154);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(155);
        }
        return split(charSequence, charSequence2, true, true);
    }

    public static List<String> split(String str, String str2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(156);
        }
        if (str2 == null) {
            $$$reportNull$$$0(157);
        }
        return split(str, str2, z, true);
    }

    public static List<String> split(String str, String str2, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(158);
        }
        if (str2 == null) {
            $$$reportNull$$$0(159);
        }
        return split((CharSequence) str, (CharSequence) str2, z, z2);
    }

    public static List<CharSequence> split(CharSequence charSequence, CharSequence charSequence2, boolean z, boolean z2) {
        if (charSequence == null) {
            $$$reportNull$$$0(160);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(161);
        }
        if (charSequence2.length() == 0) {
            List<CharSequence> listSingletonList = Collections.singletonList(charSequence);
            if (listSingletonList == null) {
                $$$reportNull$$$0(162);
            }
            return listSingletonList;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int iIndexOf = indexOf(charSequence, charSequence2, i);
            if (iIndexOf == -1) {
                break;
            }
            int length = charSequence2.length() + iIndexOf;
            if (!z) {
                iIndexOf = length;
            }
            CharSequence charSequenceSubSequence = charSequence.subSequence(i, iIndexOf);
            if (charSequenceSubSequence.length() != 0 || !z2) {
                arrayList.add(charSequenceSubSequence);
            }
            i = length;
        }
        if (i < charSequence.length() || (!z2 && i == charSequence.length())) {
            arrayList.add(charSequence.subSequence(i, charSequence.length()));
        }
        return arrayList;
    }

    public static Iterable<String> tokenize(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(164);
        }
        if (str2 == null) {
            $$$reportNull$$$0(165);
        }
        return tokenize(new StringTokenizer(str, str2));
    }

    static /* synthetic */ Iterator lambda$tokenize$4(final StringTokenizer stringTokenizer) {
        return new Iterator<String>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return stringTokenizer.hasMoreTokens();
            }

            @Override // java.util.Iterator
            public String next() {
                return stringTokenizer.nextToken();
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static Iterable<String> tokenize(final StringTokenizer stringTokenizer) {
        if (stringTokenizer == null) {
            $$$reportNull$$$0(166);
        }
        return new Iterable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil$$ExternalSyntheticLambda6
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return StringUtil.lambda$tokenize$4(stringTokenizer);
            }
        };
    }

    public static List<String> getWordsIn(String str) {
        if (str == null) {
            $$$reportNull$$$0(168);
        }
        SmartList smartList = null;
        int i = -1;
        for (int i2 = 0; i2 < str.length(); i2++) {
            boolean zIsJavaIdentifierPart = Character.isJavaIdentifierPart(str.charAt(i2));
            if (zIsJavaIdentifierPart && i == -1) {
                i = i2;
            }
            if (zIsJavaIdentifierPart && i2 == str.length() - 1) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(str.substring(i, i2 + 1));
            } else if (!zIsJavaIdentifierPart && i != -1) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(str.substring(i, i2));
                i = -1;
            }
        }
        if (smartList != null) {
            if (smartList == null) {
                $$$reportNull$$$0(170);
            }
            return smartList;
        }
        List<String> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(169);
        }
        return listEmptyList;
    }

    public static List<TextRange> getWordIndicesIn(String str) {
        if (str == null) {
            $$$reportNull$$$0(171);
        }
        return getWordIndicesIn(str, null);
    }

    public static List<TextRange> getWordIndicesIn(String str, Set<Character> set) {
        boolean zIsJavaIdentifierPart;
        if (str == null) {
            $$$reportNull$$$0(172);
        }
        SmartList smartList = new SmartList();
        int i = -1;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (set == null) {
                zIsJavaIdentifierPart = Character.isJavaIdentifierPart(cCharAt);
            } else {
                zIsJavaIdentifierPart = !set.contains(Character.valueOf(cCharAt));
            }
            if (zIsJavaIdentifierPart && i == -1) {
                i = i2;
            }
            if (zIsJavaIdentifierPart && i2 == str.length() - 1) {
                smartList.add(new TextRange(i, i2 + 1));
            } else if (!zIsJavaIdentifierPart && i != -1) {
                smartList.add(new TextRange(i, i2));
                i = -1;
            }
        }
        return smartList;
    }

    public static String join(String[] strArr, String str) {
        if (str == null) {
            $$$reportNull$$$0(174);
        }
        if (strArr == null) {
            $$$reportNull$$$0(175);
        }
        return join(strArr, 0, strArr.length, str);
    }

    public static String join(String[] strArr, int i, int i2, String str) {
        if (str == null) {
            $$$reportNull$$$0(176);
        }
        if (strArr == null) {
            $$$reportNull$$$0(177);
        }
        StringBuilder sb = new StringBuilder();
        for (int i3 = i; i3 < i2; i3++) {
            if (i3 > i) {
                sb.append(str);
            }
            sb.append(strArr[i3]);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(178);
        }
        return string;
    }

    public static String[] zip(String[] strArr, String[] strArr2, String str) {
        if (strArr == null) {
            $$$reportNull$$$0(179);
        }
        if (strArr2 == null) {
            $$$reportNull$$$0(180);
        }
        if (strArr.length != strArr2.length) {
            throw new IllegalArgumentException();
        }
        String[] strArrNewStringArray = ArrayUtil.newStringArray(strArr.length);
        for (int i = 0; i < strArrNewStringArray.length; i++) {
            strArrNewStringArray[i] = strArr[i] + str + strArr2[i];
        }
        if (strArrNewStringArray == null) {
            $$$reportNull$$$0(181);
        }
        return strArrNewStringArray;
    }

    public static String[] surround(String[] strArr, String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(182);
        }
        if (str2 == null) {
            $$$reportNull$$$0(183);
        }
        if (strArr == null) {
            $$$reportNull$$$0(184);
        }
        String[] strArrNewStringArray = ArrayUtil.newStringArray(strArr.length);
        for (int i = 0; i < strArrNewStringArray.length; i++) {
            strArrNewStringArray[i] = str + strArr[i] + str2;
        }
        if (strArrNewStringArray == null) {
            $$$reportNull$$$0(185);
        }
        return strArrNewStringArray;
    }

    public static <T> String join(T[] tArr, org.jetbrains.kotlin.com.intellij.util.Function<? super T, String> function, String str) {
        if (function == null) {
            $$$reportNull$$$0(186);
        }
        if (str == null) {
            $$$reportNull$$$0(187);
        }
        if (tArr == null) {
            $$$reportNull$$$0(188);
        }
        String strJoin = Strings.join(tArr, function, str);
        if (strJoin == null) {
            $$$reportNull$$$0(189);
        }
        return strJoin;
    }

    public static <T> String join(Collection<? extends T> collection, org.jetbrains.kotlin.com.intellij.util.Function<? super T, String> function, String str) {
        if (collection == null) {
            $$$reportNull$$$0(190);
        }
        if (function == null) {
            $$$reportNull$$$0(191);
        }
        if (str == null) {
            $$$reportNull$$$0(192);
        }
        String strJoin = Strings.join((Collection) collection, (org.jetbrains.kotlin.com.intellij.util.Function) function, str);
        if (strJoin == null) {
            $$$reportNull$$$0(193);
        }
        return strJoin;
    }

    public static String join(Iterable<?> iterable, String str) {
        if (iterable == null) {
            $$$reportNull$$$0(194);
        }
        if (str == null) {
            $$$reportNull$$$0(195);
        }
        String strJoin = Strings.join(iterable, str);
        if (strJoin == null) {
            $$$reportNull$$$0(196);
        }
        return strJoin;
    }

    public static <T> String join(Iterable<? extends T> iterable, org.jetbrains.kotlin.com.intellij.util.Function<? super T, ? extends CharSequence> function, String str) {
        if (iterable == null) {
            $$$reportNull$$$0(197);
        }
        if (function == null) {
            $$$reportNull$$$0(198);
        }
        if (str == null) {
            $$$reportNull$$$0(199);
        }
        String strJoin = Strings.join(iterable, function, str);
        if (strJoin == null) {
            $$$reportNull$$$0(200);
        }
        return strJoin;
    }

    public static <T> void join(Iterable<? extends T> iterable, org.jetbrains.kotlin.com.intellij.util.Function<? super T, ? extends CharSequence> function, String str, StringBuilder sb) {
        if (iterable == null) {
            $$$reportNull$$$0(201);
        }
        if (function == null) {
            $$$reportNull$$$0(202);
        }
        if (str == null) {
            $$$reportNull$$$0(203);
        }
        if (sb == null) {
            $$$reportNull$$$0(204);
        }
        Strings.join(iterable, function, str, sb);
    }

    public static String join(Collection<String> collection, String str) {
        if (collection == null) {
            $$$reportNull$$$0(205);
        }
        if (str == null) {
            $$$reportNull$$$0(206);
        }
        String strJoin = Strings.join(collection, str);
        if (strJoin == null) {
            $$$reportNull$$$0(207);
        }
        return strJoin;
    }

    public static void join(Collection<String> collection, String str, StringBuilder sb) {
        if (collection == null) {
            $$$reportNull$$$0(208);
        }
        if (str == null) {
            $$$reportNull$$$0(209);
        }
        if (sb == null) {
            $$$reportNull$$$0(210);
        }
        Strings.join(collection, str, sb);
    }

    public static String join(int[] iArr, String str) {
        if (str == null) {
            $$$reportNull$$$0(211);
        }
        if (iArr == null) {
            $$$reportNull$$$0(212);
        }
        String strJoin = Strings.join(iArr, str);
        if (strJoin == null) {
            $$$reportNull$$$0(213);
        }
        return strJoin;
    }

    public static String join(String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(214);
        }
        String strJoin = Strings.join(strArr);
        if (strJoin == null) {
            $$$reportNull$$$0(215);
        }
        return strJoin;
    }

    public static Collector<CharSequence, ?, String> joining() {
        Collector<CharSequence, ?, String> collectorJoining = Collectors.joining(", ");
        if (collectorJoining == null) {
            $$$reportNull$$$0(216);
        }
        return collectorJoining;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001e  */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    public static String stripQuotesAroundValue(String str) {
        int i;
        if (str == null) {
            $$$reportNull$$$0(217);
        }
        int length = str.length();
        if (length > 0) {
            ?? IsQuoteAt = isQuoteAt(str, 0);
            if (length > 1) {
                i = length - 1;
                if (!isQuoteAt(str, i)) {
                    i = length;
                }
            } else {
                i = length;
            }
            if (IsQuoteAt > 0 || i < length) {
                String strSubstring = str.substring(IsQuoteAt == true ? 1 : 0, i);
                if (strSubstring == null) {
                    $$$reportNull$$$0(218);
                }
                return strSubstring;
            }
        }
        if (str == null) {
            $$$reportNull$$$0(219);
        }
        return str;
    }

    public static String formatFileSize(long j) {
        String fileSize = Formats.formatFileSize(j);
        if (fileSize == null) {
            $$$reportNull$$$0(220);
        }
        return fileSize;
    }

    public static String formatFileSize(long j, String str) {
        if (str == null) {
            $$$reportNull$$$0(221);
        }
        String fileSize = Formats.formatFileSize(j, str);
        if (fileSize == null) {
            $$$reportNull$$$0(222);
        }
        return fileSize;
    }

    public static String formatDuration(long j) {
        String duration = Formats.formatDuration(j);
        if (duration == null) {
            $$$reportNull$$$0(223);
        }
        return duration;
    }

    public static String formatDuration(Duration duration) {
        if (duration == null) {
            $$$reportNull$$$0(224);
        }
        String duration2 = Formats.formatDuration(duration);
        if (duration2 == null) {
            $$$reportNull$$$0(225);
        }
        return duration2;
    }

    @Deprecated
    public static String formatDuration(long j, String str) {
        if (str == null) {
            $$$reportNull$$$0(226);
        }
        String duration = Formats.formatDuration(j, str);
        if (duration == null) {
            $$$reportNull$$$0(XKeySym.XK_atilde);
        }
        return duration;
    }

    public static String unpluralize(String str) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_adiaeresis);
        }
        return Strings.unpluralize(str);
    }

    public static boolean containsAlphaCharacters(String str) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_aring);
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAnyChar(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_ae);
        }
        if (str2 == null) {
            $$$reportNull$$$0(XKeySym.XK_ccedilla);
        }
        return Strings.containsAnyChar(str, str2);
    }

    public static boolean containsAnyChar(String str, String str2, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_egrave);
        }
        if (str2 == null) {
            $$$reportNull$$$0(XKeySym.XK_eacute);
        }
        return Strings.containsAnyChar(str, str2, i, i2);
    }

    public static boolean containsChar(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_ecircumflex);
        }
        return Strings.containsChar(str, c);
    }

    @Deprecated
    public static String firstLetterToUpperCase(String str) {
        char cCharAt;
        char upperCase;
        if (str == null || str.isEmpty() || (upperCase = toUpperCase((cCharAt = str.charAt(0)))) == cCharAt) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = upperCase;
        return new String(charArray);
    }

    public static String strip(String str, CharFilter charFilter) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_ediaeresis);
        }
        if (charFilter == null) {
            $$$reportNull$$$0(XKeySym.XK_igrave);
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (charFilter.accept(cCharAt)) {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(XKeySym.XK_iacute);
        }
        return string;
    }

    public static String trim(String str, CharFilter charFilter) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_icircumflex);
        }
        if (charFilter == null) {
            $$$reportNull$$$0(XKeySym.XK_idiaeresis);
        }
        int length = str.length();
        int i = 0;
        while (i < length && !charFilter.accept(str.charAt(i))) {
            i++;
        }
        while (i < length && !charFilter.accept(str.charAt(length - 1))) {
            length--;
        }
        String strSubstring = str.substring(i, length);
        if (strSubstring == null) {
            $$$reportNull$$$0(240);
        }
        return strSubstring;
    }

    public static List<String> findMatches(String str, Pattern pattern) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_ntilde);
        }
        if (pattern == null) {
            $$$reportNull$$$0(242);
        }
        return findMatches(str, pattern, 1);
    }

    public static List<String> findMatches(String str, Pattern pattern, int i) {
        if (str == null) {
            $$$reportNull$$$0(243);
        }
        if (pattern == null) {
            $$$reportNull$$$0(244);
        }
        SmartList smartList = new SmartList();
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String strGroup = matcher.group(i);
            if (strGroup != null) {
                smartList.add(strGroup);
            }
        }
        return smartList;
    }

    public static int findFirst(CharSequence charSequence, CharFilter charFilter) {
        if (charSequence == null) {
            $$$reportNull$$$0(246);
        }
        if (charFilter == null) {
            $$$reportNull$$$0(247);
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (charFilter.accept(charSequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String replaceSubstring(String str, TextRange textRange, String str2) {
        if (str == null) {
            $$$reportNull$$$0(248);
        }
        if (textRange == null) {
            $$$reportNull$$$0(249);
        }
        if (str2 == null) {
            $$$reportNull$$$0(250);
        }
        String strReplace = textRange.replace(str, str2);
        if (strReplace == null) {
            $$$reportNull$$$0(251);
        }
        return strReplace;
    }

    public static boolean startsWithWhitespace(String str) {
        if (str == null) {
            $$$reportNull$$$0(252);
        }
        return !str.isEmpty() && Character.isWhitespace(str.charAt(0));
    }

    public static boolean isChar(CharSequence charSequence, int i, char c) {
        return i >= 0 && i < charSequence.length() && charSequence.charAt(i) == c;
    }

    public static boolean startsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(253);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(254);
        }
        return StringUtilRt.startsWith(charSequence, charSequence2);
    }

    public static boolean startsWith(CharSequence charSequence, int i, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(255);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(256);
        }
        return Strings.startsWith(charSequence, i, charSequence2);
    }

    public static boolean endsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(257);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(258);
        }
        return Strings.endsWith(charSequence, charSequence2);
    }

    public static boolean endsWith(CharSequence charSequence, int i, int i2, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(259);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(260);
        }
        int length = charSequence2.length();
        if (i2 < length) {
            return false;
        }
        for (int i3 = i2 - 1; i3 >= i2 - length && i3 >= i; i3--) {
            if (charSequence.charAt(i3) != charSequence2.charAt((i3 + length) - i2)) {
                return false;
            }
        }
        return true;
    }

    public static String commonPrefix(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED);
        }
        if (str2 == null) {
            $$$reportNull$$$0(GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED);
        }
        String strSubstring = str.substring(0, commonPrefixLength(str, str2));
        if (strSubstring == null) {
            $$$reportNull$$$0(263);
        }
        return strSubstring;
    }

    public static int commonPrefixLength(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(265);
        }
        return commonPrefixLength(charSequence, charSequence2, false);
    }

    public static int commonPrefixLength(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(266);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(267);
        }
        int iMin = Math.min(charSequence.length(), charSequence2.length());
        int i = 0;
        while (i < iMin && Strings.charsMatch(charSequence.charAt(i), charSequence2.charAt(i), z)) {
            i++;
        }
        return i;
    }

    public static String commonSuffix(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(268);
        }
        if (str2 == null) {
            $$$reportNull$$$0(269);
        }
        String strSubstring = str.substring(str.length() - commonSuffixLength(str, str2));
        if (strSubstring == null) {
            $$$reportNull$$$0(270);
        }
        return strSubstring;
    }

    public static int commonSuffixLength(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(271);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(272);
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i = 0;
        if (length != 0 && length2 != 0) {
            while (i < length && i < length2 && charSequence.charAt((length - i) - 1) == charSequence2.charAt((length2 - i) - 1)) {
                i++;
            }
        }
        return i;
    }

    public static boolean contains(CharSequence charSequence, int i, int i2, char c) {
        if (charSequence == null) {
            $$$reportNull$$$0(273);
        }
        return Strings.contains(charSequence, i, i2, c);
    }

    public static boolean containsWhitespaces(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (Character.isWhitespace(charSequence.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(CharSequence charSequence, char c) {
        if (charSequence == null) {
            $$$reportNull$$$0(274);
        }
        return Strings.indexOf(charSequence, c);
    }

    public static int indexOf(CharSequence charSequence, char c, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(275);
        }
        return Strings.indexOf(charSequence, c, i);
    }

    public static int indexOf(CharSequence charSequence, char c, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(276);
        }
        return Strings.indexOf(charSequence, c, i, i2);
    }

    public static boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(277);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(278);
        }
        return Strings.contains(charSequence, charSequence2);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(279);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(280);
        }
        return Strings.indexOf(charSequence, charSequence2);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(281);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(282);
        }
        return Strings.indexOf(charSequence, charSequence2, i);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(283);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(284);
        }
        return Strings.indexOf(charSequence, charSequence2, i, i2);
    }

    public static int indexOf(CharSequence charSequence, char c, int i, int i2, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(285);
        }
        return Strings.indexOf(charSequence, c, i, i2, z);
    }

    public static int indexOf(char[] cArr, char c, int i, int i2, boolean z) {
        if (cArr == null) {
            $$$reportNull$$$0(286);
        }
        return Strings.indexOf(cArr, c, i, i2, z);
    }

    public static int indexOfSubstringEnd(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(287);
        }
        if (str2 == null) {
            $$$reportNull$$$0(288);
        }
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            return -1;
        }
        return iIndexOf + str2.length();
    }

    public static int indexOfAny(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(289);
        }
        if (str2 == null) {
            $$$reportNull$$$0(ParserBasicInformation.SCOPE_UBOUND);
        }
        return Strings.indexOfAny(str, str2);
    }

    public static int indexOfAny(CharSequence charSequence, String str) {
        if (charSequence == null) {
            $$$reportNull$$$0(ParserBasicInformation.SCOPE_SIZE);
        }
        if (str == null) {
            $$$reportNull$$$0(292);
        }
        return Strings.indexOfAny(charSequence, str);
    }

    public static int indexOfAny(String str, String str2, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(293);
        }
        if (str2 == null) {
            $$$reportNull$$$0(294);
        }
        return Strings.indexOfAny(str, str2, i, i2);
    }

    public static int indexOfAny(CharSequence charSequence, String str, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(295);
        }
        if (str == null) {
            $$$reportNull$$$0(296);
        }
        return Strings.indexOfAny(charSequence, str, i, i2);
    }

    public static int lastIndexOfAny(CharSequence charSequence, String str) {
        if (charSequence == null) {
            $$$reportNull$$$0(297);
        }
        if (str == null) {
            $$$reportNull$$$0(298);
        }
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (containsChar(str, charSequence.charAt(length))) {
                return length;
            }
        }
        return -1;
    }

    public static String substringBefore(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(299);
        }
        if (str2 == null) {
            $$$reportNull$$$0(300);
        }
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            return null;
        }
        return str.substring(0, iIndexOf);
    }

    public static String substringBeforeLast(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(301);
        }
        if (str2 == null) {
            $$$reportNull$$$0(302);
        }
        int iLastIndexOf = str.lastIndexOf(str2);
        if (iLastIndexOf == -1) {
            if (str == null) {
                $$$reportNull$$$0(303);
            }
            return str;
        }
        String strSubstring = str.substring(0, iLastIndexOf);
        if (strSubstring == null) {
            $$$reportNull$$$0(304);
        }
        return strSubstring;
    }

    public static String substringAfter(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(305);
        }
        if (str2 == null) {
            $$$reportNull$$$0(306);
        }
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            return null;
        }
        return str.substring(iIndexOf + str2.length());
    }

    public static String substringAfterLast(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(307);
        }
        if (str2 == null) {
            $$$reportNull$$$0(308);
        }
        int iLastIndexOf = str.lastIndexOf(str2);
        if (iLastIndexOf == -1) {
            return null;
        }
        return str.substring(iLastIndexOf + str2.length());
    }

    public static int lastIndexOf(CharSequence charSequence, char c, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(309);
        }
        return StringUtilRt.lastIndexOf(charSequence, c, i, i2);
    }

    public static String first(String str, int i, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(310);
        }
        if (str.length() > i) {
            str = str.substring(0, i) + (z ? "..." : "");
        }
        if (str == null) {
            $$$reportNull$$$0(311);
        }
        return str;
    }

    public static CharSequence first(CharSequence charSequence, int i, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(312);
        }
        if (charSequence.length() <= i) {
            if (charSequence == null) {
                $$$reportNull$$$0(313);
            }
            return charSequence;
        }
        if (z) {
            String str = ((Object) charSequence.subSequence(0, i)) + "...";
            if (str == null) {
                $$$reportNull$$$0(314);
            }
            return str;
        }
        CharSequence charSequenceSubSequence = charSequence.subSequence(0, i);
        if (charSequenceSubSequence == null) {
            $$$reportNull$$$0(315);
        }
        return charSequenceSubSequence;
    }

    public static CharSequence last(CharSequence charSequence, int i, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(316);
        }
        if (charSequence.length() <= i) {
            if (charSequence == null) {
                $$$reportNull$$$0(TypedValues.AttributesType.TYPE_EASING);
            }
            return charSequence;
        }
        if (z) {
            String str = "..." + ((Object) charSequence.subSequence(charSequence.length() - i, charSequence.length()));
            if (str == null) {
                $$$reportNull$$$0(TypedValues.AttributesType.TYPE_PIVOT_TARGET);
            }
            return str;
        }
        CharSequence charSequenceSubSequence = charSequence.subSequence(charSequence.length() - i, charSequence.length());
        if (charSequenceSubSequence == null) {
            $$$reportNull$$$0(319);
        }
        return charSequenceSubSequence;
    }

    public static String firstLast(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(320);
        }
        if (str.length() > i) {
            int i2 = i / 2;
            str = ((Object) str.subSequence(0, i2)) + ELLIPSIS + ((Object) str.subSequence((str.length() - i2) - 1, str.length()));
        }
        if (str == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.DASH_NETWORK);
        }
        return str;
    }

    public static String escapeChar(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.DASH_NO_INIT);
        }
        return escapeChars(str, c);
    }

    public static String escapeChars(String str, char... cArr) {
        if (str == null) {
            $$$reportNull$$$0(323);
        }
        if (cArr == null) {
            $$$reportNull$$$0(324);
        }
        StringBuilder sb = new StringBuilder(str);
        for (char c : cArr) {
            escapeChar(sb, c);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(325);
        }
        return string;
    }

    public static void escapeChar(StringBuilder sb, char c) {
        if (sb == null) {
            $$$reportNull$$$0(326);
        }
        int i = 0;
        while (true) {
            int iIndexOf = indexOf(sb, c, i);
            if (iIndexOf < 0) {
                return;
            }
            sb.insert(iIndexOf, "\\");
            i = iIndexOf + 2;
        }
    }

    public static String escapeQuotes(String str) {
        if (str == null) {
            $$$reportNull$$$0(327);
        }
        return escapeChar(str, '\"');
    }

    public static void escapeQuotes(StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(328);
        }
        escapeChar(sb, '\"');
    }

    public static String escapeSlashes(String str) {
        if (str == null) {
            $$$reportNull$$$0(329);
        }
        return escapeChar(str, '/');
    }

    public static String escapeBackSlashes(String str) {
        if (str == null) {
            $$$reportNull$$$0(330);
        }
        return escapeChar(str, '\\');
    }

    public static String unescapeBackSlashes(String str) {
        if (str == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.SMOOTH_NETWORK);
        }
        StringBuilder sb = new StringBuilder(str.length());
        unescapeChar(sb, str, '\\');
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA);
        }
        return string;
    }

    public static String unescapeChar(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(333);
        }
        StringBuilder sb = new StringBuilder(str.length());
        unescapeChar(sb, str, c);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(334);
        }
        return string;
    }

    private static void unescapeChar(StringBuilder sb, String str, char c) {
        if (sb == null) {
            $$$reportNull$$$0(335);
        }
        if (str == null) {
            $$$reportNull$$$0(336);
        }
        int length = str.length();
        int i = length - 1;
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\\' && i2 != i && (cCharAt = str.charAt((i2 = i2 + 1))) != c) {
                sb.append('\\');
            }
            sb.append(cCharAt);
            i2++;
        }
    }

    public static void quote(StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(337);
        }
        quote(sb, '\"');
    }

    public static void quote(StringBuilder sb, char c) {
        if (sb == null) {
            $$$reportNull$$$0(338);
        }
        sb.insert(0, c);
        sb.append(c);
    }

    public static String wrapWithDoubleQuote(String str) {
        if (str == null) {
            $$$reportNull$$$0(339);
        }
        String str2 = "\"" + str + "\"";
        if (str2 == null) {
            $$$reportNull$$$0(340);
        }
        return str2;
    }

    @Deprecated
    public static String unescapeXml(String str) {
        if (str == null) {
            return null;
        }
        return unescapeXmlEntities(str);
    }

    @Deprecated
    public static String escapeXml(String str) {
        if (str == null) {
            return null;
        }
        return escapeXmlEntities(str);
    }

    public static String unescapeXmlEntities(String str) {
        if (str == null) {
            $$$reportNull$$$0(341);
        }
        String strUnescapeXmlEntities = Strings.unescapeXmlEntities(str);
        if (strUnescapeXmlEntities == null) {
            $$$reportNull$$$0(342);
        }
        return strUnescapeXmlEntities;
    }

    public static String escapeXmlEntities(String str) {
        if (str == null) {
            $$$reportNull$$$0(343);
        }
        String strEscapeXmlEntities = Strings.escapeXmlEntities(str);
        if (strEscapeXmlEntities == null) {
            $$$reportNull$$$0(344);
        }
        return strEscapeXmlEntities;
    }

    public static String removeHtmlTags(String str) {
        if (str == null) {
            $$$reportNull$$$0(TarConstants.XSTAR_PREFIX_OFFSET);
        }
        return removeHtmlTags(str, false);
    }

    public static String removeHtmlTags(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(346);
        }
        if (isEmpty(str)) {
            return "";
        }
        MyHtml2Text myHtml2Text = z ? new MyHtml2Text(true) : new MyHtml2Text(false);
        try {
            myHtml2Text.parse(new StringReader(str));
        } catch (IOException e) {
            Logger.getInstance((Class<?>) StringUtil.class).error((Throwable) e);
        }
        String text = myHtml2Text.getText();
        if (text == null) {
            $$$reportNull$$$0(347);
        }
        return text;
    }

    public static String removeEllipsisSuffix(String str) {
        if (str == null) {
            $$$reportNull$$$0(348);
        }
        if (str.endsWith("...")) {
            String strSubstring = str.substring(0, str.length() - "...".length());
            if (strSubstring == null) {
                $$$reportNull$$$0(349);
            }
            return strSubstring;
        }
        if (!str.endsWith(ELLIPSIS)) {
            if (str == null) {
                $$$reportNull$$$0(351);
            }
            return str;
        }
        String strSubstring2 = str.substring(0, str.length() - ELLIPSIS.length());
        if (strSubstring2 == null) {
            $$$reportNull$$$0(BrickValues.DRONE_ROTATION_MAX);
        }
        return strSubstring2;
    }

    public static String escapeMnemonics(String str) {
        if (str == null) {
            $$$reportNull$$$0(352);
        }
        return replace(str, MN_CHARS, MN_QUOTED);
    }

    public static String htmlEmphasize(String str) {
        if (str == null) {
            $$$reportNull$$$0(353);
        }
        String string = HtmlChunk.tag(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY).addText(str).wrapWith(JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME).toString();
        if (string == null) {
            $$$reportNull$$$0(354);
        }
        return string;
    }

    public static String escapeToRegexp(String str) {
        if (str == null) {
            $$$reportNull$$$0(355);
        }
        String string = escapeToRegexp(str, new StringBuilder(str.length())).toString();
        if (string == null) {
            $$$reportNull$$$0(356);
        }
        return string;
    }

    public static StringBuilder escapeToRegexp(CharSequence charSequence, StringBuilder sb) {
        if (charSequence == null) {
            $$$reportNull$$$0(357);
        }
        if (sb == null) {
            $$$reportNull$$$0(358);
        }
        StringBuilder sbEscapeToRegexp = Strings.escapeToRegexp(charSequence, sb);
        if (sbEscapeToRegexp == null) {
            $$$reportNull$$$0(359);
        }
        return sbEscapeToRegexp;
    }

    public static boolean isEscapedBackslash(char[] cArr, int i, int i2) {
        if (cArr == null) {
            $$$reportNull$$$0(360);
        }
        if (cArr[i2] != '\\') {
            return true;
        }
        boolean z = false;
        while (i < i2) {
            z = cArr[i] == '\\' ? !z : false;
            i++;
        }
        return z;
    }

    public static boolean isEscapedBackslash(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(361);
        }
        if (charSequence.charAt(i2) != '\\') {
            return true;
        }
        boolean z = false;
        while (i < i2) {
            z = charSequence.charAt(i) == '\\' ? !z : false;
            i++;
        }
        return z;
    }

    public static String replace(String str, List<String> list, List<String> list2) {
        if (str == null) {
            $$$reportNull$$$0(362);
        }
        if (list == null) {
            $$$reportNull$$$0(363);
        }
        if (list2 == null) {
            $$$reportNull$$$0(364);
        }
        String strReplace = Strings.replace(str, list, list2);
        if (strReplace == null) {
            $$$reportNull$$$0(365);
        }
        return strReplace;
    }

    public static String[] filterEmptyStrings(String[] strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(366);
        }
        int i = 0;
        for (String str : strArr) {
            if (str == null || str.isEmpty()) {
                i++;
            }
        }
        if (i == 0) {
            if (strArr == null) {
                $$$reportNull$$$0(367);
            }
            return strArr;
        }
        String[] strArrNewStringArray = ArrayUtil.newStringArray(strArr.length - i);
        int i2 = 0;
        for (String str2 : strArr) {
            if (str2 != null && !str2.isEmpty()) {
                strArrNewStringArray[i2] = str2;
                i2++;
            }
        }
        if (strArrNewStringArray == null) {
            $$$reportNull$$$0(368);
        }
        return strArrNewStringArray;
    }

    public static int countNewLines(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(369);
        }
        return countChars(charSequence, '\n');
    }

    public static int countChars(CharSequence charSequence, char c) {
        if (charSequence == null) {
            $$$reportNull$$$0(370);
        }
        return Strings.countChars(charSequence, c);
    }

    public static int countChars(CharSequence charSequence, char c, int i, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(371);
        }
        return Strings.countChars(charSequence, c, i, z);
    }

    public static int countChars(CharSequence charSequence, char c, int i, int i2, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(372);
        }
        return Strings.countChars(charSequence, c, i, i2, z);
    }

    public static String joinOrNull(String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(373);
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            if (str == null) {
                return null;
            }
            sb.append(str);
        }
        return sb.toString();
    }

    public static String getPropertyName(String str) {
        if (str == null) {
            $$$reportNull$$$0(374);
        }
        if (str.startsWith("get")) {
            return Introspector.decapitalize(str.substring(3));
        }
        if (str.startsWith(LanguageCodeUtil.IS)) {
            return Introspector.decapitalize(str.substring(2));
        }
        if (str.startsWith(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) {
            return Introspector.decapitalize(str.substring(3));
        }
        return null;
    }

    public static boolean isJavaIdentifierStart(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || Character.isJavaIdentifierStart(c);
    }

    public static boolean isJavaIdentifierPart(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || ((c >= 'A' && c <= 'Z') || Character.isJavaIdentifierPart(c));
    }

    public static boolean isJavaIdentifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(375);
        }
        int length = str.length();
        if (length == 0 || !isJavaIdentifierStart(str.charAt(0))) {
            return false;
        }
        for (int i = 1; i < length; i++) {
            if (!isJavaIdentifierPart(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String escapeProperty(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(376);
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\t') {
                sb.append("\\t");
            } else if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt == '\f') {
                sb.append("\\f");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else if (cCharAt == ' ') {
                if (z && i == 0) {
                    sb.append('\\');
                }
                sb.append(io.netty.util.internal.StringUtil.SPACE);
            } else if (cCharAt == '!' || cCharAt == '#' || cCharAt == ':' || cCharAt == '=' || cCharAt == '\\') {
                sb.append('\\');
                sb.append(cCharAt);
            } else if (20 < cCharAt && cCharAt < 127) {
                sb.append(cCharAt);
            } else {
                sb.append("\\u");
                sb.append(Character.forDigit((cCharAt >> '\f') & 15, 16));
                sb.append(Character.forDigit((cCharAt >> '\b') & 15, 16));
                sb.append(Character.forDigit((cCharAt >> 4) & 15, 16));
                sb.append(Character.forDigit(cCharAt & 15, 16));
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(377);
        }
        return string;
    }

    public static String getQualifiedName(String str, String str2) {
        if (str2 == null) {
            $$$reportNull$$$0(378);
        }
        if (str == null || str.isEmpty()) {
            if (str2 == null) {
                $$$reportNull$$$0(379);
            }
            return str2;
        }
        String str3 = str + '.' + str2;
        if (str3 == null) {
            $$$reportNull$$$0(CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE);
        }
        return str3;
    }

    public static int compareVersionNumbers(String str, String str2) {
        int iCompareTo;
        if (str == null && str2 == null) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        String[] strArrSplit = str.split("[._\\-]");
        String[] strArrSplit2 = str2.split("[._\\-]");
        int i = 0;
        while (i < strArrSplit.length && i < strArrSplit2.length) {
            String str3 = strArrSplit[i];
            String str4 = strArrSplit2[i];
            if (str3.matches("\\d+") && str4.matches("\\d+")) {
                iCompareTo = Integer.valueOf(str3).compareTo(Integer.valueOf(str4));
            } else {
                iCompareTo = strArrSplit[i].compareTo(strArrSplit2[i]);
            }
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            i++;
        }
        if (strArrSplit.length != strArrSplit2.length) {
            boolean z = strArrSplit.length > i;
            if (!z) {
                strArrSplit = strArrSplit2;
            }
            while (i < strArrSplit.length) {
                String str5 = strArrSplit[i];
                int iCompareTo2 = str5.matches("\\d+") ? Integer.valueOf(str5).compareTo((Integer) 0) : 1;
                if (iCompareTo2 != 0) {
                    return z ? iCompareTo2 : -iCompareTo2;
                }
                i++;
            }
        }
        return 0;
    }

    public static int getOccurrenceCount(String str, char c) {
        int iIndexOf;
        if (str == null) {
            $$$reportNull$$$0(381);
        }
        int i = 0;
        int i2 = 0;
        while (i < str.length() && (iIndexOf = str.indexOf(c, i)) >= 0) {
            i2++;
            i = iIndexOf + 1;
        }
        return i2;
    }

    public static int getOccurrenceCount(String str, String str2) {
        int iIndexOf;
        if (str == null) {
            $$$reportNull$$$0(382);
        }
        if (str2 == null) {
            $$$reportNull$$$0(383);
        }
        int i = 0;
        int i2 = 0;
        while (i < str.length() && (iIndexOf = str.indexOf(str2, i)) >= 0) {
            i2++;
            i = iIndexOf + 1;
        }
        return i2;
    }

    public static String fixVariableNameDerivedFromPropertyName(String str) {
        if (str == null) {
            $$$reportNull$$$0(AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP);
        }
        if (isEmptyOrSpaces(str)) {
            if (str == null) {
                $$$reportNull$$$0(385);
            }
            return str;
        }
        char cCharAt = str.charAt(0);
        if (isVowel(cCharAt)) {
            String str2 = "an" + Character.toUpperCase(cCharAt) + str.substring(1);
            if (str2 == null) {
                $$$reportNull$$$0(386);
            }
            return str2;
        }
        String str3 = "a" + Character.toUpperCase(cCharAt) + str.substring(1);
        if (str3 == null) {
            $$$reportNull$$$0(387);
        }
        return str3;
    }

    public static String sanitizeJavaIdentifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(388);
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (Character.isJavaIdentifierPart(cCharAt)) {
                if (sb.length() == 0 && !Character.isJavaIdentifierStart(cCharAt)) {
                    sb.append("_");
                }
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(389);
        }
        return string;
    }

    public static void assertValidSeparators(CharSequence charSequence) {
        int i;
        if (charSequence == null) {
            $$$reportNull$$$0(390);
        }
        char[] cArrFromSequenceWithoutCopying = CharArrayUtil.fromSequenceWithoutCopying(charSequence);
        if (cArrFromSequenceWithoutCopying != null) {
            int length = charSequence.length();
            i = 0;
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                } else if (cArrFromSequenceWithoutCopying[i] == '\r') {
                    break;
                } else {
                    i++;
                }
            }
        } else {
            int length2 = charSequence.length();
            i = 0;
            while (true) {
                if (i >= length2) {
                    i = -1;
                    break;
                } else if (charSequence.charAt(i) == '\r') {
                    break;
                } else {
                    i++;
                }
            }
        }
        if (i == -1) {
            return;
        }
        throw new AssertionError("Wrong line separators: '" + escapeStringCharacters(String.valueOf(last(charSequence.subSequence(0, i), 10, true)) + ((Object) first(charSequence.subSequence(i, charSequence.length()), 10, true))) + "' at offset " + i);
    }

    public static String tail(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(391);
        }
        String strSubstring = i >= str.length() ? "" : str.substring(i);
        if (strSubstring == null) {
            $$$reportNull$$$0(392);
        }
        return strSubstring;
    }

    public static String[] splitByLines(String str) {
        if (str == null) {
            $$$reportNull$$$0(393);
        }
        return splitByLines(str, true);
    }

    public static String[] splitByLines(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(394);
        }
        String[] strArrSplit = (z ? Splitters.EOL_SPLIT_PATTERN : Splitters.EOL_SPLIT_PATTERN_WITH_EMPTY).split(str);
        if (strArrSplit == null) {
            $$$reportNull$$$0(395);
        }
        return strArrSplit;
    }

    public static String[] splitByLinesDontTrim(String str) {
        if (str == null) {
            $$$reportNull$$$0(396);
        }
        String[] strArrSplit = Splitters.EOL_SPLIT_DONT_TRIM_PATTERN.split(str);
        if (strArrSplit == null) {
            $$$reportNull$$$0(397);
        }
        return strArrSplit;
    }

    public static String[] splitByLinesKeepSeparators(String str) {
        if (str == null) {
            $$$reportNull$$$0(398);
        }
        String[] strArrSplit = Splitters.EOL_SPLIT_KEEP_SEPARATORS.split(str);
        if (strArrSplit == null) {
            $$$reportNull$$$0(399);
        }
        return strArrSplit;
    }

    public static List<Pair<String, Integer>> getWordsWithOffset(String str) {
        if (str == null) {
            $$$reportNull$$$0(400);
        }
        ArrayList arrayList = new ArrayList();
        String str2 = str + " ";
        StringBuilder sb = new StringBuilder();
        int i = -1;
        for (int i2 = 0; i2 < str2.length(); i2++) {
            if (Character.isWhitespace(str2.charAt(i2))) {
                if (sb.length() > 0) {
                    arrayList.add(Pair.create(sb.toString(), Integer.valueOf(i)));
                    sb.setLength(0);
                    i = -1;
                }
            } else {
                if (i == -1) {
                    i = i2;
                }
                sb.append(str2.charAt(i2));
            }
        }
        return arrayList;
    }

    public static int naturalCompare(String str, String str2) {
        return NaturalComparator.INSTANCE.compare(str, str2);
    }

    public static boolean isNotNegativeNumber(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (!isDecimalDigit(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int compare(String str, String str2, boolean z) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return z ? str.compareToIgnoreCase(str2) : str.compareTo(str2);
    }

    public static int compare(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        if (charSequence == charSequence2) {
            return 0;
        }
        if (charSequence == null) {
            return -1;
        }
        if (charSequence2 == null) {
            return 1;
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        for (int i = 0; i < length && i < length2; i++) {
            int iCompare = Strings.compare(charSequence.charAt(i), charSequence2.charAt(i), z);
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return length - length2;
    }

    public static int comparePairs(String str, String str2, String str3, String str4, boolean z) {
        int iCompare = compare(str, str3, z);
        return iCompare != 0 ? iCompare : compare(str2, str4, z);
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtilRt.equal(charSequence, charSequence2, true);
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtilRt.equal(charSequence, charSequence2, false);
    }

    public static boolean equalsIgnoreWhitespaces(CharSequence charSequence, CharSequence charSequence2) {
        if ((charSequence == null) ^ (charSequence2 == null)) {
            return false;
        }
        if (charSequence == null) {
            return true;
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i = 0;
        int i2 = 0;
        while (i < length && i2 < length2) {
            if (charSequence.charAt(i) == charSequence2.charAt(i2)) {
                i++;
                i2++;
            } else {
                boolean z = false;
                while (i != length && isWhiteSpace(charSequence.charAt(i))) {
                    i++;
                    z = true;
                }
                while (i2 != length2 && isWhiteSpace(charSequence2.charAt(i2))) {
                    i2++;
                    z = true;
                }
                if (!z) {
                    return false;
                }
            }
        }
        while (i != length) {
            if (!isWhiteSpace(charSequence.charAt(i))) {
                return false;
            }
            i++;
        }
        while (i2 != length2) {
            if (!isWhiteSpace(charSequence2.charAt(i2))) {
                return false;
            }
            i2++;
        }
        return true;
    }

    public static boolean equalsTrimWhitespaces(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(402);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(403);
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && isWhiteSpace(charSequence.charAt(i2))) {
            i2++;
        }
        while (i2 < length && isWhiteSpace(charSequence.charAt(length - 1))) {
            length--;
        }
        while (i < length2 && isWhiteSpace(charSequence2.charAt(i))) {
            i++;
        }
        while (i < length2 && isWhiteSpace(charSequence2.charAt(length2 - 1))) {
            length2--;
        }
        return equals(new CharSequenceSubSequence(charSequence, i2, length), new CharSequenceSubSequence(charSequence2, i, length2));
    }

    public static String collapseWhiteSpace(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(404);
        }
        StringBuilder sb = new StringBuilder();
        int length = charSequence.length();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = charSequence.charAt(i);
            if (!isWhiteSpace(cCharAt)) {
                if (z && sb.length() > 0) {
                    sb.append(io.netty.util.internal.StringUtil.SPACE);
                }
                sb.append(cCharAt);
                z = false;
            } else if (!z) {
                z = true;
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(HttpStatus.SC_METHOD_NOT_ALLOWED);
        }
        return string;
    }

    public static boolean findIgnoreCase(String str, String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(HttpStatus.SC_NOT_ACCEPTABLE);
        }
        for (String str2 : strArr) {
            if (equalsIgnoreCase(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public static int compare(char c, char c2, boolean z) {
        int i = c - c2;
        if (i == 0 || !z) {
            return i;
        }
        char upperCase = StringUtilRt.toUpperCase(c);
        char upperCase2 = StringUtilRt.toUpperCase(c2);
        int i2 = upperCase - upperCase2;
        return i2 != 0 ? StringUtilRt.toLowerCase(upperCase) - StringUtilRt.toLowerCase(upperCase2) : i2;
    }

    public static String formatLinks(String str) {
        if (str == null) {
            $$$reportNull$$$0(HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED);
        }
        Pattern patternCompile = Pattern.compile("http://[a-zA-Z0-9./\\-+]+");
        StringBuffer stringBuffer = new StringBuffer();
        Matcher matcher = patternCompile.matcher(str);
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "<a href=\"" + matcher.group() + "\">" + matcher.group() + "</a>");
        }
        matcher.appendTail(stringBuffer);
        String string = stringBuffer.toString();
        if (string == null) {
            $$$reportNull$$$0(HttpStatus.SC_REQUEST_TIMEOUT);
        }
        return string;
    }

    public static String shortenTextWithEllipsis(String str, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(HttpStatus.SC_CONFLICT);
        }
        return shortenTextWithEllipsis(str, i, i2, false);
    }

    public static String trimMiddle(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(HttpStatus.SC_GONE);
        }
        return shortenTextWithEllipsis(str, i, i >> 1, true);
    }

    public static String shortenTextWithEllipsis(String str, int i, int i2, String str2) {
        if (str == null) {
            $$$reportNull$$$0(411);
        }
        if (str2 == null) {
            $$$reportNull$$$0(412);
        }
        int length = str.length();
        if (length <= i) {
            if (str == null) {
                $$$reportNull$$$0(HttpStatus.SC_REQUEST_URI_TOO_LONG);
            }
            return str;
        }
        String str3 = str.substring(0, (i - i2) - str2.length()) + str2 + str.substring(length - i2);
        if (str3 == null) {
            $$$reportNull$$$0(HttpStatus.SC_REQUEST_TOO_LONG);
        }
        return str3;
    }

    public static String shortenTextWithEllipsis(String str, int i, int i2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        return shortenTextWithEllipsis(str, i, i2, z ? ELLIPSIS : "...");
    }

    public static String shortenPathWithEllipsis(String str, int i, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(416);
        }
        return shortenTextWithEllipsis(str, i, (int) (((double) i) * 0.7d), z);
    }

    public static String shortenPathWithEllipsis(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(417);
        }
        return shortenPathWithEllipsis(str, i, false);
    }

    public static boolean charsEqualIgnoreCase(char c, char c2) {
        return Strings.charsEqualIgnoreCase(c, c2);
    }

    public static char toUpperCase(char c) {
        return Strings.toUpperCase(c);
    }

    public static String toUpperCase(String str) {
        return Strings.toUpperCase(str);
    }

    public static char toLowerCase(char c) {
        return Strings.toLowerCase(c);
    }

    public static boolean isUpperCase(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(XKeySym.XK_breve);
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (!Character.isUpperCase(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static LineSeparator detectSeparators(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(419);
        }
        int iIndexOfAny = indexOfAny(charSequence, "\n\r");
        if (iIndexOfAny == -1) {
            return null;
        }
        LineSeparator lineSeparatorAt = getLineSeparatorAt(charSequence, iIndexOfAny);
        if (lineSeparatorAt != null) {
            return lineSeparatorAt;
        }
        throw new AssertionError();
    }

    public static LineSeparator getLineSeparatorAt(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(420);
        }
        if (i < 0 || i >= charSequence.length()) {
            return null;
        }
        char cCharAt = charSequence.charAt(i);
        if (cCharAt == '\r') {
            int i2 = i + 1;
            return (i2 >= charSequence.length() || charSequence.charAt(i2) != '\n') ? LineSeparator.CR : LineSeparator.CRLF;
        }
        if (cCharAt == '\n') {
            return LineSeparator.LF;
        }
        return null;
    }

    public static String convertLineSeparators(String str) {
        if (str == null) {
            $$$reportNull$$$0(421);
        }
        String strConvertLineSeparators = StringUtilRt.convertLineSeparators(str);
        if (strConvertLineSeparators == null) {
            $$$reportNull$$$0(422);
        }
        return strConvertLineSeparators;
    }

    public static String convertLineSeparators(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(423);
        }
        String strConvertLineSeparators = StringUtilRt.convertLineSeparators(str, z);
        if (strConvertLineSeparators == null) {
            $$$reportNull$$$0(424);
        }
        return strConvertLineSeparators;
    }

    public static String convertLineSeparators(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(425);
        }
        if (str2 == null) {
            $$$reportNull$$$0(XKeySym.XK_Scedilla);
        }
        String strConvertLineSeparators = StringUtilRt.convertLineSeparators(str, str2);
        if (strConvertLineSeparators == null) {
            $$$reportNull$$$0(XKeySym.XK_Tcaron);
        }
        return strConvertLineSeparators;
    }

    public static String convertLineSeparators(String str, String str2, int[] iArr) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_Zacute);
        }
        if (str2 == null) {
            $$$reportNull$$$0(Constants.ERROR_TOO_MANY_REQUESTS);
        }
        String strConvertLineSeparators = StringUtilRt.convertLineSeparators(str, str2, iArr);
        if (strConvertLineSeparators == null) {
            $$$reportNull$$$0(XKeySym.XK_Zcaron);
        }
        return strConvertLineSeparators;
    }

    public static int parseInt(String str, int i) {
        return StringUtilRt.parseInt(str, i);
    }

    public static long parseLong(String str, long j) {
        return StringUtilRt.parseLong(str, j);
    }

    public static double parseDouble(String str, double d) {
        return StringUtilRt.parseDouble(str, d);
    }

    public static <E extends Enum<E>> E parseEnum(String str, E e, Class<E> cls) {
        if (str == null) {
            $$$reportNull$$$0(431);
        }
        if (cls == null) {
            $$$reportNull$$$0(432);
        }
        return (E) StringUtilRt.parseEnum(str, e, cls);
    }

    public static String getShortName(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(XKeySym.XK_aogonek);
        }
        String shortName = StringUtilRt.getShortName(cls);
        if (shortName == null) {
            $$$reportNull$$$0(XKeySym.XK_ogonek);
        }
        return shortName;
    }

    public static String getShortName(String str) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_lstroke);
        }
        String shortName = StringUtilRt.getShortName(str);
        if (shortName == null) {
            $$$reportNull$$$0(436);
        }
        return shortName;
    }

    public static String getShortName(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_lcaron);
        }
        String shortName = StringUtilRt.getShortName(str, c);
        if (shortName == null) {
            $$$reportNull$$$0(XKeySym.XK_sacute);
        }
        return shortName;
    }

    public static boolean isShortNameOf(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_caron);
        }
        if (str2 == null) {
            $$$reportNull$$$0(440);
        }
        if (str.length() < str2.length()) {
            return false;
        }
        if (str.length() == str2.length()) {
            return str.equals(str2);
        }
        int length = str.length() - str2.length();
        if (str.charAt(length - 1) != '.') {
            return false;
        }
        return str.regionMatches(length, str2, 0, str2.length());
    }

    static String toShortString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CharSequence) {
            return obj.toString();
        }
        String name2 = obj.getClass().getName();
        String string = obj.toString();
        return (string.startsWith(name2) && string.length() > name2.length() && !Character.isLetter(string.charAt(name2.length()))) ? trimStart(string, name2) : string;
    }

    public static CharSequence newBombedCharSequence(CharSequence charSequence, long j) {
        if (charSequence == null) {
            $$$reportNull$$$0(XKeySym.XK_scaron);
        }
        final long jCurrentTimeMillis = System.currentTimeMillis() + j;
        return new BombedCharSequence(charSequence) { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.BombedCharSequence
            protected void checkCanceled() {
                if (System.currentTimeMillis() >= jCurrentTimeMillis) {
                    throw new ProcessCanceledException();
                }
            }
        };
    }

    public static boolean trimEnd(StringBuilder sb, CharSequence charSequence) {
        if (sb == null) {
            $$$reportNull$$$0(XKeySym.XK_scedilla);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(443);
        }
        if (!endsWith(sb, charSequence)) {
            return false;
        }
        sb.delete(sb.length() - charSequence.length(), sb.length());
        return true;
    }

    public static boolean isBetween(String str, String str2, String str3) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_zacute);
        }
        if (str2 == null) {
            $$$reportNull$$$0(XKeySym.XK_doubleacute);
        }
        if (str3 == null) {
            $$$reportNull$$$0(XKeySym.XK_zcaron);
        }
        String lowerCase = toLowerCase(str);
        return lowerCase.startsWith(toLowerCase(str2)) && toLowerCase(str3).startsWith(lowerCase);
    }

    public static boolean hasUpperCaseChar(String str) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_zabovedot);
        }
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLowerCaseChar(String str) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_Racute);
        }
        for (char c : str.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static String replaceUnicodeEscapeSequences(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = UNICODE_CHAR.matcher(str);
        if (!matcher.find()) {
            return str;
        }
        matcher.reset();
        StringBuilder sb = new StringBuilder(str.length());
        int iEnd = 0;
        while (matcher.find()) {
            sb.append((CharSequence) str, iEnd, matcher.start());
            sb.append((char) Integer.parseInt(matcher.group().substring(2), 16));
            iEnd = matcher.end();
        }
        sb.append(str.substring(iEnd));
        return sb.toString();
    }

    public static abstract class BombedCharSequence implements CharSequence {
        private final CharSequence delegate;
        private int i;
        private boolean myDefused;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
            if (i == 1 || i == 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/StringUtil$BombedCharSequence";
            } else {
                objArr[0] = "sequence";
            }
            if (i == 1) {
                objArr[1] = "toString";
            } else if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/StringUtil$BombedCharSequence";
            } else {
                objArr[1] = "subSequence";
            }
            if (i != 1 && i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        protected abstract void checkCanceled();

        public BombedCharSequence(CharSequence charSequence) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            this.delegate = charSequence;
        }

        @Override // java.lang.CharSequence
        public int length() {
            check();
            return this.delegate.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            check();
            return this.delegate.charAt(i);
        }

        protected void check() {
            if (this.myDefused) {
                return;
            }
            int i = this.i + 1;
            this.i = i;
            if ((i & 1023) == 0) {
                checkCanceled();
            }
        }

        @Override // java.lang.CharSequence
        public String toString() {
            check();
            String string = this.delegate.toString();
            if (string == null) {
                $$$reportNull$$$0(1);
            }
            return string;
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            check();
            CharSequence charSequenceSubSequence = this.delegate.subSequence(i, i2);
            if (charSequenceSubSequence == null) {
                $$$reportNull$$$0(2);
            }
            return charSequenceSubSequence;
        }
    }

    public static String toHexString(byte[] bArr) {
        if (bArr == null) {
            $$$reportNull$$$0(449);
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append("0123456789abcdef".charAt((b >> 4) & 15)).append("0123456789abcdef".charAt(b & 15));
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE);
        }
        return string;
    }

    public static byte[] parseHexString(String str) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_Abreve);
        }
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Non-even-length: " + str);
        }
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static boolean isLatinAlphanumeric(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            char cCharAt = charSequence.charAt(i);
            if ((cCharAt < 'A' || cCharAt > 'Z') && ((cCharAt < 'a' || cCharAt > 'z') && !Character.isDigit(cCharAt))) {
                return false;
            }
        }
        return true;
    }

    public static String internEmptyString(String str) {
        if (str == null) {
            return null;
        }
        return str.isEmpty() ? "" : str;
    }

    public static int skipWhitespaceForward(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(XKeySym.XK_Lacute);
        }
        int length = charSequence.length();
        while (i < length && isWhitespaceOrTab(charSequence.charAt(i))) {
            i++;
        }
        return i;
    }

    public static int skipWhitespaceBackward(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(XKeySym.XK_Cacute);
        }
        while (i > 0 && isWhitespaceOrTab(charSequence.charAt(i - 1))) {
            i--;
        }
        return i;
    }

    @Deprecated
    public static String naturalJoin(List<String> list) {
        if (list.isEmpty()) {
            return "";
        }
        if (list.size() == 1) {
            String str = list.get(0);
            if (str == null) {
                $$$reportNull$$$0(455);
            }
            return str;
        }
        String str2 = join((Collection<String>) list.subList(0, list.size() - 1), ", ") + " and " + list.get(list.size() - 1);
        if (str2 == null) {
            $$$reportNull$$$0(XKeySym.XK_Ccaron);
        }
        return str2;
    }
}
