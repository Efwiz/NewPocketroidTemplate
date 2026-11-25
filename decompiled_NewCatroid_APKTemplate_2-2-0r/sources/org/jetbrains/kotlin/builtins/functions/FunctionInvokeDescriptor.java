package org.jetbrains.kotlin.builtins.functions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FunctionInvokeDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000  2\u00020\u0001:\u0001 B)\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ<\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u000f2\n\u0010\u0018\u001a\u00060\u0019R\u00020\rH\u0014J\b\u0010\u001a\u001a\u00020\bH\u0016J\b\u0010\u001b\u001a\u00020\bH\u0016J\b\u0010\u001c\u001a\u00020\bH\u0016J\u0018\u0010\u001d\u001a\u00020\u000f2\u000e\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u001fH\u0002R\u0011\u0010\n\u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionInvokeDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "container", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "original", "callableKind", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "isSuspend", "", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/builtins/functions/FunctionInvokeDescriptor;Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;Z)V", "hasBigArity", "()Z", "createSubstitutedCopy", "Lorg/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl;", "newOwner", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", Namer.METADATA_CLASS_KIND, "newName", "Lorg/jetbrains/kotlin/name/Name;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "doSubstitute", "configuration", "Lorg/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl$CopyConfiguration;", "isExternal", "isInline", "isTailrec", "replaceParameterNames", "parameterNames", "", "Factory", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionInvokeDescriptor extends SimpleFunctionDescriptorImpl {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ FunctionInvokeDescriptor(DeclarationDescriptor declarationDescriptor, FunctionInvokeDescriptor functionInvokeDescriptor, CallableMemberDescriptor.Kind kind, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(declarationDescriptor, functionInvokeDescriptor, kind, z);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isInline() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isTailrec() {
        return false;
    }

    private FunctionInvokeDescriptor(DeclarationDescriptor declarationDescriptor, FunctionInvokeDescriptor functionInvokeDescriptor, CallableMemberDescriptor.Kind kind, boolean z) {
        super(declarationDescriptor, functionInvokeDescriptor, Annotations.INSTANCE.getEMPTY(), OperatorNameConventions.INVOKE, kind, SourceElement.NO_SOURCE);
        setOperator(true);
        setSuspend(z);
        setHasStableParameterNames(false);
    }

    public final boolean hasBigArity() {
        return getValueParameters().size() >= 23;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    protected FunctionDescriptor doSubstitute(FunctionDescriptorImpl.CopyConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        FunctionInvokeDescriptor functionInvokeDescriptor = (FunctionInvokeDescriptor) super.doSubstitute(configuration);
        if (functionInvokeDescriptor == null) {
            return null;
        }
        List<ValueParameterDescriptor> valueParameters = functionInvokeDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KotlinType type = ((ValueParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type) != null) {
                    List<ValueParameterDescriptor> valueParameters2 = functionInvokeDescriptor.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                    List<ValueParameterDescriptor> list2 = valueParameters2;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator<T> it2 = list2.iterator();
                    while (it2.hasNext()) {
                        KotlinType type2 = ((ValueParameterDescriptor) it2.next()).getType();
                        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                        arrayList.add(FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type2));
                    }
                    return functionInvokeDescriptor.replaceParameterNames(arrayList);
                }
            }
        }
        return functionInvokeDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor newOwner, FunctionDescriptor original, CallableMemberDescriptor.Kind kind, Name newName, Annotations annotations, SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        return new FunctionInvokeDescriptor(newOwner, (FunctionInvokeDescriptor) original, kind, isSuspend());
    }

    private final FunctionDescriptor replaceParameterNames(List<Name> parameterNames) {
        Name name2;
        Pair pair;
        int size = getValueParameters().size() - parameterNames.size();
        boolean z = true;
        if (size == 0) {
            List<ValueParameterDescriptor> valueParameters = getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List listZip = CollectionsKt.zip(parameterNames, valueParameters);
            if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
                Iterator it = listZip.iterator();
                do {
                    if (it.hasNext()) {
                        pair = (Pair) it.next();
                    }
                } while (Intrinsics.areEqual((Name) pair.component1(), ((ValueParameterDescriptor) pair.component2()).getShortName()));
            }
            return this;
        }
        List<ValueParameterDescriptor> valueParameters2 = getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters2;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            Name name3 = valueParameterDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            int index = valueParameterDescriptor.getIndex();
            int i = index - size;
            if (i >= 0 && (name2 = parameterNames.get(i)) != null) {
                name3 = name2;
            }
            arrayList.add(valueParameterDescriptor.mo7204copy(this, name3, index));
        }
        ArrayList arrayList2 = arrayList;
        FunctionDescriptorImpl.CopyConfiguration copyConfigurationNewCopyBuilder = newCopyBuilder(TypeSubstitutor.EMPTY);
        List<Name> list2 = parameterNames;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            z = false;
        } else {
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                if (((Name) it2.next()) == null) {
                }
            }
            z = false;
        }
        FunctionDescriptorImpl.CopyConfiguration original = copyConfigurationNewCopyBuilder.setHasSynthesizedParameterNames(z).setValueParameters((List<ValueParameterDescriptor>) arrayList2).setOriginal((CallableMemberDescriptor) getOriginal());
        Intrinsics.checkNotNullExpressionValue(original, "setOriginal(...)");
        FunctionDescriptor functionDescriptorDoSubstitute = super.doSubstitute(original);
        Intrinsics.checkNotNull(functionDescriptorDoSubstitute);
        return functionDescriptorDoSubstitute;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.builtins.functions.FunctionInvokeDescriptor$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: FunctionInvokeDescriptor.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionInvokeDescriptor$Factory;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/builtins/functions/FunctionInvokeDescriptor;", "functionClass", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor;", "isSuspend", "", "createValueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "containingDeclaration", "index", "", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FunctionInvokeDescriptor create(FunctionClassDescriptor functionClass, boolean isSuspend) {
            Intrinsics.checkNotNullParameter(functionClass, "functionClass");
            List<TypeParameterDescriptor> declaredTypeParameters = functionClass.getDeclaredTypeParameters();
            FunctionInvokeDescriptor functionInvokeDescriptor = new FunctionInvokeDescriptor(functionClass, null, CallableMemberDescriptor.Kind.DECLARATION, isSuspend, null);
            ReceiverParameterDescriptor thisAsReceiverParameter = functionClass.getThisAsReceiverParameter();
            List listEmptyList = CollectionsKt.emptyList();
            List listEmptyList2 = CollectionsKt.emptyList();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declaredTypeParameters) {
                if (((TypeParameterDescriptor) obj).getVariance() != Variance.IN_VARIANCE) {
                    break;
                }
                arrayList.add(obj);
            }
            Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(arrayList);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10));
            for (IndexedValue indexedValue : iterableWithIndex) {
                arrayList2.add(FunctionInvokeDescriptor.INSTANCE.createValueParameter(functionInvokeDescriptor, indexedValue.getIndex(), (TypeParameterDescriptor) indexedValue.getValue()));
            }
            functionInvokeDescriptor.initialize((ReceiverParameterDescriptor) null, thisAsReceiverParameter, listEmptyList, listEmptyList2, (List) arrayList2, (KotlinType) ((TypeParameterDescriptor) CollectionsKt.last((List) declaredTypeParameters)).getDefaultType(), Modality.ABSTRACT, DescriptorVisibilities.PUBLIC);
            functionInvokeDescriptor.setHasSynthesizedParameterNames(true);
            return functionInvokeDescriptor;
        }

        private final ValueParameterDescriptor createValueParameter(FunctionInvokeDescriptor containingDeclaration, int index, TypeParameterDescriptor typeParameter) {
            String lowerCase;
            String strAsString = typeParameter.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (Intrinsics.areEqual(strAsString, ExifInterface.GPS_DIRECTION_TRUE)) {
                lowerCase = "instance";
            } else if (Intrinsics.areEqual(strAsString, ExifInterface.LONGITUDE_EAST)) {
                lowerCase = "receiver";
            } else {
                lowerCase = strAsString.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            }
            Annotations empty = Annotations.INSTANCE.getEMPTY();
            Name nameIdentifier = Name.identifier(lowerCase);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            SimpleType defaultType = typeParameter.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            return new ValueParameterDescriptorImpl(containingDeclaration, null, index, empty, nameIdentifier, defaultType, false, false, false, null, NO_SOURCE);
        }
    }
}
