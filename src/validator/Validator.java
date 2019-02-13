package validator;

public class Validator {

    //private byte[] matcherPIN;

    private String matcherUser = "user";
    private String matcherAdmin = "admin";

    public Validator() {

    }

/*    public boolean verifyPIN(byte[] pattern){
        return pattern.equals(matcherPIN);
    }*/
    public boolean verifyUser(String pattern){
        return pattern.equals(matcherUser);
    }
    public boolean verifyAdmin(String pattern){
        return pattern.equals(matcherAdmin);
    }
}
