package org.jetbrains.kotlin.ir.descriptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.LazyScopeAdapter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.TypeIntersectionScope;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.AbstractTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J7\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u0013\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\u00192\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0015H\u0016J\b\u0010\u001b\u001a\u00020\u0007H\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0000H\u0016J\u0010\u0010!\u001a\n #*\u0004\u0018\u00010\"0\"H\u0016J\u0010\u0010$\u001a\n #*\u0004\u0018\u00010%0%H\u0016J\b\u0010&\u001a\u00020\rH\u0016J\u000e\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(H\u0016J\b\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020-H\u0016J\b\u0010.\u001a\u00020-H\u0016J\b\u0010/\u001a\u000200H\u0016R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000f¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedTypeParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;)V", "_defaultType", "Lorg/jetbrains/kotlin/types/SimpleType;", "get_defaultType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "_defaultType$delegate", "Lkotlin/Lazy;", "_typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "get_typeConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "_typeConstructor$delegate", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getDefaultType", "getIndex", "", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getStorageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getTypeConstructor", "getUpperBounds", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "getVariance", "Lorg/jetbrains/kotlin/types/Variance;", "isCapturedFromOuterDeclaration", "", "isReified", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedTypeParameterDescriptor extends IrBasedDeclarationDescriptor<IrTypeParameter> implements TypeParameterDescriptor {

    /* JADX INFO: renamed from: _defaultType$delegate, reason: from kotlin metadata */
    private final Lazy _defaultType;

    /* JADX INFO: renamed from: _typeConstructor$delegate, reason: from kotlin metadata */
    private final Lazy _typeConstructor;

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedTypeParameterDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public boolean isCapturedFromOuterDeclaration() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedTypeParameterDescriptor(IrTypeParameter owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
        this._typeConstructor = LazyKt.lazy(new Function0<IrBasedTypeParameterDescriptor$_typeConstructor$2.AnonymousClass1>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedTypeParameterDescriptor$_typeConstructor$2
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r1v0, types: [org.jetbrains.kotlin.ir.descriptors.IrBasedTypeParameterDescriptor$_typeConstructor$2$1] */
            @Override // kotlin.jvm.functions.Function0
            public final AnonymousClass1 invoke() {
                return new AbstractTypeConstructor(LockBasedStorageManager.NO_LOCKS) { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedTypeParameterDescriptor$_typeConstructor$2.1
                    private final SupertypeLoopChecker.EMPTY supertypeLoopChecker;

                    @Override // org.jetbrains.kotlin.types.TypeConstructor
                    public boolean isDenotable() {
                        return true;
                    }

                    @Override // org.jetbrains.kotlin.types.TypeConstructor
                    public boolean isFinal() {
                        return false;
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(storageManager);
                        Intrinsics.checkNotNull(storageManager);
                        this.supertypeLoopChecker = SupertypeLoopChecker.EMPTY.INSTANCE;
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
                    public List<KotlinType> computeSupertypes() {
                        return this.this$0.getUpperBounds();
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
                    public SupertypeLoopChecker.EMPTY getSupertypeLoopChecker() {
                        return this.supertypeLoopChecker;
                    }

                    @Override // org.jetbrains.kotlin.types.TypeConstructor
                    public List<TypeParameterDescriptor> getParameters() {
                        return CollectionsKt.emptyList();
                    }

                    @Override // org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
                    /* JADX INFO: renamed from: getDeclarationDescriptor, reason: from getter */
                    public IrBasedTypeParameterDescriptor getThis$0() {
                        return this.this$0;
                    }

                    @Override // org.jetbrains.kotlin.types.TypeConstructor
                    /* JADX INFO: renamed from: getBuiltIns */
                    public KotlinBuiltIns getKotlinBuiltIns() {
                        return DescriptorUtilsKt.getModule(this.this$0).getBuiltIns();
                    }

                    @Override // org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor
                    protected boolean isSameClassifier(ClassifierDescriptor classifier) {
                        Intrinsics.checkNotNullParameter(classifier, "classifier");
                        return getThis$0() == classifier;
                    }
                };
            }
        });
        this._defaultType = LazyKt.lazy(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedTypeParameterDescriptor$_defaultType$2
            {
                super(0);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                TypeAttributes empty = TypeAttributes.INSTANCE.getEmpty();
                TypeConstructor typeConstructor = this.this$0.getTypeConstructor();
                List listEmptyList = CollectionsKt.emptyList();
                final IrBasedTypeParameterDescriptor irBasedTypeParameterDescriptor = this.this$0;
                return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(empty, typeConstructor, listEmptyList, false, new LazyScopeAdapter(null, new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedTypeParameterDescriptor$_defaultType$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final MemberScope invoke() {
                        return TypeIntersectionScope.INSTANCE.create("Scope for type parameter " + irBasedTypeParameterDescriptor.getName().asString(), irBasedTypeParameterDescriptor.getUpperBounds());
                    }
                }, 1, 0 == true ? 1 : 0));
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    public Name getName() {
        return getOwner().getName();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public boolean isReified() {
        return getOwner().isReified();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public Variance getVariance() {
        return getOwner().getVariance();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public List<KotlinType> getUpperBounds() {
        List<IrType> superTypes = getOwner().getSuperTypes();
        ArrayList arrayList = new ArrayList(superTypes.size());
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(toIrBasedKotlinType((IrType) it.next()));
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
    }

    private final TypeConstructor get_typeConstructor() {
        return (TypeConstructor) this._typeConstructor.getValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return get_typeConstructor();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    public SourceElement getSource() {
        return SourceElement.NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public int getIndex() {
        return getOwner().getIndex();
    }

    private final SimpleType get_defaultType() {
        return (SimpleType) this._defaultType.getValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        return get_defaultType();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public StorageManager getStorageManager() {
        return LockBasedStorageManager.NO_LOCKS;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNull(visitor);
        return visitor.visitTypeParameterDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNull(visitor);
        visitor.visitTypeParameterDescriptor(this, null);
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedDeclarationDescriptor
    public String toString() {
        return super.toString() + "\nParent: " + getModuleDescriptor();
    }
}
