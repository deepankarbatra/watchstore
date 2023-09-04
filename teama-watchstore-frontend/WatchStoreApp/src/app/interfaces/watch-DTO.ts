import Image  from "./imageDto";




export default interface Watch {
    modelNumber: string;
    watchName: string;
    watchBrand: string;
    watchType: string;
    stockQuantity: number;
    price: number;
    availableStatus: boolean;
    dateOfArrival: Date;
    images: Image[];
  }