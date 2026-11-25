package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature;
import org.jetbrains.kotlin.load.java.SpecialBuiltinMembers;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: builtinSpecialBridges.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JB\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\u00050\u0004\"\b\b\u0000\u0010\u0006*\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H\u00060\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J*\u0010\r\u001a\u00020\u000e\"\b\b\u0000\u0010\u0006*\u00020\u0001*\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H\u00060\nH\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/BuiltinSpecialBridgesUtil;", "", "()V", "generateBridgesForBuiltinSpecial", "", "Lorg/jetbrains/kotlin/codegen/BridgeForBuiltinSpecial;", "Signature", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "signatureByDescriptor", "Lkotlin/Function1;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "shouldHaveTypeSafeBarrier", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltinSpecialBridgesUtil {
    public static final BuiltinSpecialBridgesUtil INSTANCE = new BuiltinSpecialBridgesUtil();

    private BuiltinSpecialBridgesUtil() {
    }

    @JvmStatic
    public static final <Signature> Set<BridgeForBuiltinSpecial<Signature>> generateBridgesForBuiltinSpecial(final FunctionDescriptor function, final Function1<? super FunctionDescriptor, ? extends Signature> signatureByDescriptor, GenerationState state) {
        LinkedHashSet linkedHashSet;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(signatureByDescriptor, "signatureByDescriptor");
        Intrinsics.checkNotNullParameter(state, "state");
        DescriptorBasedFunctionHandleForJvm descriptorBasedFunctionHandleForJvm = new DescriptorBasedFunctionHandleForJvm(function, state);
        boolean isDeclaration = descriptorBasedFunctionHandleForJvm.getIsDeclaration();
        FunctionDescriptor functionDescriptor = function;
        CallableMemberDescriptor overriddenBuiltinReflectingJvmDescriptor = SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(functionDescriptor);
        Intrinsics.checkNotNull(overriddenBuiltinReflectingJvmDescriptor);
        Set setFindAllReachableDeclarations = BuiltinSpecialBridgesKt.findAllReachableDeclarations(function, state);
        Signature signatureInvoke = signatureByDescriptor.invoke(function);
        Signature signatureInvoke2 = signatureByDescriptor.invoke((FunctionDescriptor) overriddenBuiltinReflectingJvmDescriptor);
        boolean z = true;
        boolean z2 = BuiltinSpecialBridgesKt.getSpecialBridgeSignatureIfExists(functionDescriptor, signatureByDescriptor) != null;
        Sequence sequenceMapNotNull = SequencesKt.mapNotNull(DescriptorUtilsKt.overriddenTreeAsSequence(functionDescriptor, true), new Function1<CallableMemberDescriptor, Signature>() { // from class: org.jetbrains.kotlin.codegen.BuiltinSpecialBridgesUtil$generateBridgesForBuiltinSpecial$specialBridgesSignaturesInSuperClass$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Signature invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it == function) {
                    it = null;
                }
                if (it != null) {
                    return (Signature) BuiltinSpecialBridgesKt.getSpecialBridgeSignatureIfExists(it, signatureByDescriptor);
                }
                return null;
            }
        });
        if (!SequencesKt.contains(sequenceMapNotNull, signatureInvoke2)) {
            Set set = setFindAllReachableDeclarations;
            if (!(set instanceof Collection) || !set.isEmpty()) {
                Iterator it = set.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) it.next();
                    if (functionDescriptor2.getModality() == Modality.FINAL && Intrinsics.areEqual(signatureByDescriptor.invoke(functionDescriptor2), signatureInvoke2)) {
                        break;
                    }
                }
            } else {
                z = false;
                break;
            }
        }
        FunctionDescriptor functionDescriptorFindSuperImplementationForStubDelegation = null;
        BridgeForBuiltinSpecial bridgeForBuiltinSpecial = (!z2 || z) ? null : new BridgeForBuiltinSpecial(signatureInvoke2, signatureInvoke, true, false, 8, null);
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        Iterator it2 = setFindAllReachableDeclarations.iterator();
        while (it2.hasNext()) {
            linkedHashSet2.add(signatureByDescriptor.invoke(it2.next()));
        }
        LinkedHashSet linkedHashSet3 = linkedHashSet2;
        CollectionsKt.removeAll(linkedHashSet3, SequencesKt.plus(sequenceMapNotNull, (Iterable) CollectionsKt.listOfNotNull(bridgeForBuiltinSpecial != null ? bridgeForBuiltinSpecial.getFrom() : null)));
        if (!isDeclaration) {
            Collection<? extends FunctionDescriptor> overriddenDescriptors = function.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
            ArrayList<FunctionDescriptor> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            Iterator<T> it3 = collection.iterator();
            while (it3.hasNext()) {
                arrayList.add(((FunctionDescriptor) it3.next()).getOriginal());
            }
            for (FunctionDescriptor functionDescriptor3 : arrayList) {
                Intrinsics.checkNotNull(functionDescriptor3);
                if (!new DescriptorBasedFunctionHandleForJvm(functionDescriptor3, state).getIsAbstract()) {
                    Set setFindAllReachableDeclarations2 = BuiltinSpecialBridgesKt.findAllReachableDeclarations(functionDescriptor3, state);
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setFindAllReachableDeclarations2, 10));
                    Iterator it4 = setFindAllReachableDeclarations2.iterator();
                    while (it4.hasNext()) {
                        arrayList2.add(signatureByDescriptor.invoke(it4.next()));
                    }
                    linkedHashSet3.removeAll(arrayList2);
                }
            }
        }
        LinkedHashSet linkedHashSet4 = new LinkedHashSet();
        if (bridgeForBuiltinSpecial != null && !isDeclaration && !descriptorBasedFunctionHandleForJvm.getIsAbstract()) {
            functionDescriptorFindSuperImplementationForStubDelegation = BuiltinSpecialBridgesKt.findSuperImplementationForStubDelegation(function, state, signatureByDescriptor);
        }
        FunctionDescriptor functionDescriptor4 = functionDescriptorFindSuperImplementationForStubDelegation;
        if (functionDescriptor4 != null) {
            linkedHashSet = linkedHashSet4;
            linkedHashSet.add(new BridgeForBuiltinSpecial(signatureInvoke, signatureByDescriptor.invoke(functionDescriptor4), false, true, 4, null));
        } else {
            linkedHashSet = linkedHashSet4;
        }
        if (linkedHashSet3.remove(signatureInvoke) && functionDescriptor4 == null && !isDeclaration && !descriptorBasedFunctionHandleForJvm.getIsAbstract() && !Intrinsics.areEqual(signatureInvoke, signatureInvoke2)) {
            linkedHashSet.add(new BridgeForBuiltinSpecial(signatureInvoke, signatureInvoke2, false, false));
        }
        LinkedHashSet linkedHashSet5 = linkedHashSet3;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(linkedHashSet5, 10));
        Iterator it5 = linkedHashSet5.iterator();
        while (it5.hasNext()) {
            arrayList3.add(new BridgeForBuiltinSpecial(it5.next(), signatureInvoke, false, false, 12, null));
        }
        linkedHashSet.addAll(arrayList3);
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet, bridgeForBuiltinSpecial);
        return linkedHashSet;
    }

    @JvmStatic
    public static final <Signature> boolean shouldHaveTypeSafeBarrier(FunctionDescriptor functionDescriptor, Function1<? super FunctionDescriptor, ? extends Signature> signatureByDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(signatureByDescriptor, "signatureByDescriptor");
        if (BuiltinMethodsWithSpecialGenericSignature.getDefaultValueForOverriddenBuiltinFunction(functionDescriptor) == null) {
            return false;
        }
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(functionDescriptor);
        if (functionDescriptor2 == null) {
            throw new IllegalStateException(("Overridden built-in member not found: " + functionDescriptor).toString());
        }
        return Intrinsics.areEqual(signatureByDescriptor.invoke(functionDescriptor), signatureByDescriptor.invoke(functionDescriptor2));
    }
}
