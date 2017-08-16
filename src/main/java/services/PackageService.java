package services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.packages.manager.model.PackageObject;
import com.packages.manager.model.Product;
import com.packages.manager.model.dto.PackageObjectDTO;
import com.packages.manager.model.dto.ProductDTO;

public class PackageService {

	private static Configuration  configuration = new Configuration().configure( "/resources/hibernate.cfg.xml");
	private static SessionFactory factory = configuration.buildSessionFactory();

	private static Logger logger = LoggerFactory.getLogger(PackageService.class);
	
	public static PackageObject getPackageById (int id) 
	{
		PackageObject packageObject = null;
		Session session = factory.openSession();
        try {
        	packageObject =  session.load(PackageObject.class, id);
            Hibernate.initialize(packageObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
		return packageObject;
	}
	
	public static PackageObject createPackageObject (PackageObjectDTO packageObjectDTO) 
	{
		Session session = factory.openSession();
		Transaction tx = null;
		PackageObject packageObject = new PackageObject();

		try
		{
			tx = session.beginTransaction();
			
			packageObject.setName(packageObjectDTO.getName());
			packageObject.setPrice(packageObjectDTO.getPrice());
			packageObject.setProducts(new ArrayList<Product>());
			
			session.save(packageObject);
		}
		catch (HibernateException e) 
		{
	         if (tx != null) {
	        	 tx.rollback();
	         }
	         logger.error(e.getMessage());
		}
		finally 
		{
            if (session != null && session.isOpen()) {
            	session.close(); 
            }
		}
		return packageObject;
	}
	
	public static boolean updatePackageObject (PackageObjectDTO packageObjectDTO, Product product) 
	{
		Transaction tx = null;
		Session session = factory.openSession();
		PackageObject packageObject = new PackageObject();
		try
		{
			tx = session.beginTransaction();
			packageObject.setName(packageObjectDTO.getName());
			packageObject.setPrice(packageObjectDTO.getPrice());
			if (product != null) {
				packageObject.getProducts().add(product);
			}
			session.save(packageObject);
			return true;
		}
		catch (HibernateException e) 
		{
	         if (tx != null) {
	        	 tx.rollback();
	         }
	         logger.error(e.getMessage());
	         return false;
		}
		finally 
		{
            if (session != null && session.isOpen()) {
            	session.close(); 
            }
		}
	}
	
	public static boolean deletePackage(int id) {
		return false;
	}
	
	public static List<PackageObject> getAllPackages() {
		return null;
	}
}
