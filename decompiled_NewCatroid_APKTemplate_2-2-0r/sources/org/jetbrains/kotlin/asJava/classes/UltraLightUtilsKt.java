package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.UltraLightClassModifierExtension;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.asJava.elements.KotlinLightTypeParameterListBuilder;
import org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry;
import org.jetbrains.kotlin.asJava.elements.KtLightAnnotationsValuesKt;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.ModifierFlags;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.SignatureParsing;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentName;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ultraLightUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¼\u0002\n\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0002\u0001\u0004\u001a \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001aE\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u00102\u0006\u0010\u0011\u001a\u0002H\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u00100\u0017H\u0002¢\u0006\u0002\u0010\u0018\u001a \u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a\"\u0010\u001d\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000b\u001a\u00020 H\u0002\u001a\u0010\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001fH\u0002\u001a\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$H\u0002\u001a\u0018\u0010&\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010'\u001a\u00020(H\u0002\u001a'\u0010)\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\u000e\b\u0004\u0010*\u001a\b\u0012\u0004\u0012\u0002H\u00100+H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u000200H\u0002\u001a\u0010\u00101\u001a\u0004\u0018\u00010\u001f2\u0006\u00102\u001a\u000203\u001a\f\u00104\u001a\u000205*\u000206H\u0000\u001a\u000e\u00107\u001a\u0004\u0018\u000108*\u000209H\u0000\u001a$\u0010:\u001a\u00020;*\u00020<2\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020;0>H\u0080\bø\u0001\u0000\u001a$\u0010@\u001a\u00020\u0007*\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010A\u001a\u00020B2\u0006\u0010\u000b\u001a\u00020 H\u0000\u001a\u0018\u0010C\u001a\u00020\u001f*\u0006\u0012\u0002\b\u00030D2\u0006\u0010E\u001a\u00020 H\u0002\u001a\f\u0010F\u001a\u0004\u0018\u00010\n*\u00020\n\u001a(\u0010G\u001a\u00020H*\u00020I2\u0006\u0010J\u001a\u00020K2\b\b\u0002\u0010L\u001a\u00020M2\n\b\u0002\u0010N\u001a\u0004\u0018\u00010O\u001a\u001a\u0010P\u001a\u0004\u0018\u00010Q*\u0006\u0012\u0002\b\u00030D2\u0006\u0010E\u001a\u00020 H\u0000\u001a*\u0010R\u001a\u0010\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u000208\u0018\u00010S*\u00020\u00152\u0006\u0010\u0012\u001a\u00020T2\u0006\u0010U\u001a\u00020.H\u0000\u001a\u000e\u0010V\u001a\u0004\u0018\u00010\n*\u00020OH\u0000\u001a\u0018\u0010W\u001a\u00020(*\u00020X2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0000\u001a\u0014\u0010Y\u001a\u00020(*\u00020X2\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a\u0014\u0010Z\u001a\u00020(*\u00020T2\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a\u0014\u0010[\u001a\u00020\\*\u00020I2\u0006\u0010J\u001a\u00020KH\u0002\u001a\n\u0010]\u001a\u00020(*\u000209\u001a\n\u0010^\u001a\u00020(*\u00020_\u001a8\u0010`\u001a\u00020\u0007*\u00020\u00152\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020 2\u0018\u0010a\u001a\u0014\u0012\u0004\u0012\u00020c\u0012\u0004\u0012\u00020d\u0012\u0004\u0012\u00020;0bH\u0000\u001a\u000e\u0010e\u001a\u0004\u0018\u00010f*\u00020OH\u0000\u001a\r\u0010g\u001a\u00020(*\u00020hH\u0086\b\u001a\r\u0010i\u001a\u00020(*\u00020jH\u0086\b\u001a\u000f\u0010k\u001a\u0004\u0018\u00010l*\u00020jH\u0086\b\u001a\f\u0010m\u001a\u00020\u001f*\u00020OH\u0000\u001a*\u0010n\u001a\b\u0012\u0004\u0012\u00020p0o*\b\u0012\u0004\u0012\u0002090o2\u0006\u0010E\u001a\u00020 2\b\u0010q\u001a\u0004\u0018\u00010rH\u0000\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0005\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006s²\u0006\n\u0010t\u001a\u00020$X\u008a\u0084\u0002"}, d2 = {"supportForDescriptor", "org/jetbrains/kotlin/asJava/classes/UltraLightUtilsKt$supportForDescriptor$1", "Lorg/jetbrains/kotlin/asJava/classes/UltraLightUtilsKt$supportForDescriptor$1;", "supportForSourceDeclaration", "org/jetbrains/kotlin/asJava/classes/UltraLightUtilsKt$supportForSourceDeclaration$1", "Lorg/jetbrains/kotlin/asJava/classes/UltraLightUtilsKt$supportForSourceDeclaration$1;", "annotateByKotlinType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "psiType", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "psiContext", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeElement;", "buildTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "D", ExifInterface.GPS_DIRECTION_TRUE, "declaration", "owner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterListOwner;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "typeParametersSupport", "Lorg/jetbrains/kotlin/asJava/classes/TypeParametersSupport;", "(Ljava/lang/Object;Lcom/intellij/psi/PsiTypeParameterListOwner;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/TypeParametersSupport;)Lcom/intellij/psi/PsiTypeParameterList;", "buildTypeParameterListForDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "buildTypeParameterListForSourceDeclaration", "Lorg/jetbrains/kotlin/psi/KtTypeParameterListOwner;", "createTypeFromCanonicalText", "canonicalSignature", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "escapeString", "str", "packCommonFlags", "", "access", "packMethodFlags", "isInterface", "", "runReadAction", "runnable", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toQualifiedName", "Lorg/jetbrains/kotlin/name/FqName;", "userType", "Lorg/jetbrains/kotlin/psi/KtUserType;", "tryGetPredefinedName", "klass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "analyze", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "Lorg/jetbrains/kotlin/psi/KtElement;", "analyzeAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "applyCompilerPlugins", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "body", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/asJava/UltraLightClassModifierExtension;", "asPsiType", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "asStringForPsiLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "parent", "cleanFromAnonymousTypes", "createGeneratedMethodFromDescriptor", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "declarationOriginKindForOrigin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "declarationForOrigin", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "createPsiLiteral", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpression;", "findAnnotation", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "fqName", "getKotlinType", "isDeprecated", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "isHiddenByDeprecation", "isJvmStatic", "lightMethod", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightMethodBuilder;", "looksLikeDeprecated", "looksLikeLevelArgument", "Lorg/jetbrains/kotlin/psi/KtValueArgument;", "mapType", "mapTypeToSignatureWriter", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;", "resolve", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "safeIsLocal", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "safeIsScript", "Lorg/jetbrains/kotlin/psi/KtFile;", "safeScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "simpleVisibility", "toLightAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAnnotationForSourceEntry;", "site", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "light-classes", "accessFlags"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UltraLightUtilsKt {
    private static final UltraLightUtilsKt$supportForDescriptor$1 supportForDescriptor = new TypeParametersSupport<CallableMemberDescriptor, TypeParameterDescriptor>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$supportForDescriptor$1
        @Override // org.jetbrains.kotlin.asJava.classes.TypeParametersSupport
        public TypeParameterDescriptor asDescriptor(TypeParameterDescriptor typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            return typeParameter;
        }

        @Override // org.jetbrains.kotlin.asJava.classes.TypeParametersSupport
        public List<TypeParameterDescriptor> parameters(CallableMemberDescriptor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            List<TypeParameterDescriptor> typeParameters = declaration.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            return typeParameters;
        }

        @Override // org.jetbrains.kotlin.asJava.classes.TypeParametersSupport
        public String name(TypeParameterDescriptor typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            String strAsString = typeParameter.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }

        @Override // org.jetbrains.kotlin.asJava.classes.TypeParametersSupport
        public boolean hasNonTrivialBounds(CallableMemberDescriptor declaration, TypeParameterDescriptor typeParameter) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            List<KotlinType> upperBounds = typeParameter.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            List<KotlinType> list = upperBounds;
            if ((list instanceof Collection) && list.isEmpty()) {
                return false;
            }
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (!KotlinBuiltIns.isDefaultBound((KotlinType) it.next())) {
                    return true;
                }
            }
            return false;
        }
    };
    private static final UltraLightUtilsKt$supportForSourceDeclaration$1 supportForSourceDeclaration = new TypeParametersSupport<KtTypeParameterListOwner, KtTypeParameter>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$supportForSourceDeclaration$1
        @Override // org.jetbrains.kotlin.asJava.classes.TypeParametersSupport
        public List<KtTypeParameter> parameters(KtTypeParameterListOwner declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            List<KtTypeParameter> typeParameters = declaration.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            return typeParameters;
        }

        @Override // org.jetbrains.kotlin.asJava.classes.TypeParametersSupport
        public String name(KtTypeParameter typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            return typeParameter.getMyName();
        }

        @Override // org.jetbrains.kotlin.asJava.classes.TypeParametersSupport
        public boolean hasNonTrivialBounds(KtTypeParameterListOwner declaration, KtTypeParameter typeParameter) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            if (typeParameter.getExtendsBound() == null) {
                List<KtTypeConstraint> typeConstraints = declaration.getTypeConstraints();
                Intrinsics.checkNotNullExpressionValue(typeConstraints, "getTypeConstraints(...)");
                if (typeConstraints.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.asJava.classes.TypeParametersSupport
        public TypeParameterDescriptor asDescriptor(KtTypeParameter typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(typeParameter);
            if (declarationDescriptorResolve instanceof TypeParameterDescriptor) {
                return (TypeParameterDescriptor) declarationDescriptorResolve;
            }
            return null;
        }
    };

    public static final PsiTypeParameterList buildTypeParameterListForDescriptor(CallableMemberDescriptor declaration, PsiTypeParameterListOwner owner, KtUltraLightSupport support) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(support, "support");
        return buildTypeParameterList(declaration, owner, support, supportForDescriptor);
    }

    public static final PsiTypeParameterList buildTypeParameterListForSourceDeclaration(KtTypeParameterListOwner declaration, PsiTypeParameterListOwner owner, KtUltraLightSupport support) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(support, "support");
        return buildTypeParameterList(declaration, owner, support, supportForSourceDeclaration);
    }

    private static final <D, T> PsiTypeParameterList buildTypeParameterList(final D d, PsiTypeParameterListOwner psiTypeParameterListOwner, final KtUltraLightSupport ktUltraLightSupport, final TypeParametersSupport<D, T> typeParametersSupport) {
        KotlinLightTypeParameterListBuilder kotlinLightTypeParameterListBuilder = new KotlinLightTypeParameterListBuilder(psiTypeParameterListOwner);
        Iterator<T> it = typeParametersSupport.parameters(d).iterator();
        int i = 0;
        while (true) {
            int i2 = i;
            if (it.hasNext()) {
                i = i2 + 1;
                final T next = it.next();
                Function1<PsiElement, KotlinLightReferenceListBuilder> function1 = new Function1<PsiElement, KotlinLightReferenceListBuilder>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$buildTypeParameterList$referenceListBuilder$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

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
                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinLightReferenceListBuilder invoke(PsiElement element) {
                        Intrinsics.checkNotNullParameter(element, "element");
                        KotlinLightReferenceListBuilder kotlinLightReferenceListBuilder = new KotlinLightReferenceListBuilder(element.getManager(), PsiReferenceList.Role.EXTENDS_BOUNDS_LIST);
                        if (typeParametersSupport.hasNonTrivialBounds(d, next)) {
                            TypeParameterDescriptor typeParameterDescriptorAsDescriptor = typeParametersSupport.asDescriptor(next);
                            List<KotlinType> upperBounds = typeParameterDescriptorAsDescriptor != null ? typeParameterDescriptorAsDescriptor.getUpperBounds() : null;
                            if (upperBounds == null) {
                                upperBounds = CollectionsKt.emptyList();
                            }
                            KtUltraLightSupport ktUltraLightSupport2 = ktUltraLightSupport;
                            ArrayList arrayList = new ArrayList();
                            for (KotlinType kotlinType : upperBounds) {
                                Intrinsics.checkNotNull(kotlinType);
                                PsiType psiTypeAsPsiType = UltraLightUtilsKt.asPsiType(kotlinType, ktUltraLightSupport2, TypeMappingMode.DEFAULT, element);
                                PsiClassType psiClassType = psiTypeAsPsiType instanceof PsiClassType ? (PsiClassType) psiTypeAsPsiType : null;
                                if (psiClassType != null) {
                                    arrayList.add(psiClassType);
                                }
                            }
                            ArrayList arrayList2 = arrayList;
                            if (arrayList2.size() != 1 || !((PsiClassType) arrayList2.get(0)).equalsToText(CommonClassNames.JAVA_LANG_OBJECT)) {
                                Iterator it2 = arrayList2.iterator();
                                while (it2.hasNext()) {
                                    kotlinLightReferenceListBuilder.addReference((PsiClassType) it2.next());
                                }
                            }
                        }
                        return kotlinLightReferenceListBuilder;
                    }
                };
                String strName = typeParametersSupport.name(next);
                if (strName == null) {
                    strName = "";
                }
                kotlinLightTypeParameterListBuilder.addParameter(new KtUltraLightTypeParameter(strName, psiTypeParameterListOwner, kotlinLightTypeParameterListBuilder, i2, function1));
            } else {
                return kotlinLightTypeParameterListBuilder;
            }
        }
    }

    public static final KotlinType getKotlinType(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        DeclarationDescriptor declarationDescriptorResolve = resolve(ktDeclaration);
        if (declarationDescriptorResolve instanceof ValueDescriptor) {
            return ((ValueDescriptor) declarationDescriptorResolve).getType();
        }
        if (!(declarationDescriptorResolve instanceof CallableDescriptor)) {
            return null;
        }
        if ((declarationDescriptorResolve instanceof FunctionDescriptor) && ((FunctionDescriptor) declarationDescriptorResolve).isSuspend()) {
            return DescriptorUtilsKt.getModule(declarationDescriptorResolve).getBuiltIns().getNullableAnyType();
        }
        return ((CallableDescriptor) declarationDescriptorResolve).getReturnType();
    }

    public static final DeclarationDescriptor resolve(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = ktDeclaration.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).resolveToDescriptor(ktDeclaration);
    }

    public static final BindingContext analyze(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = ktElement.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).analyze(ktElement);
    }

    public static final AnnotationDescriptor analyzeAnnotation(KtAnnotationEntry ktAnnotationEntry) {
        Intrinsics.checkNotNullParameter(ktAnnotationEntry, "<this>");
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = ktAnnotationEntry.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).analyzeAnnotation(ktAnnotationEntry);
    }

    public static final PsiType asPsiType(final KotlinType kotlinType, KtUltraLightSupport support, final TypeMappingMode mode, PsiElement psiContext) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(psiContext, "psiContext");
        return mapType(support, kotlinType, psiContext, new Function2<KotlinTypeMapper, JvmSignatureWriter, Unit>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt.asPsiType.1
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
            public final void invoke2(KotlinTypeMapper typeMapper, JvmSignatureWriter signatureWriter) {
                Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
                Intrinsics.checkNotNullParameter(signatureWriter, "signatureWriter");
                typeMapper.mapType(kotlinType, signatureWriter, mode);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence<List<PsiAnnotation>> annotateByKotlinType$getAnnotationsSequence(KotlinType kotlinType, PsiTypeElement psiTypeElement) {
        return SequencesKt.sequence(new UltraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1(kotlinType, psiTypeElement, null));
    }

    private static final PsiType annotateByKotlinType(PsiType psiType, KotlinType kotlinType, PsiTypeElement psiTypeElement) {
        return ImplUtilsKt.annotateByTypeAnnotationProvider(psiType, annotateByKotlinType$getAnnotationsSequence(kotlinType, psiTypeElement));
    }

    public static final PsiType mapType(KtUltraLightSupport ktUltraLightSupport, KotlinType kotlinType, PsiElement psiContext, Function2<? super KotlinTypeMapper, ? super JvmSignatureWriter, Unit> mapTypeToSignatureWriter) {
        Intrinsics.checkNotNullParameter(ktUltraLightSupport, "<this>");
        Intrinsics.checkNotNullParameter(psiContext, "psiContext");
        Intrinsics.checkNotNullParameter(mapTypeToSignatureWriter, "mapTypeToSignatureWriter");
        BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.SKIP_CHECKS);
        mapTypeToSignatureWriter.invoke(ktUltraLightSupport.getTypeMapper(), bothSignatureWriter);
        String string = bothSignatureWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return createTypeFromCanonicalText(kotlinType, string, psiContext);
    }

    private static final PsiType createTypeFromCanonicalText(KotlinType kotlinType, String str, PsiElement psiElement) throws ClsFormatException {
        PsiType type;
        String typeString = SignatureParsing.parseTypeString(new StringCharacterIterator(str), StubBuildingVisitor.GUESSING_MAPPER);
        Intrinsics.checkNotNullExpressionValue(typeString, "parseTypeString(...)");
        TypeInfo typeInfoFromString = TypeInfo.fromString(typeString, false);
        Intrinsics.checkNotNullExpressionValue(typeInfoFromString, "fromString(...)");
        String strCreateTypeText = TypeInfo.createTypeText(typeInfoFromString);
        if (strCreateTypeText == null) {
            PsiPrimitiveType NULL = PsiType.NULL;
            Intrinsics.checkNotNullExpressionValue(NULL, "NULL");
            return NULL;
        }
        ClsTypeElementImpl clsTypeElementImpl = new ClsTypeElementImpl(psiElement, strCreateTypeText, (char) 0);
        if (kotlinType != null) {
            PsiType type2 = clsTypeElementImpl.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            type = annotateByKotlinType(type2, kotlinType, clsTypeElementImpl);
        } else {
            type = clsTypeElementImpl.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        }
        if (!(type instanceof PsiArrayType) || !(psiElement instanceof KtUltraLightParameter) || !((KtUltraLightParameter) psiElement).getMyVarArgs()) {
            return type;
        }
        PsiArrayType psiArrayType = (PsiArrayType) type;
        return new PsiEllipsisType(psiArrayType.getComponentType(), psiArrayType.getAnnotationProvider());
    }

    public static final String tryGetPredefinedName(ClassDescriptor klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        SourceElement source = klass.getSourceElement();
        KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
        KtElement psi = kotlinSourceElement != null ? kotlinSourceElement.getPsi() : null;
        KtClassOrObject ktClassOrObject = psi instanceof KtClassOrObject ? (KtClassOrObject) psi : null;
        if (ktClassOrObject == null || !((Boolean) ApplicationManager.getApplication().runReadAction(new UltraLightUtilsKt$safeIsLocal$$inlined$runReadAction$1(ktClassOrObject))).booleanValue()) {
            return null;
        }
        Name nameAsName = ktClassOrObject.getNameAsName();
        if (nameAsName == null) {
            nameAsName = SpecialNames.NO_NAME_PROVIDED;
        }
        return nameAsName.asString();
    }

    public static final KotlinType cleanFromAnonymousTypes(KotlinType kotlinType) {
        KotlinType nullableAnyType;
        KotlinType kotlinTypeCleanFromAnonymousTypes;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            return null;
        }
        if (DescriptorUtils.isAnonymousObject(classDescriptor)) {
            SimpleType defaultType = classDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            KotlinType kotlinType2 = (KotlinType) CollectionsKt.firstOrNull(TypeUtilsKt.supertypes(defaultType));
            if (kotlinType2 == null) {
                return null;
            }
            KotlinType kotlinTypeCleanFromAnonymousTypes2 = cleanFromAnonymousTypes(kotlinType2);
            return kotlinTypeCleanFromAnonymousTypes2 == null ? kotlinType2 : kotlinTypeCleanFromAnonymousTypes2;
        }
        if (kotlinType.getArguments().isEmpty()) {
            return null;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        boolean z = false;
        for (TypeProjectionImpl typeProjectionImpl : arguments) {
            TypeProjection typeProjection = !typeProjectionImpl.isStarProjection() ? typeProjectionImpl : null;
            if (typeProjection != null && (nullableAnyType = typeProjection.getNullableAnyType()) != null && (kotlinTypeCleanFromAnonymousTypes = cleanFromAnonymousTypes(nullableAnyType)) != null) {
                typeProjectionImpl = new TypeProjectionImpl(typeProjectionImpl.getProjectionKind(), kotlinTypeCleanFromAnonymousTypes);
                z = true;
            }
            arrayList.add(typeProjectionImpl);
        }
        ArrayList arrayList2 = arrayList;
        if (z) {
            return TypeSubstitutionKt.replace$default(kotlinType, arrayList2, null, null, 6, null);
        }
        return null;
    }

    public static /* synthetic */ KtLightMethod createGeneratedMethodFromDescriptor$default(KtUltraLightClass ktUltraLightClass, FunctionDescriptor functionDescriptor, JvmDeclarationOriginKind jvmDeclarationOriginKind, KtDeclaration ktDeclaration, int i, Object obj) {
        if ((i & 2) != 0) {
            jvmDeclarationOriginKind = JvmDeclarationOriginKind.OTHER;
        }
        if ((i & 4) != 0) {
            ktDeclaration = null;
        }
        return createGeneratedMethodFromDescriptor(ktUltraLightClass, functionDescriptor, jvmDeclarationOriginKind, ktDeclaration);
    }

    public static final KtLightMethod createGeneratedMethodFromDescriptor(KtUltraLightClass ktUltraLightClass, FunctionDescriptor descriptor, JvmDeclarationOriginKind declarationOriginKindForOrigin, KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktUltraLightClass, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(declarationOriginKindForOrigin, "declarationOriginKindForOrigin");
        if (ktDeclaration == null) {
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(descriptor);
            ktDeclaration = psiElementDescriptorToDeclaration instanceof KtDeclaration ? (KtDeclaration) psiElementDescriptorToDeclaration : null;
            if (ktDeclaration == null) {
                ktDeclaration = ktUltraLightClass.getKotlinOrigin();
            }
        }
        return new KtUltraLightMethodForDescriptor(descriptor, lightMethod(ktUltraLightClass, descriptor), new LightMemberOriginForDeclaration(ktDeclaration, declarationOriginKindForOrigin, null, null, 12, null), ktUltraLightClass.getSupport(), ktUltraLightClass);
    }

    private static final LightMethodBuilder lightMethod(final KtUltraLightClass ktUltraLightClass, FunctionDescriptor functionDescriptor) {
        String name2 = functionDescriptor instanceof ConstructorDescriptor ? ktUltraLightClass.getMyName() : ktUltraLightClass.getSupport().getTypeMapper().mapFunctionName(functionDescriptor, OwnerKind.IMPLEMENTATION);
        final int methodAsmFlags = DescriptorAsmUtil.getMethodAsmFlags(functionDescriptor, OwnerKind.IMPLEMENTATION, ktUltraLightClass.getSupport().getDeprecationResolver(), UltraLightSupportKt.getJvmDefaultMode(ktUltraLightClass.getSupport()));
        return new LightMethodBuilder(ktUltraLightClass.getManager(), ktUltraLightClass.getLanguage(), name2, new LightParameterListBuilder(ktUltraLightClass.getManager(), ktUltraLightClass.getLanguage()), new LightModifierList(ImplUtilsKt.lazyPub(new Function0<Integer>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$lightMethod$accessFlags$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                int i = methodAsmFlags;
                DeclarationDescriptor declarationDescriptorResolve = UltraLightUtilsKt.resolve(ktUltraLightClass.getKotlinOrigin());
                return Integer.valueOf(UltraLightUtilsKt.packMethodFlags(i, JvmCodegenUtil.isJvmInterface(declarationDescriptorResolve instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptorResolve : null)));
            }
        }), ktUltraLightClass.getManager(), ktUltraLightClass.getLanguage(), new String[0]) { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt.lightMethod.1
            final /* synthetic */ Lazy<Integer> $accessFlags$delegate;

            {
                KotlinLanguage kotlinLanguage = kotlinLanguage;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
            public boolean hasModifierProperty(String name3) {
                Intrinsics.checkNotNullParameter(name3, "name");
                return ModifierFlags.hasModifierProperty(name3, UltraLightUtilsKt.lightMethod$lambda$2(this.$accessFlags$delegate));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int lightMethod$lambda$2(Lazy<Integer> lazy) {
        return lazy.getValue().intValue();
    }

    private static final int packCommonFlags(int i) {
        int i2 = 2;
        if (!BitUtil.isSet(i, 2)) {
            i2 = 4;
            if (!BitUtil.isSet(i, 4)) {
                i2 = 1;
                if (!BitUtil.isSet(i, 1)) {
                    i2 = 4096;
                }
            }
        }
        if (BitUtil.isSet(i, 8)) {
            i2 |= 8;
        }
        return BitUtil.isSet(i, 16) ? i2 | 16 : i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int packMethodFlags(int i, boolean z) {
        int iPackCommonFlags = packCommonFlags(i);
        if (BitUtil.isSet(i, 32)) {
            iPackCommonFlags |= 32;
        }
        if (BitUtil.isSet(i, 256)) {
            iPackCommonFlags |= 256;
        }
        if (BitUtil.isSet(i, 2048)) {
            iPackCommonFlags |= 2048;
        }
        if (BitUtil.isSet(i, 1024)) {
            return iPackCommonFlags | 1024;
        }
        return (!z || BitUtil.isSet(i, 8)) ? iPackCommonFlags : iPackCommonFlags | 512;
    }

    public static final boolean isHiddenByDeprecation(KtModifierListOwner ktModifierListOwner, KtUltraLightSupport support) {
        Name enumEntryName;
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        Intrinsics.checkNotNullParameter(support, "support");
        if (ktModifierListOwner.getAnnotationEntries().isEmpty()) {
            return false;
        }
        List<KtAnnotationEntry> annotationEntries = ktModifierListOwner.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : annotationEntries) {
            KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) obj;
            Intrinsics.checkNotNull(ktAnnotationEntry);
            if (looksLikeDeprecated(ktAnnotationEntry)) {
                arrayList.add(obj);
            }
        }
        if (arrayList.isEmpty()) {
            return false;
        }
        Pair<KtAnnotationEntry, AnnotationDescriptor> pairFindAnnotation = findAnnotation(support, ktModifierListOwner, StandardNames.FqNames.deprecated);
        String strAsString = null;
        AnnotationDescriptor second = pairFindAnnotation != null ? pairFindAnnotation.getSecond() : null;
        ConstantValue<?> constantValueArgumentValue = second != null ? AnnotationUtilKt.argumentValue(second, FirebaseAnalytics.Param.LEVEL) : null;
        EnumValue enumValue = constantValueArgumentValue instanceof EnumValue ? (EnumValue) constantValueArgumentValue : null;
        if (enumValue != null && (enumEntryName = enumValue.getEnumEntryName()) != null) {
            strAsString = enumEntryName.asString();
        }
        return Intrinsics.areEqual(strAsString, VirtualFile.PROP_HIDDEN);
    }

    public static final boolean looksLikeDeprecated(KtAnnotationEntry ktAnnotationEntry) {
        Intrinsics.checkNotNullParameter(ktAnnotationEntry, "<this>");
        List<? extends ValueArgument> valueArguments = ktAnnotationEntry.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueArguments) {
            if (obj instanceof KtValueArgument) {
                arrayList.add(obj);
            }
        }
        ArrayList<KtValueArgument> arrayList2 = new ArrayList();
        int i = 0;
        for (Object obj2 : arrayList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KtValueArgument ktValueArgument = (KtValueArgument) obj2;
            if (i == 2 || looksLikeLevelArgument(ktValueArgument)) {
                arrayList2.add(obj2);
            }
            i = i2;
        }
        for (KtValueArgument ktValueArgument2 : arrayList2) {
            PsiElement[] children = ktValueArgument2.getChildren();
            Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
            ArrayList arrayList3 = new ArrayList();
            for (PsiElement psiElement : children) {
                if (psiElement instanceof KtDotQualifiedExpression) {
                    arrayList3.add(psiElement);
                }
            }
            ArrayList arrayList4 = new ArrayList();
            for (Object obj3 : arrayList3) {
                PsiElement[] children2 = ((KtDotQualifiedExpression) obj3).getChildren();
                Intrinsics.checkNotNullExpressionValue(children2, "getChildren(...)");
                PsiElement psiElement2 = (PsiElement) ArraysKt.last(children2);
                if (psiElement2 instanceof KtNameReferenceExpression ? Intrinsics.areEqual(((KtNameReferenceExpression) psiElement2).getReferencedName(), VirtualFile.PROP_HIDDEN) : false) {
                    arrayList4.add(obj3);
                }
            }
            ArrayList arrayList5 = arrayList4;
            PsiElement[] children3 = ktValueArgument2.getChildren();
            Intrinsics.checkNotNullExpressionValue(children3, "getChildren(...)");
            ArrayList arrayList6 = new ArrayList();
            for (PsiElement psiElement3 : children3) {
                if (psiElement3 instanceof KtNameReferenceExpression) {
                    arrayList6.add(psiElement3);
                }
            }
            ArrayList arrayList7 = new ArrayList();
            for (Object obj4 : arrayList6) {
                if (Intrinsics.areEqual(((KtNameReferenceExpression) obj4).getReferencedName(), VirtualFile.PROP_HIDDEN)) {
                    arrayList7.add(obj4);
                }
            }
            ArrayList arrayList8 = arrayList7;
            if (!arrayList5.isEmpty() || !arrayList8.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean looksLikeLevelArgument(KtValueArgument ktValueArgument) {
        Intrinsics.checkNotNullParameter(ktValueArgument, "<this>");
        PsiElement[] children = ktValueArgument.getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiElement psiElement : children) {
            if (psiElement instanceof KtValueArgumentName) {
                arrayList.add(psiElement);
            }
        }
        ArrayList arrayList2 = arrayList;
        if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
            return false;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((KtValueArgumentName) it.next()).getAsName().asString(), FirebaseAnalytics.Param.LEVEL)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isJvmStatic(KtAnnotated ktAnnotated, KtUltraLightSupport support) {
        Intrinsics.checkNotNullParameter(ktAnnotated, "<this>");
        Intrinsics.checkNotNullParameter(support, "support");
        return findAnnotation(support, ktAnnotated, AnnotationUtilKt.getJVM_STATIC_ANNOTATION_FQ_NAME()) != null;
    }

    public static final String simpleVisibility(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        if (ktDeclaration.hasModifier(KtTokens.PRIVATE_KEYWORD)) {
            return "private";
        }
        return ktDeclaration.hasModifier(KtTokens.PROTECTED_KEYWORD) ? "protected" : "public";
    }

    public static /* synthetic */ boolean isDeprecated$default(KtModifierListOwner ktModifierListOwner, KtUltraLightSupport ktUltraLightSupport, int i, Object obj) {
        if ((i & 1) != 0) {
            ktUltraLightSupport = null;
        }
        return isDeprecated(ktModifierListOwner, ktUltraLightSupport);
    }

    public static final boolean isDeprecated(KtModifierListOwner ktModifierListOwner, KtUltraLightSupport ktUltraLightSupport) {
        FqName qualifiedName;
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        KtModifierList modifierList = ktModifierListOwner.getModifierList();
        if (modifierList == null || modifierList.getAnnotationEntries().isEmpty()) {
            return false;
        }
        FqName fqName = StandardNames.FqNames.deprecated;
        String strAsString = fqName.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Iterator<KtAnnotationEntry> it = modifierList.getAnnotationEntries().iterator();
        while (true) {
            if (!it.hasNext()) {
                return (ktUltraLightSupport != null ? findAnnotation(ktUltraLightSupport, ktModifierListOwner, StandardNames.FqNames.deprecated) : null) != null;
            }
            KtTypeReference typeReference = it.next().getTypeReference();
            KtTypeElement typeElement = typeReference != null ? typeReference.getTypeElement() : null;
            KtUserType ktUserType = typeElement instanceof KtUserType ? (KtUserType) typeElement : null;
            if (ktUserType != null && (qualifiedName = toQualifiedName(ktUserType)) != null && (Intrinsics.areEqual(qualifiedName, fqName) || Intrinsics.areEqual(qualifiedName.asString(), strAsString))) {
                return true;
            }
        }
    }

    private static final FqName toQualifiedName(KtUserType ktUserType) {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        while (ktUserType != null) {
            String referencedName = ktUserType.getReferencedName();
            if (referencedName == null) {
                return null;
            }
            arrayListNewArrayList.add(referencedName);
            ktUserType = ktUserType.getQualifier();
        }
        return FqName.fromSegments(ContainerUtil.reverse(arrayListNewArrayList));
    }

    public static final PsiExpression createPsiLiteral(ConstantValue<?> constantValue, PsiElement parent) {
        Intrinsics.checkNotNullParameter(constantValue, "<this>");
        Intrinsics.checkNotNullParameter(parent, "parent");
        try {
            return PsiElementFactory.getInstance(parent.getProject()).createExpressionFromText(asStringForPsiLiteral(constantValue, parent), parent);
        } catch (IncorrectOperationException unused) {
            return null;
        }
    }

    private static final String escapeString(String str) {
        String strValueOf;
        StringBuilder sb = new StringBuilder();
        String str2 = str;
        for (int i = 0; i < str2.length(); i++) {
            char cCharAt = str2.charAt(i);
            if (cCharAt == '\n') {
                strValueOf = "\\n";
            } else if (cCharAt == '\r') {
                strValueOf = "\\r";
            } else if (cCharAt == '\t') {
                strValueOf = "\\t";
            } else if (cCharAt == '\"') {
                strValueOf = "\\\"";
            } else if (cCharAt == '\\') {
                strValueOf = "\\\\";
            } else {
                strValueOf = String.valueOf(cCharAt);
            }
            sb.append(strValueOf);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final String asStringForPsiLiteral(ConstantValue<?> constantValue, PsiElement psiElement) {
        if (constantValue instanceof NullValue) {
            return "null";
        }
        if (constantValue instanceof StringValue) {
            return "\"" + escapeString(((StringValue) constantValue).getValue()) + '\"';
        }
        if (constantValue instanceof KClassValue) {
            KClassValue.Value value = ((KClassValue) constantValue).getValue();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.constants.KClassValue.Value.NormalClass");
            ClassLiteralValue value2 = ((KClassValue.Value.NormalClass) value).getValue();
            String strRepeat = StringsKt.repeat(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, value2.getArrayNestedness());
            FqName fqNameAsSingleFqName = value2.getClassId().asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
            String strAsString = fqNameAsSingleFqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String canonicalText = TypeConversionUtil.erasure(KtLightAnnotationsValuesKt.psiType(strAsString, psiElement, value2.getArrayNestedness() > 0)).getCanonicalText(false);
            Intrinsics.checkNotNullExpressionValue(canonicalText, "getCanonicalText(...)");
            return canonicalText + strRepeat + ".class";
        }
        if (constantValue instanceof EnumValue) {
            EnumValue enumValue = (EnumValue) constantValue;
            return enumValue.getEnumClassId().asSingleFqName().asString() + '.' + enumValue.getEnumEntryName();
        }
        Object value3 = constantValue.getValue();
        if (value3 instanceof Long) {
            return new StringBuilder().append(constantValue.getValue()).append('L').toString();
        }
        return value3 instanceof Float ? new StringBuilder().append(constantValue.getValue()).append('f').toString() : String.valueOf(constantValue.getValue());
    }

    public static final void applyCompilerPlugins(Project project, Function1<? super UltraLightClassModifierExtension, Unit> body) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        Iterator<T> it = UltraLightClassModifierExtension.INSTANCE.getInstances(project).iterator();
        while (it.hasNext()) {
            body.invoke((UltraLightClassModifierExtension) it.next());
        }
    }

    public static final <T> T runReadAction(final Function0<? extends T> runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        return (T) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt.runReadAction.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return runnable.invoke();
            }
        });
    }

    public static final Pair<KtAnnotationEntry, AnnotationDescriptor> findAnnotation(KtUltraLightSupport ktUltraLightSupport, KtAnnotated owner, FqName fqName) {
        DeclarationDescriptor declarationDescriptorResolve;
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation;
        KtAnnotationEntry ktAnnotationEntry;
        AnnotationDescriptor annotationDescriptorAnalyzeAnnotation;
        Intrinsics.checkNotNullParameter(ktUltraLightSupport, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        List<KtAnnotationEntry> annotationEntries = owner.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : annotationEntries) {
            Name shortName = ((KtAnnotationEntry) obj).getShortName();
            if (shortName != null) {
                if (!Intrinsics.areEqual(shortName, fqName.shortName())) {
                    KtFile containingKtFile = owner.getContainingKtFile();
                    Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
                    Intrinsics.checkNotNull(shortName);
                    if (ktUltraLightSupport.possiblyHasAlias(containingKtFile, shortName)) {
                    }
                }
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        do {
            if (it.hasNext()) {
                ktAnnotationEntry = (KtAnnotationEntry) it.next();
                Intrinsics.checkNotNull(ktAnnotationEntry);
                annotationDescriptorAnalyzeAnnotation = analyzeAnnotation(ktAnnotationEntry);
            } else {
                if (!(owner instanceof KtPropertyAccessor)) {
                    return null;
                }
                KtProperty property = ((KtPropertyAccessor) owner).getProperty();
                Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
                if (findAnnotation(ktUltraLightSupport, property, fqName) == null || (declarationDescriptorResolve = resolve((KtDeclaration) owner)) == null || (annotationDescriptorMo6752findAnnotation = declarationDescriptorResolve.getAnnotations().mo6752findAnnotation(fqName)) == null) {
                    return null;
                }
                PsiElement psi = PsiSourceElementKt.getPsi(annotationDescriptorMo6752findAnnotation.getSource());
                KtAnnotationEntry ktAnnotationEntry2 = psi instanceof KtAnnotationEntry ? (KtAnnotationEntry) psi : null;
                if (ktAnnotationEntry2 == null) {
                    return null;
                }
                return TuplesKt.to(ktAnnotationEntry2, annotationDescriptorMo6752findAnnotation);
            }
        } while (!Intrinsics.areEqual(annotationDescriptorAnalyzeAnnotation != null ? annotationDescriptorAnalyzeAnnotation.getFqName() : null, fqName));
        return new Pair<>(ktAnnotationEntry, annotationDescriptorAnalyzeAnnotation);
    }

    public static final List<KtLightAnnotationForSourceEntry> toLightAnnotations(List<? extends KtAnnotationEntry> list, PsiElement parent, AnnotationUseSiteTarget annotationUseSiteTarget) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(parent, "parent");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            KtAnnotationUseSiteTarget useSiteTarget = ((KtAnnotationEntry) next).getUseSiteTarget();
            if ((useSiteTarget != null ? useSiteTarget.getAnnotationUseSiteTarget() : null) == annotationUseSiteTarget) {
                arrayList.add(next);
            }
        }
        ArrayList<KtAnnotationEntry> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (final KtAnnotationEntry ktAnnotationEntry : arrayList2) {
            Name shortName = ktAnnotationEntry.getShortName();
            arrayList3.add(new KtLightAnnotationForSourceEntry(shortName != null ? shortName.getIdentifier() : null, new Function0<String>() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$toLightAnnotations$2$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    FqName fqName;
                    AnnotationDescriptor annotationDescriptorAnalyzeAnnotation = UltraLightUtilsKt.analyzeAnnotation(ktAnnotationEntry);
                    if (annotationDescriptorAnalyzeAnnotation == null || (fqName = annotationDescriptorAnalyzeAnnotation.getFqName()) == null) {
                        return null;
                    }
                    return fqName.asString();
                }
            }, ktAnnotationEntry, parent));
        }
        return arrayList3;
    }

    public static final boolean safeIsLocal(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        return ((Boolean) ApplicationManager.getApplication().runReadAction(new UltraLightUtilsKt$safeIsLocal$$inlined$runReadAction$1(ktClassOrObject))).booleanValue();
    }

    public static final boolean safeIsScript(final KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return ((Boolean) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$safeIsScript$$inlined$runReadAction$1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return (T) Boolean.valueOf(ktFile.isScript());
            }
        })).booleanValue();
    }

    public static final KtScript safeScript(final KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return (KtScript) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$safeScript$$inlined$runReadAction$1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return (T) ktFile.getScript();
            }
        });
    }
}
