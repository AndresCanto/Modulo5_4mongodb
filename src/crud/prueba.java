package crud;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoIterable;

public class prueba 
{
	public static void main(String[] args) 
	{
		System.out.println("Prueba conexion MongoDB");
		MongoClient mongo = crearConexion();
		
		if(mongo!=null)
		{
			System.out.println("Lista de bases de datos:");
			printDatabases(mongo);
		}
		else
		{
			System.out.println("Error: Conexión no establecida");
		}
		
	}
	
	private static MongoClient crearConexion() 
	{
		MongoClient mongo = null;
		mongo = new MongoClient("localhost",27017);
		return mongo;
	}
	
	private static void printDatabases(MongoClient mongo) 
	{
		ListDatabasesIterable<Document> dbs = mongo.listDatabases();
		for(Document db:dbs)
		{
			System.out.println("-"+db.getString("name"));
		}
	}
}
