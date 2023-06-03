package lk.ijse.millmaster.bo;

import lk.ijse.millmaster.bo.Custom.Impl.ProductBOImpl;
import lk.ijse.millmaster.bo.Custom.Impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        USER,PRODUCT
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case USER:
                return new UserBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            default:
                return null;
        }
    }
}
