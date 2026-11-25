package org.jetbrains.kotlin.asJava.classes;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.SyntheticElement;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: KotlinClassInnerStuffCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000í\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r*\u0001\u0015\b\u0002\u0018\u0000 ^2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002^_B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u001c\u001a\u00020\n2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\n\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0013\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\"H\u0016¢\u0006\u0002\u0010#J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010'\u001a\u00020\nH\u0016J\u0013\u0010(\u001a\b\u0012\u0004\u0012\u00020 0\"H\u0016¢\u0006\u0002\u0010#J\u001b\u0010(\u001a\b\u0012\u0004\u0012\u00020 0\"2\u0006\u0010)\u001a\u00020*H\u0016¢\u0006\u0002\u0010+J\u001b\u0010(\u001a\b\u0012\u0004\u0012\u00020 0\"2\u0006\u0010'\u001a\u00020\nH\u0016¢\u0006\u0002\u0010,J\n\u0010-\u001a\u0004\u0018\u00010.H\u0016J\b\u0010/\u001a\u00020\u0005H\u0016J\b\u00100\u001a\u000201H\u0016J\n\u00102\u001a\u0004\u0018\u000103H\u0016J\n\u00104\u001a\u0004\u0018\u000105H\u0016J\r\u00106\u001a\u000707¢\u0006\u0002\b\u001bH\u0016J\b\u00108\u001a\u000209H\u0016J\b\u0010:\u001a\u00020;H\u0016J\b\u0010<\u001a\u00020=H\u0016J\b\u0010>\u001a\u00020?H\u0016J\b\u0010@\u001a\u00020AH\u0016J\b\u0010B\u001a\u00020\u001aH\u0016J\n\u0010C\u001a\u0004\u0018\u00010DH\u0016J\u0010\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020HH\u0016J\b\u0010I\u001a\u00020;H\u0016J\b\u0010J\u001a\u00020KH\u0016J\b\u0010L\u001a\u00020MH\u0016J\b\u0010N\u001a\u00020OH\u0016J\n\u0010P\u001a\u0004\u0018\u00010QH\u0016J\u0013\u0010R\u001a\b\u0012\u0004\u0012\u00020S0\"H\u0016¢\u0006\u0002\u0010TJ\u0010\u0010U\u001a\u00020\n2\u0006\u0010V\u001a\u00020;H\u0016J\b\u0010W\u001a\u00020\nH\u0016J\b\u0010X\u001a\u00020KH\u0016J\b\u0010Y\u001a\u00020\nH\u0016J\b\u0010Z\u001a\u00020\nH\u0016J\b\u0010[\u001a\u00020\nH\u0016J\u0010\u0010\\\u001a\u00020A2\u0006\u0010V\u001a\u00020;H\u0016J\b\u0010]\u001a\u00020;H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0019\u001a\u00070\u001a¢\u0006\u0002\b\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006`"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightElement;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "Lorg/jetbrains/kotlin/com/intellij/psi/SyntheticElement;", "enumClass", "Lorg/jetbrains/kotlin/asJava/classes/KtExtensibleLightClass;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod$Kind;", "(Lorg/jetbrains/kotlin/asJava/classes/KtExtensibleLightClass;Lorg/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod$Kind;)V", "isMangled", "", "()Z", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "getLightMemberOrigin", "()Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "modifierList", "org/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod$modifierList$1", "Lorg/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod$modifierList$1;", "parameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameterListBuilder;", "returnType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "Lorg/jetbrains/annotations/NotNull;", Namer.EQUALS_METHOD_NAME, "other", "", "findDeepestSuperMethod", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "findDeepestSuperMethods", "", "()[Lcom/intellij/psi/PsiMethod;", "findSuperMethodSignaturesIncludingStatic", "", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBackedByPsiMethod;", "checkAccess", "findSuperMethods", "parentClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "(Lcom/intellij/psi/PsiClass;)[Lcom/intellij/psi/PsiMethod;", "(Z)[Lcom/intellij/psi/PsiMethod;", "getBody", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiCodeBlock;", "getContainingClass", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getDefaultValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "getDocComment", "Lorg/jetbrains/kotlin/com/intellij/psi/javadoc/PsiDocComment;", "getHierarchicalMethodSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameterList;", "getParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getReturnType", "getReturnTypeElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeElement;", "getSignature", "Lorg/jetbrains/kotlin/com/intellij/psi/util/MethodSignature;", "substitutor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "getText", "getTextOffset", "", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getThrowsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "getTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "hasModifierProperty", "name", "hasTypeParameters", "hashCode", "isConstructor", "isDeprecated", "isVarArgs", "setName", "toString", "Companion", Namer.CLASS_KIND_ENUM, "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KotlinEnumSyntheticMethod extends LightElement implements SyntheticElement, KtLightMethod {
    private static final Companion Companion = new Companion(null);
    private final KtExtensibleLightClass enumClass;
    private final Kind kind;
    private final KotlinEnumSyntheticMethod$modifierList$1 modifierList;
    private final LightParameterListBuilder parameterList;
    private final PsiType returnType;

    /* JADX INFO: compiled from: KotlinClassInnerStuffCache.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Kind.values().length];
            try {
                iArr[Kind.VALUE_OF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Kind.VALUES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Kind.ENTRIES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiCodeBlock getBody() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod
    public PsiAnnotationMemberValue getDefaultValue() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtDeclaration getKotlinOrigin() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMember
    public LightMemberOrigin getLightMemberOrigin() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiTypeElement getReturnTypeElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isConstructor() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethod
    public boolean isMangled() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isVarArgs() {
        return false;
    }

    /* JADX INFO: compiled from: KotlinClassInnerStuffCache.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod$Kind;", "", "methodName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getMethodName", "()Ljava/lang/String;", "VALUE_OF", "VALUES", "ENTRIES", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        VALUE_OF("valueOf"),
        VALUES(CompoundEntry.NAME_values),
        ENTRIES("getEntries");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String methodName;

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }

        Kind(String str) {
            this.methodName = str;
        }

        public final String getMethodName() {
            return this.methodName;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v7, types: [org.jetbrains.kotlin.asJava.classes.KotlinEnumSyntheticMethod$modifierList$1] */
    public KotlinEnumSyntheticMethod(KtExtensibleLightClass enumClass, Kind kind) {
        PsiClassType psiClassTypeAnnotate;
        PsiClassType psiClassTypeCreateTypeFromText;
        super(enumClass.getManager(), enumClass.getLanguage());
        Intrinsics.checkNotNullParameter(enumClass, "enumClass");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.enumClass = enumClass;
        this.kind = kind;
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
        PsiClassType psiClassTypeCreateType = elementFactory.createType(this.enumClass);
        Intrinsics.checkNotNullExpressionValue(psiClassTypeCreateType, "createType(...)");
        PsiClassType psiClassTypeAnnotate2 = psiClassTypeCreateType.annotate(new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinEnumSyntheticMethod$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
            public final PsiAnnotation[] getAnnotations() {
                return KotlinEnumSyntheticMethod.returnType$lambda$3$lambda$0(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(psiClassTypeAnnotate2, "annotate(...)");
        int i = WhenMappings.$EnumSwitchMapping$0[this.kind.ordinal()];
        if (i == 1) {
            psiClassTypeAnnotate = psiClassTypeAnnotate2;
        } else if (i == 2) {
            psiClassTypeAnnotate = psiClassTypeAnnotate2.createArrayType().annotate(new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinEnumSyntheticMethod$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
                public final PsiAnnotation[] getAnnotations() {
                    return KotlinEnumSyntheticMethod.returnType$lambda$3$lambda$1(this.f$0);
                }
            });
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            PsiClass psiClassFindClass = JavaPsiFacade.getInstance(getProject()).findClass(StandardClassIds.INSTANCE.getEnumEntries().asFqNameString(), getResolveScope());
            if (psiClassFindClass != null) {
                psiClassTypeCreateTypeFromText = elementFactory.createType(psiClassFindClass, psiClassTypeCreateType);
            } else {
                psiClassTypeCreateTypeFromText = elementFactory.createTypeFromText(StandardClassIds.INSTANCE.getEnumEntries().asFqNameString() + '<' + this.enumClass.getQualifiedName() + '>', this.enumClass);
            }
            Intrinsics.checkNotNull(psiClassTypeCreateTypeFromText);
            psiClassTypeAnnotate = psiClassTypeCreateTypeFromText.annotate(new TypeAnnotationProvider() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinEnumSyntheticMethod$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider
                public final PsiAnnotation[] getAnnotations() {
                    return KotlinEnumSyntheticMethod.returnType$lambda$3$lambda$2(this.f$0);
                }
            });
        }
        Intrinsics.checkNotNullExpressionValue(psiClassTypeAnnotate, "run(...)");
        this.returnType = psiClassTypeAnnotate;
        LightParameterListBuilder lightParameterListBuilder = new LightParameterListBuilder(getManager(), getLanguage());
        if (kind == Kind.VALUE_OF) {
            PsiClassType javaLangString = PsiType.getJavaLangString(lightParameterListBuilder.getManager(), GlobalSearchScope.allScope(lightParameterListBuilder.getProject()));
            Intrinsics.checkNotNullExpressionValue(javaLangString, "getJavaLangString(...)");
            lightParameterListBuilder.addParameter(new KotlinEnumSyntheticMethod$parameterList$1$valueParameter$1(javaLangString, lightParameterListBuilder, this, StandardNames.DEFAULT_VALUE_PARAMETER.getIdentifier(), lightParameterListBuilder.getLanguage()));
        }
        this.parameterList = lightParameterListBuilder;
        final PsiManager manager = getManager();
        final Language language = getLanguage();
        final String[] strArr = {"public", "static"};
        this.modifierList = new LightModifierList(manager, language, strArr) { // from class: org.jetbrains.kotlin.asJava.classes.KotlinEnumSyntheticMethod$modifierList$1
            private final PsiAnnotation[] annotations;

            {
                this.annotations = new PsiAnnotation[]{KotlinEnumSyntheticMethod.Companion.makeNotNullAnnotation(this.this$0.enumClass)};
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            /* JADX INFO: renamed from: getParent, reason: from getter */
            public KotlinEnumSyntheticMethod getThis$0() {
                return this.this$0;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
            public PsiAnnotation findAnnotation(String fqn) {
                Intrinsics.checkNotNullParameter(fqn, "fqn");
                for (PsiAnnotation psiAnnotation : this.annotations) {
                    if (psiAnnotation.hasQualifiedName(fqn)) {
                        return psiAnnotation;
                    }
                }
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
            public PsiAnnotation[] getAnnotations() {
                return (PsiAnnotation[]) KotlinClassInnerStuffCacheKt.copy(this.annotations);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiAnnotation[] returnType$lambda$3$lambda$0(KotlinEnumSyntheticMethod this_run) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        return new PsiAnnotation[]{Companion.makeNotNullAnnotation(this_run.enumClass)};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiAnnotation[] returnType$lambda$3$lambda$1(KotlinEnumSyntheticMethod this_run) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        return new PsiAnnotation[]{Companion.makeNotNullAnnotation(this_run.enumClass)};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiAnnotation[] returnType$lambda$3$lambda$2(KotlinEnumSyntheticMethod this_run) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        return new PsiAnnotation[]{Companion.makeNotNullAnnotation(this_run.enumClass)};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return this.enumClass.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return this.enumClass.toString();
    }

    public boolean equals(Object other) {
        if (this != other) {
            if (other instanceof KotlinEnumSyntheticMethod) {
                KotlinEnumSyntheticMethod kotlinEnumSyntheticMethod = (KotlinEnumSyntheticMethod) other;
                if (!Intrinsics.areEqual(this.enumClass, kotlinEnumSyntheticMethod.enumClass) || this.kind != kotlinEnumSyntheticMethod.kind) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.enumClass, this.kind);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        return this.returnType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        return this.parameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        LightReferenceListBuilder lightReferenceListBuilder = new LightReferenceListBuilder(getManager(), getLanguage(), PsiReferenceList.Role.THROWS_LIST);
        if (this.kind == Kind.VALUE_OF) {
            lightReferenceListBuilder.addReference(Reflection.getOrCreateKotlinClass(IllegalArgumentException.class).getQualifiedName());
            lightReferenceListBuilder.addReference(Reflection.getOrCreateKotlinClass(NullPointerException.class).getQualifiedName());
        }
        return lightReferenceListBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getThis$0() {
        return this.enumClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass, reason: from getter and merged with bridge method [inline-methods] */
    public KtExtensibleLightClass mo6092getContainingClass() {
        return this.enumClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = this.enumClass.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public MethodSignature getSignature(PsiSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, substitutor);
        Intrinsics.checkNotNullExpressionValue(methodSignatureBackedByPsiMethodCreate, "create(...)");
        return methodSignatureBackedByPsiMethodCreate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return new LightIdentifier(getManager(), getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return this.kind.getMethodName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods() {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(boolean z) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, z);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(PsiClass parentClass) {
        Intrinsics.checkNotNullParameter(parentClass, "parentClass");
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, parentClass);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindSuperMethods, "findSuperMethods(...)");
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean checkAccess) {
        List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = PsiSuperMethodImplUtil.findSuperMethodSignaturesIncludingStatic(this, checkAccess);
        Intrinsics.checkNotNullExpressionValue(listFindSuperMethodSignaturesIncludingStatic, "findSuperMethodSignaturesIncludingStatic(...)");
        return listFindSuperMethodSignaturesIncludingStatic;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod findDeepestSuperMethod() {
        return PsiSuperMethodImplUtil.findDeepestSuperMethod(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findDeepestSuperMethods() {
        PsiMethod[] EMPTY_ARRAY = PsiMethod.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return this.modifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return Intrinsics.areEqual(name2, "public") || Intrinsics.areEqual(name2, "static");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public HierarchicalMethodSignature getHierarchicalMethodSignature() {
        HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
        Intrinsics.checkNotNullExpressionValue(hierarchicalMethodSignature, "getHierarchicalMethodSignature(...)");
        return hierarchicalMethodSignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] EMPTY_ARRAY = PsiTypeParameter.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        TextRange EMPTY_RANGE = TextRange.EMPTY_RANGE;
        Intrinsics.checkNotNullExpressionValue(EMPTY_RANGE, "EMPTY_RANGE");
        return EMPTY_RANGE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: KotlinClassInnerStuffCache.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod$Companion;", "", "()V", "makeNotNullAnnotation", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "context", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final PsiAnnotation makeNotNullAnnotation(PsiClass context) {
            PsiAnnotation psiAnnotationCreateAnnotationFromText = PsiElementFactory.getInstance(context.getProject()).createAnnotationFromText(ClassInnerStuffCache.NOT_NULL_ANNOTATION_QUALIFIER, context);
            Intrinsics.checkNotNullExpressionValue(psiAnnotationCreateAnnotationFromText, "createAnnotationFromText(...)");
            return psiAnnotationCreateAnnotationFromText;
        }
    }
}
