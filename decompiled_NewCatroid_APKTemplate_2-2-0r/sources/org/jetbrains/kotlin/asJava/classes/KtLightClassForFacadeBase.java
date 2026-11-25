package org.jetbrains.kotlin.asJava.classes;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.FakeFileForLightClass;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.ElementManipulators;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightEmptyImplementsList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.load.kotlin.PackagePartClassUtils;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFileAnnotationList;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: KtLightClassForFacadeBase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0013\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010:\u001a\u00020\u0002H&J\b\u0010;\u001a\u00020\u0010H&J\u000e\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H&J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015H&J\u0013\u0010>\u001a\u00020-2\b\u0010?\u001a\u0004\u0018\u00010@H\u0096\u0002J\u001c\u0010A\u001a\u0004\u0018\u00010B2\b\b\u0001\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020-H\u0016J\u0015\u0010F\u001a\n\u0012\u0006\b\u0001\u0012\u00020B0GH\u0016¢\u0006\u0002\u0010HJ\n\u0010I\u001a\u0004\u0018\u00010BH\u0016J\b\u0010J\u001a\u000206H\u0016J\n\u0010K\u001a\u0004\u0018\u00010LH\u0016J\u0012\u0010M\u001a\u0004\u0018\u00010N2\u0006\u0010O\u001a\u00020PH\u0014J\n\u0010Q\u001a\u0004\u0018\u00010RH\u0016J\b\u0010S\u001a\u00020\nH\u0016J\u0015\u0010T\u001a\n\u0012\u0006\b\u0001\u0012\u00020U0GH\u0016¢\u0006\u0002\u0010VJ\u0015\u0010W\u001a\n\u0012\u0006\b\u0001\u0012\u00020X0GH\u0016¢\u0006\u0002\u0010YJ\u0015\u0010Z\u001a\n\u0012\u0006\b\u0001\u0012\u00020B0GH\u0016¢\u0006\u0002\u0010HJ\u0015\u0010[\u001a\n\u0012\u0006\b\u0001\u0012\u00020B0GH\u0016¢\u0006\u0002\u0010HJ\n\u0010\\\u001a\u0004\u0018\u00010]H\u0016J\b\u0010^\u001a\u00020\u0010H\u0016J\b\u0010_\u001a\u00020DH\u0016J\n\u0010`\u001a\u0004\u0018\u00010aH\u0016J\b\u0010b\u001a\u00020\u0007H\u0016J\u000e\u0010c\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J\u000e\u0010d\u001a\b\u0012\u0004\u0012\u00020B0\u0015H\u0016J\u000e\u0010e\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015H\u0016J\b\u0010f\u001a\u00020]H\u0016J\b\u0010g\u001a\u00020DH\u0016J\n\u0010h\u001a\u0004\u0018\u00010]H\u0016J\b\u0010i\u001a\u00020PH\u0016J\n\u0010j\u001a\u0004\u0018\u00010BH\u0016J\u0013\u0010k\u001a\b\u0012\u0004\u0012\u00020U0GH\u0016¢\u0006\u0002\u0010VJ\u0013\u0010l\u001a\b\u0012\u0004\u0012\u00020B0GH\u0016¢\u0006\u0002\u0010HJ\b\u0010m\u001a\u00020DH\u0016J\b\u0010n\u001a\u00020PH\u0016J\b\u0010o\u001a\u00020pH\u0016J\n\u0010q\u001a\u0004\u0018\u00010rH\u0016J\u0015\u0010s\u001a\n\u0012\u0006\b\u0001\u0012\u00020t0GH\u0016¢\u0006\u0002\u0010uJ\u000e\u0010v\u001a\b\u0012\u0004\u0012\u00020x0wH\u0016J\u0012\u0010y\u001a\u00020-2\b\b\u0001\u0010C\u001a\u00020DH\u0016J\b\u0010z\u001a\u00020-H\u0016J\b\u0010{\u001a\u00020PH\u0016J\b\u0010|\u001a\u00020-H\u0016J\b\u0010}\u001a\u00020-H\u0016J\b\u0010~\u001a\u00020-H\u0016J\u0013\u0010\u007f\u001a\u00020-2\t\u0010\u0080\u0001\u001a\u0004\u0018\u00010]H\u0016J\u001b\u0010\u0081\u0001\u001a\u00020-2\u0007\u0010\u0082\u0001\u001a\u00020B2\u0007\u0010\u0083\u0001\u001a\u00020-H\u0016J\u001f\u0010\u0084\u0001\u001a\u00020-2\t\u0010\u0082\u0001\u001a\u0004\u0018\u00010B2\t\u0010\u0085\u0001\u001a\u0004\u0018\u00010BH\u0016J\t\u0010\u0086\u0001\u001a\u00020-H\u0016J\t\u0010\u0087\u0001\u001a\u00020-H\u0016J\t\u0010\u0088\u0001\u001a\u00020-H\u0016J\u0013\u0010\u0089\u0001\u001a\u0004\u0018\u00010]2\u0006\u0010C\u001a\u00020DH\u0016J\t\u0010\u008a\u0001\u001a\u00020DH\u0016R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R!\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000e\u001a\u0004\b\u0017\u0010\u0018R!\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000e\u001a\u0004\b\u001c\u0010\u0018R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001b\u0010\"\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u000e\u001a\u0004\b#\u0010$R\u0011\u0010&\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b'\u0010\u001fR\u0016\u0010(\u001a\u0004\u0018\u00010)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u001b\u0010,\u001a\u00020-8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\u000e\u001a\u0004\b.\u0010/R\u0014\u00101\u001a\u0002028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u001b\u00105\u001a\u0002068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b9\u0010\u000e\u001a\u0004\b7\u00108¨\u0006\u008b\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacadeBase;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassBase;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacade;", "facadeClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/Collection;)V", "_implementsList", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightEmptyImplementsList;", "get_implementsList", "()Lcom/intellij/psi/impl/light/LightEmptyImplementsList;", "_implementsList$delegate", "Lkotlin/Lazy;", "_modifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "get_modifierList", "()Lcom/intellij/psi/PsiModifierList;", "_modifierList$delegate", "_ownFields", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "get_ownFields", "()Ljava/util/List;", "_ownFields$delegate", "_ownMethods", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "get_ownMethods", "_ownMethods$delegate", "getFacadeClassFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getFiles", "()Ljava/util/Collection;", "firstFileInFacade", "getFirstFileInFacade", "()Lorg/jetbrains/kotlin/psi/KtFile;", "firstFileInFacade$delegate", "fqName", "getFqName", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "multiFileClass", "", "getMultiFileClass", "()Z", "multiFileClass$delegate", "originKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "packageClsFile", "Lorg/jetbrains/kotlin/asJava/elements/FakeFileForLightClass;", "getPackageClsFile", "()Lorg/jetbrains/kotlin/asJava/elements/FakeFileForLightClass;", "packageClsFile$delegate", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "createModifierListForSimpleFacade", "createOwnFields", "createOwnMethods", Namer.EQUALS_METHOD_NAME, "other", "", "findInnerClassByName", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "name", "", "checkBases", "getAllInnerClasses", "", "()[Lcom/intellij/psi/PsiClass;", "getContainingClass", "getContainingFile", "getDocComment", "", "getElementIcon", "Ljavax/swing/Icon;", OverlayablePolicy.NAME_flags, "", "getExtendsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "getImplementsList", "getImplementsListTypes", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "()[Lcom/intellij/psi/PsiClassType;", "getInitializers", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassInitializer;", "()[Lcom/intellij/psi/PsiClassInitializer;", "getInnerClasses", "getInterfaces", "getLBrace", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getModifierList", "getName", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getNavigationElement", "getOwnFields", "getOwnInnerClasses", "getOwnMethods", "getParent", "getQualifiedName", "getRBrace", "getStartOffsetInParent", "getSuperClass", "getSuperTypes", "getSupers", "getText", "getTextOffset", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "getVisibleSignatures", "", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "hasModifierProperty", "hasTypeParameters", "hashCode", "isAnnotationType", "isDeprecated", "isEnum", "isEquivalentTo", "another", "isInheritor", "baseClass", "checkDeep", "isInheritorDeep", "classToByPass", "isInterface", "isValid", "isWritable", "setName", "toString", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightClassForFacadeBase extends KtLightClassBase implements KtLightClassForFacade {

    /* JADX INFO: renamed from: _implementsList$delegate, reason: from kotlin metadata */
    private final Lazy _implementsList;

    /* JADX INFO: renamed from: _modifierList$delegate, reason: from kotlin metadata */
    private final Lazy _modifierList;

    /* JADX INFO: renamed from: _ownFields$delegate, reason: from kotlin metadata */
    private final Lazy _ownFields;

    /* JADX INFO: renamed from: _ownMethods$delegate, reason: from kotlin metadata */
    private final Lazy _ownMethods;
    private final FqName facadeClassFqName;
    private final Collection<KtFile> files;

    /* JADX INFO: renamed from: firstFileInFacade$delegate, reason: from kotlin metadata */
    private final Lazy firstFileInFacade;

    /* JADX INFO: renamed from: multiFileClass$delegate, reason: from kotlin metadata */
    private final Lazy multiFileClass;

    /* JADX INFO: renamed from: packageClsFile$delegate, reason: from kotlin metadata */
    private final Lazy packageClsFile;

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract KtLightClassForFacade copy();

    public abstract PsiModifierList createModifierListForSimpleFacade();

    public abstract List<KtLightField> createOwnFields();

    public abstract List<KtLightMethod> createOwnMethods();

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass findInnerClassByName(String name2, boolean checkBases) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        return null;
    }

    public Void getDocComment() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtClassOrObject getKotlinOrigin() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getLBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getRBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritorDeep(PsiClass baseClass, PsiClass classToByPass) {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ PsiDocComment mo6091getDocComment() {
        return (PsiDocComment) getDocComment();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade
    public FqName getFacadeClassFqName() {
        return this.facadeClassFqName;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade
    public final Collection<KtFile> getFiles() {
        return this.files;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    public KtLightClassForFacadeBase(FqName facadeClassFqName, Collection<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(facadeClassFqName, "facadeClassFqName");
        Intrinsics.checkNotNullParameter(files, "files");
        PsiManager manager = ((KtFile) CollectionsKt.first(files)).getManager();
        Intrinsics.checkNotNullExpressionValue(manager, "getManager(...)");
        super(manager);
        this.facadeClassFqName = facadeClassFqName;
        this.files = files;
        this.firstFileInFacade = ImplUtilsKt.lazyPub(new Function0<KtFile>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase$firstFileInFacade$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtFile invoke() {
                return (KtFile) CollectionsKt.first(this.this$0.getFiles());
            }
        });
        this.multiFileClass = ImplUtilsKt.lazyPub(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase$multiFileClass$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                boolean z = true;
                if (this.this$0.getFiles().size() <= 1 && !JvmFileClassUtilKt.isJvmMultifileClassFile(this.this$0.getFirstFileInFacade())) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
        this._modifierList = ImplUtilsKt.lazyPub(new Function0<PsiModifierList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase$_modifierList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiModifierList invoke() {
                if (this.this$0.getMultiFileClass()) {
                    return new LightModifierList(this.this$0.getManager(), KotlinLanguage.INSTANCE, "public", "final");
                }
                return this.this$0.createModifierListForSimpleFacade();
            }
        });
        this._implementsList = ImplUtilsKt.lazyPub(new Function0<LightEmptyImplementsList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase$_implementsList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LightEmptyImplementsList invoke() {
                return new LightEmptyImplementsList(this.this$0.getManager());
            }
        });
        this.packageClsFile = ImplUtilsKt.lazyPub(new Function0<FakeFileForLightClass>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase$packageClsFile$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FakeFileForLightClass invoke() {
                KtFile firstFileInFacade = this.this$0.getFirstFileInFacade();
                KtLightClassForFacadeBase ktLightClassForFacadeBase = this.this$0;
                KtLightClassForFacadeBase ktLightClassForFacadeBase2 = ktLightClassForFacadeBase;
                FqName fqNameParent = ktLightClassForFacadeBase.getFacadeClassFqName().parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                return new FakeFileForLightClass(firstFileInFacade, ktLightClassForFacadeBase2, fqNameParent);
            }
        });
        this._ownMethods = ImplUtilsKt.lazyPub(new Function0<List<? extends KtLightMethod>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase$_ownMethods$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KtLightMethod> invoke() {
                return this.this$0.createOwnMethods();
            }
        });
        this._ownFields = ImplUtilsKt.lazyPub(new Function0<List<? extends KtLightField>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase$_ownFields$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KtLightField> invoke() {
                return this.this$0.createOwnFields();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KtFile getFirstFileInFacade() {
        return (KtFile) this.firstFileInFacade.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade
    public boolean getMultiFileClass() {
        return ((Boolean) this.multiFileClass.getValue()).booleanValue();
    }

    private final PsiModifierList get_modifierList() {
        return (PsiModifierList) this._modifierList.getValue();
    }

    private final LightEmptyImplementsList get_implementsList() {
        return (LightEmptyImplementsList) this._implementsList.getValue();
    }

    private final FakeFileForLightClass getPackageClsFile() {
        return (FakeFileForLightClass) this.packageClsFile.getValue();
    }

    private final List<KtLightMethod> get_ownMethods() {
        return (List) this._ownMethods.getValue();
    }

    private final List<KtLightField> get_ownFields() {
        return (List) this._ownFields.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return getContainingFile();
    }

    public final FqName getFqName() {
        return getFacadeClassFqName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return get_modifierList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getModifierList().hasModifierProperty(name2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public FakeFileForLightClass getContainingFile() {
        return getPackageClsFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] EMPTY_ARRAY = PsiTypeParameter.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public LightEmptyImplementsList getImplementsList() {
        return get_implementsList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getImplementsListTypes() {
        PsiClassType[] EMPTY_ARRAY = PsiClassType.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInterfaces() {
        PsiClass[] EMPTY_ARRAY = PsiClass.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        PsiClass[] EMPTY_ARRAY = PsiClass.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getAllInnerClasses() {
        PsiClass[] EMPTY_ARRAY = PsiClass.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        PsiClassInitializer[] EMPTY_ARRAY = PsiClassInitializer.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        return super.get$name();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        for (KtFile ktFile : this.files) {
            KtAnnotationEntry ktAnnotationEntryFindAnnotationEntryOnFileNoResolve = JvmFileClassUtil.findAnnotationEntryOnFileNoResolve(ktFile, JvmNames.INSTANCE.getJVM_NAME_SHORT());
            String name3 = ktFile.get$name();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            if (!Intrinsics.areEqual(PackagePartClassUtils.getFilePartShortName(name3), name2)) {
                Object obj = null;
                if (ktAnnotationEntryFindAnnotationEntryOnFileNoResolve == null) {
                    String fileNameByFacadeName = PackagePartClassUtils.getFileNameByFacadeName(name2);
                    PsiDirectory parent = ktFile.getLightParent();
                    if (fileNameByFacadeName != null && parent != null && parent.findFile(fileNameByFacadeName) == null) {
                        ktFile.setName(fileNameByFacadeName);
                    } else {
                        Project project = getProject();
                        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                        KtFileAnnotationList ktFileAnnotationListCreateFileAnnotationListWithAnnotation = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createFileAnnotationListWithAnnotation(JvmNames.INSTANCE.getJVM_NAME_SHORT() + "(\"" + name2 + "\")");
                        KtFileAnnotationList fileAnnotationList = ktFile.getFileAnnotationList();
                        if (fileAnnotationList != null) {
                            List<KtAnnotationEntry> annotationEntries = ktFileAnnotationListCreateFileAnnotationListWithAnnotation.getAnnotationEntries();
                            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
                            fileAnnotationList.add((PsiElement) CollectionsKt.first((List) annotationEntries));
                        } else {
                            PsiElement firstChild = ktFile.getFirstChild();
                            Intrinsics.checkNotNullExpressionValue(firstChild, "getFirstChild(...)");
                            for (Object obj2 : PsiUtilsKt.siblings$default(firstChild, false, false, 3, null)) {
                                PsiElement psiElement = (PsiElement) obj2;
                                if (!(psiElement instanceof PsiWhiteSpace) && !(psiElement instanceof PsiComment)) {
                                    obj = obj2;
                                    break;
                                }
                            }
                            ktFile.addBefore(ktFileAnnotationListCreateFileAnnotationListWithAnnotation, (PsiElement) obj);
                        }
                    }
                } else {
                    List<? extends ValueArgument> valueArguments = ktAnnotationEntryFindAnnotationEntryOnFileNoResolve.getValueArguments();
                    Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
                    ValueArgument valueArgument = (ValueArgument) CollectionsKt.firstOrNull((List) valueArguments);
                    KtExpression argumentExpression = valueArgument != null ? valueArgument.getArgumentExpression() : null;
                    obj = argumentExpression instanceof KtStringTemplateExpression ? (KtStringTemplateExpression) argumentExpression : null;
                    if (obj != null) {
                        ElementManipulators.handleContentChange((PsiElement) obj, name2);
                    }
                }
            } else if (ktAnnotationEntryFindAnnotationEntryOnFileNoResolve != null) {
                ktAnnotationEntryFindAnnotationEntryOnFileNoResolve.delete();
            }
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        String strAsString = getFacadeClassFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        Collection<KtFile> collection = this.files;
        if ((collection instanceof Collection) && collection.isEmpty()) {
            return true;
        }
        for (KtFile ktFile : collection) {
            if (!ktFile.isValid() || !ktFile.hasTopLevelCallables() || !Intrinsics.areEqual(getFacadeClassFqName(), JvmFileClassUtilKt.getJavaFileFacadeFqName(ktFile))) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtFile getNavigationElement() {
        return getFirstFileInFacade();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return equals(another) || ((another instanceof KtLightClassForFacade) && Intrinsics.areEqual(((KtLightClassForFacade) another).getFacadeClassFqName(), getFacadeClassFqName()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected Icon getElementIcon(int flags) {
        throw new UnsupportedOperationException("This should be done by KotlinIconProvider");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass baseClass, boolean checkDeep) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        return Intrinsics.areEqual(baseClass.getQualifiedName(), CommonClassNames.JAVA_LANG_OBJECT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass getSuperClass() {
        return JavaPsiFacade.getInstance(getProject()).findClass(CommonClassNames.JAVA_LANG_OBJECT, getResolveScope());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getSupers() {
        PsiClass superClass = getSuperClass();
        return superClass != null ? new PsiClass[]{superClass} : new PsiClass[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        PsiClassType javaLangObject = PsiType.getJavaLangObject(getManager(), getResolveScope());
        Intrinsics.checkNotNullExpressionValue(javaLangObject, "getJavaLangObject(...)");
        return new PsiClassType[]{javaLangObject};
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    public int hashCode() {
        return getFacadeClassFqName().hashCode();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
            return false;
        }
        KtLightClassForFacadeBase ktLightClassForFacadeBase = (KtLightClassForFacadeBase) other;
        return Intrinsics.areEqual(getFacadeClassFqName(), ktLightClassForFacadeBase.getFacadeClassFqName()) && Intrinsics.areEqual(this.files, ktLightClassForFacadeBase.files);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "KtLightClassForFacadeBase:" + getFacadeClassFqName();
    }

    @Override // org.jetbrains.kotlin.asJava.KtLightClassMarker
    public LightClassOriginKind getOriginKind() {
        return LightClassOriginKind.SOURCE;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        String text = getFirstFileInFacade().getText();
        return text == null ? "" : text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        TextRange textRange = getFirstFileInFacade().getTextRange();
        if (textRange != null) {
            return textRange;
        }
        TextRange EMPTY_RANGE = TextRange.EMPTY_RANGE;
        Intrinsics.checkNotNullExpressionValue(EMPTY_RANGE, "EMPTY_RANGE");
        return EMPTY_RANGE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return getFirstFileInFacade().getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return getFirstFileInFacade().getStartOffsetInParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        Collection<KtFile> collection = this.files;
        if ((collection instanceof Collection) && collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!((KtFile) it.next()).isWritable()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightField> getOwnFields() {
        return get_ownFields();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<KtLightMethod> getOwnMethods() {
        return get_ownMethods();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        Intrinsics.checkNotNullExpressionValue(visibleSignatures, "getVisibleSignatures(...)");
        return visibleSignatures;
    }
}
