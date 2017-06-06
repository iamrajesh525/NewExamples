package examples.com.examples.recyclerview;

import java.util.ArrayList;

/**
 * Created by Rajesh on 12/15/2016.
 */

public class InstituteListModel {
    String instituteId,instituteName,phoneNumber,instituteProfilePic,description,view_count;
    ArrayList<String> courses= new ArrayList<>();



    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }
    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }


    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInstituteProfilePic() {
        return instituteProfilePic;
    }

    public void setInstituteProfilePic(String instituteProfilePic) {
        this.instituteProfilePic = instituteProfilePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

