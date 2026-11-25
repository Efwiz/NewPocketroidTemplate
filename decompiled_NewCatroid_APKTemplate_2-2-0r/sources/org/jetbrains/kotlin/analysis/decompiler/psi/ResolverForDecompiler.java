package org.jetbrains.kotlin.analysis.decompiler.psi;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ResolverForDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/ResolverForDecompiler;", "", "resolveDeclarationsInFacade", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "resolveTopLevelClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ResolverForDecompiler {
    List<DeclarationDescriptor> resolveDeclarationsInFacade(FqName facadeFqName);

    ClassDescriptor resolveTopLevelClass(ClassId classId);
}
