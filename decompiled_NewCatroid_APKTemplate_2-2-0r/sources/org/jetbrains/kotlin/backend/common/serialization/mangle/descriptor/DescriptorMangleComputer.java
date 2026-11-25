package org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.descriptors.IrImplementingDelegateDescriptor;
import org.jetbrains.kotlin.ir.descriptors.IrPropertyDelegateDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.types.DynamicType;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RawType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: DescriptorMangleComputer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002.\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0001:\u0001BB\u0019\u0012\n\u0010\b\u001a\u00060\tj\u0002`\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0010\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\fH\u0016J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u00152\u0006\u0010\u0016\u001a\u00020\u0006H\u0014J\u0010\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0004H\u0004J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0016\u001a\u00020\u0006H\u0014J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u0002H\u0004J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0016\u001a\u00020\u0006H\u0014J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u0004H\u0014J$\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040!0\u00152\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u0002H\u0014J\u0012\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0007H\u0004J\u0010\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u0005H\u0004J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00050\u00152\u0006\u0010\u0016\u001a\u00020\u0006H\u0014J\u0010\u0010(\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020\u0003H\u0014J\u0010\u0010*\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u0005H\u0004J\u001c\u0010+\u001a\u00020,2\n\u0010-\u001a\u00060\tj\u0002`\n2\u0006\u0010.\u001a\u00020/H\u0002J\u0010\u00100\u001a\u00020,2\u0006\u00101\u001a\u000202H\u0002J&\u00103\u001a\u00020,2\n\u00104\u001a\u00060\tj\u0002`\n2\u0006\u00105\u001a\u00020\u00032\b\u00106\u001a\u0004\u0018\u00010\u0007H\u0004J\u0010\u00107\u001a\u00020\u001f2\u0006\u00108\u001a\u00020\u0002H\u0014J\u0010\u00109\u001a\u00020,2\u0006\u0010:\u001a\u00020\u0002H\u0002J\u0010\u0010;\u001a\u00020,2\u0006\u0010:\u001a\u00020<H\u0014J\f\u0010=\u001a\u00020\u0002*\u00020\u0002H\u0014J\u000e\u0010>\u001a\u0004\u0018\u00010\u001f*\u00020?H\u0016J\f\u0010@\u001a\u00020,*\u00020\u0002H\u0004J\f\u0010A\u001a\u00020,*\u00020\u0002H\u0004R\u0018\u0010\u000e\u001a\u00020\u000f*\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0011¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorMangleComputer;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/BaseKotlinMangleComputer;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;)V", "isRealStatic", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Z", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newMode", "getContextReceiverTypes", "", "function", "getEffectiveParent", "typeParameter", "getExtensionReceiverParameterType", "getIndexOfTypeParameter", "", "container", "getReturnType", "getTypeParameterName", "", "getTypeParametersWithIndices", "Lkotlin/collections/IndexedValue;", "getTypeSystemContext", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "session", "getValueParameterType", "valueParameter", "getValueParameters", "isUnit", ModuleXmlParser.TYPE, "isVararg", "mangleExtensionReceiverParameter", "", "vpBuilder", "param", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "manglePropertyAccessor", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "mangleType", "tBuilder", "wrappedType", "declarationSiteSession", "renderDeclaration", "declaration", "reportUnexpectedDescriptor", "descriptor", "visitModuleDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "asTypeParameterContainer", "platformSpecificSuffix", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "visit", "visitParent", "Visitor", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DescriptorMangleComputer extends BaseKotlinMangleComputer {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public DeclarationDescriptor asTypeParameterContainer(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor;
    }

    public String platformSpecificSuffix(PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DescriptorMangleComputer(StringBuilder builder, MangleMode mode) {
        super(builder, mode, false, 4, null);
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(mode, "mode");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final TypeSystemContext getTypeSystemContext(Void session) {
        return SimpleClassicTypeSystemContext.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer
    public DescriptorMangleComputer copy(MangleMode newMode) {
        Intrinsics.checkNotNullParameter(newMode, "newMode");
        return new DescriptorMangleComputer(getBuilder(), newMode);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final void visitParent(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
        if (containingDeclaration != null) {
            visit(containingDeclaration);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final void visit(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        declarationDescriptor.accept(new Visitor(), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reportUnexpectedDescriptor(DeclarationDescriptor descriptor) {
        throw new IllegalStateException(("unexpected descriptor " + descriptor).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isRealStatic(CallableDescriptor callableDescriptor) {
        return callableDescriptor.mo6753getDispatchReceiverParameter() == null && !(callableDescriptor.getContainingDeclaration() instanceof PackageFragmentDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public List<KotlinType> getContextReceiverTypes(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        List<ReceiverParameterDescriptor> contextReceiverParameters = function.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ReceiverParameterDescriptor) it.next()).getType());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public KotlinType getExtensionReceiverParameterType(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        ReceiverParameterDescriptor extensionReceiverParameter = function.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            return extensionReceiverParameter.getType();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public List<ValueParameterDescriptor> getValueParameters(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        List<ValueParameterDescriptor> valueParameters = function.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        return valueParameters;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public KotlinType getReturnType(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return function.getReturnType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public List<IndexedValue<TypeParameterDescriptor>> getTypeParametersWithIndices(FunctionDescriptor function, DeclarationDescriptor container) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(container, "container");
        CallableDescriptor callableDescriptor = container instanceof CallableDescriptor ? (CallableDescriptor) container : null;
        List<TypeParameterDescriptor> typeParameters = callableDescriptor != null ? callableDescriptor.getTypeParameters() : null;
        if (typeParameters == null) {
            typeParameters = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : typeParameters) {
            if (Intrinsics.areEqual(((TypeParameterDescriptor) obj).getContainingDeclaration(), container)) {
                arrayList.add(obj);
            }
        }
        ArrayList<TypeParameterDescriptor> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (TypeParameterDescriptor typeParameterDescriptor : arrayList2) {
            arrayList3.add(new IndexedValue(typeParameterDescriptor.getIndex(), typeParameterDescriptor));
        }
        return arrayList3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public boolean isUnit(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return TypeUtilsKt.isUnit(type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void mangleExtensionReceiverParameter(StringBuilder vpBuilder, ReceiverParameterDescriptor param) {
        KotlinType type = param.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        mangleType(vpBuilder, type, (Void) null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final boolean isVararg(ValueParameterDescriptor valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return valueParameter.getVarargElementType() != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final KotlinType getValueParameterType(ValueParameterDescriptor valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        KotlinType type = valueParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return type;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final void mangleType(StringBuilder tBuilder, KotlinType wrappedType, Void declarationSiteSession) {
        Intrinsics.checkNotNullParameter(tBuilder, "tBuilder");
        Intrinsics.checkNotNullParameter(wrappedType, "wrappedType");
        UnwrappedType unwrappedTypeUnwrap = wrappedType.unwrap();
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = unwrappedTypeUnwrap.getConstructor().getThis$0();
            if (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) {
                copy(MangleMode.FQNAME).visit((DeclarationDescriptor) classifierDescriptorMo7434getDeclarationDescriptor);
            } else {
                if (!(classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor)) {
                    throw new IllegalStateException(("Unexpected classifier: " + classifierDescriptorMo7434getDeclarationDescriptor).toString());
                }
                mangleTypeParameterReference(tBuilder, (TypeParameterMarker) classifierDescriptorMo7434getDeclarationDescriptor);
            }
            UnwrappedType unwrappedType = unwrappedTypeUnwrap;
            mangleTypeArguments(tBuilder, unwrappedType, null);
            if (unwrappedTypeUnwrap.getIsMarkedNullable()) {
                appendSignature(tBuilder, MangleConstant.Q_MARK);
            }
            mangleTypePlatformSpecific(unwrappedType, tBuilder);
            return;
        }
        if (unwrappedTypeUnwrap instanceof DynamicType) {
            appendSignature(tBuilder, MangleConstant.DYNAMIC_MARK);
            return;
        }
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            SimpleType upperBound = flexibleType.getUpperBound();
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor2 = upperBound.getConstructor().getThis$0();
            if (classifierDescriptorMo7434getDeclarationDescriptor2 == null) {
                throw new IllegalStateException(("No descriptor for type " + upperBound).toString());
            }
            if (classifierDescriptorMo7434getDeclarationDescriptor2 instanceof ClassDescriptor) {
                SimpleType lowerBound = flexibleType.getLowerBound();
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor3 = lowerBound.getConstructor().getThis$0();
                ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor3 instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor3 : null;
                if (classDescriptor == null) {
                    throw new IllegalStateException(("No class descriptor for lower type " + lowerBound + " of " + unwrappedTypeUnwrap).toString());
                }
                if (Intrinsics.areEqual(classDescriptor, classifierDescriptorMo7434getDeclarationDescriptor2) && !(unwrappedTypeUnwrap instanceof RawType)) {
                    lowerBound = TypeSubstitutionKt.replace$default(lowerBound, upperBound.getArguments(), null, 2, null);
                }
                mangleType(tBuilder, (KotlinType) lowerBound.makeNullableAsSpecified(upperBound.getIsMarkedNullable()), (Void) null);
                return;
            }
            mangleType(tBuilder, (KotlinType) upperBound, (Void) null);
            return;
        }
        throw new IllegalStateException(("Unexpected type " + wrappedType).toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final DeclarationDescriptor getEffectiveParent(TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        DeclarationDescriptor containingDeclaration = typeParameter.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return containingDeclaration;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public String renderDeclaration(DeclarationDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return DescriptorRenderer.SHORT_NAMES_IN_TYPES.render(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public String getTypeParameterName(TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        String strAsString = typeParameter.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public final int getIndexOfTypeParameter(TypeParameterDescriptor typeParameter, DeclarationDescriptor container) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(container, "container");
        return typeParameter.getIndex();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void manglePropertyAccessor(PropertyAccessorDescriptor accessor) {
        PropertyDescriptor correspondingProperty = accessor.getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        Name name2 = accessor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        mangleFunction(accessor, name2, false, isRealStatic(correspondingProperty), correspondingProperty, null);
    }

    protected void visitModuleDeclaration(ModuleDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        reportUnexpectedDescriptor(descriptor);
    }

    /* JADX INFO: compiled from: DescriptorMangleComputer.kt */
    @Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\r2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000f2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00112\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00132\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00152\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00172\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00192\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020 2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010!\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\"2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010#\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020$2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010%\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020&2\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorMangleComputer$Visitor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "", "", "(Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorMangleComputer;)V", "visitClassDescriptor", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "data", "visitConstructorDescriptor", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "visitFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "visitModuleDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "visitPackageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "visitPackageViewDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "visitPropertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "visitPropertyGetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "visitPropertySetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "visitReceiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "visitScriptDescriptor", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "visitTypeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "visitTypeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "visitValueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "visitVariableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Visitor implements DeclarationDescriptorVisitor {
        public Visitor() {
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitClassDescriptor(ClassDescriptor classDescriptor, Object obj) {
            visitClassDescriptor(classDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, Object obj) {
            visitConstructorDescriptor(constructorDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitFunctionDescriptor(FunctionDescriptor functionDescriptor, Object obj) {
            visitFunctionDescriptor(functionDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitModuleDeclaration(ModuleDescriptor moduleDescriptor, Object obj) {
            visitModuleDeclaration(moduleDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, Object obj) {
            visitPackageFragmentDescriptor(packageFragmentDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, Object obj) {
            visitPackageViewDescriptor(packageViewDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, Object obj) {
            visitPropertyDescriptor(propertyDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, Object obj) {
            visitPropertyGetterDescriptor(propertyGetterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, Object obj) {
            visitPropertySetterDescriptor(propertySetterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, Object obj) {
            visitReceiverParameterDescriptor(receiverParameterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitScriptDescriptor(ScriptDescriptor scriptDescriptor, Object obj) {
            visitScriptDescriptor(scriptDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, Object obj) {
            visitTypeAliasDescriptor(typeAliasDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, Object obj) {
            visitTypeParameterDescriptor(typeParameterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, Object obj) {
            visitValueParameterDescriptor(valueParameterDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Object visitVariableDescriptor(VariableDescriptor variableDescriptor, Object obj) {
            visitVariableDescriptor(variableDescriptor, (Void) obj);
            return Unit.INSTANCE;
        }

        public void visitPackageFragmentDescriptor(PackageFragmentDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            FqName fqName = descriptor.getFqName();
            DescriptorMangleComputer descriptorMangleComputer = DescriptorMangleComputer.this;
            if (fqName.isRoot()) {
                return;
            }
            StringBuilder builder = descriptorMangleComputer.getBuilder();
            String strAsString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            descriptorMangleComputer.appendName(builder, strAsString);
        }

        public void visitPackageViewDescriptor(PackageViewDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer.this.reportUnexpectedDescriptor(descriptor);
        }

        public void visitVariableDescriptor(VariableDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer.this.reportUnexpectedDescriptor(descriptor);
        }

        public void visitFunctionDescriptor(FunctionDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer descriptorMangleComputer = DescriptorMangleComputer.this;
            Name name2 = descriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            descriptorMangleComputer.mangleFunction(descriptor, name2, false, DescriptorMangleComputer.this.isRealStatic(descriptor), descriptor, null);
        }

        public void visitTypeParameterDescriptor(TypeParameterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer descriptorMangleComputer = DescriptorMangleComputer.this;
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            descriptorMangleComputer.visit(containingDeclaration);
            DescriptorMangleComputer descriptorMangleComputer2 = DescriptorMangleComputer.this;
            descriptorMangleComputer2.appendSignature(descriptorMangleComputer2.getBuilder(), '@');
            DescriptorMangleComputer descriptorMangleComputer3 = DescriptorMangleComputer.this;
            descriptorMangleComputer3.appendSignature(descriptorMangleComputer3.getBuilder(), descriptor.getIndex());
        }

        public void visitClassDescriptor(ClassDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer.this.getTypeParameterContainers().add(descriptor);
            DescriptorMangleComputer descriptorMangleComputer = DescriptorMangleComputer.this;
            ClassDescriptor classDescriptor = descriptor;
            String strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            descriptorMangleComputer.mangleSimpleDeclaration(classDescriptor, strAsString);
        }

        public void visitTypeAliasDescriptor(TypeAliasDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer descriptorMangleComputer = DescriptorMangleComputer.this;
            TypeAliasDescriptor typeAliasDescriptor = descriptor;
            String strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            descriptorMangleComputer.mangleSimpleDeclaration(typeAliasDescriptor, strAsString);
        }

        public void visitModuleDeclaration(ModuleDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer.this.visitModuleDeclaration(descriptor);
        }

        public void visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, Void data) {
            Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
            Name name2 = constructorDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            DescriptorMangleComputer.this.mangleFunction(constructorDescriptor, name2, true, DescriptorMangleComputer.this.isRealStatic(constructorDescriptor), constructorDescriptor, null);
        }

        public void visitScriptDescriptor(ScriptDescriptor scriptDescriptor, Void data) {
            Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
            visitClassDescriptor((ClassDescriptor) scriptDescriptor, data);
        }

        public void visitPropertyDescriptor(PropertyDescriptor descriptor, Void data) {
            PropertyDescriptor correspondingProperty;
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            if (descriptor instanceof IrImplementingDelegateDescriptor) {
                DescriptorMangleComputer descriptorMangleComputer = DescriptorMangleComputer.this;
                PropertyDescriptor propertyDescriptor = descriptor;
                String strAsString = ((IrImplementingDelegateDescriptor) descriptor).getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                descriptorMangleComputer.mangleSimpleDeclaration(propertyDescriptor, strAsString);
                return;
            }
            IrPropertyDelegateDescriptor irPropertyDelegateDescriptor = descriptor instanceof IrPropertyDelegateDescriptor ? (IrPropertyDelegateDescriptor) descriptor : null;
            if (irPropertyDelegateDescriptor != null && (correspondingProperty = irPropertyDelegateDescriptor.getCorrespondingProperty()) != null) {
                descriptor = correspondingProperty;
            }
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            DescriptorMangleComputer.this.getTypeParameterContainers().add(descriptor);
            DescriptorMangleComputer descriptorMangleComputer2 = DescriptorMangleComputer.this;
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            descriptorMangleComputer2.visit(containingDeclaration);
            if (DescriptorMangleComputer.this.isRealStatic(descriptor)) {
                DescriptorMangleComputer descriptorMangleComputer3 = DescriptorMangleComputer.this;
                descriptorMangleComputer3.appendSignature(descriptorMangleComputer3.getBuilder(), MangleConstant.STATIC_MEMBER_MARK);
            }
            if (extensionReceiverParameter != null) {
                DescriptorMangleComputer descriptorMangleComputer4 = DescriptorMangleComputer.this;
                descriptorMangleComputer4.appendSignature(descriptorMangleComputer4.getBuilder(), '@');
                DescriptorMangleComputer descriptorMangleComputer5 = DescriptorMangleComputer.this;
                descriptorMangleComputer5.mangleExtensionReceiverParameter(descriptorMangleComputer5.getBuilder(), extensionReceiverParameter);
            }
            List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            StringBuilder builder = DescriptorMangleComputer.this.getBuilder();
            MangleConstant mangleConstant = MangleConstant.TYPE_PARAMETERS;
            final DescriptorMangleComputer descriptorMangleComputer6 = DescriptorMangleComputer.this;
            MangleUtilsKt.collectForMangler(typeParameters, builder, mangleConstant, new Function2<StringBuilder, TypeParameterDescriptor, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorMangleComputer$Visitor$visitPropertyDescriptor$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb, TypeParameterDescriptor typeParameterDescriptor) {
                    invoke2(sb, typeParameterDescriptor);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(StringBuilder collectForMangler, TypeParameterDescriptor typeParameterDescriptor) {
                    Intrinsics.checkNotNullParameter(collectForMangler, "$this$collectForMangler");
                    DescriptorMangleComputer descriptorMangleComputer7 = descriptorMangleComputer6;
                    Intrinsics.checkNotNull(typeParameterDescriptor);
                    descriptorMangleComputer7.mangleTypeParameter(collectForMangler, typeParameterDescriptor, typeParameterDescriptor.getIndex(), null);
                }
            });
            DescriptorMangleComputer.this.getBuilder().append(descriptor.getShortName().asString());
            String strPlatformSpecificSuffix = DescriptorMangleComputer.this.platformSpecificSuffix(descriptor);
            if (strPlatformSpecificSuffix != null) {
                DescriptorMangleComputer descriptorMangleComputer7 = DescriptorMangleComputer.this;
                descriptorMangleComputer7.appendSignature(descriptorMangleComputer7.getBuilder(), strPlatformSpecificSuffix);
            }
        }

        public void visitValueParameterDescriptor(ValueParameterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer.this.reportUnexpectedDescriptor(descriptor);
        }

        public void visitPropertyGetterDescriptor(PropertyGetterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer.this.manglePropertyAccessor(descriptor);
        }

        public void visitPropertySetterDescriptor(PropertySetterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer.this.manglePropertyAccessor(descriptor);
        }

        public void visitReceiverParameterDescriptor(ReceiverParameterDescriptor descriptor, Void data) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DescriptorMangleComputer.this.reportUnexpectedDescriptor(descriptor);
        }
    }
}
