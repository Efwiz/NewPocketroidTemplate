package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import javax.swing.Icon;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: KotlinSuperTypeListBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001 B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0016J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u0016H\u0016J\u0017\u0010\u001d\u001a\f\u0012\b\u0012\u00060\u0010R\u00020\u00000\u000fH\u0016¢\u0006\u0002\u0010\u0012J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R%\u0010\u000e\u001a\f\u0012\b\u0012\u00060\u0010R\u00020\u00000\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KotlinSuperTypeListBuilder;", "Lorg/jetbrains/kotlin/asJava/classes/KotlinLightReferenceListBuilder;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtSuperTypeList;", "manager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "language", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "role", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList$Role;", "(Lcom/intellij/psi/PsiClass;Lorg/jetbrains/kotlin/psi/KtSuperTypeList;Lcom/intellij/psi/PsiManager;Lcom/intellij/lang/Language;Lcom/intellij/psi/PsiReferenceList$Role;)V", "myKotlinOrigin", "referenceElementsCache", "", "Lorg/jetbrains/kotlin/asJava/classes/KotlinSuperTypeListBuilder$KotlinSuperTypeReference;", "getReferenceElementsCache", "()[Lorg/jetbrains/kotlin/asJava/classes/KotlinSuperTypeListBuilder$KotlinSuperTypeReference;", "referenceElementsCache$delegate", "Lkotlin/Lazy;", ProductAction.ACTION_ADD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "addMarkerInterfaceIfNeeded", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getParent", "getReferenceElements", "tryResolveMarkerInterfaceFQName", "", "KotlinSuperTypeReference", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinSuperTypeListBuilder extends KotlinLightReferenceListBuilder {
    private final KtSuperTypeList myKotlinOrigin;
    private final PsiClass parent;

    /* JADX INFO: renamed from: referenceElementsCache$delegate, reason: from kotlin metadata */
    private final Lazy referenceElementsCache;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinSuperTypeListBuilder(PsiClass parent, KtSuperTypeList ktSuperTypeList, PsiManager manager, Language language, PsiReferenceList.Role role) {
        super(manager, language, role);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(language, "language");
        Intrinsics.checkNotNullParameter(role, "role");
        this.parent = parent;
        this.myKotlinOrigin = ktSuperTypeList;
        this.referenceElementsCache = ImplUtilsKt.lazyPub(new Function0<KotlinSuperTypeReference[]>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinSuperTypeListBuilder$referenceElementsCache$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinSuperTypeListBuilder.KotlinSuperTypeReference[] invoke() {
                PsiJavaCodeReferenceElement[] referenceElements = super/*org.jetbrains.kotlin.asJava.classes.KotlinLightReferenceListBuilder*/.getReferenceElements();
                Intrinsics.checkNotNullExpressionValue(referenceElements, "access$getReferenceElements$s305576167(...)");
                PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr = referenceElements;
                KotlinSuperTypeListBuilder kotlinSuperTypeListBuilder = this.this$0;
                ArrayList arrayList = new ArrayList(psiJavaCodeReferenceElementArr.length);
                for (PsiJavaCodeReferenceElement psiJavaCodeReferenceElement : psiJavaCodeReferenceElementArr) {
                    Intrinsics.checkNotNull(psiJavaCodeReferenceElement);
                    arrayList.add(new KotlinSuperTypeListBuilder.KotlinSuperTypeReference(kotlinSuperTypeListBuilder, psiJavaCodeReferenceElement));
                }
                return (KotlinSuperTypeListBuilder.KotlinSuperTypeReference[]) arrayList.toArray(new KotlinSuperTypeListBuilder.KotlinSuperTypeReference[0]);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.parent;
    }

    /* JADX INFO: compiled from: KotlinSuperTypeListBuilder.kt */
    @Metadata(d1 = {"\u0000ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\f\n\u0000\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0018\u0010\n\u001a\u00020\u000b2\r\b\u0001\u0010\f\u001a\u00070\r¢\u0006\u0002\b\u000eH\u0096\u0001J\u0018\u0010\u000f\u001a\u00020\u000b2\r\b\u0001\u0010\f\u001a\u00070\r¢\u0006\u0002\b\u000eH\u0096\u0001J \u0010\u0010\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000eH\u0096\u0001J1\u0010\u0013\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0014\u001a\t\u0018\u00010\u0011¢\u0006\u0002\b\u0015H\u0096\u0001J1\u0010\u0016\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0014\u001a\t\u0018\u00010\u0011¢\u0006\u0002\b\u0015H\u0096\u0001J1\u0010\u0017\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\u000e\u0010\f\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\u000e\u0010\u0014\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0096\u0001JA\u0010\u0018\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\u000e\u0010\f\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\u000e\u0010\u0014\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\u000e\u0010\u0019\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0096\u0001J?\u0010\u001a\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000e2\r\b\u0001\u0010\u0014\u001a\u00070\u0011¢\u0006\u0002\b\u000e2\u000e\u0010\u0019\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0096\u0001J\u0016\u0010\u001b\u001a\u00070\u001c¢\u0006\u0002\b\u000e2\u0006\u0010\f\u001a\u00020\u001dH\u0097\u0001J \u0010\u001e\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000eH\u0096\u0001J\u0018\u0010\u001f\u001a\u00020\u000b2\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000eH\u0097\u0001J\t\u0010 \u001a\u00020\u000bH\u0097\u0001J\u0011\u0010!\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0096\u0001J\b\u0010\"\u001a\u00020\u000bH\u0016J)\u0010#\u001a\u00020\u000b2\u000e\u0010\f\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\u000e\u0010\u0014\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0096\u0001J\u0018\u0010$\u001a\t\u0018\u00010\u0011¢\u0006\u0002\b\u00152\u0006\u0010\f\u001a\u00020%H\u0097\u0001J\u0018\u0010&\u001a\t\u0018\u00010'¢\u0006\u0002\b\u00152\u0006\u0010\f\u001a\u00020%H\u0097\u0001J\u0013\u0010(\u001a\f0)¢\u0006\u0002\b\u000e¢\u0006\u0002\b*H\u0097\u0001J<\u0010+\u001a0\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00110\u0011 \u0012*\u0017\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010\u00110\u00110,¢\u0006\u0002\b\u000e0,¢\u0006\u0002\b\u000eH\u0097\u0001¢\u0006\u0002\u0010-J\u0011\u0010.\u001a\n \u0012*\u0004\u0018\u00010/0/H\u0097\u0001J\u0010\u00100\u001a\t\u0018\u00010\u0011¢\u0006\u0002\b\u0015H\u0097\u0001JD\u00101\u001a\t\u0018\u0001H2¢\u0006\u0002\b\u0015\"\u0010\b\u0000\u00102*\n \u0012*\u0004\u0018\u000103032\u001b\b\u0001\u0010\f\u001a\u0015\u0012\f\u0012\n \u0012*\u0004\u0018\u0001H2H204¢\u0006\u0002\b\u000eH\u0097\u0001¢\u0006\u0002\u00105J\u000e\u00106\u001a\u00070\u0011¢\u0006\u0002\b\u000eH\u0097\u0001J\u0011\u00107\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0097\u0001J\u001b\u00108\u001a\n \u0012*\u0004\u0018\u000109092\b\b\u0001\u0010\f\u001a\u00020%H\u0096\u0001J\u000e\u0010:\u001a\u00070;¢\u0006\u0002\b\u000eH\u0097\u0001J\u0011\u0010<\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0097\u0001J\u0011\u0010=\u001a\n \u0012*\u0004\u0018\u00010>0>H\u0097\u0001J\u0011\u0010?\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0097\u0001J\u0011\u0010@\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0097\u0001J\u0011\u0010A\u001a\n \u0012*\u0004\u0018\u00010B0BH\u0097\u0001J\u0011\u0010C\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0097\u0001J\u0010\u0010D\u001a\t\u0018\u00010E¢\u0006\u0002\b\u0015H\u0097\u0001J\b\u0010F\u001a\u00020GH\u0016J\u0011\u0010H\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0097\u0001J\u000e\u0010I\u001a\u00070J¢\u0006\u0002\b\u000eH\u0097\u0001J\u0011\u0010K\u001a\n \u0012*\u0004\u0018\u00010)0)H\u0096\u0001J\u0010\u0010L\u001a\t\u0018\u00010\u0011¢\u0006\u0002\b\u0015H\u0097\u0001J\u000e\u0010M\u001a\u00070N¢\u0006\u0002\b\u000eH\u0097\u0001J\u0010\u0010O\u001a\t\u0018\u00010'¢\u0006\u0002\b\u0015H\u0097\u0001J\u0015\u0010P\u001a\u000e\u0018\u00010)¢\u0006\u0002\b\u0015¢\u0006\u0002\b*H\u0097\u0001J\u0010\u0010Q\u001a\t\u0018\u00010\u0011¢\u0006\u0002\b\u0015H\u0097\u0001J<\u0010R\u001a0\u0012\f\u0012\n \u0012*\u0004\u0018\u00010'0' \u0012*\u0017\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010'0'0,¢\u0006\u0002\b\u000e0,¢\u0006\u0002\b\u000eH\u0097\u0001¢\u0006\u0002\u0010SJ\u000e\u0010T\u001a\u00070U¢\u0006\u0002\b\u000eH\u0097\u0001J\t\u0010V\u001a\u00020%H\u0097\u0001J\u001b\u0010W\u001a\u0014 \u0012*\t\u0018\u00010)¢\u0006\u0002\b*0)¢\u0006\u0002\b*H\u0097\u0001J\t\u0010X\u001a\u00020%H\u0097\u0001J\t\u0010Y\u001a\u00020%H\u0097\u0001J\u0011\u0010Z\u001a\n \u0012*\u0004\u0018\u00010N0NH\u0097\u0001J<\u0010[\u001a0\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\\0\\ \u0012*\u0017\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010\\0\\0,¢\u0006\u0002\b\u000e0,¢\u0006\u0002\b\u000eH\u0096\u0001¢\u0006\u0002\u0010]J\u000e\u0010^\u001a\u00070_¢\u0006\u0002\b\u000eH\u0097\u0001JD\u0010`\u001a\t\u0018\u0001H2¢\u0006\u0002\b\u0015\"\u0010\b\u0000\u00102*\n \u0012*\u0004\u0018\u000103032\u001b\b\u0001\u0010\f\u001a\u0015\u0012\f\u0012\n \u0012*\u0004\u0018\u0001H2H204¢\u0006\u0002\b\u000eH\u0097\u0001¢\u0006\u0002\u00105J \u0010a\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\r\b\u0001\u0010\f\u001a\u00070)¢\u0006\u0002\b\u000eH\u0096\u0001J\u0019\u0010b\u001a\u00020\u001d2\u000e\u0010\f\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0097\u0001J\t\u0010c\u001a\u00020\u001dH\u0097\u0001J\t\u0010d\u001a\u00020\u001dH\u0096\u0001J\u0018\u0010e\u001a\u00020\u001d2\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000eH\u0096\u0001J\t\u0010f\u001a\u00020\u001dH\u0096\u0001J\t\u0010g\u001a\u00020\u001dH\u0097\u0001J\t\u0010h\u001a\u00020\u001dH\u0097\u0001JD\u0010i\u001a0\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u001c0\u001c \u0012*\u0017\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010\u001c0\u001c0,¢\u0006\u0002\b\u000e0,¢\u0006\u0002\b\u000e2\u0006\u0010\f\u001a\u00020\u001dH\u0096\u0001¢\u0006\u0002\u0010jJG\u0010k\u001a\u00020\u001d2\r\b\u0001\u0010\f\u001a\u00070l¢\u0006\u0002\b\u000e2\r\b\u0001\u0010\u0014\u001a\u00070m¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0019\u001a\t\u0018\u00010\u0011¢\u0006\u0002\b\u00152\r\b\u0001\u0010n\u001a\u00070\u0011¢\u0006\u0002\b\u000eH\u0096\u0001J\u0018\u0010o\u001a\u00020\u000b2\r\b\u0001\u0010\f\u001a\u00070l¢\u0006\u0002\b\u000eH\u0096\u0001JN\u0010p\u001a\u00020\u000b\"\u0010\b\u0000\u00102*\n \u0012*\u0004\u0018\u000103032\u001b\b\u0001\u0010\f\u001a\u0015\u0012\f\u0012\n \u0012*\u0004\u0018\u0001H2H204¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0014\u001a\t\u0018\u0001H2¢\u0006\u0002\b\u0015H\u0096\u0001¢\u0006\u0002\u0010qJN\u0010r\u001a\u00020\u000b\"\u0010\b\u0000\u00102*\n \u0012*\u0004\u0018\u000103032\u001b\b\u0001\u0010\f\u001a\u0015\u0012\f\u0012\n \u0012*\u0004\u0018\u0001H2H204¢\u0006\u0002\b\u000e2\u000f\b\u0001\u0010\u0014\u001a\t\u0018\u0001H2¢\u0006\u0002\b\u0015H\u0096\u0001¢\u0006\u0002\u0010qJ \u0010s\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00112\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000eH\u0096\u0001J\u0010\u0010t\u001a\t\u0018\u00010\u0011¢\u0006\u0002\b\u0015H\u0097\u0001J\u0011\u0010u\u001a\u00020\u001d2\u0006\u0010\f\u001a\u00020vH\u0097\u0001J\u001d\u0010w\u001a\u00020\u001d2\u0012\b\u0001\u0010\f\u001a\f0x¢\u0006\u0002\b\u000e¢\u0006\u0002\byH\u0097\u0001J\u0018\u0010w\u001a\u00020\u001d2\r\b\u0001\u0010\f\u001a\u00070\u0011¢\u0006\u0002\b\u000eH\u0097\u0001J\u000e\u0010z\u001a\u00070{¢\u0006\u0002\b\u000eH\u0097\u0001R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0004\u001a\u0004\u0018\u00010\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006|"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KotlinSuperTypeListBuilder$KotlinSuperTypeReference;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/asJava/classes/KotlinSuperTypeListBuilder;Lcom/intellij/psi/PsiJavaCodeReferenceElement;)V", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;", "kotlinOrigin$delegate", "Lkotlin/Lazy;", "accept", "", "p0", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "Lorg/jetbrains/annotations/NotNull;", "acceptChildren", ProductAction.ACTION_ADD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "addAfter", "p1", "Lorg/jetbrains/annotations/Nullable;", "addBefore", "addRange", "addRangeAfter", "p2", "addRangeBefore", "advancedResolve", "Lorg/jetbrains/kotlin/com/intellij/psi/JavaResolveResult;", "", "bindToElement", "checkAdd", "checkDelete", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "delete", "deleteChildRange", "findElementAt", "", "findReferenceAt", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "getCanonicalText", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "getChildren", "", "()[Lcom/intellij/psi/PsiElement;", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getContext", "getCopyableUserData", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "(Lcom/intellij/openapi/util/Key;)Ljava/lang/Object;", "getElement", "getFirstChild", "getIcon", "Ljavax/swing/Icon;", "getLanguage", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "getLastChild", "getManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "getNavigationElement", "getNextSibling", "getNode", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "getOriginalElement", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceParameterList;", "getParent", "Lorg/jetbrains/kotlin/asJava/classes/KotlinSuperTypeListBuilder;", "getPrevSibling", "getProject", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getQualifiedName", "getQualifier", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getReference", "getReferenceName", "getReferenceNameElement", "getReferences", "()[Lcom/intellij/psi/PsiReference;", "getResolveScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getStartOffsetInParent", "getText", "getTextLength", "getTextOffset", "getTextRange", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "()[Lcom/intellij/psi/PsiType;", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "getUserData", "handleElementRename", "isEquivalentTo", "isPhysical", "isQualified", "isReferenceTo", "isSoft", "isValid", "isWritable", "multiResolve", "(Z)[Lcom/intellij/psi/JavaResolveResult;", "processDeclarations", "Lorg/jetbrains/kotlin/com/intellij/psi/scope/PsiScopeProcessor;", "Lorg/jetbrains/kotlin/com/intellij/psi/ResolveState;", "p3", "processVariants", "putCopyableUserData", "(Lcom/intellij/openapi/util/Key;Ljava/lang/Object;)V", "putUserData", SchemaSymbols.ATTVAL_REPLACE, "resolve", "textContains", "", "textMatches", "", "Lorg/jetbrains/annotations/NonNls;", "textToCharArray", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class KotlinSuperTypeReference implements PsiJavaCodeReferenceElement {
        private final PsiJavaCodeReferenceElement element;

        /* JADX INFO: renamed from: kotlinOrigin$delegate, reason: from kotlin metadata */
        private final Lazy kotlinOrigin;
        final /* synthetic */ KotlinSuperTypeListBuilder this$0;

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void accept(PsiElementVisitor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.element.accept(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void acceptChildren(PsiElementVisitor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.element.acceptChildren(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement add(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.add(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addAfter(PsiElement p0, PsiElement p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.addAfter(p0, p1);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addBefore(PsiElement p0, PsiElement p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.addBefore(p0, p1);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRange(PsiElement p0, PsiElement p1) {
            return this.element.addRange(p0, p1);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRangeAfter(PsiElement p0, PsiElement p1, PsiElement p2) {
            return this.element.addRangeAfter(p0, p1, p2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRangeBefore(PsiElement p0, PsiElement p1, PsiElement p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return this.element.addRangeBefore(p0, p1, p2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
        public JavaResolveResult advancedResolve(boolean p0) {
            return this.element.advancedResolve(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement bindToElement(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.bindToElement(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        @Deprecated(message = "Deprecated in Java")
        public void checkAdd(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.element.checkAdd(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        @Deprecated(message = "Deprecated in Java")
        public void checkDelete() {
            this.element.checkDelete();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement copy() {
            return this.element.copy();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void deleteChildRange(PsiElement p0, PsiElement p1) {
            this.element.deleteChildRange(p0, p1);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement findElementAt(int p0) {
            return this.element.findElementAt(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference findReferenceAt(int p0) {
            return this.element.findReferenceAt(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public String getCanonicalText() {
            return this.element.getCanonicalText();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement[] getChildren() {
            return this.element.getChildren();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiFile getContainingFile() {
            return this.element.getContainingFile();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getContext() {
            return this.element.getContext();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public <T> T getCopyableUserData(Key<T> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return (T) this.element.getCopyableUserData(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        /* JADX INFO: renamed from: getElement */
        public PsiElement getPsiElement() {
            return this.element.getPsiElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getFirstChild() {
            return this.element.getFirstChild();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
        public Icon getIcon(int p0) {
            return this.element.getIcon(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public Language getLanguage() {
            return this.element.getLanguage();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getLastChild() {
            return this.element.getLastChild();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiManager getManager() {
            return this.element.getManager();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getNavigationElement() {
            return this.element.getNavigationElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getNextSibling() {
            return this.element.getNextSibling();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public ASTNode getNode() {
            return this.element.getNode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getOriginalElement() {
            return this.element.getOriginalElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
        public PsiReferenceParameterList getParameterList() {
            return this.element.getParameterList();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getPrevSibling() {
            return this.element.getPrevSibling();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public Project getProject() {
            return this.element.getProject();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
        public String getQualifiedName() {
            return this.element.getQualifiedName();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
        public PsiElement getQualifier() {
            return this.element.getQualifier();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public TextRange getRangeInElement() {
            return this.element.getRangeInElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference getReference() {
            return this.element.getReference();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference
        /* JADX INFO: renamed from: getReferenceName */
        public String getCustomReferenceName() {
            return this.element.getCustomReferenceName();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
        public PsiElement getReferenceNameElement() {
            return this.element.getReferenceNameElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference[] getReferences() {
            return this.element.getReferences();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public GlobalSearchScope getResolveScope() {
            return this.element.getResolveScope();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getStartOffsetInParent() {
            return this.element.getStartOffsetInParent();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String getText() {
            return this.element.getText();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getTextLength() {
            return this.element.getTextLength();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getTextOffset() {
            return this.element.getTextOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public TextRange getTextRange() {
            return this.element.getTextRange();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
        public PsiType[] getTypeParameters() {
            return this.element.getTypeParameters();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public SearchScope getUseScope() {
            return this.element.getUseScope();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
        public <T> T getUserData(Key<T> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return (T) this.element.getUserData(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement handleElementRename(String p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.handleElementRename(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean isEquivalentTo(PsiElement p0) {
            return this.element.isEquivalentTo(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: isPhysical */
        public boolean getIsPhysical() {
            return this.element.getIsPhysical();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement
        public boolean isQualified() {
            return this.element.isQualified();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public boolean isReferenceTo(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.isReferenceTo(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public boolean isSoft() {
            return this.element.isSoft();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
        public boolean isValid() {
            return this.element.isValid();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean isWritable() {
            return this.element.isWritable();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
        public JavaResolveResult[] multiResolve(boolean z) {
            return this.element.multiResolve(z);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean processDeclarations(PsiScopeProcessor p0, ResolveState p1, PsiElement p2, PsiElement p3) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p3, "p3");
            return this.element.processDeclarations(p0, p1, p2, p3);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaReference
        public void processVariants(PsiScopeProcessor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.element.processVariants(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public <T> void putCopyableUserData(Key<T> p0, T t) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.element.putCopyableUserData(p0, t);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
        public <T> void putUserData(Key<T> p0, T t) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            this.element.putUserData(p0, t);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement replace(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.replace(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement resolve() {
            return this.element.resolve();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textContains(char p0) {
            return this.element.textContains(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textMatches(CharSequence p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.textMatches(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textMatches(PsiElement p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return this.element.textMatches(p0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public char[] textToCharArray() {
            return this.element.textToCharArray();
        }

        public KotlinSuperTypeReference(final KotlinSuperTypeListBuilder kotlinSuperTypeListBuilder, PsiJavaCodeReferenceElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            this.this$0 = kotlinSuperTypeListBuilder;
            this.element = element;
            this.kotlinOrigin = ImplUtilsKt.lazyPub(new Function0<KtSuperTypeListEntry>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinSuperTypeListBuilder$KotlinSuperTypeReference$kotlinOrigin$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final KtSuperTypeListEntry invoke() {
                    KtSuperTypeList ktSuperTypeList;
                    String nameFromSource = KotlinSuperTypeListBuilderKt.getNameFromSource(this.this$0.element);
                    if (nameFromSource == null || (ktSuperTypeList = kotlinSuperTypeListBuilder.myKotlinOrigin) == null) {
                        return null;
                    }
                    return ImplUtilsKt.findEntry(ktSuperTypeList, nameFromSource);
                }
            });
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent, reason: from getter */
        public KotlinSuperTypeListBuilder getMyParent() {
            return this.this$0;
        }

        public final KtSuperTypeListEntry getKotlinOrigin() {
            return (KtSuperTypeListEntry) this.kotlinOrigin.getValue();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void delete() {
            KtSuperTypeListEntry kotlinOrigin;
            KtSuperTypeList ktSuperTypeList = this.this$0.myKotlinOrigin;
            if (ktSuperTypeList == null || (kotlinOrigin = getKotlinOrigin()) == null) {
                return;
            }
            ktSuperTypeList.removeEntry(kotlinOrigin);
        }
    }

    private final KotlinSuperTypeReference[] getReferenceElementsCache() {
        return (KotlinSuperTypeReference[]) this.referenceElementsCache.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KotlinLightReferenceListBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList
    public KotlinSuperTypeReference[] getReferenceElements() {
        return getReferenceElementsCache();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement element) {
        KtSuperTypeListEntry kotlinOrigin;
        Intrinsics.checkNotNullParameter(element, "element");
        if (!(element instanceof KotlinSuperTypeReference)) {
            throw new UnsupportedOperationException("Unexpected element: " + PsiUtilsKt.getElementTextWithContext(element));
        }
        KtSuperTypeList ktSuperTypeList = this.myKotlinOrigin;
        if (ktSuperTypeList == null || (kotlinOrigin = ((KotlinSuperTypeReference) element).getKotlinOrigin()) == null) {
            return element;
        }
        ImplUtilsKt.addSuperTypeEntry(this, ktSuperTypeList, kotlinOrigin, (PsiJavaCodeReferenceElement) element);
        return element;
    }

    public final void addMarkerInterfaceIfNeeded(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        String strTryResolveMarkerInterfaceFQName = tryResolveMarkerInterfaceFQName(classId);
        if (strTryResolveMarkerInterfaceFQName != null) {
            addReference(strTryResolveMarkerInterfaceFQName);
        }
    }

    private final String tryResolveMarkerInterfaceFQName(ClassId classId) {
        for (JavaToKotlinClassMap.PlatformMutabilityMapping platformMutabilityMapping : JavaToKotlinClassMap.INSTANCE.getMutabilityMappings()) {
            if (Intrinsics.areEqual(platformMutabilityMapping.getKotlinReadOnly(), classId)) {
                return "kotlin.jvm.internal.markers.KMappedMarker";
            }
            if (Intrinsics.areEqual(platformMutabilityMapping.getKotlinMutable(), classId)) {
                StringBuilder sb = new StringBuilder("kotlin.jvm.internal.markers.K");
                String strAsString = classId.getRelativeClassName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return sb.append(StringsKt.replace$default(StringsKt.replace$default(strAsString, "MutableEntry", "Entry", false, 4, (Object) null), ".", "$", false, 4, (Object) null)).toString();
            }
        }
        return null;
    }
}
