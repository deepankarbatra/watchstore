import imageDto from "./imageDto";

export default interface watchUpdateDto{
    modelNumber:string,
    watchName:string,
    watchBrand:string,
    watchType:any,
    stockQuantity:number,
    price: number,
    availableStatus:boolean,
    images:imageDto[]
}