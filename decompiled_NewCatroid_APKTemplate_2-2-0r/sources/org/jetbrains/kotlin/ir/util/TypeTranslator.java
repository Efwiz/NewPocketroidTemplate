package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.descriptors.IrBasedTypeParameterDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrDynamicTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrErrorTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.types.impl.IrTypeAbbreviationImpl;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypeBoundsChecker;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.RawType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.types.typesApproximation.CapturedTypeApproximationKt;
import org.jetbrains.kotlin.utils.ThreadLocalKt;

/* JADX INFO: compiled from: TypeTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u000e\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#J\u0010\u0010%\u001a\u00020#2\u0006\u0010$\u001a\u00020#H\u0002J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'H\u0002J\u000e\u0010)\u001a\u00020#2\u0006\u0010*\u001a\u00020#J\u0010\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020#H$J\u001e\u0010-\u001a\u00020.2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020#002\u0006\u00101\u001a\u000202H\u0002J'\u00103\u001a\u0002H4\"\u0004\b\u0000\u001042\u0006\u00105\u001a\u0002062\f\u00107\u001a\b\u0012\u0004\u0012\u0002H40\u0007¢\u0006\u0002\u00108J\u0016\u00109\u001a\u00020#2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020#00H$J\u000e\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u000206J\u0010\u0010>\u001a\u00020\n2\u0006\u0010?\u001a\u00020@H$J\u000e\u0010A\u001a\u00020<2\u0006\u0010=\u001a\u000206J\u0010\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0002J\u000e\u0010F\u001a\u00020G2\u0006\u0010*\u001a\u00020#J\u0018\u0010F\u001a\u00020.2\u0006\u0010*\u001a\u00020#2\u0006\u00101\u001a\u000202H\u0002J \u0010H\u001a\b\u0012\u0004\u0012\u00020J0I2\u0006\u0010*\u001a\u00020#2\b\b\u0002\u0010K\u001a\u00020#H\u0002J\u001c\u0010L\u001a\b\u0012\u0004\u0012\u00020M0I2\f\u0010N\u001a\b\u0012\u0004\u0012\u00020'0IH\u0002J'\u0010O\u001a\u0002H4\"\u0004\b\u0000\u001042\u0006\u0010P\u001a\u00020\u00142\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H40\u0007¢\u0006\u0002\u0010RJ\u001c\u0010S\u001a\u00020<*\b\u0012\u0004\u0012\u00020J0T2\b\u0010U\u001a\u0004\u0018\u00010VH\u0002J\f\u0010W\u001a\u00020\n*\u00020#H\u0002J\u000e\u0010X\u001a\u0004\u0018\u00010Y*\u00020ZH\u0002R\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001d\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\u001e\u0010\u001f¨\u0006["}, d2 = {"Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "typeParametersResolverBuilder", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/util/TypeParametersResolver;", "enterTableScope", "", "extensions", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "(Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lkotlin/jvm/functions/Function0;ZLorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;)V", "constantValueGenerator", "Lorg/jetbrains/kotlin/ir/util/ConstantValueGenerator;", "getConstantValueGenerator", "()Lorg/jetbrains/kotlin/ir/util/ConstantValueGenerator;", "erasureStack", "Ljava/util/Stack;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getExtensions", "()Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "isWithNewInference", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "supportDefinitelyNotNullTypes", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "typeParametersResolver", "getTypeParametersResolver", "()Lorg/jetbrains/kotlin/ir/util/TypeParametersResolver;", "typeParametersResolver$delegate", "Lkotlin/properties/ReadWriteProperty;", "approximate", "Lorg/jetbrains/kotlin/types/KotlinType;", "ktType", "approximateByKotlinRules", "approximateFunctionReferenceParameterType", "Lorg/jetbrains/kotlin/types/TypeProjection;", "typeProjection", "approximateFunctionReferenceType", "kotlinType", "approximateType", ModuleXmlParser.TYPE, "approximateUpperBounds", "Lorg/jetbrains/kotlin/ir/types/IrTypeProjection;", "upperBounds", "", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "buildWithScope", ExifInterface.GPS_DIRECTION_TRUE, "container", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "builder", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "commonSupertype", SpecTypePair.NAME_types, "enterScope", "", "irElement", "isTypeAliasAccessibleHere", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "leaveScope", "resolveTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "translateType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "translateTypeAnnotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "flexibleType", "translateTypeArguments", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "arguments", "withTypeErasure", "propertyDescriptor", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "addSpecialAnnotation", "", "irConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "isMutabilityFlexible", "toIrTypeAbbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "Lorg/jetbrains/kotlin/types/SimpleType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeTranslator {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(TypeTranslator.class), "typeParametersResolver", "getTypeParametersResolver()Lorg/jetbrains/kotlin/ir/util/TypeParametersResolver;"))};
    private final boolean enterTableScope;
    private final Stack<PropertyDescriptor> erasureStack;
    private final StubGeneratorExtensions extensions;
    private final boolean isWithNewInference;
    private final LanguageVersionSettings languageVersionSettings;
    private final boolean supportDefinitelyNotNullTypes;
    private final ReferenceSymbolTable symbolTable;

    /* JADX INFO: renamed from: typeParametersResolver$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty typeParametersResolver;

    protected abstract KotlinType approximateType(KotlinType type);

    protected abstract KotlinType commonSupertype(Collection<? extends KotlinType> types);

    public abstract ConstantValueGenerator getConstantValueGenerator();

    protected abstract boolean isTypeAliasAccessibleHere(TypeAliasDescriptor typeAliasDescriptor);

    public TypeTranslator(ReferenceSymbolTable symbolTable, LanguageVersionSettings languageVersionSettings, final Function0<? extends TypeParametersResolver> typeParametersResolverBuilder, boolean z, StubGeneratorExtensions extensions) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(typeParametersResolverBuilder, "typeParametersResolverBuilder");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.symbolTable = symbolTable;
        this.languageVersionSettings = languageVersionSettings;
        this.enterTableScope = z;
        this.extensions = extensions;
        this.typeParametersResolver = ThreadLocalKt.threadLocal(new Function0<TypeParametersResolver>() { // from class: org.jetbrains.kotlin.ir.util.TypeTranslator$typeParametersResolver$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final TypeParametersResolver invoke() {
                return typeParametersResolverBuilder.invoke();
            }
        });
        this.erasureStack = new Stack<>();
        this.supportDefinitelyNotNullTypes = languageVersionSettings.supportsFeature(LanguageFeature.DefinitelyNonNullableTypes);
        this.isWithNewInference = languageVersionSettings.supportsFeature(LanguageFeature.NewInference);
    }

    protected final ReferenceSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public /* synthetic */ TypeTranslator(ReferenceSymbolTable referenceSymbolTable, LanguageVersionSettings languageVersionSettings, AnonymousClass1 anonymousClass1, boolean z, StubGeneratorExtensions stubGeneratorExtensions, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(referenceSymbolTable, languageVersionSettings, (i & 4) != 0 ? new Function0<ScopedTypeParametersResolver>() { // from class: org.jetbrains.kotlin.ir.util.TypeTranslator.1
            @Override // kotlin.jvm.functions.Function0
            public final ScopedTypeParametersResolver invoke() {
                return new ScopedTypeParametersResolver();
            }
        } : anonymousClass1, (i & 8) != 0 ? false : z, (i & 16) != 0 ? StubGeneratorExtensions.EMPTY : stubGeneratorExtensions);
    }

    protected final StubGeneratorExtensions getExtensions() {
        return this.extensions;
    }

    private final TypeParametersResolver getTypeParametersResolver() {
        return (TypeParametersResolver) this.typeParametersResolver.getValue(this, $$delegatedProperties[0]);
    }

    public final void enterScope(IrTypeParametersContainer irElement) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        getTypeParametersResolver().enterTypeParameterScope(irElement);
        if (this.enterTableScope) {
            this.symbolTable.enterScope(irElement);
        }
    }

    public final void leaveScope(IrTypeParametersContainer irElement) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        getTypeParametersResolver().leaveTypeParameterScope();
        if (this.enterTableScope) {
            this.symbolTable.leaveScope(irElement);
        }
    }

    public final <T> T withTypeErasure(PropertyDescriptor propertyDescriptor, Function0<? extends T> b) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        Intrinsics.checkNotNullParameter(b, "b");
        try {
            this.erasureStack.push(propertyDescriptor);
            return b.invoke();
        } finally {
            this.erasureStack.pop();
        }
    }

    public final <T> T buildWithScope(IrTypeParametersContainer container, Function0<? extends T> builder) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(builder, "builder");
        enterScope(container);
        T tInvoke = builder.invoke();
        leaveScope(container);
        return tInvoke;
    }

    private final IrTypeParameterSymbol resolveTypeParameter(TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor instanceof IrBasedTypeParameterDescriptor) {
            return ((IrBasedTypeParameterDescriptor) typeParameterDescriptor).getOwner().getSymbol();
        }
        TypeParameterDescriptor originalTypeParameter = OriginalDescriptorsKt.getOriginalTypeParameter(typeParameterDescriptor);
        IrTypeParameterSymbol irTypeParameterSymbolResolveScopedTypeParameter = getTypeParametersResolver().resolveScopedTypeParameter(originalTypeParameter);
        return irTypeParameterSymbolResolveScopedTypeParameter == null ? this.symbolTable.getDescriptorExtension().referenceTypeParameter(originalTypeParameter) : irTypeParameterSymbolResolveScopedTypeParameter;
    }

    public final IrType translateType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        return translateType(kotlinType, Variance.INVARIANT).getType();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrTypeProjection translateType(KotlinType kotlinType, Variance variance) {
        List<IrTypeArgument> listTranslateTypeArguments;
        KotlinType kotlinTypeApproximate = approximate(kotlinType.unwrap());
        if (KotlinTypeKt.isError(kotlinTypeApproximate)) {
            return new IrErrorTypeImpl(kotlinTypeApproximate, translateTypeAnnotations$default(this, kotlinTypeApproximate, null, 2, null), variance, kotlinTypeApproximate.getIsMarkedNullable());
        }
        if (DynamicTypesKt.isDynamic(kotlinTypeApproximate)) {
            return new IrDynamicTypeImpl(kotlinTypeApproximate, translateTypeAnnotations$default(this, kotlinTypeApproximate, null, 2, null), variance);
        }
        if (this.supportDefinitelyNotNullTypes && (kotlinTypeApproximate instanceof DefinitelyNotNullType)) {
            return IrSimpleTypeImplKt.makeTypeProjection(IrTypesKt.makeNotNull(translateType(((DefinitelyNotNullType) kotlinTypeApproximate).getOriginal())), variance);
        }
        SimpleType simpleTypeUpperIfFlexible = FlexibleTypesKt.upperIfFlexible(kotlinTypeApproximate);
        ClassifierDescriptor this$0 = simpleTypeUpperIfFlexible.getConstructor().getThis$0();
        if (this$0 == null) {
            throw new AssertionError("No descriptor for type " + simpleTypeUpperIfFlexible);
        }
        if (!this.erasureStack.isEmpty() && (this$0 instanceof TypeParameterDescriptor)) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) this$0;
            if (CollectionsKt.contains(this.erasureStack, typeParameterDescriptor.getContainingDeclaration())) {
                List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
                Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                return approximateUpperBounds(upperBounds, variance);
            }
        }
        IrSimpleTypeBuilder irSimpleTypeBuilder = new IrSimpleTypeBuilder();
        irSimpleTypeBuilder.setKotlinType(kotlinTypeApproximate);
        irSimpleTypeBuilder.setNullability(SimpleTypeNullability.INSTANCE.fromHasQuestionMark(simpleTypeUpperIfFlexible.getIsMarkedNullable()));
        irSimpleTypeBuilder.setVariance(variance);
        SimpleType simpleType = simpleTypeUpperIfFlexible;
        SimpleType abbreviation = SpecialTypesKt.getAbbreviation(simpleType);
        irSimpleTypeBuilder.setAbbreviation(abbreviation != null ? toIrTypeAbbreviation(abbreviation) : null);
        if (this$0 instanceof TypeParameterDescriptor) {
            irSimpleTypeBuilder.setClassifier(resolveTypeParameter((TypeParameterDescriptor) this$0));
            irSimpleTypeBuilder.setAnnotations(translateTypeAnnotations(simpleType, kotlinTypeApproximate));
        } else if (this$0 instanceof ScriptDescriptor) {
            irSimpleTypeBuilder.setClassifier(this.symbolTable.getDescriptorExtension().referenceScript(this$0));
        } else if (this$0 instanceof ClassDescriptor) {
            SimpleType simpleTypeLowerIfFlexible = FlexibleTypesKt.lowerIfFlexible(kotlinTypeApproximate);
            ClassifierDescriptor this$1 = simpleTypeLowerIfFlexible.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = this$1 instanceof ClassDescriptor ? (ClassDescriptor) this$1 : null;
            if (classDescriptor == null) {
                throw new AssertionError("No class descriptor for lower type " + simpleTypeLowerIfFlexible + " of " + kotlinTypeApproximate);
            }
            irSimpleTypeBuilder.setAnnotations(translateTypeAnnotations(simpleType, kotlinTypeApproximate));
            irSimpleTypeBuilder.setClassifier(this.symbolTable.getDescriptorExtension().referenceClass(classDescriptor));
            if (kotlinTypeApproximate instanceof RawType) {
                listTranslateTypeArguments = translateTypeArguments(kotlinTypeApproximate.getArguments());
            } else if (!Intrinsics.areEqual(classDescriptor, this$0)) {
                listTranslateTypeArguments = translateTypeArguments(simpleTypeLowerIfFlexible.getArguments());
            } else {
                listTranslateTypeArguments = translateTypeArguments(simpleTypeUpperIfFlexible.getArguments());
            }
            irSimpleTypeBuilder.setArguments(listTranslateTypeArguments);
        } else {
            throw new AssertionError("Unexpected type descriptor " + this$0 + " :: " + Reflection.getOrCreateKotlinClass(this$0.getClass()));
        }
        return IrSimpleTypeImplKt.buildTypeProjection(irSimpleTypeBuilder);
    }

    private final IrTypeProjection approximateUpperBounds(Collection<? extends KotlinType> upperBounds, Variance variance) {
        return translateType(approximate(TypeUtilsKt.replaceArgumentsWithStarProjections(commonSupertype(upperBounds))), variance);
    }

    private final IrTypeAbbreviation toIrTypeAbbreviation(SimpleType simpleType) {
        ClassifierDescriptor this$0 = simpleType.getConstructor().getThis$0();
        TypeAliasDescriptor typeAliasDescriptor = this$0 instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) this$0 : null;
        if (typeAliasDescriptor != null && isTypeAliasAccessibleHere(typeAliasDescriptor)) {
            return new IrTypeAbbreviationImpl(this.symbolTable.getDescriptorExtension().referenceTypeAlias(typeAliasDescriptor), simpleType.getIsMarkedNullable(), translateTypeArguments(simpleType.getArguments()), translateTypeAnnotations$default(this, simpleType, null, 2, null));
        }
        return null;
    }

    public final KotlinType approximate(KotlinType ktType) {
        Intrinsics.checkNotNullParameter(ktType, "ktType");
        KotlinType kotlinTypeApproximateByKotlinRules = approximateByKotlinRules(ktType);
        TypeConstructor constructor = kotlinTypeApproximateByKotlinRules.getConstructor();
        return constructor instanceof IntersectionTypeConstructor ? approximate(TypeUtilsKt.replaceArgumentsWithStarProjections(commonSupertype(((IntersectionTypeConstructor) constructor).mo7438getSupertypes()))) : kotlinTypeApproximateByKotlinRules;
    }

    public final KotlinType approximateFunctionReferenceType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        if (!FunctionTypesKt.isKFunctionType(kotlinType) || !(kotlinType instanceof SimpleType)) {
            return kotlinType;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        if ((arguments instanceof Collection) && arguments.isEmpty()) {
            return kotlinType;
        }
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            if (((TypeProjection) it.next()).getNullableAnyType().getConstructor() instanceof IntersectionTypeConstructor) {
                List<TypeProjection> listSubList = kotlinType.getArguments().subList(0, kotlinType.getArguments().size() - 1);
                TypeProjection typeProjection = (TypeProjection) CollectionsKt.last((List) kotlinType.getArguments());
                SimpleType simpleType = (SimpleType) kotlinType;
                List<TypeProjection> list = listSubList;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList.add(approximateFunctionReferenceParameterType((TypeProjection) it2.next()));
                }
                return TypeSubstitutionKt.replace$default(simpleType, CollectionsKt.plus((Collection<? extends TypeProjection>) arrayList, typeProjection), null, 2, null);
            }
        }
        return kotlinType;
    }

    private final TypeProjection approximateFunctionReferenceParameterType(TypeProjection typeProjection) {
        if (typeProjection.isStarProjection()) {
            return typeProjection;
        }
        TypeConstructor constructor = typeProjection.getNullableAnyType().getConstructor();
        IntersectionTypeConstructor intersectionTypeConstructor = constructor instanceof IntersectionTypeConstructor ? (IntersectionTypeConstructor) constructor : null;
        if (intersectionTypeConstructor == null) {
            return typeProjection;
        }
        KotlinType alternative = intersectionTypeConstructor.getAlternative();
        if (alternative == null) {
            alternative = commonSupertype(intersectionTypeConstructor.mo7438getSupertypes());
        }
        return new TypeProjectionImpl(typeProjection.getProjectionKind(), alternative);
    }

    private final KotlinType approximateByKotlinRules(KotlinType ktType) {
        if (this.isWithNewInference) {
            return (ktType.getConstructor().isDenotable() && ktType.getArguments().isEmpty()) ? ktType : approximateType(ktType);
        }
        return ktType.getConstructor().isDenotable() ? ktType : CapturedTypeApproximationKt.approximateCapturedTypes(ktType).getUpper();
    }

    static /* synthetic */ List translateTypeAnnotations$default(TypeTranslator typeTranslator, KotlinType kotlinType, KotlinType kotlinType2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: translateTypeAnnotations");
        }
        if ((i & 2) != 0) {
            kotlinType2 = kotlinType;
        }
        return typeTranslator.translateTypeAnnotations(kotlinType, kotlinType2);
    }

    private final List<IrConstructorCall> translateTypeAnnotations(KotlinType kotlinType, KotlinType flexibleType) {
        Annotations annotations = kotlinType.getAnnotations();
        ArrayList arrayList = new ArrayList();
        Iterator<AnnotationDescriptor> it = annotations.iterator();
        while (it.hasNext()) {
            IrConstructorCall irConstructorCallGenerateAnnotationConstructorCall$default = ConstantValueGenerator.generateAnnotationConstructorCall$default(getConstantValueGenerator(), it.next(), null, 2, null);
            if (irConstructorCallGenerateAnnotationConstructorCall$default != null) {
                arrayList.add(irConstructorCallGenerateAnnotationConstructorCall$default);
            }
        }
        if (this.extensions.getEnhancedNullability().hasEnhancedNullability(kotlinType)) {
            addSpecialAnnotation(arrayList, this.extensions.getEnhancedNullabilityAnnotationConstructor());
        }
        if (FlexibleTypesKt.isNullabilityFlexible(flexibleType)) {
            addSpecialAnnotation(arrayList, this.extensions.getFlexibleNullabilityAnnotationConstructor());
        }
        if (isMutabilityFlexible(flexibleType)) {
            addSpecialAnnotation(arrayList, this.extensions.getFlexibleMutabilityAnnotationConstructor());
        }
        if (flexibleType instanceof RawType) {
            addSpecialAnnotation(arrayList, this.extensions.getRawTypeAnnotationConstructor());
        }
        return arrayList;
    }

    private final boolean isMutabilityFlexible(KotlinType kotlinType) {
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            if (!Intrinsics.areEqual(flexibleType.getLowerBound().getConstructor(), flexibleType.getUpperBound().getConstructor()) && Intrinsics.areEqual(FlexibleTypeBoundsChecker.INSTANCE.getBaseBoundFqNameByMutability(flexibleType.getLowerBound()), FlexibleTypeBoundsChecker.INSTANCE.getBaseBoundFqNameByMutability(flexibleType.getUpperBound()))) {
                return true;
            }
        }
        return false;
    }

    private final void addSpecialAnnotation(List<IrConstructorCall> list, IrConstructor irConstructor) {
        if (irConstructor != null) {
            list.add(IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, -1, -1, IrUtilsKt.getConstructedClassType(irConstructor), irConstructor.getSymbol(), null, 16, null));
        }
    }

    private final List<IrTypeArgument> translateTypeArguments(List<? extends TypeProjection> arguments) {
        IrTypeArgument irTypeArgumentTranslateType;
        List<? extends TypeProjection> list = arguments;
        ArrayList arrayList = new ArrayList(list.size());
        for (TypeProjection typeProjection : list) {
            if (typeProjection.isStarProjection()) {
                irTypeArgumentTranslateType = IrStarProjectionImpl.INSTANCE;
            } else {
                KotlinType type = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                Variance projectionKind = typeProjection.getProjectionKind();
                Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
                irTypeArgumentTranslateType = translateType(type, projectionKind);
            }
            arrayList.add(irTypeArgumentTranslateType);
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
    }
}
