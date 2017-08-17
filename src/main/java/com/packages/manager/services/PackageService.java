package com.packages.manager.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.packages.manager.model.PackageObject;
import com.packages.manager.model.Product;
import com.packages.manager.model.dto.PackageObjectDTO;

import utils.HibernateUtils;

public class PackageService {

	private static Logger logger = LoggerFactory.getLogger(PackageService.class);
	
	public static PackageObject getPackageById (int id) 
	{
		PackageObject packageObject = null;
		Session session = HibernateUtils.getSessionFactory().openSession();
        try {
        	packageObject = session.load(PackageObject.class, id);
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
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		PackageObject packageObject = new PackageObject();

		try
		{
			tx = session.beginTransaction();
			
			packageObject.setName(packageObjectDTO.getName());
			packageObject.setDescription(packageObjectDTO.getDescription());
			packageObject.setPrice(packageObjectDTO.getPrice());
			
			session.save(packageObject);
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
		return packageObject;
	}
	
	public static boolean updatePackageObject (PackageObject packageObject, Product product) 
	{
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			if (product != null) {
				packageObject.getProducts().add(product);
			}
			session.update(packageObject);
			tx.commit();
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
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		PackageObject packageObject = PackageService.getPackageById(id);
		try
		{
			tx = session.beginTransaction();
			session.delete(packageObject);
			tx.commit();
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
	
	public static List<PackageObject> getAllPackages() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Query query= session.createQuery("from PackageObject");
		List<PackageObject> packages = query.getResultList();
		session.close();
		return packages;
	}
}
