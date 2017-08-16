package services;

import java.util.ArrayList;

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
import com.packages.manager.model.dto.ProductDTO;


public class ProductService {

	private static Configuration  configuration = new Configuration().configure( "/resources/hibernate.cfg.xml");
	private static SessionFactory factory = configuration.buildSessionFactory();

	private static Logger logger = LoggerFactory.getLogger(ProductService.class);

	public static Product getProductById (int id) 
	{
		Product product = null;
		Session session = factory.openSession();
        try {
        	product =  session.load(Product.class, id);
            Hibernate.initialize(product);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
		return product;
	}
	
	public static Product getProductByEan (String ean) 
	{
		Query query= factory.getCurrentSession().createQuery("from Product where ean=:name");
		query.setParameter("ean", ean);
		return (Product) query.uniqueResult();
	}
	
	public static Product createProduct (ProductDTO productDTO) 
	{
		Session session = factory.openSession();
		Transaction tx = null;
		Product product = new Product();

		try
		{
			tx = session.beginTransaction();
			
			product.setEan(productDTO.getEan());
			product.setName(productDTO.getName());
			product.setPrice(productDTO.getPrice());
			product.setPackages(new ArrayList<PackageObject>());
			
			session.save(product);
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
		return product;
	}
	
	public static boolean updateProduct (ProductDTO productDTO, PackageObject packageObject) 
	{
		Product product = ProductService.getProductByEan(productDTO.getEan());
		if (product == null) {
			return false;
		} else {
			Transaction tx = null;
			Session session = factory.openSession();
			try
			{
				tx = session.beginTransaction();
				product.setName(productDTO.getName());
				product.setPrice(productDTO.getPrice());
				if (packageObject != null) {
					product.getPackages().add(packageObject);
				}
				session.save(product);
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
	}
}
