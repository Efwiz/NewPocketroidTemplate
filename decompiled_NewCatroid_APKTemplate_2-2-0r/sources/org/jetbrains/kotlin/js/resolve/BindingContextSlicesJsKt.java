package org.jetbrains.kotlin.js.resolve;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice;
import org.jetbrains.kotlin.util.slicedMap.RewritePolicy;

/* JADX INFO: compiled from: bindingContextSlicesJs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\"$\u0010\u0000\u001a\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u001c\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"LEXICAL_SCOPE_FOR_JS", "Lorg/jetbrains/kotlin/util/slicedMap/BasicWritableSlice;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "MODULE_KIND", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BindingContextSlicesJsKt {
    public static final BasicWritableSlice<ResolvedCall<? extends FunctionDescriptor>, LexicalScope> LEXICAL_SCOPE_FOR_JS;
    public static final BasicWritableSlice<ModuleDescriptor, ModuleKind> MODULE_KIND;

    static {
        BasicWritableSlice<ModuleDescriptor, ModuleKind> basicWritableSlice = new BasicWritableSlice<>(RewritePolicy.DO_NOTHING);
        basicWritableSlice.setDebugName("MODULE_KIND");
        MODULE_KIND = basicWritableSlice;
        BasicWritableSlice<ResolvedCall<? extends FunctionDescriptor>, LexicalScope> basicWritableSlice2 = new BasicWritableSlice<>(RewritePolicy.DO_NOTHING);
        basicWritableSlice2.setDebugName("LEXICAL_SCOPE_FOR_JS");
        LEXICAL_SCOPE_FOR_JS = basicWritableSlice2;
    }
}
