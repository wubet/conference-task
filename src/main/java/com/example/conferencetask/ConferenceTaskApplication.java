package com.example.conferencetask;

import com.example.conferencetask.config.ConferenceTaskConfigurer;
import com.example.conferencetask.config.MyContextListener;
import com.sun.source.util.TaskListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableTask
public class ConferenceTaskApplication {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.pasaword}")
	private String password;
	@Value("${spring.datasource.driver-class-name}")
	private String driver;
	@Value("${spring.datasource.platform}")
	private String platform;

//	@Autowired
	private Environment env;

	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;


	public static void main(String[] args) {
		SpringApplication.run(ConferenceTaskApplication.class, args);
	}

	private static void verifyConnection(String uri, String name, String pass) throws SQLException {

		Connection con = DriverManager.getConnection(uri, name, pass);
		statement = con.createStatement();
		// Result set get the result of the SQL query
		resultSet = statement
				.executeQuery("select * from feedback.comments");
		writeResultSet(resultSet);

	}

	private static void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("summary: " + summary);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}


	@Bean
	public TollProcessingTask tollProcessingTask() {
		return new TollProcessingTask();
	}

	public class TollProcessingTask implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {

			//verifyConnection(url, username, password);

			if (null != args) {
				System.out.println("parameter length is " + args.length);
			}
			else{
				System.out.println("parameter length is " + "0");
			}

			String stationId = args[0];
			String licensePlate = args[1];
			String timestamp = args[2];

			System.out.print(
					"station Id is " + stationId + ", plate is " + licensePlate + ", timestamp is "
							+ timestamp);
		}
	}

}
