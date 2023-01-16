package ru.petrashova.web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Поле 'Имя' не должно быть пустым")
    private String name;
    @Column(name = "last_name")
  //  @NotEmpty(message = "Поле 'Фамилия' не должно быть пустым")
    private String lastName;
    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    private int year;
    @Column(name = "email", unique = true)
    @NotEmpty(message = "Поле 'e-mail' не должно быть пустым")
    private String email;
    @Column(name = "login")
    @NotEmpty(message = "Поле 'Имя пользователя' не должно быть пустым")
    private String login;
    @Column(name = "password")
    @NotEmpty(message = "Поле 'Пароль' не должно быть пустым")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
         name = "users_roles",
         joinColumns = @JoinColumn(name = "user_id"),
         inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set <Role> roles = new HashSet<>();

    public User() {
    }


    public User(int id, String name, String lastName, int year, String email, String login, String password, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.year = year;
        this.email = email;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getRolesToString() {
        String s = roles.stream().map(Role::getRole).collect(Collectors.joining("  "));
        return s.replace("ROLE_", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return year == user.year && login == user.login && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, year, email, login, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", year=" + year +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
