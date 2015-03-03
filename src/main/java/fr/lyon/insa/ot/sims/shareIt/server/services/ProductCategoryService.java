package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.lyon.insa.ot.sims.shareIt.server.dao.ProductCategoryRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;


@Service
public class ProductCategoryService{

	public final String[] categoryList = {"vêtements", "outils", "mobilier", "ustensiles de cuisine" };
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	
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

	public Collection<ProductCategory> getCategories() {
		return (Collection<ProductCategory>) this.productCategoryRepository.findAll();
	}

	public ProductCategory getById(int id) {

		return this.productCategoryRepository.findOne(id);
	}

}
