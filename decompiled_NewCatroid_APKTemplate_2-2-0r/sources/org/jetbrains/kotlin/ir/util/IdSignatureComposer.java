package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;

/* JADX INFO: compiled from: IdSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\fH&J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u000eH&J\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u0014H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "getMangler", "()Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "composeAnonInitSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "composeEnumEntrySignature", "composeFieldSignature", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "composeSignature", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "withFileSignature", "", "fileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "body", "Lkotlin/Function0;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IdSignatureComposer {
    IdSignature composeAnonInitSignature(ClassDescriptor descriptor);

    IdSignature composeEnumEntrySignature(ClassDescriptor descriptor);

    IdSignature composeFieldSignature(PropertyDescriptor descriptor);

    IdSignature composeSignature(DeclarationDescriptor descriptor);

    KotlinMangler.DescriptorMangler getMangler();

    void withFileSignature(IdSignature.FileSignature fileSignature, Function0<Unit> body);
}
