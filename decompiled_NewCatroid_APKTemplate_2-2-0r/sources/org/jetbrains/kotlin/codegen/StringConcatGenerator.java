package org.jetbrains.kotlin.codegen;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.google.common.collect.Sets;
import org.jetbrains.kotlin.config.JvmStringConcat;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: StringConcatGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 (2\u00020\u0001:\u0003()*B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J$\u0010\u0017\u001a\u00060\u0018j\u0002`\u00192\u0016\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\nH\u0002J&\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\n2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\u001cH\u0002J\u0012\u0010\u001d\u001a\u00020\u00142\b\b\u0002\u0010\u001e\u001a\u00020\fH\u0007J\u0006\u0010\u001f\u001a\u00020\u0014J\u000e\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"J&\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020%2\b\b\u0002\u0010!\u001a\u00020\"2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'H\u0007R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/codegen/StringConcatGenerator;", "", "mode", "Lorg/jetbrains/kotlin/config/JvmStringConcat;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "(Lorg/jetbrains/kotlin/config/JvmStringConcat;Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;)V", FirebaseAnalytics.Param.ITEMS, "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$Item;", "Lkotlin/collections/ArrayList;", "justFlushed", "", "getMode", "()Lorg/jetbrains/kotlin/config/JvmStringConcat;", "getMv", "()Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "paramSlots", "", "addStringConstant", "", "value", "", "buildRecipe", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "itemForGeneration", "fitRestrictions", "", "genStringBuilderConstructorIfNeded", "swap", "genToString", "invokeAppend", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "putValueOrProcessConstant", "stackValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Companion", "Item", "ItemType", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringConcatGenerator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<Type> STRING_BUILDER_OBJECT_APPEND_ARG_TYPES;
    private final ArrayList<Item> items;
    private boolean justFlushed;
    private final JvmStringConcat mode;
    private final InstructionAdapter mv;
    private int paramSlots;

    /* JADX INFO: compiled from: StringConcatGenerator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$ItemType;", "", "(Ljava/lang/String;I)V", "PARAMETER", "CONSTANT", "INLINED_CONSTANT", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ItemType {
        PARAMETER,
        CONSTANT,
        INLINED_CONSTANT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ItemType> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: StringConcatGenerator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ItemType.values().length];
            try {
                iArr[ItemType.PARAMETER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ItemType.CONSTANT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ItemType.INLINED_CONSTANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final void genStringBuilderConstructorIfNeded() {
        genStringBuilderConstructorIfNeded$default(this, false, 1, null);
    }

    public final void putValueOrProcessConstant(StackValue stackValue) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        putValueOrProcessConstant$default(this, stackValue, null, null, 6, null);
    }

    public final void putValueOrProcessConstant(StackValue stackValue, Type type) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(type, "type");
        putValueOrProcessConstant$default(this, stackValue, type, null, 4, null);
    }

    public StringConcatGenerator(JvmStringConcat mode, InstructionAdapter mv) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(mv, "mv");
        this.mode = mode;
        this.mv = mv;
        this.items = new ArrayList<>();
    }

    public final JvmStringConcat getMode() {
        return this.mode;
    }

    public final InstructionAdapter getMv() {
        return this.mv;
    }

    /* JADX INFO: compiled from: StringConcatGenerator.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\b\u0018\u0000 !2\u00020\u0001:\u0001!B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÆ\u0003J'\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0006\u0010\u001e\u001a\u00020\u001cJ\t\u0010\u001f\u001a\u00020\nHÖ\u0001J\t\u0010 \u001a\u00020\u0007HÖ\u0001R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$Item;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "itemType", "Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$ItemType;", "value", "", "(Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$ItemType;Ljava/lang/String;)V", "encodedUTF8Size", "", "getEncodedUTF8Size", "()I", "encodedUTF8Size$delegate", "Lkotlin/Lazy;", "getItemType", "()Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$ItemType;", "setItemType", "(Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$ItemType;)V", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getValue", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "fitEncodingLimit", "hashCode", "toString", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Item {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        /* JADX INFO: renamed from: encodedUTF8Size$delegate, reason: from kotlin metadata */
        private final Lazy encodedUTF8Size;
        private ItemType itemType;
        private final Type type;
        private final String value;

        public static /* synthetic */ Item copy$default(Item item, Type type, ItemType itemType, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                type = item.type;
            }
            if ((i & 2) != 0) {
                itemType = item.itemType;
            }
            if ((i & 4) != 0) {
                str = item.value;
            }
            return item.copy(type, itemType, str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Type getType() {
            return this.type;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ItemType getItemType() {
            return this.itemType;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getValue() {
            return this.value;
        }

        public final Item copy(Type type, ItemType itemType, String value) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(itemType, "itemType");
            Intrinsics.checkNotNullParameter(value, "value");
            return new Item(type, itemType, value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Item)) {
                return false;
            }
            Item item = (Item) other;
            return Intrinsics.areEqual(this.type, item.type) && this.itemType == item.itemType && Intrinsics.areEqual(this.value, item.value);
        }

        public int hashCode() {
            return (((this.type.hashCode() * 31) + this.itemType.hashCode()) * 31) + this.value.hashCode();
        }

        public String toString() {
            return "Item(type=" + this.type + ", itemType=" + this.itemType + ", value=" + this.value + ')';
        }

        /* JADX INFO: compiled from: StringConcatGenerator.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$Item$Companion;", "", "()V", "constant", "Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$Item;", "value", "", "inlinedConstant", "parameter", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Item inlinedConstant(String value) {
                Intrinsics.checkNotNullParameter(value, "value");
                Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
                Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
                return new Item(JAVA_STRING_TYPE, ItemType.INLINED_CONSTANT, value);
            }

            public final Item constant(String value) {
                Intrinsics.checkNotNullParameter(value, "value");
                Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
                Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
                return new Item(JAVA_STRING_TYPE, ItemType.CONSTANT, value);
            }

            public final Item parameter(Type type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return new Item(type, ItemType.PARAMETER, "\u0001");
            }
        }

        public Item(Type type, ItemType itemType, String value) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(itemType, "itemType");
            Intrinsics.checkNotNullParameter(value, "value");
            this.type = type;
            this.itemType = itemType;
            this.value = value;
            this.encodedUTF8Size = LazyKt.lazy(new Function0<Integer>() { // from class: org.jetbrains.kotlin.codegen.StringConcatGenerator$Item$encodedUTF8Size$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Integer invoke() {
                    return Integer.valueOf(CodegenUtilKt.encodedUTF8Size(this.this$0.getValue()));
                }
            });
        }

        public final ItemType getItemType() {
            return this.itemType;
        }

        public final Type getType() {
            return this.type;
        }

        public final String getValue() {
            return this.value;
        }

        public final void setItemType(ItemType itemType) {
            Intrinsics.checkNotNullParameter(itemType, "<set-?>");
            this.itemType = itemType;
        }

        public final int getEncodedUTF8Size() {
            return ((Number) this.encodedUTF8Size.getValue()).intValue();
        }

        public final boolean fitEncodingLimit() {
            return CodegenUtilKt.isDefinitelyFitEncodingLimit(this.value) || getEncodedUTF8Size() <= 65535;
        }
    }

    public static /* synthetic */ void genStringBuilderConstructorIfNeded$default(StringConcatGenerator stringConcatGenerator, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        stringConcatGenerator.genStringBuilderConstructorIfNeded(z);
    }

    public final void genStringBuilderConstructorIfNeded(boolean swap) {
        if (this.mode.isDynamic()) {
            return;
        }
        this.mv.visitTypeInsn(187, "java/lang/StringBuilder");
        this.mv.dup();
        this.mv.invokespecial("java/lang/StringBuilder", "<init>", "()V", false);
        if (swap) {
            this.mv.swap();
        }
    }

    public static /* synthetic */ void putValueOrProcessConstant$default(StringConcatGenerator stringConcatGenerator, StackValue stackValue, Type type, KotlinType kotlinType, int i, Object obj) {
        if ((i & 2) != 0) {
            type = stackValue.type;
            Intrinsics.checkNotNullExpressionValue(type, "type");
        }
        if ((i & 4) != 0) {
            kotlinType = stackValue.kotlinType;
        }
        stringConcatGenerator.putValueOrProcessConstant(stackValue, type, kotlinType);
    }

    public final void putValueOrProcessConstant(StackValue stackValue, Type type, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(type, "type");
        this.justFlushed = false;
        if (this.mode == JvmStringConcat.INDY_WITH_CONSTANTS) {
            if (stackValue instanceof StackValue.Constant) {
                Object obj = ((StackValue.Constant) stackValue).value;
                if (obj instanceof String) {
                    CharSequence charSequence = (CharSequence) obj;
                    if (StringsKt.contains$default(charSequence, (CharSequence) "\u0001", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) "\u0002", false, 2, (Object) null)) {
                        this.items.add(Item.INSTANCE.constant((String) obj));
                        return;
                    }
                }
                if ((obj instanceof Character) && (Intrinsics.areEqual(obj, (Object) (char) 1) || Intrinsics.areEqual(obj, (Object) (char) 2))) {
                    this.items.add(Item.INSTANCE.constant(String.valueOf(((Character) obj).charValue())));
                    return;
                } else {
                    this.items.add(Item.INSTANCE.inlinedConstant(String.valueOf(obj)));
                    return;
                }
            }
            if (Intrinsics.areEqual(stackValue, BranchedValue.INSTANCE.getTRUE())) {
                this.items.add(Item.INSTANCE.inlinedConstant("true"));
                return;
            } else if (Intrinsics.areEqual(stackValue, BranchedValue.INSTANCE.getFALSE())) {
                this.items.add(Item.INSTANCE.inlinedConstant("false"));
                return;
            }
        }
        stackValue.put(type, kotlinType, this.mv);
        invokeAppend(type);
    }

    public final void addStringConstant(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        StackValue stackValueConstant = StackValue.constant(value, AsmTypes.JAVA_STRING_TYPE, null);
        Intrinsics.checkNotNullExpressionValue(stackValueConstant, "constant(...)");
        putValueOrProcessConstant$default(this, stackValueConstant, null, null, 6, null);
    }

    public final void invokeAppend(Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!this.mode.isDynamic()) {
            this.mv.invokevirtual("java/lang/StringBuilder", "append", "(" + INSTANCE.stringBuilderAppendType(type) + ")Ljava/lang/StringBuilder;", false);
            return;
        }
        this.justFlushed = false;
        this.items.add(Item.INSTANCE.parameter(type));
        int size = this.paramSlots + type.getSize();
        this.paramSlots = size;
        if (size >= 199) {
            genToString();
            this.justFlushed = true;
        }
    }

    public final void genToString() {
        if (!this.mode.isDynamic()) {
            this.mv.invokevirtual("java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            return;
        }
        if (this.justFlushed) {
            return;
        }
        if (this.mode == JvmStringConcat.INDY_WITH_CONSTANTS) {
            Handle handle = new Handle(6, "java/lang/invoke/StringConcatFactory", "makeConcatWithConstants", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;", false);
            ArrayList<Item> arrayListFitRestrictions = fitRestrictions(this.items);
            StringBuilder sbBuildRecipe = buildRecipe(arrayListFitRestrictions);
            ArrayList<Item> arrayList = arrayListFitRestrictions;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                if (((Item) obj).getItemType() == ItemType.CONSTANT) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = arrayList2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                arrayList4.add(((Item) it.next()).getValue());
            }
            ArrayList arrayList5 = arrayList4;
            InstructionAdapter instructionAdapter = this.mv;
            Type type = AsmTypes.JAVA_STRING_TYPE;
            ArrayList arrayList6 = new ArrayList();
            for (Object obj2 : arrayList) {
                if (((Item) obj2).getItemType() == ItemType.PARAMETER) {
                    arrayList6.add(obj2);
                }
            }
            ArrayList arrayList7 = arrayList6;
            ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList7, 10));
            Iterator it2 = arrayList7.iterator();
            while (it2.hasNext()) {
                arrayList8.add(((Item) it2.next()).getType());
            }
            Type[] typeArr = (Type[]) arrayList8.toArray(new Type[0]);
            instructionAdapter.invokedynamic("makeConcatWithConstants", Type.getMethodDescriptor(type, (Type[]) Arrays.copyOf(typeArr, typeArr.length)), handle, ArraysKt.plus((Object[]) new String[]{sbBuildRecipe.toString()}, (Collection) arrayList5));
        } else {
            Handle handle2 = new Handle(6, "java/lang/invoke/StringConcatFactory", "makeConcat", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false);
            ArrayList<Item> arrayList9 = this.items;
            if (!(arrayList9 instanceof Collection) || !arrayList9.isEmpty()) {
                Iterator<T> it3 = arrayList9.iterator();
                while (it3.hasNext() && ((Item) it3.next()).getItemType() == ItemType.PARAMETER) {
                }
            }
            InstructionAdapter instructionAdapter2 = this.mv;
            Type type2 = AsmTypes.JAVA_STRING_TYPE;
            ArrayList<Item> arrayList10 = this.items;
            ArrayList arrayList11 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList10, 10));
            Iterator<T> it4 = arrayList10.iterator();
            while (it4.hasNext()) {
                arrayList11.add(((Item) it4.next()).getType());
            }
            Type[] typeArr2 = (Type[]) arrayList11.toArray(new Type[0]);
            instructionAdapter2.invokedynamic("makeConcat", Type.getMethodDescriptor(type2, (Type[]) Arrays.copyOf(typeArr2, typeArr2.length)), handle2, new Object[0]);
        }
        this.items.clear();
        ArrayList<Item> arrayList12 = this.items;
        Item.Companion companion = Item.INSTANCE;
        Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
        Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
        arrayList12.add(companion.parameter(JAVA_STRING_TYPE));
        this.paramSlots = AsmTypes.JAVA_STRING_TYPE.getSize();
    }

    private final StringBuilder buildRecipe(ArrayList<Item> itemForGeneration) {
        StringBuilder sb = new StringBuilder();
        for (Item item : itemForGeneration) {
            int i = WhenMappings.$EnumSwitchMapping$0[item.getItemType().ordinal()];
            if (i == 1) {
                sb.append("\u0001");
            } else if (i == 2) {
                sb.append("\u0002");
            } else if (i == 3) {
                sb.append(item.getValue());
            }
        }
        return sb;
    }

    private final ArrayList<Item> fitRestrictions(List<Item> items) {
        Object obj;
        ArrayList<Item> arrayList = new ArrayList<>();
        List<Item> list = items;
        for (Item item : list) {
            int i = WhenMappings.$EnumSwitchMapping$0[item.getItemType().ordinal()];
            if (i != 2 && i != 3) {
                arrayList.add(item);
            } else if (item.fitEncodingLimit()) {
                arrayList.add(item);
            } else {
                Iterator<T> it = CodegenUtilKt.splitStringConstant(item.getValue()).iterator();
                while (it.hasNext()) {
                    arrayList.add(new Item(item.getType(), ItemType.CONSTANT, (String) it.next()));
                }
            }
        }
        StringBuilder sbBuildRecipe = buildRecipe(arrayList);
        while (true) {
            String string = sbBuildRecipe.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            if (CodegenUtilKt.encodedUTF8Size(string) <= 65535) {
                break;
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : list) {
                if (((Item) obj2).getItemType() == ItemType.INLINED_CONSTANT) {
                    arrayList2.add(obj2);
                }
            }
            Iterator it2 = arrayList2.iterator();
            if (it2.hasNext()) {
                Object next = it2.next();
                if (it2.hasNext()) {
                    int encodedUTF8Size = ((Item) next).getEncodedUTF8Size();
                    do {
                        Object next2 = it2.next();
                        int encodedUTF8Size2 = ((Item) next2).getEncodedUTF8Size();
                        if (encodedUTF8Size < encodedUTF8Size2) {
                            next = next2;
                            encodedUTF8Size = encodedUTF8Size2;
                        }
                    } while (it2.hasNext());
                }
                obj = next;
            } else {
                obj = null;
            }
            Item item2 = (Item) obj;
            if (item2 == null) {
                break;
            }
            item2.setItemType(ItemType.CONSTANT);
            sbBuildRecipe = buildRecipe(arrayList);
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: StringConcatGenerator.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/StringConcatGenerator$Companion;", "", "()V", "STRING_BUILDER_OBJECT_APPEND_ARG_TYPES", "", "Lorg/jetbrains/org/objectweb/asm/Type;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/codegen/StringConcatGenerator;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "stringBuilderAppendType", ModuleXmlParser.TYPE, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Type stringBuilderAppendType(Type type) {
            int sort = type.getSort();
            if (sort == 3 || sort == 4) {
                Type INT_TYPE = Type.INT_TYPE;
                Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
                return INT_TYPE;
            }
            if (sort == 9) {
                Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
                Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
                return OBJECT_TYPE;
            }
            if (sort != 10) {
                return type;
            }
            if (!StringConcatGenerator.STRING_BUILDER_OBJECT_APPEND_ARG_TYPES.contains(type)) {
                type = AsmTypes.OBJECT_TYPE;
            }
            Intrinsics.checkNotNull(type);
            return type;
        }

        public final StringConcatGenerator create(GenerationState state, InstructionAdapter mv) {
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(mv, "mv");
            return new StringConcatGenerator(state.getConfig().getRuntimeStringConcat(), mv);
        }
    }

    static {
        HashSet hashSetNewHashSet = Sets.newHashSet(AsmTypes.getType(String.class), AsmTypes.getType(StringBuffer.class), AsmTypes.getType(CharSequence.class));
        Intrinsics.checkNotNullExpressionValue(hashSetNewHashSet, "newHashSet(...)");
        STRING_BUILDER_OBJECT_APPEND_ARG_TYPES = hashSetNewHashSet;
    }
}
