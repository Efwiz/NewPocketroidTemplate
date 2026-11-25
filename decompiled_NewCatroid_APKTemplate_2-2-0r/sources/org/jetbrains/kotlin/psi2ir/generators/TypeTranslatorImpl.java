package org.jetbrains.kotlin.psi2ir.generators;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.ir.util.ConstantValueGenerator;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.ScopedTypeParametersResolver;
import org.jetbrains.kotlin.ir.util.StubGeneratorExtensions;
import org.jetbrains.kotlin.ir.util.TypeParametersResolver;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.AlternativeTypeSubstitutionKt;
import org.jetbrains.kotlin.types.CommonSupertypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: TypeTranslatorImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0018\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\f¢\u0006\u0002\u0010\u0012J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001dH\u0014J\u0016\u0010\u001f\u001a\u00020\u001d2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001d0!H\u0014J\u0010\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020$H\u0014R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/TypeTranslatorImpl;", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "typeParametersResolverBuilder", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/util/TypeParametersResolver;", "enterTableScope", "", "extensions", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "allowErrorTypeInAnnotations", "(Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lkotlin/jvm/functions/Function0;ZLorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;Lorg/jetbrains/kotlin/psi/KtFile;Z)V", "constantValueGenerator", "Lorg/jetbrains/kotlin/ir/util/ConstantValueGenerator;", "getConstantValueGenerator", "()Lorg/jetbrains/kotlin/ir/util/ConstantValueGenerator;", "typeApproximatorConfiguration", "org/jetbrains/kotlin/psi2ir/generators/TypeTranslatorImpl$typeApproximatorConfiguration$1", "Lorg/jetbrains/kotlin/psi2ir/generators/TypeTranslatorImpl$typeApproximatorConfiguration$1;", "typeApproximatorForNI", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "approximateType", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "commonSupertype", SpecTypePair.NAME_types, "", "isTypeAliasAccessibleHere", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeTranslatorImpl extends TypeTranslator {
    private final ConstantValueGenerator constantValueGenerator;
    private final KtFile ktFile;
    private final TypeTranslatorImpl$typeApproximatorConfiguration$1 typeApproximatorConfiguration;
    private final TypeApproximator typeApproximatorForNI;

    public /* synthetic */ TypeTranslatorImpl(ReferenceSymbolTable referenceSymbolTable, LanguageVersionSettings languageVersionSettings, ModuleDescriptor moduleDescriptor, Function0 function0, boolean z, StubGeneratorExtensions stubGeneratorExtensions, KtFile ktFile, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(referenceSymbolTable, languageVersionSettings, moduleDescriptor, (i & 8) != 0 ? new Function0<ScopedTypeParametersResolver>() { // from class: org.jetbrains.kotlin.psi2ir.generators.TypeTranslatorImpl.1
            @Override // kotlin.jvm.functions.Function0
            public final ScopedTypeParametersResolver invoke() {
                return new ScopedTypeParametersResolver();
            }
        } : function0, (i & 16) != 0 ? false : z, (i & 32) != 0 ? StubGeneratorExtensions.EMPTY : stubGeneratorExtensions, (i & 64) != 0 ? null : ktFile, (i & 128) != 0 ? false : z2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r7v2, types: [org.jetbrains.kotlin.psi2ir.generators.TypeTranslatorImpl$typeApproximatorConfiguration$1] */
    public TypeTranslatorImpl(ReferenceSymbolTable symbolTable, LanguageVersionSettings languageVersionSettings, ModuleDescriptor moduleDescriptor, Function0<? extends TypeParametersResolver> typeParametersResolverBuilder, boolean z, StubGeneratorExtensions extensions, KtFile ktFile, boolean z2) {
        super(symbolTable, languageVersionSettings, typeParametersResolverBuilder, z, extensions);
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(typeParametersResolverBuilder, "typeParametersResolverBuilder");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.ktFile = ktFile;
        this.constantValueGenerator = new ConstantValueGeneratorImpl(moduleDescriptor, symbolTable, this, z2);
        this.typeApproximatorForNI = new TypeApproximator(moduleDescriptor.getBuiltIns(), languageVersionSettings);
        this.typeApproximatorConfiguration = new TypeApproximatorConfiguration.AllFlexibleSameValue() { // from class: org.jetbrains.kotlin.psi2ir.generators.TypeTranslatorImpl$typeApproximatorConfiguration$1
            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration.AllFlexibleSameValue
            public boolean getAllFlexible() {
                return true;
            }

            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
            public boolean getErrorType() {
                return true;
            }

            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
            public boolean getIntegerLiteralConstantType() {
                return true;
            }

            @Override // org.jetbrains.kotlin.types.TypeApproximatorConfiguration
            public boolean getIntersectionTypesInContravariantPositions() {
                return true;
            }
        };
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeTranslator
    public ConstantValueGenerator getConstantValueGenerator() {
        return this.constantValueGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeTranslator
    protected KotlinType approximateType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        UnwrappedType unwrappedTypeSubstituteAlternativesInPublicType = AlternativeTypeSubstitutionKt.substituteAlternativesInPublicType(type);
        UnwrappedType unwrappedTypeApproximateToSuperType = this.typeApproximatorForNI.approximateToSuperType(unwrappedTypeSubstituteAlternativesInPublicType, (TypeApproximatorConfiguration) this.typeApproximatorConfiguration);
        if (unwrappedTypeApproximateToSuperType != null) {
            unwrappedTypeSubstituteAlternativesInPublicType = unwrappedTypeApproximateToSuperType;
        }
        return unwrappedTypeSubstituteAlternativesInPublicType;
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeTranslator
    protected KotlinType commonSupertype(Collection<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        KotlinType kotlinTypeCommonSupertype = CommonSupertypes.commonSupertype(types);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeCommonSupertype, "commonSupertype(...)");
        return kotlinTypeCommonSupertype;
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeTranslator
    protected boolean isTypeAliasAccessibleHere(TypeAliasDescriptor typeAliasDescriptor) {
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
        if (!DescriptorVisibilities.isPrivate(typeAliasDescriptor.getVisibilityImpl())) {
            return true;
        }
        SourceElement source = typeAliasDescriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        PsiFile containingFile = psi != null ? psi.getContainingFile() : null;
        if (containingFile == null) {
            return false;
        }
        return Intrinsics.areEqual(containingFile, this.ktFile);
    }
}
