package com.huawei.hms.ml.common.object;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteObjectDecoderDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.object.IRemoteObjectDecoderDelegate";

    public static class Default implements IRemoteObjectDecoderDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.object.IRemoteObjectDecoderDelegate
        public int destroy() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.common.object.IRemoteObjectDecoderDelegate
        public List<ObjectDetectorParcel> detect(Bundle bundle, ObjectDetectorFrameParcel objectDetectorFrameParcel, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.ml.common.object.IRemoteObjectDecoderDelegate
        public int initial(IObjectWrapper iObjectWrapper, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) throws RemoteException {
            return 0;
        }
    }

    int destroy() throws RemoteException;

    List<ObjectDetectorParcel> detect(Bundle bundle, ObjectDetectorFrameParcel objectDetectorFrameParcel, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) throws RemoteException;

    int initial(IObjectWrapper iObjectWrapper, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteObjectDecoderDelegate {
        static final int TRANSACTION_destroy = 3;
        static final int TRANSACTION_detect = 2;
        static final int TRANSACTION_initial = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteObjectDecoderDelegate.DESCRIPTOR);
        }

        public static IRemoteObjectDecoderDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteObjectDecoderDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteObjectDecoderDelegate)) {
                return (IRemoteObjectDecoderDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteObjectDecoderDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteObjectDecoderDelegate.DESCRIPTOR);
                int iInitial = initial(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? ObjectDetectorOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(iInitial);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(IRemoteObjectDecoderDelegate.DESCRIPTOR);
                List<ObjectDetectorParcel> listDetect = detect(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? ObjectDetectorFrameParcel.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? ObjectDetectorOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeTypedList(listDetect);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(IRemoteObjectDecoderDelegate.DESCRIPTOR);
                int iDestroy = destroy();
                parcel2.writeNoException();
                parcel2.writeInt(iDestroy);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteObjectDecoderDelegate {
            public static IRemoteObjectDecoderDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteObjectDecoderDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.object.IRemoteObjectDecoderDelegate
            public int initial(IObjectWrapper iObjectWrapper, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteObjectDecoderDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (objectDetectorOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        objectDetectorOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initial(iObjectWrapper, objectDetectorOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.object.IRemoteObjectDecoderDelegate
            public List<ObjectDetectorParcel> detect(Bundle bundle, ObjectDetectorFrameParcel objectDetectorFrameParcel, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteObjectDecoderDelegate.DESCRIPTOR);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (objectDetectorFrameParcel != null) {
                        parcelObtain.writeInt(1);
                        objectDetectorFrameParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (objectDetectorOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        objectDetectorOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detect(bundle, objectDetectorFrameParcel, objectDetectorOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ObjectDetectorParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.object.IRemoteObjectDecoderDelegate
            public int destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteObjectDecoderDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
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

        public static boolean setDefaultImpl(IRemoteObjectDecoderDelegate iRemoteObjectDecoderDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteObjectDecoderDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteObjectDecoderDelegate;
            return true;
        }

        public static IRemoteObjectDecoderDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
