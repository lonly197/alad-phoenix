import me.alad.phoenix.PhoenixUtil;
import me.alad.phoenix.pool.ConnectionManager;
import org.apache.phoenix.jdbc.PhoenixConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ExportData {
	public static void main(String[] args) throws Exception {

		String filePath = "D:/Text/test.txt";
		if (args.length > 0) {
			filePath = args[0];
		}
		PrintWriter out = new PrintWriter(filePath);

		final ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext-phoenix.xml");

		ConnectionManager connManager = (ConnectionManager) context
				.getBean("connectionManagerBean");
		PhoenixConnection conn = connManager.getConn();
		List<Map<String, Object>> list = PhoenixUtil.executeQuery(conn,
				"select * from tag.hottags where taskid=99");

		StringBuffer sb = new StringBuffer();
		for (Map<String, Object> row : list) {
			for (Map.Entry<String, Object> entry : row.entrySet()) {
				sb.append(entry.getValue().toString() + "ï");
			}
			out.println(sb.substring(0, sb.length() - 1));
			sb.setLength(0);
		}

		connManager.closeConn(conn);
		out.close();
		
		System.out.println("====完成====");

	}

	private final static void close(AutoCloseable x) {
		if (x != null) {
			try {
				x.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
