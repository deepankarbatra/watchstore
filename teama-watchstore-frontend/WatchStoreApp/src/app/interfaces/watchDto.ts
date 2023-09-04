import imageDto from "./imageDto";

export default interface watchDto{
    modelNumber:string,
    watchName:string,
    watchBrand:string,
    watchType:any,
    stockQuantity:number,
    price: number,
    availableStatus:boolean,
    imagePathList:string[]
}