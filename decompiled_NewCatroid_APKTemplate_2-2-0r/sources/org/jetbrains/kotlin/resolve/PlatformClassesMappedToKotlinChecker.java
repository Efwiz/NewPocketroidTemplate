package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtImportDirective;

/* JADX INFO: loaded from: classes6.dex */
public class PlatformClassesMappedToKotlinChecker {
    /* JADX WARN: Code duplicated, block: B:14:0x002e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "trace";
        } else if (i == 2) {
            objArr[0] = "importDirective";
        } else if (i == 3) {
            objArr[0] = "descriptors";
        } else if (i == 5) {
            objArr[0] = "trace";
        } else if (i == 6) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 7) {
            objArr[0] = "platformToKotlinMap";
        } else {
            objArr[0] = "descriptor";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/PlatformClassesMappedToKotlinChecker";
        if (i == 4 || i == 5 || i == 6 || i == 7) {
            objArr[2] = "reportPlatformClassMappedToKotlin";
        } else {
            objArr[2] = "checkPlatformClassesMappedToKotlin";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static void checkPlatformClassesMappedToKotlin(PlatformToKotlinClassMapper platformToKotlinClassMapper, BindingTrace bindingTrace, KtImportDirective ktImportDirective, Collection<? extends DeclarationDescriptor> collection) {
        if (platformToKotlinClassMapper == null) {
            $$$reportNull$$$0(0);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(1);
        }
        if (ktImportDirective == null) {
            $$$reportNull$$$0(2);
        }
        if (collection == null) {
            $$$reportNull$$$0(3);
        }
        KtExpression importedReference = ktImportDirective.getImportedReference();
        if (importedReference != null) {
            Iterator<? extends DeclarationDescriptor> it = collection.iterator();
            while (it.hasNext()) {
                reportPlatformClassMappedToKotlin(platformToKotlinClassMapper, bindingTrace, importedReference, it.next());
            }
        }
    }

    public static void reportPlatformClassMappedToKotlin(PlatformToKotlinClassMapper platformToKotlinClassMapper, BindingTrace bindingTrace, KtElement ktElement, DeclarationDescriptor declarationDescriptor) {
        if (platformToKotlinClassMapper == null) {
            $$$reportNull$$$0(4);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(5);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(6);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (declarationDescriptor instanceof ClassDescriptor) {
            Collection<ClassDescriptor> collectionMapPlatformClass = platformToKotlinClassMapper.mapPlatformClass((ClassDescriptor) declarationDescriptor);
            if (collectionMapPlatformClass.isEmpty()) {
                return;
            }
            bindingTrace.report(Errors.PLATFORM_CLASS_MAPPED_TO_KOTLIN.on(ktElement, collectionMapPlatformClass));
        }
    }
}
