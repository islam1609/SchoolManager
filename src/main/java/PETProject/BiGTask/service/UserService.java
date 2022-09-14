package PETProject.BiGTask.service;

import PETProject.BiGTask.model.Role;
import PETProject.BiGTask.model.User;
import PETProject.BiGTask.repository.roleRepository;
import PETProject.BiGTask.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  userRepository userRepository;

    @Autowired
    private roleRepository roleRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public List<User> getAllStudent(){
        Role role = new Role();
        role.setRole("ROLE_STUDENT");
        role.setId(2L);
        return userRepository.findAllByRoles(role);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findAllByEmail(email);
        if(user != null){
            return user;
        }else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    public User registerUser(User user){
        User userCheck = userRepository.findAllByEmail(user.getEmail());
        if(userCheck==null){
            Role userRole = roleRepository.findByRole("ROLE_STUDENT");
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }
}
