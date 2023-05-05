package com.Interface;
import java.sql.*;

public class BancoDados implements InterfaceBancoDados {
		private Connection c;

		public void conectar(String db_url, String db_user, String db_password) {
		    try {
		        c = DriverManager.getConnection(db_url, db_user, db_password);
		        System.out.println("Conexão estabelecida com sucesso!");
		    } catch (SQLException e) {
		        System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
		    }
		}

		public void desconectar() {
		    try {
		        c.close();
		        System.out.println("Desconexão realizada com sucesso!");
		    } catch (SQLException e) {
		        System.out.println("Erro ao desconectar do banco de dados: " + e.getMessage());
		    }
		}

		public void consultar(String db_query) {
		    try {
		    	PreparedStatement ps = c.prepareStatement(db_query);
		        ResultSet rs = ps.executeQuery(db_query);

		        while (rs.next()) {
		        	System.out.println("ID: " + rs.getString(1)+"\tNome: "+rs.getString(2) + "\tEmail: "+rs.getString(3) + "\tCargo: " + rs.getString(4));
		        }

		        ps.close();
		        rs.close();
		    } catch (SQLException e) {
		        System.out.println("Erro ao realizar a consulta: " + e.getMessage());
		    }
		}

		public int inserirAlterarExcluir(String db_query) {
		    int numLinhasAfetadas = 0;

		    try {
		    	PreparedStatement ps = c.prepareStatement(db_query);
		        numLinhasAfetadas = ps.executeUpdate();

		        System.out.println("Operação realizada com sucesso! " + numLinhasAfetadas + " linhas foram afetadas.");

		        ps.close();
		    } catch (SQLException e) {
		        System.out.println("Erro ao realizar a operação: " + e.getMessage());
		    }

		    return numLinhasAfetadas;
		}
		
		public static void main(String[] args) {
			BancoDados bd = new BancoDados();
			String db_url = "jdbc:mysql://localhost:3306/reuniao";
			String db_user = "root";
			String db_password = "";

			//Conectar banco de dados
			bd.conectar(db_url, db_user, db_password);

			//Inserir registros na tabela
			bd.inserirAlterarExcluir("INSERT INTO pessoa (nome, email, cargo) values ('Julia','julia@yahoo.com','Administradora'),('Raul','raul@yahoo.com','Engenheiro')");

			//Consultar Valores
			bd.consultar("SELECT * FROM pessoa");

			//Desconectar banco de dados
			bd.desconectar();
			
		}
	

}
