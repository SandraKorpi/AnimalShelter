package sandrakorpi.animalshelterapi.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sandrakorpi.animalshelterapi.Enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String email;
    private String userName;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id")) // Koppla roller till användare
    @Column(name = "role") // Kolumnnamn i tabellen user_roles
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    public void addRole(Role role) {
        this.roles.add(role);
        }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Om kontot aldrig ska löpa ut
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Om kontot aldrig ska låsas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Om inloggningsuppgifter aldrig ska löpa ut
    }

    @Override
    public boolean isEnabled() {
        return true; // Om kontot alltid ska vara aktivt
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }
}
