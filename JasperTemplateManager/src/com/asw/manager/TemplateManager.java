package com.asw.manager;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.asw.exceptions.InvalidParamException;
import com.asw.exceptions.TemplateNotFoundException;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class TemplateManager {
	
//private static final Logger logger = LogManager.getLogger(TemplateManager.class);
	
	/**
	 * Name of the jasper jrmxl template
	 */
	String template;
	Map<String,Object> params;
	
	public TemplateManager(String template) {
		this.template=template;
	}
	
	public TemplateManager(String template, Map<String,Object> fillParams) {
		this.template=template;
		this.params = fillParams;
	}
	
	/**
	 * Get the list of parameters of the 
	 * jasper report section <<parameters>>
	 * @param templateName
	 * @return
	 */
	public  JRParameter[] getParametros(String templateName) {
		try {
			return this.getReport().getParameters();
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
			return new JRParameter[0];
		}
	}
	
	/**
	 * Get the list of fields of the 
	 * jasper report section <<fields>>
	 * @param templateName
	 * @return
	 */
	public JRField[] getFields() {
		try {
			return this.getReport().getFields();
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
			return new JRField[0];
		}
	}
	
	
	public JasperReport getReport() throws TemplateNotFoundException{
		JasperReport jasperReport;
		try {
			final InputStream reportInputStream = getClass().getResourceAsStream(this.template);
			JasperDesign jasperDesign = null;
			jasperDesign = JRXmlLoader.load(reportInputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		}catch(Exception jrex) {
			throw new TemplateNotFoundException();
		}
		return jasperReport;
	}
	
	public JasperReport getReportByPath() throws TemplateNotFoundException{
		JasperReport jasperReport;
		try {
			final InputStream reportInputStream = Files.newInputStream(Paths.get(this.template));
			JasperDesign jasperDesign = null;
			jasperDesign = JRXmlLoader.load(reportInputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		}catch(Exception jrex) {
			throw new TemplateNotFoundException();
		}
		return jasperReport;
	}
	
	/**
	 * Convert the list of fields to a hashmap
	 * @param fields
	 * @return
	 */
	public Map<String,Object> convertToMap(JRField[] vector) throws InvalidParamException{
		Map<String, Object> paramsMap = new HashMap<>();
		try {
			for(JRField field : vector) {
				paramsMap.put(field.getName(), null);
			}
		}catch(Exception e) {
			throw new InvalidParamException();
		}
		return paramsMap;
	}
	
	/**
	 * Convert the list of parameters to a hashmap
	 * @param fields
	 * @return
	 */
	public Map<String,Object> convertToMap(JRParameter[] vector) throws InvalidParamException{
		Map<String, Object> paramsMap = new HashMap<>();
		try {
			for(JRParameter parameter : vector) {
				paramsMap.put(parameter.getName(), null);
			}
		}catch(Exception e) {
			throw new InvalidParamException();
		}
		return paramsMap;
	}
	
	
	public  void printParametros() {
		JRParameter params[] = this.getParametros(this.template);
		for(JRParameter p : params) {
			//logger.debug(p.getName());
			System.out.println(p.getName());
		}
	}
	
	public  void printFields() {
		JRField params[] = this.getFields();
		for(JRField f : params) {
			//logger.debug(f.getName());
			System.out.println(f.getName());
		}
	}
	
	/**
	 * Generate an object of type JasperReport from the template name
	 * @return
	 * @throws JRException
	 */
	public JasperReport generateReport() throws JRException{
		final InputStream reportInputStream = getClass().getResourceAsStream(this.template);
		JasperDesign jasperDesign = null;
		try {
			jasperDesign = JRXmlLoader.load(reportInputStream);
		} catch(Exception e) {
			//logger.error("Error loadTemplate", e);
			return null;
		}
		return JasperCompileManager.compileReport(jasperDesign);
	}
	
	/**
	 * Returns document in form of byte array
	 * @param bean
	 * @return
	 */
	public byte[] buildReport(Object bean) {
		ReportDataWrapper reportDataWrapper = new ReportDataWrapper(bean);
		try {
			JasperReport jasperReport = this.generateReport();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportDataWrapper.castDataSource(bean),new JREmptyDataSource());
			return JasperExportManager.exportReportToPdf(jasperPrint);
		}catch(Exception ex) {
			return null;
		}
	}

}
