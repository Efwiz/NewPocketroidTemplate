package com.huawei.hms.ml.common.face;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteFaceDetectorDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.face.IRemoteFaceDetectorDelegate";

    public static class Default implements IRemoteFaceDetectorDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.face.IRemoteFaceDetectorDelegate
        public int destroy() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.common.face.IRemoteFaceDetectorDelegate
        public List<FaceParcel> detect(Bundle bundle, FaceFrameParcel faceFrameParcel, FaceDetectorOptionsParcel faceDetectorOptionsParcel) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.ml.common.face.IRemoteFaceDetectorDelegate
        public int initialize(IObjectWrapper iObjectWrapper, FaceDetectorOptionsParcel faceDetectorOptionsParcel) throws RemoteException {
            return 0;
        }
    }

    int destroy() throws RemoteException;

    List<FaceParcel> detect(Bundle bundle, FaceFrameParcel faceFrameParcel, FaceDetectorOptionsParcel faceDetectorOptionsParcel) throws RemoteException;

    int initialize(IObjectWrapper iObjectWrapper, FaceDetectorOptionsParcel faceDetectorOptionsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteFaceDetectorDelegate {
        static final int TRANSACTION_destroy = 3;
        static final int TRANSACTION_detect = 2;
        static final int TRANSACTION_initialize = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteFaceDetectorDelegate.DESCRIPTOR);
        }

        public static IRemoteFaceDetectorDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteFaceDetectorDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteFaceDetectorDelegate)) {
                return (IRemoteFaceDetectorDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteFaceDetectorDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteFaceDetectorDelegate.DESCRIPTOR);
                int iInitialize = initialize(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? FaceDetectorOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(iInitialize);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(IRemoteFaceDetectorDelegate.DESCRIPTOR);
                List<FaceParcel> listDetect = detect(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? FaceFrameParcel.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? FaceDetectorOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeTypedList(listDetect);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(IRemoteFaceDetectorDelegate.DESCRIPTOR);
                int iDestroy = destroy();
                parcel2.writeNoException();
                parcel2.writeInt(iDestroy);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteFaceDetectorDelegate {
            public static IRemoteFaceDetectorDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteFaceDetectorDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.face.IRemoteFaceDetectorDelegate
            public int initialize(IObjectWrapper iObjectWrapper, FaceDetectorOptionsParcel faceDetectorOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteFaceDetectorDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (faceDetectorOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        faceDetectorOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initialize(iObjectWrapper, faceDetectorOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.face.IRemoteFaceDetectorDelegate
            public List<FaceParcel> detect(Bundle bundle, FaceFrameParcel faceFrameParcel, FaceDetectorOptionsParcel faceDetectorOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteFaceDetectorDelegate.DESCRIPTOR);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (faceFrameParcel != null) {
                        parcelObtain.writeInt(1);
                        faceFrameParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (faceDetectorOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        faceDetectorOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detect(bundle, faceFrameParcel, faceDetectorOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(FaceParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.face.IRemoteFaceDetectorDelegate
            public int destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteFaceDetectorDelegate.DESCRIPTOR);
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

        public static boolean setDefaultImpl(IRemoteFaceDetectorDelegate iRemoteFaceDetectorDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteFaceDetectorDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteFaceDetectorDelegate;
            return true;
        }

        public static IRemoteFaceDetectorDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
