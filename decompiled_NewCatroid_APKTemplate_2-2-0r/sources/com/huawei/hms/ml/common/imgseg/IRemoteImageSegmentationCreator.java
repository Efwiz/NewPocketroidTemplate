package com.huawei.hms.ml.common.imgseg;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteImageSegmentationCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationCreator";

    public static class Default implements IRemoteImageSegmentationCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationCreator
        public IRemoteImageSegmentationDecoderDelegate newRemoteImageSegmentationDecoderDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteImageSegmentationDecoderDelegate newRemoteImageSegmentationDecoderDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteImageSegmentationCreator {
        static final int TRANSACTION_newRemoteImageSegmentationDecoderDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteImageSegmentationCreator.DESCRIPTOR);
        }

        public static IRemoteImageSegmentationCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteImageSegmentationCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteImageSegmentationCreator)) {
                return (IRemoteImageSegmentationCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteImageSegmentationCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteImageSegmentationCreator.DESCRIPTOR);
                IRemoteImageSegmentationDecoderDelegate iRemoteImageSegmentationDecoderDelegateNewRemoteImageSegmentationDecoderDelegate = newRemoteImageSegmentationDecoderDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteImageSegmentationDecoderDelegateNewRemoteImageSegmentationDecoderDelegate != null ? iRemoteImageSegmentationDecoderDelegateNewRemoteImageSegmentationDecoderDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteImageSegmentationCreator {
            public static IRemoteImageSegmentationCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteImageSegmentationCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationCreator
            public IRemoteImageSegmentationDecoderDelegate newRemoteImageSegmentationDecoderDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteImageSegmentationCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteImageSegmentationDecoderDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteImageSegmentationDecoderDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteImageSegmentationCreator iRemoteImageSegmentationCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteImageSegmentationCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteImageSegmentationCreator;
            return true;
        }

        public static IRemoteImageSegmentationCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
