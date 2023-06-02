package Wiki;

public class MobilePhone {  
    //Creating properties of the class  
    private String brand;    
    private String name;    
    private int ram;  
    private int rom;  
    
    //Setter and Getters  
    public MobilePhone() {};
    public MobilePhone(String brand, String name, int ram, int rom) {
		super();
		this.brand = brand;
		this.name = name;
		this.ram = ram;
		this.rom = rom;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRam() {
		return ram;
	}
	public void setRam(int ram) {
		this.ram = ram;
	}
	public int getRom() {
		return rom;
	}
	public void setRom(int rom) {
		this.rom = rom;
	}
    
    
	
}  
