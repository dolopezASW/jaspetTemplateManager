package com.asw.manager.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jasperreports.engine.JRParameter;

public class JRParameterManager implements JRPropertyManager<JRParameter>{


	@Override
	public JRParameter[] sortArrayProperty(JRParameter[] propertyArr) {
		List<JRParameter> paramsArr = Arrays.asList(propertyArr);
		if(propertyArr==null) {
			return null;
		}else {
			Arrays.sort(paramsArr.toArray());
			return (JRParameter[]) new ArrayList(paramsArr).toArray();
		}
	}

}
