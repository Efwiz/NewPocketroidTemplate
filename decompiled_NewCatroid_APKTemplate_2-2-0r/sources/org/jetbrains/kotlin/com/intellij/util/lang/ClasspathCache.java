package org.jetbrains.kotlin.com.intellij.util.lang;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.util.BloomFilterBase;
import org.jetbrains.kotlin.com.intellij.util.containers.IntObjectHashMap;
import org.jetbrains.kotlin.com.intellij.util.io.Murmur3_32Hash;
import org.jetbrains.kotlin.com.intellij.util.lang.fastutil.StrippedIntOpenHashSet;
import org.jetbrains.kotlin.com.intellij.util.lang.fastutil.StrippedLongOpenHashSet;

/* JADX INFO: loaded from: classes3.dex */
public final class ClasspathCache {
    private static final IntObjectHashMap.ArrayProducer<Loader[][]> ARRAY_FACTORY = new IntObjectHashMap.ArrayProducer() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.ClasspathCache$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.IntObjectHashMap.ArrayProducer
        public final Object produce(int i) {
            return ClasspathCache.lambda$static$0(i);
        }
    };
    private volatile IntObjectHashMap<Loader[]> classPackageCache;
    private volatile IntObjectHashMap<Loader[]> resourcePackageCache;

    public interface IndexRegistrar {
        void registerPackageIndex(IntObjectHashMap<Loader[]> intObjectHashMap, IntObjectHashMap<Loader[]> intObjectHashMap2, Loader loader);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 6:
            case 8:
                objArr[0] = "loader";
                break;
            case 2:
            case 3:
            case 4:
                objArr[0] = "resourcePath";
                break;
            case 5:
            case 7:
                objArr[0] = "map";
                break;
            default:
                objArr[0] = "registrar";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/ClasspathCache";
        switch (i) {
            case 2:
                objArr[2] = "getLoadersByName";
                break;
            case 3:
                objArr[2] = "getClassLoadersByName";
                break;
            case 4:
                objArr[2] = "getPackageNameHash";
                break;
            case 5:
            case 6:
                objArr[2] = "addResourceEntries";
                break;
            case 7:
            case 8:
                objArr[2] = "addResourceEntry";
                break;
            default:
                objArr[2] = "applyLoaderData";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static /* synthetic */ Loader[][] lambda$static$0(int i) {
        return new Loader[i][];
    }

    public static final class LoaderData implements IndexRegistrar {
        private final int[] classPackageHashes;
        private final NameFilter nameFilter;
        private final int[] resourcePackageHashes;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "buffer", "org/jetbrains/kotlin/com/intellij/util/lang/ClasspathCache$LoaderData", "save"));
        }

        LoaderData(int[] iArr, int[] iArr2, NameFilter nameFilter) {
            this.resourcePackageHashes = iArr;
            this.classPackageHashes = iArr2;
            this.nameFilter = nameFilter;
        }

        int sizeInBytes() {
            return (this.classPackageHashes.length * 4) + 8 + (this.resourcePackageHashes.length * 4) + this.nameFilter.sizeInBytes();
        }

        void save(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer == null) {
                $$$reportNull$$$0(0);
            }
            byteBuffer.putInt(this.classPackageHashes.length);
            byteBuffer.putInt(this.resourcePackageHashes.length);
            IntBuffer intBufferAsIntBuffer = byteBuffer.asIntBuffer();
            intBufferAsIntBuffer.put(this.classPackageHashes);
            intBufferAsIntBuffer.put(this.resourcePackageHashes);
            byteBuffer.position(byteBuffer.position() + (intBufferAsIntBuffer.position() * 4));
            this.nameFilter.save(byteBuffer);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.lang.ClasspathCache.IndexRegistrar
        public void registerPackageIndex(IntObjectHashMap<Loader[]> intObjectHashMap, IntObjectHashMap<Loader[]> intObjectHashMap2, Loader loader) {
            ClasspathCache.addResourceEntries(this.classPackageHashes, intObjectHashMap, loader);
            ClasspathCache.addResourceEntries(this.resourcePackageHashes, intObjectHashMap2, loader);
            loader.setNameFilter(this.nameFilter);
        }
    }

    static final class LoaderDataBuilder implements IndexRegistrar {
        private final StrippedLongOpenHashSet usedNameFingerprints;
        private final StrippedIntOpenHashSet resourcePackageHashes = new StrippedIntOpenHashSet();
        private final StrippedIntOpenHashSet classPackageHashes = new StrippedIntOpenHashSet();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 6 ? 3 : 2];
            if (i == 2 || i == 3 || i == 4 || i == 5) {
                objArr[0] = "path";
            } else if (i != 6) {
                objArr[0] = "name";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/ClasspathCache$LoaderDataBuilder";
            }
            if (i != 6) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/ClasspathCache$LoaderDataBuilder";
            } else {
                objArr[1] = "createNameFilter";
            }
            switch (i) {
                case 1:
                    objArr[2] = "addResourceName";
                    break;
                case 2:
                    objArr[2] = "addResourcePackageFromName";
                    break;
                case 3:
                    objArr[2] = "addResourcePackage";
                    break;
                case 4:
                    objArr[2] = "addClassPackageFromName";
                    break;
                case 5:
                    objArr[2] = "addClassPackage";
                    break;
                case 6:
                    break;
                default:
                    objArr[2] = "andClassName";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i == 6) {
                throw new IllegalStateException(str2);
            }
        }

        LoaderDataBuilder(boolean z) {
            this.usedNameFingerprints = z ? new StrippedLongOpenHashSet() : null;
        }

        void andClassName(String str) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            this.usedNameFingerprints.add(NameFilter.toNameFingerprint(str, str.length()));
        }

        void addResourceName(String str, int i) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            this.usedNameFingerprints.add(NameFilter.toNameFingerprint(str, i));
        }

        void addResourcePackageFromName(String str) {
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            this.resourcePackageHashes.add(ClasspathCache.getPackageNameHash(str, str.lastIndexOf(47)));
        }

        void addResourcePackage(String str) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            this.resourcePackageHashes.add(ClasspathCache.getPackageNameHash(str, str.length()));
        }

        void addClassPackageFromName(String str) {
            if (str == null) {
                $$$reportNull$$$0(4);
            }
            this.classPackageHashes.add(ClasspathCache.getPackageNameHash(str, str.lastIndexOf(47)));
        }

        void addClassPackage(String str) {
            if (str == null) {
                $$$reportNull$$$0(5);
            }
            this.classPackageHashes.add(ClasspathCache.getPackageNameHash(str, str.length()));
        }

        LoaderData build() {
            return new LoaderData(this.resourcePackageHashes.toArray(), this.classPackageHashes.toArray(), createNameFilter());
        }

        private NameFilter createNameFilter() {
            NameFilter nameFilter = new NameFilter(this.usedNameFingerprints.size(), 0.005d);
            StrippedLongOpenHashSet.SetIterator it = this.usedNameFingerprints.iterator();
            while (it.hasNext()) {
                nameFilter.addNameFingerprint(it.nextLong());
            }
            return nameFilter;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.lang.ClasspathCache.IndexRegistrar
        public void registerPackageIndex(IntObjectHashMap<Loader[]> intObjectHashMap, IntObjectHashMap<Loader[]> intObjectHashMap2, Loader loader) {
            StrippedIntOpenHashSet.SetIterator it = this.classPackageHashes.iterator();
            while (it.hasNext()) {
                ClasspathCache.addResourceEntry(it.nextInt(), intObjectHashMap, loader);
            }
            StrippedIntOpenHashSet.SetIterator it2 = this.resourcePackageHashes.iterator();
            while (it2.hasNext()) {
                ClasspathCache.addResourceEntry(it2.nextInt(), intObjectHashMap2, loader);
            }
            if (this.usedNameFingerprints != null) {
                loader.setNameFilter(createNameFilter());
            }
        }
    }

    void applyLoaderData(IndexRegistrar indexRegistrar, Loader loader) {
        if (indexRegistrar == null) {
            $$$reportNull$$$0(0);
        }
        if (loader == null) {
            $$$reportNull$$$0(1);
        }
        IntObjectHashMap<Loader[]> intObjectHashMap = new IntObjectHashMap<>(this.classPackageCache);
        IntObjectHashMap<Loader[]> intObjectHashMap2 = new IntObjectHashMap<>(this.resourcePackageCache);
        indexRegistrar.registerPackageIndex(intObjectHashMap, intObjectHashMap2, loader);
        this.classPackageCache = intObjectHashMap;
        this.resourcePackageCache = intObjectHashMap2;
    }

    Loader[] getLoadersByName(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return (str.endsWith(".class") ? this.classPackageCache : this.resourcePackageCache).get(getPackageNameHash(str, str.lastIndexOf(47)));
    }

    Loader[] getClassLoadersByName(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return this.classPackageCache.get(getPackageNameHash(str, str.lastIndexOf(47)));
    }

    static int getPackageNameHash(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (i <= 0) {
            return 0;
        }
        return Murmur3_32Hash.MURMUR3_32.hashString(str, 0, i);
    }

    public static void addResourceEntries(int[] iArr, IntObjectHashMap<Loader[]> intObjectHashMap, Loader loader) {
        if (intObjectHashMap == null) {
            $$$reportNull$$$0(5);
        }
        if (loader == null) {
            $$$reportNull$$$0(6);
        }
        Loader[] loaderArr = null;
        for (int i : iArr) {
            int iIndex = intObjectHashMap.index(i);
            Loader[] byIndex = intObjectHashMap.getByIndex(iIndex, i);
            if (byIndex == null) {
                if (loaderArr == null) {
                    loaderArr = new Loader[]{loader};
                }
                intObjectHashMap.addByIndex(iIndex, i, loaderArr);
            } else {
                Loader[] loaderArr2 = new Loader[byIndex.length + 1];
                System.arraycopy(byIndex, 0, loaderArr2, 0, byIndex.length);
                loaderArr2[byIndex.length] = loader;
                intObjectHashMap.replaceByIndex(iIndex, i, loaderArr2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void addResourceEntry(int i, IntObjectHashMap<Loader[]> intObjectHashMap, Loader loader) {
        if (intObjectHashMap == null) {
            $$$reportNull$$$0(7);
        }
        if (loader == null) {
            $$$reportNull$$$0(8);
        }
        int iIndex = intObjectHashMap.index(i);
        Loader[] byIndex = intObjectHashMap.getByIndex(iIndex, i);
        if (byIndex == null) {
            intObjectHashMap.addByIndex(iIndex, i, new Loader[]{loader});
            return;
        }
        if (ClassPath.recordLoadingInfo) {
            for (Loader loader2 : byIndex) {
                if (loader == loader2) {
                    throw new IllegalStateException("Duplicated loader");
                }
            }
        }
        Loader[] loaderArr = new Loader[byIndex.length + 1];
        System.arraycopy(byIndex, 0, loaderArr, 0, byIndex.length);
        loaderArr[byIndex.length] = loader;
        intObjectHashMap.replaceByIndex(iIndex, i, loaderArr);
    }

    static final class NameFilter extends BloomFilterBase implements Predicate<String> {
        private static final Murmur3_32Hash MURMUR3_32_CUSTOM_SEED = new Murmur3_32Hash(85486);

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1 || i == 2) {
                objArr[0] = "name";
            } else {
                objArr[0] = "buffer";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/ClasspathCache$NameFilter";
            if (i == 1) {
                objArr[2] = "test";
            } else if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "toNameFingerprint";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        NameFilter(int i, double d) {
            super(i, d);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        NameFilter(ByteBuffer byteBuffer) throws IOException {
            super(byteBuffer);
            if (byteBuffer == null) {
                $$$reportNull$$$0(0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addNameFingerprint(long j) {
            addIt((int) (j >> 32), (int) j);
        }

        @Override // java.util.function.Predicate
        public boolean test(String str) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            int length = str.endsWith("/") ? str.length() - 1 : str.length();
            return maybeContains(MURMUR3_32_CUSTOM_SEED.hashString(str, 0, length), Murmur3_32Hash.MURMUR3_32.hashString(str, 0, length));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static long toNameFingerprint(String str, int i) {
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            int iHashString = MURMUR3_32_CUSTOM_SEED.hashString(str, 0, i);
            return (((long) Murmur3_32Hash.MURMUR3_32.hashString(str, 0, i)) & 4294967295L) | (((long) iHashString) << 32);
        }
    }
}
