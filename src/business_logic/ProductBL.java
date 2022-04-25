package business_logic;

import business_logic.validators.ProductValidator;
import data_access.GenericDAO;
import data_access.ProductDAO;
import model.Product;

import java.util.ArrayList;

/** Business Logic class for Products - further logic and validations */
public class ProductBL {

    private final ProductValidator productValidator;
    private final ProductDAO productDAO;
    private final GenericDAO<Product> productGenericDAO;

    /** Basic constructor */
    public ProductBL(){
        productValidator = new ProductValidator();
        productDAO = new ProductDAO();
        productGenericDAO = new GenericDAO<>(Product.class);
    }


    /**
     * Update query on Product
     * @param oldProduct "old version" of product
     * @param newProduct "new version" of product
     * @throws InvalidInputException if new product is invalid
     */
    public void update(Product oldProduct, Product newProduct) throws InvalidInputException {
        productValidator.validateUpdate(newProduct);
        productGenericDAO.update(oldProduct,newProduct);
    }


    /**
     * Delete query on Product
     * @param product product to delete
     * @throws InvalidInputException if product cannot be deleted
     */
    public void delete(Product product) throws InvalidInputException {
        productValidator.validateDelete(product);
        productGenericDAO.delete(product);
    }


    /**
     * Insert query on Product
     * @param product product to insert
     * @throws InvalidInputException if product is invalid
     */
    public void insert(Product product) throws InvalidInputException {
        productValidator.validateInsert(product);
        productGenericDAO.insert(product);
    }


    /** Get a list containing all products in the database at the moment */
    public ArrayList<Product> getProducts(){return productGenericDAO.getExistingEntities();}


    /** Get the highest ID */
    public int getHighestID(){return productGenericDAO.getHighestID();}


    /** Get the ID of Product based on its name */
    public int getID(String name){return productDAO.getID(name);}

}
