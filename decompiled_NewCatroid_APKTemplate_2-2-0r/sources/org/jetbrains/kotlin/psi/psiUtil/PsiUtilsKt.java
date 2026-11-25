package org.jetbrains.kotlin.psi.psiUtil;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiSearchScopeUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFileAnnotationList;
import org.jetbrains.kotlin.psi.KtImportList;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPureElement;

/* JADX INFO: compiled from: psiUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u001a\u001a\u0010$\u001a\u0004\u0018\u00010\u00022\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u001eH\u0002\u001a\u0016\u0010(\u001a\u00020)2\u0006\u0010%\u001a\u00020*2\u0006\u0010+\u001a\u00020)\u001a2\u0010,\u001a\u00020-\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0014\b\n\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020-00H\u0086\bø\u0001\u0000\u001aH\u0010,\u001a\u00020-\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0014\b\u0004\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-002\u0014\b\n\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020-00H\u0086\bø\u0001\u0000\u001a\u0012\u00102\u001a\u00020-*\u00020\u00022\u0006\u00103\u001a\u00020\u0002\u001a\n\u00104\u001a\u000205*\u00020\u0002\u001a\u0010\u00106\u001a\b\u0012\u0004\u0012\u0002070\n*\u000207\u001a\f\u00108\u001a\u0004\u0018\u00010\u0002*\u000207\u001a8\u00109\u001a\b\u0012\u0004\u0012\u0002H.0:\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0014\b\n\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020-00H\u0086\bø\u0001\u0000\u001aN\u00109\u001a\b\u0012\u0004\u0012\u0002H.0:\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0014\b\u0004\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-002\u0014\b\n\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020-00H\u0086\bø\u0001\u0000\u001ae\u0010;\u001a\u0002H<\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002\"\u000e\b\u0001\u0010<*\b\u0012\u0004\u0012\u0002H.0=*\u00020\u00022\u0006\u0010>\u001a\u0002H<2\u0014\b\u0004\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-002\u0014\b\n\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020-00H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010?\u001a\u0015\u0010@\u001a\u00020-*\u00020A2\u0006\u00103\u001a\u00020\u0002H\u0086\u0002\u001a\u0012\u0010B\u001a\u00020-*\u00020\u001e2\u0006\u0010C\u001a\u00020\u0006\u001a\u001f\u0010D\u001a\b\u0012\u0004\u0012\u0002HF0E\"\b\b\u0000\u0010F*\u00020\u0002*\u0002HF¢\u0006\u0002\u0010G\u001a\u0018\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00020:*\u00020&2\u0006\u0010'\u001a\u00020\u001e\u001a9\u0010I\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0014\b\n\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020-00H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010J\u001aO\u0010I\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0014\b\u0004\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-002\u0014\b\n\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020-00H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010K\u001a2\u0010L\u001a\u000205\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0014\b\b\u0010M\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020500H\u0086\bø\u0001\u0000\u001aH\u0010L\u001a\u000205\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0014\b\u0004\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-002\u0014\b\b\u0010M\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020500H\u0086\bø\u0001\u0000\u001a \u0010N\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b¢\u0006\u0002\u0010O\u001a$\u0010P\u001a\b\u0012\u0004\u0012\u0002H.0Q\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b¢\u0006\u0002\u0010R\u001a\n\u0010S\u001a\u00020**\u00020T\u001a\n\u0010U\u001a\u00020V*\u00020\u0002\u001a>\u0010W\u001a\u0004\u0018\u0001H.\"\b\b\u0000\u0010.*\u00020\u0002*\u0002H.2\u0006\u00103\u001a\u00020\u00022\u0019\u0010X\u001a\u0015\u0012\u0004\u0012\u0002H.\u0012\u0006\u0012\u0004\u0018\u00010\u000200¢\u0006\u0002\bY¢\u0006\u0002\u0010Z\u001aD\u0010[\u001a\u0004\u0018\u0001H.\"\b\b\u0000\u0010.*\u00020\u0002*\u0002H.2\u0006\u00103\u001a\u00020\u00022\u001f\u0010\\\u001a\u001b\u0012\u0004\u0012\u0002H.\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020]00¢\u0006\u0002\bY¢\u0006\u0002\u0010Z\u001a \u0010^\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b¢\u0006\u0002\u0010O\u001a \u0010_\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b¢\u0006\u0002\u0010O\u001a\u0016\u0010`\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\b\b\u0002\u0010a\u001a\u00020-\u001a\u0016\u0010b\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\b\b\u0002\u0010a\u001a\u00020-\u001a \u0010c\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b¢\u0006\u0002\u0010O\u001a)\u0010c\u001a\u0004\u0018\u0001H.\"\b\b\u0000\u0010.*\u00020\u0002*\u00020\u00022\f\u0010d\u001a\b\u0012\u0004\u0012\u0002H.0e¢\u0006\u0002\u0010f\u001a\u0017\u0010g\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010h\u001a\u00020\u0002H\u0086\u0010\u001a(\u0010i\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0006\u0010j\u001a\u00020-H\u0086\b¢\u0006\u0002\u0010k\u001aL\u0010i\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\u0006\u0010j\u001a\u00020-2\"\u0010l\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020e0Q\"\n\u0012\u0006\b\u0001\u0012\u00020\u00020eH\u0086\b¢\u0006\u0002\u0010m\u001aJ\u0010n\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\b\b\u0002\u0010j\u001a\u00020-2\u001b\b\b\u0010X\u001a\u0015\u0012\u0004\u0012\u0002H.\u0012\u0006\u0012\u0004\u0018\u00010\u000200¢\u0006\u0002\bYH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010o\u001aP\u0010p\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u00022\b\b\u0002\u0010j\u001a\u00020-2!\b\b\u0010\\\u001a\u001b\u0012\u0004\u0012\u0002H.\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020]00¢\u0006\u0002\bYH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010o\u001aI\u0010q\u001a\u0004\u0018\u0001H.\"\b\b\u0000\u0010.*\u00020\u0002*\u00020\u00022\b\b\u0002\u0010j\u001a\u00020-2\"\u0010r\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H.0e0Q\"\n\u0012\u0006\b\u0001\u0012\u0002H.0e¢\u0006\u0002\u0010m\u001a'\u0010s\u001a\u0004\u0018\u00010\u0002\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002\"\n\b\u0001\u0010t\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b\u001a3\u0010u\u001a\u0004\u0018\u00010\u0002\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002\"\n\b\u0001\u0010t\u0018\u0001*\u00020\u0002\"\n\b\u0002\u0010v\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b\u001a]\u0010w\u001a\u0004\u0018\u0001H.\"\b\b\u0000\u0010.*\u00020\u0002*\u00020\u00022\b\b\u0002\u0010j\u001a\u00020-2\"\u0010r\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H.0e0Q\"\n\u0012\u0006\b\u0001\u0012\u0002H.0e2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u00020-00¢\u0006\u0002\u0010x\u001a\u0016\u0010y\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\b\b\u0002\u0010a\u001a\u00020-\u001a\u0016\u0010z\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\b\b\u0002\u0010a\u001a\u00020-\u001a\u0012\u0010{\u001a\u00020\u0006*\u00020\u00022\u0006\u0010|\u001a\u00020\u0002\u001a \u0010}\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b¢\u0006\u0002\u0010O\u001a\n\u0010~\u001a\u00020V*\u00020\u0001\u001a\n\u0010\u007f\u001a\u00020V*\u00020\u0002\u001a!\u0010\u0080\u0001\u001a\u0004\u0018\u0001H.\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b¢\u0006\u0002\u0010O\u001a\f\u0010\u0081\u0001\u001a\u00020-*\u00030\u0082\u0001\u001a\f\u0010\u0081\u0001\u001a\u00020-*\u00030\u0083\u0001\u001a\f\u0010\u0084\u0001\u001a\u00020-*\u00030\u0082\u0001\u001a\f\u0010\u0084\u0001\u001a\u00020-*\u00030\u0083\u0001\u001a\f\u0010\u0085\u0001\u001a\u00020-*\u00030\u0082\u0001\u001a\f\u0010\u0086\u0001\u001a\u00020-*\u00030\u0082\u0001\u001a\f\u0010\u0087\u0001\u001a\u00020-*\u00030\u0082\u0001\u001a\u001f\u0010\u0088\u0001\u001a\u00020-*\u0004\u0018\u00010\u00022\u0006\u00103\u001a\u00020\u00022\b\b\u0002\u0010j\u001a\u00020-\u001a\u001a\u0010\u0089\u0001\u001a\u00020-*\u00020\u00022\r\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u00020\u00020]\u001a\u001d\u0010\u008b\u0001\u001a\u00020-*\u00030\u008c\u0001\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\u001a\u001c\u0010\u008e\u0001\u001a\b\u0012\u0004\u0012\u0002070\n*\u0002072\t\b\u0002\u0010\u008f\u0001\u001a\u00020-\u001a\"\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0013\u0010\u0091\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-00\u001a\u0018\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\t\b\u0002\u0010\u0092\u0001\u001a\u00020-\u001a'\u0010\u0093\u0001\u001a\n\u0018\u0001H.¢\u0006\u0003\b\u0094\u0001\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u0002H.H\u0086\b¢\u0006\u0002\u0010O\u001a\u0010\u0010\r\u001a\b\u0012\u0004\u0012\u0002070\n*\u000207\u001a\"\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0013\u0010\u0091\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-00\u001a\u0018\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\t\b\u0002\u0010\u0092\u0001\u001a\u00020-\u001a'\u0010\u0096\u0001\u001a\n\u0018\u0001H.¢\u0006\u0003\b\u0094\u0001\"\n\b\u0000\u0010.\u0018\u0001*\u00020\u0002*\u0002H.H\u0086\b¢\u0006\u0002\u0010O\u001a\u001c\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u0002070\n*\u0002072\t\b\u0002\u0010\u008f\u0001\u001a\u00020-\u001a&\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u00022\t\b\u0002\u0010\u008f\u0001\u001a\u00020-2\b\b\u0002\u0010a\u001a\u00020-\u001a\u000b\u0010\u0098\u0001\u001a\u00020-*\u00020\u0002\u001a\u000b\u0010\u0099\u0001\u001a\u00020\u0001*\u00020\u0001\u001a\u000f\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u0002\u001a\u000b\u0010\u009b\u0001\u001a\u00020\u0002*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u001b\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u001b\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\f\"\u001b\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\f\"\u001b\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\f\"\u0015\u0010\u0013\u001a\u00020\u0006*\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\"\u0015\u0010\u0017\u001a\u00020\u0006*\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016\"\u0015\u0010\u0019\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\b\"\u0015\u0010\u001b\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\b\"\u0017\u0010\u001d\u001a\u0004\u0018\u00010\u001e*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 \"\u0015\u0010!\u001a\u00020\u001e*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\"\u0010#ò\u0001\u0005\n\u00030\u008d\u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u009c\u0001"}, d2 = {"allChildren", "Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getAllChildren", "(Lcom/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "endOffset", "", "getEndOffset", "(Lcom/intellij/psi/PsiElement;)I", "nextLeafs", "Lkotlin/sequences/Sequence;", "getNextLeafs", "(Lcom/intellij/psi/PsiElement;)Lkotlin/sequences/Sequence;", "parents", "getParents", "parentsWithSelf", "getParentsWithSelf", "prevLeafs", "getPrevLeafs", "pureEndOffset", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "getPureEndOffset", "(Lorg/jetbrains/kotlin/psi/KtPureElement;)I", "pureStartOffset", "getPureStartOffset", "startOffset", "getStartOffset", "startOffsetSkippingComments", "getStartOffsetSkippingComments", "textRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTextRange", "(Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;)Lcom/intellij/openapi/util/TextRange;", "textRangeWithoutComments", "getTextRangeWithoutComments", "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/openapi/util/TextRange;", "findFirstLeafWhollyInRange", "file", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "range", "replaceFileAnnotationList", "Lorg/jetbrains/kotlin/psi/KtFileAnnotationList;", "Lorg/jetbrains/kotlin/psi/KtFile;", "annotationList", "anyDescendantOfType", "", ExifInterface.GPS_DIRECTION_TRUE, "predicate", "Lkotlin/Function1;", "canGoInside", "before", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "checkDecompiledText", "", "children", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "closestPsiElement", "collectDescendantsOfType", "", "collectDescendantsOfTypeTo", "C", "", "to", "(Lcom/intellij/psi/PsiElement;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "contains", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "containsInside", TypedValues.CycleType.S_WAVE_OFFSET, "createSmartPointer", "Lorg/jetbrains/kotlin/com/intellij/psi/SmartPsiElementPointer;", ExifInterface.LONGITUDE_EAST, "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/SmartPsiElementPointer;", "elementsInRange", "findDescendantOfType", "(Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function1;)Lcom/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Lcom/intellij/psi/PsiElement;", "forEachDescendantOfType", "action", "getChildOfType", "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/PsiElement;", "getChildrenOfType", "", "(Lcom/intellij/psi/PsiElement;)[Lcom/intellij/psi/PsiElement;", "getContainingKtFile", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LazyParseablePsiElement;", "getElementTextWithContext", "", "getIfChildIsInBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lkotlin/ExtensionFunctionType;", "(Lcom/intellij/psi/PsiElement;Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function1;)Lcom/intellij/psi/PsiElement;", "getIfChildIsInBranches", "branches", "", "getLastParentOfTypeInRow", "getLastParentOfTypeInRowWithSelf", "getNextSiblingIgnoringWhitespace", "withItself", "getNextSiblingIgnoringWhitespaceAndComments", "getNonStrictParentOfType", "parentClass", "Ljava/lang/Class;", "(Lcom/intellij/psi/PsiElement;Ljava/lang/Class;)Lcom/intellij/psi/PsiElement;", "getOutermostParentContainedIn", "container", "getParentOfType", SchemaSymbols.ATTVAL_STRICT, "(Lcom/intellij/psi/PsiElement;Z)Lcom/intellij/psi/PsiElement;", "stopAt", "(Lcom/intellij/psi/PsiElement;Z[Ljava/lang/Class;)Lcom/intellij/psi/PsiElement;", "getParentOfTypeAndBranch", "(Lcom/intellij/psi/PsiElement;ZLkotlin/jvm/functions/Function1;)Lcom/intellij/psi/PsiElement;", "getParentOfTypeAndBranches", "getParentOfTypes", "parentClasses", "getParentOfTypes2", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "getParentOfTypes3", "U", "getParentOfTypesAndPredicate", "(Lcom/intellij/psi/PsiElement;Z[Ljava/lang/Class;Lkotlin/jvm/functions/Function1;)Lcom/intellij/psi/PsiElement;", "getPrevSiblingIgnoringWhitespace", "getPrevSiblingIgnoringWhitespaceAndComments", "getStartOffsetIn", "ancestor", "getStrictParentOfType", "getText", "getTextWithLocation", "getTopmostParentOfType", "hasActualModifier", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "hasExpectModifier", "hasFunModifier", "hasSuspendModifier", "hasValueModifier", "isAncestor", "isInsideOf", PathTree.NAME_elements, "isNull", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "leaves", "forward", "nextLeaf", "filter", "skipEmptyElements", "nextSiblingOfSameType", "Lorg/jetbrains/annotations/Nullable;", "prevLeaf", "prevSiblingOfSameType", "siblings", "startsWithComment", "trimWhiteSpaces", "unwrapParenthesesLabelsAndAnnotations", "unwrapParenthesesLabelsAndAnnotationsDeeply", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PsiUtilsKt {
    public static final PsiChildRange getAllChildren(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        PsiElement firstChild = psiElement.getFirstChild();
        return firstChild != null ? new PsiChildRange(firstChild, psiElement.getLastChild()) : PsiChildRange.INSTANCE.getEMPTY();
    }

    public static /* synthetic */ Sequence siblings$default(PsiElement psiElement, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return siblings(psiElement, z, z2);
    }

    public static final Sequence<PsiElement> siblings(final PsiElement psiElement, final boolean z, final boolean z2) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return new Sequence<PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.siblings.1
            /* JADX WARN: Type inference failed for: r1v0, types: [T, org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
            @Override // kotlin.sequences.Sequence
            public Iterator<PsiElement> iterator() {
                Ref.ObjectRef objectRef = new Ref.ObjectRef();
                objectRef.element = psiElement;
                return new PsiUtilsKt$siblings$1$iterator$1(z2, objectRef, z);
            }
        };
    }

    public static final Sequence<PsiElement> getParentsWithSelf(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return SequencesKt.generateSequence(psiElement, new Function1<PsiElement, PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$parentsWithSelf$1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof PsiFile) {
                    return null;
                }
                return it.getLightParent();
            }
        });
    }

    public static final Sequence<PsiElement> getParents(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return SequencesKt.drop(getParentsWithSelf(psiElement), 1);
    }

    public static final PsiElement prevLeaf(PsiElement psiElement, boolean z) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return PsiTreeUtil.prevLeaf(psiElement, z);
    }

    public static /* synthetic */ PsiElement prevLeaf$default(PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return prevLeaf(psiElement, z);
    }

    public static final PsiElement nextLeaf(PsiElement psiElement, boolean z) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return PsiTreeUtil.nextLeaf(psiElement, z);
    }

    public static /* synthetic */ PsiElement nextLeaf$default(PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return nextLeaf(psiElement, z);
    }

    public static final Sequence<PsiElement> getPrevLeafs(final PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return SequencesKt.generateSequence((Function0) new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$prevLeafs$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiElement invoke() {
                return PsiUtilsKt.prevLeaf$default(psiElement, false, 1, null);
            }
        }, (Function1) new Function1<PsiElement, PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$prevLeafs$2
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return PsiUtilsKt.prevLeaf$default(it, false, 1, null);
            }
        });
    }

    public static final Sequence<PsiElement> getNextLeafs(final PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return SequencesKt.generateSequence((Function0) new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$nextLeafs$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiElement invoke() {
                return PsiUtilsKt.nextLeaf$default(psiElement, false, 1, null);
            }
        }, (Function1) new Function1<PsiElement, PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$nextLeafs$2
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return PsiUtilsKt.nextLeaf$default(it, false, 1, null);
            }
        });
    }

    public static final PsiElement prevLeaf(PsiElement psiElement, Function1<? super PsiElement, Boolean> filter) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(filter, "filter");
        PsiElement psiElementPrevLeaf$default = prevLeaf$default(psiElement, false, 1, null);
        while (psiElementPrevLeaf$default != null && !filter.invoke(psiElementPrevLeaf$default).booleanValue()) {
            psiElementPrevLeaf$default = prevLeaf$default(psiElementPrevLeaf$default, false, 1, null);
        }
        return psiElementPrevLeaf$default;
    }

    public static final PsiElement nextLeaf(PsiElement psiElement, Function1<? super PsiElement, Boolean> filter) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(filter, "filter");
        PsiElement psiElementNextLeaf$default = nextLeaf$default(psiElement, false, 1, null);
        while (psiElementNextLeaf$default != null && !filter.invoke(psiElementNextLeaf$default).booleanValue()) {
            psiElementNextLeaf$default = nextLeaf$default(psiElementNextLeaf$default, false, 1, null);
        }
        return psiElementNextLeaf$default;
    }

    public static /* synthetic */ PsiElement getParentOfTypes$default(PsiElement psiElement, boolean z, Class[] clsArr, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return getParentOfTypes(psiElement, z, clsArr);
    }

    public static final <T extends PsiElement> T getParentOfTypes(PsiElement psiElement, boolean z, Class<? extends T>... parentClasses) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(parentClasses, "parentClasses");
        return (T) getParentOfTypesAndPredicate(psiElement, z, (Class[]) Arrays.copyOf(parentClasses, parentClasses.length), new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getParentOfTypes.1
            /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return true;
            }
        });
    }

    public static /* synthetic */ PsiElement getParentOfTypesAndPredicate$default(PsiElement psiElement, boolean z, Class[] clsArr, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return getParentOfTypesAndPredicate(psiElement, z, clsArr, function1);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0034 A[SYNTHETIC] */
    public static final <T extends PsiElement> T getParentOfTypesAndPredicate(PsiElement psiElement, boolean z, Class<? extends T>[] parentClasses, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(parentClasses, "parentClasses");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if (z) {
            psiElement = psiElement.getLightParent();
        }
        while (psiElement != null) {
            if (parentClasses.length != 0) {
                int length = parentClasses.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        if (!parentClasses[i].isInstance(psiElement)) {
                            i++;
                        } else if (predicate.invoke(psiElement).booleanValue()) {
                            return (T) psiElement;
                        }
                    }
                }
            } else if (predicate.invoke(psiElement).booleanValue()) {
                return (T) psiElement;
            }
            if (psiElement instanceof PsiFile) {
                return null;
            }
            psiElement = psiElement.getLightParent();
        }
        return null;
    }

    public static final <T extends PsiElement> T getNonStrictParentOfType(PsiElement psiElement, Class<T> parentClass) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(parentClass, "parentClass");
        return (T) PsiTreeUtil.getParentOfType(psiElement, parentClass, false);
    }

    public static final /* synthetic */ <T extends PsiElement> T getParentOfType(PsiElement psiElement, boolean z) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, z);
    }

    public static final /* synthetic */ <T extends PsiElement, V extends PsiElement> PsiElement getParentOfTypes2(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        return PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, PsiElement.class);
    }

    public static final /* synthetic */ <T extends PsiElement, V extends PsiElement, U extends PsiElement> PsiElement getParentOfTypes3(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        Intrinsics.reifiedOperationMarker(4, "U");
        return PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, PsiElement.class, PsiElement.class);
    }

    public static final /* synthetic */ <T extends PsiElement> T getParentOfType(PsiElement psiElement, boolean z, Class<? extends PsiElement>... stopAt) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(stopAt, "stopAt");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, z, (Class<? extends PsiElement>[]) Arrays.copyOf(stopAt, stopAt.length));
    }

    public static final /* synthetic */ <T extends PsiElement> T getStrictParentOfType(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, true);
    }

    public static final /* synthetic */ <T extends PsiElement> T getNonStrictParentOfType(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, false);
    }

    public static final /* synthetic */ <T extends PsiElement> T getTopmostParentOfType(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) PsiTreeUtil.getTopmostParentOfType(psiElement, PsiElement.class);
    }

    public static final /* synthetic */ <T extends PsiElement> T getChildOfType(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) PsiTreeUtil.getChildOfType(psiElement, PsiElement.class);
    }

    public static final /* synthetic */ <T extends PsiElement> T[] getChildrenOfType(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        PsiElement[] childrenOfType = PsiTreeUtil.getChildrenOfType(psiElement, PsiElement.class);
        if (childrenOfType == null) {
            Intrinsics.reifiedOperationMarker(0, ExifInterface.GPS_DIRECTION_TRUE);
            childrenOfType = new PsiElement[0];
        }
        return (T[]) childrenOfType;
    }

    public static /* synthetic */ PsiElement getNextSiblingIgnoringWhitespaceAndComments$default(PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return getNextSiblingIgnoringWhitespaceAndComments(psiElement, z);
    }

    public static final PsiElement getNextSiblingIgnoringWhitespaceAndComments(PsiElement psiElement, boolean z) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return (PsiElement) SequencesKt.firstOrNull(SequencesKt.filter(siblings$default(psiElement, false, z, 1, null), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getNextSiblingIgnoringWhitespaceAndComments.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(((it instanceof PsiWhiteSpace) || (it instanceof PsiComment)) ? false : true);
            }
        }));
    }

    public static /* synthetic */ PsiElement getNextSiblingIgnoringWhitespace$default(PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return getNextSiblingIgnoringWhitespace(psiElement, z);
    }

    public static final PsiElement getNextSiblingIgnoringWhitespace(PsiElement psiElement, boolean z) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return (PsiElement) SequencesKt.firstOrNull(SequencesKt.filter(siblings$default(psiElement, false, z, 1, null), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getNextSiblingIgnoringWhitespace.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!(it instanceof PsiWhiteSpace));
            }
        }));
    }

    public static /* synthetic */ PsiElement getPrevSiblingIgnoringWhitespaceAndComments$default(PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return getPrevSiblingIgnoringWhitespaceAndComments(psiElement, z);
    }

    public static final PsiElement getPrevSiblingIgnoringWhitespaceAndComments(PsiElement psiElement, boolean z) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return (PsiElement) SequencesKt.firstOrNull(SequencesKt.filter(siblings(psiElement, false, z), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getPrevSiblingIgnoringWhitespaceAndComments.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(((it instanceof PsiWhiteSpace) || (it instanceof PsiComment)) ? false : true);
            }
        }));
    }

    public static /* synthetic */ PsiElement getPrevSiblingIgnoringWhitespace$default(PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return getPrevSiblingIgnoringWhitespace(psiElement, z);
    }

    public static final PsiElement getPrevSiblingIgnoringWhitespace(PsiElement psiElement, boolean z) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return (PsiElement) SequencesKt.firstOrNull(SequencesKt.filter(siblings(psiElement, false, z), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getPrevSiblingIgnoringWhitespace.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!(it instanceof PsiWhiteSpace));
            }
        }));
    }

    public static final /* synthetic */ <T extends PsiElement> T nextSiblingOfSameType(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) PsiTreeUtil.getNextSiblingOfType(t, PsiElement.class);
    }

    public static final /* synthetic */ <T extends PsiElement> T prevSiblingOfSameType(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) PsiTreeUtil.getPrevSiblingOfType(t, PsiElement.class);
    }

    public static /* synthetic */ boolean isAncestor$default(PsiElement psiElement, PsiElement psiElement2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return isAncestor(psiElement, psiElement2, z);
    }

    public static final boolean isAncestor(PsiElement psiElement, PsiElement element, boolean z) {
        Intrinsics.checkNotNullParameter(element, "element");
        return PsiTreeUtil.isAncestor(psiElement, element, z);
    }

    public static final <T extends PsiElement> T getIfChildIsInBranch(T t, PsiElement element, Function1<? super T, ? extends PsiElement> branch) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(branch, "branch");
        if (isAncestor$default(branch.invoke(t), element, false, 2, null)) {
            return t;
        }
        return null;
    }

    public static final <T extends PsiElement> T getIfChildIsInBranches(T t, PsiElement element, Function1<? super T, ? extends Iterable<? extends PsiElement>> branches) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(branches, "branches");
        Iterable<? extends PsiElement> iterableInvoke = branches.invoke(t);
        if (!(iterableInvoke instanceof Collection) || !((Collection) iterableInvoke).isEmpty()) {
            Iterator<? extends PsiElement> it = iterableInvoke.iterator();
            while (it.hasNext()) {
                if (isAncestor$default(it.next(), element, false, 2, null)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static /* synthetic */ PsiElement getParentOfTypeAndBranch$default(PsiElement psiElement, boolean z, Function1 branch, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(branch, "branch");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, z);
        if (parentOfType != null) {
            return getIfChildIsInBranch(parentOfType, psiElement, branch);
        }
        return null;
    }

    public static /* synthetic */ PsiElement getParentOfTypeAndBranches$default(PsiElement psiElement, boolean z, Function1 branches, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(branches, "branches");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, z);
        if (parentOfType != null) {
            return getIfChildIsInBranches(parentOfType, psiElement, branches);
        }
        return null;
    }

    public static final PsiElement getOutermostParentContainedIn(PsiElement psiElement, PsiElement container) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(container, "container");
        while (true) {
            PsiElement parent = psiElement.getLightParent();
            if (Intrinsics.areEqual(parent, container)) {
                return psiElement;
            }
            if (parent == null) {
                return null;
            }
            psiElement = parent;
        }
    }

    public static final PsiChildRange trimWhiteSpaces(PsiChildRange psiChildRange) {
        Object next;
        Intrinsics.checkNotNullParameter(psiChildRange, "<this>");
        if (psiChildRange.getFirst() == null) {
            return psiChildRange;
        }
        Object obj = null;
        Iterator it = siblings$default(psiChildRange.getFirst(), false, false, 3, null).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((PsiElement) next) instanceof PsiWhiteSpace);
        PsiElement psiElement = (PsiElement) next;
        PsiElement last = psiChildRange.getLast();
        Intrinsics.checkNotNull(last);
        for (Object obj2 : siblings$default(last, false, false, 2, null)) {
            if (!(((PsiElement) obj2) instanceof PsiWhiteSpace)) {
                obj = obj2;
                break;
            }
        }
        return new PsiChildRange(psiElement, (PsiElement) obj);
    }

    public static final /* synthetic */ <T extends PsiElement> void forEachDescendantOfType(PsiElement psiElement, final Function1<? super PsiElement, Boolean> canGoInside, final Function1<? super T, Unit> action) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(action, "action");
        checkDecompiledText(psiElement);
        Intrinsics.needClassReification();
        psiElement.accept(new PsiRecursiveElementVisitor() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.forEachDescendantOfType.2
            /* JADX WARN: Type inference incomplete: some casts might be missing */
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (canGoInside.invoke(element).booleanValue()) {
                    super.visitElement(element);
                }
                Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                if (element instanceof PsiElement) {
                    action.invoke((T) element);
                }
            }
        });
    }

    public static /* synthetic */ boolean anyDescendantOfType$default(PsiElement psiElement, Function1 predicate, int i, Object obj) {
        if ((i & 1) != 0) {
            Intrinsics.needClassReification();
            predicate = AnonymousClass1.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        checkDecompiledText(psiElement);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$anyDescendantOfType$$inlined$findDescendantOfType$1(predicate, objectRef));
        return ((PsiElement) objectRef.element) != null;
    }

    public static /* synthetic */ boolean anyDescendantOfType$default(PsiElement psiElement, Function1 canGoInside, Function1 predicate, int i, Object obj) {
        if ((i & 2) != 0) {
            Intrinsics.needClassReification();
            predicate = AnonymousClass2.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        checkDecompiledText(psiElement);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$anyDescendantOfType$$inlined$findDescendantOfType$2(predicate, objectRef, canGoInside));
        return ((PsiElement) objectRef.element) != null;
    }

    public static /* synthetic */ PsiElement findDescendantOfType$default(PsiElement psiElement, Function1 predicate, int i, Object obj) {
        if ((i & 1) != 0) {
            Intrinsics.needClassReification();
            predicate = C48981.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        checkDecompiledText(psiElement);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$findDescendantOfType$$inlined$findDescendantOfType$1(predicate, objectRef));
        return (PsiElement) objectRef.element;
    }

    public static /* synthetic */ PsiElement findDescendantOfType$default(PsiElement psiElement, Function1 canGoInside, Function1 predicate, int i, Object obj) {
        if ((i & 2) != 0) {
            Intrinsics.needClassReification();
            predicate = C48993.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        checkDecompiledText(psiElement);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.needClassReification();
        psiElement.accept(new AnonymousClass4(predicate, objectRef, canGoInside));
        return (PsiElement) objectRef.element;
    }

    public static final /* synthetic */ <T extends PsiElement> T findDescendantOfType(PsiElement psiElement, Function1<? super PsiElement, Boolean> canGoInside, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        checkDecompiledText(psiElement);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.needClassReification();
        psiElement.accept(new AnonymousClass4(predicate, objectRef, canGoInside));
        return (T) objectRef.element;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$findDescendantOfType$4, reason: invalid class name */
    /* JADX INFO: compiled from: psiUtils.kt */
    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"org/jetbrains/kotlin/psi/psiUtil/PsiUtilsKt$findDescendantOfType$4", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiRecursiveElementWalkingVisitor;", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 176)
    public static final class AnonymousClass4 extends PsiRecursiveElementWalkingVisitor {
        final /* synthetic */ Function1<PsiElement, Boolean> $canGoInside;
        final /* synthetic */ Function1<T, Boolean> $predicate;
        final /* synthetic */ Ref.ObjectRef<T> $result;

        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass4(Function1<? super T, Boolean> function1, Ref.ObjectRef<T> objectRef, Function1<? super PsiElement, Boolean> function2) {
            this.$predicate = function1;
            this.$result = objectRef;
            this.$canGoInside = function2;
        }

        /* JADX WARN: Multi-variable type inference failed */
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
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
        public void visitElement(PsiElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if ((element instanceof PsiElement) && this.$predicate.invoke(element).booleanValue()) {
                this.$result.element = element;
                stopWalking();
            } else if (this.$canGoInside.invoke(element).booleanValue()) {
                super.visitElement(element);
            }
        }
    }

    public static final void checkDecompiledText(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile instanceof KtFile) {
            KtFile ktFile = (KtFile) containingFile;
            if (ktFile.getIsCompiled() && ktFile.getStub() != null) {
                throw new IllegalStateException("Attempt to load decompiled text, please use stubs instead. Decompile process might be slow and should be avoided".toString());
            }
        }
    }

    public static /* synthetic */ List collectDescendantsOfType$default(PsiElement psiElement, Function1 predicate, int i, Object obj) {
        if ((i & 1) != 0) {
            Intrinsics.needClassReification();
            predicate = C48951.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        Intrinsics.needClassReification();
        PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$1 psiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$1 = new PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$1(predicate, arrayList);
        checkDecompiledText(psiElement);
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$2(psiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$1));
        return arrayList;
    }

    public static /* synthetic */ List collectDescendantsOfType$default(PsiElement psiElement, Function1 canGoInside, Function1 predicate, int i, Object obj) {
        if ((i & 2) != 0) {
            Intrinsics.needClassReification();
            predicate = AnonymousClass3.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        Intrinsics.needClassReification();
        PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1 psiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1 = new PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1(predicate, arrayList);
        checkDecompiledText(psiElement);
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$2(canGoInside, psiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1));
        return arrayList;
    }

    public static final /* synthetic */ <T extends PsiElement> List<T> collectDescendantsOfType(PsiElement psiElement, Function1<? super PsiElement, Boolean> canGoInside, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        Intrinsics.needClassReification();
        PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1 psiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1 = new PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1(predicate, arrayList);
        checkDecompiledText(psiElement);
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$2(canGoInside, psiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1));
        return arrayList;
    }

    public static /* synthetic */ Collection collectDescendantsOfTypeTo$default(PsiElement psiElement, Collection to, Function1 canGoInside, Function1 predicate, int i, Object obj) {
        if ((i & 4) != 0) {
            Intrinsics.needClassReification();
            predicate = C48961.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.needClassReification();
        C48972 c48972 = new C48972(predicate, to);
        checkDecompiledText(psiElement);
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$collectDescendantsOfTypeTo$$inlined$forEachDescendantOfType$1(canGoInside, c48972));
        return to;
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$collectDescendantsOfTypeTo$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: psiUtils.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003\"\u000e\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0006\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "C", "", "it", "invoke", "(Lcom/intellij/psi/PsiElement;)V"}, k = 3, mv = {1, 9, 0}, xi = 176)
    public static final class C48972<T> extends Lambda implements Function1<T, Unit> {
        final /* synthetic */ Function1<T, Boolean> $predicate;

        /* JADX INFO: Incorrect field signature: TC; */
        final /* synthetic */ Collection $to;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Incorrect types in method signature: (Lkotlin/jvm/functions/Function1<-TT;Ljava/lang/Boolean;>;TC;)V */
        public C48972(Function1 function1, Collection collection) {
            super(1);
            this.$predicate = function1;
            this.$to = collection;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
            invoke((PsiElement) obj);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Incorrect types in method signature: (TT;)V */
        public final void invoke(PsiElement it) {
            Intrinsics.checkNotNullParameter(it, "it");
            if (this.$predicate.invoke(it).booleanValue()) {
                this.$to.add(it);
            }
        }
    }

    public static final /* synthetic */ <T extends PsiElement, C extends Collection<T>> C collectDescendantsOfTypeTo(PsiElement psiElement, C to, Function1<? super PsiElement, Boolean> canGoInside, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.needClassReification();
        C48972 c48972 = new C48972(predicate, to);
        checkDecompiledText(psiElement);
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$collectDescendantsOfTypeTo$$inlined$forEachDescendantOfType$1(canGoInside, c48972));
        return to;
    }

    public static final int getStartOffset(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return psiElement.getTextRange().getStartOffset();
    }

    public static final int getEndOffset(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return psiElement.getTextRange().getEndOffset();
    }

    public static final int getPureStartOffset(KtPureElement ktPureElement) {
        Intrinsics.checkNotNullParameter(ktPureElement, "<this>");
        KtElement psiOrParent = ktPureElement.getPsiOrParent();
        Intrinsics.checkNotNullExpressionValue(psiOrParent, "getPsiOrParent(...)");
        return getTextRangeWithoutComments(psiOrParent).getStartOffset();
    }

    public static final int getPureEndOffset(KtPureElement ktPureElement) {
        Intrinsics.checkNotNullParameter(ktPureElement, "<this>");
        KtElement psiOrParent = ktPureElement.getPsiOrParent();
        Intrinsics.checkNotNullExpressionValue(psiOrParent, "getPsiOrParent(...)");
        return getTextRangeWithoutComments(psiOrParent).getEndOffset();
    }

    public static final int getStartOffsetSkippingComments(PsiElement psiElement) {
        Object next;
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (!startsWithComment(psiElement)) {
            return getStartOffset(psiElement);
        }
        Iterator it = SequencesKt.generateSequence(psiElement.getFirstChild(), new Function1<PsiElement, PsiElement>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$startOffsetSkippingComments$firstNonCommentChild$1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(PsiElement it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getNextSibling();
            }
        }).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            PsiElement psiElement2 = (PsiElement) next;
            if (!(psiElement2 instanceof PsiWhiteSpace) && !(psiElement2 instanceof PsiComment)) {
                break;
            }
        }
        PsiElement psiElement3 = (PsiElement) next;
        return psiElement3 != null ? getStartOffset(psiElement3) : getStartOffset(psiElement);
    }

    public static final int getStartOffsetIn(PsiElement psiElement, PsiElement ancestor) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(ancestor, "ancestor");
        int startOffsetInParent = 0;
        while (!Intrinsics.areEqual(psiElement, ancestor)) {
            startOffsetInParent += psiElement.getStartOffsetInParent();
            psiElement = psiElement.getLightParent();
            Intrinsics.checkNotNullExpressionValue(psiElement, "getParent(...)");
        }
        return startOffsetInParent;
    }

    public static final boolean containsInside(TextRange textRange, int i) {
        Intrinsics.checkNotNullParameter(textRange, "<this>");
        return textRange.getStartOffset() < i && i < textRange.getEndOffset();
    }

    public static final TextRange getTextRange(PsiChildRange psiChildRange) {
        Intrinsics.checkNotNullParameter(psiChildRange, "<this>");
        if (psiChildRange.isEmpty()) {
            return null;
        }
        PsiElement first = psiChildRange.getFirst();
        Intrinsics.checkNotNull(first);
        int startOffset = getStartOffset(first);
        PsiElement last = psiChildRange.getLast();
        Intrinsics.checkNotNull(last);
        return new TextRange(startOffset, getEndOffset(last));
    }

    public static final String getText(PsiChildRange psiChildRange) {
        Intrinsics.checkNotNullParameter(psiChildRange, "<this>");
        return psiChildRange.isEmpty() ? "" : SequencesKt.joinToString$default(SequencesKt.map(psiChildRange, new Function1<PsiElement, String>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getText.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getText();
            }
        }), "", null, null, 0, null, null, 62, null);
    }

    public static final List<PsiElement> elementsInRange(PsiFile psiFile, TextRange range) {
        TextRange textRange;
        PsiElement psiElementFindFirstLeafWhollyInRange;
        Intrinsics.checkNotNullParameter(psiFile, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        int startOffset = range.getStartOffset();
        ArrayList arrayList = new ArrayList();
        while (startOffset < range.getEndOffset() && (psiElementFindFirstLeafWhollyInRange = findFirstLeafWhollyInRange(psiFile, (textRange = new TextRange(startOffset, range.getEndOffset())))) != null) {
            for (PsiElement psiElement : getParentsWithSelf(psiElementFindFirstLeafWhollyInRange)) {
                PsiElement parent = psiElement.getLightParent();
                if ((psiElement instanceof PsiFile) || !textRange.contains(parent.getTextRange())) {
                    arrayList.add(psiElement);
                    startOffset = getEndOffset(psiElement);
                }
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return arrayList;
    }

    private static final PsiElement findFirstLeafWhollyInRange(PsiFile psiFile, TextRange textRange) {
        PsiElement psiElementFindElementAt = psiFile.findElementAt(textRange.getStartOffset());
        if (psiElementFindElementAt == null) {
            return null;
        }
        TextRange textRange2 = psiElementFindElementAt.getTextRange();
        if (textRange2.getStartOffset() < textRange.getStartOffset()) {
            psiElementFindElementAt = nextLeaf(psiElementFindElementAt, true);
            if (psiElementFindElementAt == null) {
                return null;
            }
            textRange2 = psiElementFindElementAt.getTextRange();
        }
        textRange2.getStartOffset();
        textRange.getStartOffset();
        if (textRange2.getEndOffset() <= textRange.getEndOffset()) {
            return psiElementFindElementAt;
        }
        return null;
    }

    public static final TextRange getTextRangeWithoutComments(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (startsWithComment(psiElement)) {
            return new TextRange(getStartOffsetSkippingComments(psiElement), getEndOffset(psiElement));
        }
        TextRange textRange = psiElement.getTextRange();
        Intrinsics.checkNotNullExpressionValue(textRange, "getTextRange(...)");
        return textRange;
    }

    public static final boolean startsWithComment(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return psiElement.getFirstChild() instanceof PsiComment;
    }

    public static final String getElementTextWithContext(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return org.jetbrains.kotlin.utils.PsiUtilsKt.getElementTextWithContext(psiElement);
    }

    public static final String getTextWithLocation(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return FormatHelper.QUOTE + psiElement.getText() + "' at " + PsiDiagnosticUtils.atLocation(psiElement);
    }

    public static final KtFileAnnotationList replaceFileAnnotationList(KtFile file, KtFileAnnotationList annotationList) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(annotationList, "annotationList");
        if (file.getFileAnnotationList() != null) {
            KtFileAnnotationList fileAnnotationList = file.getFileAnnotationList();
            Intrinsics.checkNotNull(fileAnnotationList);
            PsiElement psiElementReplace = fileAnnotationList.replace(annotationList);
            Intrinsics.checkNotNull(psiElementReplace, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFileAnnotationList");
            return (KtFileAnnotationList) psiElementReplace;
        }
        KtPackageDirective packageDirective = file.getPackageDirective();
        KtImportList ktImportList = null;
        if ((packageDirective != null ? packageDirective.getPackageKeyword() : null) != null) {
            KtPackageDirective packageDirective2 = file.getPackageDirective();
            Intrinsics.checkNotNull(packageDirective2);
            ktImportList = packageDirective2;
        } else if (file.getImportList() != null) {
            KtImportList importList = file.getImportList();
            Intrinsics.checkNotNull(importList);
            ktImportList = importList;
        } else if (CollectionsKt.firstOrNull((List) file.getDeclarations()) != null) {
            ktImportList = (PsiElement) CollectionsKt.first((List) file.getDeclarations());
        }
        if (ktImportList != null) {
            PsiElement psiElementAddBefore = file.addBefore(annotationList, ktImportList);
            Intrinsics.checkNotNull(psiElementAddBefore, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFileAnnotationList");
            return (KtFileAnnotationList) psiElementAddBefore;
        }
        if (file.getLastChild() == null) {
            PsiElement psiElementAdd = file.add(annotationList);
            Intrinsics.checkNotNull(psiElementAdd, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFileAnnotationList");
            return (KtFileAnnotationList) psiElementAdd;
        }
        PsiElement psiElementAddAfter = file.addAfter(annotationList, file.getLastChild());
        Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFileAnnotationList");
        return (KtFileAnnotationList) psiElementAddAfter;
    }

    public static final boolean contains(SearchScope searchScope, PsiElement element) {
        Intrinsics.checkNotNullParameter(searchScope, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        return PsiSearchScopeUtil.isInScope(searchScope, element);
    }

    public static final <E extends PsiElement> SmartPsiElementPointer<E> createSmartPointer(E e) {
        Intrinsics.checkNotNullParameter(e, "<this>");
        SmartPsiElementPointer<E> smartPsiElementPointerCreateSmartPsiElementPointer = SmartPointerManager.getInstance(e.getProject()).createSmartPsiElementPointer(e);
        Intrinsics.checkNotNullExpressionValue(smartPsiElementPointerCreateSmartPsiElementPointer, "createSmartPsiElementPointer(...)");
        return smartPsiElementPointerCreateSmartPsiElementPointer;
    }

    public static final boolean before(PsiElement psiElement, PsiElement element) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        return psiElement.getTextRange().getEndOffset() <= element.getTextRange().getStartOffset();
    }

    public static final /* synthetic */ <T extends PsiElement> T getLastParentOfTypeInRow(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Sequence<PsiElement> parents = getParents(psiElement);
        Intrinsics.needClassReification();
        Object objLastOrNull = SequencesKt.lastOrNull(SequencesKt.takeWhile(parents, new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getLastParentOfTypeInRow.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                return Boolean.valueOf(it instanceof PsiElement);
            }
        }));
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) objLastOrNull;
    }

    public static final /* synthetic */ <T extends PsiElement> T getLastParentOfTypeInRowWithSelf(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Sequence<PsiElement> parentsWithSelf = getParentsWithSelf(psiElement);
        Intrinsics.needClassReification();
        Object objLastOrNull = SequencesKt.lastOrNull(SequencesKt.takeWhile(parentsWithSelf, new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getLastParentOfTypeInRowWithSelf.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                return Boolean.valueOf(it instanceof PsiElement);
            }
        }));
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) objLastOrNull;
    }

    public static final boolean hasExpectModifier(KtModifierListOwner ktModifierListOwner) {
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        return ktModifierListOwner.hasModifier(KtTokens.HEADER_KEYWORD) || ktModifierListOwner.hasModifier(KtTokens.EXPECT_KEYWORD);
    }

    public static final boolean hasExpectModifier(KtModifierList ktModifierList) {
        Intrinsics.checkNotNullParameter(ktModifierList, "<this>");
        return ktModifierList.hasModifier(KtTokens.HEADER_KEYWORD) || ktModifierList.hasModifier(KtTokens.EXPECT_KEYWORD);
    }

    public static final boolean hasActualModifier(KtModifierListOwner ktModifierListOwner) {
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        return ktModifierListOwner.hasModifier(KtTokens.IMPL_KEYWORD) || ktModifierListOwner.hasModifier(KtTokens.ACTUAL_KEYWORD);
    }

    public static final boolean hasActualModifier(KtModifierList ktModifierList) {
        Intrinsics.checkNotNullParameter(ktModifierList, "<this>");
        return ktModifierList.hasModifier(KtTokens.IMPL_KEYWORD) || ktModifierList.hasModifier(KtTokens.ACTUAL_KEYWORD);
    }

    public static final boolean hasSuspendModifier(KtModifierList ktModifierList) {
        Intrinsics.checkNotNullParameter(ktModifierList, "<this>");
        return ktModifierList.hasModifier(KtTokens.SUSPEND_KEYWORD);
    }

    public static final boolean hasFunModifier(KtModifierList ktModifierList) {
        Intrinsics.checkNotNullParameter(ktModifierList, "<this>");
        return ktModifierList.hasModifier(KtTokens.FUN_KEYWORD);
    }

    public static final boolean hasValueModifier(KtModifierList ktModifierList) {
        Intrinsics.checkNotNullParameter(ktModifierList, "<this>");
        return ktModifierList.hasModifier(KtTokens.VALUE_KEYWORD);
    }

    public static final Sequence<ASTNode> children(ASTNode aSTNode) {
        Intrinsics.checkNotNullParameter(aSTNode, "<this>");
        return SequencesKt.generateSequence(aSTNode.getFirstChildNode(), new Function1<ASTNode, ASTNode>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.children.1
            @Override // kotlin.jvm.functions.Function1
            public final ASTNode invoke(ASTNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                return node.getTreeNext();
            }
        });
    }

    public static final Sequence<ASTNode> parents(ASTNode aSTNode) {
        Intrinsics.checkNotNullParameter(aSTNode, "<this>");
        return SequencesKt.generateSequence(aSTNode.getTreeParent(), new Function1<ASTNode, ASTNode>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.parents.1
            @Override // kotlin.jvm.functions.Function1
            public final ASTNode invoke(ASTNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                return node.getTreeParent();
            }
        });
    }

    public static /* synthetic */ Sequence siblings$default(ASTNode aSTNode, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return siblings(aSTNode, z);
    }

    public static final Sequence<ASTNode> siblings(ASTNode aSTNode, boolean z) {
        Intrinsics.checkNotNullParameter(aSTNode, "<this>");
        if (z) {
            return SequencesKt.generateSequence(aSTNode.getTreeNext(), new Function1<ASTNode, ASTNode>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.siblings.2
                @Override // kotlin.jvm.functions.Function1
                public final ASTNode invoke(ASTNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getTreeNext();
                }
            });
        }
        return SequencesKt.generateSequence(aSTNode.getTreePrev(), new Function1<ASTNode, ASTNode>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.siblings.3
            @Override // kotlin.jvm.functions.Function1
            public final ASTNode invoke(ASTNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getTreePrev();
            }
        });
    }

    public static /* synthetic */ Sequence leaves$default(ASTNode aSTNode, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return leaves(aSTNode, z);
    }

    public static final Sequence<ASTNode> leaves(ASTNode aSTNode, boolean z) {
        Intrinsics.checkNotNullParameter(aSTNode, "<this>");
        if (z) {
            return SequencesKt.generateSequence(TreeUtil.nextLeaf(aSTNode), new Function1<ASTNode, ASTNode>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.leaves.1
                @Override // kotlin.jvm.functions.Function1
                public final ASTNode invoke(ASTNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return TreeUtil.nextLeaf(it);
                }
            });
        }
        return SequencesKt.generateSequence(TreeUtil.prevLeaf(aSTNode), new Function1<ASTNode, ASTNode>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.leaves.2
            @Override // kotlin.jvm.functions.Function1
            public final ASTNode invoke(ASTNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return TreeUtil.prevLeaf(it);
            }
        });
    }

    public static final PsiElement closestPsiElement(ASTNode aSTNode) {
        Intrinsics.checkNotNullParameter(aSTNode, "<this>");
        while (aSTNode.getPsi() == null) {
            aSTNode = aSTNode.getTreeParent();
            Intrinsics.checkNotNullExpressionValue(aSTNode, "getTreeParent(...)");
        }
        return aSTNode.getPsi();
    }

    public static final KtFile getContainingKtFile(LazyParseablePsiElement lazyParseablePsiElement) {
        Intrinsics.checkNotNullParameter(lazyParseablePsiElement, "<this>");
        PsiFile containingFile = lazyParseablePsiElement.getContainingFile();
        if (containingFile instanceof KtFile) {
            return (KtFile) containingFile;
        }
        throw new IllegalStateException("KtElement not inside KtFile: " + containingFile + " with text \"" + ((containingFile == null || !containingFile.isValid()) ? "" : containingFile.getText()) + "\" for element " + lazyParseablePsiElement + " of type " + lazyParseablePsiElement.getClass() + " node = " + lazyParseablePsiElement.getNode());
    }

    public static final boolean isNull(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        return (ktExpression instanceof KtConstantExpression) && Intrinsics.areEqual(((KtConstantExpression) ktExpression).getNode().getElementType(), KtNodeTypes.NULL);
    }

    public static final PsiElement unwrapParenthesesLabelsAndAnnotations(PsiElement psiElement) {
        while (true) {
            if (psiElement instanceof KtParenthesizedExpression) {
                psiElement = ((KtParenthesizedExpression) psiElement).getExpression();
            } else if (psiElement instanceof KtLabeledExpression) {
                psiElement = ((KtLabeledExpression) psiElement).getBaseExpression();
            } else {
                if (!(psiElement instanceof KtAnnotatedExpression)) {
                    return psiElement;
                }
                psiElement = ((KtAnnotatedExpression) psiElement).getBaseExpression();
            }
        }
    }

    public static final PsiElement unwrapParenthesesLabelsAndAnnotationsDeeply(PsiElement psiElement) {
        PsiElement psiElementUnwrapParenthesesLabelsAndAnnotations;
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        do {
            PsiElement parent = psiElement.getLightParent();
            psiElementUnwrapParenthesesLabelsAndAnnotations = parent != null ? unwrapParenthesesLabelsAndAnnotations(parent) : null;
            psiElement = psiElement.getLightParent();
            Intrinsics.checkNotNullExpressionValue(psiElement, "getParent(...)");
        } while (!Intrinsics.areEqual(psiElementUnwrapParenthesesLabelsAndAnnotations, psiElement));
        return psiElementUnwrapParenthesesLabelsAndAnnotations;
    }

    public static final /* synthetic */ <T extends PsiElement> T getParentOfTypeAndBranch(PsiElement psiElement, boolean z, Function1<? super T, ? extends PsiElement> branch) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(branch, "branch");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, z);
        if (parentOfType != null) {
            return (T) getIfChildIsInBranch(parentOfType, psiElement, branch);
        }
        return null;
    }

    public static final /* synthetic */ <T extends PsiElement> T getParentOfTypeAndBranches(PsiElement psiElement, boolean z, Function1<? super T, ? extends Iterable<? extends PsiElement>> branches) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(branches, "branches");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, PsiElement.class, z);
        if (parentOfType != null) {
            return (T) getIfChildIsInBranches(parentOfType, psiElement, branches);
        }
        return null;
    }

    public static final boolean isInsideOf(PsiElement psiElement, Iterable<? extends PsiElement> elements) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        if ((elements instanceof Collection) && ((Collection) elements).isEmpty()) {
            return false;
        }
        Iterator<? extends PsiElement> it = elements.iterator();
        while (it.hasNext()) {
            if (isAncestor$default(it.next(), psiElement, false, 2, null)) {
                return true;
            }
        }
        return false;
    }

    public static final /* synthetic */ <T extends PsiElement> void forEachDescendantOfType(PsiElement psiElement, final Function1<? super T, Unit> action) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        checkDecompiledText(psiElement);
        Intrinsics.needClassReification();
        psiElement.accept(new PsiRecursiveElementVisitor() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt$forEachDescendantOfType$$inlined$forEachDescendantOfType$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                super.visitElement(element);
                Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                if (element instanceof PsiElement) {
                    action.invoke(element);
                }
            }
        });
    }

    public static final /* synthetic */ <T extends PsiElement> boolean anyDescendantOfType(PsiElement psiElement, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        checkDecompiledText(psiElement);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$anyDescendantOfType$$inlined$findDescendantOfType$1(predicate, objectRef));
        return ((PsiElement) objectRef.element) != null;
    }

    public static final /* synthetic */ <T extends PsiElement> boolean anyDescendantOfType(PsiElement psiElement, Function1<? super PsiElement, Boolean> canGoInside, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(canGoInside, "canGoInside");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        checkDecompiledText(psiElement);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$anyDescendantOfType$$inlined$findDescendantOfType$2(predicate, objectRef, canGoInside));
        return ((PsiElement) objectRef.element) != null;
    }

    public static final /* synthetic */ <T extends PsiElement> T findDescendantOfType(PsiElement psiElement, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        checkDecompiledText(psiElement);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$findDescendantOfType$$inlined$findDescendantOfType$1(predicate, objectRef));
        return (T) objectRef.element;
    }

    public static final /* synthetic */ <T extends PsiElement> List<T> collectDescendantsOfType(PsiElement psiElement, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        Intrinsics.needClassReification();
        PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$1 psiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$1 = new PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$1(predicate, arrayList);
        checkDecompiledText(psiElement);
        Intrinsics.needClassReification();
        psiElement.accept(new PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$2(psiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfType$1));
        return arrayList;
    }
}
