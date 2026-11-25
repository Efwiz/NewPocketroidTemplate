package org.jetbrains.kotlin.utils.addToStdlib;

import android.Manifest;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.reandroid.archive.PathTree;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.utils.IDEAPlatforms;
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI;

/* JADX INFO: compiled from: addToStdlib.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0013\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0010\u001a#\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0002¢\u0006\u0002\u0010\u0007\u001aY\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\t\"\u000e\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\f\"\u0004\b\u0001\u0010\u000b2*\u0010\r\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\u000f0\u000e\"\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\u000f¢\u0006\u0002\u0010\u0010\u001a=\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0012\"\u000e\b\u0000\u0010\u0005*\b\u0012\u0004\u0012\u0002H\u00050\f2\u0006\u0010\u0013\u001a\u0002H\u00052\u0012\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00050\u000e\"\u0002H\u0005¢\u0006\u0002\u0010\u0015\u001a,\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u0002H\u00050\u000f\"\u0004\b\u0000\u0010\u00052\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0002H\u0086\bø\u0001\u0000\u001a/\u0010\u0019\u001a\u0004\u0018\u0001H\u001a\"\u0004\b\u0000\u0010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u0002H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a/\u0010\u001e\u001a\u0004\u0018\u0001H\u001a\"\u0004\b\u0000\u0010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u0002H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a7\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00050 \"\u0004\b\u0000\u0010\u00052\u001e\u0010\u0014\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00050\u00020\u000e\"\b\u0012\u0004\u0012\u0002H\u00050\u0002¢\u0006\u0002\u0010!\u001a\u0010\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020%\u001a\u0010\u0010&\u001a\u00020#2\b\u0010'\u001a\u0004\u0018\u00010\u0003\u001aF\u0010(\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0005\"\b\b\u0001\u0010\u001a*\u0002H\u0005*\u0002H\u001a2\u0006\u0010)\u001a\u00020\u001c2\u0017\u0010*\u001a\u0013\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u0002H\u00050+¢\u0006\u0002\b,H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001a1\u0010.\u001a\u0002H\u0005\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0003*\u0004\u0018\u00010\u00032\f\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0002H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010/\u001aA\u00100\u001a\u0002H1\"\b\b\u0000\u00102*\u0002H1\"\u0004\b\u0001\u00101*\u0002H22\u0006\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H2\u0012\u0004\u0012\u0002H10+H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001a \u00103\u001a\u0002H\u0005\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0003*\u0004\u0018\u00010\u0003H\u0087\b¢\u0006\u0002\u00104\u001a$\u00105\u001a\r\u0012\t\u0012\u0007H\u0005¢\u0006\u0002\b706\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u000306H\u0087\b\u001a$\u00105\u001a\r\u0012\t\u0012\u0007H\u0005¢\u0006\u0002\b708\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u000308H\u0087\b\u001a.\u00109\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0:\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b*\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0:\u001a\u001c\u00109\u001a\b\u0012\u0004\u0012\u0002H\u00050;\"\u0004\b\u0000\u0010\u0005*\b\u0012\u0004\u0012\u0002H\u00050;\u001a6\u0010<\u001a\b\u0012\u0004\u0012\u0002H\u000508\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030=2\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u001c0+H\u0086\bø\u0001\u0000\u001a\u001e\u0010?\u001a\u0002H\u0005\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030\u000eH\u0086\b¢\u0006\u0002\u0010@\u001a\u001e\u0010?\u001a\u0002H\u0005\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030=H\u0086\b¢\u0006\u0002\u0010A\u001a\u001e\u0010?\u001a\u0002H\u0005\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030 H\u0086\b¢\u0006\u0002\u0010B\u001a$\u0010C\u001a\u0004\u0018\u0001H\u0005\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0003*\u0006\u0012\u0002\b\u00030\u000eH\u0086\b¢\u0006\u0002\u0010@\u001a$\u0010C\u001a\u0004\u0018\u0001H\u0005\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0003*\u0006\u0012\u0002\b\u00030=H\u0086\b¢\u0006\u0002\u0010A\u001a$\u0010C\u001a\u0004\u0018\u0001H\u0005\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0003*\u0006\u0012\u0002\b\u00030 H\u0086\b¢\u0006\u0002\u0010B\u001aC\u0010D\u001a\u0004\u0018\u0001H\u001a\"\u0004\b\u0000\u0010\u0005\"\b\b\u0001\u0010\u001a*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00050\u000e2\u0014\u0010E\u001a\u0010\u0012\u0004\u0012\u0002H\u0005\u0012\u0006\u0012\u0004\u0018\u0001H\u001a0+H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010F\u001aC\u0010D\u001a\u0004\u0018\u0001H\u001a\"\u0004\b\u0000\u0010\u0005\"\b\b\u0001\u0010\u001a*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00050=2\u0014\u0010E\u001a\u0010\u0012\u0004\u0012\u0002H\u0005\u0012\u0006\u0012\u0004\u0018\u0001H\u001a0+H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010G\u001aH\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u00050:\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u0005082\u0018\u0010I\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n060+H\u0086\bø\u0001\u0000\u001aN\u0010J\u001a\u0014\u0012\u0004\u0012\u0002H\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u0005080:\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u0005082\u0018\u0010K\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n060+H\u0086\bø\u0001\u0000\u001a\u0082\u0001\u0010J\u001a\u0014\u0012\u0004\u0012\u0002H\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000b080:\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010L\"\u0004\b\u0002\u0010\n\"\u0004\b\u0003\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u0005082\u0018\u0010K\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002HL060+2\u0012\u0010M\u001a\u000e\u0012\u0004\u0012\u0002HL\u0012\u0004\u0012\u0002H\n0+2\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u000b0+H\u0086\bø\u0001\u0000\u001a_\u0010O\u001a\u0004\u0018\u0001HP\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u001a\"\u0010\b\u0002\u0010P*\n\u0012\u0006\b\u0000\u0012\u0002H\u001a0Q*\b\u0012\u0004\u0012\u0002H\u00050=2\u0006\u0010R\u001a\u0002HP2\u001a\u0010E\u001a\u0016\u0012\u0004\u0012\u0002H\u0005\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u001a\u0018\u00010=0+H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010S\u001a;\u0010T\u001a\u0002HP\"\u0004\b\u0000\u0010\u0005\"\u0010\b\u0001\u0010P*\n\u0012\u0006\b\u0000\u0012\u0002H\u00050Q*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050=0=2\u0006\u0010U\u001a\u0002HP¢\u0006\u0002\u0010V\u001aU\u0010W\u001a\u0002H\u001a\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u001a*\b\u0012\u0004\u0012\u0002H\u0005062\u0012\u0010E\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u001a0+2\u0018\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u0002H\u001a0YH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010Z\u001ag\u0010[\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b\"\b\b\u0002\u0010\\*\u0002H\u000b*\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0]2\u0006\u0010^\u001a\u0002H\n2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\\0+2\u0012\u0010`\u001a\u000e\u0012\u0004\u0012\u0002H\\\u0012\u0004\u0012\u00020a0+H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010b\u001a-\u0010c\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u0005*\u00020\u001c2\u000e\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00050\u0002H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001aN\u0010d\u001a\u0004\u0018\u0001He\"\u0004\b\u0000\u0010\u0005\"\u000e\b\u0001\u0010P*\b\u0012\u0004\u0012\u0002H\u000506\"\u0004\b\u0002\u0010e*\u0002HP2\u0019\u0010*\u001a\u0015\u0012\u0004\u0012\u0002HP\u0012\u0006\u0012\u0004\u0018\u0001He0+¢\u0006\u0002\b,H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010f\u001aN\u0010d\u001a\u0004\u0018\u0001He\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010e*\n\u0012\u0006\b\u0001\u0012\u0002H\u00050\u000e2!\u0010*\u001a\u001d\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00050\u000e\u0012\u0006\u0012\u0004\u0018\u0001He0+¢\u0006\u0002\b,H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010F\u001a-\u0010g\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u0005*\u00020\u001c2\u000e\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00050\u0002H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a-\u0010h\u001a\u0004\u0018\u00010i*\u00020%2\u0006\u0010j\u001a\u00020k2\b\b\u0002\u0010l\u001a\u00020i2\b\b\u0002\u0010m\u001a\u00020\u001c¢\u0006\u0002\u0010n\u001a-\u0010o\u001a\u0004\u0018\u00010i*\u00020%2\u0006\u0010j\u001a\u00020k2\b\b\u0002\u0010l\u001a\u00020i2\b\b\u0002\u0010m\u001a\u00020\u001c¢\u0006\u0002\u0010n\u001a$\u0010p\u001a\u0004\u0018\u0001H\u0005\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0003*\u0006\u0012\u0002\b\u00030=H\u0086\b¢\u0006\u0002\u0010A\u001a9\u0010q\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001a08\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u0005080\u000f\"\u0004\b\u0000\u0010\u0005\"\u0006\b\u0001\u0010\u001a\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00050=H\u0086\b\u001a\u001b\u0010r\u001a\u0002Hs\"\u0004\b\u0000\u0010s*\b\u0012\u0004\u0012\u0002Hs0t¢\u0006\u0002\u0010u\u001a'\u0010v\u001a\t\u0018\u0001H\u0005¢\u0006\u0002\b7\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\u0003*\u0004\u0018\u00010\u0003H\u0087\b¢\u0006\u0002\u00104\u001a6\u0010w\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u001a*\b\u0012\u0004\u0012\u0002H\u00050=2\u0012\u0010x\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u001a0+H\u0086\bø\u0001\u0000\u001a0\u0010y\u001a\u00020\u0017\"\u0004\b\u0000\u0010\u0005*\b\u0012\u0004\u0012\u0002H\u00050=2\u0012\u0010I\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00170+H\u0086\bø\u0001\u0000\u001a.\u0010z\u001a\u000e\u0012\u0004\u0012\u0002H{\u0012\u0004\u0012\u0002H|0\u000f\"\u0004\b\u0000\u0010|\"\u0004\b\u0001\u0010{*\u000e\u0012\u0004\u0012\u0002H|\u0012\u0004\u0012\u0002H{0\u000f\u001a\u001e\u0010}\u001a\u00020a\"\u0004\b\u0000\u0010s*\b\u0012\u0004\u0012\u0002Hs0t2\u0006\u0010~\u001a\u00020i\u001a^\u0010\u007f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u001a0\u000f08\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u001a*\b\u0012\u0004\u0012\u0002H\u00050=2\r\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u0002H\u001a0=2\r\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00022\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u0002H\u0082\b\u001aB\u0010\u0083\u0001\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u0001H\u0005\u0012\u0006\u0012\u0004\u0018\u0001H\u001a0\u000f08\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u001a*\b\u0012\u0004\u0012\u0002H\u00050=2\r\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u0002H\u001a0=\"\u001e\u0010\u0000\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0084\u0001"}, d2 = {"constantMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Lkotlin/Function0;", "", "constant", ExifInterface.GPS_DIRECTION_TRUE, "calculator", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "enumMapOf", "Ljava/util/EnumMap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/EnumMap;", "enumSetOf", "Ljava/util/EnumSet;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, PathTree.NAME_elements, "(Ljava/lang/Enum;[Ljava/lang/Enum;)Ljava/util/EnumSet;", "measureTimeMillisWithResult", "", "block", "runIf", "R", "condition", "", "(ZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "runUnless", "sequenceOfLazyValues", "Lkotlin/sequences/Sequence;", "([Lkotlin/jvm/functions/Function0;)Lkotlin/sequences/Sequence;", "shouldNotBeCalled", "", CrashHianalyticsData.MESSAGE, "", "unreachableBranch", "argument", "applyIf", PsiKeyword.IF, "body", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;ZLkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "assertedCast", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "butIf", "B", "A", "cast", "(Ljava/lang/Object;)Ljava/lang/Object;", "castAll", "", "Lkotlin/internal/NoInfer;", "", "compactIfPossible", "", "", "filterIsInstanceWithChecker", "", "additionalChecker", "firstIsInstance", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_DESCRIPTOR, "(Ljava/lang/Iterable;)Ljava/lang/Object;", "(Lkotlin/sequences/Sequence;)Ljava/lang/Object;", "firstIsInstanceOrNull", "firstNotNullResult", "transform", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "flatAssociateBy", "selector", "flatGroupBy", "keySelector", "U", "keyTransformer", "valueTransformer", "flatMapToNullable", "C", "", FirebaseAnalytics.Param.DESTINATION, "(Ljava/lang/Iterable;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "flattenTo", "c", "(Ljava/lang/Iterable;Ljava/util/Collection;)Ljava/util/Collection;", "foldMap", "operation", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "getOrPut", "VA", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "defaultValue", "postCompute", "", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "ifFalse", "ifNotEmpty", "O", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "ifTrue", "indexOfOrNull", "", PsiKeyword.CHAR, "", "startIndex", "ignoreCase", "(Ljava/lang/String;CIZ)Ljava/lang/Integer;", "lastIndexOfOrNull", "lastIsInstanceOrNull", "partitionIsInstance", "popLast", ExifInterface.LONGITUDE_EAST, "", "(Ljava/util/List;)Ljava/lang/Object;", "safeAs", "same", "extractor", "sumByLong", "swap", "T2", "T1", "trimToSize", "newSize", "zipWithDefault", "other", "leftDefault", "rightDefault", "zipWithNulls", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AddToStdlibKt {
    private static final ConcurrentHashMap<Function0<?>, Object> constantMap = new ConcurrentHashMap<>();

    public static final /* synthetic */ <T> T firstIsInstanceOrNull(Sequence<?> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Iterator<?> it = sequence.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (t instanceof Object) {
                return t;
            }
        }
        return null;
    }

    public static final /* synthetic */ <T> T firstIsInstanceOrNull(Iterable<?> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (t instanceof Object) {
                return t;
            }
        }
        return null;
    }

    public static final /* synthetic */ <T> T firstIsInstanceOrNull(Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        for (Object obj : objArr) {
            T t = (T) obj;
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (t instanceof Object) {
                return t;
            }
        }
        return null;
    }

    public static final /* synthetic */ <T> T firstIsInstance(Sequence<?> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Iterator<?> it = sequence.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (t instanceof Object) {
                return t;
            }
        }
        throw new NoSuchElementException("No element of given type found");
    }

    public static final /* synthetic */ <T> T firstIsInstance(Iterable<?> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (t instanceof Object) {
                return t;
            }
        }
        throw new NoSuchElementException("No element of given type found");
    }

    public static final /* synthetic */ <T> T firstIsInstance(Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        for (Object obj : objArr) {
            T t = (T) obj;
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (t instanceof Object) {
                return t;
            }
        }
        throw new NoSuchElementException("No element of given type found");
    }

    public static final /* synthetic */ <T> List<T> filterIsInstanceWithChecker(Iterable<?> iterable, Function1<? super T, Boolean> additionalChecker) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(additionalChecker, "additionalChecker");
        ArrayList arrayList = new ArrayList();
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            Manifest manifest = (Object) it.next();
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if ((manifest instanceof Object) && additionalChecker.invoke(manifest).booleanValue()) {
                arrayList.add(manifest);
            }
        }
        return arrayList;
    }

    public static final /* synthetic */ <T> T lastIsInstanceOrNull(Iterable<?> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        if (iterable instanceof List) {
            int size = ((Collection) iterable).size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    T t = (T) ((List) iterable).get(size);
                    Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                    if (t instanceof Object) {
                        return t;
                    }
                    if (i >= 0) {
                        size = i;
                    }
                }
            }
            return null;
        }
        for (T t2 : CollectionsKt.reversed(iterable)) {
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (t2 instanceof Object) {
                return t2;
            }
        }
        return null;
    }

    public static final /* synthetic */ <T, R> Pair<List<R>, List<T>> partitionIsInstance(Iterable<? extends T> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (T t : iterable) {
            Intrinsics.reifiedOperationMarker(3, "R");
            if (t instanceof Object) {
                arrayList.add(t);
            } else {
                arrayList2.add(t);
            }
        }
        return new Pair<>(arrayList, arrayList2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @UnsafeCastFunction
    public static final /* synthetic */ <T> List<T> castAll(List<?> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        for (Object obj : list) {
            Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        }
        return list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @UnsafeCastFunction
    public static final /* synthetic */ <T> Collection<T> castAll(Collection<?> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        for (Object obj : collection) {
            Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        }
        return collection;
    }

    public static final <T> Sequence<T> sequenceOfLazyValues(Function0<? extends T>... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return SequencesKt.map(ArraysKt.asSequence(elements), new Function1<Function0<? extends T>, T>() { // from class: org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt.sequenceOfLazyValues.1
            @Override // kotlin.jvm.functions.Function1
            public final T invoke(Function0<? extends T> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.invoke();
            }
        });
    }

    public static final <T1, T2> Pair<T2, T1> swap(Pair<? extends T1, ? extends T2> pair) {
        Intrinsics.checkNotNullParameter(pair, "<this>");
        return new Pair<>(pair.getSecond(), pair.getFirst());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @UnsafeCastFunction
    public static final /* synthetic */ <T> T safeAs(Object obj) {
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @UnsafeCastFunction
    public static final /* synthetic */ <T> T cast(Object obj) {
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @UnsafeCastFunction
    public static final /* synthetic */ <T> T assertedCast(Object obj, Function0<String> message) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        if (obj != 0) {
            return obj;
        }
        throw new AssertionError(message.invoke());
    }

    public static final <T> T constant(Function0<? extends T> calculator) {
        Intrinsics.checkNotNullParameter(calculator, "calculator");
        T t = (T) constantMap.get(calculator);
        if (t != null) {
            return t;
        }
        Field[] declaredFields = calculator.getClass().getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
        ArrayList arrayList = new ArrayList();
        for (Field field : declaredFields) {
            if ((field.getModifiers() & 8) == 0) {
                arrayList.add(field);
            }
        }
        arrayList.isEmpty();
        T tInvoke = calculator.invoke();
        constantMap.put(calculator, tInvoke);
        return tInvoke;
    }

    public static /* synthetic */ Integer indexOfOrNull$default(String str, char c, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return indexOfOrNull(str, c, i, z);
    }

    public static final Integer indexOfOrNull(String str, char c, int i, boolean z) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Integer numValueOf = Integer.valueOf(StringsKt.indexOf(str, c, i, z));
        if (numValueOf.intValue() >= 0) {
            return numValueOf;
        }
        return null;
    }

    public static /* synthetic */ Integer lastIndexOfOrNull$default(String str, char c, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(str);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return lastIndexOfOrNull(str, c, i, z);
    }

    public static final Integer lastIndexOfOrNull(String str, char c, int i, boolean z) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Integer numValueOf = Integer.valueOf(StringsKt.lastIndexOf(str, c, i, z));
        if (numValueOf.intValue() >= 0) {
            return numValueOf;
        }
        return null;
    }

    @IDEAPluginsCompatibilityAPI(message = "Use firstNotNullOfOrNull from stdlib instead", plugins = "Android plugin in the IDEA, kotlin-ultimate.kotlin-ocswift", usedIn = {IDEAPlatforms._211, IDEAPlatforms._212, IDEAPlatforms._213})
    public static final <T, R> R firstNotNullResult(Iterable<? extends T> iterable, Function1<? super T, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            R rInvoke = transform.invoke(it.next());
            if (rInvoke != null) {
                return rInvoke;
            }
        }
        return null;
    }

    @IDEAPluginsCompatibilityAPI(message = "Use firstNotNullOfOrNull from stdlib instead", plugins = "Android plugin in the IDEA", usedIn = {IDEAPlatforms._211, IDEAPlatforms._212, IDEAPlatforms._213})
    public static final <T, R> R firstNotNullResult(T[] tArr, Function1<? super T, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        for (T t : tArr) {
            R rInvoke = transform.invoke(t);
            if (rInvoke != null) {
                return rInvoke;
            }
        }
        return null;
    }

    public static final <T> long sumByLong(Iterable<? extends T> iterable, Function1<? super T, Long> selector) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator<? extends T> it = iterable.iterator();
        long jLongValue = 0;
        while (it.hasNext()) {
            jLongValue += selector.invoke(it.next()).longValue();
        }
        return jLongValue;
    }

    public static final <T, C extends Collection<? extends T>, O> O ifNotEmpty(C c, Function1<? super C, ? extends O> body) {
        Intrinsics.checkNotNullParameter(c, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        if (c.isEmpty()) {
            return null;
        }
        return body.invoke(c);
    }

    public static final <T, O> O ifNotEmpty(T[] tArr, Function1<? super T[], ? extends O> body) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        if (tArr.length == 0) {
            return null;
        }
        return body.invoke(tArr);
    }

    public static final <T> Pair<Long, T> measureTimeMillisWithResult(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        long jCurrentTimeMillis = System.currentTimeMillis();
        return new Pair<>(Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis), block.invoke());
    }

    public static final <T, C extends Collection<? super T>> C flattenTo(Iterable<? extends Iterable<? extends T>> iterable, C c) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(c, "c");
        Iterator<? extends Iterable<? extends T>> it = iterable.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(c, it.next());
        }
        return c;
    }

    public static final <T, R, C extends Collection<? super R>> C flatMapToNullable(Iterable<? extends T> iterable, C destination, Function1<? super T, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            Iterable<? extends R> iterableInvoke = transform.invoke(it.next());
            if (iterableInvoke == null) {
                return null;
            }
            CollectionsKt.addAll(destination, iterableInvoke);
        }
        return destination;
    }

    public static final <T, R> boolean same(Iterable<? extends T> iterable, Function1<? super T, ? extends R> extractor) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(extractor, "extractor");
        Iterator<? extends T> it = iterable.iterator();
        R rInvoke = extractor.invoke(it.next());
        while (it.hasNext()) {
            if (!Intrinsics.areEqual(extractor.invoke(it.next()), rInvoke)) {
                return false;
            }
        }
        return true;
    }

    public static final <R> R runIf(boolean z, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        if (z) {
            return block.invoke();
        }
        return null;
    }

    public static final <R> R runUnless(boolean z, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        if (z) {
            return null;
        }
        return block.invoke();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <A extends B, B> B butIf(A a, boolean z, Function1<? super A, ? extends B> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return z ? block.invoke(a) : a;
    }

    public static final <T, R> R foldMap(Collection<? extends T> collection, Function1<? super T, ? extends R> transform, Function2<? super R, ? super R, ? extends R> operation) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Intrinsics.checkNotNullParameter(operation, "operation");
        Iterator<? extends T> it = collection.iterator();
        R rInvoke = transform.invoke(it.next());
        while (it.hasNext()) {
            rInvoke = operation.invoke(rInvoke, transform.invoke(it.next()));
        }
        return rInvoke;
    }

    public static final <E> void trimToSize(List<E> list, int i) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        list.subList(i, list.size()).clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [V, java.lang.Object] */
    public static final <K, V, VA extends V> V getOrPut(Map<K, V> map, K k, Function1<? super K, ? extends VA> defaultValue, Function1<? super VA, Unit> postCompute) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        Intrinsics.checkNotNullParameter(postCompute, "postCompute");
        V v = (V) map.get(k);
        if (v != null) {
            return v;
        }
        VA vaInvoke = defaultValue.invoke(k);
        map.put(k, vaInvoke);
        postCompute.invoke(vaInvoke);
        return vaInvoke;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Set<T> compactIfPossible(Set<? extends T> set) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        int size = set.size();
        if (size != 0) {
            return size != 1 ? set : SetsKt.setOf(CollectionsKt.single(set));
        }
        return SetsKt.emptySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <K, V> Map<K, V> compactIfPossible(Map<K, ? extends V> map) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        int size = map.size();
        if (size == 0) {
            return MapsKt.emptyMap();
        }
        if (size != 1) {
            return map;
        }
        Map<K, V> mapSingletonMap = Collections.singletonMap(CollectionsKt.single(map.keySet()), CollectionsKt.single(map.values()));
        Intrinsics.checkNotNullExpressionValue(mapSingletonMap, "singletonMap(...)");
        return mapSingletonMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T, R extends T> T applyIf(R r, boolean z, Function1<? super R, ? extends T> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return z ? body.invoke(r) : r;
    }

    public static final <T> T ifTrue(boolean z, Function0<? extends T> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        if (z) {
            return body.invoke();
        }
        return null;
    }

    public static final <T> T ifFalse(boolean z, Function0<? extends T> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        if (z) {
            return null;
        }
        return body.invoke();
    }

    public static final <T, U, K, V> Map<K, List<V>> flatGroupBy(List<? extends T> list, Function1<? super T, ? extends Collection<? extends U>> keySelector, Function1<? super U, ? extends K> keyTransformer, Function1<? super T, ? extends V> valueTransformer) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(keySelector, "keySelector");
        Intrinsics.checkNotNullParameter(keyTransformer, "keyTransformer");
        Intrinsics.checkNotNullParameter(valueTransformer, "valueTransformer");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T t : list) {
            Collection<? extends U> collectionInvoke = keySelector.invoke(t);
            V vInvoke = valueTransformer.invoke(t);
            Iterator<? extends U> it = collectionInvoke.iterator();
            while (it.hasNext()) {
                K kInvoke = keyTransformer.invoke(it.next());
                ArrayList arrayList = linkedHashMap.get(kInvoke);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(kInvoke, arrayList);
                }
                arrayList.add(vInvoke);
            }
        }
        return linkedHashMap;
    }

    public static final <T, K> Map<K, T> flatAssociateBy(List<? extends T> list, Function1<? super T, ? extends Collection<? extends K>> selector) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (T t : list) {
            Iterator<? extends K> it = selector.invoke(t).iterator();
            while (it.hasNext()) {
                mapCreateMapBuilder.put(it.next(), t);
            }
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    public static final <E> E popLast(List<E> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return list.remove(CollectionsKt.getLastIndex(list));
    }

    public static final <K extends Enum<K>, V> EnumMap<K, V> enumMapOf(Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        return new EnumMap<>(MapsKt.mapOf((Pair[]) Arrays.copyOf(pairs, pairs.length)));
    }

    public static final <T extends Enum<T>> EnumSet<T> enumSetOf(T element, T... elements) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(elements, "elements");
        EnumSet<T> enumSetOf = EnumSet.of(element, (T[]) Arrays.copyOf(elements, elements.length));
        Intrinsics.checkNotNullExpressionValue(enumSetOf, "of(...)");
        return enumSetOf;
    }

    public static /* synthetic */ Void shouldNotBeCalled$default(String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "should not be called";
        }
        return shouldNotBeCalled(str);
    }

    public static final Void shouldNotBeCalled(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        throw new IllegalStateException(message.toString());
    }

    private static final <T, R> List<Pair<T, R>> zipWithDefault(Iterable<? extends T> iterable, Iterable<? extends R> iterable2, Function0<? extends T> function0, Function0<? extends R> function1) {
        Iterator<? extends T> it = iterable.iterator();
        Iterator<? extends R> it2 = iterable2.iterator();
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        while (it.hasNext() && it2.hasNext()) {
            listCreateListBuilder.add(TuplesKt.to(it.next(), it2.next()));
        }
        while (it.hasNext()) {
            listCreateListBuilder.add(TuplesKt.to(it.next(), function1.invoke()));
        }
        while (it2.hasNext()) {
            listCreateListBuilder.add(TuplesKt.to(function0.invoke(), it2.next()));
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    public static final Void unreachableBranch(Object obj) {
        throw new IllegalStateException(("This argument should've been processed by previous when branches but it wasn't: " + obj).toString());
    }

    public static final <T, K> Map<K, List<T>> flatGroupBy(List<? extends T> list, Function1<? super T, ? extends Collection<? extends K>> keySelector) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(keySelector, "keySelector");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T t : list) {
            for (K k : keySelector.invoke(t)) {
                ArrayList arrayList = (List) linkedHashMap.get(k);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(k, arrayList);
                }
                arrayList.add(t);
            }
        }
        return linkedHashMap;
    }

    public static final <T, R> List<Pair<T, R>> zipWithNulls(Iterable<? extends T> iterable, Iterable<? extends R> other) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Iterator<? extends T> it = iterable.iterator();
        Iterator<? extends R> it2 = other.iterator();
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        while (it.hasNext() && it2.hasNext()) {
            listCreateListBuilder.add(TuplesKt.to(it.next(), it2.next()));
        }
        while (it.hasNext()) {
            listCreateListBuilder.add(TuplesKt.to(it.next(), null));
        }
        while (it2.hasNext()) {
            listCreateListBuilder.add(TuplesKt.to(null, it2.next()));
        }
        return CollectionsKt.build(listCreateListBuilder);
    }
}
