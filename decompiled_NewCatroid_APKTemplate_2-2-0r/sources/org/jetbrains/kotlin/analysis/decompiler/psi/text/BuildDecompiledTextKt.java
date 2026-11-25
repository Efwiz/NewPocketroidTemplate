package org.jetbrains.kotlin.analysis.decompiler.psi.text;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.contracts.description.AbstractContractProvider;
import org.jetbrains.kotlin.contracts.description.ContractProviderKey;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererModifier;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.renderer.RenderingUtilsKt;
import org.jetbrains.kotlin.resolve.DataClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: buildDecompiledText.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r\u001a\n\u0010\u000e\u001a\u00020\u000f*\u00020\u0010\u001a\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"DECOMPILED_CODE_COMMENT", "", "DECOMPILED_COMMENT_FOR_PARAMETER", "DECOMPILED_CONTRACT_STUB", "FLEXIBLE_TYPE_COMMENT", "buildDecompiledText", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/text/DecompiledText;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "descriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptorRenderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "defaultDecompilerRendererOptions", "", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "mustNotBeWrittenToDecompiledText", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "decompiler-to-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BuildDecompiledTextKt {

    /* JADX INFO: compiled from: buildDecompiledText.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CallableMemberDescriptor.Kind.values().length];
            try {
                iArr[CallableMemberDescriptor.Kind.DECLARATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CallableMemberDescriptor.Kind.FAKE_OVERRIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CallableMemberDescriptor.Kind.DELEGATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CallableMemberDescriptor.Kind.SYNTHESIZED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void defaultDecompilerRendererOptions(DescriptorRendererOptions descriptorRendererOptions) {
        Intrinsics.checkNotNullParameter(descriptorRendererOptions, "<this>");
        descriptorRendererOptions.setWithDefinedIn(false);
        descriptorRendererOptions.setClassWithPrimaryConstructor(true);
        descriptorRendererOptions.setSecondaryConstructorsAsPrimary(false);
        descriptorRendererOptions.setModifiers(DescriptorRendererModifier.ALL);
        descriptorRendererOptions.setExcludedTypeAnnotationClasses(SetsKt.emptySet());
        descriptorRendererOptions.setAlwaysRenderModifiers(true);
        descriptorRendererOptions.setParameterNamesInFunctionalTypes(false);
        descriptorRendererOptions.setDefaultParameterValueRenderer(new Function1<ValueParameterDescriptor, String>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.text.BuildDecompiledTextKt.defaultDecompilerRendererOptions.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ValueParameterDescriptor valueParameterDescriptor) {
                Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<anonymous parameter 0>");
                return "COMPILED_CODE";
            }
        });
        descriptorRendererOptions.setIncludePropertyConstant(true);
        descriptorRendererOptions.setPropertyConstantRenderer(new Function1<ConstantValue<?>, String>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.text.BuildDecompiledTextKt.defaultDecompilerRendererOptions.2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ConstantValue<?> constantValue) {
                Intrinsics.checkNotNullParameter(constantValue, "<anonymous parameter 0>");
                return "COMPILED_CODE";
            }
        });
    }

    public static final boolean mustNotBeWrittenToDecompiledText(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[callableMemberDescriptor.getKind().ordinal()];
        if (i == 1) {
            return false;
        }
        if (i != 2 && i != 3) {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            DataClassDescriptorResolver dataClassDescriptorResolver = DataClassDescriptorResolver.INSTANCE;
            Name name2 = callableMemberDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (dataClassDescriptorResolver.isComponentLike(name2) || CollectionsKt.listOf((Object[]) new Name[]{OperatorNameConventions.EQUALS, StandardNames.HASHCODE_NAME, OperatorNameConventions.TO_STRING}).contains(callableMemberDescriptor.getShortName())) {
                return false;
            }
        }
        return true;
    }

    public static final DecompiledText buildDecompiledText(FqName packageFqName, List<? extends DeclarationDescriptor> descriptors, DescriptorRenderer descriptorRenderer) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        Intrinsics.checkNotNullParameter(descriptorRenderer, "descriptorRenderer");
        StringBuilder sb = new StringBuilder();
        buildDecompiledText$appendDecompiledTextAndPackageName(sb, packageFqName);
        Iterator<? extends DeclarationDescriptor> it = descriptors.iterator();
        while (it.hasNext()) {
            buildDecompiledText$appendDescriptor$default(sb, descriptorRenderer, it.next(), "", null, 16, null);
            sb.append("\n");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return new DecompiledText(string);
    }

    private static final void buildDecompiledText$appendDecompiledTextAndPackageName(StringBuilder sb, FqName fqName) {
        sb.append("// IntelliJ API Decompiler stub source generated from a class file\n// Implementation of methods is not available");
        sb.append("\n\n");
        if (fqName.isRoot()) {
            return;
        }
        sb.append("package ").append(RenderingUtilsKt.render(fqName)).append("\n\n");
    }

    static /* synthetic */ void buildDecompiledText$appendDescriptor$default(StringBuilder sb, DescriptorRenderer descriptorRenderer, DeclarationDescriptor declarationDescriptor, String str, Boolean bool, int i, Object obj) {
        if ((i & 16) != 0) {
            bool = null;
        }
        buildDecompiledText$appendDescriptor(sb, descriptorRenderer, declarationDescriptor, str, bool);
    }

    private static final void buildDecompiledText$appendDescriptor(StringBuilder sb, DescriptorRenderer descriptorRenderer, DeclarationDescriptor declarationDescriptor, String str, Boolean bool) {
        if (DescriptorUtils.isEnumEntry(declarationDescriptor)) {
            Iterator<AnnotationDescriptor> it = declarationDescriptor.getAnnotations().iterator();
            while (it.hasNext()) {
                sb.append(DescriptorRenderer.renderAnnotation$default(descriptorRenderer, it.next(), null, 2, null));
                sb.append(" ");
            }
            String strAsString = declarationDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            sb.append(KtPsiUtilKt.quoteIfNeeded(strAsString));
            Intrinsics.checkNotNull(bool);
            sb.append(bool.booleanValue() ? ";" : ",");
        } else {
            sb.append(StringsKt.replace$default(descriptorRenderer.render(declarationDescriptor), "= ...", "/* = compiled code */", false, 4, (Object) null));
        }
        if (declarationDescriptor instanceof CallableDescriptor) {
            KotlinType returnType = ((CallableDescriptor) declarationDescriptor).getReturnType();
            Intrinsics.checkNotNull(returnType);
            if (FlexibleTypesKt.isFlexible(returnType)) {
                sb.append(" ").append("/* platform type */");
            }
        }
        boolean z = declarationDescriptor instanceof FunctionDescriptor;
        if (z || (declarationDescriptor instanceof PropertyDescriptor)) {
            if (((MemberDescriptor) declarationDescriptor).getModality() != Modality.ABSTRACT) {
                if (z) {
                    sb.append(" { ");
                    AbstractContractProvider abstractContractProvider = (AbstractContractProvider) ((FunctionDescriptor) declarationDescriptor).getUserData(ContractProviderKey.INSTANCE);
                    if ((abstractContractProvider != null ? abstractContractProvider.getContractDescription() : null) != null) {
                        sb.append("contract { /* compiled contract */ }").append("; ");
                    }
                    sb.append("/* compiled code */").append(" }");
                } else {
                    sb.append(" ").append("/* compiled code */");
                }
            }
            if (declarationDescriptor instanceof PropertyDescriptor) {
                for (PropertyAccessorDescriptor propertyAccessorDescriptor : ((PropertyDescriptor) declarationDescriptor).getAccessors()) {
                    if (!propertyAccessorDescriptor.isDefault()) {
                        sb.append("\n" + str + "    ");
                        sb.append(propertyAccessorDescriptor.getVisibilityImpl().getInternalDisplayName()).append(" ");
                        sb.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(propertyAccessorDescriptor.getModality().name())).append(" ");
                        if (propertyAccessorDescriptor.isExternal()) {
                            sb.append("external ");
                        }
                        Iterator<AnnotationDescriptor> it2 = propertyAccessorDescriptor.getAnnotations().iterator();
                        while (it2.hasNext()) {
                            sb.append(DescriptorRenderer.renderAnnotation$default(descriptorRenderer, it2.next(), null, 2, null));
                            sb.append(" ");
                        }
                        if (propertyAccessorDescriptor instanceof PropertyGetterDescriptor) {
                            sb.append("get");
                        } else if (propertyAccessorDescriptor instanceof PropertySetterDescriptor) {
                            sb.append("set(");
                            ValueParameterDescriptor valueParameterDescriptor = ((PropertySetterDescriptor) propertyAccessorDescriptor).getValueParameters().get(0);
                            Iterator<AnnotationDescriptor> it3 = valueParameterDescriptor.getAnnotations().iterator();
                            while (it3.hasNext()) {
                                sb.append(DescriptorRenderer.renderAnnotation$default(descriptorRenderer, it3.next(), null, 2, null));
                                sb.append(" ");
                            }
                            Name name2 = valueParameterDescriptor.getShortName();
                            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                            if (Intrinsics.areEqual(name2, SpecialNames.IMPLICIT_SET_PARAMETER)) {
                                name2 = StandardNames.DEFAULT_VALUE_PARAMETER;
                            }
                            StringBuilder sbAppend = sb.append(name2.asString()).append(": ");
                            KotlinType type = valueParameterDescriptor.getType();
                            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                            sbAppend.append(descriptorRenderer.renderType(type));
                            sb.append(Constant.AFTER_QUTO);
                            sb.append(" {").append("/* compiled code */").append(" }");
                        }
                    }
                }
            }
        } else if ((declarationDescriptor instanceof ClassDescriptor) && !DescriptorUtils.isEnumEntry(declarationDescriptor)) {
            sb.append(" {\n");
            String str2 = str + "    ";
            Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
            List listPlus = CollectionsKt.plus((Collection) DescriptorUtilsKt.getSecondaryConstructors(classDescriptor), (Iterable) ResolutionScope.DefaultImpls.getContributedDescriptors$default(classDescriptor.getDefaultType().getMemberScope(), null, null, 3, null));
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : listPlus) {
                if (DescriptorUtils.isEnumEntry((DeclarationDescriptor) obj)) {
                    arrayList.add(obj);
                } else {
                    arrayList2.add(obj);
                }
            }
            Pair pair = new Pair(arrayList, arrayList2);
            List<DeclarationDescriptor> list = (List) pair.component1();
            List<DeclarationDescriptor> list2 = (List) pair.component2();
            int i = 0;
            for (DeclarationDescriptor declarationDescriptor2 : list) {
                int i2 = i + 1;
                buildDecompiledText$appendDescriptor$newlineExceptFirst(booleanRef, sb);
                sb.append(str2);
                Intrinsics.checkNotNull(declarationDescriptor2);
                buildDecompiledText$appendDescriptor(sb, descriptorRenderer, declarationDescriptor2, str2, Boolean.valueOf(i == CollectionsKt.getLastIndex(list)));
                i = i2;
            }
            ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = classDescriptor.mo6346getCompanionObjectDescriptor();
            if (classDescriptorMo6346getCompanionObjectDescriptor != null) {
                buildDecompiledText$appendDescriptor$newlineExceptFirst(booleanRef, sb);
                sb.append(str2);
                buildDecompiledText$appendDescriptor$default(sb, descriptorRenderer, classDescriptorMo6346getCompanionObjectDescriptor, str2, null, 16, null);
            }
            for (DeclarationDescriptor declarationDescriptor3 : list2) {
                if (Intrinsics.areEqual(declarationDescriptor3.getContainingDeclaration(), declarationDescriptor) && !Intrinsics.areEqual(declarationDescriptor3, classDescriptorMo6346getCompanionObjectDescriptor) && (!(declarationDescriptor3 instanceof CallableMemberDescriptor) || !mustNotBeWrittenToDecompiledText((CallableMemberDescriptor) declarationDescriptor3))) {
                    buildDecompiledText$appendDescriptor$newlineExceptFirst(booleanRef, sb);
                    sb.append(str2);
                    Intrinsics.checkNotNull(declarationDescriptor3);
                    buildDecompiledText$appendDescriptor$default(sb, descriptorRenderer, declarationDescriptor3, str2, null, 16, null);
                }
            }
            sb.append(str).append("}");
        }
        sb.append("\n");
    }

    private static final void buildDecompiledText$appendDescriptor$newlineExceptFirst(Ref.BooleanRef booleanRef, StringBuilder sb) {
        if (booleanRef.element) {
            sb.append("\n");
        } else {
            booleanRef.element = true;
        }
    }
}
