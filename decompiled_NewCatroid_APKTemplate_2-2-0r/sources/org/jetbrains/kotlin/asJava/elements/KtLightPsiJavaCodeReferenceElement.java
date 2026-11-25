package org.jetbrains.kotlin.asJava.elements;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import javax.swing.Icon;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: KtLightPsiJavaCodeReferenceElement.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ä\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\f\n\u0000\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B)\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u000b2\r\b\u0001\u0010\f\u001a\u00070\r¢\u0006\u0002\b\u000eH\u0096\u0001J\u0018\u0010\u000f\u001a\u00020\u000b2\r\b\u0001\u0010\f\u001a\u00070\r¢\u0006\u0002\b\u000eH\u0096\u0001J \u0010\u0010\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000eH\u0096\u0001J1\u0010\u0012\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0013\u001a\t\u0018\u00010\u0001¢\u0006\u0002\b\u0014H\u0096\u0001J1\u0010\u0015\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0013\u001a\t\u0018\u00010\u0001¢\u0006\u0002\b\u0014H\u0096\u0001J1\u0010\u0016\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\u000e\u0010\f\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\u000e\u0010\u0013\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0096\u0001JA\u0010\u0017\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\u000e\u0010\f\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\u000e\u0010\u0013\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\u000e\u0010\u0018\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0096\u0001J?\u0010\u0019\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000e2\r\b\u0001\u0010\u0013\u001a\u00070\u0001¢\u0006\u0002\b\u000e2\u000e\u0010\u0018\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0096\u0001J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J \u0010\u001e\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000eH\u0096\u0001J\u0018\u0010\u001f\u001a\u00020\u000b2\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000eH\u0097\u0001J\t\u0010 \u001a\u00020\u000bH\u0097\u0001J\u0011\u0010!\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0096\u0001J\t\u0010\"\u001a\u00020\u000bH\u0096\u0001J)\u0010#\u001a\u00020\u000b2\u000e\u0010\f\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\u000e\u0010\u0013\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0096\u0001J\u0018\u0010$\u001a\t\u0018\u00010\u0001¢\u0006\u0002\b\u00142\u0006\u0010\f\u001a\u00020%H\u0097\u0001J\u0018\u0010&\u001a\t\u0018\u00010\u0002¢\u0006\u0002\b\u00142\u0006\u0010\f\u001a\u00020%H\u0097\u0001J\u0013\u0010'\u001a\f0\b¢\u0006\u0002\b\u000e¢\u0006\u0002\b(H\u0097\u0001J<\u0010)\u001a0\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00010\u0001 \u0011*\u0017\u0012\u000e\b\u0001\u0012\n \u0011*\u0004\u0018\u00010\u00010\u00010*¢\u0006\u0002\b\u000e0*¢\u0006\u0002\b\u000eH\u0097\u0001¢\u0006\u0002\u0010+J\u0011\u0010,\u001a\n \u0011*\u0004\u0018\u00010-0-H\u0097\u0001J\u0010\u0010.\u001a\t\u0018\u00010\u0001¢\u0006\u0002\b\u0014H\u0097\u0001JD\u0010/\u001a\t\u0018\u0001H0¢\u0006\u0002\b\u0014\"\u0010\b\u0000\u00100*\n \u0011*\u0004\u0018\u000101012\u001b\b\u0001\u0010\f\u001a\u0015\u0012\f\u0012\n \u0011*\u0004\u0018\u0001H0H002¢\u0006\u0002\b\u000eH\u0097\u0001¢\u0006\u0002\u00103J\u000e\u00104\u001a\u00070\u0001¢\u0006\u0002\b\u000eH\u0097\u0001J\u0011\u00105\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0097\u0001J\u001b\u00106\u001a\n \u0011*\u0004\u0018\u000107072\b\b\u0001\u0010\f\u001a\u00020%H\u0096\u0001J\u000e\u00108\u001a\u000709¢\u0006\u0002\b\u000eH\u0097\u0001J\u0011\u0010:\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0097\u0001J\u0011\u0010;\u001a\n \u0011*\u0004\u0018\u00010<0<H\u0097\u0001J\u0011\u0010=\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0097\u0001J\u0011\u0010>\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0097\u0001J\u0011\u0010?\u001a\n \u0011*\u0004\u0018\u00010@0@H\u0097\u0001J\u0011\u0010A\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0097\u0001J\n\u0010B\u001a\u0004\u0018\u00010CH\u0016J\u0011\u0010D\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0097\u0001J\u0011\u0010E\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0097\u0001J\u000e\u0010F\u001a\u00070G¢\u0006\u0002\b\u000eH\u0097\u0001J\n\u0010H\u001a\u0004\u0018\u00010\bH\u0016J\n\u0010I\u001a\u0004\u0018\u00010\u0001H\u0016J\u000e\u0010J\u001a\u00070K¢\u0006\u0002\b\u000eH\u0097\u0001J\u0010\u0010L\u001a\t\u0018\u00010\u0002¢\u0006\u0002\b\u0014H\u0097\u0001J\n\u0010M\u001a\u0004\u0018\u00010\bH\u0016J\n\u0010N\u001a\u0004\u0018\u00010\u0001H\u0016J<\u0010O\u001a0\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00020\u0002 \u0011*\u0017\u0012\u000e\b\u0001\u0012\n \u0011*\u0004\u0018\u00010\u00020\u00020*¢\u0006\u0002\b\u000e0*¢\u0006\u0002\b\u000eH\u0097\u0001¢\u0006\u0002\u0010PJ\u000e\u0010Q\u001a\u00070R¢\u0006\u0002\b\u000eH\u0097\u0001J\t\u0010S\u001a\u00020%H\u0097\u0001J\u001b\u0010T\u001a\u0014 \u0011*\t\u0018\u00010\b¢\u0006\u0002\b(0\b¢\u0006\u0002\b(H\u0097\u0001J\t\u0010U\u001a\u00020%H\u0097\u0001J\t\u0010V\u001a\u00020%H\u0097\u0001J\u0011\u0010W\u001a\n \u0011*\u0004\u0018\u00010K0KH\u0097\u0001J\u0013\u0010X\u001a\b\u0012\u0004\u0012\u00020Y0*H\u0016¢\u0006\u0002\u0010ZJ\u000e\u0010[\u001a\u00070\\¢\u0006\u0002\b\u000eH\u0097\u0001JD\u0010]\u001a\t\u0018\u0001H0¢\u0006\u0002\b\u0014\"\u0010\b\u0000\u00100*\n \u0011*\u0004\u0018\u000101012\u001b\b\u0001\u0010\f\u001a\u0015\u0012\f\u0012\n \u0011*\u0004\u0018\u0001H0H002¢\u0006\u0002\b\u000eH\u0097\u0001¢\u0006\u0002\u00103J \u0010^\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\r\b\u0001\u0010\f\u001a\u00070\b¢\u0006\u0002\b\u000eH\u0096\u0001J\u0019\u0010_\u001a\u00020\u001d2\u000e\u0010\f\u001a\n \u0011*\u0004\u0018\u00010\u00010\u0001H\u0097\u0001J\t\u0010`\u001a\u00020\u001dH\u0097\u0001J\b\u0010a\u001a\u00020\u001dH\u0016J\u0018\u0010b\u001a\u00020\u001d2\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000eH\u0096\u0001J\t\u0010c\u001a\u00020\u001dH\u0096\u0001J\t\u0010d\u001a\u00020\u001dH\u0097\u0001J\t\u0010e\u001a\u00020\u001dH\u0097\u0001J\u001b\u0010f\u001a\b\u0012\u0004\u0012\u00020\u001b0*2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016¢\u0006\u0002\u0010gJG\u0010h\u001a\u00020\u001d2\r\b\u0001\u0010\f\u001a\u00070i¢\u0006\u0002\b\u000e2\r\b\u0001\u0010\u0013\u001a\u00070j¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0018\u001a\t\u0018\u00010\u0001¢\u0006\u0002\b\u00142\r\b\u0001\u0010k\u001a\u00070\u0001¢\u0006\u0002\b\u000eH\u0096\u0001J\u0010\u0010l\u001a\u00020\u000b2\u0006\u0010m\u001a\u00020iH\u0016JN\u0010n\u001a\u00020\u000b\"\u0010\b\u0000\u00100*\n \u0011*\u0004\u0018\u000101012\u001b\b\u0001\u0010\f\u001a\u0015\u0012\f\u0012\n \u0011*\u0004\u0018\u0001H0H002¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0013\u001a\t\u0018\u0001H0¢\u0006\u0002\b\u0014H\u0096\u0001¢\u0006\u0002\u0010oJN\u0010p\u001a\u00020\u000b\"\u0010\b\u0000\u00100*\n \u0011*\u0004\u0018\u000101012\u001b\b\u0001\u0010\f\u001a\u0015\u0012\f\u0012\n \u0011*\u0004\u0018\u0001H0H002¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0013\u001a\t\u0018\u0001H0¢\u0006\u0002\b\u0014H\u0096\u0001¢\u0006\u0002\u0010oJ \u0010q\u001a\n \u0011*\u0004\u0018\u00010\u00010\u00012\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000eH\u0096\u0001J\u0010\u0010r\u001a\t\u0018\u00010\u0001¢\u0006\u0002\b\u0014H\u0097\u0001J\u0011\u0010s\u001a\u00020\u001d2\u0006\u0010\f\u001a\u00020tH\u0097\u0001J\u001d\u0010u\u001a\u00020\u001d2\u0012\b\u0001\u0010\f\u001a\f0v¢\u0006\u0002\b\u000e¢\u0006\u0002\bwH\u0097\u0001J\u0018\u0010u\u001a\u00020\u001d2\r\b\u0001\u0010\f\u001a\u00070\u0001¢\u0006\u0002\b\u000eH\u0097\u0001J\u000e\u0010x\u001a\u00070y¢\u0006\u0002\b\u000eH\u0097\u0001R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006z"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightPsiJavaCodeReferenceElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "ktElement", "reference", "Lkotlin/Function0;", "customReferenceName", "", "(Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function0;Ljava/lang/String;)V", "accept", "", "p0", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "Lorg/jetbrains/annotations/NotNull;", "acceptChildren", ProductAction.ACTION_ADD, JvmProtoBufUtil.PLATFORM_TYPE_ID, "addAfter", "p1", "Lorg/jetbrains/annotations/Nullable;", "addBefore", "addRange", "addRangeAfter", "p2", "addRangeBefore", "advancedResolve", "Lorg/jetbrains/kotlin/com/intellij/psi/JavaResolveResult;", "incompleteCode", "", "bindToElement", "checkAdd", "checkDelete", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "delete", "deleteChildRange", "findElementAt", "", "findReferenceAt", "getCanonicalText", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "getChildren", "", "()[Lcom/intellij/psi/PsiElement;", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getContext", "getCopyableUserData", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "(Lcom/intellij/openapi/util/Key;)Ljava/lang/Object;", "getElement", "getFirstChild", "getIcon", "Ljavax/swing/Icon;", "getLanguage", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "getLastChild", "getManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "getNavigationElement", "getNextSibling", "getNode", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "getOriginalElement", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceParameterList;", "getParent", "getPrevSibling", "getProject", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getQualifiedName", "getQualifier", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getReference", "getReferenceName", "getReferenceNameElement", "getReferences", "()[Lcom/intellij/psi/PsiReference;", "getResolveScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getStartOffsetInParent", "getText", "getTextLength", "getTextOffset", "getTextRange", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "()[Lcom/intellij/psi/PsiType;", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "getUserData", "handleElementRename", "isEquivalentTo", "isPhysical", "isQualified", "isReferenceTo", "isSoft", "isValid", "isWritable", "multiResolve", "(Z)[Lcom/intellij/psi/JavaResolveResult;", "processDeclarations", "Lorg/jetbrains/kotlin/com/intellij/psi/scope/PsiScopeProcessor;", "Lorg/jetbrains/kotlin/com/intellij/psi/ResolveState;", "p3", "processVariants", "processor", "putCopyableUserData", "(Lcom/intellij/openapi/util/Key;Ljava/lang/Object;)V", "putUserData", SchemaSymbols.ATTVAL_REPLACE, "resolve", "textContains", "", "textMatches", "", "Lorg/jetbrains/annotations/NonNls;", "textToCharArray", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightPsiJavaCodeReferenceElement implements PsiElement, PsiJavaCodeReferenceElement, PsiReference {
    private final /* synthetic */ LazyPsiReferenceDelegate $$delegate_1;
    private final String customReferenceName;
    private final PsiElement ktElement;

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        this.ktElement.accept(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void acceptChildren(PsiElementVisitor p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        this.ktElement.acceptChildren(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.ktElement.add(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement p0, PsiElement p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.ktElement.addAfter(p0, p1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement p0, PsiElement p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.ktElement.addBefore(p0, p1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRange(PsiElement p0, PsiElement p1) {
        return this.ktElement.addRange(p0, p1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRangeAfter(PsiElement p0, PsiElement p1, PsiElement p2) {
        return this.ktElement.addRangeAfter(p0, p1, p2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRangeBefore(PsiElement p0, PsiElement p1, PsiElement p2) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return this.ktElement.addRangeBefore(p0, p1, p2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.$$delegate_1.bindToElement(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    @Deprecated(message = "Deprecated in Java")
    public void checkAdd(PsiElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        this.ktElement.checkAdd(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    @Deprecated(message = "Deprecated in Java")
    public void checkDelete() {
        this.ktElement.checkDelete();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return this.ktElement.copy();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() {
        this.ktElement.delete();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void deleteChildRange(PsiElement p0, PsiElement p1) {
        this.ktElement.deleteChildRange(p0, p1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int p0) {
        return this.ktElement.findElementAt(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference findReferenceAt(int p0) {
        return this.ktElement.findReferenceAt(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        return this.$$delegate_1.getCanonicalText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        return this.ktElement.getChildren();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return this.ktElement.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return this.ktElement.getContext();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public <T> T getCopyableUserData(Key<T> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return (T) this.ktElement.getCopyableUserData(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        return this.$$delegate_1.getPsiElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getFirstChild() {
        return this.ktElement.getFirstChild();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
    public Icon getIcon(int p0) {
        return this.ktElement.getIcon(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        return this.ktElement.getLanguage();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getLastChild() {
        return this.ktElement.getLastChild();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        return this.ktElement.getManager();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        return this.ktElement.getNavigationElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNextSibling() {
        return this.ktElement.getNextSibling();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public ASTNode getNode() {
        return this.ktElement.getNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getOriginalElement() {
        return this.ktElement.getOriginalElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiReferenceParameterList getParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.ktElement.getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getPrevSibling() {
        return this.ktElement.getPrevSibling();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Project getProject() {
        return this.ktElement.getProject();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public String getQualifiedName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    public PsiElement getQualifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return this.$$delegate_1.getRangeInElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return this.ktElement.getReference();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiElement getReferenceNameElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        return this.ktElement.getReferences();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public GlobalSearchScope getResolveScope() {
        return this.ktElement.getResolveScope();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return this.ktElement.getStartOffsetInParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.ktElement.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextLength() {
        return this.ktElement.getTextLength();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return this.ktElement.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return this.ktElement.getTextRange();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        return this.ktElement.getUseScope();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> T getUserData(Key<T> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return (T) this.ktElement.getUserData(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.$$delegate_1.handleElementRename(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement p0) {
        return this.ktElement.isEquivalentTo(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return this.ktElement.getIsPhysical();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public boolean isQualified() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.$$delegate_1.isReferenceTo(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return this.$$delegate_1.isSoft();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return this.ktElement.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return this.ktElement.isWritable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor p0, ResolveState p1, PsiElement p2, PsiElement p3) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p3, "p3");
        return this.ktElement.processDeclarations(p0, p1, p2, p3);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public void processVariants(PsiScopeProcessor processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public <T> void putCopyableUserData(Key<T> p0, T t) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        this.ktElement.putCopyableUserData(p0, t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> void putUserData(Key<T> p0, T t) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        this.ktElement.putUserData(p0, t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.ktElement.replace(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        return this.$$delegate_1.resolve();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textContains(char p0) {
        return this.ktElement.textContains(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.ktElement.textMatches(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.ktElement.textMatches(p0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        return this.ktElement.textToCharArray();
    }

    public KtLightPsiJavaCodeReferenceElement(PsiElement ktElement, Function0<? extends PsiReference> reference, String str) {
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        Intrinsics.checkNotNullParameter(reference, "reference");
        this.ktElement = ktElement;
        this.customReferenceName = str;
        this.$$delegate_1 = new LazyPsiReferenceDelegate(ktElement, reference);
    }

    public /* synthetic */ KtLightPsiJavaCodeReferenceElement(PsiElement psiElement, Function0 function0, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(psiElement, function0, (i & 4) != 0 ? null : str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
    public JavaResolveResult advancedResolve(boolean incompleteCode) {
        JavaResolveResult EMPTY = JavaResolveResult.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
        return EMPTY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
    /* JADX INFO: renamed from: getReferenceName, reason: from getter */
    public String getCustomReferenceName() {
        return this.customReferenceName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
    public PsiType[] getTypeParameters() {
        return new PsiType[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public JavaResolveResult[] multiResolve(boolean z) {
        return new JavaResolveResult[0];
    }
}
