package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.ProductCategoryRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;


@Service
public class ProductCategoryService implements IProductCategoryService {

	public final String[] categoryList = {"vêtements", "outils", "mobilier", "ustensiles de cuisine" };
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	
	@Override
	public void createCategory(String name) {
		Collection <ProductCategory> existingCategories = this.productCategoryRepository.findByName(name); 
		if ( existingCategories != null && existingCategories.size() > 0){
			//la categorie existe deja, on ne fait rien :))
		}
		else{
			ProductCategory category = new ProductCategory ( );
			category.setName(name);
			this.productCategoryRepository.save(category);
		}
	}

	@Override
	public Collection<ProductCategory> getCategories() {
		return (Collection<ProductCategory>) this.productCategoryRepository.findAll();
	}


	@Override
	public ProductCategory getById(int id) {

		return this.productCategoryRepository.findOne(id);
	}

}
