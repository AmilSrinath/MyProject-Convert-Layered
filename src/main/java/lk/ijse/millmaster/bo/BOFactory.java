package lk.ijse.millmaster.bo;

import lk.ijse.millmaster.bo.Custom.Impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        USER
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
