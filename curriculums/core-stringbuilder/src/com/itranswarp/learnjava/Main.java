package com.itranswarp.learnjava;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) {
		String[] fields = { "name", "position", "salary" };
		String table = "employee";
		String insert = buildInsertSql(table, fields);
		System.out.println(insert);
		System.out.println(
				"INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)".equals(insert) ? "测试成功" : "测试失败");
	}

	
	
    static String buildInsertSql(String table, String[] fields) {
        // TODO:
        StringBuilder ans = new StringBuilder();
        String f = ") VALUES (?, ?, ?)";
        ans.append("INSERT INTO employee (");
        for (String x: fields)
            ans.append(x + ", ");
        ans.delete(ans.length()-2, ans.length());
        ans.append(f);
        return ans.toString();
    }

}
