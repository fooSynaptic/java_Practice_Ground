package com.itranswarp.learnjava;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Template {

	final String template;
	final Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");

	public Template(String template) {
		this.template = template;
	}

	public String render(Map<String, Object> data) {
		Matcher m = pattern.matcher(template);
		// TODO:
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			for (String key : data.keySet()) {
				if (("${" + key + "}").equals(template.substring(m.start(), m.end()))){
					m.appendReplacement(sb, (String) data.get(key));
					break;
				}

			}

		}

		m.appendTail(sb);

		return sb.toString();

		}

	}