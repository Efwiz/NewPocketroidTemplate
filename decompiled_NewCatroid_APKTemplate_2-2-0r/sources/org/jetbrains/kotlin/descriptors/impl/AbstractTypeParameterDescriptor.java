package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.LazyScopeAdapter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.TypeIntersectionScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.AbstractTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractTypeParameterDescriptor extends DeclarationDescriptorNonRootImpl implements TypeParameterDescriptor {
    private final NotNullLazyValue<SimpleType> defaultType;
    private final int index;
    private final boolean reified;
    private final StorageManager storageManager;
    private final NotNullLazyValue<TypeConstructor> typeConstructor;
    private final Variance variance;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 12:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                i2 = 2;
                break;
            case 12:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "containingDeclaration";
                break;
            case 2:
                objArr[0] = "annotations";
                break;
            case 3:
                objArr[0] = "name";
                break;
            case 4:
                objArr[0] = "variance";
                break;
            case 5:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 6:
                objArr[0] = "supertypeLoopChecker";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/descriptors/impl/AbstractTypeParameterDescriptor";
                break;
            case 12:
                objArr[0] = "bounds";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        switch (i) {
            case 7:
                objArr[1] = "getVariance";
                break;
            case 8:
                objArr[1] = "getUpperBounds";
                break;
            case 9:
                objArr[1] = "getTypeConstructor";
                break;
            case 10:
                objArr[1] = "getDefaultType";
                break;
            case 11:
                objArr[1] = "getOriginal";
                break;
            case 12:
            default:
                objArr[1] = "org/jetbrains/kotlin/descriptors/impl/AbstractTypeParameterDescriptor";
                break;
            case 13:
                objArr[1] = "processBoundsWithoutCycles";
                break;
            case 14:
                objArr[1] = "getStorageManager";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                break;
            case 12:
                objArr[2] = "processBoundsWithoutCycles";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                throw new IllegalStateException(str2);
            case 12:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public boolean isCapturedFromOuterDeclaration() {
        return false;
    }

    /* JADX INFO: renamed from: reportSupertypeLoopError */
    protected abstract void mo7470reportSupertypeLoopError(KotlinType kotlinType);

    protected abstract List<KotlinType> resolveUpperBounds();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected AbstractTypeParameterDescriptor(final StorageManager storageManager, DeclarationDescriptor declarationDescriptor, Annotations annotations, final Name name2, Variance variance, boolean z, int i, SourceElement sourceElement, final SupertypeLoopChecker supertypeLoopChecker) {
        super(declarationDescriptor, annotations, name2, sourceElement);
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (annotations == null) {
            $$$reportNull$$$0(2);
        }
        if (name2 == null) {
            $$$reportNull$$$0(3);
        }
        if (variance == null) {
            $$$reportNull$$$0(4);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(5);
        }
        if (supertypeLoopChecker == null) {
            $$$reportNull$$$0(6);
        }
        this.variance = variance;
        this.reified = z;
        this.index = i;
        this.typeConstructor = storageManager.createLazyValue(new Function0<TypeConstructor>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor.1
            @Override // kotlin.jvm.functions.Function0
            public TypeConstructor invoke() {
                return new TypeParameterTypeConstructor(AbstractTypeParameterDescriptor.this, storageManager, supertypeLoopChecker);
            }
        });
        this.defaultType = storageManager.createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor.2
            @Override // kotlin.jvm.functions.Function0
            public SimpleType invoke() {
                return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(TypeAttributes.INSTANCE.getEmpty(), AbstractTypeParameterDescriptor.this.getTypeConstructor(), Collections.emptyList(), false, new LazyScopeAdapter(new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor.2.1
                    @Override // kotlin.jvm.functions.Function0
                    public MemberScope invoke() {
                        return TypeIntersectionScope.create("Scope for type parameter " + name2.asString(), AbstractTypeParameterDescriptor.this.getUpperBounds());
                    }
                }));
            }
        });
        this.storageManager = storageManager;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public Variance getVariance() {
        Variance variance = this.variance;
        if (variance == null) {
            $$$reportNull$$$0(7);
        }
        return variance;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public boolean isReified() {
        return this.reified;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public int getIndex() {
        return this.index;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public List<KotlinType> getUpperBounds() {
        List<KotlinType> supertypes = ((TypeParameterTypeConstructor) getTypeConstructor()).mo7438getSupertypes();
        if (supertypes == null) {
            $$$reportNull$$$0(8);
        }
        return supertypes;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public final TypeConstructor getTypeConstructor() {
        TypeConstructor typeConstructorInvoke = this.typeConstructor.invoke();
        if (typeConstructorInvoke == null) {
            $$$reportNull$$$0(9);
        }
        return typeConstructorInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        SimpleType simpleTypeInvoke = this.defaultType.invoke();
        if (simpleTypeInvoke == null) {
            $$$reportNull$$$0(10);
        }
        return simpleTypeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public TypeParameterDescriptor getOriginal() {
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) super.getOriginal();
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(11);
        }
        return typeParameterDescriptor;
    }

    protected List<KotlinType> processBoundsWithoutCycles(List<KotlinType> list) {
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        return list;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return declarationDescriptorVisitor.visitTypeParameterDescriptor(this, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public StorageManager getStorageManager() {
        StorageManager storageManager = this.storageManager;
        if (storageManager == null) {
            $$$reportNull$$$0(14);
        }
        return storageManager;
    }

    private class TypeParameterTypeConstructor extends AbstractTypeConstructor {
        private final SupertypeLoopChecker supertypeLoopChecker;
        final /* synthetic */ AbstractTypeParameterDescriptor this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8) ? 2 : 3];
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 8:
                    objArr[0] = "org/jetbrains/kotlin/descriptors/impl/AbstractTypeParameterDescriptor$TypeParameterTypeConstructor";
                    break;
                case 6:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 7:
                    objArr[0] = "supertypes";
                    break;
                case 9:
                    objArr[0] = "classifier";
                    break;
                default:
                    objArr[0] = "storageManager";
                    break;
            }
            if (i == 1) {
                objArr[1] = "computeSupertypes";
            } else if (i == 2) {
                objArr[1] = "getParameters";
            } else if (i == 3) {
                objArr[1] = "getDeclarationDescriptor";
            } else if (i == 4) {
                objArr[1] = "getBuiltIns";
            } else if (i == 5) {
                objArr[1] = "getSupertypeLoopChecker";
            } else if (i != 8) {
                objArr[1] = "org/jetbrains/kotlin/descriptors/impl/AbstractTypeParameterDescriptor$TypeParameterTypeConstructor";
            } else {
                objArr[1] = "processSupertypesWithoutCycles";
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 8:
                    break;
                case 6:
                    objArr[2] = "reportSupertypeLoopError";
                    break;
                case 7:
                    objArr[2] = "processSupertypesWithoutCycles";
                    break;
                case 9:
                    objArr[2] = "isSameClassifier";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 8) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isDenotable() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isFinal() {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TypeParameterTypeConstructor(AbstractTypeParameterDescriptor abstractTypeParameterDescriptor, StorageManager storageManager, SupertypeLoopChecker supertypeLoopChecker) {
            super(storageManager);
            if (storageManager == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = abstractTypeParameterDescriptor;
            this.supertypeLoopChecker = supertypeLoopChecker;
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected Collection<KotlinType> computeSupertypes() {
            List<KotlinType> listResolveUpperBounds = this.this$0.resolveUpperBounds();
            if (listResolveUpperBounds == null) {
                $$$reportNull$$$0(1);
            }
            return listResolveUpperBounds;
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public List<TypeParameterDescriptor> getParameters() {
            List<TypeParameterDescriptor> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(2);
            }
            return listEmptyList;
        }

        @Override // org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
        /* JADX INFO: renamed from: getDeclarationDescriptor */
        public ClassifierDescriptor getThis$0() {
            AbstractTypeParameterDescriptor abstractTypeParameterDescriptor = this.this$0;
            if (abstractTypeParameterDescriptor == null) {
                $$$reportNull$$$0(3);
            }
            return abstractTypeParameterDescriptor;
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public KotlinBuiltIns getBuiltIns() {
            KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(this.this$0);
            if (builtIns == null) {
                $$$reportNull$$$0(4);
            }
            return builtIns;
        }

        public String toString() {
            return this.this$0.getName().toString();
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            SupertypeLoopChecker supertypeLoopChecker = this.supertypeLoopChecker;
            if (supertypeLoopChecker == null) {
                $$$reportNull$$$0(5);
            }
            return supertypeLoopChecker;
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected void reportSupertypeLoopError(KotlinType kotlinType) {
            if (kotlinType == null) {
                $$$reportNull$$$0(6);
            }
            this.this$0.mo7470reportSupertypeLoopError(kotlinType);
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected List<KotlinType> processSupertypesWithoutCycles(List<KotlinType> list) {
            if (list == null) {
                $$$reportNull$$$0(7);
            }
            List<KotlinType> listProcessBoundsWithoutCycles = this.this$0.processBoundsWithoutCycles(list);
            if (listProcessBoundsWithoutCycles == null) {
                $$$reportNull$$$0(8);
            }
            return listProcessBoundsWithoutCycles;
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected KotlinType defaultSupertypeIfEmpty() {
            return ErrorUtils.createErrorType(ErrorTypeKind.CYCLIC_UPPER_BOUNDS, new String[0]);
        }

        @Override // org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor
        protected boolean isSameClassifier(ClassifierDescriptor classifierDescriptor) {
            if (classifierDescriptor == null) {
                $$$reportNull$$$0(9);
            }
            return (classifierDescriptor instanceof TypeParameterDescriptor) && DescriptorEquivalenceForOverrides.INSTANCE.areTypeParametersEquivalent(this.this$0, (TypeParameterDescriptor) classifierDescriptor, true);
        }
    }
}
