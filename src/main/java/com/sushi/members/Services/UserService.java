package com.sushi.members.Services;

import com.sushi.members.jpa.LocalStoreRepository;
import com.sushi.members.jpa.PersonRepository;
import com.sushi.members.jpa.RoleRepository;
import com.sushi.members.jpa.UserRepository;
import com.sushi.members.jpa.entity.LocalShop;
import com.sushi.members.jpa.entity.Person;
import com.sushi.members.jpa.entity.Role;
import com.sushi.members.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private Map<String, User> users;
    private Map<String,String> usernameByLowerCase;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LocalStoreRepository localStoreRepository;
    @Autowired
    private PersonRepository personRepository;
    public void addRole(Long id, String roleName) {
        if(roleRepository.findByRoleName(roleName)==null) {
            Role role = new Role(roleName);
            role.setRoleId(id);
            roleRepository.save(role);
        }

    }
    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }
    public void initData() {
        if(!localStoreRepository.existsByStorename("总店")) {
            LocalShop localShop = new LocalShop();
            localShop.setStorename("总店");
            localShop.setStoreLevel(1);
            localShop.setStoreAddress("总部大厦");
            localShop.setStoreAdmin("马总");
            localStoreRepository.save(localShop);
        }
        if(!personRepository.existsByUsername("testUser")) {
            Person person = new Person();
            person.setUsername("testUser");
            person.setPoint(300L);
            person.setEmail("testemail@qq.com");
            person.setStatus("2");
            person.setPhoneNum("13911001234");
            person.setRegisterDate(LocalDate.now());
            personRepository.save(person);
        }
    }

    public void addUser(String username) {

        if(!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setDisplayName("管理员");
            user.setActive(true);
            List<Role> roleList = roleRepository.findAll();
            user.setRoles(roleList);
            user.setEffectiveData(LocalDate.now());
            user.setExpiryDate(LocalDate.now().plusYears(1));
            user.setPassword(String.valueOf("root".hashCode()));
            userRepository.save(user);

        }

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= null;
        username = username.toLowerCase();
        user = userRepository.findByUsername(username);
        if(user==null) {
            throw new UsernameNotFoundException("Could not find user.");

        }
        return user;
    }
    public boolean isValidCredentials(String username, String password) {
        boolean correct = false;
        String passwordHash = String.valueOf(password.hashCode());
        UserDetails user = loadUserByUsername(username);
        if(user.getPassword().equals(passwordHash)) {
            correct = true;
        }
        return correct;
    }

}
