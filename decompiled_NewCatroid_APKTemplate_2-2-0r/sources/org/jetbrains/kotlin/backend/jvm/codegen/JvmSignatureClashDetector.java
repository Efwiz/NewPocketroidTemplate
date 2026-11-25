package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformerKt;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReporterWithContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ConflictingJvmDeclarationsData;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.MemberKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.RawSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: JvmSignatureClashDetector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 ,2\u00020\u0001:\u0001,B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\bH\u0002J\u001e\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\bH\u0002J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00072\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\f0\u001b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\fH\u0002J\u0006\u0010\u001d\u001a\u00020\u000eJ\b\u0010\u001e\u001a\u00020\u000eH\u0002J,\u0010\u001f\u001a\u00020\u000e2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00150!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010#\u001a\u00020\u0015H\u0002J\b\u0010$\u001a\u00020\u000eH\u0002J\b\u0010%\u001a\u00020\u000eH\u0002J\u000e\u0010&\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\fJ\u0016\u0010'\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u0007J\u0016\u0010)\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0007J\f\u0010*\u001a\u00020+*\u00020\fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u0005\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b`\nX\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u000b\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\b`\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/JvmSignatureClashDetector;", "", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;)V", "fieldsBySignature", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lkotlin/collections/LinkedHashMap;", "methodsBySignature", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "collectOverridesOf", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "result", "collectOverridesTree", "visited", "getConflictingJvmDeclarationsData", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "rawSignature", "methods", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getOverriddenFunctions", "", "mapRawSignature", "reportErrors", "reportFieldSignatureConflicts", "reportJvmSignatureClash", "diagnosticFactory1", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "irDeclarations", "conflictingJvmDeclarationsData", "reportMethodSignatureConflicts", "reportPredefinedMethodSignatureConflicts", "trackFakeOverrideMethod", "trackField", "irField", "trackMethod", "isSpecialOverride", "", "Companion", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSignatureClashDetector {
    private final ClassCodegen classCodegen;
    private final LinkedHashMap<RawSignature, Set<IrField>> fieldsBySignature;
    private final LinkedHashMap<RawSignature, Set<IrFunction>> methodsBySignature;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<IrDeclarationOriginImpl> SPECIAL_BRIDGES_AND_OVERRIDES = SetsKt.setOf((Object[]) new IrDeclarationOriginImpl[]{IrDeclarationOrigin.BRIDGE.INSTANCE, IrDeclarationOrigin.BRIDGE_SPECIAL.INSTANCE, IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE, JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE, JvmLoweredDeclarationOrigin.SUPER_INTERFACE_METHOD_BRIDGE.INSTANCE, AnnotationImplementationTransformerKt.getANNOTATION_IMPLEMENTATION()});
    private static final List<RawSignature> PREDEFINED_SIGNATURES = CollectionsKt.listOf((Object[]) new RawSignature[]{new RawSignature("getClass", "()Ljava/lang/Class;", MemberKind.METHOD), new RawSignature("notify", "()V", MemberKind.METHOD), new RawSignature("notifyAll", "()V", MemberKind.METHOD), new RawSignature("wait", "()V", MemberKind.METHOD), new RawSignature("wait", "(J)V", MemberKind.METHOD), new RawSignature("wait", "(JI)V", MemberKind.METHOD)});

    public JvmSignatureClashDetector(ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        this.classCodegen = classCodegen;
        this.methodsBySignature = new LinkedHashMap<>();
        this.fieldsBySignature = new LinkedHashMap<>();
    }

    public final void trackField(IrField irField, RawSignature rawSignature) {
        Intrinsics.checkNotNullParameter(irField, "irField");
        Intrinsics.checkNotNullParameter(rawSignature, "rawSignature");
        LinkedHashMap<RawSignature, Set<IrField>> linkedHashMap = this.fieldsBySignature;
        SmartSet smartSetCreate = linkedHashMap.get(rawSignature);
        if (smartSetCreate == null) {
            smartSetCreate = SmartSet.INSTANCE.create();
            linkedHashMap.put(rawSignature, smartSetCreate);
        }
        smartSetCreate.add(irField);
    }

    public final void trackMethod(IrFunction irFunction, RawSignature rawSignature) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(rawSignature, "rawSignature");
        LinkedHashMap<RawSignature, Set<IrFunction>> linkedHashMap = this.methodsBySignature;
        SmartSet smartSetCreate = linkedHashMap.get(rawSignature);
        if (smartSetCreate == null) {
            smartSetCreate = SmartSet.INSTANCE.create();
            linkedHashMap.put(rawSignature, smartSetCreate);
        }
        smartSetCreate.add(irFunction);
    }

    public final void trackFakeOverrideMethod(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        if (irFunction.getDispatchReceiverParameter() != null) {
            Iterator<IrFunction> it = getOverriddenFunctions((IrSimpleFunction) irFunction).iterator();
            while (it.hasNext()) {
                trackMethod(irFunction, mapRawSignature(it.next()));
            }
            return;
        }
        trackMethod(irFunction, mapRawSignature(irFunction));
    }

    private final RawSignature mapRawSignature(IrFunction irFunction) {
        JvmMethodSignature jvmMethodSignatureMapFakeOverrideSignatureSkipGeneric = this.classCodegen.getMethodSignatureMapper().mapFakeOverrideSignatureSkipGeneric(irFunction);
        String name2 = jvmMethodSignatureMapFakeOverrideSignatureSkipGeneric.getAsmMethod().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        String descriptor = jvmMethodSignatureMapFakeOverrideSignatureSkipGeneric.getAsmMethod().getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        return new RawSignature(name2, descriptor, MemberKind.METHOD);
    }

    private final Set<IrFunction> getOverriddenFunctions(IrSimpleFunction irFunction) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        collectOverridesOf(irFunction, linkedHashSet);
        return linkedHashSet;
    }

    private final void collectOverridesOf(IrSimpleFunction irFunction, Set<IrFunction> result) {
        Iterator<IrSimpleFunctionSymbol> it = irFunction.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            collectOverridesTree(it.next().getOwner(), result);
        }
    }

    private final void collectOverridesTree(IrSimpleFunction irFunction, Set<IrFunction> visited) {
        if (visited.add(irFunction)) {
            collectOverridesOf(irFunction, visited);
        }
    }

    private final boolean isSpecialOverride(IrFunction irFunction) {
        return CollectionsKt.contains(SPECIAL_BRIDGES_AND_OVERRIDES, irFunction.getOrigin());
    }

    public final void reportErrors() {
        reportMethodSignatureConflicts();
        reportPredefinedMethodSignatureConflicts();
        reportFieldSignatureConflicts();
    }

    private final void reportMethodSignatureConflicts() {
        int i;
        for (Map.Entry<RawSignature, Set<IrFunction>> entry : this.methodsBySignature.entrySet()) {
            RawSignature key = entry.getKey();
            Set<IrFunction> value = entry.getValue();
            if (value.size() > 1) {
                Set<IrFunction> set = value;
                boolean z = set instanceof Collection;
                int i2 = 0;
                if (z && set.isEmpty()) {
                    i = 0;
                } else {
                    Iterator<T> it = set.iterator();
                    i = 0;
                    while (it.hasNext()) {
                        if (IrFakeOverrideUtilsKt.isFakeOverride((IrFunction) it.next()) && (i = i + 1) < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                if (!z || !set.isEmpty()) {
                    Iterator<T> it2 = set.iterator();
                    while (it2.hasNext()) {
                        if (isSpecialOverride((IrFunction) it2.next()) && (i2 = i2 + 1) < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                int size = (value.size() - i) - i2;
                Set<IrFunction> set2 = value;
                ConflictingJvmDeclarationsData conflictingJvmDeclarationsData = getConflictingJvmDeclarationsData(key, set2);
                if (size == 0 && (i > 1 || i2 > 1)) {
                    if (!Intrinsics.areEqual(this.classCodegen.getIrClass().getOrigin(), JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE)) {
                        reportJvmSignatureClash(JvmBackendErrors.INSTANCE.getCONFLICTING_INHERITED_JVM_DECLARATIONS(), CollectionsKt.listOf(this.classCodegen.getIrClass()), conflictingJvmDeclarationsData);
                    }
                } else {
                    if (i == 0 && i2 == 0) {
                        if (Intrinsics.areEqual(this.classCodegen.getIrClass().getOrigin(), JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE)) {
                            if (!z || !set.isEmpty()) {
                                Iterator<T> it3 = set.iterator();
                                while (it3.hasNext()) {
                                    if (DescriptorVisibilities.isPrivate(((IrFunction) it3.next()).getVisibility())) {
                                    }
                                }
                            }
                        }
                        reportJvmSignatureClash(JvmBackendErrors.INSTANCE.getCONFLICTING_JVM_DECLARATIONS(), set2, conflictingJvmDeclarationsData);
                        break;
                    }
                    if (!Intrinsics.areEqual(this.classCodegen.getIrClass().getOrigin(), JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE)) {
                        KtDiagnosticFactory1<ConflictingJvmDeclarationsData> accidental_override = JvmBackendErrors.INSTANCE.getACCIDENTAL_OVERRIDE();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : set) {
                            IrFunction irFunction = (IrFunction) obj;
                            if (!IrFakeOverrideUtilsKt.isFakeOverride(irFunction) && !isSpecialOverride(irFunction)) {
                                arrayList.add(obj);
                            }
                        }
                        reportJvmSignatureClash(accidental_override, arrayList, conflictingJvmDeclarationsData);
                    }
                }
            }
        }
    }

    private final void reportPredefinedMethodSignatureConflicts() {
        for (RawSignature rawSignature : PREDEFINED_SIGNATURES) {
            Set<IrFunction> set = this.methodsBySignature.get(rawSignature);
            if (set != null) {
                ArrayList arrayList = new ArrayList();
                for (Object obj : set) {
                    IrFunction irFunction = (IrFunction) obj;
                    if (!IrFakeOverrideUtilsKt.isFakeOverride(irFunction) && !isSpecialOverride(irFunction)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (!arrayList2.isEmpty()) {
                    String internalName = this.classCodegen.getType().getInternalName();
                    Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                    ArrayList arrayList3 = arrayList2;
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                    Iterator it = arrayList3.iterator();
                    while (it.hasNext()) {
                        arrayList4.add(IrBasedDescriptorsKt.toIrBasedDescriptor((IrFunction) it.next()));
                    }
                    reportJvmSignatureClash(JvmBackendErrors.INSTANCE.getACCIDENTAL_OVERRIDE(), arrayList2, new ConflictingJvmDeclarationsData(internalName, null, rawSignature, null, arrayList4));
                }
            }
        }
    }

    private final void reportFieldSignatureConflicts() {
        for (Map.Entry<RawSignature, Set<IrField>> entry : this.fieldsBySignature.entrySet()) {
            RawSignature key = entry.getKey();
            Set<IrField> value = entry.getValue();
            if (value.size() > 1) {
                Set<IrField> set = value;
                reportJvmSignatureClash(JvmBackendErrors.INSTANCE.getCONFLICTING_JVM_DECLARATIONS(), set, getConflictingJvmDeclarationsData(key, set));
            }
        }
    }

    private final void reportJvmSignatureClash(KtDiagnosticFactory1<ConflictingJvmDeclarationsData> diagnosticFactory1, Collection<? extends IrDeclaration> irDeclarations, ConflictingJvmDeclarationsData conflictingJvmDeclarationsData) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (IrDeclaration irDeclaration : irDeclarations) {
            IrClass irClass = irDeclaration.getStartOffset() < 0 ? null : irDeclaration;
            if (irClass == null) {
                irClass = this.classCodegen.getIrClass();
            }
            IrDeclaration irDeclaration2 = irClass;
            linkedHashSet.add(this.classCodegen.getContext().getKtDiagnosticReporter().at(UtilsKt.sourceElement(irDeclaration2), irDeclaration2, IrUtilsKt.getFile(irDeclaration)));
        }
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            ((KtDiagnosticReporterWithContext.DiagnosticContextImpl) it.next()).report(diagnosticFactory1, conflictingJvmDeclarationsData);
        }
    }

    private final ConflictingJvmDeclarationsData getConflictingJvmDeclarationsData(RawSignature rawSignature, Collection<? extends IrDeclaration> methods) {
        String internalName = this.classCodegen.getType().getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        Collection<? extends IrDeclaration> collection = methods;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(IrBasedDescriptorsKt.toIrBasedDescriptor((IrDeclaration) it.next()));
        }
        return new ConflictingJvmDeclarationsData(internalName, null, rawSignature, null, arrayList);
    }

    /* JADX INFO: compiled from: JvmSignatureClashDetector.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/JvmSignatureClashDetector$Companion;", "", "()V", "PREDEFINED_SIGNATURES", "", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", "getPREDEFINED_SIGNATURES", "()Ljava/util/List;", "SPECIAL_BRIDGES_AND_OVERRIDES", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "getSPECIAL_BRIDGES_AND_OVERRIDES", "()Ljava/util/Set;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<IrDeclarationOriginImpl> getSPECIAL_BRIDGES_AND_OVERRIDES() {
            return JvmSignatureClashDetector.SPECIAL_BRIDGES_AND_OVERRIDES;
        }

        public final List<RawSignature> getPREDEFINED_SIGNATURES() {
            return JvmSignatureClashDetector.PREDEFINED_SIGNATURES;
        }
    }
}
