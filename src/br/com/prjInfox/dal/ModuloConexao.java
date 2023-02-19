package br.com.prjInfox.dal;
import java.sql.*;
/**
 * @author jailson R Lima
 */
public class ModuloConexao {
   //Metodo resposavél pela conexão com o banco de dados
   public static Connection conector(){
   java.sql.Connection conexao = null;
   //Linha a baixo chama o drive de coneção
   String drive = "com.mysql.cj.jdbc.Driver";//tipo de banco de dados
   //Armazenando irformações reerentes ao banco
   String url = "jdbc:mysql://localhost:3306/bdordemservico?serverTimezone=UTC"; //nome e local do banco de dados
   String user = "root" ; //usuário do banco de dados    
   String password = "mysqlkey"; //Senha 
   
        try {
           Class.forName(drive);//Executa o arquivo do drive
           conexao = DriverManager.getConnection(url, user, password); //Recebe os dados de conexão do Banco de Dados        
           return conexao;
       } catch (ClassNotFoundException | SQLException e) {
           return null;
       }
   }
}
