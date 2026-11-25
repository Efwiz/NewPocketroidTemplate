package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.ir.util.ConstantValueGenerator;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: ConstantValueGeneratorImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001c\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J$\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J\u001c\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/ConstantValueGeneratorImpl;", "Lorg/jetbrains/kotlin/ir/util/ConstantValueGenerator;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "allowErrorTypeInAnnotations", "", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Z)V", "extractAnnotationOffsets", "Lkotlin/Pair;", "", "annotationDescriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "extractAnnotationParameterOffsets", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "extractOffsets", "sourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantValueGeneratorImpl extends ConstantValueGenerator {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConstantValueGeneratorImpl(ModuleDescriptor moduleDescriptor, ReferenceSymbolTable symbolTable, TypeTranslator typeTranslator, boolean z) {
        super(moduleDescriptor, symbolTable, typeTranslator, z);
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
    }

    @Override // org.jetbrains.kotlin.ir.util.ConstantValueGenerator
    protected Pair<Integer, Integer> extractAnnotationOffsets(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        return extractOffsets(annotationDescriptor.getSource());
    }

    @Override // org.jetbrains.kotlin.ir.util.ConstantValueGenerator
    protected Pair<Integer, Integer> extractAnnotationParameterOffsets(AnnotationDescriptor annotationDescriptor, Name argumentName) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        Intrinsics.checkNotNullParameter(argumentName, "argumentName");
        return extractOffsets(LazyAnnotationsKt.getSourceForArgument(annotationDescriptor, argumentName));
    }

    private final Pair<Integer, Integer> extractOffsets(SourceElement sourceElement) {
        PsiElement psi = PsiSourceElementKt.getPsi(sourceElement);
        if (psi == null || psi.getContainingFile().getFileType().isBinary()) {
            return TuplesKt.to(-1, -1);
        }
        return new Pair<>(Integer.valueOf(PsiUtilsKt.getStartOffset(psi)), Integer.valueOf(PsiUtilsKt.getEndOffset(psi)));
    }
}
