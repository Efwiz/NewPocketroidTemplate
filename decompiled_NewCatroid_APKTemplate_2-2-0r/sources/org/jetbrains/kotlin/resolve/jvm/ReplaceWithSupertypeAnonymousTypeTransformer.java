package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.resolve.DeclarationSignatureAnonymousTypeTransformer;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ReplaceWithSupertypeAnonymousTypeTransformer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/ReplaceWithSupertypeAnonymousTypeTransformer;", "Lorg/jetbrains/kotlin/resolve/DeclarationSignatureAnonymousTypeTransformer;", "()V", "transformAnonymousType", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", ModuleXmlParser.TYPE, "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ReplaceWithSupertypeAnonymousTypeTransformer implements DeclarationSignatureAnonymousTypeTransformer {
    @Override // org.jetbrains.kotlin.resolve.DeclarationSignatureAnonymousTypeTransformer
    public KotlinType transformAnonymousType(DeclarationDescriptorWithVisibility descriptor, KotlinType type) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(type, "type");
        return !DescriptorUtils.isLocal(descriptor) ? ReplaceWithSupertypeAnonymousTypeTransformerKt.replaceAnonymousTypeWithSuperType(type) : type;
    }
}
