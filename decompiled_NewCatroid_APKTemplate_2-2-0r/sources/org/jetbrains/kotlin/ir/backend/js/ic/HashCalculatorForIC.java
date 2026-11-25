package org.jetbrains.kotlin.ir.backend.js.ic;

import android.Manifest;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.nio.LongBuffer;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.Charsets;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeVisitor;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.library.impl.IrFileReadersKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: HashCalculatorForIC.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0006\u001a\u00020\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\b\u0010\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000eJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000fJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014J>\u0010\u0015\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0014\u0010\u0019\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00160\u001b0\u001a2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u000b0\u001dJ4\u0010\u001e\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u00162\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00160 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u000b0\u001dH\u0086\bø\u0001\u0002J\u000e\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020$R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0012\n\u0002\b!\n\u0005\b¡\u001e0\u0001\n\u0005\b\u009920\u0001¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/HashCalculatorForIC;", "", "()V", "md5Digest", "Ljava/security/MessageDigest;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "finalizeAndGetHash", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "finalizeAndGetHash-Yd-dAqs", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", ConfigConstants.CONFIG_KEY_UPDATE, "", "data", "", "", "", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "updateAnnotationContainer", "annotationContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "updateConfigKeys", ExifInterface.GPS_DIRECTION_TRUE, "config", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", UserMetadata.KEYDATA_FILENAME, "", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "valueUpdater", "Lkotlin/Function1;", "updateForEach", "collection", "", "f", "updateSymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class HashCalculatorForIC {
    private final MessageDigest md5Digest = MessageDigest.getInstance("MD5");

    public final void update(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.md5Digest.update(data);
    }

    public final void update(int data) {
        Iterator<Integer> it = new IntRange(0, 3).iterator();
        while (it.hasNext()) {
            this.md5Digest.update((byte) (data >> (((IntIterator) it).nextInt() * 8)));
        }
    }

    public final void update(String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        update(data.length());
        byte[] bytes = data.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        update(bytes);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void update(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        irElement.accept(new DumpIrTreeVisitor(new Appendable() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.HashCalculatorForIC.update.2
            @Override // java.lang.Appendable
            public AnonymousClass2 append(CharSequence csq) {
                Intrinsics.checkNotNullParameter(csq, "csq");
                HashCalculatorForIC hashCalculatorForIC = HashCalculatorForIC.this;
                byte[] bytes = csq.toString().getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                hashCalculatorForIC.update(bytes);
                return this;
            }

            @Override // java.lang.Appendable
            public AnonymousClass2 append(CharSequence csq, int start, int end) {
                Intrinsics.checkNotNullParameter(csq, "csq");
                return append(csq.subSequence(start, end));
            }

            @Override // java.lang.Appendable
            public AnonymousClass2 append(char c) {
                return append((CharSequence) String.valueOf(c));
            }
        }, null, 2, 0 == true ? 1 : 0), "");
    }

    public final void updateAnnotationContainer(IrAnnotationContainer annotationContainer) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        List<IrConstructorCall> annotations = annotationContainer.getAnnotations();
        update(annotations.size());
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            update((IrElement) it.next());
        }
    }

    public final void updateSymbol(IrSymbol symbol) {
        IrField backingField;
        IrExpressionBody initializer;
        IrSimpleFunctionSymbol symbol2;
        IrSimpleFunctionSymbol symbol3;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        update(symbol.toString());
        IrSymbolOwner owner = symbol.getOwner();
        IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
        if (irClass != null) {
            if (!IrUtilsKt.isInterface(irClass)) {
                irClass = null;
            }
            if (irClass != null) {
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
                    if ((irDeclaration instanceof IrOverridableMember) && ((IrOverridableMember) irDeclaration).getModality() == Modality.OPEN) {
                        listCreateListBuilder.add(irDeclaration.getSymbol());
                    }
                    if (irDeclaration instanceof IrProperty) {
                        IrProperty irProperty = (IrProperty) irDeclaration;
                        IrSimpleFunction getter = irProperty.getGetter();
                        if (getter != null) {
                            if (getter.getModality() != Modality.OPEN) {
                                getter = null;
                            }
                            if (getter != null && (symbol3 = getter.getSymbol()) != null) {
                                listCreateListBuilder.add(symbol3);
                            }
                        }
                        IrSimpleFunction setter = irProperty.getSetter();
                        if (setter != null) {
                            if (setter.getModality() != Modality.OPEN) {
                                setter = null;
                            }
                            if (setter != null && (symbol2 = setter.getSymbol()) != null) {
                                listCreateListBuilder.add(symbol2);
                            }
                        }
                    }
                }
                List listBuild = CollectionsKt.build(listCreateListBuilder);
                update(listBuild.size());
                Iterator it = listBuild.iterator();
                while (it.hasNext()) {
                    updateSymbol((IrSymbol) it.next());
                }
            }
        }
        IrSymbolOwner owner2 = symbol.getOwner();
        IrTypeParametersContainer irTypeParametersContainer = owner2 instanceof IrTypeParametersContainer ? (IrTypeParametersContainer) owner2 : null;
        if (irTypeParametersContainer != null) {
            List<IrTypeParameter> typeParameters = irTypeParametersContainer.getTypeParameters();
            update(typeParameters.size());
            Iterator<T> it2 = typeParameters.iterator();
            while (it2.hasNext()) {
                update(((IrTypeParameter) it2.next()).getSymbol().toString());
            }
        }
        IrSymbolOwner owner3 = symbol.getOwner();
        IrFunction irFunction = owner3 instanceof IrFunction ? (IrFunction) owner3 : null;
        if (irFunction != null) {
            List<IrValueParameter> valueParameters = irFunction.getValueParameters();
            update(valueParameters.size());
            Iterator<T> it3 = valueParameters.iterator();
            while (it3.hasNext()) {
                update(((IrValueParameter) it3.next()).getDefaultValue() != null ? ((Number) 1).intValue() : 0);
            }
        }
        IrSymbolOwner owner4 = symbol.getOwner();
        IrAnnotationContainer irAnnotationContainer = owner4 instanceof IrAnnotationContainer ? (IrAnnotationContainer) owner4 : null;
        if (irAnnotationContainer != null) {
            updateAnnotationContainer(irAnnotationContainer);
        }
        IrSymbolOwner owner5 = symbol.getOwner();
        IrProperty irProperty2 = owner5 instanceof IrProperty ? (IrProperty) owner5 : null;
        if (irProperty2 == null || !irProperty2.getIsConst() || (backingField = irProperty2.getBackingField()) == null || (initializer = backingField.getInitializer()) == null) {
            return;
        }
        update(initializer);
    }

    public final <T> void updateForEach(Collection<? extends T> collection, Function1<? super T, Unit> f) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        Intrinsics.checkNotNullParameter(f, "f");
        update(collection.size());
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            f.invoke(it.next());
        }
    }

    /* JADX INFO: renamed from: finalizeAndGetHash-Yd-dAqs, reason: not valid java name */
    public final Hash128Bits m7106finalizeAndGetHashYddAqs() {
        byte[] bArrDigest = this.md5Digest.digest();
        this.md5Digest.reset();
        Intrinsics.checkNotNull(bArrDigest);
        LongBuffer longBufferAsLongBuffer = IrFileReadersKt.getBuffer(bArrDigest).asLongBuffer();
        return ICHash.m7108constructorimpl(new Hash128Bits(ULong.m3072constructorimpl(longBufferAsLongBuffer.get(0)), ULong.m3072constructorimpl(longBufferAsLongBuffer.get(1)), null));
    }

    public final <T> void updateConfigKeys(CompilerConfiguration config, List<? extends CompilerConfigurationKey<? extends T>> keys, Function1<? super T, Unit> valueUpdater) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(keys, "keys");
        Intrinsics.checkNotNullParameter(valueUpdater, "valueUpdater");
        List<? extends CompilerConfigurationKey<? extends T>> list = keys;
        update(list.size());
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CompilerConfigurationKey<T> compilerConfigurationKey = (CompilerConfigurationKey) it.next();
            String string = compilerConfigurationKey.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            update(string);
            Manifest manifest = (Object) config.get(compilerConfigurationKey);
            if (manifest == null) {
                this.md5Digest.update((byte) 0);
            } else {
                this.md5Digest.update((byte) 1);
                valueUpdater.invoke(manifest);
            }
        }
    }
}
