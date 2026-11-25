package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: MultifileClassCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u001e\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/MultifileClassCodegen;", "", CompilerOptions.GENERATE, "", "generateClassOrObject", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "packagePartContext", "Lorg/jetbrains/kotlin/codegen/context/FieldOwnerContext;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MultifileClassCodegen {
    void generate();

    void generateClassOrObject(KtClassOrObject classOrObject, FieldOwnerContext<PackageFragmentDescriptor> packagePartContext);
}
