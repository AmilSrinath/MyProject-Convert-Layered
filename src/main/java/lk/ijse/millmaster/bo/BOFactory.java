package lk.ijse.millmaster.bo;

import lk.ijse.millmaster.bo.Custom.Impl.AssestBOImpl;
import lk.ijse.millmaster.bo.Custom.Impl.BuyerBOImpl;
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
        USER,PRODUCT,ASSEST,BUYER
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
            default:
                return null;
        }
    }
}
