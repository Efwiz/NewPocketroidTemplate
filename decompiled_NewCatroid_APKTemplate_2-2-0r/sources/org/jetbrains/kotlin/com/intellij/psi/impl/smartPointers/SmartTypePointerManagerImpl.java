package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.ClassTypePointerFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.kotlin.com.intellij.psi.SmartTypePointer;
import org.jetbrains.kotlin.com.intellij.psi.SmartTypePointerManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImmediateClassType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.NullableFunction;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SmartTypePointerManagerImpl extends SmartTypePointerManager {
    private static final SmartTypePointer NULL_POINTER = new SmartTypePointer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartTypePointerManagerImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.psi.SmartTypePointer
        public final PsiType getType() {
            return SmartTypePointerManagerImpl.lambda$static$0();
        }
    };
    private final Project myProject;
    private final SmartPointerManager myPsiPointerManager;

    /* JADX WARN: Code duplicated, block: B:18:0x0028  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl";
        } else if (i == 2) {
            objArr[0] = "classType";
        } else if (i != 3) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl";
        }
        if (i == 1) {
            objArr[1] = "createSmartTypePointer";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl";
        } else {
            objArr[1] = "createClassReferenceTypePointer";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "createClassReferenceTypePointer";
            } else if (i != 3) {
                objArr[2] = "createSmartTypePointer";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ PsiType lambda$static$0() {
        return null;
    }

    public SmartTypePointerManagerImpl(Project project) {
        this.myPsiPointerManager = SmartPointerManager.getInstance(project);
        this.myProject = project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartTypePointerManager
    public SmartTypePointer createSmartTypePointer(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
        SmartTypePointer smartTypePointer = (SmartTypePointer) psiType.accept(new SmartTypeCreatingVisitor());
        if (smartTypePointer == null) {
            smartTypePointer = NULL_POINTER;
        }
        if (smartTypePointer == null) {
            $$$reportNull$$$0(1);
        }
        return smartTypePointer;
    }

    private static final class SimpleTypePointer implements SmartTypePointer {
        private final PsiType myType;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ModuleXmlParser.TYPE, "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl$SimpleTypePointer", "<init>"));
        }

        private SimpleTypePointer(PsiType psiType) {
            if (psiType == null) {
                $$$reportNull$$$0(0);
            }
            this.myType = psiType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.SmartTypePointer
        public PsiType getType() {
            return this.myType;
        }
    }

    private static class ArrayTypePointer extends TypePointerBase<PsiArrayType> {
        private final SmartTypePointer myComponentTypePointer;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = "componentTypePointer";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl$ArrayTypePointer";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ArrayTypePointer(PsiArrayType psiArrayType, SmartTypePointer smartTypePointer) {
            super(psiArrayType);
            if (psiArrayType == null) {
                $$$reportNull$$$0(0);
            }
            if (smartTypePointer == null) {
                $$$reportNull$$$0(1);
            }
            this.myComponentTypePointer = smartTypePointer;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.TypePointerBase
        public PsiArrayType calcType() {
            PsiType type = this.myComponentTypePointer.getType();
            if (type == null) {
                return null;
            }
            return new PsiArrayType(type);
        }
    }

    private static class WildcardTypePointer extends TypePointerBase<PsiWildcardType> {
        private final SmartTypePointer myBoundPointer;
        private final boolean myIsExtending;
        private final PsiManager myManager;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ModuleXmlParser.TYPE, "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl$WildcardTypePointer", "<init>"));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        WildcardTypePointer(PsiWildcardType psiWildcardType, SmartTypePointer smartTypePointer) {
            super(psiWildcardType);
            if (psiWildcardType == null) {
                $$$reportNull$$$0(0);
            }
            this.myManager = psiWildcardType.getManager();
            this.myBoundPointer = smartTypePointer;
            this.myIsExtending = psiWildcardType.isExtends();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.TypePointerBase
        public PsiWildcardType calcType() {
            SmartTypePointer smartTypePointer = this.myBoundPointer;
            if (smartTypePointer == null) {
                return PsiWildcardType.createUnbounded(this.myManager);
            }
            PsiType type = smartTypePointer.getType();
            if (type == null) {
                return null;
            }
            if (this.myIsExtending) {
                return PsiWildcardType.createExtends(this.myManager, type);
            }
            return PsiWildcardType.createSuper(this.myManager, type);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ClassTypePointer extends TypePointerBase<PsiClassType> {
        private final SmartPsiElementPointer<?>[] myAnnotations;
        private final SmartPsiElementPointer<?> myClass;
        private final LanguageLevel myLevel;
        private final Map<SmartPsiElementPointer<PsiTypeParameter>, SmartTypePointer> myMap;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "aClass";
            } else if (i == 2) {
                objArr[0] = "languageLevel";
            } else if (i == 3) {
                objArr[0] = "map";
            } else if (i != 4) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = "annotations";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl$ClassTypePointer";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ClassTypePointer(PsiClassType psiClassType, SmartPsiElementPointer<?> smartPsiElementPointer, LanguageLevel languageLevel, Map<SmartPsiElementPointer<PsiTypeParameter>, SmartTypePointer> map, SmartPsiElementPointer<?>[] smartPsiElementPointerArr) {
            super(psiClassType);
            if (psiClassType == null) {
                $$$reportNull$$$0(0);
            }
            if (smartPsiElementPointer == null) {
                $$$reportNull$$$0(1);
            }
            if (languageLevel == null) {
                $$$reportNull$$$0(2);
            }
            if (map == null) {
                $$$reportNull$$$0(3);
            }
            if (smartPsiElementPointerArr == null) {
                $$$reportNull$$$0(4);
            }
            this.myClass = smartPsiElementPointer;
            this.myLevel = languageLevel;
            this.myMap = map;
            this.myAnnotations = smartPsiElementPointerArr;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.TypePointerBase
        public PsiClassType calcType() {
            PsiElement element = this.myClass.getElement();
            if (!(element instanceof PsiClass)) {
                return null;
            }
            HashMap map = new HashMap();
            for (Map.Entry<SmartPsiElementPointer<PsiTypeParameter>, SmartTypePointer> entry : this.myMap.entrySet()) {
                PsiTypeParameter psiTypeParameter = (PsiTypeParameter) entry.getKey().getElement();
                if (psiTypeParameter != null) {
                    SmartTypePointer value = entry.getValue();
                    map.put(psiTypeParameter, value == null ? null : value.getType());
                }
            }
            PsiClass psiClass = (PsiClass) element;
            for (PsiTypeParameter psiTypeParameter2 : PsiUtil.typeParametersIterable(psiClass)) {
                if (!map.containsKey(psiTypeParameter2)) {
                    map.put(psiTypeParameter2, null);
                }
            }
            return new PsiImmediateClassType(psiClass, PsiSubstitutor.createSubstitutor(map), this.myLevel, (PsiAnnotation[]) Stream.of((Object[]) this.myAnnotations).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartTypePointerManagerImpl$ClassTypePointer$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((SmartPsiElementPointer) obj).getElement();
                }
            }).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartTypePointerManagerImpl$ClassTypePointer$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((PsiElement) obj);
                }
            }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartTypePointerManagerImpl$ClassTypePointer$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return SmartTypePointerManagerImpl.ClassTypePointer.lambda$calcType$0(i);
                }
            }));
        }

        static /* synthetic */ PsiAnnotation[] lambda$calcType$0(int i) {
            return new PsiAnnotation[i];
        }
    }

    private final class DisjunctionTypePointer extends TypePointerBase<PsiDisjunctionType> {
        private final List<SmartTypePointer> myPointers;
        final /* synthetic */ SmartTypePointerManagerImpl this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ModuleXmlParser.TYPE, "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl$DisjunctionTypePointer", "<init>"));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private DisjunctionTypePointer(final SmartTypePointerManagerImpl smartTypePointerManagerImpl, PsiDisjunctionType psiDisjunctionType) {
            super(psiDisjunctionType);
            if (psiDisjunctionType == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = smartTypePointerManagerImpl;
            this.myPointers = ContainerUtil.map((Collection) psiDisjunctionType.getDisjunctions(), new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartTypePointerManagerImpl$DisjunctionTypePointer$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return smartTypePointerManagerImpl.createSmartTypePointer((PsiType) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.TypePointerBase
        public PsiDisjunctionType calcType() {
            return new PsiDisjunctionType(ContainerUtil.map((Collection) this.myPointers, (org.jetbrains.kotlin.com.intellij.util.Function) new NullableFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartTypePointerManagerImpl$DisjunctionTypePointer$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return ((SmartTypePointer) obj).getType();
                }
            }), PsiManager.getInstance(this.this$0.myProject));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SmartTypeCreatingVisitor extends PsiTypeVisitor<SmartTypePointer> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "arrayType";
            } else if (i == 2) {
                objArr[0] = "wildcardType";
            } else if (i == 3) {
                objArr[0] = "classType";
            } else if (i != 4) {
                objArr[0] = "primitiveType";
            } else {
                objArr[0] = "disjunctionType";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartTypePointerManagerImpl$SmartTypeCreatingVisitor";
            if (i == 1) {
                objArr[2] = "visitArrayType";
            } else if (i == 2) {
                objArr[2] = "visitWildcardType";
            } else if (i == 3) {
                objArr[2] = "visitClassType";
            } else if (i != 4) {
                objArr[2] = "visitPrimitiveType";
            } else {
                objArr[2] = "visitDisjunctionType";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private SmartTypeCreatingVisitor() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public SmartTypePointer visitPrimitiveType(PsiPrimitiveType psiPrimitiveType) {
            if (psiPrimitiveType == null) {
                $$$reportNull$$$0(0);
            }
            return new SimpleTypePointer(psiPrimitiveType);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public SmartTypePointer visitArrayType(PsiArrayType psiArrayType) {
            if (psiArrayType == null) {
                $$$reportNull$$$0(1);
            }
            SmartTypePointer smartTypePointer = (SmartTypePointer) psiArrayType.getComponentType().accept(this);
            if (smartTypePointer != null) {
                return new ArrayTypePointer(psiArrayType, smartTypePointer);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public SmartTypePointer visitWildcardType(PsiWildcardType psiWildcardType) {
            if (psiWildcardType == null) {
                $$$reportNull$$$0(2);
            }
            PsiType bound = psiWildcardType.getBound();
            return new WildcardTypePointer(psiWildcardType, bound == null ? null : (SmartTypePointer) bound.accept(this));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public SmartTypePointer visitClassType(PsiClassType psiClassType) {
            if (psiClassType == null) {
                $$$reportNull$$$0(3);
            }
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element == null) {
                return SmartTypePointerManagerImpl.this.createClassReferenceTypePointer(psiClassType);
            }
            PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            for (PsiTypeParameter psiTypeParameter : PsiUtil.typeParametersIterable(element)) {
                PsiType psiTypeSubstitute = substitutor.substitute(psiTypeParameter);
                if (psiTypeSubstitute != null) {
                    SmartPsiElementPointer smartPsiElementPointerCreateSmartPsiElementPointer = SmartTypePointerManagerImpl.this.myPsiPointerManager.createSmartPsiElementPointer(psiTypeParameter);
                    SmartTypePointer smartTypePointer = (SmartTypePointer) psiTypeSubstitute.accept(this);
                    map.put(smartPsiElementPointerCreateSmartPsiElementPointer, smartTypePointer);
                    map2.put(psiTypeParameter, smartTypePointer.getType());
                } else {
                    map2.put(psiTypeParameter, null);
                }
            }
            Stream streamOf = Stream.of((Object[]) psiClassType.getAnnotations());
            final SmartPointerManager smartPointerManager = SmartTypePointerManagerImpl.this.myPsiPointerManager;
            Objects.requireNonNull(smartPointerManager);
            SmartPsiElementPointer[] smartPsiElementPointerArr = (SmartPsiElementPointer[]) streamOf.map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartTypePointerManagerImpl$SmartTypeCreatingVisitor$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return smartPointerManager.createSmartPsiElementPointer((PsiAnnotation) obj);
                }
            }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartTypePointerManagerImpl$SmartTypeCreatingVisitor$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return SmartTypePointerManagerImpl.SmartTypeCreatingVisitor.lambda$visitClassType$0(i);
                }
            });
            LanguageLevel languageLevel = psiClassType.getLanguageLevel();
            return new ClassTypePointer(new PsiImmediateClassType(element, PsiSubstitutor.createSubstitutor(map2), languageLevel, psiClassType.getAnnotations()), SmartTypePointerManagerImpl.this.myPsiPointerManager.createSmartPsiElementPointer(element), languageLevel, map, smartPsiElementPointerArr);
        }

        static /* synthetic */ SmartPsiElementPointer[] lambda$visitClassType$0(int i) {
            return new SmartPsiElementPointer[i];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public SmartTypePointer visitDisjunctionType(PsiDisjunctionType psiDisjunctionType) {
            if (psiDisjunctionType == null) {
                $$$reportNull$$$0(4);
            }
            return new DisjunctionTypePointer(psiDisjunctionType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SmartTypePointer createClassReferenceTypePointer(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(2);
        }
        for (ClassTypePointerFactory classTypePointerFactory : ClassTypePointerFactory.EP_NAME.getExtensions()) {
            SmartTypePointer smartTypePointerCreateClassTypePointer = classTypePointerFactory.createClassTypePointer(psiClassType, this.myProject);
            if (smartTypePointerCreateClassTypePointer != null) {
                if (smartTypePointerCreateClassTypePointer == null) {
                    $$$reportNull$$$0(3);
                }
                return smartTypePointerCreateClassTypePointer;
            }
        }
        return new SimpleTypePointer(psiClassType);
    }
}
