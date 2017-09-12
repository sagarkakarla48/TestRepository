/**
 * 
 */
package com.ltts.picktolight.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.APKFileDetails;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.service.APKFileDetailsService;
import com.ltts.picktolight.service.BatteryInfoService;
import com.ltts.picktolight.service.UploadProductService;
import com.ltts.picktolight.util.CurrentDateTime;
import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001334
 *
 */

@Controller
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private UploadProductService uploadProductService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private APKFileDetailsService apkFileDetailsService;
	@Autowired
	BatteryInfoService batteryInfoService;

	// String constants to jsp page names and normal strings
	private static final String uploadExcel = "UploadExcel";
	private static final String resultExcel = "ResultExcel";
	private static final String uploadAPK = "UploadAPK";
	private static final String selectUpload = "SelectUpload";
	private static final String versionNotValid = "Version Not Valid";

	/**
	 * This method helps us to open web page to upload products Excel sheet	
	 * @return uploadexcel.jsp
	 */
	@RequestMapping(value = "/productsexcel", method = RequestMethod.POST)
	public String productsExcel() {
		logger.info("productsExcel() method In FileUploadController Class :- Start");
		logger.info("productsExcel() method In FileUploadController Class :- End");
		return uploadExcel;
	}


	/**
	 * here we are uploading an products excel sheet and sending information to 
	 * resultexcel.jsp file to show the content
	 * @param file uploaded excel file
	 * @param request to set values to UI or  resultexcel.jsp
	 * @return String resultexcel.jsp
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String formUpload(@RequestParam("myFile") MultipartFile file, HttpServletRequest request,Model model)throws IOException {

		logger.info("formUpload() method In FileUploadController Class :- Start");

		String result = "";
		try
		{
			File convFile = new File(file.getOriginalFilename());
			convFile.createNewFile();			
			String fileName = convFile.getName();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			List<UploadProducts> productList = new ArrayList<UploadProducts>();

			// comparing extensions of excel sheet
			if (extension.equals("xls")){
				//Getting POJO List From XLS Excel Sheet
				productList = getUploadXLSExcelData(file);
			}else if(extension.equals("xlsx")){
				//Getting POJO List From XLSX Excel Sheet
				productList = getUploadXLSXExcelData(file);
			}else if(extension.equalsIgnoreCase("CSV")){
				//Getting POJO List From CSV Excel Sheet
				productList = getUploadCSVExcelData(file);
			}

			ObjectMapper omapper=new ObjectMapper();
			String list=omapper.writeValueAsString(productList);
			request.setAttribute("list", list);
			result = resultExcel;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		logger.info("formUpload() method In FileUploadController Class :- End");
		return result;
	}


	/**
	 * here we are Getting POJO List From XLS Excel Sheet 
	 * @param MultipartFile file
	 * @return UploadProducts productList
	 * @throws Exception
	 */	
	private List<UploadProducts> getUploadXLSExcelData(MultipartFile file) throws Exception{
		// Creates a workbook object from the uploaded excelfile
		HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
		// Creates a worksheet object representing the first sheet
		HSSFSheet sheet = wb.getSheetAt(0);  
		HSSFRow row;
		List<UploadProducts> productList = new ArrayList<UploadProducts>();
		// Reads the data in excel file until last row is encountered
		for (int i = 1; i<=sheet.getLastRowNum(); i++) {
			// Creates an object representing a single row in excel
			row = sheet.getRow(i); 

			if(row!=null){
				UploadProducts uploadProduct=new UploadProducts();

				// Reads the data in excel file until last column is encountered
				for(int j = 0; j < row.getLastCellNum(); j++){

					// Creates an object representing a single column  in excel
					Cell cell = row.getCell(j);
					String data = "";
					if(cell != null){
						if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
							//gets the data from excel cell
							Double value = cell.getNumericCellValue();
							Integer num = value.intValue();
							data = num.toString();
						}else if(cell.getCellType() == cell.CELL_TYPE_STRING){
							data = cell.getStringCellValue();
						}
					}else{

					}

					// Sets the Read data to the model(POJO) class
					if(j == 0){
						uploadProduct.setProductId(data);
					}else if(j == 1){
						uploadProduct.setDescription(data);
					}else if(j == 2){
						uploadProduct.setSuperDescription(data);
					}else if(j == 3){
						uploadProduct.setRackNum(data);
					}else if(j == 4){
						uploadProduct.setPickOrder(data);
					}
				}
				//uploadProduct.setDatetime(CurrentDateTime.getCurrentTimeStamp());
				productList.add(uploadProduct);	
			}	
		}
		return productList;
	}

	/**
	 * here we are Getting POJO List From XLSX Excel Sheet 
	 * @param MultipartFile file
	 * @return UploadProducts productList
	 * @throws Exception
	 */	
	private List<UploadProducts> getUploadXLSXExcelData(MultipartFile file) throws Exception{	
		// Creates a workbook object from the uploaded excelfile
		XSSFWorkbook  wb = new XSSFWorkbook(file.getInputStream()); 
		// Creates a worksheet object representing the first sheet
		XSSFSheet sheet = wb.getSheetAt(0);  
		XSSFRow row;
		List<UploadProducts> productList = new ArrayList<UploadProducts>();

		// Reads the data in excel file until last row is encountered
		for (int i = 1; i<=sheet.getLastRowNum(); i++) {
			// Creates an object representing a single row in excel
			row = sheet.getRow(i); 

			if(row!=null){
				UploadProducts uploadProduct=new UploadProducts();
				// Reads the data in excel file until last column is encountered
				for(int j = 0; j < row.getLastCellNum(); j++){
					// Creates an object representing a single column  in excel
					Cell cell = row.getCell(j);
					String data = "";
					if(cell != null){
						if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
							//gets the data from excel cell
							Double value = cell.getNumericCellValue();
							Integer num = value.intValue();
							data = num.toString();
						}else if(cell.getCellType() == cell.CELL_TYPE_STRING){
							data = cell.getStringCellValue();
						}
					}else{

					}

					// Sets the Read data to the model(POJO) class
					if(j == 0){
						uploadProduct.setProductId(data);
					}else if(j == 1){
						uploadProduct.setDescription(data);
					}else if(j == 2){
						uploadProduct.setSuperDescription(data);
					}else if(j == 3){
						uploadProduct.setRackNum(data);
					}else if(j == 4){
						uploadProduct.setPickOrder(data);
					}
				}
				//uploadProduct.setDatetime(CurrentDateTime.getCurrentTimeStamp());
				productList.add(uploadProduct);	
			}	
		}
		return productList;
	}


	/**
	 * here we are Getting POJO List From CSV Excel Sheet 
	 * @param MultipartFile file
	 * @return UploadProducts productList
	 * @throws Exception
	 */	
	private List<UploadProducts> getUploadCSVExcelData(MultipartFile file) throws Exception{	


		List<UploadProducts> productList = new ArrayList<UploadProducts>();

		Reader in;
		try {
			File convFile = new File(file.getOriginalFilename());
			convFile.createNewFile(); 
			FileOutputStream fos = new FileOutputStream(convFile); 
			fos.write(file.getBytes());
			fos.close(); 
			in = new FileReader(convFile);

			Iterable<CSVRecord> records;
			records = CSVFormat.EXCEL.parse(in);
			int i = 0;
			for (CSVRecord record : records) {
				if(i > 0){
					UploadProducts uploadProduct=new UploadProducts();
					uploadProduct.setProductId(record.get(0));
					uploadProduct.setDescription(record.get(1));
					uploadProduct.setSuperDescription(record.get(2));
					uploadProduct.setRackNum(record.get(3));
					uploadProduct.setPickOrder(record.get(4));
					//uploadProduct.setDatetime(CurrentDateTime.getCurrentTimeStamp());
					productList.add(uploadProduct);
				}
				i++;
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return productList;
	}

	/**
	 * to open uploadexcel.jsp file where we can upload an excel sheet
	 * @return String uploadexcel.jsp
	 */
	@RequestMapping(value = "/productsExcelUpload", method = RequestMethod.POST)
	public String productsExcelUpload() {
		logger.info("productsExcelUpload() method In FileUploadController Class :- Start");
		logger.info("productsExcelUpload() method In FileUploadController Class :- End");
		return uploadExcel;

	}


	/**
	 * To open a web page where we can see upload excel sheet or APK file
	 * @return SelectUpload.jsp page
	 */
	@RequestMapping(value = "/selectUpload", method = RequestMethod.POST)
	public String selectUpload() {
		logger.info("selectUpload() method In AllActionsController Class :- Start");
		logger.info("selectUpload() method In AllActionsController Class :- End");
		return selectUpload;
	}

	/**
	 * To open a web page where we can upload products excel sheet
	 * @return uploadexcel.jsp page
	 */
	@RequestMapping(value = "/uploadProductsExcel", method = RequestMethod.POST)
	public String uploadProductsExcel() {
		logger.info("uploadProductsExcel() method In AllActionsController Class :- Start");
		logger.info("uploadProductsExcel() method In AllActionsController Class :- End");
		return uploadExcel;
	}


	/**
	 * after uploading products excel sheet this method will be called
	 * it is responsible to send the product details to database
	 * @param productlist POJO list which holds the details of all products
	 * @return List POJO class list
	 */
	@RequestMapping(value = "/saveProducts", method = RequestMethod.POST)
	public @ResponseBody List<UploadProducts> saveOrders(@RequestBody List<UploadProducts> productlist) {

		logger.info("saveOrders() method In FileUploadController Class :- Start");

		try {

			if (productlist != null && productlist.size() > 0) {

				/**
				 * Here we are checking for processing orders and if any orders
				 * are in process we need to wait until they complete because we
				 * need to delete all the data before uploading the Product-Rack
				 * map sheet.
				 */

				/*List<ProductBarcode> barcodeOrdersList = uploadProductService.getAllProductOrdersData();
				if (barcodeOrdersList != null && barcodeOrdersList.size() > 0) {
					productlist = null;
				} else {*/
				// here we uploading the products-rack map sheet data
				this.uploadProductService.uploadProduct(productlist);

				// here we saving the product-rack map values for
				// maintaining the mobile battery info corresponding to
				// particular rack
				this.batteryInfoService.saveBatteryLevels(productlist);
				//}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("saveOrders() method In FileUploadController Class :- End");
		return productlist;
	}


	/**
	 * opens a page to upload apk file
	 * @param model to directly bind values to uploadAPK.jsp page
	 * @return uploadAPK.jsp page
	 */
	@RequestMapping(value = "/uploadapk", method = RequestMethod.POST)
	public String apkUpload(Model model) {
		logger.info("apkUpload() method In FileUploadController Class :- Start");

		try {
			APKFileDetails apkdetails = new APKFileDetails();

			// getting apk details to show existing version in upload Apk page
			apkdetails = this.apkFileDetailsService.getAPKDetails(apkdetails);
			apkdetails.setExistingversion(apkdetails.getVersion());
			apkdetails.setVersion(null);

			// here we are setting APKFileDetails object to model to
			// automatically set value to existing version field
			model.addAttribute("apkdetails", apkdetails);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		logger.info("apkUpload() method In FileUploadController Class :- End");
		return uploadAPK;
	}


	/**
	 * saving apk file in a folder and storing its details in the database
	 * @param file file uploaded by user
	 * @param apkfileobj POJO class object which holds info related to APK file
	 * @param request
	 * @return status uploaded or not
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadApk", method = RequestMethod.POST)
	public @ResponseBody String uploadApkFile(@RequestParam("myFile") MultipartFile file, APKFileDetails apkfileobj,
			HttpServletRequest request) {
		logger.info("uploadApkFile() method In FileUploadController Class :- Start");

		String filename = file.getOriginalFilename();
		apkfileobj.setApkname(filename);
		apkfileobj.setDatetime(CurrentDateTime.getCurrentTimeStamp());
		File name = new File(filename);
		String result = "";
		boolean saveStatus = true;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				System.out.println("root path" + rootPath);
				String contextpath = servletContext.getRealPath("/");
				// path where APK file stores
				File dir = new File(contextpath + "//resources//" + "apkFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				// String filepath=serverFile.getAbsolutePath();
				System.out.println("Server File Location=" + serverFile.getAbsolutePath());

				System.out.println("You successfully uploaded file=" + name);
				APKFileDetails apkdetails = new APKFileDetails();

				// get apk details to check current version with existing
				// version
				apkdetails = this.apkFileDetailsService.getAPKDetails(apkfileobj);
				if (apkdetails != null && apkdetails.getVersion()!=null) {
					float previousVersion = Float.valueOf(apkdetails.getVersion());
					float newVerion = Float.valueOf(apkfileobj.getVersion());
					if (previousVersion >= newVerion || newVerion == 0.0) {
						saveStatus = false;
					}
				}

				if (saveStatus) {
					// sending APK file details to ServiceImplementation class
					this.apkFileDetailsService.saveAPKDetails(apkfileobj);
					result = StringConstatns.success;
				} else {
					result = versionNotValid;
				}
			} catch (Exception e) {
				result = "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			result = StringConstatns.failure;
		}

		logger.info("uploadApkFile() method In FileUploadController Class :- End");
		return result;
	}


	/**
	 * to know not working mobile devices 
	 * @return products corresponding to mobile devices which are not working
	 */
	@RequestMapping(value = "/getProductInfoForDeviceTracking", method = RequestMethod.POST)
	public @ResponseBody String getProductInfoForDeviceTracking() {

		logger.info("getProductInfoForDeviceTracking() method in FileUploadController Class :- Start");	
		List<UploadProducts> productinfolist=new ArrayList<UploadProducts>();
		List<String> racklist=new ArrayList<String>();
		UploadProducts productinfo=new UploadProducts();
		String json=null;
		try {
			productinfolist=this.uploadProductService.getProductInfoForDeviceTracking(productinfo);
			if(productinfolist!=null && productinfolist.size()>0){
				for(UploadProducts product:productinfolist){
					racklist.add(product.getRackNum());
				}
				ObjectMapper omapper=new ObjectMapper();
				json=omapper.writeValueAsString(racklist);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getProductInfoForDeviceTracking() method in FileUploadController Class :- End");	
		return json;
	}

}
