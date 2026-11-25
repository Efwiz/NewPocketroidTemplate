package org.jetbrains.kotlin.psi;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.apk.UncompressedFiles;
import com.reandroid.arsc.value.CompoundEntry;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.ui.SpriteActivity;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.SamWrapperCodegen;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.LocalTimeCounter;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: KtPsiFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ð\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 Ø\u00012\u00020\u0001:\bÕ\u0001Ö\u0001×\u0001Ø\u0001B\u0019\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bB\u0019\b\u0017\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bB)\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010\u0011\u001a\u00020\u0012J$\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u0010\u0015\u001a\u00020\u00122\b\b\u0001\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\rH\u0007J\u0010\u0010\u0017\u001a\u00020\u00182\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001b\u001a\u00020\u001c2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J0\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\b\b\u0002\u0010!\u001a\u00020\u00052\b\b\u0002\u0010\"\u001a\u00020\u0005J\u0010\u0010#\u001a\u00020$2\b\b\u0001\u0010%\u001a\u00020\u0012J\u001a\u0010&\u001a\u00020'2\b\b\u0001\u0010\u0011\u001a\u00020\u00122\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u000e\u0010(\u001a\u00020)2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010*\u001a\u00020+2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0012\u0010,\u001a\u0004\u0018\u00010-2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0010\u0010.\u001a\u00020/2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0006\u00100\u001a\u00020\rJ\u0006\u00101\u001a\u00020\rJ\u0006\u00102\u001a\u00020\rJ\u0010\u00103\u001a\u0002042\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0006\u00105\u001a\u000206J\u0010\u00105\u001a\u0002062\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0006\u00107\u001a\u00020\rJ\u001f\u00108\u001a\u0002H9\"\b\b\u0000\u00109*\u00020:2\b\b\u0001\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010;J\u0010\u0010<\u001a\u00020=2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0010\u0010>\u001a\u00020?2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0006\u0010@\u001a\u00020\rJ\u0006\u0010A\u001a\u00020\rJ\u0006\u0010B\u001a\u00020$J\u0006\u0010C\u001a\u00020DJ\u0006\u0010E\u001a\u00020FJ\u0010\u0010G\u001a\u00020H2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0006\u0010I\u001a\u00020JJ\u0010\u0010K\u001a\u00020\u001e2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u001a\u0010L\u001a\u00020M2\b\b\u0001\u0010\u0011\u001a\u00020\u00122\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u0012\u0010N\u001a\u0004\u0018\u00010\u001e2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0010\u0010O\u001a\u00020\u00142\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u001a\u0010O\u001a\u00020\u00142\b\b\u0001\u0010\u0015\u001a\u00020\u00122\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0010\u0010P\u001a\u00020\u00182\b\b\u0001\u0010Q\u001a\u00020\u0012J\u0010\u0010R\u001a\u00020S2\b\b\u0001\u0010Q\u001a\u00020\u0012J$\u0010T\u001a\u00020\u00142\b\b\u0001\u0010\u0015\u001a\u00020\u00122\b\b\u0001\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\rH\u0007J\u0010\u0010U\u001a\u00020V2\b\b\u0001\u0010W\u001a\u00020\u0012J\u000e\u0010X\u001a\u00020?2\u0006\u0010Y\u001a\u00020ZJ\u000e\u0010[\u001a\u00020\\2\u0006\u0010Y\u001a\u00020ZJ\u0010\u0010]\u001a\u00020\r2\b\b\u0001\u0010\u001f\u001a\u00020\u0012J\"\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020\u001e2\u0006\u0010a\u001a\u00020\u001e2\n\b\u0002\u0010b\u001a\u0004\u0018\u00010\u001eJ\u000e\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020fJ\u001c\u0010g\u001a\b\u0012\u0004\u0012\u00020d0h2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020f0jH\u0007J\u0010\u0010k\u001a\u00020l2\b\b\u0001\u0010m\u001a\u00020\u0012J\u001a\u0010n\u001a\u00020o2\b\b\u0001\u0010p\u001a\u00020\u00122\b\b\u0001\u0010q\u001a\u00020\u0012J\u0010\u0010r\u001a\u00020s2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0012\u0010t\u001a\u0004\u0018\u00010s2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0010\u0010u\u001a\u00020v2\b\b\u0001\u0010w\u001a\u00020\u0012J\u0010\u0010x\u001a\u00020?2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u000e\u0010y\u001a\u00020\r2\u0006\u0010z\u001a\u00020{J\u0010\u0010|\u001a\u00020F2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u000e\u0010|\u001a\u00020F2\u0006\u0010z\u001a\u00020{J\u0010\u0010}\u001a\u00020\r2\b\b\u0001\u0010\u001f\u001a\u00020\u0012J\u0012\u0010~\u001a\u0004\u0018\u00010\r2\b\b\u0001\u0010\u001f\u001a\u00020\u0012J\u0006\u0010\u007f\u001a\u00020\rJ\u0010\u0010\u007f\u001a\u00020\r2\b\u0010\u0080\u0001\u001a\u00030\u0081\u0001J\u0011\u0010\u0082\u0001\u001a\u0002062\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0012\u0010\u0083\u0001\u001a\u00030\u0084\u00012\b\b\u0001\u0010\u001f\u001a\u00020\u0012J\u0012\u0010\u0085\u0001\u001a\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0088\u0001J\u0014\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0088\u0001J\u0011\u0010\u008a\u0001\u001a\u00020?2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0011\u0010\u008b\u0001\u001a\u00020s2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u001b\u0010\u008c\u0001\u001a\u00020\u00142\b\b\u0001\u0010\u0015\u001a\u00020\u00122\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0012\u0010\u008d\u0001\u001a\u00030\u008e\u00012\b\b\u0003\u0010\u0011\u001a\u00020\u0012J\u0015\u0010\u008f\u0001\u001a\u00030\u008e\u00012\u000b\b\u0001\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u0012J\u0012\u0010\u0091\u0001\u001a\u00030\u0092\u00012\b\b\u0001\u0010\u0011\u001a\u00020\u0012J(\u0010\u0091\u0001\u001a\u00030\u0092\u00012\b\b\u0001\u0010\u001f\u001a\u00020\u00122\u000b\b\u0001\u0010\u0093\u0001\u001a\u0004\u0018\u00010\u00122\u0007\u0010\u0094\u0001\u001a\u00020\u0005J5\u0010\u0091\u0001\u001a\u00030\u0092\u00012\b\b\u0001\u0010\u001f\u001a\u00020\u00122\u000b\b\u0001\u0010\u0093\u0001\u001a\u0004\u0018\u00010\u00122\u0007\u0010\u0094\u0001\u001a\u00020\u00052\u000b\b\u0001\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u0012JB\u0010\u0091\u0001\u001a\u00030\u0092\u00012\u000b\b\u0001\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u00122\b\b\u0001\u0010\u001f\u001a\u00020\u00122\u000b\b\u0001\u0010\u0093\u0001\u001a\u0004\u0018\u00010\u00122\u0007\u0010\u0094\u0001\u001a\u00020\u00052\u000b\b\u0001\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u0012J\u0010\u0010\u0096\u0001\u001a\u00030\u0097\u00012\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\u009a\u0001\u001a\u00030\u0099\u00012\u0006\u0010\u001d\u001a\u00020\u001eJ\u0013\u0010\u009b\u0001\u001a\u00030\u009c\u00012\t\b\u0001\u0010\u009d\u0001\u001a\u00020\u0012J\u0007\u0010\u009e\u0001\u001a\u00020\rJ\u0012\u0010\u009f\u0001\u001a\u00030\u0084\u00012\b\b\u0001\u0010\u001f\u001a\u00020\u0012J\u0012\u0010 \u0001\u001a\u00030¡\u00012\b\b\u0001\u0010\u001f\u001a\u00020\u0012J*\u0010¢\u0001\u001a\u00020$2\u0007\u0010£\u0001\u001a\u00020\u001e2\u000b\b\u0003\u0010¤\u0001\u001a\u0004\u0018\u00010\u00122\u000b\b\u0003\u0010¥\u0001\u001a\u0004\u0018\u00010\u0012J\u0007\u0010¦\u0001\u001a\u00020\rJ\u0013\u0010§\u0001\u001a\u00030¨\u00012\t\b\u0001\u0010©\u0001\u001a\u00020\u0012J\u0012\u0010ª\u0001\u001a\u00030«\u00012\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0012\u0010¬\u0001\u001a\u00030\u00ad\u00012\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\b\u0010®\u0001\u001a\u00030¯\u0001J\u0013\u0010®\u0001\u001a\u00030¯\u00012\t\b\u0001\u0010°\u0001\u001a\u00020\u0012J\u0012\u0010±\u0001\u001a\u00020Z2\t\b\u0001\u0010\u0093\u0001\u001a\u00020\u0012J\u0011\u0010±\u0001\u001a\u00020Z2\b\u0010²\u0001\u001a\u00030³\u0001J+\u0010´\u0001\u001a\u00030µ\u00012\b\b\u0001\u0010\u001f\u001a\u00020\u00122\r\u0010¶\u0001\u001a\b\u0012\u0004\u0012\u00020\u00120h2\b\b\u0001\u0010q\u001a\u00020\u0012J+\u0010´\u0001\u001a\u00030µ\u00012\b\b\u0001\u0010\u001f\u001a\u00020\u00122\r\u0010¶\u0001\u001a\b\u0012\u0004\u0012\u00020\u00120h2\b\u0010²\u0001\u001a\u00030³\u0001J\u001c\u0010·\u0001\u001a\r ¹\u0001*\u0005\u0018\u00010¸\u00010¸\u00012\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0012\u0010º\u0001\u001a\u00030»\u00012\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u001c\u0010¼\u0001\u001a\u00030½\u00012\b\b\u0001\u0010\u0011\u001a\u00020\u00122\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u0014\u0010¾\u0001\u001a\u0004\u0018\u00010Z2\t\b\u0001\u0010\u0093\u0001\u001a\u00020\u0012J\u0012\u0010¿\u0001\u001a\u00030À\u00012\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0012\u0010Á\u0001\u001a\u00030Â\u00012\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0007\u0010Ã\u0001\u001a\u00020\rJ\u0007\u0010Ä\u0001\u001a\u00020\rJ\u0013\u0010Å\u0001\u001a\u00030Æ\u00012\t\b\u0001\u0010Ç\u0001\u001a\u00020\u0012J\u0013\u0010È\u0001\u001a\u00030É\u00012\t\b\u0001\u0010Ê\u0001\u001a\u00020\u0012J\u0007\u0010Ë\u0001\u001a\u00020\rJ\u0011\u0010Ë\u0001\u001a\u00020\r2\b\b\u0001\u0010\u0011\u001a\u00020\u0012J\u0014\u0010Ì\u0001\u001a\u000f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0Í\u0001J\u0015\u0010Î\u0001\u001a\u0004\u0018\u00010\u001e2\b\b\u0001\u0010\u0011\u001a\u00020\u0012H\u0002J\u001d\u0010Ï\u0001\u001a\u00020\u00142\b\b\u0001\u0010\u0015\u001a\u00020\u00122\b\b\u0001\u0010\u0011\u001a\u00020\u0012H\u0002J\u000f\u0010Ð\u0001\u001a\u00020$2\u0006\u0010\u001d\u001a\u00020\u001eJ\u001c\u0010Ñ\u0001\u001a\u00030Ò\u0001*\b0Ó\u0001j\u0003`Ô\u00012\u0006\u0010e\u001a\u00020fH\u0002R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Ù\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "markGenerated", "", "(Lcom/intellij/openapi/project/Project;Z)V", "eventSystemEnabled", "(Lcom/intellij/openapi/project/Project;ZZ)V", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/psi/KtElement;Z)V", "context", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/openapi/project/Project;ZLcom/intellij/psi/PsiElement;Z)V", "creareDelegatedSuperTypeEntry", "Lorg/jetbrains/kotlin/psi/KtConstructorDelegationCall;", "text", "", "createAnalyzableFile", "Lorg/jetbrains/kotlin/psi/KtFile;", PsiTreeChangeEvent.PROP_FILE_NAME, "contextToAnalyzeIn", "createAnnotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "createAnonymousInitializer", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "createArgument", "Lorg/jetbrains/kotlin/psi/KtValueArgument;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "isSpread", "reformat", "createBlock", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "bodyText", "createBlockCodeFragment", "Lorg/jetbrains/kotlin/psi/KtBlockCodeFragment;", "createBlockStringTemplateEntry", "Lorg/jetbrains/kotlin/psi/KtStringTemplateEntryWithExpression;", "createCallArguments", "Lorg/jetbrains/kotlin/psi/KtValueArgumentList;", "createCallableReferenceExpression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "createClass", "Lorg/jetbrains/kotlin/psi/KtClass;", "createClassKeyword", "createColon", "createComma", "createComment", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiComment;", "createCompanionObject", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "createConstructorKeyword", "createDeclaration", "TDeclaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/psi/KtDeclaration;", "createDestructuringDeclaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "createDestructuringParameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "createDot", "createEQ", "createEmptyBody", "createEmptyClassBody", "Lorg/jetbrains/kotlin/psi/KtClassBody;", "createEmptyModifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "createEnumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "createEnumEntryInitializerList", "Lorg/jetbrains/kotlin/psi/KtInitializerList;", "createExpression", "createExpressionCodeFragment", "Lorg/jetbrains/kotlin/psi/KtExpressionCodeFragment;", "createExpressionIfPossible", "createFile", "createFileAnnotation", "annotationText", "createFileAnnotationListWithAnnotation", "Lorg/jetbrains/kotlin/psi/KtFileAnnotationList;", "createFileWithLightClassSupport", "createFunction", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "funDecl", "createFunctionTypeParameter", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "createFunctionTypeReceiver", "Lorg/jetbrains/kotlin/psi/KtFunctionTypeReceiver;", "createIdentifier", "createIf", "Lorg/jetbrains/kotlin/psi/KtIfExpression;", "condition", "thenExpr", "elseExpr", "createImportDirective", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "importPath", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "createImportDirectives", "", UncompressedFiles.NAME_paths, "", "createLabeledExpression", "Lorg/jetbrains/kotlin/psi/KtLabeledExpression;", "labelName", "createLambdaExpression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "parameters", "body", "createLambdaParameterList", "Lorg/jetbrains/kotlin/psi/KtParameterList;", "createLambdaParameterListIfAny", "createLiteralStringTemplateEntry", "Lorg/jetbrains/kotlin/psi/KtLiteralStringTemplateEntry;", "literal", "createLoopParameter", "createModifier", "modifier", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "createModifierList", "createNameIdentifier", "createNameIdentifierIfPossible", "createNewLine", "lineBreaks", "", "createObject", "createOperationName", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "createPackageDirective", "Lorg/jetbrains/kotlin/psi/KtPackageDirective;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "createPackageDirectiveIfNeeded", "createParameter", "createParameterList", "createPhysicalFile", "createPrimaryConstructor", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "createPrimaryConstructorWithModifiers", "modifiers", "createProperty", "Lorg/jetbrains/kotlin/psi/KtProperty;", ModuleXmlParser.TYPE, "isVar", "initializer", "createPropertyDelegate", "Lorg/jetbrains/kotlin/psi/KtPropertyDelegate;", "createPropertyGetter", "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "createPropertySetter", "createSecondaryConstructor", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "decl", "createSemicolon", "createSimpleName", "createSimpleNameStringTemplateEntry", "Lorg/jetbrains/kotlin/psi/KtSimpleNameStringTemplateEntry;", "createSingleStatementBlock", "statement", "prevComment", "nextComment", "createStar", "createStringTemplate", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", FirebaseAnalytics.Param.CONTENT, "createSuperTypeCallEntry", "Lorg/jetbrains/kotlin/psi/KtSuperTypeCallEntry;", "createSuperTypeEntry", "Lorg/jetbrains/kotlin/psi/KtSuperTypeEntry;", "createThisExpression", "Lorg/jetbrains/kotlin/psi/KtThisExpression;", "qualifier", "createType", "typeElement", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "createTypeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "typeParameters", "createTypeArgument", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "createTypeArguments", "Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;", "createTypeCodeFragment", "Lorg/jetbrains/kotlin/psi/KtTypeCodeFragment;", "createTypeIfPossible", "createTypeParameter", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "createTypeParameterList", "Lorg/jetbrains/kotlin/psi/KtTypeParameterList;", "createValKeyword", "createVarKeyword", "createWhenCondition", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "conditionText", "createWhenEntry", "Lorg/jetbrains/kotlin/psi/KtWhenEntry;", "entryText", "createWhiteSpace", "createWhitespaceAndArrow", "Lkotlin/Pair;", "doCreateExpression", "doCreateFile", "wrapInABlockWrapper", "appendImport", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "BlockWrapper", "CallableBuilder", "ClassHeaderBuilder", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtPsiFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final PsiElement context;
    private final boolean eventSystemEnabled;
    private final boolean markGenerated;
    private final Project project;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KtPsiFactory(Project project) {
        this(project, false, 2, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(project, "project");
    }

    public /* synthetic */ KtPsiFactory(Project project, boolean z, PsiElement psiElement, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(project, z, psiElement, z2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated(message = "Use 'KtPsiFactory(project, markGenerated)' or 'KtPsiFactory.contextual(context, markGenerated)' instead")
    public KtPsiFactory(KtElement element) {
        this(element, false, 2, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(element, "element");
    }

    @JvmStatic
    public static final KtPsiFactory contextual(PsiElement psiElement) {
        return INSTANCE.contextual(psiElement);
    }

    @JvmStatic
    public static final KtPsiFactory contextual(PsiElement psiElement, boolean z) {
        return INSTANCE.contextual(psiElement, z);
    }

    private KtPsiFactory(Project project, boolean z, PsiElement psiElement, boolean z2) {
        this.project = project;
        this.markGenerated = z;
        this.context = psiElement;
        this.eventSystemEnabled = z2;
    }

    /* JADX INFO: compiled from: KtPsiFactory.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory$Companion;", "", "()V", "contextual", "Lorg/jetbrains/kotlin/psi/KtPsiFactory;", "context", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "markGenerated", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final KtPsiFactory contextual(PsiElement context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return contextual$default(this, context, false, 2, null);
        }

        private Companion() {
        }

        public static /* synthetic */ KtPsiFactory contextual$default(Companion companion, PsiElement psiElement, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            return companion.contextual(psiElement, z);
        }

        @JvmStatic
        public final KtPsiFactory contextual(PsiElement context, boolean markGenerated) {
            Intrinsics.checkNotNullParameter(context, "context");
            Project project = context.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            return new KtPsiFactory(project, markGenerated, context, false, (DefaultConstructorMarker) null);
        }
    }

    public /* synthetic */ KtPsiFactory(Project project, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(project, (i & 2) != 0 ? true : z);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KtPsiFactory(Project project, boolean z) {
        this(project, z, (PsiElement) null, false);
        Intrinsics.checkNotNullParameter(project, "project");
    }

    public /* synthetic */ KtPsiFactory(Project project, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(project, (i & 2) != 0 ? true : z, z2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KtPsiFactory(Project project, boolean z, boolean z2) {
        this(project, z, (PsiElement) null, z2);
        Intrinsics.checkNotNullParameter(project, "project");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @Deprecated(message = "Use 'KtPsiFactory(project, markGenerated)' or 'KtPsiFactory.contextual(context, markGenerated)' instead")
    public KtPsiFactory(KtElement element, boolean z) {
        Intrinsics.checkNotNullParameter(element, "element");
        Project project = element.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        this(project, z, (PsiElement) null, false);
    }

    public /* synthetic */ KtPsiFactory(KtElement ktElement, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktElement, (i & 2) != 0 ? true : z);
    }

    public final PsiElement createValKeyword() {
        PsiElement valOrVarKeyword = createProperty("val x = 1").getValOrVarKeyword();
        Intrinsics.checkNotNullExpressionValue(valOrVarKeyword, "getValOrVarKeyword(...)");
        return valOrVarKeyword;
    }

    public final PsiElement createVarKeyword() {
        PsiElement valOrVarKeyword = createProperty("var x = 1").getValOrVarKeyword();
        Intrinsics.checkNotNullExpressionValue(valOrVarKeyword, "getValOrVarKeyword(...)");
        return valOrVarKeyword;
    }

    private final KtExpression doCreateExpression(String text) {
        return createProperty("val x =\n" + text).getInitializer();
    }

    public final KtExpression createExpression(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtExpression ktExpressionDoCreateExpression = doCreateExpression(text);
        if (ktExpressionDoCreateExpression == null) {
            throw new IllegalStateException(("Failed to create expression from text: '" + text + '\'').toString());
        }
        Intrinsics.areEqual(ktExpressionDoCreateExpression.getText(), text);
        return ktExpressionDoCreateExpression;
    }

    public final KtExpression createExpressionIfPossible(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        try {
            KtExpression ktExpressionDoCreateExpression = doCreateExpression(text);
            if (ktExpressionDoCreateExpression != null && Intrinsics.areEqual(ktExpressionDoCreateExpression.getText(), text)) {
                return ktExpressionDoCreateExpression;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public final KtThisExpression createThisExpression() {
        KtExpression ktExpressionCreateExpression = createExpression("this.x");
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtQualifiedExpression");
        KtExpression receiverExpression = ((KtQualifiedExpression) ktExpressionCreateExpression).getReceiverExpression();
        Intrinsics.checkNotNull(receiverExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtThisExpression");
        return (KtThisExpression) receiverExpression;
    }

    public final KtThisExpression createThisExpression(String qualifier) {
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        KtExpression ktExpressionCreateExpression = createExpression("this@" + qualifier + ".x");
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtQualifiedExpression");
        KtExpression receiverExpression = ((KtQualifiedExpression) ktExpressionCreateExpression).getReceiverExpression();
        Intrinsics.checkNotNull(receiverExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtThisExpression");
        return (KtThisExpression) receiverExpression;
    }

    public final KtValueArgumentList createCallArguments(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtExpression initializer = createProperty("val x = foo " + text).getInitializer();
        Intrinsics.checkNotNull(initializer, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtCallExpression");
        KtValueArgumentList valueArgumentList = ((KtCallExpression) initializer).getValueArgumentList();
        Intrinsics.checkNotNull(valueArgumentList);
        return valueArgumentList;
    }

    public final KtTypeArgumentList createTypeArguments(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtExpression initializer = createProperty("val x = foo" + text + "()").getInitializer();
        Intrinsics.checkNotNull(initializer, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtCallExpression");
        KtTypeArgumentList typeArgumentList = ((KtCallExpression) initializer).getTypeArgumentList();
        Intrinsics.checkNotNull(typeArgumentList);
        return typeArgumentList;
    }

    public final KtTypeProjection createTypeArgument(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        List<KtTypeProjection> arguments = createTypeArguments("<" + text + '>').getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        return (KtTypeProjection) CollectionsKt.first((List) arguments);
    }

    public final KtTypeReference createType(String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        KtTypeReference ktTypeReferenceCreateTypeIfPossible = createTypeIfPossible(type);
        if (ktTypeReferenceCreateTypeIfPossible == null || !Intrinsics.areEqual(ktTypeReferenceCreateTypeIfPossible.getText(), type)) {
            throw new IllegalArgumentException("Incorrect type: " + type);
        }
        return ktTypeReferenceCreateTypeIfPossible;
    }

    public final KtTypeReference createType(KtTypeElement typeElement) {
        Intrinsics.checkNotNullParameter(typeElement, "typeElement");
        KtTypeReference ktTypeReferenceCreateType = createType(SpriteActivity.EXTRA_X_TRANSFORM);
        KtTypeElement typeElement2 = ktTypeReferenceCreateType.getTypeElement();
        Intrinsics.checkNotNull(typeElement2);
        typeElement2.replace(typeElement);
        return ktTypeReferenceCreateType;
    }

    public final KtTypeReference createTypeIfPossible(String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        KtTypeReference ktTypeReferenceMo7396getTypeReference = createProperty("val x : " + type).mo7396getTypeReference();
        if (Intrinsics.areEqual(ktTypeReferenceMo7396getTypeReference != null ? ktTypeReferenceMo7396getTypeReference.getText() : null, type)) {
            return ktTypeReferenceMo7396getTypeReference;
        }
        return null;
    }

    public final KtFunctionTypeReceiver createFunctionTypeReceiver(KtTypeReference typeReference) {
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        KtTypeElement typeElement = createType("A.() -> B").getTypeElement();
        Intrinsics.checkNotNull(typeElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFunctionType");
        KtFunctionTypeReceiver receiver = ((KtFunctionType) typeElement).getReceiver();
        Intrinsics.checkNotNull(receiver);
        receiver.getTypeReference().replace(typeReference);
        return receiver;
    }

    public final KtParameter createFunctionTypeParameter(KtTypeReference typeReference) {
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        KtTypeElement typeElement = createType("(A) -> B").getTypeElement();
        Intrinsics.checkNotNull(typeElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFunctionType");
        List<KtParameter> parameters = ((KtFunctionType) typeElement).getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) parameters);
        KtParameter ktParameter = (KtParameter) objFirst;
        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
        Intrinsics.checkNotNull(ktTypeReferenceMo7396getTypeReference);
        ktTypeReferenceMo7396getTypeReference.replace(typeReference);
        Intrinsics.checkNotNullExpressionValue(objFirst, "apply(...)");
        return ktParameter;
    }

    public final KtTypeAlias createTypeAlias(String name2, List<String> typeParameters, KtTypeElement typeElement) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(typeElement, "typeElement");
        KtTypeAlias ktTypeAliasCreateTypeAlias = createTypeAlias(name2, typeParameters, SpriteActivity.EXTRA_X_TRANSFORM);
        KtTypeReference typeReference = ktTypeAliasCreateTypeAlias.getTypeReference();
        Intrinsics.checkNotNull(typeReference);
        typeReference.replace(createType(typeElement));
        return ktTypeAliasCreateTypeAlias;
    }

    public final KtTypeAlias createTypeAlias(String name2, List<String> typeParameters, String body) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(body, "body");
        return (KtTypeAlias) createDeclaration("typealias " + name2 + (!typeParameters.isEmpty() ? CollectionsKt.joinToString$default(typeParameters, null, "<", ">", 0, null, null, 57, null) : "") + " = " + body);
    }

    public final PsiElement createStar() {
        PsiElement psiElementFindElementAt = createType("List<*>").findElementAt(5);
        Intrinsics.checkNotNull(psiElementFindElementAt);
        return psiElementFindElementAt;
    }

    public final PsiElement createComma() {
        PsiElement psiElementFindElementAt = createType("T<X, Y>").findElementAt(3);
        Intrinsics.checkNotNull(psiElementFindElementAt);
        return psiElementFindElementAt;
    }

    public final PsiElement createDot() {
        PsiElement psiElementFindElementAt = createType("T.(X)").findElementAt(1);
        Intrinsics.checkNotNull(psiElementFindElementAt);
        return psiElementFindElementAt;
    }

    public final PsiElement createColon() {
        PsiElement psiElementFindElementAt = createProperty("val x: Int").findElementAt(5);
        Intrinsics.checkNotNull(psiElementFindElementAt);
        return psiElementFindElementAt;
    }

    public final PsiElement createEQ() {
        PsiElement psiElementMo7390getEqualsToken = createFunction("fun foo() = foo").mo7390getEqualsToken();
        Intrinsics.checkNotNull(psiElementMo7390getEqualsToken);
        return psiElementMo7390getEqualsToken;
    }

    public final PsiElement createSemicolon() {
        PsiElement psiElementFindElementAt = createProperty("val x: Int;").findElementAt(10);
        Intrinsics.checkNotNull(psiElementFindElementAt);
        return psiElementFindElementAt;
    }

    public final Pair<PsiElement, PsiElement> createWhitespaceAndArrow() {
        KtTypeElement typeElement = createType("() -> Int").getTypeElement();
        Intrinsics.checkNotNull(typeElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFunctionType");
        KtFunctionType ktFunctionType = (KtFunctionType) typeElement;
        PsiElement psiElementFindElementAt = ktFunctionType.findElementAt(2);
        Intrinsics.checkNotNull(psiElementFindElementAt);
        PsiElement psiElementFindElementAt2 = ktFunctionType.findElementAt(3);
        Intrinsics.checkNotNull(psiElementFindElementAt2);
        return new Pair<>(psiElementFindElementAt, psiElementFindElementAt2);
    }

    public final PsiElement createWhiteSpace() {
        return createWhiteSpace(" ");
    }

    public final PsiElement createWhiteSpace(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        PsiElement psiElementFindElementAt = createProperty("val" + text + "x: Int").findElementAt(3);
        Intrinsics.checkNotNull(psiElementFindElementAt);
        return psiElementFindElementAt;
    }

    public final PsiElement createNewLine() {
        return createWhiteSpace("\n ");
    }

    public final PsiElement createNewLine(int lineBreaks) {
        return createWhiteSpace(StringsKt.repeat("\n", lineBreaks));
    }

    public final KtClass createClass(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return (KtClass) createDeclaration(text);
    }

    public final KtObjectDeclaration createObject(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return (KtObjectDeclaration) createDeclaration(text);
    }

    public final KtObjectDeclaration createCompanionObject() {
        return createCompanionObject("companion object {\n}");
    }

    public final KtObjectDeclaration createCompanionObject(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return (KtObjectDeclaration) CollectionsKt.first((List) createClass("class A {\n " + text + "\n}").getCompanionObjects());
    }

    public final KtAnnotationEntry createFileAnnotation(String annotationText) {
        Intrinsics.checkNotNullParameter(annotationText, "annotationText");
        List<KtAnnotationEntry> annotationEntries = createFileAnnotationListWithAnnotation(annotationText).getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) annotationEntries);
        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
        return (KtAnnotationEntry) objFirst;
    }

    public final KtFileAnnotationList createFileAnnotationListWithAnnotation(String annotationText) {
        Intrinsics.checkNotNullParameter(annotationText, "annotationText");
        KtFileAnnotationList fileAnnotationList = createFile("@file:" + annotationText).getFileAnnotationList();
        Intrinsics.checkNotNull(fileAnnotationList);
        return fileAnnotationList;
    }

    public final KtFile createFile(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return createFile("dummy.kt", text);
    }

    private final KtFile doCreateFile(String fileName, String text) {
        PsiFile psiFileCreateFileFromText = PsiFileFactory.getInstance(this.project).createFileFromText(fileName, KotlinFileType.INSTANCE, text, LocalTimeCounter.currentTime(), this.eventSystemEnabled, this.markGenerated);
        Intrinsics.checkNotNull(psiFileCreateFileFromText, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
        return (KtFile) psiFileCreateFileFromText;
    }

    public final KtFile createFile(String fileName, String text) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(text, "text");
        KtFile ktFileDoCreateFile = doCreateFile(fileName, text);
        PsiElement psiElement = this.context;
        if (psiElement != null) {
            KtPsiFactoryKt.setAnalysisContext(ktFileDoCreateFile, psiElement);
        } else {
            KtPsiFactoryKt.setDoNotAnalyze(ktFileDoCreateFile, "This file was created by KtPsiFactory and should not be analyzed\nUse createAnalyzableFile to create file that can be analyzed\n");
        }
        return ktFileDoCreateFile;
    }

    @Deprecated(message = "Call 'createFile()' on a contextual 'KtPsiFactory' instead")
    public final KtFile createAnalyzableFile(String fileName, String text, PsiElement contextToAnalyzeIn) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(contextToAnalyzeIn, "contextToAnalyzeIn");
        KtFile ktFileDoCreateFile = doCreateFile(fileName, text);
        KtPsiFactoryKt.setAnalysisContext(ktFileDoCreateFile, contextToAnalyzeIn);
        return ktFileDoCreateFile;
    }

    @Deprecated(message = "Call 'createPhysicalFile() on a contextual 'KtPsiFactory' instead")
    public final KtFile createFileWithLightClassSupport(String fileName, String text, PsiElement contextToAnalyzeIn) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(contextToAnalyzeIn, "contextToAnalyzeIn");
        KtFile ktFileCreatePhysicalFile = createPhysicalFile(fileName, text);
        KtPsiFactoryKt.setAnalysisContext(ktFileCreatePhysicalFile, contextToAnalyzeIn);
        return ktFileCreatePhysicalFile;
    }

    public final KtFile createPhysicalFile(String fileName, String text) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(text, "text");
        PsiFile psiFileCreateFileFromText = PsiFileFactory.getInstance(this.project).createFileFromText(fileName, (FileType) KotlinFileType.INSTANCE, (CharSequence) text, LocalTimeCounter.currentTime(), true);
        Intrinsics.checkNotNull(psiFileCreateFileFromText, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
        KtFile ktFile = (KtFile) psiFileCreateFileFromText;
        KtPsiFactoryKt.setAnalysisContext(ktFile, this.context);
        return ktFile;
    }

    public final KtProperty createProperty(String modifiers, String name2, String type, boolean isVar, String initializer) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return createProperty((modifiers + StringUtil.SPACE) + (isVar ? " var " : " val ") + name2 + (type != null ? ":" + type : "") + (initializer != null ? " = " + initializer : ""));
    }

    public final KtProperty createProperty(String name2, String type, boolean isVar, String initializer) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return createProperty(null, name2, type, isVar, initializer);
    }

    public final KtProperty createProperty(String name2, String type, boolean isVar) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return createProperty(name2, type, isVar, null);
    }

    public final KtProperty createProperty(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return (KtProperty) createDeclaration(text);
    }

    public final KtPropertyAccessor createPropertyGetter(KtExpression expression) {
        KtProperty ktPropertyCreateProperty;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof KtBlockExpression) {
            ktPropertyCreateProperty = createProperty("val x get() {\nreturn 1\n}");
        } else {
            ktPropertyCreateProperty = createProperty("val x get() = 1");
        }
        KtPropertyAccessor getter = ktPropertyCreateProperty.getGetter();
        Intrinsics.checkNotNull(getter);
        KtExpression bodyExpression = getter.getBodyExpression();
        Intrinsics.checkNotNull(bodyExpression);
        bodyExpression.replace(expression);
        return getter;
    }

    public final KtPropertyAccessor createPropertySetter(KtExpression expression) {
        KtProperty ktPropertyCreateProperty;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof KtBlockExpression) {
            ktPropertyCreateProperty = createProperty("val x get() = 1\nset(value) {\n field = value\n }");
        } else {
            ktPropertyCreateProperty = createProperty("val x get() = 1\nset(value) = TODO()");
        }
        KtPropertyAccessor setter = ktPropertyCreateProperty.getSetter();
        Intrinsics.checkNotNull(setter);
        KtExpression bodyExpression = setter.getBodyExpression();
        Intrinsics.checkNotNull(bodyExpression);
        bodyExpression.replace(expression);
        return setter;
    }

    public final KtPropertyDelegate createPropertyDelegate(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtPropertyDelegate delegate = createProperty("val x by lazy { 1 }").getDelegate();
        Intrinsics.checkNotNull(delegate);
        KtExpression expression2 = delegate.getExpression();
        Intrinsics.checkNotNull(expression2);
        expression2.replace(expression);
        return delegate;
    }

    public final KtDestructuringDeclaration createDestructuringDeclaration(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtBlockExpression bodyBlockExpression = createFunction("fun foo() {" + text + '}').getBodyBlockExpression();
        Intrinsics.checkNotNull(bodyBlockExpression);
        List<KtExpression> statements = bodyBlockExpression.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) statements);
        Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtDestructuringDeclaration");
        return (KtDestructuringDeclaration) objFirst;
    }

    public final KtParameter createDestructuringParameter(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtExpression bodyExpression = createFunction("fun foo() = { " + text + " -> }").getBodyExpression();
        Intrinsics.checkNotNull(bodyExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtLambdaExpression");
        List<KtParameter> valueParameters = ((KtLambdaExpression) bodyExpression).getFunctionLiteral().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) valueParameters);
        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
        return (KtParameter) objFirst;
    }

    public final <TDeclaration extends KtDeclaration> TDeclaration createDeclaration(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        List<KtDeclaration> declarations = createFile(text).getDeclarations();
        if (declarations.size() == 1) {
            Object objFirst = CollectionsKt.first((List<? extends Object>) declarations);
            Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type TDeclaration of org.jetbrains.kotlin.psi.KtPsiFactory.createDeclaration");
            return (TDeclaration) objFirst;
        }
        KotlinExceptionWithAttachments kotlinExceptionWithAttachments = new KotlinExceptionWithAttachments("unexpected " + declarations.size() + " declarations");
        kotlinExceptionWithAttachments.withAttachment("text.kt", (Object) text);
        for (IndexedValue indexedValue : CollectionsKt.withIndex(declarations)) {
            kotlinExceptionWithAttachments.withPsiAttachment("declaration" + indexedValue.getIndex() + KotlinFileType.DOT_DEFAULT_EXTENSION, (PsiElement) indexedValue.getValue());
        }
        throw kotlinExceptionWithAttachments;
    }

    public final PsiElement createNameIdentifier(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiElement psiElementCreateNameIdentifierIfPossible = createNameIdentifierIfPossible(name2);
        Intrinsics.checkNotNull(psiElementCreateNameIdentifierIfPossible);
        return psiElementCreateNameIdentifierIfPossible;
    }

    public final PsiElement createNameIdentifierIfPossible(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return createProperty(name2, null, false).mo7392getNameIdentifier();
    }

    public final KtSimpleNameExpression createSimpleName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        KtExpression initializer = createProperty(name2, null, false, name2).getInitializer();
        Intrinsics.checkNotNull(initializer, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSimpleNameExpression");
        return (KtSimpleNameExpression) initializer;
    }

    public final KtSimpleNameExpression createOperationName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        KtExpression ktExpressionCreateExpression = createExpression("0 " + name2 + " 0");
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtBinaryExpression");
        KtOperationReferenceExpression operationReference = ((KtBinaryExpression) ktExpressionCreateExpression).getOperationReference();
        Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
        return operationReference;
    }

    public final PsiElement createIdentifier(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiElement identifier = createSimpleName(name2).getIdentifier();
        Intrinsics.checkNotNull(identifier);
        return identifier;
    }

    public final KtNamedFunction createFunction(String funDecl) {
        Intrinsics.checkNotNullParameter(funDecl, "funDecl");
        return (KtNamedFunction) createDeclaration(funDecl);
    }

    public final KtCallableReferenceExpression createCallableReferenceExpression(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtExpression ktExpressionCreateExpression = createExpression(text);
        if (ktExpressionCreateExpression instanceof KtCallableReferenceExpression) {
            return (KtCallableReferenceExpression) ktExpressionCreateExpression;
        }
        return null;
    }

    public final KtSecondaryConstructor createSecondaryConstructor(String decl) {
        Intrinsics.checkNotNullParameter(decl, "decl");
        return (KtSecondaryConstructor) CollectionsKt.first((List) createClass("class Foo {\n " + decl + " \n}").getSecondaryConstructors());
    }

    public final KtModifierList createModifierList(KtModifierKeywordToken modifier) {
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        String value = modifier.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return createModifierList(value);
    }

    public final KtModifierList createModifierList(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtModifierList modifierList = createClass(text + " interface x").getModifierList();
        Intrinsics.checkNotNull(modifierList);
        return modifierList;
    }

    public final KtModifierList createEmptyModifierList() {
        KtModifierKeywordToken PRIVATE_KEYWORD = KtTokens.PRIVATE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PRIVATE_KEYWORD, "PRIVATE_KEYWORD");
        KtModifierList ktModifierListCreateModifierList = createModifierList(PRIVATE_KEYWORD);
        ktModifierListCreateModifierList.getFirstChild().delete();
        return ktModifierListCreateModifierList;
    }

    public final PsiElement createModifier(KtModifierKeywordToken modifier) {
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        String value = modifier.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        PsiElement modifier2 = createModifierList(value).getModifier(modifier);
        Intrinsics.checkNotNull(modifier2);
        return modifier2;
    }

    public final KtAnnotationEntry createAnnotationEntry(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtModifierList modifierList = createProperty(text + " val x").getModifierList();
        Intrinsics.checkNotNull(modifierList);
        List<KtAnnotationEntry> annotationEntries = modifierList.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) annotationEntries);
        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
        return (KtAnnotationEntry) objFirst;
    }

    public final KtBlockExpression createEmptyBody() {
        KtBlockExpression bodyBlockExpression = createFunction("fun foo() {}").getBodyBlockExpression();
        Intrinsics.checkNotNull(bodyBlockExpression);
        return bodyBlockExpression;
    }

    public final KtAnonymousInitializer createAnonymousInitializer() {
        return (KtAnonymousInitializer) CollectionsKt.first((List) createClass("class A { init {} }").getAnonymousInitializers());
    }

    public final KtClassBody createEmptyClassBody() {
        KtClassBody body = createClass("class A(){}").getBody();
        Intrinsics.checkNotNull(body);
        return body;
    }

    public final KtParameter createParameter(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return (KtParameter) CollectionsKt.first((List) createClass("class A(" + text + ')').getPrimaryConstructorParameters());
    }

    public final KtParameter createLoopParameter(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtExpression ktExpressionCreateExpression = createExpression("for (" + text + " in list) {}");
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtForExpression");
        KtParameter loopParameter = ((KtForExpression) ktExpressionCreateExpression).getLoopParameter();
        Intrinsics.checkNotNull(loopParameter);
        return loopParameter;
    }

    public final KtParameterList createParameterList(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtParameterList valueParameterList = createFunction("fun foo" + text + "{}").getValueParameterList();
        Intrinsics.checkNotNull(valueParameterList);
        return valueParameterList;
    }

    public final KtTypeParameterList createTypeParameterList(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtTypeParameterList typeParameterList = createClass("class Foo" + text).mo7395getTypeParameterList();
        Intrinsics.checkNotNull(typeParameterList);
        return typeParameterList;
    }

    public final KtTypeParameter createTypeParameter(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        List<KtTypeParameter> parameters = createTypeParameterList("<" + text + '>').getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) parameters);
        Intrinsics.checkNotNull(objFirst);
        return (KtTypeParameter) objFirst;
    }

    public final KtParameterList createLambdaParameterListIfAny(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return createLambdaExpression(text, "0").getFunctionLiteral().getValueParameterList();
    }

    public final KtParameterList createLambdaParameterList(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtParameterList ktParameterListCreateLambdaParameterListIfAny = createLambdaParameterListIfAny(text);
        Intrinsics.checkNotNull(ktParameterListCreateLambdaParameterListIfAny);
        return ktParameterListCreateLambdaParameterListIfAny;
    }

    public final KtLambdaExpression createLambdaExpression(String parameters, String body) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(body, "body");
        KtExpression ktExpressionCreateExpression = parameters.length() > 0 ? createExpression("{ " + parameters + " -> " + body + " }") : createExpression("{ " + body + " }");
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtLambdaExpression");
        return (KtLambdaExpression) ktExpressionCreateExpression;
    }

    public final KtEnumEntry createEnumEntry(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtDeclaration ktDeclaration = ((KtClass) createDeclaration("enum class E {" + text + '}')).getDeclarations().get(0);
        Intrinsics.checkNotNull(ktDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtEnumEntry");
        return (KtEnumEntry) ktDeclaration;
    }

    public final KtInitializerList createEnumEntryInitializerList() {
        KtInitializerList initializerList = createEnumEntry("Entry()").getInitializerList();
        Intrinsics.checkNotNull(initializerList);
        return initializerList;
    }

    public final KtWhenEntry createWhenEntry(String entryText) {
        Intrinsics.checkNotNullParameter(entryText, "entryText");
        KtWhenEntry ktWhenEntry = (KtWhenEntry) PsiTreeUtil.findChildOfType(createFunction("fun foo() { when(12) { " + entryText + " } }"), KtWhenEntry.class);
        Intrinsics.checkNotNull(ktWhenEntry);
        Intrinsics.areEqual(entryText, ktWhenEntry.getText());
        return ktWhenEntry;
    }

    public final KtWhenCondition createWhenCondition(String conditionText) {
        Intrinsics.checkNotNullParameter(conditionText, "conditionText");
        KtWhenCondition ktWhenCondition = createWhenEntry(conditionText + " -> {}").getConditions()[0];
        Intrinsics.checkNotNullExpressionValue(ktWhenCondition, "get(...)");
        return ktWhenCondition;
    }

    public final KtStringTemplateEntryWithExpression createBlockStringTemplateEntry(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtExpression ktExpressionCreateExpressionByPattern = CreateByPatternKt.createExpressionByPattern(this, "\"$${$0}\"", new Object[]{expression}, false);
        Intrinsics.checkNotNull(ktExpressionCreateExpressionByPattern, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtStringTemplateExpression");
        KtStringTemplateEntry ktStringTemplateEntry = ((KtStringTemplateExpression) ktExpressionCreateExpressionByPattern).getEntries()[0];
        Intrinsics.checkNotNull(ktStringTemplateEntry, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression");
        return (KtStringTemplateEntryWithExpression) ktStringTemplateEntry;
    }

    public final KtSimpleNameStringTemplateEntry createSimpleNameStringTemplateEntry(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        KtExpression ktExpressionCreateExpression = createExpression("\"$" + name2 + '\"');
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtStringTemplateExpression");
        KtStringTemplateEntry ktStringTemplateEntry = ((KtStringTemplateExpression) ktExpressionCreateExpression).getEntries()[0];
        Intrinsics.checkNotNull(ktStringTemplateEntry, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSimpleNameStringTemplateEntry");
        return (KtSimpleNameStringTemplateEntry) ktStringTemplateEntry;
    }

    public final KtLiteralStringTemplateEntry createLiteralStringTemplateEntry(String literal) {
        Intrinsics.checkNotNullParameter(literal, "literal");
        KtExpression ktExpressionCreateExpression = createExpression("\"" + literal + '\"');
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtStringTemplateExpression");
        KtStringTemplateEntry ktStringTemplateEntry = ((KtStringTemplateExpression) ktExpressionCreateExpression).getEntries()[0];
        Intrinsics.checkNotNull(ktStringTemplateEntry, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry");
        return (KtLiteralStringTemplateEntry) ktStringTemplateEntry;
    }

    public final KtStringTemplateExpression createStringTemplate(String content) {
        Intrinsics.checkNotNullParameter(content, "content");
        KtExpression ktExpressionCreateExpression = createExpression("\"" + content + '\"');
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtStringTemplateExpression");
        return (KtStringTemplateExpression) ktExpressionCreateExpression;
    }

    public final KtPackageDirective createPackageDirective(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        KtPackageDirective packageDirective = createFile("package " + fqName.asString()).getPackageDirective();
        Intrinsics.checkNotNull(packageDirective);
        return packageDirective;
    }

    public final KtPackageDirective createPackageDirectiveIfNeeded(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (fqName.isRoot()) {
            return null;
        }
        return createPackageDirective(fqName);
    }

    public final KtImportDirective createImportDirective(ImportPath importPath) {
        Intrinsics.checkNotNullParameter(importPath, "importPath");
        if (importPath.getFqName().isRoot()) {
            throw new IllegalArgumentException("import path must not be empty");
        }
        StringBuilder sb = new StringBuilder();
        appendImport(sb, importPath);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return (KtImportDirective) CollectionsKt.first((List) createFile(string).getImportDirectives());
    }

    private final void appendImport(StringBuilder sb, ImportPath importPath) {
        if (importPath.getFqName().isRoot()) {
            throw new IllegalArgumentException("import path must not be empty");
        }
        sb.append("import ");
        sb.append(importPath.getPathStr());
        Name alias = importPath.getAlias();
        if (alias != null) {
            sb.append(" as ").append(alias.asString());
        }
    }

    @Deprecated(message = "function is not used in the kotlin plugin/compiler and will be removed soon")
    public final List<KtImportDirective> createImportDirectives(Collection<ImportPath> paths) {
        Intrinsics.checkNotNullParameter(paths, "paths");
        StringBuilder sb = new StringBuilder();
        Iterator<ImportPath> it = paths.iterator();
        while (it.hasNext()) {
            appendImport(sb, it.next());
            sb.append('\n');
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return createFile(string).getImportDirectives();
    }

    public final PsiElement createClassKeyword() {
        PsiElement classKeyword = createClass("class A").getClassKeyword();
        Intrinsics.checkNotNull(classKeyword);
        return classKeyword;
    }

    public static /* synthetic */ KtPrimaryConstructor createPrimaryConstructor$default(KtPsiFactory ktPsiFactory, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        return ktPsiFactory.createPrimaryConstructor(str);
    }

    public final KtPrimaryConstructor createPrimaryConstructor(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        KtPrimaryConstructor primaryConstructor = createClass(text.length() > 0 ? "class A " + text : "class A()").getPrimaryConstructor();
        Intrinsics.checkNotNull(primaryConstructor);
        return primaryConstructor;
    }

    public final KtPrimaryConstructor createPrimaryConstructorWithModifiers(String modifiers) {
        KtPrimaryConstructor ktPrimaryConstructorCreatePrimaryConstructor;
        return (modifiers == null || (ktPrimaryConstructorCreatePrimaryConstructor = createPrimaryConstructor(new StringBuilder().append(modifiers).append(" constructor()").toString())) == null) ? createPrimaryConstructor$default(this, null, 1, null) : ktPrimaryConstructorCreatePrimaryConstructor;
    }

    public final PsiElement createConstructorKeyword() {
        KtPrimaryConstructor primaryConstructor = createClass("class A constructor()").getPrimaryConstructor();
        Intrinsics.checkNotNull(primaryConstructor);
        PsiElement constructorKeyword = primaryConstructor.getConstructorKeyword();
        Intrinsics.checkNotNull(constructorKeyword);
        return constructorKeyword;
    }

    public final KtLabeledExpression createLabeledExpression(String labelName) {
        Intrinsics.checkNotNullParameter(labelName, "labelName");
        KtExpression ktExpressionCreateExpression = createExpression(labelName + "@ 1");
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtLabeledExpression");
        return (KtLabeledExpression) ktExpressionCreateExpression;
    }

    public final KtTypeCodeFragment createTypeCodeFragment(String text, PsiElement context) {
        Intrinsics.checkNotNullParameter(text, "text");
        return new KtTypeCodeFragment(this.project, "fragment.kt", text, context);
    }

    public final KtExpressionCodeFragment createExpressionCodeFragment(String text, PsiElement context) {
        Intrinsics.checkNotNullParameter(text, "text");
        return new KtExpressionCodeFragment(this.project, "fragment.kt", text, null, context);
    }

    public final KtBlockCodeFragment createBlockCodeFragment(String text, PsiElement context) {
        Intrinsics.checkNotNullParameter(text, "text");
        return new KtBlockCodeFragment(this.project, "fragment.kt", text, null, context);
    }

    public static /* synthetic */ KtIfExpression createIf$default(KtPsiFactory ktPsiFactory, KtExpression ktExpression, KtExpression ktExpression2, KtExpression ktExpression3, int i, Object obj) {
        if ((i & 4) != 0) {
            ktExpression3 = null;
        }
        return ktPsiFactory.createIf(ktExpression, ktExpression2, ktExpression3);
    }

    public final KtIfExpression createIf(KtExpression condition, KtExpression thenExpr, KtExpression elseExpr) {
        KtIfExpression ktIfExpressionCreateExpressionByPattern$default;
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(thenExpr, "thenExpr");
        if (elseExpr != null) {
            KtExpression ktExpressionCreateExpressionByPattern$default = CreateByPatternKt.createExpressionByPattern$default(this, "if ($0) $1 else $2", new Object[]{condition, thenExpr, elseExpr}, false, 4, null);
            Intrinsics.checkNotNull(ktExpressionCreateExpressionByPattern$default, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtIfExpression");
            ktIfExpressionCreateExpressionByPattern$default = (KtIfExpression) ktExpressionCreateExpressionByPattern$default;
        } else {
            ktIfExpressionCreateExpressionByPattern$default = CreateByPatternKt.createExpressionByPattern$default(this, "if ($0) $1", new Object[]{condition, thenExpr}, false, 4, null);
        }
        Intrinsics.checkNotNull(ktIfExpressionCreateExpressionByPattern$default, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtIfExpression");
        return (KtIfExpression) ktIfExpressionCreateExpressionByPattern$default;
    }

    public static /* synthetic */ KtValueArgument createArgument$default(KtPsiFactory ktPsiFactory, KtExpression ktExpression, Name name2, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            name2 = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = true;
        }
        return ktPsiFactory.createArgument(ktExpression, name2, z, z2);
    }

    public final KtValueArgument createArgument(final KtExpression expression, final Name name2, final boolean isSpread, final boolean reformat) {
        List<KtValueArgument> arguments = ((KtValueArgumentList) CreateByPatternKt.buildByPattern(new Function2<String, Object[], KtValueArgumentList>() { // from class: org.jetbrains.kotlin.psi.KtPsiFactory$createArgument$argumentList$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final KtValueArgumentList invoke(String pattern, Object[] args) {
                Intrinsics.checkNotNullParameter(pattern, "pattern");
                Intrinsics.checkNotNullParameter(args, "args");
                Object[] objArrCopyOf = Arrays.copyOf(args, args.length);
                boolean z = reformat;
                final KtPsiFactory ktPsiFactory = this;
                return (KtValueArgumentList) CreateByPatternKt.createByPattern(pattern, objArrCopyOf, z, new Function1<String, KtValueArgumentList>() { // from class: org.jetbrains.kotlin.psi.KtPsiFactory$createArgument$argumentList$1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final KtValueArgumentList invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return ktPsiFactory.createCallArguments(it);
                    }
                });
            }
        }, new Function1<BuilderByPattern<KtValueArgumentList>, Unit>() { // from class: org.jetbrains.kotlin.psi.KtPsiFactory$createArgument$argumentList$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BuilderByPattern<KtValueArgumentList> builderByPattern) {
                invoke2(builderByPattern);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BuilderByPattern<KtValueArgumentList> buildByPattern) {
                Intrinsics.checkNotNullParameter(buildByPattern, "$this$buildByPattern");
                buildByPattern.appendFixedText("(");
                Name name3 = name2;
                if (name3 != null) {
                    String strAsString = name3.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    if (KtPsiUtilKt.isIdentifier(strAsString)) {
                        buildByPattern.appendName(name2);
                    } else {
                        buildByPattern.appendFixedText("`" + strAsString + '`');
                    }
                    buildByPattern.appendFixedText(" = ");
                }
                if (isSpread) {
                    buildByPattern.appendFixedText("*");
                }
                buildByPattern.appendExpression(expression);
                buildByPattern.appendFixedText(Constant.AFTER_QUTO);
            }
        })).getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        Object objSingle = CollectionsKt.single((List<? extends Object>) arguments);
        Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
        return (KtValueArgument) objSingle;
    }

    public final KtValueArgument createArgument(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        List<KtValueArgument> arguments = createCallArguments("(" + text + ')').getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) arguments);
        Intrinsics.checkNotNull(objFirst);
        return (KtValueArgument) objFirst;
    }

    public final KtSuperTypeCallEntry createSuperTypeCallEntry(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        Object objFirst = CollectionsKt.first((List<? extends Object>) createClass("class A: " + text).getSuperTypeListEntries());
        Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSuperTypeCallEntry");
        return (KtSuperTypeCallEntry) objFirst;
    }

    public final KtSuperTypeEntry createSuperTypeEntry(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        Object objFirst = CollectionsKt.first((List<? extends Object>) createClass("class A: " + text).getSuperTypeListEntries());
        Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSuperTypeEntry");
        return (KtSuperTypeEntry) objFirst;
    }

    public final KtConstructorDelegationCall creareDelegatedSuperTypeEntry(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return ((KtSecondaryConstructor) CollectionsKt.first((List) createClass("class A { constructor()" + (text.length() == 0 ? "" : ": ") + text + " {}").getSecondaryConstructors())).getDelegationCall();
    }

    /* JADX INFO: compiled from: KtPsiFactory.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0006\u0010\r\u001a\u00020\fJ&\u0010\u000e\u001a\u00020\u00002\b\b\u0001\u0010\u000f\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\u00002\b\b\u0001\u0010\u0013\u001a\u00020\fJ\u0010\u0010\u000f\u001a\u00020\u00002\b\b\u0001\u0010\u000f\u001a\u00020\fJ\b\u0010\u0014\u001a\u00020\tH\u0002J#\u0010\u0015\u001a\u00020\t2\u001b\u0010\u0016\u001a\u0017\u0012\b\u0012\u00060\u0004j\u0002`\u0005\u0012\u0004\u0012\u00020\t0\u0017¢\u0006\u0002\b\u0018J\u0014\u0010\u0019\u001a\u00020\u00002\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u0014\u0010\u001a\u001a\u00020\u00002\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bR\u0012\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory$ClassHeaderBuilder;", "", "()V", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "state", "Lorg/jetbrains/kotlin/psi/KtPsiFactory$ClassHeaderBuilder$State;", "appendInAngleBrackets", "", CompoundEntry.NAME_values, "", "", "asString", "baseClass", "name", "typeArguments", "isInterface", "", "modifier", "placeKeyword", "transform", "f", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "typeConstraints", "typeParameters", "State", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClassHeaderBuilder {
        private final StringBuilder sb = new StringBuilder();
        private State state = State.MODIFIERS;

        /* JADX INFO: compiled from: KtPsiFactory.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory$ClassHeaderBuilder$State;", "", "(Ljava/lang/String;I)V", "MODIFIERS", "NAME", "TYPE_PARAMETERS", "BASE_CLASS", "TYPE_CONSTRAINTS", "DONE", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public enum State {
            MODIFIERS,
            NAME,
            TYPE_PARAMETERS,
            BASE_CLASS,
            TYPE_CONSTRAINTS,
            DONE;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<State> getEntries() {
                return $ENTRIES;
            }
        }

        public final ClassHeaderBuilder modifier(String modifier) {
            Intrinsics.checkNotNullParameter(modifier, "modifier");
            State state = State.MODIFIERS;
            this.sb.append(modifier);
            return this;
        }

        private final void placeKeyword() {
            State state = State.MODIFIERS;
            if (this.sb.length() > 0) {
                this.sb.append(" ");
            }
            this.sb.append(ExternalAnnotationProvider.CLASS_PREFIX);
            this.state = State.NAME;
        }

        public final ClassHeaderBuilder name(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            placeKeyword();
            this.sb.append(name2);
            this.state = State.TYPE_PARAMETERS;
            return this;
        }

        private final void appendInAngleBrackets(Collection<String> values) {
            if (values.isEmpty()) {
                return;
            }
            this.sb.append(CollectionsKt.joinToString$default(values, ", ", "<", ">", 0, null, null, 56, null));
        }

        public final ClassHeaderBuilder typeParameters(Collection<String> values) {
            Intrinsics.checkNotNullParameter(values, "values");
            State state = State.TYPE_PARAMETERS;
            appendInAngleBrackets(values);
            this.state = State.BASE_CLASS;
            return this;
        }

        public final ClassHeaderBuilder baseClass(String name2, Collection<String> typeArguments, boolean isInterface) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
            State state = State.BASE_CLASS;
            this.sb.append(" : " + name2);
            appendInAngleBrackets(typeArguments);
            if (!isInterface) {
                this.sb.append("()");
            }
            this.state = State.TYPE_CONSTRAINTS;
            return this;
        }

        public final ClassHeaderBuilder typeConstraints(Collection<String> values) {
            Intrinsics.checkNotNullParameter(values, "values");
            State state = State.TYPE_CONSTRAINTS;
            if (!values.isEmpty()) {
                this.sb.append(CollectionsKt.joinToString$default(values, ", ", " where ", "", -1, "", null, 32, null));
            }
            this.state = State.DONE;
            return this;
        }

        public final void transform(Function1<? super StringBuilder, Unit> f) {
            Intrinsics.checkNotNullParameter(f, "f");
            f.invoke(this.sb);
        }

        public final String asString() {
            if (this.state != State.DONE) {
                this.state = State.DONE;
            }
            String string = this.sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
    }

    /* JADX INFO: compiled from: KtPsiFactory.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\u0018\u0000 +2\u00020\u0001:\u0003+,-B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\u00002\b\b\u0001\u0010\r\u001a\u00020\u000bJ\u0012\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u001a\u0010\u0013\u001a\u00020\u00002\b\b\u0001\u0010\u0014\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0015\u001a\u00020\u00002\b\b\u0001\u0010\r\u001a\u00020\u000bJ\u0010\u0010\u0016\u001a\u00020\u00002\b\b\u0001\u0010\r\u001a\u00020\u000bJ\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u000bJ\u0010\u0010\u0018\u001a\u00020\u00002\b\b\u0003\u0010\u0018\u001a\u00020\u000bJ\u0006\u0010\u0019\u001a\u00020\u0000J&\u0010\u001a\u001a\u00020\u00002\b\b\u0001\u0010\u0018\u001a\u00020\u000b2\b\b\u0001\u0010\u001b\u001a\u00020\u000b2\n\b\u0003\u0010\u001c\u001a\u0004\u0018\u00010\u000bJ\b\u0010\u001d\u001a\u00020\u0012H\u0002J\u0010\u0010\u001e\u001a\u00020\u00002\b\b\u0001\u0010\u001f\u001a\u00020\u000bJ\u0010\u0010 \u001a\u00020\u00002\b\b\u0001\u0010\u001b\u001a\u00020\u000bJ\u0010\u0010!\u001a\u00020\u00002\b\b\u0001\u0010\"\u001a\u00020\u000bJ#\u0010#\u001a\u00020\u00122\u001b\u0010$\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\u00120%¢\u0006\u0002\b&J\u0014\u0010'\u001a\u00020\u00002\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000b0)J\u0016\u0010*\u001a\u00020\u00002\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000b0)R\u0012\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory$CallableBuilder;", "", "target", "Lorg/jetbrains/kotlin/psi/KtPsiFactory$CallableBuilder$Target;", "(Lorg/jetbrains/kotlin/psi/KtPsiFactory$CallableBuilder$Target;)V", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "state", "Lorg/jetbrains/kotlin/psi/KtPsiFactory$CallableBuilder$State;", "asString", "", "blockBody", "body", "bodyPrefix", "breakLine", "", "closeParams", "", "getterExpression", "expression", "initializer", "lazyBody", "modifier", "name", "noReturnType", "param", ModuleXmlParser.TYPE, "defaultValue", "placeKeyword", "receiver", "receiverType", "returnType", "superDelegation", "argumentList", "transform", "f", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "typeConstraints", CompoundEntry.NAME_values, "", "typeParams", "Companion", "State", "Target", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CallableBuilder {
        private static final String CONSTRUCTOR_NAME;

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final StringBuilder sb;
        private State state;
        private final Target target;

        /* JADX INFO: compiled from: KtPsiFactory.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory$CallableBuilder$State;", "", "(Ljava/lang/String;I)V", "MODIFIERS", "NAME", "RECEIVER", "FIRST_PARAM", "REST_PARAMS", "TYPE_CONSTRAINTS", "BODY", "DONE", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public enum State {
            MODIFIERS,
            NAME,
            RECEIVER,
            FIRST_PARAM,
            REST_PARAMS,
            TYPE_CONSTRAINTS,
            BODY,
            DONE;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<State> getEntries() {
                return $ENTRIES;
            }
        }

        /* JADX INFO: compiled from: KtPsiFactory.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory$CallableBuilder$Target;", "", "(Ljava/lang/String;I)V", "FUNCTION", "CONSTRUCTOR", "READ_ONLY_PROPERTY", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public enum Target {
            FUNCTION,
            CONSTRUCTOR,
            READ_ONLY_PROPERTY;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<Target> getEntries() {
                return $ENTRIES;
            }
        }

        /* JADX INFO: compiled from: KtPsiFactory.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Target.values().length];
                try {
                    iArr[Target.FUNCTION.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Target.CONSTRUCTOR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Target.READ_ONLY_PROPERTY.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public CallableBuilder(Target target) {
            Intrinsics.checkNotNullParameter(target, "target");
            this.target = target;
            this.sb = new StringBuilder();
            this.state = State.MODIFIERS;
        }

        /* JADX INFO: compiled from: KtPsiFactory.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory$CallableBuilder$Companion;", "", "()V", "CONSTRUCTOR_NAME", "", "getCONSTRUCTOR_NAME", "()Ljava/lang/String;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final String getCONSTRUCTOR_NAME() {
                return CallableBuilder.CONSTRUCTOR_NAME;
            }
        }

        static {
            String value = KtTokens.CONSTRUCTOR_KEYWORD.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            CONSTRUCTOR_NAME = value;
        }

        private final void closeParams() {
            if (this.target == Target.FUNCTION || this.target == Target.CONSTRUCTOR) {
                if (this.state != State.FIRST_PARAM) {
                    State state = this.state;
                    State state2 = State.REST_PARAMS;
                }
                this.sb.append(Constant.AFTER_QUTO);
            }
            this.state = State.TYPE_CONSTRAINTS;
        }

        private final void placeKeyword() {
            String str;
            State state = State.MODIFIERS;
            if (this.sb.length() > 0 && !StringsKt.endsWith$default((CharSequence) this.sb, (CharSequence) " ", false, 2, (Object) null)) {
                this.sb.append(" ");
            }
            int i = WhenMappings.$EnumSwitchMapping$0[this.target.ordinal()];
            if (i == 1) {
                str = "fun";
            } else if (i == 2) {
                str = "";
            } else {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                str = "val";
            }
            this.sb.append(str.concat(" "));
            this.state = State.RECEIVER;
        }

        private final String bodyPrefix(boolean breakLine) {
            int i = WhenMappings.$EnumSwitchMapping$0[this.target.ordinal()];
            if (i == 1 || i == 2) {
                return "";
            }
            if (i == 3) {
                return (breakLine ? "\n" : " ").concat("get()");
            }
            throw new NoWhenBranchMatchedException();
        }

        static /* synthetic */ String bodyPrefix$default(CallableBuilder callableBuilder, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                z = true;
            }
            return callableBuilder.bodyPrefix(z);
        }

        public final CallableBuilder modifier(String modifier) {
            Intrinsics.checkNotNullParameter(modifier, "modifier");
            State state = State.MODIFIERS;
            this.sb.append(modifier);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ CallableBuilder typeParams$default(CallableBuilder callableBuilder, Collection collection, int i, Object obj) {
            if ((i & 1) != 0) {
                collection = CollectionsKt.emptyList();
            }
            return callableBuilder.typeParams(collection);
        }

        public final CallableBuilder typeParams(Collection<String> values) {
            Intrinsics.checkNotNullParameter(values, "values");
            placeKeyword();
            if (!values.isEmpty()) {
                this.sb.append(CollectionsKt.joinToString$default(values, ", ", "<", "> ", -1, "", null, 32, null));
            }
            return this;
        }

        public final CallableBuilder receiver(String receiverType) {
            Intrinsics.checkNotNullParameter(receiverType, "receiverType");
            State state = State.RECEIVER;
            this.sb.append(receiverType).append(".");
            this.state = State.NAME;
            return this;
        }

        public static /* synthetic */ CallableBuilder name$default(CallableBuilder callableBuilder, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = CONSTRUCTOR_NAME;
            }
            return callableBuilder.name(str);
        }

        public final CallableBuilder name(String name2) {
            State state;
            Intrinsics.checkNotNullParameter(name2, "name");
            if (this.state != State.NAME) {
                State state2 = this.state;
                State state3 = State.RECEIVER;
            }
            if (Intrinsics.areEqual(name2, CONSTRUCTOR_NAME)) {
                Target target = this.target;
                Target target2 = Target.CONSTRUCTOR;
            }
            this.sb.append(name2);
            int i = WhenMappings.$EnumSwitchMapping$0[this.target.ordinal()];
            if (i == 1 || i == 2) {
                this.sb.append("(");
                state = State.FIRST_PARAM;
            } else {
                state = State.TYPE_CONSTRAINTS;
            }
            this.state = state;
            return this;
        }

        public static /* synthetic */ CallableBuilder param$default(CallableBuilder callableBuilder, String str, String str2, String str3, int i, Object obj) {
            if ((i & 4) != 0) {
                str3 = null;
            }
            return callableBuilder.param(str, str2, str3);
        }

        public final CallableBuilder param(String name2, String type, String defaultValue) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(type, "type");
            if (this.target != Target.FUNCTION) {
                Target target = this.target;
                Target target2 = Target.CONSTRUCTOR;
            }
            if (this.state != State.FIRST_PARAM) {
                State state = this.state;
                State state2 = State.REST_PARAMS;
            }
            if (this.state == State.REST_PARAMS) {
                this.sb.append(", ");
            }
            this.sb.append(name2).append(": ").append(type);
            if (defaultValue != null) {
                this.sb.append(" = ").append(defaultValue);
            }
            if (this.state == State.FIRST_PARAM) {
                this.state = State.REST_PARAMS;
            }
            return this;
        }

        public final CallableBuilder returnType(String type) {
            Intrinsics.checkNotNullParameter(type, "type");
            closeParams();
            this.sb.append(": ").append(type);
            return this;
        }

        public final CallableBuilder noReturnType() {
            closeParams();
            return this;
        }

        public final CallableBuilder typeConstraints(Collection<String> values) {
            Intrinsics.checkNotNullParameter(values, "values");
            if (this.state == State.TYPE_CONSTRAINTS) {
                Target target = this.target;
                Target target2 = Target.CONSTRUCTOR;
            }
            if (!values.isEmpty()) {
                this.sb.append(CollectionsKt.joinToString$default(values, ", ", " where ", "", -1, "", null, 32, null));
            }
            this.state = State.BODY;
            return this;
        }

        public final CallableBuilder superDelegation(String argumentList) {
            Intrinsics.checkNotNullParameter(argumentList, "argumentList");
            if (this.state == State.TYPE_CONSTRAINTS) {
                Target target = this.target;
                Target target2 = Target.CONSTRUCTOR;
            }
            this.sb.append(": super").append(argumentList);
            this.state = State.BODY;
            return this;
        }

        public final CallableBuilder blockBody(String body) {
            Intrinsics.checkNotNullParameter(body, "body");
            if (this.state != State.BODY) {
                State state = this.state;
                State state2 = State.TYPE_CONSTRAINTS;
            }
            this.sb.append(bodyPrefix$default(this, false, 1, null)).append(" {\n").append(body).append("\n}");
            this.state = State.DONE;
            return this;
        }

        public static /* synthetic */ CallableBuilder getterExpression$default(CallableBuilder callableBuilder, String str, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            return callableBuilder.getterExpression(str, z);
        }

        public final CallableBuilder getterExpression(String expression, boolean breakLine) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Target target = Target.READ_ONLY_PROPERTY;
            if (this.state != State.BODY) {
                State state = this.state;
                State state2 = State.TYPE_CONSTRAINTS;
            }
            this.sb.append(bodyPrefix(breakLine)).append(" = ").append(expression);
            this.state = State.DONE;
            return this;
        }

        public final CallableBuilder initializer(String body) {
            Intrinsics.checkNotNullParameter(body, "body");
            if (this.target == Target.READ_ONLY_PROPERTY && this.state != State.BODY) {
                State state = State.TYPE_CONSTRAINTS;
            }
            this.sb.append(" = ").append(body);
            this.state = State.DONE;
            return this;
        }

        public final CallableBuilder lazyBody(String body) {
            Intrinsics.checkNotNullParameter(body, "body");
            if (this.target == Target.READ_ONLY_PROPERTY && this.state != State.BODY) {
                State state = State.TYPE_CONSTRAINTS;
            }
            this.sb.append(" by kotlin.lazy {\n").append(body).append("\n}");
            this.state = State.DONE;
            return this;
        }

        public final void transform(Function1<? super StringBuilder, Unit> f) {
            Intrinsics.checkNotNullParameter(f, "f");
            f.invoke(this.sb);
        }

        public final String asString() {
            if (this.state != State.DONE) {
                this.state = State.DONE;
            }
            String string = this.sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
    }

    public final KtBlockExpression createBlock(String bodyText) {
        Intrinsics.checkNotNullParameter(bodyText, "bodyText");
        KtBlockExpression bodyBlockExpression = createFunction("fun foo() {\n" + bodyText + "\n}").getBodyBlockExpression();
        Intrinsics.checkNotNull(bodyBlockExpression);
        return bodyBlockExpression;
    }

    public static /* synthetic */ KtBlockExpression createSingleStatementBlock$default(KtPsiFactory ktPsiFactory, KtExpression ktExpression, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            str2 = null;
        }
        return ktPsiFactory.createSingleStatementBlock(ktExpression, str, str2);
    }

    public final KtBlockExpression createSingleStatementBlock(KtExpression statement, String prevComment, String nextComment) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        String str = "";
        String str2 = prevComment == null ? "" : " " + prevComment + StringUtil.SPACE;
        if (nextComment != null) {
            str = " " + nextComment + StringUtil.SPACE;
        }
        KtBlockExpression bodyBlockExpression = ((KtNamedFunction) CreateByPatternKt.createDeclarationByPattern$default(this, "fun foo() {\n" + str2 + SamWrapperCodegen.SAM_WRAPPER_SUFFIX + str + "\n}", new Object[]{statement}, false, 4, null)).getBodyBlockExpression();
        Intrinsics.checkNotNull(bodyBlockExpression);
        return bodyBlockExpression;
    }

    public final PsiComment createComment(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        PsiElement[] children = createFile(text).getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiElement psiElement : children) {
            if (psiElement instanceof PsiComment) {
                arrayList.add(psiElement);
            }
        }
        PsiComment psiComment = (PsiComment) CollectionsKt.single((List) arrayList);
        Intrinsics.areEqual(psiComment.getText(), text);
        return psiComment;
    }

    public final KtBlockExpression wrapInABlockWrapper(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof KtBlockExpression) {
            return (KtBlockExpression) expression;
        }
        KtExpression bodyExpression = createFunction("fun f() { " + expression.getText() + " }").getBodyExpression();
        Intrinsics.checkNotNull(bodyExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtBlockExpression");
        return new BlockWrapper((KtBlockExpression) bodyExpression, expression);
    }

    /* JADX INFO: compiled from: KtPsiFactory.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\u0005H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPsiFactory$BlockWrapper;", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "Lorg/jetbrains/kotlin/psi/KtPsiUtil$KtExpressionWrapper;", "fakeBlockExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/psi/KtBlockExpression;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "getBaseExpression", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getContainingKtFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "getParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getPsiOrParent", "Lorg/jetbrains/kotlin/psi/KtElement;", "getStatements", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class BlockWrapper extends KtBlockExpression implements KtPsiUtil.KtExpressionWrapper {
        private final KtExpression expression;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BlockWrapper(KtBlockExpression fakeBlockExpression, KtExpression expression) {
            super(fakeBlockExpression.getText());
            Intrinsics.checkNotNullParameter(fakeBlockExpression, "fakeBlockExpression");
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
        }

        @Override // org.jetbrains.kotlin.psi.KtBlockExpression
        public List<KtExpression> getStatements() {
            return CollectionsKt.listOf(this.expression);
        }

        @Override // org.jetbrains.kotlin.psi.KtPsiUtil.KtExpressionWrapper
        /* JADX INFO: renamed from: getBaseExpression, reason: from getter */
        public KtExpression getExpression() {
            return this.expression;
        }

        @Override // org.jetbrains.kotlin.psi.KtBlockExpression, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent */
        public PsiElement getLightOwner() {
            PsiElement parent = this.expression.getLightOwner();
            Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
            return parent;
        }

        @Override // org.jetbrains.kotlin.psi.KtBlockExpression, org.jetbrains.kotlin.psi.KtPureElement
        public KtElement getPsiOrParent() {
            KtElement psiOrParent = this.expression.getPsiOrParent();
            Intrinsics.checkNotNullExpressionValue(psiOrParent, "getPsiOrParent(...)");
            return psiOrParent;
        }

        @Override // org.jetbrains.kotlin.psi.KtBlockExpression, org.jetbrains.kotlin.psi.KtPureElement
        public KtFile getContainingKtFile() {
            KtFile containingKtFile = this.expression.getContainingKtFile();
            Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
            return containingKtFile;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiFile getContainingFile() {
            PsiFile containingFile = this.expression.getContainingFile();
            Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
            return containingFile;
        }
    }
}
