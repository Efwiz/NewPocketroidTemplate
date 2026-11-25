package org.jetbrains.kotlin.asJava.classes;

import java.util.Set;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightField.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\b\u0010\u0018\u00002\u00020\u00012\u00020\u00022\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003B5\b\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f¢\u0006\u0002\u0010\u0010J\n\u0010;\u001a\u0004\u0018\u00010<H\u0016J\u001a\u0010;\u001a\u0004\u0018\u00010<2\u000e\u0010=\u001a\n\u0012\u0004\u0012\u00020?\u0018\u00010>H\u0016J\b\u0010@\u001a\u00020\u000bH\u0016J\n\u0010A\u001a\u0004\u0018\u00010BH\u0016J\n\u0010C\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010D\u001a\u00020EH\u0016J\b\u0010/\u001a\u00020FH\u0016J\b\u0010G\u001a\u00020\u000bH\u0016J\n\u0010H\u001a\u0004\u0018\u00010\tH\u0016J\b\u0010I\u001a\u00020JH\u0016J\n\u0010K\u001a\u0004\u0018\u00010LH\u0016J\b\u0010M\u001a\u00020\u001dH\u0016J\b\u0010N\u001a\u00020OH\u0016J\u0010\u0010P\u001a\u00020O2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010Q\u001a\u00020O2\b\u0010R\u001a\u0004\u0018\u00010SH\u0016J\u0012\u0010T\u001a\u00020U2\b\u0010V\u001a\u0004\u0018\u00010\u0018H\u0016J\u0012\u0010W\u001a\u00020S2\b\b\u0001\u0010\b\u001a\u00020\tH\u0016R!\u0010\u0011\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0017\u001a\u0004\u0018\u00010\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0016\u001a\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001c\u001a\u00020\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0016\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\"R\u0016\u0010%\u001a\u0004\u0018\u00010&8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020*X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001b\u0010-\u001a\u00020.8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\u0016\u001a\u0004\b/\u00100R\u0016\u00102\u001a\u0004\u0018\u00010\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u0010\u001fR\u0016\u00104\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b5\u00106R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u00107\u001a\u0004\u0018\u0001088BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:¨\u0006X"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightFieldImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightFieldBuilder;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightElementWithNullabilityAnnotationDescriptorBased;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "declaration", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "name", "", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "modifiers", "", "(Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;Ljava/lang/String;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Ljava/util/Set;)V", "_constantInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "get_constantInitializer", "()Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "_constantInitializer$delegate", "Lkotlin/Lazy;", "_initializer", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpression;", "get_initializer", "()Lcom/intellij/psi/PsiExpression;", "_initializer$delegate", "_type", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "get_type", "()Lcom/intellij/psi/PsiType;", "_type$delegate", "getDeclaration", "()Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "kotlinOrigin", "getKotlinOrigin", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "getLightMemberOrigin", "()Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "modifierList", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightFieldModifierList;", "getModifierList", "()Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightFieldModifierList;", "modifierList$delegate", "psiTypeForNullabilityAnnotation", "getPsiTypeForNullabilityAnnotation", "qualifiedNameForNullabilityAnnotation", "getQualifiedNameForNullabilityAnnotation", "()Ljava/lang/String;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "getVariableDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "computeConstantValue", "", "visitedVars", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiVariable;", "getContainingClass", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getInitializer", "getLanguage", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getParent", "getText", "getTextOffset", "", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getType", "hasInitializer", "", "hasModifierProperty", "isEquivalentTo", "another", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "setInitializer", "", "initializer", "setName", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtUltraLightFieldImpl extends LightFieldBuilder implements KtUltraLightElementWithNullabilityAnnotationDescriptorBased<KtDeclaration, PsiField>, KtLightField {

    /* JADX INFO: renamed from: _constantInitializer$delegate, reason: from kotlin metadata */
    private final Lazy _constantInitializer;

    /* JADX INFO: renamed from: _initializer$delegate, reason: from kotlin metadata */
    private final Lazy _initializer;

    /* JADX INFO: renamed from: _type$delegate, reason: from kotlin metadata */
    private final Lazy _type;
    private final KtLightClass containingClass;
    private final KtNamedDeclaration declaration;
    private final KtNamedDeclaration kotlinOrigin;
    private final LightMemberOriginForDeclaration lightMemberOrigin;

    /* JADX INFO: renamed from: modifierList$delegate, reason: from kotlin metadata */
    private final Lazy modifierList;
    private final KtUltraLightSupport support;

    protected final KtNamedDeclaration getDeclaration() {
        return this.declaration;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected KtUltraLightFieldImpl(KtNamedDeclaration declaration, String name2, KtLightClass containingClass, KtUltraLightSupport support, final Set<String> modifiers) {
        super(name2, PsiType.NULL, declaration);
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(modifiers, "modifiers");
        this.declaration = declaration;
        this.containingClass = containingClass;
        this.support = support;
        this.modifierList = ImplUtilsKt.lazyPub(new Function0<KtUltraLightFieldModifierList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl$modifierList$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtUltraLightFieldModifierList invoke() {
                return new KtUltraLightFieldModifierList(this.this$0.support, this.this$0.getDeclaration(), this.this$0, modifiers);
            }
        });
        this._type = ImplUtilsKt.lazyPub(new Function0<PsiType>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl$_type$2
            {
                super(0);
            }

            private static final PsiType invoke$nonExistent(KtUltraLightFieldImpl ktUltraLightFieldImpl) {
                PsiType psiTypeCreateTypeFromText = JavaPsiFacade.getElementFactory(ktUltraLightFieldImpl.getProject()).createTypeFromText(StandardNames.INSTANCE.getNON_EXISTENT_CLASS().asString(), ktUltraLightFieldImpl.getDeclaration());
                Intrinsics.checkNotNullExpressionValue(psiTypeCreateTypeFromText, "createTypeFromText(...)");
                return psiTypeCreateTypeFromText;
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiType invoke() {
                PsiType psiTypeAsPsiType;
                if (((this.this$0.getDeclaration() instanceof KtProperty) && ((KtProperty) this.this$0.getDeclaration()).hasDelegate()) || (this.this$0.getDeclaration() instanceof KtEnumEntry) || (this.this$0.getDeclaration() instanceof KtObjectDeclaration)) {
                    KotlinType kotlinType = this.this$0.getKotlinType();
                    PsiType psiTypeErasure = (kotlinType == null || (psiTypeAsPsiType = UltraLightUtilsKt.asPsiType(kotlinType, this.this$0.support, TypeMappingMode.DEFAULT, this.this$0)) == null) ? null : TypeConversionUtil.erasure(psiTypeAsPsiType);
                    return psiTypeErasure == null ? invoke$nonExistent(this.this$0) : psiTypeErasure;
                }
                final KotlinType kotlinType2 = UltraLightUtilsKt.getKotlinType(this.this$0.getDeclaration());
                if (kotlinType2 == null) {
                    PsiPrimitiveType NULL = PsiType.NULL;
                    Intrinsics.checkNotNullExpressionValue(NULL, "NULL");
                    return NULL;
                }
                final VariableDescriptor variableDescriptor = this.this$0.getVariableDescriptor();
                if (variableDescriptor != null) {
                    return UltraLightUtilsKt.mapType(this.this$0.support, kotlinType2, this.this$0, new Function2<KotlinTypeMapper, JvmSignatureWriter, Unit>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl$_type$2.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(KotlinTypeMapper kotlinTypeMapper, JvmSignatureWriter jvmSignatureWriter) {
                            invoke2(kotlinTypeMapper, jvmSignatureWriter);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(KotlinTypeMapper typeMapper, JvmSignatureWriter sw) {
                            Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
                            Intrinsics.checkNotNullParameter(sw, "sw");
                            typeMapper.writeFieldSignature(kotlinType2, variableDescriptor, sw);
                        }
                    });
                }
                PsiPrimitiveType NULL2 = PsiType.NULL;
                Intrinsics.checkNotNullExpressionValue(NULL2, "NULL");
                return NULL2;
            }
        });
        this._initializer = ImplUtilsKt.lazyPub(new Function0<PsiExpression>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl$_initializer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiExpression invoke() {
                ConstantValue constantValue = this.this$0.get_constantInitializer();
                if (constantValue != null) {
                    return UltraLightUtilsKt.createPsiLiteral(constantValue, this.this$0.getDeclaration());
                }
                return null;
            }
        });
        this._constantInitializer = ImplUtilsKt.lazyPub(new Function0<ConstantValue<?>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl$_constantInitializer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ConstantValue<?> invoke() {
                VariableDescriptor variableDescriptor;
                if (!(this.this$0.getDeclaration() instanceof KtProperty) || !this.this$0.getDeclaration().hasModifier(KtTokens.CONST_KEYWORD) || !((KtProperty) this.this$0.getDeclaration()).hasInitializer() || !this.this$0.hasModifierProperty("final")) {
                    return null;
                }
                if ((TypeConversionUtil.isPrimitiveAndNotNull(this.this$0.get_type()) || this.this$0.get_type().equalsToText(CommonClassNames.JAVA_LANG_STRING)) && (variableDescriptor = this.this$0.getVariableDescriptor()) != null) {
                    return variableDescriptor.mo6754getCompileTimeInitializer();
                }
                return null;
            }
        });
        this.kotlinOrigin = declaration;
        this.lightMemberOrigin = new LightMemberOriginForDeclaration(declaration, JvmDeclarationOriginKind.OTHER, null, null, 12, null);
    }

    private final KtUltraLightFieldModifierList getModifierList() {
        return (KtUltraLightFieldModifierList) this.modifierList.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        if (!Intrinsics.areEqual(getKotlinOrigin(), another)) {
            KtLightField ktLightField = another instanceof KtLightField ? (KtLightField) another : null;
            if (!Intrinsics.areEqual(ktLightField != null ? (KtDeclaration) ktLightField.getKotlinOrigin() : null, getKotlinOrigin())) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList, reason: collision with other method in class */
    public PsiModifierList mo6095getModifierList() {
        return getModifierList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getModifierList().hasModifierProperty(name2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        KotlinLanguage INSTANCE = KotlinLanguage.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        return INSTANCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return getKotlinOrigin().getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return getKotlinOrigin().getTextRange();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return getKotlinOrigin().getTextOffset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VariableDescriptor getVariableDescriptor() {
        DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(this.declaration);
        if (declarationDescriptorResolve == null) {
            return null;
        }
        PropertyDescriptor propertyDescriptor = declarationDescriptorResolve instanceof PropertyDescriptor ? (PropertyDescriptor) declarationDescriptorResolve : null;
        if (propertyDescriptor != null) {
            return propertyDescriptor;
        }
        return declarationDescriptorResolve instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) declarationDescriptorResolve : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final KotlinType getKotlinType() {
        KtNamedDeclaration ktNamedDeclaration = this.declaration;
        if ((ktNamedDeclaration instanceof KtProperty) && ((KtProperty) ktNamedDeclaration).hasDelegate()) {
            KtExpression delegateExpression = ((KtProperty) this.declaration).getDelegateExpression();
            if (delegateExpression == null) {
                return null;
            }
            LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
            Project project = getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            return companion.getInstance(project).analyze(delegateExpression).getType(delegateExpression);
        }
        KtNamedDeclaration ktNamedDeclaration2 = this.declaration;
        if (ktNamedDeclaration2 instanceof KtObjectDeclaration) {
            DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(ktNamedDeclaration2);
            ClassDescriptor classDescriptor = declarationDescriptorResolve instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptorResolve : null;
            return classDescriptor != null ? classDescriptor.getDefaultType() : null;
        }
        if (ktNamedDeclaration2 instanceof KtEnumEntry) {
            KtClassOrObject ktClassOrObject = (KtClassOrObject) this.containingClass.getKotlinOrigin();
            DeclarationDescriptor declarationDescriptorResolve2 = ktClassOrObject != null ? UltraLightUtilsKt.resolve(ktClassOrObject) : null;
            ClassDescriptor classDescriptor2 = declarationDescriptorResolve2 instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptorResolve2 : null;
            return classDescriptor2 != null ? classDescriptor2.getDefaultType() : null;
        }
        return UltraLightUtilsKt.getKotlinType(ktNamedDeclaration2);
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public String getQualifiedNameForNullabilityAnnotation() {
        KtNamedDeclaration ktNamedDeclaration = this.declaration;
        if (ktNamedDeclaration instanceof KtObjectDeclaration) {
            return NotNull.class.getName();
        }
        if (ktNamedDeclaration instanceof KtEnumEntry) {
            return null;
        }
        return computeQualifiedNameForNullabilityAnnotation(getKotlinType());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
    public PsiType getPsiTypeForNullabilityAnnotation() {
        return getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PsiType get_type() {
        return (PsiType) this._type.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiField
    public PsiType getType() {
        return get_type();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public KtLightClass getLightOwner() {
        return this.containingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public KtLightClass getContainingClass() {
        return this.containingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return this.containingClass.getContainingFile();
    }

    private final PsiExpression get_initializer() {
        return (PsiExpression) this._initializer.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return get_initializer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return getInitializer() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConstantValue<?> get_constantInitializer() {
        return (ConstantValue) this._constantInitializer.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        ConstantValue<?> constantValue = get_constantInitializer();
        if (constantValue != null) {
            return constantValue.getValue();
        }
        return null;
    }

    public Object computeConstantValue(Set<PsiVariable> visitedVars) {
        return computeConstantValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtNamedDeclaration getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMember
    public LightMemberOriginForDeclaration getLightMemberOrigin() {
        return this.lightMemberOrigin;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        KtNamedDeclaration kotlinOrigin = getKotlinOrigin();
        if (!(kotlinOrigin instanceof KtNamedDeclaration)) {
            kotlinOrigin = null;
        }
        if (kotlinOrigin != null) {
            kotlinOrigin.setName(name2);
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer, reason: merged with bridge method [inline-methods] */
    public Void mo6096setInitializer(PsiExpression initializer) {
        ImplUtilsKt.cannotModify(this);
        throw null;
    }
}
