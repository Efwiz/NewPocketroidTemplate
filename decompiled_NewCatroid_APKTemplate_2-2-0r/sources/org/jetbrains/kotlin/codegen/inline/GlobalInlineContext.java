package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.utils.ThreadLocalKt;

/* JADX INFO: compiled from: GlobalInlineContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\"B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ4\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0018\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00140\u001cJ\u0006\u0010\u001d\u001a\u00020\u0014J\u0006\u0010\u001e\u001a\u00020\u0014J\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0010J\u000e\u0010!\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0010R#\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\f\u0010\rR'\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\n0\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0011\u0010\u0006¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/GlobalInlineContext;", "", "()V", "inlineCallsAndDeclarations", "Ljava/util/LinkedList;", "getInlineCallsAndDeclarations", "()Ljava/util/LinkedList;", "inlineCallsAndDeclarations$delegate", "Lkotlin/properties/ReadWriteProperty;", "inlineDeclarationSet", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getInlineDeclarationSet", "()Ljava/util/Set;", "inlineDeclarationSet$delegate", "typesUsedInInlineFunctions", "", "getTypesUsedInInlineFunctions", "typesUsedInInlineFunctions$delegate", "enterDeclaration", "", "descriptor", "enterIntoInlining", "", "callee", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/codegen/inline/GlobalInlineContext$InlineFunctionSource;", "reportInlineCallCycle", "Lkotlin/Function2;", "exitDeclaration", "exitFromInlining", "isTypeFromInlineFunction", ModuleXmlParser.TYPE, "recordTypeFromInlineFunction", "InlineFunctionSource", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GlobalInlineContext {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(GlobalInlineContext.class), "inlineCallsAndDeclarations", "getInlineCallsAndDeclarations()Ljava/util/LinkedList;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(GlobalInlineContext.class), "inlineDeclarationSet", "getInlineDeclarationSet()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(GlobalInlineContext.class), "typesUsedInInlineFunctions", "getTypesUsedInInlineFunctions()Ljava/util/LinkedList;"))};

    /* JADX INFO: renamed from: inlineCallsAndDeclarations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty inlineCallsAndDeclarations = ThreadLocalKt.threadLocal(new Function0<LinkedList<Object>>() { // from class: org.jetbrains.kotlin.codegen.inline.GlobalInlineContext$inlineCallsAndDeclarations$2
        @Override // kotlin.jvm.functions.Function0
        public final LinkedList<Object> invoke() {
            return new LinkedList<>();
        }
    });

    /* JADX INFO: renamed from: inlineDeclarationSet$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty inlineDeclarationSet = ThreadLocalKt.threadLocal(new Function0<Set<CallableDescriptor>>() { // from class: org.jetbrains.kotlin.codegen.inline.GlobalInlineContext$inlineDeclarationSet$2
        @Override // kotlin.jvm.functions.Function0
        public final Set<CallableDescriptor> invoke() {
            return new LinkedHashSet();
        }
    });

    /* JADX INFO: renamed from: typesUsedInInlineFunctions$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty typesUsedInInlineFunctions = ThreadLocalKt.threadLocal(new Function0<LinkedList<Set<String>>>() { // from class: org.jetbrains.kotlin.codegen.inline.GlobalInlineContext$typesUsedInInlineFunctions$2
        @Override // kotlin.jvm.functions.Function0
        public final LinkedList<Set<String>> invoke() {
            return new LinkedList<>();
        }
    });

    /* JADX INFO: compiled from: GlobalInlineContext.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/GlobalInlineContext$InlineFunctionSource;", "", "()V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class InlineFunctionSource {
    }

    private final LinkedList<Object> getInlineCallsAndDeclarations() {
        return (LinkedList) this.inlineCallsAndDeclarations.getValue(this, $$delegatedProperties[0]);
    }

    private final Set<CallableDescriptor> getInlineDeclarationSet() {
        return (Set) this.inlineDeclarationSet.getValue(this, $$delegatedProperties[1]);
    }

    private final LinkedList<Set<String>> getTypesUsedInInlineFunctions() {
        return (LinkedList) this.typesUsedInInlineFunctions.getValue(this, $$delegatedProperties[2]);
    }

    public final void enterDeclaration(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        getInlineDeclarationSet().contains(descriptor.getOriginal());
        Set<CallableDescriptor> inlineDeclarationSet = getInlineDeclarationSet();
        CallableDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        inlineDeclarationSet.add(original);
        getInlineCallsAndDeclarations().add(descriptor.getOriginal());
    }

    public final void exitDeclaration() {
        Set<CallableDescriptor> inlineDeclarationSet = getInlineDeclarationSet();
        TypeIntrinsics.asMutableCollection(inlineDeclarationSet).remove(getInlineCallsAndDeclarations().removeLast());
    }

    public final boolean enterIntoInlining(CallableDescriptor callee, InlineFunctionSource element, Function2<? super InlineFunctionSource, ? super CallableDescriptor, Unit> reportInlineCallCycle) {
        Intrinsics.checkNotNullParameter(reportInlineCallCycle, "reportInlineCallCycle");
        if (callee != null && getInlineDeclarationSet().contains(callee.getOriginal())) {
            if (element != null) {
                CallableDescriptor original = callee.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                reportInlineCallCycle.invoke(element, original);
            }
            LinkedList<Object> inlineCallsAndDeclarations = getInlineCallsAndDeclarations();
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (Object obj : inlineCallsAndDeclarations) {
                if (!z) {
                    if (Intrinsics.areEqual(obj, callee.getOriginal())) {
                        arrayList.add(obj);
                        z = true;
                    }
                } else {
                    arrayList.add(obj);
                }
            }
            for (Pair pair : CollectionsKt.zipWithNext(arrayList)) {
                Object objComponent1 = pair.component1();
                Object objComponent2 = pair.component2();
                if ((objComponent1 instanceof InlineFunctionSource) && (objComponent2 instanceof CallableDescriptor)) {
                    reportInlineCallCycle.invoke(objComponent1, objComponent2);
                }
            }
            return false;
        }
        getInlineCallsAndDeclarations().add(element);
        getTypesUsedInInlineFunctions().push(new HashSet());
        return true;
    }

    public final void exitFromInlining() {
        getInlineCallsAndDeclarations().removeLast();
        Set<String> setPop = getTypesUsedInInlineFunctions().pop();
        Set<String> setPeek = getTypesUsedInInlineFunctions().peek();
        if (setPeek != null) {
            Intrinsics.checkNotNull(setPop);
            setPeek.addAll(setPop);
        }
    }

    public final boolean recordTypeFromInlineFunction(String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return getTypesUsedInInlineFunctions().peek().add(type);
    }

    public final boolean isTypeFromInlineFunction(String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return getTypesUsedInInlineFunctions().peek().contains(type);
    }
}
