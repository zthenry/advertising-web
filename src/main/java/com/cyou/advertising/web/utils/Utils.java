package com.cyou.advertising.web.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class Utils {
	
	public static void ajaxPrint(HttpServletResponse response, Map<String, Object> map)
			throws IOException {
		String s = JsonUtil.toJson(map);
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();  
	    out.print(s);  
	    out.close();
	}

}
