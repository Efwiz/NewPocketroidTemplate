package org.jetbrains.kotlin.com.intellij.util.containers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.mediarouter.media.GlobalMediaRouter;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.physics.bullet.collision.CollisionConstants;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.archive.PathTree;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Conditions;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public final class ContainerUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 11:
            case 12:
            case 14:
            case 17:
            case 20:
            case 21:
            case 23:
            case 25:
            case 27:
            case 28:
            case 30:
            case 31:
            case 34:
            case 37:
            case 38:
            case 40:
            case 43:
            case 47:
            case 52:
            case 54:
            case 55:
            case 56:
            case 58:
            case 62:
            case 66:
            case 67:
            case 70:
            case 78:
            case 80:
            case 90:
            case 93:
            case 96:
            case 102:
            case 103:
            case 128:
            case 133:
            case 135:
            case 143:
            case 147:
            case 151:
            case 156:
            case 165:
            case 168:
            case 172:
            case 175:
            case 178:
            case 181:
            case 182:
            case 185:
            case 190:
            case 192:
            case 198:
            case 199:
            case 201:
            case 204:
            case 212:
            case 215:
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_iacute /* 237 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case 242:
            case 243:
            case 244:
            case 247:
            case 252:
            case 257:
            case 260:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 272:
            case 275:
            case 279:
            case 283:
            case 292:
            case 294:
            case 299:
            case 302:
            case 305:
            case 308:
            case 312:
            case 313:
            case 316:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 324:
            case 328:
            case 329:
            case 336:
            case 337:
            case 340:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
            case 361:
            case 371:
            case 373:
            case 375:
            case 378:
            case 382:
            case 397:
            case 398:
            case 400:
            case 401:
            case 402:
            case 403:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case 411:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case 419:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zacute /* 428 */:
            case 431:
            case 432:
            case XKeySym.XK_lstroke /* 435 */:
            case 436:
            case XKeySym.XK_caron /* 439 */:
            case 440:
            case 443:
            case XKeySym.XK_zcaron /* 446 */:
            case 449:
            case XKeySym.XK_Abreve /* 451 */:
            case XKeySym.XK_Cacute /* 454 */:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
            case 457:
            case XKeySym.XK_Eogonek /* 458 */:
            case 467:
            case 468:
            case XKeySym.XK_Odoubleacute /* 469 */:
            case 470:
            case 471:
            case XKeySym.XK_Rcaron /* 472 */:
            case 474:
            case TarConstants.XSTAR_ATIME_OFFSET /* 476 */:
            case 477:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 11:
            case 12:
            case 14:
            case 17:
            case 20:
            case 21:
            case 23:
            case 25:
            case 27:
            case 28:
            case 30:
            case 31:
            case 34:
            case 37:
            case 38:
            case 40:
            case 43:
            case 47:
            case 52:
            case 54:
            case 55:
            case 56:
            case 58:
            case 62:
            case 66:
            case 67:
            case 70:
            case 78:
            case 80:
            case 90:
            case 93:
            case 96:
            case 102:
            case 103:
            case 128:
            case 133:
            case 135:
            case 143:
            case 147:
            case 151:
            case 156:
            case 165:
            case 168:
            case 172:
            case 175:
            case 178:
            case 181:
            case 182:
            case 185:
            case 190:
            case 192:
            case 198:
            case 199:
            case 201:
            case 204:
            case 212:
            case 215:
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_iacute /* 237 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case 242:
            case 243:
            case 244:
            case 247:
            case 252:
            case 257:
            case 260:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 272:
            case 275:
            case 279:
            case 283:
            case 292:
            case 294:
            case 299:
            case 302:
            case 305:
            case 308:
            case 312:
            case 313:
            case 316:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 324:
            case 328:
            case 329:
            case 336:
            case 337:
            case 340:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
            case 361:
            case 371:
            case 373:
            case 375:
            case 378:
            case 382:
            case 397:
            case 398:
            case 400:
            case 401:
            case 402:
            case 403:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case 411:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case 419:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zacute /* 428 */:
            case 431:
            case 432:
            case XKeySym.XK_lstroke /* 435 */:
            case 436:
            case XKeySym.XK_caron /* 439 */:
            case 440:
            case 443:
            case XKeySym.XK_zcaron /* 446 */:
            case 449:
            case XKeySym.XK_Abreve /* 451 */:
            case XKeySym.XK_Cacute /* 454 */:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
            case 457:
            case XKeySym.XK_Eogonek /* 458 */:
            case 467:
            case 468:
            case XKeySym.XK_Odoubleacute /* 469 */:
            case 470:
            case 471:
            case XKeySym.XK_Rcaron /* 472 */:
            case 474:
            case TarConstants.XSTAR_ATIME_OFFSET /* 476 */:
            case 477:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 11:
            case 12:
            case 14:
            case 17:
            case 20:
            case 21:
            case 23:
            case 25:
            case 27:
            case 28:
            case 30:
            case 31:
            case 34:
            case 37:
            case 38:
            case 40:
            case 43:
            case 47:
            case 52:
            case 54:
            case 55:
            case 56:
            case 58:
            case 62:
            case 66:
            case 67:
            case 70:
            case 78:
            case 80:
            case 90:
            case 93:
            case 96:
            case 102:
            case 103:
            case 128:
            case 133:
            case 135:
            case 143:
            case 147:
            case 151:
            case 156:
            case 165:
            case 168:
            case 172:
            case 175:
            case 178:
            case 181:
            case 182:
            case 185:
            case 190:
            case 192:
            case 198:
            case 199:
            case 201:
            case 204:
            case 212:
            case 215:
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_iacute /* 237 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case 242:
            case 243:
            case 244:
            case 247:
            case 252:
            case 257:
            case 260:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 272:
            case 275:
            case 279:
            case 283:
            case 292:
            case 294:
            case 299:
            case 302:
            case 305:
            case 308:
            case 312:
            case 313:
            case 316:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 324:
            case 328:
            case 329:
            case 336:
            case 337:
            case 340:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
            case 361:
            case 371:
            case 373:
            case 375:
            case 378:
            case 382:
            case 397:
            case 398:
            case 400:
            case 401:
            case 402:
            case 403:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case 411:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case 419:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zacute /* 428 */:
            case 431:
            case 432:
            case XKeySym.XK_lstroke /* 435 */:
            case 436:
            case XKeySym.XK_caron /* 439 */:
            case 440:
            case 443:
            case XKeySym.XK_zcaron /* 446 */:
            case 449:
            case XKeySym.XK_Abreve /* 451 */:
            case XKeySym.XK_Cacute /* 454 */:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
            case 457:
            case XKeySym.XK_Eogonek /* 458 */:
            case 467:
            case 468:
            case XKeySym.XK_Odoubleacute /* 469 */:
            case 470:
            case 471:
            case XKeySym.XK_Rcaron /* 472 */:
            case 474:
            case TarConstants.XSTAR_ATIME_OFFSET /* 476 */:
            case 477:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtil";
                break;
            case 2:
            case 8:
                objArr[0] = "first";
                break;
            case 3:
            case 9:
                objArr[0] = TypeBlock.NAME_entries;
                break;
            case 5:
            case 91:
                objArr[0] = UserMetadata.KEYDATA_FILENAME;
                break;
            case 6:
            case 94:
            case 98:
            case XKeySym.XK_ccedilla /* 231 */:
            case XKeySym.XK_eacute /* 233 */:
                objArr[0] = CompoundEntry.NAME_values;
                break;
            case 13:
            case 18:
            case 32:
            case 39:
            case 41:
            case 42:
            case 44:
            case 53:
            case 63:
            case 86:
            case 88:
            case 211:
            case 214:
            case 217:
            case 320:
            default:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 15:
            case 57:
            case 105:
            case 137:
            case 138:
            case 142:
            case 171:
            case 191:
            case 225:
            case XKeySym.XK_atilde /* 227 */:
            case 307:
            case 310:
            case 323:
            case 351:
            case XKeySym.XK_breve /* 418 */:
            case XKeySym.XK_Scedilla /* 426 */:
            case XKeySym.XK_scedilla /* 442 */:
                objArr[0] = "array";
                break;
            case 16:
            case 33:
            case 106:
            case 111:
            case 114:
            case 116:
            case 220:
            case 297:
            case 314:
            case 347:
            case 348:
            case 352:
            case 355:
            case 356:
            case 358:
            case 376:
            case 452:
                objArr[0] = "iterable";
                break;
            case 19:
                objArr[0] = "originalList";
                break;
            case 22:
            case 24:
            case 26:
            case 29:
                objArr[0] = "original";
                break;
            case 35:
            case 60:
            case 81:
            case 83:
            case 85:
            case 87:
            case 125:
            case 126:
            case 130:
            case 131:
            case 134:
            case 144:
            case 148:
            case 153:
            case 155:
            case 157:
            case 160:
            case 161:
            case 166:
            case 169:
            case 174:
            case 176:
            case 180:
            case 183:
            case 186:
            case 188:
            case 196:
            case 208:
            case 210:
            case 213:
            case 216:
            case 218:
            case 303:
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case 420:
            case Constants.ERROR_TOO_MANY_REQUESTS /* 429 */:
            case XKeySym.XK_aogonek /* 433 */:
            case XKeySym.XK_lcaron /* 437 */:
            case XKeySym.XK_scaron /* 441 */:
            case XKeySym.XK_zabovedot /* 447 */:
                objArr[0] = "collection";
                break;
            case 36:
            case 84:
            case 89:
            case 100:
            case 112:
            case 118:
            case 120:
            case 206:
            case 222:
            case 266:
            case 268:
            case 300:
                objArr[0] = "iterator";
                break;
            case 45:
            case 97:
            case 163:
            case 343:
            case 396:
            case 423:
            case XKeySym.XK_Ncaron /* 466 */:
                objArr[0] = "map";
                break;
            case 46:
            case 65:
            case 69:
                objArr[0] = "map2";
                break;
            case 48:
            case 50:
                objArr[0] = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
                break;
            case 49:
            case 51:
                objArr[0] = "set2";
                break;
            case 59:
            case 79:
            case 108:
            case 122:
            case 200:
            case 205:
            case XKeySym.XK_adiaeresis /* 228 */:
            case XKeySym.XK_ae /* 230 */:
            case XKeySym.XK_egrave /* 232 */:
            case 250:
            case 284:
            case 285:
            case 288:
            case ParserBasicInformation.SCOPE_UBOUND /* 290 */:
            case 293:
            case 362:
            case 369:
            case 383:
            case AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP /* 384 */:
            case 387:
            case 389:
            case 391:
            case 392:
            case 394:
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
            case 459:
            case XKeySym.XK_Udoubleacute /* 475 */:
            case 478:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 61:
                objArr[0] = "grouper";
                break;
            case 64:
            case 68:
                objArr[0] = "map1";
                break;
            case 71:
            case 75:
            case XKeySym.XK_ecircumflex /* 234 */:
            case 385:
                objArr[0] = "list1";
                break;
            case 72:
            case 76:
            case XKeySym.XK_ediaeresis /* 235 */:
            case 386:
                objArr[0] = "list2";
                break;
            case 73:
            case 77:
            case 286:
            case 289:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 295:
            case 363:
            case 365:
            case 367:
            case 370:
            case XKeySym.XK_Nacute /* 465 */:
                objArr[0] = "comparator";
                break;
            case 74:
            case 107:
            case 109:
            case 110:
            case 113:
                objArr[0] = "processor";
                break;
            case 82:
                objArr[0] = "appendix";
                break;
            case 92:
                objArr[0] = "valueConvertor";
                break;
            case 95:
            case 99:
            case 101:
                objArr[0] = "keyConvertor";
                break;
            case 104:
            case 115:
            case 121:
            case 123:
            case 152:
            case 154:
            case 162:
            case 167:
            case 187:
            case 194:
            case 197:
            case 219:
            case 346:
            case 349:
            case BrickValues.DRONE_ROTATION_MAX /* 350 */:
            case 353:
            case 354:
            case 357:
            case 359:
            case 388:
            case 390:
                objArr[0] = "condition";
                break;
            case 117:
            case 119:
                objArr[0] = "equalTo";
                break;
            case 124:
            case 127:
            case 129:
            case 132:
            case 136:
            case 139:
            case 141:
            case 146:
            case 149:
            case 267:
            case 417:
            case 421:
            case 425:
            case XKeySym.XK_Zcaron /* 430 */:
            case XKeySym.XK_ogonek /* 434 */:
            case XKeySym.XK_sacute /* 438 */:
                objArr[0] = "mapper";
                break;
            case 140:
            case 145:
            case 158:
            case 159:
            case 221:
            case 223:
            case 224:
                objArr[0] = "aClass";
                break;
            case 150:
            case 381:
                objArr[0] = "to";
                break;
            case 164:
                objArr[0] = "keyFilter";
                break;
            case 170:
            case 173:
            case 177:
            case 179:
            case 184:
            case 207:
                objArr[0] = "instanceOf";
                break;
            case 189:
                objArr[0] = "keyValues";
                break;
            case 193:
            case 209:
            case XKeySym.XK_Lacute /* 453 */:
                objArr[0] = "enumeration";
                break;
            case 195:
                objArr[0] = "arrays";
                break;
            case 202:
                objArr[0] = "iterable1";
                break;
            case 203:
                objArr[0] = "iterable2";
                break;
            case 226:
            case 379:
                objArr[0] = "fun";
                break;
            case XKeySym.XK_idiaeresis /* 239 */:
                objArr[0] = "it1";
                break;
            case 240:
                objArr[0] = "it2";
                break;
            case XKeySym.XK_ntilde /* 241 */:
                objArr[0] = "iterables";
                break;
            case 245:
            case 246:
                objArr[0] = "iterators";
                break;
            case 248:
            case 249:
                objArr[0] = "lists";
                break;
            case 251:
                objArr[0] = "listGenerator";
                break;
            case 253:
            case 255:
            case 258:
                objArr[0] = "collection1";
                break;
            case 254:
            case 256:
            case 259:
                objArr[0] = "collection2";
                break;
            case 263:
            case 265:
            case 330:
            case 360:
                objArr[0] = FirebaseAnalytics.Param.ITEMS;
                break;
            case 269:
                objArr[0] = "filter";
                break;
            case 270:
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
            case 271:
                objArr[0] = "what";
                break;
            case 273:
            case 399:
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
            case XKeySym.XK_zacute /* 444 */:
                objArr[0] = "c";
                break;
            case 274:
            case 278:
            case 282:
            case 342:
            case XKeySym.XK_Racute /* 448 */:
                objArr[0] = "factory";
                break;
            case 276:
            case 280:
                objArr[0] = "c1";
                break;
            case 277:
            case 281:
                objArr[0] = "c2";
                break;
            case 287:
            case 296:
                objArr[0] = "a";
                break;
            case 298:
            case 301:
            case 304:
            case 306:
            case 309:
            case 315:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
            case MediaError.DetailedErrorCode.DASH_NO_INIT /* 322 */:
            case 325:
            case 377:
                objArr[0] = "mapping";
                break;
            case 311:
            case 327:
                objArr[0] = "emptyArray";
                break;
            case 326:
                objArr[0] = "arr";
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NETWORK /* 331 */:
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
            case 333:
            case 334:
            case 338:
            case 341:
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE /* 415 */:
            case 416:
                objArr[0] = "result";
                break;
            case 335:
                objArr[0] = "parentDisposable";
                break;
            case 339:
                objArr[0] = "defaultValue";
                break;
            case 344:
                objArr[0] = "defValue";
                break;
            case 364:
            case 366:
                objArr[0] = "x";
                break;
            case 368:
            case 372:
            case 374:
                objArr[0] = "collections";
                break;
            case 393:
            case 395:
                objArr[0] = "clazz";
                break;
            case 404:
            case XKeySym.XK_Uring /* 473 */:
                objArr[0] = "strategy";
                break;
            case HttpStatus.SC_CONFLICT /* 409 */:
            case HttpStatus.SC_GONE /* 410 */:
            case 412:
                objArr[0] = "hashingStrategy";
                break;
            case XKeySym.XK_doubleacute /* 445 */:
                objArr[0] = "sample";
                break;
            case XKeySym.XK_Ecaron /* 460 */:
                objArr[0] = "prefix";
                break;
            case 461:
            case XKeySym.XK_Dcaron /* 463 */:
                objArr[0] = "o1";
                break;
            case 462:
            case 464:
                objArr[0] = "o2";
                break;
            case 479:
                objArr[0] = "accumulator";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "ar";
                break;
            case 4:
            case 7:
                objArr[1] = "newHashMap";
                break;
            case 10:
                objArr[1] = "newLinkedHashMap";
                break;
            case 11:
                objArr[1] = "canonicalStrategy";
                break;
            case 12:
                objArr[1] = "identityStrategy";
                break;
            case 14:
                objArr[1] = "newLinkedList";
                break;
            case 17:
                objArr[1] = "newArrayList";
                break;
            case 20:
            case 21:
                objArr[1] = "newUnmodifiableList";
                break;
            case 23:
                objArr[1] = "unmodifiableOrEmptyCollection";
                break;
            case 25:
                objArr[1] = "unmodifiableOrEmptyList";
                break;
            case 27:
            case 28:
                objArr[1] = "unmodifiableOrEmptySet";
                break;
            case 30:
            case 31:
                objArr[1] = "unmodifiableOrEmptyMap";
                break;
            case 34:
            case 37:
                objArr[1] = "newHashSet";
                break;
            case 38:
                objArr[1] = "newHashOrEmptySet";
                break;
            case 40:
                objArr[1] = "newLinkedHashSet";
                break;
            case 43:
                objArr[1] = "newConcurrentSet";
                break;
            case 47:
            case 52:
                objArr[1] = SchemaSymbols.ATTVAL_UNION;
                break;
            case 54:
            case 55:
            case 56:
                objArr[1] = "immutableSet";
                break;
            case 58:
                objArr[1] = "immutableSingletonList";
                break;
            case 62:
                objArr[1] = "groupBy";
                break;
            case 66:
            case 67:
            case 257:
            case 260:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
                objArr[1] = "intersection";
                break;
            case 70:
                objArr[1] = "diff";
                break;
            case 78:
                objArr[1] = "mergeSortedLists";
                break;
            case 80:
                objArr[1] = "subList";
                break;
            case 90:
                objArr[1] = "collect";
                break;
            case 93:
                objArr[1] = "newMapFromKeys";
                break;
            case 96:
                objArr[1] = "newMapFromValues";
                break;
            case 102:
                objArr[1] = "classify";
                break;
            case 103:
                objArr[1] = "emptyIterable";
                break;
            case 128:
            case 135:
                objArr[1] = "map2Map";
                break;
            case 133:
                objArr[1] = "map2MapNotNull";
                break;
            case 143:
            case 147:
            case 151:
                objArr[1] = "map2Array";
                break;
            case 156:
            case 168:
            case 172:
            case 185:
                objArr[1] = "findAll";
                break;
            case 165:
                objArr[1] = "filter";
                break;
            case 175:
            case 178:
            case 181:
            case 182:
                objArr[1] = "findAllAsArray";
                break;
            case 190:
                objArr[1] = "stringMap";
                break;
            case 192:
            case 198:
            case 199:
                objArr[1] = "iterate";
                break;
            case 201:
                objArr[1] = "iterateBackward";
                break;
            case 204:
                objArr[1] = ArchiveStreamFactory.ZIP;
                break;
            case 212:
                objArr[1] = "addAll";
                break;
            case 215:
                objArr[1] = "addAllNotNull";
                break;
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_iacute /* 237 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case 242:
            case 243:
            case 244:
            case 247:
            case 252:
                objArr[1] = "concat";
                break;
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
                objArr[1] = "getFirstItems";
                break;
            case 272:
                objArr[1] = "subtract";
                break;
            case 275:
            case 279:
            case 443:
            case XKeySym.XK_zcaron /* 446 */:
                objArr[1] = "toArray";
                break;
            case 283:
                objArr[1] = "mergeCollectionsToArray";
                break;
            case 292:
            case 294:
                objArr[1] = "sorted";
                break;
            case 299:
            case 302:
            case 305:
            case 324:
            case 328:
            case 329:
                objArr[1] = "map";
                break;
            case 308:
            case 312:
            case 313:
            case 316:
            case 319:
                objArr[1] = "mapNotNull";
                break;
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
                objArr[1] = "packNullables";
                break;
            case 336:
                objArr[1] = "createMaybeSingletonList";
                break;
            case 337:
                objArr[1] = "createMaybeSingletonSet";
                break;
            case 340:
                objArr[1] = "getOrCreate";
                break;
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
                objArr[1] = "getOrElse";
                break;
            case 361:
                objArr[1] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 371:
                objArr[1] = "removeDuplicatesFromSorted";
                break;
            case 373:
                objArr[1] = "flatten";
                break;
            case 375:
                objArr[1] = "flattenIterables";
                break;
            case 378:
                objArr[1] = "flatMap";
                break;
            case 382:
                objArr[1] = "convert";
                break;
            case 397:
                objArr[1] = "reverseMap";
                break;
            case 398:
                objArr[1] = "emptyList";
                break;
            case 400:
                objArr[1] = "createConcurrentWeakValueMap";
                break;
            case 401:
                objArr[1] = "createConcurrentSoftKeySoftValueMap";
                break;
            case 402:
                objArr[1] = "createConcurrentWeakKeySoftValueMap";
                break;
            case 403:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
                objArr[1] = "createConcurrentWeakKeyWeakValueMap";
                break;
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
                objArr[1] = "createConcurrentSoftValueMap";
                break;
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
                objArr[1] = "createConcurrentSoftMap";
                break;
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case 411:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
                objArr[1] = "createConcurrentWeakMap";
                break;
            case 419:
            case 422:
            case 424:
                objArr[1] = "map2List";
                break;
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zacute /* 428 */:
            case 431:
            case 432:
                objArr[1] = "map2Set";
                break;
            case XKeySym.XK_lstroke /* 435 */:
            case 436:
                objArr[1] = "map2LinkedSet";
                break;
            case XKeySym.XK_caron /* 439 */:
            case 440:
                objArr[1] = "map2SetNotNull";
                break;
            case 449:
                objArr[1] = "copyAndClear";
                break;
            case XKeySym.XK_Abreve /* 451 */:
                objArr[1] = "copyList";
                break;
            case XKeySym.XK_Cacute /* 454 */:
            case 455:
                objArr[1] = "toList";
                break;
            case XKeySym.XK_Ccaron /* 456 */:
            case 457:
            case XKeySym.XK_Eogonek /* 458 */:
                objArr[1] = "notNullize";
                break;
            case 467:
                objArr[1] = "toString";
                break;
            case 468:
                objArr[1] = "createWeakKeySoftValueMap";
                break;
            case XKeySym.XK_Odoubleacute /* 469 */:
                objArr[1] = "createWeakKeyWeakValueMap";
                break;
            case 470:
                objArr[1] = "createSoftKeySoftValueMap";
                break;
            case 471:
                objArr[1] = "createSoftMap";
                break;
            case XKeySym.XK_Rcaron /* 472 */:
            case 474:
                objArr[1] = "createWeakMap";
                break;
            case TarConstants.XSTAR_ATIME_OFFSET /* 476 */:
            case 477:
                objArr[1] = "immutableCopy";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtil";
                break;
        }
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 11:
            case 12:
            case 14:
            case 17:
            case 20:
            case 21:
            case 23:
            case 25:
            case 27:
            case 28:
            case 30:
            case 31:
            case 34:
            case 37:
            case 38:
            case 40:
            case 43:
            case 47:
            case 52:
            case 54:
            case 55:
            case 56:
            case 58:
            case 62:
            case 66:
            case 67:
            case 70:
            case 78:
            case 80:
            case 90:
            case 93:
            case 96:
            case 102:
            case 103:
            case 128:
            case 133:
            case 135:
            case 143:
            case 147:
            case 151:
            case 156:
            case 165:
            case 168:
            case 172:
            case 175:
            case 178:
            case 181:
            case 182:
            case 185:
            case 190:
            case 192:
            case 198:
            case 199:
            case 201:
            case 204:
            case 212:
            case 215:
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_iacute /* 237 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case 242:
            case 243:
            case 244:
            case 247:
            case 252:
            case 257:
            case 260:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 272:
            case 275:
            case 279:
            case 283:
            case 292:
            case 294:
            case 299:
            case 302:
            case 305:
            case 308:
            case 312:
            case 313:
            case 316:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 324:
            case 328:
            case 329:
            case 336:
            case 337:
            case 340:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
            case 361:
            case 371:
            case 373:
            case 375:
            case 378:
            case 382:
            case 397:
            case 398:
            case 400:
            case 401:
            case 402:
            case 403:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case 411:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case 419:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zacute /* 428 */:
            case 431:
            case 432:
            case XKeySym.XK_lstroke /* 435 */:
            case 436:
            case XKeySym.XK_caron /* 439 */:
            case 440:
            case 443:
            case XKeySym.XK_zcaron /* 446 */:
            case 449:
            case XKeySym.XK_Abreve /* 451 */:
            case XKeySym.XK_Cacute /* 454 */:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
            case 457:
            case XKeySym.XK_Eogonek /* 458 */:
            case 467:
            case 468:
            case XKeySym.XK_Odoubleacute /* 469 */:
            case 470:
            case 471:
            case XKeySym.XK_Rcaron /* 472 */:
            case 474:
            case TarConstants.XSTAR_ATIME_OFFSET /* 476 */:
            case 477:
                break;
            case 2:
            case 3:
            case 5:
            case 6:
                objArr[2] = "newHashMap";
                break;
            case 8:
            case 9:
                objArr[2] = "newLinkedHashMap";
                break;
            case 13:
                objArr[2] = "newLinkedList";
                break;
            case 15:
            case 16:
            case 18:
                objArr[2] = "newArrayList";
                break;
            case 19:
                objArr[2] = "newUnmodifiableList";
                break;
            case 22:
                objArr[2] = "unmodifiableOrEmptyCollection";
                break;
            case 24:
                objArr[2] = "unmodifiableOrEmptyList";
                break;
            case 26:
                objArr[2] = "unmodifiableOrEmptySet";
                break;
            case 29:
                objArr[2] = "unmodifiableOrEmptyMap";
                break;
            case 32:
            case 33:
            case 35:
            case 36:
                objArr[2] = "newHashSet";
                break;
            case 39:
            case 41:
                objArr[2] = "newLinkedHashSet";
                break;
            case 42:
                objArr[2] = "newTroveSet";
                break;
            case 44:
                objArr[2] = "reverse";
                break;
            case 45:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
                objArr[2] = SchemaSymbols.ATTVAL_UNION;
                break;
            case 53:
                objArr[2] = "immutableSet";
                break;
            case 57:
            case 59:
                objArr[2] = "immutableList";
                break;
            case 60:
            case 61:
                objArr[2] = "groupBy";
                break;
            case 63:
            case 343:
            case 344:
                objArr[2] = "getOrElse";
                break;
            case 64:
            case 65:
            case 255:
            case 256:
            case 258:
            case 259:
                objArr[2] = "intersection";
                break;
            case 68:
            case 69:
                objArr[2] = "diff";
                break;
            case 71:
            case 72:
            case 73:
            case 74:
                objArr[2] = "processSortedListsInOrder";
                break;
            case 75:
            case 76:
            case 77:
                objArr[2] = "mergeSortedLists";
                break;
            case 79:
                objArr[2] = "subList";
                break;
            case 81:
            case 82:
            case 83:
            case 84:
            case 208:
            case 209:
            case 210:
            case 211:
                objArr[2] = "addAll";
                break;
            case 85:
            case 86:
            case 87:
            case 88:
            case 213:
            case 214:
                objArr[2] = "addAllNotNull";
                break;
            case 89:
            case 206:
            case 207:
                objArr[2] = "collect";
                break;
            case 91:
            case 92:
                objArr[2] = "newMapFromKeys";
                break;
            case 94:
            case 95:
                objArr[2] = "newMapFromValues";
                break;
            case 97:
            case 98:
            case 99:
                objArr[2] = "fillMapWithValues";
                break;
            case 100:
            case 101:
                objArr[2] = "classify";
                break;
            case 104:
            case 105:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
                objArr[2] = "find";
                break;
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
                objArr[2] = "process";
                break;
            case 122:
            case 123:
                objArr[2] = "findLast";
                break;
            case 124:
            case 125:
            case 126:
            case 127:
            case 134:
                objArr[2] = "map2Map";
                break;
            case 129:
            case 130:
            case 131:
            case 132:
                objArr[2] = "map2MapNotNull";
                break;
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 144:
            case 145:
            case 146:
            case 148:
            case 149:
            case 150:
                objArr[2] = "map2Array";
                break;
            case 152:
            case 153:
            case 161:
            case 162:
            case 163:
            case 164:
                objArr[2] = "filter";
                break;
            case 154:
            case 155:
            case 166:
            case 167:
            case 170:
            case 171:
            case 183:
            case 184:
                objArr[2] = "findAll";
                break;
            case 157:
            case 158:
            case 159:
            case 160:
                objArr[2] = "filterIsInstance";
                break;
            case 169:
                objArr[2] = "skipNulls";
                break;
            case 173:
            case 174:
            case 176:
            case 177:
            case 179:
            case 180:
                objArr[2] = "findAllAsArray";
                break;
            case 186:
            case 187:
                objArr[2] = DevModeOverwritingStrategies.ALL;
                break;
            case 188:
                objArr[2] = "removeDuplicates";
                break;
            case 189:
                objArr[2] = "stringMap";
                break;
            case 191:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
                objArr[2] = "iterate";
                break;
            case 200:
                objArr[2] = "iterateBackward";
                break;
            case 202:
            case 203:
                objArr[2] = ArchiveStreamFactory.ZIP;
                break;
            case 205:
                objArr[2] = "swapElements";
                break;
            case 216:
            case 217:
                objArr[2] = "removeAll";
                break;
            case 218:
            case 219:
                objArr[2] = "retainAll";
                break;
            case 220:
            case 221:
            case 222:
            case 223:
            case 224:
            case 225:
                objArr[2] = "findInstance";
                break;
            case 226:
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_adiaeresis /* 228 */:
            case XKeySym.XK_ecircumflex /* 234 */:
            case XKeySym.XK_ediaeresis /* 235 */:
            case XKeySym.XK_idiaeresis /* 239 */:
            case 240:
            case XKeySym.XK_ntilde /* 241 */:
            case 248:
            case 249:
            case 250:
            case 251:
                objArr[2] = "concat";
                break;
            case XKeySym.XK_ae /* 230 */:
            case XKeySym.XK_ccedilla /* 231 */:
                objArr[2] = "append";
                break;
            case XKeySym.XK_egrave /* 232 */:
            case XKeySym.XK_eacute /* 233 */:
                objArr[2] = "prepend";
                break;
            case 245:
            case 246:
                objArr[2] = "concatIterators";
                break;
            case 253:
            case 254:
                objArr[2] = "intersects";
                break;
            case 263:
                objArr[2] = "getFirstItems";
                break;
            case 265:
                objArr[2] = "iterateAndGetLastItem";
                break;
            case 266:
            case 267:
                objArr[2] = "mapIterator";
                break;
            case 268:
            case 269:
                objArr[2] = "filterIterator";
                break;
            case 270:
            case 271:
                objArr[2] = "subtract";
                break;
            case 273:
            case 274:
            case 276:
            case 277:
            case 278:
            case XKeySym.XK_scaron /* 441 */:
            case XKeySym.XK_scedilla /* 442 */:
            case XKeySym.XK_zacute /* 444 */:
            case XKeySym.XK_doubleacute /* 445 */:
                objArr[2] = "toArray";
                break;
            case 280:
            case 281:
            case 282:
                objArr[2] = "mergeCollectionsToArray";
                break;
            case 284:
            case 285:
            case 286:
            case 287:
            case 295:
            case 296:
                objArr[2] = "sort";
                break;
            case 288:
            case 289:
            case ParserBasicInformation.SCOPE_UBOUND /* 290 */:
            case ParserBasicInformation.SCOPE_SIZE /* 291 */:
            case 293:
                objArr[2] = "sorted";
                break;
            case 297:
            case 298:
            case 300:
            case 301:
            case 303:
            case 304:
            case MediaError.DetailedErrorCode.DASH_NO_INIT /* 322 */:
            case 323:
            case 325:
            case 326:
            case 327:
                objArr[2] = "map";
                break;
            case 306:
            case 307:
            case 309:
            case 310:
            case 311:
            case 314:
            case 315:
            case TypedValues.AttributesType.TYPE_EASING /* 317 */:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET /* 318 */:
                objArr[2] = "mapNotNull";
                break;
            case 320:
                objArr[2] = "packNullables";
                break;
            case 330:
                objArr[2] = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
                break;
            case MediaError.DetailedErrorCode.SMOOTH_NETWORK /* 331 */:
            case MediaError.DetailedErrorCode.SMOOTH_NO_MEDIA_DATA /* 332 */:
            case 333:
                objArr[2] = "putIfNotNull";
                break;
            case 334:
            case 335:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 338:
            case 339:
            case 341:
            case 342:
                objArr[2] = "getOrCreate";
                break;
            case 346:
            case 347:
            case 348:
            case 349:
                objArr[2] = "and";
                break;
            case BrickValues.DRONE_ROTATION_MAX /* 350 */:
            case 351:
            case 352:
            case 353:
                objArr[2] = "exists";
                break;
            case 354:
            case 355:
            case 356:
            case 357:
                objArr[2] = "or";
                break;
            case 358:
            case 359:
                objArr[2] = "count";
                break;
            case 360:
                objArr[2] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 362:
            case 363:
            case 364:
            case 365:
                objArr[2] = "quickSort";
                break;
            case 366:
            case 367:
                objArr[2] = "med3";
                break;
            case 368:
            case 372:
                objArr[2] = "flatten";
                break;
            case 369:
            case 370:
                objArr[2] = "removeDuplicatesFromSorted";
                break;
            case 374:
                objArr[2] = "flattenIterables";
                break;
            case 376:
            case 377:
                objArr[2] = "flatMap";
                break;
            case 379:
            case CollisionConstants.GIM_DEFAULT_HASH_TABLE_SIZE /* 380 */:
            case 381:
                objArr[2] = "convert";
                break;
            case 383:
                objArr[2] = "containsIdentity";
                break;
            case AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP /* 384 */:
                objArr[2] = "indexOfIdentity";
                break;
            case 385:
            case 386:
                objArr[2] = "equalsIdentity";
                break;
            case 387:
            case 388:
                objArr[2] = "indexOf";
                break;
            case 389:
            case 390:
                objArr[2] = "lastIndexOf";
                break;
            case 391:
                objArr[2] = "lastIndexOfIdentity";
                break;
            case 392:
            case 393:
                objArr[2] = "findLastInstance";
                break;
            case 394:
            case 395:
                objArr[2] = "lastIndexOfInstance";
                break;
            case 396:
                objArr[2] = "reverseMap";
                break;
            case 399:
                objArr[2] = "createLockFreeCopyOnWriteList";
                break;
            case 404:
                objArr[2] = "createConcurrentWeakKeyWeakValueMap";
                break;
            case HttpStatus.SC_CONFLICT /* 409 */:
                objArr[2] = "createHashingStrategy";
                break;
            case HttpStatus.SC_GONE /* 410 */:
            case 412:
                objArr[2] = "createConcurrentWeakMap";
                break;
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /* 414 */:
                objArr[2] = "createConcurrentList";
                break;
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE /* 415 */:
            case 416:
                objArr[2] = "addIfNotNull";
                break;
            case 417:
            case XKeySym.XK_breve /* 418 */:
            case 420:
            case 421:
            case 423:
                objArr[2] = "map2List";
                break;
            case 425:
            case XKeySym.XK_Scedilla /* 426 */:
            case Constants.ERROR_TOO_MANY_REQUESTS /* 429 */:
            case XKeySym.XK_Zcaron /* 430 */:
                objArr[2] = "map2Set";
                break;
            case XKeySym.XK_aogonek /* 433 */:
            case XKeySym.XK_ogonek /* 434 */:
                objArr[2] = "map2LinkedSet";
                break;
            case XKeySym.XK_lcaron /* 437 */:
            case XKeySym.XK_sacute /* 438 */:
                objArr[2] = "map2SetNotNull";
                break;
            case XKeySym.XK_zabovedot /* 447 */:
            case XKeySym.XK_Racute /* 448 */:
                objArr[2] = "copyAndClear";
                break;
            case ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE /* 450 */:
                objArr[2] = "copyList";
                break;
            case 452:
                objArr[2] = "toCollection";
                break;
            case XKeySym.XK_Lacute /* 453 */:
                objArr[2] = "toList";
                break;
            case 459:
            case XKeySym.XK_Ecaron /* 460 */:
                objArr[2] = "startsWith";
                break;
            case 461:
            case 462:
            case XKeySym.XK_Dcaron /* 463 */:
            case 464:
            case XKeySym.XK_Nacute /* 465 */:
                objArr[2] = "compareLexicographically";
                break;
            case XKeySym.XK_Ncaron /* 466 */:
                objArr[2] = "toString";
                break;
            case XKeySym.XK_Uring /* 473 */:
                objArr[2] = "createWeakMap";
                break;
            case XKeySym.XK_Udoubleacute /* 475 */:
                objArr[2] = "immutableCopy";
                break;
            case 478:
            case 479:
                objArr[2] = "reduce";
                break;
            default:
                objArr[2] = "ar";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 11:
            case 12:
            case 14:
            case 17:
            case 20:
            case 21:
            case 23:
            case 25:
            case 27:
            case 28:
            case 30:
            case 31:
            case 34:
            case 37:
            case 38:
            case 40:
            case 43:
            case 47:
            case 52:
            case 54:
            case 55:
            case 56:
            case 58:
            case 62:
            case 66:
            case 67:
            case 70:
            case 78:
            case 80:
            case 90:
            case 93:
            case 96:
            case 102:
            case 103:
            case 128:
            case 133:
            case 135:
            case 143:
            case 147:
            case 151:
            case 156:
            case 165:
            case 168:
            case 172:
            case 175:
            case 178:
            case 181:
            case 182:
            case 185:
            case 190:
            case 192:
            case 198:
            case 199:
            case 201:
            case 204:
            case 212:
            case 215:
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_iacute /* 237 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case 242:
            case 243:
            case 244:
            case 247:
            case 252:
            case 257:
            case 260:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /* 261 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /* 262 */:
            case GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED /* 264 */:
            case 272:
            case 275:
            case 279:
            case 283:
            case 292:
            case 294:
            case 299:
            case 302:
            case 305:
            case 308:
            case 312:
            case 313:
            case 316:
            case 319:
            case MediaError.DetailedErrorCode.DASH_NETWORK /* 321 */:
            case 324:
            case 328:
            case 329:
            case 336:
            case 337:
            case 340:
            case TarConstants.XSTAR_PREFIX_OFFSET /* 345 */:
            case 361:
            case 371:
            case 373:
            case 375:
            case 378:
            case 382:
            case 397:
            case 398:
            case 400:
            case 401:
            case 402:
            case 403:
            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
            case HttpStatus.SC_NOT_ACCEPTABLE /* 406 */:
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /* 407 */:
            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
            case 411:
            case HttpStatus.SC_REQUEST_TOO_LONG /* 413 */:
            case 419:
            case 422:
            case 424:
            case XKeySym.XK_Tcaron /* 427 */:
            case XKeySym.XK_Zacute /* 428 */:
            case 431:
            case 432:
            case XKeySym.XK_lstroke /* 435 */:
            case 436:
            case XKeySym.XK_caron /* 439 */:
            case 440:
            case 443:
            case XKeySym.XK_zcaron /* 446 */:
            case 449:
            case XKeySym.XK_Abreve /* 451 */:
            case XKeySym.XK_Cacute /* 454 */:
            case 455:
            case XKeySym.XK_Ccaron /* 456 */:
            case 457:
            case XKeySym.XK_Eogonek /* 458 */:
            case 467:
            case 468:
            case XKeySym.XK_Odoubleacute /* 469 */:
            case 470:
            case 471:
            case XKeySym.XK_Rcaron /* 472 */:
            case 474:
            case TarConstants.XSTAR_ATIME_OFFSET /* 476 */:
            case 477:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... eArr) {
        if (eArr == null) {
            $$$reportNull$$$0(15);
        }
        return new ArrayList<>(Arrays.asList(eArr));
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(16);
        }
        ArrayList<E> arrayList = new ArrayList<>();
        Iterator<? extends E> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static <T> List<T> newArrayList(T[] tArr, int i, int i2) {
        if (tArr == null) {
            $$$reportNull$$$0(18);
        }
        if (i < 0 || i > i2 || i2 > tArr.length) {
            throw new IllegalArgumentException("start:" + i + " end:" + i2 + " length:" + tArr.length);
        }
        return new AbstractList<T>(i2, i, tArr) { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil.1
            private final int size;
            final /* synthetic */ Object[] val$elements;
            final /* synthetic */ int val$end;
            final /* synthetic */ int val$start;

            {
                this.val$end = i2;
                this.val$start = i;
                this.val$elements = tArr;
                this.size = i2 - i;
            }

            @Override // java.util.AbstractList, java.util.List
            public T get(int i3) {
                if (i3 < 0 || i3 >= this.size) {
                    throw new IndexOutOfBoundsException("index:" + i3 + " size:" + this.size);
                }
                return (T) this.val$elements[this.val$start + i3];
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return this.size;
            }
        };
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(32);
        }
        return new HashSet<>(Arrays.asList(tArr));
    }

    public static <T> LinkedHashSet<T> newLinkedHashSet(Iterable<? extends T> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(39);
        }
        LinkedHashSet<T> linkedHashSet = new LinkedHashSet<>();
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(it.next());
        }
        return linkedHashSet;
    }

    public static <T> Set<T> newConcurrentSet() {
        Set<T> setNewSetFromMap = Collections.newSetFromMap(new ConcurrentHashMap());
        if (setNewSetFromMap == null) {
            $$$reportNull$$$0(43);
        }
        return setNewSetFromMap;
    }

    public static <E> List<E> reverse(final List<? extends E> list) {
        if (list == null) {
            $$$reportNull$$$0(44);
        }
        if (list.isEmpty()) {
            return emptyList();
        }
        return new AbstractList<E>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil.2
            @Override // java.util.AbstractList, java.util.List
            public E get(int i) {
                List list2 = list;
                return (E) list2.get((list2.size() - 1) - i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return list.size();
            }
        };
    }

    public static <T> Set<T> union(Collection<? extends T> collection, Collection<? extends T> collection2) {
        if (collection == null) {
            $$$reportNull$$$0(50);
        }
        if (collection2 == null) {
            $$$reportNull$$$0(51);
        }
        HashSet hashSet = new HashSet(collection.size() + collection2.size());
        hashSet.addAll(collection);
        hashSet.addAll(collection2);
        return hashSet;
    }

    @SafeVarargs
    public static <E> Set<E> immutableSet(E... eArr) {
        if (eArr == null) {
            $$$reportNull$$$0(53);
        }
        int length = eArr.length;
        if (length == 0) {
            Set<E> setEmptySet = Collections.emptySet();
            if (setEmptySet == null) {
                $$$reportNull$$$0(54);
            }
            return setEmptySet;
        }
        if (length == 1) {
            Set<E> setSingleton = Collections.singleton(eArr[0]);
            if (setSingleton == null) {
                $$$reportNull$$$0(55);
            }
            return setSingleton;
        }
        Set<E> setUnmodifiableSet = Collections.unmodifiableSet(new HashSet(Arrays.asList(eArr)));
        if (setUnmodifiableSet == null) {
            $$$reportNull$$$0(56);
        }
        return setUnmodifiableSet;
    }

    @SafeVarargs
    public static <E> ImmutableList<E> immutableList(E... eArr) {
        if (eArr == null) {
            $$$reportNull$$$0(57);
        }
        return new ImmutableListBackedByArray(eArr);
    }

    public static <E> ImmutableList<E> immutableSingletonList(E e) {
        ImmutableList<E> immutableListSingleton = ImmutableList.singleton(e);
        if (immutableListSingleton == null) {
            $$$reportNull$$$0(58);
        }
        return immutableListSingleton;
    }

    private static final class ImmutableListBackedByArray<E> extends ImmutableList<E> {
        private final E[] myStore;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "a";
            } else if (i != 2) {
                objArr[0] = "array";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtil$ImmutableListBackedByArray";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtil$ImmutableListBackedByArray";
            } else {
                objArr[1] = "toArray";
            }
            if (i == 1) {
                objArr[2] = "toArray";
            } else if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        private ImmutableListBackedByArray(E[] eArr) {
            if (eArr == null) {
                $$$reportNull$$$0(0);
            }
            this.myStore = eArr;
        }

        @Override // java.util.List
        public E get(int i) {
            return this.myStore[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.myStore.length;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public <T> T[] toArray(T[] tArr) {
            if (tArr == null) {
                $$$reportNull$$$0(1);
            }
            int size = size();
            if (tArr.length < size) {
                tArr = (T[]) ArrayUtil.newArray(ArrayUtil.getComponentType(tArr), size);
            }
            System.arraycopy(this.myStore, 0, tArr, 0, size);
            if (tArr.length > size) {
                tArr[size] = null;
            }
            if (tArr == null) {
                $$$reportNull$$$0(2);
            }
            return tArr;
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super E> consumer) {
            int length = this.myStore.length;
            for (int i = 0; i < length; i++) {
                consumer.accept(this.myStore[i]);
            }
        }
    }

    public static <T> void addAllNotNull(Collection<? super T> collection, Iterable<? extends T> iterable) {
        if (collection == null) {
            $$$reportNull$$$0(85);
        }
        if (iterable == null) {
            $$$reportNull$$$0(86);
        }
        addAllNotNull(collection, iterable.iterator());
    }

    public static <T> void addAllNotNull(Collection<? super T> collection, Iterator<? extends T> it) {
        if (collection == null) {
            $$$reportNull$$$0(87);
        }
        if (it == null) {
            $$$reportNull$$$0(88);
        }
        while (it.hasNext()) {
            T next = it.next();
            if (next != null) {
                collection.add(next);
            }
        }
    }

    public static <T> T find(T[] tArr, Condition<? super T> condition) {
        if (condition == null) {
            $$$reportNull$$$0(104);
        }
        if (tArr == null) {
            $$$reportNull$$$0(105);
        }
        for (T t : tArr) {
            if (condition.value(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> boolean process(Iterable<? extends T> iterable, Processor<? super T> processor) {
        if (iterable == null) {
            $$$reportNull$$$0(106);
        }
        if (processor == null) {
            $$$reportNull$$$0(107);
        }
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            if (!processor.process(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean process(List<? extends T> list, Processor<? super T> processor) {
        if (list == null) {
            $$$reportNull$$$0(108);
        }
        if (processor == null) {
            $$$reportNull$$$0(109);
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!processor.process(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean process(T[] tArr, Processor<? super T> processor) {
        if (processor == null) {
            $$$reportNull$$$0(110);
        }
        if (tArr == null) {
            $$$reportNull$$$0(111);
        }
        for (T t : tArr) {
            if (!processor.process(t)) {
                return false;
            }
        }
        return true;
    }

    public static <T> T find(Iterable<? extends T> iterable, Condition<? super T> condition) {
        if (iterable == null) {
            $$$reportNull$$$0(114);
        }
        if (condition == null) {
            $$$reportNull$$$0(115);
        }
        return (T) find(iterable.iterator(), condition);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Object] */
    public static <T> T find(Iterator<? extends T> it, Condition<? super T> condition) {
        if (it == null) {
            $$$reportNull$$$0(120);
        }
        if (condition == null) {
            $$$reportNull$$$0(121);
        }
        while (it.hasNext()) {
            T next = it.next();
            if (condition.value(next)) {
                return next;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T, V> V[] map2Array(T[] tArr, Class<V> cls, Function<? super T, ? extends V> function) {
        if (cls == null) {
            $$$reportNull$$$0(140);
        }
        if (function == null) {
            $$$reportNull$$$0(141);
        }
        if (tArr == null) {
            $$$reportNull$$$0(142);
        }
        V[] vArr = (V[]) ArrayUtil.newArray(cls, tArr.length);
        for (int i = 0; i < tArr.length; i++) {
            vArr[i] = function.fun(tArr[i]);
        }
        if (vArr == null) {
            $$$reportNull$$$0(143);
        }
        return vArr;
    }

    public static <T, V> V[] map2Array(Collection<? extends T> collection, Class<V> cls, Function<? super T, ? extends V> function) {
        if (collection == null) {
            $$$reportNull$$$0(144);
        }
        if (cls == null) {
            $$$reportNull$$$0(145);
        }
        if (function == null) {
            $$$reportNull$$$0(146);
        }
        V[] vArr = (V[]) ArrayUtil.newArray(cls, collection.size());
        Iterator<? extends T> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            vArr[i] = function.fun(it.next());
            i++;
        }
        if (vArr == null) {
            $$$reportNull$$$0(147);
        }
        return vArr;
    }

    public static <T> List<T> filter(T[] tArr, Condition<? super T> condition) {
        if (condition == null) {
            $$$reportNull$$$0(152);
        }
        if (tArr == null) {
            $$$reportNull$$$0(153);
        }
        return findAll(tArr, condition);
    }

    public static <T> List<T> findAll(T[] tArr, Condition<? super T> condition) {
        if (condition == null) {
            $$$reportNull$$$0(154);
        }
        if (tArr == null) {
            $$$reportNull$$$0(155);
        }
        SmartList smartList = new SmartList();
        for (T t : tArr) {
            if (condition.value(t)) {
                smartList.add(t);
            }
        }
        return smartList;
    }

    public static <T> List<T> filterIsInstance(Collection<?> collection, Class<? extends T> cls) {
        if (collection == null) {
            $$$reportNull$$$0(157);
        }
        if (cls == null) {
            $$$reportNull$$$0(158);
        }
        return filter(collection, Conditions.instanceOf(cls));
    }

    public static <T> List<T> filter(Collection<? extends T> collection, Condition<? super T> condition) {
        if (collection == null) {
            $$$reportNull$$$0(161);
        }
        if (condition == null) {
            $$$reportNull$$$0(162);
        }
        return findAll(collection, condition);
    }

    public static <T> List<T> findAll(Collection<? extends T> collection, Condition<? super T> condition) {
        if (collection == null) {
            $$$reportNull$$$0(166);
        }
        if (condition == null) {
            $$$reportNull$$$0(167);
        }
        if (collection.isEmpty()) {
            return emptyList();
        }
        SmartList smartList = new SmartList();
        for (T t : collection) {
            if (condition.value(t)) {
                smartList.add(t);
            }
        }
        return smartList;
    }

    public static <T> List<T> skipNulls(Collection<? extends T> collection) {
        if (collection == null) {
            $$$reportNull$$$0(169);
        }
        return findAll(collection, Conditions.notNull());
    }

    public static <T> Iterable<T> iterate(final Collection<? extends T> collection, final Condition<? super T> condition) {
        if (collection == null) {
            $$$reportNull$$$0(196);
        }
        if (condition == null) {
            $$$reportNull$$$0(197);
        }
        if (!collection.isEmpty()) {
            return new Iterable() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil$$ExternalSyntheticLambda1
                @Override // java.lang.Iterable
                public final Iterator iterator() {
                    return ContainerUtil.lambda$iterate$5(collection, condition);
                }
            };
        }
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(198);
        }
        return listEmptyList;
    }

    static /* synthetic */ Iterator lambda$iterate$5(Collection collection, Condition condition) {
        return new Iterator<T>(collection, condition) { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil.4
            private final Iterator impl;
            private Object next = findNext();
            final /* synthetic */ Collection val$collection;
            final /* synthetic */ Condition val$condition;

            {
                this.val$collection = collection;
                this.val$condition = condition;
                this.impl = collection.iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.next != null;
            }

            @Override // java.util.Iterator
            public T next() {
                T t = (T) this.next;
                this.next = findNext();
                return t;
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            private T findNext() {
                while (this.impl.hasNext()) {
                    T t = (T) this.impl.next();
                    if (this.val$condition.value(t)) {
                        return t;
                    }
                }
                return null;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void swapElements(List<?> list, int i, int i2) {
        if (list == null) {
            $$$reportNull$$$0(205);
        }
        Object obj = list.get(i);
        list.set(i, list.get(i2));
        list.set(i2, obj);
    }

    @SafeVarargs
    public static <T, C extends Collection<? super T>> C addAll(C c, T... tArr) {
        if (c == null) {
            $$$reportNull$$$0(210);
        }
        if (tArr == null) {
            $$$reportNull$$$0(211);
        }
        for (T t : tArr) {
            c.add(t);
        }
        if (c == null) {
            $$$reportNull$$$0(212);
        }
        return c;
    }

    @SafeVarargs
    public static <T, C extends Collection<T>> C addAllNotNull(C c, T... tArr) {
        if (c == null) {
            $$$reportNull$$$0(213);
        }
        if (tArr == null) {
            $$$reportNull$$$0(214);
        }
        for (T t : tArr) {
            if (t != null) {
                c.add(t);
            }
        }
        if (c == null) {
            $$$reportNull$$$0(215);
        }
        return c;
    }

    public static <T, V> List<T> concat(V[] vArr, Function<? super V, ? extends Collection<? extends T>> function) {
        if (function == null) {
            $$$reportNull$$$0(226);
        }
        if (vArr == null) {
            $$$reportNull$$$0(XKeySym.XK_atilde);
        }
        return concat(Arrays.asList(vArr), function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> List<T> concat(final List<? extends T> list, final List<? extends T> list2) {
        if (list == 0) {
            $$$reportNull$$$0(XKeySym.XK_ecircumflex);
        }
        if (list2 == 0) {
            $$$reportNull$$$0(XKeySym.XK_ediaeresis);
        }
        if (list.isEmpty() && list2.isEmpty()) {
            List<T> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(XKeySym.XK_igrave);
            }
            return listEmptyList;
        }
        if (list.isEmpty()) {
            if (list2 == 0) {
                $$$reportNull$$$0(XKeySym.XK_iacute);
            }
            return list2;
        }
        if (list2.isEmpty()) {
            if (list == 0) {
                $$$reportNull$$$0(XKeySym.XK_icircumflex);
            }
            return list;
        }
        final int size = list.size();
        final int size2 = list2.size() + size;
        return new AbstractList<T>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil.7
            @Override // java.util.AbstractList, java.util.List
            public T get(int i) {
                int i2 = size;
                if (i < i2) {
                    return (T) list.get(i);
                }
                return (T) list2.get(i - i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return size2;
            }
        };
    }

    public static <T> Iterable<T> concat(final Iterable<? extends T> iterable, final Iterable<? extends T> iterable2) {
        if (iterable == null) {
            $$$reportNull$$$0(XKeySym.XK_idiaeresis);
        }
        if (iterable2 == null) {
            $$$reportNull$$$0(240);
        }
        return new Iterable<T>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil.8
            @Override // java.lang.Iterable
            public void forEach(Consumer<? super T> consumer) {
                iterable.forEach(consumer);
                iterable2.forEach(consumer);
            }

            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return new Iterator<T>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil.8.1
                    boolean firstFinished;
                    Iterator<? extends T> it;

                    {
                        this.it = iterable.iterator();
                        advance();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return !this.firstFinished || this.it.hasNext();
                    }

                    @Override // java.util.Iterator
                    public T next() {
                        T next = this.it.next();
                        advance();
                        return next;
                    }

                    private void advance() {
                        if (this.firstFinished || this.it.hasNext()) {
                            return;
                        }
                        this.it = iterable2.iterator();
                        this.firstFinished = true;
                    }
                };
            }
        };
    }

    @SafeVarargs
    public static <T> Iterable<T> concat(final Iterable<? extends T>... iterableArr) {
        if (iterableArr == null) {
            $$$reportNull$$$0(XKeySym.XK_ntilde);
        }
        if (iterableArr.length == 0) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(242);
            }
            return listEmptyList;
        }
        if (iterableArr.length == 1) {
            Iterable<T> iterable = (Iterable<T>) iterableArr[0];
            if (iterable == null) {
                $$$reportNull$$$0(243);
            }
            return iterable;
        }
        return new Iterable() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil$$ExternalSyntheticLambda0
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return ContainerUtil.lambda$concat$8(iterableArr);
            }
        };
    }

    static /* synthetic */ Iterator lambda$concat$8(Iterable[] iterableArr) {
        Iterator[] itArr = new Iterator[iterableArr.length];
        for (int i = 0; i < iterableArr.length; i++) {
            itArr[i] = iterableArr[i].iterator();
        }
        return concatIterators(itArr);
    }

    @SafeVarargs
    public static <T> Iterator<T> concatIterators(Iterator<? extends T>... itArr) {
        if (itArr == null) {
            $$$reportNull$$$0(245);
        }
        return new SequenceIterator(itArr);
    }

    public static <T, V> List<V> concat(Iterable<? extends T> iterable, Function<? super T, ? extends Collection<? extends V>> function) {
        if (iterable == null) {
            $$$reportNull$$$0(250);
        }
        if (function == null) {
            $$$reportNull$$$0(251);
        }
        List<V> arrayList = new ArrayList<>();
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.addAll(function.fun(it.next()));
        }
        if (arrayList.isEmpty()) {
            arrayList = emptyList();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(252);
        }
        return arrayList;
    }

    public static <T> boolean intersects(Collection<? extends T> collection, Collection<? extends T> collection2) {
        if (collection == null) {
            $$$reportNull$$$0(253);
        }
        if (collection2 == null) {
            $$$reportNull$$$0(254);
        }
        if (collection.size() <= collection2.size()) {
            Iterator<? extends T> it = collection.iterator();
            while (it.hasNext()) {
                if (collection2.contains(it.next())) {
                    return true;
                }
            }
            return false;
        }
        Iterator<? extends T> it2 = collection2.iterator();
        while (it2.hasNext()) {
            if (collection.contains(it2.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> Collection<T> intersection(Collection<? extends T> collection, Collection<? extends T> collection2) {
        if (collection == null) {
            $$$reportNull$$$0(255);
        }
        if (collection2 == null) {
            $$$reportNull$$$0(256);
        }
        if (collection.isEmpty() || collection2.isEmpty()) {
            return emptyList();
        }
        List arrayList = new ArrayList();
        for (T t : collection) {
            if (collection2.contains(t)) {
                arrayList.add(t);
            }
        }
        if (arrayList.isEmpty()) {
            arrayList = emptyList();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(257);
        }
        return arrayList;
    }

    public static <T> T getFirstItem(Collection<? extends T> collection) {
        return (T) getFirstItem(collection, null);
    }

    public static <T> T getFirstItem(List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static <T> T getFirstItem(Collection<? extends T> collection, T t) {
        return (collection == null || collection.isEmpty()) ? t : collection.iterator().next();
    }

    public static <T> T getOnlyItem(Collection<? extends T> collection) {
        return (T) getOnlyItem(collection, null);
    }

    public static <T> T getOnlyItem(Collection<? extends T> collection, T t) {
        return (collection == null || collection.size() != 1) ? t : collection.iterator().next();
    }

    public static <T> T[] toArray(Collection<T> collection, ArrayFactory<? extends T> arrayFactory) {
        if (collection == null) {
            $$$reportNull$$$0(273);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(274);
        }
        T[] tArr = (T[]) collection.toArray(arrayFactory.create(collection.size()));
        if (tArr == null) {
            $$$reportNull$$$0(275);
        }
        return tArr;
    }

    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        if (list == null) {
            $$$reportNull$$$0(285);
        }
        if (comparator == null) {
            $$$reportNull$$$0(286);
        }
        int size = list.size();
        if (size < 2) {
            return;
        }
        if (size == 2) {
            T t = list.get(0);
            T t2 = list.get(1);
            if (comparator.compare(t, t2) > 0) {
                list.set(0, t2);
                list.set(1, t);
                return;
            }
            return;
        }
        if (size >= 10) {
            list.sort(comparator);
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int i2 = 0; i2 < i; i2++) {
                T t3 = list.get(i);
                T t4 = list.get(i2);
                if (comparator.compare(t3, t4) < 0) {
                    list.set(i, t4);
                    list.set(i2, t3);
                }
            }
        }
    }

    public static <T extends Comparable<? super T>> List<T> sorted(Collection<? extends T> collection) {
        if (collection == null) {
            $$$reportNull$$$0(293);
        }
        ArrayList arrayList = new ArrayList(collection);
        arrayList.sort(null);
        return arrayList;
    }

    public static <T, V> List<V> map(Iterable<? extends T> iterable, Function<? super T, ? extends V> function) {
        if (iterable == null) {
            $$$reportNull$$$0(297);
        }
        if (function == null) {
            $$$reportNull$$$0(298);
        }
        List<V> arrayList = new ArrayList<>();
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(function.fun(it.next()));
        }
        if (arrayList.isEmpty()) {
            arrayList = emptyList();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(299);
        }
        return arrayList;
    }

    public static <T, V> List<V> map(Collection<? extends T> collection, Function<? super T, ? extends V> function) {
        if (collection == null) {
            $$$reportNull$$$0(303);
        }
        if (function == null) {
            $$$reportNull$$$0(304);
        }
        if (collection.isEmpty()) {
            return emptyList();
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(function.fun(it.next()));
        }
        return arrayList;
    }

    public static <T, V> List<V> mapNotNull(T[] tArr, Function<? super T, ? extends V> function) {
        if (function == null) {
            $$$reportNull$$$0(306);
        }
        if (tArr == null) {
            $$$reportNull$$$0(307);
        }
        if (tArr.length == 0) {
            return emptyList();
        }
        List<V> arrayList = new ArrayList<>(tArr.length);
        for (T t : tArr) {
            V vFun = function.fun(t);
            if (vFun != null) {
                arrayList.add(vFun);
            }
        }
        if (arrayList.isEmpty()) {
            arrayList = emptyList();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(308);
        }
        return arrayList;
    }

    public static <T, V> List<V> mapNotNull(Collection<? extends T> collection, Function<? super T, ? extends V> function) {
        if (collection == null) {
            $$$reportNull$$$0(TypedValues.AttributesType.TYPE_EASING);
        }
        if (function == null) {
            $$$reportNull$$$0(TypedValues.AttributesType.TYPE_PIVOT_TARGET);
        }
        if (collection.isEmpty()) {
            return emptyList();
        }
        List<V> arrayList = new ArrayList<>(collection.size());
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            V vFun = function.fun(it.next());
            if (vFun != null) {
                arrayList.add(vFun);
            }
        }
        if (arrayList.isEmpty()) {
            arrayList = emptyList();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(319);
        }
        return arrayList;
    }

    public static <T, V> List<V> map(T[] tArr, Function<? super T, ? extends V> function) {
        if (function == null) {
            $$$reportNull$$$0(MediaError.DetailedErrorCode.DASH_NO_INIT);
        }
        if (tArr == null) {
            $$$reportNull$$$0(323);
        }
        List<V> arrayList = new ArrayList<>(tArr.length);
        for (T t : tArr) {
            arrayList.add(function.fun(t));
        }
        if (arrayList.isEmpty()) {
            arrayList = emptyList();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(324);
        }
        return arrayList;
    }

    @SafeVarargs
    public static <T> Set<T> set(T... tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(330);
        }
        return new HashSet(Arrays.asList(tArr));
    }

    public static <T> List<T> createMaybeSingletonList(T t) {
        List<T> listEmptyList = t == null ? emptyList() : Collections.singletonList(t);
        if (listEmptyList == null) {
            $$$reportNull$$$0(336);
        }
        return listEmptyList;
    }

    public static <T> boolean and(T[] tArr, Condition<? super T> condition) {
        if (condition == null) {
            $$$reportNull$$$0(346);
        }
        if (tArr == null) {
            $$$reportNull$$$0(347);
        }
        for (T t : tArr) {
            if (!condition.value(t)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean and(Iterable<? extends T> iterable, Condition<? super T> condition) {
        if (iterable == null) {
            $$$reportNull$$$0(348);
        }
        if (condition == null) {
            $$$reportNull$$$0(349);
        }
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            if (!condition.value(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean exists(T[] tArr, Condition<? super T> condition) {
        if (condition == null) {
            $$$reportNull$$$0(BrickValues.DRONE_ROTATION_MAX);
        }
        if (tArr == null) {
            $$$reportNull$$$0(351);
        }
        for (T t : tArr) {
            if (condition.value(t)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean exists(Iterable<? extends T> iterable, Condition<? super T> condition) {
        if (iterable == null) {
            $$$reportNull$$$0(352);
        }
        if (condition == null) {
            $$$reportNull$$$0(353);
        }
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            if (condition.value(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean or(Iterable<? extends T> iterable, Condition<? super T> condition) {
        if (iterable == null) {
            $$$reportNull$$$0(356);
        }
        if (condition == null) {
            $$$reportNull$$$0(357);
        }
        return exists(iterable, condition);
    }

    public static <T> void quickSort(List<? extends T> list, Comparator<? super T> comparator) {
        if (list == null) {
            $$$reportNull$$$0(362);
        }
        if (comparator == null) {
            $$$reportNull$$$0(363);
        }
        quickSort(list, comparator, 0, list.size());
    }

    private static <T> void quickSort(List<? extends T> list, Comparator<? super T> comparator, int i, int i2) {
        int iMed3;
        if (list == null) {
            $$$reportNull$$$0(364);
        }
        if (comparator == null) {
            $$$reportNull$$$0(365);
        }
        if (i2 < 7) {
            for (int i3 = i; i3 < i2 + i; i3++) {
                for (int i4 = i3; i4 > i; i4--) {
                    int i5 = i4 - 1;
                    if (comparator.compare(list.get(i4), list.get(i5)) >= 0) {
                        break;
                    }
                    swapElements(list, i4, i5);
                }
            }
            return;
        }
        int iMed4 = (i2 >> 1) + i;
        if (i2 > 7) {
            int iMed5 = (i + i2) - 1;
            if (i2 > 40) {
                int i6 = i2 / 8;
                int i7 = i6 * 2;
                iMed3 = med3(list, comparator, i, i + i6, i + i7);
                iMed4 = med3(list, comparator, iMed4 - i6, iMed4, iMed4 + i6);
                iMed5 = med3(list, comparator, iMed5 - i7, iMed5 - i6, iMed5);
            } else {
                iMed3 = i;
            }
            iMed4 = med3(list, comparator, iMed3, iMed4, iMed5);
        }
        T t = list.get(iMed4);
        int i8 = i2 + i;
        int i9 = i8 - 1;
        int i10 = i;
        int i11 = i10;
        int i12 = i9;
        while (true) {
            if (i10 <= i9 && comparator.compare(list.get(i10), t) <= 0) {
                if (comparator.compare(list.get(i10), t) == 0) {
                    swapElements(list, i11, i10);
                    i11++;
                }
                i10++;
            } else {
                while (i9 >= i10 && comparator.compare(t, list.get(i9)) <= 0) {
                    if (comparator.compare(list.get(i9), t) == 0) {
                        swapElements(list, i9, i12);
                        i12--;
                    }
                    i9--;
                }
                if (i10 > i9) {
                    break;
                }
                swapElements(list, i10, i9);
                i10++;
                i9--;
            }
        }
        int i13 = i11 - i;
        int i14 = i10 - i11;
        int iMin = Math.min(i13, i14);
        vecswap(list, i, i10 - iMin, iMin);
        int i15 = i12 - i9;
        int iMin2 = Math.min(i15, (i8 - i12) - 1);
        vecswap(list, i10, i8 - iMin2, iMin2);
        if (i14 > 1) {
            quickSort(list, comparator, i, i14);
        }
        if (i15 > 1) {
            quickSort(list, comparator, i8 - i15, i15);
        }
    }

    private static <T> int med3(List<? extends T> list, Comparator<? super T> comparator, int i, int i2, int i3) {
        if (list == null) {
            $$$reportNull$$$0(366);
        }
        if (comparator == null) {
            $$$reportNull$$$0(367);
        }
        if (comparator.compare(list.get(i), list.get(i2)) < 0) {
            if (comparator.compare(list.get(i2), list.get(i3)) >= 0) {
                if (comparator.compare(list.get(i), list.get(i3)) >= 0) {
                    return i;
                }
                return i3;
            }
            return i2;
        }
        if (comparator.compare(list.get(i3), list.get(i2)) >= 0) {
            if (comparator.compare(list.get(i3), list.get(i)) >= 0) {
                return i;
            }
            return i3;
        }
        return i2;
    }

    private static <T> void vecswap(List<T> list, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            swapElements(list, i, i2);
            i4++;
            i++;
            i2++;
        }
    }

    public static <E> List<E> flatten(Iterable<? extends Collection<? extends E>> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(372);
        }
        Iterator<? extends Collection<? extends E>> it = iterable.iterator();
        int size = 0;
        while (it.hasNext()) {
            size += it.next().size();
        }
        List<E> arrayList = new ArrayList<>(size);
        Iterator<? extends Collection<? extends E>> it2 = iterable.iterator();
        while (it2.hasNext()) {
            arrayList.addAll(it2.next());
        }
        if (arrayList.isEmpty()) {
            arrayList = emptyList();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(373);
        }
        return arrayList;
    }

    public static <T> int indexOfIdentity(List<? extends T> list, T t) {
        if (list == null) {
            $$$reportNull$$$0(AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP);
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i) == t) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int indexOf(List<? extends T> list, Condition<? super T> condition) {
        if (list == null) {
            $$$reportNull$$$0(387);
        }
        if (condition == null) {
            $$$reportNull$$$0(388);
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (condition.value(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static <T> List<T> emptyList() {
        List<T> listEmptyList = ContainerUtilRt.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(398);
        }
        return listEmptyList;
    }

    public static <T> List<T> createLockFreeCopyOnWriteList() {
        return createConcurrentList();
    }

    @Deprecated
    public static <V> ConcurrentLongObjectMap<V> createConcurrentLongObjectMap() {
        return new ConcurrentLongObjectHashMap();
    }

    @Deprecated
    public static <V> ConcurrentIntObjectMap<V> createConcurrentIntObjectMap() {
        return new ConcurrentIntObjectHashMap();
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakValueMap() {
        ConcurrentMap<K, V> concurrentMapCreateConcurrentWeakValueMap = CollectionFactory.createConcurrentWeakValueMap();
        if (concurrentMapCreateConcurrentWeakValueMap == null) {
            $$$reportNull$$$0(400);
        }
        return concurrentMapCreateConcurrentWeakValueMap;
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentSoftKeySoftValueMap() {
        ConcurrentMap<K, V> concurrentMapCreateConcurrentSoftKeySoftValueMap = CollectionFactory.createConcurrentSoftKeySoftValueMap(100, 0.75f, Runtime.getRuntime().availableProcessors());
        if (concurrentMapCreateConcurrentSoftKeySoftValueMap == null) {
            $$$reportNull$$$0(401);
        }
        return concurrentMapCreateConcurrentSoftKeySoftValueMap;
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakKeySoftValueMap() {
        ConcurrentMap<K, V> concurrentMapCreateConcurrentWeakKeySoftValueMap = CollectionFactory.createConcurrentWeakKeySoftValueMap();
        if (concurrentMapCreateConcurrentWeakKeySoftValueMap == null) {
            $$$reportNull$$$0(402);
        }
        return concurrentMapCreateConcurrentWeakKeySoftValueMap;
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentSoftValueMap() {
        ConcurrentMap<K, V> concurrentMapCreateConcurrentSoftValueMap = CollectionFactory.createConcurrentSoftValueMap();
        if (concurrentMapCreateConcurrentSoftValueMap == null) {
            $$$reportNull$$$0(HttpStatus.SC_NOT_ACCEPTABLE);
        }
        return concurrentMapCreateConcurrentSoftValueMap;
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentSoftMap() {
        ConcurrentMap<K, V> concurrentMapCreateConcurrentSoftMap = CollectionFactory.createConcurrentSoftMap();
        if (concurrentMapCreateConcurrentSoftMap == null) {
            $$$reportNull$$$0(HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED);
        }
        return concurrentMapCreateConcurrentSoftMap;
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakMap() {
        ConcurrentMap<K, V> concurrentMapCreateConcurrentWeakMap = CollectionFactory.createConcurrentWeakMap();
        if (concurrentMapCreateConcurrentWeakMap == null) {
            $$$reportNull$$$0(HttpStatus.SC_REQUEST_TIMEOUT);
        }
        return concurrentMapCreateConcurrentWeakMap;
    }

    public static <T> ConcurrentList<T> createConcurrentList() {
        return new LockFreeCopyOnWriteArrayList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> void addIfNotNull(Collection<? super T> collection, T t) {
        if (collection == null) {
            $$$reportNull$$$0(416);
        }
        if (t != 0) {
            collection.add(t);
        }
    }

    public static <T, V> Set<V> map2Set(Collection<? extends T> collection, Function<? super T, ? extends V> function) {
        if (collection == null) {
            $$$reportNull$$$0(Constants.ERROR_TOO_MANY_REQUESTS);
        }
        if (function == null) {
            $$$reportNull$$$0(XKeySym.XK_Zcaron);
        }
        if (collection.isEmpty()) {
            Set<V> setEmptySet = Collections.emptySet();
            if (setEmptySet == null) {
                $$$reportNull$$$0(431);
            }
            return setEmptySet;
        }
        HashSet hashSet = new HashSet(collection.size());
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            hashSet.add(function.fun(it.next()));
        }
        return hashSet;
    }

    public static <T> T[] copyAndClear(Collection<? extends T> collection, ArrayFactory<? extends T> arrayFactory, boolean z) {
        if (collection == null) {
            $$$reportNull$$$0(XKeySym.XK_zabovedot);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(XKeySym.XK_Racute);
        }
        int size = collection.size();
        Object[] objArrCreate = arrayFactory.create(size);
        if (size > 0) {
            objArrCreate = (T[]) collection.toArray(objArrCreate);
            if (z) {
                collection.clear();
            }
        }
        if (objArrCreate == null) {
            $$$reportNull$$$0(449);
        }
        return objArrCreate;
    }

    public static <T> Collection<T> toCollection(Iterable<? extends T> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(452);
        }
        return iterable instanceof Collection ? (Collection) iterable : newArrayList(iterable);
    }

    public static <T> List<T> notNullize(List<T> list) {
        if (list == null) {
            list = emptyList();
        }
        if (list == null) {
            $$$reportNull$$$0(XKeySym.XK_Ccaron);
        }
        return list;
    }

    public static <T> Set<T> notNullize(Set<T> set) {
        if (set == null) {
            set = Collections.emptySet();
        }
        if (set == null) {
            $$$reportNull$$$0(457);
        }
        return set;
    }

    public static <K, V> Map<K, V> createSoftKeySoftValueMap() {
        Map<K, V> mapCreateSoftKeySoftValueMap = CollectionFactory.createSoftKeySoftValueMap();
        if (mapCreateSoftKeySoftValueMap == null) {
            $$$reportNull$$$0(470);
        }
        return mapCreateSoftKeySoftValueMap;
    }

    public static <K, V> Map<K, V> createWeakMap() {
        return createWeakMap(4);
    }

    public static <K, V> Map<K, V> createWeakMap(int i) {
        Map<K, V> mapCreateWeakMap = CollectionFactory.createWeakMap(i, 0.8f, HashingStrategy.canonical());
        if (mapCreateWeakMap == null) {
            $$$reportNull$$$0(XKeySym.XK_Rcaron);
        }
        return mapCreateWeakMap;
    }

    public static <T> IntObjectMap<T> createIntKeyWeakValueMap() {
        return new IntKeyWeakValueHashMap();
    }

    public static <T> List<T> immutableCopy(List<? extends T> list) {
        if (list == null) {
            $$$reportNull$$$0(XKeySym.XK_Udoubleacute);
        }
        if (list.isEmpty()) {
            List<T> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(TarConstants.XSTAR_ATIME_OFFSET);
            }
            return listEmptyList;
        }
        if (list.size() == 1) {
            List<T> listSingletonList = Collections.singletonList(list.get(0));
            if (listSingletonList == null) {
                $$$reportNull$$$0(477);
            }
            return listSingletonList;
        }
        return immutableList(list.toArray());
    }
}
