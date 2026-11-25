package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;

/* JADX INFO: loaded from: classes3.dex */
public final class RecaptureTypeMapper extends PsiTypeMapper {
    public static final Key<PsiElement> SELF_REFERENCE = Key.create("SELF_REFERENCE");
    private final Set<PsiClassType> myVisited = new ReferenceOpenHashSet();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "classType";
        } else if (i != 2) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "capturedWildcardType";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/RecaptureTypeMapper";
        if (i == 1) {
            objArr[2] = "visitClassType";
        } else if (i != 2) {
            objArr[2] = "visitType";
        } else {
            objArr[2] = "visitCapturedWildcardType";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper, org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitType(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
        return psiType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper, org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitClassType(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(1);
        }
        if (!this.myVisited.add(psiClassType)) {
            return psiClassType;
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        return element == null ? psiClassType : new PsiImmediateClassType(element, recapture(classResolveResultResolveGenerics.getSubstitutor()));
    }

    public PsiSubstitutor recapture(PsiSubstitutor psiSubstitutor) {
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        for (Map.Entry<PsiTypeParameter, PsiType> entry : psiSubstitutor.getSubstitutionMap().entrySet()) {
            PsiType value = entry.getValue();
            psiSubstitutorPut = psiSubstitutorPut.put(entry.getKey(), value == null ? null : mapType(value));
        }
        return psiSubstitutorPut;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeMapper, org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
    public PsiType visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
        if (psiCapturedWildcardType == null) {
            $$$reportNull$$$0(2);
        }
        PsiElement context = psiCapturedWildcardType.getContext();
        PsiElement psiElement = (PsiElement) context.getCopyableUserData(SELF_REFERENCE);
        if (psiElement != null) {
            context = psiElement;
        }
        PsiCapturedWildcardType psiCapturedWildcardTypeCreate = PsiCapturedWildcardType.create(psiCapturedWildcardType.getWildcard(), context, psiCapturedWildcardType.getTypeParameter());
        psiCapturedWildcardTypeCreate.setUpperBound((PsiType) psiCapturedWildcardType.getUpperBound(false).accept(this));
        return psiCapturedWildcardTypeCreate;
    }

    public static void encode(PsiElement psiElement) {
        psiElement.accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.RecaptureTypeMapper.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/impl/RecaptureTypeMapper$1", "visitElement"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement psiElement2) {
                if (psiElement2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (psiElement2 instanceof PsiExpression) {
                    psiElement2.putCopyableUserData(RecaptureTypeMapper.SELF_REFERENCE, psiElement2);
                }
                super.visitElement(psiElement2);
            }
        });
    }

    public static void clean(PsiElement psiElement) {
        psiElement.accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.RecaptureTypeMapper.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/impl/RecaptureTypeMapper$2", "visitElement"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement psiElement2) {
                if (psiElement2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (psiElement2 instanceof PsiExpression) {
                    psiElement2.putCopyableUserData(RecaptureTypeMapper.SELF_REFERENCE, null);
                }
                super.visitElement(psiElement2);
            }
        });
    }
}
