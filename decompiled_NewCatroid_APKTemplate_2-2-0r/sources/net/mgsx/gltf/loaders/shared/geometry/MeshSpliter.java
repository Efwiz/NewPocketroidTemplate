package net.mgsx.gltf.loaders.shared.geometry;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.IntMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes12.dex */
class MeshSpliter {
    MeshSpliter() {
    }

    public static void split(Array<float[]> array, Array<short[]> array2, float[] fArr, VertexAttributes vertexAttributes, int[] iArr, int i) {
        int i2 = vertexAttributes.vertexSize / 4;
        int i3 = 65535;
        int i4 = i2 * 65535;
        IntArray intArray = new IntArray(i);
        IntArray intArray2 = new IntArray();
        int iMax = 0;
        for (int i5 : iArr) {
            iMax = Math.max(iMax, i5);
        }
        IntMap intMap = new IntMap();
        int length = iArr.length;
        int i6 = 0;
        while (i6 < length) {
            int i7 = i6 + 1;
            int i8 = iArr[i6];
            intArray.add(i8);
            int i9 = i8 / i3;
            int i10 = 1;
            boolean z = true;
            while (i10 < i) {
                int i11 = i7 + 1;
                int i12 = iArr[i7];
                intArray.add(i12);
                if (i12 / i3 != i9) {
                    z = false;
                }
                i10++;
                i7 = i11;
            }
            if (z) {
                IntArray intArray3 = (IntArray) intMap.get(i9);
                if (intArray3 == null) {
                    intArray3 = new IntArray();
                    intMap.put(i9, intArray3);
                }
                int i13 = 0;
                while (i13 < i) {
                    intArray3.add(intArray.get(i13) - (i9 * i3));
                    i13++;
                    i3 = 65535;
                }
            } else {
                intArray2.addAll(intArray);
            }
            intArray.clear();
            i6 = i7;
            i3 = 65535;
        }
        Iterator it = intMap.iterator();
        int iMax2 = 0;
        while (it.hasNext()) {
            iMax2 = Math.max(iMax2, ((IntMap.Entry) it.next()).key);
        }
        IntArray intArray4 = (IntArray) intMap.get(iMax2);
        int iMax3 = 0;
        for (int i14 = 0; i14 < intArray4.size; i14++) {
            iMax3 = Math.max(iMax3, intArray4.get(i14));
        }
        for (int i15 = 0; i15 <= iMax2; i15++) {
            float[] fArr2 = new float[i4];
            int i16 = i15 * 65535 * i2;
            System.arraycopy(fArr, i16, fArr2, 0, Math.min(fArr.length - i16, i4));
            array.add(fArr2);
        }
        float[] fArrPeek = array.peek();
        IntArray intArray5 = new IntArray();
        while (intArray2.size > 0) {
            int i17 = -1;
            if (iMax3 < 0 || iMax3 >= 65534) {
                iMax2++;
                intArray4 = new IntArray();
                intMap.put(iMax2, intArray4);
                fArrPeek = new float[i4];
                array.add(fArrPeek);
                iMax3 = -1;
            }
            IntIntMap intIntMap = new IntIntMap();
            int i18 = 0;
            while (i18 < intArray2.size) {
                int i19 = intArray2.get(i18);
                int i20 = iMax2;
                int i21 = intIntMap.get(i19, i17);
                if (i21 < 0) {
                    int i22 = iMax3 + 1;
                    if (i22 >= 65535) {
                        intArray5.add(i19);
                    } else {
                        intIntMap.put(i19, i22);
                        intArray4.add(i22);
                        iMax3 = i22;
                    }
                } else {
                    intArray4.add(i21);
                }
                i18++;
                iMax2 = i20;
                i17 = -1;
            }
            int i23 = iMax2;
            for (IntIntMap.Entry entry : intIntMap) {
                System.arraycopy(fArr, entry.key * i2, fArrPeek, entry.value * i2, i2);
            }
            if (intArray5.size == 0) {
                iMax2 = i23;
                break;
            }
            intArray2.clear();
            iMax2 = i23;
            iMax3 = -1;
            IntArray intArray6 = intArray5;
            intArray5 = intArray2;
            intArray2 = intArray6;
        }
        for (int i24 = 0; i24 <= iMax2; i24++) {
            IntArray intArray7 = (IntArray) intMap.get(i24);
            short[] sArr = new short[intArray7.size];
            for (int i25 = 0; i25 < intArray7.size; i25++) {
                sArr[i25] = (short) intArray7.get(i25);
            }
            array2.add(sArr);
        }
        int i26 = (iMax3 + 1) * i2;
        float[] fArr3 = new float[i26];
        System.arraycopy(fArrPeek, 0, fArr3, 0, i26);
        array.set(array2.size - 1, fArr3);
    }
}
