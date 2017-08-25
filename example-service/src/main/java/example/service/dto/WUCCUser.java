package example.service.dto;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WUCCUser {

    private String sub;
    private String preferred_username;
    private Collection<String> role;
    private String fullName;
    private String salesDepartment;
    private String superAdmin;
    private String name;

    public String getSub(){return this.sub;}
    public void setSub(String sub){this.sub=sub;}

    public String getPreferred_username(){return this.preferred_username;}
    public void setPreferred_username(String preferred_username){this.preferred_username=preferred_username;}

    public Collection<String> getRole() {
        return role;
    }
    public void setRole(Collection<String> role){this.role = role;}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSalesDepartment() {
        return salesDepartment;
    }

    public void setSalesDepartment(String salesDepartment) {
        this.salesDepartment = salesDepartment;
    }

    public String getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(String superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
