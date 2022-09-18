//package com.testedelivey.bancoDeDados;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import javax.swing.JOptionPane;
//
//public class Conexao {
//
//	public static Connection conexao;
//	public static final String DRIVER_NAO_ENCONTRADO = "Não foi possível encontrar o driver de acesso ao banco de dados";
//	public static final String CONEXAO_NAO_REALIZADA = "Não foi possível conectar com o banco de dados";
//
//	public static Connection getConexao() {
//		try{
//			if(conexao == null){
//				Class.forName("org.firebirdsql.jdbc.FBDriver");
//				conexao = DriverManager.getConnection("jdbc:firebirdsql://localhost/" +
//						System.getProperty("user.dir") + "/BASE.FDB", "SYSDBA", "masterkey");
//
//			}
//			return conexao;
//			} catch (ClassNotFoundException e) {
//				JOptionPane.showMessageDialog(null, DRIVER_NAO_ENCONTRADO);
//				e.printStackTrace();
//				return null;
//			} catch (SQLException e) {
//				JOptionPane.showMessageDialog(null, CONEXAO_NAO_REALIZADA);
//				e.printStackTrace();
//				return null;
//			}
//		}
//
//}
