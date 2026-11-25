package org.jetbrains.kotlin.psi2ir.generators.fragments;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;

/* JADX INFO: compiled from: FragmentContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentContext;", "", "capturedDescriptorToFragmentParameterMap", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "(Ljava/util/Map;)V", "getCapturedDescriptorToFragmentParameterMap", "()Ljava/util/Map;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FragmentContext {
    private final Map<DeclarationDescriptor, IrValueParameterSymbol> capturedDescriptorToFragmentParameterMap;

    /* JADX WARN: Multi-variable type inference failed */
    public FragmentContext() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public FragmentContext(Map<DeclarationDescriptor, IrValueParameterSymbol> capturedDescriptorToFragmentParameterMap) {
        Intrinsics.checkNotNullParameter(capturedDescriptorToFragmentParameterMap, "capturedDescriptorToFragmentParameterMap");
        this.capturedDescriptorToFragmentParameterMap = capturedDescriptorToFragmentParameterMap;
    }

    public /* synthetic */ FragmentContext(LinkedHashMap linkedHashMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new LinkedHashMap() : linkedHashMap);
    }

    public final Map<DeclarationDescriptor, IrValueParameterSymbol> getCapturedDescriptorToFragmentParameterMap() {
        return this.capturedDescriptorToFragmentParameterMap;
    }
}
