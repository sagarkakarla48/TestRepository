/**
 * 
 */
package com.ltts.picktolight.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.service.ProductService;
import com.ltts.picktolight.util.CurrentDateTime;

/**
 * @author 90001332
 *
 */
@Controller
public class ProductsBarcodeController {

	private static final Logger logger = LoggerFactory.getLogger(ProductsBarcodeController.class);

	@Autowired
	private ProductService productService;

	//JSP pages
	private static final String orderedProductNumber = "OrderedProductsNumber";
	private static final String ordersExcelUpload = "OrdersExclelUpload";
	private static final String orderListConfirmation = "OrderListConfirmation";
	private static final String productBarcode = "ProductBarcode";



	/**
	 * this method helps to view JSP page to enter number of products to be ordered
	 * @return OrderedProductsNumber.jsp page
	 */
	@RequestMapping(value = "/orderedProductsNumber", method = RequestMethod.POST)
	public String orderedProductsNumber() {
		logger.info("orderedProductsNumber() method in ProductsBarcodeController Class :- Start");
		logger.info("orderedProductsNumber() method in ProductsBarcodeController Class :- End");
		return orderedProductNumber;

	}

	/**
	 * Here we are able to navigate to a jsp page to upload our orders using excel sheet
	 * @return OrdersExclelUpload.jsp page
	 */
	@RequestMapping(value = "/orderExcelUpload", method = RequestMethod.POST)
	public String orderExcelUpload() {
		logger.info("orderExcelUpload() method in ProductsBarcodeController Class :- Start");
		logger.info("orderExcelUpload() method in ProductsBarcodeController Class :- End");
		return ordersExcelUpload;

	}



	/**
	 * Getting orders from excel and sending them to order confirmation page
	 * @param file uploaded file
	 * @param request to set values to access in OrderListConfirmation.jsp page
	 * @return OrderListConfirmation.jsp page
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadOrdersFile", method = RequestMethod.POST)
	public String formUpload(@RequestParam("myFile") MultipartFile file, HttpServletRequest request)
			throws IOException {

		logger.info("formUpload() method in ProductsBarcodeController Class :- Start");

		String result = "";
		try
		{
			File convFile = new File(file.getOriginalFilename());
			convFile.createNewFile();			
			String fileName = convFile.getName();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			List<ProductBarcode> orderList = new ArrayList<ProductBarcode>();

			// comparing extensions of excel sheet
			if (extension.equals("xls")){
				//Getting POJO List From XLS Excel Sheet
				orderList = getUploadXLSExcelData(file);
			}else if(extension.equals("xlsx")){
				//Getting POJO List From XLSX Excel Sheet
				orderList = getUploadXLSXExcelData(file);
			}else if(extension.equalsIgnoreCase("CSV")){
				//Getting POJO List From CSV Excel Sheet
				orderList = getUploadCSVExcelData(file);
			}

			ObjectMapper omapper=new ObjectMapper();
			String list=omapper.writeValueAsString(orderList);
			request.setAttribute("orderslist", list);
			result = orderListConfirmation;	
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("formUpload() method in ProductsBarcodeController Class :- End");
		return result;
	}

	/**
	 * here we are Getting POJO List From XLS Excel Sheet 
	 * @param MultipartFile file
	 * @return ProductBarcode ordersList
	 * @throws Exception
	 */	
	private List<ProductBarcode> getUploadXLSExcelData(MultipartFile file) throws Exception{

		HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());  
		HSSFSheet sheet = wb.getSheetAt(0);  
		HSSFRow row;
		List<ProductBarcode> ordersList = new ArrayList<ProductBarcode>();	
		String color = this.productService.getColorCode();
		for (int i = 1; i<=sheet.getLastRowNum(); i++) {

			row = sheet.getRow(i); 

			if(row!=null){
				ProductBarcode orders=new ProductBarcode();
				for(int j = 0; j < row.getLastCellNum(); j++){
					Cell cell = row.getCell(j);
					String data = "";
					if(cell != null){
						if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
							Double value = cell.getNumericCellValue();
							Integer num = value.intValue();
							data = num.toString();
						}else if(cell.getCellType() == cell.CELL_TYPE_STRING){
							data = cell.getStringCellValue();
						}
					}else{

					}

					if(j == 0){
						orders.setSuperMarketDesc(data);
					}else if(j == 1){
						orders.setSerialNo(data);
					}else if(j == 2){
						orders.setOrderid(data);
					}else if(j == 3){
						orders.setUnitdDesc(data);
					}else if(j == 4){
						orders.setPickerOrder(data);
					}else if(j == 5){
						orders.setBarcode(data);
					}else if(j == 6){
						orders.setQuantity(data);
					}else if(j == 7){
						orders.setiDesc(data);
					}else if(j == 8){
						orders.setDescription(data);
					}else if(j == 9){
						orders.setInstallation(data);
					}
				}
				orders.setColor(color);
				orders.setDatetime(CurrentDateTime.getCurrentTimeStamp());
				ordersList.add(orders);	
			}	
		}
		return ordersList;
	}	

	/**
	 * here we are Getting POJO List From XLSX Excel Sheet 
	 * @param MultipartFile file
	 * @return ProductBarcode ordersList
	 * @throws Exception
	 */	
	private List<ProductBarcode> getUploadXLSXExcelData(MultipartFile file) throws Exception{		 
		XSSFWorkbook  wb = new XSSFWorkbook(file.getInputStream());  
		XSSFSheet sheet = wb.getSheetAt(0);  
		XSSFRow row;
		List<ProductBarcode> ordersList = new ArrayList<ProductBarcode>();	
		String color = this.productService.getColorCode();
		for (int i = 1; i<=sheet.getLastRowNum(); i++) {

			row = sheet.getRow(i); 

			if(row!=null){
				ProductBarcode orders=new ProductBarcode();
				for(int j = 0; j < row.getLastCellNum(); j++){
					Cell cell = row.getCell(j);
					String data = "";
					if(cell != null){
						if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
							Double value = cell.getNumericCellValue();
							Integer num = value.intValue();
							data = num.toString();
						}else if(cell.getCellType() == cell.CELL_TYPE_STRING){
							data = cell.getStringCellValue();
						}
					}else{

					}

					if(j == 0){
						orders.setSuperMarketDesc(data);
					}else if(j == 1){
						orders.setSerialNo(data);
					}else if(j == 2){
						orders.setOrderid(data);
					}else if(j == 3){
						orders.setUnitdDesc(data);
					}else if(j == 4){
						orders.setPickerOrder(data);
					}else if(j == 5){
						orders.setBarcode(data);
					}else if(j == 6){
						orders.setQuantity(data);
					}else if(j == 7){
						orders.setiDesc(data);
					}else if(j == 8){
						orders.setDescription(data);
					}else if(j == 9){
						orders.setInstallation(data);
					}
				}
				orders.setColor(color);
				orders.setDatetime(CurrentDateTime.getCurrentTimeStamp());
				ordersList.add(orders);	
			}	
		}
		return ordersList;
	}

	/**
	 * here we are Getting POJO List From CSV Excel Sheet 
	 * @param MultipartFile file
	 * @return ProductBarcode ordersList
	 * @throws Exception
	 */		
	private List<ProductBarcode> getUploadCSVExcelData(MultipartFile file) throws Exception{	


		List<ProductBarcode> ordersList = new ArrayList<ProductBarcode>();
		String color = this.productService.getColorCode();

		Reader reader;
		try {
			File convFile = new File(file.getOriginalFilename());
			convFile.createNewFile(); 
			FileOutputStream fos = new FileOutputStream(convFile); 
			fos.write(file.getBytes());
			fos.close(); 
			reader = new FileReader(convFile);			

			Iterable<CSVRecord> records;
			records = CSVFormat.EXCEL.parse(reader);
			int i = 0;
			for (CSVRecord record : records) {
				if(i > 0){
					ProductBarcode orders=new ProductBarcode();
					orders.setSuperMarketDesc(record.get(0));
					orders.setSerialNo(record.get(1));
					orders.setOrderid(record.get(2));
					orders.setUnitdDesc(record.get(3));
					orders.setPickerOrder(record.get(4));
					orders.setBarcode(record.get(5));
					orders.setQuantity(record.get(6));
					orders.setiDesc(record.get(7));
					orders.setDescription(record.get(8));
					orders.setInstallation(record.get(9));
					orders.setDatetime(CurrentDateTime.getCurrentTimeStamp());
					orders.setColor(color);
					ordersList.add(orders);						
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


		return ordersList;
	}	


	/**
	 * This method allows us to navigate to a JSP page where
	 * user can enter product and quantity to be ordered
	 * @param request to set values to the ProductBarcode.jsp
	 * @return  ProductBarcode.jsp page where we can scan barcodes and enter quantity of products
	 */
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	public String createOrder(HttpServletRequest request) {	

		logger.info("createOrder() method in ProductsBarcodeController Class :- Start");		

		// here we get the number of products
		String number = request.getParameter("number");

		// here we set the number to request object and we get this number in
		// ProductBarcode page
		request.setAttribute("number", number);

		logger.info("createOrder() method in ProductsBarcodeController Class :- End");		

		return productBarcode;
	}



	/**
	 * to save all the products details of an order
	 * here we are sending those details to addproduct() 
	 * @param productlist holds products details
	 * @return
	 */
	@RequestMapping(value = "/productsSave", method = RequestMethod.POST)
	public @ResponseBody Map<String,List<ProductBarcode>> addProducts(@RequestBody List<ProductBarcode> productlist) {

		logger.info("addProducts() method in ProductsBarcodeController Class :- Start");	
		Map<String,List<ProductBarcode>> returnData=new LinkedHashMap<String,List<ProductBarcode>>();
		try {
			if (productlist != null) {
				returnData = this.productService.addProduct(productlist);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("addProducts() method in ProductsBarcodeController Class :- End");	
		return returnData;
	}



	/**
	 * this method gets the color from database
	 * @return colorcode
	 */
	@RequestMapping(value = "/getcolor", method = RequestMethod.GET)
	public @ResponseBody String getColor(){

		logger.info("getColor() method in ProductsBarcodeController Class :- Start");

		ObjectMapper omapper = new ObjectMapper();
		String json = "";
		try{

			json = omapper.writeValueAsString(this.productService.getColorCode());		

		}catch(Exception e){
			logger.error(e.getMessage());
		}		

		logger.info("getColor() method in ProductsBarcodeController Class :- End");
		return json;
	}

}
