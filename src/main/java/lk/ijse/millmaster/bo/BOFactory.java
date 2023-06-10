package lk.ijse.millmaster.bo;

import lk.ijse.millmaster.bo.Custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        USER,PRODUCT,ASSEST,BUYER,SUPPLIER,EMPLYOEE,ORDER
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case USER:
                return new UserBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case ASSEST:
                return new AssestBOImpl();
            case BUYER:
                return new BuyerBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case EMPLYOEE:
                return new EmployeeBOImpl();
            case ORDER:
                return new OrderBOImpl();
            default:
                return null;
        }
    }
}
