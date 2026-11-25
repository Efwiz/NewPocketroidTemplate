package org.jetbrains.kotlin.load.java.typeEnhancement;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\bJ1\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\u001b\u0010\f\u001a\u0017\u0012\b\u0012\u00060\u000eR\u00020\u0000\u0012\u0004\u0012\u00020\n0\r¢\u0006\u0002\b\u000fH\u0086\bø\u0001\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancementBuilder;", "", "()V", "signatures", "", "", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/PredefinedFunctionEnhancementInfo;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "", "forClass", "", "internalName", "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancementBuilder$ClassEnhancementBuilder;", "Lkotlin/ExtensionFunctionType;", "ClassEnhancementBuilder", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SignatureEnhancementBuilder {
    private final Map<String, PredefinedFunctionEnhancementInfo> signatures = new LinkedHashMap();

    public final void forClass(String internalName, Function1<? super ClassEnhancementBuilder, Unit> block) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(block, "block");
        block.invoke(new ClassEnhancementBuilder(this, internalName));
    }

    /* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J/\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u001f\u0010\n\u001a\u001b\u0012\f\u0012\n0\fR\u00060\u0000R\u00020\r\u0012\u0004\u0012\u00020\b0\u000b¢\u0006\u0002\b\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancementBuilder$ClassEnhancementBuilder;", "", "className", "", "(Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancementBuilder;Ljava/lang/String;)V", "getClassName", "()Ljava/lang/String;", "function", "", "name", "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancementBuilder$ClassEnhancementBuilder$FunctionEnhancementBuilder;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancementBuilder;", "Lkotlin/ExtensionFunctionType;", "FunctionEnhancementBuilder", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ClassEnhancementBuilder {
        private final String className;
        final /* synthetic */ SignatureEnhancementBuilder this$0;

        public ClassEnhancementBuilder(SignatureEnhancementBuilder signatureEnhancementBuilder, String className) {
            Intrinsics.checkNotNullParameter(className, "className");
            this.this$0 = signatureEnhancementBuilder;
            this.className = className;
        }

        public final String getClassName() {
            return this.className;
        }

        public final void function(String name2, Function1<? super FunctionEnhancementBuilder, Unit> block) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(block, "block");
            Map map = this.this$0.signatures;
            FunctionEnhancementBuilder functionEnhancementBuilder = new FunctionEnhancementBuilder(this, name2);
            block.invoke(functionEnhancementBuilder);
            Pair<String, PredefinedFunctionEnhancementInfo> pairBuild = functionEnhancementBuilder.build();
            map.put(pairBuild.getFirst(), pairBuild.getSecond());
        }

        /* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
        @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\r0\tJ?\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032*\u0010\u0011\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\t0\u0012\"\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\t¢\u0006\u0002\u0010\u0015J'\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0012\"\u00020\u0014¢\u0006\u0002\u0010\u0017J?\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032*\u0010\u0011\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\t0\u0012\"\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\t¢\u0006\u0002\u0010\u0015J'\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0012\"\u00020\u0014¢\u0006\u0002\u0010\u0017J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\"\u0010\u0007\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\n0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancementBuilder$ClassEnhancementBuilder$FunctionEnhancementBuilder;", "", "functionName", "", "(Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancementBuilder$ClassEnhancementBuilder;Ljava/lang/String;)V", "getFunctionName", "()Ljava/lang/String;", "parameters", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/TypeEnhancementInfo;", "returnType", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/load/java/typeEnhancement/PredefinedFunctionEnhancementInfo;", "parameter", "", ModuleXmlParser.TYPE, "pairs", "", "", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "(Ljava/lang/String;[Lkotlin/Pair;)V", "qualifiers", "(Ljava/lang/String;[Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;)V", "returns", "Lorg/jetbrains/kotlin/resolve/jvm/JvmPrimitiveType;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public final class FunctionEnhancementBuilder {
            private final String functionName;
            private final List<Pair<String, TypeEnhancementInfo>> parameters;
            private Pair<String, TypeEnhancementInfo> returnType;
            final /* synthetic */ ClassEnhancementBuilder this$0;

            public FunctionEnhancementBuilder(ClassEnhancementBuilder classEnhancementBuilder, String functionName) {
                Intrinsics.checkNotNullParameter(functionName, "functionName");
                this.this$0 = classEnhancementBuilder;
                this.functionName = functionName;
                this.parameters = new ArrayList();
                this.returnType = TuplesKt.to(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, null);
            }

            public final String getFunctionName() {
                return this.functionName;
            }

            public final void parameter(String type, Pair<Integer, JavaTypeQualifiers>... pairs) {
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(pairs, "pairs");
                this.parameters.add(TuplesKt.to(type, pairs.length == 0 ? null : new TypeEnhancementInfo((Pair<Integer, JavaTypeQualifiers>[]) Arrays.copyOf(pairs, pairs.length))));
            }

            public final void parameter(String type, JavaTypeQualifiers... qualifiers) {
                TypeEnhancementInfo typeEnhancementInfo;
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
                List<Pair<String, TypeEnhancementInfo>> list = this.parameters;
                if (qualifiers.length == 0) {
                    typeEnhancementInfo = null;
                } else {
                    Iterable<IndexedValue> iterableWithIndex = ArraysKt.withIndex(qualifiers);
                    LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
                    for (IndexedValue indexedValue : iterableWithIndex) {
                        linkedHashMap.put(Integer.valueOf(indexedValue.getIndex()), (JavaTypeQualifiers) indexedValue.getValue());
                    }
                    typeEnhancementInfo = new TypeEnhancementInfo(linkedHashMap);
                }
                list.add(TuplesKt.to(type, typeEnhancementInfo));
            }

            public final void returns(String type, Pair<Integer, JavaTypeQualifiers>... pairs) {
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(pairs, "pairs");
                this.returnType = TuplesKt.to(type, new TypeEnhancementInfo((Pair<Integer, JavaTypeQualifiers>[]) Arrays.copyOf(pairs, pairs.length)));
            }

            public final void returns(String type, JavaTypeQualifiers... qualifiers) {
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
                Iterable<IndexedValue> iterableWithIndex = ArraysKt.withIndex(qualifiers);
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
                for (IndexedValue indexedValue : iterableWithIndex) {
                    linkedHashMap.put(Integer.valueOf(indexedValue.getIndex()), (JavaTypeQualifiers) indexedValue.getValue());
                }
                this.returnType = TuplesKt.to(type, new TypeEnhancementInfo(linkedHashMap));
            }

            public final void returns(JvmPrimitiveType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                String desc = type.getDesc();
                Intrinsics.checkNotNullExpressionValue(desc, "getDesc(...)");
                this.returnType = TuplesKt.to(desc, null);
            }

            public final Pair<String, PredefinedFunctionEnhancementInfo> build() {
                SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
                String className = this.this$0.getClassName();
                String str = this.functionName;
                List<Pair<String, TypeEnhancementInfo>> list = this.parameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add((String) ((Pair) it.next()).getFirst());
                }
                String strSignature = signatureBuildingComponents.signature(className, signatureBuildingComponents.jvmDescriptor(str, arrayList, this.returnType.getFirst()));
                TypeEnhancementInfo second = this.returnType.getSecond();
                List<Pair<String, TypeEnhancementInfo>> list2 = this.parameters;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add((TypeEnhancementInfo) ((Pair) it2.next()).getSecond());
                }
                return TuplesKt.to(strSignature, new PredefinedFunctionEnhancementInfo(second, arrayList2));
            }
        }
    }

    public final Map<String, PredefinedFunctionEnhancementInfo> build() {
        return this.signatures;
    }
}
