package uwl.senate.coc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class College {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String year;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.PERSIST, orphanRemoval=true)
    @JoinTable(name = "dept_college",
            joinColumns =
                    { @JoinColumn(name = "college_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "dept_id", referencedColumnName = "id")} )
    public List<Department> departments;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.PERSIST, orphanRemoval=true)
    @JoinTable(name = "user_college",
            joinColumns =
                    { @JoinColumn(name = "college_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    { @JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;

    public College() {
    }

    private College( Builder b ) {
        this.id = b.id;
        this.name = b.name;
        this.year = b.year;
        this.users = b.users;
        this.departments = b.departments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setYear(String year){
        this.year = year;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String year;
        private List<User> users;
        private List<Department> departments;

        public Builder id( Long id ) {
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder year(String year){
            this.year = year;
            return this;
        }
        public Builder users(List<User> users){
            this.users = users;
            return this;
        }

        public Builder departments(List<Department> departments){
            this.departments = departments;
            return this;
        }

        public College build() {
            return new College( this );
        }
    }
}


