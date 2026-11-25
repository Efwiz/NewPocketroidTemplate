package org.jetbrains.kotlin.com.intellij.psi.impl.cache;

import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;

/* JADX INFO: loaded from: classes3.dex */
public final class ModifierFlags {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ABSTRACT_MASK = 1024;
    public static final int DEFAULT_MASK = 512;
    public static final int FINAL_MASK = 16;
    public static final Object2IntMap<IElementType> KEYWORD_TO_MODIFIER_FLAG_MAP;
    public static final Int2ObjectMap<String> MODIFIER_FLAG_TO_NAME_MAP;
    public static final Object2IntMap<String> NAME_TO_MODIFIER_FLAG_MAP;
    public static final int NATIVE_MASK = 256;
    public static final int NON_SEALED_MASK = 65536;
    public static final int OPEN_MASK = 8192;
    public static final int PACKAGE_LOCAL_MASK = 4096;
    public static final int PRIVATE_MASK = 2;
    public static final int PROTECTED_MASK = 4;
    public static final int PUBLIC_MASK = 1;
    public static final int SEALED_MASK = 32768;
    public static final int STATIC_MASK = 8;
    public static final int STRICTFP_MASK = 2048;
    public static final int SYNCHRONIZED_MASK = 32;
    public static final int TRANSIENT_MASK = 128;
    public static final int TRANSITIVE_MASK = 16384;
    public static final int VOLATILE_MASK = 64;

    /* JADX WARN: Multi-variable type inference failed */
    static {
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        NAME_TO_MODIFIER_FLAG_MAP = object2IntOpenHashMap;
        MODIFIER_FLAG_TO_NAME_MAP = new Int2ObjectOpenHashMap();
        KEYWORD_TO_MODIFIER_FLAG_MAP = new Object2IntOpenHashMap();
        object2IntOpenHashMap.put("public", 1);
        object2IntOpenHashMap.put("private", 2);
        object2IntOpenHashMap.put("protected", 4);
        object2IntOpenHashMap.put("static", 8);
        object2IntOpenHashMap.put("final", 16);
        object2IntOpenHashMap.put("synchronized", 32);
        object2IntOpenHashMap.put("volatile", 64);
        object2IntOpenHashMap.put("transient", 128);
        object2IntOpenHashMap.put("native", 256);
        object2IntOpenHashMap.put("default", 512);
        object2IntOpenHashMap.put("abstract", 1024);
        object2IntOpenHashMap.put("strictfp", 2048);
        object2IntOpenHashMap.put(PsiModifier.PACKAGE_LOCAL, 4096);
        object2IntOpenHashMap.put("open", 8192);
        object2IntOpenHashMap.put("transitive", 16384);
        object2IntOpenHashMap.put("sealed", 32768);
        object2IntOpenHashMap.put("non-sealed", 65536);
        ObjectIterator it = object2IntOpenHashMap.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            MODIFIER_FLAG_TO_NAME_MAP.put(NAME_TO_MODIFIER_FLAG_MAP.getInt(str), str);
        }
        Object2IntMap<IElementType> object2IntMap = KEYWORD_TO_MODIFIER_FLAG_MAP;
        object2IntMap.put(JavaTokenType.PUBLIC_KEYWORD, 1);
        object2IntMap.put(JavaTokenType.PRIVATE_KEYWORD, 2);
        object2IntMap.put(JavaTokenType.PROTECTED_KEYWORD, 4);
        object2IntMap.put(JavaTokenType.STATIC_KEYWORD, 8);
        object2IntMap.put(JavaTokenType.FINAL_KEYWORD, 16);
        object2IntMap.put(JavaTokenType.SYNCHRONIZED_KEYWORD, 32);
        object2IntMap.put(JavaTokenType.VOLATILE_KEYWORD, 64);
        object2IntMap.put(JavaTokenType.TRANSIENT_KEYWORD, 128);
        object2IntMap.put(JavaTokenType.NATIVE_KEYWORD, 256);
        object2IntMap.put(JavaTokenType.DEFAULT_KEYWORD, 512);
        object2IntMap.put(JavaTokenType.ABSTRACT_KEYWORD, 1024);
        object2IntMap.put(JavaTokenType.STRICTFP_KEYWORD, 2048);
        object2IntMap.put(JavaTokenType.OPEN_KEYWORD, 8192);
        object2IntMap.put(JavaTokenType.TRANSITIVE_KEYWORD, 16384);
        object2IntMap.put(JavaTokenType.SEALED_KEYWORD, 32768);
        object2IntMap.put(JavaTokenType.NON_SEALED_KEYWORD, 65536);
    }

    public static boolean hasModifierProperty(String str, int i) {
        return BitUtil.isSet(i, NAME_TO_MODIFIER_FLAG_MAP.getInt(str));
    }
}
