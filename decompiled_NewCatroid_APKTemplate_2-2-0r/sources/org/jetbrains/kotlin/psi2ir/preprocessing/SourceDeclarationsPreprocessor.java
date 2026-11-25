package org.jetbrains.kotlin.psi2ir.preprocessing;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptorKt;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: SourceDeclarationsPreprocessor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0014\u0010\f\u001a\u00020\u00062\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/preprocessing/SourceDeclarationsPreprocessor;", "", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;)V", "processClassOrObject", "", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "processDeclaration", "ktDeclaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "run", "ktFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceDeclarationsPreprocessor {
    private final GeneratorContext context;

    public SourceDeclarationsPreprocessor(GeneratorContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final void run(Collection<? extends KtFile> ktFiles) {
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        Iterator it = CollectionsKt.toSet(ktFiles).iterator();
        while (it.hasNext()) {
            Iterator<KtDeclaration> it2 = ((KtFile) it.next()).getDeclarations().iterator();
            while (it2.hasNext()) {
                processDeclaration(it2.next());
            }
        }
    }

    private final void processDeclaration(KtDeclaration ktDeclaration) {
        if (ktDeclaration instanceof KtClassOrObject) {
            processClassOrObject((KtClassOrObject) ktDeclaration);
        }
    }

    private final void processClassOrObject(KtClassOrObject ktClassOrObject) {
        ClassDescriptor classDescriptorFindClassDescriptor = SyntheticClassOrObjectDescriptorKt.findClassDescriptor(ktClassOrObject, this.context.getBindingContext());
        if (DescriptorUtils.isEnumEntry(classDescriptorFindClassDescriptor)) {
            return;
        }
        this.context.getSymbolTable().getDescriptorExtension().referenceClass(classDescriptorFindClassDescriptor);
        KtClassBody body = ktClassOrObject.getBody();
        if (body != null) {
            for (KtDeclaration ktDeclaration : body.getDeclarations()) {
                Intrinsics.checkNotNull(ktDeclaration);
                processDeclaration(ktDeclaration);
            }
        }
    }
}
