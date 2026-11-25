package org.jetbrains.kotlin.resolve.calls;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: DslMarkerUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u000e\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b2\u0006\u0010\f\u001a\u00020\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/DslMarkerUtils;", "", "()V", "DSL_MARKER_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getDSL_MARKER_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "extractDslMarkerFqNames", "Lorg/jetbrains/kotlin/resolve/calls/DslMarkerUtils$DslMarkersFromReceiver;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "DslMarkersFromReceiver", "FunctionTypeAnnotationsKey", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DslMarkerUtils {
    public static final DslMarkerUtils INSTANCE = new DslMarkerUtils();
    private static final FqName DSL_MARKER_FQ_NAME = new FqName("kotlin.DslMarker");

    private DslMarkerUtils() {
    }

    /* JADX INFO: compiled from: DslMarkerUtils.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/DslMarkerUtils$FunctionTypeAnnotationsKey;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FunctionTypeAnnotationsKey implements CallableDescriptor.UserDataKey<Annotations> {
        public static final FunctionTypeAnnotationsKey INSTANCE = new FunctionTypeAnnotationsKey();

        private FunctionTypeAnnotationsKey() {
        }
    }

    /* JADX INFO: compiled from: DslMarkerUtils.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/DslMarkerUtils$DslMarkersFromReceiver;", "", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR, "", "Lorg/jetbrains/kotlin/name/FqName;", "fromContainingFunctionType", "(Ljava/util/Set;Ljava/util/Set;)V", "getCommon", "()Ljava/util/Set;", "getFromContainingFunctionType", DevModeOverwritingStrategies.ALL, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class DslMarkersFromReceiver {
        private final Set<FqName> common;
        private final Set<FqName> fromContainingFunctionType;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DslMarkersFromReceiver copy$default(DslMarkersFromReceiver dslMarkersFromReceiver, Set set, Set set2, int i, Object obj) {
            if ((i & 1) != 0) {
                set = dslMarkersFromReceiver.common;
            }
            if ((i & 2) != 0) {
                set2 = dslMarkersFromReceiver.fromContainingFunctionType;
            }
            return dslMarkersFromReceiver.copy(set, set2);
        }

        public final Set<FqName> component1() {
            return this.common;
        }

        public final Set<FqName> component2() {
            return this.fromContainingFunctionType;
        }

        public final DslMarkersFromReceiver copy(Set<FqName> common, Set<FqName> fromContainingFunctionType) {
            Intrinsics.checkNotNullParameter(common, "common");
            Intrinsics.checkNotNullParameter(fromContainingFunctionType, "fromContainingFunctionType");
            return new DslMarkersFromReceiver(common, fromContainingFunctionType);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DslMarkersFromReceiver)) {
                return false;
            }
            DslMarkersFromReceiver dslMarkersFromReceiver = (DslMarkersFromReceiver) other;
            return Intrinsics.areEqual(this.common, dslMarkersFromReceiver.common) && Intrinsics.areEqual(this.fromContainingFunctionType, dslMarkersFromReceiver.fromContainingFunctionType);
        }

        public int hashCode() {
            return (this.common.hashCode() * 31) + this.fromContainingFunctionType.hashCode();
        }

        public String toString() {
            return "DslMarkersFromReceiver(common=" + this.common + ", fromContainingFunctionType=" + this.fromContainingFunctionType + ')';
        }

        public DslMarkersFromReceiver(Set<FqName> common, Set<FqName> fromContainingFunctionType) {
            Intrinsics.checkNotNullParameter(common, "common");
            Intrinsics.checkNotNullParameter(fromContainingFunctionType, "fromContainingFunctionType");
            this.common = common;
            this.fromContainingFunctionType = fromContainingFunctionType;
        }

        public final Set<FqName> getCommon() {
            return this.common;
        }

        public final Set<FqName> getFromContainingFunctionType() {
            return this.fromContainingFunctionType;
        }

        public final Set<FqName> all() {
            return SetsKt.plus((Set) this.common, (Iterable) this.fromContainingFunctionType);
        }
    }

    public final DslMarkersFromReceiver extractDslMarkerFqNames(ReceiverValue receiver) {
        Set setEmptySet;
        Annotations annotations;
        List listExtractDslMarkerFqNames;
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        KotlinType type = receiver.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        Set<FqName> setExtractDslMarkerFqNames = extractDslMarkerFqNames(type);
        ExtensionReceiver extensionReceiver = receiver instanceof ExtensionReceiver ? (ExtensionReceiver) receiver : null;
        CallableDescriptor declarationDescriptor = extensionReceiver != null ? extensionReceiver.getDeclarationDescriptor() : null;
        FunctionDescriptor functionDescriptor = declarationDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) declarationDescriptor : null;
        if (functionDescriptor == null || (annotations = (Annotations) functionDescriptor.getUserData(FunctionTypeAnnotationsKey.INSTANCE)) == null || (listExtractDslMarkerFqNames = DslMarkerUtilsKt.extractDslMarkerFqNames(annotations)) == null || (setEmptySet = CollectionsKt.toSet(listExtractDslMarkerFqNames)) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        return new DslMarkersFromReceiver(setExtractDslMarkerFqNames, setEmptySet);
    }

    public final Set<FqName> extractDslMarkerFqNames(KotlinType kotlinType) {
        Sequence<ClassifierDescriptor> allSuperClassifiers;
        Iterable iterableAsIterable;
        TypeConstructor constructor;
        ClassifierDescriptor this$0;
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(DslMarkerUtilsKt.extractDslMarkerFqNames(kotlinType.getAnnotations()));
        SimpleType abbreviation = SpecialTypesKt.getAbbreviation(kotlinType);
        if (abbreviation != null && (constructor = abbreviation.getConstructor()) != null && (this$0 = constructor.getThis$0()) != null) {
            linkedHashSet.addAll(DslMarkerUtilsKt.extractDslMarkerFqNames(this$0.getAnnotations()));
            TypeAliasDescriptor typeAliasDescriptor = this$0 instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) this$0 : null;
            if (typeAliasDescriptor != null) {
                linkedHashSet.addAll(INSTANCE.extractDslMarkerFqNames(typeAliasDescriptor.getUnderlyingType()));
            }
        }
        ClassifierDescriptor this$1 = kotlinType.getConstructor().getThis$0();
        if (this$1 != null && (allSuperClassifiers = DescriptorUtilsKt.getAllSuperClassifiers(this$1)) != null && (iterableAsIterable = SequencesKt.asIterable(allSuperClassifiers)) != null) {
            Iterator it = iterableAsIterable.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(linkedHashSet, DslMarkerUtilsKt.extractDslMarkerFqNames(((ClassifierDescriptor) it.next()).getAnnotations()));
            }
        }
        return linkedHashSet;
    }

    public final FqName getDSL_MARKER_FQ_NAME() {
        return DSL_MARKER_FQ_NAME;
    }
}
