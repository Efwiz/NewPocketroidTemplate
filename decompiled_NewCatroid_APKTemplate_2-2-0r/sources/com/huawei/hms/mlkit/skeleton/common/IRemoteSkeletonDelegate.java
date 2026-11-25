package com.huawei.hms.mlkit.skeleton.common;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteSkeletonDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate";

    public static class Default implements IRemoteSkeletonDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate
        public float calcSimilarity(List<SkeletonParcel> list, List<SkeletonParcel> list2, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException {
            return 0.0f;
        }

        @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate
        public int destroy() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate
        public List<SkeletonParcel> detect(Bundle bundle, SkeletonFrameParcel skeletonFrameParcel, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate
        public int initialize(IObjectWrapper iObjectWrapper, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException {
            return 0;
        }
    }

    float calcSimilarity(List<SkeletonParcel> list, List<SkeletonParcel> list2, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException;

    int destroy() throws RemoteException;

    List<SkeletonParcel> detect(Bundle bundle, SkeletonFrameParcel skeletonFrameParcel, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException;

    int initialize(IObjectWrapper iObjectWrapper, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteSkeletonDelegate {
        static final int TRANSACTION_calcSimilarity = 3;
        static final int TRANSACTION_destroy = 4;
        static final int TRANSACTION_detect = 2;
        static final int TRANSACTION_initialize = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteSkeletonDelegate.DESCRIPTOR);
        }

        public static IRemoteSkeletonDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteSkeletonDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteSkeletonDelegate)) {
                return (IRemoteSkeletonDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteSkeletonDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteSkeletonDelegate.DESCRIPTOR);
                int iInitialize = initialize(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? SkeletonOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(iInitialize);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(IRemoteSkeletonDelegate.DESCRIPTOR);
                List<SkeletonParcel> listDetect = detect(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? SkeletonFrameParcel.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? SkeletonOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeTypedList(listDetect);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(IRemoteSkeletonDelegate.DESCRIPTOR);
                float fCalcSimilarity = calcSimilarity(parcel.createTypedArrayList(SkeletonParcel.CREATOR), parcel.createTypedArrayList(SkeletonParcel.CREATOR), parcel.readInt() != 0 ? SkeletonOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeFloat(fCalcSimilarity);
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface(IRemoteSkeletonDelegate.DESCRIPTOR);
                int iDestroy = destroy();
                parcel2.writeNoException();
                parcel2.writeInt(iDestroy);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteSkeletonDelegate {
            public static IRemoteSkeletonDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteSkeletonDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate
            public int initialize(IObjectWrapper iObjectWrapper, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteSkeletonDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (skeletonOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        skeletonOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initialize(iObjectWrapper, skeletonOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate
            public List<SkeletonParcel> detect(Bundle bundle, SkeletonFrameParcel skeletonFrameParcel, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteSkeletonDelegate.DESCRIPTOR);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (skeletonFrameParcel != null) {
                        parcelObtain.writeInt(1);
                        skeletonFrameParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (skeletonOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        skeletonOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detect(bundle, skeletonFrameParcel, skeletonOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SkeletonParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate
            public float calcSimilarity(List<SkeletonParcel> list, List<SkeletonParcel> list2, SkeletonOptionsParcel skeletonOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteSkeletonDelegate.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    parcelObtain.writeTypedList(list2);
                    if (skeletonOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        skeletonOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().calcSimilarity(list, list2, skeletonOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate
            public int destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteSkeletonDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().destroy();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteSkeletonDelegate iRemoteSkeletonDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteSkeletonDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteSkeletonDelegate;
            return true;
        }

        public static IRemoteSkeletonDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
