package mostain.victory.com.onlinevehicle;

/**
 * Created by shifa on 23/7/2018.
 */

public class ModelOfData {
    String email;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    String carType;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getTyres() {
        return tyres;
    }

    public void setTyres(String tyres) {
        this.tyres = tyres;
    }

    public ModelOfData(String email, String carType, String plateNumber, String glass, String tyres) {
        this.email = email;
        this.plateNumber = plateNumber;
        this.glass = glass;
        this.tyres = tyres;
        this.carType = carType;
    }

    String plateNumber;
    String glass;
    String tyres;
}
