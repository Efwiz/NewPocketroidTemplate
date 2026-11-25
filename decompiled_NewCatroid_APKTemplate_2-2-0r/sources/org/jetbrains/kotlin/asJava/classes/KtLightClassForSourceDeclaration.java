package org.jetbrains.kotlin.asJava.classes;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.List;
import javax.swing.Icon;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.KtLightIdentifier;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.debugText.DebugTextUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub;

/* JADX INFO: compiled from: KtLightClassForSourceDeclaration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\b&\u0018\u00002\u00020\u00012\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00030\u0002B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010!\u001a\u00020\u0013H$J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#H\u0014J\b\u0010%\u001a\u00020&H&J\n\u0010'\u001a\u0004\u0018\u00010\nH$J\n\u0010(\u001a\u0004\u0018\u00010\nH$J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0002J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010$H\u0096\u0002J\n\u00100\u001a\u0004\u0018\u000101H&J\u0012\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u000205H\u0014J\u001a\u00106\u001a\u0014\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u000308\u0012\u0002\b\u0003\u0018\u000107H\u0016J\n\u00109\u001a\u0004\u0018\u00010\nH\u0016J\n\u0010:\u001a\u0004\u0018\u00010\nH\u0016J\n\u0010;\u001a\u0004\u0018\u00010<H&J\n\u0010=\u001a\u0004\u0018\u00010>H\u0016J\n\u0010?\u001a\u0004\u0018\u00010@H\u0016J\b\u0010A\u001a\u00020&H\u0016J\u000e\u0010B\u001a\b\u0012\u0004\u0012\u00020,0#H&J\n\u0010C\u001a\u0004\u0018\u00010&H&J\n\u0010D\u001a\u0004\u0018\u00010>H&J\b\u0010E\u001a\u000205H\u0016J\u0012\u0010F\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0004\u0018\u00010\u0003H\u0016J\u0013\u0010G\u001a\b\u0012\u0004\u0012\u00020I0HH&¢\u0006\u0002\u0010JJ\u0013\u0010K\u001a\b\u0012\u0004\u0012\u00020,0HH&¢\u0006\u0002\u0010LJ\n\u0010M\u001a\u0004\u0018\u00010NH\u0002J\b\u0010O\u001a\u00020>H\u0016J\b\u0010P\u001a\u000205H\u0016J\n\u0010Q\u001a\u0004\u0018\u00010RH\u0016J\n\u0010S\u001a\u0004\u0018\u00010\u0013H\u0016J\u0013\u0010T\u001a\b\u0012\u0004\u0012\u00020U0HH\u0016¢\u0006\u0002\u0010VJ\b\u0010W\u001a\u00020XH\u0016J\u0012\u0010Y\u001a\u00020.2\b\b\u0001\u0010Z\u001a\u00020>H\u0016J\b\u0010[\u001a\u00020.H\u0016J\b\u0010\\\u001a\u000205H\u0016J\b\u0010]\u001a\u00020.H\u0016J\b\u0010^\u001a\u00020.H&J\b\u0010_\u001a\u00020.H\u0016J\u0012\u0010`\u001a\u00020.2\b\u0010a\u001a\u0004\u0018\u00010&H\u0016J\u0010\u0010b\u001a\u00020.2\u0006\u0010c\u001a\u00020.H&J\u0018\u0010d\u001a\u00020.2\u0006\u0010e\u001a\u00020,2\u0006\u0010f\u001a\u00020.H&J\b\u0010g\u001a\u00020.H\u0016J\b\u0010h\u001a\u00020.H\u0016J\b\u0010i\u001a\u00020.H\u0016J\u0012\u0010j\u001a\u00020&2\b\b\u0001\u0010Z\u001a\u00020>H\u0016J\b\u0010k\u001a\u00020>H\u0016R\u001d\u0010\t\u001a\u0004\u0018\u00010\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u000f\u001a\u0004\u0018\u00010\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0010\u0010\fR\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000e\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0005\u001a\u00020\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006l"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForSourceDeclaration;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/StubBasedPsiElement;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinClassOrObjectStub;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classOrObject", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/config/JvmDefaultMode;)V", "_extendsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "get_extendsList", "()Lcom/intellij/psi/PsiReferenceList;", "_extendsList$delegate", "Lkotlin/Lazy;", "_implementsList", "get_implementsList", "_implementsList$delegate", "_typeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "get_typeParameterList", "()Lcom/intellij/psi/PsiTypeParameterList;", "_typeParameterList$delegate", "getClassOrObject", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getJvmDefaultMode", "()Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "kotlinOrigin", "getKotlinOrigin", "originKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "buildTypeParameterList", "cacheDependencies", "", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "createExtendsList", "createImplementsList", "createSubstitutor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "ancestor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", Namer.EQUALS_METHOD_NAME, "", "other", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getElementIcon", "Ljavax/swing/Icon;", OverlayablePolicy.NAME_flags, "", "getElementType", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/IStubElementType;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "getExtendsList", "getImplementsList", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/asJava/elements/KtLightIdentifier;", "getNavigationElement", "getOwnInnerClasses", "getParent", "getQualifiedName", "getStartOffsetInParent", "getStub", "getSuperTypes", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "()[Lcom/intellij/psi/PsiClassType;", "getSupers", "()[Lcom/intellij/psi/PsiClass;", "getSupertypeByPsi", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImmediateClassType;", "getText", "getTextOffset", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTypeParameterList", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "hasModifierProperty", "name", "hasTypeParameters", "hashCode", "isAnnotationType", "isDeprecated", "isEnum", "isEquivalentTo", "another", "isFinal", "isFinalByPsi", "isInheritor", "baseClass", "checkDeep", "isInterface", "isValid", "isWritable", "setName", "toString", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightClassForSourceDeclaration extends KtLightClassBase implements StubBasedPsiElement<KotlinClassOrObjectStub<? extends KtClassOrObject>> {

    /* JADX INFO: renamed from: _extendsList$delegate, reason: from kotlin metadata */
    private final Lazy _extendsList;

    /* JADX INFO: renamed from: _implementsList$delegate, reason: from kotlin metadata */
    private final Lazy _implementsList;

    /* JADX INFO: renamed from: _typeParameterList$delegate, reason: from kotlin metadata */
    private final Lazy _typeParameterList;
    private final KtClassOrObject classOrObject;
    private final JvmDefaultMode jvmDefaultMode;
    private final KtClassOrObject kotlinOrigin;

    protected abstract PsiTypeParameterList buildTypeParameterList();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract PsiElement copy();

    protected abstract PsiReferenceList createExtendsList();

    protected abstract PsiReferenceList createImplementsList();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract PsiFile getContainingFile();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public abstract PsiModifierList mo6095getModifierList();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public abstract List<PsiClass> getOwnInnerClasses();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public abstract PsiElement getParentMethod();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public abstract String getQualifiedName();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public abstract PsiClassType[] getSuperTypes();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public abstract PsiClass[] getSupers();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public abstract boolean isDeprecated();

    public abstract boolean isFinal(boolean isFinalByPsi);

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public abstract boolean isInheritor(PsiClass baseClass, boolean checkDeep);

    protected final KtClassOrObject getClassOrObject() {
        return this.classOrObject;
    }

    protected final JvmDefaultMode getJvmDefaultMode() {
        return this.jvmDefaultMode;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtLightClassForSourceDeclaration(KtClassOrObject classOrObject, JvmDefaultMode jvmDefaultMode) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        PsiManagerEx manager = classOrObject.getManager();
        Intrinsics.checkNotNullExpressionValue(manager, "getManager(...)");
        super(manager);
        this.classOrObject = classOrObject;
        this.jvmDefaultMode = jvmDefaultMode;
        this._extendsList = ImplUtilsKt.lazyPub(new Function0<PsiReferenceList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration$_extendsList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiReferenceList invoke() {
                return this.this$0.createExtendsList();
            }
        });
        this._implementsList = ImplUtilsKt.lazyPub(new Function0<PsiReferenceList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration$_implementsList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiReferenceList invoke() {
                return this.this$0.createImplementsList();
            }
        });
        this.kotlinOrigin = classOrObject;
        this._typeParameterList = ImplUtilsKt.lazyPub(new Function0<PsiTypeParameterList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration$_typeParameterList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiTypeParameterList invoke() {
                return this.this$0.buildTypeParameterList();
            }
        });
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    protected List<Object> cacheDependencies() {
        return ImplUtilsKt.getExternalDependencies(this.classOrObject);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        String text = getKotlinOrigin().getText();
        return text == null ? "" : text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        TextRange textRange = getKotlinOrigin().getTextRange();
        return textRange == null ? TextRange.EMPTY_RANGE : textRange;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return getKotlinOrigin().getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return getKotlinOrigin().getStartOffsetInParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return getKotlinOrigin().isWritable();
    }

    private final PsiReferenceList get_extendsList() {
        return (PsiReferenceList) this._extendsList.getValue();
    }

    private final PsiReferenceList get_implementsList() {
        return (PsiReferenceList) this._implementsList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtClassOrObject getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        return this.classOrObject;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return getKotlinOrigin().isEquivalentTo(another) || equals(another) || (getQualifiedName() != null && (another instanceof KtLightClassForSourceDeclaration) && Intrinsics.areEqual(getQualifiedName(), ((KtLightClassForSourceDeclaration) another).getQualifiedName()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected Icon getElementIcon(int flags) {
        throw new UnsupportedOperationException("This should be done by KotlinIconProvider");
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
            return false;
        }
        KtLightClassForSourceDeclaration ktLightClassForSourceDeclaration = (KtLightClassForSourceDeclaration) other;
        return this.jvmDefaultMode == ktLightClassForSourceDeclaration.jvmDefaultMode && Intrinsics.areEqual(this.classOrObject, ktLightClassForSourceDeclaration.classOrObject);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassBase
    public int hashCode() {
        return (this.classOrObject.hashCode() * 31) + this.jvmDefaultMode.hashCode();
    }

    private final PsiTypeParameterList get_typeParameterList() {
        return (PsiTypeParameterList) this._typeParameterList.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return get_typeParameterList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = get_typeParameterList().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        Name nameAsName = this.classOrObject.getNameAsName();
        if (nameAsName != null) {
            return nameAsName.asString();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiModifierList psiModifierListMo6095getModifierList = mo6095getModifierList();
        if (psiModifierListMo6095getModifierList != null) {
            return psiModifierListMo6095getModifierList.hasModifierProperty(name2);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        KtClassOrObject ktClassOrObject = this.classOrObject;
        if (ktClassOrObject instanceof KtClass) {
            return ((KtClass) ktClassOrObject).isInterface() || this.classOrObject.isAnnotation();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        KtClassOrObject ktClassOrObject = this.classOrObject;
        return (ktClassOrObject instanceof KtClass) && ktClassOrObject.isAnnotation();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        KtClassOrObject ktClassOrObject = this.classOrObject;
        return (ktClassOrObject instanceof KtClass) && ((KtClass) ktClassOrObject).isEnum();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        KtClassOrObject ktClassOrObject = this.classOrObject;
        if (ktClassOrObject instanceof KtClass) {
            List<KtTypeParameter> typeParameters = ((KtClass) ktClassOrObject).getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            if (!typeParameters.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return this.classOrObject.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) throws IncorrectOperationException {
        Intrinsics.checkNotNullParameter(name2, "name");
        getKotlinOrigin().setName(name2);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getClass().getSimpleName() + ':' + DebugTextUtilKt.getDebugText(this.classOrObject);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope useScope = getKotlinOrigin().getUseScope();
        Intrinsics.checkNotNullExpressionValue(useScope, "getUseScope(...)");
        return useScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement
    public IStubElementType<? extends StubElement<?>, ?> getElementType() {
        return this.classOrObject.getElementType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement
    public KotlinClassOrObjectStub<? extends KtClassOrObject> getStub() {
        return (KotlinClassOrObjectStub) this.classOrObject.getStub();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public KtLightIdentifier mo7392getNameIdentifier() {
        return new KtLightIdentifier(this, this.classOrObject, null, 4, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        return get_extendsList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        return get_implementsList();
    }

    @Override // org.jetbrains.kotlin.asJava.KtLightClassMarker
    public LightClassOriginKind getOriginKind() {
        return LightClassOriginKind.SOURCE;
    }
}
