package lk.ijse.millmaster.dao;

import lk.ijse.millmaster.dao.Custom.Impl.ProductDAOImpl;
import lk.ijse.millmaster.dao.Custom.Impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER,PRODUCT
    }

    public CrudDAO getDAO(DAOTypes types){
        switch (types) {
            case USER:
                return new UserDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            default:
                return null;
        }
    }
}
