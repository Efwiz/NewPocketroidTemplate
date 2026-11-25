package org.jetbrains.kotlin.asJava.classes;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.Collection;
import javax.swing.Icon;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.FakeFileForLightClass;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmClass;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightEmptyImplementsList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;

/* JADX INFO: compiled from: KtLightClassForScriptBase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0013\b&\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u001b\u001a\u00020\u001cH&J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0096\u0002J\n\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010#\u001a\u00020\u0007H\u0016J\n\u0010$\u001a\u0004\u0018\u00010\"H\u0016J\u0012\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020(H\u0014J\b\u0010)\u001a\u00020\u0015H\u0016J\b\u0010*\u001a\u00020\u0015H\u0016J\u0013\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,H\u0016¢\u0006\u0002\u0010.J\u0013\u0010/\u001a\b\u0012\u0004\u0012\u0002000,H\u0016¢\u0006\u0002\u00101J\u0013\u00102\u001a\b\u0012\u0004\u0012\u0002030,H\u0016¢\u0006\u0002\u00104J\n\u00105\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u00106\u001a\u00020\u0011H\u0016J\b\u00107\u001a\u000208H\u0016J\n\u00109\u001a\u0004\u0018\u00010:H\u0016J\b\u0010;\u001a\u00020\u0004H\u0016J\b\u0010<\u001a\u00020\u001cH\u0016J\b\u0010=\u001a\u000208H\u0016J\n\u0010>\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010?\u001a\u00020\u001cH\u0016J\n\u0010@\u001a\u0004\u0018\u000103H\u0016J\u0013\u0010A\u001a\b\u0012\u0004\u0012\u00020-0,H\u0016¢\u0006\u0002\u0010.J\u0013\u0010B\u001a\b\u0012\u0004\u0012\u0002030,H\u0016¢\u0006\u0002\u00104J\n\u0010C\u001a\u0004\u0018\u00010\"H\u0016J\u0013\u0010D\u001a\b\u0012\u0004\u0012\u00020E0,H\u0016¢\u0006\u0002\u0010FJ\u000e\u0010G\u001a\b\u0012\u0004\u0012\u00020I0HH\u0016J\u0012\u0010J\u001a\u00020\u001e2\b\b\u0001\u0010K\u001a\u000208H\u0016J\b\u0010L\u001a\u00020\u001eH\u0016J\b\u0010M\u001a\u00020(H\u0016J\b\u0010N\u001a\u00020\u001eH\u0016J\b\u0010O\u001a\u00020\u001eH\u0016J\b\u0010P\u001a\u00020\u001eH\u0016J\u0012\u0010Q\u001a\u00020\u001e2\b\u0010R\u001a\u0004\u0018\u00010\u001cH\u0016J\u0018\u0010S\u001a\u00020\u001e2\u0006\u0010T\u001a\u0002032\u0006\u0010U\u001a\u00020\u001eH\u0016J\u001c\u0010V\u001a\u00020\u001e2\b\u0010T\u001a\u0004\u0018\u0001032\b\u0010W\u001a\u0004\u0018\u000103H\u0016J\b\u0010X\u001a\u00020\u001eH\u0016J\b\u0010Y\u001a\u00020\u001eH\u0016J\u0012\u0010Z\u001a\u0004\u0018\u00010\u001c2\u0006\u0010K\u001a\u000208H\u0016J\b\u0010[\u001a\u000208H\u0016R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000b\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForScriptBase;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForScript;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassBase;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "(Lorg/jetbrains/kotlin/psi/KtScript;)V", "_containingFile", "Lorg/jetbrains/kotlin/asJava/elements/FakeFileForLightClass;", "get_containingFile", "()Lorg/jetbrains/kotlin/asJava/elements/FakeFileForLightClass;", "_containingFile$delegate", "Lkotlin/Lazy;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "modifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getScript", "()Lorg/jetbrains/kotlin/psi/KtScript;", "scriptExtendsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "getScriptExtendsList", "()Lcom/intellij/psi/PsiReferenceList;", "scriptExtendsList$delegate", "scriptImplementsList", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightEmptyImplementsList;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getContainingClass", "", "getContainingFile", "getDocComment", "getElementIcon", "Ljavax/swing/Icon;", OverlayablePolicy.NAME_flags, "", "getExtendsList", "getImplementsList", "getImplementsListTypes", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "()[Lcom/intellij/psi/PsiClassType;", "getInitializers", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassInitializer;", "()[Lcom/intellij/psi/PsiClassInitializer;", "getInterfaces", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "()[Lcom/intellij/psi/PsiClass;", "getLBrace", "getModifierList", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getNavigationElement", "getParent", "getQualifiedName", "getRBrace", "getScope", "getSuperClass", "getSuperTypes", "getSupers", "getTypeParameterList", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "getVisibleSignatures", "", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "hasModifierProperty", "name", "hasTypeParameters", "hashCode", "isAnnotationType", "isDeprecated", "isEnum", "isEquivalentTo", "another", "isInheritor", "baseClass", "checkDeep", "isInheritorDeep", "classToByPass", "isInterface", "isValid", "setName", "toString", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightClassForScriptBase extends KtLightClassBase implements KtLightClassForScript {

    /* JADX INFO: renamed from: _containingFile$delegate, reason: from kotlin metadata */
    private final Lazy _containingFile;
    private final PsiModifierList modifierList;
    private final KtScript script;

    /* JADX INFO: renamed from: scriptExtendsList$delegate, reason: from kotlin metadata */
    private final Lazy scriptExtendsList;
    private final LightEmptyImplementsList scriptImplementsList;

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract PsiElement copy();

    public Void getContainingClass() {
        return null;
    }

    public Void getDocComment() {
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

    public Void getTypeParameterList() {
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public /* bridge */ /* synthetic */ JvmClass getContainingClass() {
        return (JvmClass) getContainingClass();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public /* bridge */ /* synthetic */ PsiClass getContainingClass() {
        return (PsiClass) getContainingClass();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public /* bridge */ /* synthetic */ PsiDocComment mo6091getDocComment() {
        return (PsiDocComment) getDocComment();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ PsiTypeParameterList mo6093getTypeParameterList() {
        return (PsiTypeParameterList) getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForScript
    public KtScript getScript() {
        return this.script;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtLightClassForScriptBase(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        PsiManagerEx manager = script.getManager();
        Intrinsics.checkNotNullExpressionValue(manager, "getManager(...)");
        super(manager);
        this.script = script;
        this.modifierList = new LightModifierList(getManager(), KotlinLanguage.INSTANCE, "public", "final");
        this.scriptImplementsList = new LightEmptyImplementsList(getManager());
        this.scriptExtendsList = ImplUtilsKt.lazyPub(new Function0<KotlinLightReferenceListBuilder>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForScriptBase$scriptExtendsList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinLightReferenceListBuilder invoke() {
                KotlinLightReferenceListBuilder kotlinLightReferenceListBuilder = new KotlinLightReferenceListBuilder(this.this$0.getManager(), PsiReferenceList.Role.EXTENDS_LIST);
                kotlinLightReferenceListBuilder.addReference("kotlin.script.templates.standard.ScriptTemplateWithArgs");
                return kotlinLightReferenceListBuilder;
            }
        });
        this._containingFile = ImplUtilsKt.lazyPub(new Function0<FakeFileForLightClass>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForScriptBase$_containingFile$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FakeFileForLightClass invoke() {
                KtFile containingKtFile = this.this$0.getScript().getContainingKtFile();
                Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
                KtLightClassForScriptBase ktLightClassForScriptBase = this.this$0;
                KtLightClassForScriptBase ktLightClassForScriptBase2 = ktLightClassForScriptBase;
                FqName fqNameParent = ktLightClassForScriptBase.getFqName().parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                return new FakeFileForLightClass(containingKtFile, ktLightClassForScriptBase2, fqNameParent);
            }
        });
    }

    private final PsiReferenceList getScriptExtendsList() {
        return (PsiReferenceList) this.scriptExtendsList.getValue();
    }

    private final FakeFileForLightClass get_containingFile() {
        return (FakeFileForLightClass) this._containingFile.getValue();
    }

    public final FqName getFqName() {
        FqName fqNameMo7391getFqName = getScript().mo7391getFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameMo7391getFqName, "getFqName(...)");
        return fqNameMo7391getFqName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return this.modifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.modifierList.hasModifierProperty(name2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public FakeFileForLightClass getContainingFile() {
        return get_containingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] EMPTY_ARRAY = PsiTypeParameter.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        return this.scriptImplementsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        return getScriptExtendsList();
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        PsiClassInitializer[] EMPTY_ARRAY = PsiClassInitializer.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String strAsString = getScript().mo7391getFqName().shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        String strAsString = getScript().mo7391getFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return getScript().isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtScript getNavigationElement() {
        return getScript();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return equals(another) || ((another instanceof KtLightClassForScriptBase) && Intrinsics.areEqual(getFqName(), ((KtLightClassForScriptBase) another).getFqName()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected Icon getElementIcon(int flags) {
        throw new UnsupportedOperationException("This should be done by KotlinIconProvider");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        Intrinsics.checkNotNullExpressionValue(visibleSignatures, "getVisibleSignatures(...)");
        return visibleSignatures;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        throw new IncorrectOperationException();
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return getLightParent();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    public int hashCode() {
        return getScript().hashCode();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    public boolean equals(Object other) {
        if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
            return false;
        }
        KtLightClassForScriptBase ktLightClassForScriptBase = other instanceof KtLightClassForScriptBase ? (KtLightClassForScriptBase) other : null;
        if (ktLightClassForScriptBase == null) {
            return false;
        }
        return this == other || Intrinsics.areEqual(getScript(), ktLightClassForScriptBase.getScript());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "KtLightClassForScriptBase:" + getScript().mo7391getFqName();
    }
}
