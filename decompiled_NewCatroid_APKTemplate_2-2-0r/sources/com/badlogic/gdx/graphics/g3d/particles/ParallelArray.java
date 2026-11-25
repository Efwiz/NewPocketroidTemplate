package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ArrayReflection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ParallelArray.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ParallelArray {
    public int capacity;
    Array<Channel> arrays = new Array<>(false, 2, Channel.class);
    public int size = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ParallelArray$ChannelInitializer.class
      classes8.dex
     */
    public interface ChannelInitializer<T extends Channel> {
        void init(T t);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ParallelArray$ChannelDescriptor.class
      classes8.dex
     */
    public static class ChannelDescriptor {
        public int count;

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        public int f113id;
        public Class<?> type;

        public ChannelDescriptor(int i, Class<?> cls, int i2) {
            this.f113id = i;
            this.type = cls;
            this.count = i2;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ParallelArray$Channel.class
      classes8.dex
     */
    public abstract class Channel {
        public Object data;

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        public int f112id;
        public int strideSize;

        public abstract void add(int i, Object... objArr);

        protected abstract void setCapacity(int i);

        public abstract void swap(int i, int i2);

        public Channel(int i, Object obj, int i2) {
            this.f112id = i;
            this.strideSize = i2;
            this.data = obj;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ParallelArray$FloatChannel.class
      classes8.dex
     */
    public class FloatChannel extends Channel {
        public float[] data;

        public FloatChannel(int i, int i2, int i3) {
            super(i, new float[i3 * i2], i2);
            this.data = (float[]) super.data;
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void add(int i, Object... objArr) {
            int i2 = this.strideSize * ParallelArray.this.size;
            int i3 = this.strideSize + i2;
            int i4 = 0;
            while (i2 < i3) {
                this.data[i2] = ((Float) objArr[i4]).floatValue();
                i2++;
                i4++;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void swap(int i, int i2) {
            int i3 = this.strideSize * i;
            int i4 = this.strideSize * i2;
            int i5 = this.strideSize + i3;
            while (i3 < i5) {
                float[] fArr = this.data;
                float f = fArr[i3];
                fArr[i3] = fArr[i4];
                fArr[i4] = f;
                i3++;
                i4++;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void setCapacity(int i) {
            int i2 = this.strideSize * i;
            float[] fArr = new float[i2];
            float[] fArr2 = this.data;
            System.arraycopy(fArr2, 0, fArr, 0, Math.min(fArr2.length, i2));
            this.data = fArr;
            super.data = fArr;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ParallelArray$IntChannel.class
      classes8.dex
     */
    public class IntChannel extends Channel {
        public int[] data;

        public IntChannel(int i, int i2, int i3) {
            super(i, new int[i3 * i2], i2);
            this.data = (int[]) super.data;
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void add(int i, Object... objArr) {
            int i2 = this.strideSize * ParallelArray.this.size;
            int i3 = this.strideSize + i2;
            int i4 = 0;
            while (i2 < i3) {
                this.data[i2] = ((Integer) objArr[i4]).intValue();
                i2++;
                i4++;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void swap(int i, int i2) {
            int i3 = this.strideSize * i;
            int i4 = this.strideSize * i2;
            int i5 = this.strideSize + i3;
            while (i3 < i5) {
                int[] iArr = this.data;
                int i6 = iArr[i3];
                iArr[i3] = iArr[i4];
                iArr[i4] = i6;
                i3++;
                i4++;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void setCapacity(int i) {
            int i2 = this.strideSize * i;
            int[] iArr = new int[i2];
            int[] iArr2 = this.data;
            System.arraycopy(iArr2, 0, iArr, 0, Math.min(iArr2.length, i2));
            this.data = iArr;
            super.data = iArr;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ParallelArray$ObjectChannel.class
      classes8.dex
     */
    public class ObjectChannel<T> extends Channel {
        Class<T> componentType;
        public T[] data;

        public ObjectChannel(int i, int i2, int i3, Class<T> cls) {
            super(i, ArrayReflection.newInstance(cls, i3 * i2), i2);
            this.componentType = cls;
            this.data = (T[]) ((Object[]) super.data);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void add(int i, Object... objArr) {
            int i2 = this.strideSize * ParallelArray.this.size;
            int i3 = this.strideSize + i2;
            int i4 = 0;
            while (i2 < i3) {
                ((T[]) this.data)[i2] = objArr[i4];
                i2++;
                i4++;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void swap(int i, int i2) {
            int i3 = this.strideSize * i;
            int i4 = this.strideSize * i2;
            int i5 = this.strideSize + i3;
            while (i3 < i5) {
                T[] tArr = this.data;
                T t = tArr[i3];
                tArr[i3] = tArr[i4];
                tArr[i4] = t;
                i3++;
                i4++;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParallelArray.Channel
        public void setCapacity(int i) {
            T[] tArr = (T[]) ((Object[]) ArrayReflection.newInstance(this.componentType, this.strideSize * i));
            T[] tArr2 = this.data;
            System.arraycopy(tArr2, 0, tArr, 0, Math.min(tArr2.length, tArr.length));
            this.data = tArr;
            super.data = tArr;
        }
    }

    public ParallelArray(int i) {
        this.capacity = i;
    }

    public <T extends Channel> T addChannel(ChannelDescriptor channelDescriptor) {
        return (T) addChannel(channelDescriptor, null);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public <T extends Channel> T addChannel(ChannelDescriptor channelDescriptor, ChannelInitializer<T> channelInitializer) {
        T t = (T) getChannel(channelDescriptor);
        if (t == null) {
            t = (T) allocateChannel(channelDescriptor);
            if (channelInitializer != null) {
                channelInitializer.init(t);
            }
            this.arrays.add(t);
        }
        return t;
    }

    private <T extends Channel> T allocateChannel(ChannelDescriptor channelDescriptor) {
        if (channelDescriptor.type == Float.TYPE) {
            return new FloatChannel(channelDescriptor.f113id, channelDescriptor.count, this.capacity);
        }
        if (channelDescriptor.type == Integer.TYPE) {
            return new IntChannel(channelDescriptor.f113id, channelDescriptor.count, this.capacity);
        }
        return new ObjectChannel(channelDescriptor.f113id, channelDescriptor.count, this.capacity, channelDescriptor.type);
    }

    public <T> void removeArray(int i) {
        this.arrays.removeIndex(findIndex(i));
    }

    private int findIndex(int i) {
        for (int i2 = 0; i2 < this.arrays.size; i2++) {
            if (this.arrays.items[i2].f112id == i) {
                return i2;
            }
        }
        return -1;
    }

    public void addElement(Object... objArr) {
        if (this.size == this.capacity) {
            throw new GdxRuntimeException("Capacity reached, cannot add other elements");
        }
        Array.ArrayIterator<Channel> it = this.arrays.iterator();
        int i = 0;
        while (it.hasNext()) {
            Channel next = it.next();
            next.add(i, objArr);
            i += next.strideSize;
        }
        this.size++;
    }

    public void removeElement(int i) {
        int i2 = this.size - 1;
        Array.ArrayIterator<Channel> it = this.arrays.iterator();
        while (it.hasNext()) {
            it.next().swap(i, i2);
        }
        this.size = i2;
    }

    public <T extends Channel> T getChannel(ChannelDescriptor channelDescriptor) {
        Array.ArrayIterator<Channel> it = this.arrays.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            if (t.f112id == channelDescriptor.f113id) {
                return t;
            }
        }
        return null;
    }

    public void clear() {
        this.arrays.clear();
        this.size = 0;
    }

    public void setCapacity(int i) {
        if (this.capacity != i) {
            Array.ArrayIterator<Channel> it = this.arrays.iterator();
            while (it.hasNext()) {
                it.next().setCapacity(i);
            }
            this.capacity = i;
        }
    }
}
