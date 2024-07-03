import java.time.LocalDate;

public class Cheese {

    private String type;
    private String producer;
    private String originCountry;
    private int ageInMonths;
    private double pricePerKg;
    private LocalDate expiryDate;
    private boolean isVegan;

    public Cheese(String type, String producer, String originCountry, int ageInMonths, double pricePerKg, LocalDate expiryDate, boolean isVegan) {
        this.type = type;
        this.producer = producer;
        this.originCountry = originCountry;
        this.ageInMonths = ageInMonths;
        this.pricePerKg = pricePerKg;
        this.isVegan = isVegan;
        this.expiryDate = expiryDate;
    }

    // Setters: ChatGPT didn't include any setters. Should I?
    public void setType(String type){
        this.type = type;
    }

    public void setProducer(String producer){
        this.producer = producer;
    }

    public void setOriginCountry(String originCountry){
        this.originCountry = originCountry;
    }

    public void setAgeInMonths(int ageInMonths){
        this.ageInMonths = ageInMonths;
    }

    public void setPricePerKg(double pricePerKg){
        this.pricePerKg = pricePerKg;
    }

    public void setExpiryDate(LocalDate expiryDate){
        this.expiryDate = expiryDate;
    }

    public void setIsVegan(boolean isVegan){
        this.isVegan =  isVegan;
    }

    // Getters
    public String getType(){
        return type;
    }

    public String getProducer(){
        return producer;
    }

    public String getOriginCountry(){
        return originCountry;
    }

    public int getAgeInMonths(){
        return ageInMonths;
    }

    public double getPricePerKg(){
        return pricePerKg;
    }

    public LocalDate getExpiryDate(){
        return expiryDate;
    }

    public boolean getIsVegan(){
        return isVegan;
    }
}
