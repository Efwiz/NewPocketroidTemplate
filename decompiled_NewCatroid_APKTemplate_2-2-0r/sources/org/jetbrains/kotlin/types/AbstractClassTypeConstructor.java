package org.jetbrains.kotlin.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: loaded from: classes6.dex */
public abstract class AbstractClassTypeConstructor extends AbstractTypeConstructor implements TypeConstructor {
    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/types/AbstractClassTypeConstructor";
        } else if (i == 2) {
            objArr[0] = "classifier";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/types/AbstractClassTypeConstructor";
        } else {
            objArr[0] = "storageManager";
        }
        if (i == 1) {
            objArr[1] = "getBuiltIns";
        } else if (i == 3 || i == 4) {
            objArr[1] = "getAdditionalNeighboursInSupertypeGraph";
        } else {
            objArr[1] = "org/jetbrains/kotlin/types/AbstractClassTypeConstructor";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "isSameClassifier";
            } else if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public abstract ClassDescriptor getThis$0();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClassTypeConstructor(StorageManager storageManager) {
        super(storageManager);
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public final boolean isFinal() {
        ClassDescriptor this$0 = getThis$0();
        return ModalityUtilsKt.isFinalClass(this$0) && !this$0.isExpect();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public KotlinBuiltIns getBuiltIns() {
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(getThis$0());
        if (builtIns == null) {
            $$$reportNull$$$0(1);
        }
        return builtIns;
    }

    @Override // org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor
    protected boolean isSameClassifier(ClassifierDescriptor classifierDescriptor) {
        if (classifierDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        return (classifierDescriptor instanceof ClassDescriptor) && areFqNamesEqual(getThis$0(), classifierDescriptor);
    }

    @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
    protected Collection<KotlinType> getAdditionalNeighboursInSupertypeGraph(boolean z) {
        DeclarationDescriptor packageFragment = getThis$0().getPackageFragment();
        if (!(packageFragment instanceof ClassDescriptor)) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(3);
            }
            return listEmptyList;
        }
        SmartList smartList = new SmartList();
        ClassDescriptor classDescriptor = (ClassDescriptor) packageFragment;
        smartList.add(classDescriptor.getDefaultType());
        ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = classDescriptor.mo6346getCompanionObjectDescriptor();
        if (z && classDescriptorMo6346getCompanionObjectDescriptor != null) {
            smartList.add(classDescriptorMo6346getCompanionObjectDescriptor.getDefaultType());
        }
        return smartList;
    }

    @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
    protected KotlinType defaultSupertypeIfEmpty() {
        if (KotlinBuiltIns.isSpecialClassWithNoSupertypes(getThis$0())) {
            return null;
        }
        return getBuiltIns().getAnyType();
    }
}
