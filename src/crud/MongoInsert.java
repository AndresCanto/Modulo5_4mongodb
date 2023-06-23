package crud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoInsert 
{
	MongoClient mongo = null;
	MongoDatabase db = null;
	String tabla = "trabajador";
	
	public static void main(String[] args)
	{
		MongoInsert mi = new MongoInsert();
		System.out.println("Prueba conexión MongoDB");
		mi.mongo = new MongoClient("localhost",27017);
		Scanner sc = new Scanner(System.in);
		if(mi.mongo!=null)
		{
			mi.db = mi.mongo.getDatabase("EmpresaX"); // un pick a la db
			
//			System.out.print("Nombre: ");
//			String nom = sc.nextLine();
//			System.out.print("Apellidos: ");
//			String aps = sc.nextLine();
//			System.out.print("Edad: ");
//			int ed = Integer.parseInt(sc.nextLine());
//			//insert
//			mi.insertarDocumento(nom,aps,ed);
			
			//select
			mi.listarDocumentos();
			
			//select where
//			System.out.println("Nombre a buscar:");
//			String find = sc.nextLine();
//			mi.listarFiltrado(find);
			
			//update
//			System.out.println("Trabajador a actualizar: ");
//			String upnom = sc.nextLine();
//			System.out.println("Nueva edad:");
//			int upedad = Integer.parseInt(sc.nextLine());
//			mi.updateEdadByName(upnom,upedad);
			
//			delete mayor que
//			System.out.println("Edad maxima: ");
//			int edMax = Integer.parseInt(sc.nextLine());
//			mi.deleteTrabMayor(edMax);
			
//			delete en lista
			List<String> listaBorrar = new ArrayList<String>();
			System.out.println("Primera persona a eliminar (apellidos): ");
			listaBorrar.add(sc.nextLine());
			System.out.println("Segunda persona a eliminar (apellidos): ");
			listaBorrar.add(sc.nextLine());
			mi.deleteList(listaBorrar);
		}
	}
	
	private void insertarDocumento(String nombre, String apellidos, int edad) 
	{
		MongoCollection<Document> table = db.getCollection(tabla);
		
		Document doc = new Document();
		doc.put("nombre", nombre);
		doc.put("apellidos", apellidos);
		doc.put("edad", edad);
		doc.put("fecha", new Date());
		
		table.insertOne(doc);
	}
	
	private void listarDocumentos() 
	{
		System.out.println("Listando todos los documentos");
		
		MongoCollection<Document> table = db.getCollection(tabla);
		
		MongoCursor<Document> cursor = table.find().iterator();
		while(cursor.hasNext())
		{
			Document doc = cursor.next();
//			System.out.println(doc.get("nombre")+"\t"+doc.get("apellidos")+"\t"+doc.get("edad")+" edad \t ("+doc.get("fecha")+")");
			System.out.println(doc.toJson());
		}
	}
	
	private void listarFiltrado(String nombre) 
	{
		System.out.println("Listando resultado filtrado");
		
		MongoCollection<Document> table = db.getCollection(tabla);
		
		BasicDBObject query =  new BasicDBObject();
		query.put("nombre", nombre);
		
		MongoCursor<Document> cursor = table.find(query).iterator();
		while(cursor.hasNext())
		{
			Document doc = cursor.next();
//			System.out.println(doc.get("nombre")+"\t"+doc.get("apellidos")+"\t"+doc.get("edad")+" edad \t ("+doc.get("fecha")+")");
			System.out.println(doc.toJson());
		}
	}
	
	private void updateEdadByName(String upnom, int upedad) 
	{
		System.out.println("Listando resultado filtrado");
		
		MongoCollection<Document> table = db.getCollection(tabla);
		
		BasicDBObject updateAnios =  new BasicDBObject();
		updateAnios.append("$set", new BasicDBObject().append("edad", upedad));
		
		BasicDBObject searchByID = new BasicDBObject();
		searchByID.append("nombre", upnom);
		
		table.updateMany(searchByID, updateAnios);
		listarDocumentos();
	}
	
	private void deleteTrabMayor(int edad) 
	{
		MongoCollection<Document> table = db.getCollection(tabla);
		BasicDBObject query =  new BasicDBObject();
		query.put("edad", new BasicDBObject("$gt",edad));
		table.deleteMany(query);
		listarDocumentos();		
	}
	
	private void deleteList(List<String> lista) 
	{
		MongoCollection<Document> table = db.getCollection(tabla);
		BasicDBObject query =  new BasicDBObject();
		query.put("apellidos", new BasicDBObject("$in",lista));
		table.deleteMany(query);
		listarDocumentos();		
	}
}