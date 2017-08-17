package com.packages.manager.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.packages.manager.model.PackageObject;
import com.packages.manager.model.Product;
import com.packages.manager.model.dto.ProductDTO;

import utils.HibernateUtils;


public class ProductService {

	private static Logger logger = LoggerFactory.getLogger(ProductService.class);

	public static Product getProductById (int id) 
	{
		Product product = null;
		Session session = HibernateUtils.getSessionFactory().openSession();
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
		Session session = HibernateUtils.getSessionFactory().openSession();
		Query query= session.createQuery("from Product where ean=:ean");
		query.setParameter("ean", ean);
		Product product = (Product) query.uniqueResult();
		session.close();
		return product;
	}
	
	public static Product createProduct (ProductDTO productDTO) 
	{
		if (ProductService.getProductByEan (productDTO.getEan()) != null) {
			return ProductService.updateProduct(productDTO, null);
		}
		
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		Product product = new Product();

		try
		{
			tx = session.beginTransaction();
			
			product.setEan(productDTO.getEan());
			product.setName(productDTO.getName());
			product.setPrice(productDTO.getPrice());
			
			session.save(product);
			tx.commit();
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
	
	public static Product updateProduct (ProductDTO productDTO, PackageObject packageObject) 
	{
		Product product = ProductService.getProductByEan(productDTO.getEan());
		if (product == null) {
			return null;
		} else {
			Transaction tx = null;
			Session session = HibernateUtils.getSessionFactory().openSession();
			try
			{
				tx = session.beginTransaction();
				product.setName(productDTO.getName());
				product.setPrice(productDTO.getPrice());
//				if (packageObject != null) {
//					product.getPackages().add(packageObject);
//				}
				session.update(product);
				tx.commit();
				return product;
			}
			catch (HibernateException e) 
			{
		         if (tx != null) {
		        	 tx.rollback();
		         }
		         logger.error(e.getMessage());
		         return null;
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
