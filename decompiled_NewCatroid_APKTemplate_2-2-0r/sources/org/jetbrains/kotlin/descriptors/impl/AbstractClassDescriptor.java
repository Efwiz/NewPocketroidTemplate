package org.jetbrains.kotlin.descriptors.impl;

import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.InnerClassesScopeWrapper;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.SubstitutingScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractClassDescriptor extends ModuleAwareClassDescriptor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final NotNullLazyValue<SimpleType> defaultType;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final Name f738name;
    private final NotNullLazyValue<ReceiverParameterDescriptor> thisAsReceiverParameter;
    private final NotNullLazyValue<MemberScope> unsubstitutedInnerClassesScope;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 9 || i == 12 || i == 14 || i == 16 || i == 17 || i == 19 || i == 20) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 9 || i == 12 || i == 14 || i == 16 || i == 17 || i == 19 || i == 20) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "name";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 12:
            case 14:
            case 16:
            case 17:
            case 19:
            case 20:
                objArr[0] = "org/jetbrains/kotlin/descriptors/impl/AbstractClassDescriptor";
                break;
            case 7:
            case 13:
                objArr[0] = "typeArguments";
                break;
            case 8:
            case 11:
                objArr[0] = "kotlinTypeRefiner";
                break;
            case 10:
            case 15:
                objArr[0] = "typeSubstitution";
                break;
            case 18:
                objArr[0] = "substitutor";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        if (i == 2) {
            objArr[1] = "getName";
        } else if (i == 3) {
            objArr[1] = "getOriginal";
        } else if (i == 4) {
            objArr[1] = "getUnsubstitutedInnerClassesScope";
        } else if (i == 5) {
            objArr[1] = "getThisAsReceiverParameter";
        } else if (i == 6) {
            objArr[1] = "getContextReceivers";
        } else if (i == 9 || i == 12 || i == 14 || i == 16) {
            objArr[1] = "getMemberScope";
        } else if (i == 17) {
            objArr[1] = "getUnsubstitutedMemberScope";
        } else if (i == 19) {
            objArr[1] = "substitute";
        } else if (i != 20) {
            objArr[1] = "org/jetbrains/kotlin/descriptors/impl/AbstractClassDescriptor";
        } else {
            objArr[1] = "getDefaultType";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 12:
            case 14:
            case 16:
            case 17:
            case 19:
            case 20:
                break;
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 15:
                objArr[2] = "getMemberScope";
                break;
            case 18:
                objArr[2] = "substitute";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 9 && i != 12 && i != 14 && i != 16 && i != 17 && i != 19 && i != 20) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public SimpleType getDefaultFunctionTypeForSamInterface() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public ClassDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isDefinitelyNotSamInterface() {
        return false;
    }

    public AbstractClassDescriptor(StorageManager storageManager, Name name2) {
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
        if (name2 == null) {
            $$$reportNull$$$0(1);
        }
        this.f738name = name2;
        this.defaultType = storageManager.createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor.1
            @Override // kotlin.jvm.functions.Function0
            public SimpleType invoke() {
                AbstractClassDescriptor abstractClassDescriptor = AbstractClassDescriptor.this;
                return TypeUtils.makeUnsubstitutedType(abstractClassDescriptor, abstractClassDescriptor.getUnsubstitutedMemberScope(), new Function1<KotlinTypeRefiner, SimpleType>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor.1.1
                    @Override // kotlin.jvm.functions.Function1
                    public SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                        ClassifierDescriptor classifierDescriptorRefineDescriptor = kotlinTypeRefiner.refineDescriptor(AbstractClassDescriptor.this);
                        if (classifierDescriptorRefineDescriptor == null) {
                            return AbstractClassDescriptor.this.defaultType.invoke();
                        }
                        if (classifierDescriptorRefineDescriptor instanceof TypeAliasDescriptor) {
                            return KotlinTypeFactory.computeExpandedType((TypeAliasDescriptor) classifierDescriptorRefineDescriptor, TypeUtils.getDefaultTypeProjections(classifierDescriptorRefineDescriptor.getTypeConstructor().getParameters()));
                        }
                        if (classifierDescriptorRefineDescriptor instanceof ModuleAwareClassDescriptor) {
                            return TypeUtils.makeUnsubstitutedType(classifierDescriptorRefineDescriptor.getTypeConstructor().refine(kotlinTypeRefiner), ((ModuleAwareClassDescriptor) classifierDescriptorRefineDescriptor).getUnsubstitutedMemberScope(kotlinTypeRefiner), this);
                        }
                        return classifierDescriptorRefineDescriptor.getDefaultType();
                    }
                });
            }
        });
        this.unsubstitutedInnerClassesScope = storageManager.createLazyValue(new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor.2
            @Override // kotlin.jvm.functions.Function0
            public MemberScope invoke() {
                return new InnerClassesScopeWrapper(AbstractClassDescriptor.this.getUnsubstitutedMemberScope());
            }
        });
        this.thisAsReceiverParameter = storageManager.createLazyValue(new Function0<ReceiverParameterDescriptor>() { // from class: org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor.3
            @Override // kotlin.jvm.functions.Function0
            public ReceiverParameterDescriptor invoke() {
                return new LazyClassReceiverParameterDescriptor(AbstractClassDescriptor.this);
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    public Name getName() {
        Name name2 = this.f738name;
        if (name2 == null) {
            $$$reportNull$$$0(2);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getUnsubstitutedInnerClassesScope() {
        MemberScope memberScopeInvoke = this.unsubstitutedInnerClassesScope.invoke();
        if (memberScopeInvoke == null) {
            $$$reportNull$$$0(4);
        }
        return memberScopeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ReceiverParameterDescriptor getThisAsReceiverParameter() {
        ReceiverParameterDescriptor receiverParameterDescriptorInvoke = this.thisAsReceiverParameter.invoke();
        if (receiverParameterDescriptorInvoke == null) {
            $$$reportNull$$$0(5);
        }
        return receiverParameterDescriptorInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ReceiverParameterDescriptor> getContextReceivers() {
        List<ReceiverParameterDescriptor> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(6);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    public MemberScope getMemberScope(List<? extends TypeProjection> list, KotlinTypeRefiner kotlinTypeRefiner) {
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        if (kotlinTypeRefiner == null) {
            $$$reportNull$$$0(8);
        }
        if (!list.isEmpty()) {
            return new SubstitutingScope(getUnsubstitutedMemberScope(kotlinTypeRefiner), TypeConstructorSubstitution.create(getTypeConstructor(), list).buildSubstitutor());
        }
        MemberScope unsubstitutedMemberScope = getUnsubstitutedMemberScope(kotlinTypeRefiner);
        if (unsubstitutedMemberScope == null) {
            $$$reportNull$$$0(9);
        }
        return unsubstitutedMemberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    public MemberScope getMemberScope(TypeSubstitution typeSubstitution, KotlinTypeRefiner kotlinTypeRefiner) {
        if (typeSubstitution == null) {
            $$$reportNull$$$0(10);
        }
        if (kotlinTypeRefiner == null) {
            $$$reportNull$$$0(11);
        }
        if (!typeSubstitution.isEmpty()) {
            return new SubstitutingScope(getUnsubstitutedMemberScope(kotlinTypeRefiner), TypeSubstitutor.create(typeSubstitution));
        }
        MemberScope unsubstitutedMemberScope = getUnsubstitutedMemberScope(kotlinTypeRefiner);
        if (unsubstitutedMemberScope == null) {
            $$$reportNull$$$0(12);
        }
        return unsubstitutedMemberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getMemberScope(List<? extends TypeProjection> list) {
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        MemberScope memberScope = getMemberScope(list, DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
        if (memberScope == null) {
            $$$reportNull$$$0(14);
        }
        return memberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getMemberScope(TypeSubstitution typeSubstitution) {
        if (typeSubstitution == null) {
            $$$reportNull$$$0(15);
        }
        MemberScope memberScope = getMemberScope(typeSubstitution, DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
        if (memberScope == null) {
            $$$reportNull$$$0(16);
        }
        return memberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getUnsubstitutedMemberScope() {
        MemberScope unsubstitutedMemberScope = getUnsubstitutedMemberScope(DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
        if (unsubstitutedMemberScope == null) {
            $$$reportNull$$$0(17);
        }
        return unsubstitutedMemberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public ClassDescriptor substitute(TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(18);
        }
        return typeSubstitutor.isEmpty() ? this : new LazySubstitutingClassDescriptor(this, typeSubstitutor);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        SimpleType simpleTypeInvoke = this.defaultType.invoke();
        if (simpleTypeInvoke == null) {
            $$$reportNull$$$0(20);
        }
        return simpleTypeInvoke;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> declarationDescriptorVisitor) {
        declarationDescriptorVisitor.visitClassDescriptor(this, null);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return declarationDescriptorVisitor.visitClassDescriptor(this, d);
    }
}
