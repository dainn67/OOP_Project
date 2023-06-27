/**
 * 
 */
/**
 * @author homesweethome
 *
 */
module oop_project {
//	requires org.jsoup;
//	requires com.google.gson;
    requires jsoup;
    requires gson;
    requires rt;
    opens main_package to com.google.gson;
}
