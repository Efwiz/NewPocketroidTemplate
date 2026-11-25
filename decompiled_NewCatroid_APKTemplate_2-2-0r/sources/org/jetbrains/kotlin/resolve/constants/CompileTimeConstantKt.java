package org.jetbrains.kotlin.resolve.constants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: CompileTimeConstant.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u000b"}, d2 = {"createIntegerValueTypeConstant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "value", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "parameters", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "newInferenceEnabled", "", "hasUnsignedTypesInModuleDependencies", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompileTimeConstantKt {
    public static final CompileTimeConstant<?> createIntegerValueTypeConstant(Number value, ModuleDescriptor module, CompileTimeConstant.Parameters parameters, boolean z) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        return new IntegerValueTypeConstant(value, module, parameters, z, false, 16, null);
    }

    public static final boolean hasUnsignedTypesInModuleDependencies(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        return FindClassInModuleKt.findClassAcrossModuleDependencies(module, StandardNames.FqNames.uInt) != null;
    }
}
