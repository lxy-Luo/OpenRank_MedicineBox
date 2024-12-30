import ItemModel from '../model/ItemModel';
import MedicalRecordDTO from '../viewmodel/medical/MedicalRecordDTO';
import MedicalRecordItem from '../viewmodel/medical/MedicalRecordItem';
import MedicalRecordVO from '../viewmodel/medical/MedicalRecordVO';
class MedicineBoxService{
    //将medicalRecordDTO 通过查询得到 MedicalRecordVO
  calculateMedicalRecordVO(DTO: MedicalRecordDTO[]): MedicalRecordVO[]{
    let group = []
    DTO.forEach((element: MedicalRecordDTO) => {
      let info: MedicalRecordItem = ItemModel.medicalGetById(element.DrugId)

      group.push(new MedicalRecordVO(element.id,
                            element.DrugId,
                            info.name,
                            info.image,
                            info.categoryId,
                            info.unit,
                            info.introduction,
                            element.time,
                            element.All_number,
                            element.image))
    });
    return group
  }
}
let medicineBoxService = new MedicineBoxService()
export default medicineBoxService as MedicineBoxService