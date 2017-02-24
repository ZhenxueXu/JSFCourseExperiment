
package beans;

import entities.Users;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named(value = "controllerBean")
@SessionScoped
public class ControllerBean implements Serializable {

    public ControllerBean() {
    }
    private String telephone;
    @PersistenceContext(unitName = "EcityRentWebPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private String password;
    private Users user;
    
    public String login(){
        user=em.find(Users.class, telephone);
        if(user==null){
            FacesMessage msg=new FacesMessage("用户不存在");
            FacesContext.getCurrentInstance().addMessage("login:telephone", msg);
            return "null";
        }else if(user.getPassword() == null ? password != null : !user.getPassword().equals(password)){
            FacesMessage msg=new FacesMessage("密码错误");
            FacesContext.getCurrentInstance().addMessage("login:password", msg);
            return "null";
            
        }else{
            return "index";
        }
    }
    

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    
}
