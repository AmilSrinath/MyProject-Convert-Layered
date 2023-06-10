package lk.ijse.millmaster.dao;

import lk.ijse.millmaster.dao.Custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER,PRODUCT,ASSEST,BUYER,SUPPLIER,EMPLYOEE,ORDER
    }

    public CrudDAO getDAO(DAOTypes types){
        switch (types) {
            case USER:
                return new UserDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case ASSEST:
                return new AssestDAOImpl();
            case BUYER:
                return new BuyerDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case EMPLYOEE:
                return new EmployeeDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            default:
                return null;
        }
    }
}
