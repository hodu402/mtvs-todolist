package com.todolist.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    private static final HikariDataSource dataSource;
    //클래스 로딩 시 한번만 실행되는 정적블록
    static {
        try{
            //config.properties에서 DB연결 정보를 가져옴

            Properties props = new Properties();
            //config.properties파일을 메서드로 읽어와서 props에 넣음
            props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));

            //DB 연결 정보를 HikariConfig에 설정해서 커넥션 풀 구성을 시작
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            //커넥션 풀 옵션 설정
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(1800000);
            config.setConnectionTimeout(2000);

            //위 설정을 기반으로 커넥션 풀 생성
            dataSource = new HikariDataSource(config);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    //DB연결을 요청하는 메서드, 호출로 HikariCP가 커넥션을 하나 빌려줌
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    //앱을 종료했을 때 커넥션 풀을 닫음
    public static void close(){
        if(dataSource != null){
            dataSource.close();
        }
    }

    //커넥션 풀 상태확인
    public static void printConnectionPoolStatus() {
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("[hikariCp 커넥션 풀 상태 ]");
        System.out.println("총 커넥션 수 (total Connections) : " + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 수 (Active Connections) : " + poolMXBean.getActiveConnections());
        System.out.println("유휴 커넥션 수 (IDLE Connections) : " + poolMXBean.getIdleConnections());
        System.out.println("대기 중인 커넥션 요청 수 (Pending Threads) : " + poolMXBean.getThreadsAwaitingConnection());
    }
}

/*커넥션 풀은 미리 여러 개의 DB 연결을 만들어서 풀(pool, 수영장)에 보관
*  누군가 DB를 사용하려고 하면 그중 하나를 꺼내서 쓰고, 다 쓰면 다시 넣음
*
* HikariCP는 자바에서 가장 빠르고 가벼운 커넥션 풀 라이브러리*/
