package com.qmx.smedicinebox;

import com.qmx.smedicinebox.sys.dao.MedicationPictureDao;
import com.qmx.smedicinebox.sys.dao.MedicationSituationDao;
import com.qmx.smedicinebox.sys.service.MedicationSituationService;
import com.qmx.smedicinebox.sys.service.MedicineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig
class SMedicineBoxApplicationTests {

	@Autowired
	private MedicationSituationDao medicationSituationDao;

	@Autowired
	private MedicationSituationService medicationSituationService;

	@Autowired
	private MedicationPictureDao medicationPictureDao;

	@Autowired
	private MedicineService medicineService;

//	@Test
//	void contextLoads() throws FileNotFoundException {
//		ArrayList<File> files = new ArrayList<>();
//		ArrayList<String> strings = new ArrayList<>();
//
//		for(int i=0;i<9;i++){
//			files.add(new File("E:\\Downloads\\Firefox\\"+(i+1)+".png"));
//		}
//
//
//		for (int i=0;i<9;i++){
//			// 获取当前时间，转成字符串
//
//			String format = DateTimeUtil.TransformDateTimeToString(new Date(), Constant.EMPTY,Constant.UNDERSCORE,Constant.EMPTY);
////        String path = img.getUserId() + "_" + id + "_" + "temImg.png";
//			String path = "26" + "_" + format + ".png";
//			strings.add(OssUtil.upload(files.get(i), path));
//		}
//
//		System.out.println(strings);
//
//
//
//	}

//	@Test
//	void contextLoads() throws FileNotFoundException {
////		String jsonStr = "{\"words_result\":[{\"words\":\"日\"},{\"words\":\"善举\"},{\"words\":\"OTC\"},{\"words\":\"国药字Z41020143\"},{\"words\":\"奥美沙坦\"},{\"words\":\"水丸\"},{\"words\":\",5\"},{\"words\":\"回回回回回回回回回回回回回回回回回\"},{\"words\":\"功能主治】\"},{\"words\":\"?行气化湿☑健脾和骨\"},{\"words\":\"用子湿浊中阻、脾胃不和所致的胸膈君闷\"},{\"words\":\"脘腹胀痛、呕吐恶心、噯气纳呆\"},{\"words\":\"0克\"},{\"words\":\"GEL PEN\"}],\"words_result_num\":14,\"log_id\":1780203491971411207}";
////
////		MedicineEntity medicine = medicineService.equalMedicineName(jsonStr);
////		System.out.println(medicine);
//		boolean flag = medicationSituationService.determineSave(5018);
//		System.out.println(flag);
//	}

//		@Test
//	void contextLoads() throws FileNotFoundException {
//			String filePath ="E:/Image/7.jpg" ;
//
//			String s = Ocr.generalBasic(filePath);
//
//			MedicineEntity medicine = medicineService.equalMedicineName(s);
//
//
//			if(Objects.isNull(medicine)){
//				System.out.println("图片识别不了");
//				return ;
//			}
//
//			boolean flag = medicationSituationService.determineSave(medicine.getMId());
//			if(flag == false){
//				System.out.println("这个药品近五分钟已经吃过一遍了");
//				return;
//			}
//
//			System.out.println("图片识别成功");
//			System.out.println(medicine);
//			return ;
//	}


//		@Test
//	void contextLoads() throws FileNotFoundException {
//			int i = medicationPictureDao.baseInsert(new MedicationPictureEntity());
//			if(i==0) System.out.println("出错");
//		}

}
