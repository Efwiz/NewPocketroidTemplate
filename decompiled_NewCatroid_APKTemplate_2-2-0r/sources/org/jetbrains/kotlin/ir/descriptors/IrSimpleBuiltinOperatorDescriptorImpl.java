package org.jetbrains.kotlin.ir.descriptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrBuiltinFunctionDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\b\u0010\u0014\u001a\u00020\bH\u0016J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrSimpleBuiltinOperatorDescriptorImpl;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinOperatorDescriptorBase;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinOperatorDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "returnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/types/KotlinType;)V", "valueParameters", "", "Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinValueParameterDescriptor;", "addValueParameter", "", "valueParameter", Namer.EQUALS_METHOD_NAME, "", "other", "", "getReturnType", "getValueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSimpleBuiltinOperatorDescriptorImpl extends IrBuiltinOperatorDescriptorBase implements IrBuiltinOperatorDescriptor {
    private final KotlinType returnType;
    private final List<IrBuiltinValueParameterDescriptor> valueParameters;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrSimpleBuiltinOperatorDescriptorImpl(DeclarationDescriptor containingDeclaration, Name name2, KotlinType returnType) {
        super(containingDeclaration, name2);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        this.returnType = returnType;
        this.valueParameters = new ArrayList();
    }

    public final void addValueParameter(IrBuiltinValueParameterDescriptor valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        this.valueParameters.add(valueParameter);
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return this.returnType;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        return this.valueParameters;
    }

    public boolean equals(Object other) {
        if (this != other) {
            if (other instanceof IrSimpleBuiltinOperatorDescriptorImpl) {
                IrSimpleBuiltinOperatorDescriptorImpl irSimpleBuiltinOperatorDescriptorImpl = (IrSimpleBuiltinOperatorDescriptorImpl) other;
                if (Intrinsics.areEqual(getShortName(), irSimpleBuiltinOperatorDescriptorImpl.getShortName())) {
                    List<IrBuiltinValueParameterDescriptor> list = this.valueParameters;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((IrBuiltinValueParameterDescriptor) it.next()).getType());
                    }
                    ArrayList arrayList2 = arrayList;
                    List<IrBuiltinValueParameterDescriptor> list2 = irSimpleBuiltinOperatorDescriptorImpl.valueParameters;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator<T> it2 = list2.iterator();
                    while (it2.hasNext()) {
                        arrayList3.add(((IrBuiltinValueParameterDescriptor) it2.next()).getType());
                    }
                    if (!Intrinsics.areEqual(arrayList2, arrayList3) || !Intrinsics.areEqual(getPackageFragment(), irSimpleBuiltinOperatorDescriptorImpl.getPackageFragment())) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        int iHashCode = ((getPackageFragment().hashCode() * 31) + getShortName().hashCode()) * 31;
        List<IrBuiltinValueParameterDescriptor> list = this.valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrBuiltinValueParameterDescriptor) it.next()).getType());
        }
        return iHashCode + arrayList.hashCode();
    }
}
