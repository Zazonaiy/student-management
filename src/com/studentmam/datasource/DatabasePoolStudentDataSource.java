package com.studentmam.datasource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class DatabasePoolStudentDataSource {
	private static DatabasePoolStudentDataSource instance = new DatabasePoolStudentDataSource();
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final Integer STEP = 2;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	private int initCount;
	private int minCount;
	private int maxCount;
	//容器
	private Queue<Connection> freeConPool = new LinkedBlockingQueue<>(); //空闲池
	private Queue<Connection> inuseConPool = new LinkedBlockingQueue<>();
	
	private DatabasePoolStudentDataSource() {
		try {
			InputStream is = DatabaseStudentDataSource.class.getClassLoader().getResourceAsStream("database.properties");
			Properties prop = new Properties();
			prop.load(is);
			dbUrl = prop.getProperty("db_url");
			dbUser = prop.getProperty("db_user");
			dbPass = prop.getProperty("db_pass");
			initCount = Integer.valueOf(prop.getProperty("init_count","5"));//第二个参数为默认值
			minCount = Integer.valueOf(prop.getProperty("min_count", "3"));
			maxCount = Integer.valueOf(prop.getProperty("max_count", "10"));
			is.close();
			
			//初始化连接池
			initPool();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 初始化连接池
	 * @throws Exception
	 */
	private void initPool() throws Exception{
		for (int i = 0; i < initCount; i++) {
			freeConPool.add(createConnection());
		}
		System.out.println("连接池初始化完成！ 初始连接参数: " + initCount + " 最小连接数: " + minCount + " 最大连接数: " + maxCount);
	}
	
	private Connection createConnection() throws Exception {
		Class.forName(DRIVER).newInstance();
		Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
		return con;
	}
	
	/**
	 * 自动增长空闲连接数
	 * @return
	 */
	private void increasePool() throws Exception{
		for (int i = 0; i < STEP; i++) {
			freeConPool.add(createConnection());
		}
	}
	
	/**
	 * 从空闲池取走连接
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection(){
		Connection con = freeConPool.poll();
		try {
			if (con == null) {
				System.err.println("当前没有可用的连接了，正在申请新连接...");
				if (inuseConPool.size() >= maxCount) {
					//连接数量达到上限，不能创建新连接
					System.err.println("已达到最大连接数，无法申请新连接，等待中...");
					Thread.sleep((long) (Math.random() * 1000));
					return getConnection(); //再次尝试获取连接 递归
				}
				//连接数未达到上线，创建新连接放入空闲池，再重新去取
				increasePool();
				return getConnection();
			}
			
			//拿到了可用的连接，返回
			inuseConPool.add(con);
			System.out.println("获取连接成功! con=" + con);
			return con;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 把连接从占用队转移到空闲队，以供其他线程调用
	 * @param con
	 * @throws Exception
	 */
	synchronized public void closeConnection(Connection con) throws Exception{
		Iterator<Connection> it = inuseConPool.iterator();
		while (it.hasNext()) {
			Connection connection = it.next();
			if (con == connection) {	//比较引用
				it.remove();
				break;
			}
		}
		//将该链接放入到空闲池中
		freeConPool.add(con);
		//如果空闲池中的连接数比minCount多，释放一个空闲池中的连接
		if (freeConPool.size() > minCount) {
			Connection connection = freeConPool.poll();
			connection.close();
		}
		System.out.println("连接关闭成功，当前池中可用连接数为" + freeConPool.size());
	}
	
	/**
	 * 销毁连接池
	 * 清空占用池和空闲池，并释放掉里面所有的连接
	 * @throws Exception
	 */
	synchronized public void closePool() throws Exception{
		while (!inuseConPool.isEmpty()) {
			Connection con = inuseConPool.poll();
			con.close();
			con = null;
		}
		while (!freeConPool.isEmpty()) {
			Connection con = freeConPool.poll();
			con.close();
			con = null;
		}
		System.out.println("连接池已销毁! ");
	}
	
	public static DatabasePoolStudentDataSource getInstance() {
		return instance;
	}
}
